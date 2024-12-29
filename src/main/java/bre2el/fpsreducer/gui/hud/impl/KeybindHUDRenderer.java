package bre2el.fpsreducer.gui.hud.impl;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.client.Main.Theme;
import bre2el.fpsreducer.feature.module.Module;
import bre2el.fpsreducer.feature.module.ModuleManager;
import bre2el.fpsreducer.gui.font.FontRenderers;
import bre2el.fpsreducer.util.ColorUtil;
import bre2el.fpsreducer.util.InputUtil;
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
import net.minecraft.client.gui.Drawable;
import org.lwjgl.glfw.GLFW;

public class KeybindHUDRenderer implements Drawable {
    public int height;
    public List<Module> modules = new ArrayList();
    public int width;
    public int x = 0;
    public int y = 0;
    public static String[] fC;
    public static byte[] fD;
    public int dragY;
    public int dragX;
    public boolean dragging;
    public static KeybindHUDRenderer INSTANCE = new KeybindHUDRenderer();

    static {
        is();
        ir();
    }

    public void setPos(int y, int x) {
        this.x = y;
        this.y = x;
    }

    public static void is() {
        fD = new byte[16];
        fD[11] = 70;
        fD[10] = -21;
        fD[2] = 69;
        fD[1] = -87;
        fD[5] = 108;
        fD[8] = 51;
        fD[7] = -25;
        fD[0] = 89;
        fD[13] = -104;
        fD[9] = 35;
        fD[14] = 22;
        fD[6] = -27;
        fD[15] = 46;
        fD[4] = -116;
        fD[3] = -108;
        fD[12] = -18;
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX > this.x && mouseX < this.x + this.width && mouseY > this.y && mouseY < this.y + this.height + this.modules.size() * 12;
    }

    public static String iq(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{107, 2, -88, 111, -96, -59, 20, -99, 0, -97, 77, 105, 34, -119, 2, -91};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 157, 256);
        SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] var8 = var7.generateSecret(var6).getEncoded();
        SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
        Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var10.init(2, var9, new IvParameterSpec(var2));
        byte[] var11 = var10.doFinal(var5);
        String info = new String(var11, "UTF-8");
        return info;
    }

    public void renderHUD(DrawContext context) {
        int var2 = 20;
        RenderUtil.drawBlurredShadow(
                context.getMatrices(), (float)this.x, (float)this.y, (float)this.width, (float)(this.height + this.modules.size() * 12), 16, Color.BLACK
        );
        RenderUtil.renderRoundedRect(
                context.getMatrices(),
                (float)this.x,
                (float)this.y,
                (float)(this.x + this.width),
                (float)(this.y + this.height + this.modules.size() * 12),
                Color.BLACK,
                6.0F
        );
        context.drawHorizontalLine(this.x, this.x + this.width - 1, this.y + 18, ColorUtil.TwoColoreffect(Theme.PRIMARY, Theme.SECONDARY, 10.0, 2.0).getRGB());
        FontRenderers.Main
                .drawCenteredString(context.getMatrices(), fC[3], (double)((float)this.x + (float)this.width / 2.0F), (double)(this.y + 4), Color.WHITE.getRGB());

        for (Module var4 : ModuleManager.INSTANCE.getModules()) {
            if (var4.getKey() != 0 && !var4.isEnabled()) {
                if (!this.modules.contains(var4)) {
                    this.modules.add(var4);
                }

                FontRenderers.Sub
                        .drawString(
                                context.getMatrices(), var4.getName(), (float)(this.x + 5), (float)this.y + (float)this.height / 10.0F + (float)var2, Color.WHITE.getRGB()
                        );
                FontRenderers.Sub
                        .drawString(
                                context.getMatrices(),
                                "[" + InputUtil.getKeyString(var4.getKey()) + "]" + (!var4.isHold() ? "" : fC[4]),
                                (float)(this.x - 5 + this.width)
                                        - FontRenderers.Sub.getStringWidth("[" + InputUtil.getKeyString(var4.getKey()) + "]" + (!var4.isHold() ? "" : fC[5])),
                                (float)this.y + (float)this.height / 10.0F + (float)var2,
                                Color.WHITE.getRGB()
                        );
                var2 += 12;
            } else {
                this.modules.remove(var4);
            }
        }
    }

    public static void ir() {
        try {
            fC = new String[6];
            fC[0] = iq("zqi9JgilUBeWLPJQXJ2Wb1mpRZSMbOXnMyPrRu6YFi5i9aIXH28XmZwYoAuqEMtm", ")�\u000by;@", fD);
            fC[5] = iq("r0zS47Lk/v6oVbwegf13UlmpRZSMbOXnMyPrRu6YFi5U+P8cIGB8GW1ctGvWBDRD", "`\n�{��", fD);
            fC[4] = iq("fKuv3fEbeBUVlXVIJUYTdVmpRZSMbOXnMyPrRu6YFi5EHXUpvmS4VHLdv+0yRXz2", "y����{", fD);
            fC[1] = iq("T7ibZgXFan89Ddj8YlQlAFmpRZSMbOXnMyPrRu6YFi6Nt7SP6vCSiflix99tzlnP", "�\u001c���", fD);
            fC[2] = iq("tLgdp72ZDjl6QyRHhsjpJlmpRZSMbOXnMyPrRu6YFi4Z7VEiCFhzROuuakkdwC/1", "��-\\;\r", fD);
            fC[3] = iq("huaWjMQZS6zotVF/xYdqJFmpRZSMbOXnMyPrRu6YFi70Q5UUMSc2cZZMQb4yuWt/", "�~E\u0000�", fD);
        } catch (Exception e) {

        }
    }

    public KeybindHUDRenderer() {
        this.width = 180;
        this.height = 30;
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (this.isHovered(mouseX, mouseY) && GLFW.glfwGetMouseButton(Main.mc.getWindow().getHandle(), 0) == 1) {
            this.dragging = true;
            this.dragX = mouseX - this.x;
            this.dragY = mouseY - this.y;
        }

        if (this.dragging) {
            this.x = mouseX - this.dragX;
            this.y = mouseY - this.dragY;
        }

        if (GLFW.glfwGetMouseButton(Main.mc.getWindow().getHandle(), 0) == 0) {
            this.dragging = false;
        }
    }
}
