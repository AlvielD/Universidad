from pyueye import ueye

# Set some camera options
h_cam = ueye.HIDS(0)
ret = ueye.is_InitCamera(h_cam, None)

# Check if the camera was initialized correctly
if ret != ueye.IS_SUCCESS:
	print('Error, the camera was not initialized correctly')

# Set the camera mode
ueye.is_SetDisplayMode(h_cam, ueye.IS_SET_DM_DIRECT3D)

# Set color mode
ueye.is_SetColorMode(h_cam, ueye.IS_CM_RGB8_PACKED)

# Start recording
ueye.is_CaptureVideo(h_cam, ueye.IS_WAIT)

# Capture a frame
ueye.is_FreezeVideo(h_cam, ueye.IS_WAIT)

# Save the image
FileParams = ueye.IMAGE_FILE_PARAMS()
FileParams.pwchFileName = "python_test_image.jpg"
FileParams.nFileType = ueye.IS_IMG_JPG
FileParams.ppcImageMem = None
FileParams.pnImageID = None
ueye.is_ImageFile(h_cam, ueye.IS_IMAGE_FILE_CMD_SAVE, FileParams, ueye.sizeof(FileParams))

# Close the camera
ueye.is_ExitCamera(h_cam)