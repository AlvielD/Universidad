function r = abbWrite(s,r)
% abbWrite         Write to ABB RAPID variable from a struct
%                  Communication with robot using PC SDK DLL (ABB.Robotics)
% Write access (master) should be granted before this function is used
%               
% This function is part of a set of functions intendend to make the use of
% PCSDK from Matlab a little bit easier. For more on PCSDK see:
% http://developercenter.robotstudio.com/pcsdk 
% and information on how Matlab support .NET applications see:
% https://se.mathworks.com/help/matlab/matlab_external/using-net-from-matlab-an-overview.html
%
% see also: abbCom, abbMaster, abbNew, abbEdit
%
% use:
%  r = abbWrite(s,r);
% --------------------------------------------------------------------------
%  r     A struct as from abbNew,
%  s     A struct for communication, see abbCom
% --------------------------------------------------------------------------
% ex:   
%   p0 = struct('RapidName',char('T_ROB1','MatlabCom','pMatlabCom0'), 'RapidType','robtarget')
%   s = abbCom('Rudolf');
%   p0 = abbRead(s,p0);
%   p0 = abbEdit(p0);
%   s = abbMaster(s);
%   r = abbWrite(s,p0);

% Karl S : March 2014, January 2019

if nargin < 2
    disp([mfilename,': two input arguments are needed, see help. (',datestr(now),')']);
    return
end

if ~isstruct(s) || ~isfield(s,'ctrl') || ~isa(s.ctrl,'ABB.Robotics.Controllers.Controller')
    r.message = [mfilename,': no connection to ABB robot, check argument 1 (s.ctrl)'];   
    return
end

if (~isstruct(r) || ~isfield(r,'RapidName') || ~isfield(r,'RapidType'))
    r.message = [mfilename,': no RAPID data struct, check argument 2 (r) ',datestr(now)];
    return
end

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
    r.message = [mfilename,': no Master access (write access) to robot ',datestr(now)];
    return
end

% write
try
    ssVar = NET.createArray('System.String',3);
    ssVar.Set(0, strtrim(r.RapidName(1,:)));
    ssVar.Set(1, strtrim(r.RapidName(2,:)));
    ssVar.Set(2, strtrim(r.RapidName(3,:)));
    rVar = s.ctrl.Rapid.GetRapidData( ssVar );
    if ~strcmp(char(rVar.RapidType),r.RapidType)
        r.message = [' r.RapidType not as expected (',r.RapidType, ...
                     '), RAPID says it is ',char(rVar.RapidType)];
    else
        if strcmpi(char(rVar.RapidType),'num')
            rVar.Value.Value = r.Value;
            r.StringValue = char(rVar.StringValue);   
            disp([' num: ',r.StringValue]);
            r.message = [mfilename,': num written to RAPID (robot) ',datestr(now)];
        elseif strcmpi(char(rVar.RapidType),'bool')
            rVar.Value.Value = logical(r.Value);
            r.StringValue = char(rVar.StringValue);   
            disp([' bool: ',r.StringValue]);
            r.message = [mfilename,': bool written to RAPID (robot) ',datestr(now)];
        elseif strcmpi(char(rVar.RapidType),'robtarget')
            rVar.Value.Trans.X = r.Trans.X;
            rVar.Value.Trans.Y = r.Trans.Y;
            rVar.Value.Trans.Z = r.Trans.Z;
            rVar.Value.Rot.Q1 = r.Rot.Q1;
            rVar.Value.Rot.Q2 = r.Rot.Q2;
            rVar.Value.Rot.Q3 = r.Rot.Q3;
            rVar.Value.Rot.Q4 = r.Rot.Q4;
            rVar.Value.Robconf.Cf1 = r.Robconf.Cf1;
            rVar.Value.Robconf.Cf4 = r.Robconf.Cf4;
            rVar.Value.Robconf.Cf6 = r.Robconf.Cf6;
            rVar.Value.Robconf.Cfx = r.Robconf.Cfx;
            rVar.Value.Extax.Eax_a = r.Extax.Eax_a;
            rVar.Value.Extax.Eax_b = r.Extax.Eax_b;
            rVar.Value.Extax.Eax_c = r.Extax.Eax_c;
            rVar.Value.Extax.Eax_d = r.Extax.Eax_d;
            rVar.Value.Extax.Eax_e = r.Extax.Eax_e;
            rVar.Value.Extax.Eax_f = r.Extax.Eax_f;
            % 
            r.StringValue = char(rVar.StringValue);   
            disp([' robtarget: ',r.StringValue]);
            r.message = [mfilename,': robtarget written to RAPID (robot) ',datestr(now)];
        elseif strcmpi(char(rVar.RapidType),'tooldata')
            rVar.Value.Robhold = r.Robhold;
            rVar.Value.Tframe.Trans.X = r.Tframe.Trans.X;
            rVar.Value.Tframe.Trans.Y = r.Tframe.Trans.Y;
            rVar.Value.Tframe.Trans.Z = r.Tframe.Trans.Z;
            rVar.Value.Tframe.Rot.Q1 = r.Tframe.Rot.Q1;
            rVar.Value.Tframe.Rot.Q2 = r.Tframe.Rot.Q2;
            rVar.Value.Tframe.Rot.Q3 = r.Tframe.Rot.Q3;
            rVar.Value.Tframe.Rot.Q4 = r.Tframe.Rot.Q4;
            rVar.Value.Tload.Mass = r.Tload.Mass;
            rVar.Value.Tload.Ix = r.Tload.Ix;
            rVar.Value.Tload.Iy = r.Tload.Iy;
            rVar.Value.Tload.Iz = r.Tload.Iz;
            rVar.Value.Tload.Cog.X = r.Tload.Cog.X;
            rVar.Value.Tload.Cog.Y = r.Tload.Cog.Y;
            rVar.Value.Tload.Cog.Z = r.Tload.Cog.Z;
            rVar.Value.Tload.Aom.Q1 = r.Tload.Aom.Q1;
            rVar.Value.Tload.Aom.Q2 = r.Tload.Aom.Q2;
            rVar.Value.Tload.Aom.Q3 = r.Tload.Aom.Q3;
            rVar.Value.Tload.Aom.Q4 = r.Tload.Aom.Q4;
            % 
            r.StringValue = char(rVar.StringValue);   
            disp([' tooldata: ',r.StringValue]);
            r.message = [mfilename,': tooldata written to RAPID (robot) ',datestr(now)];
        elseif strcmpi(char(rVar.RapidType),'wobjdata')
            rVar.Value.Robhold = r.Robhold;
            rVar.Value.Ufprog = r.Ufprog;
            rVar.Value.Ufmec = System.String( strtrim(r.Ufmec) );
            rVar.Value.Uframe.Trans.X = r.Uframe.Trans.X;
            rVar.Value.Uframe.Trans.Y = r.Uframe.Trans.Y;
            rVar.Value.Uframe.Trans.Z = r.Uframe.Trans.Z;
            rVar.Value.Uframe.Rot.Q1 = r.Uframe.Rot.Q1;
            rVar.Value.Uframe.Rot.Q2 = r.Uframe.Rot.Q2;
            rVar.Value.Uframe.Rot.Q3 = r.Uframe.Rot.Q3;
            rVar.Value.Uframe.Rot.Q4 = r.Uframe.Rot.Q4;
            rVar.Value.Oframe.Trans.X = r.Oframe.Trans.X;
            rVar.Value.Oframe.Trans.Y = r.Oframe.Trans.Y;
            rVar.Value.Oframe.Trans.Z = r.Oframe.Trans.Z;
            rVar.Value.Oframe.Rot.Q1 = r.Oframe.Rot.Q1;
            rVar.Value.Oframe.Rot.Q2 = r.Oframe.Rot.Q2;
            rVar.Value.Oframe.Rot.Q3 = r.Oframe.Rot.Q3;
            rVar.Value.Oframe.Rot.Q4 = r.Oframe.Rot.Q4;
            %
            r.StringValue = char(rVar.StringValue);   
            disp([' wobjdata: ',r.StringValue]);
            r.message = [mfilename,': wobjdata written to RAPID (robot) ',datestr(now)];
        elseif strcmpi(char(rVar.RapidType),'jointtarget')
            rVar.Value.RobAx.Rax_1 = r.RobAx.Rax_1;
            rVar.Value.RobAx.Rax_2 = r.RobAx.Rax_2;
            rVar.Value.RobAx.Rax_3 = r.RobAx.Rax_3;
            rVar.Value.RobAx.Rax_4 = r.RobAx.Rax_4;
            rVar.Value.RobAx.Rax_5 = r.RobAx.Rax_5;
            rVar.Value.RobAx.Rax_6 = r.RobAx.Rax_6;
            rVar.Value.ExtAx.Eax_a = r.ExtAx.Eax_a;
            rVar.Value.ExtAx.Eax_b = r.ExtAx.Eax_b;
            rVar.Value.ExtAx.Eax_c = r.ExtAx.Eax_c;
            rVar.Value.ExtAx.Eax_d = r.ExtAx.Eax_d;
            rVar.Value.ExtAx.Eax_e = r.ExtAx.Eax_e;
            rVar.Value.ExtAx.Eax_f = r.ExtAx.Eax_f;
            % 
            r.StringValue = char(rVar.StringValue);   
            disp([' robtarget: ',r.StringValue]);
            r.message = [mfilename,': robtarget written to RAPID (robot) ',datestr(now)];
        else
            r.message = [mfilename,': not ready for this RapidType ',datestr(now)];
        end
    end
catch me
    r.message = [mfilename,': error while writing, see field ''me''. ',datestr(now)];
    r.me = me;
end

end