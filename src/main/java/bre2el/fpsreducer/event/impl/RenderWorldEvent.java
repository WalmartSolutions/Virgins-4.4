package bre2el.fpsreducer.event.impl;

import bre2el.fpsreducer.event.orbit.Cancellable;
import net.minecraft.client.util.math.MatrixStack;

public class RenderWorldEvent extends Cancellable {
    public MatrixStack matrices;
    public float tickDelta;
    public static RenderWorldEvent INSTANCE = new RenderWorldEvent();

    public static RenderWorldEvent get(MatrixStack matrices, float tickDelta) {
        INSTANCE.matrices = matrices;
        INSTANCE.tickDelta = tickDelta;
        return INSTANCE;
    }
}