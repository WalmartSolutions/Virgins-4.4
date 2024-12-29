package bre2el.fpsreducer.feature.module.modules.combat;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.event.impl.ItemUseEvent.Pre;
import bre2el.fpsreducer.event.impl.UpdateEvent.Post;
import bre2el.fpsreducer.event.orbit.EventHandler;
import bre2el.fpsreducer.feature.module.Module;
import bre2el.fpsreducer.feature.module.Module.Category;
import bre2el.fpsreducer.feature.module.setting.BoolSetting;
import bre2el.fpsreducer.feature.module.setting.KeyBindSetting;
import bre2el.fpsreducer.feature.module.setting.KeySetting;
import bre2el.fpsreducer.feature.module.setting.NumberSetting;
import bre2el.fpsreducer.feature.module.setting.RangeSetting;
import bre2el.fpsreducer.feature.module.setting.Setting;
import bre2el.fpsreducer.util.InputUtil;
import bre2el.fpsreducer.util.InventoryUtil;
import bre2el.fpsreducer.util.MathUtil;
import bre2el.fpsreducer.util.RotationUtil;
import bre2el.fpsreducer.util.TimerUtil;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import net.minecraft.block.Blocks;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.UseAction;
import net.minecraft.util.hit.BlockHitResult;

public class AnchorMacro extends Module {
    public BoolSetting charger;
    public TimerUtil timer;
    public BoolSetting placer;
    public RangeSetting airPlaceChance;
    public int clickTimer;
    public BoolSetting use;
    public BoolSetting safe;
    public static byte[] h;
    public boolean sneak;
    public BoolSetting exploder;
    public BoolSetting airPlace;
    public KeySetting airPlaceKey;
    public int swapTimer2;
    public NumberSetting slot;
    public RangeSetting swapDelay;
    public static String[] g;
    public RangeSetting clickDlay;
    public int swapTimer;
    public KeyBindSetting bind;
    public int sneakTimer;
    public int swapTimer3;

    public static String j(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{68, -125, 46, 14, -66, 74, -65, -61, -86, 50, 92, -121, -79, 15, -2, 0};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 76, 256);
        SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] var8 = var7.generateSecret(var6).getEncoded();
        SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
        Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var10.init(2, var9, new IvParameterSpec(var2));
        byte[] var11 = var10.doFinal(var5);
        String info = new String(var11, "UTF-8");
        return info;
    }

    public AnchorMacro() {
        super(g[25], g[26], Category.Combat);
        this.bind = new KeyBindSetting(g[27], 0, false);
        this.use = new BoolSetting(g[28], g[29], true);
        this.placer = new BoolSetting(g[30], g[31], false);
        this.charger = new BoolSetting(g[32], g[33], true);
        this.exploder = new BoolSetting(g[34], g[35], true);
        this.swapDelay = new RangeSetting(g[36], g[37], 2.0, 4.0, 0.0, 20.0, 1.0, 4.0);
        this.clickDlay = new RangeSetting(g[38], g[39], 2.0, 4.0, 0.0, 20.0, 1.0, 4.0);
        this.slot = new NumberSetting(g[40], g[41], 9.0, 1.0, 9.0, 1.0);
        this.safe = new BoolSetting(g[42], g[43], false);
        this.airPlace = new BoolSetting(g[44], g[45], false);
        this.airPlaceKey = new KeySetting(g[46], g[47], 1);
        this.airPlaceChance = new RangeSetting(g[48], g[49], 60.0, 80.0, 0.0, 100.0, 1.0, 40.0);
        this.timer = new TimerUtil();
        this.addSettings(
                new Setting[]{
                        this.bind,
                        this.use,
                        this.charger,
                        this.exploder,
                        this.swapDelay,
                        this.clickDlay,
                        this.slot,
                        this.safe,
                        this.airPlace,
                        this.airPlaceKey,
                        this.airPlaceChance
                }
        );
    }

    @EventHandler
    public void onUpdate(Post event) {
        if (!nullCheck()) {
            if (this.sneak && this.sneakTimer > 3) {
                this.sneakTimer = 0;
                this.sneak = false;
                Main.mc.options.sneakKey.setPressed(false);
            }
        }
    }

    void tick() {
        this.swapTimer++;
        this.swapTimer2++;
        this.swapTimer3++;
        this.clickTimer++;
        this.sneakTimer++;
    }

    @EventHandler
    public void onItemUse(Pre event) {
        if (this.safe.isEnabled()
                && Main.mc.player.getMainHandStack().isOf(Items.GLOWSTONE)
                && RotationUtil.getHitResultBlock(Main.mc.player, false, RotationUtil.getRotation().getYaw(), RotationUtil.getRotation().getPitch(), 4.5) instanceof BlockHitResult var3
                && Main.mc.world.getBlockState(var3.getBlockPos()).isOf(Blocks.RESPAWN_ANCHOR)
                && (Integer)Main.mc.world.getBlockState(var3.getBlockPos()).get(RespawnAnchorBlock.CHARGES) != 0) {
            new Thread(() -> {
                Main.mc.options.sneakKey.setPressed(true);
                try {
                    Thread.sleep(50L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                InputUtil.callMouse(1, 1);
                try {
                    Thread.sleep(50L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Main.mc.options.sneakKey.setPressed(false);
                Main.mc.player.getInventory().selectedSlot = (int)this.slot.getDefaultValue() - 1;
            }).start();
        }

        if (Main.mc.player.getMainHandStack().isOf(Items.GLOWSTONE)
                && RotationUtil.getHitResultBlock(Main.mc.player, false, RotationUtil.getRotation().getYaw(), RotationUtil.getRotation().getPitch(), 4.5) instanceof BlockHitResult var6
                && Main.mc.world.getBlockState(var6.getBlockPos()).isOf(Blocks.RESPAWN_ANCHOR)
                && (Integer)Main.mc.world.getBlockState(var6.getBlockPos()).get(RespawnAnchorBlock.CHARGES) != 0) {
            AutoHitCrystal.hitCrystalStopper.setEnabled();
        }

        if (this.airPlace.isEnabled()) {
            if (!this.timer.hasReached(1000.0)) {
                return;
            }

            if (RotationUtil.getHitResultBlock(Main.mc.player, false, RotationUtil.getRotation().getYaw(), RotationUtil.getRotation().getPitch(), 4.5) instanceof BlockHitResult var7
                    && Main.mc.world.getBlockState(var7.getBlockPos()).isOf(Blocks.RESPAWN_ANCHOR)
                    && (Integer)Main.mc.world.getBlockState(var7.getBlockPos()).get(RespawnAnchorBlock.CHARGES) != 0) {
                InventoryUtil.selectItemFromHotbar(Items.RESPAWN_ANCHOR);
                if (MathUtil.getRandomInt(1, 100) <= MathUtil.getRandomInt((int)this.airPlaceChance.getDefaultValue(), (int)this.airPlaceChance.getSecondValue())
                        && Main.mc.player.getMainHandStack().isOf(Items.RESPAWN_ANCHOR)) {
                    Main.mc.options.useKey.setPressed(false);
                    Main.mc.getNetworkHandler().sendPacket(new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, var7, 0));
                    this.timer.reset();
                }
            }
        }
    }

    public static void l() {
        h = new byte[16];
        h[11] = -105;
        h[6] = -107;
        h[1] = 8;
        h[10] = 111;
        h[3] = 98;
        h[7] = 90;
        h[12] = 59;
        h[5] = -37;
        h[4] = 117;
        h[13] = 77;
        h[2] = 124;
        h[14] = 73;
        h[8] = 81;
        h[9] = 123;
        h[15] = -15;
        h[0] = -110;
    }

    public static void k() {
        try {
            g = new String[50];
            g[48] = j("FVB+QIOUhFaL33otJmAxWZIIfGJ125VaUXtvlztNSfFWzwBJf2attb/2PgujTcrE", "�w\u007f\u0002y�", h);
            g[35] = j("pBsr2gOGsWek3sFXQMo0lJIIfGJ125VaUXtvlztNSfGCagZwytHNwVKbCzpiQYpVPSn9PCX9LCP5jH9w9IoOEyW7AdmvEnTrmnfFcHSGePM=", "��\n�\u0007�", h);
            g[33] = j("Sk2y0CiRsSQzNt9+DueiAZIIfGJ125VaUXtvlztNSfHpqn147/chJt9oapuoyctnExf8xmFuiLVagbj6ezVBkFwxhVagG12ysuG+NnTxGMI=", "s����!", h);
            g[21] = j("E6yVwq11b42299qeWfsTV5IIfGJ125VaUXtvlztNSfEWblz5P4HAIIGmznSRma/Z", "QoTQ�)", h);
            g[41] = j("kgFPDzfVz4M7qI5I2HmjKZIIfGJ125VaUXtvlztNSfFO5Y2b9BWSwOIkNGr3TdMBCrbXA4UiBVtNcTuOvogkDUCmwnDW7jGpv5OmdZwUOh4=", "b}����", h);
            g[19] = j("kgsO06s7W5K3aG6TWyFubpIIfGJ125VaUXtvlztNSfHCqFAe/jbNEmiBrZoHGp5M", "�\b��!\"", h);
            g[47] = j("T9rt9mji6Z7pr/SHtXRpBZIIfGJ125VaUXtvlztNSfF01VgAgVKcilLCZmSpqLA7VaKMcaXSikle1lXVpA1PlQ==", "ߒ��\u000f�", h);
            g[44] = j("+FKJ9T0BIaKW9HmnJC4sgpIIfGJ125VaUXtvlztNSfFKYpyo6hfwL84jSMbb+qkn", "��\u0005<��", h);
            g[7] = j("W1isliSa0zp8SfXpP9zpb5IIfGJ125VaUXtvlztNSfEkkgE7Tc3BCP+JsSogiqSQ", "��]t��", h);
            g[37] = j("7GaQyB0z2HAeASmqXr4u+pIIfGJ125VaUXtvlztNSfFNZpwhHMqPsbK3WjS0LAsQmHhyXar23n0Rn6FgISx64w==", "���߭�", h);
            g[1] = j("AX+itt1zeag+rTXkVjuLyZIIfGJ125VaUXtvlztNSfGd0NxDGQMfLR5+3Cm3y+QmSLGyJCAipc85DAtH0NmRs6Pwina22N3/hIEsE+03lA8=", "����y�", h);
            g[8] = j("Ilt0UV0/V/dMbDoHPwDyMZIIfGJ125VaUXtvlztNSfGvAjZpOJyCsY8DEjBniY5BjJyZx/CXuoDjEA2rjVQLh+2Ik2L5eiUVhQXZ6R53q4E=", "�a�\u0001�", h);
            g[16] = j("/pHpQpFoVMMM5Rr+79RFxJIIfGJ125VaUXtvlztNSfFSGuhdP3plP0Pj32X90oLaCMrlSWsdZxWpQ/t4JvfUwoANq/LVg/seus0baMWZYmo=", "�-\u0006\u001c��", h);
            g[15] = j("sCJvmHXymGRabDi3/ZLYSJIIfGJ125VaUXtvlztNSfFPdaRfzuz5RWfwhbUsCRlt", "/\u0012\u001aHU>", h);
            g[49] = j("5nF4mgiHovfNBEzamK18cJIIfGJ125VaUXtvlztNSfFvwNwOTNHI1SD+3t8Wa1dHIIeAirKW/RKfh4mSLKsZ8w==", "\u0012���\u0012?", h);
            g[10] = j("6l4/3wAWxu2NUI0s+I7zdJIIfGJ125VaUXtvlztNSfEiZb+csqiEeiRmfvTxj4ERQTsc/g3lLn5xroCEAx4FQA84R26XquVLNkTtobJFwIU=", "UvS�\u0001�", h);
            g[23] = j("2e2GlQq+ri61wL/nD678V5IIfGJ125VaUXtvlztNSfEhDs1Cz6+OGeO1mHCa54eu", "bB矎�", h);
            g[38] = j("ZA9z7vUs0GxuB8B135NGApIIfGJ125VaUXtvlztNSfG/aBKkMforgsRc/UTQp6Wq", "Eg��Ob", h);
            g[32] = j("R7YnBQZrsG/SMj3Msc7N05IIfGJ125VaUXtvlztNSfF+uThU1lgGoSsQ9W3yzxBe", "P�b�\u0007�", h);
            g[5] = j("pwrmZmdH6pXF6r/aB0Vgq5IIfGJ125VaUXtvlztNSfFSgeji2L/FvsW+4eejDJ0p", "��'�I�", h);
            g[14] = j("Oj/jql6tsfSvqKTH0b59wJIIfGJ125VaUXtvlztNSfFAiXQ4uW5rbPZrpomu+xuow8MIaXgq4C3tQCf+s7enqg==", "`S\u0014�X�", h);
            g[11] = j("NNAqwQkAWDn63UHMDhBSo5IIfGJ125VaUXtvlztNSfH20/VFkwkR671ayAUEH7Jf", ",�1���", h);
            g[24] = j("U5B4wkbLZkXqFJSUm7lzOJIIfGJ125VaUXtvlztNSfHEV0glGFZW/lh8Y8Fjw0RG/0YyDSQ95+yRYedaR/MQUw==", "3���5+", h);
            g[0] = j("9yOZ9OfEMzHGUhSJdR5T7JIIfGJ125VaUXtvlztNSfGimA75iiTK07a1K5GO0H9e", "N��g�0", h);
            g[4] = j("p6J+op/geXiUwszSOKsz+ZIIfGJ125VaUXtvlztNSfHrd1A6xKsj39OrClT5FB2zkyRrR9fLfZwGrLfvI/sdc0yOzNlkG95F4BoXZIzQ56I=", "��\u0015���", h);
            g[13] = j("QM7GpasZfOwlhA9tH014VZIIfGJ125VaUXtvlztNSfHr77npyWf/SDD+IasLyq9W", "-�X;��", h);
            g[42] = j("XCL6vtqEspFXiVuxSnyKYJIIfGJ125VaUXtvlztNSfFzIiwQa8AGdk+RHs3IEwpl", "\u0007\u0001�E\u0012�", h);
            g[12] = j("keiTpWgVFM22lWcU38kR5ZIIfGJ125VaUXtvlztNSfHNvAfK0txTvJxiXs1XtUC/iwFUcWHV+fBU6AM3CQXScw==", "\f���\t�", h);
            g[22] = j("enuTjfe8pN2ND6iPmly7WJIIfGJ125VaUXtvlztNSfEFQrWzzdGwhlHcCSZUtT5qr91VzU7gr+AOamEEVCMxwA==", "��\u000b���", h);
            g[29] = j("k8JB1tOPQXQxbsPXKs2xqpIIfGJ125VaUXtvlztNSfGJolx+535BycQhw5eZcSVT8L73n7PE/CJz6sw9jTSfVhlN0KtbrI5I+BULLS1NLMA=", "��\b���", h);
            g[17] = j("VPHNBbdaSDbgz0pwOh2HvpIIfGJ125VaUXtvlztNSfGZSTruWXMsNPmtE62d5R/h", "X��h��", h);
            g[9] = j("dtU+De2E+pf2bWjnjT51IJIIfGJ125VaUXtvlztNSfEzMVfHDVHuKYbc5B1AxUGX", "\u0001ie��u", h);
            g[30] = j("trVBrw8E1/6bJuFA7GJ1C5IIfGJ125VaUXtvlztNSfFdmkQnju+5Y2iTF6yXe7ai", "\u0011�P���", h);
            g[43] = j(
                    "b6cfjjnfL1oDlcuOy0S5rpIIfGJ125VaUXtvlztNSfFdINdAwxITRKaMbTmh4DD9E+F9bbXWneUlMh9I+oAtO07WFrEPBpMkgMeoBSxqKyIdBO7lhE/or1nL5j9MJhbDLIs3VbY6OQXdOgz+6/SFYg==",
                    "��˱��",
                    h
            );
            g[2] = j("IZBduDR5hiMqA6vjzW8kTpIIfGJ125VaUXtvlztNSfH9ERyX1d5Q4p779mRr1F9ZnAx3T6qApU3dP0t7CDgdww==", "Dd\u0003�\\�", h);
            g[31] = j("ZnfpXi3gTQmhtb3etG3d5pIIfGJ125VaUXtvlztNSfGpq7Y6D26xc/tRQ9BN7PXQLe0ELYxKKV3asbHumTqwLXPMFRub9sSHL5X1wzTvMys=", "O�t�_�", h);
            g[25] = j("6bT37QmthNm/pNCwUQz83ZIIfGJ125VaUXtvlztNSfF98hFPqAITTLsoKj57feyY", ">�\u0010��t", h);
            g[3] = j("KwLnugM3aMAosWt5EZ5335IIfGJ125VaUXtvlztNSfGpDrb2mqRyzZHxS2KCx8YQ", "@�h\t:V", h);
            g[18] = j(
                    "5K7q1O0UqbIfXMct314Bz5IIfGJ125VaUXtvlztNSfHm73UQhmDAWobQppAg7G95eRbPnBCx+mFX7aICrQURhxyrQN8j3c24kivD8wXyaDPThiQo2tgNMtQge83AIZ4/JNbaS160Z906WEoAlcKLUA==",
                    "v��?�O",
                    h
            );
            g[40] = j("Nt7upSKFGYZs+F16Uo08Y5IIfGJ125VaUXtvlztNSfFxqq+phCGmkTfCqHB8sXwx", "[�����", h);
            g[34] = j("K/+lu0ULzK0cg7MQZduPr5IIfGJ125VaUXtvlztNSfFY20T8RTJnKrizq6bPtI6V", "�Y��_�", h);
            g[6] = j("5OefN9tYlrDOssChLelRQZIIfGJ125VaUXtvlztNSfH1WFxWfHNnfWCcdttoeql7ehxJ+pfDv8z6xCHpmN8idJfjPoWKWhjb4jbZMwwxkOs=", "U�7�_�", h);
            g[36] = j("MyYrVDKxUUduxdI4s9Co0pIIfGJ125VaUXtvlztNSfE0NVql/YengXtgECl9uV2f", "8\t�%��", h);
            g[28] = j("Mmyir6/Nr4yl8juMr9bJqZIIfGJ125VaUXtvlztNSfFed5wxXDIZGUIZoVXus6oi", "�}��;�", h);
            g[20] = j(
                    "sLW1XPKTtTEZZBD0wA9O3ZIIfGJ125VaUXtvlztNSfF3ofum/zd2PBGD+U829jFOkmq1gp+FITjsKpAp9QSKNnAjCdy2by2gkKVedR0XS1CujSvQWVBWS9OGZtihcvFw", "]\b�Z\rU", h
            );
            g[27] = j("k5Y3jbMNwYSUbFzyYuP24JIIfGJ125VaUXtvlztNSfE/ljODIcEEEAOk5J/wU4yywnK8ycmW2fXI/cvMcSOeEg==", "�?��jH", h);
            g[26] = j("nkuyippjpQ9JOSp1LNuooZIIfGJ125VaUXtvlztNSfHq+5REU8yB9BSdnMdPex5xhdPCUmMxTD3QVUbVEMXTXWYvZ4m9ewd+kZ70yM9ypbs=", "6ƶ!�}", h);
            g[39] = j("QHi5T0PdpBsifDehQ/NydJIIfGJ125VaUXtvlztNSfFWt7F4wm6mBFD+gXUdxceCQYgTHaT9B7kNxcX/YNni9w==", "�L�\u0003`r", h);
            g[46] = j("Rm0QaB63ry2KbsUfO9J+V5IIfGJ125VaUXtvlztNSfHaacHZybTVLz8Rf/6hcJHt", "`���", h);
            g[45] = j(
                    "NZsQS724Ix8y3+V/nvwU6pIIfGJ125VaUXtvlztNSfGbgECNSCE7BgeahNZ0tMGeN0jWL3IxBmS4u3z1t+ohf/e0Qe+hcxaUhSOI8wkZzZnkx0ORudw0Mk0p351JMhFh", "FT��I\u0014", h
            );
        } catch (Exception e){

        }
    }

    @EventHandler
    public void onUpdate(bre2el.fpsreducer.event.impl.UpdateEvent.Pre event) {
        if (!nullCheck()) {
            if (Main.mc.currentScreen == null) {
                if (InputUtil.checkMouse(1)) {
                    if (!this.use.isEnabled()
                            || Main.mc.player.getMainHandStack().getUseAction() != UseAction.BLOCK
                            && Main.mc.player.getMainHandStack().getUseAction() != UseAction.SPEAR
                            && Main.mc.player.getMainHandStack().getUseAction() != UseAction.BOW
                            && Main.mc.player.getMainHandStack().getUseAction() != UseAction.CROSSBOW
                            && Main.mc.player.getMainHandStack().getUseAction() != UseAction.EAT
                            && Main.mc.player.getMainHandStack().getUseAction() != UseAction.DRINK) {
                        if (InventoryUtil.hasItemInHotbar(Items.GLOWSTONE) && InventoryUtil.hasItemInHotbar(Items.RESPAWN_ANCHOR)) {
                            if (this.placer.isEnabled()
                                    && this.swapTimer >= MathUtil.getRandomInt((int)this.swapDelay.getDefaultValue(), (int)this.swapDelay.getSecondValue())
                                    && InventoryUtil.selectItemFromHotbar(Items.RESPAWN_ANCHOR)) {
                                Main.mc.options.useKey.setPressed(false);
                                if (this.clickTimer >= MathUtil.getRandomInt((int)this.clickDlay.getDefaultValue(), (int)this.clickDlay.getSecondValue())) {
                                    Main.mc.options.useKey.setPressed(false);
                                    InputUtil.callMouse(1, 1);
                                    this.clickTimer = 0;
                                    this.swapTimer = 0;
                                }
                            }

                            if (this.charger.isEnabled()
                                    && RotationUtil.getHitResultBlock(
                                    Main.mc.player, false, RotationUtil.getRotation().getYaw(), RotationUtil.getRotation().getPitch(), 4.5
                            ) instanceof BlockHitResult var3
                                    && Main.mc.world.getBlockState(var3.getBlockPos()).isOf(Blocks.RESPAWN_ANCHOR)
                                    && (Integer)Main.mc.world.getBlockState(var3.getBlockPos()).get(RespawnAnchorBlock.CHARGES) == 0
                                    && this.swapTimer2 >= MathUtil.getRandomInt((int)this.swapDelay.getDefaultValue(), (int)this.swapDelay.getSecondValue())
                                    && InventoryUtil.selectItemFromHotbar(Items.GLOWSTONE)
                                    && this.clickTimer >= MathUtil.getRandomInt((int)this.clickDlay.getDefaultValue(), (int)this.clickDlay.getSecondValue())) {
                                Main.mc.options.useKey.setPressed(false);
                                InputUtil.callMouse(1, 1);
                                this.clickTimer = 0;
                                this.swapTimer2 = 0;
                                AutoHitCrystal.hitCrystalStopper.setEnabled();
                                InputUtil.callMouse(0, 0);
                            } else {
                                if (this.exploder.isEnabled()
                                        && RotationUtil.getHitResultBlock(
                                        Main.mc.player, false, RotationUtil.getRotation().getYaw(), RotationUtil.getRotation().getPitch(), 4.5
                                ) instanceof BlockHitResult var5
                                        && Main.mc.world.getBlockState(var5.getBlockPos()).isOf(Blocks.RESPAWN_ANCHOR)
                                        && (Integer)Main.mc.world.getBlockState(var5.getBlockPos()).get(RespawnAnchorBlock.CHARGES) != 0
                                        && this.swapTimer3 >= MathUtil.getRandomInt((int)this.swapDelay.getDefaultValue(), (int)this.swapDelay.getSecondValue())) {
                                    if (!this.safe.isEnabled()) {
                                        Main.mc.player.getInventory().selectedSlot = (int)this.slot.getDefaultValue() - 1;
                                    }

                                    if (this.clickTimer >= MathUtil.getRandomInt((int)this.clickDlay.getDefaultValue(), (int)this.clickDlay.getSecondValue())) {
                                        Main.mc.options.useKey.setPressed(false);
                                        InputUtil.callMouse(1, 1);
                                        this.clickTimer = 0;
                                        this.swapTimer3 = 0;
                                    }
                                }

                                this.tick();
                            }
                        }
                    }
                }
            }
        }
    }

    static {
        l();
        k();
    }
}
