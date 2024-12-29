package bre2el.fpsreducer.feature.module.modules.combat;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.event.impl.AttackEvent.Post;
import bre2el.fpsreducer.event.impl.AttackEvent.Pre;
import bre2el.fpsreducer.event.orbit.EventHandler;
import bre2el.fpsreducer.feature.module.Module;
import bre2el.fpsreducer.feature.module.Module.Category;
import bre2el.fpsreducer.feature.module.setting.KeyBindSetting;
import bre2el.fpsreducer.feature.module.setting.NumberSetting;
import bre2el.fpsreducer.feature.module.setting.Setting;
import bre2el.fpsreducer.util.InventoryUtil;
import bre2el.fpsreducer.util.PlayerUtil;
import bre2el.fpsreducer.util.RotationUtil;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.util.hit.EntityHitResult;

public class TotemHit extends Module {
    public KeyBindSetting bind;
    public static String[] eC;
    public NumberSetting delay;
    public static byte[] eD;
    public boolean attacked;
    public int tick;

    @EventHandler
    void onAttack(Pre event) {
        if (Main.mc.player.getMainHandStack().isOf(Items.TOTEM_OF_UNDYING)) {
            if (RotationUtil.getHitResult(Main.mc.player, false, RotationUtil.getRotation().getYaw(), RotationUtil.getRotation().getPitch()) instanceof EntityHitResult var2
                    && var2.getEntity() instanceof PlayerEntity var3
                    && PlayerUtil.validateTarget(var3)) {
                for (int var6 = 0; var6 < 9; var6++) {
                    if (Main.mc.player.getInventory().getStack(var6).getItem() instanceof SwordItem) {
                        Main.mc.player.getInventory().selectedSlot = var6;
                        this.attacked = true;
                    }
                }
            }
        }
    }

    public static void gS() {
        eD = new byte[16];
        eD[15] = -14;
        eD[12] = 85;
        eD[5] = 67;
        eD[0] = 65;
        eD[10] = -51;
        eD[3] = -10;
        eD[11] = 76;
        eD[6] = -32;
        eD[8] = -64;
        eD[2] = 74;
        eD[14] = -80;
        eD[13] = 22;
        eD[1] = -122;
        eD[7] = 50;
        eD[9] = 116;
        eD[4] = -64;
    }

    static {
        gS();
        gR();
    }

    public static String gQ(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{-94, -37, 1, 0, 83, 109, -52, 90, 66, -124, -42, 10, 19, 31, -103, -12};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 148, 256);
        SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] var8 = var7.generateSecret(var6).getEncoded();
        SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
        Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var10.init(2, var9, new IvParameterSpec(var2));
        byte[] var11 = var10.doFinal(var5);
        return new String(var11, "UTF-8");
    }

    public TotemHit() {
        super(eC[5], eC[6], Category.Combat);
        this.bind = new KeyBindSetting(eC[7], 0, false);
        this.delay = new NumberSetting(eC[8], eC[9], 0.0, 0.0, 10.0, 1.0);
        this.addSettings(new Setting[]{this.bind, this.delay});
    }

    @EventHandler
    void onAttack(Post event) {
        if (this.attacked) {
            if (!(this.delay.getDefaultValue() > 0.0)) {
                InventoryUtil.selectItemFromHotbar(Items.TOTEM_OF_UNDYING);
                this.attacked = false;
            }
        }
    }

    @EventHandler
    void onUpdate(bre2el.fpsreducer.event.impl.UpdateEvent.Pre event) {
        if (!nullCheck()) {
            if (this.attacked) {
                if (this.delay.getDefaultValue() != 0.0) {
                    if (!((double)this.tick < this.delay.getDefaultValue())) {
                        InventoryUtil.selectItemFromHotbar(Items.TOTEM_OF_UNDYING);
                        this.attacked = false;
                        this.tick = 0;
                    } else {
                        this.tick++;
                    }
                }
            }
        }
    }

    public static void gR() {
        try {
            eC = new String[10];
            eC[6] = gQ(
                    "ja59j30Y8O8/48Ad2gqtVEGGSvbAQ+AywHTNTFUWsPL8C80TjCbQwm6sps59yxB5Xc2Q2p47RB73U7VBQc0VGiV98Q5Mmz4sAev7QaqcKlIvdq80E7Af0guH555eDgME", "�u�\u00111�", eD
            );
            eC[7] = gQ("IJ9W90T1+5SHA0FlzD3GnkGGSvbAQ+AywHTNTFUWsPLJWoMkaBLTLReocYuf50Icb9163kHdIPqk+ov3sqDw8A==", "�\u0005n�\u00036", eD);
            eC[4] = gQ("j4wv3Qz4EHggJVwFD+5p9UGGSvbAQ+AywHTNTFUWsPIkcnPgaLzy7/MGkzl/ULLpOMUVAF6QHRZARXmucl3xEw==", "�̱\u0010O'", eD);
            eC[2] = gQ("WLKSQ7wo65NDPya9z6Kf+UGGSvbAQ+AywHTNTFUWsPJeHy+8sQ0ZtamdC1niA4DZaFYuzpkU9W45LvlRe+34Hw==", "�a\u0005�[�", eD);
            eC[8] = gQ("e4sH5nlcYPXZqq/JryPsN0GGSvbAQ+AywHTNTFUWsPI/hqNAeVfOvIdHcYXF1SJS", "�1]�", eD);
            eC[0] = gQ("8O/RsFWYp1AXB3InrFw770GGSvbAQ+AywHTNTFUWsPLNxtVmee2OQ+KG+tXrPMOS", "\u0019}NK(�", eD);
            eC[9] = gQ("yZJeVeguH8bzcsS25TJL0kGGSvbAQ+AywHTNTFUWsPLsoQORlKGbMg3NCSU17B2d/oTE+FbnUeaWQ+TMHKlzZQ==", "����k�", eD);
            eC[1] = gQ(
                    "IMRVcXh4HXnSeoSHtqe10UGGSvbAQ+AywHTNTFUWsPLPS0MiuoIYIPpshIdwrreyi/GO6Er3CSCGGuT+FPWGzgBf2mKpSSBVNuPmHI6d3TPoDQ5kpCCP/gGD9xi8be7H", "e��0Ch", eD
            );
            eC[5] = gQ("/xA2CEWRBD3YeTsZh9srLUGGSvbAQ+AywHTNTFUWsPKYWR4CMfX5RfaUQlmLI8qf", "C>�3,J", eD);
            eC[3] = gQ("hyFzVRPCzmDVZiJPnNFi2EGGSvbAQ+AywHTNTFUWsPJ/8Y9tsmG07k5yVj04eVKO", "A�\u0018�\u001bY", eD);
        } catch (Exception e) {
        }
    }
}
