package bre2el.fpsreducer.feature.module.modules.movement;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.event.impl.UpdateMouseEvent;
import bre2el.fpsreducer.event.impl.AttackEvent.Pre;
import bre2el.fpsreducer.event.impl.PacketEvent.Receive;
import bre2el.fpsreducer.event.impl.PacketEvent.Send;
import bre2el.fpsreducer.event.orbit.EventHandler;
import bre2el.fpsreducer.feature.module.Module;
import bre2el.fpsreducer.feature.module.Module.Category;
import bre2el.fpsreducer.feature.module.setting.BoolSetting;
import bre2el.fpsreducer.feature.module.setting.KeyBindSetting;
import bre2el.fpsreducer.feature.module.setting.KeySetting;
import bre2el.fpsreducer.feature.module.setting.ModeSetting;
import bre2el.fpsreducer.feature.module.setting.NumberSetting;
import bre2el.fpsreducer.feature.module.setting.RangeSetting;
import bre2el.fpsreducer.feature.module.setting.Setting;
import bre2el.fpsreducer.util.EntityUtil;
import bre2el.fpsreducer.util.MathUtil;
import bre2el.fpsreducer.util.PlayerUtil;
import bre2el.fpsreducer.util.TimerUtil;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.common.KeepAliveC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.s2c.common.KeepAliveS2CPacket;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3d;

public class FakeLag extends Module {
    public ModeSetting mode;
    public BoolSetting weapon;
    public static String[] bK;
    public BoolSetting flushOnHit;
    public NumberSetting range;
    public BoolSetting pingSpoof;
    public KeyBindSetting bind;
    public RangeSetting delay;
    public Queue<Packet<?>> packets;
    public boolean blinking;
    public boolean lagging;
    public static byte[] bL;
    public Vector3d start;
    public BoolSetting flushOnDamage;
    public KeySetting freeze;
    public TimerUtil timer;
    public List<PlayerMoveC2SPacket> movePackets;

    void dump() {
        if (!nullCheck()) {
            new Thread(() -> {
                List var1 = this.movePackets;
                synchronized (this.movePackets){} // $VF: monitorenter
                this.movePackets.forEach(Main.mc.getNetworkHandler()::sendPacket);
                this.movePackets.clear();
                this.blinking = false;
                // $VF: monitorexit
            }).start();
        }
    }

    // do NOT ask me (ablue) to deobf strings
    public FakeLag() {
        super("FakeLag", "Generates fake lag spikes like you have a bad connection", Category.Movement);
        this.bind = new KeyBindSetting("Keybind for FakeLag", 0, false);
        this.mode = new ModeSetting("Mode", "Defines lagging mode", "Latency", new String[]{"Latency", "Range", "Blink range"});
        this.delay = new RangeSetting("Delay", "Defines lagging delay", 50.0, 100.0, 0.0, 1000.0, 1.0, 200.0);
        this.range = new NumberSetting("Range", "Distance to activate fake lag", 8.0, 0.0, 10.0, 0.1);
        this.freeze = new KeySetting("Freeze", "Key to hold packets", 0);
        this.flushOnHit = new BoolSetting("Flush on hit", "Release packets when hit target", false);
        this.flushOnDamage = new BoolSetting("Flush on damage", "Release packets when hurt", false);
        this.pingSpoof = new BoolSetting("Ping spoof", "Increases ping to make you more legit to see", false);
        this.weapon = new BoolSetting("Only weapon", "Lags only when holding weapon", false);
        this.packets = new ConcurrentLinkedQueue();
        this.movePackets = new ArrayList();
        this.start = new Vector3d(0.0, 0.0, 0.0);
        this.addSettings(new Setting[]{this.bind, this.mode, this.delay, this.range, this.flushOnHit, this.flushOnDamage, this.pingSpoof, this.weapon});
    }


    @Override
    public void onDisable() {
        this.flush();
        this.dump();
        this.lagging = false;
        super.onDisable();
    }

    // $VF: Could not create synchronized statement, marking monitor enters and exits
    // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
    @EventHandler(
            priority = -200
    )
    public void onSendPacket(Send event) {
        if (!nullCheck()) {
            if (!this.freeze.isActive(this.freeze.getKey())) {
                String var2 = this.mode.getMode();
                switch (var2) {
                    case "Latency":
                    case "Range":
                        if (this.lagging) {
                            this.packets.add(event.packet);
                            event.cancel();
                        }
                        break;
                    case "Blink range":
                        if (!(event.packet instanceof PlayerMoveC2SPacket var4)) {
                            return;
                        }

                        if (!this.lagging) {
                            return;
                        }

                        if (!this.blinking) {
                            this.startBlink();
                        }

                        PlayerMoveC2SPacket var8 = !this.movePackets.isEmpty() ? (PlayerMoveC2SPacket)this.movePackets.get(this.movePackets.size() - 1) : null;
                        if (var8 != null
                                && var4.isOnGround() == var8.isOnGround()
                                && var4.getYaw(-1.0F) == var8.getYaw(-1.0F)
                                && var4.getPitch(-1.0F) == var8.getPitch(-1.0F)
                                && var4.getX(-1.0) == var8.getX(-1.0)
                                && var4.getY(-1.0) == var8.getY(-1.0)
                                && var4.getZ(-1.0) == var8.getZ(-1.0)) {
                            return;
                        }

                        event.cancel();
                        List var6 = this.movePackets;
                        synchronized (this.movePackets){} // $VF: monitorenter
                        this.movePackets.add(var4);
                        // $VF: monitorexit
                }
            } else {
                this.packets.add(event.packet);
                event.cancel();
            }
        }
    }

    boolean check() {
        String var1 = this.mode.getMode();
        switch (var1) {
            case "Latency":
                if (!this.timer.hasReached((double)MathUtil.getRandomInt((int)this.delay.getDefaultValue(), (int)this.delay.getSecondValue()))) {
                    return false;
                }

                this.timer.reset();
                return true;
            case "Range":
            case "Blink range":
                if (this.timer.hasReached((double)MathUtil.getRandomInt((int)this.delay.getDefaultValue(), (int)this.delay.getSecondValue()))) {
                    this.timer.reset();
                    PlayerEntity var3 = EntityUtil.findClosestPlayer(PlayerEntity.class, (float)this.range.getDefaultValue());
                    if (var3 != null && PlayerUtil.validateTarget(var3)) {
                        return true;
                    }

                    return false;
                }
            default:
                return false;
        }
    }

    @EventHandler
    public void onAttack(Pre event) {
        if (!nullCheck()) {
            if (this.flushOnHit.isEnabled()) {
                this.flush();
                this.dump();
            }
        }
    }

    void flush() {
        if (!nullCheck()) {
            this.lagging = false;
            new Thread(() -> {
                Queue var1 = this.packets;
                synchronized (this.packets){} // $VF: monitorenter

                while (!this.packets.isEmpty()) {
                    if (this.packets.peek() != null) {
                        Main.mc.getNetworkHandler().sendPacket(this.packets.poll());
                    }
                }

                // $VF: monitorexit
            }).start();
        }
    }

    @Override
    public void onEnable() {
        this.timer = new TimerUtil();
        super.onEnable();
    }

    @EventHandler(
            priority = 200
    )
    public void onReceivePacket(Receive event) {
        if (!nullCheck()) {
            if (!this.freeze.isActive(this.freeze.getKey())) {
                if (this.pingSpoof.isEnabled() && event.packet instanceof KeepAliveS2CPacket var2) {
                    event.cancel();
                    new Thread(() -> {
                        try {
                            Thread.sleep((long)MathUtil.getRandomInt((int)this.delay.getDefaultValue(), (int)this.delay.getSecondValue()));
                        } catch (InterruptedException e) {
                            // lol
                        }
                        Main.mc.getNetworkHandler().sendPacket(new KeepAliveC2SPacket(var2.getId()));
                    }).start();
                }
            } else {
                event.cancel();
            }
        }
    }

    void startBlink() {
        this.blinking = true;
        Vec3d var1 = Main.mc.player.getPos();
        this.start.x = var1.x;
        this.start.y = var1.y;
        this.start.z = var1.z;
    }

    @EventHandler
    public void onUpdateMouse(UpdateMouseEvent event) {
        if (!nullCheck()) {
            if (Main.mc.player.isDead()) {
                this.lagging = false;
                this.flush();
                this.dump();
            }

            this.setDetail(this.mode.getMode() + " " + this.delay.getDefaultValue() + "-" + this.delay.getSecondValue() + "ms");
            if (this.check()) {
                this.lagging = !this.lagging;
            }

            if (!this.lagging
                    || this.weapon.isEnabled() && !PlayerUtil.weaponCheck()
                    || this.flushOnDamage.isEnabled() && Main.mc.player.hurtTime == Main.mc.player.maxHurtTime - 1) {
                this.flush();
                this.dump();
            }
        }
    }
}
