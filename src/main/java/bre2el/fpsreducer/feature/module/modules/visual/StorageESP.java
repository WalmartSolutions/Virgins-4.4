package bre2el.fpsreducer.feature.module.modules.visual;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.event.impl.RenderWorldEvent;
import bre2el.fpsreducer.event.orbit.EventHandler;
import bre2el.fpsreducer.feature.module.Module;
import bre2el.fpsreducer.feature.module.Module.Category;
import bre2el.fpsreducer.feature.module.setting.KeyBindSetting;
import bre2el.fpsreducer.feature.module.setting.Setting;
import bre2el.fpsreducer.util.BlockUtil;
import bre2el.fpsreducer.util.RenderUtil;
import bre2el.fpsreducer.util.RenderUtil.RenderHelper;
import java.awt.Color;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.block.entity.DropperBlockEntity;
import net.minecraft.block.entity.EnchantingTableBlockEntity;
import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.block.entity.FurnaceBlockEntity;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.block.entity.TrappedChestBlockEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.ChestBoatEntity;
import net.minecraft.entity.vehicle.ChestMinecartEntity;
import net.minecraft.entity.vehicle.HopperMinecartEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class StorageESP extends Module {
    public static String[] cm;
    public static byte[] cn;
    public KeyBindSetting bind;

    public StorageESP() {
        super(cm[3], cm[4], Category.Visual);
        this.bind = new KeyBindSetting(cm[5], 0, false);
        this.addSettings(new Setting[]{this.bind});
    }

    public static void du() {
        cn = new byte[16];
        cn[7] = -109;
        cn[13] = -79;
        cn[0] = 95;
        cn[15] = 37;
        cn[8] = 29;
        cn[3] = 114;
        cn[12] = -28;
        cn[2] = -79;
        cn[4] = -70;
        cn[10] = -61;
        cn[1] = -94;
        cn[5] = -31;
        cn[11] = -58;
        cn[14] = -125;
        cn[9] = 37;
        cn[6] = -98;
    }

    @EventHandler
    void onRenderWorld(RenderWorldEvent event) {
        if (!nullCheck()) {
            MatrixStack var2 = event.matrices;

            for (BlockEntity var4 : BlockUtil.blockEntities()) {
                if (var4 instanceof ShulkerBoxBlockEntity) {
                    BlockPos var5 = var4.getPos();
                    RenderHelper.setMatrixStack(var2);
                    RenderUtil.setCameraAction();
                    RenderHelper.getPositionMatrix().set(var2.peek().getPositionMatrix());
                    RenderUtil.renderFilledBox(
                            var2,
                            (float)var5.getX(),
                            (float)var5.getY(),
                            (float)var5.getZ(),
                            (float)(var5.getX() + 1),
                            (float)(var5.getY() + 1),
                            (float)(var5.getZ() + 1),
                            new Color(255, 255, 255, 160)
                    );
                    RenderUtil.renderOutlinedBox(
                            var2,
                            (float)var5.getX(),
                            (float)var5.getY(),
                            (float)var5.getZ(),
                            (float)(var5.getX() + 1),
                            (float)(var5.getY() + 1),
                            (float)(var5.getZ() + 1),
                            new Color(255, 255, 255, 160)
                    );
                    MatrixStack var6 = RenderHelper.getMatrixStack();
                    var6.pop();
                }

                if (var4 instanceof ChestBlockEntity) {
                    BlockPos var9 = var4.getPos();
                    RenderHelper.setMatrixStack(var2);
                    RenderUtil.setCameraAction();
                    RenderHelper.getPositionMatrix().set(var2.peek().getPositionMatrix());
                    RenderUtil.renderFilledBox(
                            var2,
                            (float)var9.getX(),
                            (float)var9.getY(),
                            (float)var9.getZ(),
                            (float)(var9.getX() + 1),
                            (float)(var9.getY() + 1),
                            (float)(var9.getZ() + 1),
                            new Color(219, 145, 26, 160)
                    );
                    RenderUtil.renderOutlinedBox(
                            var2,
                            (float)var9.getX(),
                            (float)var9.getY(),
                            (float)var9.getZ(),
                            (float)(var9.getX() + 1),
                            (float)(var9.getY() + 1),
                            (float)(var9.getZ() + 1),
                            new Color(219, 145, 26, 160)
                    );
                    MatrixStack var22 = RenderHelper.getMatrixStack();
                    var22.pop();
                }

                if (var4 instanceof TrappedChestBlockEntity) {
                    BlockPos var10 = var4.getPos();
                    RenderHelper.setMatrixStack(var2);
                    RenderUtil.setCameraAction();
                    RenderHelper.getPositionMatrix().set(var2.peek().getPositionMatrix());
                    RenderUtil.renderFilledBox(
                            var2,
                            (float)var10.getX(),
                            (float)var10.getY(),
                            (float)var10.getZ(),
                            (float)(var10.getX() + 1),
                            (float)(var10.getY() + 1),
                            (float)(var10.getZ() + 1),
                            new Color(219, 145, 26, 160)
                    );
                    RenderUtil.renderOutlinedBox(
                            var2,
                            (float)var10.getX(),
                            (float)var10.getY(),
                            (float)var10.getZ(),
                            (float)(var10.getX() + 1),
                            (float)(var10.getY() + 1),
                            (float)(var10.getZ() + 1),
                            new Color(219, 145, 26, 160)
                    );
                    MatrixStack var23 = RenderHelper.getMatrixStack();
                    var23.pop();
                }

                if (var4 instanceof EnderChestBlockEntity) {
                    BlockPos var11 = var4.getPos();
                    RenderHelper.setMatrixStack(var2);
                    RenderUtil.setCameraAction();
                    RenderHelper.getPositionMatrix().set(var2.peek().getPositionMatrix());
                    RenderUtil.renderFilledBox(
                            var2,
                            (float)var11.getX(),
                            (float)var11.getY(),
                            (float)var11.getZ(),
                            (float)(var11.getX() + 1),
                            (float)(var11.getY() + 1),
                            (float)(var11.getZ() + 1),
                            new Color(135, 26, 219, 160)
                    );
                    RenderUtil.renderOutlinedBox(
                            var2,
                            (float)var11.getX(),
                            (float)var11.getY(),
                            (float)var11.getZ(),
                            (float)(var11.getX() + 1),
                            (float)(var11.getY() + 1),
                            (float)(var11.getZ() + 1),
                            new Color(135, 26, 219, 160)
                    );
                    MatrixStack var24 = RenderHelper.getMatrixStack();
                    var24.pop();
                }

                if (var4 instanceof BarrelBlockEntity) {
                    BlockPos var12 = var4.getPos();
                    RenderHelper.setMatrixStack(var2);
                    RenderUtil.setCameraAction();
                    RenderHelper.getPositionMatrix().set(var2.peek().getPositionMatrix());
                    RenderUtil.renderFilledBox(
                            var2,
                            (float)var12.getX(),
                            (float)var12.getY(),
                            (float)var12.getZ(),
                            (float)(var12.getX() + 1),
                            (float)(var12.getY() + 1),
                            (float)(var12.getZ() + 1),
                            new Color(219, 145, 26, 160)
                    );
                    RenderUtil.renderOutlinedBox(
                            var2,
                            (float)var12.getX(),
                            (float)var12.getY(),
                            (float)var12.getZ(),
                            (float)(var12.getX() + 1),
                            (float)(var12.getY() + 1),
                            (float)(var12.getZ() + 1),
                            new Color(219, 145, 26, 160)
                    );
                    MatrixStack var25 = RenderHelper.getMatrixStack();
                    var25.pop();
                }

                if (var4 instanceof EnchantingTableBlockEntity) {
                    BlockPos var13 = var4.getPos();
                    RenderHelper.setMatrixStack(var2);
                    RenderUtil.setCameraAction();
                    RenderHelper.getPositionMatrix().set(var2.peek().getPositionMatrix());
                    RenderUtil.renderFilledBox(
                            var2,
                            (float)var13.getX(),
                            (float)var13.getY(),
                            (float)var13.getZ(),
                            (float)(var13.getX() + 1),
                            (float)(var13.getY() + 1),
                            (float)(var13.getZ() + 1),
                            new Color(0, 255, 255, 160)
                    );
                    RenderUtil.renderOutlinedBox(
                            var2,
                            (float)var13.getX(),
                            (float)var13.getY(),
                            (float)var13.getZ(),
                            (float)(var13.getX() + 1),
                            (float)(var13.getY() + 1),
                            (float)(var13.getZ() + 1),
                            new Color(0, 255, 255, 160)
                    );
                    MatrixStack var26 = RenderHelper.getMatrixStack();
                    var26.pop();
                }

                if (var4 instanceof FurnaceBlockEntity) {
                    BlockPos var14 = var4.getPos();
                    RenderHelper.setMatrixStack(var2);
                    RenderUtil.setCameraAction();
                    RenderHelper.getPositionMatrix().set(var2.peek().getPositionMatrix());
                    RenderUtil.renderFilledBox(
                            var2,
                            (float)var14.getX(),
                            (float)var14.getY(),
                            (float)var14.getZ(),
                            (float)(var14.getX() + 1),
                            (float)(var14.getY() + 1),
                            (float)(var14.getZ() + 1),
                            new Color(50, 50, 50, 160)
                    );
                    RenderUtil.renderOutlinedBox(
                            var2,
                            (float)var14.getX(),
                            (float)var14.getY(),
                            (float)var14.getZ(),
                            (float)(var14.getX() + 1),
                            (float)(var14.getY() + 1),
                            (float)(var14.getZ() + 1),
                            new Color(50, 50, 50, 160)
                    );
                    MatrixStack var27 = RenderHelper.getMatrixStack();
                    var27.pop();
                }

                if (var4 instanceof HopperBlockEntity) {
                    BlockPos var15 = var4.getPos();
                    RenderHelper.setMatrixStack(var2);
                    RenderUtil.setCameraAction();
                    RenderHelper.getPositionMatrix().set(var2.peek().getPositionMatrix());
                    RenderUtil.renderFilledBox(
                            var2,
                            (float)var15.getX(),
                            (float)var15.getY(),
                            (float)var15.getZ(),
                            (float)(var15.getX() + 1),
                            (float)(var15.getY() + 1),
                            (float)(var15.getZ() + 1),
                            new Color(50, 50, 50, 160)
                    );
                    RenderUtil.renderOutlinedBox(
                            var2,
                            (float)var15.getX(),
                            (float)var15.getY(),
                            (float)var15.getZ(),
                            (float)(var15.getX() + 1),
                            (float)(var15.getY() + 1),
                            (float)(var15.getZ() + 1),
                            new Color(50, 50, 50, 160)
                    );
                    MatrixStack var28 = RenderHelper.getMatrixStack();
                    var28.pop();
                }

                if (var4 instanceof DispenserBlockEntity) {
                    BlockPos var16 = var4.getPos();
                    RenderHelper.setMatrixStack(var2);
                    RenderUtil.setCameraAction();
                    RenderHelper.getPositionMatrix().set(var2.peek().getPositionMatrix());
                    RenderUtil.renderFilledBox(
                            var2,
                            (float)var16.getX(),
                            (float)var16.getY(),
                            (float)var16.getZ(),
                            (float)(var16.getX() + 1),
                            (float)(var16.getY() + 1),
                            (float)(var16.getZ() + 1),
                            new Color(50, 50, 50, 160)
                    );
                    RenderUtil.renderOutlinedBox(
                            var2,
                            (float)var16.getX(),
                            (float)var16.getY(),
                            (float)var16.getZ(),
                            (float)(var16.getX() + 1),
                            (float)(var16.getY() + 1),
                            (float)(var16.getZ() + 1),
                            new Color(50, 50, 50, 160)
                    );
                    MatrixStack var29 = RenderHelper.getMatrixStack();
                    var29.pop();
                }

                if (var4 instanceof DropperBlockEntity) {
                    BlockPos var17 = var4.getPos();
                    RenderHelper.setMatrixStack(var2);
                    RenderUtil.setCameraAction();
                    RenderHelper.getPositionMatrix().set(var2.peek().getPositionMatrix());
                    RenderUtil.renderFilledBox(
                            var2,
                            (float)var17.getX(),
                            (float)var17.getY(),
                            (float)var17.getZ(),
                            (float)(var17.getX() + 1),
                            (float)(var17.getY() + 1),
                            (float)(var17.getZ() + 1),
                            new Color(50, 50, 50, 160)
                    );
                    RenderUtil.renderOutlinedBox(
                            var2,
                            (float)var17.getX(),
                            (float)var17.getY(),
                            (float)var17.getZ(),
                            (float)(var17.getX() + 1),
                            (float)(var17.getY() + 1),
                            (float)(var17.getZ() + 1),
                            new Color(50, 50, 50, 160)
                    );
                    MatrixStack var30 = RenderHelper.getMatrixStack();
                    var30.pop();
                }

                if (var4 instanceof MobSpawnerBlockEntity) {
                    BlockPos var18 = var4.getPos();
                    RenderHelper.setMatrixStack(var2);
                    RenderUtil.setCameraAction();
                    RenderHelper.getPositionMatrix().set(var2.peek().getPositionMatrix());
                    RenderUtil.renderFilledBox(
                            var2,
                            (float)var18.getX(),
                            (float)var18.getY(),
                            (float)var18.getZ(),
                            (float)(var18.getX() + 1),
                            (float)(var18.getY() + 1),
                            (float)(var18.getZ() + 1),
                            new Color(0, 150, 0, 160)
                    );
                    RenderUtil.renderOutlinedBox(
                            var2,
                            (float)var18.getX(),
                            (float)var18.getY(),
                            (float)var18.getZ(),
                            (float)(var18.getX() + 1),
                            (float)(var18.getY() + 1),
                            (float)(var18.getZ() + 1),
                            new Color(0, 150, 0, 160)
                    );
                    MatrixStack var31 = RenderHelper.getMatrixStack();
                    var31.pop();
                }
            }

            for (Entity var8 : Main.mc.world.getEntities()) {
                if (var8 instanceof ChestBoatEntity) {
                    Vec3d var19 = RenderUtil.getEntityPos(var8);
                    RenderHelper.setMatrixStack(var2);
                    RenderUtil.setCameraAction();
                    RenderHelper.getPositionMatrix().set(var2.peek().getPositionMatrix());
                    RenderUtil.renderFilledBox(
                            var2,
                            (float)var19.x - var8.getWidth() / 2.0F,
                            (float)var19.y,
                            (float)var19.z - var8.getWidth() / 2.0F,
                            (float)var19.x + var8.getWidth() / 2.0F,
                            (float)var19.y + var8.getHeight(),
                            (float)var19.z + var8.getWidth() / 2.0F,
                            new Color(219, 145, 26, 160)
                    );
                    RenderUtil.renderOutlinedBox(
                            var2,
                            (float)var19.x - var8.getWidth() / 2.0F,
                            (float)var19.y,
                            (float)var19.z - var8.getWidth() / 2.0F,
                            (float)var19.x + var8.getWidth() / 2.0F,
                            (float)var19.y + var8.getHeight(),
                            (float)var19.z + var8.getWidth() / 2.0F,
                            new Color(219, 145, 26, 160)
                    );
                    MatrixStack var32 = RenderHelper.getMatrixStack();
                    var32.pop();
                }

                if (var8 instanceof ChestMinecartEntity) {
                    Vec3d var20 = RenderUtil.getEntityPos(var8);
                    RenderHelper.setMatrixStack(var2);
                    RenderUtil.setCameraAction();
                    RenderHelper.getPositionMatrix().set(var2.peek().getPositionMatrix());
                    RenderUtil.renderFilledBox(
                            var2,
                            (float)var20.x - var8.getWidth() / 2.0F,
                            (float)var20.y,
                            (float)var20.z - var8.getWidth() / 2.0F,
                            (float)var20.x + var8.getWidth() / 2.0F,
                            (float)var20.y + var8.getHeight(),
                            (float)var20.z + var8.getWidth() / 2.0F,
                            new Color(219, 145, 26, 160)
                    );
                    RenderUtil.renderOutlinedBox(
                            var2,
                            (float)var20.x - var8.getWidth() / 2.0F,
                            (float)var20.y,
                            (float)var20.z - var8.getWidth() / 2.0F,
                            (float)var20.x + var8.getWidth() / 2.0F,
                            (float)var20.y + var8.getHeight(),
                            (float)var20.z + var8.getWidth() / 2.0F,
                            new Color(219, 145, 26, 160)
                    );
                    MatrixStack var33 = RenderHelper.getMatrixStack();
                    var33.pop();
                }

                if (var8 instanceof HopperMinecartEntity) {
                    Vec3d var21 = RenderUtil.getEntityPos(var8);
                    RenderHelper.setMatrixStack(var2);
                    RenderUtil.setCameraAction();
                    RenderHelper.getPositionMatrix().set(var2.peek().getPositionMatrix());
                    RenderUtil.renderFilledBox(
                            var2,
                            (float)var21.x - var8.getWidth() / 2.0F,
                            (float)var21.y,
                            (float)var21.z - var8.getWidth() / 2.0F,
                            (float)var21.x + var8.getWidth() / 2.0F,
                            (float)var21.y + var8.getHeight(),
                            (float)var21.z + var8.getWidth() / 2.0F,
                            new Color(50, 50, 50, 160)
                    );
                    RenderUtil.renderOutlinedBox(
                            var2,
                            (float)var21.x - var8.getWidth() / 2.0F,
                            (float)var21.y,
                            (float)var21.z - var8.getWidth() / 2.0F,
                            (float)var21.x + var8.getWidth() / 2.0F,
                            (float)var21.y + var8.getHeight(),
                            (float)var21.z + var8.getWidth() / 2.0F,
                            new Color(50, 50, 50, 160)
                    );
                    MatrixStack var34 = RenderHelper.getMatrixStack();
                    var34.pop();
                }
            }
        }
    }

    public static String ds(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{102, -48, -102, 70, 8, 112, 90, 33, 9, -101, -6, 0, 26, -18, 62, 126};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 109, 256);
        SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] var8 = var7.generateSecret(var6).getEncoded();
        SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
        Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var10.init(2, var9, new IvParameterSpec(var2));
        byte[] var11 = var10.doFinal(var5);
        return new String(var11, "UTF-8");
    }

    static {
        du();
        dt();
    }

    public static void dt() {
        try {
            cm = new String[6];
            cm[2] = ds("qKeiySutNm2+oUeR0rJx8l+isXK64Z6THSXDxuSxgyVm8xyWQDVn7O/+1pwhLO/hIpWwIczagg5RXoTUARZhyQ==", "��Ѵ]�", cn);
            cm[0] = ds("wqcWF16CCWyQn5BI/SutH1+isXK64Z6THSXDxuSxgyUeL25OzqlzJPrwBVXvUOSf", "!1�5��", cn);
            cm[5] = ds("R2JetoO4YNURG5glkKOnnl+isXK64Z6THSXDxuSxgyVsK2mImRu2G9oI5Zw91ApF5o0w9wzk1Q/dxNBPeXTO4w==", "?�)\u0006��", cn);
            cm[4] = ds("7oFKtxwOYYPKhMVygRMCll+isXK64Z6THSXDxuSxgyV+ykSAqcC19cGQbTG048mJj01eoDq0FIncJ7NP8Rwh8g==", "����iT", cn);
            cm[1] = ds("++EbnD9ffQYQ/7PFDqpUvV+isXK64Z6THSXDxuSxgyX59cjMSkLd5mKiLtTeXpTuMK382CE3v/WjHCH8dpfn0g==", "P�1\".�", cn);
            cm[3] = ds("ixa+SWvjdtF1qMRVlrl3rV+isXK64Z6THSXDxuSxgyXXhq0QkS9q4hDyXs+80lN4", "3��\u001a�[", cn);
        } catch (Exception e) {
        }
    }
}
