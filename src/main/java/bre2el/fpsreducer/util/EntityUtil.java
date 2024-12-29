package bre2el.fpsreducer.util;

import bre2el.fpsreducer.client.Main;
import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.TntMinecartEntity;

public class EntityUtil {
    public static TntMinecartEntity getCart(float f) {
        for (Entity entity : Main.mc.world.getEntities()) {
            if (!(entity instanceof TntMinecartEntity) || !(entity.distanceTo((Entity)Main.mc.player) <= f)) continue;
            return (TntMinecartEntity)entity;
        }
        return null;
    }

    public static <T extends Entity> T findClosestPlayer(Class<T> clazz, float f) {
        for (Entity entity : Main.mc.world.getEntities()) {
            if (!clazz.isAssignableFrom(entity.getClass()) || entity.equals((Object)Main.mc.player) || !(entity.distanceTo((Entity)Main.mc.player) <= f)) continue;
            return (T)entity;
        }
        return null;
    }
}
