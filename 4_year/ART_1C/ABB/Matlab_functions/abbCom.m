function s = abbCom(s)
% abbCom           Make a struct to use for communication with ABB robot
%               
% This function is part of a set of functions intendend to make the use of
% PCSDK from Matlab a little bit easier. For more on PCSDK see:
% http://developercenter.robotstudio.com/pcsdk 
% and information on how Matlab support .NET applications see:
% https://se.mathworks.com/help/matlab/matlab_external/using-net-from-matlab-an-overview.html
%
% see also: abbMaster
%
% use:
%  s = abbCom()        % just make an empty structure s
%  s = abbCom(s)       % reuse structure s
%  s = abbCom(robot)   % connect to UiS robot, i.e. 'Norbert' eller 'Rudolf' 
% -----------------------------------------------------------------------------
%  s      A struct with the following fields:
%   s.ctrl         controller object, 'ABB.Robotics.Controllers.Controller'
%   s.scan         scan object, 'ABB.Robotics.Controllers.Discovery.NetworkScanner' 
%   s.connected    true or false
%   s.user         user name or empty
%   s.robot        the name of the robot to connect to
%   s.message      last error or information message 
%   s.me           last error or information message from Matlab (catch)
%   s.WRD          object for nMatlabComWRD (What Robot Does)
%   s.WMW          object for nMatlabComWMW (What Matlab Wants robot to do next)
%   s.Num1         object for nMatlabComNum1 
% -----------------------------------------------------------------------------
% ex:  s = abbCom('Norbert');

% Karl S : March 2014, January 2019

adrNorbert = '/152.94.0.38';
adrRudolf = '/152.94.0.39';
if (nargin < 1); s = 0; end;

disp([mfilename,': start ',datestr(now)]);

% input argument as string, struct (or something else)
if ischar(s)    % robot name
    s = struct( 'ctrl',[], 'scan',[],  ...
                'connected', false, ...
                'user', '', ...
                'robot', s, ...
                'WRD', [], ...
                'WMW', [], ...
                'Num1', [], ...
                'message', [mfilename,': struct created ',datestr(now)], ...
                'me', [] );
elseif isstruct(s)  
    if ~isfield(s,'ctrl'); s.ctrl = []; end;
    if ~isfield(s,'scan'); s.scan = []; end;
    s.connected = false; 
    if (~isfield(s,'user') || ~ischar(s.user)); s.user = ''; end;
    if (~isfield(s,'robot') || ~ischar(s.robot)); s.robot = ''; end;
    s.message = [mfilename,': use input argument ''s'' ',datestr(now)];
    s.me = [];
else  % no argument (-> 0) or unexpected argument
    s = struct( 'ctrl',[], 'scan',[],  ...
                'connected', false, ...
                'user', '', ...
                'robot', '', ...
                'WRD', [], ...
                'WMW', [], ...
                'Num1', [], ...
                'message', [mfilename,': empty struct created ',datestr(now)], ...
                'me', [] );
    return;
end

% test robot (name)
if ((numel(s.robot) > 0) && (upper(s.robot(1)) == 'N'))
    adrRobot = adrNorbert;
elseif ((numel(s.robot) > 0) && (upper(s.robot(1)) == 'R'))
    adrRobot = adrRudolf;
else
    s.message = char(s.message, ' robot should be Norbert or Rudolf.');
    return;
end

if ~ispc()
    s.message = char(s.message, ' .NET package works only on (Windows) PC.');
    return
end

if ~isa(s.ctrl,'ABB.Robotics.Controllers.Controller') 
    if ~isa(s.scan,'ABB.Robotics.Controllers.Discovery.NetworkScanner') 
        try
            disp('Try to connect to .NET DLL.');
            asmR = NET.addAssembly('ABB.Robotics');
            asmRC = NET.addAssembly('ABB.Robotics.Controllers');
        catch me
            if strcmp(me.identifier, 'MATLAB:NET:CLRException:AddAssembly')
                s.message = char(s.message, ' Perhaps ABB.Robotics DLL should use 32 bit Matlab');
            end
            s.message = char(s.message, ' Error contacting ABB.Robotics DLL.');
            s.me = me;
            return
        end
        %
        if ~( exist('asmR','var') && isa(asmR,'NET.Assembly') && ...
              exist('asmRC','var') && isa(asmRC,'NET.Assembly') )
            s.message = char(s.message, ' Error making ''asmR'' or ''asmRC''.');
            return
        end

        try
            disp(' Make object ''scan'' and locate robot.');
            s.scan =  ABB.Robotics.Controllers.Discovery.NetworkScanner();
        catch me
            s.message = char(s.message, ' Error making ''scan'' object.');
            s.me = me;
            return
        end
    end  
    
    % now: isa(s.scan,'ABB.Robotics.Controllers.Discovery.NetworkScanner') == true
    disp(' Find and display available (ABB) controllers');
    try
        s.scan.Scan();
        cic = s.scan.Controllers(); 
        if (cic.Count <= 0)   
            disp(' no controller available, wait and try again.');
            pause(10); 
            cic = s.scan.Controllers();   % uten semikolon vises resultatet
            if (cic.Count <= 0)
                s.message = char(s.message, ' still no controller available, return.');
                return
            end
        end
        % 
        disp([mfilename,': Make object ''ctrl'' for ,',s.robot,' (',adrRobot,').']);
        s.ctrl = ABB.Robotics.Controllers.Controller(adrRobot);
    catch me
        s.message = char(s.message, ' Error making ''ctrl'' object.');
        s.me = me;
        return
    end
end

if ~isa(s.ctrl,'ABB.Robotics.Controllers.Controller')
    s.message = char(s.message, ' ''ctrl'' object not as it should be.');
    return
end

try
    s.robot = char(s.ctrl.SystemName);  
    s.connected = true;       
catch me
    s.message = char(s.message, ' error: no name for robot.');
    s.me = me;
    return
end
    
disp([' Established connection to ''',s.robot,'''. Log on as ''Default User''.']);    

if ( isempty(s.ctrl.CurrentUser) || ~strcmpi(char(s.ctrl.CurrentUser.Name),'Default User') )
    try
        s.ctrl.Logon( ABB.Robotics.Controllers.UserInfo.DefaultUser );
    catch me
        s.message = char(s.message, ' Error during logon, s.ctrl.Logon(..).');
        s.me = me;
        s.user = '';
    end
end

if strcmpi(char(s.ctrl.CurrentUser.Name),'Default User')
    s.message = char(s.message, ' Connected and logged on as ''Default User''.');
    disp(' Connected and logged on as ''Default User''.');
    s.user = 'Default User';   
    % 
    try
        ss = NET.createArray('System.String',3);  
        ss.Set(0, 'T_ROB1'); ss.Set(1, 'MatlabCom'); ss.Set(2, 'nMatlabComWRD');
        s.WRD = s.ctrl.Rapid.GetRapidData( ss ); 
        ss.Set(2, 'nMatlabComWMW');
        s.WMW = s.ctrl.Rapid.GetRapidData( ss ); 
        ss.Set(2, 'nMatlabComNum1');
        s.Num1 = s.ctrl.Rapid.GetRapidData( ss ); 
    catch me
        s.message = char(s.message, ' Could not connect to MatlabCom module.');
        s.me = me;
    end
else
    s.message = char(s.message, ' Error: NOT logged on as ''Default User''.');
    disp(' Error: NOT logged on as ''Default User''.');
end

return
