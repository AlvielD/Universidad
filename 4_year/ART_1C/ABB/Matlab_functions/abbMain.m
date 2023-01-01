% abbMain          Script to test abb*.m functions and RAPID MatlabCom module
%
% This function is part of a set of functions intendend to make the use of
% PCSDK from Matlab a little bit easier. For more on PCSDK see:
% http://developercenter.robotstudio.com/pcsdk 
% and information on how Matlab support .NET applications see:
% https://se.mathworks.com/help/matlab/matlab_external/using-net-from-matlab-an-overview.html
%
% The RAPID program should have module "MatlabCom"
% Run this script on a PC connected to the same network as ABB robot
%
% use:  abbMain;                              % use Rudolf 
%  or:  ABBrc = abbCom('Norbert'); abbMain;   % use Norbert       

% Karl S : March 2014, January 2019
          
%% *********** INITIALIZE  **************  
clc; disp(' '); disp(' '); disp(' ');
disp([mfilename,': start ',datestr(now)]);

% Check if ABB Robot Controller struct ABBrc exists and is connected
if (~exist('ABBrc','var') || ~isstruct(ABBrc))
    ABBrc = abbCom('Rudolf');    
end
if ( ~ABBrc.connected || ...
     ~isfield(ABBrc,'ctrl') || ...
     ~isa(ABBrc.ctrl,'ABB.Robotics.Controllers.Controller') || ...     
     isempty(ABBrc.ctrl.CurrentUser) )
    ABBrc.robot = 'Rudolf';
    ABBrc = abbCom(ABBrc);
end

if ~ABBrc.connected 
    disp([mfilename,': NOT connected, quit ',mfilename,'.']);
    return;
end

try   
    RobotName = char(ABBrc.ctrl.SystemName);
    disp([mfilename,': RobotName = ',RobotName,', og ABBrc.robot = ',ABBrc.robot]);
    UserName = char(ABBrc.ctrl.CurrentUser.Name);
    disp([mfilename,': UserName = ',UserName,', og ABBrc.user = ',ABBrc.user]);
catch me
    return;
end

try
    ABBrc = abbMaster(ABBrc);  
    % Note that Master Access may be lost, it should be checked before use
    if ABBrc.mMaster.IsMaster()
        disp([mfilename,': Master Access granted']);
    else
        disp([mfilename,': Master Access NOT granted']);
    end
catch me
    return
end

disp(' ');
disp([mfilename,': Press a key to continue.']);
pause; 

%% Make Matlab variables to reflect RAPID variables in program T_ROB1 and module MatlabCom
cMC = char('T_ROB1','MatlabCom');  
p0      = abbRead(ABBrc, abbNew(char(cMC,'pMatlabCom0'), 'robtarget'));
p1      = abbRead(ABBrc, abbNew(char(cMC,'pMatlabCom1'), 'robtarget'));
p2      = abbRead(ABBrc, abbNew(char(cMC,'pMatlabCom2'), 'robtarget'));
pCur    = abbRead(ABBrc, abbNew(char(cMC,'pMatlabComCur'), 'robtarget'));
tool    = abbRead(ABBrc, abbNew(char(cMC,'tMatlabCom'), 'tooldata'));
wobj    = abbRead(ABBrc, abbNew(char(cMC,'wobjMatlabCom'), 'wobjdata'));
joint   = abbRead(ABBrc, abbNew(char(cMC,'jMatlabCom'), 'jointtarget'));
nMAXLEN = abbRead(ABBrc, abbNew(char(cMC,'nMatlabComMAXLEN'), 'num'));
MAXLEN = nMAXLEN.Value;     % and read the MAXLEN constant
% NOT ready for speed and zone data
% speed   = abbRead(ABBrc, abbNew(char(cMC,'vMatlabCom'), 'speeddata'));
% zone    = abbRead(ABBrc, abbNew(char(cMC,'zMatlabCom'), 'zonedata'));
disp(' ');
disp('String of variable is      ex: s = abbString(p0).')
disp('Varibles may be edited by  ex: p0 = abbEdit(p0).')
disp('Varibles may be viewed by  ex: t = abbText(p0).')
disp('Varibles may be written by ex: r = abbWrite(ABBrc,p0).')
disp(' ');

return;   % 

  
