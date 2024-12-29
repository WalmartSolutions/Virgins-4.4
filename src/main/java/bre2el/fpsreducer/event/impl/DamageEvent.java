package bre2el.fpsreducer.event.impl;

import bre2el.fpsreducer.event.orbit.Cancellable;
import net.minecraft.entity.damage.DamageSource;

public class DamageEvent extends Cancellable {
    public static class Post extends DamageEvent {
        public static Post INSTANCE = new Post();
        public DamageSource source;

        public static Post get(DamageSource source) {
            INSTANCE.source = source;
            return INSTANCE;
        }
    }

    public static class Pre extends DamageEvent {
        public static Pre INSTANCE = new Pre();
        public DamageSource source;

        public static Pre get(DamageSource source) {
            INSTANCE.source = source;
            return INSTANCE;
        }
    }
}
