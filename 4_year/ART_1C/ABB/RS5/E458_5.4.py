import cv2
import pprint
from pyzbar.pyzbar import decode

cap = cv2.VideoCapture(1)

# Change resolution to 1280 x960
cap.set(3 , 1280)
cap.set(4 , 960)
ret, frame = cap.read()

# Image processing #
frame = cv2.fastNlMeansDenoisingColored(frame, None, 10, 10, 7, 21) # Denoising
frame = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY) # Convert to grayscale
frame = cv2.equalizeHist(frame) # Equalize histogram

# Show image
"""
cv2.namedWindow("frame", cv2.WINDOW_NORMAL) 
cv2.resizeWindow("frame", 1280, 960) 
cv2.imshow('frame', frame)

k = cv2.waitKey(0)
if k == 27:         # wait for ESC key to exit
    cv2.destroyAllWindows()
"""

# Extract QR code
decodedObjects = decode(frame)

pprint.pprint(f"Position of decoded objects: {decodedObjects}")