package bre2el.fpsreducer.feature.module.modules.movement;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.event.impl.UpdateEvent.Pre;
import bre2el.fpsreducer.event.orbit.EventHandler;
import bre2el.fpsreducer.feature.module.Module;
import bre2el.fpsreducer.feature.module.Module.Category;
import bre2el.fpsreducer.feature.module.setting.KeyBindSetting;
import bre2el.fpsreducer.feature.module.setting.Setting;
import bre2el.fpsreducer.mixin.ClientPlayerEntityAccessor;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class Sprint extends Module {
    public static String[] fc;
    public KeyBindSetting bind;
    public static byte[] fd;

    public static void hE() {
        try {
            fc = new String[6];
            fc[2] = hD("MX9RqbY40Q+obixClacvu3zQLc4O+qGshcDYrh6JFjqtA3I74rZqbrJlMmhiCRR1ggm+DjH7BQJBmCh8NjwebA==", "\\\u0014�e\u0016�", fd);
            fc[1] = hD("YoTzGLWHahUohe/37/19V3zQLc4O+qGshcDYrh6JFjoqkDsEXsP+JNy0oiTyXKJ8KAp0+wFaxiIMIm0gCECjOw==", "��S�k", fd);
            fc[3] = hD("kROrPvLPVff0EOfUx7GE5XzQLc4O+qGshcDYrh6JFjpB4uUKloTn2o/xRotSVHXn", "���\u0007 ", fd);
            fc[5] = hD("caPyuxBM5kKgtbDexz4JP3zQLc4O+qGshcDYrh6JFjrNopoy3OSq+UARxjXtClj0MR1lc39xD0bd6yj4XY53DQ==", "F��/�U", fd);
            fc[4] = hD("w6TxWFVMVa/BNd2+qXRuHHzQLc4O+qGshcDYrh6JFjrFxYXAdMz9ZCMrRn9RaUJrLCyXqFdFy2reJ29/Al/MpA==", "��Q��P", fd);
            fc[0] = hD("36L2+BgmsRUQ7r8/cMKVSnzQLc4O+qGshcDYrh6JFjpQWIHXIGfbbxXN6i5UyEpt", "jg�G�\u000b", fd);
        } catch (Exception e) {

        }
    }

    public static String hD(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{0, -61, 3, 72, 67, 35, -104, -98, 126, -42, 100, 8, -125, 38, -61, 25};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 174, 256);
        SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] var8 = var7.generateSecret(var6).getEncoded();
        SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
        Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var10.init(2, var9, new IvParameterSpec(var2));
        byte[] var11 = var10.doFinal(var5);
        return new String(var11, "UTF-8");
    }

    static {
        hF();
        hE();
    }

    public Sprint() {
        super(fc[3], fc[4], Category.Movement);
        this.bind = new KeyBindSetting(fc[5], 0, false);
        this.addSettings(new Setting[]{this.bind});
    }

    public static void hF() {
        fd = new byte[16];
        fd[13] = -119;
        fd[7] = -84;
        fd[3] = -50;
        fd[8] = -123;
        fd[12] = 30;
        fd[11] = -82;
        fd[10] = -40;
        fd[1] = -48;
        fd[5] = -6;
        fd[6] = -95;
        fd[15] = 58;
        fd[4] = 14;
        fd[14] = 22;
        fd[9] = -64;
        fd[2] = 45;
        fd[0] = 124;
    }

    @EventHandler
    void onUpdate(Pre event) {
        if (!nullCheck()) {
            Main.mc.player.setSprinting(this.shouldSprint());
        }
    }

    public boolean shouldSprint() {
        return !Main.mc.player.isTouchingWater() && !Main.mc.player.isSubmergedInWater()
                ? Main.mc.player.forwardSpeed > 1.0E-5F
                && ((ClientPlayerEntityAccessor)Main.mc.player).invokeCanSprint()
                && (!Main.mc.player.horizontalCollision || Main.mc.player.collidedSoftly)
                && (!Main.mc.player.isTouchingWater() || Main.mc.player.isSubmergedInWater())
                : false;
    }
}
