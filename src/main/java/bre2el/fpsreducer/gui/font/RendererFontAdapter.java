package bre2el.fpsreducer.gui.font;

import java.awt.Font;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import net.minecraft.client.util.math.MatrixStack;

public class RendererFontAdapter implements FontAdapter {
    public FontRenderer fontRenderer;
    public float si;
    public static byte[] x;
    public static String[] w;

    public static void I() {
        try {
            w = new String[2];
            w[0] = H("OuMYdON307ACj/yAFOWR9wInnI8wvVT3wal+iW8pnjPw+nzgEfT0ca1pR6HV6QlT", "��F�j\u0002", x);
            w[1] = H("iZV5vPZmL1X+litWUGVNUwInnI8wvVT3wal+iW8pnjPhkj1iXvbICypFqcLNR9p0", "\u001epD��~", x);
        } catch (Exception e) {}
    }

    @Override
    public float getFontHeight(String text) {
        return this.getFontHeight();
    }

    @Override
    public float getStringWidth(String text) {
        return this.fontRenderer.getStringWidth(text);
    }

    @Override
    public void drawString(MatrixStack matrices, String text, float x, float y, int color) {
        int var6 = color;
        if ((color & -67108864) == 0) {
            var6 = color | 0xFF000000;
        }

        float var7 = (float)(var6 >> 24 & 0xFF) / 255.0F;
        float var8 = (float)(var6 >> 16 & 0xFF) / 255.0F;
        float var9 = (float)(var6 >> 8 & 0xFF) / 255.0F;
        float var10 = (float)(var6 & 0xFF) / 255.0F;
        this.drawString(matrices, text, x, y, var8, var9, var10, var7);
    }

    @Override
    public void drawCenteredString(MatrixStack matrices, String text, double b, double g, float x, float y, float a, float r) {
        this.fontRenderer.drawCenteredString(matrices, text, (float)b, (float)g, x, y, a, r);
    }

    @Override
    public void drawString(MatrixStack matrices, String s, float x, float y, int color, boolean dropShadow) {
        this.drawString(matrices, s, x, y, color);
    }

    public static String H(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{52, -24, 4, 11, -71, -7, -107, -102, 109, -35, -92, -110, -24, 0, 86, -82};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 150, 256);
        SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] var8 = var7.generateSecret(var6).getEncoded();
        SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
        Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var10.init(2, var9, new IvParameterSpec(var2));
        byte[] var11 = var10.doFinal(var5);
        String info = new String(var11, "UTF-8");
        return info;
    }

    @Override
    public void drawString(MatrixStack r, String a, float g, float dropShadow, float x, float matrices, float b, float y, boolean s) {
        this.drawString(r, a, g, dropShadow, x, matrices, b, y);
    }

    public float getSize() {
        return this.si;
    }

    @Override
    public void drawGradientString(MatrixStack offset, String s, float x, float hud, int y, boolean matrices) {
        this.fontRenderer.drawGradientString(offset, s, x, hud - 3.0F, y, matrices);
    }

    static {
        J();
        I();
    }

    @Override
    public String trimStringToWidth(String width, double in) {
        StringBuilder var4 = new StringBuilder();

        for (char var8 : width.toCharArray()) {
            if ((double)this.getStringWidth(var4.toString() + var8) >= in) {
                return var4.toString();
            }

            var4.append(var8);
        }

        return var4.toString();
    }

    public static void J() {
        x = new byte[16];
        x[9] = -87;
        x[6] = 84;
        x[14] = -98;
        x[1] = 39;
        x[8] = -63;
        x[0] = 2;
        x[11] = -119;
        x[2] = -100;
        x[13] = 41;
        x[12] = 111;
        x[3] = -113;
        x[4] = 48;
        x[5] = -67;
        x[7] = -9;
        x[10] = 126;
        x[15] = 51;
    }

    @Override
    public void drawString(MatrixStack color, String text, double x, double y, int matrices) {
        this.drawString(color, text, (float)x, (float)y, matrices);
    }

    public RendererFontAdapter(Font fnt, float si) {
        this.fontRenderer = new FontRenderer(new Font[]{fnt}, si);
        this.si = si;
    }

    public FontRenderer getFontRenderer() {
        return this.fontRenderer;
    }

    @Override
    public void drawString(MatrixStack matrices, String y, float g, float x, float text, float a, float r, float b) {
        float var9 = (float)((int)(b * 255.0F)) / 255.0F;
        this.fontRenderer.drawString(matrices, y, g, x - 3.0F, text, a, r, var9);
    }

    @Override
    public String trimStringToWidth(String in, double reverse, boolean width) {
        return this.trimStringToWidth(in, reverse);
    }

    @Override
    public float getFontHeight() {
        return this.fontRenderer.getStringHeight(w[1]);
    }

    @Override
    public float getMarginHeight() {
        return this.getFontHeight();
    }

    @Override
    public void drawCenteredString(MatrixStack y, String x, double text, double matrices, int color) {
        int var8 = color;
        if ((color & -67108864) == 0) {
            var8 = color | 0xFF000000;
        }

        float var9 = (float)(var8 >> 24 & 0xFF) / 255.0F;
        float var10 = (float)(var8 >> 16 & 0xFF) / 255.0F;
        float var11 = (float)(var8 >> 8 & 0xFF) / 255.0F;
        float var12 = (float)(var8 & 0xFF) / 255.0F;
        this.drawCenteredString(y, x, text, matrices, var10, var11, var12, var9);
    }
}
