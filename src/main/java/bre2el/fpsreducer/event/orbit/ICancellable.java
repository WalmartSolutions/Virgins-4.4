package bre2el.fpsreducer.event.orbit;

public interface ICancellable {
    void setCancelled(boolean var1);

    default void cancel() {
        this.setCancelled(true);
    }

    boolean isCancelled();
}
