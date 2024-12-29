package bre2el.fpsreducer.feature.module.modules.utility;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.event.impl.UpdateEvent.Pre;
import bre2el.fpsreducer.event.orbit.EventHandler;
import bre2el.fpsreducer.feature.module.Module;
import bre2el.fpsreducer.feature.module.ModuleManager;
import bre2el.fpsreducer.feature.module.Module.Category;
import bre2el.fpsreducer.feature.module.setting.KeyBindSetting;
import bre2el.fpsreducer.feature.module.setting.Setting;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.Entity.RemovalReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.GameMode;

public class AntiBot extends Module {
    public KeyBindSetting bind;
    public static byte[] dX;
    public static String[] dW;

    public static String fU(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{0, 46, -34, 123, -13, -43, 108, -73, 106, 100, -22, -88, -93, -88, -60, -113};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 72, 256);
        SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] var8 = var7.generateSecret(var6).getEncoded();
        SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
        Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var10.init(2, var9, new IvParameterSpec(var2));
        byte[] var11 = var10.doFinal(var5);
        String info = new String(var11, "UTF-8");
        return info;
    }

    public static void fV() {
        try {
            dW = new String[8];
            dW[5] = fU("mI/eGp8brEfQuotGqMJxrZZuNR5bTUupxjiUC6CWfeXdRN2UM3YsD96bYFxKXNRb", "�\u0010\u0011\u0017��", dX);
            dW[2] = fU("a78Ae/Xedt3d2KfPP4UtVpZuNR5bTUupxjiUC6CWfeUEeX0LLr2aQrExlIiUYc7bHC3DC3GT0aUN1p6cK8QjSA==", "��2�\u0010\u0017", dX);
            dW[7] = fU("z7P1LlQpjw0AeTq7wJUj6JZuNR5bTUupxjiUC6CWfeWR1y1mRIxDsFQFnRHvwZJJ", "��>��\u0013", dX);
            dW[0] = fU("92Vs03pbqnI6z7mz6XWEFJZuNR5bTUupxjiUC6CWfeULLaLp8Lka9Gv1Y0zHetQZ", "��N�{�", dX);
            dW[3] = fU("od/ou+dz5hu4mWTgAanQ1pZuNR5bTUupxjiUC6CWfeXrOyPKWb+gbs23FNUCvO1m", "�����-", dX);
            dW[4] = fU("6LX6vj8yOzoznTVZoextnZZuNR5bTUupxjiUC6CWfeXdDuJ1zLzmpUGDIYnMqL18", "�\";�\u0001�", dX);
            dW[1] = fU("aGjLvlZ0daXpb82dWYXsoZZuNR5bTUupxjiUC6CWfeUPXvb9uHQViQFF8UeGtb7t", "wEn\b��", dX);
            dW[6] = fU("uzS+F3RIDtKTsf9IYQSKC5ZuNR5bTUupxjiUC6CWfeWhAMHha9MoTfTOCyHOgNhz4s8zD7xvasEbBrayEF2urA==", "\u000f���\u07bb", dX);
        } catch (Exception e) {

        }
    }

    @EventHandler
    void onUpdate(Pre event) {
        if (!nullCheck()) {
            for (Entity var3 : Main.mc.world.getEntities()) {
                if (var3 != null && var3 instanceof PlayerEntity) {
                    PlayerEntity var4 = (PlayerEntity)var3;
                    if (var3.isInvisible() && botCheck(var4)) {
                        var3.remove(RemovalReason.DISCARDED);
                    }
                }
            }
        }
    }

    public AntiBot() {
        super(dW[4], dW[5], Category.Utility);
        this.bind = new KeyBindSetting(dW[6], 0, false);
        this.addSettings(new Setting[]{this.bind});
    }

    public static boolean isBot(Entity e) {
        return !(e instanceof PlayerEntity) ? false : botCheck((PlayerEntity)e) && ModuleManager.INSTANCE.getModuleByName(dW[7]).isEnabled();
    }

    public static void fW() {
        dX = new byte[16];
        dX[14] = 125;
        dX[13] = -106;
        dX[6] = 75;
        dX[12] = -96;
        dX[15] = -27;
        dX[10] = -108;
        dX[7] = -87;
        dX[5] = 77;
        dX[0] = -106;
        dX[8] = -58;
        dX[1] = 110;
        dX[4] = 91;
        dX[11] = 11;
        dX[3] = 30;
        dX[9] = 56;
        dX[2] = 53;
    }

    static GameMode getGameMode(PlayerEntity player) {
        if (player == null) {
            return null;
        } else {
            PlayerListEntry var1 = Main.mc.getNetworkHandler().getPlayerListEntry(player.getUuid());
            return var1 == null ? null : var1.getGameMode();
        }
    }

    static {
        fW();
        fV();
    }

    static boolean botCheck(PlayerEntity entity) {
        if (getGameMode(entity) == null) {
            return true;
        } else {
            return Main.mc.getNetworkHandler().getPlayerListEntry(entity.getUuid()) == null
                    ? true
                    : Main.mc.getNetworkHandler().getPlayerListEntry(entity.getUuid()).getProfile() == null;
        }
    }
}
