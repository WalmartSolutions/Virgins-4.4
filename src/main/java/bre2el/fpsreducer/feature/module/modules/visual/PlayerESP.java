package bre2el.fpsreducer.feature.module.modules.visual;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.client.Main.Theme;
import bre2el.fpsreducer.event.impl.RenderHUDEvent;
import bre2el.fpsreducer.event.impl.RenderWorldEvent;
import bre2el.fpsreducer.event.orbit.EventHandler;
import bre2el.fpsreducer.feature.module.Module;
import bre2el.fpsreducer.feature.module.Module.Category;
import bre2el.fpsreducer.feature.module.setting.BoolSetting;
import bre2el.fpsreducer.feature.module.setting.KeyBindSetting;
import bre2el.fpsreducer.feature.module.setting.ModeSetting;
import bre2el.fpsreducer.feature.module.setting.Setting;
import bre2el.fpsreducer.util.PlayerUtil;
import bre2el.fpsreducer.util.RenderUtil;
import bre2el.fpsreducer.util.RenderUtil.RenderHelper;
import java.awt.Color;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

public class PlayerESP extends Module {
    public KeyBindSetting bind;
    public BoolSetting durability;
    public static String[] cS;
    public static byte[] cT;
    public ModeSetting mode;

    static {
        eq();
        ep();
    }

    public static String eo(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{66, -66, -17, -107, -110, 121, -66, 98, -124, 0, -29, 123, 29, 21, 45, -95};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 81, 256);
        SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] var8 = var7.generateSecret(var6).getEncoded();
        SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
        Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var10.init(2, var9, new IvParameterSpec(var2));
        byte[] var11 = var10.doFinal(var5);
        return new String(var11, "UTF-8");
    }

    public PlayerESP() {
        super(cS[12], cS[13], Category.Visual);
        this.bind = new KeyBindSetting(cS[14], 0, false);
        this.mode = new ModeSetting(cS[15], cS[16], cS[17], new String[]{cS[18], cS[19]});
        this.durability = new BoolSetting(cS[20], cS[21], false);
        this.addSettings(new Setting[]{this.bind, this.mode, this.durability});
    }

    public static void eq() {
        cT = new byte[16];
        cT[3] = 117;
        cT[4] = 0;
        cT[0] = -25;
        cT[12] = -5;
        cT[13] = 56;
        cT[1] = -57;
        cT[2] = -123;
        cT[6] = -40;
        cT[5] = -79;
        cT[8] = -21;
        cT[10] = -42;
        cT[15] = -93;
        cT[9] = 53;
        cT[14] = 109;
        cT[7] = 83;
        cT[11] = -55;
    }


    @EventHandler
    void onRender3D(RenderWorldEvent event) {
        if (!nullCheck()) {
            for (PlayerEntity var3 : Main.mc.world.getPlayers()) {
                if (var3 != Main.mc.player && PlayerUtil.validateTarget(var3) && this.mode.getMode().equals(cS[22])) {
                    Vec3d var4 = RenderUtil.getEntityPos(var3);
                    RenderHelper.setMatrixStack(event.matrices);
                    RenderUtil.setCameraAction();
                    RenderHelper.getPositionMatrix().set(event.matrices.peek().getPositionMatrix());
                    Color var5 = Theme.PRIMARY;
                    RenderUtil.renderFilledBox(
                            event.matrices,
                            (float)var4.x - var3.getWidth() / 1.5F,
                            (float)var4.y,
                            (float)var4.z - var3.getWidth() / 1.5F,
                            (float)var4.x + var3.getWidth() / 1.5F,
                            (float)var4.y + var3.getHeight(),
                            (float)var4.z + var3.getWidth() / 1.5F,
                            new Color(var5.getRed(), var5.getGreen(), var5.getBlue(), 160)
                    );
                    RenderUtil.renderOutlinedBox(
                            event.matrices,
                            (float)var4.x - var3.getWidth() / 1.5F,
                            (float)var4.y,
                            (float)var4.z - var3.getWidth() / 1.5F,
                            (float)var4.x + var3.getWidth() / 1.5F,
                            (float)var4.y + var3.getHeight(),
                            (float)var4.z + var3.getWidth() / 1.5F,
                            new Color(var5.getRed(), var5.getGreen(), var5.getBlue(), 200)
                    );
                    RenderHelper.getMatrixStack().pop();
                }
            }
        }
    }

    @EventHandler
    void onRenderHUD(RenderHUDEvent event) {
        if (!nullCheck()) {
            for (PlayerEntity var3 : Main.mc.world.getPlayers()) {
                if (var3 != Main.mc.player && PlayerUtil.validateTarget(var3) && this.mode.getMode().equals(cS[23])) {
                    RenderUtil.drawNametag(event.context, var3, Theme.PRIMARY, this.durability.isEnabled());
                }
            }
        }
    }

    public static void ep() {
        try {
            cS = new String[24];
            cS[23] = eo("ANgyGGNIZjx9QBNli13iE+fHhXUAsdhT6zXWyfs4baOoWYSsn1Rtpqd+h4eAr+PT", "\\�\u0002�q<", cT);
            cS[15] = eo("lCGThgACJeelYbA9l6oUrufHhXUAsdhT6zXWyfs4baMWqCp/sCyA3FC89EGDMPuM", "]\u0018���", cT);
            cS[18] = eo("YVWE3SknZ7p1NLc3itMpn+fHhXUAsdhT6zXWyfs4baOvaFpsst+l9xtO/J5V/rb9", "5��ť\u007f", cT);
            cS[1] = eo("noGlH22aNAOd6dzNgjeJ6ufHhXUAsdhT6zXWyfs4baOr/WcITZlV5ARr7JcDaRiN/LO6WrEAUB0fp3BAS9eFQQ==", "BX\u0015\b3�", cT);
            cS[14] = eo("+xxggwXZNr+XRfset+8h7efHhXUAsdhT6zXWyfs4baMAjZyHuJqegvbL2AXwFY9D", "\r\n�3\u0006�", cT);
            cS[4] = eo("m/eVAH1uTsqGM3dirISa4OfHhXUAsdhT6zXWyfs4baPZ+sReOqAW6T3KZei0bp0O", "IA�\u001d��", cT);
            cS[3] = eo("8ZFwHgsnhlfc5dxKGiShoufHhXUAsdhT6zXWyfs4baNfQQt/x/23pRruCUyJR7I8", "b\u0007��_�", cT);
            cS[8] = eo("EaDefCJl8zidMXGdbLQLtOfHhXUAsdhT6zXWyfs4baN38D3pWaYvTzgP9rBSkVmV", "�UM,z�", cT);
            cS[10] = eo("Kd75WK+ZV5MlfMSjEMiptOfHhXUAsdhT6zXWyfs4baOB2reDXeAxhsvyJ+tKkEbB", "\u0010��)�/", cT);
            cS[19] = eo("mRZB1HFzQAdCLg9imRjto+fHhXUAsdhT6zXWyfs4baNfNZvX75ctnQTQJXqFwhdx", "J�oiX�", cT);
            cS[6] = eo("sNsTN+75qn4CtNkqqsJ2tOfHhXUAsdhT6zXWyfs4baOp6EL/hiAiH7F8FVVHY9cQ", "�q+�a�", cT);
            cS[9] = eo("MsLxIRrtf5OK+CQcompgrufHhXUAsdhT6zXWyfs4baPZgyZGSE7QFwY9wa+LTxj4u4lYjyrekYgiEOkYEVkcuA==", "bm[\u0014/�", cT);
            cS[5] = eo("oJcoqc2Nm0YSwRhLrr5Jr+fHhXUAsdhT6zXWyfs4baPbEGzftGLdCvXKNthW7BQ0", "4��ֲ�", cT);
            cS[11] = eo("ijBwjef+pBnaS+cFQdaHi+fHhXUAsdhT6zXWyfs4baNNXVPO9Rl3Gc13VRBMcMcs", "��y���", cT);
            cS[17] = eo("RhAMhm6ZzYHY4yXhMmtxM+fHhXUAsdhT6zXWyfs4baPCp38PpZp9G/qPctYJfPPX", "��\u000bKf=", cT);
            cS[0] = eo("/qzRQspGBDALbKmDNQuXJefHhXUAsdhT6zXWyfs4baMSSbGp7cFjr7US8t0+lYzs", "�*\u0014r�", cT);
            cS[22] = eo("JSMAK8byn/RptlKDoTs1FefHhXUAsdhT6zXWyfs4baNmbgjM/9iXy/ny1M1U4LFf", "�ft[}�", cT);
            cS[12] = eo("S66ZnTgBifZkJvy99wd1gOfHhXUAsdhT6zXWyfs4baPHJRzZoXSfFmq7sszAmChj", "��ߖ�", cT);
            cS[16] = eo("PtJvR1whusHl8J9NSBJ8ZOfHhXUAsdhT6zXWyfs4baNrVpTvWLAvtTW8PkuM93nI", "0��*��", cT);
            cS[13] = eo("8k0s/nZaynLHi8vYx3eA6ufHhXUAsdhT6zXWyfs4baOXEJcOWMHGcGmA2JXKJLkIPIaPlf/QTjrdeSw+iEDvLA==", ";\u001c�M�\u0005", cT);
            cS[2] = eo("umDom2Bf5YJTEYCBUPTuLOfHhXUAsdhT6zXWyfs4baMNZFF5jk6YIZvh3W6PcboN", "<1��7", cT);
            cS[7] = eo("nZjYfm47p764nUPM7I09/OfHhXUAsdhT6zXWyfs4baMLMrzUYT9qDuGbSWMPe/x4", "�E��=�", cT);
            cS[20] = eo("MZRW57IuRFUg5i5b+2Pl2ufHhXUAsdhT6zXWyfs4baNeCOawzwkY9bcL7oEgL/FI", "�1�+��", cT);
            cS[21] = eo("INwfk9tGkrrZStyUUNGoOufHhXUAsdhT6zXWyfs4baOLVIIqRT/usWrmGPQc98EUJBEEkj5Dk0WSBLCZN0kJ6Q==", "Ł�8�.", cT);
        } catch (Exception e) {
        }
    }
}
