#!/usr/bin/env python
# -*- coding: utf-8 -*-
# 
# ../ELE610/py3/appImageViewer2.py
#
#  Extends appImageViewer1 by adding some more functionality using heritage.
#  Now program can capture an image from an IDS camera attached to the USB 
#  gate of the computer. This program also requires IDS pyueye package,
#  https://pypi.org/project/pyueye/ 
#  This program adds only some few methods to appImageViewer1,
#  methods that should make it possible to capture a single image. The new 
#     camera menu has actions for: Camera On, Get One Image, and Camera Off
#  Program tested using IDS XS camera (the small one at UiS, the larger one is CP)
#
#  appImageViewer1.py is basically developed by copying the file appImageViewer.py
#  and then make the wanted changes and additions. This may be a good way to 
#  make a new program, but it also has some disadvantages; if you want to keep and
#  improve the original program, the new improvements should probably also be
#  done in the copied file (appImageViewer1.py) and you thus have to maintain the
#  common code in two files. 
#  
#  A better way to copy functionality is to use heritage. This is the approach done here,
#  the main window in this file is imported from appImageViewer1.py, and then new
#  functionality is added, or existing functionality may be updated. 
#
#  The user manual for IDS camera uEye software development kit (SDK) is helpful
#  for finding and using the IDS interface functions, it used to be available on
#  https://en.ids-imaging.com/manuals/uEye_SDK/EN/uEye_Manual_4.91/index.html
#  but the requested page cannot be found any more (is it somewhere else, like:
#  https://en.ids-imaging.com/files/downloads/ids-software-suite/interfaces/release-notes/python-release-notes_EN.html 
#  https://en.ids-imaging.com/release-note/release-notes-ids-software-suite-4-90.html 
#  http://en.ids-imaging.com/ueye-interface-python.html  (??)
#  Anyway, I have a copy of the SDK user manual September 2008 version on:
#  ...\Dropbox\ELE610\IDS camera\IDS_uEye_SDK_manual_enu.pdf
#
# Karl Skretting, UiS, November 2018, February 2019, November 2020

# Example on how to use file:
# (C:\...\Anaconda3) C:\..\py3> activate py35
# (py35) C:\..\py3> python appImageViewer2.py
# (py35) C:\..\py3> python appImageViewer2.py rutergray.png

_appFileName = "appImageViewer2"
_author = "Karl Skretting, UiS" 
_version = "2020.11.11"

import sys
import os.path
import numpy as np

try:
	from PyQt5.QtCore import Qt, QPoint, QRectF, QT_VERSION_STR
	from PyQt5.QtGui import QImage, QPixmap, QTransform
	from PyQt5.QtWidgets import (QApplication, QMainWindow, QAction, QFileDialog, QLabel, 
			QGraphicsScene, QGraphicsPixmapItem)
except ImportError:
	raise ImportError("%s: Requires PyQt5." % _appFileName)
#end try, import PyQt5 classes

try:
	from pyueye import ueye
	from pyueye_example_camera import Camera
	from pyueye_example_utils import ImageData, ImageBuffer  # FrameThread, 
	ueyeOK = True
except ImportError:
	ueye_error = "%s: Requires IDS pyueye example files (and IDS camera)." % _appFileName
	# raise ImportError(ueye_error)
	ueyeOK = False   # --> may run program even without pyueye
#end try, import pyueye

from appImageViewer1 import myPath, MainWindow as inheritedMainWindow 
from myImageTools import np2qimage

class MainWindow(inheritedMainWindow):  
	"""MainWindow class for this image viewer is inherited from another image viewer."""
	
# Two initialization methods used when an object is created
	def __init__(self, fName="", parent=None):
		# print(f"File {_appFileName}: (debug) first line in MainWindow.__init__()")
		super().__init__(fName, parent)  # use inherited __init__ with extension as follows
		#
		# set appFileName as it should be, it is set wrong in super()...
		self.appFileName = _appFileName 
		if self.pixmap.isNull(): 
			self.setWindowTitle(self.appFileName)
			self.npImage = np.array([])  # size == 0  
		else:
			self.setWindowTitle("%s : %s" % (self.appFileName, fName))
			self.pixmap2image2np()   # function defined in appImageViewer1.py
		# 
		self.cam = None
		self.camOn = False
		#
		# I had some trouble finding a good way to inherit (and add modifications to) 
		# functions 'initMenu' and 'setMenuItems' from appImageViewer1
		# To avoid any complications the corresponding functions are given new names here,
		# thus risking that the inherited (or the new ones) are not executed whenever they should be.
		self.initMenu2()
		self.setMenuItems2()
		# print(f"File {_appFileName}: (debug) last line in MainWindow.__init__()")
		return
	
	def initMenu2(self):
		"""Initialize Camera menu."""
		# print("File %s: (debug) first line in initMenu2()" % _appFileName)
		a = self.qaCameraOn = QAction('Camera on', self)
		a.triggered.connect(self.cameraOn)
		#
		a = self.qaCameraInfo = QAction('Print camera info', self)
		a.triggered.connect(self.printCameraInfo)
		#
		a = self.qaGetOneImage = QAction('Get one image', self)
		a.setShortcut('Ctrl+N')
		a.triggered.connect(self.getOneImage)
		#
		a = self.qaCameraOff = QAction('Camera off', self)
		a.triggered.connect(self.cameraOff)
		#
		camMenu = self.mainMenu.addMenu('&Camera')
		camMenu.addAction(self.qaCameraOn)
		camMenu.addAction(self.qaCameraInfo)
		camMenu.addAction(self.qaGetOneImage)
		camMenu.addAction(self.qaCameraOff)
		# print("File %s: (debug) last line in initMenu2()" % _appFileName)
		return
	
# Some methods that may be used by several of the menu actions
	def setMenuItems2(self):
		"""Enable/disable menu items as appropriate."""
		# should the 'inherited' function be used, first check if it exists 
		setM = getattr(super(), "setMenuItems", None)  # both 'self' and 'super()' seems to work as intended here
		if callable(setM):
			# print("setMenuItems2(): The 'setMenuItems' function is inherited.")
			setM()  # and run it
		# self.setMenuItems() 
		self.qaCameraOn.setEnabled(ueyeOK and (not self.camOn))
		self.qaCameraInfo.setEnabled(ueyeOK and self.camOn)
		self.qaGetOneImage.setEnabled(ueyeOK and self.camOn)
		self.qaCameraOff.setEnabled(ueyeOK and self.camOn)
		return
		
	def copy_image(self, image_data):
		"""Copy an image from camera memory to numpy image array 'self.npImage'."""
		tempBilde = image_data.as_1d_image()
		if np.min(tempBilde) != np.max(tempBilde):
			self.npImage = np.copy(tempBilde[:,:,[0,1,2]])  # or [2,1,0] ??  RGB or BGR?
			print("copy_image(): 'self.npImage' is an ndarray of %s, shape %s." % (self.npImage.dtype.name, str(self.npImage.shape)))
		else:
			self.npImage = np.array([])  # size == 0
		#end if 
		image_data.unlock()  # important action
		return 
		
# Methods for actions on the Camera-menu
	def cameraOn(self):
		"""Turn IDS camera on."""
		if ueyeOK and (not self.camOn):
			self.cam = Camera()
			self.cam.init()  # gives error when camera not connected
			self.cam.set_colormode(ueye.IS_CM_BGR8_PACKED)
			# This function is currently not supported by the camera models USB 3 uEye XC and XS.
			self.cam.set_aoi(0, 0, 720, 1280)  # but this is the size used
			self.cam.alloc(3)  # argument is number of buffers
			self.camOn = True
			self.setMenuItems2()
			print('%s: cameraOn() Camera started ok' % self.appFileName)
		#
		return
	
	def printCameraInfo(self):
		"""Print some information on camera."""
		if ueyeOK and self.camOn:
			print("printCameraInfo(): print (test) state and settings.")
			# just set a camera option (parameter) even if it is not used here
			d = ueye.double()
			retVal = ueye.is_SetFrameRate(self.cam.handle(), 2.0, d)
			if retVal == ueye.IS_SUCCESS:
				print('  frame rate set to                      %8.3f fps' % d)
			retVal = ueye.is_Exposure(self.cam.handle(), ueye.IS_EXPOSURE_CMD_GET_EXPOSURE_DEFAULT, d, 8)
			if retVal == ueye.IS_SUCCESS:
				print('  default setting for the exposure time  %8.3f ms' % d)
			retVal = ueye.is_Exposure(self.cam.handle(), ueye.IS_EXPOSURE_CMD_GET_EXPOSURE_RANGE_MIN, d, 8)
			if retVal == ueye.IS_SUCCESS:
				print('  minimum exposure time                  %8.3f ms' % d)
			retVal = ueye.is_Exposure(self.cam.handle(), ueye.IS_EXPOSURE_CMD_GET_EXPOSURE_RANGE_MAX, d, 8)
			if retVal == ueye.IS_SUCCESS:
				print('  maximum exposure time                  %8.3f ms' % d)
			retVal = ueye.is_Exposure(self.cam.handle(), ueye.IS_EXPOSURE_CMD_GET_EXPOSURE, d, 8)
			if retVal == ueye.IS_SUCCESS:
				print('  currently set exposure time            %8.3f ms' % d)
			d =  ueye.double(25.0)
			retVal = ueye.is_Exposure(self.cam.handle(), ueye.IS_EXPOSURE_CMD_SET_EXPOSURE, d, 8)
			if retVal == ueye.IS_SUCCESS:
				print('  tried to changed exposure time to      %8.3f ms' % d)
			retVal = ueye.is_Exposure(self.cam.handle(), ueye.IS_EXPOSURE_CMD_GET_EXPOSURE, d, 8)
			if retVal == ueye.IS_SUCCESS:
				print('  currently set exposure time            %8.3f ms' % d)
			#
		return
		
	def getOneImage(self):
		"""Get one image from IDS camera."""
		if ueyeOK and self.camOn:
			self.view.setMouseTracking(False)
			print('%s: getOneImage() try to capture one image' % self.appFileName)
			imBuf = ImageBuffer()  # used to get return pointers
			self.cam.freeze_video(True)
			retVal = ueye.is_WaitForNextImage(self.cam.handle(), 1000, imBuf.mem_ptr, imBuf.mem_id)
			if retVal == ueye.IS_SUCCESS:
				print('  ueye.IS_SUCCESS: image buffer id = %i' % imBuf.mem_id)
				self.copy_image( ImageData(self.cam.handle(), imBuf) )  # copy image_data 
				if (self.npImage.size > 0): # ok
					self.image = np2qimage(self.npImage)
					if (not self.image.isNull()):
						self.pixmap = QPixmap.fromImage(self.image)
						if self.curItem: 
							self.scene.removeItem(self.curItem)
						self.curItem = QGraphicsPixmapItem(self.pixmap)
						self.scene.addItem(self.curItem)
						self.scene.setSceneRect(0, 0, self.pixmap.width(), self.pixmap.height())
						self.setWindowTitle("%s : Camera image" % self.appFileName) 
						self.status.setText('pixmap: (w,h) = (%i,%i)' % (self.pixmap.width(), self.pixmap.height()))
						self.scaleOne()
						self.view.setMouseTracking(True)
					else:
						self.pixmap = QPixmap()
					#end
				else:  # empty image self.npImage
					self.image = QImage()
					self.pixmap = QPixmap()
					print('  no image in buffer ' + str(image_data))
				#
			else: 
				self.setWindowTitle("%s: getOneImage() error retVal = %i" % (self.appFileName, retVal) )
			#end if
			self.setIsAllGray()
			self.setMenuItems2()
		#else:  
		#	pass  # ignore action
		return
		
	def cameraOff(self):
		"""Turn IDS camera off and print some information."""
		if ueyeOK and self.camOn:
			self.cam.exit()
			self.camOn = False
			self.setMenuItems2()
			print('%s: cameraOff() Camera stopped ok' % self.appFileName)
		return
	
#end class MainWindow

if __name__ == '__main__':
	print("%s: (version %s), path for images is: %s" % (_appFileName, _version, myPath))
	print("%s: Using Qt %s" % (_appFileName, QT_VERSION_STR))
	mainApp = QApplication(sys.argv)
	if (len(sys.argv) >= 2):
		fn = sys.argv[1]
		if not os.path.isfile(fn):
			fn = myPath + os.path.sep + fn   # alternative location
		mainWin = MainWindow(fName=fn)
	else:
		mainWin = MainWindow()
	mainWin.show()
	sys.exit(mainApp.exec_())  # single_trailing_underscore_ is used for avoiding conflict with Python keywords
