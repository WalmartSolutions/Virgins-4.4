package bre2el.fpsreducer.mixin;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.event.impl.BlockBreakEvent.Post;
import bre2el.fpsreducer.event.impl.BlockBreakEvent.Pre;
// import bre2el.fpsreducer.feature.config.FriendsConfig;
import bre2el.fpsreducer.feature.module.Module;
import bre2el.fpsreducer.feature.module.ModuleManager;
import bre2el.fpsreducer.feature.module.Module.GlobalFlags;
import bre2el.fpsreducer.util.RenderUtil.ShaderLoader;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.gui.screen.DownloadingTerrainScreen.WorldEntryReason;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({MinecraftClient.class})
public class MinecraftClientMixin {
    @Unique
    private static String[] fG;
    @Unique
    private static byte[] fH;

    @Inject(
            method = {"handleBlockBreaking"},
            at = {@At("TAIL")},
            cancellable = true
    )
    void onPostBlockBreak(boolean breaking, CallbackInfo ci) {
        if (!GlobalFlags.DESTRUCTED.flag) {
            if (Main.EVENTBUS.post(Post.get()).isCancelled()) {
                ci.cancel();
            }
        }
    }

    @Inject(
            method = {"joinWorld"},
            at = {@At("HEAD")}
    )
    public void onJoin(ClientWorld world, WorldEntryReason worldEntryReason, CallbackInfo ci) {
        // WHAT IN THE PERFORMANCE MASTER 400 IS THIS
//        new Thread(() -> {
//            if (!Main.pingServer().contains(Main.MESSAGE)) {
//                throw new RuntimeException();
//            }
//        }).start();
    }

    @Inject(
            method = {"close"},
            at = {@At("HEAD")}
    )
    void onClose(CallbackInfo ci) {
//        if (!GlobalFlags.DESTRUCTED.flag) {
//            Main.CONFIG.uploadConfig(fG[5]);
//            FriendsConfig.uploadFriends();
//        }
    }

    @Inject(
            method = {"tick"},
            at = {@At("RETURN")}
    )
    void onPostTick(CallbackInfo ci) {
        if (!GlobalFlags.DESTRUCTED.flag) {
            Main.EVENTBUS.post(bre2el.fpsreducer.event.impl.UpdateEvent.Post.get());
        }
    }

    @Unique
    private static void iy() {
        fH = new byte[16];
        fH[15] = 111;
        fH[10] = -41;
        fH[2] = -29;
        fH[12] = 8;
        fH[9] = 40;
        fH[6] = 122;
        fH[14] = -38;
        fH[4] = -107;
        fH[0] = 74;
        fH[8] = -20;
        fH[1] = -83;
        fH[5] = -96;
        fH[11] = 114;
        fH[7] = 102;
        fH[3] = -93;
        fH[13] = -8;
    }

    @Inject(
            method = {"handleBlockBreaking"},
            at = {@At("HEAD")},
            cancellable = true
    )
    void onPreBlockBreak(boolean breaking, CallbackInfo ci) {
        if (!GlobalFlags.DESTRUCTED.flag) {
            if (Main.EVENTBUS.post(Pre.get()).isCancelled()) {
                ci.cancel();
            }
        }
    }

    @Inject(
            method = {"doItemUse"},
            at = {@At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/network/ClientPlayerEntity;isRiding()Z",
                    shift = Shift.AFTER
            )},
            cancellable = true
    )
    void onPreItemUse(CallbackInfo ci) {
        if (!GlobalFlags.DESTRUCTED.flag) {
            if (Main.EVENTBUS.post(bre2el.fpsreducer.event.impl.ItemUseEvent.Pre.get()).isCancelled()) {
                ci.cancel();
            }
        }
    }

    @Inject(
            method = {"tick"},
            at = {@At("HEAD")}
    )
    void onPreTick(CallbackInfo ci) {
        if (!GlobalFlags.DESTRUCTED.flag) {
            Main.EVENTBUS.post(bre2el.fpsreducer.event.impl.UpdateEvent.Pre.get());
            if (!Module.nullCheck()) {
                for (Module var3 : ModuleManager.INSTANCE.getModules()) {
                    var3.onTick();
                }
            }
        }
    }

    @Unique
    private static String iw(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{-59, 14, -86, -101, 52, 96, -101, 80, -3, -26, -16, 0, 24, -92, 35, -60};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 65, 256);
        SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] var8 = var7.generateSecret(var6).getEncoded();
        SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
        Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var10.init(2, var9, new IvParameterSpec(var2));
        byte[] var11 = var10.doFinal(var5);
        String info = new String(var11, "UTF-8");
        return info;
    }

    @Inject(
            method = {"<init>"},
            at = {@At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/MinecraftClient;setOverlay(Lnet/minecraft/client/gui/screen/Overlay;)V",
                    shift = Shift.BEFORE
            )}
    )
    void init(RunArgs ci, CallbackInfo args) {
        Main.SHADERS = new ShaderLoader();
        Main.SHADERS.loadShader(fG[3], fG[4]);
        Main.mc = MinecraftClient.getInstance();
       // if (!Main.authentication()) {
       //     throw new RuntimeException();
        //     }
    }

    static {
        iy();
        ix();
    }

    @Unique
    private static void ix() {
        try {
            fG = new String[6];
            fG[0] = iw("mVvbuL4RwiEnKVIVcEyezkqt46OVoHpm7CjXcgj42m9kh2BpeShg8y3SRW7pVByTuhnLDelD0lrKKIwAFR3eEQ==", "���a�p", fH);
            fG[5] = iw("TXrf+zxG2AASqCVBS1TPhEqt46OVoHpm7CjXcgj42m+Y6Qt+aPIbC7ygD2q7Gfd/", "��\u0005Ò�", fH);
            fG[3] = iw("yHSgSpwpx7SyeTpGczrzqUqt46OVoHpm7CjXcgj42m/QwB1EdwzFmOQSS1kQOci+604zIJTKBo5s8cBvK/S+5A==", "Jc�SRZ", fH);
            fG[2] = iw("pgmVtQ9XVfi386nVONnf+kqt46OVoHpm7CjXcgj42m+XdCOZZRbINKtvjzO09Dhd", "R�J�BX", fH);
            fG[4] = iw("4yeKhD5Y7qKjg7X03uFCoUqt46OVoHpm7CjXcgj42m/8Fjn8inENE4IkcShH+fefuMBJPEGa8noJ2oyHGeVvHg==", "����#", fH);
            fG[1] = iw("oLs0Zi0sjmnvQexe7tn+JEqt46OVoHpm7CjXcgj42m9bxdTQYW+GUPVZO9ils+Egnkq+Pznx8AuoHv3xtz2+wg==", "�\nCq�\u0019", fH);
        } catch (Exception e) {

        }
    }

    @Inject(
            method = {"doAttack"},
            at = {@At("TAIL")},
            cancellable = true
    )
    void onPostAttack(CallbackInfoReturnable<Boolean> cir) {
        if (!GlobalFlags.DESTRUCTED.flag) {
            if (Main.EVENTBUS.post(bre2el.fpsreducer.event.impl.AttackEvent.Post.get()).isCancelled()) {
                cir.cancel();
            }
        }
    }

    @Inject(
            method = {"doAttack"},
            at = {@At("HEAD")},
            cancellable = true
    )
    void onPreAttack(CallbackInfoReturnable<Boolean> cir) {
        if (!GlobalFlags.DESTRUCTED.flag) {
            if (Main.EVENTBUS.post(bre2el.fpsreducer.event.impl.AttackEvent.Pre.get()).isCancelled()) {
                cir.cancel();
            }
        }
    }

    @Inject(
            method = {"doItemUse"},
            at = {@At("TAIL")},
            cancellable = true
    )
    void onPostItemUse(CallbackInfo ci) {
        if (!GlobalFlags.DESTRUCTED.flag) {
            if (Main.EVENTBUS.post(bre2el.fpsreducer.event.impl.ItemUseEvent.Post.get()).isCancelled()) {
                ci.cancel();
            }
        }
    }
}
