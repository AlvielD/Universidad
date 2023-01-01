function s = abbMaster(s)
% abbMaster        Asks for master access to a robot
%               
% This function is part of a set of functions intendend to make the use of
% PCSDK from Matlab a little bit easier. For more on PCSDK see:
% http://developercenter.robotstudio.com/pcsdk 
% and information on how Matlab support .NET applications see:
% https://se.mathworks.com/help/matlab/matlab_external/using-net-from-matlab-an-overview.html
%
% see also: abbCom
%
% use:
% s = abbMaster(s)
% ------------------------------------------------------------------------------
% s   a struct from abbCom
%     a new field is added: s.mMaster (ABB.Robotics.Controllers.Mastership)
% ------------------------------------------------------------------------------

% Karl S : March 2014, January 2019

if (nargin < 1); s = 0; end;

if ~isfield(s,'ctrl') || ~isa(s.ctrl,'ABB.Robotics.Controllers.Controller')
    disp([mfilename,': Argument is not a valid communication structure: call abbCom first.']);
    s = abbCom(s);
end

if ~isfield(s,'ctrl') || ~isa(s.ctrl,'ABB.Robotics.Controllers.Controller') || ~s.connected
    disp([mfilename,': No access to controller.']);
    return
end

if ~isfield(s,'mMaster') || ~isa(s.mMaster,'ABB.Robotics.Controllers.Mastership') || ~s.mMaster.IsMaster()
    disp([mfilename,': Grant ''Master access'' (use Flex Pendant)']);
    s.mMaster = ABB.Robotics.Controllers.Mastership.Request(s.ctrl.Rapid);
end

return

   