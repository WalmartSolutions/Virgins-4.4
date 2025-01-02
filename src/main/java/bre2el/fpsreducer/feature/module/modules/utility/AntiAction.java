package bre2el.fpsreducer.feature.module.modules.utility;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.event.impl.MouseButtonEvent;
import bre2el.fpsreducer.event.impl.ItemUseEvent.Pre;
import bre2el.fpsreducer.event.orbit.EventHandler;
import bre2el.fpsreducer.feature.module.Module;
import bre2el.fpsreducer.feature.module.Module.Category;
import bre2el.fpsreducer.feature.module.setting.BoolSetting;
import bre2el.fpsreducer.feature.module.setting.KeyBindSetting;
import bre2el.fpsreducer.feature.module.setting.Setting;
import bre2el.fpsreducer.util.PlayerUtil;
import bre2el.fpsreducer.util.RotationManager;
import bre2el.fpsreducer.util.RotationUtil;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import net.minecraft.block.Blocks;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;

public class AntiAction extends Module {
    public static String[] dm;
    public static byte[] dn;
    public BoolSetting antiMiss;
    public KeyBindSetting bind;
    public BoolSetting glowstone;

    @EventHandler
    void onItemUse(Pre event) {
        if (!nullCheck()) {
            if (this.glowstone.isEnabled()
                    && RotationUtil.getHitResultBlock(Main.mc.player, false, RotationUtil.getRotation().getYaw(), RotationUtil.getRotation().getPitch(), 4.5) instanceof BlockHitResult var2
                    && Main.mc.world.getBlockState(var2.getBlockPos()).isOf(Blocks.RESPAWN_ANCHOR)
                    && (Integer)Main.mc.world.getBlockState(var2.getBlockPos()).get(RespawnAnchorBlock.CHARGES) != 0
                    && !Main.mc.player.isSneaking()
                    && Main.mc.player.getMainHandStack().isOf(Items.GLOWSTONE)) {
                event.cancel();
            }
        }
    }

    static {
        eU();
        eT();
    }

    @EventHandler
    void onAttack(bre2el.fpsreducer.event.impl.AttackEvent.Pre event) {
        if (!nullCheck()) {
            if (this.antiMiss.isEnabled()) {
                if (!this.isPlayer()) {
                    event.cancel();
                }
            }
        }
    }

    public AntiAction() {
        super(dm[7], dm[8], Category.Utility);
        this.bind = new KeyBindSetting(dm[9], 0, false);
        this.glowstone = new BoolSetting(dm[10], dm[11], true);
        this.antiMiss = new BoolSetting(dm[12], dm[13], false);
        this.addSettings(new Setting[]{this.bind, this.glowstone, this.antiMiss});
    }

    @EventHandler
    void onMouseButton(MouseButtonEvent event) {
        if (!nullCheck()) {
            if (Main.mc.currentScreen == null) {
                if (this.antiMiss.isEnabled()) {
                    if (!this.isPlayer() && event.button == 0) {
                        event.cancel();
                    }
                }
            }
        }
    }

    public static void eU() {
        dn = new byte[16];
        dn[2] = 68;
        dn[5] = -39;
        dn[11] = 70;
        dn[9] = -26;
        dn[4] = -22;
        dn[7] = -120;
        dn[12] = -1;
        dn[3] = -122;
        dn[0] = 44;
        dn[8] = -8;
        dn[1] = -63;
        dn[15] = 69;
        dn[10] = -86;
        dn[13] = -98;
        dn[14] = -20;
        dn[6] = 8;
    }

    boolean isPlayer() {
        if (!RotationUtil.checkRotations()) {
            if (RotationUtil.getHitResult(Main.mc.player, false, Main.mc.player.getYaw(), Main.mc.player.getPitch()) instanceof EntityHitResult var5
                    && var5.getEntity() instanceof PlayerEntity var4
                    && PlayerUtil.validateTarget(var4)) {
                return true;
            }

            return false;
        } else {
            if (RotationUtil.getHitResult(Main.mc.player, false, RotationManager.INSTANCE.getServerYaw(), RotationManager.INSTANCE.getServerPitch()) instanceof EntityHitResult var2
                    && var2.getEntity() instanceof PlayerEntity var1
                    && PlayerUtil.validateTarget(var1)) {
                return true;
            }

            return false;
        }
    }

    public static void eT() {
        try {
            dm = new String[14];
            dm[3] = eS("oR07jkyQ1b9t68AQNHfM/SzBRIbq2QiI+OaqRv+e7EVjgSEbnWxBY7silrkhDWdpECZycLj7axBgJeyo5qGqlA==", "͝\u0017�\u0013�", dn);
            dm[13] = eS("HNFpZiHvJAD6jrnJ+lcADSzBRIbq2QiI+OaqRv+e7EWqQhhubeDGsw/1wuKTuDlXKNTH2lderG7piFhyIgQmTA==", "�\u0002�Mъ", dn);
            dm[10] = eS("wBnGX4uTEOuqY4qxv3ZqvSzBRIbq2QiI+OaqRv+e7EVR4t3UnWAjeITUaiuqbTGWEO5m/stk1FWJrxDI3NCvRA==", "�����,", dn);
            dm[1] = eS("Mh812vzBwgbcll5VevxJNSzBRIbq2QiI+OaqRv+e7EUrkOA4nmBJwpzyxcgEqG32vq37+mVh4jhmmG0k7jtq9mZoOtpwGbhYr/FmdQfUvow=", "�i�6|�", dn);
            dm[12] = eS("CrB2SgXw3kPmL944ggCkOCzBRIbq2QiI+OaqRv+e7EUtHKrL7diz2jX4/bLYmrzC", "�+��\u0003\u001a", dn);
            dm[4] = eS(
                    "puSgho+dQVW+zKBOEOMc2CzBRIbq2QiI+OaqRv+e7EUCkfRX5tFWTzYFY+EZUSEiM63UGo5dBc8eBhRrHPZHHLod/F2nCa+Nof0aO01mA3wMvMP3LhSwF6pr8VqWBzD0", "�}\u001az��", dn
            );
            dm[11] = eS(
                    "wm+V1qNvweLoqjEMuHJ+MSzBRIbq2QiI+OaqRv+e7EUwvGfscd/2WCRPq1pYqyjRCUo+4OKWzGhYJA+L7/W4qcYBdBe/8SUL8PdG0ZooHoFr16W+AD473u3qBfXD8I6G", "q1ڢ;S", dn
            );
            dm[8] = eS("uC4OdlSAUWrWpYSwpSULSSzBRIbq2QiI+OaqRv+e7EU6/YrDpBNmVAFLGwUq0PFta/MkLj+4Wn5PKbUAJ5wGkq42PaA25wXgXdIxX5SExkA=", "N�� -�", dn);
            dm[2] = eS("uwkxnZ3qqueMtz5IBukVQizBRIbq2QiI+OaqRv+e7EW5jFQycCrWiYrjELru803icSfEA4VM3ERpNStbvVDU1Q==", "�$;�\u000f7", dn);
            dm[5] = eS("V0lOYPFcV19UO4jt5LDpiSzBRIbq2QiI+OaqRv+e7EXOeLFzn1vbScyiNhSQE1Pj", "5ѝ4��", dn);
            dm[9] = eS("KJy7Z9WEoByIop8KulFevyzBRIbq2QiI+OaqRv+e7EWNNkCryt76kRe58TeZaNa+5ojwdLhEwgqUsAP2QwZG/g==", "�kx�j", dn);
            dm[7] = eS("t0246riu1DkdvHlkKzWEwyzBRIbq2QiI+OaqRv+e7EVH96SLNz46ioPd5u5b4aMF", "_~I��6", dn);
            dm[6] = eS("AVEJUtrybRXPACFAOCaRKCzBRIbq2QiI+OaqRv+e7EUC5BAWYjloxM9wVqItAytUccgh7OcOVTd7ewRICnXMLQ==", "!v2���", dn);
            dm[0] = eS("XcZoLpQ1OSt2vSbpNy0m0SzBRIbq2QiI+OaqRv+e7EWoUbVAzb4ZH3hMBkNp5vSo", "\u0011\u0004\u0016GB�", dn);
        } catch (Exception e) {}
    }

    public static String eS(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{125, -21, -98, -120, 17, 24, 16, 0, -90, -115, 110, 104, 10, -100, -53, 66};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 133, 256);
        SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] var8 = var7.generateSecret(var6).getEncoded();
        SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
        Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var10.init(2, var9, new IvParameterSpec(var2));
        byte[] var11 = var10.doFinal(var5);
        return new String(var11, "UTF-8");
    }
}
