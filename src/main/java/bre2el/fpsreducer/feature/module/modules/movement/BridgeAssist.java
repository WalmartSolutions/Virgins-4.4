package bre2el.fpsreducer.feature.module.modules.movement;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.event.impl.UpdateEvent.Pre;
import bre2el.fpsreducer.event.orbit.EventHandler;
import bre2el.fpsreducer.feature.module.Module;
import bre2el.fpsreducer.feature.module.Module.Category;
import bre2el.fpsreducer.feature.module.setting.BoolSetting;
import bre2el.fpsreducer.feature.module.setting.KeyBindSetting;
import bre2el.fpsreducer.feature.module.setting.RangeSetting;
import bre2el.fpsreducer.feature.module.setting.Setting;
import bre2el.fpsreducer.util.MathUtil;
import bre2el.fpsreducer.util.TimerUtil;
import com.google.common.collect.Streams;
import java.util.Base64;
import java.util.stream.Stream;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import net.minecraft.item.BlockItem;
import net.minecraft.util.math.Box;

public class BridgeAssist extends Module {
    public RangeSetting delay;
    public KeyBindSetting bind;
    public static byte[] dJ;
    public BoolSetting onlyShift;
    public TimerUtil timer;
    public BoolSetting lockingDown;
    public static String[] dI;
    public boolean wasActivated;
    public BoolSetting blocksOnly;

    public static void fA() {
        dI = new String[22];
        try {
            dI[6] = fz("sTammno5s7psrqeWNOsdAhL87Ao4eSwEaO9+wNdlKQkvE6q8lheHs5IpYGjbqnUXtT9A96jDEcvcFRMROrlZjg==", "ߢ\u001d�r�", dJ);
            dI[20] = fz("/8ZkYMT9piQtKE1D4huS2RL87Ao4eSwEaO9+wNdlKQlyd54uS5HiVTSTUsVa5Uxc", "�L�I�P", dJ);
            dI[13] = fz("iV5Du2C607jBUz6qF8XWBRL87Ao4eSwEaO9+wNdlKQn9KrkGt2KR/38+awvw4bQJ+e1vh9kvi3DUPkRGX6Qs9Q==", "��oO��", dJ);
            dI[11] = fz("kIjB2Y1lEuf1N4NoQ/3UXRL87Ao4eSwEaO9+wNdlKQmLkJn/Jwddz06tBtggUYrn", "\r�k+��", dJ);
            dI[2] = fz("Lb1WACpwgbNVsL4P6GynHxL87Ao4eSwEaO9+wNdlKQlP0qeXLtuQGFPnHM6DD3ssWcBePcMZs9nZtSbMtPWjyw==", "�SҾ9�", dJ);
            dI[1] = fz("EO6obsScqEHfLMhwgv8LSRL87Ao4eSwEaO9+wNdlKQmD6vamhmizuT5gSIWXPFoVa4OKRDwOun9ZonBPUOU57krVtdQ3VXBDJszxTZm+jkY=", "\nUـ�U", dJ);
            dI[19] = fz("IkzTsmmX1PYMQjNjzezJuhL87Ao4eSwEaO9+wNdlKQmSYq8ZdQxKc2zbgfPX+Osp1F3CfYLZ87fy/E5iOKkNLw==", "�d\u000b@\u0011G", dJ);
            dI[17] = fz("m0Jl300yVhcqWJb9FUZtdxL87Ao4eSwEaO9+wNdlKQnyR9/ewI1NJWZwoAChiTAyPnKMxnwwtrGZCTPU8d91ug==", " \ud8a8\uddd5�", dJ);
            dI[21] = fz("mBXuAu+1POXQgvCmAFAAwhL87Ao4eSwEaO9+wNdlKQnyQCs5OaQtWoSGQeRZs60oAK6T6jCOVwy4G9mcEoPyGQ==", "�@��\u000f�", dJ);
            dI[7] = fz("Ae/b9V0fgSy9i6ZP25WBhxL87Ao4eSwEaO9+wNdlKQk6VhwaeO8a/1AmSxE4ObvF", "�\u0004�:\u0019\u0014", dJ);
            dI[10] = fz("XVVw2wjVgiAsXlT+zLuSfBL87Ao4eSwEaO9+wNdlKQmhBsuAIXs0UgtXUwtK8aI4FMEzOcRQn7UrOTicMeEo1w==", "�Md�\u001d�", dJ);
            dI[14] = fz("cgUr2MlOuSoqX8WgvwSdbRL87Ao4eSwEaO9+wNdlKQlaBWBH1JgaF4JVAcEl3xPl", "p��\u0001P�", dJ);
            dI[0] = fz("2GXHk45bpUgKbsM3CxN+IxL87Ao4eSwEaO9+wNdlKQnIPUT5w2W6dyV0VE+C7I6x", "-���\u0005*", dJ);
            dI[15] = fz("X/jWMkZYcrzmFAMT3aNhxxL87Ao4eSwEaO9+wNdlKQlRGdOhJQk4Xd3QnwRv15T0kFw3UrlNsWnC7vpzLyr9Tw==", ";fJo�&", dJ);
            dI[4] = fz("g7zGAPxKUiHcgIBZVAS8CBL87Ao4eSwEaO9+wNdlKQlNA26rpZ/4PWBdvv9WrQO+ez3sYZxURsMQyDa8g3/HoQ==", "b�`�$6", dJ);
            dI[16] = fz("/y7eICrReb7oPOHSxAcFlBL87Ao4eSwEaO9+wNdlKQnfjTylbKwvosfvKWduPcZU", "�\r#T��", dJ);
            dI[18] = fz("gYxmHDlVelqLDXGs2Ut9AxL87Ao4eSwEaO9+wNdlKQlxYsyJbcUsxyQ/DyXtR1v8", "3�X�V", dJ);
            dI[9] = fz("5FN2gjaBDt6uzjspy1l3EhL87Ao4eSwEaO9+wNdlKQnqHIoNy85Yxpjt9V6w6/55", "~\u0013�cz�", dJ);
            dI[5] = fz("SCxWcEAqhBDsHa2K41Id4hL87Ao4eSwEaO9+wNdlKQmrgXLcLsRrn12vL43vVZ2x", "�y\u001e�%\\", dJ);
            dI[8] = fz("ZUjT8uAgJlgfqDQkps5PRBL87Ao4eSwEaO9+wNdlKQkaQt0VDuaZOW/A9vAj7Q/mF4JSX3MXOxQVul5V8pyPeQ==", "�u�\u0000��", dJ);
            dI[3] = fz("jtOWG+Du252A9lWKsqKT5xL87Ao4eSwEaO9+wNdlKQk7jfnkXlrJLezQxJMnOUgl", "\u07b2�E�8", dJ);
            dI[12] = fz("YPYAczGja6w2G6EIV3NI0BL87Ao4eSwEaO9+wNdlKQnhAd2drz7UvoQ/mD4SEISlhV+fXbp/a7CiZM28PJKj1gnhyeBgxIkOzkLe3XNtOAc=", "����\u00153", dJ);
        } catch (Exception e) {
            // ???? wtf
        }
    }

    @Override
    public void onDisable() {
        this.reset();
        super.onDisable();
    }

    static {
        fB();
        fA();
    }

    Box setMinY(Box box, double minY) {
        return new Box(box.minX, box.minY - minY, box.minZ, box.maxX, box.maxY, box.maxZ);
    }

    @EventHandler
    public void onUpdate(Pre event) {
        if (!nullCheck()) {
            if (Main.mc.currentScreen == null) {
                if (this.check()) {
                    this.timer.reset();
                    if (!this.blocksOnly.isEnabled() || this.checkHands()) {
                        if (!this.lockingDown.isEnabled() || !(Main.mc.player.getPitch() < 40.0F)) {
                            Box var2 = Main.mc.player.getBoundingBox();
                            Box var3 = this.setMinY(var2.offset(0.0, -0.5, 0.0).expand(-0.3, 0.0, -0.3), 0.0);
                            Stream var4 = Streams.stream(Main.mc.world.getBlockCollisions(Main.mc.player, var3));
                            if (!var4.findAny().isPresent()) {
                                if (!Main.mc.options.sneakKey.isPressed()) {
                                    if (Main.mc.options.sprintKey.isPressed()) {
                                        Main.mc.options.sprintKey.setPressed(false);
                                    }

                                    Main.mc.options.sneakKey.setPressed(true);
                                    if (this.onlyShift.isEnabled() && !Main.mc.options.sneakKey.isPressed()) {
                                        return;
                                    }

                                    this.wasActivated = true;
                                }
                            } else if (Main.mc.options.sneakKey.isPressed() && this.wasActivated) {
                                Main.mc.options.sneakKey.setPressed(false);
                                this.wasActivated = false;
                            }
                        }
                    }
                }
            }
        }
    }

    boolean check() {
        return this.timer.hasReached((double)MathUtil.getRandomInt((int)this.delay.getDefaultValue(), (int)this.delay.getSecondValue()));
    }

    public static String fz(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{-66, 0, -22, 84, 11, -112, 44, 45, 123, 101, -27, 102, 82, 126, 8, -75};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 194, 256);
        SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] var8 = var7.generateSecret(var6).getEncoded();
        SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
        Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var10.init(2, var9, new IvParameterSpec(var2));
        byte[] var11 = var10.doFinal(var5);
        String info = new String(var11, "UTF-8");
        return info;
    }

    boolean checkHands() {
        return Main.mc.player.getMainHandStack().getItem() instanceof BlockItem || Main.mc.player.getOffHandStack().getItem() instanceof BlockItem;
    }

    void reset() {
        if (Main.mc.options.sneakKey.isPressed() && this.wasActivated) {
            Main.mc.options.sneakKey.setPressed(false);
            this.wasActivated = false;
        }
    }

    public static void fB() {
        dJ = new byte[16];
        dJ[8] = 104;
        dJ[11] = -64;
        dJ[9] = -17;
        dJ[3] = 10;
        dJ[2] = -20;
        dJ[13] = 101;
        dJ[5] = 121;
        dJ[7] = 4;
        dJ[14] = 41;
        dJ[0] = 18;
        dJ[6] = 44;
        dJ[15] = 9;
        dJ[1] = -4;
        dJ[4] = 56;
        dJ[12] = -41;
        dJ[10] = 126;
    }

    public BridgeAssist() {
        super(dI[11], dI[12], Category.Movement);
        this.bind = new KeyBindSetting(dI[13], 0, false);
        this.delay = new RangeSetting(dI[14], dI[15], 20.0, 40.0, 0.0, 50.0, 1.0, 20.0);
        this.blocksOnly = new BoolSetting(dI[16], dI[17], false);
        this.lockingDown = new BoolSetting(dI[18], dI[19], false);
        this.onlyShift = new BoolSetting(dI[20], dI[21], false);
        this.timer = new TimerUtil();
        this.addSettings(new Setting[]{this.bind, this.delay, this.blocksOnly, this.lockingDown, this.onlyShift});
    }
}
