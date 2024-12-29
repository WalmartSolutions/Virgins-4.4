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
import net.minecraft.entity.effect.StatusEffectInstance;
import org.lwjgl.glfw.GLFW;

public class PotionHUDRenderer implements Drawable {
    public static PotionHUDRenderer INSTANCE = new PotionHUDRenderer();
    public int dragX;
    public static String[] gI;
    public int height;
    public boolean dragging;
    public static byte[] gJ;
    public int dragY;
    public int x = 0;
    public int y = 100;
    public int width = 180;

    static {
        kb();
        ka();
    }

    public PotionHUDRenderer() {
        this.height = 30;
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
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

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX > this.x && mouseX < this.x + this.width && mouseY > this.y && mouseY < this.y + this.height + Main.mc.player.getStatusEffects().size() * 12;
    }

    public static void ka() {
        try {
            gI = new String[12];
            gI[3] = jZ("Xvcxbc5fLbptExiZDFJAqCZRESTsXZ5f5Z3B/jUWW2I2yxqo3dsjPKnIJmE9lGnB", "\u0013�Ȇ\\", gJ);
            gI[9] = jZ("Ia3QhNKZW/yjZ7V4McrloSZRESTsXZ5f5Z3B/jUWW2KOhSRwNuNNeOMg35TozhQi", "\u0017\u0016�Z��", gJ);
            gI[0] = jZ("PxwWmyuxOZr/paj95uopDCZRESTsXZ5f5Z3B/jUWW2Jjp5xpxKdZWV3SPk1TCw41", "}�%9��", gJ);
            gI[10] = jZ("stRC0bCOG/Kj/DEV7KlyZyZRESTsXZ5f5Z3B/jUWW2IeYKHc45Ml1Eo1jKRGhHsW", "]����E", gJ);
            gI[5] = jZ("fSWLuwAKR+MartqJJm/LTiZRESTsXZ5f5Z3B/jUWW2LPUYsK9WiZH1ogOiD2tkbI", "�6G�=q", gJ);
            gI[4] = jZ("auPkQRuYi0l0URE8WkbS3yZRESTsXZ5f5Z3B/jUWW2JNVxbbIkOpzVqNnsFJoBy9", "y\u000b=m\u0001�", gJ);
            gI[8] = jZ("WK1lg8n4MkBjLdkLmYskSSZRESTsXZ5f5Z3B/jUWW2KkbdmM5LmNtJ4ckSiTDYD1", "6Q�\u0015�u", gJ);
            gI[7] = jZ("kKLal10AU7E94oUcKMrQRyZRESTsXZ5f5Z3B/jUWW2JFvMg66f9u0was4Wf2Vwu/SbwwJ2dLNxbQy8ABLWiSKg==", "�\u001e��5A", gJ);
            gI[6] = jZ("BkdqlDtbeszdmmTTs1hNLCZRESTsXZ5f5Z3B/jUWW2KQIXgqTh9963zgbtB4WxUT", "�\u001a=�\u001f�", gJ);
            gI[1] = jZ("K96RHgBmaEhExiRCXbIqYCZRESTsXZ5f5Z3B/jUWW2IN4wrt7IMIWUSZTM8YVwb3DVJq1YKaz8Sc+x+8iZrc/w==", "1\u001a\u0018b��", gJ);
            gI[11] = jZ("7p6j/+pW6MP2CN76i45QLiZRESTsXZ5f5Z3B/jUWW2LeMrzTJ9PRYkt8KlNXo13o", "z�*�P;", gJ);
            gI[2] = jZ("J9O3AeiY7ubvf6pD8iRUuSZRESTsXZ5f5Z3B/jUWW2KWNy606777AWtYovefLfMh", "r�\u0013�0�", gJ);
        } catch (Exception e) {

        }
    }

    public void renderHUD(DrawContext context) {
        int var2 = 20;
        RenderUtil.drawBlurredShadow(
                context.getMatrices(),
                (float)this.x,
                (float)this.y,
                (float)this.width,
                (float)(this.height + Main.mc.player.getStatusEffects().size() * 12),
                16,
                Color.BLACK
        );
        RenderUtil.renderRoundedRect(
                context.getMatrices(),
                (float)this.x,
                (float)this.y,
                (float)(this.x + this.width),
                (float)(this.y + this.height + Main.mc.player.getStatusEffects().size() * 12),
                Color.BLACK,
                6.0F
        );
        context.drawHorizontalLine(this.x, this.x + this.width - 1, this.y + 18, ColorUtil.TwoColoreffect(Theme.PRIMARY, Theme.SECONDARY, 10.0, 2.0).getRGB());
        FontRenderers.Main
                .drawCenteredString(context.getMatrices(), gI[6], (double)((float)this.x + (float)this.width / 2.0F), (double)(this.y + 4), Color.WHITE.getRGB());

        for (StatusEffectInstance var4 : Main.mc.player.getStatusEffects()) {
            FontRenderers.Sub
                    .drawString(
                            context.getMatrices(),
                            this.formatPotion(var4),
                            (float)(this.x + 5),
                            (float)this.y + (float)this.height / 10.0F + (float)var2,
                            Color.WHITE.getRGB()
                    );
            var2 += 12;
        }
    }

    public static void kb() {
        gJ = new byte[16];
        gJ[8] = -27;
        gJ[2] = 17;
        gJ[4] = -20;
        gJ[10] = -63;
        gJ[13] = 22;
        gJ[14] = 91;
        gJ[3] = 36;
        gJ[11] = -2;
        gJ[0] = 38;
        gJ[7] = 95;
        gJ[15] = 98;
        gJ[12] = 53;
        gJ[6] = -98;
        gJ[9] = -99;
        gJ[1] = 81;
        gJ[5] = 93;
    }

    public static String jZ(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{-1, 57, -5, 91, 126, -4, 106, 11, 101, 63, -113, 0, 33, 110, 31, -81};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 152, 256);
        SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] var8 = var7.generateSecret(var6).getEncoded();
        SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
        Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var10.init(2, var9, new IvParameterSpec(var2));
        byte[] var11 = var10.doFinal(var5);
        String info = new String(var11, "UTF-8");
        return info;
    }

    public String formatPotion(StatusEffectInstance potion) {
        String var2 = potion.getTranslationKey().replaceAll(gI[7], "");
        if (!var2.isEmpty()) {
            var2 = var2.substring(0, 1).toUpperCase() + var2.substring(1);
        }

        if (var2.contains(gI[8])) {
            int var3 = var2.indexOf(gI[9]);
            var2 = var2.substring(0, var3).trim();
        }

        int var8 = potion.getDuration();
        int var4 = var8 / 20;
        int var5 = var4 / 60;
        var4 %= 60;
        int var6 = potion.getAmplifier() + 1;
        String var7 = " " + var6;
        return var2.replaceAll(gI[10], gI[11]) + var7 + ": " + var5 + "m " + var4 + "s";
    }
}
