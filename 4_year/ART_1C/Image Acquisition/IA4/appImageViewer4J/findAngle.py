import numpy as np
import cv2
import math
import pprint

def houghcircles(npImage):
    
    # TODO ask parameters in a dialog
    npImage = cv2.cvtColor(npImage, cv2.COLOR_BGR2GRAY)
    circles = cv2.HoughCircles(npImage, cv2.HOUGH_GRADIENT, dp=1.2, minDist=20,
        param1=60, param2=40, minRadius=430, maxRadius=434)

    return circles[0][0] if len(circles) == 1 else None

def distColorRGB(img):
    """Make binary image by testing if pixel color is close to a selected RGB color.
	Distance is measured for each pixel p with color (r,g,b) to selected color (R,G,B) as
	    d = max(abs(r-R), abs(g-G), abs(b-B)), 
	where d is pixel value for resulting gray scale image
    """
    rgb = [160, 35, 35]          # Define the color to be detected
    A = img.astype(np.float32)   # Convert image to float32

    if (len(A.shape) > 2) and (A.shape[2] >= 3):        # Check if image is color
        B = np.ones(shape=A.shape, dtype=np.float32)    # Create an image with the color to be detected

        B[:, :, 0] = rgb[0]                             # Set the red channel
        B[:, :, 1] = rgb[1]                             # Set the green channel
        B[:, :, 2] = rgb[2]                             # Set the blue channel
        
        B = cv2.cvtColor(B, cv2.COLOR_BGR2RGB)         # Convert to gray scale

        D = np.max(np.abs(A-B),axis=2).astype(np.uint8)  # The distance image

    cv2.namedWindow("output", cv2.WINDOW_NORMAL) 
    cv2.resizeWindow("output", 800, 480) 
    cv2.imshow('output', D)

    k = cv2.waitKey(0)
    if k == 27:         # wait for ESC key to exit
        cv2.destroyAllWindows()

    return D

def compute_angle(start_pt, end_pt, center_pt, im):

    # Compute the angle
    theta = math.degrees(math.atan2(end_pt[1] - center_pt[1], end_pt[0] - center_pt[0]) + 360) % 360

    # Draw the lines
    im = cv2.line(im, (int(center_pt[0]), int(center_pt[1])), (int(end_pt[0]), int(end_pt[1])), (255, 0, 0), 2)
    im = cv2.line(im, (int(center_pt[0]), int(center_pt[1])), (int(start_pt[0]), int(start_pt[1])), (255, 0, 0), 2)

    return theta, im

def centroid_interest_region(im):
    """Find the centroid of the interest region."""

    valid_x = []
    valid_y = []

    # Find the distance image for the interesting color
    binaryIm = distColorRGB(im)

    # Check which pixels are in the interesting region
    for y in range(im.shape[0]):
        for x in range(im.shape[1]):
            if binaryIm[y, x] < 10:
                valid_x.append(x)
                valid_y.append(y)

    # Calculate the centroid of the interesting region
    mx = np.mean(valid_x)
    my = np.mean(valid_y)

    return mx, my

def findAngle(start_im: str, end_im: str):
    
    # Find the starting point
    start_pt = centroid_interest_region(start_im)

    # Find the ending point
    end_pt = centroid_interest_region(end_im)

    # Find the center of the circle
    circle = houghcircles(end_im)

    # Find the angle
    if circle is not None:

        theta, angle_im = compute_angle(start_pt, end_pt, (circle[0], circle[1]), end_im)

        # Print information
        """
        print('Start point: ', start_pt)
        print('End point: ', end_pt)
        print('Center point: ', circle)
        print('Angle: ', angle)
        """
    else:
        print('No circle found')
        theta = None
        angle_im = None

    return theta, angle_im


if __name__ == '__main__':

    # Read the initial image to find the starting point
    start_im = cv2.imread('.\\appImageViewer4J\images\Initial_angle_disk.jpg')

    # Read the image to find the ending point
    end_im = cv2.imread('.\\appImageViewer4J\images\\50ms_disk_speedup.jpg')

    # Find the angle
    findAngle(start_im, end_im)
