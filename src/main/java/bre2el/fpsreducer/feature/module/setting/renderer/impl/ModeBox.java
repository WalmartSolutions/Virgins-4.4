package bre2el.fpsreducer.feature.module.setting.renderer.impl;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.client.Main.Theme;
import bre2el.fpsreducer.event.impl.MouseButtonEvent;
import bre2el.fpsreducer.event.orbit.EventHandler;
import bre2el.fpsreducer.feature.module.setting.ModeSetting;
import bre2el.fpsreducer.feature.module.setting.Setting;
import bre2el.fpsreducer.feature.module.setting.renderer.RenderSetting;
import bre2el.fpsreducer.gui.font.FontRenderers;
import bre2el.fpsreducer.gui.impl.button.ModuleButton;
import bre2el.fpsreducer.util.RenderUtil;
import java.awt.Color;
import net.minecraft.client.gui.DrawContext;

public class ModeBox extends RenderSetting {
    public boolean clicking;
    public Setting setting;
    public ModeSetting modeSetting;

    public ModeBox(Setting offset, ModuleButton parent, int setting) {
        super(offset, parent, setting);
        this.setting = offset;
        this.modeSetting = (ModeSetting)offset;
        Main.EVENTBUS.subscribe(this);
    }

    public boolean isHoveredMode(int mouseX, int mouseY, int count) {
        int var4 = this.parent.frame.y + this.parent.offset + this.parent.settings.indexOf(this) * 20;
        int var5 = var4 + 8 + count;
        int var6 = var5 + 12;
        return mouseX > this.parent.frame.x + 50 && mouseX < this.parent.frame.x + 100 && mouseY > var5 && mouseY < var6;
    }

    @Override
    public void mouseClicked(double mouseX, double button, int mouseY) {
        if (mouseY == 0) {
            int var6 = 0;
            if (this.modeSetting.isExtended()) {
                for (String var8 : this.modeSetting.modes) {
                    if (this.isHoveredMode((int)mouseX, (int)button, var6)) {
                        this.clicking = true;
                        this.modeSetting.setMode(var8);
                        this.modeSetting.setExtended(false);
                        return;
                    }

                    var6 += 12;
                }
            }

            if (this.isHovered((int)mouseX, (int)button)) {
                this.modeSetting.setExtended(!this.modeSetting.isExtended());
            }
        }

        super.mouseClicked(mouseX, button, mouseY);
    }

    public boolean isHovered(int mouseY, int mouseX) {
        return mouseY > this.parent.frame.x + 10
                && mouseY < this.parent.frame.x + this.parent.frame.width - 10
                && mouseX > this.parent.frame.y + this.parent.offset + this.parent.settings.indexOf(this) * 20 + 10
                && mouseX < this.parent.frame.y + this.parent.offset + this.parent.settings.indexOf(this) * 20 + 20;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (this.parent.extended) {
            if (this.isHovered(mouseX, mouseY)) {
                this.parent.frame.cursorSetting = this.setting;
            }

            int var5 = 0;
            FontRenderers.Sub
                    .drawString(
                            context.getMatrices(),
                            this.setting.getName(),
                            (float)(this.parent.frame.x + 22),
                            (float)(this.parent.frame.y + this.parent.frame.height + this.parent.offset + this.parent.settings.indexOf(this) * 20 - 6),
                            Color.WHITE.getRGB(),
                            true
                    );
            if (this.modeSetting.isExtended()) {
                RenderUtil.renderRoundedRect(
                        context.getMatrices(),
                        (float)(this.parent.frame.x + 50),
                        (float)(this.parent.frame.y + this.parent.offset + this.parent.settings.indexOf(this) * 20 + 8),
                        (float)(this.parent.frame.x + 100),
                        (float)(this.parent.frame.y + this.parent.offset + this.parent.settings.indexOf(this) * 20 + 22 + (this.modeSetting.modes.size() - 1) * 12),
                        Theme.PRIMARY,
                        2.0F
                );

                for (String var7 : this.modeSetting.modes) {
                    FontRenderers.Small
                            .drawString(
                                    context.getMatrices(),
                                    var7,
                                    (float)(this.parent.frame.x + 54),
                                    (float)(this.parent.frame.y + this.parent.frame.height + this.parent.offset + this.parent.settings.indexOf(this) * 20 - 6 + var5),
                                    Color.WHITE.getRGB(),
                                    true
                            );
                    var5 += 12;
                }
            } else {
                RenderUtil.renderRoundedRect(
                        context.getMatrices(),
                        (float)(this.parent.frame.x + 50),
                        (float)(this.parent.frame.y + this.parent.offset + this.parent.settings.indexOf(this) * 20 + 8),
                        (float)(this.parent.frame.x + 100),
                        (float)(this.parent.frame.y + this.parent.offset + this.parent.settings.indexOf(this) * 20 + 22),
                        Theme.PRIMARY,
                        2.0F
                );
                FontRenderers.Small
                        .drawString(
                                context.getMatrices(),
                                this.modeSetting.getMode(),
                                (float)(this.parent.frame.x + 54),
                                (float)(this.parent.frame.y + this.parent.frame.height + this.parent.offset + this.parent.settings.indexOf(this) * 20 - 6),
                                Color.WHITE.getRGB(),
                                true
                        );
            }

            super.render(context, mouseX, mouseY, delta);
        }
    }

    @EventHandler
    void onMouseButton(MouseButtonEvent event) {
        if (this.clicking && event.button == 0) {
            this.clicking = false;
            event.cancel();
        }
    }
}
