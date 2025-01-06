package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.opencv.core.Core;

import java.io.IOException;

public class HelloApplication extends Application {


    static {

        // Set the path to OpenCV's native libraries (DLL files)
        System.setProperty("java.library.path", "C:\\Program Files\\opencv\\build\\java\\x64");

        // Load the OpenCV native library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Print OpenCV version to confirm it loaded correctly
        System.out.println("OpenCV version: " + Core.VERSION);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Gustavo A. Maimoun Java Capstone Project");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}