package bre2el.fpsreducer.feature.module.modules.combat;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.event.impl.UpdateEvent.Pre;
import bre2el.fpsreducer.event.orbit.EventHandler;
import bre2el.fpsreducer.feature.module.Module;
import bre2el.fpsreducer.feature.module.Module.Category;
import bre2el.fpsreducer.feature.module.setting.BoolSetting;
import bre2el.fpsreducer.feature.module.setting.KeyBindSetting;
import bre2el.fpsreducer.feature.module.setting.RangeSetting;
import bre2el.fpsreducer.feature.module.setting.Setting;
import bre2el.fpsreducer.mixin.ClientPlayerEntityAccessor;
import bre2el.fpsreducer.util.InputUtil;
import bre2el.fpsreducer.util.MathUtil;
import bre2el.fpsreducer.util.PlayerUtil;
import bre2el.fpsreducer.util.RotationUtil;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import net.minecraft.block.Blocks;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;

public class TriggerBot extends Module {
    public boolean hit;
    public KeyBindSetting bind;
    public BoolSetting critical;
    public BoolSetting weapon;
    public static String[] ca;
    public RangeSetting range;
    public boolean sprint;
    public static byte[] cb;
    public BoolSetting doubleClick;
    public RangeSetting cooldown;

    public static void db() {
        try {
            ca = new String[26];
            ca[12] = da("4x3g6Kjvu9Mq6EnmYhvd2tGy6mV+vwmHX88nfuqiJvEkELO+mm0n47Ht0tvXdqHvLy5B9xerWEU5hvp++bhjihkc9LnYzkGRfAiiWcNRuhk=", "/fAK��", cb);
            ca[0] = da("Z/Zyl/VV0fGyBU7fi+wxLtGy6mV+vwmHX88nfuqiJvGlu0i4cb5gvfTo29rKKtYT", "K`�Z�?", cb);
            ca[23] = da("R5kN+vOECcz2pNY7uOz2XtGy6mV+vwmHX88nfuqiJvFXnRVeyHyg73NJP182AZSo", "s����\r", cb);
            ca[21] = da("fiqQ2CpnGbhqwbPcRL6ZhNGy6mV+vwmHX88nfuqiJvGM/y/Cp94d2LE8QHN4BekFaBJgpuZfqgVkjyTJDPSiKUZyVucLTf6FeDfQGDJz7SE=", "o���\u0004�", cb);
            ca[18] = da("l5i/O53vFVu7QWoMug4EDtGy6mV+vwmHX88nfuqiJvHw0DhG8+m6V5NcbQPpQqKC", "�=D8b1", cb);
            ca[7] = da("p/HzqRczcB+fvSQ00sEjANGy6mV+vwmHX88nfuqiJvH/CtmNnn+Z9t0YL/kjoxuY", "�\u000b��\u000f\n", cb);
            ca[2] = da("JZtr2M+6Cg1hePmFucCbf9Gy6mV+vwmHX88nfuqiJvEsFwrlqcos61iVEeJcNXNiFWeOhbpe8whrxLePSOzdDw==", "s��\u0002Y7", cb);
            ca[8] = da("LstY7sZKjpRaJzhSH6o+u9Gy6mV+vwmHX88nfuqiJvE8E6uKjavuoBygYGDSxUslCUyMfFtZepQzUzwz2YdnddwHP02dJDD/Wf9lSQ/mMeA=", "�\u0015�=\u0013E", cb);
            ca[5] = da("uhXz14oP+ii4EGXOSVN3RtGy6mV+vwmHX88nfuqiJvGvbuaALjpKEdPICJpwVWSS", "�0\u001a��\u000e", cb);
            ca[14] = da("0IVc2nFaMd3cWThW4LdistGy6mV+vwmHX88nfuqiJvGCboUL7SEwt4jOMa4p1rNAryRQjluI5+x8sqCt7zB7HA==", "�gd\t\u0014�", cb);
            ca[6] = da("vyw/WLR8kI5Hmv/eigHYNtGy6mV+vwmHX88nfuqiJvEJowBDPPcghYxaHMFYfRxy", "�]S��u", cb);
            ca[20] = da("w91Jzqs02FduuxIpTqlfcdGy6mV+vwmHX88nfuqiJvE6bZuriJgLWRJYud26bJXL", "\n�z���", cb);
            ca[3] = da("UNJPeTMhZ9hryzFsUs1BINGy6mV+vwmHX88nfuqiJvH0Nrlt8hJDiprAwPNVmPuM", "A��q\u0014A", cb);
            ca[15] = da("8TyHEx0IU33QS6g0gqJc8dGy6mV+vwmHX88nfuqiJvG8oc4x5t9wlHIfKz95U6io8NAfhuZyJm3VpeoNvKn9KA==", "\u0002'�a]�", cb);
            ca[10] = da("4u0f8Isf97I2FJwNY22zd9Gy6mV+vwmHX88nfuqiJvE3c4+AJLmmmBk4mfHB0SdA", "žlF\u0001\u001b", cb);
            ca[24] = da("USKkMv/NoFcsqAtP/ua7qNGy6mV+vwmHX88nfuqiJvE0fqQl0Zhx49FvR7uDpIhZ", "�\u0012&��\u0017", cb);
            ca[25] = da("Jyi953BsvX7oNmint7NlINGy6mV+vwmHX88nfuqiJvF5moxHBNhaD5iPdBs6gijx2uRAmpyYf1DhuwDn0E1jDaiqLMzKx+InC73GGJsoxbs=", "�� �ݒ", cb);
            ca[13] = da("tl+1gWh38xMa2t5AXhlgxdGy6mV+vwmHX88nfuqiJvEeGX/wHx2+I51Dr2cXRmOE", "\u001b��\u0001�-", cb);
            ca[16] = da("Eu5B34vP3ojC03wuMx6rmtGy6mV+vwmHX88nfuqiJvHKlJg+pFgVY8fVOeQ0kIbW", "�&�5.�", cb);
            ca[19] = da("Btdtplfn7EC2KYdfz5jh99Gy6mV+vwmHX88nfuqiJvEyk63JYYsVukmUSl3+WU23", "�v�YI\u000f", cb);
            ca[11] = da("tZ8jdKGNSSvLB6DVXon1VdGy6mV+vwmHX88nfuqiJvEbJgJXs9Mm76Gea0sKegos", "�\u001c�;Qg", cb);
            ca[22] = da("y0fpCQeW1/svIpGSrYvg/dGy6mV+vwmHX88nfuqiJvEKa0KYVrF0lOS7g9yD4u6x", "H��Ij�", cb);
            ca[9] = da("KR1lK7OxA3Xn7n9C4Bwl2dGy6mV+vwmHX88nfuqiJvHlidCDy/slvFupJdzppBEb", "BR ���", cb);
            ca[4] = da("hg04rJBInOvPPIEQNgpcDdGy6mV+vwmHX88nfuqiJvExFag2FkLghVlnFIKjQlk/k1N7pGZCNThOm8db5vzn1A==", "���|c�", cb);
            ca[17] = da("bvfghD3wwOReu6trYVETz9Gy6mV+vwmHX88nfuqiJvEdD+h2WCSj7W5jMunQqQzm3aMcKne3kAocf5WK6+NCTg==", "�\u001a@�2�", cb);
            ca[1] = da("h3NG3cRPQ5y9MngE7cGyltGy6mV+vwmHX88nfuqiJvGghgDSQRoRgLNJEfUal2cDsKJ8kv6PD8t1T5o9SnFkbQ==", "̐���\u0011", cb);
        } catch (Exception e) {
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

    public List<BlockPos> getCobweb() {
        ArrayList var1 = new ArrayList();
        if (nullCheck()) {
            return var1;
        } else {
            ClientWorld var2 = Main.mc.world;
            BlockPos var3 = Main.mc.player.getBlockPos();
            int var4 = (int)Math.ceil(1.5);

            for (int var5 = -var4; var5 <= var4; var5++) {
                for (int var6 = -var4; var6 <= var4; var6++) {
                    double var7 = (double)(var5 * var5 + var6 * var6);
                    if (!(var7 > 2.25)) {
                        for (int var9 = -2; var9 <= 2; var9++) {
                            BlockPos var10 = var3.add(var5, var9, var6);
                            if (var2.getBlockState(var10).getBlock() == Blocks.COBWEB) {
                                var1.add(var10);
                            }
                        }
                    }
                }
            }

            return var1;
        }
    }

    public static String da(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{101, -85, -25, -120, 10, 43, -46, 40, -45, 0, 6, -20, -40, -45, -117, -53};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 123, 256);
        SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] var8 = var7.generateSecret(var6).getEncoded();
        SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
        Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var10.init(2, var9, new IvParameterSpec(var2));
        byte[] var11 = var10.doFinal(var5);
        return new String(var11, "UTF-8");
    }

    static {
        dc();
        db();
    }

    public static void dc() {
        cb = new byte[16];
        cb[0] = -47;
        cb[13] = -94;
        cb[11] = 126;
        cb[12] = -22;
        cb[5] = -65;
        cb[15] = -15;
        cb[14] = 38;
        cb[8] = 95;
        cb[9] = -49;
        cb[7] = -121;
        cb[2] = -22;
        cb[4] = 126;
        cb[3] = 101;
        cb[1] = -78;
        cb[6] = 9;
        cb[10] = 39;
    }

    @EventHandler
    public void onUpdate(Pre event) {
        if (!nullCheck()) {
            this.setDetail(this.cooldown.getDefaultValue() + "-" + this.cooldown.getSecondValue());
            if (this.hit) {
                this.hit = false;
                InputUtil.callMouse(0, 0);
            }

            if (this.sprint) {
                Main.mc.player.setSprinting(this.shouldSprint());
                this.attack();
                this.sprint = false;
            } else {
                if (Main.mc.crosshairTarget instanceof EntityHitResult var3
                        && var3.getEntity() instanceof PlayerEntity var4
                        && var4.isAlive()
                        && PlayerUtil.validateTarget(var4)) {
                    if (this.weapon.isEnabled() && !PlayerUtil.weaponCheck()) {
                        return;
                    }

                    if (this.critical.isEnabled() && !Main.mc.player.isOnGround() && Main.mc.player.fallDistance == 0.0F) {
                        if (Main.mc.player.isInsideWaterOrBubbleColumn() || Main.mc.player.isInLava() || !this.getCobweb().isEmpty() && this.getCobweb() != null) {
                            if (this.critical.isEnabled() && Main.mc.player.isSprinting()) {
                                Main.mc.player.setSprinting(false);
                                this.sprint = true;
                                return;
                            }

                            if ((double)Main.mc.player.distanceTo(var4) >= this.range.getDefaultValue()
                                    && (double)Main.mc.player.distanceTo(var4) <= this.range.getSecondValue()) {
                                this.attack();
                            }
                        }

                        return;
                    }

                    if ((double)Main.mc.player.distanceTo(var4) >= this.range.getDefaultValue()
                            && (double)Main.mc.player.distanceTo(var4) <= this.range.getSecondValue()) {
                        this.attack();
                    }

                    return;
                }

                if (RotationUtil.getHitResult(Main.mc.player, false, RotationUtil.getRotation().getYaw(), RotationUtil.getRotation().getPitch()) instanceof EntityHitResult var7
                        && var7.getEntity() instanceof PlayerEntity var8
                        && var8.isAlive()
                        && PlayerUtil.validateTarget(var8)) {
                    if (this.weapon.isEnabled() && !PlayerUtil.weaponCheck()) {
                        return;
                    }

                    if (this.critical.isEnabled() && !Main.mc.player.isOnGround() && Main.mc.player.fallDistance == 0.0F) {
                        if (Main.mc.player.isInsideWaterOrBubbleColumn() || Main.mc.player.isInLava() || !this.getCobweb().isEmpty() && this.getCobweb() != null) {
                            if (this.critical.isEnabled() && Main.mc.player.isSprinting()) {
                                Main.mc.player.setSprinting(false);
                                this.sprint = true;
                                return;
                            }

                            if ((double)Main.mc.player.distanceTo(var8) >= this.range.getDefaultValue()
                                    && (double)Main.mc.player.distanceTo(var8) <= this.range.getSecondValue()) {
                                this.attack();
                            }
                        }

                        return;
                    }

                    if ((double)Main.mc.player.distanceTo(var8) >= this.range.getDefaultValue()
                            && (double)Main.mc.player.distanceTo(var8) <= this.range.getSecondValue()) {
                        this.attack();
                    }
                }
            }
        }
    }

    public TriggerBot() {
        super(ca[13], ca[14], Category.Combat);
        this.bind = new KeyBindSetting(ca[15], 0, false);
        this.cooldown = new RangeSetting(ca[16], ca[17], 90.0, 100.0, 0.0, 150.0, 1.0, 30.0);
        this.range = new RangeSetting(ca[18], ca[19], 0.0, 3.0, 0.0, 6.0, 0.1, 6.0);
        this.weapon = new BoolSetting(ca[20], ca[21], false);
        this.doubleClick = new BoolSetting(ca[22], ca[23], false);
        this.critical = new BoolSetting(ca[24], ca[25], false);
        this.addSettings(new Setting[]{this.bind, this.cooldown, this.range, this.weapon, this.doubleClick, this.critical});
    }

    void attack() {
        if (Main.mc.player.getAttackCooldownProgress(0.0F) >= MathUtil.getRandomFloat((float)this.cooldown.getDefaultValue(), (float)this.cooldown.getSecondValue()) / 100.0F) {
            InputUtil.callMouse(0, 1);
            new Thread(() -> {
                try {
                    Thread.sleep(10L);
                } catch (InterruptedException e) {
                }
                if (this.doubleClick.isEnabled()) {
                    InputUtil.callMouse(0, 1);
                }
            }).start();
        }
    }
}
