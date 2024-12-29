package bre2el.fpsreducer.feature.module.modules.combat;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.event.impl.UpdateEvent.Pre;
import bre2el.fpsreducer.event.orbit.EventHandler;
import bre2el.fpsreducer.feature.module.Module;
import bre2el.fpsreducer.feature.module.Module.Category;
import bre2el.fpsreducer.feature.module.setting.KeyBindSetting;
import bre2el.fpsreducer.feature.module.setting.ModeSetting;
import bre2el.fpsreducer.feature.module.setting.RangeSetting;
import bre2el.fpsreducer.feature.module.setting.Setting;
import bre2el.fpsreducer.util.EntityUtil;
import bre2el.fpsreducer.util.InputUtil;
import bre2el.fpsreducer.util.MathUtil;
import bre2el.fpsreducer.util.PlayerUtil;
import bre2el.fpsreducer.util.RotationUtil;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.hit.EntityHitResult;

public class ShieldDisabler extends Module {
    public RangeSetting attackDelay;
    public KeyBindSetting bind;
    public boolean active;
    public RangeSetting swapDelay;
    public ModeSetting mode;
    public int attackTick;
    public boolean swapped;
    public boolean attacked;
    public int prevSlot;
    public int swapTick;
    public boolean kbstun;
    public static byte[] ep;
    public static String[] eo;

    public ShieldDisabler() {
        super(eo[14], eo[15], Category.Combat);
        this.bind = new KeyBindSetting(eo[16], 0, false);
        this.mode = new ModeSetting(eo[17], eo[18], eo[19], new String[]{eo[20], eo[21]});
        this.swapDelay = new RangeSetting(eo[22], eo[23], 0.0, 2.0, 0.0, 20.0, 1.0, 8.0);
        this.attackDelay = new RangeSetting(eo[24], eo[25], 1.0, 3.0, 0.0, 20.0, 1.0, 4.0);
        this.addSettings(new Setting[]{this.bind, this.mode, this.swapDelay, this.attackDelay});
    }

    public static void gx() {
        ep = new byte[16];
        ep[1] = -52;
        ep[3] = 53;
        ep[8] = -9;
        ep[6] = -93;
        ep[9] = -13;
        ep[2] = 36;
        ep[15] = -118;
        ep[7] = 117;
        ep[14] = 68;
        ep[11] = 35;
        ep[10] = 5;
        ep[4] = 12;
        ep[12] = -78;
        ep[0] = 65;
        ep[5] = 85;
        ep[13] = -41;
    }

    static {
        gx();
        gw();
    }

    @EventHandler
    public void onUpdate(Pre event) {
        if (!nullCheck()) {
            if (this.kbstun) {
                InputUtil.callMouse(0, 1);
                this.kbstun = false;
            }

            if (!this.active) {
                if (!this.attacked) {
                    PlayerEntity var7 = EntityUtil.findClosestPlayer(PlayerEntity.class, PlayerUtil.getReachDistance());
                    if (var7 != null && PlayerUtil.validateTarget(var7) && var7.isBlocking()) {
                        if (RotationUtil.getHitResult(Main.mc.player, false, RotationUtil.getRotation().getYaw(), RotationUtil.getRotation().getPitch()) instanceof EntityHitResult var8
                                && var8.getEntity() instanceof PlayerEntity var9
                                && PlayerUtil.validateTarget(var9)) {
                            this.active = true;
                            this.prevSlot = Main.mc.player.getInventory().selectedSlot;
                            this.swapTick = 0;
                            this.attackTick = 0;
                        }
                    }
                } else {
                    Main.mc.player.getInventory().selectedSlot = this.prevSlot;
                    this.attacked = false;
                }
            } else {
                if (!this.swapped
                        && (float)this.swapTick >= MathUtil.getRandomFloat((float)this.swapDelay.getDefaultValue(), (float)this.swapDelay.getSecondValue())) {
                    for (int var2 = 0; var2 < 9; var2++) {
                        if (Main.mc.player.getInventory().getStack(var2).getItem() instanceof AxeItem) {
                            Main.mc.player.getInventory().selectedSlot = var2;
                            this.swapTick = 0;
                            this.attackTick = 0;
                            this.swapped = true;
                        }
                    }
                }

                if (this.swapped) {
                    if ((float)this.attackTick >= MathUtil.getRandomFloat((float)this.attackDelay.getDefaultValue(), (float)this.attackDelay.getSecondValue())) {
                        InputUtil.callMouse(0, 1);
                        if (this.mode.getMode().equals(eo[26])) {
                            InputUtil.callMouse(0, 1);
                        }

                        if (this.mode.getMode().equals(eo[27])) {
                            for (int var6 = 0; var6 < 9; var6++) {
                                ItemStack var3 = Main.mc.player.getInventory().getStack(var6);
                                if (var3.getItem() instanceof SwordItem && var3.hasEnchantments()) {
                                    ItemEnchantmentsComponent var4 = EnchantmentHelper.getEnchantments(var3);
                                    if (var4.getEnchantments()
                                            .contains(Main.mc.world.getRegistryManager().get(Enchantments.KNOCKBACK.getRegistryRef()).getEntry(Enchantments.KNOCKBACK).get())) {
                                        Main.mc.player.getInventory().selectedSlot = var6;
                                        this.kbstun = true;
                                    }
                                }
                            }
                        }

                        this.active = false;
                        this.swapped = false;
                        this.attacked = true;
                    }

                    this.attackTick++;
                }

                this.swapTick++;
            }
        }
    }

    public static String gv(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{103, -128, 0, 60, 82, -32, -8, -82, 8, -73, -20, -32, 104, -15, 33, 125};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 142, 256);
        SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] var8 = var7.generateSecret(var6).getEncoded();
        SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
        Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var10.init(2, var9, new IvParameterSpec(var2));
        byte[] var11 = var10.doFinal(var5);
        return new String(var11, "UTF-8");
    }

    public static void gw() {
        try {
            eo = new String[28];
            eo[19] = gv("XThREpPLlCFS7nu6MkRVxkHMJDUMVaN19/MFI7LXRIpax/xk9QRSHb0paGtFeOvb", "���4�", ep);
            eo[15] = gv("hr8kwxSxMCehGazRSSwdDkHMJDUMVaN19/MFI7LXRIr9EHQ6JruJkk+f3eCu5Kxk31HSgoHTzi9i/EzIeHYoyA==", "\u0004�^�>:", ep);
            eo[20] = gv("reDNaglHalMq2Pqa7tLRPUHMJDUMVaN19/MFI7LXRIoE9wTyqrciTR8kCRCHU7hU", "�\u000b\u000eL��", ep);
            eo[10] = gv("1yVYkTAU9rsSVsSB31J4wUHMJDUMVaN19/MFI7LXRIqOE9TWiHwqh215S3+zKASt", "\u000bf\u001cxT\u0010", ep);
            eo[5] = gv("ip4CMQ5KIfsg1RxYreYyn0HMJDUMVaN19/MFI7LXRIr9XgvlAVSI8ftSrOGCZX8+", "���L�", ep);
            eo[7] = gv("0DKrrcZ/qdVe0alAy3m7VEHMJDUMVaN19/MFI7LXRIpMZkb6WSn1u9u1CLaIUc/6", "���FJ�", ep);
            eo[26] = gv("FgGYZJdKRmeBZg/tAijGBkHMJDUMVaN19/MFI7LXRIqSw+mATW28hNUSVjdRV5Fn", ">f�\n��", ep);
            eo[11] = gv("3JzZYr8HrmmOJOAuwT9mVkHMJDUMVaN19/MFI7LXRIphzB03nk+s4DUCkWYa5EocAD+5ADXWIfQLCxm+z+sDnw==", "�6�a�", ep);
            eo[0] = gv("ECzZYMjtghlWgqnBnZ+Q80HMJDUMVaN19/MFI7LXRIrJVTysK1QGVF4cASeYsJkM", "̤\u0000\u001f)�", ep);
            eo[9] = gv("+QUih7Zesh4gcJJf3ftAoEHMJDUMVaN19/MFI7LXRIqBtmki0RMgUjuCyVHjWLWEmnWL5hX8VOovwb88q1hfCw==", ",\\Ӄw�", ep);
            eo[1] = gv("tSINUSsBDoXAA1c5YJRzYEHMJDUMVaN19/MFI7LXRIoTN0/0rMG0XB0ecV7DSG0bHIxp8bPrGLewExYOeQ+JnQ==", "\u000b�?rj�", ep);
            eo[14] = gv("/6RqGpDWs+/S7ZToDj7RIkHMJDUMVaN19/MFI7LXRIqugNnyUHFi4aHpMnOqklz8", "�\u0014�-�`", ep);
            eo[3] = gv("7PvwTtabo5i2FNEC+62+u0HMJDUMVaN19/MFI7LXRIq74gP4qrQ3/wtIXfIACng/", "xt\u001e\u0001:�", ep);
            eo[25] = gv("QiRx6H9JDi1UhbIUdoyO7UHMJDUMVaN19/MFI7LXRIqu4rvuZ82C3m9xiqDaTVgvSreYZ3eIT/qmqPm75NNk5Q==", "�Y�\fU:", ep);
            eo[18] = gv("eqx7zs+QKo8CAt4hADi+FUHMJDUMVaN19/MFI7LXRIqLDy0HjzcJePAn/8dRq3ufjQWoZWbOzZZh4njGmwe2xw==", "H���ϲ", ep);
            eo[6] = gv("nU42lA68yNQAJaliawUn7kHMJDUMVaN19/MFI7LXRIqqoDprvbsxFImrywxnm3QE", "ܖx6*!", ep);
            eo[23] = gv("7k29FfvnafLpbNrihoGunkHMJDUMVaN19/MFI7LXRIqwQSH+VjcfT4iB3RMTIDaPEni0JeTryK4bEwEY7IH6MA==", "�0\u00147Vr", ep);
            eo[21] = gv("rpk59VTA4Mj/vX24vk5uiUHMJDUMVaN19/MFI7LXRIqD4htB8E/gCblbgM2irNuQ", "������", ep);
            eo[17] = gv("i7aKMD3VcTrYELfUmCWKCUHMJDUMVaN19/MFI7LXRIpD/UjcMpxr1rGmwgg38lcm", "�mX�u�", ep);
            eo[24] = gv("bcy/zwbDlATlOSzWTmvMFEHMJDUMVaN19/MFI7LXRIpwQEJcwY8fjQzt21s7oUfX", "�C0��\u007f", ep);
            eo[4] = gv("+fZaHGTqdPOk+mvbPo9/UUHMJDUMVaN19/MFI7LXRIoJI/Hh3yFQI/GnbDOceFf+MjBNl1u4qscjMP/9AnU9IA==", "�鰿�G", ep);
            eo[2] = gv("bUwbonahmsFz4uMGZADBnkHMJDUMVaN19/MFI7LXRIrRCAMVd2cDJs471XUKmSNn9D6BwAkM8RDnQsrl4o57Xw==", "�(�\u001dV�", ep);
            eo[27] = gv("ot+vBwwqHOaPKj3MAMRcl0HMJDUMVaN19/MFI7LXRIpjO09WezOlo0MpnynBQuAU", "�H¤$9", ep);
            eo[16] = gv("SZjfaa3ImDcdXmxDjEAzqEHMJDUMVaN19/MFI7LXRIpQuic2Gqxy6RJlpUyljaR+EebHTJskDIapfiMuY19QvQ==", "��m�-j", ep);
            eo[22] = gv("H2QeltxxJ74fBEDBea3xN0HMJDUMVaN19/MFI7LXRIpEH0GEzJz2oykrppDcR9Bo", "O���\u0010�", ep);
            eo[13] = gv("CRtExbRfpLj86iP7oERyGEHMJDUMVaN19/MFI7LXRIryYy/SZBKnQIYETrFcNjsz", "�$\u0001(�\u000f", ep);
            eo[12] = gv("4MbPuxe1b31N5C7NItQYiUHMJDUMVaN19/MFI7LXRIr9mRJJwxof0W4nLDoiBJXR", "weݒ\f�", ep);
            eo[8] = gv("vzD4z7nkkIR/lsWJg0VM50HMJDUMVaN19/MFI7LXRIo2VbfjnBNUVk+HVmQ80Lji", "\u0012$?��\u000b", ep);
        } catch (Exception e) {
        }
    }
}
