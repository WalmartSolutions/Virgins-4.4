package bre2el.fpsreducer.util;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.event.impl.*;
import bre2el.fpsreducer.event.impl.AttackEvent;
import bre2el.fpsreducer.event.impl.BlockBreakEvent;
import bre2el.fpsreducer.event.impl.ItemUseEvent;
import bre2el.fpsreducer.event.impl.PacketEvent;
import bre2el.fpsreducer.event.orbit.EventHandler;
import bre2el.fpsreducer.feature.module.Module;
import bre2el.fpsreducer.mixin.ClientPlayerEntityAccessor;
import bre2el.fpsreducer.util.RotationUtil;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.math.MathHelper;

public class RotationManager {
    public float lastPitch;
    public float bodyYaw;
    public float serverPitch;
    public boolean enabled;
    public static RotationManager INSTANCE = new RotationManager();
    public boolean resetRotation;
    public float yaw;
    public boolean rotateBack;
    public float lastYaw;
    public List<Module> stopRotateList = new ArrayList<Module>();
    public float prevBodyYaw;
    public float serverYaw;
    public boolean wasDisabled;
    public float pitch;
    public Rotation currentRotation;

    float calculateBodyYaw() {
        double d = Main.mc.player.getX() - Main.mc.player.prevX;
        double d2 = Main.mc.player.getZ() - Main.mc.player.prevZ;
        float f = this.bodyYaw;
        if (d * d + d2 * d2 > 0.002500000176951289) {
            f = (float)(MathHelper.atan2((double)d2, (double)d) * 57.2957763671875 - 90.0);
        }
        if (Main.mc.player.handSwingProgress > 0.0f) {
            f = ((ClientPlayerEntityAccessor)MinecraftClient.getInstance().player).getLastYaw();
        }
        float f2 = MathHelper.clamp((float)MathHelper.wrapDegrees((float)(((ClientPlayerEntityAccessor)MinecraftClient.getInstance().player).getLastYaw() - (this.bodyYaw + MathHelper.wrapDegrees((float)(f - this.bodyYaw)) * 0.3f))), (float)-45.0f, (float)75.0f);
        return (f2 > 50.0f ? f2 * 0.2f : 0.0f) + ((ClientPlayerEntityAccessor)MinecraftClient.getInstance().player).getLastYaw() - f2;
    }

    public boolean isRotateBack() {
        return this.rotateBack;
    }

    void resetClientRotation() {
        Main.mc.player.setYaw(this.yaw);
        Main.mc.player.setPitch(this.pitch);
        this.resetRotation = false;
    }

    public void setPitch(float f) {
        this.pitch = f;
    }

    @EventHandler(priority=200)
    void onAttack(AttackEvent.Post post) {
        if (!this.isEnabled() && this.wasDisabled) {
            this.enabled = true;
            this.wasDisabled = false;
        }
    }

    public List<Module> getStopRotateList() {
        return this.stopRotateList;
    }

    public float getPitch() {
        return this.pitch;
    }

    public void setServerPitch(float f) {
        this.serverPitch = f;
    }

    public float getServerPitch() {
        return this.serverPitch;
    }

    @EventHandler
    void onPacketSend(PacketEvent.Send packetEvent) {
        Packet packet = packetEvent.packet;
        if (packet instanceof PlayerMoveC2SPacket) {
            PlayerMoveC2SPacket playerMoveC2SPacket = (PlayerMoveC2SPacket)packet;
            this.serverYaw = playerMoveC2SPacket.getYaw(this.serverYaw);
            this.serverPitch = playerMoveC2SPacket.getPitch(this.serverPitch);
        }
    }

    public void setPrevBodyYaw(float f) {
        this.prevBodyYaw = f;
    }

    public void setServerRotation(Rotation Rotation) {
        this.serverYaw = Rotation.yaw;
        this.serverPitch = Rotation.pitch;
    }

    @EventHandler(priority=-200)
    void onBlockBreak(BlockBreakEvent blockBreakEvent) {
        block0: {
            if (blockBreakEvent.isCancelled() || !this.isEnabled()) break block0;
            this.enabled = false;
            this.wasDisabled = true;
        }
    }

    public void setResetRotation(boolean bl) {
        this.resetRotation = bl;
    }

    @EventHandler(priority=-200)
    void onSendMovementPacketPre(SendMovementPacketEvent.Pre pre) {
        block3: {
            this.lastYaw = ((ClientPlayerEntityAccessor)Main.mc.player).getLastYaw();
            this.lastPitch = ((ClientPlayerEntityAccessor)Main.mc.player).getLastPitch();
            if (this.isEnabled() && this.currentRotation != null) {
                this.setClientRotation(this.currentRotation);
                this.setServerRotation(this.currentRotation);
            }
            if (!this.rotateBack) break block3;
            Rotation Rotation = new Rotation(this.serverYaw, this.serverPitch);
            Rotation Rotation2 = new Rotation(Main.mc.player.getYaw(), Main.mc.player.getPitch());
            if (RotationUtil.getTotalDiff(Rotation, Rotation2) > 1.0) {
                Rotation Rotation3 = RotationUtil.getSmoothRotation(Rotation, Rotation2, 1.0);
                this.setClientRotation(Rotation3);
                this.setServerRotation(Rotation3);
            } else {
                this.rotateBack = false;
                this.currentRotation = null;
            }
        }
    }

    public void setLastPitch(float f) {
        this.lastPitch = f;
    }

    public float getPrevBodyYaw() {
        return this.prevBodyYaw;
    }

    public float getLastYaw() {
        return this.lastYaw;
    }

    public void setLastYaw(float f) {
        this.lastYaw = f;
    }

    @EventHandler(priority=200)
    void onSendMovementPacketPost(SendMovementPacketEvent.Post post) {
        this.prevBodyYaw = this.bodyYaw;
        this.bodyYaw = this.calculateBodyYaw();
        if (this.currentRotation != null) {
            this.currentRotation.runCallback();
        }
        if (this.resetRotation) {
            this.resetClientRotation();
        }
    }

    public void setCurrentRotation(Rotation Rotation) {
        this.currentRotation = Rotation;
    }

    void setClientRotation(Rotation Rotation) {
        this.yaw = Main.mc.player.getYaw();
        this.pitch = Main.mc.player.getPitch();
        Main.mc.player.setYaw(Rotation.yaw);
        Main.mc.player.setPitch(Rotation.pitch);
        this.resetRotation = true;
    }

    @EventHandler(priority=-200)
    void onItemUse(ItemUseEvent itemUseEvent) {
        block0: {
            if (itemUseEvent.isCancelled() || !this.isEnabled()) break block0;
            this.enabled = false;
            this.wasDisabled = true;
        }
    }

    public void setBodyYaw(float f) {
        this.bodyYaw = f;
    }

    @EventHandler
    void onPacketReceive(PacketEvent.Receive packetEvent$Receive) {
        Packet packet = packetEvent$Receive.packet;
        if (packet instanceof PlayerPositionLookS2CPacket) {
            PlayerPositionLookS2CPacket playerPositionLookS2CPacket = (PlayerPositionLookS2CPacket)packet;
            this.serverYaw = playerPositionLookS2CPacket.getYaw();
            this.serverPitch = playerPositionLookS2CPacket.getPitch();
        }
    }

    public float getLastPitch() {
        return this.lastPitch;
    }

    public void stopModule(Module module) {
        this.stopRotateList.add(module);
        INSTANCE.setEnabled(false);
    }

    public float getBodyYaw() {
        return this.bodyYaw;
    }

    @EventHandler(priority=-200)
    void onAttack(AttackEvent attackEvent) {
        if (!attackEvent.isCancelled() && this.isEnabled()) {
            this.enabled = false;
            this.wasDisabled = true;
        }
    }

    public void setEnabled(boolean bl) {
        if (this.isEnabled() && !bl && !this.rotateBack) {
            this.rotateBack = true;
        }
        this.enabled = bl;
    }

    @EventHandler(priority=200)
    void onItemUse(ItemUseEvent.Post post) {
        if (!this.isEnabled() && this.wasDisabled) {
            this.enabled = true;
            this.wasDisabled = false;
        }
    }

    public boolean isResetRotation() {
        return this.resetRotation;
    }

    @EventHandler(priority=200)
    void onBlockBreak(BlockBreakEvent.Post post) {
        if (!this.isEnabled() && this.wasDisabled) {
            this.enabled = true;
            this.wasDisabled = false;
        }
    }

    public Rotation getCurrentRotation() {
        return this.currentRotation;
    }

    public void setRotateBack(boolean bl) {
        this.rotateBack = bl;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public float getYaw() {
        return this.yaw;
    }

    public void setYaw(float f) {
        this.yaw = f;
    }

    public void setServerYaw(float f) {
        this.serverYaw = f;
    }

    public float getServerYaw() {
        return this.serverYaw;
    }

    public static class Rotation {
        public float pitch;
        public Runnable callback;
        public float yaw;

        public Runnable getCallback() {
            return this.callback;
        }

        public void setCallback(Runnable runnable) {
            this.callback = runnable;
        }

        public void setPitch(float f) {
            this.pitch = f;
        }

        public float getPitch() {
            return this.pitch;
        }

        public Rotation(float f, float f2) {
            this.yaw = f;
            this.pitch = f2;
        }

        public void setYaw(float f) {
            this.yaw = f;
        }

        public float getYaw() {
            return this.yaw;
        }

        public void runCallback() {
            if (this.callback != null) {
                this.callback.run();
            }
        }
    }

}
