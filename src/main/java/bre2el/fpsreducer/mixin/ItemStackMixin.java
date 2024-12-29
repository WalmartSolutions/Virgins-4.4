package bre2el.fpsreducer.mixin;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.feature.module.Module;
import bre2el.fpsreducer.feature.module.ModuleManager;
import bre2el.fpsreducer.feature.module.Module.GlobalFlags;
import bre2el.fpsreducer.util.InputUtil;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({ItemStack.class})
public class ItemStackMixin {
    @Unique
    private static byte[] cH;
    @Unique
    private static String[] cG;

    @Unique
    private static void dX() {
        try {
            cG = new String[4];
            cG[0] = dW("HNFwwDeVxGpxQUzBtWSrcE1sE+NNRyP8gm5Om2Ms3zuOWesAC+/dhp7/F0b7BLs0", "6q\\�I@", cH);
            cG[1] = dW("YF7fv232+7ydDDgUoh5n1E1sE+NNRyP8gm5Om2Ms3ztWLPl1PthwXH0HFFAA16GG", "r�$���", cH);
            cG[2] = dW("cl06dpwPQv2NP/WBfNmOok1sE+NNRyP8gm5Om2Ms3zut+gxFMUMy3pPuWWEykjfA", "\ue604��\u001c", cH);
            cG[3] = dW("wRg55trzgfdNgQNVFtpdG01sE+NNRyP8gm5Om2Ms3zvx2klgrYDe1Z2BEB5FV3zv", "5\u000e\u0000�,\u001f", cH);
        } catch (Exception e) {
        }
    }

    @Inject(
            method = {"getBobbingAnimationTime"},
            at = {@At("RETURN")},
            cancellable = true
    )
    void getBobbingAnimationTime(CallbackInfoReturnable<Integer> cir) {
        if (!Module.nullCheck()) {
            if (!GlobalFlags.DESTRUCTED.flag) {
                if (ModuleManager.INSTANCE.getModuleByName(cG[2]).isEnabled()) {
                    if (Main.mc.player.getMainHandStack().isOf(Items.END_CRYSTAL)) {
                        if (InputUtil.checkMouse(1)) {
                            cir.setReturnValue(0);
                        }
                    }
                }
            }
        }
    }

    static {
        dY();
        dX();
    }

    @Unique
    private static void dY() {
        cH = new byte[16];
        cH[0] = 77;
        cH[13] = 44;
        cH[1] = 108;
        cH[2] = 19;
        cH[6] = 35;
        cH[7] = -4;
        cH[3] = -29;
        cH[5] = 71;
        cH[10] = 78;
        cH[15] = 59;
        cH[9] = 110;
        cH[4] = 77;
        cH[12] = 99;
        cH[11] = -101;
        cH[14] = -33;
        cH[8] = -126;
    }

    @Inject(
            method = {"setBobbingAnimationTime"},
            at = {@At("HEAD")},
            cancellable = true
    )
    void setBobbingAnimationTime(int bobbingAnimationTime, CallbackInfo ci) {
        if (!Module.nullCheck()) {
            if (!GlobalFlags.DESTRUCTED.flag) {
                if (ModuleManager.INSTANCE.getModuleByName(cG[3]).isEnabled()) {
                    if (Main.mc.player.getMainHandStack().isOf(Items.END_CRYSTAL)) {
                        if (InputUtil.checkMouse(1)) {
                            ci.cancel();
                        }
                    }
                }
            }
        }
    }

    @Unique
    private static String dW(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{-63, 0, -15, 118, 18, 26, -44, -36, -103, -83, 100, 111, -93, 64, -23, -35};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 96, 256);
        SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] var8 = var7.generateSecret(var6).getEncoded();
        SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
        Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var10.init(2, var9, new IvParameterSpec(var2));
        byte[] var11 = var10.doFinal(var5);
        String info = new String(var11, "UTF-8");
        return info;
    }
}
