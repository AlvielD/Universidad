%% Comparing SIFT and SURF feature detectors/descriptors
% It is always interesting to test how two different algorithms perform in a 
% set of images, let's do some experiments on a set of pictures comparing SIFT 
% against SURF performance.

% Define some variables
im_obj_filepaths = ["./images/test/book_obj.jpg", ...
    "./images/test/cameraman_obj.jpg", "./images/test/fattyCat_obj.png", ...
    "./images/test/sormarka_obj.png"];

im_filepaths = ["./images/test/book.jpg", ...
    "./images/test/cameraman.jpg", "./images/test/fattyCat.jpg", ...
    "./images/test/sormarka.png"];

Descriptor = {'SIFT', 'SURF'};
Metrics = {'Detection Time', 'Extraction Time', 'Matching Time', 'Nº Feature Points', ...
    'Nº Matched Features'};

num_ims = 4;
num_metrics = length(Metrics);

SIFT_times = zeros(num_ims, num_metrics);
SURF_times = zeros(num_ims, num_metrics);

use_noise = false;
variance = 0.05;
noise = 'salt & pepper';

display_results = false;
save_data = false;
%% SIFT MEASUREMENT

for i=1:num_ims

    im_obj = imread(im_obj_filepaths(i));   % Read object image
    im_obj_gray = rgb2gray(im_obj);         % Turn image into gray scale

    im = imread(im_filepaths(i));           % Read image for object recognition
    im_gray = rgb2gray(im);                 % Turn image into gray scale

     % Apply noise
    if use_noise
        im_obj_gray = imnoise(im_obj_gray, noise, variance);
        im_gray = imnoise(im_gray, noise, variance);
    end

    % KEYPOINT DETECTION
    % Time testing
    f = @() detectSIFTFeatures(im_obj_gray);    % Handle function
    SIFT_det = timeit(f);                       % Test time of the function

    % Detect keypoints
    sift_points_obj_det = detectSIFTFeatures(im_obj_gray);  % Detect keypoints of the object
    sift_points = detectSIFTFeatures(im_gray);              % Detect keypoints of the background image


    % FEATURE EXTRACTION
    % Time testing
    f = @() extractFeatures(im_obj_gray, sift_points_obj_det);  % Handle to function
    SIFT_ext = timeit(f);                               % Test time of the function

    % Extract features
    [des_obj, sift_points_obj] = extractFeatures(im_obj_gray, sift_points_obj_det); % Extract feature descriptors of the object
    [des, sift_points] = extractFeatures(im_gray, sift_points);                 % Extract feature descriptors of the background image
    
    % Plot results
    if display_results
        figure;
        subplot(121)
        imshow(im_obj);
        title("Original Image");
        subplot(122)
        imshow(im_obj_gray), hold on;
        plot(sift_points_obj.selectStrongest(50), 'ShowOrientation', true);
        title("Featured Image");
        filename = "./images/Image" + i + "_det_SIFTfp.jpg";
        if save_data
            saveas(gcf, filename);
        end
    end


    % FEATURE MATCHING
    % Time testing
    f = @() matchFeatures(des_obj, des);    % Handle to function
    SIFT_match = timeit(f);                 % Test the time of the function

    % Match features
    [indexPairs, matchmetric] = matchFeatures(des_obj, des);
    matchedPoints_obj = sift_points_obj(indexPairs(:,1),:);
    matchedPoints = sift_points(indexPairs(:,2),:);
    
    % Plot results
    if display_results
        figure;
        showMatchedFeatures(im_obj_gray, im_gray, matchedPoints_obj, matchedPoints);
        filename = "./images/Image" + i + "_SIFTmatches.jpg";
        if save_data
            saveas(gcf, filename);
        end
    end


    % SAVE RESULTS
    SIFT_times(i, :) = [SIFT_det, SIFT_ext, SIFT_match, sift_points_obj_det.Count, ...
        length(indexPairs)];

end
%% SURF MEASUREMENT

for i=1:num_ims

    im_obj = imread(im_obj_filepaths(i));   % Read object image
    im_obj_gray = rgb2gray(im_obj);         % Turn image into gray scale

    im = imread(im_filepaths(i));           % Read image for object recognition
    im_gray = rgb2gray(im);                 % Turn image into gray scale

    % Apply noise
    if use_noise
        im_obj_gray = imnoise(im_obj_gray, noise, variance);
        im_gray = imnoise(im_gray, noise, variance);
    end

    % KEYPOINT DETECTION
    % Time testing
    f = @() detectSURFFeatures(im_obj_gray);    % Handle function
    SURF_det = timeit(f);                    % Test time of the function

    surf_points_obj_det = detectSURFFeatures(im_obj_gray);  % Detect keypoints of the object
    surf_points = detectSURFFeatures(im_gray);              % Detect keypoints of the background image
    
    
    % FEATURE EXTRACTION
    % Time testing
    f = @() extractFeatures(im_obj_gray, surf_points_obj_det); % Handle to function
    SURF_ext = timeit(f);                           % Test the time of the function

    % Extract features
    [des_obj, surf_points_obj] = extractFeatures(im_obj_gray, surf_points_obj_det); % Extract feature descriptors of the object
    [des, surf_points] = extractFeatures(im_gray, surf_points);              % Extract feature descriptors of the background image
    
    % Plot results
    if display_results
        figure;
        subplot(121)
        imshow(im_obj);
        title("Original Image");
        subplot(122)
        imshow(im_obj_gray), hold on;
        plot(surf_points_obj.selectStrongest(50), 'ShowOrientation', true);
        title("Featured Image");
        filename = "./images/Image" + i + "_det_SURFfp.jpg";
        if save_data
            saveas(gcf, filename);
        end
    end


    % FEATURE MATCHING
    f = @() matchFeatures(des_obj, des); % Handle to function
    SURF_match = timeit(f);              % Test the time of the function

    % Match features
    [indexPairs, matchmetric] = matchFeatures(des_obj, des);
    matchedPoints_obj = surf_points_obj(indexPairs(:,1),:);
    matchedPoints = surf_points(indexPairs(:,2),:);

    % Plot results
    if display_results
        figure;
        showMatchedFeatures(im_obj, im, matchedPoints_obj, matchedPoints);
        filename = "./images/Image" + i + "_SURFmatches.jpg";
        if save_data
            saveas(gcf, filename);
        end
    end

    SURF_times(i, :) = [SURF_det, SURF_ext, SURF_match, surf_points_obj_det.Count, ...
        length(indexPairs)];

end
%% Plot and save results

% Create table to compare results
for i=1:num_ims
    Image_measures = [SIFT_times(i, :); SURF_times(i, :)];

    Table = array2table(Image_measures, "VariableNames", Metrics, "RowNames", Descriptor);
    
    if save_data
        filename = "./data/results_image" + i + ".csv";
        writetable(Table, filename);
    end

    tableName = "Image " + i;
    Table = table(Table, 'VariableNames', tableName);

    disp(Table)
end