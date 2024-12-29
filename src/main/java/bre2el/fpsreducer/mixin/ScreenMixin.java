package bre2el.fpsreducer.mixin;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.gui.hud.HUDEditor;
import bre2el.fpsreducer.gui.hud.impl.HotbarSettingPopupScreen;
import bre2el.fpsreducer.gui.hud.impl.MessageBox;
import bre2el.fpsreducer.gui.impl.ClickGUI;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Screen.class})
public class ScreenMixin {
    @Inject(
            method = {"renderBackground"},
            at = {@At("HEAD")},
            cancellable = true
    )
    void renderBackground(DrawContext delta, int mouseY, int mouseX, float context, CallbackInfo ci) {
        if (Main.mc.currentScreen instanceof ClickGUI
                || Main.mc.currentScreen instanceof HUDEditor
                || Main.mc.currentScreen instanceof HotbarSettingPopupScreen
                || Main.mc.currentScreen instanceof MessageBox) {
            ci.cancel();
        }
    }
}
