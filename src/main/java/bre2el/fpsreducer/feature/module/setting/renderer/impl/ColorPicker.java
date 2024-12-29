package bre2el.fpsreducer.feature.module.setting.renderer.impl;

import org.lwjgl.glfw.GLFW;
import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.gui.font.FontRenderers;
import net.minecraft.client.gui.DrawContext;
import java.awt.Color;
import bre2el.fpsreducer.gui.impl.button.ModuleButton;
import bre2el.fpsreducer.feature.module.setting.ColorPickerSetting;
import bre2el.fpsreducer.feature.module.setting.Setting;
import bre2el.fpsreducer.feature.module.setting.renderer.RenderSetting;

public class ColorPicker extends RenderSetting
{
    public Setting setting;
    public boolean draggingPalette;
    public ColorPickerSetting colorPickerSetting;
    public int selectedColor;

    public ColorPicker(final Setting offset, final ModuleButton setting, final int parent) {
        super(offset, setting, parent);
        this.draggingPalette = false;
        this.setting = offset;
        this.colorPickerSetting = (ColorPickerSetting)offset;
        this.selectedColor = this.colorPickerSetting.getColor().getRGB();
    }

    @Override
    public void mouseReleased(final double mouseX, final double mouseY, final int button) {
        this.draggingPalette = false;
    }

    @Override
    public void mouseClicked(final double mouseX, final double mouseY, final int button) {
        final int n = this.parent.frame.x + 22;
        final int n2 = this.parent.frame.y + this.parent.frame.height + this.parent.offset + this.parent.settings.indexOf(this) * 20;
        if (this.isHovering(mouseX, mouseY, n, n2, 18.0, 18.0)) {
            this.draggingPalette = true;
            this.updateSelectedColor(mouseX - n, mouseY - n2, 18.0, 18.0);
        }
    }

    void updateSelectedColor(final double localX, final double localY, final double width, final double height) {
        this.selectedColor = Color.HSBtoRGB((float)Math.max(0.0, Math.min(localX / (float)width, 1.0)), (float)Math.max(0.0, Math.min(localY / (float)height, 1.0)), 1.0f);
        this.colorPickerSetting.setColor(new Color(this.selectedColor));
    }

    boolean isHovering(final double y, final double height, final double width, final double mouseX, final double mouseY, final double x) {
        if (y >= width) {
            if (y <= width + mouseY) {
                if (height >= mouseX) {
                    if (height <= mouseX + x) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void render(final DrawContext delta, final int mouseX, final int context, final float mouseY) {
        if (this.parent.extended) {
            int n = this.parent.frame.x + 22;
            int n2 = this.parent.frame.y + this.parent.frame.height + this.parent.offset + this.parent.settings.indexOf(this) * 20;
            int n3 = 18;
            int n4 = 18;
            for (int i = 0; i < 18; ++i) {
                for (int j = 0; j < 18; ++j) {
                    delta.fill(n + i, n2 + j, n + i + 1, n2 + j + 1, Color.HSBtoRGB(i / 18.0f, j / 18.0f, 1.0f));
                }
            }
            delta.drawBorder(n, n2, 18, 18, Color.BLACK.getRGB());
            delta.fill(n + 18 + 10, n2, n + 18 + 50, n2 + 15, this.selectedColor);
            FontRenderers.Sub.drawString(delta.getMatrices(), this.colorPickerSetting.getName(), (float)(n + 18 + 11), (float)(n2 - 11), Color.BLACK.getRGB());
            FontRenderers.Sub.drawString(delta.getMatrices(), this.colorPickerSetting.getName(), (float)(n + 18 + 10), (float)(n2 - 10), Color.WHITE.getRGB());
            if (GLFW.glfwGetMouseButton(Main.mc.getWindow().getHandle(), 0) == 1) {
                n = this.parent.frame.x + 22;
                n2 = this.parent.frame.y + this.parent.frame.height + this.parent.offset + this.parent.settings.indexOf(this) * 20;
                n3 = 18;
                n4 = 18;
                if (this.draggingPalette) {
                    this.updateSelectedColor(mouseX - n, context - n2, 18.0, 18.0);
                }
            }
            if (this.isHovering(mouseX, context, n, n2, n3, n4)) {
                this.parent.frame.cursorSetting = this.setting;
            }
            super.render(delta, mouseX, context, mouseY);
        }
    }
}
