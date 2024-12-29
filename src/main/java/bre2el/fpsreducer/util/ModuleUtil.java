package bre2el.fpsreducer.util;

public class ModuleUtil {
    public TimerUtil timerUtil = new TimerUtil();
    public boolean enabled;

    public boolean isEnabled() {
        return this.enabled;
    }

    public void stop(int milliseconds) {
        if (this.timerUtil.hasReached((double)milliseconds)) {
            this.enabled = false;
            this.timerUtil.reset();
        }
    }

    public void setEnabled() {
        this.enabled = true;
        this.timerUtil.reset();
    }

    public TimerUtil getTimerUtil() {
        return this.timerUtil;
    }
}
