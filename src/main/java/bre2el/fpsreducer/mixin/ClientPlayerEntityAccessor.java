package bre2el.fpsreducer.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({ClientPlayerEntity.class})
public interface ClientPlayerEntityAccessor {
    @Invoker("canSprint")
    boolean invokeCanSprint();

    @Accessor("lastPitch")
    float getLastPitch();

    @Accessor("lastYaw")
    float getLastYaw();
}
