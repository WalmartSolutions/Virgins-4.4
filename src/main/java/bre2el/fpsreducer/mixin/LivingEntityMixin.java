package bre2el.fpsreducer.mixin;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.event.impl.DamageEvent.Post;
import bre2el.fpsreducer.event.impl.DamageEvent.Pre;
import bre2el.fpsreducer.feature.module.Module.GlobalFlags;
import bre2el.fpsreducer.util.RotationManager;
import bre2el.fpsreducer.util.RotationUtil;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({LivingEntity.class})
public class LivingEntityMixin {
    @ModifyExpressionValue(
            method = {"jump"},
            at = {@At(
                    value = "NEW",
                    target = "(DDD)Lnet/minecraft/util/math/Vec3d;"
            )}
    )
    Vec3d jump(Vec3d original) {
        if (!GlobalFlags.DESTRUCTED.flag) {
            //if (this == Main.mc.player) {
                if (!RotationUtil.checkRotations()) {
                    return original;
                } else {
                    float var2 = RotationManager.INSTANCE.getServerYaw() * (float) (Math.PI / 180.0);
                    return new Vec3d((double)(-MathHelper.sin(var2) * 0.2F), 0.0, (double)(MathHelper.cos(var2) * 0.2F));
                }
           // } else {
            //    return original;
           // }
        } else {
            return original;
        }
    }

    @ModifyExpressionValue(
            method = {"tick"},
            at = {@At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;getYaw()F"
            )},
            slice = {@Slice(
                    to = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/entity/LivingEntity;getYaw()F",
                            ordinal = 1
                    )
            )}
    )
    float bodyRot(float original) {
        if (!GlobalFlags.DESTRUCTED.flag) {
            // if (this == Main.mc.player) {
                return !RotationUtil.checkRotations() ? original : RotationManager.INSTANCE.getServerYaw();
            //} else {
           //     return original;
            //}
        } else {
            return original;
        }
    }

    @Inject(
            method = {"onDamaged"},
            at = {@At("RETURN")}
    )
    void onDamaged(DamageSource ci, CallbackInfo damageSource) {
        if (!GlobalFlags.DESTRUCTED.flag) {
            Main.EVENTBUS.post(Post.get(ci));
        }
    }

    @ModifyExpressionValue(
            method = {"turnHead"},
            at = {@At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;getYaw()F"
            )}
    )
    float bodyRot2(float original) {
        if (!GlobalFlags.DESTRUCTED.flag) {
           // if (this == Main.mc.player) {
                return !RotationUtil.checkRotations() ? original : RotationManager.INSTANCE.getServerYaw();
            //} else {
             //   return original;
            //}
        } else {
            return original;
        }
    }

    @Inject(
            method = {"onDamaged"},
            at = {@At("HEAD")}
    )
    void damaged(DamageSource damageSource, CallbackInfo ci) {
        if (!GlobalFlags.DESTRUCTED.flag) {
            Main.EVENTBUS.post(Pre.get(damageSource));
        }
    }
}
