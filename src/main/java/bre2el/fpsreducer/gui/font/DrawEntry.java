package bre2el.fpsreducer.gui.font;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;

public class DrawEntry {
    public float r;
    public float g;
    public Glyph toDraw;
    public float atX;
    public float b;
    public float atY;
    public float elementCodec;

    public float g() {
        return this.g;
    }

    public float atY() {
        return this.atY;
    }

    public float r() {
        return this.r;
    }

    public Glyph toDraw() {
        return this.toDraw;
    }

    public float atX() {
        return this.atX;
    }

    public DrawEntry(float f, float f2, float f3, float f4, float f5, Glyph glyph) {
        this.atX = f;
        this.atY = f2;
        this.r = f3;
        this.g = f4;
        this.elementCodec = f5;
        this.toDraw = glyph;
    }

    public float b() {
        return this.elementCodec;
    }
}
