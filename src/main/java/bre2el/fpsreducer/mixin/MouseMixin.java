package bre2el.fpsreducer.mixin;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.event.impl.MouseButtonEvent;
import bre2el.fpsreducer.event.impl.UpdateMouseEvent;
import bre2el.fpsreducer.feature.module.Module.GlobalFlags;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Mouse.class})
public class MouseMixin {
    @Inject(
            method = {"onMouseButton"},
            at = {@At("HEAD")},
            cancellable = true
    )
    void onMouseButton(long action, int button, int mods, int window, CallbackInfo ci) {
        if (!GlobalFlags.DESTRUCTED.flag) {
            if (Main.EVENTBUS.post(MouseButtonEvent.get(button, mods)).isCancelled()) {
                ci.cancel();
            }
        }
    }

    @Inject(
            method = {"updateMouse"},
            at = {@At("HEAD")}
    )
    public void onUpdateMouse(CallbackInfo ci) {
        if (!GlobalFlags.DESTRUCTED.flag) {
            Main.EVENTBUS.post(UpdateMouseEvent.get());
        }
    }
}
