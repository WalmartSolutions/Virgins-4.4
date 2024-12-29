package bre2el.fpsreducer.feature.module.setting.renderer.impl;

import bre2el.fpsreducer.feature.module.setting.KeyBindSetting;
import bre2el.fpsreducer.feature.module.setting.Setting;
import bre2el.fpsreducer.feature.module.setting.renderer.RenderSetting;
import bre2el.fpsreducer.gui.font.FontRenderers;
import bre2el.fpsreducer.gui.impl.button.ModuleButton;
import bre2el.fpsreducer.util.InputUtil;
import java.awt.Color;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import net.minecraft.client.gui.DrawContext;

public class KeyBind extends RenderSetting {
    public static byte[] dD;
    public KeyBindSetting keyBindSetting;
    public static String[] dC;
    public Setting setting;
    public boolean listening;
    public int key;

    static {
        fs();
        fr();
    }

    @Override
    public void mouseClicked(double button, double mouseY, int mouseX) {
        if (this.parent.extended) {
            if (this.isHovered((int)button, (int)mouseY)) {
                if (!this.listening && mouseX == 1) {
                    this.parent.module.setHold(!this.keyBindSetting.isHold());
                    this.keyBindSetting.setHold(!this.keyBindSetting.isHold());
                    return;
                }

                if (mouseX == 0) {
                    this.listening = !this.listening;
                }
            }

            super.mouseClicked(button, mouseY, mouseX);
        }
    }

    public KeyBind(Setting setting, ModuleButton offset, int parent) {
        super(setting, offset, parent);
        this.setting = setting;
        this.keyBindSetting = (KeyBindSetting)setting;
    }

    public static void fs() {
        dD = new byte[16];
        dD[13] = 87;
        dD[14] = -56;
        dD[9] = -18;
        dD[2] = 27;
        dD[11] = -85;
        dD[15] = -55;
        dD[4] = -90;
        dD[3] = 10;
        dD[10] = -105;
        dD[5] = 116;
        dD[6] = -15;
        dD[7] = 100;
        dD[12] = -68;
        dD[0] = 11;
        dD[8] = -107;
        dD[1] = -1;
    }

    public static String fq(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{20, -72, 0, 37, 16, 1, 104, -34, 51, 57, -1, -123, -60, -123, -51, 120};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 167, 256);
        SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] var8 = var7.generateSecret(var6).getEncoded();
        SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
        Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var10.init(2, var9, new IvParameterSpec(var2));
        byte[] var11 = var10.doFinal(var5);
        String info = new String(var11, "UTF-8");
        return info;
    }

    public static void fr() {
        try {
            dC = new String[8];
            dC[6] = fq("CLkxB5dCJHM2Jsnzs0JO2Qv/GwqmdPFkle6Xq7xXyMk9T/nQ1UxfTYGmq2HWDLPo", "�8!NW�", dD);
            dC[4] = fq("JY7XWMtC4oBhydRz/IDEbQv/GwqmdPFkle6Xq7xXyMl7+qSpOn3qo2X7s3c5J/bq", "/45���", dD);
            dC[0] = fq("46C7mJ1EJQa7fsk5KhSIKgv/GwqmdPFkle6Xq7xXyMmQt+i6v6YTfSeKdzGjwehQ", "����\u001cT", dD);
            dC[7] = fq("1RRMlQLCKGdwmgYvlsyoXwv/GwqmdPFkle6Xq7xXyMkHCdzGm7br7zvmpNkv1wnY", "-����]", dD);
            dC[5] = fq("JuW/Mw6ae3iPx82BE1Ga9Qv/GwqmdPFkle6Xq7xXyMkQG0PUzIIKicKqlgEIIPVe", "�ڲ���", dD);
            dC[1] = fq("YjRW+pziV4r8PRF8ifVvKwv/GwqmdPFkle6Xq7xXyMkLHIz1NrKx8WZqjNPv0s1+", "�`��{�", dD);
            dC[3] = fq("/bQOi5r/tgSRyAG2CRZOsgv/GwqmdPFkle6Xq7xXyMm+15ICJYBddQnAwmkbDWeL", "��3}s6", dD);
            dC[2] = fq("8LfgUvkDUaZ0kwFS5izq7gv/GwqmdPFkle6Xq7xXyMlfkjwx4OzfR+E/V0q2mfht", "���^��", dD);
        } catch (Exception e) {
            
        }
    }

    @Override
    public void render(DrawContext context, int delta, int mouseX, float mouseY) {
        if (this.parent.extended) {
            if (this.isHovered(delta, mouseX)) {
                this.parent.frame.cursorSetting = this.setting;
            }

            FontRenderers.Sub
                    .drawString(
                            context.getMatrices(),
                            !this.listening
                                    ? (!this.keyBindSetting.isHold() ? dC[7] : dC[6]) + ": " + InputUtil.getKeyString(this.keyBindSetting.getKey())
                                    : (!this.keyBindSetting.isHold() ? dC[5] : dC[4]) + ": ...",
                            (float)(this.parent.frame.x + 22),
                            (float)(this.parent.frame.y + this.parent.frame.height + this.parent.offset + this.parent.settings.indexOf(this) * 20 - 4),
                            Color.WHITE.getRGB(),
                            true
                    );
            super.render(context, delta, mouseX, mouseY);
        }
    }

    @Override
    public void keyPressed(int key) {
        if (this.parent.extended) {
            if (this.listening) {
                if (key != 256 && key != 261) {
                    this.parent.module.setKey(key);
                    this.keyBindSetting.setKey(key);
                } else {
                    this.parent.module.setKey(0);
                    this.keyBindSetting.setKey(0);
                }

                this.listening = false;
            }

            super.keyPressed(key);
        }
    }

    public boolean isHovered(int mouseY, int mouseX) {
        return mouseY > this.parent.frame.x + 10
                && mouseY < this.parent.frame.x + this.parent.frame.width - 20
                && mouseX > this.parent.frame.y + this.parent.offset + this.parent.settings.indexOf(this) * 20 + 10
                && mouseX < this.parent.frame.y + this.parent.offset + this.parent.settings.indexOf(this) * 20 + 20;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getKey() {
        return this.key;
    }
}
