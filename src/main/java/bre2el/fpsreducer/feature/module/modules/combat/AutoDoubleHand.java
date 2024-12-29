package bre2el.fpsreducer.feature.module.modules.combat;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.event.impl.PacketEvent.Receive;
import bre2el.fpsreducer.event.impl.UpdateEvent.Pre;
import bre2el.fpsreducer.event.orbit.EventHandler;
import bre2el.fpsreducer.feature.module.Module;
import bre2el.fpsreducer.feature.module.Module.Category;
import bre2el.fpsreducer.feature.module.modules.combat.AutoDoubleHand.Reason;
import bre2el.fpsreducer.feature.module.setting.BoolSetting;
import bre2el.fpsreducer.feature.module.setting.KeyBindSetting;
import bre2el.fpsreducer.feature.module.setting.NumberSetting;
import bre2el.fpsreducer.feature.module.setting.RangeSetting;
import bre2el.fpsreducer.feature.module.setting.Setting;
import bre2el.fpsreducer.util.InventoryUtil;
import bre2el.fpsreducer.util.MathUtil;
import bre2el.fpsreducer.util.RotationUtil;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import net.minecraft.block.Blocks;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

public class AutoDoubleHand extends Module {
    public KeyBindSetting bind;
    public static byte[] cx;
    public BoolSetting anchor;
    public BoolSetting inv;
    public Reason currentReason;
    public BoolSetting sword;
    public static String[] cw;
    public RangeSetting delay;
    public NumberSetting health;
    public NumberSetting swordHealth;
    public NumberSetting range;
    public BlockPos lastAnchor;
    public BoolSetting use;
    public int cooldownTick;
    public BoolSetting pop;
    public BoolSetting ignoreOwn;
    public BoolSetting crystal;
    public NumberSetting slot;
    public RangeSetting cooldown;

    public static void dJ() {
        cx = new byte[16];
        cx[0] = -99;
        cx[8] = -26;
        cx[4] = -18;
        cx[1] = -22;
        cx[2] = 68;
        cx[13] = -41;
        cx[3] = 67;
        cx[12] = 78;
        cx[6] = -83;
        cx[14] = -110;
        cx[9] = 114;
        cx[11] = -8;
        cx[5] = 77;
        cx[10] = -97;
        cx[15] = -99;
        cx[7] = 119;
    }

    public static String dH(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{-67, -26, -78, 0, -86, -116, 12, -12, -125, 122, 111, 20, 1, -31, 123, -40};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 82, 256);
        SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] var8 = var7.generateSecret(var6).getEncoded();
        SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
        Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var10.init(2, var9, new IvParameterSpec(var2));
        byte[] var11 = var10.doFinal(var5);
        String info = new String(var11, "UTF-8");
        return info;
    }

    @EventHandler
    public void onReceivePacket(Receive event) {
        if (event.packet instanceof EntityStatusS2CPacket var2) {
            if (var2.getStatus() == 35) {
                if (var2.getEntity(Main.mc.world) == Main.mc.player) {
                    if (this.pop.isEnabled()) {
                        this.dhand(Reason.POP);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onUpdate(Pre event) {
        if (!nullCheck()) {
            this.setDetail(this.delay.getDefaultValue() + "-" + this.delay.getSecondValue());
            if (this.inv.isEnabled() && Main.mc.currentScreen instanceof InventoryScreen && !InventoryUtil.selectItemFromHotbar(Items.TOTEM_OF_UNDYING)) {
                Main.mc.player.getInventory().selectedSlot = (int)this.slot.getDefaultValue() - 1;
            }

            if (this.crystal.isEnabled()) {
                for (PlayerEntity var3 : Main.mc.world.getPlayers()) {
                    if (!this.ignoreOwn.isEnabled() || !(var3 instanceof ClientPlayerEntity)) {
                        HitResult var4 = RotationUtil.getHitResultOther(var3, false, var3.getYaw(), var3.getPitch(), 5.0);
                        if (var4 instanceof EntityHitResult) {
                            EntityHitResult var5 = (EntityHitResult)var4;
                            if (var5.getEntity() instanceof EndCrystalEntity
                                    && var5.getEntity().getY() <= Main.mc.player.getY() + 0.45
                                    && var5.getPos().isInRange(Main.mc.player.getPos(), this.range.getDefaultValue())) {
                                this.dhand(Reason.CRYSTAL);
                            }
                        }
                    }
                }
            }

            if (this.anchor.isEnabled()) {
                for (PlayerEntity var8 : Main.mc.world.getPlayers()) {
                    if (!this.ignoreOwn.isEnabled() || !(var8 instanceof ClientPlayerEntity)) {
                        HitResult var10 = RotationUtil.getHitResultBlock(var8, false, var8.getYaw(), var8.getPitch(), 4.5);
                        if (var10 instanceof BlockHitResult) {
                            BlockHitResult var12 = (BlockHitResult)var10;
                            if (Main.mc.world.getBlockState(var12.getBlockPos()).getBlock() == Blocks.RESPAWN_ANCHOR
                                    && (
                                    (Integer)Main.mc.world.getBlockState(var12.getBlockPos()).get(RespawnAnchorBlock.CHARGES) != 0
                                            || (Integer)Main.mc.world.getBlockState(var12.getBlockPos()).get(RespawnAnchorBlock.CHARGES) == 0
                                            && var8.getMainHandStack().isOf(Items.GLOWSTONE)
                            )
                                    && var12.getBlockPos().toCenterPos().isInRange(Main.mc.player.getPos(), this.range.getDefaultValue())) {
                                if (this.lastAnchor != var12.getBlockPos()) {
                                    this.dhand(Reason.ANCHOR);
                                }

                                this.lastAnchor = var12.getBlockPos();
                            }
                        }
                    }
                }
            }

            if (this.sword.isEnabled()) {
                for (PlayerEntity var9 : Main.mc.world.getPlayers()) {
                    if (!this.ignoreOwn.isEnabled() || !(var9 instanceof ClientPlayerEntity)) {
                        HitResult var11 = RotationUtil.getHitResultOther(var9, false, var9.getYaw(), var9.getPitch(), 3.0);
                        if (var11 instanceof EntityHitResult) {
                            EntityHitResult var13 = (EntityHitResult)var11;
                            if (var13.getEntity() instanceof ClientPlayerEntity
                                    && ((ClientPlayerEntity)var13.getEntity()).getMainHandStack().getItem() instanceof SwordItem
                                    && var13.getPos().isInRange(Main.mc.player.getPos(), this.range.getDefaultValue())) {
                                this.dhand(Reason.SWORD);
                            }
                        }
                    }
                }
            }

            if (this.health.getDefaultValue() > 0.0 && (double)Main.mc.player.getHealth() <= this.health.getDefaultValue()) {
                this.dhand(Reason.LOW_HEALTH);
            }

            this.cooldownTick++;
            if (this.cooldownTick >= 50) {
                this.cooldownTick = 0;
            }
        }
    }

    public AutoDoubleHand() {
        super(cw[29], cw[30], Category.Combat);
        this.bind = new KeyBindSetting(cw[31], 0, false);
        this.delay = new RangeSetting(cw[32], cw[33], 0.0, 4.0, 0.0, 10.0, 1.0, 4.0);
        this.cooldown = new RangeSetting(cw[34], cw[35], 0.0, 4.0, 0.0, 10.0, 1.0, 4.0);
        this.health = new NumberSetting(cw[36], cw[37], 2.0, 0.0, 20.0, 1.0);
        this.inv = new BoolSetting(cw[38], cw[39], false);
        this.slot = new NumberSetting(cw[40], cw[41], 9.0, 1.0, 9.0, 1.0);
        this.pop = new BoolSetting(cw[42], cw[43], true);
        this.crystal = new BoolSetting(cw[44], cw[45], true);
        this.anchor = new BoolSetting(cw[46], cw[47], true);
        this.sword = new BoolSetting(cw[48], cw[49], true);
        this.swordHealth = new NumberSetting(cw[50], cw[51], 2.0, 0.0, 20.0, 1.0);
        this.range = new NumberSetting(cw[52], cw[53], 6.0, 0.0, 12.0, 1.0);
        this.ignoreOwn = new BoolSetting(cw[54], cw[55], true);
        this.use = new BoolSetting(cw[56], cw[57], true);
        this.addSettings(
                new Setting[]{
                        this.bind,
                        this.delay,
                        this.cooldown,
                        this.health,
                        this.inv,
                        this.slot,
                        this.pop,
                        this.crystal,
                        this.anchor,
                        this.sword,
                        this.swordHealth,
                        this.range,
                        this.ignoreOwn,
                        this.use
                }
        );
    }

    static {
        dJ();
        dI();
    }

    void dhand(Reason reason) {
        if (this.use.isEnabled() || !Main.mc.player.isUsingItem()) {
            if ((float)this.cooldownTick >= MathUtil.getRandomFloat((float)this.cooldown.getDefaultValue(), (float)this.cooldown.getSecondValue())) {
                if (reason != Reason.POP && this.currentReason == reason) {
                    return;
                }

                this.currentReason = reason;
                this.cooldownTick = 0;
                InventoryUtil.selectItemFromHotbar(Items.TOTEM_OF_UNDYING);
            }
        }
    }

    public static void dI() {
        try {
            cw = new String[58];
            cw[1] = dH("85gXFfQNADKkihlrBl+FYp3qREPuTa135nKf+E7Xkp0VO9yDhb4+9nbbMPc+H9EAZZ4SQqUSF3iYiwI96Put7ETjEr3jqAL8mkr6kaggW8A=", "x0Iʡ[", cx);
            cw[35] = dH("XjVC6xAmMq8MkOeQQx+/V53qREPuTa135nKf+E7Xkp0JckjTfKz0B1niNiJgMeU4HbJ2TttrFsk9KTbGx8HMBQ==", "\u0014\u000b[��b", cx);
            cw[55] = dH(
                    "lNDdGzzHI3qxyuepTZhOEp3qREPuTa135nKf+E7Xkp02ELhVEZ6kJuDjQIwtCpmsIOZ7lofP/eN08AKLJQ+4eLZDBPbLJJimpZEjGSH79QNz/lpOzqEdnKNSOuTv4CSR", "Lp)eX�", cx
            );
            cw[12] = dH("nzb80Sq5ns86u5TO1/r3o53qREPuTa135nKf+E7Xkp0NmC92UyzKcEkpF9atauXnapSTNNRGB9ToSWjd0gUFKlBAxAxeCqloy/7kwyPvycw=", "\u000e���pi", cx);
            cw[44] = dH("cyshPIjtZsbWDRaZZW5WJJ3qREPuTa135nKf+E7Xkp13vV+zk2ClwmoSAriQatMC", "\n��\u001d�b", cx);
            cw[34] = dH("HcNibQMp4JuR0YhIzLwwH53qREPuTa135nKf+E7Xkp3rSkQjKiuQLy8ztpf56QJz", "��vZo/", cx);
            cw[43] = dH("x1kEdCG7F4Xy6GG6RPaP6p3qREPuTa135nKf+E7Xkp1T7i3WQL5YihOrHPAGhXTfDnGvJ09V4+mbBwVTq6KDpEoGpNkxhmaayO1LDIlVtZI=", "7˱j^�", cx);
            cw[18] = dH("w73zM/+BM7HIaxrO0CBjEp3qREPuTa135nKf+E7Xkp0skW1UKVJWXdmSPSgv0VkmnTcc7Vxcv+Fkug/uBzr+6FdVe3Qf9S6I6APeKCX0PDc=", "\u0004�ZЬ�", cx);
            cw[52] = dH("GTxdrnjWnxgdqiSmUvULlJ3qREPuTa135nKf+E7Xkp15LoiN/b1DeyoPT/kWlN2F", "foc(�\u0019", cx);
            cw[8] = dH("pnL4Cy22eR+rizQpqzWy5J3qREPuTa135nKf+E7Xkp3TX1L1A7g3A2d2/C79jnxwGtyk/Eo+P1e4by69QYVPvQ==", ">>�Q\u001f�", cx);
            cw[20] = dH("6rduYhS2RkOBxza39+ltPZ3qREPuTa135nKf+E7Xkp3SuoHK0INyun6/5RbGXtVQyeIY0cOH4PGG3Dw5qXJSwA==", "�t��1�", cx);
            cw[30] = dH("c7HOZ2C7rZHXtQpND7HG2J3qREPuTa135nKf+E7Xkp0CbGVFC2b2v0LQ5xS1telhFTkk060cyMZW/NBcm3nFaQgfSryHQvNEw5RQ4o6Z0KY=", "e\u000b$S��", cx);
            cw[13] = dH("mBmmo8PoAbxKLKJV8SqCR53qREPuTa135nKf+E7Xkp1WYRk9RoN37l4gXX9yrP//", "T^/��E", cx);
            cw[41] = dH("B+UYe65H9Yvi8YRvVNSuiZ3qREPuTa135nKf+E7Xkp0Oq1eZYpiJIitsC3OZnLTjBwcr8c/UHUXT46gOYu8cxnQPCh4xdAIaoJTX3uMMZac=", "�\u0014����", cx);
            cw[5] = dH("3gW069cEeO+AXmbgWIU9KZ3qREPuTa135nKf+E7Xkp3tHCfvWl3I6Nmdv6p1jGfE", "\u000bm\u001fa\u0012�", cx);
            cw[3] = dH("fL8zZ2BKVILxvjIqTsT9mJ3qREPuTa135nKf+E7Xkp1iMQVwvlVaOZ+sXduEcf2R", "g���\u0000�", cx);
            cw[24] = dH(
                    "c7WPLGJRubBT2tHRhmez053qREPuTa135nKf+E7Xkp1t8ifmKn24FR7LXhJRfmaJGFqqjqwZxtFUf+WTYqtcnJFvG/jNfgp/4D1rKCtnKlxuBrPF8XdgZJ4TmT2K4+3a", "3\u0002����", cx
            );
            cw[53] = dH(
                    "TMe6e11wapaIxF0Inr5yYJ3qREPuTa135nKf+E7Xkp13l5df2UjlSLWDWo2UdzuBMe8S3qKRPVlF1QXN69fpi+/wEoMONQBAO8+8SU33rDcapNABaCgVvDTNA1GzTSR4", "���3$�", cx
            );
            cw[23] = dH("6Sg7+VQ/IZMo+h6N3LaZwJ3qREPuTa135nKf+E7Xkp1Q1VTEXcfa63FIK4vDhcHW", "E\u0002��m�", cx);
            cw[25] = dH("cxZpUpETvnfXqEjGsapU953qREPuTa135nKf+E7Xkp1sME9e79c6uj5fjRm6hbXoHVSteg8cwUdG6ZUe9oAOjg==", "19N��E", cx);
            cw[10] = dH("mqfpwHmBzcvwT7VNXEbDw53qREPuTa135nKf+E7Xkp1QVBcoYdWsKpzcCSmbAJRrQHVbNiyWNnpiX8xyYA1FxjdfH2ZBHyqHhLcdW/9sipg=", "�C\u0017K��", cx);
            cw[22] = dH("VNaQP6CvcoPs2xXU9Et4vp3qREPuTa135nKf+E7Xkp3tiMrc6GNMSKAdUWiVBJuKKgR8XqXNS6LJfWNj67iHtQ1wARGOLv8WV9gaRTdF4pU=", "�\u007f.]��", cx);
            cw[56] = dH("w5L+AvgHdeWc6AzjTrS2c53qREPuTa135nKf+E7Xkp1bMYQo1cGiZ9Wiz36DyECA", "��=Ӳ�", cx);
            cw[16] = dH("Hpuwm1zpNEtsKRrSFUVKAp3qREPuTa135nKf+E7Xkp21fBJ7zGLyRViHOYek7+VSHyxhANVREkUah68hAk6dr4+EtjWwoGQz5COehCCiNkM=", "�n�`�", cx);
            cw[57] = dH("I1QlqUlHvoSVFiKdGy1gYp3qREPuTa135nKf+E7Xkp21JEBietzzGQmq4iIKj6YApKOrBctcw7DJe+hUNhayXQ==", "��O�", cx);
            cw[42] = dH("YCXMFkJtjZxT0cFXrhqh/Z3qREPuTa135nKf+E7Xkp06iXIK8iACkE2bEwDkUFwV", "�k\u001d��g", cx);
            cw[54] = dH("hap36vFY7O9V7xPoi9Ef3Z3qREPuTa135nKf+E7Xkp2CA9r+AfIMRinip9fQrB+Th29E/EDsHkHdAQOtZMY0tQ==", "�\u001eϿS�", cx);
            cw[36] = dH("dqvHakOmbA/R3tjuX7h7j53qREPuTa135nKf+E7Xkp33AQxcL7eqreE4OEuAtref", "�X@\u0005��", cx);
            cw[39] = dH("Ur+o1Zdy8fON4Yfb2uxZ0p3qREPuTa135nKf+E7Xkp1MxxS50gVOFcrtWOHNjpQHbnsKVO/mM5PBD+GzWTgei60ZoBV5i++qAjRYru8kS0Y=", "��\u0007\u0003��", cx);
            cw[4] = dH("mPa+KwQRjUoZyokkbYfOe53qREPuTa135nKf+E7Xkp0BJ7zbIkCElhweLX4jKHdSQbCdGV29jwiNB3toiA6VZQ==", "�P\u001b�<\u0001", cx);
            cw[31] = dH("L/8vFdZOKfF+u5GS6FWhSZ3qREPuTa135nKf+E7Xkp3lQ9/e8MXDTa8raP4wOaf8Dk7gY5HBjKcweSVqRkBavA==", "6StR�R", cx);
            cw[50] = dH("spqwxsfZ3/XqcpgjVrPQ1p3qREPuTa135nKf+E7Xkp2tVlEYDi9zB2aQI5O0z9bwtt8RkI/BzR0KFDysu0jDYg==", "\f�L\u001c�", cx);
            cw[7] = dH("MBss5T0WFqKBl0sIYT83sZ3qREPuTa135nKf+E7Xkp25jFsCaejDZgsnARQjJi9Z", "\u0003�D\u0012ء", cx);
            cw[32] = dH("rDqK/9WrLRCRWB2l064ojJ3qREPuTa135nKf+E7Xkp2YkgDfES6mxepKR58NLI8c", "9\u001d�\\n8", cx);
            cw[37] = dH("3bOSuTo/Cs/ytbGzEVPngp3qREPuTa135nKf+E7Xkp3Tnh8zq4DZB3xsja659ocUSea3cR2Tx5raKeXdgBvIBg==", "e\f[4��", cx);
            cw[19] = dH("42xB0nsCZGrLANmdl2yzBJ3qREPuTa135nKf+E7Xkp246D7P/MkzzW51Qs6qASOr", "G�g�z\u007f", cx);
            cw[46] = dH("saw/J+JogUYegWLw+omNEJ3qREPuTa135nKf+E7Xkp2HGrTFfa7AZfGFZ4vJv/C7", "�\u0019\f0Q�", cx);
            cw[2] = dH("wFO0hCMDkvgDFq/brS/iP53qREPuTa135nKf+E7Xkp3kMQ2eTDt/X/n4G1v7l7SX3tJiwxFOzh4Mfn5EhAl5ng==", "W@Nba\t", cx);
            cw[11] = dH("eHrc6PB8JG5yI4vYS4HyNZ3qREPuTa135nKf+E7Xkp1Z+zK2nSxXot3LrIHXrerC", "@��ҹ�", cx);
            cw[38] = dH("phR5fHb/rlncIuQGcve3TJ3qREPuTa135nKf+E7Xkp3B61BLm/6AvtbiFuKcw5t7sD5Fbj+h0q6kbTY7lXssnA==", "f[A�\bP", cx);
            cw[51] = dH("T2W4p64apMGqPIqCmpn9D53qREPuTa135nKf+E7Xkp2XOHnTTZ5cmBpYE6hbQoBI+kDTUY7U9tcHQ6vWfiwxEymZV7TTr71DTJAQ00lrMb0=", "V�L��\t", cx);
            cw[40] = dH("CM3ggE98/Ht/7PD6DuFIrZ3qREPuTa135nKf+E7Xkp0pYhXGgpG/DxOIj2oDCuWm", "�N|fT\u0019", cx);
            cw[47] = dH("4QvFcaCHauzd8IqvK4PjeJ3qREPuTa135nKf+E7Xkp3Hf5CgBifcUealbiQO76mJP9UNQ58vIb/yIPNQfgqJFfksiz8oFJ4qxmXfWV7s47E=", "J\u0016h���", cx);
            cw[26] = dH(
                    "XzjBf0N/8p4v310w6nMrdJ3qREPuTa135nKf+E7Xkp29/84yNULawpcqdSWR8QR9wahtUKIkqKFWXDQJE6Uq/uof7kQbkhiPTra5w5ugTrjfBYb8rDYrJrST44UySC8g", "r+9�.\u001f", cx
            );
            cw[27] = dH("9cHvySZSlch83c/rdCdFP53qREPuTa135nKf+E7Xkp1VwkISY09D3pHQxLm9thwx", "\u000b�$�`�", cx);
            cw[0] = dH("WeOSzbHTb/8hqlG1x5/N853qREPuTa135nKf+E7Xkp0sO2hYdu7q6a9HyfT2SBTL", "v�KK�\u0003", cx);
            cw[6] = dH("qOukZG0loK9YZFUZepCmfZ3qREPuTa135nKf+E7Xkp18+d/S7ZWnON7JUiOFp9daghGRYyJSH9dTtV3rqsNJIQ==", "�+���a", cx);
            cw[15] = dH("gAmqexhR+UFk2nEIWR6tBJ3qREPuTa135nKf+E7Xkp28s80H1Kma4EKIzFIW/LxP", "�����i", cx);
            cw[17] = dH("s4WkqmAN5GkwVVLb+QZ6hZ3qREPuTa135nKf+E7Xkp0CGsfl92HOKEYeyWdtIz0e", "lQ\u001eR\f�", cx);
            cw[49] = dH("ILR0HAlQH5VqBcViDr9RMZ3qREPuTa135nKf+E7Xkp2+HHFS+UNkePTEpVVtebO5GkymelOUJbo4T4APU9/2KQ==", "C�D�\u0003�", cx);
            cw[21] = dH("aSfK9pQ3pZpfuXpZxhUd6Z3qREPuTa135nKf+E7Xkp3lImYLJGQfEwRKiT3bv57nnilnMCUQT3eDaYH6RaeMhg==", "?�\u007fW�", cx);
            cw[14] = dH("VyglV8R1yESiOmBzFIjr453qREPuTa135nKf+E7Xkp1RB8k5kZCP4DHhxsmlI3CpiGLj92WFQB6MFeY4HHotpX7LWUrgcAJXszy6tsXTqG0=", "i~,��2", cx);
            cw[29] = dH("3how1Fm35ujK1PKE30UGJ53qREPuTa135nKf+E7Xkp34U0QjaujmsDANY8us365x", "~��C�G", cx);
            cw[45] = dH("fK152PcCm1fTyTI5fXEZIJ3qREPuTa135nKf+E7Xkp1bQcSPLagfPyGEfWprTKcvpkH5GTbH/KsTfzt1lD5u5tmppuvzgiojV3S27f00H90=", "�|\u0004qo6", cx);
            cw[9] = dH("pqtzeC2sUHQOfVYKC1R70p3qREPuTa135nKf+E7Xkp3FetL8OlBhfIP19e3/vcSujfM+cjQdWP8TgPTRfiuoWQ==", "8\u00171�\u0012]", cx);
            cw[48] = dH("yTDlAdLfvx50nOQfhsBjMJ3qREPuTa135nKf+E7Xkp0AQFbNX2OPkGXWwlxP3Z2+", "��>�1�", cx);
            cw[33] = dH("F7lowKqfcRRIOLFdxEM2+Z3qREPuTa135nKf+E7Xkp1edIwwhvYzFILCu4Q5fVtti4MjGtCJMpxK6dEIufMoKg==", "F�\u0019r�v", cx);
            cw[28] = dH("Yt0pqMNuAL0jCUxU+WBfdp3qREPuTa135nKf+E7Xkp0lvWn119KkvteBMX5tMBRqVLT241sIadDDR+FkvahQnQ==", "0\"mM�u", cx);
        } catch (Exception e) {

        }
    }

    public enum Reason {
        POP,
        LOW_HEALTH,
        SWORD,
        CRYSTAL,
        ANCHOR;
    }
}
