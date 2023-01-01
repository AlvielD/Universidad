#!/usr/bin/env python
# -*- coding: utf-8 -*-
#
# ../ELE610/py3/appSimpleImageViewer.py
#
#  Simple program that uses Qt to display an image, only basic options.
#    File menu: Open File, and Quit
#    Scale menu: Scale Up, and Scale down
#  No status line at bottom.
#
# Karl Skretting, UiS, September - November 2018, November 2020

# Example on how to use file:
# (C:\...\Anaconda3) C:\..\py3> activate py38
# (py38) C:\..\py3> python appSimpleImageViewer.py

_appFileName = "appSimpleImageViewerj"
_author = "Álvaro Esteban Muñoz & Nourane Bouzad" 
_version = "2021.00.01"

import sys
import numpy as np
import cv2
import qimage2ndarray
from PyQt5.QtCore import (QT_VERSION_STR, QRect, Qt)
from PyQt5.QtGui import QPixmap, QTransform, QImage
from PyQt5.QtWidgets import (QApplication, QWidget, QMainWindow,
		QGraphicsScene, QGraphicsView, QGraphicsPixmapItem, QAction, QFileDialog, QInputDialog)
try:
	from myTools import DBpath # my DropBox
	myPath = DBpath( 'm610' )  # path where KS have some images
except:
	myPath = './images'                # path where you may have some images
#end try

class MainWindow(QMainWindow):
	"""MainWindow class for a simple image viewer."""
	def __init__(self, parent = None):
		"""Initialize the main window object with title, location and size,
		an empty image (pixmap), empty scene and empty view
		"""
		super().__init__(parent)
		self.setWindowTitle('Simple Image Viewer')
		self.setGeometry(150, 50, 1400, 800)  # initial window position and size
		#
		self.curItem = None
		self.pixmap = QPixmap()  # a null pixmap
		self.scene = QGraphicsScene()
		self.view = QGraphicsView(self.scene, parent=self)
		self.view.setGeometry(0, 20, self.width(), self.height()-20) 
		self.initMenu()
		#
		return
	
	def initMenu(self):
		"""Set up the menu for main window: File with Open and Quit, Scale with + and -."""
		qaOpenFile = QAction('openFile', self)
		qaOpenFile.setShortcut('Ctrl+O')
		qaOpenFile.setStatusTip('Open (image) File using dialog box')
		qaOpenFile.triggered.connect(self.openFile)
		qaSaveFile = QAction('saveFile', self)
		qaSaveFile.setShortcut('Ctrl+S')
		qaSaveFile.setStatusTip('Save image to a file')
		qaSaveFile.triggered.connect(self.saveFile)
		qaCloseWin = QAction('closeWin', self)
		qaCloseWin.setShortcut('Ctrl+Q')
		qaCloseWin.setStatusTip('Close and quit program')
		qaCloseWin.triggered.connect(self.closeWin)
		qaScaleUp = QAction('scaleUp', self)
		qaScaleUp.setShortcut('Ctrl++')
		qaScaleUp.triggered.connect(self.scaleUp)
		qaScaleDown = QAction('scaleDown', self)
		qaScaleDown.setShortcut('Ctrl+-')
		qaScaleDown.triggered.connect(self.scaleDown)
		qaCropImg = QAction('cropImage', self)
		qaCropImg.setShortcut('Ctrl+R')
		qaCropImg.setStatusTip('Crop image to a selected area')
		qaCropImg.triggered.connect(self.cropImg)
		qaGrayScale = QAction('grayScale', self)
		qaGrayScale.setShortcut('Ctrl+G')
		qaGrayScale.setStatusTip('Turn the image into a gray scale image')
		qaGrayScale.triggered.connect(self.grayScale)
		#
		mainMenu = self.menuBar()
		fileMenu = mainMenu.addMenu('&File')
		fileMenu.addAction(qaOpenFile)
		fileMenu.addAction(qaCloseWin)
		fileMenu.addAction(qaSaveFile)
		scaleMenu = mainMenu.addMenu('&Scale')
		scaleMenu.addAction(qaScaleUp)
		scaleMenu.addAction(qaScaleDown)
		editMenu = mainMenu.addMenu('&Edit')
		editMenu.addAction(qaCropImg)
		editMenu.addAction(qaGrayScale)
		return
	
# Methods for File menu
	def openFile(self):   
		"""Use the Qt file open dialog to select an image to open as a pixmap, 
		The pixmap is added as an item to the graphics scene which is shown in the graphics view.
		The view is scaled to unity.
		"""
		options = QFileDialog.Options()
		options |= QFileDialog.DontUseNativeDialog     # make dialog appear the same on all systems
		flt = "All jpg files (*.jpg);;All bmp files (*.bmp);;All png files (*.png);;All files (*)"
		(fName, used_filter) = QFileDialog.getOpenFileName(parent=self, caption="Open image file", 
			directory=myPath, filter=flt, options=options)
		#
		if (fName != ""):
			if self.curItem: 
				self.scene.removeItem(self.curItem)
				self.curItem = None
			#end if
			self.pixmap.load(fName)
			# If the file does not exist or is of an unknown format, the pixmap becomes a null pixmap.
			if self.pixmap.isNull():
				self.setWindowTitle('Image Viewer (error for file %s)' % fName)
				self.view.setGeometry( 0, 20, self.width(), self.height()-20 ) 
			else: # ok
				self.curItem = QGraphicsPixmapItem(self.pixmap)
				self.scene.addItem(self.curItem)
				self.setWindowTitle('Image Viewer: ' + fName)
				self.view.setTransform(QTransform())  # identity (for scale)
			#end if
		#end if
		return
		
	def closeWin(self):            
		"""Quit program."""
		print("Close the main window and quit program.")
		self.close()   
		return

	def saveFile(self):
		options = QFileDialog.Options()

		flt = "All jpg files (*.jpg);;All bmp files (*.bmp);;All png files (*.png);;All files (*)"
		(fName, used_filter) = QFileDialog.getSaveFileName(self, caption="Save image file as", 
			directory=myPath, filter=flt, options=options)

		if (fName != ""):
			if self.pixmap.save(fName):
				print(f"Saved image into file {fName}")
			else:
				print("Failed to save the image")

		return
	
# Methods for Scale menu
	def scaleUp(self):
		"""Scale up the view by factor 2"""
		if not self.pixmap.isNull():
			self.view.scale(2,2)  
		return
		
	def scaleDown(self):
		"""Scale down the view by factor 0.5"""
		if not self.pixmap.isNull():
			self.view.scale(0.5,0.5)  
		return

# Methods for Edit menu
	def cropImg(self):

		# Ask for the parameters to crop the image
		x = QInputDialog.getInt(self, 'Crop area', 'Introduce x coordinate for the left top corner of the cropped area')
		y = QInputDialog.getInt(self, 'Crop area', 'Introduce y coordinate for the left top corner of the cropped area')
		height = QInputDialog.getInt(self, 'Crop area', 'Introduce height for the area to crop')
		width = QInputDialog.getInt(self, 'Crop area', 'Introduce widht for the area to crop')

		# Create the new form for the image
		rect = QRect(x[0], y[0], width[0], height[0])
		if not self.pixmap.isNull():
			# Clear the scene
			self.scene.removeItem(self.curItem)
			self.curItem = None

			# Copy the original image in the cropping rectangle
			cropped = self.pixmap.copy(rect)

			# Set the cropped image as the actual image
			self.pixmap = cropped
			self.curItem = QGraphicsPixmapItem(cropped)
			self.scene.addItem(self.curItem)

		return

	def grayScale(self):

		if not self.pixmap.isNull():
			image = self.pixmap.toImage()

			npImage = qimage2ndarray.rgb_view(image)
			npImage = cv2.cvtColor(npImage, cv2.COLOR_RGB2GRAY)
			image = qimage2ndarray.array2qimage(npImage)

			self.pixmap = QPixmap.fromImage(image)
			self.scene.removeItem(self.curItem)
			self.curItem = QGraphicsPixmapItem(self.pixmap)
			self.scene.addItem(self.curItem)

		return

	
# methods for 'slots'
	def resizeEvent(self, arg1):
		"""Make the size of the view follow any changes in the size of the main window.
		This method is a 'slot' that is called whenever the size of the main window changes.
		"""
		self.view.setGeometry( 0, 20, self.width(), self.height()-20 ) 
		return
#end class MainWindow
		
if __name__ == '__main__':
	print("%s: (version %s), path for images is: %s" % (_appFileName, _version, myPath))
	print("%s: Using Qt %s" % (_appFileName, QT_VERSION_STR))
	mainApp = QApplication(sys.argv)
	mainWin = MainWindow()
	mainWin.show()
	sys.exit(mainApp.exec_())
	
