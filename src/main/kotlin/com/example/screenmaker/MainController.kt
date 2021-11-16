package com.example.screenmaker

import javafx.fxml.FXML
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.control.*
import javafx.scene.image.ImageView
import javafx.scene.image.WritableImage
import javafx.scene.image.WritablePixelFormat
import javafx.scene.layout.BorderPane
import javafx.scene.layout.StackPane
import java.awt.Image
import java.awt.Rectangle
import java.awt.Robot
import java.awt.Toolkit
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import javax.imageio.ImageIO

class MainController {

    @FXML
    private lateinit var main: BorderPane

    @FXML
    private lateinit var menuBar: MenuBar

    @FXML
    private lateinit var openMI: MenuItem

    @FXML
    private lateinit var saveMI: MenuItem

    @FXML
    private lateinit var saveAsMI: MenuItem

    @FXML
    private lateinit var closeMI: MenuItem

    @FXML
    private lateinit var delaySlider: Slider

    @FXML
    private lateinit var minimizeCheckBox: CheckBox

    @FXML
    private lateinit var screenshotButton: Button

    @FXML
    private lateinit var colorPicker: ColorPicker

    @FXML
    private lateinit var eraserCheckBox: CheckBox

    @FXML
    private lateinit var brushSizeSlider: Slider

    @FXML
    private lateinit var imgContainer: StackPane

    @FXML
    private lateinit var img: ImageView

    @FXML
    private lateinit var canvas: Canvas

    lateinit var gc: GraphicsContext

    fun initialize() {
        gc = canvas.graphicsContext2D
    }

    @FXML
    private fun onScreenshotButtonClicked() {
        try {
            val robot = Robot()
            val fileName = "/Users/a1/Desktop/screenshots/screenshotapp" + LocalDateTime.now().toString() + ".jpg"

            val screenSize = Toolkit.getDefaultToolkit().screenSize
            val captureRect = Rectangle(0, 0, screenSize.width, screenSize.height)
            val screenFullImage = robot.createScreenCapture(captureRect)
            val format = WritablePixelFormat.getByteBgraInstance()
            val buffer = ByteArray(captureRect.width * captureRect.height * 4)

            ImageIO.write(screenFullImage, "jpg", File(fileName))

            val newImg = WritableImage(captureRect.width, captureRect.height)
            newImg.pixelWriter.setPixels(
                0, 0, captureRect.width, captureRect.height,
                format, buffer, 0, captureRect.width * 4
            )

            img.image = newImg
            img.fitWidth = newImg.width
            img.fitHeight = newImg.height
            canvas.widthProperty().bind(img.fitWidthProperty())
            canvas.heightProperty().bind(img.fitHeightProperty())

            imgContainer.prefHeightProperty().bind(img.fitHeightProperty())
            imgContainer.prefWidthProperty().bind(img.fitWidthProperty())

        } catch (ex: IOException) {
            print(ex)
        }
    }

}