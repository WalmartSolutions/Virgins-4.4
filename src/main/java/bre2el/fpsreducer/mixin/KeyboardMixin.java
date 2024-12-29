package bre2el.fpsreducer.mixin;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.event.impl.KeyEvent;
import bre2el.fpsreducer.feature.module.Module;
import bre2el.fpsreducer.feature.module.ModuleManager;
import bre2el.fpsreducer.feature.module.Module.GlobalFlags;
import bre2el.fpsreducer.feature.module.modules.client.Virgin;
import net.minecraft.client.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Keyboard.class})
public class KeyboardMixin {
    @Inject(
            method = {"onKey"},
            at = {@At("HEAD")},
            cancellable = true
    )
    void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        if (!Module.nullCheck()) {
            if (!GlobalFlags.DESTRUCTED.flag) {
                if (Main.EVENTBUS.post(KeyEvent.get(key, action)).isCancelled()) {
                    ci.cancel();
                }

                for (Module var9 : ModuleManager.INSTANCE.getModules()) {
                    if (var9 instanceof Virgin var10 && var10.getKey() == 0) {
                        var9.setKey(344);
                    }

                    if (key == var9.getKey() && Main.mc.currentScreen == null) {
                        if (var9.isHold()) {
                            if (action == 1) {
                                var9.toggle();
                            }

                            if (action == 0) {
                                var9.toggle();
                            }
                        } else if (action == 1) {
                            var9.toggle();
                        }
                    }
                }
            }
        }
    }
}
