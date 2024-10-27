package com.example.demo;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScreenshotApp extends Application implements nativeKeyPressed {
    private final ScreenshotService screenshotService = new ScreenshotService();
    private DocumentService documentService = new DocumentService();
    private boolean sessionActive = false;
    private File latestScreenshot;
    private boolean commentAdded = true;  // Tracks if a comment was added for the latest screenshot

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Screenshot Capture Application");

        // Set up JNativeHook and disable its logging
        try {
            Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
            logger.setLevel(Level.WARNING);
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener((NativeKeyListener) this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // UI components
        Button startButton = new Button("Start Session");
        Button stopButton = new Button("Stop Session");
        Button addCommentButton = new Button("Add Screenshot with Comment");
        TextField commentField = new TextField();
        commentField.setPromptText("Enter comment for the screenshot...");

        startButton.setOnAction(e -> startSession());
        stopButton.setOnAction(e -> stopSession());
        addCommentButton.setOnAction(e -> addScreenshotWithComment(commentField.getText()));

        VBox layout = new VBox(10, startButton, stopButton, commentField, addCommentButton);
        Scene scene = new Scene(layout, 400, 200);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void startSession() {
        sessionActive = true;
        System.out.println("Session started. Press Control + Space to capture screenshots.");
    }

    private void stopSession() {
        sessionActive = false;
        System.out.println("Session stopped.");
        saveDocument();
    }

    private void saveDocument() {
        // Save any unsaved screenshot without comment if applicable
        savePendingScreenshotWithoutComment();
        documentService.saveDocument("SessionDocument.docx");
        System.out.println("Document saved.");
        resetSession();
    }

    private void resetSession() {
        screenshotService.resetScreenshots();
        documentService = new DocumentService();
        latestScreenshot = null;
        commentAdded = true;
        System.out.println("Session reset. Ready for a new session.");
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        // Check for Control + Space
        if (sessionActive &&
                e.getKeyCode() == NativeKeyEvent.VC_SPACE &&
                (e.getModifiers() & NativeKeyEvent.CTRL_MASK) != 0) {
            captureScreenshot();
        }
    }

    private void captureScreenshot() {
        // Check if the previous screenshot was saved; if not, save it without a comment
        savePendingScreenshotWithoutComment();

        screenshotService.captureScreenshot();
        commentAdded = false;  // Mark as unsaved comment
        System.out.println("Screenshot captured. Ready for adding a comment.");
    }

    private void addScreenshotWithComment(String comment) {
        if (latestScreenshot != null) {
            documentService.addScreenshotToDocument(latestScreenshot, comment);
            System.out.println("Screenshot with comment added to document.");
            commentAdded = true;  // Mark as comment added
        } else {
            System.out.println("No screenshot captured yet. Press Control + Space first.");
        }
    }

    private void savePendingScreenshotWithoutComment() {
        if (latestScreenshot != null && !commentAdded) {
            // If the last screenshot was not saved with a comment, save it without a comment
            documentService.addScreenshotToDocument(latestScreenshot, "");
            System.out.println("Screenshot saved without comment.");
            commentAdded = true;  // Mark as saved without comment
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {}

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {}

    @Override
    public void stop() throws Exception {
        GlobalScreen.unregisterNativeHook();  // Unregister JNativeHook
        super.stop();
    }


    public static void main(String[] args) {
        launch();
    }
}