package bre2el.fpsreducer.util;

import java.awt.Color;

public class ColorUtil {
    public static int interpolateInt(int n, int n2, double d) {
        return (int)ColorUtil.interpolate(n, n2, (float)d);
    }

    public static Color TwoColoreffect(Color color, Color color2, double d, double d2) {
        int n = (int)(((double)System.currentTimeMillis() / d + d2) % 360.0);
        n = (n >= 180 ? 360 - n : n) * 2;
        return ColorUtil.interpolateColorC(color, color2, (float)n / 360.0f);
    }

    public static double interpolate(double d, double d2, double d3) {
        return d + (d2 - d) * d3;
    }

    public static float[] colorToFloatArray(int n) {
        float f = (float)(n >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(n >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(n & 0xFF) / 255.0f;
        return new float[]{f, f2, f3};
    }

    public static Color interpolateColorC(Color color, Color color2, float f) {
        f = Math.min(1.0f, Math.max(0.0f, f));
        return new Color(ColorUtil.interpolateInt(color.getRed(), color2.getRed(), f), ColorUtil.interpolateInt(color.getGreen(), color2.getGreen(), f), ColorUtil.interpolateInt(color.getBlue(), color2.getBlue(), f), ColorUtil.interpolateInt(color.getAlpha(), color2.getAlpha(), f));
    }
}
