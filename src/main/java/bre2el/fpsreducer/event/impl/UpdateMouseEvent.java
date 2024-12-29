package bre2el.fpsreducer.event.impl;

import bre2el.fpsreducer.event.orbit.Cancellable;

public class UpdateMouseEvent extends Cancellable {
    private static final UpdateMouseEvent INSTANCE = new UpdateMouseEvent();

    public static UpdateMouseEvent get() {
        return INSTANCE;
    }
}

