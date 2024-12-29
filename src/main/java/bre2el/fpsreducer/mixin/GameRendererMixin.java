package bre2el.fpsreducer.mixin;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.event.impl.RenderWorldEvent;
import bre2el.fpsreducer.util.RenderUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GameRenderer.class})
public class GameRendererMixin {
    @Inject(
            method = {"renderWorld"},
            at = {@At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/render/GameRenderer;renderHand:Z",
                    opcode = 180,
                    ordinal = 0
            )}
    )
    void renderHand(RenderTickCounter tickCounter, CallbackInfo ci) {
        if (Main.mc.player != null) {
            Camera var3 = Main.mc.gameRenderer.getCamera();
            MatrixStack var4 = new MatrixStack();
            RenderSystem.getModelViewStack().pushMatrix().mul(var4.peek().getPositionMatrix());
            var4.multiply(RotationAxis.POSITIVE_X.rotationDegrees(var3.getPitch()));
            var4.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(var3.getYaw() + 180.0F));
            RenderSystem.applyModelViewMatrix();
            RenderUtil.lastProjMat.set(RenderSystem.getProjectionMatrix());
            RenderUtil.lastModMat.set(RenderSystem.getModelViewMatrix());
            RenderUtil.lastWorldSpaceMatrix.set(var4.peek().getPositionMatrix());
            Main.EVENTBUS.post(RenderWorldEvent.get(var4, tickCounter.getTickDelta(true)));
            RenderSystem.getModelViewStack().popMatrix();
            RenderSystem.applyModelViewMatrix();
        }
    }
}
