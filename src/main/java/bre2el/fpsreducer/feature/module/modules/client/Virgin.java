package bre2el.fpsreducer.feature.module.modules.client;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.feature.module.Module;
import bre2el.fpsreducer.feature.module.setting.ColorPickerSetting;
import bre2el.fpsreducer.feature.module.setting.KeyBindSetting;
import bre2el.fpsreducer.feature.module.setting.Setting;
import bre2el.fpsreducer.gui.impl.ClickGUI;
import com.google.common.eventbus.Subscribe;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.awt.Color;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import static bre2el.fpsreducer.client.Main.mc;

public class Virgin extends Module {
    public ColorPickerSetting primary;
    public ColorPickerSetting background;
    public ColorPickerSetting text;
    public static byte[] bB;
    public static String[] bA;
    public KeyBindSetting bind;
    public ColorPickerSetting secondary;

    public static void co() {
        try {
            bA = new String[22];
            bA[21] = cn("HPxB/VU9sCA7ih2hhTuGWnFKX0Vq6z19I2YOdGm4UBEoxGOIoDgFIM56YgbujAFF7D1Oyn+lr+vxsU+qlLEk1Q==", "0�\u001c��", bB);
            bA[14] = cn("WU+92Cnqg2B4B5IbtPEOeXFKX0Vq6z19I2YOdGm4UBGpIUlTDDcYrE+9mjIg4u+p", "�\u001d\u0018��", bB);
            bA[6] = cn("gN9xOA955XNyXX44i/MR9HFKX0Vq6z19I2YOdGm4UBGA/i2ZTCSYoASiGEGE6wmKxO2mErDbbYvFShXxY9tbOA==", "Rn��vF", bB);
            bA[19] = cn("X7XDvzfXMn6jZTTqiHii6XFKX0Vq6z19I2YOdGm4UBHmj9pFbbeOI1Tb+5PU0pMAYyVR06/mrmD1sBZk9jTgpw==", "Ŵ6C�\u0016", bB);
            bA[5] = cn("NBoHRVjCabse6QKGAM6+UnFKX0Vq6z19I2YOdGm4UBEXYqFn12Lo/lLZ7x12YMhw", "�\u0017�\u0014\u0006�", bB);
            bA[3] = cn("ysczhF903arz5cUamnc7MnFKX0Vq6z19I2YOdGm4UBG4sqQSdZcaUpHzBKhQHLqZ", "�_�4�\u001b", bB);
            bA[15] = cn("F8D+S0cHaVfizh9YNFn5JHFKX0Vq6z19I2YOdGm4UBGFfx+vaXXYpPsJ86sBmshMfhY0Q6FIgOIFW9d7WT9Zfg==", "(I��\u0004�", bB);
            bA[0] = cn("9li2S1RQ8vdiguLZX25PtHFKX0Vq6z19I2YOdGm4UBFDp3KND634Wa68r+9w27ys", "�\u00036W~�", bB);
            bA[7] = cn("qKqrS+qtq2SrLc/TOqRMz3FKX0Vq6z19I2YOdGm4UBGBkZmYsjBVh0Too9s7K/KKvB8asFHNv3/rXFC5WEDq+w==", "���\u0013�z", bB);
            bA[4] = cn("y8GKgtYAmSQVl2h31kCJbnFKX0Vq6z19I2YOdGm4UBFr72/gInxkcAZFhk38tPDuSRTL0/nuOLVMFJft9ZTJug==", "� =��\u0012", bB);
            bA[8] = cn("lbe+l5ZdTOG5cOYrq3i9bXFKX0Vq6z19I2YOdGm4UBHDlJjBywm4lWGt8Z2CBM6ssC+xKMwFZVAOb7dqgMLd7Q==", "]�i\f��", bB);
            bA[10] = cn("NRCfMbssce45yQ7/WO1iXXFKX0Vq6z19I2YOdGm4UBEBFwgaHAfsqFUpxd+rhk19W0RnJQtqFecdTe6GG2Ae9A==", "r�\u000b��\u0004", bB);
            bA[12] = cn("HPvhIhzZNENQY2Ay7nJYtnFKX0Vq6z19I2YOdGm4UBERIFPmnQCFQzMQtBpSnH5D", "�\u00136tn�", bB);
            bA[9] = cn("FZOkM3ugmBBTTzI+vCMK/nFKX0Vq6z19I2YOdGm4UBGSPnF/jtpnErkJ4ozaeYEh", "B)�D��", bB);
            bA[1] = cn("QfI0NOfHjSIEC/Qid+K5o3FKX0Vq6z19I2YOdGm4UBHkfuqPiTw0PpI62xyxqw4Q", "W^\\�C�", bB);
            bA[2] = cn("KR/5Kvqz5kvDJZR6gCxzf3FKX0Vq6z19I2YOdGm4UBEvzvh7pNgAMlsN8K26GRIUjxseYIkO2Hr7s8Dq8YviQg==", "=�И\f�", bB);
            bA[16] = cn("dWm5XDB+yMuOfq9VGzD71XFKX0Vq6z19I2YOdGm4UBFwQfreciwTWkTtno8ntY/D", "��i��f", bB);
            bA[20] = cn("iT+K0ik8qj1bxEgawb5RU3FKX0Vq6z19I2YOdGm4UBHutThW6ZK3XyHUK/0VwO5W", "{\u0000a��g", bB);
            bA[11] = cn("y872esOy/1mY99rU4g0hhnFKX0Vq6z19I2YOdGm4UBFwkLq26T7OAS5foaBuBOie", "S��!��", bB);
            bA[18] = cn("7ky90EJsH5xArRaeE2eq7nFKX0Vq6z19I2YOdGm4UBHqPuIhHX2f+T6MtVQwivvlvXOAqXejJGOgqz/P5bIYbQ==", "B\t�o�B", bB);
            bA[13] = cn("cfSuMr9XxJRu2QKi0fqSHHFKX0Vq6z19I2YOdGm4UBEPWMt/S1m1F/UgaZycpebLdNm2wvbQP3YL+TcQ3ykTzQ==", "��N���", bB);
            bA[17] = cn("3+mdaaV04fbn7636yzJ8kXFKX0Vq6z19I2YOdGm4UBEZE1Mg3g8oXp3ccm71pnFgo43bLQfo4PqSk/JdOYQupw==", "�\u0016V\b�\u001d", bB);
        } catch (Exception e) {

        }
    }

    @Override
    public void onDisable() {
        // this is empty in the actual client
    }

    @Subscribe
    public void onTick() {
        if (mc.currentScreen == null) {
            setEnabled(false);
        }

        if (mc.currentScreen instanceof ClickGUI && GLFW.glfwGetKey(mc.getWindow().getHandle(), GLFW.GLFW_KEY_BACKSPACE) == GLFW.GLFW_PRESS) {
            mc.setScreen(null);
            setEnabled(false);
        }
    }

    public static String cn(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{52, -53, -57, 8, 123, 0, 9, 126, -63, -49, -4, -60, 52, -2, 108, -88};
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

    static {
        cp();
        co();
    }

    public Virgin() {
        super(bA[11], bA[12], Category.Client);
        this.bind = new KeyBindSetting(bA[13], 344, false);
        this.primary = new ColorPickerSetting(bA[14], bA[15], new Color(68,255,192));
        this.secondary = new ColorPickerSetting(bA[16], bA[17], new Color(68,255,220));
        this.background = new ColorPickerSetting(bA[18], bA[19], new Color(0,0,0, 255));
        this.text = new ColorPickerSetting(bA[20], bA[21], Color.WHITE);
        this.addSettings(new Setting[]{this.bind, this.primary, this.secondary});
        this.setKey(this.bind.getKey());
    }

    @Override
    public void onEnable() {
        if (mc.currentScreen == null) {
            mc.setScreen(new ClickGUI(Text.empty()));
        }
    }

    public static void cp() {
        bB = new byte[16];
        bB[8] = 35;
        bB[2] = 95;
        bB[14] = 80;
        bB[15] = 17;
        bB[1] = 74;
        bB[4] = 106;
        bB[11] = 116;
        bB[5] = -21;
        bB[10] = 14;
        bB[3] = 69;
        bB[0] = 113;
        bB[7] = 125;
        bB[13] = -72;
        bB[9] = 102;
        bB[6] = 61;
        bB[12] = 105;
    }
}
