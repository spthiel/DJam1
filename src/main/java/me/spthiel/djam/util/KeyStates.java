package me.spthiel.djam.util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyStates implements KeyListener {

    private static boolean[] pressed = new boolean[256];

    public static boolean isPressed(int keycode) {
        return pressed[keycode];
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        pressed[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressed[e.getKeyCode()] = false;
    }
}
