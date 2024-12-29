package bre2el.fpsreducer.mixin;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.feature.module.Module;
import bre2el.fpsreducer.feature.module.ModuleManager;
import bre2el.fpsreducer.feature.module.Module.GlobalFlags;
import bre2el.fpsreducer.feature.module.modules.combat.Reach;
import bre2el.fpsreducer.util.PlayerUtil;
import bre2el.fpsreducer.util.RotationManager;
import bre2el.fpsreducer.util.RotationUtil;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin({PlayerEntity.class})
public class PlayerEntityMixin {
    @Unique
    private static byte[] bd;
    @Unique
    private static String[] bc;

    @Unique
    private static void bE() {
        try {
            bc = new String[2];
            bc[0] = bD("fgZwQBBKZiiufFj4bHwjhgujVoQk4RFUKMmDoY6EHaKbidKpikteyklmWK51KQTo", "PO\u0002��", bd);
            bc[1] = bD("9RHqkUnrSksScm2oQQhumgujVoQk4RFUKMmDoY6EHaLvMfhXCsz1xfUDgz9kBc7I", "Dߜ�q@", bd);
        } catch (Exception e) {

        }
    }

    @ModifyExpressionValue(
            method = {"attack"},
            at = {@At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;getYaw()F"
            )}
    )
    float attack(float original) {
        if (!GlobalFlags.DESTRUCTED.flag) {
            //if (this == Main.mc.player) {
                return !RotationUtil.checkRotations() ? original : RotationManager.INSTANCE.getServerYaw();
           // } else {
             //   return original;
           // }
        } else {
            return original;
        }
    }

    @Unique
    private static String bD(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{-33, 0, 67, 10, 98, 61, 81, 77, -125, -30, 48, -57, -70, -42, -42, -19};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 106, 256);
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
        bF();
        bE();
    }

    @Unique
    private static void bF() {
        bd = new byte[16];
        bd[0] = 11;
        bd[9] = -55;
        bd[3] = -124;
        bd[12] = -114;
        bd[1] = -93;
        bd[4] = 36;
        bd[8] = 40;
        bd[7] = 84;
        bd[10] = -125;
        bd[15] = -94;
        bd[13] = -124;
        bd[2] = 86;
        bd[11] = -95;
        bd[14] = 29;
        bd[5] = -31;
        bd[6] = 17;
    }

    @ModifyReturnValue(
            method = {"getEntityInteractionRange"},
            at = {@At("RETURN")}
    )
    double getEntityInteractionRange(double original) {
        if (!Module.nullCheck()) {
            if (!GlobalFlags.DESTRUCTED.flag) {
                if (!ModuleManager.INSTANCE.getModuleClass(Reach.class).isEnabled()) {
                    return original;
                } else {
                    Reach var3 = ModuleManager.INSTANCE.getModuleClass(Reach.class);
                    return var3.weapon.isEnabled() && !PlayerUtil.weaponCheck() ? original : var3.reach.getDefaultValue();
                }
            } else {
                return original;
            }
        } else {
            return original;
        }
    }
}
