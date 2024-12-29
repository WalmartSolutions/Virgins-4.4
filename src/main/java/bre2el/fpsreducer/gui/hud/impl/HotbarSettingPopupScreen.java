package bre2el.fpsreducer.gui.hud.impl;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.client.Main.Theme;
import bre2el.fpsreducer.gui.font.FontRenderers;
import bre2el.fpsreducer.gui.impl.ClickGUI;
import bre2el.fpsreducer.util.ColorUtil;
import bre2el.fpsreducer.util.RenderUtil;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class HotbarSettingPopupScreen extends Screen {
    public boolean h8;
    public static HotbarSettingPopupScreen INSTANCE = new HotbarSettingPopupScreen();
    public boolean h6;
    public boolean h2;
    public List<Double> slots = new ArrayList();
    public static byte[] bJ;
    public boolean h4;
    public boolean h7;
    public boolean h5;
    public boolean h1;
    public boolean h3;
    public boolean h0;
    public static String[] bI;

    public static void cB() {
        bJ = new byte[16];
        bJ[11] = -52;
        bJ[2] = -88;
        bJ[13] = -27;
        bJ[9] = 125;
        bJ[0] = 53;
        bJ[6] = 26;
        bJ[5] = -28;
        bJ[4] = -65;
        bJ[15] = -100;
        bJ[10] = -60;
        bJ[8] = 20;
        bJ[14] = 107;
        bJ[12] = 123;
        bJ[3] = 85;
        bJ[1] = 26;
        bJ[7] = -68;
    }

    int isHovered(int x, int y, double mouseX, double mouseY) {
        if (mouseX > (double)(x + 10) && mouseX < (double)(x + 20) && mouseY > (double)(y + 50 - 10) && mouseY < (double)(y + 50 - 20)) {
            return 0;
        } else if (mouseX > (double)(x + 25) && mouseX < (double)(x + 35) && mouseY > (double)(y + 50 - 10) && mouseY < (double)(y + 50 - 20)) {
            return 1;
        } else if (mouseX > (double)(x + 40) && mouseX < (double)(x + 50) && mouseY > (double)(y + 50 - 10) && mouseY < (double)(y + 50 - 20)) {
            return 2;
        } else if (mouseX > (double)(x + 55) && mouseX < (double)(x + 65) && mouseY > (double)(y + 50 - 10) && mouseY < (double)(y + 50 - 20)) {
            return 3;
        } else if (mouseX > (double)(x + 70) && mouseX < (double)(x + 80) && mouseY > (double)(y + 50 - 10) && mouseY < (double)(y + 50 - 20)) {
            return 4;
        } else if (mouseX > (double)(x + 85) && mouseX < (double)(x + 95) && mouseY > (double)(y + 50 - 10) && mouseY < (double)(y + 50 - 20)) {
            return 5;
        } else if (mouseX > (double)(x + 100) && mouseX < (double)(x + 110) && mouseY > (double)(y + 50 - 10) && mouseY < (double)(y + 50 - 20)) {
            return 6;
        } else if (mouseX > (double)(x + 115) && mouseX < (double)(x + 125) && mouseY > (double)(y + 50 - 10) && mouseY < (double)(y + 50 - 20)) {
            return 7;
        } else {
            return mouseX > (double)(x + 130) && mouseX < (double)(x + 140) && mouseY > (double)(y + 50 - 10) && mouseY < (double)(y + 50 - 20) ? 8 : -1;
        }
    }

    public HotbarSettingPopupScreen() {
        super(Text.of(""));
    }

    int isHovered2(int y, int mouseY, double mouseX, double x) {
        for (int var10 = 0; var10 < 9; var10++) {
            int var11 = y + 10 + var10 * 15;
            int var12 = var11 + 10;
            int var13 = mouseY + 50 - 20;
            int var14 = var13 + 10;
            if (mouseX > (double)var11 && mouseX < (double)var12 && x > (double)var13 && x < (double)var14) {
                return var10;
            }
        }

        return -1;
    }

    public void render(DrawContext mouseX, int context, int delta, float mouseY) {
        int var5 = (int)((float)Main.mc.getWindow().getScaledWidth() / 2.0F) - 75;
        int var6 = (int)((float)Main.mc.getWindow().getScaledHeight() / 2.0F) - 50;
        RenderUtil.drawBlurredShadow(mouseX.getMatrices(), (float)var5, (float)var6, 150.0F, 50.0F, 16, Color.BLACK);
        RenderUtil.renderRoundedRect(mouseX.getMatrices(), (float)var5, (float)var6, (float)(var5 + 150), (float)(var6 + 50), Color.BLACK, 6.0F);
        mouseX.drawHorizontalLine(var5, var5 + 150 - 1, var6 + 18, ColorUtil.TwoColoreffect(Theme.PRIMARY, Theme.SECONDARY, 10.0, 2.0).getRGB());
        FontRenderers.Main.drawCenteredString(mouseX.getMatrices(), bI[1], (double)((float)var5 + 75.0F), (double)(var6 + 4), Color.WHITE.getRGB());
        mouseX.fill(var5 + 10, var6 + 50 - 10, var5 + 20, var6 + 50 - 20, !this.h0 ? Color.GRAY.getRGB() : Theme.PRIMARY.getRGB());
        mouseX.fill(var5 + 25, var6 + 50 - 10, var5 + 35, var6 + 50 - 20, !this.h1 ? Color.GRAY.getRGB() : Theme.PRIMARY.getRGB());
        mouseX.fill(var5 + 40, var6 + 50 - 10, var5 + 50, var6 + 50 - 20, !this.h2 ? Color.GRAY.getRGB() : Theme.PRIMARY.getRGB());
        mouseX.fill(var5 + 55, var6 + 50 - 10, var5 + 65, var6 + 50 - 20, !this.h3 ? Color.GRAY.getRGB() : Theme.PRIMARY.getRGB());
        mouseX.fill(var5 + 70, var6 + 50 - 10, var5 + 80, var6 + 50 - 20, !this.h4 ? Color.GRAY.getRGB() : Theme.PRIMARY.getRGB());
        mouseX.fill(var5 + 85, var6 + 50 - 10, var5 + 95, var6 + 50 - 20, !this.h5 ? Color.GRAY.getRGB() : Theme.PRIMARY.getRGB());
        mouseX.fill(var5 + 100, var6 + 50 - 10, var5 + 110, var6 + 50 - 20, !this.h6 ? Color.GRAY.getRGB() : Theme.PRIMARY.getRGB());
        mouseX.fill(var5 + 115, var6 + 50 - 10, var5 + 125, var6 + 50 - 20, !this.h7 ? Color.GRAY.getRGB() : Theme.PRIMARY.getRGB());
        mouseX.fill(var5 + 130, var6 + 50 - 10, var5 + 140, var6 + 50 - 20, !this.h8 ? Color.GRAY.getRGB() : Theme.PRIMARY.getRGB());
        if (this.h0 && !this.slots.contains(0.0)) {
            this.slots.add(0.0);
        }

        if (this.h1 && !this.slots.contains(1.0)) {
            this.slots.add(1.0);
        }

        if (this.h2 && !this.slots.contains(2.0)) {
            this.slots.add(2.0);
        }

        if (this.h3 && !this.slots.contains(3.0)) {
            this.slots.add(3.0);
        }

        if (this.h4 && !this.slots.contains(4.0)) {
            this.slots.add(4.0);
        }

        if (this.h5 && !this.slots.contains(5.0)) {
            this.slots.add(5.0);
        }

        if (this.h6 && !this.slots.contains(6.0)) {
            this.slots.add(6.0);
        }

        if (this.h7 && !this.slots.contains(7.0)) {
            this.slots.add(7.0);
        }

        if (this.h8 && !this.slots.contains(8.0)) {
            this.slots.add(8.0);
        }

        if (!this.h0) {
            this.slots.remove(0.0);
        }

        if (!this.h1) {
            this.slots.remove(1.0);
        }

        if (!this.h2) {
            this.slots.remove(2.0);
        }

        if (!this.h3) {
            this.slots.remove(3.0);
        }

        if (!this.h4) {
            this.slots.remove(4.0);
        }

        if (!this.h5) {
            this.slots.remove(5.0);
        }

        if (!this.h6) {
            this.slots.remove(6.0);
        }

        if (!this.h7) {
            this.slots.remove(7.0);
        }

        if (!this.h8) {
            this.slots.remove(8.0);
        }

        super.render(mouseX, context, delta, mouseY);
    }

    static {
        cB();
        cA();
    }

    public static void cA() {
        try {
            bI = new String[2];
            bI[1] = cz("196ehkRhFfFzZqqYcbVl7zUaqFW/5Bq8FH3EzHvla5w8b55aRNoCtkb9M7gpjj/D", "�3�SpX", bJ);
            bI[0] = cz("RClgXpjkXqRVr3kNvZK70zUaqFW/5Bq8FH3EzHvla5yAbC5qiHLu7VZENh80soPB", "k�7鼙", bJ);
        } catch (Exception e) {

        }
    }

    public static String cz(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{22, 67, -41, 72, 13, -112, -49, -74, -29, 0, 62, -98, -84, 125, 54, 26};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 129, 256);
        SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] var8 = var7.generateSecret(var6).getEncoded();
        SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
        Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var10.init(2, var9, new IvParameterSpec(var2));
        byte[] var11 = var10.doFinal(var5);
        String info = new String(var11, "UTF-8");
        return info;
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) {
            Main.mc.setScreen(ClickGUI.INSTANCE);
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    public boolean mouseClicked(double mouseY, double mouseX, int button) {
        switch (this.isHovered2(
                (int)((float)Main.mc.getWindow().getScaledWidth() / 2.0F) - 75, (int)((float)Main.mc.getWindow().getScaledHeight() / 2.0F) - 50, mouseY, mouseX
        )) {
            case 0:
                this.h0 = !this.h0;
                break;
            case 1:
                this.h1 = !this.h1;
                break;
            case 2:
                this.h2 = !this.h2;
                break;
            case 3:
                this.h3 = !this.h3;
                break;
            case 4:
                this.h4 = !this.h4;
                break;
            case 5:
                this.h5 = !this.h5;
                break;
            case 6:
                this.h6 = !this.h6;
                break;
            case 7:
                this.h7 = !this.h7;
                break;
            case 8:
                this.h8 = !this.h8;
        }

        return super.mouseClicked(mouseY, mouseX, button);
    }

    public void method_25426() {
        if (this.h0 && !this.slots.contains(0.0)) {
            this.slots.add(0.0);
        }

        if (this.h1 && !this.slots.contains(1.0)) {
            this.slots.add(1.0);
        }

        if (this.h2 && !this.slots.contains(2.0)) {
            this.slots.add(2.0);
        }

        if (this.h3 && !this.slots.contains(3.0)) {
            this.slots.add(3.0);
        }

        if (this.h4 && !this.slots.contains(4.0)) {
            this.slots.add(4.0);
        }

        if (this.h5 && !this.slots.contains(5.0)) {
            this.slots.add(5.0);
        }

        if (this.h6 && !this.slots.contains(6.0)) {
            this.slots.add(6.0);
        }

        if (this.h7 && !this.slots.contains(7.0)) {
            this.slots.add(7.0);
        }

        if (this.h8 && !this.slots.contains(8.0)) {
            this.slots.add(8.0);
        }

        if (!this.h0) {
            this.slots.remove(0.0);
        }

        if (!this.h1) {
            this.slots.remove(1.0);
        }

        if (!this.h2) {
            this.slots.remove(2.0);
        }

        if (!this.h3) {
            this.slots.remove(3.0);
        }

        if (!this.h4) {
            this.slots.remove(4.0);
        }

        if (!this.h5) {
            this.slots.remove(5.0);
        }

        if (!this.h6) {
            this.slots.remove(6.0);
        }

        if (!this.h7) {
            this.slots.remove(7.0);
        }

        if (!this.h8) {
            this.slots.remove(8.0);
        }
    }
}
