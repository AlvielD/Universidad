function [x,y,z] = lab4_path(no)
% lab4_path     Returns x-, y- and z-coordinates for (robot) paths
%             
% use:   [x,y,z] = lab4_path(1);  % flower
%        [x,y,z] = lab4_path(2);  % rectangle 

% Karl S : Feb. 2014 --> Feb 2019

if (no == 1)
    % a flower
    N = 183;    % number of points
    x = zeros(N,1);
    y = zeros(N,1);
    z = zeros(N,1);  
    x(1) = 0; y(1) = 0; z(1) = 100;
    r0 = 120;   % main (average) radius [mm]
    r1 = 150;   % max radius
    degrees_per_periode = 36;
    for n = 2:(N-1)
        th = 2*(n-2)*(2*pi)/360;   
        r = r0 + (r1-r0)*sin(th*360/degrees_per_periode);
        x(n) = r*cos(th);
        y(n) = r*sin(th);
    end
    x(N) = 0; y(N) = 0; z(N) = 150;
elseif (no == 2)
    % another (more) simple path
    s = 100;
    x = [0,-s,-s,s,s,-s,0];
    y = [0,-s,s,s,-s,-s,0];
    z = [100,0,0,0,0,0,150];
else
    disp([mfilename,': use first argument as 1 or 2 now']);
end

% display path (from above)
figure(1); clf;
plot(x,y);
axis equal;

return