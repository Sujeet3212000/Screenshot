package com.example.demo;

import javafx.scene.control.Alert;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;

public class ScreenshotService {

    private final List<File> screenshots = new ArrayList<>();
    private int screenshotCount = 0;

    public void captureScreenshot() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Screenshot Taken");
        alert.setHeaderText("Screenshot Captured!");
        alert.setContentText("Total screenshots: " + screenshotCount);
        alert.showAndWait();
        try {
            Robot robot = new Robot();
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage screenCapture = robot.createScreenCapture(screenRect);
            File file = new File("screenshot_" + screenshotCount++ + ".png");
            ImageIO.write(screenCapture, "png", file);
            System.out.println("Screenshot saved: " + file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetScreenshots() {
        screenshotCount = 0;
    }

}
