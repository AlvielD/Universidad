function [nofW,t] = abbWriteArray(s,arrXYZ)
% abbWriteArray    Write to ABB RAPID array nMatlabComArray* in MatlabCom
%                  Communication with robot using PC SDK DLL (ABB.Robotics)
% Write access (master) should be granted before this function is used
%               
% This function is part of a set of functions intendend to make the use of
% PCSDK from Matlab a little bit easier. For more on PCSDK see:
% http://developercenter.robotstudio.com/pcsdk 
% and information on how Matlab support .NET applications see:
% https://se.mathworks.com/help/matlab/matlab_external/using-net-from-matlab-an-overview.html
%
% This function writes to RAPID variables in module MatlabCom
% Write arrXYZ(:,1) into nMatlabComArrayX1, arrXYZ(:,2) into nMatlabComArrayY1
% and arrXYZ(:,3) into nMatlabComArrayZ1. 
% But note that if (nMatlabComWRD == 1) then nMatlabComArrayX2, nMatlabComArrayY2
% and nMatlabComArrayZ2 will be used instead.
% After writing is done, the number of elements written to each array will
% be written to nMatlabComLen1 (or nMatlabComLen2).
% If (size(arrXYZ) > nMatlabComMAXLEN) only the first nMatlabComMAXLEN
% rows of arrXYZ will be used.
% Finally, if nMatlabComWMW == 0, it is changed to 1 or 2 (toArray number)
%
% see also: abbCom, abbMain
%
% use:
%  [nofW,t] = abbWriteArray(s,arrXYZ);
% --------------------------------------------------------------------------
%  nofW    Number of rows written, 0 if error
%  t       Text with messages of what was done.
%  s       A struct for communication, see abbCom
%  arrXYZ  An array of numbers to write to RAPID
% --------------------------------------------------------------------------
% ex:   arrXYZ = randn(15,3);
%       s = abbCom('Norbert'); 
%       s = abbMaster(s);
%       [nofW,t] = abbWriteArray(s,arrXYZ);

% Karl S : January 2019

nofW = 0;
if nargin < 2
    t = [mfilename,': two input arguments are needed, see help. (',datestr(now),')'];
    disp(t);
    return
end

if ~isstruct(s) || ~isfield(s,'ctrl') || ~isa(s.ctrl,'ABB.Robotics.Controllers.Controller')
    t = [mfilename,': no connection to ABB robot, check argument 1 (s.ctrl)'];   
    return
end

if ~isnumeric(arrXYZ)
    t = [mfilename,': second argument, arrXYZ, is not numeric.'];   
    return
end
[M,N] = size(arrXYZ);
t = [mfilename,' started ',datestr(now),' : size of arrXYZ is ',int2str(M),'x',int2str(N)];

% write access
if ~isfield(s,'mMaster') || ~isa(s.mMaster,'ABB.Robotics.Controllers.Mastership') || ~s.mMaster.IsMaster()
    disp([mfilename,': No ''Master access'', press ''grant'' on Flex pendant.']);
    s.mMaster = ABB.Robotics.Controllers.Mastership.Request(s.ctrl.Rapid);
    if s.mMaster.IsMaster()
        disp([mfilename,': Master access granted. :-) ',datestr(now)]);
    end
else
    disp([mfilename,': Master access was granted ',datestr(now)]);
end
if ~s.mMaster.IsMaster()
    t = char(t, ' no Master access (write access) to robot.');
    return
end

try
    % note that connection to some variables is established in abbCom
    wrd = s.WRD.Value.Value;
    if (wrd == 1)
        toArray = 2;
    else
        toArray = 1;
    end
    nMAXLEN = abbRead(s, abbNew(char('T_ROB1','MatlabCom','nMatlabComMAXLEN'), 'num'));
    MAXLEN = nMAXLEN.Value;
    t = char(t, [' write ',int2str(min(MAXLEN,M)),' rows to ',s.robot,' nMatlabComArray(X/Y/Z)',int2str(toArray)]);
    %
    % use .NET directly to make ss, and define variables to write into
    ss = NET.createArray('System.String',3);  
    ss.Set(0, 'T_ROB1'); ss.Set(1, 'MatlabCom'); ss.Set(2, 'nMatlabComLen1');
    ss.Set(2, ['nMatlabComArrayX',int2str(toArray)]);
    arrayX = s.ctrl.Rapid.GetRapidData( ss );
    ss.Set(2, ['nMatlabComArrayY',int2str(toArray)]);
    arrayY = s.ctrl.Rapid.GetRapidData( ss );
    ss.Set(2, ['nMatlabComArrayZ',int2str(toArray)]);
    arrayZ = s.ctrl.Rapid.GetRapidData( ss );
    ss.Set(2, ['nMatlabComLen',int2str(toArray)]);
    arrayLen = s.ctrl.Rapid.GetRapidData( ss );
    rNum =  ABB.Robotics.Controllers.RapidDomain.Num();
    if ~s.mMaster.IsMaster()  % check master access again
        s = abbMaster(s); 
    end
    for m = 1:min(MAXLEN,M)
        rNum.Value = arrXYZ(m,1);
        arrayX.WriteItem(rNum, nofW);  % NOTE C indexing, first element index 0
        if N >= 2
            rNum.Value = arrXYZ(m,2);
            arrayY.WriteItem(rNum, nofW);
        end
        if N >= 3
            rNum.Value = arrXYZ(m,3);
            arrayZ.WriteItem(rNum, nofW);
        end
        nofW = nofW + 1;
    end
    t = char(t, [' Number of rows written: ',num2str(nofW),' (',int2str(nofW*min(3,N)),' elements)']);
    arrayLen.Value.Value = nofW;  % and write this to RAPID
    wmw = s.WMW.Value.Value;   
    if wmw == 0  
        s.WMW.Value.Value = toArray;
        t = char(t, [' Also set WMW to ',num2str(toArray)]);
    else
        t = char(t, [' Do not set WMW, it was ',num2str(wmw)]);
    end
    %
catch me
    t = char(t, [' error while writing ',datestr(now)]);
    disp(me);
end

end