package bre2el.fpsreducer.gui.font;

import bre2el.fpsreducer.client.Main;
import com.google.common.base.Preconditions;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.chars.Char2IntArrayMap;
import it.unimi.dsi.fastutil.chars.Char2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.awt.Color;
import java.awt.Font;
import java.io.Closeable;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import net.minecraft.client.render.*;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

public class FontRenderer implements Closeable {
    public float originalSize;
    public int previousGameScale;
    public static Char2IntArrayMap colorCodes = new Char2IntArrayMap();
    public int scaleMul;
    public Font[] fonts;
    public static char RND_START;
    public static byte[] r;
    public static Object2ObjectArrayMap<Identifier, ObjectList<DrawEntry>> GLYPH_PAGE_CACHE = new Object2ObjectArrayMap();
    public Char2ObjectArrayMap<Glyph> allGlyphs;
    public static char RND_END;
    public static Random RND = new Random();
    public static int BLOCK_SIZE;
    public ObjectList<GlyphMap> maps = new ObjectArrayList();
    public static String[] q;

    public void drawString(@NotNull MatrixStack s, @NotNull String b, float g, float stack, float a, float r, float x, float y) {
        if (!b.isEmpty()) {
            this.sizeCheck();
            float var9 = a;
            float var10 = r;
            float var11 = x;
            s.push();
            s.translate(g, stack, 0.0F);
            s.scale(1.0F / (float)this.scaleMul, 1.0F / (float)this.scaleMul, 1.0F);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableCull();
            GL11.glTexParameteri(3553, 10241, 9729);
            GL11.glTexParameteri(3553, 10240, 9729);
            RenderSystem.setShader(GameRenderer::getPositionTexColorProgram);
            BufferBuilder var12 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
            Matrix4f var13 = s.peek().getPositionMatrix();
            char[] var14 = b.toCharArray();
            float var15 = 0.0F;
            float var16 = 0.0F;
            boolean var17 = false;
            int var18 = 0;

            for (int var19 = 0; var19 < var14.length; var19++) {
                char var20 = var14[var19];
                if (!var17) {
                    if (var20 != 167) {
                        if (var20 != '\n') {
                            Glyph var21 = this.locateGlyph1(var20);
                            if (var21 != null) {
                                if (var21.value() != ' ') {
                                    Identifier var22 = var21.owner().bindToTexture;
                                    DrawEntry var23 = new DrawEntry(var15, var16, var9, var10, var11, var21);
                                    (GLYPH_PAGE_CACHE.computeIfAbsent(var22, integer -> new ObjectArrayList())).add(var23);
                                }

                                var15 += (float)var21.width();
                            }
                        } else {
                            var16 += this.getStringHeight(b.substring(var18, var19)) * (float)this.scaleMul;
                            var15 = 0.0F;
                            var18 = var19 + 1;
                        }
                    } else {
                        var17 = true;
                    }
                } else {
                    var17 = false;
                    char var39 = Character.toUpperCase(var20);
                    if (!colorCodes.containsKey(var39)) {
                        if (var39 == 'R') {
                            var9 = a;
                            var10 = r;
                            var11 = x;
                        }
                    } else {
                        int var41 = colorCodes.get(var39);
                        int[] var43 = RGBIntToRGB(var41);
                        var9 = (float)var43[0] / 255.0F;
                        var10 = (float)var43[1] / 255.0F;
                        var11 = (float)var43[2] / 255.0F;
                    }
                }
            }

            ObjectIterator var37 = GLYPH_PAGE_CACHE.keySet().iterator();

            while (var37.hasNext()) {
                Identifier var38 = (Identifier)var37.next();
                RenderSystem.setShaderTexture(0, var38);

                for (DrawEntry var44 : GLYPH_PAGE_CACHE.get(var38)) {
                    float var24 = var44.atX;
                    float var25 = var44.atY;
                    float var26 = var44.r;
                    float var27 = var44.g;
                    float var28 = var44.elementCodec;
                    Glyph var29 = var44.toDraw;
                    GlyphMap var30 = var29.owner();
                    float var31 = (float)var29.width();
                    float var32 = (float)var29.height();
                    float var33 = (float)var29.u() / (float)var30.width;
                    float var34 = (float)var29.v() / (float)var30.height;
                    float var35 = (float)(var29.u() + var29.width()) / (float)var30.width;
                    float var36 = (float)(var29.v() + var29.height()) / (float)var30.height;
                    var12.vertex(var13, var24 + 0.0F, var25 + var32, 0.0F).texture(var33, var36).color(var26, var27, var28, y);
                    var12.vertex(var13, var24 + var31, var25 + var32, 0.0F).texture(var35, var36).color(var26, var27, var28, y);
                    var12.vertex(var13, var24 + var31, var25 + 0.0F, 0.0F).texture(var35, var34).color(var26, var27, var28, y);
                    var12.vertex(var13, var24 + 0.0F, var25 + 0.0F, 0.0F).texture(var33, var34).color(var26, var27, var28, y);
                }

                BufferRenderer.drawWithGlobalProgram(var12.end());
            }

            s.pop();
            GLYPH_PAGE_CACHE.clear();
        }
    }

    @Contract(
            value = "-> new",
            pure = true
    )
    @NotNull
    public static Identifier randomIdentifier() {
        return Identifier.of("", "temp/" + randomString(32));
    }

    public void drawCenteredString(MatrixStack r, String a, float s, float g, float stack, float b, float x, float y) {
        this.drawString(r, a, s - this.getStringWidth(a) / 2.0F, g, stack, b, x, y);
    }

    void init(@NotNull Font[] fonts, float sizePx) {
        this.previousGameScale = getGuiScale();
        this.scaleMul = this.previousGameScale;
        this.fonts = new Font[fonts.length];

        for (int var3 = 0; var3 < fonts.length; var3++) {
            this.fonts[var3] = fonts[var3].deriveFont(sizePx * (float)this.scaleMul);
        }
    }

    static String randomString(int length) {
        return (String)IntStream.range(0, length).mapToObj(operand -> String.valueOf((char)RND.nextInt(97, 123))).collect(Collectors.joining());
    }

    void sizeCheck() {
        int var1 = getGuiScale();
        if (var1 != this.previousGameScale) {
            this.close();
            this.init(this.fonts, this.originalSize);
        }
    }

    public static void z() {
        try {
            q = new String[2];
            q[1] = y("0yBrJUGEXdV4Zba0Ji+xJwlmUnRqqZRvkifxVN6cXwqDaCk0FhqzBj9HGoGz7ITTaqQ/+Be4L40EkKMyJveKKA==", "\u0019��jן", r);
            q[0] = y("9WdBlRzaz44VgSwODAas1QlmUnRqqZRvkifxVN6cXwp8JIKdTMfzo5pa7B9Cz0jxCO9gGHbHCWD6DfwgKjY2ng==", "�t��1�", r);
        } catch (Exception e ) {}
    }

    public static int getGuiScale() {
        return (int)Main.mc.getWindow().getScaleFactor();
    }

    public void drawGradientString(@NotNull MatrixStack stack, @NotNull String s, float x, float y, int offset, boolean hud) {
        this.sizeCheck();
        stack.push();
        stack.translate(x, y, 0.0F);
        stack.scale(1.0F / (float)this.scaleMul, 1.0F / (float)this.scaleMul, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        GL11.glTexParameteri(3553, 10241, 9729);
        GL11.glTexParameteri(3553, 10240, 9729);
        RenderSystem.setShader(GameRenderer::getPositionTexColorProgram);
        BufferBuilder var7 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
        Matrix4f var8 = stack.peek().getPositionMatrix();
        char[] var9 = s.toCharArray();
        float var10 = 0.0F;
        float var11 = 0.0F;
        int var12 = 0;
        int var13 = 0;
        float var14 = 1.0F;

        for (int var15 = 0; var15 < var9.length; var15++) {
            char var16 = var9[var15];
            Color var17 = new Color(255, 255, 255, 220);
            if (!hud) {
                var17 = new Color(255, 255, 255, 220);
            }

            var14 = (float)var17.getAlpha() / 255.0F;
            if (var16 != '\n') {
                Glyph var18 = this.locateGlyph1(var16);
                if (var18.value() != ' ') {
                    Identifier var19 = var18.owner().bindToTexture;
                    DrawEntry var20 = new DrawEntry(
                            var10, var11, (float)var17.getRed() / 255.0F, (float)var17.getGreen() / 255.0F, (float)var17.getBlue() / 255.0F, var18
                    );
                    ((ObjectList)GLYPH_PAGE_CACHE.computeIfAbsent(var19, integer -> new ObjectArrayList())).add(var20);
                }

                var10 += (float)var18.width();
                var13++;
            } else {
                var11 += this.getStringHeight(s.substring(var12, var15)) * (float)this.scaleMul;
                var10 = 0.0F;
                var12 = var15 + 1;
            }
        }

        ObjectIterator var33 = GLYPH_PAGE_CACHE.keySet().iterator();

        while (var33.hasNext()) {
            Identifier var34 = (Identifier)var33.next();
            RenderSystem.setShaderTexture(0, var34);

            for (DrawEntry var37 : GLYPH_PAGE_CACHE.get(var34)) {
                float var38 = var37.atX;
                float var21 = var37.atY;
                float var22 = var37.r;
                float var23 = var37.g;
                float var24 = var37.elementCodec;
                Glyph var25 = var37.toDraw;
                GlyphMap var26 = var25.owner();
                float var27 = (float)var25.width();
                float var28 = (float)var25.height();
                float var29 = (float)var25.u() / (float)var26.width;
                float var30 = (float)var25.v() / (float)var26.height;
                float var31 = (float)(var25.u() + var25.width()) / (float)var26.width;
                float var32 = (float)(var25.v() + var25.height()) / (float)var26.height;
                var7.vertex(var8, var38 + 0.0F, var21 + var28, 0.0F).texture(var29, var32).color(var22, var23, var24, var14);
                var7.vertex(var8, var38 + var27, var21 + var28, 0.0F).texture(var31, var32).color(var22, var23, var24, var14);
                var7.vertex(var8, var38 + var27, var21 + 0.0F, 0.0F).texture(var31, var30).color(var22, var23, var24, var14);
                var7.vertex(var8, var38 + 0.0F, var21 + 0.0F, 0.0F).texture(var29, var30).color(var22, var23, var24, var14);
            }

            BufferRenderer.drawWithGlobalProgram(var7.end());
        }

        stack.pop();
        GLYPH_PAGE_CACHE.clear();
    }

    @NotNull
    public static String stripControlCodes(@NotNull String text) {
        char[] var1 = text.toCharArray();
        StringBuilder var2 = new StringBuilder();

        for (int var3 = 0; var3 < var1.length; var3++) {
            char var4 = var1[var3];
            if (var4 != 167) {
                var2.append(var4);
            } else {
                var3++;
            }
        }

        return var2.toString();
    }

    public FontRenderer(@NotNull Font[] sizePx, float fonts) {
        this.allGlyphs = new Char2ObjectArrayMap();
        this.scaleMul = 0;
        this.previousGameScale = -1;
        Preconditions.checkArgument(sizePx.length > 0, q[1]);
        this.originalSize = fonts;
        this.init(sizePx, fonts);
    }

    @Contract(
            value = "_ -> new",
            pure = true
    )
    @NotNull
    public static int[] RGBIntToRGB(int in) {
        int var1 = in >> 16 & 0xFF;
        int var2 = in >> 8 & 0xFF;
        int var3 = in & 0xFF;
        return new int[]{var1, var2, var3};
    }

    static {
        A();
        z();

        colorCodes.put('0', 0);
        colorCodes.put('1', 170);
        colorCodes.put('2', 43520);
        colorCodes.put('3', 43690);
        colorCodes.put('4', 11141120);
        colorCodes.put('5', 11141290);
        colorCodes.put('6', 16755200);
        colorCodes.put('7', 11184810);
        colorCodes.put('8', 5592405);
        colorCodes.put('9', 5592575);
        colorCodes.put('A', 5635925);
        colorCodes.put('B', 5636095);
        colorCodes.put('C', 16733525);
        colorCodes.put('D', 16733695);
        colorCodes.put('E', 16777045);
        colorCodes.put('F', 16777215);
    }

    Glyph locateGlyph1(char glyph) {
        return (Glyph)this.allGlyphs.computeIfAbsent(glyph, this::locateGlyph0);
    }

    public static String y(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{103, 78, 99, -60, -12, 0, -126, 49, -81, 115, 55, 73, 21, -55, 53, 4};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 91, 256);
        SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] var8 = var7.generateSecret(var6).getEncoded();
        SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
        Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var10.init(2, var9, new IvParameterSpec(var2));
        byte[] var11 = var10.doFinal(var5);
        String info = new String(var11, "UTF-8");
        return info;
    }

    static int floorNearestMulN(int x, int n) {
        return n * (int)Math.floor((double)x / (double)n);
    }

    public void close() {
        ObjectListIterator var1 = this.maps.iterator();

        while (var1.hasNext()) {
            GlyphMap var2 = (GlyphMap)var1.next();
            var2.destroy();
        }

        this.maps.clear();
        this.allGlyphs.clear();
    }

    Glyph locateGlyph0(char glyph) {
        ObjectListIterator var2 = this.maps.iterator();

        while (var2.hasNext()) {
            GlyphMap var3 = (GlyphMap)var2.next();
            if (var3.contains(glyph)) {
                return var3.getGlyph(glyph);
            }
        }

        int var4 = floorNearestMulN(glyph, 256);
        GlyphMap var5 = this.generateMap((char)var4, (char)(var4 + 256));
        return var5.getGlyph(glyph);
    }

    public float getStringHeight(String text) {
        char[] var2 = stripControlCodes(text).toCharArray();
        if (var2.length == 0) {
            var2 = new char[]{' '};
        }

        float var3 = 0.0F;
        float var4 = 0.0F;

        for (char var8 : var2) {
            if (var8 != '\n') {
                Glyph var9 = this.locateGlyph1(var8);
                var3 = Math.max((float)var9.height() / (float)this.scaleMul, var3);
            } else {
                float var11;
                int var10000 = (var11 = var3 - 0.0F) == 0.0F ? 0 : (var11 < 0.0F ? -1 : 1);
                var3 = (float)this.locateGlyph1(' ').height() / (float)this.scaleMul;
                var4 += var3;
                var3 = 0.0F;
            }
        }

        return var3 + var4;
    }

    public static void A() {
        r = new byte[16];
        r[9] = 39;
        r[15] = 10;
        r[6] = -108;
        r[10] = -15;
        r[14] = 95;
        r[7] = 111;
        r[5] = -87;
        r[11] = 84;
        r[1] = 102;
        r[8] = -110;
        r[12] = -34;
        r[13] = -100;
        r[3] = 116;
        r[0] = 9;
        r[4] = 106;
        r[2] = 82;
    }

    @NotNull
    GlyphMap generateMap(char from, char to) {
        GlyphMap var3 = new GlyphMap(from, to, this.fonts, randomIdentifier());
        this.maps.add(var3);
        return var3;
    }

    public float getStringWidth(String text) {
        char[] var2 = stripControlCodes(text).toCharArray();
        float var3 = 0.0F;
        float var4 = 0.0F;

        for (char var8 : var2) {
            if (var8 != '\n') {
                Glyph var9 = this.locateGlyph1(var8);
                float var10 = var9 == null ? 1.0F : (float)var9.width();
                var3 += var10 / (float)this.scaleMul;
            } else {
                var4 = Math.max(var3, var4);
                var3 = 0.0F;
            }
        }

        return Math.max(var3, var4);
    }
}
