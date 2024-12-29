package bre2el.fpsreducer.feature.module.setting.renderer;

import net.minecraft.client.gui.DrawContext;
import bre2el.fpsreducer.feature.module.setting.Setting;
import bre2el.fpsreducer.gui.impl.button.ModuleButton;

public class RenderSetting
{
    public int offset;
    public ModuleButton parent;
    public Setting setting;

    public void render(final DrawContext context, final int mouseY, final int delta, final float mouseX) {
    }

    public RenderSetting(final Setting parent, final ModuleButton setting, final int offset) {
        this.setting = parent;
        this.parent = setting;
        this.offset = offset;
    }

    public void keyReleased(final int key) {
    }

    public void mouseClicked(final double mouseX, final double mouseY, final int button) {
    }

    public void keyPressed(final int key) {
    }

    public void mouseReleased(final double button, final double mouseY, final int mouseX) {
    }
}
