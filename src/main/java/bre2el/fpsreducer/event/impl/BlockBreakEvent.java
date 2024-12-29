package bre2el.fpsreducer.event.impl;

import bre2el.fpsreducer.event.orbit.Cancellable;

public class BlockBreakEvent extends Cancellable {
    public static class Post extends BlockBreakEvent {
        public static Post INSTANCE = new Post();

        public static Post get() {
            return INSTANCE;
        }
    }

    public static class Pre extends BlockBreakEvent {
        public static Pre INSTANCE = new Pre();

        public static Pre get() {
            return INSTANCE;
        }
    }
}
