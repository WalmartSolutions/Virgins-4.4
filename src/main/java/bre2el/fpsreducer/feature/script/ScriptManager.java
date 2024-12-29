package bre2el.fpsreducer.feature.script;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import net.fabricmc.loader.api.FabricLoader;

public class ScriptManager {
    public Path scriptsPath;
    public static String[] dc;
    public static byte[] dd;
    public List<String> scripts = new ArrayList();

    public List<String> getScripts() {
        return this.scripts;
    }

    public static void eE() {
        try {
            dc = new String[4];
            dc[0] = eD("S0RqpooMWa/KaGrTz+t4yTgY14VaG5ib9ViC04w5GvKTLSb2kG3lZksJ+nrIykbT", "�����=", dd);
            dc[2] = eD("tHoxT0pDZaTkGaOXmaNPZzgY14VaG5ib9ViC04w5GvK6bBqk5pWks5LjLvg86csX", "�į\u0010F�", dd);
            dc[1] = eD("jfSWpbpQ3IQ7XVPiGXeG3zgY14VaG5ib9ViC04w5GvLc2I0XCZ4Q4Ke31/II56Ds", "�m�\u0003d\r", dd);
            dc[3] = eD("z38IfZwvWjQG6wcJfm8xCjgY14VaG5ib9ViC04w5GvLZZW4tkWqZUCG7rNKmlWbV", "�z\u0007�\t<", dd);
        } catch (Exception e) {}
    }

    static {
        eF();
        eE();
    }

    public void loadScripts() throws Exception {
        if (!Files.exists(this.scriptsPath, new LinkOption[0])) {
            Files.createDirectories(this.scriptsPath);
        }

        DirectoryStream<Path> var1 = Files.newDirectoryStream(this.scriptsPath, dc[3]);

        for (Path var3 : var1) {
            List<String> var4 = Files.readAllLines(var3);
            this.scripts.addAll(var4);
        }

        var1.close();
    }

    public static String eD(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{62, -40, -127, -44, 108, 120, -79, 121, -76, -49, -89, 114, 41, 0, 0, 53};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 72, 256);
        SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] var8 = var7.generateSecret(var6).getEncoded();
        SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
        Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var10.init(2, var9, new IvParameterSpec(var2));
        byte[] var11 = var10.doFinal(var5);
        String info = new String(var11, "UTF-8");
        return info;
    }

    public static void eF() {
        dd = new byte[16];
        dd[4] = 90;
        dd[8] = -11;
        dd[11] = -45;
        dd[3] = -123;
        dd[13] = 57;
        dd[1] = 24;
        dd[15] = -14;
        dd[12] = -116;
        dd[7] = -101;
        dd[10] = -126;
        dd[6] = -104;
        dd[5] = 27;
        dd[2] = -41;
        dd[14] = 26;
        dd[0] = 56;
        dd[9] = 88;
    }

    public ScriptManager() {
        this.scriptsPath = FabricLoader.getInstance().getConfigDir().resolve(dc[2]);
    }
}