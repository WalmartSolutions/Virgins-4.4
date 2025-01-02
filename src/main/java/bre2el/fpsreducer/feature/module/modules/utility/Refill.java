package bre2el.fpsreducer.feature.module.modules.utility;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.event.impl.UpdateEvent.Pre;
import bre2el.fpsreducer.event.orbit.EventHandler;
import bre2el.fpsreducer.feature.module.Module;
import bre2el.fpsreducer.feature.module.Module.Category;
import bre2el.fpsreducer.feature.module.setting.BoolSetting;
import bre2el.fpsreducer.feature.module.setting.KeyBindSetting;
import bre2el.fpsreducer.feature.module.setting.NumberSetting;
import bre2el.fpsreducer.feature.module.setting.RangeSetting;
import bre2el.fpsreducer.feature.module.setting.Setting;
import bre2el.fpsreducer.mixin.HandledScreenAccessor;
import bre2el.fpsreducer.util.MathUtil;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SplashPotionItem;
import net.minecraft.registry.Registries;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import org.lwjgl.glfw.GLFW;

public class Refill extends Module {
    public KeyBindSetting bind;
    public BoolSetting pearl;
    public int delayTick;
    public NumberSetting pearlSlot;
    public static String[] bu;
    public static byte[] bv;
    public RangeSetting delay;
    public int slot;
    public BoolSetting pot;

    public static void cg() {
        bv = new byte[16];
        bv[12] = -117;
        bv[6] = -96;
        bv[11] = -111;
        bv[10] = -93;
        bv[4] = -2;
        bv[13] = -74;
        bv[2] = 104;
        bv[5] = -28;
        bv[8] = 85;
        bv[9] = -33;
        bv[7] = 107;
        bv[15] = -43;
        bv[3] = -23;
        bv[14] = -64;
        bv[0] = -33;
        bv[1] = 105;
    }

    public static boolean checkPot(StatusEffect amplifier, int itemStack, int type, ItemStack duration) {
        StatusEffectInstance var4 = new StatusEffectInstance(Registries.STATUS_EFFECT.getEntry(amplifier), itemStack, type);
        return duration.getItem() instanceof SplashPotionItem
                && duration.get(DataComponentTypes.POTION_CONTENTS).getEffects().toString().contains(var4.toString());
    }

    public Refill() {
        super(bu[11], bu[12], Category.Utility);
        this.bind = new KeyBindSetting(bu[13], 0, false);
        this.delay = new RangeSetting(bu[14], bu[15], 0.0, 2.0, 0.0, 20.0, 1.0, 4.0);
        this.pearl = new BoolSetting(bu[16], bu[17], true);
        this.pearlSlot = new NumberSetting(bu[18], bu[19], 9.0, 1.0, 9.0, 1.0);
        this.pot = new BoolSetting(bu[20], bu[21], true);
        this.addSettings(new Setting[]{this.bind, this.delay, this.pearl, this.pearlSlot, this.pot});
    }

    static {
        cg();
        cf();
    }

    boolean checkDelay() {
        return MathUtil.getRandomInt((int)this.delay.getDefaultValue(), (int)this.delay.getSecondValue()) <= this.delayTick;
    }

    @EventHandler
    public void onUpdate(Pre event) {
        if (!nullCheck()) {
            this.delayTick++;
            if (this.checkDelay()) {
                if (Main.mc.currentScreen instanceof InventoryScreen var2) {
                    if (((HandledScreenAccessor)var2).hoveredSlot() == null) {
                        return;
                    }

                    int var5 = ((HandledScreenAccessor)var2).hoveredSlot().getIndex();
                    if (var5 > 35) {
                        return;
                    }

                    if (var5 < 9) {
                        return;
                    }

                    this.slot = var5;
                    PlayerInventory var4 = Main.mc.player.getInventory();
                    if (this.pearl.isEnabled()
                            && ((ItemStack)var4.main.get(this.slot)).getItem() == Items.ENDER_PEARL
                            && Main.mc.player.getInventory().getStack((int)(this.pearlSlot.getDefaultValue() - 1.0)).getItem() != Items.ENDER_PEARL) {
                        Main.mc
                                .interactionManager
                                .clickSlot(
                                        ((PlayerScreenHandler)((InventoryScreen)Main.mc.currentScreen).getScreenHandler()).syncId,
                                        this.slot,
                                        (int)(this.pearlSlot.getDefaultValue() - 1.0),
                                        SlotActionType.SWAP,
                                        Main.mc.player
                                );
                        this.reset();
                    }

                    if (this.pot.isEnabled()
                            && GLFW.glfwGetKey(Main.mc.getWindow().getHandle(), 340) == 1
                            && var4.main.get(this.slot).getItem() == Items.SPLASH_POTION
                            && checkPot(StatusEffects.INSTANT_HEALTH.value(), 1, 1, Main.mc.player.getInventory().getStack(this.slot))) {
                        Main.mc
                                .interactionManager
                                .clickSlot(
                                        ((PlayerScreenHandler)((InventoryScreen)Main.mc.currentScreen).getScreenHandler()).syncId,
                                        this.slot,
                                        1,
                                        SlotActionType.QUICK_MOVE,
                                        Main.mc.player
                                );
                        this.reset();
                    }
                }
            }
        }
    }

    public static String ce(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{-21, 8, 0, 75, 25, -74, 72, -94, -59, -98, 42, 109, -21, 121, 22, -19};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 185, 256);
        SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] var8 = var7.generateSecret(var6).getEncoded();
        SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
        Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var10.init(2, var9, new IvParameterSpec(var2));
        byte[] var11 = var10.doFinal(var5);
        return new String(var11, "UTF-8");
    }

    void reset() {
        this.delayTick = 0;
    }

    public static void cf() {
        try {
            bu = new String[22];
            bu[10] = ce("UGTmXErGrbK8ooBfi8mfDt9paOn+5KBrVd+jkYu2wNUXTdDcZfkY7bf913Vw4XoS", "=��34�", bv);
            bu[11] = ce("OQMSrN7fw7yANQxMeHGhzN9paOn+5KBrVd+jkYu2wNWS3zB5UGCtC63afBqGzTNr", "��\u001ey\u001b�", bv);
            bu[21] = ce("n3qeZMJxy+GqN+C1EUv0v99paOn+5KBrVd+jkYu2wNVDY78w2qAcH8UN3sDaUbmb", "�\t����", bv);
            bu[8] = ce("VOn8kTV+Mbk8Y2tW0xZox99paOn+5KBrVd+jkYu2wNUyThxUs4G3FdEjPePB+f/4tUCofh6WCyQwHhA30Svbyw==", "\u0014�\u0014�Ԑ", bv);
            bu[13] = ce("BjLSCPV9pJB6MaYyd89XLN9paOn+5KBrVd+jkYu2wNUJQZTdO8Ks1f8n807Vri6fA4fz5zV2whHII5QReU/ohQ==", "l{�~\n�", bv);
            bu[16] = ce("fakiq9d56jRyg1l5EwjCBd9paOn+5KBrVd+jkYu2wNW9ehwxTliR1k9BSr7MZR/U", "\u000b�L�U�", bv);
            bu[9] = ce("Tig52y7KZAP7c6Xlx6+o/N9paOn+5KBrVd+jkYu2wNVK4iB9z+neN51JrBojGuE0", "\t�P%��", bv);
            bu[17] = ce("BkW44tbeyj0mpSsyL8IV/d9paOn+5KBrVd+jkYu2wNVZXSIT9USZ2FiR/M7Il3BS", "UW\u001e\u0018ж", bv);
            bu[5] = ce("GQfYE8GloJgNDfy152BSvN9paOn+5KBrVd+jkYu2wNWSWBoaousjz4EIcJYgc91s", "�^] !a", bv);
            bu[18] = ce("dZ3NcznG/rtv4D19bnvmVd9paOn+5KBrVd+jkYu2wNUgEwg9J9oJmOokEZDu8rqa", "#-G_�+", bv);
            bu[7] = ce("BpeJZS/kDh1tYhKorteD4d9paOn+5KBrVd+jkYu2wNXTsE3JeDnS12NXib0b6Ir1", "\f\u000f�\b4�", bv);
            bu[15] = ce("Qyoiew9Ii1PxFD1phgQped9paOn+5KBrVd+jkYu2wNV2mcfZSO/OqO42CLZgR1Yl", "����k�", bv);
            bu[3] = ce("pmBwJeJtU86ZN74xChAHdt9paOn+5KBrVd+jkYu2wNVy0K05dgVRvoF3a8w01OIz", "\u0012O쐴�", bv);
            bu[1] = ce("6lov4n/1BKL2CQOHJgwaRt9paOn+5KBrVd+jkYu2wNXifJa1XiUE8bHbhitYZwjEn8oIjDgM1sHCzL89zuo4/XP/5jEKALuop8dwzIsh3lQ=", "��.\"%�", bv);
            bu[12] = ce("pfgHi9tPmZezv7GvyUpIcN9paOn+5KBrVd+jkYu2wNXbFT79O1tXup1sdMexRhWqYBBZJ7XqLk/w2uisBHqFx1pPphu0Ommx/47MnXIwW/Y=", "\u0014�2mH\u0004", bv);
            bu[6] = ce("31dSAHtAUMBGg1l7HsYPON9paOn+5KBrVd+jkYu2wNXRskZhiIB1DmM0n2MA9mpp", "�G����", bv);
            bu[19] = ce("RXSGFQEvslHuygWlBk+Xrt9paOn+5KBrVd+jkYu2wNVSiHrTmfU1GWkuptViFEcZu3l+dtGcU5/mAzejuouJEg==", "��\u0007��!", bv);
            bu[2] = ce("5UrkN4zjYk2uWPMTkyQLKN9paOn+5KBrVd+jkYu2wNWQWqoI09fiTqjZUUSEDAb998u9GTePMsMdLr3JSx4I5g==", "���\u0014��", bv);
            bu[4] = ce("VL1PP1sBW0sJ9dBbtcd0I99paOn+5KBrVd+jkYu2wNUbu4Rt/f3MCVB/dqXwIjEY", "�(Y\t�\f", bv);
            bu[14] = ce("c5sV0WBwaHjTGZvC6m9T0t9paOn+5KBrVd+jkYu2wNXqCTVGT2XyBaLr50uP/ZgW", "ds��?�", bv);
            bu[20] = ce("oXsNZqMuv76aXDb98InibN9paOn+5KBrVd+jkYu2wNV2DQ/5lIxkRm+PKYH4s3Px", "\u001bJ��\b\u000e", bv);
            bu[0] = ce("42A/wmDyJfyECtPWUi3p5N9paOn+5KBrVd+jkYu2wNXx2QU/vLqRsu0ZtnuJ0FoC", "#\u0016��\u0010�", bv);
        } catch (Exception e) {

        }
    }
}
