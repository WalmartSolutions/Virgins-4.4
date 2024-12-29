package bre2el.fpsreducer.feature.module.setting.renderer.impl;

import bre2el.fpsreducer.client.Main.Theme;
import bre2el.fpsreducer.feature.module.Module;
import bre2el.fpsreducer.feature.module.setting.NumberSetting;
import bre2el.fpsreducer.feature.module.setting.Setting;
import bre2el.fpsreducer.feature.module.setting.renderer.RenderSetting;
import bre2el.fpsreducer.gui.font.FontRenderers;
import bre2el.fpsreducer.gui.impl.button.ModuleButton;
import bre2el.fpsreducer.util.MathUtil;
import bre2el.fpsreducer.util.RenderUtil;
import java.awt.Color;
import net.minecraft.client.gui.DrawContext;

public class Slider extends RenderSetting {
    public boolean dragging;
    public Setting setting;
    public NumberSetting numberSetting;

    @Override
    public void render(DrawContext mouseY, int delta, int mouseX, float context) {
        if (this.parent.extended) {
            if (this.isHovered(delta, mouseX)) {
                this.parent.frame.cursorSetting = this.setting;
            }

            FontRenderers.Sub
                    .drawString(
                            mouseY.getMatrices(),
                            Module.formatString(this.setting.getName() + ": " + this.numberSetting.getDefaultValue()),
                            (float)(this.parent.frame.x + 22),
                            (float)(this.parent.frame.y + this.parent.frame.height + this.parent.offset + this.parent.settings.indexOf(this) * 20 - 6),
                            Color.WHITE.getRGB(),
                            true
                    );
            float var5 = (float)Math.min(this.parent.frame.width, Math.max(0, delta - this.parent.frame.x));
            float var6 = (float)(
                    (int)(
                            (double)this.parent.frame.width
                                    * (this.numberSetting.getDefaultValue() - this.numberSetting.getMin())
                                    / (this.numberSetting.getMax() - this.numberSetting.getMin())
                    )
            );
            var6 = MathUtil.interpolate((float)this.numberSetting.getDefaultValue(), var6, (float)System.currentTimeMillis() * 0.005F);
            RenderUtil.renderRoundedRect(
                    mouseY.getMatrices(),
                    (float)this.parent.frame.x,
                    (float)(this.parent.frame.y + this.parent.frame.height + this.parent.offset + this.parent.settings.indexOf(this) * 20 - 6 + 6),
                    (float)((int)((float)this.parent.frame.x + var6)),
                    (float)(this.parent.frame.y + this.parent.frame.height + this.parent.offset + this.parent.settings.indexOf(this) * 20 - 6 + 8),
                    Theme.PRIMARY,
                    0.0F
            );
            RenderUtil.renderFilledCircle(
                    mouseY.getMatrices(),
                    (float)this.parent.frame.x + var6,
                    (float)(this.parent.frame.y + this.parent.frame.height + this.parent.offset + this.parent.settings.indexOf(this) * 20 + 1),
                    3.0F,
                    Theme.PRIMARY
            );
            if (this.dragging) {
                if (var5 != 0.0F) {
                    this.numberSetting
                            .setValue(
                                    (double)(var5 / (float)this.parent.frame.width) * (this.numberSetting.getMax() - this.numberSetting.getMin())
                                            + this.numberSetting.getMin()
                            );
                } else {
                    this.numberSetting.setValue(this.numberSetting.getMin());
                }
            }

            super.render(mouseY, delta, mouseX, context);
        }
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX > this.parent.frame.x
                && mouseX < this.parent.frame.x + this.parent.frame.width
                && mouseY > this.parent.frame.y + this.parent.offset + this.parent.settings.indexOf(this) * 20 + 14
                && mouseY < this.parent.frame.y + this.parent.offset + this.parent.settings.indexOf(this) * 20 + 24;
    }

    public void setDragging(boolean dragging) {
        this.dragging = dragging;
    }

    @Override
    public void mouseClicked(double button, double mouseX, int mouseY) {
        if (this.parent.extended) {
            if (mouseY == 0 && this.isHovered((int)button, (int)mouseX)) {
                this.dragging = true;
            }

            super.mouseClicked(button, mouseX, mouseY);
        }
    }

    public Slider(Setting offset, ModuleButton parent, int setting) {
        super(offset, parent, setting);
        this.setting = offset;
        this.numberSetting = (NumberSetting)offset;
    }

    @Override
    public void mouseReleased(double mouseY, double button, int mouseX) {
        this.dragging = false;
        super.mouseReleased(mouseY, button, mouseX);
    }
}
