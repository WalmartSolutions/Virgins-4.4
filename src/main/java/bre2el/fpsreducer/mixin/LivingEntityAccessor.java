package bre2el.fpsreducer.mixin;

import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({LivingEntity.class})
public interface LivingEntityAccessor {
    @Accessor("serverYaw")
    void setServerYaw(double var1);

    @Accessor("serverPitch")
    void setServerPitch(double var1);

    @Accessor("serverPitch")
    double serverPitch();

    @Accessor("jumpingCooldown")
    void setJumpingCooldown(int var1);
}
