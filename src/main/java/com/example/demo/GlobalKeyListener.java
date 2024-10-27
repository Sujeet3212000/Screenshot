package com.example.demo;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class GlobalKeyListener implements NativeKeyListener {
    private ScreenshotApp screenshotApp;

    public GlobalKeyListener(ScreenshotApp app) {
        this.screenshotApp = app;
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (screenshotApp.isSessionActive() &&
                e.getKeyCode() == NativeKeyEvent.VC_SPACE &&
                (e.getModifiers() & NativeKeyEvent.CTRL_MASK) != 0) {
            screenshotApp.captureScreenshot();
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {}

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {}
}