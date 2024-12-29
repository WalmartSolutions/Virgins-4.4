package bre2el.fpsreducer.event.impl;

import bre2el.fpsreducer.event.orbit.Cancellable;

public class KeyEvent extends Cancellable {
    public static KeyEvent INSTANCE = new KeyEvent();
    public int key;
    public int action;

    public static KeyEvent get(int key, int action) {
        INSTANCE.key = key;
        INSTANCE.action = action;
        return INSTANCE;
    }
}

