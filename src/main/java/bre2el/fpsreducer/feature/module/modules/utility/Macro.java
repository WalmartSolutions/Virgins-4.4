package bre2el.fpsreducer.feature.module.modules.utility;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.event.impl.UpdateMouseEvent;
import bre2el.fpsreducer.event.impl.AttackEvent.Post;
import bre2el.fpsreducer.event.impl.AttackEvent.Pre;
import bre2el.fpsreducer.event.orbit.EventHandler;
import bre2el.fpsreducer.feature.module.Module;
import bre2el.fpsreducer.feature.module.Module.Category;
import bre2el.fpsreducer.feature.module.setting.BoolSetting;
import bre2el.fpsreducer.feature.module.setting.KeyBindSetting;
import bre2el.fpsreducer.feature.module.setting.KeySetting;
import bre2el.fpsreducer.feature.module.setting.Setting;
import bre2el.fpsreducer.util.InputUtil;
import bre2el.fpsreducer.util.InventoryUtil;
import bre2el.fpsreducer.util.PlayerUtil;
import bre2el.fpsreducer.util.RotationUtil;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Items;
import net.minecraft.item.MaceItem;
import net.minecraft.util.hit.EntityHitResult;

public class Macro extends Module {
    public int prevSlot;
    public boolean axed;
    public boolean pressAgain;
    public KeySetting lava;
    public KeySetting water;
    public int prev;
    public KeyBindSetting bind;
    public boolean maced;
    public static String[] dO;
    public static byte[] dP;
    public int tick;
    public BoolSetting mace;
    public BoolSetting axe;
    public KeySetting pearl;
    public KeySetting cobweb;

    public static void fJ() {
        try {
            dO = new String[30];
            dO[12] = fI("PXhirZkffJI1gFXyX3s6heTHG5ffw+ym/ECDO3KxJdmlJw+QycYVUF1043S3eXCtibF8IQ1/Vm6+dG28m2tdUH/4KNv8sfz7E93rbSzo9wA=", "��т9J", dP);
            dO[21] = fI("U1i2Y8RuG2VbX+ATwTrDpeTHG5ffw+ym/ECDO3KxJdkPaSdB2kgZMiUXBG/ghDd1", "|=K�t:", dP);
            dO[13] = fI("uMiBfmDC/D+JJtD+JfhpsOTHG5ffw+ym/ECDO3KxJdn+UzOO4hqIQeGTIcfZQSVm", "ϛs\u001bo�", dP);
            dO[22] = fI("gHeVdUgkIdDLdiBn1Jkue+THG5ffw+ym/ECDO3KxJdmzVGaueoIJ3yDToMN7jLn0", "z��K�N", dP);
            dO[27] = fI("g1r10dRhnHlOy53kUIx3NuTHG5ffw+ym/ECDO3KxJdmVtxhrHCGuRXMp6J+FojKlnmJdGAQ/XwnViW3KSg6i2/YS+SplimkkesmU1p9JDEg=", "\u0005���fq", dP);
            dO[20] = fI("92hW6bVzOCw08rG+M5z4v+THG5ffw+ym/ECDO3KxJdnb4QHquepuyBF9gYuVG9Mi", "G$ �[%", dP);
            dO[24] = fI("BTedxHN1oLEq53NlouctqOTHG5ffw+ym/ECDO3KxJdkYaYZHa7CeqqDZ9bb8rTL+", "Ds�A��", dP);
            dO[29] = fI("MYvhVk4m+p8D3DT4szMzMOTHG5ffw+ym/ECDO3KxJdlKAELIjF2MzS6U0DMoo0DMuXRl1nqm/mFOS5jbnPWD7EcE7PM+1JE1Kt55DNGkZJg=", "ECGS\u0003\u007f", dP);
            dO[10] = fI("zljHZ7MnbHO5+dUiCiqKceTHG5ffw+ym/ECDO3KxJdktJlt+mh7pqhYDaI61uo9a", "C���Ц", dP);
            dO[4] = fI("98THBEoY8ECwDZ1U4GIsA+THG5ffw+ym/ECDO3KxJdkenvey+2xnaVzXVlyjQ8kG", "�FԀZR", dP);
            dO[23] = fI("JFooZv9laoOUyoslJXdCG+THG5ffw+ym/ECDO3KxJdnGxK0P49PKvbW/sYoHY7ys", "\u0006�}+�y", dP);
            dO[3] = fI("hpWymgnSEfy737ihknBpX+THG5ffw+ym/ECDO3KxJdnVzHsnK5DJ88vp0JSxggF5", "|%gim\u0018", dP);
            dO[19] = fI("ZwR3JunT1ZyspGbx1tKGbeTHG5ffw+ym/ECDO3KxJdkQpVbPLhDYUQBQYKY/Pha7", "�\"���\u001a", dP);
            dO[15] = fI("1qBZ/I5DNdcgztq9YtJFDOTHG5ffw+ym/ECDO3KxJdmxy9ez0hT1H1PpfoUMdNET", ";�\u0017�+{", dP);
            dO[0] = fI("Dw7gOqu/olUkp9cBWD/b0+THG5ffw+ym/ECDO3KxJdmxCeMqXDkcw4cQTzwQkq3U", "(���\u0000�", dP);
            dO[25] = fI("NFWawnY2zLNfsoRAWvS7NOTHG5ffw+ym/ECDO3KxJdm6splcq+ARxmGaxyVIIZgg", "�p��)-", dP);
            dO[11] = fI("L3EW+R0Wh9XKXr456XZ5BOTHG5ffw+ym/ECDO3KxJdkSWUtbdzYwD5dTkDv2vg7t", "�+��t�", dP);
            dO[2] = fI("B2v/2TUvkad56MEeWh5jHeTHG5ffw+ym/ECDO3KxJdlLAIUlSJ4U1walJQtTdhzGVC4aw7Rjn4ZPNXjfwusRXQ==", "���.�T", dP);
            dO[8] = fI("xbB+Ht+0a8zR84CD5byPGOTHG5ffw+ym/ECDO3KxJdnB3FNTrzjIeKSd57+r8Vu5", "�<�vҘ", dP);
            dO[9] = fI("HouQt9k3YlK9vOYrlzHcWeTHG5ffw+ym/ECDO3KxJdmq6Hv9kvnZah4fVn/yM6nG", "�[�jfF", dP);
            dO[28] = fI("syoEA6O4onhed/3e+uioUeTHG5ffw+ym/ECDO3KxJdl+qUntA5pq4NUWWZmzsSbk", "ut�Ova", dP);
            dO[1] = fI("B7/9vnkqiFHZWBO2F0xwJ+THG5ffw+ym/ECDO3KxJdkC6UQVI0Sa6I3pkbKayirk", "�p�\u0019�.", dP);
            dO[16] = fI("7VwKtyDjjFbBGRG4jkqtCuTHG5ffw+ym/ECDO3KxJdl8AeFhkd/G7nDY9P9I0MqZ", "���[��", dP);
            dO[6] = fI("SR9SXrLEs+Cqj5RG++cSxeTHG5ffw+ym/ECDO3KxJdkj1bkquroiZRdiZowIpR3O", "�e�$\u0002�", dP);
            dO[14] = fI("Jwc0Szr8GTq5g/cG36FbkeTHG5ffw+ym/ECDO3KxJdlo3J+SZXtFqgjtLFbhuGuZDcEvB85yNjRLc4fwfegP9Ns4qRalGz4cPfUtZAGcdvo=", "L2;�I\u0001", dP);
            dO[26] = fI("Ysy4Ea8dKIFf2a9IEun/P+THG5ffw+ym/ECDO3KxJdmVPUqtpmsZVMXpyPuhGBMq", "P����D", dP);
            dO[18] = fI("1f5sigzPAywjHAmaETlFjuTHG5ffw+ym/ECDO3KxJdkjg7ra95BN6/y6eBkZwGJr", "��e\u007f�z", dP);
            dO[7] = fI("kTTNWytwRrYtl5FP2/fYd+THG5ffw+ym/ECDO3KxJdngOCBLHP7D0JKYy7UoNzf1", "�rvhZD", dP);
            dO[17] = fI("oxztLcHWPmb3euiKjG+CduTHG5ffw+ym/ECDO3KxJdmvwcQ89qzxJfUQQqubuM10hNTolzjswgyBxcV1cTQATw==", "\u0010���~�", dP);
            dO[5] = fI("W8qNts6BJsa6h0lmIQtcIOTHG5ffw+ym/ECDO3KxJdljWPyDLQRkRZavUjyKNMfx", "r-��|�", dP);
        } catch (Exception e) {}
    }

    public Macro() {
        super(dO[15], dO[16], Category.Utility);
        this.bind = new KeyBindSetting(dO[17], 0, false);
        this.pearl = new KeySetting(dO[18], dO[19], 0);
        this.lava = new KeySetting(dO[20], dO[21], 0);
        this.water = new KeySetting(dO[22], dO[23], 0);
        this.cobweb = new KeySetting(dO[24], dO[25], 0);
        this.mace = new BoolSetting(dO[26], dO[27], false);
        this.axe = new BoolSetting(dO[28], dO[29], false);
        this.prevSlot = -1;
        this.addSettings(new Setting[]{this.bind, this.pearl, this.lava, this.water, this.cobweb, this.mace, this.axe});
    }

    @EventHandler
    void onAttack(Post event) {
        if (!nullCheck()) {
            if (this.maced) {
                Main.mc.player.getInventory().selectedSlot = this.prev;
                this.maced = false;
            }

            if (this.axed) {
                Main.mc.player.getInventory().selectedSlot = this.prev;
                this.axed = false;
            }
        }
    }

    public static void fK() {
        dP = new byte[16];
        dP[4] = -33;
        dP[1] = -57;
        dP[7] = -90;
        dP[9] = 64;
        dP[11] = 59;
        dP[5] = -61;
        dP[2] = 27;
        dP[14] = 37;
        dP[3] = -105;
        dP[6] = -20;
        dP[13] = -79;
        dP[12] = 114;
        dP[15] = -39;
        dP[0] = -28;
        dP[10] = -125;
        dP[8] = -4;
    }

    @EventHandler
    void onUpdateMouse(UpdateMouseEvent event) {
        if (!nullCheck()) {
            if (Main.mc.currentScreen == null) {
                if (this.prevSlot == -1) {
                    this.tick = 0;
                    if (this.pearl.isActive(this.pearl.getKey())) {
                        this.prevSlot = Main.mc.player.getInventory().selectedSlot;
                        if (InventoryUtil.selectItemFromHotbar(Items.ENDER_PEARL)) {
                            InputUtil.callMouse(1, 1);
                        }
                    }

                    if (this.lava.isActive(this.lava.getKey())) {
                        this.prevSlot = Main.mc.player.getInventory().selectedSlot;
                        if (InventoryUtil.selectItemFromHotbar(Items.LAVA_BUCKET)) {
                            InputUtil.callMouse(1, 1);
                            this.pressAgain = true;
                        }
                    }

                    if (this.water.isActive(this.water.getKey())) {
                        this.prevSlot = Main.mc.player.getInventory().selectedSlot;
                        if (InventoryUtil.selectItemFromHotbar(Items.WATER_BUCKET)) {
                            InputUtil.callMouse(1, 1);
                            this.pressAgain = true;
                        }
                    }

                    if (this.cobweb.isActive(this.cobweb.getKey())) {
                        this.prevSlot = Main.mc.player.getInventory().selectedSlot;
                        if (InventoryUtil.selectItemFromHotbar(Items.COBWEB)) {
                            InputUtil.callMouse(1, 1);
                        }
                    }
                } else {
                    this.tick++;
                    if (this.tick > 10 && this.tick < 50 && this.pressAgain) {
                        InputUtil.callMouse(1, 1);
                        this.pressAgain = false;
                    }

                    if (this.tick > 50) {
                        Main.mc.player.getInventory().selectedSlot = this.prevSlot;
                        this.prevSlot = -1;
                    }
                }
            }
        }
    }

    @EventHandler
    void onAttack(Pre event) {
        if (!nullCheck()) {
            if (!(Main.mc.player.getMainHandStack().getItem() instanceof MaceItem)) {
                if (RotationUtil.getHitResult(Main.mc.player, false, RotationUtil.getRotation().getYaw(), RotationUtil.getRotation().getPitch()) instanceof EntityHitResult var2
                        && var2.getEntity() instanceof PlayerEntity var3
                        && PlayerUtil.validateTarget(var3)) {
                    this.prev = Main.mc.player.getInventory().selectedSlot;
                    if (this.mace.isEnabled()) {
                        this.maced = InventoryUtil.selectItemFromHotbar(Items.MACE);
                    }

                    if (this.axe.isEnabled()) {
                        for (int var6 = 0; var6 < 9; var6++) {
                            if (Main.mc.player.getInventory().getStack(var6).getItem() instanceof AxeItem) {
                                this.axed = true;
                                Main.mc.player.getInventory().selectedSlot = var6;
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    static {
        fK();
        fJ();
    }

    public static String fI(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{-9, -49, -126, -18, 86, 110, -70, 50, 106, -34, 52, 43, 0, -66, 1, 45};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 187, 256);
        SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] var8 = var7.generateSecret(var6).getEncoded();
        SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
        Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var10.init(2, var9, new IvParameterSpec(var2));
        byte[] var11 = var10.doFinal(var5);
        return new String(var11, "UTF-8");
    }
}
