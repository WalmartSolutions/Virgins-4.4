package bre2el.fpsreducer.util;

import net.minecraft.entity.player.PlayerEntity;

public class HealthUtil {
    public static int sortHealth(PlayerEntity playerEntity, PlayerEntity playerEntity2) {
        boolean bl;
        boolean bl2 = playerEntity != null;
        boolean bl3 = bl = playerEntity2 != null;
        if (!bl2 && !bl) {
            return 0;
        }
        if (bl2 && !bl) {
            return 1;
        }
        if (!bl2) {
            return -1;
        }
        return Float.compare(HealthUtil.getHealth(playerEntity), HealthUtil.getHealth(playerEntity2));
    }

    public static float getHealth(PlayerEntity playerEntity) {
        return playerEntity.getHealth();
    }
}
