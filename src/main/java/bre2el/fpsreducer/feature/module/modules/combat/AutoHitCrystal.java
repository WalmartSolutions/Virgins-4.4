package bre2el.fpsreducer.feature.module.modules.combat;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.event.impl.UpdateEvent.Pre;
import bre2el.fpsreducer.event.orbit.EventHandler;
import bre2el.fpsreducer.feature.module.Module;
import bre2el.fpsreducer.feature.module.ModuleManager;
import bre2el.fpsreducer.feature.module.Module.Category;
import bre2el.fpsreducer.feature.module.modules.utility.BackPlace;
import bre2el.fpsreducer.feature.module.setting.BoolSetting;
import bre2el.fpsreducer.feature.module.setting.ItemSetting;
import bre2el.fpsreducer.feature.module.setting.KeyBindSetting;
import bre2el.fpsreducer.feature.module.setting.RangeSetting;
import bre2el.fpsreducer.feature.module.setting.Setting;
import bre2el.fpsreducer.util.EntityUtil;
import bre2el.fpsreducer.util.InputUtil;
import bre2el.fpsreducer.util.InventoryUtil;
import bre2el.fpsreducer.util.MathUtil;
import bre2el.fpsreducer.util.ModuleUtil;
import bre2el.fpsreducer.util.RotationManager;
import bre2el.fpsreducer.util.RotationUtil;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.ButtonBlock;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.EnchantingTableBlock;
import net.minecraft.block.EnderChestBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.FurnaceBlock;
import net.minecraft.block.GrindstoneBlock;
import net.minecraft.block.LeverBlock;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.SignBlock;
import net.minecraft.block.StonecutterBlock;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.UseAction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitResult.Type;

public class AutoHitCrystal extends Module {
    public int swapTimer;
    public static byte[] cL;
    public RangeSetting clickDelay;
    public boolean moved;
    public static String[] cK;
    public static ModuleUtil hitCrystalStopper = new ModuleUtil();
    public RangeSetting range;
    public KeyBindSetting bind;
    public RangeSetting swapDelay;
    public int clickTimer;
    public ItemSetting items;
    public BoolSetting pause;

    boolean crystalCheck() {
        return Main.mc.player.getMainHandStack().isOf(Items.END_CRYSTAL);
    }

    boolean obiCheck() {
        return Main.mc.player.getMainHandStack().isOf(Items.OBSIDIAN);
    }

    @EventHandler
    void onUpdate(Pre event) {
        if (!nullCheck()) {
            if (Main.mc.currentScreen == null) {
                if (InputUtil.checkMouse(1)) {
                    if (this.pause.isEnabled() && hitCrystalStopper.isEnabled()) {
                        hitCrystalStopper.stop(300);
                    } else {
                        if (this.range.getDefaultValue() != 0.0 || this.range.getSecondValue() != 0.0) {
                            PlayerEntity var2 = EntityUtil.findClosestPlayer(
                                    PlayerEntity.class, MathUtil.getRandomFloat((float)this.range.getDefaultValue(), (float)this.range.getSecondValue())
                            );
                            if (var2 == null) {
                                return;
                            }
                        }

                        if (!this.isObi() || !this.obiCheck() || !(Main.mc.player.getPitch() > 80.0F)) {
                            if (InventoryUtil.hasItemInHotbar(Items.OBSIDIAN) && InventoryUtil.hasItemInHotbar(Items.END_CRYSTAL)) {
                                BlockHitResult var5 = !(Main.mc.crosshairTarget instanceof BlockHitResult var3) ? null : var3;
                                if (ModuleManager.INSTANCE.getModuleByName(cK[31]).isEnabled()
                                        && ModuleManager.INSTANCE.getModuleClass(BackPlace.class).backBreak.isEnabled()
                                        && RotationUtil.getHitResultIgnorePlayers(
                                        Main.mc.player, false, RotationUtil.getRotation().getYaw(), RotationUtil.getRotation().getPitch(), 4.5
                                ) instanceof BlockHitResult var6
                                        && var6.getType() == Type.BLOCK) {
                                    var5 = var6;
                                }

                                if (var5 != null && var5.getType() == Type.BLOCK) {
                                    if (Main.mc.world.getBlockState(var5.getBlockPos()).getBlock() instanceof DoorBlock) {
                                        return;
                                    }

                                    if (Main.mc.world.getBlockState(var5.getBlockPos()).getBlock() instanceof SignBlock) {
                                        return;
                                    }

                                    if (Main.mc.world.getBlockState(var5.getBlockPos()).getBlock() instanceof ChestBlock) {
                                        return;
                                    }

                                    if (Main.mc.world.getBlockState(var5.getBlockPos()).getBlock() instanceof EnderChestBlock) {
                                        return;
                                    }

                                    if (Main.mc.world.getBlockState(var5.getBlockPos()).getBlock() instanceof BarrelBlock) {
                                        return;
                                    }

                                    if (Main.mc.world.getBlockState(var5.getBlockPos()).getBlock() instanceof ShulkerBoxBlock) {
                                        return;
                                    }

                                    if (Main.mc.world.getBlockState(var5.getBlockPos()).getBlock() instanceof FurnaceBlock) {
                                        return;
                                    }

                                    if (Main.mc.world.getBlockState(var5.getBlockPos()).getBlock() instanceof EnchantingTableBlock) {
                                        return;
                                    }

                                    if (Main.mc.world.getBlockState(var5.getBlockPos()).getBlock() instanceof AnvilBlock) {
                                        return;
                                    }

                                    if (Main.mc.world.getBlockState(var5.getBlockPos()).getBlock() instanceof TrapdoorBlock) {
                                        return;
                                    }

                                    if (Main.mc.world.getBlockState(var5.getBlockPos()).getBlock() instanceof FenceGateBlock) {
                                        return;
                                    }

                                    if (Main.mc.world.getBlockState(var5.getBlockPos()).getBlock() instanceof CraftingTableBlock) {
                                        return;
                                    }

                                    if (Main.mc.world.getBlockState(var5.getBlockPos()).getBlock() instanceof ButtonBlock) {
                                        return;
                                    }

                                    if (Main.mc.world.getBlockState(var5.getBlockPos()).getBlock() instanceof LeverBlock) {
                                        return;
                                    }

                                    if (Main.mc.world.getBlockState(var5.getBlockPos()).getBlock() instanceof SweetBerryBushBlock) {
                                        return;
                                    }

                                    if (Main.mc.world.getBlockState(var5.getBlockPos()).getBlock() instanceof StonecutterBlock) {
                                        return;
                                    }

                                    if (Main.mc.world.getBlockState(var5.getBlockPos()).getBlock() instanceof GrindstoneBlock) {
                                        return;
                                    }

                                    if (Main.mc.world.getBlockState(var5.getBlockPos()).isOf(Blocks.RESPAWN_ANCHOR)
                                            && (Integer)Main.mc.world.getBlockState(var5.getBlockPos()).get(RespawnAnchorBlock.CHARGES) != 0
                                            && !Main.mc.player.isSneaking()) {
                                        return;
                                    }

                                    for (Item var11 : this.items.getSelected()) {
                                        if (Main.mc.player.getMainHandStack().isOf(var11)) {
                                            break;
                                        }
                                    }

                                    if (!this.items.getSelected().contains(Main.mc.player.getMainHandStack().getItem())) {
                                        return;
                                    }

                                    if (!this.isObi()
                                            && this.swapTimer >= MathUtil.getRandomInt((int)this.swapDelay.getDefaultValue(), (int)this.swapDelay.getSecondValue())) {
                                        this.swapTimer = 0;
                                        if (Main.mc.world.getBlockState(var5.getBlockPos()).canPlaceAt(Main.mc.world, var5.getBlockPos())) {
                                            InventoryUtil.selectItemFromHotbar(Items.OBSIDIAN);
                                            InputUtil.sendInput(0, 0);
                                            Main.mc.options.useKey.setPressed(false);
                                            this.moved = true;
                                        }
                                    }

                                    if (!this.moved && !this.isObi() && !this.isCrystal()) {
                                        if (!this.obiCheck()) {
                                            for (Item var12 : this.items.getSelected()) {
                                                if (Main.mc.player.getMainHandStack().isOf(var12)) {
                                                    break;
                                                }
                                            }

                                            if (!this.items.getSelected().contains(Main.mc.player.getMainHandStack().getItem())) {
                                                return;
                                            }
                                        }

                                        if (this.swapTimer >= MathUtil.getRandomInt((int)this.swapDelay.getDefaultValue(), (int)this.swapDelay.getSecondValue())) {
                                            this.swapTimer = 0;
                                            if (Main.mc.world.getBlockState(var5.getBlockPos()).canPlaceAt(Main.mc.world, var5.getBlockPos())) {
                                                InventoryUtil.selectItemFromHotbar(Items.OBSIDIAN);
                                                this.moved = true;
                                            }
                                        }
                                    }

                                    if (this.obiCheck()
                                            && this.moved
                                            && this.clickTimer >= MathUtil.getRandomInt((int)this.clickDelay.getDefaultValue(), (int)this.clickDelay.getSecondValue())) {
                                        this.clickTimer = 0;
                                        if (!Main.mc.world.getBlockState(var5.getBlockPos()).isReplaceable()) {
                                            InputUtil.callMouse(1, 1);
                                            Main.mc.options.useKey.setPressed(false);
                                        }
                                    }

                                    if (this.isObi()) {
                                        if (!this.moved && !this.obiCheck()) {
                                            for (Item var13 : this.items.getSelected()) {
                                                if (Main.mc.player.getMainHandStack().isOf(var13)) {
                                                    break;
                                                }
                                            }

                                            if (!this.items.getSelected().contains(Main.mc.player.getMainHandStack().getItem())) {
                                                return;
                                            }
                                        }

                                        if (this.swapTimer < MathUtil.getRandomInt((int)this.swapDelay.getDefaultValue(), (int)this.swapDelay.getSecondValue())) {
                                            Main.mc.options.useKey.setPressed(false);
                                        } else {
                                            this.swapTimer = 0;
                                            if (InventoryUtil.selectItemFromHotbar(Items.END_CRYSTAL) && !ModuleManager.INSTANCE.getModuleByName(cK[32]).isEnabled()) {
                                                InputUtil.callMouse(1, 1);
                                                Main.mc.options.useKey.setPressed(false);
                                                this.moved = false;
                                            }
                                        }
                                    }

                                    if (this.crystalCheck()
                                            && this.isObi()
                                            && this.clickTimer >= MathUtil.getRandomInt((int)this.clickDelay.getDefaultValue(), (int)this.clickDelay.getSecondValue())) {
                                        this.clickTimer = 0;
                                        if (!ModuleManager.INSTANCE.getModuleByName(cK[33]).isEnabled()) {
                                            InputUtil.callMouse(1, 1);
                                            Main.mc.options.useKey.setPressed(false);
                                        }
                                    }
                                }

                                if (this.isCrystal()
                                        && Main.mc.player.getMainHandStack().getUseAction() != UseAction.BLOCK
                                        && Main.mc.player.getMainHandStack().getUseAction() != UseAction.SPEAR
                                        && Main.mc.player.getMainHandStack().getUseAction() != UseAction.BOW
                                        && Main.mc.player.getMainHandStack().getUseAction() != UseAction.CROSSBOW
                                        && Main.mc.player.getMainHandStack().getUseAction() != UseAction.EAT
                                        && Main.mc.player.getMainHandStack().getUseAction() != UseAction.DRINK
                                        && this.swapTimer >= MathUtil.getRandomInt((int)this.swapDelay.getDefaultValue(), (int)this.swapDelay.getSecondValue())) {
                                    this.swapTimer = 0;
                                    InventoryUtil.selectItemFromHotbar(Items.END_CRYSTAL);
                                    InputUtil.sendInput(1, 0);
                                }

                                this.swapTimer++;
                                this.clickTimer++;
                            }
                        }
                    }
                } else {
                    this.moved = false;
                }
            }
        }
    }

    public static String ec(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{-95, -41, 0, -45, -49, -109, 25, 83, -103, -18, -124, -70, 74, 91, -42, -116};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 84, 256);
        SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] var8 = var7.generateSecret(var6).getEncoded();
        SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
        Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var10.init(2, var9, new IvParameterSpec(var2));
        byte[] var11 = var10.doFinal(var5);
        String info = new String(var11, "UTF-8");
        return info;
    }

    static {
        ee();
        ed();
    }

    public AutoHitCrystal() {
        super(cK[18], cK[19], Category.Combat);
        this.bind = new KeyBindSetting(cK[20], 0, false);
        this.swapDelay = new RangeSetting(cK[21], cK[22], 3.0, 4.0, 0.0, 10.0, 1.0, 4.0);
        this.clickDelay = new RangeSetting(cK[23], cK[24], 3.0, 4.0, 0.0, 10.0, 1.0, 4.0);
        this.items = new ItemSetting(
                cK[25], cK[26], "", new Item[]{Items.TOTEM_OF_UNDYING, Items.DIAMOND_SWORD, Items.NETHERITE_SWORD, Items.END_CRYSTAL, Items.OBSIDIAN}
        );
        this.pause = new BoolSetting(cK[27], cK[28], true);
        this.range = new RangeSetting(cK[29], cK[30], 0.0, 12.0, 0.0, 16.0, 1.0, 16.0);
        this.addSettings(new Setting[]{this.bind, this.swapDelay, this.clickDelay, this.items, this.pause, this.range});
    }

    boolean isCrystal() {
        if (ModuleManager.INSTANCE.getModuleByName(cK[34]).isEnabled() && ModuleManager.INSTANCE.getModuleClass(BackPlace.class).backBreak.isEnabled()) {
            if (RotationUtil.getHitResultIgnorePlayers(Main.mc.player, false, RotationUtil.getRotation().getYaw(), RotationUtil.getRotation().getPitch(), 4.5) instanceof EntityHitResult var4
                    && var4.getEntity() instanceof EndCrystalEntity) {
                return true;
            }

            return false;
        } else if (!RotationUtil.checkRotations()) {
            if (RotationUtil.getHitResult(Main.mc.player, false, Main.mc.player.getYaw(), Main.mc.player.getPitch()) instanceof EntityHitResult var3
                    && var3.getEntity() instanceof EndCrystalEntity) {
                return true;
            }

            return false;
        } else {
            if (RotationUtil.getHitResult(Main.mc.player, false, RotationManager.INSTANCE.getServerYaw(), RotationManager.INSTANCE.getServerPitch()) instanceof EntityHitResult var1
                    && var1.getEntity() instanceof EndCrystalEntity) {
                return true;
            }

            return false;
        }
    }

    boolean isObi() {
        if (ModuleManager.INSTANCE.getModuleByName(cK[35]).isEnabled() && ModuleManager.INSTANCE.getModuleClass(BackPlace.class).backBreak.isEnabled()) {
            if (RotationUtil.getHitResultIgnorePlayers(Main.mc.player, false, RotationUtil.getRotation().getYaw(), RotationUtil.getRotation().getPitch(), 4.5) instanceof BlockHitResult var5
                    && Main.mc.world.getBlockState(var5.getBlockPos()).isOf(Blocks.OBSIDIAN)) {
                return true;
            }

            return false;
        } else {
            HitResult var1 = RotationUtil.getHitResultBlock(Main.mc.player, false, RotationUtil.getRotation().getYaw(), RotationUtil.getRotation().getPitch(), 4.5);
            if (Main.mc.crosshairTarget instanceof BlockHitResult var3 && Main.mc.world.getBlockState(var3.getBlockPos()).isOf(Blocks.OBSIDIAN)
                    || var1 instanceof BlockHitResult var2 && Main.mc.world.getBlockState(var2.getBlockPos()).isOf(Blocks.OBSIDIAN)) {
                return true;
            }

            return false;
        }
    }

    @EventHandler
    void onItemUse(bre2el.fpsreducer.event.impl.ItemUseEvent.Pre event) {
        if (!nullCheck()) {
            if (RotationUtil.getHitResultBlock(Main.mc.player, false, RotationUtil.getRotation().getYaw(), RotationUtil.getRotation().getPitch(), 4.5) instanceof BlockHitResult var2
                    && Main.mc.world.getBlockState(var2.getBlockPos()).isOf(Blocks.RESPAWN_ANCHOR)
                    && (Integer)Main.mc.world.getBlockState(var2.getBlockPos()).get(RespawnAnchorBlock.CHARGES) != 0) {
                hitCrystalStopper.setEnabled();
            }
        }
    }

    public static void ee() {
        cL = new byte[16];
        cL[5] = -45;
        cL[15] = -95;
        cL[4] = 124;
        cL[7] = -86;
        cL[3] = -5;
        cL[1] = 40;
        cL[10] = -123;
        cL[12] = -25;
        cL[14] = -15;
        cL[2] = -13;
        cL[9] = -99;
        cL[11] = -49;
        cL[8] = -84;
        cL[6] = -27;
        cL[0] = -13;
        cL[13] = 44;
    }

    public static void ed() {
        try {
            cK = new String[36];
            cK[24] = ec("hD/SpKJmd3oBMun/x5cCQ/Mo8/t80+WqrJ2Fz+cs8aGVa1/tS1sL9b9ubvozsGKzd89rLGpTr63GnL0Mp5lPsw==", "��\u0004�s�", cL);
            cK[20] = ec("+ldeMh008i0zEmdKeWdUi/Mo8/t80+WqrJ2Fz+cs8aEicwHBEA9gYf5m9OQu8OmSXS918K/n7kHQKRroZrLamQ==", "�L*�*�", cL);
            cK[23] = ec("lj3etc2fYVAqGuLAcYFR/PMo8/t80+WqrJ2Fz+cs8aHqsebVGxtduABpzl048I0d", "�����\u000b", cL);
            cK[13] = ec("0Owvve+VgH2HvHesFfWikPMo8/t80+WqrJ2Fz+cs8aGTpa7qFBO2Q+z2VzThzV0N", "��D�1�", cL);
            cK[5] = ec("4Bu8CptuApwIxQrqx9S4YvMo8/t80+WqrJ2Fz+cs8aFpACY8TMqhSzAZJTf0/fx8", "�r/x�g", cL);
            cK[30] = ec(
                    "oNvmBNYDx4p6J7kLrzpmf/Mo8/t80+WqrJ2Fz+cs8aESi83wwLkpTsuzSAEaMZqDLfs7Qa7HNjeOOm2wNr8brpEHMwB6oovpRImolVEPn6Ue9KFC5/MDObB+NDyIQtGmFotx26PI7ZdH6NcJl0Dj0fLO/6KwxqXj5r6pXAITCCA=",
                    "-S�=��",
                    cL
            );
            cK[11] = ec("I8IQyS6AETF3ylxNwaskqvMo8/t80+WqrJ2Fz+cs8aFJgn6WE76SxACnZMckNOZX", "�;���0", cL);
            cK[3] = ec("QPbbwbeCt1JrTPIt3PwuAfMo8/t80+WqrJ2Fz+cs8aG/ToaiC6qSItcQDxQqUy0n", "����(`", cL);
            cK[26] = ec(
                    "B5OettcI9t0Ko9G9upUYqfMo8/t80+WqrJ2Fz+cs8aED9bwFob3bcrxk6Z0ZnMKSjV8uzrNWWq9y7HbR16KKN5EAiIXIw9u7FJ+17x7ueo6alr56pIl4tC5IIgim4qB4", "���P��", cL
            );
            cK[9] = ec("AxERj6zwjz+uxB/ETkz6s/Mo8/t80+WqrJ2Fz+cs8aFyAlrllMVXt4EyeX5725E4", "������", cL);
            cK[21] = ec("fQVstsqlqlAfBd1ItxstU/Mo8/t80+WqrJ2Fz+cs8aHrZuiYHM/up9HbYIzRLl8j", "�X\u0000\u0012ds", cL);
            cK[4] = ec("HVodDezvKbwEtFfQsRyTA/Mo8/t80+WqrJ2Fz+cs8aGzOHgegQPNFNbE/a3r+r8TUdvW8NWlx5xmoo5Fmf0yMA==", "�O}a�a", cL);
            cK[12] = ec(
                    "Bp4Q7oNPzxemDAEg/ROpTfMo8/t80+WqrJ2Fz+cs8aEZfegFGBbmZYKtSCWovzAp6B5kfGTeUbLUKh/KOClH0N+0H0x7CdSAfB65z+BGKlcD4tOBGmSHoxyzKctNhQbY00l7zQVUUqaG++jQMKwKLuuGWeXAGGl6M5atEmdubj4=",
                    "�Z��6�",
                    cL
            );
            cK[16] = ec("I0BxGGSYPEi+FFUk9ZgAlPMo8/t80+WqrJ2Fz+cs8aHohjjlWnCdK39i95QyP0X+", "-�\u000et�\u001c", cL);
            cK[0] = ec("xESq8YNUBCMG5HmNb9qWTvMo8/t80+WqrJ2Fz+cs8aHAJ2bF2zFHvMh3JYcYM7H2", "\u001dt����", cL);
            cK[15] = ec("yNsbTXdtwMwyHa9+zi3m0fMo8/t80+WqrJ2Fz+cs8aG9ZDBepC+CubHz0O5Lszcj", "~��:�R", cL);
            cK[8] = ec(
                    "OopSkP4IsyVmUxncYBzz1PMo8/t80+WqrJ2Fz+cs8aEF54BoejeLP57YopuGHNnzOi+/uHxMlSXkip7hF8pES6gcprW8oQmrG0CEOekc3joLbz8WPzZkH7MUxoQlxYZh", "<\u001c�R��", cL
            );
            cK[28] = ec("OljS1mp3Ju9VrUFgQK9YXPMo8/t80+WqrJ2Fz+cs8aGMvTT3Pt7f7wu9hEVyn9d81kbbXqpQq2EH+Otbb27Uxdqxmp2PMuIl+Z2bwWgzcUA=", "�$\\\u0018;`", cL);
            cK[27] = ec("ZI+++ug9y5O65Dd647mud/Mo8/t80+WqrJ2Fz+cs8aECfWfoTuha6P7o/je+dQqk", "���H1�", cL);
            cK[31] = ec("wcm7IWGQ8EuHHrqVAx+iXfMo8/t80+WqrJ2Fz+cs8aF+tIXhQgaAyaQ1XEWp448R", "\u0000���d�", cL);
            cK[22] = ec("63ky/mjeEyZVTU552MOW6PMo8/t80+WqrJ2Fz+cs8aGKcWIR2p9KtiaOzkbnuiWLIEhXb+padI1qj0tp5daf4Q==", "1�r�y�", cL);
            cK[35] = ec("kFAWCTJJaP+tqis6DNPJBvMo8/t80+WqrJ2Fz+cs8aGK56KW37iSfotfMzQEewYx", "5z{��\u0013", cL);
            cK[18] = ec("xqXlo58W1D7qQMMLzXL32/Mo8/t80+WqrJ2Fz+cs8aFSHAOEwL678Zhp4HrUc6U4", "��l�-�", cL);
            cK[6] = ec("RHAGSiLydjplgMCvV198vPMo8/t80+WqrJ2Fz+cs8aFBP3rLLMt1ExR9qQCQolLUsbZQcVtAtY9topFdmqRlWg==", "�Ʈ^�\u0015", cL);
            cK[25] = ec("imzmAahygvaaP9S2DdFvsfMo8/t80+WqrJ2Fz+cs8aHugZ4GZwAMfBNLQPXlm1O/", "r�\u0001\u0013e ", cL);
            cK[34] = ec("jhJqA+//esfuYsh3+71eLfMo8/t80+WqrJ2Fz+cs8aGRuM4Xxnr9R3uxeIPwF4Io", "��͕��", cL);
            cK[2] = ec("b+WR6yQA2qzUedm3ZK10q/Mo8/t80+WqrJ2Fz+cs8aGDfKNjJeQc9PsCdf7YqDIrGE+ofO1TchEqnb1sAc/Kvw==", "�d�P\u0015�", cL);
            cK[7] = ec("OiUDDDEaRt7fNZXKMluTSvMo8/t80+WqrJ2Fz+cs8aElCSs1XoCr7xMSsqUv0lOv", "\u001d�8���", cL);
            cK[17] = ec("oi8UbiPMRx4zkByb2t+97/Mo8/t80+WqrJ2Fz+cs8aFoapdxUpn+0FSDS0CItBrP", "���nvD", cL);
            cK[33] = ec("HruVymo3jOx4kBW5wYQgr/Mo8/t80+WqrJ2Fz+cs8aGMUDnOkROoNKnXy1tMC+Zi", "�\u001b���C", cL);
            cK[32] = ec("mZJ3VRFSRIpFTMC1JKNGp/Mo8/t80+WqrJ2Fz+cs8aGbS0gxzEAwDW/EDiGWj1UL", "�\u0003����", cL);
            cK[10] = ec("1oxIFpvABRwxtmgBIuWW6fMo8/t80+WqrJ2Fz+cs8aFaEo0YUI1ExwMcUef1UolXtKfdy+lHFyJPhADCeW+dHs3ny0MwiCx4Eu7UsLfMDRo=", "��F���", cL);
            cK[19] = ec("3QR76pMYNP9Vgwqrbymq6vMo8/t80+WqrJ2Fz+cs8aGDkcsnT/C7mnraurGxirKSWLaNtHa/31LXksu0345p/A==", ")�B=\n�", cL);
            cK[14] = ec("+XKAE2BW5NPjNhngdsxkgPMo8/t80+WqrJ2Fz+cs8aGHIJKLhXJq9isIVUJwPqNK", "\u0006;m�\u0010�", cL);
            cK[1] = ec("P3jchMT4eFEecg9mMVB1gfMo8/t80+WqrJ2Fz+cs8aHWWZDov12PquIOEdbuLZiF9RCIo5B85PL1Jp5/N9DmQw==", "IH�$��", cL);
            cK[29] = ec("x26Cr4UJ1Ldgn/Ba0MMdivMo8/t80+WqrJ2Fz+cs8aGLNNc+A8dzFhXDbigA0x81", "�!Jx��", cL);
        } catch (Exception e) {

        }
    }
}
