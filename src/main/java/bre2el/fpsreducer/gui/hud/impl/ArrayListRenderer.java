package bre2el.fpsreducer.gui.hud.impl;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.client.Main.Theme;
import bre2el.fpsreducer.feature.module.Module;
import bre2el.fpsreducer.feature.module.ModuleManager;
import bre2el.fpsreducer.gui.font.FontRenderers;
import bre2el.fpsreducer.util.ColorUtil;
import bre2el.fpsreducer.util.RenderUtil.GlowTextRenderer;
import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

public class ArrayListRenderer implements Drawable {
    public int width;
    public int dragX;
    public int x = 5;
    public boolean dragging;
    public int height;
    public int y = 5;
    public int index;
    public static ArrayListRenderer INSTANCE = new ArrayListRenderer();
    public int dragY;

    public void renderHUD(DrawContext context, boolean lower) {
        this.index = 0;
        this.height = 12;
        int var3 = 0;
        RenderSystem.enableBlend();
        GL11.glEnable(32925);
        GlowTextRenderer var4 = new GlowTextRenderer(Main.SHADERS);

        for (Module var6 : ModuleManager.INSTANCE.getEnabledModules(lower)) {
            if (lower) {
                if (!((float)this.x <= (float)Main.mc.getWindow().getScaledWidth() / 2.0F)) {
                    FontRenderers.Main
                            .drawString(
                                    context.getMatrices(),
                                    (var6.getName() + " " + var6.getDetail()).toLowerCase(),
                                    (float)this.x - FontRenderers.Main.getStringWidth((var6.getName() + " " + var6.getDetail()).toLowerCase()) + 1.0F,
                                    (float)(this.y + 1 + this.index),
                                    Color.BLACK.getRGB(),
                                    true
                            );
                    FontRenderers.Main
                            .drawString(
                                    context.getMatrices(),
                                    (var6.getName() + " " + var6.getDetail()).toLowerCase(),
                                    (float)this.x - FontRenderers.Main.getStringWidth((var6.getName() + " " + var6.getDetail()).toLowerCase()),
                                    (float)(this.y + this.index),
                                    new Color(120, 120, 120, 255).getRGB(),
                                    true
                            );
                    FontRenderers.Main
                            .drawString(
                                    context.getMatrices(),
                                    (var6.getName() + " ").toLowerCase(),
                                    (float)this.x - FontRenderers.Main.getStringWidth((var6.getName() + " " + var6.getDetail()).toLowerCase()),
                                    (float)(this.y + this.index),
                                    ColorUtil.TwoColoreffect(Theme.PRIMARY, Theme.SECONDARY, 10.0, (double)var3).getRGB(),
                                    true
                            );
                    var4.renderGlowText(
                            context,
                            FontRenderers.Main,
                            (var6.getName() + " ").toLowerCase(),
                            (int)((float)this.x - FontRenderers.Main.getStringWidth((var6.getName() + " " + var6.getDetail()).toLowerCase())),
                            this.y + this.index,
                            ColorUtil.TwoColoreffect(Theme.SECONDARY, Theme.PRIMARY, 10.0, (double)var3).getRGB(),
                            ColorUtil.colorToFloatArray(ColorUtil.TwoColoreffect(Theme.SECONDARY, Theme.PRIMARY, 10.0, (double)var3).getRGB()),
                            100.0F
                    );
                } else {
                    FontRenderers.Main
                            .drawString(
                                    context.getMatrices(),
                                    (var6.getName() + " " + var6.getDetail()).toLowerCase(),
                                    (float)(this.x + 1),
                                    (float)(this.y + 1 + this.index),
                                    Color.BLACK.getRGB(),
                                    true
                            );
                    FontRenderers.Main
                            .drawString(
                                    context.getMatrices(),
                                    (var6.getName() + " ").toLowerCase(),
                                    (float)this.x,
                                    (float)(this.y + this.index),
                                    ColorUtil.TwoColoreffect(Theme.PRIMARY, Theme.SECONDARY, 7.0, (double)var3).getRGB(),
                                    true
                            );
                    var4.renderGlowText(
                            context,
                            FontRenderers.Main,
                            (var6.getName() + " ").toLowerCase(),
                            this.x,
                            this.y + this.index,
                            ColorUtil.TwoColoreffect(Theme.SECONDARY, Theme.PRIMARY, 7.0, (double)var3).getRGB(),
                            ColorUtil.colorToFloatArray(ColorUtil.TwoColoreffect(Theme.SECONDARY, Theme.PRIMARY, 7.0, (double)var3).getRGB()),
                            100.0F
                    );
                    FontRenderers.Main
                            .drawString(
                                    context.getMatrices(),
                                    var6.getDetail().toLowerCase(),
                                    (float)this.x + FontRenderers.Main.getStringWidth((var6.getName() + " ").toLowerCase()),
                                    (float)(this.y + this.index),
                                    new Color(120, 120, 120, 255).getRGB(),
                                    true
                            );
                }
            } else if (!((float)this.x <= (float)Main.mc.getWindow().getScaledWidth() / 2.0F)) {
                FontRenderers.Main
                        .drawString(
                                context.getMatrices(),
                                var6.getName() + " " + var6.getDetail(),
                                (float)this.x - FontRenderers.Main.getStringWidth(var6.getName() + " " + var6.getDetail()) + 1.0F,
                                (float)(this.y + 1 + this.index),
                                Color.BLACK.getRGB(),
                                true
                        );
                FontRenderers.Main
                        .drawString(
                                context.getMatrices(),
                                var6.getName() + " " + var6.getDetail(),
                                (float)this.x - FontRenderers.Main.getStringWidth(var6.getName() + " " + var6.getDetail()),
                                (float)(this.y + this.index),
                                new Color(120, 120, 120, 255).getRGB(),
                                true
                        );
                FontRenderers.Main
                        .drawString(
                                context.getMatrices(),
                                var6.getName() + " ",
                                (float)this.x - FontRenderers.Main.getStringWidth(var6.getName() + " " + var6.getDetail()),
                                (float)(this.y + this.index),
                                ColorUtil.TwoColoreffect(Theme.PRIMARY, Theme.SECONDARY, 10.0, (double)var3).getRGB(),
                                true
                        );
                var4.renderGlowText(
                        context,
                        FontRenderers.Main,
                        var6.getName() + " ",
                        (int)((float)this.x - FontRenderers.Main.getStringWidth(var6.getName() + " " + var6.getDetail())),
                        this.y + this.index,
                        ColorUtil.TwoColoreffect(Theme.SECONDARY, Theme.PRIMARY, 10.0, (double)var3).getRGB(),
                        ColorUtil.colorToFloatArray(ColorUtil.TwoColoreffect(Theme.SECONDARY, Theme.PRIMARY, 10.0, (double)var3).getRGB()),
                        100.0F
                );
            } else {
                FontRenderers.Main
                        .drawString(
                                context.getMatrices(),
                                var6.getName() + " " + var6.getDetail(),
                                (float)(this.x + 1),
                                (float)(this.y + 1 + this.index),
                                Color.BLACK.getRGB(),
                                true
                        );
                FontRenderers.Main
                        .drawString(
                                context.getMatrices(),
                                var6.getName() + " ",
                                (float)this.x,
                                (float)(this.y + this.index),
                                ColorUtil.TwoColoreffect(Theme.PRIMARY, Theme.SECONDARY, 7.0, (double)var3).getRGB(),
                                true
                        );
                var4.renderGlowText(
                        context,
                        FontRenderers.Main,
                        var6.getName() + " ",
                        this.x,
                        this.y + this.index,
                        ColorUtil.TwoColoreffect(Theme.SECONDARY, Theme.PRIMARY, 7.0, (double)var3).getRGB(),
                        ColorUtil.colorToFloatArray(ColorUtil.TwoColoreffect(Theme.SECONDARY, Theme.PRIMARY, 7.0, (double)var3).getRGB()),
                        100.0F
                );
                FontRenderers.Main
                        .drawString(
                                context.getMatrices(),
                                var6.getDetail(),
                                (float)this.x + FontRenderers.Main.getStringWidth(var6.getName() + " "),
                                (float)(this.y + this.index),
                                new Color(120, 120, 120, 255).getRGB(),
                                true
                        );
            }

            var3 += 50;
            this.index += 12;
            this.height += 12;
        }

        GL11.glDisable(32925);
        RenderSystem.disableBlend();
    }

    public void render(DrawContext mouseX, int delta, int mouseY, float context) {
        if (this.isHovered(delta, mouseY) && GLFW.glfwGetMouseButton(Main.mc.getWindow().getHandle(), 0) == 1) {
            this.dragging = true;
            this.dragX = delta - this.x;
            this.dragY = mouseY - this.y;
        }

        if (this.dragging) {
            this.x = delta - this.dragX;
            this.y = mouseY - this.dragY;
        }

        if (GLFW.glfwGetMouseButton(Main.mc.getWindow().getHandle(), 0) == 0) {
            this.dragging = false;
        }
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isHovered(int mouseY, int mouseX) {
        return mouseY > this.x && mouseY < this.x + this.width && mouseX > this.y && mouseX < this.y + this.height + this.index;
    }

    public ArrayListRenderer() {
        this.width = 15;
        this.height = 12;
    }
}
