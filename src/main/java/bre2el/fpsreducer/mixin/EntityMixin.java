package bre2el.fpsreducer.mixin;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.feature.module.ModuleManager;
import bre2el.fpsreducer.feature.module.Module.GlobalFlags;
import bre2el.fpsreducer.feature.module.modules.combat.Reach;
import bre2el.fpsreducer.util.PlayerUtil;
import bre2el.fpsreducer.util.RotationManager;
import bre2el.fpsreducer.util.RotationUtil;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Entity.class})
public abstract class EntityMixin {
    @Unique
    private static String[] ey;
    @Unique
    private static byte[] ez;

    @Shadow
    private static Vec3d movementInputToVelocity(Vec3d yaw, float movementInput, float speed) {
        return null;
    }

    @Redirect(
            method = {"updateVelocity"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;movementInputToVelocity(Lnet/minecraft/util/math/Vec3d;FF)Lnet/minecraft/util/math/Vec3d;"
            )
    )
    public Vec3d updateVelocity(Vec3d movementInput, float speed, float yaw) {
        if (!GlobalFlags.DESTRUCTED.flag) {
            //if (this == Main.mc.player) {
                return !RotationUtil.checkRotations()
                        ? movementInputToVelocity(movementInput, speed, yaw)
                        : movementInputToVelocity(movementInput, speed, RotationManager.INSTANCE.getServerYaw());
            //} else {
            //    return movementInputToVelocity(movementInput, speed, yaw);
           // }
        } else {
            return movementInputToVelocity(movementInput, speed, yaw);
        }
    }

    @Inject(
            method = {"getTargetingMargin"},
            at = {@At("HEAD")},
            cancellable = true
    )
    void onGetTargetingMargin(CallbackInfoReturnable<Float> cir) {
        if (Main.mc.player != null && Main.mc.world != null) {
            if (!GlobalFlags.DESTRUCTED.flag) {
                if (ModuleManager.INSTANCE.getModuleByName(ey[1]).isEnabled()) {
                    Reach var2 = ModuleManager.INSTANCE.getModuleClass(Reach.class);
                    if (var2.weapon.isEnabled() && !PlayerUtil.weaponCheck()) {
                        return;
                    }

                    cir.setReturnValue((float)var2.hitbox.getDefaultValue());
                }
            }
        }
    }

    @Shadow
    public abstract boolean hasVehicle();

    static {
        gM();
        gL();
    }

    @Inject(
            method = {"setVelocity(Lnet/minecraft/util/math/Vec3d;)V"},
            at = {@At("HEAD")},
            cancellable = true
    )
    void setVelocity(Vec3d ci, CallbackInfo velocity) {
        //if (this == Main.mc.player) { cursed ahh
            if (this.hasVehicle()) {
                velocity.cancel();
            }
       // }
    }

    @Unique
    private static void gL() {
        try {
            ey = new String[2];
            ey[1] = gK("Sng+bxOH5+NooQjsvGP2CP0nPqR/lxGpVqVxyIqa8Ce/EBebonSLzixMrEtoH9YJ", "\u007f�z�&!", ez);
            ey[0] = gK("plOeS+LGsi33FWdKhUBsgf0nPqR/lxGpVqVxyIqa8CdCwe1fSAPOM4qzByyw9HvJ", "�5v�%�", ez);
        } catch (Exception e) {
        }
    }

    @Unique
    private static String gK(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{94, -4, -97, -19, 42, 75, 83, 63, -105, -36, 0, -94, -45, -2, 96, 10};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 87, 256);
        SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] var8 = var7.generateSecret(var6).getEncoded();
        SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
        Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var10.init(2, var9, new IvParameterSpec(var2));
        byte[] var11 = var10.doFinal(var5);
        String info = new String(var11, "UTF-8");
        return info;
    }

    private static void gM() {
        ez = new byte[16];
        ez[4] = 127;
        ez[15] = 39;
        ez[6] = 17;
        ez[11] = -56;
        ez[9] = -91;
        ez[3] = -92;
        ez[8] = 86;
        ez[12] = -118;
        ez[14] = -16;
        ez[1] = 39;
        ez[2] = 62;
        ez[0] = -3;
        ez[7] = -87;
        ez[13] = -102;
        ez[5] = -105;
        ez[10] = 113;
    }
}
