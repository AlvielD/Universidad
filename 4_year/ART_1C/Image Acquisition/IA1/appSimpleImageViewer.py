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

_appFileName = "appSimpleImageViewer"
_author = "Karl Skretting, UiS" 
_version = "2020.11.10"

import sys
from PyQt5.QtCore import QT_VERSION_STR
from PyQt5.QtGui import QPixmap, QTransform  
from PyQt5.QtWidgets import (QApplication, QWidget, QMainWindow,
		QGraphicsScene, QGraphicsView, QGraphicsPixmapItem, QAction, QFileDialog)
try:
	from myTools import DBpath # my DropBox
	myPath = DBpath( 'm610' )  # path where KS have some images
except:
	myPath = ''                # path where you may have some images
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
		#
		mainMenu = self.menuBar()
		fileMenu = mainMenu.addMenu('&File')
		fileMenu.addAction(qaOpenFile)
		fileMenu.addAction(qaCloseWin)
		scaleMenu = mainMenu.addMenu('&Scale')
		scaleMenu.addAction(qaScaleUp)
		scaleMenu.addAction(qaScaleDown)
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
	
