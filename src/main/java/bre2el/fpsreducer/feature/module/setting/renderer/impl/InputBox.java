package bre2el.fpsreducer.feature.module.setting.renderer.impl;

import bre2el.fpsreducer.feature.module.setting.KeySetting;
import bre2el.fpsreducer.feature.module.setting.Setting;
import bre2el.fpsreducer.feature.module.setting.renderer.RenderSetting;
import bre2el.fpsreducer.gui.font.FontRenderers;
import bre2el.fpsreducer.gui.impl.button.ModuleButton;
import bre2el.fpsreducer.util.InputUtil;
import java.awt.Color;
import net.minecraft.client.gui.DrawContext;

public class InputBox extends RenderSetting {
    public Setting setting;
    public KeySetting inputSetting;
    public boolean listening;

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX > this.parent.frame.x + 10
                && mouseX < this.parent.frame.x + this.parent.frame.width - 20
                && mouseY > this.parent.frame.y + this.parent.offset + this.parent.settings.indexOf(this) * 20 + 10
                && mouseY < this.parent.frame.y + this.parent.offset + this.parent.settings.indexOf(this) * 20 + 20;
    }

    @Override
    public void keyPressed(int key) {
        if (this.parent.extended) {
            if (this.listening) {
                if (key != 256 && key != 261) {
                    this.inputSetting.setKey(0);
                }

                this.inputSetting.setKey(key);
                this.listening = false;
            }

            super.keyPressed(key);
        }
    }

    public InputBox(Setting setting, ModuleButton parent, int offset) {
        super(setting, parent, offset);
        this.setting = setting;
        this.inputSetting = (KeySetting)setting;
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
                            !this.listening ? this.setting.getName() + ": " + InputUtil.getKeyString(this.inputSetting.getKey()) : this.setting.getName() + ": ...",
                            (float)(this.parent.frame.x + 22),
                            (float)(this.parent.frame.y + this.parent.frame.height + this.parent.offset + this.parent.settings.indexOf(this) * 20 - 6),
                            Color.WHITE.getRGB(),
                            true
                    );
            super.render(context, mouseX, mouseY, delta);
        }
    }

    @Override
    public void mouseClicked(double mouseX, double button, int mouseY) {
        if (this.parent.extended) {
            if (this.listening) {
                this.inputSetting.setKey(mouseY);
                this.listening = false;
            }

            if (this.isHovered((int)mouseX, (int)button) && mouseY == 0) {
                this.listening = !this.listening;
            }

            super.mouseClicked(mouseX, button, mouseY);
        }
    }
}
