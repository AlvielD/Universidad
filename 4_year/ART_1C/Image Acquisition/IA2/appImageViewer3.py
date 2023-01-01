#!/usr/bin/env python
# -*- coding: utf-8 -*-
#
# ../ELE610/py3/appImageViewer3.py    (dice)
#
#  Extends appImageViewer2 by adding some more functionality using heritage.
#  Some (skeleton) methods for locating dice in image and counting eyes has
#  been implemented here, these can (and should) be further developed.
#  Color menu can have actions for: dice colors, select colors, ...
#  Dice menu can have actions for: locating dices, finding circles, ...
#
# Karl Skretting, UiS, November 2020

# Example on how to use file:
# (C:\...\Anaconda3) C:\..\py3> activate py38
# (py38) C:\..\py3> python appImageViewer3.py
# (py38) C:\..\py3> python appImageViewer3.py gul4.bmp

_appFileName = "appImageViewer3"
_author = "Karl Skretting, UiS" 
_version = "2020.11.12"

import sys
import os.path
#from math import hypot, pi, atan2, cos, sin    # sqrt, cos, sin, tan, log, ceil, floor 
import numpy as np
import cv2

try:
	from PyQt5.QtCore import Qt, QPoint, QT_VERSION_STR  
	from PyQt5.QtGui import QImage, QPixmap, QTransform, QColor
	from PyQt5.QtWidgets import (QApplication, QAction, QFileDialog, QLabel, QGraphicsPixmapItem, QColorDialog, QInputDialog)
except ImportError:
	raise ImportError("%s: Requires PyQt5." % _appFileName)
#end try, import PyQt5 classes

from appImageViewer2 import myPath, MainWindow as inheritedMainWindow 
from clsHoughCirclesDialog import HoughCirclesDialog

# define 17 colorNames as in: https://doc.qt.io/qt-5/qcolor.html 
colorNames17 = ['white','black','cyan','darkCyan','red','darkRed','magenta','darkMagenta',
				'green','darkGreen','yellow','darkYellow','blue','darkBlue',
				'gray','darkGray','lightGray']

class MainWindow(inheritedMainWindow):  
	"""MainWindow class for this image viewer is inherited from another image viewer."""
	
# Two initialization methods used when an object is created
	def __init__(self, fName="", parent=None):
		# print("File %s: (debug) first line in __init__()" % _appFileName)
		super().__init__(fName, parent)# use inherited __init__ with extension as follows
		#
		# set appFileName as it should be, it is set wrong in super()...
		self.appFileName = _appFileName 
		if (not self.pixmap.isNull()): 
			self.setWindowTitle("%s : %s" % (self.appFileName, fName))
		else:
			self.setWindowTitle(self.appFileName)
		# 
		self.view.rubberBandRectGiven.connect(self.meanColorEnd)  
		# signal is already connected to cropEnd (appImageViewer1), now connected to two slots!
		self.meanColorActive = False  
		#
		self.initMenu3()
		self.setMenuItems3()
		# print("File %s: (debug) last line in __init__()" % _appFileName)
		return
	#end function __init__
	
	def initMenu3(self):
		"""Initialize Color and Dice menu."""
		# print("File %s: (debug) first line in initMenu3()" % _appFileName)
		a = self.qaCheck = QAction('Check color', self)
		a.triggered.connect(self.checkColor)
		a.setToolTip("Check image and activate menu item accordingly")
		a = self.qaSwapRandB = QAction('Swap red and blue', self)
		a.triggered.connect(self.swapRandB)
		a.setToolTip("Change from RGB to BGR or opposite")
		a = self.qaEditColors = QAction('Edit custom colors', self)
		a.triggered.connect(self.editColors)
		a.setToolTip("Edit list of custom colors (in QColorDialog)")
		a = self.qaMeanColor = QAction('Add mean color', self)
		a.triggered.connect(self.meanColorStart)
		a.setToolTip("Add average color from image rectangle to custom color list")
		a = self.qaClearColors = QAction('Clear custom colors', self)
		a.triggered.connect(self.clearColors)
		a.setToolTip("Clear custom color list, i.e. set all to 'white'")
		a = self.qaSetColors = QAction('Set custom colors', self)
		a.triggered.connect(self.setColors)
		a.setToolTip("Set custom color list, i.e. set all to standard colors.")
		#
		a = self.qaDistColorRGB = QAction('Distance to RGB color', self)
		a.triggered.connect(self.distColorRGB)
		a.setToolTip("Make a gray scale image by distance to a selected RGB color")
		a = self.qaBestDistColorRGB = QAction('Distance to closest RGB color', self)
		a.triggered.connect(self.bestDistColorRGB)
		a.setToolTip("Make a gray scale image by distance to a closest custom RGB color")
		a = self.qaAttractColorRGB = QAction('Attract to closest RGB color', self)
		a.triggered.connect(self.attractColorRGB)
		a.setToolTip("Attract image to a custom RGB colors")
		#
		a = self.qaFindCircles = QAction('Find circles', self)
		a.triggered.connect(self.findCircles)
		a.setToolTip("Find circles using cv2.HoughCircles(..)")
		a = self.qaFindDices = QAction('Find dices', self)
		a.triggered.connect(self.findDices)
		a.setToolTip("Find dices (TODO)")
		a = self.qaFindEyes = QAction('Find dice eyes', self)
		a.triggered.connect(self.findEyes)
		a.setToolTip("Find eyes for each dice (TODO)")
		#
		colorMenu = self.mainMenu.addMenu("Color")
		colorMenu.addAction(self.qaCheck)
		colorMenu.addAction(self.qaSwapRandB)
		colorMenu.addAction(self.qaEditColors)
		colorMenu.addAction(self.qaMeanColor)
		colorMenu.addAction(self.qaClearColors)
		colorMenu.addAction(self.qaSetColors)
		colorMenu.addAction(self.qaDistColorRGB)
		colorMenu.addAction(self.qaBestDistColorRGB)
		colorMenu.addAction(self.qaAttractColorRGB)
		colorMenu.setToolTipsVisible(True)
		#
		diceMenu = self.mainMenu.addMenu("Dice")
		diceMenu.addAction(self.qaFindCircles)
		diceMenu.addAction(self.qaFindDices)
		diceMenu.addAction(self.qaFindEyes)
		diceMenu.setToolTipsVisible(True)
		return
	#end function initMenu3
	
# Some methods that may be used by several of the menu actions
	def setMenuItems3(self):
		"""Enable/disable menu items as appropriate."""
		pixmapOK = ((not self.pixmap.isNull()) and isinstance(self.curItem, QGraphicsPixmapItem))
		#
		self.qaCheck.setEnabled(True)   
		self.qaSwapRandB.setEnabled(pixmapOK)   
		self.qaEditColors.setEnabled(True)
		self.qaMeanColor.setEnabled(pixmapOK and (not self.meanColorActive))
		self.qaClearColors.setEnabled(True)
		self.qaSetColors.setEnabled(True)
		self.qaDistColorRGB.setEnabled(pixmapOK)
		self.qaBestDistColorRGB.setEnabled(pixmapOK)
		self.qaAttractColorRGB.setEnabled(pixmapOK)
		self.qaFindCircles.setEnabled(pixmapOK)   # and self.isAllGray ?
		self.qaFindDices.setEnabled(pixmapOK)   
		self.qaFindEyes.setEnabled(pixmapOK)   
		return
		
# Methods for actions on the Color-menu
	def checkColor(self):
		"""Check colors for image and set menu items according to active image."""
		self.setIsAllGray()   # check color state on self.image (=self.pixmap, and usually also self.npImage)
		self.setMenuItems()
		self.setMenuItems3()
		return 
		
	def swapRandB(self):
		"""Swap red and blue component of RGB or BGR image 
		or swap black and white for gray scale image.
		Have no 'undo' here as doing the operation once more undo these changes, 
		thus 'undo' will undo previous operation as before.
		"""
		# self.prevPixmap = self.pixmap   # to include 'undo', which is not needed here
		if (len(self.npImage.shape) == 3) and (self.npImage.shape[2] >= 3):
			B = cv2.cvtColor(self.npImage, cv2.COLOR_BGR2RGBA)
			self.np2image2pixmap(B, numpyAlso=True)
			self.setWindowTitle("%s : image where Red and Blue components are swapped" % self.appFileName)
		else:
			print('swapRandB: npImage is not an RGB/BGR image, swap black and white.')
			B =  255 - self.npImage
			self.np2image2pixmap(B, numpyAlso=True)
			self.setWindowTitle("%s : image where black and white are swapped" % self.appFileName)
		#
		self.setMenuItems()   # prevPixmap may have changed from None to pixmap
		return
		
	def editColors(self):
		"""Set colors for the custom color list in QColorDialog by simply calling it."""
		newColor = QColorDialog.getColor(title="You may now add or change custom colors.")
		if newColor.isValid():
			print(f"selctColor(): selected color from dialog box is {newColor.name()}.")
			print(f"  There are {QColorDialog.customCount()} colors in the custom color list:")
			for cNo in range(QColorDialog.customCount()):
				print(f"  {QColorDialog.customColor(cNo).name()}", end="")
			print(" ")
		else:
			print("selctColor(): no valid color, [Cancel] button pressed.")
		#
		return
	
	def meanColorStart(self):
		"""Set crop active and turn rubber band on."""
		self.meanColorActive = True
		self.view.rubberBandActive = True
		self.setMenuItems3()  # meanColor should be weak on Color menu
		print("%s.MainWindow.meanColorStart() started" % _appFileName)
		return
	
	def meanColorEnd(self, rectangle):
		"""Find mean color using the input rectangle (in image pixels) on the current pixmap .
		
		The mean color replaces the first 'white' color in QColorDialog custom color list.
		This function does not change the image.
		"""
		def intRGB(rgb):
			# (r,g,b) = intRGB(rgb)
			r = min(max(0,int(rgb[0]+0.5)),255)
			g = min(max(0,int(rgb[1]+0.5)),255)
			b = min(max(0,int(rgb[2]+0.5)),255)
			return (r,g,b)
		#
		if not self.meanColorActive: 
			return
		#
		self.meanColorActive = False
		verbose = True
		if verbose:
			print("%s.MainWindow.findColorEnd() started" % _appFileName)
		#
		(x0, y0) = (rectangle.x(), rectangle.y())
		(w, h) = (rectangle.width(), rectangle.height())
		(x1, y1) = (x0+w, y0+h)
		# note shape for self.npImage:              [    y,    x, bgra --> rgb]  # self.npImage.shape[2] == 4
		np_rect = np.float32(np.reshape(self.npImage[y0:y1,x0:x1,[2,1,0]], (w*h, 3)))
		if verbose and isinstance(np_rect, np.ndarray):  # the last test here should always be true
			print(f"  'npImage'  is ndarray of {self.npImage.dtype.name}, shape: {str(self.npImage.shape)}")
			print(f"  (x0,y0) = ({x0},{y0}),  (x1,y1) = ({x1},{y1}),  (w,h) = ({w},{h})  w*h = {w*h}")
			print(f"  'np_rect'  is ndarray of {np_rect.dtype.name}, shape: {str(np_rect.shape)}")
		#
		rgb = np.mean(np_rect, axis=0)  # ndarray
		if verbose:
			print("  mean color for rectangle is (rgb) = (%5.1f, %5.1f, %5.1f)" % (rgb[0], rgb[1], rgb[2]))
			print("  mean color for rectangle is (rgb) = (%3i,   %3i,   %3i  )" % intRGB(rgb))
		#
		for cNo in range(QColorDialog.customCount()):
			if QColorDialog.customColor(cNo) == QColor('white'):
				(r,g,b) = intRGB(rgb)
				QColorDialog.setCustomColor(cNo, QColor(r,g,b))
				break
		#
		self.setMenuItems3()  # meanColor should be turned back on
		#
		return
	
	def clearColors(self):
		"""Clear all custom colors, i.e. set each to 'white'"""
		for cNo in range(QColorDialog.customCount()):
			if not (QColorDialog.customColor(cNo) == QColor('white')):
				QColorDialog.setCustomColor(cNo, QColor('white'))
		return
		
	def setColors(self):
		"""Set all custom colors, i.e. set each to standard color"""
		for cNo in range(min(QColorDialog.customCount(),16)):
			QColorDialog.setCustomColor(cNo, QColor(colorNames17[cNo+1]))
		return
		
	def distColorRGB(self):
		"""Make binary image by testing if pixel color is close to a selected RGB color.
		Distance is measured for each pixel p with color (r,g,b) to selected color (R,G,B) as
		   d = max(abs(r-R), abs(g-G), abs(b-B)), 
		where d is pixel value for resulting gray scale image
		"""
		color = QColorDialog.getColor(title="Select the color to measure distance to.")
		if color.isValid():
			print(f"  selected color is {color.name()}")
			rgb = [color.red(), color.green(), color.blue()]
			A = self.npImage.astype(np.float32)   # active image
			if (len(A.shape) > 2) and (A.shape[2] >= 3): 
				A = A[:,:,[2,1,0]]  # bgr(a) --> rgb
				B = np.ones(shape=A.shape, dtype=np.float32)  # the one color image
				B[:,:,:] = rgb  
				D = np.max(np.abs(A-B),axis=2).astype(np.uint8)  # the distance image
				del B
			else:
				grayLevel = int((color.red() + color.green() + color.blue() + 0.5)/3)
				print(f"Color '{color.name()}' = ({color.red()}, {color.green()}, {color.blue()}) has grayLevel {grayLevel}")
				D = np.abs(A - grayLevel).astype(np.uint8)
				del grayLevel
			#
			print(f"  'A'  is ndarray of {A.dtype.name}, shape: {str(A.shape)}")
			print(f"  'D'  is ndarray of {D.dtype.name}, shape: {str(D.shape)}, max: {D.max()}")
			#
			self.prevPixmap = self.pixmap   
			self.np2image2pixmap(D, numpyAlso=True)
			self.setWindowTitle(f"{self.appFileName} distColorRGB(): distance to color {color.name()}")
			self.checkColor()
			#
		return
			# A possibility could do to threshold the gray scale image here, perhaps as below
			# # could try different values here, like threshold in toBinary()
			# (dist,ok) = QInputDialog.getInt(self,    # parent
					# f"approxColor(): input distance to '{color.name()}'",  # title
					# "Give allowed distance for each color component",      # label
					# value=25, min=0, max=255)
			# print(f"QInputDialog.getInt(..) returned  'dist' = {dist},  'ok' = {ok}")
			# if ok:
				# # oldPixmap = self.prevPixmap
				# self.prevPixmap = self.pixmap   
				# B = 255*(D <= dist).astype(np.uint8)
				# self.np2image2pixmap(B, numpyAlso=True)
				# self.setWindowTitle(f"{self.appFileName} approxColor(): binary image for color {color.name()}")
				# self.setIsAllGray()
			# else:
				# pass  # 
				# # self.pixmap = self.prevPixmap   # restore 
				# # self.pixmap2image2np()
				# # self.prevPixmap = oldPixmap
			#
		
	def bestDistColorRGB(self):
		"""Make binary image by testing if pixel color is close to one of custom RGB color.
		Distance is measured for each pixel p with color (r,g,b) to custom colors (Ri,Gi,Bi) as
		   d = min_i max(abs(r-Ri), abs(g-Gi), abs(b-Bi)),   i in range(nofCustomColors)
		where d is pixel value for resulting gray scale image
		"""
		color = QColorDialog.getColor(title="Check the colors to measure distance to.")
		# but we don't use the returned color, does [Cancel] give not Valid color?
		if color.isValid():  
			# print(f"  selected color is {color.name()}")
			A = self.npImage.astype(np.float32)   # active image
			if (len(A.shape) > 2) and (A.shape[2] >= 3): 
				A = A[:,:,[2,1,0]]  # bgr(a) --> rgb
			D = 255*np.ones((A.shape[0], A.shape[1]), dtype=np.uint8)
			for cNo in range(QColorDialog.customCount()):
				color = QColorDialog.customColor(cNo)
				if not (color == QColor('white')):
					rgb = [color.red(), color.green(), color.blue()]
					grayLevel = int((color.red() + color.green() + color.blue() + 0.5)/3)
					if (len(A.shape) > 2) and (A.shape[2] >= 3): 
						B = np.ones(shape=A.shape, dtype=np.float32)  # the one color image
						B[:,:,:] = rgb  
						Di = np.max(np.abs(A-B),axis=2).astype(np.uint8)  # the distance image
						del B
					else:
						Di = np.abs(A - grayLevel).astype(np.uint8)
					#end if
					D = np.minimum(D,Di)
				#end if
			#end for
			print(f"  'A'  is ndarray of {A.dtype.name}, shape: {str(A.shape)}")
			print(f"  'D'  is ndarray of {D.dtype.name}, shape: {str(D.shape)}, max: {D.max()}")
			#
			self.prevPixmap = self.pixmap   
			self.np2image2pixmap(D, numpyAlso=True)
			self.setWindowTitle(f"{self.appFileName} image after bestDistColorRGB()")
			self.checkColor()
		return
		
	def attractColorRGB(self):
		"""Make image by assigning colors to closest of custom RGB colors."""
		color = QColorDialog.getColor(title="Check the colors to attract image towards.")
		# but we don't use the returned color, does [Cancel] give not Valid color?
		if color.isValid():  
			# print(f"  selected color is {color.name()}")
			A = self.npImage.astype(np.float32)   # active image
			if (len(A.shape) > 2) and (A.shape[2] >= 3): 
				A = A[:,:,[2,1,0]]  # bgr(a) --> rgb
			D = 255*np.ones((A.shape[0], A.shape[1], QColorDialog.customCount()), dtype=np.uint8)   # 3D-array
			for cNo in range(QColorDialog.customCount()):
				color = QColorDialog.customColor(cNo)
				if not (color == QColor('white')):
					rgb = [color.red(), color.green(), color.blue()]
					grayLevel = int((color.red() + color.green() + color.blue() + 0.5)/3)
					if (len(A.shape) > 2) and (A.shape[2] >= 3): 
						B = np.ones(shape=A.shape, dtype=np.float32)  # the one color image
						B[:,:,:] = rgb  
						D[:,:,cNo] = np.max(np.abs(A-B),axis=2).astype(np.uint8)  # the distance images
						del B
					else:
						D[:,:,cNo] = np.abs(A - grayLevel).astype(np.uint8)
					#end if
				#end if
			#end for
			print(f"  'A'  is ndarray of {A.dtype.name}, shape: {str(A.shape)}")
			print(f"  'D'  is ndarray of {D.dtype.name}, shape: {str(D.shape)}, max: {D.max()}")
			(distLimit,ok) = QInputDialog.getInt(self,    # parent
					f"attractColorRGB(): input 'distLimit'",  # title
					"Give limit distance for attracted color (else 'white')",      # label
					value=25, min=0, max=255)
			print(f"QInputDialog.getInt(..) returned  'distLimit' = {distLimit},  'ok' = {ok}")
			if ok:
				B = 255*np.ones((A.shape[0], A.shape[1], 3), dtype=np.uint8)
				minD = D.min(axis=2)  # distance to closest color
				I = D.argmin(axis=2)  # indexes for best color
				for cNo in range(QColorDialog.customCount()):
					color = QColorDialog.customColor(cNo)
					if not (color == QColor('white')):
						idx = np.logical_and(I==cNo, minD<=distLimit)
						print(f"  set {np.count_nonzero(idx)} pixels to color {color.name()}")
						B[idx,0] = color.blue()   # numpy array is BGR!
						B[idx,1] = color.green()
						B[idx,2] = color.red()
					#end if
				#end for
				print(f"  'B'  is ndarray of {B.dtype.name}, shape: {str(B.shape)}")
				#
				self.prevPixmap = self.pixmap   
				self.np2image2pixmap(B, numpyAlso=True)
				self.setWindowTitle(f"{self.appFileName} image after attractColorRGB()")
				self.checkColor()
			#end ok
		return
		
# Methods for actions on the Dice-menu
	def prepareHoughCirclesA(self):
		"""Make self.A the gray scale image to detect circles in"""
		if (self.A.size == 0):
			# make self.A a gray scale image
			if (len(self.npImage.shape) == 3):
				if (self.npImage.shape[2] == 3):
					self.A = cv2.cvtColor(self.npImage, cv2.COLOR_BGR2GRAY )
					self.B = self.npImage.copy()
				elif (self.npImage.shape[2] == 4):
					self.A = cv2.cvtColor(self.npImage, cv2.COLOR_BGRA2GRAY )
					self.B = cv2.cvtColor(self.npImage, cv2.COLOR_BGRA2BGR )   
				else:
					print("prepareHoughCircles(): numpy 3D image is not as expected. --> return")
					return
				#end
			elif (len(self.npImage.shape) == 2):
				self.A = self.npImage.copy()
				self.B = cv2.cvtColor(self.npImage, cv2.COLOR_GRAY2BGR )   
			else:
				print("prepareHoughCircles(): numpy image is not as expected. --> return")
				return
			#end
		return
		
	def prepareHoughCirclesB(self):
		"""Make self.B the BGR image to draw detected circles in"""
		if (len(self.npImage.shape) == 3):
			if (self.npImage.shape[2] == 3):
				self.B = self.npImage.copy()
			elif (self.npImage.shape[2] == 4):
				self.B = cv2.cvtColor(self.npImage, cv2.COLOR_BGRA2BGR )   
			#end
		elif (len(self.npImage.shape) == 2):
			self.B = cv2.cvtColor(self.npImage, cv2.COLOR_GRAY2BGR )   
		#end
		return
		
	def tryHoughCircles(self, t):
		"""Simply display results for the parameters given in tuple 't', without committing."""
		(dp, minDist, param1, param2, minRadius, maxRadius, maxCircles) = t
		print("tryHoughCircles(): now called using:")
		print(f"t = (dp={dp}, minDist={minDist}, param1={param1}, param2={param2}, minRadius={minRadius}, maxRadius={maxRadius})")
		#
		self.prepareHoughCirclesA()  # check self.A
		C = cv2.HoughCircles(self.A, cv2.HOUGH_GRADIENT, dp=dp, minDist=minDist,
					param1=param1, param2=param2, minRadius=minRadius, maxRadius=maxRadius)
		#draw circles on B
		self.prepareHoughCirclesB()  # make self.B
		if C is not None:
			C = np.int16(np.around(C))
			print(f"  'C'  is ndarray of {C.dtype.name}, shape: {str(C.shape)}")
			print(f"  Found {C.shape[1]} circles with radius from {C[0,:,2].min()} to {C[0,:,2].max()}")
			for i in range(min(maxCircles, C.shape[1])):
				(x,y,r) = ( C[0,i,0], C[0,i,1], C[0,i,2] )  # center and radius
				cv2.circle(self.B, (x,y), r, (255, 0, 255), 2) # and circle outline 
				print(f"  circle {i} has center in ({x},{y}) and radius {r}")
			#end for
		#end if
		self.np2image2pixmap(self.B, numpyAlso=False)   # note: self.npImage is not updated
		return
	
	def findCircles(self):
		"""Find circles in active image using HoughCircles(..)."""
		oldPixmap = self.prevPixmap  
		self.prevPixmap = self.pixmap
		self.A = np.array([])  
		self.prepareHoughCirclesA()  # make self.A
		#find circles, note that HoughCirclesDialog is in another file: clsHoughCirclesDialog.py
		d = HoughCirclesDialog(self, title="Select parameters that locate the dice eyes") 
		(dp, minDist, param1, param2, minRadius, maxRadius, maxCircles) = d.getValues()   # display dialog and return values
		if d.result():
			C = cv2.HoughCircles(self.A, cv2.HOUGH_GRADIENT, dp=dp, minDist=minDist,
					param1=param1, param2=param2, minRadius=minRadius, maxRadius=maxRadius)
			#draw circles on B
			self.prepareHoughCirclesB()  # make self.B
			if C is not None:
				C = np.int16(np.around(C))
				print(f"  'C'  is ndarray of {C.dtype.name}, shape: {str(C.shape)}")
				for i in range(min(maxCircles, C.shape[1])):
					(x,y,r) = ( C[0,i,0], C[0,i,1], C[0,i,2] )  # center and radius
					# cv2.circle(self.B, (x,y), 1, (0, 100, 100), 3)  # indicate center 
					cv2.circle(self.B, (x,y), r, (255, 0, 255), 3) # and circle outline 
				#end for
			#
			#finish
			self.A = np.array([])  
			self.np2image2pixmap(self.B, numpyAlso=True)
			self.B = np.array([])  
			self.setWindowTitle(f"{self.appFileName} indicate found circles.")
			self.checkColor()
		else:
			self.pixmap = self.prevPixmap   # restore 
			self.pixmap2image2np()
			self.prevPixmap = oldPixmap
		#end if
		return
		
	def findDices(self):
		"""Find dices in active image using ??."""
		print("This function is not ready yet.")
		print("Different approaches may be used, here we sketch one alternative that may (or may not) work.")
		#
		# -- your code may be written in between the comment lines below --
		# find dices by looking for large rectangles (squares) in the image matching each color
		# each color can be a small set of color point that can be loaded into custom color list
		# for each color (point set)
		#	find distance to this color (point set) and threshold
		#	perhaps morphological operations on this binary image, erode and dilate
		#	find large area (and check it is almost square)
		#	(to find eyes too, the number of same size black wholes inside the square could be found)
		#	print results, or indicate it on image
		#
		return
		
	def findEyes(self):
		"""Find how many eyes each dice has using ??."""
		print("This function is not ready yet.")
		print("Different approaches may be used, here we sketch one alternative that may (or may not) work.")
		#
		# -- your code may be written in between the comment lines below --
		# Make image into a gray scale image, either by straightforward BGR2GRAY or
		#   by distance to all relevant dice colors, (2-4 sample points for each)
		# Threshold this gray scale image by an appropriate value
		# Find many circles (eyes) of appropriate size in this binary image
		# for each circle
		#	find colors for each circle at 0.8*radius and 1.2*radius, ex. 36 points for [0,10,..,350] degrees
		#	check if the circle is an eye of an dice and the color of the dice
		#	print results (and store it)
		# Finally, print or indicate it on image how many eyes there are for each dice
		#
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
		if os.path.isfile(fn):
			mainWin = MainWindow(fName=fn)
		else:
			mainWin = MainWindow()
	else:
		mainWin = MainWindow()
	mainWin.show()
	sys.exit(mainApp.exec_())
	