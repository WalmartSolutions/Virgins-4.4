package bre2el.fpsreducer.feature.script;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.event.impl.RenderWorldEvent;
import bre2el.fpsreducer.event.impl.UpdateEvent.Pre;
import bre2el.fpsreducer.event.orbit.EventHandler;
import bre2el.fpsreducer.feature.module.Module;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class ScriptEventHandler {
    public static byte[] bp;
    public static String[] bo;
    public ScriptParser parser;

    static {
        bX();
        bW();
    }

    public static String bV(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{59, 0, 111, -100, 60, -92, 78, -54, -55, 75, -67, -112, 13, -126, 87, -32};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 53, 256);
        SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] var8 = var7.generateSecret(var6).getEncoded();
        SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
        Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var10.init(2, var9, new IvParameterSpec(var2));
        byte[] var11 = var10.doFinal(var5);
        String info = new String(var11, "UTF-8");
        return info;
    }

    public static void bW() {
        bo = new String[4];
        try {
            bo[3] = bV("mwZMXJJqfjNGdOVhWT+2lkctqioNntzMQ8dQMX6oYM9zRNAViz/0nWGN7lFfsdYl", "\\W&��`", bp);
            bo[1] = bV("ZPhvvxYCbBcjpOm1Zi66rkctqioNntzMQ8dQMX6oYM+1tQPonF2FK92oO7mP5SPj", "�/\u000e�e\u001d", bp);
            bo[2] = bV("ZqCDA05sYOaY+hmMuOusV0ctqioNntzMQ8dQMX6oYM+Dz7OQUySC+vRljp3F+Hqs", "\"nN\u001c?`", bp);
            bo[0] = bV("hIloHCVd6/mey1jk7e2QjkctqioNntzMQ8dQMX6oYM/xEIGQdjvY17EG3h9+M3Nl", "X�\u007f�l�", bp);
        } catch (Exception e) {

        }
    }

    public static void bX() {
        bp = new byte[16];
        bp[2] = -86;
        bp[6] = -36;
        bp[7] = -52;
        bp[5] = -98;
        bp[11] = 49;
        bp[15] = -49;
        bp[14] = 96;
        bp[9] = -57;
        bp[12] = 126;
        bp[4] = 13;
        bp[0] = 71;
        bp[1] = 45;
        bp[10] = 80;
        bp[13] = -88;
        bp[8] = 67;
        bp[3] = 42;
    }

    @EventHandler
    public void registerTick(Pre event) {
        if (!Module.nullCheck()) {
            this.parser.triggerEvent(bo[2]);
        }
    }

    public ScriptEventHandler(ScriptParser parser) {
        this.parser = parser;
        Main.EVENTBUS.subscribe(this);
    }

    @EventHandler
    public void registerRender(RenderWorldEvent event) {
        if (!Module.nullCheck()) {
            this.parser.triggerEvent(bo[3]);
        }
    }
}