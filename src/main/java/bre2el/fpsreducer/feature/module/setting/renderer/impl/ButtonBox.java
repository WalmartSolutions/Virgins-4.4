package bre2el.fpsreducer.feature.module.setting.renderer.impl;

import bre2el.fpsreducer.feature.module.setting.ActionSetting;
import bre2el.fpsreducer.feature.module.setting.Setting;
import bre2el.fpsreducer.feature.module.setting.renderer.RenderSetting;
import bre2el.fpsreducer.gui.font.FontRenderers;
import bre2el.fpsreducer.gui.impl.button.ModuleButton;
import java.awt.Color;
import net.minecraft.client.gui.DrawContext;

public class ButtonBox extends RenderSetting {
    public ActionSetting actionSetting;
    public Setting setting;

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (this.parent.extended) {
            if (this.isHovered((int)mouseX, (int)mouseY) && button == 0) {
                this.actionSetting.setActivated(true);
            }

            super.mouseClicked(mouseX, mouseY, button);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (this.parent.extended) {
            if (this.isHovered(mouseX, mouseY)) {
                this.parent.frame.cursorSetting = this.setting;
            }

            FontRenderers.Sub
                    .drawString(
                            context.getMatrices(),
                            this.setting.getName(),
                            (float)(this.parent.frame.x + 22),
                            (float)(this.parent.frame.y + this.parent.frame.height + this.parent.offset + this.parent.settings.indexOf(this) * 20 - 4),
                            Color.WHITE.getRGB(),
                            true
                    );
            super.render(context, mouseX, mouseY, delta);
        }
    }

    public ButtonBox(Setting offset, ModuleButton setting, int parent) {
        super(offset, setting, parent);
        this.setting = offset;
        this.actionSetting = (ActionSetting)offset;
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX > this.parent.frame.x + 10
                && mouseX < this.parent.frame.x + this.parent.frame.width - 20
                && mouseY > this.parent.frame.y + this.parent.offset + this.parent.settings.indexOf(this) * 20 + 10
                && mouseY < this.parent.frame.y + this.parent.offset + this.parent.settings.indexOf(this) * 20 + 20;
    }
}
