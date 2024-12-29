package bre2el.fpsreducer.feature.module.modules.utility;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.event.impl.UpdateEvent.Pre;
import bre2el.fpsreducer.event.orbit.EventHandler;
import bre2el.fpsreducer.feature.module.Module;
import bre2el.fpsreducer.feature.module.Module.Category;
import bre2el.fpsreducer.feature.module.setting.BoolSetting;
import bre2el.fpsreducer.feature.module.setting.KeyBindSetting;
import bre2el.fpsreducer.feature.module.setting.Setting;
import bre2el.fpsreducer.mixin.LivingEntityAccessor;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class NoDelay extends Module {
    public BoolSetting jump;
    public static byte[] bP;
    public KeyBindSetting bind;
    public BoolSetting breakDelay;
    public static String[] bO;

    static {
        cK();
        cJ();
    }

    public NoDelay() {
        super(bO[7], bO[8], Category.Utility);
        this.bind = new KeyBindSetting(bO[9], 0, false);
        this.jump = new BoolSetting(bO[10], bO[11], true);
        this.breakDelay = new BoolSetting(bO[12], bO[13], true);
        this.addSettings(new Setting[]{this.bind, this.jump, this.breakDelay});
    }

    @EventHandler
    void onUpdate(Pre event) {
        if (!nullCheck()) {
            if (this.jump.isEnabled()) {
                ((LivingEntityAccessor)Main.mc.player).setJumpingCooldown(0);
            }
        }
    }

    public static void cJ() {
        try {
            bO = new String[14];
            bO[1] = cI("fVC0sT4X6+K298YzTPwJMRv2zzP/V0Av/4XRZ/VE4vmmnmRBjOo8zsgOWIkDb2An+uMAjBwb5ppCUtCQ6RFxdQ==", "�;Vͺw", bP);
            bO[4] = cI("oS8zaG2I54SpA2dpgjWeNBv2zzP/V0Av/4XRZ/VE4vk+8UB8hXB5FUEtDDjUQHsM7tCglJQz864bmCmJJ2sjWA==", "\u000bF.���", bP);
            bO[9] = cI("lOQeSEiXX4ScEWuSCS4nDBv2zzP/V0Av/4XRZ/VE4vn8wIkDaxIDFoxAhs+i9K6HRjyK299itZQ99K65PMUxog==", "FWSsM\u0010", bP);
            bO[7] = cI("6r0fYKJuk3I6PoO3Z11n3xv2zzP/V0Av/4XRZ/VE4vksXHvMdnS9KqbY8Piph116", " �ZZJ\u0002", bP);
            bO[11] = cI("AO+7fXP2OAEQ5tJHeUEOWxv2zzP/V0Av/4XRZ/VE4vkCBBMsMehk29uZrOCTL/RHrMIVawEEzdBZH6ATX10oeQ==", "��릟�", bP);
            bO[13] = cI("wkAf1Q9QoQ0JIdeQq8fsSxv2zzP/V0Av/4XRZ/VE4vmXnGqNHHulWivLt55QGXdLNZsFOcFg11Hg62uwdC6JwQ==", "L\u0007tL��", bP);
            bO[6] = cI("5XhLvaP++eE+l6bYqg3pLxv2zzP/V0Av/4XRZ/VE4vntcaFLmDksvoflX4WTUx74wvFEWMQdoqPtbrn3hTyiPw==", "��E\u0015H�", bP);
            bO[5] = cI("k2eviY9QleJYWYNJf6mN8Rv2zzP/V0Av/4XRZ/VE4vknDSnyXl2mb1+W6wV9NVBE", "#�W��9", bP);
            bO[2] = cI("FuIcPlCqbbfqNHLJ1TbdWxv2zzP/V0Av/4XRZ/VE4vlk9wzUGt319cynVM7XRFTBy7Tg7ji7oDKxyNGeLh2vnw==", "�\u0013�R�3", bP);
            bO[8] = cI("g2q5/C39gcTD1s8sTfVCExv2zzP/V0Av/4XRZ/VE4vlnbq7+2uC+l5nDtEGYNnp8ubR0eszkSiXQ16jWkc6Nbg==", "�e\u0010\u007fn@", bP);
            bO[10] = cI("Z6Ow4WLnYKxs/jLoCG17/Rv2zzP/V0Av/4XRZ/VE4vkc61LTAjqEne4jERmIMEjf", "������", bP);
            bO[0] = cI("UdH+VYc3KhyYoXioD3CfkBv2zzP/V0Av/4XRZ/VE4vnxF0PcPzvcSnCaaVctzaae", "����h�", bP);
            bO[3] = cI("l7EWe447H1o4qYNYd/idXxv2zzP/V0Av/4XRZ/VE4vnDB3SwKOeWbBOTib6XsTx0", "�D=\u0015��", bP);
            bO[12] = cI("xK5CmwKfxTQkNeW/3vllRhv2zzP/V0Av/4XRZ/VE4vmTnRUDoEgCiJWivH6Bt7TR", "7�h�J�", bP);
        } catch (Exception e) {

        }
    }

    public static String cI(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{31, 0, -84, 8, -8, 24, -106, -76, -119, 100, 67, -28, 115, -38, 16, 77};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 68, 256);
        SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] var8 = var7.generateSecret(var6).getEncoded();
        SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
        Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var10.init(2, var9, new IvParameterSpec(var2));
        byte[] var11 = var10.doFinal(var5);
        String info = new String(var11, "UTF-8");
        return info;
    }

    public static void cK() {
        bP = new byte[16];
        bP[8] = -1;
        bP[14] = -30;
        bP[10] = -47;
        bP[7] = 47;
        bP[0] = 27;
        bP[4] = -1;
        bP[12] = -11;
        bP[15] = -7;
        bP[13] = 68;
        bP[1] = -10;
        bP[3] = 51;
        bP[6] = 64;
        bP[2] = -49;
        bP[5] = 87;
        bP[9] = -123;
        bP[11] = 103;
    }
}
