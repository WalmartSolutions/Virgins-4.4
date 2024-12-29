package bre2el.fpsreducer.util;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.feature.module.modules.utility.AntiBot;
import bre2el.fpsreducer.feature.module.modules.utility.Friends;
import bre2el.fpsreducer.feature.module.modules.utility.Teams;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.MaceItem;
import net.minecraft.item.SwordItem;

public class PlayerUtil {
    public static boolean weaponCheck() {
        Item var0 = Main.mc.player.getMainHandStack().getItem();
        return var0 instanceof SwordItem || var0 instanceof AxeItem || var0 instanceof MaceItem;
    }

    public static float getReachDistance() {
        return 3.0F;
    }

    public static boolean validateTarget(PlayerEntity entity) {
        return !AntiBot.isBot(entity) && !Teams.isTeam(entity) && !Friends.isFriend(entity);
    }
}
