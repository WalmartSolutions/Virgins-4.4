package bre2el.fpsreducer.event.orbit;

public class Cancellable implements ICancellable {
    public boolean cancelled = false;

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }
}
