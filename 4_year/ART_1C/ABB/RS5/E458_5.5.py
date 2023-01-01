# Transformation from Image qr_corners System to Wobj qr_corners System
# Wobj_x = -Image_y
# Wobj_y = -Image_x

import cv2
import requests
from rwsuis import RWS
import time
import math

from pyzbar.pyzbar import decode

#########################################################
#                  Constants definitions                #
#########################################################

# ROBOT CONTANTS
ROBOT_IP = "http://152.94.0.38"
ROBOT_URL = f"{ROBOT_IP}/rw/panel/ctrlstate?json=1"
ROBOT_VAR = "puck_target"

# CAMERA CONSTANTS
FOCAL_LENGTH = 3.7
SENSOR_WIDTH = 3.6288
HEIGHT_PUCK = 30
RESOLUTION_WIDTH = 1280
RESOLUTION_HEIGHT = 960
SLOPE_X = 0.0229
SLOPE_Y = -0.0003
CLOSE_UP_DISTANCE = 100

#########################################################
#                     Util Functions                    #
#########################################################

"""
def to_mm(pixels: int):
    return pixels*(fov_width/RESOLUTION_WIDTH)
"""

# Wait method used in Python
def wait_for_rapid (robot, var='ready_flag'):
    """ Waits for robot to complete RAPID instructionsuntil boolean
    variable in RAPID is set to 'TRUE '.
    Default variable name is 'ready_flag ', but others may be used.
    """

    while robot.get_rapid_variable(var) == "FALSE" and robot.is_running():
        time.sleep(0.1)
    robot.set_rapid_variable (var, "FALSE")

#########################################################
#      First, use OpenCV to initialize the camera       #
#         or use the Camera class given if you          #
#              prefer to use uEye API                   #
#########################################################

# Initialize the camera
print("Initializing camera...")
cap = cv2.VideoCapture(1, cv2.CAP_DSHOW)
print("Camera initialized")

# Change resolution to 1280 x960
cap.set(3 , 1280)
cap.set(4 , 960)

#########################################################
#       Second, initialize robot communication,         #
#             and execute RAPID program                 #
#                 (Create RWS object)                   #
#########################################################

robot = RWS.RWS(ROBOT_IP)
robot.request_mastership()
robot.start_RAPID()
#robot.request_rmmp()
#time.sleep(5)
#robot.motors_on()

# Initialize the robot constants
PIXEL_CENTER = [RESOLUTION_WIDTH/2, RESOLUTION_HEIGHT/2]

while True:  # Run script indefinitely
    print("""
        Choose what to do:
        1: Pick and place a single puck
        0: Exit
        """)
    # Program may be extended

    userinput = int(input('\nWhat should RAPID do?: '))

    if userinput == 1:
        """
        Pick up and place a chosen puck to a chosen location. 
        Captures an image and finds all pucks in the work area.
        The user is prompted to select puck and location.
        Uses collision avoidance when picking up puck.
        """

        print("Pick and place a single puck")

        #########################################################
        #     Start wanted case in RAPID and wait for RAPID     #
        #########################################################

        robot.set_rapid_variable("WPW", 1)  # Tell RAPID what to do
        wait_for_rapid(robot)               # Wait until rapid finishes

        #########################################################
        #  Capture image, process it and scan it for QR codes   #
        #     Do this several times if no QR code is found      #
        #########################################################
       
        _, frame = cap.read()   # Capture frame

        # Image processing
        #---------------------------------------------#
        frame = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY) # Convert to grayscale
        frame = cv2.bilateralFilter(src=frame, d=3, sigmaColor=75, sigmaSpace=75)
        frame = cv2.normalize(frame, dst=None, alpha=0, beta=255, norm_type=cv2.NORM_MINMAX, dtype=-1) # Normalize histogram
        #---------------------------------------------#

        # Show the frame
        #---------------------------------------------#
        cv2.namedWindow("frame", cv2.WINDOW_NORMAL) 
        cv2.resizeWindow("frame", RESOLUTION_WIDTH, RESOLUTION_HEIGHT) 
        cv2.imshow('frame', frame)

        k = cv2.waitKey(0)
        if k == 27:         # wait for ESC key to exit
            cv2.destroyAllWindows() 
        #---------------------------------------------#

        qr_data = decode(frame) # Scan QR code

        #########################################################
        #    Create a robtarget from the QR codes' position     #
        #########################################################

        rob_target = None
        
        if len(qr_data) > 0:
            # We found a QR code
            print(f"Found {len(qr_data)} QR codes")
            
            # Compute center of the puck (pixels)
            qr_corners = qr_data[0].polygon
            puck_center_x = sum([qr_corners[i][0] for i in range(len(qr_corners))])/len(qr_corners)
            puck_center_y = sum([qr_corners[i][1] for i in range(len(qr_corners))])/len(qr_corners)

            print(puck_center_x, puck_center_y)

            # Compute center of the puck relative to the center of the image
            puck_pos = [puck_center_x - PIXEL_CENTER[0], puck_center_y - PIXEL_CENTER[1]]

            # Compensation constants
            gripper_height = robot.get_gripper_height()
            working_distance = gripper_height + 70 - HEIGHT_PUCK
            fov_width = (SENSOR_WIDTH/FOCAL_LENGTH)*working_distance
            pixels_to_mm = fov_width/RESOLUTION_WIDTH

            # Transformation of the position
            puck_pos = [-puck_pos[1], -puck_pos[0]]                         # Change coordinate system
            puck_mm = [puck_pos[0]*pixels_to_mm, puck_pos[1]*pixels_to_mm]  # Convert to mm

            # Correct camera position
            gripper_position = robot.get_gripper_position()[0]
            camera_position = [gripper_position[0] + 55, gripper_position[1]]

            puck_mm = [puck_mm[0] + camera_position[0], puck_mm[1] + camera_position[1]] # Add camera position
            print(puck_mm)

            # Compute compensation
            comp_x = SLOPE_X * working_distance
            comp_y = SLOPE_Y * working_distance

            rob_target = [puck_mm[0] - comp_x, puck_mm[1] - comp_y, CLOSE_UP_DISTANCE]  # Compesation for camera
            #rob_target = [rob_target[0] + 32, rob_target[1] + 32, rob_target[2]]    # Error correction
        else:
            print("No QR codes found")

        #########################################################
        #               Send the robtarget to RAPID             #
        #########################################################
        
        robot.set_robtarget_translation(ROBOT_VAR, rob_target)

        #########################################################
        #    Tell RAPID to move to a close-up image position    #
        #               (Update flag variable)                  #
        #########################################################

        robot.set_rapid_variable('image_processed', True)

        #########################################################
        #       Wait for robot to reach close-up position       #
        #  Capture image, process it and scan it for QR codes   #
        #     Do this several times if no QR code is found      #
        #########################################################
        
        wait_for_rapid(robot)
        _, frame = cap.read()

        cv2.namedWindow("frame", cv2.WINDOW_NORMAL) 
        cv2.resizeWindow("frame", RESOLUTION_WIDTH, RESOLUTION_HEIGHT) 
        cv2.imshow('frame', frame)

        k = cv2.waitKey(0)
        if k == 27:         # wait for ESC key to exit
            cv2.destroyAllWindows() 

        # Image processing
        #---------------------------------------------#
        frame = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY) # Convert to grayscale
        frame = cv2.bilateralFilter(src=frame, d=3, sigmaColor=75, sigmaSpace=75)
        frame = cv2.normalize(frame, dst=None, alpha=20, beta=255, norm_type=cv2.NORM_MINMAX, dtype=-1) # Normalize histogram
        #---------------------------------------------#

        qr_data = decode(frame)

        #########################################################
        #         Create a (more accurate) robtarget            #
        #            from the QR codes' position                #
        #########################################################
        rob_target = None
        
        if len(qr_data) > 0:
            # We found a QR code
            print(f"Found {len(qr_data)} QR codes")
            
            # Compute center of the puck (pixels)
            qr_corners = qr_data[0].polygon
            puck_center_x = sum([qr_corners[i][0] for i in range(len(qr_corners))])/len(qr_corners)
            puck_center_y = sum([qr_corners[i][1] for i in range(len(qr_corners))])/len(qr_corners)

            print(puck_center_x, puck_center_y)

            # Compute center of the puck relative to the center of the image
            puck_pos = [puck_center_x - PIXEL_CENTER[0], puck_center_y - PIXEL_CENTER[1]]

            # Compensation constants
            gripper_height = robot.get_gripper_height()
            working_distance = gripper_height + 70 - HEIGHT_PUCK
            fov_width = (SENSOR_WIDTH/FOCAL_LENGTH)*working_distance
            pixels_to_mm = fov_width/RESOLUTION_WIDTH

            # Transformation of the position
            puck_pos = [-puck_pos[1], -puck_pos[0]]                         # Change coordinate system
            puck_mm = [puck_pos[0]*pixels_to_mm, puck_pos[1]*pixels_to_mm]  # Convert to mm

            # Correct camera position
            gripper_position = robot.get_gripper_position()
            camera_position = [gripper_position[0][0] + 55, gripper_position[0][1]]

            puck_mm = [puck_mm[0] + camera_position[0], puck_mm[1] + camera_position[1]] # Add camera position
            print(puck_mm)

            # Compute compensation
            comp_x = SLOPE_X * working_distance
            comp_y = SLOPE_Y * working_distance

            rob_target = [puck_mm[0] - comp_x, puck_mm[1] - comp_y, 10]
            rob_target = [rob_target[0] + 35, rob_target[1] + 70, rob_target[2]]    # Error correction
        else:
            print("No QR codes found")

        #########################################################
        #               Send the robtarget to RAPID             #
        #########################################################

        robot.set_robtarget_translation(ROBOT_VAR, rob_target)
        
        #########################################################
        #           Tell RAPID to pick up and place puck        #
        #               (Update flag variable)                  #
        #########################################################

        robot.set_rapid_variable('image_processed', True)

    elif userinput == 0:
        print("Exiting Python program and turning off robot motors")
        #########################################################
        #   Start RAPID execution and switch off robot motors   #
        #                (Use RWS functions)                    #
        #########################################################
        # ----------------insert code here--------------------- #
        break