package bre2el.fpsreducer.event.impl;

import bre2el.fpsreducer.event.orbit.Cancellable;

public class MouseButtonEvent extends Cancellable {
    public int button;
    public int action;
    public static MouseButtonEvent INSTANCE = new MouseButtonEvent();

    public static MouseButtonEvent get(int button, int action) {
        INSTANCE.setCancelled(false);
        INSTANCE.button = button;
        INSTANCE.action = action;
        return INSTANCE;
    }
}