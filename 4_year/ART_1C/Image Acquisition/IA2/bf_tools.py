#!/usr/bin/python -tt
# -*- coding: utf-8 -*-  (use format: UTF-8 without BOM)
#
# bf_tools.py   Image Acquisition (ELE610) using python 3.x
# TODO: rename to myCameraTools.py ??
#
# .../Dropbox/ELE610/py3/bf_tools.py   
# Karl Skretting, UiS, October 2018

# Anaconda prompt..> activate py35
# (py35) C:\..\py3> python -i startup.py 
# >>> from bf_tools import mpl_plot
# >>> import bf_tools as bft
# >>> (img, cInfo, sInfo, rectAOI) = bft.cam_info(dev_id=0, verbose=True)  # image as RGB
# >>> bft.mpl_plot(img)              # matplotlib use RGB
# >>> bft.cv_plot(img[:,:,[2,1,0]])  # OpenCV use BGR

import numpy as np
import matplotlib.pyplot as plt
import cv2
# some functions in this module need ueye, but not all (ex: cv_plot, mpl_plot)
try:
	from pyueye import ueye
except ImportError:
	print('ImportError for pyueye; ueye is not available.')
except:
	print('Error importing pyueye; ueye is not available.')

class NoeGikkFeil(Exception):
	""" Exception used in cam_info() and perhaps other functions (?)
	"""
	def __init__(self, errNo, message):
		self.errNo = errNo
		self.message = message
	
# end class

def error_text(errNo):
	""" get the error text for the given ueye error number
	Error codes are listed in User Manual, uEye Software Development Kit (SDK) ch. 5
	ex:   t = error_text(errNo)
	"""
	if (errNo == ueye.IS_SUCCESS): # 0
		t = "No error."
	elif (errNo == ueye.IS_NO_SUCCESS): # -1
		t = "IS_NO_SUCCESS"
	elif (errNo == ueye.IS_INVALID_CAMERA_HANDLE):  # 1
		t = "IS_INVALID_CAMERA_HANDLE"
	elif (errNo == ueye.IS_IO_REQUEST_FAILED):  # 2
		t = "IS_IO_REQUEST_FAILED"
	elif (errNo == ueye.IS_CANT_OPEN_DEVICE):  # 3
		t = "IS_CANT_OPEN_DEVICE"
	elif (errNo == ueye.IS_TIMED_OUT): # 122
		t = "IS_TIMED_OUT"
	elif (errNo == ueye.IS_CAPTURE_RUNNING): # 140
		t = "IS_CAPTURE_RUNNING"
	elif (errNo == ueye.IS_DEVICE_ALREADY_PAIRED): # 197
		t = "IS_DEVICE_ALREADY_PAIRED"
	else:
		t = "Text not assigned, see IDS uEye User Manual for list"
	#
	return t

def cv_plot(img, title=''):
	""" plot image (using OpenCV)
	use:  cv_plot(img, title='')
	"""
	if not isinstance(img, np.ndarray):
		print( 'Arg 1 is not ndarray, i.e. not an image.' )
		return
	#end if
	#
	cv2.imshow(title,img)
	print( 'Press a key to continue.' )
	cv2.waitKey()  # wait until a key is pressed
	# k = cv2.waitKey(0) & 0xFF    # returned value k is NoneType ??
	# print('key number %i (%s) was pressed.' % (k, ord(k)))  # ??
	cv2.destroyAllWindows()
	return 

def mpl_plot(img, title='', fsize=(8,6)):
	""" plot image (using matplotlib.pyplot)
	title may be given, fsize (w,h) is given in inches, each inch 100 pixel
	ex:  mpl_plot(img[:,:,[2,1,0]], title='')    # BGR bilde
	"""
	if not isinstance(img, np.ndarray):
		print( 'Arg 1 is not ndarray, i.e. not an image.' )
		return
	#end if
	#
	(fig, ax) = plt.subplots(num=1, figsize=fsize, dpi=100, facecolor='w', edgecolor='k')
	ax.axis('off')
	if (img.ndim == 3):   # RGB ?
		ax.imshow( img )
		if not len(title):
			title = 'RGB-image of %s, height x width is %ix%i' % (str(img.dtype), img.shape[0], img.shape[1])
	if (img.ndim == 2): 
		if (img.dtype == 'uint8'):
			ax.imshow( img, plt.cm.gray )
		if (img.dtype == 'int16'):
			ax.imshow( img, plt.cm.bwr )
		if not len(title):
			title = 'Grayscale image of %s, height x width is %ix%i' % (str(img.dtype), img.shape[0], img.shape[1])
	#endif
	plt.title( title )
	plt.show(block = True)     
	return

def cam_info(dev_id=0, verbose=True):
	""" initialize camera, get and display camera information, capture an image,
	and then close the camera again. This is perhaps simpler, i.e. easier to 
	understand, than the code in pyueye_example_main.py as Qt and OpenCV
	is not used here. 
	This function is not appropriate to use within a loop.
	Example:
	  (img, cInfo, sInfo, rectAOI) = cam_info(dev_id=1, verbose=True):
	"""
	#Variables
	hCam = ueye.HIDS(dev_id)     # ueye.HIDS()        blir objekt   ueye.c_uint 
	cInfo = ueye.CAMINFO()
	sInfo = ueye.SENSORINFO()    # tomt objekt
	pcImageMemory = ueye.c_mem_p()
	MemID = ueye.INT()           # ueye.int() og ueye.INT() blir begge objekt  ueye.c_int
	rectAOI = ueye.IS_RECT()
	pitch = ueye.INT()
	nBitsPerPixel = ueye.INT(24)    # 24: bits per pixel for color mode; take 8 bits per pixel for monochrome
	nColorMode = ueye.INT(0)        # Y8/RGB16/RGB24/REG32
	channels = 3                    #3: channels for color mode(RGB); take 1 channel for monochrome
	bytes_per_pixel = int( (nBitsPerPixel.value + 7)/8 )   # runder av nedover 
	isInitialised = False
	isMemoryAssigned = False
	img = -1
	#-----------------------------------------------------------------------
	try:
		# Starts the driver and establishes the connection to the camera
		nRet = ueye.is_InitCamera(hCam, None)
		if nRet != ueye.IS_SUCCESS:
			raise NoeGikkFeil(nRet, 'ERROR in ueye.is_InitCamera()')
		#
		isInitialised = True

		# Reads out the data hard-coded in the non-volatile camera memory 
		# and writes it to the data structure that cInfo points to
		nRet = ueye.is_GetCameraInfo(hCam, cInfo)
		if nRet != ueye.IS_SUCCESS:
			raise NoeGikkFeil(nRet, 'ERROR in ueye.is_GetCameraInfo()')
		#
		if verbose:
			print(" CameraInfo: ")   # cInfo er 64 byte, se kap 2.3 User Manual uEye
			print("   Camera serial no.:    %s" % cInfo.SerNo.decode('utf-8')) # 12 byte
			print("   Camera ID:            %s" % cInfo.ID.decode('utf-8')) # 20 byte
			print("   Camera Version:       %s" % cInfo.Version.decode('utf-8')) # 10 byte
			print("   Camera Date:          %s" % cInfo.Date.decode('utf-8')) # 12 byte
			print("   Camera Select byte:   %i" % cInfo.Select.value) # 1 byte
			print("   Camera Type byte:     %i" % cInfo.Type.value) # 1 byte
			# 8 site byte er ubrukt

		# You can query additional information about the sensor type used in the camera
		nRet = ueye.is_GetSensorInfo(hCam, sInfo)
		if nRet != ueye.IS_SUCCESS:
			raise NoeGikkFeil(nRet, 'ERROR in ueye.is_GetSensorInfo()')
		#
		if verbose:
			print(" SensorInfo: ")   # sInfo, se kap 4.50 User Manual uEye
			print("   Sensor SensorID:      %i" % sInfo.SensorID.value) # WORD (ushort) (2 byte)
			print("   Sensor strSensorName: %s" % sInfo.strSensorName.decode('utf-8')) # 32 byte
			print("   Sensor nColorMode:    %i" % int.from_bytes(sInfo.nColorMode.value, byteorder='big')) # 1 byte
			print("   Sensor nMaxWidth:     %i" % sInfo.nMaxWidth.value) # DWORD (uint) (4 byte)
			print("   Sensor nMaxHeight:    %i" % sInfo.nMaxHeight.value) # DWORD (uint) (4 byte)
			print("   Sensor bMasterGain:   %s" % str(bool(sInfo.bMasterGain.value)))
			print("   Sensor bRGain:        %s" % str(bool(sInfo.bRGain.value)))
			print("   Sensor bGGain:        %s" % str(bool(sInfo.bGGain.value)))
			print("   Sensor bBGain:        %s" % str(bool(sInfo.bBGain.value)))
			print("   Sensor bGlobShutter:  %s" % str(bool(sInfo.bGlobShutter.value)))
	
		nRet = ueye.is_ResetToDefault( hCam	)
		if nRet != ueye.IS_SUCCESS:
			raise NoeGikkFeil(nRet, 'ERROR in ueye.is_ResetToDefault()')
		#
		if verbose:
			print(" is_ResetToDefault(..) ok") 

		# Set display mode to DIB
		nRet = ueye.is_SetDisplayMode(hCam, ueye.IS_SET_DM_DIB)
		if nRet != ueye.IS_SUCCESS:
			raise NoeGikkFeil(nRet, 'ERROR in ueye.is_SetDisplayMode()')
		#	
		if verbose:
			print(" is_SetDisplayMode(..) ok") 
	
		# Set the right color mode
		col_mode = int.from_bytes(sInfo.nColorMode.value, byteorder='big')  # ordinary Python int
		if col_mode == ueye.IS_COLORMODE_BAYER:  
			nRet = ueye.is_GetColorDepth(hCam, nBitsPerPixel, nColorMode)
			if nRet != ueye.IS_SUCCESS:
				raise NoeGikkFeil(nRet, 'ERROR in ueye.is_GetColorDepth()')
			#	
			bytes_per_pixel = int( (nBitsPerPixel.value + 7)/8 )   # runder av nedover 
			if verbose:
				print(" IS_COLORMODE_BAYER: nColorMode = %i, nBitsPerPixel = %i, bytes_per_pixel = %i" % 
						(nColorMode, nBitsPerPixel, bytes_per_pixel) )
		elif col_mode == ueye.IS_COLORMODE_CBYCRY:
			# for color camera models use RGB32 mode
			nColorMode = ueye.INT( ueye.IS_CM_BGRA8_PACKED )
			nBitsPerPixel = ueye.INT(32)
			bytes_per_pixel = int( (nBitsPerPixel.value + 7)/8 )   # runder av nedover 
			if verbose:
				print(" IS_COLORMODE_CBYCRY: nColorMode = %i, nBitsPerPixel = %i, bytes_per_pixel = %i" % 
						(nColorMode, nBitsPerPixel, bytes_per_pixel) )
		elif col_mode == ueye.IS_COLORMODE_MONOCHROME:
			nColorMode = ueye.INT( ueye.IS_CM_MONO8 )
			nBitsPerPixel = ueye.INT(8)
			bytes_per_pixel = int( (nBitsPerPixel.value + 7)/8 )   # runder av nedover 
			if verbose:
				print(" IS_COLORMODE_MONOCHROME: nColorMode = %i, nBitsPerPixel = %i, bytes_per_pixel = %i" % 
						(nColorMode, nBitsPerPixel, bytes_per_pixel) )
		else: 		# for monochrome camera models use Y8 mode
			nColorMode = ueye.INT( ueye.IS_CM_MONO8 )
			nBitsPerPixel = ueye.INT(8)
			bytes_per_pixel = int( (nBitsPerPixel.value + 7)/8 )   # runder av nedover 
			if verbose:
				print(" else: nColorMode = %i, nBitsPerPixel = %i, bytes_per_pixel = %i" % 
						(nColorMode, nBitsPerPixel, bytes_per_pixel) )
		#end if
	
		# Can be used to set the size and position of an "area of interest"(AOI) within an image
		nRet = ueye.is_AOI(hCam, ueye.IS_AOI_IMAGE_GET_AOI, rectAOI, ueye.sizeof(rectAOI))
		if nRet != ueye.IS_SUCCESS:
			raise NoeGikkFeil(nRet, 'ERROR in ueye.is_AOI()')
		#
		width = rectAOI.s32Width   # c_int
		height = rectAOI.s32Height # c_int
		if verbose:
			print(" AOI: max size (width x height) = %i x %i" % (width.value, height.value) ) 
	
		# Allocates an image memory for an image having its dimensions 
		# defined by width and height and its color depth defined by nBitsPerPixel
		nRet = ueye.is_AllocImageMem(hCam, width, height, nBitsPerPixel, pcImageMemory, MemID)
		if nRet != ueye.IS_SUCCESS:
			raise NoeGikkFeil(nRet, 'ERROR in ueye.is_AllocImageMem()')
		#
		isMemoryAssigned = True
		if verbose:
			print(" is_AllocImageMem ok." ) 
		#
		# Makes the specified image memory the active memory
		nRet = ueye.is_SetImageMem(hCam, pcImageMemory, MemID)
		if nRet != ueye.IS_SUCCESS:
			raise NoeGikkFeil(nRet, 'ERROR in ueye.is_SetImageMem()')
		#
		# Set the desired color mode
		nRet = ueye.is_SetColorMode(hCam, nColorMode)
		if nRet != ueye.IS_SUCCESS:
			raise NoeGikkFeil(nRet, 'ERROR in ueye.is_SetColorMode()')
		#
		# alt (?) ok så langt
		
		# Activates the camera's live video mode (free run mode) 
		# nRet = ueye.is_CaptureVideo(hCam, ueye.IS_DONT_WAIT)  # gir sort bilde
		# nRet = ueye.is_CaptureVideo(hCam, 10)  # wait 0.1 s
		nRet = ueye.is_CaptureVideo(hCam, ueye.IS_WAIT)  # works well
		if nRet != ueye.IS_SUCCESS:
			raise NoeGikkFeil(nRet, 'ERROR in ueye.is_CaptureVideo()')
		#
		# When (if ever) do we need Freeze?  if there is no wait-time?
		# nRet = ueye.is_FreezeVideo(hCam, 10)  # wait 0.1 s
		# if nRet != ueye.IS_SUCCESS:
		# 	raise NoeGikkFeil(nRet, 'ERROR in ueye.is_FreezeVideo()')
		#
		# Enables the queue mode for existing image memory sequences
		nRet = ueye.is_InquireImageMem(hCam, pcImageMemory, MemID, width, height, nBitsPerPixel, pitch)
		if nRet != ueye.IS_SUCCESS:
			raise NoeGikkFeil(nRet, 'ERROR in ueye.is_InquireImageMem()')
		#
		if verbose:
			print(" InquireImageMem: size (width x height) = %i x %i, bits per pixel %i, line increment %i = %i" % 
					(width.value, height.value, nBitsPerPixel.value, pitch.value, 
					width.value * int((nBitsPerPixel.value + 7)/8)) ) 
		#
		if verbose:
			print(" Capture the image from camera memory and store it as numpy array" )
		# ...extract the data of our image memory (undocumented?) function
		array = ueye.get_data(pcImageMemory, width, height, nBitsPerPixel, pitch, copy=True)
		if verbose:
			print((' numpy array has size %i and ndim %i, shape = ' % (array.size, array.ndim)), array.shape)
		# ...reshape it in an numpy array...
		img = np.reshape(array, (height.value, width.value, bytes_per_pixel) )
		if verbose:
			print((' img as captured has size %i and ndim %i, shape = ' % (img.size, img.ndim)), img.shape)
		#-----------------------------------------------------
		# may do some image processing. Ex: resize the image by a half
		#   img = cv2.resize(img, (0,0), fx=0.5, fy=0.5)
		#-----------------------------------------------------
		#
		img = img[:,:,[2,1,0]]   # # BGRx --> RGB
		if verbose:
			print((' img as returned has size %i and ndim %i, shape = ' % (img.size, img.ndim)), img.shape)
		#
	except NoeGikkFeil as e:
		print( ' ** %s, errNo = %i (%s).' % (e.message, e.errNo, error_text(e.errNo)) )
		#
	except Exception as e:
		print(" ** An ordinary error occurred.")
		print(" **", e )
	except:
		print(" ** Another error occurred.")
	else:
		print(" Function returns with no errors.")
	finally:
		# free memory and stop camera
		if isMemoryAssigned:
			# cv2.destroyAllWindows()  
			ueye.is_FreeImageMem(hCam, pcImageMemory, MemID)
			isMemoryAssigned = False
		if isInitialised:
			ueye.is_ExitCamera(hCam)
			isInitialised = False
	#end try
	return (img, cInfo, sInfo, rectAOI)
#end cam_info(..)

if __name__ == '__main__':
	(img, cInfo, sInfo, rectAOI) = cam_info(dev_id=0, verbose=True)
	
