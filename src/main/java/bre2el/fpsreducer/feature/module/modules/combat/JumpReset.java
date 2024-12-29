package bre2el.fpsreducer.feature.module.modules.combat;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.event.impl.DamageEvent.Pre;
import bre2el.fpsreducer.event.impl.UpdateEvent.Post;
import bre2el.fpsreducer.event.orbit.EventHandler;
import bre2el.fpsreducer.feature.module.Module;
import bre2el.fpsreducer.feature.module.Module.Category;
import bre2el.fpsreducer.feature.module.setting.KeyBindSetting;
import bre2el.fpsreducer.feature.module.setting.RangeSetting;
import bre2el.fpsreducer.feature.module.setting.Setting;
import bre2el.fpsreducer.util.MathUtil;
import bre2el.fpsreducer.util.PlayerUtil;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;

public class JumpReset extends Module {
    public RangeSetting chance;
    public static byte[] dV;
    public boolean jump;
    public KeyBindSetting bind;
    public static String[] dU;

    public static String fR(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{115, 17, -50, -9, -98, 6, -58, -94, 0, 93, -104, -95, 114, -41, 59, -77};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 142, 256);
        SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] var8 = var7.generateSecret(var6).getEncoded();
        SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
        Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var10.init(2, var9, new IvParameterSpec(var2));
        byte[] var11 = var10.doFinal(var5);
        return new String(var11, "UTF-8");
    }

    @EventHandler
    public void onDamaged(Pre event) {
        if (event.source.isOf(DamageTypes.PLAYER_ATTACK)
                && event.source.getAttacker() instanceof PlayerEntity var2
                && var2.getAttacking() == Main.mc.player
                && PlayerUtil.validateTarget(var2)
                && var2 != Main.mc.player
                && var2.distanceTo(Main.mc.player) <= 3.0F) {
            if (Main.mc.player.isBlocking()) {
                return;
            }

            if (Main.mc.player.isUsingItem()) {
                return;
            }

            if (Main.mc.currentScreen instanceof HandledScreen) {
                return;
            }

            if (!Main.mc.player.isOnGround()) {
                return;
            }

            if (Main.mc.player.isInsideWaterOrBubbleColumn()) {
                return;
            }

            if (Main.mc.player.isInsideWall()) {
                return;
            }

            if (Main.mc.player.isTouchingWater()) {
                return;
            }

            if (MathUtil.getRandomInt(1, 100) <= MathUtil.getRandomInt((int)this.chance.getDefaultValue(), (int)this.chance.getSecondValue())) {
                Main.mc.options.jumpKey.setPressed(true);
                this.jump = true;
            }
        }
    }

    public JumpReset() {
        super(dU[5], dU[6], Category.Combat);
        this.bind = new KeyBindSetting(dU[7], 0, false);
        this.chance = new RangeSetting(dU[8], dU[9], 20.0, 40.0, 0.0, 100.0, 1.0, 40.0);
        this.addSettings(new Setting[]{this.bind, this.chance});
    }

    @EventHandler
    public void onUpdate(Post event) {
        if (!nullCheck()) {
            this.setDetail(this.chance.getDefaultValue() + "-" + this.chance.getSecondValue());
            if (this.jump) {
                this.jump = false;
                Main.mc.options.jumpKey.setPressed(false);
            }
        }
    }

    public static void fS() {
        try {
            dU = new String[10];
            dU[6] = fR(
                    "b3QJUMOUM587D1kJ0G+Hjb2gNgPnyUmV3FKyfFVyr4WK8n/xI2q1F7SYph3nO+/lw4tCL80DT2v+9ZXNLufaau8LSsPtkJ2MT39/obX4sbLU5yrNf7UKrwoK/snMaHo+", "(_z���", dV
            );
            dU[1] = fR(
                    "eHsXi1WnRnBz7B60Bl0brL2gNgPnyUmV3FKyfFVyr4XZQGV6wVP7CsbLR1nCzt2+0dP9je3LLyZgehtrWlBwjzMI/+zWQwoOinNB3skpTNKw7htqS9gsuaLsGMjIFVMJ", "�B����", dV
            );
            dU[9] = fR("CDReFHvqc5yXx3UT/yanPL2gNgPnyUmV3FKyfFVyr4W7MzKEZlbmd0DyByLV37gUqZgGSVOzihIXT0AteP8EnA==", "�\u0010f_.�", dV);
            dU[8] = fR("S3fTizICR6TbaYYX9/eGrb2gNgPnyUmV3FKyfFVyr4XBQNeQUPO7XmVfQ+Qs4phr", "�����L", dV);
            dU[2] = fR("J8JVUL0fvsUwB7ok2422gb2gNgPnyUmV3FKyfFVyr4V+QMPmzzX7sXDPYlOlrfyCaSi4KynaKKeXbMG1L8xQuQ==", "k�<J��", dV);
            dU[5] = fR("y6nt8iacpy0RgzO7IxBoBb2gNgPnyUmV3FKyfFVyr4URB8/dkI5T2pO7M12ojD/K", "�+�4�\u001c", dV);
            dU[3] = fR("9lDO9MaMUF4imqf8n1/5m72gNgPnyUmV3FKyfFVyr4X9OS6f27WbVYM5pKBrd30H", "vy�D�", dV);
            dU[7] = fR("/7Aauak4pHdRYYm7HEQo472gNgPnyUmV3FKyfFVyr4UfwdSGk21usAkLTPyC0K78q5AhDUnaOwRHZXThs6p1yw==", "ϟ�6��", dV);
            dU[0] = fR("yUEceQ54a8HOZiqM0eV4Vr2gNgPnyUmV3FKyfFVyr4VlhSmePo/I5Xz6UmqYxbmL", "���]��", dV);
            dU[4] = fR("czTVSAKwjWpPdyhOPzI73r2gNgPnyUmV3FKyfFVyr4Vc8EbG+NxtY3lKlNwv6UxE8y3OcHGdk0UPx8PKyy1XRw==", "�'/�`�", dV);
        } catch (Exception e) {
        }
    }

    public static void fT() {
        dV = new byte[16];
        dV[14] = -81;
        dV[13] = 114;
        dV[15] = -123;
        dV[0] = -67;
        dV[11] = 124;
        dV[2] = 54;
        dV[3] = 3;
        dV[6] = 73;
        dV[10] = -78;
        dV[5] = -55;
        dV[1] = -96;
        dV[4] = -25;
        dV[12] = 85;
        dV[7] = -107;
        dV[9] = 82;
        dV[8] = -36;
    }

    static {
        fT();
        fS();
    }
}
