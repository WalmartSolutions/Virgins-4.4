package bre2el.fpsreducer.event.impl;

import bre2el.fpsreducer.event.orbit.Cancellable;
import net.minecraft.client.gui.DrawContext;

public class RenderHUDEvent extends Cancellable {
    public float tickDelta;
    public static RenderHUDEvent INSTANCE = new RenderHUDEvent();
    public DrawContext context;

    public static RenderHUDEvent get(DrawContext context, float tickDelta) {
        INSTANCE.context = context;
        INSTANCE.tickDelta = tickDelta;
        return INSTANCE;
    }
}
