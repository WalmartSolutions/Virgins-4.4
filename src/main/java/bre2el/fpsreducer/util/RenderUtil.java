package bre2el.fpsreducer.util;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.gui.font.FontAdapter;
import bre2el.fpsreducer.gui.font.FontRenderers;
import bre2el.fpsreducer.gui.font.GlyphMap;
import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Kernel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.apache.commons.lang3.RandomStringUtils;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4d;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class RenderUtil {
    public static Matrix4f lastModMat = new Matrix4f();
    public static Matrix4f lastWorldSpaceMatrix = new Matrix4f();
    public static Map<Integer, BlurredShadow> shadowCache = new HashMap<>();
    public static Matrix4f lastProjMat = new Matrix4f();

    public static void drawBox(DrawContext ent, Entity color, Color context) {
        Matrix4f var3 = ent.getMatrices().peek().getPositionMatrix();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.lineWidth(0.5F);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        BufferBuilder var4 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        double var5 = color.prevX + (color.getX() - color.prevX) * (double)Main.mc.getRenderTickCounter().getTickDelta(true);
        double var7 = color.prevY + (color.getY() - color.prevY) * (double)Main.mc.getRenderTickCounter().getTickDelta(true);
        double var9 = color.prevZ + (color.getZ() - color.prevZ) * (double)Main.mc.getRenderTickCounter().getTickDelta(true);
        Box var11 = color.getBoundingBox();
        Box var12 = new Box(
                var11.minX - color.getX() + var5 - 0.05,
                var11.minY - color.getY() + var7,
                var11.minZ - color.getZ() + var9 - 0.05,
                var11.maxX - color.getX() + var5 + 0.05,
                var11.maxY - color.getY() + var7 + 0.15,
                var11.maxZ - color.getZ() + var9 + 0.05
        );
        Vec3d[] var13 = new Vec3d[]{
                new Vec3d(var12.minX, var12.minY, var12.minZ),
                new Vec3d(var12.minX, var12.maxY, var12.minZ),
                new Vec3d(var12.maxX, var12.minY, var12.minZ),
                new Vec3d(var12.maxX, var12.maxY, var12.minZ),
                new Vec3d(var12.minX, var12.minY, var12.maxZ),
                new Vec3d(var12.minX, var12.maxY, var12.maxZ),
                new Vec3d(var12.maxX, var12.minY, var12.maxZ),
                new Vec3d(var12.maxX, var12.maxY, var12.maxZ)
        };
        Vector4d var14 = null;

        for (Vec3d var18 : var13) {
            var18 = worldSpaceToScreenSpace(new Vec3d(var18.x, var18.y, var18.z));
            if (var18.z > 0.0 && var18.z < 1.0) {
                if (var14 == null) {
                    var14 = new Vector4d(var18.x, var18.y, var18.z, 0.0);
                }

                var14.x = Math.min(var18.x, var14.x);
                var14.y = Math.min(var18.y, var14.y);
                var14.z = Math.max(var18.x, var14.z);
                var14.w = Math.max(var18.y, var14.w);
            }
        }

        if (var14 != null) {
            double var24 = var14.x;
            double var25 = var14.y;
            double var19 = var14.z;
            double var21 = var14.w;
            setRectPoints(var4, var3, (float)(var24 - 1.0), (float)var25, (float)(var24 + 0.5), (float)(var21 + 0.5), context, context, context, context);
            setRectPoints(
                    var4, var3, (float)(var24 - 1.0), (float)(var25 - 0.5), (float)(var19 + 0.5), (float)(var25 + 0.5 + 0.5), context, context, context, context
            );
            setRectPoints(var4, var3, (float)(var19 - 0.5 - 0.5), (float)var25, (float)(var19 + 0.5), (float)(var21 + 0.5), context, context, context, context);
            setRectPoints(
                    var4, var3, (float)(var24 - 1.0), (float)(var21 - 0.5 - 0.5), (float)(var19 + 0.5), (float)(var21 + 0.5), context, context, context, context
            );
            BuiltBuffer var23 = var4.endNullable();
            if (var23 == null) {
                return;
            }

            BufferRenderer.drawWithGlobalProgram(var23);
            RenderSystem.disableBlend();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    public static void drawNametag(DrawContext context, Entity ent, Color color, boolean armor) {
        Matrix4f var4 = context.getMatrices().peek().getPositionMatrix();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.lineWidth(0.1F);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        BufferBuilder var5 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        double var6 = ent.prevX + (ent.getX() - ent.prevX) * (double)Main.mc.getRenderTickCounter().getTickDelta(true);
        double var8 = ent.prevY + (ent.getY() - ent.prevY) * (double)Main.mc.getRenderTickCounter().getTickDelta(true);
        double var10 = ent.prevZ + (ent.getZ() - ent.prevZ) * (double)Main.mc.getRenderTickCounter().getTickDelta(true);
        Box var12 = ent.getBoundingBox();
        Box var13 = new Box(
                var12.minX - ent.getX() + var6 - 0.05,
                var12.minY - ent.getY() + var8,
                var12.minZ - ent.getZ() + var10 - 0.05,
                var12.maxX - ent.getX() + var6 + 0.05,
                var12.maxY - ent.getY() + var8 + 0.15,
                var12.maxZ - ent.getZ() + var10 + 0.05
        );
        Vec3d[] var14 = new Vec3d[]{
                new Vec3d(var13.minX, var13.minY, var13.minZ),
                new Vec3d(var13.minX, var13.maxY, var13.minZ),
                new Vec3d(var13.maxX, var13.minY, var13.minZ),
                new Vec3d(var13.maxX, var13.maxY, var13.minZ),
                new Vec3d(var13.minX, var13.minY, var13.maxZ),
                new Vec3d(var13.minX, var13.maxY, var13.maxZ),
                new Vec3d(var13.maxX, var13.minY, var13.maxZ),
                new Vec3d(var13.maxX, var13.maxY, var13.maxZ)
        };
        Vector4d var15 = null;

        for (Vec3d var19 : var14) {
            var19 = worldSpaceToScreenSpace(new Vec3d(var19.x, var19.y, var19.z));
            if (var19.z > 0.0 && var19.z < 1.0) {
                if (var15 == null) {
                    var15 = new Vector4d(var19.x, var19.y, var19.z, 0.0);
                }

                var15.x = Math.min(var19.x, var15.x);
                var15.y = Math.min(var19.y, var15.y);
                var15.z = Math.max(var19.x, var15.z);
                var15.w = Math.max(var19.y, var15.w);
            }
        }

        if (var15 != null) {
            double var34 = var15.x;
            double var35 = var15.y;
            double var20 = var15.z;
            double var22 = var15.w;
            setRectPoints(var5, var4, (float)(var34 - 1.0), (float)var35, (float)(var34 + 0.5), (float)(var22 + 0.5), color, color, color, color);
            setRectPoints(var5, var4, (float)(var34 - 1.0), (float)(var35 - 0.5), (float)(var20 + 0.5), (float)(var35 + 0.5 + 0.5), color, color, color, color);
            setRectPoints(var5, var4, (float)(var20 - 0.5 - 0.5), (float)var35, (float)(var20 + 0.5), (float)(var22 + 0.5), color, color, color, color);
            setRectPoints(var5, var4, (float)(var34 - 1.0), (float)(var22 - 0.5 - 0.5), (float)(var20 + 0.5), (float)(var22 + 0.5), color, color, color, color);
            BuiltBuffer var24 = var5.endNullable();
            if (var24 == null) {
                return;
            }

            BufferRenderer.drawWithGlobalProgram(var24);
            RenderSystem.disableBlend();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            String var25 = ent.getName().getString();
            double var26 = (var34 + var20) / 2.0;
            double var28 = var35 - 10.0;
            FontRenderers.Main
                    .drawCenteredString(context.getMatrices(), var25, (double)((float)var26 + 1.0F), (double)((float)var28 + 1.0F), Color.BLACK.getRGB());
            FontRenderers.Main.drawCenteredString(context.getMatrices(), var25, (double)((float)var26), (double)((float)var28), -1);
            PlayerEntity var30 = (PlayerEntity)ent;
            RenderSystem.enableDepthTest();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShader(GameRenderer::getPositionColorProgram);
            var5 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
            if (armor
                    && (
                    !var30.getEquippedStack(EquipmentSlot.HEAD).isEmpty()
                            || !var30.getEquippedStack(EquipmentSlot.CHEST).isEmpty()
                            || !var30.getEquippedStack(EquipmentSlot.LEGS).isEmpty()
                            || !var30.getEquippedStack(EquipmentSlot.FEET).isEmpty()
            )) {
                if (var30.getEquippedStack(EquipmentSlot.HEAD).getItem() instanceof ArmorItem var31 && var31.getProtection() != 0) {
                    FontRenderers.Main
                            .drawCenteredString(
                                    context.getMatrices(),
                                    String.valueOf(var31.getDefaultStack().getMaxDamage() - var31.getDefaultStack().getDamage()),
                                    (double)((int)var20 - 40),
                                    (double)((int)var35 - 14),
                                    Color.WHITE.getRGB()
                            );
                    context.drawItem(var31.getDefaultStack(), (int)var20 - 40, (int)var35 - 20);
                }

                if (var30.getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof ArmorItem var38 && var38.getProtection() != 0) {
                    FontRenderers.Main
                            .drawCenteredString(
                                    context.getMatrices(),
                                    String.valueOf(var38.getDefaultStack().getMaxDamage() - var38.getDefaultStack().getDamage()),
                                    (double)((int)var20 - 20),
                                    (double)((int)var35 - 14),
                                    Color.WHITE.getRGB()
                            );
                    context.drawItem(new ItemStack(var38), (int)var20 - 20, (int)var35 - 20);
                }

                if (var30.getEquippedStack(EquipmentSlot.LEGS).getItem() instanceof ArmorItem var39 && var39.getProtection() != 0) {
                    FontRenderers.Main
                            .drawCenteredString(
                                    context.getMatrices(),
                                    String.valueOf(var39.getDefaultStack().getMaxDamage() - var39.getDefaultStack().getDamage()),
                                    (double)((int)var20),
                                    (double)((int)var35 - 14),
                                    Color.WHITE.getRGB()
                            );
                    context.drawItem(new ItemStack(var39), (int)var20, (int)var35 - 20);
                }

                if (var30.getEquippedStack(EquipmentSlot.FEET).getItem() instanceof ArmorItem var40 && var40.getProtection() != 0) {
                    FontRenderers.Main
                            .drawCenteredString(
                                    context.getMatrices(),
                                    String.valueOf(var40.getDefaultStack().getMaxDamage() - var40.getDefaultStack().getDamage()),
                                    (double)((int)var20 + 20),
                                    (double)((int)var35 - 14),
                                    Color.WHITE.getRGB()
                            );
                    context.drawItem(new ItemStack(var40), (int)var20 + 20, (int)var35 - 20);
                }
            }

            var24 = var5.endNullable();
            if (var24 == null) {
                return;
            }

            BufferRenderer.drawWithGlobalProgram(var24);
        }
    }

    public static float interpolateFloat(float oldValue, float newValue, double interpolationValue) {
        return (float)interpolate((double)oldValue, (double)newValue, (double)((float)interpolationValue));
    }

    public static void renderOutlinedBox(MatrixStack f6, float color, float matrixStack, float f4, float f, float f2, float f3, Color f5) {
        RenderSystem.enableBlend();
        RenderSystem.disableDepthTest();
        RenderSystem.setShaderColor((float)f5.getRed() / 255.0F, (float)f5.getGreen() / 255.0F, (float)f5.getBlue() / 255.0F, (float)f5.getAlpha() / 255.0F);
        RenderSystem.setShader(GameRenderer::getPositionProgram);
        Tessellator var8 = Tessellator.getInstance();
        BufferBuilder var9 = var8.begin(DrawMode.DEBUG_LINE_STRIP, VertexFormats.POSITION);
        var9.vertex(f6.peek().getPositionMatrix(), color, matrixStack, f4);
        var9.vertex(f6.peek().getPositionMatrix(), color, matrixStack, f3);
        var9.vertex(f6.peek().getPositionMatrix(), f, matrixStack, f3);
        var9.vertex(f6.peek().getPositionMatrix(), f, matrixStack, f4);
        var9.vertex(f6.peek().getPositionMatrix(), color, matrixStack, f4);
        var9.vertex(f6.peek().getPositionMatrix(), color, f2, f4);
        var9.vertex(f6.peek().getPositionMatrix(), color, f2, f3);
        var9.vertex(f6.peek().getPositionMatrix(), color, matrixStack, f3);
        var9.vertex(f6.peek().getPositionMatrix(), f, matrixStack, f3);
        var9.vertex(f6.peek().getPositionMatrix(), f, f2, f3);
        var9.vertex(f6.peek().getPositionMatrix(), color, f2, f3);
        var9.vertex(f6.peek().getPositionMatrix(), f, f2, f3);
        var9.vertex(f6.peek().getPositionMatrix(), f, f2, f4);
        var9.vertex(f6.peek().getPositionMatrix(), f, matrixStack, f4);
        var9.vertex(f6.peek().getPositionMatrix(), f, f2, f4);
        var9.vertex(f6.peek().getPositionMatrix(), color, f2, f4);
        BufferRenderer.drawWithGlobalProgram(var9.end());
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
    }

    public static void renderBoundingBox(Box bb, MatrixStack matrixStack, Color color) {
        Matrix4f var3 = matrixStack.peek().getPositionMatrix();
        Tessellator var4 = RenderSystem.renderThreadTesselator();
        RenderSystem.enableCull();
        RenderSystem.setShaderColor(
                (float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, (float)color.getAlpha() / 255.0F
        );
        BufferBuilder var5 = var4.begin(DrawMode.DEBUG_LINES, VertexFormats.POSITION);
        RenderSystem.setShader(GameRenderer::getPositionProgram);
        var5.vertex(var3, (float)bb.minX, (float)bb.minY, (float)bb.minZ);
        var5.vertex(var3, (float)bb.maxX, (float)bb.minY, (float)bb.minZ);
        var5.vertex(var3, (float)bb.maxX, (float)bb.minY, (float)bb.minZ);
        var5.vertex(var3, (float)bb.maxX, (float)bb.minY, (float)bb.maxZ);
        var5.vertex(var3, (float)bb.maxX, (float)bb.minY, (float)bb.maxZ);
        var5.vertex(var3, (float)bb.minX, (float)bb.minY, (float)bb.maxZ);
        var5.vertex(var3, (float)bb.minX, (float)bb.minY, (float)bb.maxZ);
        var5.vertex(var3, (float)bb.minX, (float)bb.minY, (float)bb.minZ);
        var5.vertex(var3, (float)bb.minX, (float)bb.minY, (float)bb.minZ);
        var5.vertex(var3, (float)bb.minX, (float)bb.maxY, (float)bb.minZ);
        var5.vertex(var3, (float)bb.maxX, (float)bb.minY, (float)bb.minZ);
        var5.vertex(var3, (float)bb.maxX, (float)bb.maxY, (float)bb.minZ);
        var5.vertex(var3, (float)bb.maxX, (float)bb.minY, (float)bb.maxZ);
        var5.vertex(var3, (float)bb.maxX, (float)bb.maxY, (float)bb.maxZ);
        var5.vertex(var3, (float)bb.minX, (float)bb.minY, (float)bb.maxZ);
        var5.vertex(var3, (float)bb.minX, (float)bb.maxY, (float)bb.maxZ);
        var5.vertex(var3, (float)bb.minX, (float)bb.maxY, (float)bb.minZ);
        var5.vertex(var3, (float)bb.maxX, (float)bb.maxY, (float)bb.minZ);
        var5.vertex(var3, (float)bb.maxX, (float)bb.maxY, (float)bb.minZ);
        var5.vertex(var3, (float)bb.maxX, (float)bb.maxY, (float)bb.maxZ);
        var5.vertex(var3, (float)bb.maxX, (float)bb.maxY, (float)bb.maxZ);
        var5.vertex(var3, (float)bb.minX, (float)bb.maxY, (float)bb.maxZ);
        var5.vertex(var3, (float)bb.minX, (float)bb.maxY, (float)bb.maxZ);
        var5.vertex(var3, (float)bb.minX, (float)bb.maxY, (float)bb.minZ);
        BuiltBuffer var6 = var5.endNullable();
        if (var6 != null) {
            BufferRenderer.drawWithGlobalProgram(var6);
            RenderSystem.disableBlend();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    static String validatePath(String path) {
        if (!Identifier.isPathValid(path)) {
            StringBuilder var1 = new StringBuilder();

            for (char var5 : path.toLowerCase().toCharArray()) {
                if (Identifier.isPathCharacterValid(var5)) {
                    var1.append(var5);
                }
            }

            return var1.toString();
        } else {
            return path;
        }
    }

    public static void setCameraAction() {
        Camera var0 = MinecraftClient.getInstance().getBlockEntityRenderDispatcher().camera;
        if (var0 != null) {
            MatrixStack var1 = RenderHelper.getMatrixStack();
            var1.push();
            Vec3d var2 = var0.getPos();
            var1.translate(-var2.x, -var2.y, -var2.z);
        }
    }

    public static Vec3d worldSpaceToScreenSpace(Vec3d pos) {
        Camera var1 = Main.mc.getEntityRenderDispatcher().camera;
        int var2 = Main.mc.getWindow().getHeight();
        int[] var3 = new int[4];
        GL11.glGetIntegerv(2978, var3);
        Vector3f var4 = new Vector3f();
        double var5 = pos.x - var1.getPos().x;
        double var7 = pos.y - var1.getPos().y;
        double var9 = pos.z - var1.getPos().z;
        Vector4f var11 = new Vector4f((float)var5, (float)var7, (float)var9, 1.0F).mul(lastWorldSpaceMatrix);
        Matrix4f var12 = new Matrix4f(lastProjMat);
        Matrix4f var13 = new Matrix4f(lastModMat);
        var12.mul(var13).project(var11.x(), var11.y(), var11.z(), var3, var4);
        return new Vec3d(
                (double)var4.x / Main.mc.getWindow().getScaleFactor(), (double)((float)var2 - var4.y) / Main.mc.getWindow().getScaleFactor(), (double)var4.z
        );
    }

    static void setRectPoints(
            BufferBuilder y1, Matrix4f c1, float x1, float x, float y, float c3, Color matrix, Color c2, Color c4, Color bufferBuilder
    ) {
        y1.vertex(c1, x1, c3, 0.0F).color(matrix.getRGB());
        y1.vertex(c1, y, c3, 0.0F).color(c2.getRGB());
        y1.vertex(c1, y, x, 0.0F).color(c4.getRGB());
        y1.vertex(c1, x1, x, 0.0F).color(bufferBuilder.getRGB());
    }

    public static void renderFilledBox(MatrixStack f6, float f3, float f2, float f4, float color, float f, float matrixStack, Color f5) {
        RenderSystem.enableBlend();
        RenderSystem.disableDepthTest();
        RenderSystem.setShaderColor((float)f5.getRed() / 255.0F, (float)f5.getGreen() / 255.0F, (float)f5.getBlue() / 255.0F, (float)f5.getAlpha() / 255.0F);
        RenderSystem.setShader(GameRenderer::getPositionProgram);
        Tessellator var8 = Tessellator.getInstance();
        BufferBuilder var9 = var8.begin(DrawMode.TRIANGLE_STRIP, VertexFormats.POSITION);
        var9.vertex(f6.peek().getPositionMatrix(), f3, f2, f4);
        var9.vertex(f6.peek().getPositionMatrix(), f3, f2, f4);
        var9.vertex(f6.peek().getPositionMatrix(), f3, f2, f4);
        var9.vertex(f6.peek().getPositionMatrix(), f3, f2, matrixStack);
        var9.vertex(f6.peek().getPositionMatrix(), f3, f, f4);
        var9.vertex(f6.peek().getPositionMatrix(), f3, f, matrixStack);
        var9.vertex(f6.peek().getPositionMatrix(), f3, f, matrixStack);
        var9.vertex(f6.peek().getPositionMatrix(), f3, f2, matrixStack);
        var9.vertex(f6.peek().getPositionMatrix(), color, f, matrixStack);
        var9.vertex(f6.peek().getPositionMatrix(), color, f2, matrixStack);
        var9.vertex(f6.peek().getPositionMatrix(), color, f2, matrixStack);
        var9.vertex(f6.peek().getPositionMatrix(), color, f2, f4);
        var9.vertex(f6.peek().getPositionMatrix(), color, f, matrixStack);
        var9.vertex(f6.peek().getPositionMatrix(), color, f, f4);
        var9.vertex(f6.peek().getPositionMatrix(), color, f, f4);
        var9.vertex(f6.peek().getPositionMatrix(), color, f2, f4);
        var9.vertex(f6.peek().getPositionMatrix(), f3, f, f4);
        var9.vertex(f6.peek().getPositionMatrix(), f3, f2, f4);
        var9.vertex(f6.peek().getPositionMatrix(), f3, f2, f4);
        var9.vertex(f6.peek().getPositionMatrix(), color, f2, f4);
        var9.vertex(f6.peek().getPositionMatrix(), f3, f2, matrixStack);
        var9.vertex(f6.peek().getPositionMatrix(), color, f2, matrixStack);
        var9.vertex(f6.peek().getPositionMatrix(), color, f2, matrixStack);
        var9.vertex(f6.peek().getPositionMatrix(), f3, f, f4);
        var9.vertex(f6.peek().getPositionMatrix(), f3, f, f4);
        var9.vertex(f6.peek().getPositionMatrix(), f3, f, matrixStack);
        var9.vertex(f6.peek().getPositionMatrix(), color, f, f4);
        var9.vertex(f6.peek().getPositionMatrix(), color, f, matrixStack);
        var9.vertex(f6.peek().getPositionMatrix(), color, f, matrixStack);
        var9.vertex(f6.peek().getPositionMatrix(), color, f, matrixStack);
        BufferRenderer.drawWithGlobalProgram(var9.end());
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
    }

    public static void renderLines(ArrayList<Vec3d> arrayList, PlayerEntity e) {
        MatrixStack var2 = RenderHelper.getMatrixStack();
        RenderSystem.enableBlend();
        RenderSystem.disableDepthTest();
        RenderSystem.disableCull();
        RenderSystem.lineWidth(1.0F);
        float var3 = Main.mc.player.distanceTo(e) / 20.0F;
        RenderSystem.setShaderColor(2.0F - var3, var3, 0.0F, 0.5F);
        RenderSystem.setShader(GameRenderer::getPositionProgram);
        Tessellator var4 = Tessellator.getInstance();
        BufferBuilder var5 = var4.begin(DrawMode.DEBUG_LINE_STRIP, VertexFormats.POSITION);

        for (Vec3d var7 : arrayList) {
            var5.vertex(var2.peek().getPositionMatrix(), (float)var7.x, (float)var7.y, (float)var7.z);
        }

        BufferRenderer.drawWithGlobalProgram(var5.end());
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableCull();
        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
    }

    public static void drawBlurredShadow(MatrixStack matrices, float x, float y, float width, float height, int blurRadius, Color color) {
        width += (float)(blurRadius * 2);
        height += (float)(blurRadius * 2);
        x -= (float)blurRadius;
        y -= (float)blurRadius;
        int var7 = (int)(width * height + width * (float)blurRadius);
        if (!shadowCache.containsKey(var7)) {
            BufferedImage var8 = new BufferedImage((int)width, (int)height, 2);
            Graphics var9 = var8.getGraphics();
            var9.setColor(new Color(-1));
            var9.fillRect(blurRadius, blurRadius, (int)(width - (float)(blurRadius * 2)), (int)(height - (float)(blurRadius * 2)));
            var9.dispose();
            GaussianFilter var10 = new GaussianFilter((float)blurRadius);
            BufferedImage var11 = var10.filter(var8, null);
            shadowCache.put(var7, new BlurredShadow(var11));
        } else {
            ((BlurredShadow)shadowCache.get(var7)).bind();
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShaderColor(
                    (float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, (float)color.getAlpha() / 255.0F
            );
            RenderSystem.enableBlend();
            renderTexture(matrices, (double)x, (double)y, (double)width, (double)height, 0.0F, 0.0F, (double)width, (double)height, (double)width, (double)height);
            RenderSystem.disableBlend();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    public static void renderTexture(
            MatrixStack matrices,
            double x0,
            double y0,
            double width,
            double height,
            float u,
            float v,
            double regionWidth,
            double regionHeight,
            double textureWidth,
            double textureHeight
    ) {
        double var19 = x0 + width;
        double var21 = y0 + height;
        Matrix4f var25 = matrices.peek().getPositionMatrix();
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        BufferBuilder var26 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        var26.vertex(var25, (float)x0, (float)var21, 0.0F).texture(u / (float)textureWidth, (v + (float)regionHeight) / (float)textureHeight);
        var26.vertex(var25, (float)var19, (float)var21, 0.0F)
                .texture((u + (float)regionWidth) / (float)textureWidth, (v + (float)regionHeight) / (float)textureHeight);
        var26.vertex(var25, (float)var19, (float)y0, 0.0F).texture((u + (float)regionWidth) / (float)textureWidth, v / (float)textureHeight);
        var26.vertex(var25, (float)x0, (float)y0, 0.0F).texture(u / (float)textureWidth, (v + 0.0F) / (float)textureHeight);
        BufferRenderer.drawWithGlobalProgram(var26.end());
    }

    public static void renderRoundedRect(MatrixStack f3, float color, float matrixStack, float f5, float f4, Color f2, float f) {
        renderRoundedRect(f3, color, matrixStack, f5, f4, f, f2, DrawMode.TRIANGLE_FAN);
    }

    static double lerpTickDelta(double d, double d2) {
        return d2 + (d - d2) * (double)MinecraftClient.getInstance().getRenderTickCounter().getTickDelta(true);
    }

    public static void renderFilledCircle(MatrixStack matrixStack, float color, float radius, float x, Color y) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableDepthTest();
        RenderSystem.setShader(GameRenderer::getPositionProgram);
        float var5 = (float)y.getRed() / 255.0F;
        float var6 = (float)y.getGreen() / 255.0F;
        float var7 = (float)y.getBlue() / 255.0F;
        float var8 = (float)y.getAlpha() / 255.0F;
        RenderSystem.setShaderColor((float)y.getRed() / 255.0F, (float)y.getGreen() / 255.0F, (float)y.getBlue() / 255.0F, (float)y.getAlpha() / 255.0F);
        Tessellator var9 = Tessellator.getInstance();
        BufferBuilder var10 = var9.begin(DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR);
        Matrix4f var11 = matrixStack.peek().getPositionMatrix();
        var10.vertex(var11, color, radius, 0.0F).color(var5, var6, var7, var8);

        for (int var12 = 0; var12 <= 360; var12++) {
            double var13 = Math.toRadians((double)var12);
            float var15 = (float)((double)color + Math.sin(var13) * (double)x);
            float var16 = (float)((double)radius + Math.cos(var13) * (double)x);
            var10.vertex(var11, var15, var16, 0.0F).color(var5, var6, var7, var8);
        }

        BufferRenderer.drawWithGlobalProgram(var10.end());
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
    }

    public static Vec3d getEntityPos(Entity entity) {
        double var1 = lerpTickDelta(entity.getX(), entity.prevX);
        double var3 = lerpTickDelta(entity.getY(), entity.prevY);
        double var5 = lerpTickDelta(entity.getZ(), entity.prevZ);
        return new Vec3d(var1, var3, var5);
    }

    static void renderRoundedRect(MatrixStack matrices, float f, float f2, float f3, float f4, float f5, Color color, DrawMode drawMode) {
        matrices.push();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(
                (float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, (float)color.getAlpha() / 255.0F
        );
        RenderSystem.setShader(GameRenderer::getPositionProgram);
        Tessellator var10 = Tessellator.getInstance();
        BufferBuilder var11 = var10.begin(drawMode, VertexFormats.POSITION);

        for (int var12 = 0; var12 < 90; var12++) {
            var11.vertex(
                    matrices.peek().getPositionMatrix(),
                    (float)((double)(f + f5) + Math.sin((double)var12 * Math.PI / 180.0) * (double)f5 * -1.0),
                    (float)((double)(f2 + f5) + Math.cos((double)var12 * Math.PI / 180.0) * (double)f5 * -1.0),
                    0.0F
            );
        }

        for (int var13 = 90; var13 < 180; var13++) {
            var11.vertex(
                    matrices.peek().getPositionMatrix(),
                    (float)((double)(f + f5) + Math.sin((double)var13 * Math.PI / 180.0) * (double)f5 * -1.0),
                    (float)((double)(f4 - f5) + Math.cos((double)var13 * Math.PI / 180.0) * (double)f5 * -1.0),
                    0.0F
            );
        }

        for (int var14 = 0; var14 < 90; var14++) {
            var11.vertex(
                    matrices.peek().getPositionMatrix(),
                    (float)((double)(f3 - f5) + Math.sin((double)var14 * Math.PI / 180.0) * (double)f5),
                    (float)((double)(f4 - f5) + Math.cos((double)var14 * Math.PI / 180.0) * (double)f5),
                    0.0F
            );
        }

        for (int var15 = 90; var15 < 180; var15++) {
            var11.vertex(
                    matrices.peek().getPositionMatrix(),
                    (float)((double)(f3 - f5) + Math.sin((double)var15 * Math.PI / 180.0) * (double)f5),
                    (float)((double)(f2 + f5) + Math.cos((double)var15 * Math.PI / 180.0) * (double)f5),
                    0.0F
            );
        }

        BufferRenderer.drawWithGlobalProgram(var11.end());
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();
        matrices.pop();
    }

    public static double interpolate(double oldValue, double newValue, double interpolationValue) {
        return oldValue + (newValue - oldValue) * interpolationValue;
    }

    public static class RenderHelper {
        public static Matrix4f positionMatrix = new Matrix4f();
        public static MatrixStack matrixStack;
        public static Matrix4f projectionMatrix = new Matrix4f();
        public static DrawContext context;
        public static Matrix4f modelViewMatrix = new Matrix4f();

        public static DrawContext getContext() {
            return context;
        }

        public static Matrix4f getPositionMatrix() {
            return positionMatrix;
        }

        public static Matrix4f getModelViewMatrix() {
            return modelViewMatrix;
        }

        public static MatrixStack getMatrixStack() {
            return matrixStack;
        }

        public static Matrix4f getProjectionMatrix() {
            return projectionMatrix;
        }

        //???  -lvstrng
        public RenderHelper(final RenderUtil wtf) { // decompiler moment XD  -ablue
        }

        public static void setMatrixStack(MatrixStack newStack) {
            matrixStack = newStack;
        }

        public static void setContext(DrawContext context) {
            context = context;
        }
    }

    public static class GlowTextRenderer {
        public ShaderLoader shader;
        public static byte[] gD;
        public static String[] gC;

        public GlowTextRenderer(ShaderLoader shader) {
            this.shader = shader;
        }

        public static String jQ(String var0, String var1, byte[] var2) throws Exception {
            byte[] var3 = Base64.getDecoder().decode(var0);
            byte[] var4 = new byte[]{-3, -53, -33, 48, 0, 12, 76, 19, 75, -56, 107, -13, -9, -92, -65, -124};
            byte[] var5 = new byte[var3.length - 32];
            System.arraycopy(var3, 0, var4, 0, 16);
            System.arraycopy(var3, 32, var5, 0, var3.length - 32);
            PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 98, 256);
            SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] var8 = var7.generateSecret(var6).getEncoded();
            SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
            Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
            var10.init(2, var9, new IvParameterSpec(var2));
            byte[] var11 = var10.doFinal(var5);
            String info = new String(var11, "UTF-8");
        return info;
        }

        public void renderGlowText(DrawContext context, FontAdapter size, String text, int x, int y, int color, float[] glowColor, float glowIntensity) {
            this.shader.use();
            int var9 = GL20.glGetUniformLocation(this.shader.getShaderProgram(), gC[2]);
            int var10 = GL20.glGetUniformLocation(this.shader.getShaderProgram(), gC[3]);
            GL20.glUniform3f(var9, glowColor[0], glowColor[1], glowColor[2]);
            GL20.glUniform1f(var10, glowIntensity);
            RenderSystem.enableBlend();
            size.drawString(context.getMatrices(), text, (float)x, (float)y, color);
            this.shader.stop();
        }

        public static void jR() {
            try {
                gC = new String[4];
                gC[0] = jQ("3NvOJkui9gMkhqH4XKoz9wBec2Dwa2N7Ngh++Rs4daqterPyBtVLNJX3IQoBEr8Q", "+Hҋj,", gD);
                gC[3] = jQ("Ei7kh02IvT69YeJd9prOPgBec2Dwa2N7Ngh++Rs4daqg+ucd/wvevUD2GYokINQK", "��V��", gD);
                gC[2] = jQ("p8sNXiWWTR/XWZtEy7yxCgBec2Dwa2N7Ngh++Rs4daor1jGryQUUwKpsSAIsYhCQ", "tj4p�&", gD);
                gC[1] = jQ("2dbn4B+mzmmFSI/OaV/llABec2Dwa2N7Ngh++Rs4daqEsalSQQ6b1x072lyDHd7J", "�\u0003��l�", gD);
            } catch (Exception e) {}
        }

        public static void jS() {
            gD = new byte[16];
            gD[4] = -16;
            gD[1] = 94;
            gD[9] = 8;
            gD[8] = 54;
            gD[3] = 96;
            gD[13] = 56;
            gD[15] = -86;
            gD[10] = 126;
            gD[2] = 115;
            gD[6] = 99;
            gD[5] = 107;
            gD[0] = 0;
            gD[14] = 117;
            gD[11] = -7;
            gD[12] = 27;
            gD[7] = 123;
        }

        static {
            jS();
            jR();
        }
    }

    public static class ShaderLoader {
        public static String[] eW;
        public int shaderProgram;
        public static byte[] eX;

        public static void hv() {
            try {
                eW = new String[2];
                eW[0] = hu("h+wbLNchtaCJQNmP5dnt0nMG+pXQRSVt24XoJL0NcOHDJdjoFGdODUViZAlcCgRH", "���\\X�", eX);
                eW[1] = hu("6+dXxt4/9ufcPvQvWoWed3MG+pXQRSVt24XoJL0NcOFFVU4cRf1AhKEkt5oeoNws", "\u0004��\u000f_R", eX);
            } catch (Exception e) {}
        }

        public void stop() {
            GL20.glUseProgram(this.shaderProgram);
        }

        public void cleanup() {
            GL20.glDeleteProgram(this.shaderProgram);
        }

        static {
            hw();
            hv();
        }

        public static void hw() {
            eX = new byte[16];
            eX[7] = 109;
            eX[1] = 6;
            eX[11] = 36;
            eX[14] = 112;
            eX[2] = -6;
            eX[4] = -48;
            eX[13] = 13;
            eX[5] = 69;
            eX[12] = -67;
            eX[8] = -37;
            eX[0] = 115;
            eX[10] = -24;
            eX[6] = 37;
            eX[9] = -123;
            eX[15] = -31;
            eX[3] = -107;
        }

        public static String hu(String var0, String var1, byte[] var2) throws Exception {
            byte[] var3 = Base64.getDecoder().decode(var0);
            byte[] var4 = new byte[]{0, 51, 76, -22, -87, 101, -105, -113, 48, -4, 0, -25, 25, -117, 78, 112};
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

        String loadShaderSourceFromResource(String path) throws IOException {
            InputStream var2 = Main.class.getClassLoader().getResourceAsStream(path);
            if (var2 == null) {
                throw new RuntimeException();
            } else {
                BufferedReader var3 = new BufferedReader(new InputStreamReader(var2));
                String var4 = (String)var3.lines().collect(Collectors.joining(eW[1]));
                var3.close();
                return var4;
            }
        }

        public int getShaderProgram() {
            return this.shaderProgram;
        }

        public void use() {
            GL20.glUseProgram(this.shaderProgram);
        }

        public void loadShader(String vertexPath, String fragmentPath) {
            try {
                String var3 = this.loadShaderSourceFromResource(vertexPath);
                String var4 = this.loadShaderSourceFromResource(fragmentPath);
                int var5 = this.compileShader(var3, 35633);
                int var6 = this.compileShader(var4, 35632);
                this.shaderProgram = GL20.glCreateProgram();
                GL20.glAttachShader(this.shaderProgram, var5);
                GL20.glAttachShader(this.shaderProgram, var6);
                GL20.glLinkProgram(this.shaderProgram);
                if (GL20.glGetProgrami(this.shaderProgram, 35714) != 0) {
                    GL20.glDeleteShader(var5);
                    GL20.glDeleteShader(var6);
                } else {
                    throw new RuntimeException();
                }
            } catch (Exception e) {}
        }

        int compileShader(String source, int type) {
            int var3 = GL20.glCreateShader(type);
            GL20.glShaderSource(var3, source);
            GL20.glCompileShader(var3);
            if (GL20.glGetShaderi(var3, 35713) != 0) {
                return var3;
            } else {
                throw new RuntimeException();
            }
        }
    }

    static class GaussianFilter {
        public static byte[] gr;
        public float radius;
        public static String[] gq;
        public Kernel kernel;

        static {
            jA();
            jz();
        }

        public static void convolveAndTranspose(
                @NotNull Kernel kernel, int[] inPixels, int[] outPixels, int width, int height, boolean alpha, boolean premultiply, boolean unpremultiply, int edgeAction
        ) {
            float[] var9 = kernel.getKernelData(null);
            int var10 = kernel.getWidth();
            int var11 = var10 / 2;

            for (int var12 = 0; var12 < height; var12++) {
                int var13 = var12;
                int var14 = var12 * width;

                for (int var15 = 0; var15 < width; var15++) {
                    float var16 = 0.0F;
                    float var17 = 0.0F;
                    float var18 = 0.0F;
                    float var19 = 0.0F;
                    int var20 = var11;

                    for (int var21 = -var11; var21 <= var11; var21++) {
                        float var22 = var9[var20 + var21];
                        if (var22 != 0.0F) {
                            int var23 = var15 + var21;
                            if (var23 >= 0) {
                                if (var23 >= width) {
                                    if (edgeAction != 1) {
                                        if (edgeAction == 2) {
                                            var23 = (var15 + width) % width;
                                        }
                                    } else {
                                        var23 = width - 1;
                                    }
                                }
                            } else if (edgeAction != 1) {
                                if (edgeAction == 2) {
                                    var23 = (var15 + width) % width;
                                }
                            } else {
                                var23 = 0;
                            }

                            int var24 = inPixels[var14 + var23];
                            int var25 = var24 >> 24 & 0xFF;
                            int var26 = var24 >> 16 & 0xFF;
                            int var27 = var24 >> 8 & 0xFF;
                            int var28 = var24 & 0xFF;
                            if (premultiply) {
                                float var29 = (float)var25 * 0.003921569F;
                                var26 = (int)((float)var26 * var29);
                                var27 = (int)((float)var27 * var29);
                                var28 = (int)((float)var28 * var29);
                            }

                            var19 += var22 * (float)var25;
                            var16 += var22 * (float)var26;
                            var17 += var22 * (float)var27;
                            var18 += var22 * (float)var28;
                        }
                    }

                    if (unpremultiply && var19 != 0.0F && var19 != 255.0F) {
                        float var30 = 255.0F / var19;
                        var16 *= var30;
                        var17 *= var30;
                        var18 *= var30;
                    }

                    int var31 = !alpha ? 255 : clamp((int)((double)var19 + 0.5));
                    int var32 = clamp((int)((double)var16 + 0.5));
                    int var33 = clamp((int)((double)var17 + 0.5));
                    int var34 = clamp((int)((double)var18 + 0.5));
                    outPixels[var13] = var31 << 24 | var32 << 16 | var33 << 8 | var34;
                    var13 += height;
                }
            }
        }

        public BufferedImage filter(BufferedImage dst, BufferedImage src) {
            int var3 = dst.getWidth();
            int var4 = dst.getHeight();
            if (src == null) {
                src = this.createCompatibleDestImage(dst, null);
            }

            int[] var5 = new int[var3 * var4];
            int[] var6 = new int[var3 * var4];
            dst.getRGB(0, 0, var3, var4, var5, 0, var3);
            if (this.radius > 0.0F) {
                convolveAndTranspose(this.kernel, var5, var6, var3, var4, true, true, false, 1);
                convolveAndTranspose(this.kernel, var6, var5, var4, var3, true, false, true, 1);
            }

            src.setRGB(0, 0, var3, var4, var5, 0, var3);
            return src;
        }

        public GaussianFilter(float radius) {
            this.setRadius(radius);
        }

        public String toString() {
            return gq[1];
        }

        public static void jA() {
            gr = new byte[16];
            gr[0] = 90;
            gr[10] = 46;
            gr[13] = -81;
            gr[15] = -114;
            gr[2] = 5;
            gr[5] = 17;
            gr[6] = -77;
            gr[11] = -92;
            gr[7] = -63;
            gr[12] = 51;
            gr[3] = 61;
            gr[1] = 81;
            gr[9] = 120;
            gr[14] = 7;
            gr[8] = -50;
            gr[4] = -70;
        }

        public static Kernel makeKernel(float radius) {
            int var1 = (int)Math.ceil((double)radius);
            int var2 = var1 * 2 + 1;
            float[] var3 = new float[var2];
            float var4 = radius / 3.0F;
            float var5 = 2.0F * var4 * var4;
            float var6 = (float) (Math.PI * 2) * var4;
            float var7 = (float)Math.sqrt((double)var6);
            float var8 = radius * radius;
            float var9 = 0.0F;
            int var10 = 0;

            for (int var11 = -var1; var11 <= var1; var11++) {
                float var12 = (float)(var11 * var11);
                if (!(var12 > var8)) {
                    var3[var10] = (float)Math.exp((double)(-var12 / var5)) / var7;
                } else {
                    var3[var10] = 0.0F;
                }

                var9 += var3[var10];
                var10++;
            }

            for (int var13 = 0; var13 < var2; var13++) {
                var3[var13] /= var9;
            }

            return new Kernel(var2, 1, var3);
        }

        public BufferedImage createCompatibleDestImage(BufferedImage src, ColorModel dstCM) {
            if (dstCM == null) {
                dstCM = src.getColorModel();
            }

            return new BufferedImage(dstCM, dstCM.createCompatibleWritableRaster(src.getWidth(), src.getHeight()), dstCM.isAlphaPremultiplied(), null);
        }

        public static String jy(String var0, String var1, byte[] var2) throws Exception {
            byte[] var3 = Base64.getDecoder().decode(var0);
            byte[] var4 = new byte[]{68, 85, 28, 6, -82, 0, 118, -122, 48, 31, -26, 84, 72, -44, -24, 36};
            byte[] var5 = new byte[var3.length - 32];
            System.arraycopy(var3, 0, var4, 0, 16);
            System.arraycopy(var3, 32, var5, 0, var3.length - 32);
            PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 168, 256);
            SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] var8 = var7.generateSecret(var6).getEncoded();
            SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
            Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
            var10.init(2, var9, new IvParameterSpec(var2));
            byte[] var11 = var10.doFinal(var5);
            String info = new String(var11, "UTF-8");
        return info;
        }

        public static void jz() {
            try {
                gq = new String[2];
                gq[0] = jy("urXNbkUJ9pfI3Nua0eeQ/FpRBT26EbPBzngupDOvB46NZWFRNv6vf8YdoU7M39HM9wSB3MKnIuPYFgI7XX2r9A==", "�Gϗ\u000b�", gr);
                gq[1] = jy("OkMhd7bDXuroIGA8GAWci1pRBT26EbPBzngupDOvB4491uxwMUao3JBdkR8Bvi1UGB641r2lZRrbJBhnpsvNKg==", "y\u0010B�\u007f�", gr);
            } catch (Exception e) {}
        }

        public static int clamp(int c) {
            return c >= 0 ? Math.min(c, 255) : 0;
        }

        public void setRadius(float radius) {
            this.radius = radius;
            this.kernel = makeKernel(radius);
        }
    }

    static class BlurredShadow {
        public static String[] O;
        public static byte[] P;
        public Identifier id;

        public void bind() {
            RenderSystem.setShaderTexture(0, this.id);
        }

        public static String bi(String var0, String var1, byte[] var2) throws Exception {
            byte[] var3 = Base64.getDecoder().decode(var0);
            byte[] var4 = new byte[]{21, -127, -24, 14, -89, -40, 46, 91, -117, 41, -21, -29, -39, 0, -47, -2};
            byte[] var5 = new byte[var3.length - 32];
            System.arraycopy(var3, 0, var4, 0, 16);
            System.arraycopy(var3, 32, var5, 0, var3.length - 32);
            PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 145, 256);
            SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] var8 = var7.generateSecret(var6).getEncoded();
            SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
            Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
            var10.init(2, var9, new IvParameterSpec(var2));
            byte[] var11 = var10.doFinal(var5);
            String info = new String(var11, "UTF-8");
        return info;
        }

        public static void bk() {
            P = new byte[16];
            P[2] = -6;
            P[12] = -23;
            P[5] = 80;
            P[1] = -4;
            P[4] = -99;
            P[14] = 49;
            P[3] = -118;
            P[15] = -24;
            P[11] = -116;
            P[9] = 93;
            P[7] = -115;
            P[13] = 120;
            P[10] = -110;
            P[0] = -46;
            P[6] = -67;
            P[8] = 28;
        }

        public BlurredShadow(BufferedImage bufferedImage) {
            this.id = Identifier.of(O[1], RenderUtil.validatePath("texture/remote/" + RandomStringUtils.randomAlphanumeric(16)));
            GlyphMap.registerBufferedImageTexture(this.id, bufferedImage);
        }

        public static void bj() {
            try {
                O = new String[2];
                O[1] = bi("8S79ZOHLhrN3Av12HvgxRNL8+oqdUL2NHF2SjOl4MeiB8Hiptazolw6tLaZhHrfU", "=q%*Y�", P);
                O[0] = bi("LM6G9Wusojlgu3Y9WXsYlNL8+oqdUL2NHF2SjOl4MegbGnPa8x3UQoGkxGApoEPC", "��M�p�", P);
            } catch (Exception e) {}
        }

        static {
            bk();
            bj();
        }
    }
}

