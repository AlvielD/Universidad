function r = abbEdit(r,newval)
% abbEdit          Edit ABB RAPID variable in struct, no communication to robot.
% 
% This function is part of a set of functions intendend to make the use of
% PCSDK from Matlab a little bit easier. For more on PCSDK see:
% http://developercenter.robotstudio.com/pcsdk 
% and information on how Matlab support .NET applications see:
% https://se.mathworks.com/help/matlab/matlab_external/using-net-from-matlab-an-overview.html
%
% see also: abbNew
%               
% Use:
%  r = abbEdit(r);
%  r = abbEdit(r,newval);
% --------------------------------------------------------------------------
%  r        a struct representing a RAPID data variable, as from abbNew()
%  newval   new value, may also use: r.Value = newval;
% --------------------------------------------------------------------------
% ex:   
%   p = abbNew(char('T_ROB1','MatlabCom','pMatlabComOrigo'), 'robtarget');
%   p = abbEdit(p);
%   tool = abbNew(char('T_ROB1','MatlabCom','tMatlabCom'), 'tooldata');
%   tool = abbEdit(tool);
%   wobj = abbNew(char('T_ROB1','MatlabCom','wobjMatlabCom'), 'wobjdata');
%   wobj = abbEdit(wobj);
%   n1 = abbNew(char('T_ROB1','MatlabCom','nMatlabComNum1'), 'num');
%   n1 = abbEdit(n1);

% Karl S : March 2014, January 2019

if nargin < 1
    r = struct('RapidNavn', char('Program','Module','VarName'), ...
               'RapidType', '', ...
               'message', [mfilename,': invalid input ',datestr(now)], ...
               'me', '', ...    
               'StringValue', '' );   
    return;
end

try
    if strcmpi(r.RapidType,'num')
        if nargin >= 2
            r.Value = double(newval(1));
        else
            r.Value = input(' Input new value for num : ');
        end
        r.message = [mfilename,': num struct edited ',datestr(now)];
    elseif strcmpi(r.RapidType,'bool')
        if nargin >= 2
            r.Value = logical(newval(1));
        else
            r.Value = logical(input(' Input new value for bool (0/1) : '));
        end
        r.message = [mfilename,': bool struct edited ',datestr(now)];
    elseif strcmpi(r.RapidType,'robtarget')
        disp(' RobTarget has fields: Trans, Rot, Robconf and Extax. ');
        reply = ' ';
        while ~strcmpi(reply,'Q')
            disp( [' ',abbString(r)] );
            reply = input(' Input field:  T(rans), R(ot), (rob)C(onf), E(xtax) or Q(uit) : ','s');
            reply = strtrim(reply);
            if isempty(reply); reply = ' '; end
            reply = reply(1);
            if strcmpi(reply,'T'); r.Trans = internEditPos(r.Trans); end;
            if strcmpi(reply,'R'); r.Rot = internEditRot(r.Rot); end;
            if strcmpi(reply,'C'); r.Robconf = internEditRobconf(r.Robconf); end;
            if strcmpi(reply,'E'); r.Extax = internEditExtax(r.Extax); end;
        end
        r.message = [mfilename,': robtarget struct edited ',datestr(now)];
    elseif strcmpi(r.RapidType,'tooldata')
        disp(' ToolData has fields: Robhold, Tframe og Tload. ');
        reply = ' ';
        while ~strcmpi(reply,'Q')
            disp( [' ',abbString(r)] );
            reply = input(' Input field:  R(obhold), (T)f(rame), (T)l(oad) or Q(uit) : ','s');
            reply = strtrim(reply);
            if isempty(reply); reply = ' '; end
            reply = reply(1);
            if strcmpi(reply,'R'); r.Robhold = logical(input('  Input new value for Robhold (0/1) : ')); end;
            if strcmpi(reply,'f'); r.Tframe = internEditFrame(r.Tframe); end;
            if strcmpi(reply,'l'); r.Tload = internEditLoad(r.Tload); end;
        end
        r.message = [mfilename,': tooldata struct edited ',datestr(now)];
    elseif strcmpi(r.RapidType,'wobjdata')
        disp(' WobjData field: Robhold, Ufprog, Ufmec, Uframe og Oframe. ');
        reply = ' ';
        while ~strcmpi(reply,'Q')
            disp( [' ',abbString(r)] );
            reply = input(' Input field:  R(obhold), (Uf)p(rog), (Uf)m(ec), U(frame), O(frame) or Q(uit) : ','s');
            reply = strtrim(reply);
            if isempty(reply); reply = ' '; end
            reply = reply(1);
            if strcmpi(reply,'R'); r.Robhold = logical(input('  Input new value for Robhold (0/1) : ')); end;
            if strcmpi(reply,'p'); r.Ufprog = logical(input('  Input new value for Ufprog (0/1) : ')); end;
            if strcmpi(reply,'m'); r.Ufmec = input('  Input new string for Ufmec : ','s'); end;
            if strcmpi(reply,'U'); r.Uframe = internEditFrame(r.Uframe); end;
            if strcmpi(reply,'O'); r.Oframe = internEditFrame(r.Oframe); end;
        end
        r.message = [mfilename,': wobjdata struct edited ',datestr(now)];
    elseif strcmpi(r.RapidType,'jointtarget')
        disp(' JointTarget fields: RobAx and ExtAx. ');
        reply = ' ';
        while ~strcmpi(reply,'Q')
            disp( [' ',abbString(r)] );
            reply = input(' Input field:  R(obAx), E(xtAx) or Q(uit) : ','s');
            reply = strtrim(reply);
            if isempty(reply); reply = ' '; end
            reply = reply(1);
            if strcmpi(reply,'R'); r.RobAx = internEditRobax(r.RobAx); end;
            if strcmpi(reply,'E'); r.ExtAx = internEditExtax(r.ExtAx); end;
        end
        r.message = [mfilename,': jointtarget struct edited ',datestr(now)];
    else
        r.message = [mfilename,': ikke klar for denne RapidType ennå.'];
    end
    r.StringValue = abbString(r);
catch me
    disp('***   SOME ERROR    ***');
    r.message = [mfilename,': Error while editing, look field ''me''.',datestr(now)];
    r.me = me;    
end

end

%% subfunctions

% edit a frame
function s = internEditFrame(s)
    disp('  The frame has fields: Trans og Rot.')
    reply = ' ';
    while ~strcmpi(reply,'Q')
        reply = input('  Input field:  T(rans), R(ot) or Q(uit) : ','s');
        reply = strtrim(reply);
        if isempty(reply); reply = ' '; end
        reply = reply(1);
        if strcmpi(reply,'T'); s.Trans = internEditPos(s.Trans); end;
        if strcmpi(reply,'R'); s.Rot = internEditRot(s.Rot); end;
    end
end

% editr a load
function s = internEditLoad(s)
    disp('  The load has fields Mass, Ix, Iy, Iz, Cog (posision) og Aom (rotation).')
    reply = ' ';
    while ~strcmpi(reply,'Q')
        reply = input('  Input field:  M(ass), C(og), A(om), x, y, z or Q(uit) : ','s');
        reply = strtrim(reply);
        if isempty(reply); reply = ' '; end
        reply = reply(1);
        if strcmpi(reply,'M'); s.Mass = input('   Input value for Mass : '); end;
        if strcmpi(reply,'x'); s.Ix = input('   Input value for Ix : '); end;
        if strcmpi(reply,'y'); s.Iy = input('   Input value for Iy : '); end;
        if strcmpi(reply,'z'); s.Iz = input('   Input value for Iz : '); end;
        if strcmpi(reply,'C'); s.Cog = internEditPos(s.Cog); end;
        if strcmpi(reply,'A'); s.Aom = internEditRot(s.Aom); end;
    end
end

% edit posision
function s = internEditPos(s)
    reply = ' ';
    while ~strcmpi(reply,'Q')
        disp(['   Posision is ',...
              '(x,y,z) = (',num2str(s.X),',',num2str(s.Y),',',num2str(s.Z),')']);
        reply = input('   Input value, x/y/z/q(uit) : ','s');
        reply = strtrim(reply);
        if isempty(reply); reply = ' '; end
        reply = reply(1);
        if strcmpi(reply,'x'); s.X = input('   Input posision X : '); end;
        if strcmpi(reply,'y'); s.Y = input('   Input posision Y : '); end;
        if strcmpi(reply,'z'); s.Z = input('   Input posision Z : '); end;
    end
    disp(['   Posision is ',...
          '(x,y,z) = (',num2str(s.X),',',num2str(s.Y),',',num2str(s.Z),')']);
end


% edit rotation
function s = internEditRot(s)
    th = acos(s.Q1)*180/pi;
    if s.Q1 <= -1
        disp('   Q1 <= -1  strange --> Q1 assigned 1.');
        s.Q1 = 1;
        th = 0;
    end
    scale = max([abs(s.Q2),abs(s.Q3),abs(s.Q4)]); 
    if (scale > 0)  % s.Q1 < 1
        x = s.Q2/scale;  y = s.Q3/scale;  z = s.Q4/scale;
    else
        x = 1;  y = 0;  z = 0;
    end
    reply = ' ';
    while ~strcmpi(reply,'q')
        disp(['   Rotation is given as a rotation of ',num2str(2*th),' degrees around ',...
              'axis (x,y,z) = (',num2str(x),',',num2str(y),',',num2str(z),')']);
        reply = input('   Input new axis (using x,y or z) or angle, x/y/z/a/q(uit) : ','s');
        reply = strtrim(reply);
        if isempty(reply); reply = ' '; end
        reply = reply(1);
        if strcmpi(reply,'x'); x = input('   Input X for axis : '); end;
        if strcmpi(reply,'y'); y = input('   Input Y for axis : '); end;
        if strcmpi(reply,'z'); z = input('   Input Z for axis : '); end;
        if strcmpi(reply,'a'); th = 0.5*input('   Input angle in degrees (-180,180] : '); end;
    end
    scale = max([abs(x),abs(y),abs(z)]); 
    if (scale > 0)  
        x = x/scale;  y = y/scale;  z = z/scale;
    end
    disp(['   Rotation is given as a rotation of ',num2str(2*th),' degrees around ',...
          'axis (x,y,z) = (',num2str(x),',',num2str(y),',',num2str(z),')']);
    s.Q1 = cos(th*pi/180);
    if (abs(s.Q1) < 1)
        if (x*x+y*y+z*z) <= 0
            x = 1; y = 0; z = 0;
        end
        scale = sign(sin(th*pi/180))*sqrt((1-s.Q1*s.Q1)/(x*x+y*y+z*z));
        s.Q2 = scale*x;
        s.Q3 = scale*y;
        s.Q4 = scale*z;
    else
        s.Q1 = 1;
        s.Q2 = 0;
        s.Q3 = 0;
        s.Q4 = 0;
    end
    disp(['   This rotation is represented as Q1=',num2str(s.Q1,'%11.8f'),', Q2=',num2str(s.Q2,'%11.8f'),...
          ', Q3=',num2str(s.Q3,'%11.8f'),', Q4=',num2str(s.Q4,'%11.8f'),'.']);        
end

function s = internEditRobconf(s)
    reply = ' ';
    while ~strcmpi(reply,'Q')
        disp(['   Robot configuration ',...
              'Cf1=',num2str(s.Cf1),', Cf4=',num2str(s.Cf4), ...
              ', Cf6=',num2str(s.Cf6),', Cfx=',num2str(s.Cfx)]);
        reply = input('   Input value, 1/4/6/x/q(uit) : ','s');
        reply = strtrim(reply);
        if isempty(reply); reply = ' '; end
        reply = reply(1);
        if strcmpi(reply,'1'); s.Cf1 = input('   Input value for Cf1 : '); end;
        if strcmpi(reply,'4'); s.Cf4 = input('   Input value for Cf4 : '); end;
        if strcmpi(reply,'6'); s.Cf6 = input('   Input value for Cf6 : '); end;
        if strcmpi(reply,'x'); s.Cfx = input('   Input value for Cfx : '); end;
    end
    disp(['   Robot configuration ',...
          'Cf1=',num2str(s.Cf1),', Cf4=',num2str(s.Cf4), ...
          ', Cf6=',num2str(s.Cf6),', Cfx=',num2str(s.Cfx)]);
end

function s = internEditExtax(s)
    reply = ' ';
    while ~strcmpi(reply,'Q')
        disp(['   External axes are ',...
              'Eax_a=',number2str(s.Eax_a),', Eax_b=',number2str(s.Eax_b), ...
              ', Eax_c=',number2str(s.Eax_c),', Eax_d=',number2str(s.Eax_d), ...
              ', Eax_e=',number2str(s.Eax_e),', Eax_f=',number2str(s.Eax_f)]);
        reply = input('   Input value, a/b/c/d/e/f/q(uit) : ','s');
        reply = strtrim(reply);
        if isempty(reply); reply = ' '; end
        reply = reply(1);
        if strcmpi(reply,'a'); s.Eax_a = input('   Input value for Eax_a : '); end;
        if strcmpi(reply,'b'); s.Eax_b = input('   Input value for Eax_b : '); end;
        if strcmpi(reply,'c'); s.Eax_c = input('   Input value for Eax_c : '); end;
        if strcmpi(reply,'d'); s.Eax_d = input('   Input value for Eax_d : '); end;
        if strcmpi(reply,'e'); s.Eax_e = input('   Input value for Eax_e : '); end;
        if strcmpi(reply,'f'); s.Eax_f = input('   Input value for Eax_f : '); end;
    end
    disp(['   External axes are ',...
          'Eax_a=',number2str(s.Eax_a),', Eax_b=',number2str(s.Eax_b), ...
          ', Eax_c=',number2str(s.Eax_c),', Eax_d=',number2str(s.Eax_d), ...
          ', Eax_e=',number2str(s.Eax_e),', Eax_f=',number2str(s.Eax_f)]);
end

function s = internEditRobax(s)
    reply = ' ';
    while ~strcmpi(reply,'Q')
        disp(['   Robot axes ',...
              'Rax_1=',number2str(s.Rax_1),', Rax_2=',number2str(s.Rax_2), ...
              ', Rax_3=',number2str(s.Rax_3),', Rax_4=',number2str(s.Rax_4), ...
              ', Rax_5=',number2str(s.Rax_5),', Rax_6=',number2str(s.Rax_6)]);
        reply = input('   Input value, 1/2/3/4/5/6/q(uit) : ','s');
        reply = strtrim(reply);
        if isempty(reply); reply = ' '; end
        reply = reply(1);
        if strcmpi(reply,'1'); s.Rax_1 = input('   Input value for Rax_1 : '); end;
        if strcmpi(reply,'2'); s.Rax_2 = input('   Input value for Rax_2 : '); end;
        if strcmpi(reply,'3'); s.Rax_3 = input('   Input value for Rax_3 : '); end;
        if strcmpi(reply,'4'); s.Rax_4 = input('   Input value for Rax_4 : '); end;
        if strcmpi(reply,'5'); s.Rax_5 = input('   Input value for Rax_5 : '); end;
        if strcmpi(reply,'6'); s.Rax_6 = input('   Input value for Rax_6 : '); end;
    end
    disp(['   Robot axes ',...
          'Rax_1=',number2str(s.Rax_1),', Rax_2=',number2str(s.Rax_2), ...
          ', Rax_3=',number2str(s.Rax_3),', Rax_4=',number2str(s.Rax_4), ...
          ', Rax_5=',number2str(s.Rax_5),', Rax_6=',number2str(s.Rax_6)]);
end

function s = number2str(x)
    if (x >= 9e9)
        s = '9E9';
    else
        s = num2str(x);
    end
end