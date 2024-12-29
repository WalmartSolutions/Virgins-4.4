package bre2el.fpsreducer.gui.impl;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.client.Main.Theme;
import bre2el.fpsreducer.feature.module.Module;
import bre2el.fpsreducer.feature.module.ModuleManager;
import bre2el.fpsreducer.feature.module.Module.Category;
import bre2el.fpsreducer.feature.module.setting.Setting;
import bre2el.fpsreducer.feature.module.setting.renderer.RenderSetting;
import bre2el.fpsreducer.gui.font.FontRenderers;
import bre2el.fpsreducer.gui.impl.button.ModuleButton;
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
import org.lwjgl.glfw.GLFW;

public class Frame {
    public Category category;
    public Module cursorModule;
    public static byte[] dF;
    public int width;
    public static String[] dE;
    public int offset;
    public int x;
    public int height;
    public Setting cursorSetting;
    public int index;
    public int y;
    public List<ModuleButton> buttons;
    public String searchResult = "";

    public static void fu() {
        try {
            dE = new String[2];
            dE[1] = ft("9f5taB1CMt4WMv5JAtWSKX26z8hOi6KF4fb82l4p6bHnT0VNQutbJCzuzQFU29ZF", "k8\u0017v\u0002C", dF);
            dE[0] = ft("2PIRbEdqA3oc6U8QkJvyNX26z8hOi6KF4fb82l4p6bFywtUkGYBh/pd71laBGLvF", "�a5���", dF);
        } catch (Exception e) {
        }
    }

    public void render(DrawContext context, int mouseX, int mouseY, float tickDelta) {
        RenderUtil.drawBlurredShadow(
                context.getMatrices(), (float)this.x, (float)this.y, (float)this.width, (float)(this.offset + this.index), 20, Theme.BACKGROUND
        );
        RenderUtil.renderRoundedRect(
                context.getMatrices(), (float)this.x, (float)this.y, (float)(this.x + this.width), (float)(this.y + this.offset + this.index), Theme.BACKGROUND, 8.0F
        );
        context.drawHorizontalLine(this.x, this.x + this.width - 1, this.y + 16, ColorUtil.TwoColoreffect(Theme.PRIMARY, Theme.SECONDARY, 10.0, 2.0).getRGB());
        FontRenderers.Title
                .drawCenteredString(context.getMatrices(), this.category.name, (double)((float)this.x + (float)this.width / 2.0F), (double)this.y, Theme.TEXT.getRGB());

        for (ModuleButton var6 : this.buttons) {
            var6.render(context, mouseX, mouseY, tickDelta);
        }
    }

    public void updateButtons() {
        int var1 = this.height;

        for (ModuleButton var3 : this.buttons) {
            var3.offset = var1;
            var1 += this.height;
            if (var3.extended) {
                for (RenderSetting var10000 : var3.settings) {
                    var1 += this.height;
                }
            }
        }
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (ModuleButton var7 : this.buttons) {
            var7.mouseClicked(mouseX, mouseY, button);
        }

        return false;
    }

    public static String ft(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{119, 32, 58, -22, -2, 15, 24, -101, 13, 123, 32, -86, 44, 51, -115, 0};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 171, 256);
        SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] var8 = var7.generateSecret(var6).getEncoded();
        SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
        Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var10.init(2, var9, new IvParameterSpec(var2));
        byte[] var11 = var10.doFinal(var5);
        String info = new String(var11, "UTF-8");
        return info;
    }

    public Frame(Category x, int category, int height, int width, int y) {
        this.category = x;
        this.x = category;
        this.y = height;
        this.width = width;
        this.height = y;
        this.buttons = new ArrayList();
        int var6 = y;

        for (Module var8 : ModuleManager.INSTANCE.getModulesInCategory(x)) {
            this.buttons.add(new ModuleButton(var8, this, var6));
            var6 += y;
            this.offset = var6;
        }
    }

    public boolean mouseReleased(double mouseY, double mouseX, int button) {
        for (ModuleButton var7 : this.buttons) {
            var7.mouseReleased(mouseY, mouseX, button);
        }

        return false;
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (GLFW.glfwGetKey(Main.mc.getWindow().getHandle(), 340) != 1) {
            if (verticalAmount > 0.0) {
                this.y -= 5;
            }

            if (verticalAmount < 0.0) {
                this.y += 5;
            }
        } else {
            if (verticalAmount > 0.0) {
                this.x -= 5;
            }

            if (verticalAmount < 0.0) {
                this.x += 5;
            }
        }

        return false;
    }

    static {
        fv();
        fu();
    }

    public static void fv() {
        dF = new byte[16];
        dF[10] = -4;
        dF[14] = -23;
        dF[2] = -49;
        dF[8] = -31;
        dF[9] = -10;
        dF[4] = 78;
        dF[6] = -94;
        dF[0] = 125;
        dF[13] = 41;
        dF[1] = -70;
        dF[5] = -117;
        dF[11] = -38;
        dF[15] = -79;
        dF[7] = -123;
        dF[12] = 94;
        dF[3] = -56;
    }

    public void renderSearchBar(DrawContext context) {
        float var2 = (float)Main.mc.getWindow().getScaledWidth() / 2.5F;
        float var3 = (float)Main.mc.getWindow().getScaledHeight() - (float)Main.mc.getWindow().getScaledHeight() / 10.0F;
        float var4 = (float)Main.mc.getWindow().getScaledWidth() / 5.0F;
        RenderUtil.drawBlurredShadow(context.getMatrices(), var2, var3, var4, 25.0F, 20, new Color(0, 0, 0, 200));
        RenderUtil.renderRoundedRect(context.getMatrices(), var2 - 2.0F, var3 - 2.0F, var2 + var4 + 2.0F, var3 + 25.0F + 2.0F, Theme.PRIMARY, 9.0F);
        RenderUtil.renderRoundedRect(context.getMatrices(), var2, var3, var2 + var4, var3 + 25.0F, Theme.BACKGROUND, 9.0F);
        if (!this.searchResult.isEmpty()) {
            FontRenderers.Main.drawString(context.getMatrices(), this.searchResult, var2 + 15.0F, var3 + 10.0F, Theme.TEXT.getRGB(), true);
        } else {
            FontRenderers.Main.drawString(context.getMatrices(), dE[1], var2 + 15.0F, var3 + 10.0F, Color.GRAY.getRGB(), true);
        }
    }

    public boolean keyPressed(int keyCode) {
        for (ModuleButton var3 : this.buttons) {
            var3.keyPressed(keyCode);
        }

        return false;
    }
}
