package bre2el.fpsreducer.event.impl;

import bre2el.fpsreducer.event.orbit.Cancellable;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.Packet;

public class PacketEvent extends Cancellable {
    public static class Receive extends PacketEvent {
        public static Receive INSTANCE = new Receive();
        public Packet<?> packet;
        public PacketListener listener;

        public static Receive get(Packet<?> packet, PacketListener listener) {
            INSTANCE.listener = listener;
            INSTANCE.packet = packet;
            return INSTANCE;
        }
    }

    public static class Send extends PacketEvent {
        public static Send INSTANCE = new Send();
        public Packet<?> packet;
       // public PacketListener listener;

        public static Send get(Packet<?> packet) {
            // INSTANCE.listener = listener;
            INSTANCE.packet = packet;
            return INSTANCE;
        }
    }
}
