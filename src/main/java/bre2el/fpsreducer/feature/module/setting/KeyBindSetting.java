package bre2el.fpsreducer.feature.module.setting;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class KeyBindSetting extends Setting {
    public boolean hold;
    public static String[] fO;
    public String description;
    public static byte[] fP;
    public int key;

    public void setKey(int key) {
        this.key = key;
    }

    public boolean isHold() {
        return this.hold;
    }

    static {
        iK();
        iJ();
    }

    public int getKey() {
        return this.key;
    }

    public void setHold(boolean hold) {
        this.hold = hold;
    }

    public static void iJ() {
        try {
            fO = new String[2];
            fO[0] = iI("H8xTNImx84ivFYZJXhkKbztbNMFeCf2gQhDQ4O9F9B3JW+4cj4Ik6xb6BdK5Ag3M", "�\u00150\u001b��", fP);
            fO[1] = iI("jifAg3edEThq6yZp5FLEcDtbNMFeCf2gQhDQ4O9F9B2PanISbu+4cb0+L+oJl6MI", "Z3��J\r", fP);
        } catch (Exception e) {

        }
    }

    public KeyBindSetting(String hold, int key, boolean description) {
        super(fO[1], hold);
        this.description = hold;
        this.key = key;
        this.hold = description;
    }

    public static void iK() {
        fP = new byte[16];
        fP[6] = -3;
        fP[0] = 59;
        fP[1] = 91;
        fP[8] = 66;
        fP[9] = 16;
        fP[5] = 9;
        fP[12] = -17;
        fP[3] = -63;
        fP[4] = 94;
        fP[15] = 29;
        fP[7] = -96;
        fP[10] = -48;
        fP[2] = 52;
        fP[14] = -12;
        fP[13] = 69;
        fP[11] = -32;
    }

    public static String iI(String var0, String var1, byte[] var2) throws Exception{
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{77, -108, 75, -37, -10, 24, 72, 93, 8, -17, -27, 76, 0, 44, -82, 15};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 150, 256);
        SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] var8 = var7.generateSecret(var6).getEncoded();
        SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
        Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var10.init(2, var9, new IvParameterSpec(var2));
        byte[] var11 = var10.doFinal(var5);
        String info = new String(var11, "UTF-8");
        return info;
    }
}
