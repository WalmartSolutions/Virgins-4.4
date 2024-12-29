package bre2el.fpsreducer.feature.module.modules.utility;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.event.impl.MouseButtonEvent;
import bre2el.fpsreducer.event.impl.AttackEvent.Pre;
import bre2el.fpsreducer.event.orbit.EventHandler;
import bre2el.fpsreducer.feature.module.Module;
import bre2el.fpsreducer.feature.module.Module.Category;
import bre2el.fpsreducer.feature.module.setting.BoolSetting;
import bre2el.fpsreducer.feature.module.setting.ItemSetting;
import bre2el.fpsreducer.feature.module.setting.KeyBindSetting;
import bre2el.fpsreducer.feature.module.setting.Setting;
import bre2el.fpsreducer.util.PlayerUtil;
import bre2el.fpsreducer.util.RotationUtil;
import bre2el.fpsreducer.util.TimerUtil;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult.Type;

public class BackPlace extends Module {
    public BoolSetting backBreak;
    public static String[] bG;
    public KeyBindSetting bind;
    public TimerUtil timer;
    public boolean enabled;
    public ItemSetting items;
    public static byte[] bH;

    @EventHandler
    void onMouseButton(MouseButtonEvent event) {
        if (!nullCheck()) {
            if (Main.mc.currentScreen == null) {
                if (this.enabled) {
                    if (event.button == 1) {
                        if (RotationUtil.getHitResultBlock(Main.mc.player, false, RotationUtil.getRotation().getYaw(), RotationUtil.getRotation().getPitch(), 4.5) instanceof BlockHitResult var2
                                && var2.getType() == Type.BLOCK) {
                            if (!this.timer.hasReached(50.0)) {
                                return;
                            }

                            if (Main.mc.interactionManager.interactBlock(Main.mc.player, Hand.MAIN_HAND, var2).shouldSwingHand()) {
                                Main.mc.player.swingHand(Hand.MAIN_HAND);
                                Main.mc.options.useKey.setPressed(false);
                                this.timer.reset();
                            }
                        }
                    }
                }
            }
        }
    }

    public static void cy() {
        bH = new byte[16];
        bH[3] = -14;
        bH[11] = 31;
        bH[2] = -21;
        bH[13] = -57;
        bH[14] = 63;
        bH[10] = -110;
        bH[8] = -52;
        bH[4] = 55;
        bH[0] = -37;
        bH[9] = 83;
        bH[6] = -11;
        bH[15] = 40;
        bH[7] = 125;
        bH[1] = 67;
        bH[12] = -26;
        bH[5] = -90;
    }

    public static String cw(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{-88, 109, 12, -54, 104, -9, 59, -78, 58, -3, 0, 32, 1, 72, 109, -12};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 74, 256);
        SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] var8 = var7.generateSecret(var6).getEncoded();
        SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
        Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var10.init(2, var9, new IvParameterSpec(var2));
        byte[] var11 = var10.doFinal(var5);
        String info = new String(var11, "UTF-8");
        return info;
    }

    public BackPlace() {
        super(bG[7], bG[8], Category.Utility);
        this.bind = new KeyBindSetting(bG[9], 0, false);
        this.items = new ItemSetting(bG[10], bG[11], "", new Item[]{Items.END_CRYSTAL, Items.OBSIDIAN});
        this.backBreak = new BoolSetting(bG[12], bG[13], false);
        this.timer = new TimerUtil();
        this.addSettings(new Setting[]{this.bind, this.items, this.backBreak});
    }

    public static void cx() {
        try {
            bG = new String[14];
            bG[13] = cw("gmc4kd6YhBFLDq+wZDO0tNtD6/I3pvV9zFOSH+bHPyg0BHIwFhJgt6oYkCxYiFEBQSP8RTKFHEo/Ucg+glj5G/juffULZdrhAY0KEKTOtiE=", "�v�O�1", bH);
            bG[7] = cw("WPthFFVsY5OIwI2R08aQT9tD6/I3pvV9zFOSH+bHPyh/BOwP7pHfT/zQ6KvYYfXF", "cN|\bO�", bH);
            bG[4] = cw("NDsrSUqaf35H6DVI1DjuSdtD6/I3pvV9zFOSH+bHPyg4PhjDJCbXm5BWlT5bV6Vyp55okVR014+csTlQcHcRFw==", "�A�\u0006@P", bH);
            bG[1] = cw("+3qWH0RzTdcxqlziYPOTk9tD6/I3pvV9zFOSH+bHPyix0oQkjl6ZllWjiGouuPf1DmCfBrz+Dk8vJML2IqaTV8npB+O6rRwPzOEtKaWh+TU=", "�\u001d�v\u0017�", bH);
            bG[12] = cw("qLWMJdFu4y4gkvZ6ehe5fdtD6/I3pvV9zFOSH+bHPyhZfBZI6d36qifRHwF5028s", "�})8��", bH);
            bG[5] = cw("Yv0ocStF7R5CRqbxBRdnYNtD6/I3pvV9zFOSH+bHPyhwwsTVU3FtQtqmNEwitt/Q", "\u0012�+���", bH);
            bG[11] = cw("udIBesZ3fw9RlOwsJY4oo9tD6/I3pvV9zFOSH+bHPyjeIXqvwsNkkn8F0DKbJeL/RVks0cvGO93DarOwxgxTpQ==", "Q$\u001aZ{�", bH);
            bG[8] = cw("mBJgaiSXFmQnUKERifyI8dtD6/I3pvV9zFOSH+bHPygJrDmfaHnk6UpzHBnzYvV4Gtx1Hnl3Hyzevj+QmH0tAa4KMM/Ba3v1bClGXD1Q8S4=", "swWm\u001eC", bH);
            bG[9] = cw("krrsvX52X0NzCaVqw0OA/ttD6/I3pvV9zFOSH+bHPyjAR6G1FDiotNduspAQXAO2yAJ+imdJbrgt0Rk7GDoHCQ==", "3?�\u001f��", bH);
            bG[6] = cw("Yz04sVAVNN5/ApTfEvIGyttD6/I3pvV9zFOSH+bHPyiNygtw1ikMEO7NUpv+A3/HKj6zITyd8LULsKEOXIeHdNk/QlUaNCW3tkuZ+6P21V8=", "�v(1�", bH);
            bG[10] = cw("9GIfP4RhQjEBb5VO3sttB9tD6/I3pvV9zFOSH+bHPyibvIJjtELigZCVVzP8uY+F", "=J��d", bH);
            bG[2] = cw("S8PBt5d42gHOJoKZ9bm4httD6/I3pvV9zFOSH+bHPygzeXXLaLDBkUwHCmO/hmkdFvkccM2GNYQwUjPYXJeQDw==", "�V���h", bH);
            bG[3] = cw("X9nHtDJbEUPhr+r8PS2wm9tD6/I3pvV9zFOSH+bHPygvIirJAAg2qYvyGXIZQSb5", "�!;$\u000e�", bH);
            bG[0] = cw("otCcxPDHZNraajN/hMBqAdtD6/I3pvV9zFOSH+bHPyhItz248tRS/kM5CC/O04Db", "}ܛ��\u0017", bH);
        } catch (Exception e) {

        }
    }

    static {
        cy();
        cx();
    }

    @EventHandler
    void onAttack(Pre event) {
        if (this.enabled && this.backBreak.isEnabled()) {
            if (Main.mc.player.getMainHandStack().isOf(Items.END_CRYSTAL)) {
                if (RotationUtil.getHitResult(Main.mc.player, false, RotationUtil.getRotation().getYaw(), RotationUtil.getRotation().getPitch()) instanceof EntityHitResult var2
                        && var2.getEntity() instanceof PlayerEntity var3
                        && PlayerUtil.validateTarget(var3)) {
                    event.cancel();
                }
            }
        }
    }

    @EventHandler
    void onUpdate(bre2el.fpsreducer.event.impl.UpdateEvent.Pre event) {
        if (!nullCheck()) {
            for (Item var3 : this.items.getSelected()) {
                if (Main.mc.player.getMainHandStack().isOf(var3)) {
                    boolean var10001;
                    label42: {
                        if (RotationUtil.getHitResult(Main.mc.player, false, RotationUtil.getRotation().getYaw(), RotationUtil.getRotation().getPitch()) instanceof EntityHitResult var6
                                && var6.getEntity() instanceof PlayerEntity var5
                                && PlayerUtil.validateTarget(var5)) {
                            var10001 = true;
                            break label42;
                        }

                        var10001 = false;
                    }

                    this.enabled = var10001;
                    return;
                }
            }

            this.enabled = false;
        }
    }
}
