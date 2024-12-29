package bre2el.fpsreducer.feature.module.setting.renderer.impl;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.gui.impl.button.ModuleButton;
import java.awt.Color;
import bre2el.fpsreducer.gui.font.FontRenderers;
import net.minecraft.client.gui.DrawContext;
import bre2el.fpsreducer.feature.module.setting.Setting;
import bre2el.fpsreducer.feature.module.setting.BoolSetting;
import bre2el.fpsreducer.feature.module.setting.renderer.RenderSetting;

public class CheckBox extends RenderSetting
{
    public BoolSetting boolSetting;
    public Setting setting;

    @Override
    public void mouseClicked(final double mouseX, final double button, final int mouseY) {
        if (this.parent.extended) {
            if (this.isHovered((int)mouseX, (int)button)) {
                if (mouseY == 0) {
                    this.boolSetting.setEnabled(!this.boolSetting.isEnabled());
                }
            }
            super.mouseClicked(mouseX, button, mouseY);
        }
    }

    public boolean isHovered(final int mouseY, final int mouseX) {
        if (mouseY > this.parent.frame.x + 10) {
            if (mouseY < this.parent.frame.x + 20) {
                if (mouseX > this.parent.frame.y + this.parent.offset + this.parent.settings.indexOf(this) * 20 + 10) {
                    if (mouseX < this.parent.frame.y + this.parent.offset + this.parent.settings.indexOf(this) * 20 + 20) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void render(final DrawContext context, final int mouseX, final int mouseY, final float delta) {
        if (this.parent.extended) {
            if (this.isHovered(mouseX, mouseY)) {
                this.parent.frame.cursorSetting = this.setting;
            }
            FontRenderers.Sub.drawString(context.getMatrices(), this.setting.getName(), (float)(this.parent.frame.x + 22), (float)(this.parent.frame.y + this.parent.frame.height + this.parent.offset + this.parent.settings.indexOf(this) * 20 - 6), Color.WHITE.getRGB(), true);
            context.fill(this.parent.frame.x + 10, this.parent.frame.y + this.parent.offset + this.parent.settings.indexOf(this) * 20 + 10, this.parent.frame.x + 20, this.parent.frame.y + this.parent.offset + this.parent.settings.indexOf(this) * 20 + 20, Color.DARK_GRAY.getRGB());
            if (this.boolSetting.isEnabled()) {
                context.fill(this.parent.frame.x + 12, this.parent.frame.y + this.parent.offset + this.parent.settings.indexOf(this) * 20 + 12, this.parent.frame.x + 18, this.parent.frame.y + this.parent.offset + this.parent.settings.indexOf(this) * 20 + 18, Main.Theme.PRIMARY.getRGB());
            }
            super.render(context, mouseX, mouseY, delta);
        }
    }

    public CheckBox(final Setting parent, final ModuleButton setting, final int offset) {
        super(parent, setting, offset);
        this.setting = parent;
        this.boolSetting = (BoolSetting)parent;
    }
}
