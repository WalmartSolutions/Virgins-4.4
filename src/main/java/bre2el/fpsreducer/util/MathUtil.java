package bre2el.fpsreducer.util;

import java.util.concurrent.ThreadLocalRandom;

public class MathUtil {
    public static double clamp(double value, double min, double max) {
        return !(value < min) ? (!(value > max) ? value : max) : min;
    }

    public static float interpolate(float n, float n3, float n2) {
        return n - (n - n3) * findMiddleValue(n2, 0.0F, 1.0F);
    }

    public static int getRandomInt(int from, int to) {
        return from < to ? ThreadLocalRandom.current().nextInt(from, to + 1) : from;
    }

    public static float getRandomFloat(float from, float to) {
        return !(from >= to) ? ThreadLocalRandom.current().nextFloat(from, to + 1.0F) : from;
    }

    public static double lerp(float start, double delta, double end) {
        int var5 = (int)Math.ceil(Math.abs(end - delta) * (double)start);
        return !(delta < end) ? Math.max(delta - (double)var5, end) : Math.min(delta + (double)var5, end);
    }

    public static float findMiddleValue(float f3, float f, float f2) {
        return !(f3 < f) ? Math.min(f3, f2) : f;
    }
}