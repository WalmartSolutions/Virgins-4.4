package bre2el.fpsreducer.feature.module.modules.visual;

import bre2el.fpsreducer.feature.module.Module;
import bre2el.fpsreducer.feature.module.Module.Category;
import bre2el.fpsreducer.feature.module.setting.KeyBindSetting;
import bre2el.fpsreducer.feature.module.setting.Setting;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

//seems like it doesn't do anything lol
public class NoInvis extends Module {
    public static String[] bc;
    public static byte[] bd;
    public KeyBindSetting bind;

    public static void bF() {
        bd = new byte[16];
        bd[2] = 32;
        bd[6] = 26;
        bd[1] = 51;
        bd[11] = 38;
        bd[5] = 101;
        bd[0] = -127;
        bd[15] = 86;
        bd[13] = -23;
        bd[7] = -112;
        bd[9] = 119;
        bd[3] = -125;
        bd[4] = 45;
        bd[14] = -2;
        bd[12] = 102;
        bd[8] = -127;
        bd[10] = -82;
    }

    public NoInvis() {
        super(bc[3], bc[4], Category.Visual);
        this.bind = new KeyBindSetting(bc[5], 0, false);
        this.addSettings(new Setting[]{this.bind});
    }

    static {
        bF();
        bE();
    }

    public static void bE() {
        try {
            bc = new String[6];
            bc[0] = bD("PsZsO8lJp5+E7j8ePiT0doEzIIMtZRqQgXeuJmbp/lY8Lem7ZVzaUvW0JhujPaKu", "m>Ȇ��", bd);
            bc[2] = bD("B/iABh6rIPPFzsXSafNARIEzIIMtZRqQgXeuJmbp/lZlKXGI1rd7tEVTkYNKB3Xcw7jeky5DqvPVm2CE9XcDJw==", "�R_b>", bd);
            bc[3] = bD("7X88+rk3aTCKigdWOTwiIoEzIIMtZRqQgXeuJmbp/lbaimZdL4vGBlwZ0DKVMvp/", "�*����", bd);
            bc[1] = bD("F/3P6L3xlc/eTzAcRY1c/YEzIIMtZRqQgXeuJmbp/laCMkd9MOT+lmxAJTvSDPBKZZV5Bk+DwFLuxjqgfqA9wA==", "\u001cK�o\u0002", bd);
            bc[4] = bD("kNisvpBwJbrOJUAk7Q4MNYEzIIMtZRqQgXeuJmbp/lbyLOu9SpxiUKLm3xy4PSSVVjA6DwdstnOrbFKp8dL9qQ==", "R1��t'", bd);
            bc[5] = bD("rKhYZK70gPbCqpTzq5S3MoEzIIMtZRqQgXeuJmbp/lYl/Ie7q6W2QyA08sQmckClWuMsGyVKe0k3zwiXvEjSEg==", "�R�.�\u0017", bd);
        } catch (Exception e) {}
    }

    public static String bD(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{36, 50, -45, 26, 23, -115, 64, 69, 0, 36, -87, 0, -65, -56, -63, -64};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 146, 256);
        SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] var8 = var7.generateSecret(var6).getEncoded();
        SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
        Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var10.init(2, var9, new IvParameterSpec(var2));
        byte[] var11 = var10.doFinal(var5);
        return new String(var11, "UTF-8");
    }
}
