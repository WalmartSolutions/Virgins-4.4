package bre2el.fpsreducer.feature.module.modules.combat;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.event.impl.AttackEvent.Pre;
import bre2el.fpsreducer.event.orbit.EventHandler;
import bre2el.fpsreducer.feature.module.Module;
import bre2el.fpsreducer.feature.module.Module.Category;
import bre2el.fpsreducer.feature.module.setting.BoolSetting;
import bre2el.fpsreducer.feature.module.setting.KeyBindSetting;
import bre2el.fpsreducer.feature.module.setting.RangeSetting;
import bre2el.fpsreducer.feature.module.setting.Setting;
import bre2el.fpsreducer.util.EntityUtil;
import bre2el.fpsreducer.util.InputUtil;
import bre2el.fpsreducer.util.InventoryUtil;
import bre2el.fpsreducer.util.MathUtil;
import bre2el.fpsreducer.util.RotationUtil;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Items;
import net.minecraft.item.MaceItem;
import net.minecraft.item.SwordItem;
import net.minecraft.util.UseAction;
import net.minecraft.util.hit.EntityHitResult;

public class AutoBlock extends Module {
    public static String[] by;
    public BoolSetting weapon;
    public KeyBindSetting bind;
    public boolean active;
    public int prev;
    public RangeSetting delay;
    public int clock;
    public BoolSetting findShield;
    public static byte[] bz;

    public static void cm() {
        bz = new byte[16];
        bz[4] = 83;
        bz[14] = -109;
        bz[2] = -41;
        bz[13] = -79;
        bz[9] = 9;
        bz[3] = -79;
        bz[15] = 125;
        bz[5] = -98;
        bz[7] = -64;
        bz[10] = -113;
        bz[12] = 98;
        bz[0] = 88;
        bz[11] = 105;
        bz[8] = 110;
        bz[6] = 81;
        bz[1] = -72;
    }

    static {
        cm();
        cl();
    }

    public static String ck(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{-3, -29, -60, 0, 101, 38, 5, -57, -92, -18, -117, 50, 44, -84, 67, 70};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 91, 256);
        SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] var8 = var7.generateSecret(var6).getEncoded();
        SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
        Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var10.init(2, var9, new IvParameterSpec(var2));
        byte[] var11 = var10.doFinal(var5);
        String info = new String(var11, "UTF-8");
        return info;
    }

    @EventHandler
    public void onAttack(Pre event) {
        if (!this.weapon.isEnabled() || Main.mc.player.getMainHandStack().getItem() instanceof AxeItem || Main.mc.player.getMainHandStack().getItem() instanceof SwordItem) {
            if (!InventoryUtil.hasItemInHotbar(Items.SHIELD) && !Main.mc.player.getOffHandStack().isOf(Items.SHIELD)) {
                if (RotationUtil.getHitResult(Main.mc.player, false, RotationUtil.getRotation().getYaw(), RotationUtil.getRotation().getPitch()) instanceof EntityHitResult var5
                        && RotationUtil.getAngleToLookVecPlayers((PlayerEntity)var5.getEntity(), Main.mc.player.getBoundingBox().getCenter()) <= 90.0) {
                    if (!Main.mc.player.getOffHandStack().isOf(Items.SHIELD)) {
                        if (this.findShield.isEnabled()) {
                            this.prev = Main.mc.player.getInventory().selectedSlot;
                            InventoryUtil.selectItemFromHotbar(Items.SHIELD);
                            InputUtil.callMouse(1, 1);
                        }
                    } else {
                        InputUtil.callMouse(1, 1);
                    }

                    this.active = true;
                }
            } else {
                if (RotationUtil.getHitResult(Main.mc.player, false, RotationUtil.getRotation().getYaw(), RotationUtil.getRotation().getPitch()) instanceof EntityHitResult var2
                        && RotationUtil.getAngleToLookVecPlayers((PlayerEntity)var2.getEntity(), Main.mc.player.getBoundingBox().getCenter()) <= 90.0) {
                    if (!Main.mc.player.getOffHandStack().isOf(Items.SHIELD)) {
                        if (this.findShield.isEnabled()) {
                            this.prev = Main.mc.player.getInventory().selectedSlot;
                            InventoryUtil.selectItemFromHotbar(Items.SHIELD);
                            InputUtil.callMouse(1, 1);
                        }
                    } else {
                        InputUtil.callMouse(1, 1);
                    }

                    this.active = true;
                }
            }
        }
    }

    void reset() {
        if (Main.mc.player.getMainHandStack().isOf(Items.SHIELD) || Main.mc.player.getOffHandStack().isOf(Items.SHIELD)) {
            this.active = false;
            if (this.findShield.isEnabled() && this.prev != -1) {
                Main.mc.player.getInventory().selectedSlot = this.prev;
            }

            this.clock = 0;
        }
    }

    @EventHandler
    public void onUpdate(bre2el.fpsreducer.event.impl.UpdateEvent.Pre event) {
        if (!nullCheck()) {
            if (InventoryUtil.hasItemInHotbar(Items.SHIELD) || Main.mc.player.getOffHandStack().isOf(Items.SHIELD)) {
                PlayerEntity var2 = EntityUtil.findClosestPlayer(PlayerEntity.class, 3.0F);
                if (!this.active
                        && var2 != null
                        && !var2.isOnGround()
                        && (
                        var2.getMainHandStack().getItem() instanceof MaceItem
                                || var2.getMainHandStack().getItem() instanceof AxeItem
                                || var2.getMainHandStack().getItem() instanceof SwordItem
                )) {
                    this.block();
                    return;
                }

                if (!this.active) {
                    return;
                }

                this.clock++;
                if (this.clock < MathUtil.getRandomInt((int)this.delay.getDefaultValue(), (int)this.delay.getSecondValue())) {
                    return;
                }

                if (!(
                        RotationUtil.getHitResult(Main.mc.player, false, RotationUtil.getRotation().getYaw(), RotationUtil.getRotation().getPitch()) instanceof EntityHitResult var4
                )
                        || !(var4.getEntity() instanceof PlayerEntity)
                        || !InputUtil.checkMouse(1)
                        || !Main.mc.player.getMainHandStack().isOf(Items.SHIELD)) {
                    this.reset();
                }
            }
        }
    }

    public static void cl() {
        try {
            by = new String[18];
            by[17] = ck("TG5XKZPPj9FJs1clnc5Ap1i417FTnlHAbgmPaWKxk33Bx5mSgi1SPTryAn1+0tu4", "�{$̈�", bz);
            by[2] = ck("7DRIi9ig+cahGSRkLTY9Vli417FTnlHAbgmPaWKxk31FCU7Ag1XLJPSVHElfGF2aXsrCVav7oHdRfGt6w+A8/g==", "x�����", bz);
            by[6] = ck("P3zUFN6yD6yofCNGiNeyK1i417FTnlHAbgmPaWKxk31yG4FGAzHNG/nIq46Wi8+YxFH5qK7z3Rn4vfyGCUjgBQ==", "�\u0019�\u0010�7", bz);
            by[4] = ck("T0O2iBG6XwvKek+OsNGCiFi417FTnlHAbgmPaWKxk33s2dkLuThIPM8rKnc6zdE4IC1SjC29nWxH8ftnL0kz9w==", "\r�\u000b�Ȱ", bz);
            by[7] = ck("QYKOU7o3FgGqH4JrFOiGjFi417FTnlHAbgmPaWKxk31vHJh/1pixruIpcdo0Wgrx", "�\u001f���\u000b", bz);
            by[8] = ck("BWFF1g0LdLAlB1iwvKBlx1i417FTnlHAbgmPaWKxk338Y3Up7/x4xZH0uQSg0zdb", "�\u0017{�c@", bz);
            by[16] = ck("l7I8nea0GpVgYD4TPzSk+Fi417FTnlHAbgmPaWKxk32VEjEGr2AoRbxjTeqPgMph", "��E㦵", bz);
            by[5] = ck("soyvF8UlQDhR0wwgwOcUrVi417FTnlHAbgmPaWKxk31hnWcxjxL4dfO4OMYT4InR", "\u001d���-I", bz);
            by[11] = ck("vXN0fg/VGOBb49KYX8unMFi417FTnlHAbgmPaWKxk30V9J3xh5NiGn3feKhoqXX8YuULU9ZgSlRkSmM5rfSo3Q==", "��Z8", bz);
            by[9] = ck("juY/ySfFViqUi6Ns6c7E8li417FTnlHAbgmPaWKxk30lfPb7OmQjC7KnWDt18pUi", "�\"��\tm", bz);
            by[3] = ck("5ySHH57kjYO+ybAJ79EccVi417FTnlHAbgmPaWKxk317QwQIm+09NQJvBBq/EBg+", "������", bz);
            by[14] = ck("AmMwtzIzw4DS0YKQ6OwKb1i417FTnlHAbgmPaWKxk30EZJ6rvFHccVmMzA98yyyw", "۰�)|m", bz);
            by[13] = ck("KkGTIJjA8daB0GMTOfmq41i417FTnlHAbgmPaWKxk31Syw5jG3ekjoWnGUxai4XzyvMg7LqIlWaB+kjr82MvQA==", "C��N\u001aZ", bz);
            by[15] = ck("T9oKqwpP6tfqTPDecKH/BVi417FTnlHAbgmPaWKxk32c3z/aGdzt2HvFFnbINYA6tll1/+FdGksZrpsIK+I3MA==", "\ud9d7\udeec&�", bz);
            by[10] = ck("tQ4B5KR4RR1qS4pgZU/nkVi417FTnlHAbgmPaWKxk33ux2Mp5PW1Wc8nt7h8qZ4C1za7Eq8TxUSdrVM9QG3/w96RibSk9A1NSc7ppZYTnT4=", "\u0011I\u000f'��", bz);
            by[1] = ck("hjVuX24U2jxvZjiVlVLNHFi417FTnlHAbgmPaWKxk33DFAxPLb5ybrrAHP+ANu4YG/QEog3QeV/Lyv2bN+601KS1x6MWcFRQ1ul6KOZcU74=", "\u001cj ag\"", bz);
            by[0] = ck("Se77qAL21+cy0shuajD971i417FTnlHAbgmPaWKxk30wzsMFd/DGbdrgKS+FlLR8", "��VJ\u001a\u0001", bz);
            by[12] = ck("pobRpA+MMlGCehT+UoXwd1i417FTnlHAbgmPaWKxk33Nqgv/SBoE5ldkLa+bZsB0", "o�ٛ��", bz);
        } catch (Exception e) {

        }
    }

    public AutoBlock() {
        super(by[9], by[10], Category.Combat);
        this.bind = new KeyBindSetting(by[11], 0, false);
        this.findShield = new BoolSetting(by[12], by[13], false);
        this.weapon = new BoolSetting(by[14], by[15], true);
        this.delay = new RangeSetting(by[16], by[17], 0.0, 4.0, 0.0, 10.0, 1.0, 4.0);
        this.prev = -1;
        this.addSettings(new Setting[]{this.bind, this.findShield, this.weapon, this.delay});
    }

    void block() {
        UseAction var1 = Main.mc.player.getMainHandStack().getUseAction();
        if (var1 != UseAction.EAT && var1 != UseAction.SPEAR && !Main.mc.player.isUsingItem()) {
            if (this.findShield.isEnabled()) {
                InventoryUtil.selectItemFromHotbar(Items.SHIELD);
            }

            this.prev = Main.mc.player.getInventory().selectedSlot;
            InputUtil.callMouse(1, 1);
            this.active = true;
        }
    }
}
