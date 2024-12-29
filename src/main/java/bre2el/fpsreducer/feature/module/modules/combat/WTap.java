package bre2el.fpsreducer.feature.module.modules.combat;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.event.impl.AttackEvent.Post;
import bre2el.fpsreducer.event.impl.UpdateEvent.Pre;
import bre2el.fpsreducer.event.orbit.EventHandler;
import bre2el.fpsreducer.feature.module.Module;
import bre2el.fpsreducer.feature.module.Module.Category;
import bre2el.fpsreducer.feature.module.setting.BoolSetting;
import bre2el.fpsreducer.feature.module.setting.KeyBindSetting;
import bre2el.fpsreducer.feature.module.setting.RangeSetting;
import bre2el.fpsreducer.feature.module.setting.Setting;
import bre2el.fpsreducer.util.MathUtil;
import bre2el.fpsreducer.util.PlayerUtil;
import bre2el.fpsreducer.util.RotationUtil;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.EntityHitResult;
import org.lwjgl.glfw.GLFW;

public class WTap extends Module {
    public int delayTimer;
    public BoolSetting air;
    public static String[] ew;
    public int holdTimer;
    public RangeSetting release;
    public RangeSetting fov;
    public static byte[] ex;
    public RangeSetting delay;
    public int hit;
    public KeyBindSetting bind;
    public BoolSetting weapon;

    @EventHandler
    public void onUpdate(Pre event) {
        if (!nullCheck()) {
            if (this.hit == 1) {
                if (this.delayTimer < MathUtil.getRandomInt((int) this.delay.getDefaultValue(), (int) this.delay.getSecondValue())) {
                    this.delayTimer++;
                    return;
                }

                this.delayTimer = 0;
                Main.mc.options.forwardKey.setPressed(false);
                this.hit = 2;
            }

            if (this.hit == 2) {
                if (this.holdTimer < MathUtil.getRandomInt((int) this.release.getDefaultValue(), (int) this.release.getSecondValue())) {
                    this.holdTimer++;
                    return;
                }

                this.holdTimer = 0;
                if (GLFW.glfwGetKey(Main.mc.getWindow().getHandle(), 87) == 1) {
                    Main.mc.options.forwardKey.setPressed(true);
                }

                this.hit = 0;
            }
        }
    }

    static {
        gJ();
        gI();
    }

    public static void gI() {
        try {
            ew = new String[26];
            ew[8] = gH("nMMWDXZ7NOmtXK1iOY8ve2m+owd4yafcYU5LcG591/CUzKlq7emEONWdIRrHNg1dvMkJebWxTwYSkxNlcIh+aWG6yf62w7sTt3JUAG4lnW0=", "\u0014�p\r\u001e�", ex);
            ew[12] = gH("IHJ9tiJQZ/ge9zgsFhaWs2m+owd4yafcYU5LcG591/BwLGFpfBwsKk3fT7iKVshAkr8K7zpFjz7uDZEsvR2rGA==", "�\n\nh��", ex);
            ew[11] = gH("vQVkxL7louq3c0nyJPCxFWm+owd4yafcYU5LcG591/BDbhArgPZQp0zdMuEx/pQe", "G�PcIR", ex);
            ew[10] = gH("Lnlx24R5d2Ic+HOZpatRLmm+owd4yafcYU5LcG591/C+oX+lCAxS/mqPnIP6QJEx503RzZFkwa6jVes4/TnnjzG+ExRfJT4caGrz0B5ypQY=", "/1�\u0012Q�", ex);
            ew[2] = gH("ZhxxXi/UshULpC4lrWHJWmm+owd4yafcYU5LcG591/AmbaUqKYxANzEIjCGAa9pVFjEJRexhyS8f7vlO3xYYQA==", "\u0016�Bx��", ex);
            ew[1] = gH("pMcym0BeRr0bzrc4keexA2m+owd4yafcYU5LcG591/AmFX25hGoSSEWavGx0Z4pF+Mrej5nt3DB3dXGNRAsU6WrDfoPWnYNMY2rbBbtpz0U=", "�D�:p�", ex);
            ew[21] = gH("YCeO3xIYmU9sC5iyz8U9Qmm+owd4yafcYU5LcG591/DNOQxF0aJuPPtSpXyTi6xnDgyFUS/UFYiVX06JZBbgO/Au+gJuue0AfADqyWxMd7g=", "q\u000f\u000f�ζ", ex);
            ew[23] = gH("NIUqoU6ZTO7mTMfzh9jW0mm+owd4yafcYU5LcG591/BBDqbCPaLKGgGkgA6/i/imEN4QPRkahFI5QQZVYURlVY8szZz4FzIS6wKrSwIBh7o=", "k�,sz�", ex);
            ew[9] = gH("HpFfo3z1/Fis4ZAjFAKa7mm+owd4yafcYU5LcG591/ANX9DPAguu1NLpEkAZa3Zs", "�{pҏo", ex);
            ew[7] = gH("/SMtTikMBJh5/DU1GnDo22m+owd4yafcYU5LcG591/DYhICNvA1F+f3clXdDMGCq", "�m��&D", ex);
            ew[4] = gH("TOk4aF+L1t2JRPwjUW7HOmm+owd4yafcYU5LcG591/DRasImnWdvPls3qFSx1c43", "\u001aGo$�m", ex);
            ew[14] = gH("UoHT8sKo7UKEowkrntYx/mm+owd4yafcYU5LcG591/ABvE2nD0gNbDwfQ10JQfweEk5ySJueTqXhulvzFtiBviJgv2YhXMuvF3rzriXPPEo=", "��\u001e\u0019\u001e}", ex);
            ew[24] = gH("CbqeuaL5uca3wMALN+HYZmm+owd4yafcYU5LcG591/Bj5YroQvQI2e75PA97sduK", "A餟�\u0012", ex);
            ew[25] = gH("yqitTusS75m9wgrHiNy9JWm+owd4yafcYU5LcG591/BDgc4usQpzEYh7asJkm6qB3U/wZyLjQKAb85oFxrNNMg==", "\u0000\u0003�$�\u0006", ex);
            ew[5] = gH("ym1wsuKaT6nuHrAhOSG7/Wm+owd4yafcYU5LcG591/AS7Ia52/ZNTtCScjMBZ7Bn", "cz��n$", ex);
            ew[18] = gH("01rYNn9pujZScvHoNYj+mGm+owd4yafcYU5LcG591/A2UF0rSbe7U7QkoPiIPTZ8", "����(�", ex);
            ew[13] = gH("eV/t/3jqg0n6ULZ0E4FIb2m+owd4yafcYU5LcG591/D3j5IYeFT75MedtL7VJKAN", "��8��", ex);
            ew[15] = gH("7ecTuSqjYb4ZaR0pjf618Wm+owd4yafcYU5LcG591/A0zwkjgju6DtSWE0I2IPIwkHu39DqaKKnfI1Bwyb8ouA==", "\u0017��ʬ�", ex);
            ew[16] = gH("xkUSgCtIuZEMv8G8wO171mm+owd4yafcYU5LcG591/A+PNflRFRhfMvOujOlHxfn", "���\u000b>\u001e", ex);
            ew[19] = gH("nPrgxobbzJ2gk2VrG5e8G2m+owd4yafcYU5LcG591/DznJkeUGjN4fDk0pE+9qcb", "��VP!$", ex);
            ew[22] = gH("hR7PhNv5cl5+fPiF6e1Prmm+owd4yafcYU5LcG591/AWFpfSzemx+cECS3imXv8m", "'�[�\u0002�", ex);
            ew[0] = gH("IiAmnRvf4FzDN+0xbtnjM2m+owd4yafcYU5LcG591/BnblGYsZsml/CDXyxoJGAx", "U\u0005ŊY\u001b", ex);
            ew[17] = gH("msM5Aj6120HrFGxCHTtmMWm+owd4yafcYU5LcG591/DmdTqgBnT9FvdZ4tYunw//", "�\rW\u0003G\u0011", ex);
            ew[3] = gH("/O3r7hT3n631RC0Ptw00aWm+owd4yafcYU5LcG591/CV3g85HIgCofGCkmslnYw3", "��a��!", ex);
            ew[6] = gH("tlSfPhh5DZlgmFuXs9rxH2m+owd4yafcYU5LcG591/AF1XF1B988/MoAmwNAUmZC", "�\"�\u0006�\n", ex);
            ew[20] = gH("KacfxxhceNSfurnZ/Vv+Jmm+owd4yafcYU5LcG591/B6HXSgJItrrXr3RMqjOS5c", "]Q��", ex);
        } catch (Exception e) {
        }
    }

    public static void gJ() {
        ex = new byte[16];
        ex[12] = 110;
        ex[0] = 105;
        ex[1] = -66;
        ex[9] = 78;
        ex[15] = -16;
        ex[5] = -55;
        ex[14] = -41;
        ex[4] = 120;
        ex[3] = 7;
        ex[7] = -36;
        ex[13] = 125;
        ex[2] = -93;
        ex[8] = 97;
        ex[10] = 75;
        ex[11] = 112;
        ex[6] = -89;
    }

    @EventHandler
    public void onAttack(Post event) {
        if (this.hit == 0) {
            if (!this.air.isEnabled() || Main.mc.player.isOnGround()) {
                if (!this.weapon.isEnabled() || PlayerUtil.weaponCheck()) {
                    if (RotationUtil.getHitResult(Main.mc.player, false, RotationUtil.getRotation().getYaw(), RotationUtil.getRotation().getPitch()) instanceof EntityHitResult var2
                            && var2.getEntity() instanceof PlayerEntity var3
                            && var3 != Main.mc.player
                            && PlayerUtil.validateTarget(var3)
                            && RotationUtil.getAngleToLookVecPlayers(var3, Main.mc.player.getBoundingBox().getCenter())
                            <= (double)MathUtil.getRandomFloat((float)this.fov.getDefaultValue(), (float)this.fov.getSecondValue())) {
                        this.hit = 1;
                    }
                }
            }
        }
    }

    @Override
    public void onDisable() {
        this.hit = 0;
        super.onDisable();
    }

    public static String gH(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{-31, 59, 1, 89, -19, 24, 125, -49, -20, 87, 61, 0, -125, -85, 82, -26};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 147, 256);
        SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] var8 = var7.generateSecret(var6).getEncoded();
        SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
        Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var10.init(2, var9, new IvParameterSpec(var2));
        byte[] var11 = var10.doFinal(var5);
        return new String(var11, "UTF-8");
    }

    public WTap() {
        super(ew[13], ew[14], Category.Combat);
        this.bind = new KeyBindSetting(ew[15], 0, false);
        this.delay = new RangeSetting(ew[16], ew[17], 2.0, 4.0, 0.0, 10.0, 1.0, 4.0);
        this.release = new RangeSetting(ew[18], ew[19], 6.0, 8.0, 0.0, 10.0, 1.0, 4.0);
        this.fov = new RangeSetting(ew[20], ew[21], 140.0, 180.0, 0.0, 180.0, 1.0, 60.0);
        this.weapon = new BoolSetting(ew[22], ew[23], false);
        this.air = new BoolSetting(ew[24], ew[25], true);
        this.addSettings(new Setting[]{this.bind, this.delay, this.release, this.fov, this.weapon, this.air});
    }
}
