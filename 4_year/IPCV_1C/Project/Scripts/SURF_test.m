%% Testing SURF algorithm
%% Detection of feature points

% Read the image
img = imread("./images/test/cameraman_obj.jpg");
img = rgb2gray(img);

% Detect feature points
points = detectSURFFeatures(img);

% Extract the descriptors
[descriptors, points] = extractFeatures(img, points);

% Plot the results
figure('Position', [0, 0, 1800, 1800]);
subplot(121)
imshow(img)
title("Original image")
subplot(122)
imshow(img); hold on;
plot(points.selectStrongest(25), 'ShowOrientation', true);
title("Featured image")
saveas(gcf, "./images/SURF_features.jpg")
%% SURF descriptor definition

% Let's study how it is stored in memory the SURF descriptor
descriptors
%% 
% Notice for SURF we only use 64-dimensional vectors to describe a point, half 
% of the space we use for SIFT, this makes it faster and it's proved that it's 
% even more robust than SIFT.
% 
% The dimensionality of the vector comes from the same point of SIFT, we use 
% a neighbourhood region of 4x4 sub regions and for each subregion we get horizontal 
% and vertical Haar wavelet responses and their sum over over each subregion.

% Example of the first point descriptor vector
descriptors(1, :)
descriptor = descriptors(1, :);  % Take the first descriptor vector
subregions = 16;
x = categorical({'\Sigma{d_x}', '\Sigma{d_y}', '\Sigma{|d_x|}', '\Sigma{|d_y|}'});
figure('Position', [0, 0, 1800, 1800]);
for i=0:subregions-1
    y = descriptor((i*4)+1:(i*4)+4);
    subplot(4,4,i+1);
    bar(x, y);
end
saveas(gcf, "./images/SURF_descriptor.jpg");
%% SURF Feature matching

% Read the image of the object
im_obj = imread("./images/test/book_obj.jpg");
obj_gray = rgb2gray(im_obj);

% Detect feature points
fp_obj = detectSURFFeatures(obj_gray);

% Extract the descriptors
[des_obj, fp_obj] = extractFeatures(obj_gray, fp_obj);

% Plot the object image and the featured image
figure;
subplot(121)
imshow(im_obj)
title("Original Image")
subplot(122)
imshow(obj_gray);
hold on;
plot(fp_obj.selectStrongest(50), 'ShowOrientation', true);
hold off
title("Featured Image")
saveas(gcf, "./images/SURF_features_book.jpg")
%%
% Match the object features with an image where the object appears
im = imread("./images/test/book.jpg");
im_gray = rgb2gray(im);

% Detect feature points
fp = detectSURFFeatures(im_gray);

% Extract the descriptors
[des, fp] = extractFeatures(im_gray, fp);

[indexPairs,matchmetric] = matchFeatures(des_obj,des);

matchedPoints_obj = fp_obj(indexPairs(:,1),:);
matchedPoints = fp(indexPairs(:,2),:);

figure;
showMatchedFeatures(im_obj,im,matchedPoints_obj,matchedPoints);
saveas(gcf, "./images/matches_book.jpg")