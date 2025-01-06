package com.example.demo;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

public class CameraFeed {
    private VideoCapture camera;
    private ImageView imageView;

    public CameraFeed(ImageView imageView) {
        this.imageView = imageView;
        this.camera = new VideoCapture(0); // Use default camera
    }

    public void startCamera() {
        if (!camera.isOpened()) {
            System.out.println("Error: Camera not found!");
            return;
        }

        // Start capturing frames using an AnimationTimer to periodically update the UI
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                Mat frame = new Mat();
                if (camera.read(frame)) {
                    // Convert Mat frame to Image and update ImageView
                    Image image = matToImage(frame);
                    imageView.setImage(image);
                }
            }
        }.start();
    }

    public void stopCamera() {
        camera.release();
    }

    // Convert OpenCV Mat object to JavaFX Image
    private Image matToImage(Mat mat) {
        // Convert the Mat to BufferedImage (you'll need to use OpenCV's `Imgcodecs`)
        // The code here will convert Mat to Image, depending on your setup.
        // If you're using OpenCV with JavaFX, you may need to use Imgcodecs to load
        // image bytes to a BufferedImage and then wrap it into an Image.

        // For simplicity, we'll use a dummy Image here for example purposes.
        return new Image("file:/path/to/image.jpg"); // Replace this with actual code
    }
}