package com.example.demo;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class MainUIController {
    private Button startButton;
    private Button pauseButton;
    private Button stopButton;
    private VBox mainUI;

    public MainUIController() {
        startButton = new Button("Start Session");
        pauseButton = new Button("Pause Session");
        stopButton = new Button("Stop Session");

        // Initialize button actions
        startButton.setOnAction(e -> startSession());
        pauseButton.setOnAction(e -> pauseSession());
        stopButton.setOnAction(e -> stopSession());

        // Arrange buttons in a layout
        mainUI = new VBox(10, startButton, pauseButton, stopButton);
    }
    public VBox getUI() {
        return mainUI;
    }

    private void startSession() {
        // Start capturing screenshots in a session
    }

    private void pauseSession() {
        // Pause the current screenshot session
    }

    private void stopSession() {
        // Stop the current session and save document
    }
}
