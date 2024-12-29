package bre2el.fpsreducer.mixin;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.event.impl.PacketEvent.Receive;
import bre2el.fpsreducer.event.impl.PacketEvent.Send;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ClientConnection.class})
public abstract class ClientConnectionMixin {
    @Inject(
            method = {"send(Lnet/minecraft/network/packet/Packet;)V"},
            at = {@At("HEAD")},
            cancellable = true
    )
    void sendPacket(Packet<?> packet, CallbackInfo ci) {
        //if (!GlobalFlags.DESTRUCTED.flag) {
            if (Main.EVENTBUS.post(Send.get(packet)).isCancelled()) {
                ci.cancel();
            }
       // }
    }

    @Inject(
            method = {"handlePacket"},
            at = {@At("HEAD")},
            cancellable = true
    )
    private static void handlePacket(Packet<?> listener, PacketListener ci, CallbackInfo packet) {
        //if (!GlobalFlags.DESTRUCTED.flag) {
            if (Main.EVENTBUS.post(Receive.get(listener, ci)).isCancelled()) {
                packet.cancel();
            }
       // }
    }
}
