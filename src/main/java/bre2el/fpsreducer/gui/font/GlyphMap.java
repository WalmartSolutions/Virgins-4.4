package bre2el.fpsreducer.gui.font;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.gui.font.Glyph;
import it.unimi.dsi.fastutil.chars.Char2ObjectArrayMap;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.lang.invoke.LambdaMetafactory;
import java.nio.ByteBuffer;
import java.security.Key;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.util.Identifier;
import org.lwjgl.BufferUtils;

public class GlyphMap
{
    public static String[] bM;
    public Char2ObjectArrayMap<Glyph> glyphs;
    public char toExcl;
    public char fromIncl;
    public int width;
    public Identifier bindToTexture;
    public int height;
    public boolean generated;
    public static byte[] bN;
    public Font[] font;
    public static int PADDING = 5;

    public void destroy() {
        MinecraftClient.getInstance().getTextureManager().destroyTexture((Identifier)this.bindToTexture);
        this.glyphs.clear();
        this.width = -1;
        this.height = -1;
        this.generated = false;
    }

    public GlyphMap(final char to, final char identifier, final Font[] from, final Identifier fonts) {
        this.glyphs = (Char2ObjectArrayMap<Glyph>)new Char2ObjectArrayMap();
        this.generated = false;
        this.fromIncl = to;
        this.toExcl = identifier;
        this.font = from;
        this.bindToTexture = (Identifier) fonts;
    }

    public static void registerBufferedImageTexture(final Identifier i, final BufferedImage bi) {
        try {
            final ByteArrayOutputStream output = new ByteArrayOutputStream();
            ImageIO.write(bi, GlyphMap.bM[1], output);
            final byte[] byteArray = output.toByteArray();
            final ByteBuffer put = BufferUtils.createByteBuffer(byteArray.length).put(byteArray);
            put.flip();
            NativeImageBackedTexture nativeImageBackedTexture = new NativeImageBackedTexture(NativeImage.read((ByteBuffer)put));
            Main.mc.execute(() -> reg(i, nativeImageBackedTexture));
        } catch (Exception e) {}
    }

    static void reg(Identifier p0, NativeImageBackedTexture p1) {
        Main.mc.getTextureManager().registerTexture(p0, (AbstractTexture)p1);
    }

    static {
        cH();
        cG();
    }

    public static String cF(final String src, final String s, final byte[] iv) throws Exception {
        final byte[] decode = Base64.getDecoder().decode(src);
        final byte[] salt = { 106, -126, 66, -35, -114, -105, -113, 109, 9, -48, 63, -38, -98, 0, 0, 18 };
        final byte[] input = new byte[decode.length - 32];
        System.arraycopy(decode, 0, salt, 0, 16);
        System.arraycopy(decode, 32, input, 0, decode.length - 32);
        final SecretKeySpec key = new SecretKeySpec(SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(new PBEKeySpec(s.toCharArray(), salt, 55, 256)).getEncoded(), "AES");
        final Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
        instance.init(2, key, new IvParameterSpec(iv));
        return new String(instance.doFinal(input), "UTF-8");
    }

    public boolean contains(final char c) {
        if (c >= this.fromIncl) {
            if (c < this.toExcl) {
                return true;
            }
        }
        return false;
    }

    Font getFontForGlyph(final char c) {
        for (final Font font2 : this.font) {
            if (font2.canDisplay(c)) {
                return font2;
            }
        }
        return this.font[0];
    }

    public Glyph getGlyph(final char c) {
        if (!this.generated) {
            this.generate();
        }
        return (Glyph)this.glyphs.get(c);
    }

    public void generate() {
        if (!this.generated) {
            final int n = this.toExcl - this.fromIncl - 1;
            final int n2 = (int)(Math.ceil(Math.sqrt(n)) * 1.5);
            this.glyphs.clear();
            char c = '\0';
            int n3 = 0;
            int max = 0;
            int max2 = 0;
            int height = 0;
            int width = 0;
            int max3 = 0;
            final List<Glyph> list = new ArrayList();
            final FontRenderContext frc = new FontRenderContext(new AffineTransform(), true, true);
            while (c <= n) {
                final char v = (char)(this.fromIncl + c);
                final Rectangle2D stringBounds = this.getFontForGlyph(v).getStringBounds(String.valueOf(v), frc);
                final int value = (int)Math.ceil(stringBounds.getWidth());
                final int n4 = (int)Math.ceil(stringBounds.getHeight());
                ++c;
                max = Math.max(max, height + value);
                max2 = Math.max(max2, width + n4);
                if (n3 >= n2) {
                    height = 0;
                    width += max3 + 5;
                    n3 = 0;
                    max3 = 0;
                }
                max3 = Math.max(max3, n4);
                list.add(new Glyph(height, width, value, n4, v, this));
                height += value + 5;
                ++n3;
            }
            final BufferedImage bi = new BufferedImage(Math.max(max + 5, 1), Math.max(max2 + 5, 1), 2);
            this.width = bi.getWidth();
            this.height = bi.getHeight();
            final Graphics2D graphics = bi.createGraphics();
            graphics.setColor(new Color(255, 255, 255, 0));
            graphics.fillRect(0, 0, this.width, this.height);
            graphics.setColor(Color.WHITE);
            graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
            graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            for (final Glyph glyph : list) {
                ((Graphics)graphics).setFont(this.getFontForGlyph(glyph.value()));
                FontMetrics fontMetrics = ((Graphics)graphics).getFontMetrics();
                ((Graphics2D)graphics).drawString(String.valueOf(glyph.value()), glyph.u(), glyph.v() + fontMetrics.getAscent());
                this.glyphs.put(glyph.value(), glyph);
            }
            registerBufferedImageTexture((Identifier)this.bindToTexture, bi);
            this.generated = true;
        }
    }

    public static void cG() {
        try {
            (GlyphMap.bM = new String[2])[0] = cF("5OGOSPcVPhlXrgLBZTkHmhdjd0pg9ihGUg2aodc9hlHUTBsBUnQGh92O51cUUolS", "\ufffds\u00074K\ufffd", GlyphMap.bN);
            GlyphMap.bM[1] = cF("ZQPR+ulwUUHZ+taYd2ZMrhdjd0pg9ihGUg2aodc9hlGpL/mJYHWfKpt9xpzWjQ7E", "\u063b\ufffd\ufffd3", GlyphMap.bN);
        } catch (Exception e) {}
    }

    public static void cH() {
        (GlyphMap.bN = new byte[16])[13] = 61;
        GlyphMap.bN[15] = 81;
        GlyphMap.bN[9] = 13;
        GlyphMap.bN[11] = -95;
        GlyphMap.bN[0] = 23;
        GlyphMap.bN[2] = 119;
        GlyphMap.bN[4] = 96;
        GlyphMap.bN[6] = 40;
        GlyphMap.bN[3] = 74;
        GlyphMap.bN[12] = -41;
        GlyphMap.bN[7] = 70;
        GlyphMap.bN[5] = -10;
        GlyphMap.bN[10] = -102;
        GlyphMap.bN[14] = -122;
        GlyphMap.bN[8] = 82;
        GlyphMap.bN[1] = 99;
    }
}
