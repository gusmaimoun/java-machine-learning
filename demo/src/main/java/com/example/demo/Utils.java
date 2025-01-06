package com.example.demo;

import javafx.scene.image.Image;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class Utils {

    // Converts OpenCV Mat to JavaFX Image
    public static Image mat2Image(Mat frame) {
        // Convert Mat to BufferedImage
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".bmp", frame, matOfByte);  // Encode the Mat into a byte array

        byte[] byteArray = matOfByte.toArray();  // Convert Mat to byte array
        InputStream in = new ByteArrayInputStream(byteArray);

        return new Image(in);
    }
}
