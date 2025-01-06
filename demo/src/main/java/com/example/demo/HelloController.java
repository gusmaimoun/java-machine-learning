package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

public class HelloController {

    private CascadeClassifier faceCascade = new CascadeClassifier();
    private int absoluteFaceSize = 0;
    private VideoCapture capture;
    private Mat frame = new Mat();
    private boolean cameraActive = false;

    @FXML private CheckBox haarClassifier;
    @FXML private CheckBox lbpClassifier;
    @FXML private Button cameraButton;
    @FXML private ImageView originalFrame;

    // Load classifiers for Haar and LBP
    @FXML
    private void haarSelected(ActionEvent event) {
        checkboxSelection("haarcascade_frontalface_alt.xml");
    }

    @FXML
    private void lbpSelected(ActionEvent event) {
        checkboxSelection("lbpcascade_frontalface.xml");
    }

    // Helper method for loading classifier XML files
    private void checkboxSelection(String... classifierPath) {
        for (String xmlClassifier : classifierPath) {
            // Update the paths to point to the correct location
            this.faceCascade.load("C:/Users/gusta/OneDrive/Desktop/demo/src/main/resources/lbpcascades/" + xmlClassifier);
        }
        cameraButton.setDisable(false);  // Enable the camera button
    }

    @FXML
    private void startCamera(ActionEvent event) {
        if (!cameraActive) {
            capture = new VideoCapture(0);  // Start webcam feed
            cameraActive = true;
            cameraButton.setText("Stop Camera");

            // Start a new thread to process video frames
            new Thread(() -> {
                while (cameraActive) {
                    capture.read(frame);  // Capture frame
                    if (!frame.empty()) {
                        detectAndDisplay(frame);  // Detect faces
                        // Convert frame and display it in ImageView
                        originalFrame.setImage(Utils.mat2Image(frame));  // Now using the Utils class
                    }
                }
            }).start();
        } else {
            cameraActive = false;
            capture.release();
            cameraButton.setText("Start Camera");
        }
    }

    // Method to detect and display faces on each frame
    private void detectAndDisplay(Mat frame) {
        Mat grayFrame = new Mat();
        Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2GRAY);  // Convert to grayscale
        Imgproc.equalizeHist(grayFrame, grayFrame);  // Equalize histogram for better results

        // Set the minimum face size for detection
        if (absoluteFaceSize == 0) {
            int height = grayFrame.rows();
            if (Math.round(height * 0.2f) > 0) {
                absoluteFaceSize = Math.round(height * 0.2f);  // Minimum face size is 20% of image height
            }
        }

        // Detect faces in the frame (using MatOfRect)
        MatOfRect faces = new MatOfRect();  // MatOfRect to store detected faces
        faceCascade.detectMultiScale(grayFrame, faces, 1.1, 2, 0, new Size(absoluteFaceSize, absoluteFaceSize), new Size());

        // Draw rectangles around detected faces
        Rect[] facesArray = faces.toArray();  // Convert MatOfRect to array of Rect
        for (Rect face : facesArray) {
            Imgproc.rectangle(frame, face.tl(), face.br(), new Scalar(0, 255, 0), 3);  // Draw green rectangle around face
        }
    }
}