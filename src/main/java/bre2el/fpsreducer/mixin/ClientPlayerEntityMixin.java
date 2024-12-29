package bre2el.fpsreducer.mixin;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.event.impl.SendMovementPacketEvent.Post;
import bre2el.fpsreducer.event.impl.SendMovementPacketEvent.Pre;
import bre2el.fpsreducer.feature.module.Module.GlobalFlags;
import bre2el.fpsreducer.util.RotationManager;
import bre2el.fpsreducer.util.RotationUtil;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static bre2el.fpsreducer.client.Main.mc;

@Mixin({ClientPlayerEntity.class})
public abstract class ClientPlayerEntityMixin {
    @Shadow public abstract float getPitch(float tickDelta);

    @Inject(
            method = {"tick"},
            at = {@At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/network/ClientPlayNetworkHandler;sendPacket(Lnet/minecraft/network/packet/Packet;)V",
                    ordinal = 0
            )}
    )
    void onTickHasVehicleBeforeSendPackets(CallbackInfo info) {
        if (!GlobalFlags.DESTRUCTED.flag) {
            Main.EVENTBUS.post(Pre.get());
        }
    }

    @ModifyExpressionValue(
            method = {"sendMovementPackets", "tick"},
            at = {@At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/network/ClientPlayerEntity;getPitch()F"
            )}
    )
    float silentPitch(float original) {
        if (!GlobalFlags.DESTRUCTED.flag) {
            return !RotationUtil.checkRotations() ? original : RotationManager.INSTANCE.getServerPitch();
        } else {
            return original;
        }
    }

    @Inject(
            method = {"tick"},
            at = {@At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/network/ClientPlayNetworkHandler;sendPacket(Lnet/minecraft/network/packet/Packet;)V",
                    ordinal = 1,
                    shift = Shift.AFTER
            )}
    )
    void onTickHasVehicleAfterSendPackets(CallbackInfo info) {
        if (!GlobalFlags.DESTRUCTED.flag) {
            Main.EVENTBUS.post(Post.get());
        }
    }

    @ModifyExpressionValue(
            method = {"sendMovementPackets", "tick"},
            at = {@At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/network/ClientPlayerEntity;getYaw()F"
            )}
    )
    float silentYaw(float original) {
        if (!GlobalFlags.DESTRUCTED.flag) {
            return !RotationUtil.checkRotations() ? original : RotationManager.INSTANCE.getServerYaw();
        } else {
            return original;
        }
    }

    @Inject(
            method = {"sendMovementPackets"},
            at = {@At("HEAD")}
    )
    void onSendMovementPacketsPre(CallbackInfo info) {
        if (!GlobalFlags.DESTRUCTED.flag) {
            Main.EVENTBUS.post(Pre.get());
            RotationManager.INSTANCE.setLastPitch(this.getPitch(mc.getRenderTickCounter().getTickDelta(true)));
        }
    }

    @Inject(
            method = {"sendMovementPackets"},
            at = {@At("RETURN")}
    )
    void onSendMovementPacketsPost(CallbackInfo info) {
        if (!GlobalFlags.DESTRUCTED.flag) {
            Main.EVENTBUS.post(Post.get());
        }
    }
}
