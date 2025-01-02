package bre2el.fpsreducer.feature.module.modules.utility;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.event.impl.UpdateEvent.Pre;
import bre2el.fpsreducer.event.orbit.EventHandler;
import bre2el.fpsreducer.feature.module.Module;
import bre2el.fpsreducer.feature.module.Module.Category;
import bre2el.fpsreducer.feature.module.setting.BoolSetting;
import bre2el.fpsreducer.feature.module.setting.KeyBindSetting;
import bre2el.fpsreducer.feature.module.setting.KeySetting;
import bre2el.fpsreducer.feature.module.setting.ModeSetting;
import bre2el.fpsreducer.feature.module.setting.NumberSetting;
import bre2el.fpsreducer.feature.module.setting.Setting;
import bre2el.fpsreducer.util.InputUtil;
import bre2el.fpsreducer.util.InventoryUtil;
import bre2el.fpsreducer.util.TimerUtil;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;

public class ElytraSwap extends Module {
    public KeySetting input;
    public BoolSetting hotbarSwap;
    public KeyBindSetting bind;
    public TimerUtil elytraTimer;
    public boolean swap;
    public static byte[] dL;
    public boolean mace;
    public int tick;
    public ModeSetting firework;
    public static String[] dK;
    public NumberSetting slot;
    public TimerUtil clickTimer;

    int getElytra() {
        ArrayList var1 = new ArrayList();

        for (int var2 = 9; var2 < 35; var2++) {
            if (Main.mc.player.getInventory().getStack(var2).isOf(Items.ELYTRA)) {
                var1.add(var2);
            }
        }

        if (!var1.isEmpty()) {
            Collections.shuffle(var1);
            return (Integer)var1.getFirst();
        } else {
            return -1;
        }
    }

    public static void fE() {
        dL = new byte[16];
        dL[10] = 36;
        dL[1] = -103;
        dL[15] = 109;
        dL[2] = -81;
        dL[6] = -111;
        dL[7] = 117;
        dL[9] = -36;
        dL[14] = 108;
        dL[5] = -13;
        dL[8] = -1;
        dL[3] = 127;
        dL[12] = -12;
        dL[13] = 85;
        dL[0] = 71;
        dL[11] = 37;
        dL[4] = -81;
    }

    public static void fD() {
        try {
            dK = new String[38];
            dK[35] = fC("s4qYorYQ82TcykYP2j3Ek0eZr3+v85F1/9wkJfRVbG0BwuMx925eUK3WUeG4Z8fF", "�jW�h7", dL);
            dK[21] = fC("UWCvd0gxVk5/mniV+owYyUeZr3+v85F1/9wkJfRVbG2acmT68sxq6GAYERO+kj91RJJmOfZrTksCGvzjOSk/5Q==", "1g۞��", dL);
            dK[6] = fC("BWNT4k+Q3MHZxsCwcXFPWEeZr3+v85F1/9wkJfRVbG0tjuHU0YbCPPnl+9SZiZDW", "Ƚ\u0000�\u0017�", dL);
            dK[16] = fC("dh/nPZqEU2/yWBhNs9mcRkeZr3+v85F1/9wkJfRVbG0g3shRGlXhottDSZYNxFFU", "\\��\u0002�", dL);
            dK[32] = fC("QWkMk5IXu/A6IdH0o0JpBkeZr3+v85F1/9wkJfRVbG354Hh/3UlcbKhyHKK/OpzT", "u\fG\b#)", dL);
            dK[27] = fC("LAsWTidfSifAFdx28VSbWUeZr3+v85F1/9wkJfRVbG2D9xs3tBc63g6t48rQodYM", "�\u0005\u001b���", dL);
            dK[11] = fC("StetCjmI/WXmJi6H+/AZREeZr3+v85F1/9wkJfRVbG3W7lz1Zrqgb+zyan6FG1ni", "V\u000e�-�\u0004", dL);
            dK[22] = fC("ptf0JbpvHhGG4tznoKjVWUeZr3+v85F1/9wkJfRVbG1HauUyenAuBh0LYv3Ccmz4", "\u001d�\u0012ƿ$", dL);
            dK[31] = fC("VlsEIFD8G5NXPf4Q1r7Ml0eZr3+v85F1/9wkJfRVbG1q37xJedOFRHzFWNoE9cYb", "\u0019\u0006�2q�", dL);
            dK[37] = fC("jnw397A4G63Dij3pzyBsikeZr3+v85F1/9wkJfRVbG1jM7bgf+A53k9ETdMa7xA6", "L?�&��", dL);
            dK[33] = fC("lEsQkNdj9eC8vWu14CcaEkeZr3+v85F1/9wkJfRVbG2e5u3w7pKZm8kBSxDxI+OQ", "5�����", dL);
            dK[12] = fC("OvqdTK7kpGOxeGZo5ZIfVEeZr3+v85F1/9wkJfRVbG0Z4eepc+M/9Ul2sxUd8/NW", "�\u000f\u0011�\u000f�", dL);
            dK[23] = fC("6mdfgvdz/mnyYPTEv/X7mkeZr3+v85F1/9wkJfRVbG0+BdHeCb5WqNIDllk1H84i", "1\rC>\u0001_", dL);
            dK[34] = fC("jEBowmwJnbA+wO+jHblKQUeZr3+v85F1/9wkJfRVbG3/fv3mfYL3hAPaNndFwyxj", "a��N�h", dL);
            dK[2] = fC("AxWEDHlpmwibnGRpb4PPnUeZr3+v85F1/9wkJfRVbG0h36bxkOL1Ou3Zd3DxQfHCtC2bM0uW3yIDzKs0FOvByQ==", "�K5���", dL);
            dK[15] = fC("1mJAiSKM93iOPlNJmGUBf0eZr3+v85F1/9wkJfRVbG2rP8SDWXWv33888x533tkF", "\u0014v���\u0002", dL);
            dK[29] = fC("BBrW2ZhEUXOCWY9KsTKMkkeZr3+v85F1/9wkJfRVbG2UamraJXvsMp3UDRLqy5HX", "M�\u0018\u0003��", dL);
            dK[7] = fC("d69zDfcnBLWU7ddCVve8KkeZr3+v85F1/9wkJfRVbG0QO1zw0DA+ek7hJ0S2OUbS", "�E�z�V", dL);
            dK[28] = fC("kWQvTRq1VrV78Pf7Ru3zykeZr3+v85F1/9wkJfRVbG2+M7I1WNRe/tR/V/5boCqI", "\u001fu}�W�", dL);
            dK[4] = fC("WXJhFlO24EVKoHD0JHbyKEeZr3+v85F1/9wkJfRVbG3iQD6Jpgpw/qZpKtSoeroi", "��9!Y�", dL);
            dK[9] = fC("kCV7Quwpfb8QHjVL2UhRfUeZr3+v85F1/9wkJfRVbG3AZAMynemjTe3xt5mTdDZW", "�x�\n[�", dL);
            dK[0] = fC("M9S7U8zGcbkIi7Zus/qCbEeZr3+v85F1/9wkJfRVbG1Y9QWvSlPzEcbyTFUEk1gd", "t!�zI�", dL);
            dK[10] = fC("dIjFAGRJdnLINgVOcHJTiEeZr3+v85F1/9wkJfRVbG3Hby+rutwdhXdd00yCnm1u", "�����", dL);
            dK[3] = fC("F9Rr09GNIA71kEQqvno/x0eZr3+v85F1/9wkJfRVbG0ERotvENlW8vqF5iE+OuNN", "D{�\u0007��", dL);
            dK[14] = fC("yI93bBvAF8sCOIwbsOiL40eZr3+v85F1/9wkJfRVbG09YQHqCcqOCYU2pTJGC91q", "�L��W�", dL);
            dK[19] = fC("7jKHdAe1byYRHwihM0NwkUeZr3+v85F1/9wkJfRVbG3kLdLMx/EONZQjqBwk3iXX", "I\u001e�ڑ~", dL);
            dK[20] = fC("pyhIynpFGrHILQlgI3WugkeZr3+v85F1/9wkJfRVbG0bar9Zflx3VLvYPzq0jaubq78vuE0B7lJ2ic7tcmTrivBoQsfj7ad69HPktENBknQ=", "z�Q�1^", dL);
            dK[24] = fC("qOmnC80RsI7e4OUpvr+M70eZr3+v85F1/9wkJfRVbG0y95/2zOBZEz1xUtNNsjVo", "�\u0003J���", dL);
            dK[5] = fC("+uz5SqQttVCf34t0Lbw5J0eZr3+v85F1/9wkJfRVbG0Rb8xjElGeeL4nBX1qesdU", "\u0002t���;", dL);
            dK[30] = fC("Cy93JHpRKm9/uOBkfqRR70eZr3+v85F1/9wkJfRVbG2heGvk1ICb839fpAODvUbI", "B9E�*�", dL);
            dK[13] = fC("pgf16yNTu9WPJz8pk3xPoEeZr3+v85F1/9wkJfRVbG30+0dd+ky683w/7l4tQ8z4", "��ǐ��", dL);
            dK[25] = fC("eNFTmgezLTusmdlaPCEURUeZr3+v85F1/9wkJfRVbG0tbTdRY0J0ZZFLS+81Eh7V", "\u001bQD���", dL);
            dK[18] = fC("hTYXk6BWRJcmv4XrYkU8H0eZr3+v85F1/9wkJfRVbG33JRYYO5jWW36MXMJK6oy6", "��O,��", dL);
            dK[26] = fC("4J/Bxlc3/ewdgWcOPmAwWEeZr3+v85F1/9wkJfRVbG0cqyjU53aKPO9IEnpjrhc6", "l\u0013S\u0005\u0013�", dL);
            dK[1] = fC("XrFPXB8q3AsuxIvbQWHXpEeZr3+v85F1/9wkJfRVbG3zHKUhZ9esTOe1biTM/lIx19ESGDz0pEtDb+M4YjGOdNCyXb8jH3tp9T4Kxdlc2e8=", "E\u000f=\\�G", dL);
            dK[17] = fC("t0fiIMrYWBuZemHrjsArkUeZr3+v85F1/9wkJfRVbG39XAuSwuhWuud4LUmfJxw9", "��4�\u0013B", dL);
            dK[36] = fC("Zz7cSvqgLiE8e40vLbB1H0eZr3+v85F1/9wkJfRVbG0QkTgaujLHabFwSoJpIMMT", "|\u0381�\u001c�", dL);
            dK[8] = fC("fG8yfKKHr/yc7XTCf8pcKEeZr3+v85F1/9wkJfRVbG2P43FGa2T585I3t2CbYXG2", "���ȗd", dL);
        } catch (Exception e) {}
    }

    @EventHandler
    public void onUpdate(Pre event) {
        if (Main.mc.currentScreen == null || this.hotbarSwap.isEnabled()) {
            if (!this.mace) {
                if (this.swap) {
                    this.swap = false;
                    if (this.hotbarSwap.isEnabled() && Main.mc.currentScreen instanceof InventoryScreen) {
                        Main.mc.player.closeHandledScreen();
                        InventoryUtil.selectItemFromHotbar(Items.ELYTRA);
                        InputUtil.callMouse(1, 1);
                    }

                    if (!this.firework.getMode().equals(dK[35])
                            && Main.mc.player.getEquippedStack(EquipmentSlot.CHEST).isOf(Items.ELYTRA)
                            && InventoryUtil.selectItemFromHotbar(Items.FIREWORK_ROCKET)
                            && !this.firework.getMode().equals(dK[36])
                            && this.clickTimer.hasReached(500.0)) {
                        // ?????????????? nice one virgin
                        new Thread(() -> {
                            try {
                                Main.mc.options.jumpKey.setPressed(true);
                                Thread.sleep(75L);
                                Main.mc.options.jumpKey.setPressed(false);
                                Thread.sleep(75L);
                                Main.mc.options.jumpKey.setPressed(true);
                                Thread.sleep(75L);
                                Main.mc.options.jumpKey.setPressed(false);
                                Thread.sleep(75L);
                                InputUtil.callMouse(1, 1);
                                Thread.sleep(50L);
                                this.clickTimer.reset();
                                if (this.firework.getMode().equals(dK[37])) {
                                    this.mace = true;
                                }
                            } catch (InterruptedException e) {}
                        }).start();
                    }
                }

                if (this.elytraTimer.hasReached(250.0)) {
                    if (this.input.isActive(this.input.getKey())) {
                        if (!(Main.mc.player.getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof ArmorItem)) {
                            if (Main.mc.player.getEquippedStack(EquipmentSlot.CHEST).isOf(Items.ELYTRA)) {
                                InventoryUtil.selectItemFromHotbar(Items.NETHERITE_CHESTPLATE);
                                //xd nice armor check retard
                                if (!this.armorCheck()) {
                                    InventoryUtil.selectItemFromHotbar(Items.DIAMOND_CHESTPLATE);
                                }

                                if (!this.armorCheck()) {
                                    InventoryUtil.selectItemFromHotbar(Items.IRON_CHESTPLATE);
                                }

                                if (!this.armorCheck()) {
                                    InventoryUtil.selectItemFromHotbar(Items.GOLDEN_CHESTPLATE);
                                }

                                if (!this.armorCheck()) {
                                    InventoryUtil.selectItemFromHotbar(Items.CHAINMAIL_CHESTPLATE);
                                }

                                if (!this.armorCheck()) {
                                    InventoryUtil.selectItemFromHotbar(Items.LEATHER_CHESTPLATE);
                                }

                                if (!this.armorCheck()) {
                                    return;
                                }

                                InputUtil.callMouse(1, 1);
                                this.swap = true;
                                this.elytraTimer.reset();
                            }
                        } else {
                            InventoryUtil.selectItemFromHotbar(Items.ELYTRA);
                            if (!Main.mc.player.getMainHandStack().isOf(Items.ELYTRA)) {
                                if (this.hotbarSwap.isEnabled()) {
                                    int var2 = this.getElytra();
                                    if (var2 == -1) {
                                        return;
                                    }

                                    Main.mc.setScreen(new InventoryScreen(Main.mc.player));
                                    Main.mc
                                            .interactionManager
                                            .clickSlot(
                                                    Main.mc.player.currentScreenHandler.syncId, var2, (int)this.slot.getDefaultValue() - 1, SlotActionType.SWAP, Main.mc.player
                                            );
                                    this.swap = true;
                                    this.elytraTimer.reset();
                                }
                            } else {
                                InputUtil.callMouse(1, 1);
                                this.swap = true;
                                this.elytraTimer.reset();
                            }
                        }
                    }
                }
            } else {
                this.tick++;
                if (this.tick > 5) {
                    if (Main.mc.player.isFallFlying()) {
                        InventoryUtil.selectItemFromHotbar(Items.MACE);
                    }

                    this.tick = 0;
                    this.mace = false;
                }
            }
        }
    }

    public static String fC(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{-29, -66, -113, 60, 96, -92, 0, -58, -72, 55, 22, -46, -67, 23, -65, 78};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 139, 256);
        SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] var8 = var7.generateSecret(var6).getEncoded();
        SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
        Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var10.init(2, var9, new IvParameterSpec(var2));
        byte[] var11 = var10.doFinal(var5);
        return new String(var11, "UTF-8");
    }

    static {
        fE();
        fD();
    }

    boolean armorCheck() {
        return Main.mc.player.getMainHandStack().getItem() instanceof ArmorItem;
    }

    public ElytraSwap() {
        super(dK[19], dK[20], Category.Utility);
        this.bind = new KeyBindSetting(dK[21], 0, false);
        this.input = new KeySetting(dK[22], dK[23], 0);
        this.firework = new ModeSetting(dK[24], dK[25], dK[26], new String[]{dK[27], dK[28], dK[29], dK[30]});
        this.hotbarSwap = new BoolSetting(dK[31], dK[32], false);
        this.slot = new NumberSetting(dK[33], dK[34], 9.0, 1.0, 9.0, 1.0);
        this.elytraTimer = new TimerUtil();
        this.clickTimer = new TimerUtil();
        this.addSettings(new Setting[]{this.bind, this.input, this.firework, this.hotbarSwap, this.slot});
    }
}
