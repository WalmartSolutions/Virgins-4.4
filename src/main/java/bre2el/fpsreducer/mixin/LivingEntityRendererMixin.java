package bre2el.fpsreducer.mixin;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.feature.module.Module.GlobalFlags;
import bre2el.fpsreducer.util.RenderUtil;
import bre2el.fpsreducer.util.RotationManager;
import bre2el.fpsreducer.util.RotationUtil;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({LivingEntityRenderer.class})
public class LivingEntityRendererMixin {

    @Unique
    public float originalHeadPitch;
    @Unique
    public float originalPrevHeadYaw;
    @Unique
    public float originalPrevHeadPitch;
    @Unique
    public float originalHeadYaw;

    @Inject(
            method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
            at = @At(value = "TAIL")
    )
    private void onRender(LivingEntity livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        if (!GlobalFlags.DESTRUCTED.flag) {
            if (RotationUtil.checkRotations() && Main.mc.player != null && vertexConsumerProvider == Main.mc.player) {
                livingEntity.prevPitch = this.originalPrevHeadPitch;
                livingEntity.setPitch(this.originalHeadPitch);
                livingEntity.headYaw = this.originalHeadYaw;
                livingEntity.prevHeadYaw = this.originalPrevHeadYaw;
            } else {
                RotationManager.INSTANCE.setEnabled(false);
            }
        }
    }

//    @Inject(
//            method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
//            at = @At(value = "TAIL")
//    )// wtf is wrong
//    private void render(T livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
//        if (!GlobalFlags.DESTRUCTED.flag) {
//            if (RotationUtil.checkRotations() && Main.mc.player != null && vertexConsumerProvider == Main.mc.player) {
//                livingEntity.field_6004 = this.originalPrevHeadPitch;
//                livingEntity.setPitch(this.originalHeadPitch);
//                livingEntity.headYaw = this.originalHeadYaw;
//                livingEntity.prevHeadYaw = this.originalPrevHeadYaw;
//            } else {
//                RotationManager.INSTANCE.setEnabled(false);
//            }
//        }
//    }
//
    @Inject(
            method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
            at = @At(value = "HEAD")
    )
    void render(LivingEntity livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        if (!GlobalFlags.DESTRUCTED.flag) {
            if (RotationUtil.checkRotations() && Main.mc.player != null && livingEntity == Main.mc.player) {
                this.originalHeadYaw = livingEntity.headYaw;
                this.originalPrevHeadYaw = livingEntity.prevHeadYaw;
                this.originalPrevHeadPitch = livingEntity.prevPitch;
                this.originalHeadPitch = livingEntity.getPitch();
                livingEntity.setPitch(((ClientPlayerEntityAccessor)Main.mc.player).getLastPitch());
                livingEntity.prevPitch = RotationManager.INSTANCE.getLastPitch();
                livingEntity.headYaw = ((ClientPlayerEntityAccessor)Main.mc.player).getLastYaw();
                livingEntity.bodyYaw = RenderUtil.interpolateFloat(
                        RotationManager.INSTANCE.getPrevBodyYaw(), RotationManager.INSTANCE.getBodyYaw(), (double)Main.mc.getRenderTickCounter().getTickDelta(true)
                );
                livingEntity.prevHeadYaw = RotationManager.INSTANCE.getLastYaw();
                livingEntity.prevBodyYaw = RenderUtil.interpolateFloat(
                        RotationManager.INSTANCE.getPrevBodyYaw(), RotationManager.INSTANCE.getBodyYaw(), (double)Main.mc.getRenderTickCounter().getTickDelta(true)
                );
            } else {
                RotationManager.INSTANCE.setEnabled(false);
            }
        }
    }
}
