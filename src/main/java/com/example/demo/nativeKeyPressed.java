package com.example.demo;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;

public interface nativeKeyPressed {
    void nativeKeyPressed(NativeKeyEvent e);

    void nativeKeyReleased(NativeKeyEvent e);

    void nativeKeyTyped(NativeKeyEvent e);
}
