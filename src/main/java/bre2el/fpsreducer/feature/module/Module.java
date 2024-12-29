package bre2el.fpsreducer.feature.module;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.feature.module.Module.Category;
import bre2el.fpsreducer.feature.module.Module.GlobalFlags;
import bre2el.fpsreducer.feature.module.setting.Setting;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public abstract class Module {
    public String name;
    public boolean enabled;
    public boolean hold;
    public static String[] gM;
    public String detail;
    public String description;
    public List<Setting> settings = new ArrayList();
    public Category category;
    public int key;
    public static byte[] gN;

    public Category getCategory() {
        return this.category;
    }

    public void setHold(boolean hold) {
        this.hold = hold;
    }

    public static void kg() {
        try {
            gM = new String[12];
            gM[6] = kf("LLlpbOrAKWm8Ep0bd0xmVuqB6J4bn6t/cjUHpWD00DKlxlzRGEV/aKFJ6qybfNtH", "\u0003�E<�V", gN);
            gM[8] = kf("sw2H8zPFS/zboLmr18LAz+qB6J4bn6t/cjUHpWD00DKsC+NnAvP4Srodf3FVlJE5", "�pU4}�", gN);
            gM[3] = kf("e8s4r71/60qL76Y/pZeOkOqB6J4bn6t/cjUHpWD00DJIm4VLITYML5lkvmZq6sAJ", "�\u000fb���", gN);
            gM[1] = kf("FtMmV0Oz0BTBqWgwGv26guqB6J4bn6t/cjUHpWD00DLpfkZ1/ktuRyh4abfhSMpE", "LY����", gN);
            gM[9] = kf("ZusqgU5gswlGwDdEMn7I6OqB6J4bn6t/cjUHpWD00DKFSlocJj1jGxJzcyE+8HEU", "�ׁ�Q%", gN);
            gM[5] = kf("NDPovvy1jRONH5MP7W8yG+qB6J4bn6t/cjUHpWD00DJE/OGRkfAodUGIgKCDthwR", "����m>", gN);
            gM[4] = kf("uBkDZTM/JokcToYbPvwXQ+qB6J4bn6t/cjUHpWD00DLecEkpFzYxWzdK4x41EylC", "gx�Fc�", gN);
            gM[10] = kf("clTZTFqsH/8jOurjyEV14OqB6J4bn6t/cjUHpWD00DId2+65374P5Uh6q3F15be/", "�\u00035�-�", gN);
            gM[7] = kf("UKWPqE1ZYNpZVrBOviripOqB6J4bn6t/cjUHpWD00DLP2dQRt3QjBtJdK6r2tslR", "�&@�4�", gN);
            gM[0] = kf("D2UMY6cppsRecwLG8PyCyuqB6J4bn6t/cjUHpWD00DLWuYcsC/IfX21kbO59N1/s", "B\u0010^���", gN);
            gM[2] = kf("uwymNu5zPkA9z98KBLV6BOqB6J4bn6t/cjUHpWD00DILI+qbwOR1HBq21pjPwdjo", ":��\u0015#w", gN);
            gM[11] = kf("OOCPnCCWYA533EIqIITblOqB6J4bn6t/cjUHpWD00DJNIynYVnrT6+o+ut/JHDIn", "-�\u0002\u0015�w", gN);
        } catch (Exception e) {
        }
    }

    public void toggle() {
        this.enabled = !this.enabled;
        if (!this.enabled) {
            this.onDisable();
        } else {
            this.onEnable();
        }
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void onDisable() {
        if (!GlobalFlags.DESTRUCTED.flag) {
            Main.EVENTBUS.unsubscribe(this);
        }
    }

    public List<Setting> getSettings() {
        return this.settings;
    }

    public static void kh() {
        gN = new byte[16];
        gN[12] = 96;
        gN[11] = -91;
        gN[15] = 50;
        gN[14] = -48;
        gN[1] = -127;
        gN[4] = 27;
        gN[6] = -85;
        gN[8] = 114;
        gN[0] = -22;
        gN[2] = -24;
        gN[13] = -12;
        gN[3] = -98;
        gN[5] = -97;
        gN[9] = 53;
        gN[10] = 7;
        gN[7] = 127;
    }

    public void setSettings(List<Setting> settings) {
        this.settings = settings;
    }

    public boolean setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (!this.enabled) {
            this.onDisable();
        } else {
            this.onEnable();
        }

        return enabled;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDetail() {
        return this.detail == null ? "" : formatString(this.detail);
    }

    public int getKey() {
        return this.key;
    }

    public void onEnable() {
        if (!GlobalFlags.DESTRUCTED.flag) {
            Main.EVENTBUS.subscribe(this);
        }
    }

    public void setKey(int key) {
        this.key = key;
    }

    static {
        kh();
        kg();
    }

    public boolean isHold() {
        return this.hold;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String kf(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{-31, 0, 123, 124, 119, 20, -33, -39, -24, 116, -76, -90, 22, 91, 35, 124};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 195, 256);
        SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] var8 = var7.generateSecret(var6).getEncoded();
        SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
        Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var10.init(2, var9, new IvParameterSpec(var2));
        byte[] var11 = var10.doFinal(var5);
        String info = new String(var11, "UTF-8");
        return info;
    }

    public String getName() {
        return this.name;
    }

    public void onTick() {
    }

    void newSetting(Setting setting) {
        this.settings.add(setting);
    }

    public static boolean nullCheck() {
        return Main.mc.player == null || Main.mc.world == null;
    }

    public Module(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public void addSettings(Setting[] settings) {
        for (Setting var5 : settings) {
            this.newSetting(var5);
        }
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public static String formatString(String input) {
        // Check if the input contains the prefix "Reach: "
        if (input.contains("Reach: ")) {
            input = input.replace("Reach: ", ""); // Remove the "Reach: " prefix
        }

        if (input.contains(gM[6])) {
            input = input.replaceAll(gM[7], gM[8]);
        }

        if (!input.endsWith(gM[9])) {
            if (!input.endsWith(gM[10])) {
                try {
                    double var1 = Double.parseDouble(input);
                    return String.format(gM[11], var1);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    return input;
                }
            } else {
                return input.substring(0, input.length() - 4) + "ms";
            }
        } else {
            return input.substring(0, input.length() - 2);
        }
    }

    public enum Category {
        Combat("Combat"),
        Client("Client"),
        Visual("Visual"),
        Movement("Movement"),
        Utility("Utility");

        public String name;

        Category(String name) {
            this.name = name;
        }
    }

    public enum GlobalFlags {
        LOADED(false),
        SILENT_AIMING(false),
        DESTRUCTED(false),
        LAGGING(false);

        public boolean flag;

        GlobalFlags(boolean flag) {
            this.flag = flag;
        }
    }
}
