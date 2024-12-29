package bre2el.fpsreducer.mixin;

import bre2el.fpsreducer.feature.module.Module;
import bre2el.fpsreducer.feature.module.ModuleManager;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import bre2el.fpsreducer.feature.module.modules.utility.BackPlace;
import bre2el.fpsreducer.feature.module.modules.utility.NoDelay;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({ClientPlayerInteractionManager.class})
public class ClientPlayerInteractionManagerMixin {
    @Unique
    private static String[] Y;
    @Unique
    private static byte[] Z;

    static {
        bz();
        by();
    }

    @Inject(
            method = {"interactEntity"},
            at = {@At("HEAD")},
            cancellable = true
    )
    void interactEntity(PlayerEntity cir, Entity hand, Hand entity, CallbackInfoReturnable<ActionResult> player) {
        if (!Module.nullCheck()) {
            if (!Module.GlobalFlags.DESTRUCTED.flag) {
                if (ModuleManager.INSTANCE.getModuleByName(Y[3]).isEnabled() && ModuleManager.INSTANCE.getModuleClass(BackPlace.class).enabled) {
                    player.setReturnValue(ActionResult.PASS);
                }
            }
        }
    }

    @ModifyConstant(
            method = {"updateBlockBreakingProgress"},
            constant = {@Constant(
                    intValue = 5
            )}
    )
    int updateBlocBreakingProgress(int value) {
        if (!Module.nullCheck()) {
           // if (!GlobalFlags.DESTRUCTED.flag) {
                return ModuleManager.INSTANCE.getModuleByName(Y[2]).isEnabled() && ModuleManager.INSTANCE.getModuleClass(NoDelay.class).breakDelay.isEnabled()
                        ? 0
                        : value;
            //} else {
            //    return value;
            //}
        } else {
            return value;
        }
    }

    @Unique
    private static void bz() {
        Z = new byte[16];
        Z[3] = -104;
        Z[11] = 106;
        Z[10] = 28;
        Z[12] = -114;
        Z[13] = 28;
        Z[7] = -65;
        Z[8] = 46;
        Z[0] = -128;
        Z[14] = -17;
        Z[4] = -3;
        Z[9] = -66;
        Z[6] = -75;
        Z[15] = 49;
        Z[1] = 50;
        Z[2] = -113;
        Z[5] = 15;
    }

    @Unique
    private static void by() {
        try {
            Y = new String[4];
            Y[3] = bx("SRkClYqu0Uv4q0wGFrrV9oAyj5j9D7W/Lr4cao4c7zGEK0L37J2G0gIeNPP28S4u", "�\u0002�D�e", Z);
            Y[0] = bx("Mh7P7tJqJxIR/GBQA6v3cYAyj5j9D7W/Lr4cao4c7zFdBSsxDdJ3N/TLUYNXSYK4", "��N���", Z);
            Y[2] = bx("fFhc9x8/Wv/B/MxqJqgoWIAyj5j9D7W/Lr4cao4c7zH3tb0ALYdvYDd9uzx14VZv", "�\u0017E�ʶ", Z);
            Y[1] = bx("981IL01NUSlsRkAJo30pW4Ayj5j9D7W/Lr4cao4c7zHsn6Vmpyq/DMbtt54ec8ux", "��Xg��", Z);
        } catch (Exception e) {
        }
    }

    @Unique
    private static String bx(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{-69, 0, -42, 51, -21, 85, 29, -44, 103, -103, -61, 120, -71, 89, -76, 92};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 62, 256);
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
