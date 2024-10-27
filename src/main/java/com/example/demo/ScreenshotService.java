package com.example.demo;

import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;


public class ScreenshotService {
    private int screenshotCount = 0;
    private TrayIcon trayIcon;

    public ScreenshotService() {
        setupTrayIcon();
    }

    private void setupTrayIcon() {
        // Check if SystemTray is supported
        if (!SystemTray.isSupported()) {
            System.err.println("System tray not supported on this platform.");
            return;
        }

        // Set up a tray icon
        SystemTray tray = SystemTray.getSystemTray();
        Image iconImage = Toolkit.getDefaultToolkit().getImage("path/to/icon.png");  // Replace with your icon path
        trayIcon = new TrayIcon(iconImage, "Screenshot App");

        trayIcon.setImageAutoSize(true);
        trayIcon.addActionListener(e -> System.out.println("Tray Icon Clicked"));

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.err.println("Error adding tray icon: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public File captureScreenshot() {
        try {
            // Set up the Robot for screen capture
            Robot robot = new Robot();
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

            // Capture the screen
            BufferedImage screenCapture = robot.createScreenCapture(screenRect);

            // Save the screenshot to file
            File file = new File("screenshot_" + screenshotCount++ + ".png");
            ImageIO.write(screenCapture, "png", file);

            // Display system tray notification
            if (trayIcon != null) {
                trayIcon.displayMessage("Screenshot Captured",
                        "Screenshot saved: " + file.getAbsolutePath(),
                        TrayIcon.MessageType.INFO);
            }

            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void resetScreenshots() {
        screenshotCount = 0;
    }
}