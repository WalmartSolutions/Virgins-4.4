package bre2el.fpsreducer.feature.module.modules.visual;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.event.impl.RenderHUDEvent;
import bre2el.fpsreducer.event.orbit.EventHandler;
import bre2el.fpsreducer.feature.module.Module;
import bre2el.fpsreducer.feature.module.Module.Category;
import bre2el.fpsreducer.feature.module.setting.ActionSetting;
import bre2el.fpsreducer.feature.module.setting.BoolSetting;
import bre2el.fpsreducer.feature.module.setting.KeyBindSetting;
import bre2el.fpsreducer.feature.module.setting.Setting;
import bre2el.fpsreducer.gui.hud.HUDEditor;
import bre2el.fpsreducer.gui.hud.impl.ArrayListRenderer;
import bre2el.fpsreducer.gui.hud.impl.KeybindHUDRenderer;
import bre2el.fpsreducer.gui.hud.impl.KeystrokesRenderer;
import bre2el.fpsreducer.gui.hud.impl.PotionHUDRenderer;
import bre2el.fpsreducer.gui.hud.impl.WatermarkRenderer;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class HUD extends Module {
    public ActionSetting edit;
    public BoolSetting lowerCase;
    public static byte[] cz;
    public BoolSetting watermark;
    public WatermarkRenderer watermarkRenderer;
    public KeystrokesRenderer keystrokesRenderer;
    public BoolSetting potionHud;
    public KeybindHUDRenderer keybindHUDRenderer;
    public BoolSetting keyBindHud;
    public KeyBindSetting bind;
    public BoolSetting arrayList;
    public BoolSetting keyStrokes;
    public PotionHUDRenderer potionHUDRenderer;
    public static String[] cy;
    public ArrayListRenderer arrayListRenderer;

    @EventHandler
    public void onRender(RenderHUDEvent event) {
        if (!nullCheck()) {
            float var2 = (float)Main.mc.getWindow().getFramebufferWidth() / (float)Main.mc.getWindow().getScaledWidth();
            float var3 = (float)Main.mc.getWindow().getFramebufferHeight() / (float)Main.mc.getWindow().getScaledHeight();
            float var4 = Math.min(var2, var3) * 0.5F;
            event.context.getMatrices().push();
            event.context.getMatrices().scale(1.0F / var4, 1.0F / var4, 1.0F);
            if (this.arrayList.isEnabled()) {
                this.arrayListRenderer.renderHUD(event.context, this.lowerCase.isEnabled());
            }

            if (this.keyBindHud.isEnabled()) {
                this.keybindHUDRenderer.renderHUD(event.context);
            }

            if (this.potionHud.isEnabled()) {
                this.potionHUDRenderer.renderHUD(event.context);
            }

            if (this.keyStrokes.isEnabled()) {
                this.keystrokesRenderer.renderHUD(event.context);
            }

            if (this.watermark.isEnabled()) {
                this.watermarkRenderer.renderHUD(event.context);
            }
        }
    }

    public static String dK(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{2, 0, -90, -120, 116, -62, -83, 0, -50, 41, -71, 70, -30, -60, -9, -64};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 145, 256);
        SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] var8 = var7.generateSecret(var6).getEncoded();
        SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
        Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var10.init(2, var9, new IvParameterSpec(var2));
        byte[] var11 = var10.doFinal(var5);
        String info = new String(var11, "UTF-8");
        return info;
    }

    public static void dM() {
        cz = new byte[16];
        cz[3] = 126;
        cz[6] = -39;
        cz[11] = 38;
        cz[14] = -83;
        cz[13] = -14;
        cz[15] = -77;
        cz[9] = -117;
        cz[1] = 52;
        cz[12] = 55;
        cz[0] = -87;
        cz[7] = 122;
        cz[5] = 26;
        cz[2] = -99;
        cz[10] = -38;
        cz[8] = -43;
        cz[4] = 105;
    }

    static {
        dM();
        dL();
    }

    public HUD() {
        super(cy[17], cy[18], Category.Visual);
        this.bind = new KeyBindSetting(cy[19], 0, false);
        this.edit = new ActionSetting(cy[20], cy[21]);
        this.arrayList = new BoolSetting(cy[22], cy[23], false);
        this.lowerCase = new BoolSetting(cy[24], cy[25], false);
        this.keyBindHud = new BoolSetting(cy[26], cy[27], false);
        this.potionHud = new BoolSetting(cy[28], cy[29], false);
        this.keyStrokes = new BoolSetting(cy[30], cy[31], false);
        this.watermark = new BoolSetting(cy[32], cy[33], false);
        this.arrayListRenderer = ArrayListRenderer.INSTANCE;
        this.keybindHUDRenderer = KeybindHUDRenderer.INSTANCE;
        this.potionHUDRenderer = PotionHUDRenderer.INSTANCE;
        this.keystrokesRenderer = KeystrokesRenderer.INSTANCE;
        this.watermarkRenderer = WatermarkRenderer.INSTANCE;
        this.addSettings(new Setting[]{this.bind, this.edit, this.arrayList, this.lowerCase, this.keyBindHud, this.potionHud, this.keyStrokes, this.watermark});
    }

    @Override
    public void onTick() {
        if (this.edit.isActivated()) {
            Main.mc.setScreen(HUDEditor.INSTANCE);
            this.edit.setActivated(false);
        }

        super.onTick();
    }

    public static void dL() {
        try {
            cy = new String[34];
            cy[21] = dK("RxogjiJYJ+Kf5Gwz+hqy2Kk0nX5pGtl61YvaJjfyrbNBUycqS0HzktYyaI9/P3/ioW8qDXcHCB7HurTsWHmesA==", "�U��}�", cz);
            cy[9] = dK("fnhtAblwlTRIMgc/CEVr8Kk0nX5pGtl61YvaJjfyrbMa7Hbrz6wL48VIT964MCWO", "\u0016`���9", cz);
            cy[25] = dK("eA+59LEU3y9+FwtJAWb1Kqk0nX5pGtl61YvaJjfyrbOBSAZNkMp6kfcHKIaKZdkYn7WmoJ0tyjzCGVJm0s8QdRNWpinNu9cTfuNdJaYsC/g=", "\u001bu�\\\u0004\u0006", cz);
            cy[23] = dK("23sMjfcNChMUdeVtmnxbAKk0nX5pGtl61YvaJjfyrbP4abVlARfYerGYYPEh6DyGm+jlIItSpC74TKJGET11M8Dh5FCfuJDhy/bb1LYqfag=", "\u001bc:���", cz);
            cy[33] = dK("S0o7pCdi2AaepohcbxT06Kk0nX5pGtl61YvaJjfyrbMlGHx3RuX/isfV7YD5TMIh3EBx0DmR4CfRHY95iVjZbA==", "S|p��W", cz);
            cy[15] = dK("7IMsVmUR4dF5/oA/TdrT1qk0nX5pGtl61YvaJjfyrbM/M6+lone/KHoeEL73aBbY", "*2�Df\u0019", cz);
            cy[2] = dK("yhGOafR7eQeBcV0rjeVzxKk0nX5pGtl61YvaJjfyrbNNSDC01qVE36jEDU3cvq4f", "���+��", cz);
            cy[11] = dK("Tg7WYTo3z1QlzR8DKRyDIqk0nX5pGtl61YvaJjfyrbODvYiLuKkUzFxgbU989lWu", "0����s", cz);
            cy[24] = dK("LSIRe32123NQ34JYddYIL6k0nX5pGtl61YvaJjfyrbNtSpNZgnbZytlOvpYHcbWm", "ʖ `��", cz);
            cy[30] = dK("Z/PFkF3p6YwwiPIFG+V4aqk0nX5pGtl61YvaJjfyrbMTFkgWYtsKBmkiM9eBvkRE", "�g5\u000f~�", cz);
            cy[17] = dK("mAhgnJj4q2rzeIKHLTJQRKk0nX5pGtl61YvaJjfyrbN25A3iBa1tdZcmofvvzKL5", "PUٱ��", cz);
            cy[10] = dK("oGVU74vPcTu4QSkOsbVOH6k0nX5pGtl61YvaJjfyrbNwbmgwqR/Lwoe0ywLnU0NHrMVixUW86JeZxio6ZD3xSQ==", "�ؠ��z", cz);
            cy[13] = dK("bcFgTMn+ldb1txanQK7Hrak0nX5pGtl61YvaJjfyrbOawdGra9c/Db9NkLRKErtN", "�˴���", cz);
            cy[16] = dK("MXoYEq2LhhkN3ZTI686ANqk0nX5pGtl61YvaJjfyrbNNB4/QBb8yWnaOj+QZu5+qUSXGsZktCD699+KsAYf62w==", "\u0019�D�Ug", cz);
            cy[32] = dK("HRAUsSAvpOV4ziGOcJ8nUqk0nX5pGtl61YvaJjfyrbOPIaoYDokzaS1s+sFoQlxD", "�\u0016����", cz);
            cy[28] = dK("HN77xQ5ouuKN3co4lr6Bjak0nX5pGtl61YvaJjfyrbMqkJEgM8qV4irXrJF94vFX", "@�F�d#", cz);
            cy[27] = dK("AB4Dbv3hs7quUAb1aHBbjak0nX5pGtl61YvaJjfyrbO9paeROM9gEbQsik/w2ebgCzgFS/BIxb/7Cyo0un51/w==", "�\u0012�E\u001b+", cz);
            cy[18] = dK("HvsZxMzN54XfM7w4u1iQyqk0nX5pGtl61YvaJjfyrbMcaOuxCmtwFfNjy7dRBtTrrvfzh6rd3rVQ8MGoqDX0FQ==", "!7�4��", cz);
            cy[29] = dK("ZV6oW0AoF6YRH846Ihhwh6k0nX5pGtl61YvaJjfyrbOxcD9DkEMJvaCgiL8Dgil6oj8Fasb6x02fWHKXBQcucw==", "���<�d", cz);
            cy[20] = dK("m6VjG2ikoy97UysPtHo11qk0nX5pGtl61YvaJjfyrbNOe0aGOWADmV7kh3uRDzCD", "i�}���", cz);
            cy[6] = dK("ytOvaJqVK1NEbHhJPc3Opak0nX5pGtl61YvaJjfyrbPiuch9aIHgkIz8V/UCQVB33NRAi/QaGaqwskrLTWrAIXk4nBH0VHVcydcFFrj2N4Q=", "\\,��jj", cz);
            cy[3] = dK("kmYw0Cw4YPEPiJQ9PHHzbak0nX5pGtl61YvaJjfyrbPGUBf4N0JdVkdogUYxgMmA", "\u001e��u*m", cz);
            cy[14] = dK("fWr/KDoI6i58rakFLJ7vKak0nX5pGtl61YvaJjfyrbOX8iuni45IxAUJc9Ck3BYP2N4gi1CYPqvCqvjWcRpcTQ==", "V��,�a", cz);
            cy[12] = dK("QT61HAY4ohfonTqxBWy4dKk0nX5pGtl61YvaJjfyrbNHQ/5u9NwGZZ2Ixebt5kBP4mbzoJRLKZIhSbZwOI9Slw==", "7�ݙ�H", cz);
            cy[31] = dK("3Nq+gRpVHLIVFeh60rIfPqk0nX5pGtl61YvaJjfyrbNdPM/y6krsTi7xsKqsgPl9T816EzazpthRqa/3YalxmQ==", "�cA|\u000e�", cz);
            cy[1] = dK("HEJg2g9g8QSLSiO5Y5zbaak0nX5pGtl61YvaJjfyrbPvDTNpJLmydTGg0Z7EyUX9CPBuD9d+Wv//fBkBSZoUmA==", "\u0011���G�", cz);
            cy[5] = dK("PPfmjX9ZTC8GDCCIgjmVa6k0nX5pGtl61YvaJjfyrbOrOrklau/B/Rzjkf97GyRM", "���A�", cz);
            cy[19] = dK("qGLAn0NdBHa5/gCVIx7CpKk0nX5pGtl61YvaJjfyrbMp5SqOF87W8vVIsGezDLhA", "�\u0015��S>", cz);
            cy[0] = dK("VYcSFwZIC9LWGyjKdvmW3Kk0nX5pGtl61YvaJjfyrbM1VYF1XnNHo3qB8OyHspcS", "�*�h&�", cz);
            cy[7] = dK("RUMQL5tb0sV/awtxlUoZLKk0nX5pGtl61YvaJjfyrbO+SPv0j2+d6owHomxzLfOT", "�̎��b", cz);
            cy[22] = dK("aZL/ESFeg6m4pQyXHAu0NKk0nX5pGtl61YvaJjfyrbNyp/2kn6Eq114/luVsNtle", "H��lP�", cz);
            cy[8] = dK("fALO8gqUBRe4DQ2X7HXu26k0nX5pGtl61YvaJjfyrbMV6GFdh5DRo2TsSPlVEnHqYc8ksH7YlzW4F7klcodCqt0MOL2xMZi5N0D0i/40xME=", "�E0��K", cz);
            cy[4] = dK("oNZx6Hq5Y9GSyISIrAnw+qk0nX5pGtl61YvaJjfyrbNhsnZdH0O67T+BzndgajJv0dGUoVfv6j5MuIb0KsO8LQ==", "]�6m]:", cz);
            cy[26] = dK("9lktWoBMCpEan+3YacUyMqk0nX5pGtl61YvaJjfyrbMWbpGQp04RQ45P57EujUTa", " G�!��", cz);
        } catch (Exception e) {

        }
    }
}
