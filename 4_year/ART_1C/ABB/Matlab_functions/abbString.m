function s = abbString(r)
% abbString        Make a string of ABB RAPID struct (like .StringValue)
%               
% This function is part of a set of functions intendend to make the use of
% PCSDK from Matlab a little bit easier. For more on PCSDK see:
% http://developercenter.robotstudio.com/pcsdk 
% and information on how Matlab support .NET applications see:
% https://se.mathworks.com/help/matlab/matlab_external/using-net-from-matlab-an-overview.html
%
% see also: abbNew, abbEdit, abbRead, abbWrite, abbText
%
% use:
%  s = abbString(r);
% --------------------------------------------------------------------------
%  s     A string
%  r     A struct of fields as RAPID data variable
% --------------------------------------------------------------------------
% ex:   
%   p = abbNew(char('T_ROB1','MatlabCom','pMatlabComOrigo'), 'robtarget');
%   s = abbString(p);
%   tool = abbNew(char('T_ROB1','MatlabCom','tMatlabCom'), 'tooldata');
%   s = abbString(tool);
%   wobj = abbNew(char('T_ROB1','MatlabCom','wobjMatlabCom'), 'wobjdata');
%   s = abbString(wobj);

% Karl S : March 2014, January 2019

if nargin < 1
    s = [mfilename,': one input argument is needed, ',datestr(now)];
    disp(s);
    return
end

% RAPID data struct r
if (~isstruct(r) || ~isfield(r,'RapidName') || ~isfield(r,'RapidType'))
    s = [mfilename,': argument should be a valid RAPID data struct (abbNew) ',datestr(now)];
    disp(s);
    return
end

% make string
try
    if strcmpi(r.RapidType,'num')
        s = number2str(r.Value);
    elseif strcmpi(r.RapidType,'bool')
        s = logical2str(r.Value);
    elseif strcmpi(r.RapidType,'robtarget') 
        s = ['[',abbStringpos(r.Trans),',',...
                 abbStringrot(r.Rot),',',...
                 abbStringrobconf(r.Robconf),',',...
                 abbStringextax(r.Extax),']'];
    elseif strcmpi(r.RapidType,'tooldata')
        s = ['[',logical2str(r.Robhold),',',...
                 abbStringframe(r.Tframe),',',...
                 abbStringload(r.Tload),']'];
    elseif strcmpi(r.RapidType,'wobjdata')
        s = ['[',logical2str(r.Robhold),',',logical2str(r.Ufprog),',"',r.Ufmec,'",',...
                 abbStringframe(r.Uframe),',',...
                 abbStringframe(r.Oframe),']'];
    elseif strcmpi(r.RapidType,'jointtarget') 
        s = ['[',abbStringrobax(r.RobAx),',',...
                 abbStringextax(r.ExtAx),']'];
    else
        s = [mfilename,': not ready for this RapidType  ',datestr(now)];
    end
catch me
    s = [mfilename,': error converting struct values to string ',datestr(now)];
    disp(me.message);
end

end

%% subfunctions
function s = abbStringload(r)
    s = ['[',number2str(r.Mass),...
         ',',abbStringpos(r.Cog),',',abbStringrot(r.Aom),...
         ',',number2str(r.Ix),',',number2str(r.Iy),',',number2str(r.Iz),']'];
end

function s = abbStringframe(r)
    s = ['[',abbStringpos(r.Trans),',',abbStringrot(r.Rot),']'];
end

function s = abbStringpos(r)
    s = ['[',number2str(r.X),',',number2str(r.Y),',',number2str(r.Z),']'];
end

function s = abbStringrot(r)
    s = ['[',number2str(r.Q1),',',number2str(r.Q2),',',number2str(r.Q3),',',number2str(r.Q4),']'];
end

function s = abbStringrobconf(r)
    s = ['[',number2str(r.Cf1),',',number2str(r.Cf4),',',number2str(r.Cf6),',',number2str(r.Cfx),']'];
end

function s = abbStringextax(r)
    s = ['[',number2str(r.Eax_a),',',number2str(r.Eax_b),',',number2str(r.Eax_c),...
         ',',number2str(r.Eax_d),',',number2str(r.Eax_e),',',number2str(r.Eax_f),']'];
end

function s = abbStringrobax(r)
    s = ['[',number2str(r.Rax_1),',',number2str(r.Rax_2),',',number2str(r.Rax_3),...
         ',',number2str(r.Rax_4),',',number2str(r.Rax_5),',',number2str(r.Rax_6),']'];
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
