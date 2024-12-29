package bre2el.fpsreducer.mixin;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.event.impl.RenderHUDEvent;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({InGameHud.class})
public class InGameHudMixin {
    @Inject(
            method = {"render"},
            at = {@At("TAIL")}
    )
    public void render(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        Main.EVENTBUS.post(RenderHUDEvent.get(context, tickCounter.getTickDelta(true)));
        context.getMatrices().scale(1.0F, 1.0F, 1.0F);
    }
}
