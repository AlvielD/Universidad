import requests
import time

from rwsuis import RWS

ROBOT_IP = "http://152.94.0.38"
ROBOT_URL = f"{ROBOT_IP}/rw/panel/ctrlstate?json=1"

# Making a GET request to the API #
auth = requests.auth.HTTPDigestAuth('Default User', 'robotics')

response = requests.get(ROBOT_URL, auth=auth)
controller_status = response.status_code

# Making a POST request to the API #
robot = RWS.RWS(ROBOT_IP)

robot.request_rmmp() # Mastership request
time.sleep(10)       # Give time to accept request

# robot.request_mastership() # Mastership request (automatic mode)

new_robtarget = [0, 0, 400]
robot.set_robtarget_translation("simple_robtarget", new_robtarget)

print(response)
print(controller_status)