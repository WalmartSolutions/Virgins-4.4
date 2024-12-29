package bre2el.fpsreducer.feature.module.modules.combat;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.event.impl.UpdateMouseEvent;
import bre2el.fpsreducer.event.orbit.EventHandler;
import bre2el.fpsreducer.feature.module.Module;
import bre2el.fpsreducer.feature.module.Module.Category;
import bre2el.fpsreducer.feature.module.setting.KeyBindSetting;
import bre2el.fpsreducer.feature.module.setting.KeySetting;
import bre2el.fpsreducer.feature.module.setting.Setting;
import bre2el.fpsreducer.util.EntityUtil;
import bre2el.fpsreducer.util.RotationUtil;
import bre2el.fpsreducer.util.TimerUtil;
import bre2el.fpsreducer.util.RotationManager.Rotation;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.MathHelper;

public class SideKnockback extends Module {
    public KeySetting side;
    public boolean activated;
    public TimerUtil timer;
    public static byte[] bf;
    public KeyBindSetting bind;
    public static String[] be;

    public static void bI() {
        bf = new byte[16];
        bf[11] = 118;
        bf[12] = 99;
        bf[14] = -62;
        bf[2] = 104;
        bf[6] = 48;
        bf[7] = 2;
        bf[0] = 11;
        bf[9] = 27;
        bf[5] = -24;
        bf[8] = 121;
        bf[13] = -39;
        bf[10] = -50;
        bf[1] = -100;
        bf[15] = -42;
        bf[3] = -97;
        bf[4] = 70;
    }

    static {
        bI();
        bH();
    }

    public static String bG(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{-50, 0, -66, 21, -96, -42, 28, 93, -74, 24, 39, 97, 25, -23, 23, -23};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 134, 256);
        SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] var8 = var7.generateSecret(var6).getEncoded();
        SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
        Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var10.init(2, var9, new IvParameterSpec(var2));
        byte[] var11 = var10.doFinal(var5);
        return new String(var11, "UTF-8");
    }

    @EventHandler
    void onUpdateMouse(UpdateMouseEvent event) {
        if (!nullCheck()) {
            if (!this.activated) {
                if (this.timer.hasReached(100.0) && !this.activated && this.side.isActive(this.side.getKey())) {
                    this.activated = true;
                }
            } else {
                this.timer.reset();
                PlayerEntity var2 = EntityUtil.findClosestPlayer(PlayerEntity.class, 3.0F);
                if (var2 == null) {
                    this.activated = false;
                } else {
                    Rotation var3 = RotationUtil.getDirection(Main.mc.player, var2.getBoundingBox().getBottomCenter());
                    float var4 = (float)RotationUtil.getAngleToLookVecByRot(
                            Main.mc.player.getYaw(), Main.mc.player.getPitch(), var2.getBoundingBox().getBottomCenter()
                    );
                    float var5 = var4 * 0.01F;
                    float var6 = MathHelper.lerpAngleDegrees(var5, Main.mc.player.getYaw(), var3.getYaw());
                    float var7 = MathHelper.lerpAngleDegrees(var5, Main.mc.player.getPitch(), var3.getPitch());
                    Main.mc.player.setYaw(var6);
                    Main.mc.player.setPitch(var7);
                    if (Main.mc.crosshairTarget instanceof EntityHitResult var8 && var8.getEntity() == var2
                            || RotationUtil.getHitResult(Main.mc.player, false, RotationUtil.getRotation().getYaw(), RotationUtil.getRotation().getPitch()) instanceof EntityHitResult var9
                            && var9.getEntity() == var2) {
                        Main.mc.interactionManager.attackEntity(Main.mc.player, var2);
                        Main.mc.player.swingHand(Hand.MAIN_HAND);
                        this.activated = false;
                    }
                }
            }
        }
    }

    @Override
    public void onDisable() {
        this.activated = false;
        super.onDisable();
    }

    public static void bH() {
        try {
            be = new String[10];
            be[0] = bG("QXc9Ud0D7vbMVjp2a5ZMKgucaJ9G6DACeRvOdmPZwtYzvlSf2ax8mKmjcYOk0eoO", "�Ζ�<%", bf);
            be[9] = bG("JglrKU1/rxK+zEf/jW2dBQucaJ9G6DACeRvOdmPZwtadvrnjGUd5azHC5Xz/4upoRErs3SYVAvJvVF4QoWzjow==", "��y��X", bf);
            be[6] = bG("b+S47sl96zq5qoKJHti+NAucaJ9G6DACeRvOdmPZwta0B+xF4du9mbAD8YHWm5ryJD59YxmSmjW8r6lFa9+3HA==", "\u0007����$", bf);
            be[7] = bG("1QnaYd02VWLdt8e8v7/OUwucaJ9G6DACeRvOdmPZwtYXz/sytXPvqoGgoeksM5uspWy9tNAlL8Bl0DN9AuxJ5w==", "�\r9�-b", bf);
            be[2] = bG("orasn06QpSwu79kznztnogucaJ9G6DACeRvOdmPZwtYSw1ywn9LxqpIlQXsXT7ez4jsorXwGV3r5aaqBNV9wPg==", "\u0001�6�\u0000�", bf);
            be[5] = bG("ORmpoj381bs3iWqLKvF4yAucaJ9G6DACeRvOdmPZwtbQJYdbwaGuEKA2cHrQkAl+", "�\u000e��_�", bf);
            be[8] = bG("K1UArpjVtzzUxJ7P+O37GQucaJ9G6DACeRvOdmPZwtaYO7/PkcXMzvIgPZdrQZD/", "��kC\u0019�", bf);
            be[4] = bG("MAzwqJr9pBAL87XDxgJp+QucaJ9G6DACeRvOdmPZwtZ8Ib8Q7XSeym82xmZ/Bi7qYhlxGPqRI2+B6d1cAUEq2Q==", "ܺ\u000f��n", bf);
            be[3] = bG("EIe37KrvColzDAY2nxkACQucaJ9G6DACeRvOdmPZwtaIhvXS1pcG+8PvVaZO6VJh", "�T�>�~", bf);
            be[1] = bG("0aMLoR/eilUXSyY2lr+SrwucaJ9G6DACeRvOdmPZwtbbyolwwP0bBaF3gvFQuBHhdzxbeGkJ58tCkJ6hv46www==", "0>\u0005V\u001eB", bf);
        } catch (Exception e) {
        }
    }

    public SideKnockback() {
        super(be[5], be[6], Category.Combat);
        this.bind = new KeyBindSetting(be[7], 0, false);
        this.side = new KeySetting(be[8], be[9], 0);
        this.timer = new TimerUtil();
        this.addSettings(new Setting[]{this.bind, this.side});
    }
}
