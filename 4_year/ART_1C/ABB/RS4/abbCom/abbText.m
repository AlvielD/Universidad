function t = abbText(r)
% abbText          Make a text from ABB RAPID struct 
%               
% This function is part of a set of functions intendend to make the use of
% PCSDK from Matlab a little bit easier. For more on PCSDK see:
% http://developercenter.robotstudio.com/pcsdk 
% and information on how Matlab support .NET applications see:
% https://se.mathworks.com/help/matlab/matlab_external/using-net-from-matlab-an-overview.html
%
% see also: abbString
%
% use:
%  t = abbText(r);
% --------------------------------------------------------------------------
%  t     A char array
%  r     A struct of fields as RAPID data variable
% --------------------------------------------------------------------------
% ex:   
%   p = abbNew(char('T_ROB1','MatlabCom','pMatlabComOrigo'), 'robtarget');
%   t = abbText(p);
%   tool = abbNew(char('T_ROB1','MatlabCom','tMatlabCom'), 'tooldata');
%   t = abbText(tool);
%   wobj = abbNew(char('T_ROB1','MatlabCom','wobjMatlabCom'), 'wobjdata');
%   t = abbText(wobj);

% Karl S : March 2014, January 2019

if nargin < 1
    t = [mfilename,': one input argument is needed, ',datestr(now)];
    disp(t);
    return
end

if (~isstruct(r) || ~isfield(r,'RapidName') || ~isfield(r,'RapidType'))
    t = [mfilename,': argument should be a valid RAPID data struct (abbNew) ',datestr(now)];
    disp(t);
    return
end

sep_ch = ' - ';
try
    if strcmpi(r.RapidType,'num')
        t = char(['ABB RAPID struct from ',strtrim(r.RapidName(1,:)),...
                  sep_ch,strtrim(r.RapidName(2,:)),sep_ch,strtrim(r.RapidName(3,:)),...
                  ',   num: '], ...
                 ['  Value       : ',number2str(r.Value)],... 
                 ['  StringValue : ',r.StringValue] );
    elseif strcmpi(r.RapidType,'bool')
        t = char(['ABB RAPID struct from ',strtrim(r.RapidName(1,:)),...
                  sep_ch,strtrim(r.RapidName(2,:)),sep_ch,strtrim(r.RapidName(3,:)),...
                  ',   bool: '], ...
                 ['  Value       : ',logical2str(r.Value)],... 
                 ['  StringValue : ',r.StringValue] );
    elseif strcmpi(r.RapidType,'robtarget')
        t = char(['ABB RAPID struct from ',strtrim(r.RapidName(1,:)),...
                  sep_ch,strtrim(r.RapidName(2,:)),sep_ch,strtrim(r.RapidName(3,:)),...
                  ',   RobTarget: '], ...
                 ['  Trans       : ',abbTextpos(r.Trans)],... 
                 ['  Rot         : ',abbTextrot(r.Rot)],... 
                 ['  Robconf     : ',abbTextrobconf(r.Robconf)],... 
                 ['  Extax       : ',abbTextextax(r.Extax)],...
                 ['  StringValue : ',r.StringValue] );
    elseif strcmpi(r.RapidType,'tooldata')
        tl = abbTextload(r.Tload);
        tf = abbTextframe(r.Tframe);
        t = char(['ABB RAPID struct from ',strtrim(r.RapidName(1,:)),...
                  sep_ch,strtrim(r.RapidName(2,:)),sep_ch,strtrim(r.RapidName(3,:)),...
                  ',   ToolData: '], ...
                 ['  Robhold     : ',logical2str(r.Robhold)],... 
                 ['  Tframe      : ',tf(1,:)],... 
                 ['                ',tf(2,:)],... 
                 ['  Tload       : ',tl(1,:)],... 
                 ['                ',tl(2,:)],... 
                 ['                ',tl(3,:)],... 
                 ['                ',tl(4,:)],... 
                 ['  StringValue : ',r.StringValue] );
    elseif strcmpi(r.RapidType,'wobjdata')
        uf = abbTextframe(r.Uframe);
        of = abbTextframe(r.Oframe);
        t = char(['ABB RAPID struct from ',strtrim(r.RapidName(1,:)),...
                  sep_ch,strtrim(r.RapidName(2,:)),sep_ch,strtrim(r.RapidName(3,:)),...
                  ',   WobjData: '], ...
                 ['  Robhold     : ',logical2str(r.Robhold)],... 
                 ['  Ufprog      : ',logical2str(r.Ufprog)],... 
                 ['  Ufmec       : "',r.Ufmec,'"'],... 
                 ['  Uframe      : ',uf(1,:)],... 
                 ['                ',uf(2,:)],... 
                 ['  Oframe      : ',of(1,:)],... 
                 ['                ',of(2,:)],... 
                 ['  StringValue : ',r.StringValue] );
    elseif strcmpi(r.RapidType,'jointtarget')
        t = char(['ABB RAPID struct from ',strtrim(r.RapidName(1,:)),...
                  sep_ch,strtrim(r.RapidName(2,:)),sep_ch,strtrim(r.RapidName(3,:)),...
                  ',   JointTarget: '], ...
                 ['  RobAx       : ',abbTextrobax(r.RobAx)],... 
                 ['  ExtAx       : ',abbTextextax(r.ExtAx)],...
                 ['  StringValue : ',r.StringValue] );
    else
        t = [mfilename,': not ready for this RapidType  ',datestr(now)];
    end
catch me
    t = [mfilename,': error converting struct values to text ',datestr(now)];
    disp(me.message);
end

end

%% subfunctions
function t = abbTextload(r)
    t = char(['Mass  : ',number2str(r.Mass)],...
             ['Cog   : ',abbTextpos(r.Cog)],...
             ['Aom   : ',abbTextrot(r.Aom)],...
             ['I     : Ix=',number2str(r.Ix),', Iy=',number2str(r.Iy),', Iz=',number2str(r.Iz)]);
end

function t = abbTextframe(r)
    t = char(['Trans : ',abbTextpos(r.Trans)],...
             ['Rot   : ',abbTextrot(r.Rot)]);
end

function s = abbTextpos(r)
    s = ['[ X=',number2str(r.X),', Y=',number2str(r.Y),', Z=',number2str(r.Z),' ]'];
end

function s = abbTextrot(r)
    s = ['[ Q1=',number2str(r.Q1),', Q2=',number2str(r.Q2),...
         ', Q3=',number2str(r.Q3),', Q4=',number2str(r.Q4),' ]'];
end

function s = abbTextrobconf(r)
    s = ['[ Cf1=',number2str(r.Cf1),', Cf4=',number2str(r.Cf4),...
         ', Cf6=',number2str(r.Cf6),', Cfx=',number2str(r.Cfx),' ]'];
end

function s = abbTextextax(r)
    s = ['[ Eax_a=',number2str(r.Eax_a),', Eax_b=',number2str(r.Eax_b),', Eax_c=',number2str(r.Eax_c),...
         ', Eax_d=',number2str(r.Eax_d),', Eax_e=',number2str(r.Eax_e),', Eax_f=',number2str(r.Eax_f),' ]'];
end

function s = abbTextrobax(r)
    s = ['[ Rax_1=',number2str(r.Rax_1),', Rax_2=',number2str(r.Rax_2),', Rax_3=',number2str(r.Rax_3),...
         ', Rax_4=',number2str(r.Rax_4),', Rax_5=',number2str(r.Rax_5),', Rax_6=',number2str(r.Rax_6),' ]'];
end

function s = number2str(x)
    if (x >= 8e9)
        s = '9E9';
    elseif (abs(rem(x,1)) < 1e-3)
        s = int2str(round(x));
    elseif (abs(rem(1000*x,1)) < 1e-3)
        s = num2str(round(1000*x)/1000,'%.3f');
    elseif (abs(rem(1000000*x,1)) < 1e-3)
        s = num2str(round(1000000*x)/1000000,'%.6f');
    else
        s = num2str(x,'%.9f');
    end
end

function s = logical2str(x)
    if x
        s = 'TRUE';
    else
        s = 'FALSE';
    end
end
