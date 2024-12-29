package bre2el.fpsreducer.feature.module.modules.visual;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.event.impl.RenderWorldEvent;
import bre2el.fpsreducer.event.orbit.EventHandler;
import bre2el.fpsreducer.feature.module.Module;
import bre2el.fpsreducer.feature.module.Module.Category;
import bre2el.fpsreducer.feature.module.setting.KeyBindSetting;
import bre2el.fpsreducer.feature.module.setting.Setting;
import bre2el.fpsreducer.util.PlayerUtil;
import bre2el.fpsreducer.util.RenderUtil;
import bre2el.fpsreducer.util.RenderUtil.RenderHelper;
import java.util.ArrayList;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;

public class Tracers extends Module {
    public static String[] eK;
    public static byte[] eL;
    public KeyBindSetting bind;

    @EventHandler
    public void onRenderWorld(RenderWorldEvent event) {
        if (!nullCheck()) {
            Vec3d var2 = this.getRenderPosition();

            for (PlayerEntity var4 : Main.mc.world.getPlayers()) {
                if (PlayerUtil.validateTarget(var4) && !(var4 instanceof ClientPlayerEntity)) {
                    RenderHelper.setMatrixStack(event.matrices);
                    ArrayList var5 = new ArrayList();
                    var5.add(var2);
                    var5.add(var4.getPos());
                    RenderUtil.setCameraAction();
                    RenderUtil.renderLines(var5, var4);
                    MatrixStack var6 = RenderHelper.getMatrixStack();
                    var6.pop();
                }
            }
        }
    }

    Vec3d getRenderPosition() {
        Vector3f var1 = new Vector3f(0.0F, 0.0F, 1.0F);
        if ((Boolean)Main.mc.options.getBobView().getValue()) {
            MatrixStack var2 = new MatrixStack();
            this.setMatrix(var2);
            var1.mulPosition(var2.peek().getPositionMatrix().invert());
        }

        return new Vec3d((double)var1.x, (double)(-var1.y), (double)var1.z)
                .rotateX(-((float)Math.toRadians((double)Main.mc.gameRenderer.getCamera().getPitch())))
                .rotateY(-((float)Math.toRadians((double)Main.mc.gameRenderer.getCamera().getYaw())))
                .add(Main.mc.gameRenderer.getCamera().getPos());
    }

    public static void hd() {
        try {
            eK = new String[6];
            eK[4] = hc(
                    "dHiGhyeUpoHeafwOpqTTCNLb3GBt/SASLeb+LIQMBSBG7I1FV+088hOmftfpHmWqC6V5Yiltjb5Ds1SNFMz85HHTMdirx0WfmUpdsijM/i7A5WLHpvsJ7Pkhiq74U6cq", "6��è�", eL
            );
            eK[0] = hc("yTrz60s90VYxaO5J4WE0RtLb3GBt/SASLeb+LIQMBSBMLprb5Ktcpr+T/v4uLu8x", "�'����", eL);
            eK[3] = hc("QUYq2K2tH4zooB1fxD2yGNLb3GBt/SASLeb+LIQMBSCdcPa953zm/tD1YhU/vDak", "�W\u001c(��", eL);
            eK[1] = hc(
                    "STNlKwTV/C3v+8MBzJbjw9Lb3GBt/SASLeb+LIQMBSCP9fU1LOGNO1JVj/GK2Lo2/ZDYuK9eJodmLWKHDTQWPv8cpfxe6OiXyo/4uQi8bve/0JavyRRnA7OhZ3cf0RZF", "����a�", eL
            );
            eK[5] = hc("ubTTZ+zmkbZUDdh633SviNLb3GBt/SASLeb+LIQMBSDAtiOLOlMzEswMh30QLZqGer+eZIQBkZ3ul14pHE91JQ==", "ϸ.\u0017ZG", eL);
            eK[2] = hc("xltLDudIQTSR+ZISkf2SGNLb3GBt/SASLeb+LIQMBSBoLXErTtzrzBwiN1lYClAJS7rj9cOXXj3DjXwFXqFVcA==", "z��\n�'", eL);
        } catch (Exception e) {
        }
    }

    public static String hc(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{-67, 99, -41, -124, -105, -128, 81, -72, 0, -91, -37, -91, -125, 85, 0, 114};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 83, 256);
        SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] var8 = var7.generateSecret(var6).getEncoded();
        SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
        Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var10.init(2, var9, new IvParameterSpec(var2));
        byte[] var11 = var10.doFinal(var5);
        return new String(var11, "UTF-8");
    }

    public static void he() {
        eL = new byte[16];
        eL[0] = -46;
        eL[5] = -3;
        eL[6] = 32;
        eL[9] = -26;
        eL[2] = -36;
        eL[7] = 18;
        eL[3] = 96;
        eL[8] = 45;
        eL[13] = 12;
        eL[1] = -37;
        eL[15] = 32;
        eL[14] = 5;
        eL[11] = 44;
        eL[4] = 109;
        eL[12] = -124;
        eL[10] = -2;
    }

    public Tracers() {
        super(eK[3], eK[4], Category.Visual);
        this.bind = new KeyBindSetting(eK[5], 0, false);
        this.addSettings(new Setting[]{this.bind});
    }

    static {
        he();
        hd();
    }

    void setMatrix(MatrixStack matrixStack) {
        Entity var2 = Main.mc.cameraEntity;
        if (var2 instanceof PlayerEntity var3) {
            float var4 = Main.mc.getRenderTickCounter().getTickDelta(true);
            float var5 = var2.horizontalSpeed - var2.prevHorizontalSpeed;
            float var6 = -(var2.horizontalSpeed + var5 * var4);
            float var7 = MathHelper.lerp(var4, var3.prevStrideDistance, var3.strideDistance);
            matrixStack.translate(
                    -((double)(MathHelper.sin(var6 * (float) Math.PI) * var7) * 0.5), (double)Math.abs(MathHelper.cos(var6 * (float) Math.PI) * var7), 0.0
            );
            matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(MathHelper.sin(var6 * (float) Math.PI) * var7 * 3.0F));
            matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(Math.abs(MathHelper.cos(var6 * (float) Math.PI - 0.2F) * var7) * 5.0F));
        }
    }
}
