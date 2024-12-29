package bre2el.fpsreducer.feature.module.modules.visual;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.event.impl.RenderWorldEvent;
import bre2el.fpsreducer.event.impl.UpdateEvent.Pre;
import bre2el.fpsreducer.event.orbit.EventHandler;
import bre2el.fpsreducer.feature.module.Module;
import bre2el.fpsreducer.feature.module.Module.Category;
import bre2el.fpsreducer.feature.module.modules.visual.BlockESP.BlockVec;
import bre2el.fpsreducer.feature.module.modules.visual.BlockESP.SearchThread;
import bre2el.fpsreducer.feature.module.setting.ItemSetting;
import bre2el.fpsreducer.feature.module.setting.KeyBindSetting;
import bre2el.fpsreducer.feature.module.setting.Setting;
import bre2el.fpsreducer.util.RenderUtil;
import bre2el.fpsreducer.util.RenderUtil.RenderHelper;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Base64;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import org.jetbrains.annotations.NotNull;

public class BlockESP extends Module {
    public ItemSetting items;
    public static String[] dA;
    public SearchThread searchThread;
    public KeyBindSetting bind;
    public static byte[] dB;
    public static CopyOnWriteArrayList<BlockVec> blocks = new CopyOnWriteArrayList();

    @EventHandler
    public void onRenderWorld(RenderWorldEvent event) {
        if (!nullCheck()) {
            for (BlockVec var3 : blocks) {
                if (!(var3.getDistance(new BlockVec(Main.mc.player.getX(), Main.mc.player.getY(), Main.mc.player.getZ())) > 512.0)) {
                    BlockPos var4 = new BlockPos((int)var3.x, (int)var3.y, (int)var3.z);
                    Color var5 = new Color(Main.mc.world.getBlockState(var4).getMapColor(Main.mc.world, var4).color);
                    if (Main.mc.world.getBlockState(var4).isOf(Blocks.DIAMOND_ORE)
                            || Main.mc.world.getBlockState(var4).isOf(Blocks.DEEPSLATE_DIAMOND_ORE)) {
                        var5 = new Color(0, 255, 255, 255);
                    }

                    if (Main.mc.world.getBlockState(var4).isOf(Blocks.GOLD_ORE) || Main.mc.world.getBlockState(var4).isOf(Blocks.DEEPSLATE_GOLD_ORE)) {
                        var5 = new Color(255, 255, 0, 255);
                    }

                    if (Main.mc.world.getBlockState(var4).isOf(Blocks.EMERALD_ORE)
                            || Main.mc.world.getBlockState(var4).isOf(Blocks.DEEPSLATE_EMERALD_ORE)) {
                        var5 = new Color(0, 255, 0, 255);
                    }

                    if (Main.mc.world.getBlockState(var4).isOf(Blocks.REDSTONE_ORE)
                            || Main.mc.world.getBlockState(var4).isOf(Blocks.DEEPSLATE_REDSTONE_ORE)) {
                        var5 = new Color(255, 0, 0, 255);
                    }

                    if (Main.mc.world.getBlockState(var4).isOf(Blocks.LAPIS_ORE) || Main.mc.world.getBlockState(var4).isOf(Blocks.DEEPSLATE_LAPIS_ORE)) {
                        var5 = new Color(0, 0, 255, 255);
                    }

                    if (Main.mc.world.getBlockState(var4).isOf(Blocks.IRON_ORE) || Main.mc.world.getBlockState(var4).isOf(Blocks.DEEPSLATE_IRON_ORE)) {
                        var5 = new Color(255, 255, 255, 255);
                    }

                    RenderHelper.setMatrixStack(event.matrices);
                    RenderUtil.setCameraAction();
                    RenderHelper.getPositionMatrix().set(event.matrices.peek().getPositionMatrix());
                    RenderUtil.renderBoundingBox(new Box(var4), event.matrices, var5);
                    MatrixStack var6 = RenderHelper.getMatrixStack();
                    var6.pop();
                } else {
                    blocks.remove(var3);
                }
            }
        }
    }

    public static void fo() {
        try {
            dA = new String[10];
            dA[7] = fn("qCCCcbzKOGwdu7Gc4iCeDe1RNwI8sAkQq2ygmtp9OrRzjsoSyjEZefrLM8HRYsUz0VxhInBuYcNjyGWZMfddxQ==", "̸\u001d\u001a'3", dB);
            dA[0] = fn("Zh4Rkf5eLVnZ2+eTkh72c+1RNwI8sAkQq2ygmtp9OrTzt4iLeJA4Fsp4lBSkoVxR", "�\rx#*;", dB);
            dA[2] = fn("b+r94uQiu0PKsWJTse/N1O1RNwI8sAkQq2ygmtp9OrT0MuS/T/M53N6TYa/AXj/zwgf5eD33brdUIsOWm/nMSQ==", "�̗;\u00007", dB);
            dA[9] = fn("KhQaSRvM3PAkZyZD96//YO1RNwI8sAkQq2ygmtp9OrSnjITQBVJzDXFN/c2Ige15V+JkE5z3oEn95ZZyDSaPsw==", "��q��R", dB);
            dA[5] = fn("evAnfxePM1T8bSzduK4cv+1RNwI8sAkQq2ygmtp9OrSFU1LE5qejimRE1K1R3BL0", "�8v��y", dB);
            dA[1] = fn("ZDF+2dI4lZLBnF+wDLmaDu1RNwI8sAkQq2ygmtp9OrRBbxd1cbYmMfvPL7ro1x0s", "3w�k��", dB);
            dA[4] = fn("ouFhetyx0Zrz6iq4LzKJD+1RNwI8sAkQq2ygmtp9OrQTZDs1qa/2GZEurle/uA0bs1NnoLp/hkVBI43jf1dMYQ==", "����\u001c", dB);
            dA[6] = fn("okTdKXYBd8i0hSRquZSe4+1RNwI8sAkQq2ygmtp9OrRc/FSTRcHvjKX0rI6xLtaj", "�3�\u0011��", dB);
            dA[3] = fn("EfUr4ttCHRhV942dKfnNI+1RNwI8sAkQq2ygmtp9OrT880BcHdSvZLcYxxNbiKO9", "Mº�$�", dB);
            dA[8] = fn("WJTxdaXGsRQHY4u/WB31M+1RNwI8sAkQq2ygmtp9OrTlm5gT064dtM+TqHQ3Yf1F", "*Z�;kM", dB);
        } catch (Exception e) {

        }
    }

    @Override
    public void onDisable() {
        this.searchThread.interrupt();
        super.onDisable();
    }

    public static void fp() {
        dB = new byte[16];
        dB[2] = 55;
        dB[1] = 81;
        dB[8] = -85;
        dB[12] = -38;
        dB[14] = 58;
        dB[5] = -80;
        dB[4] = 60;
        dB[10] = -96;
        dB[3] = 2;
        dB[13] = 125;
        dB[15] = -76;
        dB[6] = 9;
        dB[9] = 108;
        dB[7] = 16;
        dB[0] = -19;
        dB[11] = -102;
    }

    public static String fn(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{38, 83, 61, 2, -111, 116, -18, 121, -37, -115, -104, 109, -87, 0, 124, -71};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 121, 256);
        SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] var8 = var7.generateSecret(var6).getEncoded();
        SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
        Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var10.init(2, var9, new IvParameterSpec(var2));
        byte[] var11 = var10.doFinal(var5);
        return new String(var11, "UTF-8");
    }

    @EventHandler
    public void onUpdate(Pre event) {
        if (!this.searchThread.isAlive()) {
            this.searchThread = new SearchThread(this);
            this.searchThread.setDaemon(true);
            this.searchThread.start();
        }
    }

    @Override
    public void onEnable() {
        blocks.clear();
        this.searchThread = new SearchThread(this);
        this.searchThread.setDaemon(true);
        this.searchThread.start();
        super.onEnable();
    }

    public BlockESP() {
        super(dA[5], dA[6], Category.Visual);
        this.bind = new KeyBindSetting(dA[7], 0, false);
        this.items = new ItemSetting(
                dA[8], dA[9], "", new Item[]{Items.ANCIENT_DEBRIS, Items.DIAMOND_ORE, Items.DEEPSLATE_DIAMOND_ORE, Items.GOLD_ORE, Items.DEEPSLATE_GOLD_ORE}
        );
        this.searchThread = new SearchThread(this);
        this.addSettings(new Setting[]{this.bind, this.items});
    }

    boolean shouldAdd(Block block) {
        for (Item var3 : this.items.getSelected()) {
            if (block != Blocks.AIR && block.asItem() == var3) {
                return true;
            }
        }

        return false;
    }

    static {
        fp();
        fo();
    }

    public record BlockVec(double x, double y, double z) {

        public double getDistance(@NotNull BlockVec v) {
            double var2 = this.x - v.x;
            double var4 = this.y - v.y;
            double var6 = this.z - v.z;
            return Math.sqrt(var2 * var2 + var4 * var4 + var6 * var6);
        }
    }

    public class SearchThread extends Thread {
        public BlockESP that;

        public SearchThread(final BlockESP that) {
            super();
            this.that = that;
        }

        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                if (Module.nullCheck()) {
                    Thread.yield();
                } else {
                    ArrayList var1 = new ArrayList();

                    for (int var2 = (int)Math.floor(Main.mc.player.getX() - 512.0); (double)var2 <= Math.ceil(Main.mc.player.getX() + 512.0); var2++) {
                        for (int var3 = Main.mc.world.getBottomY() + 1; var3 <= Main.mc.world.getTopY(); var3++) {
                            for (int var4 = (int)Math.floor(Main.mc.player.getZ() - 512.0);
                                 (double)var4 <= Math.ceil(Main.mc.player.getZ() + 512.0);
                                 var4++
                            ) {
                                BlockPos var5 = new BlockPos(var2, var3, var4);
                                if (!Main.mc.world.isAir(var5) && this.that.shouldAdd(Main.mc.world.getBlockState(var5).getBlock())) {
                                    var1.add(new BlockVec((double)var5.getX(), (double)var5.getY(), (double)var5.getZ()));
                                }
                            }
                        }
                    }

                    BlockESP.blocks.clear();
                    BlockESP.blocks.addAll(var1);
                }
            }
        }
    }

}
