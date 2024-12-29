package bre2el.fpsreducer.feature.module.setting.renderer.impl;

import bre2el.fpsreducer.client.Main.Theme;
import bre2el.fpsreducer.feature.module.Module;
import bre2el.fpsreducer.feature.module.setting.RangeSetting;
import bre2el.fpsreducer.feature.module.setting.Setting;
import bre2el.fpsreducer.feature.module.setting.renderer.RenderSetting;
import bre2el.fpsreducer.gui.font.FontRenderers;
import bre2el.fpsreducer.gui.impl.button.ModuleButton;
import bre2el.fpsreducer.util.RenderUtil;
import java.awt.Color;
import net.minecraft.client.gui.DrawContext;

public class TwoSlider extends RenderSetting {
    public Setting setting;
    public int renderWidth;
    public RangeSetting rangeSetting;
    public boolean draggingSecond;
    public int renderWidthSecond;
    public boolean draggingFirst;

    public TwoSlider(Setting offset, ModuleButton parent, int setting) {
        super(offset, parent, setting);
        this.setting = offset;
        this.rangeSetting = (RangeSetting)offset;
    }

    @Override
    public void mouseReleased(double mouseY, double button, int mouseX) {
        this.draggingFirst = false;
        this.draggingSecond = false;
        super.mouseReleased(mouseY, button, mouseX);
    }

    public boolean isHoveredSecond(int mouseX, int mouseY) {
        return mouseX > this.parent.frame.x + this.renderWidthSecond - 4
                && mouseX < this.parent.frame.x + this.renderWidthSecond + (this.parent.frame.width - this.renderWidthSecond)
                && mouseY > this.parent.frame.y + this.parent.offset + this.parent.settings.indexOf(this) * 20 + 14
                && mouseY < this.parent.frame.y + this.parent.offset + this.parent.settings.indexOf(this) * 20 + 24;
    }

    @Override
    public void render(DrawContext delta, int mouseY, int context, float mouseX) {
        if (this.parent.extended) {
            if (this.isHovered(mouseY, context) || this.isHoveredSecond(mouseY, context)) {
                this.parent.frame.cursorSetting = this.setting;
            }

            FontRenderers.Sub
                    .drawString(
                            delta.getMatrices(),
                            Module.formatString(this.setting.getName() + ": " + this.rangeSetting.getDefaultValue())
                                    + Module.formatString(" - " + this.rangeSetting.getSecondValue()),
                            (float)(this.parent.frame.x + 22),
                            (float)(this.parent.frame.y + this.parent.frame.height + this.parent.offset + this.parent.settings.indexOf(this) * 20 - 6),
                            Color.WHITE.getRGB(),
                            true
                    );
            double var5 = (double)Math.min(this.parent.frame.width, Math.max(0, mouseY - this.parent.frame.x));
            this.renderWidth = (int)(
                    (double)this.parent.frame.width
                            * (this.rangeSetting.getDefaultValue() - this.rangeSetting.getMin())
                            / (this.rangeSetting.getMax() - this.rangeSetting.getMin())
            );
            this.renderWidthSecond = (int)(
                    (double)this.parent.frame.width
                            * (this.rangeSetting.getSecondValue() - this.rangeSetting.getMin())
                            / (this.rangeSetting.getMax() - this.rangeSetting.getMin())
            );
            delta.fill(
                    this.parent.frame.x + this.renderWidth,
                    this.parent.frame.y + this.parent.frame.height + this.parent.offset + this.parent.settings.indexOf(this) * 20 - 6 + 6,
                    this.parent.frame.x + this.renderWidthSecond,
                    this.parent.frame.y + this.parent.frame.height + this.parent.offset + this.parent.settings.indexOf(this) * 20 - 6 + 8,
                    Theme.PRIMARY.getRGB()
            );
            RenderUtil.renderFilledCircle(
                    delta.getMatrices(),
                    (float)(this.parent.frame.x + this.renderWidth),
                    (float)(this.parent.frame.y + this.parent.frame.height + this.parent.offset + this.parent.settings.indexOf(this) * 20 + 1),
                    3.0F,
                    Theme.PRIMARY
            );
            RenderUtil.renderFilledCircle(
                    delta.getMatrices(),
                    (float)(this.parent.frame.x + this.renderWidthSecond),
                    (float)(this.parent.frame.y + this.parent.frame.height + this.parent.offset + this.parent.settings.indexOf(this) * 20 + 1),
                    3.0F,
                    Theme.PRIMARY
            );
            if (this.draggingFirst) {
                if (var5 != 0.0) {
                    this.rangeSetting
                            .setFirst(var5 / (double)this.parent.frame.width * (this.rangeSetting.getMax() - this.rangeSetting.getMin()) + this.rangeSetting.getMin());
                } else {
                    this.rangeSetting.setFirst(this.rangeSetting.getMin());
                }
            }

            if (this.draggingSecond) {
                if (var5 != 0.0) {
                    this.rangeSetting
                            .setSecond(var5 / (double)this.parent.frame.width * (this.rangeSetting.getMax() - this.rangeSetting.getMin()) + this.rangeSetting.getMin());
                } else {
                    this.rangeSetting.setSecond(this.rangeSetting.getMin());
                }
            }

            super.render(delta, mouseY, context, mouseX);
        }
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX > this.parent.frame.x + this.renderWidth - (this.parent.frame.width + this.renderWidth)
                && mouseX < this.parent.frame.x + this.renderWidth + 4
                && mouseY > this.parent.frame.y + this.parent.offset + this.parent.settings.indexOf(this) * 20 + 14
                && mouseY < this.parent.frame.y + this.parent.offset + this.parent.settings.indexOf(this) * 20 + 24;
    }

    @Override
    public void mouseClicked(double mouseY, double mouseX, int button) {
        if (this.parent.extended) {
            if (button == 0) {
                if (this.isHovered((int)mouseY, (int)mouseX)) {
                    this.draggingFirst = true;
                }

                if (this.isHoveredSecond((int)mouseY, (int)mouseX)) {
                    this.draggingSecond = true;
                }
            }

            super.mouseClicked(mouseY, mouseX, button);
        }
    }
}
