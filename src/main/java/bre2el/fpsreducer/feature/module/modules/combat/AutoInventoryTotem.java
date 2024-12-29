package bre2el.fpsreducer.feature.module.modules.combat;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.event.impl.KeyEvent;
import bre2el.fpsreducer.event.impl.PacketEvent.Receive;
import bre2el.fpsreducer.event.impl.UpdateEvent.Pre;
import bre2el.fpsreducer.event.orbit.EventHandler;
import bre2el.fpsreducer.feature.module.Module;
import bre2el.fpsreducer.feature.module.Module.Category;
import bre2el.fpsreducer.feature.module.setting.ActionSetting;
import bre2el.fpsreducer.feature.module.setting.BoolSetting;
import bre2el.fpsreducer.feature.module.setting.KeyBindSetting;
import bre2el.fpsreducer.feature.module.setting.ModeSetting;
import bre2el.fpsreducer.feature.module.setting.RangeSetting;
import bre2el.fpsreducer.feature.module.setting.Setting;
import bre2el.fpsreducer.gui.hud.impl.HotbarSettingPopupScreen;
import bre2el.fpsreducer.mixin.HandledScreenAccessor;
import bre2el.fpsreducer.mixin.KeyBindingAccessor;
import bre2el.fpsreducer.util.InventoryUtil;
import bre2el.fpsreducer.util.MathUtil;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.screen.slot.SlotActionType;

public class AutoInventoryTotem extends Module {
    public int tickTimer;
    public ModeSetting mode;
    public RangeSetting delay;
    public KeyBindSetting bind;
    public HotbarSettingPopupScreen popUp;
    public boolean cancel;
    public static String[] cU;
    public ActionSetting configure;
    public BoolSetting lock;
    public BoolSetting pop;
    public static byte[] cV;
    public boolean open;

    // im losing it
    public AutoInventoryTotem() {
        super("AutoInventoryTotem", "Automatically refills totem", Category.Combat);
        this.bind = new KeyBindSetting("Keybind for AutoInventoryTotem", 0, false);
        this.mode = new ModeSetting("Mode", "Refill mode. Legit will retotem only when hovering cursor on totem slot", "Auto", new String[]{"Auto", "Legit"});
        this.delay = new RangeSetting("Delay", "Delay of retotem", 0.0, 2.0, 0.0, 20.0, 1.0, 4.0);
        this.pop = new BoolSetting("Open inventory", "Open inventory when your totem pops", false);
        this.lock = new BoolSetting("Lock input", "Block input when inventory opened automaticallyconfig", false);
        this.configure = new ActionSetting("Slots GUI", "Click to open slot configure GUI");
        this.popUp = HotbarSettingPopupScreen.INSTANCE;
        this.addSettings(new Setting[]{this.bind, this.mode, this.delay, this.pop, this.lock, this.configure});
    }

    @EventHandler
    public void onUpdate(Pre event) {
        if (!nullCheck()) {
            this.setDetail(this.mode.getMode());
            if (!Main.mc.player.isDead()) {
                if (this.isTotem(Main.mc.player.getInventory().getStack(40)) && InventoryUtil.hasItemInHotbar(Items.TOTEM_OF_UNDYING) && this.cancel) {
                    Main.mc.player.closeHandledScreen();
                    this.open = false;
                    this.cancel = false;
                }

                if (this.open && !this.cancel) {
                    Main.mc.setScreen(new InventoryScreen(Main.mc.player));
                    this.cancel = true;
                }

                if (this.tickTimer >= MathUtil.getRandomInt((int)this.delay.getDefaultValue(), (int)this.delay.getSecondValue())) {
                    this.refill();
                } else {
                    this.tickTimer++;
                }
            } else {
                this.open = false;
                this.cancel = false;
            }
        }
    }

    @Override
    public void onTick() {
        if (this.configure.isActivated()) {
            Main.mc.setScreen(this.popUp);
            this.configure.setActivated(false);
        }

        super.onTick();
    }

    @EventHandler
    public void onKey(KeyEvent event) {
        if (!nullCheck()) {
            if (this.lock.isEnabled()) {
                if (this.cancel && Main.mc.currentScreen instanceof InventoryScreen var2) {
                    if (((HandledScreenAccessor)var2).hoveredSlot() == null) {
                        return;
                    }

                    for (int var4 = 0; var4 < 9; var4++) {
                        if (event.key == this.getKey(Main.mc.options.hotbarKeys[var4])) {
                            event.setCancelled(true);
                        }
                    }
                }
            }
        }
    }

    boolean isTotem(ItemStack stack) {
        return stack.isOf(Items.TOTEM_OF_UNDYING);
    }

    void reTotem(int button, int slot) {
        Main.mc.interactionManager.clickSlot(Main.mc.player.currentScreenHandler.syncId, button, slot, SlotActionType.SWAP, Main.mc.player);
        this.tickTimer = 0;
    }

    int getTotemSlot() {
        ArrayList var1 = new ArrayList();

        for (int var2 = 9; var2 < 36; var2++) {
            if (Main.mc.player.getInventory().getStack(var2).isOf(Items.TOTEM_OF_UNDYING)) {
                var1.add(var2);
            }
        }

        if (!var1.isEmpty()) {
            Collections.shuffle(var1);
            return (Integer)var1.getFirst();
        } else {
            return -1;
        }
    }

    void refill() {
        this.tickTimer = 0;
        if (Main.mc.currentScreen instanceof InventoryScreen var1) {
            PlayerInventory var10 = Main.mc.player.getInventory();
            if (this.getTotemSlot() == -1) {
                return;
            }

            String var3 = this.mode.getMode();
            switch (var3) {
                case "Auto":
                    if (!this.isTotem(Main.mc.player.getOffHandStack())) {
                        int var12 = this.getTotemSlot();
                        if (var12 != -1) {
                            this.reTotem(var12, 40);
                            return;
                        }

                        return;
                    }

                    if (this.popUp.slots.isEmpty()) {
                        return;
                    }

                    for (double var13 : this.popUp.slots) {
                        int var8 = (int)var13;
                        if (!this.isTotem(var10.getStack(var8))) {
                            int var14 = this.getTotemSlot();
                            if (var14 == -1) {
                                return;
                            }

                            this.reTotem(var14, var8);
                            return;
                        }
                    }
                    break;
                case "Legit":
                    if (((HandledScreenAccessor)var1).hoveredSlot() == null) {
                        return;
                    }

                    int var5 = ((HandledScreenAccessor)var1).hoveredSlot().getIndex();
                    if (var5 > 35) {
                        return;
                    }

                    if (var5 < 9) {
                        return;
                    }

                    if (!var10.getStack(var5).isOf(Items.TOTEM_OF_UNDYING)) {
                        return;
                    }

                    if (!this.isTotem(Main.mc.player.getOffHandStack())) {
                        if (var10.getStack(var5).isOf(Items.TOTEM_OF_UNDYING)) {
                            this.reTotem(var5, 40);
                            return;
                        }

                        return;
                    }

                    if (this.popUp.slots.isEmpty()) {
                        return;
                    }

                    for (double var7 : this.popUp.slots) {
                        int var9 = (int)var7;
                        if (!this.isTotem(var10.getStack(var9))) {
                            if (!var10.getStack(var5).isOf(Items.TOTEM_OF_UNDYING)) {
                                return;
                            }

                            this.reTotem(var5, var9);
                        }
                    }
            }
        }
    }

    public int getKey(KeyBinding bind) {
        return ((KeyBindingAccessor)bind).getKey().getCode();
    }

    @EventHandler
    public void onReceivePacket(Receive event) {
        if (this.pop.isEnabled()) {
            if (event.packet instanceof EntityStatusS2CPacket var2) {
                if (var2.getStatus() == 35) {
                    if (var2.getEntity(Main.mc.world) == Main.mc.player) {
                        this.open = true;
                    }
                }
            }
        }
    }
}
