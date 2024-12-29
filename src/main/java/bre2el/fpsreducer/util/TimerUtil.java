package bre2el.fpsreducer.util;

// skidded from argon leaked src btw

public class TimerUtil {
    public long lastMS;

    public long getTime() {
        return System.currentTimeMillis() - this.lastMS;
    }

    public void reset() {
        this.lastMS = this.getCurrentMS();
    }

    public boolean hasReached(double d) {
        return (double)(this.getCurrentMS() - this.lastMS) >= d;
    }

    public long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }

    public TimerUtil() {
        this.reset();
    }

    public boolean delay(float f) {
        return (float)(this.getTime() - this.lastMS) >= f;
    }
}
