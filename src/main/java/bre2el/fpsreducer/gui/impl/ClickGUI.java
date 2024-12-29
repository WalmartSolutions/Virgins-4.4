package bre2el.fpsreducer.gui.impl;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.client.Main.Theme;
import bre2el.fpsreducer.feature.module.Module.Category;
import bre2el.fpsreducer.gui.font.FontRenderers;
import bre2el.fpsreducer.util.RenderUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class ClickGUI extends Screen {
    public static ClickGUI INSTANCE = new ClickGUI(Text.of(""));
    public List<Frame> frames = new ArrayList();
    public int adjustedMouseY;
    public int adjustedMouseX;

    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        for (Frame var7 : this.frames) {
            var7.mouseReleased((double)this.adjustedMouseX, (double)this.adjustedMouseY, button);
        }

        return super.mouseReleased((double)this.adjustedMouseX, (double)this.adjustedMouseY, button);
    }

    // im so smart
    public void render(DrawContext delta, int mouseY, int mouseX, float context) {
        this.applyBlur(context);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        GL13.glEnable(32925);
        GL11.glEnable(2848);
        float var5 = (float)Main.mc.getWindow().getFramebufferWidth() / (float)Main.mc.getWindow().getScaledWidth();
        float var6 = (float)Main.mc.getWindow().getFramebufferHeight() / (float)Main.mc.getWindow().getScaledHeight();
        float var7 = Math.min(var5, var6) * 0.5F;
        delta.getMatrices().push();
        delta.getMatrices().scale(1.0F / var7, 1.0F / var7, 1.0F);
        this.adjustedMouseX = (int)((float)mouseY * var7);
        this.adjustedMouseY = (int)((float)mouseX * var7);

        for (Frame var9 : this.frames) {
            var9.render(delta, this.adjustedMouseX, this.adjustedMouseY, context);
        }

        for (Frame var11 : this.frames) {
            if (var11.cursorModule != null) {
                RenderUtil.drawBlurredShadow(
                        delta.getMatrices(),
                        (float)this.adjustedMouseX,
                        (float)this.adjustedMouseY,
                        FontRenderers.Sub.getStringWidth(var11.cursorModule.getDescription()) + 8.0F,
                        15.0F,
                        4,
                        Theme.BACKGROUND
                );
                RenderUtil.renderRoundedRect(
                        delta.getMatrices(),
                        (float)this.adjustedMouseX,
                        (float)this.adjustedMouseY,
                        (float)this.adjustedMouseX + FontRenderers.Sub.getStringWidth(var11.cursorModule.getDescription()) + 8.0F,
                        (float)(this.adjustedMouseY + 15),
                        Theme.BACKGROUND,
                        4.0F
                );
                FontRenderers.Sub
                        .drawString(
                                delta.getMatrices(),
                                var11.cursorModule.getDescription(),
                                (float)(this.adjustedMouseX + 5),
                                (float)(this.adjustedMouseY + 7),
                                Theme.TEXT.getRGB()
                        );
                var11.cursorModule = null;
            }

            if (var11.cursorSetting != null) {
                RenderUtil.drawBlurredShadow(
                        delta.getMatrices(),
                        (float)this.adjustedMouseX,
                        (float)this.adjustedMouseY,
                        FontRenderers.Sub.getStringWidth(var11.cursorSetting.getDescription()) + 8.0F,
                        15.0F,
                        4,
                        Theme.BACKGROUND
                );
                RenderUtil.renderRoundedRect(
                        delta.getMatrices(),
                        (float)this.adjustedMouseX,
                        (float)this.adjustedMouseY,
                        (float)this.adjustedMouseX + FontRenderers.Sub.getStringWidth(var11.cursorSetting.getDescription()) + 8.0F,
                        (float)(this.adjustedMouseY + 15),
                        Theme.BACKGROUND,
                        4.0F
                );
                FontRenderers.Sub
                        .drawString(
                                delta.getMatrices(),
                                var11.cursorSetting.getDescription(),
                                (float)(this.adjustedMouseX + 5),
                                (float)(this.adjustedMouseY + 7),
                                Theme.TEXT.getRGB()
                        );
                var11.cursorSetting = null;
            }
        }

        super.render(delta, this.adjustedMouseX, this.adjustedMouseY, context);
        RenderSystem.disableBlend();
        GL13.glDisable(32925);
        GL11.glDisable(2848);
    }

    public boolean mouseScrolled(double mouseX, double horizontalAmount, double verticalAmount, double mouseY) {
        for (Frame var10 : this.frames) {
            var10.mouseScrolled((double)this.adjustedMouseX, (double)this.adjustedMouseY, verticalAmount, mouseY);
        }

        return super.mouseScrolled((double)this.adjustedMouseX, (double)this.adjustedMouseY, verticalAmount, mouseY);
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        for (Frame var5 : this.frames) {
            var5.keyPressed(keyCode);
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    public ClickGUI(Text title) {
        super(title);
        int var2 = 100;

        for (Category var6 : Category.values()) {
            this.frames.add(new Frame(var6, var2, 20, 105, 20));
            var2 += 110;
        }
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (Frame var7 : this.frames) {
            var7.mouseClicked((double)this.adjustedMouseX, (double)this.adjustedMouseY, button);
        }

        return super.mouseClicked((double)this.adjustedMouseX, (double)this.adjustedMouseY, button);
    }
}
