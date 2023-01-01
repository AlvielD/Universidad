import pyueye_example_camera

from PyQt5.QtWidgets import (QWidget, QDialog, QInputDialog, QFormLayout, QLineEdit, QDialogButtonBox, QComboBox)

class QInputDialogJ(QWidget):
    """Display a dialog that asks for few parameters
    to thath will be returned
    """
    def __init__(self, mainApp):
        super().__init__()
        super().setWindowTitle("Camera Settings")
        super().resize(400, 200)

        # To call the mainApp methods
        self.mainApp = mainApp

        self.imageSize = QComboBox(self)
        self.imageSize.addItems(["640x480", "800x480", "1280x720"])
        self.imageSize.setEditable(True)
        self.imageSize.setCurrentText("1280x720")

        self.displayMode = QComboBox(self)
        self.displayMode.addItems(["DIB", "Direct3D", "OpenGL"])
        self.displayMode.setEditable(True)
        self.displayMode.setCurrentText("DIB")

        self.pixelClock = QLineEdit(self)
        self.frameRate = QLineEdit(self)
        self.buttonBox = QDialogButtonBox(QDialogButtonBox.Ok | QDialogButtonBox.Cancel, self)

        self.layout = QFormLayout(self)
        self.layout.addRow("Image Size", self.imageSize)
        self.layout.addRow("Display Mode", self.displayMode)
        self.layout.addRow("Pixel Clock", self.pixelClock)
        self.layout.addRow("Frame Rate", self.frameRate)
        self.layout.addWidget(self.buttonBox)

        self.buttonBox.accepted.connect(self.accept)
        self.buttonBox.rejected.connect(self.reject)

        return

    def accept(self):
        """Close the dialog and tell the mainApp to change options
        """
        self.close()
        returnedValue = (
            self.imageSize.currentText(),
            self.displayMode.currentIndex(), 
            self.pixelClock.text(),
            self.frameRate.text()
        )

        self.mainApp.changeOptions(returnedValue)

        return

    def reject(self):
        """Close the dialog and return None
        """
        self.close()

        return 
        