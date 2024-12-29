package bre2el.fpsreducer.feature.module.setting.renderer.impl;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.client.Main.Theme;
import bre2el.fpsreducer.event.impl.MouseButtonEvent;
import bre2el.fpsreducer.event.orbit.EventHandler;
import bre2el.fpsreducer.feature.module.setting.ItemSetting;
import bre2el.fpsreducer.feature.module.setting.Setting;
import bre2el.fpsreducer.feature.module.setting.renderer.RenderSetting;
import bre2el.fpsreducer.gui.font.FontRenderers;
import bre2el.fpsreducer.gui.impl.button.ModuleButton;
import bre2el.fpsreducer.util.RenderUtil;
import java.awt.Color;
import java.util.Base64;
import java.util.Locale;
import java.util.Objects;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.Item;

public class ItemBox extends RenderSetting {
    public String arg = "";
    public static String[] ga;
    public static byte[] gb;
    public boolean listening;
    public ItemSetting itemSetting;
    public int count;
    public boolean clicking;
    public int index;
    public Setting setting;

    public ItemBox(Setting parent, ModuleButton setting, int offset) {
        super(parent, setting, offset);
        this.setting = parent;
        this.itemSetting = (ItemSetting)parent;
        Main.EVENTBUS.subscribe(this);
    }

    public static void jc() {
        gb = new byte[16];
        gb[2] = -118;
        gb[5] = 110;
        gb[15] = 17;
        gb[6] = 39;
        gb[3] = 58;
        gb[13] = -53;
        gb[11] = 113;
        gb[14] = -47;
        gb[12] = 23;
        gb[10] = 126;
        gb[7] = 92;
        gb[4] = 31;
        gb[8] = 96;
        gb[0] = -126;
        gb[9] = 108;
        gb[1] = 58;
    }

    @Override
    public void keyPressed(int key) {
        if (key == 257 || key == 256) {
            this.listening = false;
        }

        if (this.listening && this.arg.length() < 12 && (key >= 48 && key <= 90 || key == 32)) {
            this.arg = this.arg + String.valueOf((char)key).toLowerCase(Locale.ROOT);
        }

        if (key == 259 && this.listening && !this.arg.isEmpty()) {
            this.arg = this.arg.substring(0, this.arg.length() - 1);
        }

        this.itemSetting.setSearchResult(this.arg);
        super.keyPressed(key);
    }

    public static void jb() {
        try {
            ga = new String[36];
            ga[13] = ja("OzfyN5euhURoXfSM4v87ToI6ijofbidcYGx+cRfL0REKqyaThmf26Rog7u9Iv5wK", "_o&\u001cЬ", gb);
            ga[31] = ja("WGiPrQ4QuR38lLjn4sDHWII6ijofbidcYGx+cRfL0RE0I+2ESACVmrtVjl9zmfum", "\u007f���\u0006X", gb);
            ga[3] = ja("coNa8Viq8T13CQBawKNSL4I6ijofbidcYGx+cRfL0REo2lzYMR60/z/HImVmKEVs", "�^\u0003\u000eq�", gb);
            ga[9] = ja("eCFEYd1MhqqjkJ9s1GsO4YI6ijofbidcYGx+cRfL0RH3vnvDThxIBPJ1ZJqK+o/X", "}��w�\u0004", gb);
            ga[12] = ja("CQ6Gz6VPhCXXwl64j3T0KII6ijofbidcYGx+cRfL0RHqtsp7iTyZWJrP7gg1u7f/", "���柸", gb);
            ga[25] = ja("Mr7jaJLuTfLO+vvlbgpPIII6ijofbidcYGx+cRfL0RHdZPKVqyQlepcmjE9A57GZ", "?��E\u0000 ", gb);
            ga[34] = ja("dlscNUGNqJQP6q8kjGGTFII6ijofbidcYGx+cRfL0RHSS63CHJU1YdIHb519eZ/9", "\u0013�P0m", gb);
            ga[24] = ja("9z9CFGK5xICjRwKSdz7InoI6ijofbidcYGx+cRfL0RH8pNMXX0vectvzmAYhnTlX", "%-r��q", gb);
            ga[2] = ja("YOpHs0w9hQ9YIEqGto0wUoI6ijofbidcYGx+cRfL0REVkMpgE/9iW8aAYBqD3vrX", "�Y�A�", gb);
            ga[1] = ja("nV+phbw29UXUk+2vx+aSroI6ijofbidcYGx+cRfL0RHMCs03G3AnRiLdmSjGYKlI", "�~`�VH", gb);
            ga[14] = ja("1EJdcIJwThuYGLr/pYBG5oI6ijofbidcYGx+cRfL0RHF5fj2GV69gDu0V6SZ2HpX", "���t��", gb);
            ga[16] = ja("8Dn54ye7wqwQtzBVgAi+D4I6ijofbidcYGx+cRfL0RH6agvrBa0MbWeZKxUC2ekz", "\u007f\b��\t�", gb);
            ga[20] = ja("D5lQmo3xjmy1p+FcSnDz6II6ijofbidcYGx+cRfL0RGT45xQ1xI0dNdEQy5p0EfN", "���G�5", gb);
            ga[23] = ja("krZUcakCrC9xeRS/9QtA34I6ijofbidcYGx+cRfL0RGOcryyyN9U/m28lwp2ltd4", "��7�5�", gb);
            ga[32] = ja("ER4CEmVTfqtBFzICfiiXdII6ijofbidcYGx+cRfL0RFy+ul4oEWBQ1n8vQYO8ILw", "]H��^", gb);
            ga[18] = ja("KtDqe8zY9Tx9yX9yVkaOBII6ijofbidcYGx+cRfL0RHqqJM8ry7BljsS7CUk6B1h", "�Xҫ��", gb);
            ga[27] = ja("xwjoRKZtJipsFtVctJTV9YI6ijofbidcYGx+cRfL0RHkgqKs/HqROg6UG5sxUpPf", "\u0018Y�\u0002/M", gb);
            ga[26] = ja("BDii6ilFtAnH4mN7OatbaoI6ijofbidcYGx+cRfL0RGkb6LkMyKdpSv8eGyuWwg/", "B^i��$", gb);
            ga[15] = ja("sUe1gJgSdfk6rFFltDP9yoI6ijofbidcYGx+cRfL0RHnUURyhwm7E+ME+NC6jers", "p�Nӳ>", gb);
            ga[7] = ja("JzcszEubQvpBYvqUkt5s54I6ijofbidcYGx+cRfL0RE+JMqpHdeKwXkC5Kk+fMxR", "������", gb);
            ga[33] = ja("SdCbFpCNPRQHggS35tp7r4I6ijofbidcYGx+cRfL0RF5/kylmjC6Ch9RbOejfJ59", "��@�.'", gb);
            ga[22] = ja("V+xMC8i2qUcnlTSrcPW8NYI6ijofbidcYGx+cRfL0RFShXJd+K3Ewuz10MYTqA4Z", "�c\n�\u0002�", gb);
            ga[8] = ja("z5qQHLnrKP5bg3oX88oQJoI6ijofbidcYGx+cRfL0RETH65vEoDTh6IIwORaTEAt", "1�qu��", gb);
            ga[4] = ja("GzO2oBJ7UuKLQm0KrazFqYI6ijofbidcYGx+cRfL0REIlpezGDFozTOf3SAmGt/l", "\u0004��6L�", gb);
            ga[28] = ja("ziN9E0sEfIdzotByo5FiSoI6ijofbidcYGx+cRfL0RHMXkfUadB4Vsr3xhebGtSS", "ϕ��cz", gb);
            ga[21] = ja("QPhJv2vDsowZbKi8IZodEII6ijofbidcYGx+cRfL0RF4neZuu8O527dCNzhxpY7K", ";�\\�\u0014P", gb);
            ga[5] = ja("xGtVCsE5DNbmWnLcKQftaoI6ijofbidcYGx+cRfL0RGS8+yU62PrL5eGlBfSOQLm", "�\u0019uS��", gb);
            ga[17] = ja("mzRNidHLbL/RmjjcT0fR84I6ijofbidcYGx+cRfL0RHwl02T6JDdJ6ZIVxUtwLWy", "�>Z$~�", gb);
            ga[35] = ja("phFy7ZViGxLIWxH2bAHejoI6ijofbidcYGx+cRfL0RGZt8B7R1d9+Q5YL1hvIjiG", "ܬ\u0001�\u001dj", gb);
            ga[29] = ja("R+6M7K3gclXD7SXjsk3vx4I6ijofbidcYGx+cRfL0RFcDhsB0NyJhpJmQrVYFWMb", "\u001b$\u007f8��", gb);
            ga[6] = ja("hE0cXW3EaCYbH9wsTQfZH4I6ijofbidcYGx+cRfL0RESAtOfsGeMD7NA/QrRDa5I", "�\"�\u001c�T", gb);
            ga[30] = ja("F1+a9x6FP5lSnyZFcA7He4I6ijofbidcYGx+cRfL0RH7Wf/ugrxZ0w57UKCfFzb/", "�\u0013��B=", gb);
            ga[11] = ja("U0fS7ENfEL7D9lIa2woHTII6ijofbidcYGx+cRfL0RGxfeS9wUWGo/FySpC1s8Cm", "�\\R��", gb);
            ga[19] = ja("6qs0NOw6XVZIMwfZyznwzII6ijofbidcYGx+cRfL0RGheFi5ucliMUSECYCTi4P/", "x$v��4", gb);
            ga[0] = ja("Fbzq6hC73Rvdo/UJuwdRvII6ijofbidcYGx+cRfL0RFYkq849ICmWijrN5WNUk+S", "�\u0007�;g\u0017", gb);
            ga[10] = ja("Rtd4kLqr2Uzwt0y0s3NjOII6ijofbidcYGx+cRfL0RG5WhH9NzPK5NhTV+PXXMoV", "��=�)3", gb);
        } catch (Exception e) {

        }
    }

    @Override
    public void mouseClicked(double button, double mouseX, int mouseY) {
        if (this.parent.extended) {
            if (mouseY != 0) {
                if (mouseY == 1 && this.isHovered((int)button, (int)mouseX)) {
                    this.itemSetting.setExtended(!this.itemSetting.isExtended());
                }
            } else {
                if (this.isHovered((int)button, (int)mouseX)) {
                    this.listening = !this.listening;
                }

                if (this.itemSetting.isExtended()) {
                    for (Item var7 : this.itemSetting.getItems()) {
                        if (this.isHoveredSearch((int)button, (int)mouseX, this.count)
                                && var7.toString()
                                .replaceAll(ga[32], ga[33])
                                .replaceAll(ga[34], ga[35])
                                .toLowerCase()
                                .contains(this.itemSetting.getSearchResult().toLowerCase())) {
                            if (this.itemSetting.getSelected().contains(var7)) {
                                this.itemSetting.getSelected().remove(var7);
                                this.clicking = true;
                            } else {
                                this.itemSetting.getSelected().add(var7);
                                this.clicking = true;
                            }
                        }
                    }
                }
            }

            super.mouseClicked(button, mouseX, mouseY);
        }
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX > this.parent.frame.x + 10
                && mouseX < this.parent.frame.x + this.parent.frame.width - 10
                && mouseY > this.parent.frame.y + this.parent.offset + this.parent.settings.indexOf(this) * 20 + 10
                && mouseY < this.parent.frame.y + this.parent.offset + this.parent.settings.indexOf(this) * 20 + 20;
    }

    public boolean isHoveredSearch(int mouseX, int count, int mouseY) {
        return mouseX > this.parent.frame.x + 50
                && mouseX < this.parent.frame.x + this.parent.frame.x + 100
                && count > this.parent.frame.y + this.parent.offset + this.parent.settings.indexOf(this) * 20 + 8 + mouseY
                && count < this.parent.frame.y + this.parent.offset + this.parent.settings.indexOf(this) * 20 + 16 + mouseY;
    }

    @EventHandler
    void onMouseButton(MouseButtonEvent event) {
        if (this.clicking && event.button == 0) {
            this.clicking = false;
            event.cancel();
        }
    }

    @Override
    public void render(DrawContext delta, int context, int mouseY, float mouseX) {
        if (this.parent.extended) {
            if (this.isHovered(context, mouseY)) {
                this.parent.frame.cursorSetting = this.setting;
            }

            this.count = 0;
            this.index = 0;
            FontRenderers.Sub
                    .drawString(
                            delta.getMatrices(),
                            this.setting.getName(),
                            (float)(this.parent.frame.x + 22),
                            (float)(this.parent.frame.y + this.parent.frame.height + this.parent.offset + this.parent.settings.indexOf(this) * 20 - 6),
                            Color.WHITE.getRGB(),
                            true
                    );
            if (!this.itemSetting.isExtended()) {
                RenderUtil.renderRoundedRect(
                        delta.getMatrices(),
                        (float)(this.parent.frame.x + 50),
                        (float)(this.parent.frame.y + this.parent.offset + this.parent.settings.indexOf(this) * 20 + 8),
                        (float)(this.parent.frame.x + 100),
                        (float)(this.parent.frame.y + this.parent.offset + this.parent.settings.indexOf(this) * 20 + 22),
                        Theme.PRIMARY,
                        2.0F
                );
                FontRenderers.Small
                        .drawString(
                                delta.getMatrices(),
                                !Objects.equals(this.itemSetting.getSearchResult(), "") ? this.itemSetting.getSearchResult().toLowerCase() : ga[18],
                                (float)(this.parent.frame.x + 54),
                                (float)(this.parent.frame.y + this.parent.frame.height + this.parent.offset + this.parent.settings.indexOf(this) * 20 - 6),
                                Color.WHITE.getRGB(),
                                true
                        );
            } else {
                for (Item var6 : this.itemSetting.getItems()) {
                    if (var6 == null) {
                        return;
                    }

                    if (this.index < 12
                            && var6.toString()
                            .replaceAll(ga[19], ga[20])
                            .replaceAll(ga[21], ga[22])
                            .toLowerCase()
                            .contains(this.itemSetting.getSearchResult().toLowerCase())) {
                        this.index += 12;
                    }
                }

                RenderUtil.renderRoundedRect(
                        delta.getMatrices(),
                        (float)(this.parent.frame.x + 50),
                        (float)(this.parent.frame.y + this.parent.offset + this.parent.settings.indexOf(this) * 20 + 8),
                        (float)(this.parent.frame.x + 100),
                        (float)(this.parent.frame.y + this.parent.offset + this.parent.settings.indexOf(this) * 20 + 22 + this.index),
                        Theme.PRIMARY,
                        2.0F
                );
                FontRenderers.Small
                        .drawString(
                                delta.getMatrices(),
                                !this.itemSetting.getSearchResult().isEmpty() ? this.itemSetting.getSearchResult().toLowerCase() : ga[23],
                                (float)(this.parent.frame.x + 54),
                                (float)(this.parent.frame.y + this.parent.frame.height + this.parent.offset + this.parent.settings.indexOf(this) * 20 - 6),
                                Color.WHITE.getRGB(),
                                true
                        );

                for (Item var8 : this.itemSetting.getItems()) {
                    if (var8 == null) {
                        return;
                    }

                    if (this.count < 12
                            && var8.toString()
                            .replaceAll(ga[24], ga[25])
                            .replaceAll(ga[26], ga[27])
                            .toLowerCase()
                            .contains(this.itemSetting.getSearchResult().toLowerCase())) {
                        this.count += 12;
                        FontRenderers.Small
                                .drawString(
                                        delta.getMatrices(),
                                        var8.toString().replaceAll(ga[28], ga[29]).replaceAll(ga[30], ga[31]),
                                        (float)(this.parent.frame.x + 54),
                                        (float)(this.parent.frame.y + this.parent.frame.height + this.parent.offset + this.parent.settings.indexOf(this) * 20 - 6 + this.count),
                                        !this.itemSetting.getSelected().contains(var8) ? Color.LIGHT_GRAY.getRGB() : Color.WHITE.getRGB(),
                                        true
                                );
                    }

                    if (this.count >= 48) {
                        break;
                    }
                }
            }

            super.render(delta, context, mouseY, mouseX);
        }
    }

    public static String ja(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{-23, -124, -13, -118, -102, -101, -47, 68, -86, 27, 14, 67, 91, 0, -1, -58};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 91, 256);
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
        jc();
        jb();
    }
}
