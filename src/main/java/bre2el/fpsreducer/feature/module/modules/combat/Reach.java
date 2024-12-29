package bre2el.fpsreducer.feature.module.modules.combat;

import bre2el.fpsreducer.event.impl.UpdateEvent.Pre;
import bre2el.fpsreducer.event.orbit.EventHandler;
import bre2el.fpsreducer.feature.module.Module;
import bre2el.fpsreducer.feature.module.Module.Category;
import bre2el.fpsreducer.feature.module.setting.BoolSetting;
import bre2el.fpsreducer.feature.module.setting.KeyBindSetting;
import bre2el.fpsreducer.feature.module.setting.NumberSetting;
import bre2el.fpsreducer.feature.module.setting.Setting;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class Reach extends Module {
    public static String[] ds;
    public BoolSetting weapon;
    public static byte[] dt;
    public KeyBindSetting bind;
    public NumberSetting hitbox;
    public NumberSetting reach;

    public Reach() {
        super(ds[9], ds[10], Category.Combat);
        this.bind = new KeyBindSetting(ds[11], 0, false);
        this.reach = new NumberSetting(ds[12], ds[13], 3.0, 3.0, 6.0, 0.01);
        this.hitbox = new NumberSetting(ds[14], ds[15], 0.0, 0.0, 1.0, 0.01);
        this.weapon = new BoolSetting(ds[16], ds[17], false);
        this.addSettings(new Setting[]{this.bind, this.reach, this.hitbox, this.weapon});
    }

    @EventHandler
    void onUpdate(Pre event) {
        if (!nullCheck()) {
            this.setDetail(this.reach.getDefaultValue() != 3.0 ? String.valueOf(this.reach.getDefaultValue()) : "");
        }
    }

    public static void fd() {
        dt = new byte[16];
        dt[1] = 43;
        dt[3] = 85;
        dt[13] = 110;
        dt[8] = 107;
        dt[2] = -63;
        dt[0] = 80;
        dt[12] = -72;
        dt[15] = -79;
        dt[5] = 50;
        dt[10] = -70;
        dt[4] = -85;
        dt[9] = 12;
        dt[11] = -45;
        dt[7] = 96;
        dt[6] = -106;
        dt[14] = 54;
    }

    public static String fb(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{0, 117, 115, -38, -35, -42, 14, -3, 17, -32, 91, 25, 83, -42, -2, -101};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 159, 256);
        SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] var8 = var7.generateSecret(var6).getEncoded();
        SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
        Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var10.init(2, var9, new IvParameterSpec(var2));
        byte[] var11 = var10.doFinal(var5);
        String info = new String(var11, "UTF-8");
        return info;
    }

    static {
        fd();
        fc();
    }

    public static void fc() {
        try {
            ds = new String[18];
            ds[16] = fb("WPEAiRHOXMovXWPVnKv4N1ArwVWrMpZgawy607huNrFcG9J6aC+UTPbKdUkW7LGu", "\u001f�V`�=", dt);
            ds[4] = fb("z5kOsgnBCLvhrV7uwfHHjFArwVWrMpZgawy607huNrENNCyAeAewP1NClCPZAe5f", "CX���o", dt);
            ds[11] = fb("5Ig9G08PoUiTk2GvnWnR+1ArwVWrMpZgawy607huNrFenB6iahUhBLK0NAzNY4PH6TYRAT8TRzvZvDAMFkAAyA==", "�8�'��", dt);
            ds[17] = fb("NtlkQScAiX9VLvNpZX6B6lArwVWrMpZgawy607huNrGDwRn566gjizQ1G7/AEHuhBndsjIgU2GWyOEiqPInMjx0TQKlf8VGMmlyXgAOQ2fo=", "YM��2�", dt);
            ds[0] = fb("sjHpAjZEFTXdBfTYChF2+FArwVWrMpZgawy607huNrEICPGDVAeXSf6hgpEde8kH", "�w�PB8", dt);
            ds[6] = fb("4ue8Z96YKwD+2IYZsOpYGlArwVWrMpZgawy607huNrE7MbhF8Mfpz3IPgsqRNFO0", "?\u001f=�H�", dt);
            ds[8] = fb("ho2yhZMy2EbmEzuVmLaLrlArwVWrMpZgawy607huNrGuYvPej440D5UArO/vJSMLz/CaS+hrqPyXU0m5ieyRLukpR4TOdoIsNZOtIhWE/Bw=", "�\u001d��T�", dt);
            ds[1] = fb("w1CCRpEwUaUN69TwOMzAbFArwVWrMpZgawy607huNrHqq09R+KFHR8t9paJnfAwVAk3iagQqUI1JVTBYeWaqRw==", ">��\u0001�I", dt);
            ds[14] = fb("jU/S+21nXzu07yiAhwMDQlArwVWrMpZgawy607huNrE4Mwf3Q2OC522SKe/1ViwE", "l���6�", dt);
            ds[15] = fb("WRIufC119KHeQRPF9BpLt1ArwVWrMpZgawy607huNrHgGVpd4f7AIpFCrnbYaUTx", "\rQ\u0004�X�", dt);
            ds[12] = fb("C2rvkS3VgV2GcM9HdSkvylArwVWrMpZgawy607huNrEeQPLVdQ2r14X+hhyPuAO+", "��\u0019�n\"", dt);
            ds[10] = fb("XYeNjNNpjj9idtHWcgsMI1ArwVWrMpZgawy607huNrGTzkJyhnF+atjYAuvvM+MeqL+Gg53RQV1NbvgTqM177A==", "������", dt);
            ds[3] = fb("C03ZAcZM6nJg/KiYVHudAVArwVWrMpZgawy607huNrHWncfBw7Ocdmv9ekWyU+eY", "�\u0016��\u0012F", dt);
            ds[5] = fb("+AEpSelxDhqqfMpVR/EdLlArwVWrMpZgawy607huNrFAoucaEI33/vmY+QXJSqXG", "Tk\u0002�V�", dt);
            ds[2] = fb("vsJI8sJh38bxkWgAM4f/E1ArwVWrMpZgawy607huNrER6mgcX7RnrxUxx+GSMakBQ9aUzUMxE+ed9wHKrFVHAg==", "r�\u0016u�@", dt);
            ds[13] = fb("3A51l4ogKPyx9t0PRvlw3lArwVWrMpZgawy607huNrENIFncpxP47cj14abmXPaT", "�4�i�]", dt);
            ds[7] = fb("bqw0UGGB9WKdg7dUnVtrK1ArwVWrMpZgawy607huNrH1R42prwLvTyu+LKINCK9B", "B\\\u000bT�\u0012", dt);
            ds[9] = fb("Hb3VT236W2Vq07fgolopolArwVWrMpZgawy607huNrFW2fU6DpoMv6hOvepgx5CB", "�\u0015ԏW�", dt);
        } catch (Exception e) {

        }
    }
}
