package bre2el.fpsreducer.gui.hud.impl;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.client.Main.Theme;
import bre2el.fpsreducer.gui.font.FontRenderers;
import bre2el.fpsreducer.util.ColorUtil;
import bre2el.fpsreducer.util.RenderUtil;
import java.awt.Color;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import org.lwjgl.glfw.GLFW;

public class WatermarkRenderer implements Drawable {
    public int dragX;
    public static String[] yx;
    public static byte[] z;
    public int y;
    public int x = 0;
    public int dragY;
    public boolean dragging;
    public int height;
    public int width;
    public static WatermarkRenderer INSTANCE = new WatermarkRenderer();

    public void render(DrawContext delta, int mouseX, int mouseY, float context) {
        if (this.isHovered(mouseX, mouseY) && GLFW.glfwGetMouseButton(Main.mc.getWindow().getHandle(), 0) == 1) {
            this.dragging = true;
            this.dragX = mouseX - this.x;
            this.dragY = mouseY - this.y;
        }

        if (this.dragging) {
            this.x = mouseX - this.dragX;
            this.y = mouseY - this.dragY;
        }

        if (GLFW.glfwGetMouseButton(Main.mc.getWindow().getHandle(), 0) == 0) {
            this.dragging = false;
        }
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX > this.x && mouseX < this.x + this.width && mouseY > this.y && mouseY < this.y + this.height;
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static String K(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{-51, 50, 2, 24, -62, -98, -57, -115, -17, -60, 0, 10, -47, 24, 77, 11};
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
        String info = new String(var11, "UTF-8");
        return info;
    }

    public static void L() {
        try {
            yx = new String[4];
            yx[3] = K("MXcKhxuY1YxYHxKyCli0DVEhwbq78YglMmgtmvHeMRlca5S/zDUYM6LNQuuHjfZ5", "Ay\n�<\u001d", z);
            yx[1] = K("dehNni1RtVAgZEvoVjsFklEhwbq78YglMmgtmvHeMRlkInLluvjaQtFVB1DlroDl", "�%��m", z);
            yx[2] = K("xPpqnXe9IdT2HILqud1tpFEhwbq78YglMmgtmvHeMRm9YlrMJ/OeQUIw9UZZUE+k", "(�yGH�", z);
            yx[0] = K("c74B2JDN51EP1+/LDBUeOVEhwbq78YglMmgtmvHeMRni0jRAuugGGOrK8q+64JsS", "�Eu�M`", z);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public WatermarkRenderer() {
        this.y = 0;
        this.width = 0;
        this.height = 16;
    }

    public static void M() {
        z = new byte[16];
        z[11] = -102;
        z[6] = -120;
        z[0] = 81;
        z[7] = 37;
        z[14] = 49;
        z[15] = 25;
        z[12] = -15;
        z[5] = -15;
        z[2] = -63;
        z[10] = 45;
        z[3] = -70;
        z[13] = -34;
        z[4] = -69;
        z[8] = 50;
        z[1] = 33;
        z[9] = 104;
    }

    public void renderHUD(DrawContext context) {
        this.width = (int)FontRenderers.Main.getStringWidth(" Virgin Client " + Main.VERSION) + 4;
        RenderUtil.drawBlurredShadow(context.getMatrices(), (float)this.x, (float)this.y, (float)(this.width + 2), (float)(this.height + 2), 16, Color.BLACK);
        RenderUtil.renderRoundedRect(
                context.getMatrices(),
                (float)(this.x - 1),
                (float)(this.y - 1),
                (float)(this.x + this.width + 3),
                (float)(this.y + this.height + 3),
                ColorUtil.TwoColoreffect(Theme.PRIMARY, Theme.SECONDARY, 7.0, 2.0),
                3.0F
        );
        RenderUtil.renderRoundedRect(
                context.getMatrices(), (float)this.x, (float)this.y, (float)(this.x + this.width + 2), (float)(this.y + this.height + 2), Color.BLACK, 3.0F
        );
        FontRenderers.Main.drawString(context.getMatrices(), " Virgin Client " + Main.VERSION, (float)(this.x + 3), (float)(this.y + 6), Color.BLACK.getRGB());
        FontRenderers.Main
                .drawString(
                        context.getMatrices(),
                        Main.VERSION,
                        (float)(this.x + 2) + FontRenderers.Main.getStringWidth(yx[2]),
                        (float)(this.y + 5),
                        ColorUtil.TwoColoreffect(Theme.PRIMARY, Theme.SECONDARY, 7.0, 2.0).getRGB()
                );
        FontRenderers.Main
                .drawString(
                        context.getMatrices(), yx[3], (float)(this.x + 2), (float)(this.y + 5), ColorUtil.TwoColoreffect(Color.WHITE, Color.LIGHT_GRAY, 7.0, 2.0).getRGB()
                );
    }

    static {
        M();
        L();
    }
}
