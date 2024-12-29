package bre2el.fpsreducer.gui.hud.impl;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.client.Main.Theme;
import bre2el.fpsreducer.gui.font.FontRenderers;
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

public class KeystrokesRenderer implements Drawable {
    public int y;
    public static byte[] j;
    public int r;
    public int x = 0;
    public int height;
    public static String[] i;
    public int dragX;
    public int a;
    public int width;
    public int dragY;
    public int l;
    public int w;
    public int d;
    public static KeystrokesRenderer INSTANCE = new KeystrokesRenderer();
    public int s;
    public boolean dragging;

    public void renderHUD(DrawContext context) {
        if (Main.mc.options.forwardKey.isPressed() && this.w < 155) {
            this.w++;
        }

        if (!Main.mc.options.forwardKey.isPressed() && this.w > 100) {
            this.w--;
        }

        Color var2 = new Color(20, 20, 20, 100 + this.w);
        RenderUtil.renderRoundedRect(context.getMatrices(), (float)this.x, (float)this.y, (float)(this.x + this.width), (float)(this.y + this.height), var2, 5.0F);
        FontRenderers.Title.drawCenteredString(context.getMatrices(), i[12], (double)(this.x + 15), (double)(this.y + 8), Color.BLACK.getRGB());
        FontRenderers.Title
                .drawCenteredString(
                        context.getMatrices(),
                        i[13],
                        (double)(this.x + 15),
                        (double)(this.y + 8),
                        !Main.mc.options.forwardKey.isPressed() ? Color.WHITE.getRGB() : Theme.PRIMARY.getRGB()
                );
        if (Main.mc.options.leftKey.isPressed() && this.a < 155) {
            this.a++;
        }

        if (!Main.mc.options.leftKey.isPressed() && this.a > 100) {
            this.a--;
        }

        Color var3 = new Color(20, 20, 20, 100 + this.a);
        RenderUtil.renderRoundedRect(
                context.getMatrices(), (float)(this.x - 30), (float)(this.y + 30), (float)(this.x - 30 + this.width), (float)(this.y + 30 + this.height), var3, 5.0F
        );
        FontRenderers.Title.drawCenteredString(context.getMatrices(), i[14], (double)(this.x - 15), (double)(this.y + 37), Color.BLACK.getRGB());
        FontRenderers.Title
                .drawCenteredString(
                        context.getMatrices(),
                        i[15],
                        (double)(this.x - 15),
                        (double)(this.y + 37),
                        !Main.mc.options.leftKey.isPressed() ? Color.WHITE.getRGB() : Theme.PRIMARY.getRGB()
                );
        if (Main.mc.options.backKey.isPressed() && this.s < 155) {
            this.s++;
        }

        if (!Main.mc.options.backKey.isPressed() && this.s > 100) {
            this.s--;
        }

        Color var4 = new Color(20, 20, 20, 100 + this.s);
        RenderUtil.renderRoundedRect(
                context.getMatrices(), (float)this.x, (float)(this.y + 30), (float)(this.x + this.width), (float)(this.y + 30 + this.height), var4, 5.0F
        );
        FontRenderers.Title.drawCenteredString(context.getMatrices(), i[16], (double)(this.x + 15), (double)(this.y + 37), Color.BLACK.getRGB());
        FontRenderers.Title
                .drawCenteredString(
                        context.getMatrices(),
                        i[17],
                        (double)(this.x + 15),
                        (double)(this.y + 37),
                        !Main.mc.options.backKey.isPressed() ? Color.WHITE.getRGB() : Theme.PRIMARY.getRGB()
                );
        if (Main.mc.options.rightKey.isPressed() && this.d < 155) {
            this.d++;
        }

        if (!Main.mc.options.rightKey.isPressed() && this.d > 100) {
            this.d--;
        }

        Color var5 = new Color(20, 20, 20, 100 + this.d);
        RenderUtil.renderRoundedRect(
                context.getMatrices(), (float)(this.x + 30), (float)(this.y + 30), (float)(this.x + 30 + this.width), (float)(this.y + 30 + this.height), var5, 5.0F
        );
        FontRenderers.Title.drawCenteredString(context.getMatrices(), i[18], (double)(this.x + 45), (double)(this.y + 37), Color.BLACK.getRGB());
        FontRenderers.Title
                .drawCenteredString(
                        context.getMatrices(),
                        i[19],
                        (double)(this.x + 45),
                        (double)(this.y + 37),
                        !Main.mc.options.rightKey.isPressed() ? Color.WHITE.getRGB() : Theme.PRIMARY.getRGB()
                );
        if (GLFW.glfwGetMouseButton(Main.mc.getWindow().getHandle(), 0) == 1 && this.l < 155) {
            this.l++;
        }

        if (GLFW.glfwGetMouseButton(Main.mc.getWindow().getHandle(), 0) != 1 && this.l > 100) {
            this.l--;
        }

        Color var6 = new Color(20, 20, 20, 100 + this.l);
        RenderUtil.renderRoundedRect(context.getMatrices(), (float)(this.x - 30), (float)(this.y + 62), (float)(this.x + 14), (float)(this.y + 90), var6, 5.0F);
        FontRenderers.Title.drawCenteredString(context.getMatrices(), i[20], (double)(this.x - 15), (double)(this.y + 74), Color.BLACK.getRGB());
        FontRenderers.Title
                .drawCenteredString(
                        context.getMatrices(),
                        i[21],
                        (double)(this.x - 15),
                        (double)(this.y + 74),
                        GLFW.glfwGetMouseButton(Main.mc.getWindow().getHandle(), 0) != 1 ? Color.WHITE.getRGB() : Theme.PRIMARY.getRGB()
                );
        if (GLFW.glfwGetMouseButton(Main.mc.getWindow().getHandle(), 1) == 1 && this.r < 155) {
            this.r++;
        }

        if (GLFW.glfwGetMouseButton(Main.mc.getWindow().getHandle(), 1) != 1 && this.r > 100) {
            this.r--;
        }

        Color var7 = new Color(20, 20, 20, 100 + this.r);
        RenderUtil.renderRoundedRect(
                context.getMatrices(), (float)(this.x + 16), (float)(this.y + 62), (float)(this.x + 30 + this.width), (float)(this.y + 90), var7, 5.0F
        );
        FontRenderers.Title.drawCenteredString(context.getMatrices(), i[22], (double)(this.x + 15 + this.width), (double)(this.y + 74), Color.BLACK.getRGB());
        FontRenderers.Title
                .drawCenteredString(
                        context.getMatrices(),
                        i[23],
                        (double)(this.x + 15 + this.width),
                        (double)(this.y + 74),
                        GLFW.glfwGetMouseButton(Main.mc.getWindow().getHandle(), 1) != 1 ? Color.WHITE.getRGB() : Theme.PRIMARY.getRGB()
                );
    }

    public KeystrokesRenderer() {
        this.y = 0;
        this.width = 30;
        this.height = 30;
        this.w = 100;
        this.a = 100;
        this.s = 100;
        this.d = 100;
        this.l = 100;
        this.r = 100;
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
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

    public static void o() {
        j = new byte[16];
        j[0] = 118;
        j[5] = 60;
        j[12] = 33;
        j[15] = 2;
        j[9] = 85;
        j[13] = 124;
        j[3] = -89;
        j[11] = 87;
        j[6] = 7;
        j[4] = -112;
        j[2] = -62;
        j[1] = 31;
        j[10] = 83;
        j[14] = 46;
        j[7] = -40;
        j[8] = -55;
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isHovered(int mouseY, int mouseX) {
        return mouseY > this.x && mouseY < this.x + this.width && mouseX > this.y && mouseX < this.y + this.height;
    }

    static {
        o();
        n();
    }

    public static String m(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{21, -77, 0, 7, 77, 19, 107, -17, 102, 112, 88, -79, 100, 28, 23, 93};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 52, 256);
        SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] var8 = var7.generateSecret(var6).getEncoded();
        SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
        Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var10.init(2, var9, new IvParameterSpec(var2));
        byte[] var11 = var10.doFinal(var5);
        String info = new String(var11, "UTF-8");
        return info;
    }

    public static void n() {
        try {
            i = new String[24];
            i[4] = m("4Vwk8MHkVfzy7B5m+0/EQ3YfwqeQPAfYyVVTVyF8LgI717zXVgXyE/hUxOQTDh2O", "��\b^�#", j);
            i[20] = m("PhZrvxeUxIfeRJw0vmV3J3YfwqeQPAfYyVVTVyF8LgKKYiBnED2RL+NW7LbmH13F", "�֜��m", j);
            i[2] = m("WPvUvTybi7cz4tPXxKwoIXYfwqeQPAfYyVVTVyF8LgLHiWzi5hGfDA6SH+r9HI1/", "�/w\u0006L�", j);
            i[6] = m("ouHYRFpfNW/xczXv5DO8YXYfwqeQPAfYyVVTVyF8LgLVxKG3b36GPVMzPY+Ev5JP", "!\u0016nV�P", j);
            i[0] = m("dmbI3zYGeSkCqBkyiJMi7XYfwqeQPAfYyVVTVyF8LgI0DgI/R984SPzt82wuR287", "]�ݒSV", j);
            i[23] = m("wy7DqJtbv6jGzCV+77I6O3YfwqeQPAfYyVVTVyF8LgJLvr1FuJfkk6YtrY7nYs28", "\u0002��6��", j);
            i[12] = m("T4AyQHy93GK00wTSfuv9KnYfwqeQPAfYyVVTVyF8LgILo5hV2vx5jbXtj2+5SxY9", "�A�\u001b�G", j);
            i[11] = m("GuSFNeabujsPr1NBXZ5mtXYfwqeQPAfYyVVTVyF8LgLE0AmRR5RmSmTwWAz8pVFi", "\u0018NN\u0013Ch", j);
            i[5] = m("4MamBbVvNmQFj5RGtURnJHYfwqeQPAfYyVVTVyF8LgKQ9887pCIlG3lueKDaH5++", "(�z \u0005�", j);
            i[16] = m("T4F4Polo7p1IEbTlWWizSXYfwqeQPAfYyVVTVyF8LgL9UHNb+3DFqme88eI7IXx5", "E�Qu\u001a?", j);
            i[1] = m("mKYbn9ntQLbHLlPZueoZY3YfwqeQPAfYyVVTVyF8LgIs/pOLXofYrgRt2X+XRtdC", "c�p�\u0011�", j);
            i[21] = m("UcXTL5PNmAGp7oY2boCmkXYfwqeQPAfYyVVTVyF8LgK2MxOa3+RnDBILKVHTF2yr", "��f�i\u0004", j);
            i[19] = m("QIWOd5JCGbnbsY/DKroT5XYfwqeQPAfYyVVTVyF8LgK7juwJ7GUatffHIdNBDFU5", "��\u0019?�\u000e", j);
            i[22] = m("pNS47Qxa9KT12rTME+gIPXYfwqeQPAfYyVVTVyF8LgIrrQadvXEUJAQzGqrS5AtV", "\u001fl�D\\�", j);
            i[15] = m("8wDIuIkVpL9Ira8Au82E43YfwqeQPAfYyVVTVyF8LgIxlMEWBNNJTwa4Bue6Tm4c", "�����T", j);
            i[9] = m("DH0eikAqyVlbsmTn99p6U3YfwqeQPAfYyVVTVyF8LgKzBvSsPLOb8+xOcgxQ31dX", "a�מ�A", j);
            i[17] = m("SPx6QEZTUGz0Ihh+H4dyEnYfwqeQPAfYyVVTVyF8LgKTBxZIjZWd4VXsnHit1vTy", "\u0014��D\u0010\n", j);
            i[8] = m("Q/f+4D8M736pmVctyWiVEHYfwqeQPAfYyVVTVyF8LgKMn9/UZeCudy3864CmQ/hh", "}����", j);
            i[13] = m("/H0M+9RmGPTfsh59sVd5hXYfwqeQPAfYyVVTVyF8LgIM9yBSh+cG4/VAWnzzpmgI", "�\u0010pTX=", j);
            i[3] = m("NmQnIw712Z4ljrh8yuEwCXYfwqeQPAfYyVVTVyF8LgKaD9EZZdY5ZdcuknLBLqWv", "HM:\u000fTY", j);
            i[18] = m("ZkIKtmlSrCjoo5oC4jkWrXYfwqeQPAfYyVVTVyF8LgJbR638iSIJPytPxSc2//94", "?\u0015�WV�", j);
            i[7] = m("KxfG8Fia9pxpxeywN0PJ1HYfwqeQPAfYyVVTVyF8LgJmB04BrzrIATT9T9WsRMrh", "���-{�", j);
            i[10] = m("jWoN3xzUGu5XxTjz2YbcanYfwqeQPAfYyVVTVyF8LgLAmRCgbJ5v2KzevKn2zIzb", "���D_[", j);
            i[14] = m("L60ioK4imKDueHVWmUNR9HYfwqeQPAfYyVVTVyF8LgKg6IncsjRBQj3sj5Wli2bt", "���\u0007GR", j);
        } catch (Exception e) {

        }
    }
}
