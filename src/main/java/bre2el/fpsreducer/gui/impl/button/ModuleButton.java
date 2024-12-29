package bre2el.fpsreducer.gui.impl.button;

import bre2el.fpsreducer.feature.module.Module;
import bre2el.fpsreducer.feature.module.setting.ActionSetting;
import bre2el.fpsreducer.feature.module.setting.BoolSetting;
import bre2el.fpsreducer.feature.module.setting.ColorPickerSetting;
import bre2el.fpsreducer.feature.module.setting.ItemSetting;
import bre2el.fpsreducer.feature.module.setting.KeyBindSetting;
import bre2el.fpsreducer.feature.module.setting.KeySetting;
import bre2el.fpsreducer.feature.module.setting.ModeSetting;
import bre2el.fpsreducer.feature.module.setting.NumberSetting;
import bre2el.fpsreducer.feature.module.setting.RangeSetting;
import bre2el.fpsreducer.feature.module.setting.Setting;
import bre2el.fpsreducer.feature.module.setting.renderer.RenderSetting;
import bre2el.fpsreducer.feature.module.setting.renderer.impl.ButtonBox;
import bre2el.fpsreducer.feature.module.setting.renderer.impl.CheckBox;
import bre2el.fpsreducer.feature.module.setting.renderer.impl.ColorPicker;
import bre2el.fpsreducer.feature.module.setting.renderer.impl.InputBox;
import bre2el.fpsreducer.feature.module.setting.renderer.impl.ItemBox;
import bre2el.fpsreducer.feature.module.setting.renderer.impl.KeyBind;
import bre2el.fpsreducer.feature.module.setting.renderer.impl.ModeBox;
import bre2el.fpsreducer.feature.module.setting.renderer.impl.Slider;
import bre2el.fpsreducer.feature.module.setting.renderer.impl.TwoSlider;
import bre2el.fpsreducer.gui.font.FontRenderers;
import bre2el.fpsreducer.gui.impl.Frame;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.DrawContext;

public class ModuleButton {
    public Frame frame;
    public List<RenderSetting> settings;
    public int index;
    public Setting setting;
    public Module module;
    public boolean extended;
    public int offset;

    public ModuleButton(Module parent, Frame module, int offset) {
        this.module = parent;
        this.frame = module;
        this.offset = offset;
        this.settings = new ArrayList<>();
        int var4 = module.height;

        for (Setting var6 : parent.getSettings()) {
            if (!(var6 instanceof BoolSetting)) {
                if (!(var6 instanceof KeySetting)) {
                    if (!(var6 instanceof KeyBindSetting)) {
                        if (!(var6 instanceof ModeSetting)) {
                            if (!(var6 instanceof NumberSetting)) {
                                if (!(var6 instanceof RangeSetting)) {
                                    if (!(var6 instanceof ItemSetting)) {
                                        if (!(var6 instanceof ActionSetting)) {
                                            if (var6 instanceof ColorPickerSetting) {
                                                this.settings.add(new ColorPicker(var6, this, var4));
                                            }
                                        } else {
                                            this.settings.add(new ButtonBox(var6, this, var4));
                                        }
                                    } else {
                                        this.settings.add(new ItemBox(var6, this, var4));
                                    }
                                } else {
                                    this.settings.add(new TwoSlider(var6, this, var4));
                                }
                            } else {
                                this.settings.add(new Slider(var6, this, var4));
                            }
                        } else {
                            this.settings.add(new ModeBox(var6, this, var4));
                        }
                    } else {
                        this.settings.add(new KeyBind(var6, this, var4));
                    }
                } else {
                    this.settings.add(new InputBox(var6, this, var4));
                }
            } else {
                this.settings.add(new CheckBox(var6, this, var4));
            }

            var4 += module.height;
        }
    }

    public boolean keyPressed(int keyCode) {
        for (RenderSetting var3 : this.settings) {
            var3.keyPressed(keyCode);
        }

        return false;
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX > this.frame.x
                && mouseX < this.frame.x + this.frame.width
                && mouseY > this.frame.y + this.offset - 10
                && mouseY < this.frame.y + this.offset + 10;
    }

    public void render(DrawContext context, int mouseX, int mouseY, float tickDelta) {
        if (!this.frame.searchResult.isEmpty()) {
            if (this.module.getName().toLowerCase().contains(this.frame.searchResult)) {
                FontRenderers.Main
                        .drawString(
                                context.getMatrices(),
                                this.module.getName(),
                                (float)(this.frame.x + 4),
                                (float)(this.frame.y + this.offset + 2),
                                !this.module.isEnabled() ? Color.GRAY.getRGB() : Color.WHITE.getRGB(),
                                true
                        );
            }
        } else {
            FontRenderers.Main
                    .drawString(
                            context.getMatrices(),
                            this.module.getName(),
                            (float)(this.frame.x + 4),
                            (float)(this.frame.y + this.offset + 2),
                            !this.module.isEnabled() ? Color.GRAY.getRGB() : Color.WHITE.getRGB(),
                            true
                    );
        }

        if (this.isHovered(mouseX, mouseY)) {
            this.frame.cursorModule = this.module;
        }

        if (this.extended) {
            for (RenderSetting var6 : this.settings) {
                if ((float)this.frame.index >= (float)(this.settings.size() * 20) / 1.8F && !(var6 instanceof ModeBox)) {
                    var6.render(context, mouseX, mouseY, tickDelta);
                }
            }

            for (RenderSetting var8 : this.settings) {
                if (var8 instanceof ModeBox) {
                    var8.render(context, mouseX, mouseY, tickDelta);
                }
            }
        }
    }

    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        for (RenderSetting var7 : this.settings) {
            var7.mouseReleased(mouseX, mouseY, button);
        }

        return false;
    }

    public boolean mouseClicked(double mouseY, double mouseX, int button) {
        if (button == 0 && this.isHovered((int)mouseY, (int)mouseX)) {
            this.module.toggle();
        }

        if (button == 1 && this.isHovered((int)mouseY, (int)mouseX)) {
            this.extended = !this.extended;
            this.frame.updateButtons();
            if (!this.extended) {
                this.index = this.index - this.settings.size() * 20;
                this.frame.index = this.frame.index - this.settings.size() * 20;
            } else {
                this.index = this.index + this.settings.size() * 20;
                this.frame.index = this.frame.index + this.settings.size() * 20;
            }
        }

        for (RenderSetting var7 : this.settings) {
            var7.mouseClicked(mouseY, mouseX, button);
        }

        return false;
    }
}
