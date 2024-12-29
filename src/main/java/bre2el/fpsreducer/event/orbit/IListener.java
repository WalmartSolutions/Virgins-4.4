package bre2el.fpsreducer.event.orbit;

public interface IListener {
    int getPriority();

    void call(Object var1);

    Class<?> getTarget();

    boolean isStatic();
}
