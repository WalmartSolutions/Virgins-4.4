package bre2el.fpsreducer.feature.module.modules.combat;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.event.impl.AttackEvent.Pre;
import bre2el.fpsreducer.event.orbit.EventHandler;
import bre2el.fpsreducer.feature.module.Module;
import bre2el.fpsreducer.feature.module.Module.Category;
import bre2el.fpsreducer.feature.module.setting.BoolSetting;
import bre2el.fpsreducer.feature.module.setting.KeyBindSetting;
import bre2el.fpsreducer.feature.module.setting.NumberSetting;
import bre2el.fpsreducer.feature.module.setting.RangeSetting;
import bre2el.fpsreducer.feature.module.setting.Setting;
import bre2el.fpsreducer.util.EntityUtil;
import bre2el.fpsreducer.util.MathUtil;
import bre2el.fpsreducer.util.PlayerUtil;
import bre2el.fpsreducer.util.RotationManager;
import bre2el.fpsreducer.util.RotationUtil;
import bre2el.fpsreducer.util.RotationManager.Rotation;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.MaceItem;
import net.minecraft.item.SwordItem;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.CheckedRandom;

public class SilentAim extends Module {
    public KeyBindSetting bind;
    public BoolSetting weapon;
    public static byte[] bj;
    public RangeSetting speed;
    public NumberSetting fov;
    public boolean doAttack;
    public NumberSetting range;
    public PlayerEntity target;
    public static String[] bi;

    public static void bN() {
        try {
            bi = new String[22];
            bi[8] = bM("0/JMGtzQbYfI1teVWUOH8dU9Xejc6iXjpz8B7kvWvgJt5iEHNfUmegSPIQDeMr9t", "ә\u000fM�G", bj);
            bi[1] = bM("n9UoLnRnPcCuNjn4RYn7GtU9Xejc6iXjpz8B7kvWvgLSnNs6k58in115plaUDJE37r0jzkhXWdJZYXx/hPcW6TMF+pXR5VNJt/8A9e8oBzU=", "�g9Q�\u0018", bj);
            bi[11] = bM("cwX8Krp5uZrWPx35RQRdR9U9Xejc6iXjpz8B7kvWvgIEGklpKNcZcpTRXnSjuE9m", "�4��̼", bj);
            bi[19] = bM("VmWcXoFxTMUrJKEeHtVu+tU9Xejc6iXjpz8B7kvWvgKCqfHx4OUyk8kTS1y5tBkm", "�eW�|\u0006", bj);
            bi[6] = bM("XrlUpKMKncLjMfdcOd5eadU9Xejc6iXjpz8B7kvWvgLGxtMdy3dOyJVJmrOHvNEMA9NEuhy7nVhyVMuDJbwy4Q==", "o�%o�\\", bj);
            bi[14] = bM("bjUBw4JQFrRIyL6PSw1XGtU9Xejc6iXjpz8B7kvWvgI/GxCq7SEr3/RM1B30rfPL", "N;���o", bj);
            bi[12] = bM("mEoaXdNnAXJfMNCSHHurmdU9Xejc6iXjpz8B7kvWvgJ3yayTX5wtouqjO1OYpPHcolDaQ4WHNFgmyT8SITjgm8KmTzfMSFRti9U2XwkGBm4=", "���\u0000�h", bj);
            bi[0] = bM("8xgE2P6wIuZ4Sk8WWinqutU9Xejc6iXjpz8B7kvWvgIOj8alr8g88t+O2kkLyFWW", "|P�n�!", bj);
            bi[5] = bM("R5XHaz/XeuB6tvkQxw0mG9U9Xejc6iXjpz8B7kvWvgKrlYrJCqy7YIw+OMh3GB8r", "/Y� �\u0003", bj);
            bi[16] = bM("9a+I/c3dH3AxrDlua4GoytU9Xejc6iXjpz8B7kvWvgJPe3OGGc4ahpC1FbNbqRBM", "^\u000f��\b\u0018", bj);
            bi[17] = bM("0XKDYI8Rvn6Du6taXFTQJNU9Xejc6iXjpz8B7kvWvgLEYlTAC6VkXeGbIupEtw/XgeYD8O6S+lws29cJwHcmdA==", "V3�\u05ee\u0018", bj);
            bi[3] = bM("ZaB4jSB2nhu48+3Rd4vPjNU9Xejc6iXjpz8B7kvWvgJvcBc2AjLnyTjXreqxxpMM", "�\u0011��fL", bj);
            bi[2] = bM("xyej4qIirlQmDmasK39SotU9Xejc6iXjpz8B7kvWvgK2mh2xntdztDdE4ywcJWwmsgNqxQMFtr56MUYrAC4gmQ==", "�\u00038��\u007f", bj);
            bi[13] = bM("xX6Q3V9VLJIz02VbgjXVatU9Xejc6iXjpz8B7kvWvgKL5UhQDmVQEKT1h9vdHta23N8KD8blzrcjUWPFEy0bRg==", "�\u000e\u000b\u0007$M", bj);
            bi[9] = bM("X/AqFPBidHy4akKBi4coWNU9Xejc6iXjpz8B7kvWvgJaLN6fnXxm5t+T0PFnQWo5", "������", bj);
            bi[21] = bM(
                    "ptzLe8XYvOT0zDhBKBUeU9U9Xejc6iXjpz8B7kvWvgJSyEaq45BtgDeQCMl0AX2/ucK3Fa6Gp4878DBNIA2fF4odZYQrNPwjdxtRIqWX13ojjOGCMKg4tjObT9feTvgI", "%,�:5!", bj
            );
            bi[4] = bM("wDw/XJ2iGGWp4D9bfPDf6dU9Xejc6iXjpz8B7kvWvgKY0Vm3MkttX727f7EutZw8Ms9UmRT6X9szITTPCcj/9A==", "��1��=", bj);
            bi[10] = bM(
                    "OOJgYFHiOaKBwRU4DKnbftU9Xejc6iXjpz8B7kvWvgLC0Spt4BOOQ1FBGd8NEXAEfYbQ3AN6OUF5Zq96oZ3Ka4M19KvJA71LBz6g5VN711bzk2gdLQpGV4mW888++lHV", "*\u001f�>��", bj
            );
            bi[20] = bM("acH+se0oF6gqqQHuoLi+ntU9Xejc6iXjpz8B7kvWvgL1SHEAuedtwXR+d9rBejB3", "�EIz��", bj);
            bi[18] = bM("3lydzhRS9AfaGpbxRzPLQtU9Xejc6iXjpz8B7kvWvgLDypwpKDHzygmqP0UKM0fR", "�\u001c�\u001d�\u000e", bj);
            bi[15] = bM("ZG4eAWowEn36XVQ4dQmRw9U9Xejc6iXjpz8B7kvWvgKIyZKlO73c0h79bvehRPLUfTmwmgnncQca9LRPcx/Wpg==", "v{�\u0013\u0012l", bj);
            bi[7] = bM("Qa/nPITJlF3E8rLVWIdd2tU9Xejc6iXjpz8B7kvWvgLnS98obIvBUgi1itH0/emF", "}�E�{F", bj);
        } catch (Exception e) {
        }
    }

    @EventHandler
    void onAttack(Pre event) {
        if (this.target != null) {
            Item var2 = Main.mc.player.getMainHandStack().getItem();
            if (!(var2 instanceof AxeItem) && !(var2 instanceof SwordItem) && !(var2 instanceof MaceItem) && this.weapon.isEnabled()) {
                return;
            }

            if (!RotationUtil.checkRotations()) {
                return;
            }

            this.doAttack = true;
            event.cancel();
        }
    }

    @Override
    public void onDisable() {
        RotationManager.INSTANCE.setEnabled(false);
        super.onDisable();
    }

    @EventHandler
    void onBlockBreak(bre2el.fpsreducer.event.impl.BlockBreakEvent.Pre event) {
        if (this.target != null) {
            event.cancel();
        }
    }

    @EventHandler
    public void onUpdate(bre2el.fpsreducer.event.impl.UpdateEvent.Pre event) {
        if (!nullCheck()) {
            this.setDetail(this.speed.getDefaultValue() + "-" + this.speed.getSecondValue());
            if (this.doAttack && this.target != null) {
                Main.mc.interactionManager.attackEntity(Main.mc.player, this.target);
                Main.mc.player.swingHand(Hand.MAIN_HAND);
                this.doAttack = false;
            }

            if (!RotationManager.INSTANCE.getStopRotateList().contains(this)) {
                if (!PlayerUtil.weaponCheck() && this.weapon.isEnabled()) {
                    RotationManager.INSTANCE.setEnabled(false);
                } else {
                    if (this.target == null) {
                        this.target = EntityUtil.findClosestPlayer(PlayerEntity.class, (float)this.range.getDefaultValue());
                        if (this.target == null) {
                            RotationManager.INSTANCE.setEnabled(false);
                        }

                        if (!PlayerUtil.validateTarget(this.target)) {
                            RotationManager.INSTANCE.setEnabled(false);
                            return;
                        }
                    }

                    if (this.target == null) {
                        RotationManager.INSTANCE.setEnabled(false);
                    } else {
                        if (!this.target.isAlive()
                                || this.target.isRemoved()
                                || (double)this.target.distanceTo(Main.mc.player) > this.range.getDefaultValue()
                                || Main.mc.player.isDead()
                                || !PlayerUtil.validateTarget(this.target)) {
                            this.target = null;
                            RotationManager.INSTANCE.setEnabled(false);
                        }

                        if (this.target == null) {
                            RotationManager.INSTANCE.setEnabled(false);
                        } else {
                            Rotation var2 = RotationUtil.getDirection(
                                    Main.mc.player, this.target.getBoundingBox().getBottomCenter().addRandom(new CheckedRandom(System.currentTimeMillis()), 0.1F)
                            );
                            Rotation var3 = new Rotation(RotationManager.INSTANCE.getServerYaw(), RotationManager.INSTANCE.getServerPitch());
                            float var4 = (float)(
                                    (double)MathUtil.getRandomFloat((float)this.speed.getDefaultValue(), (float)this.speed.getSecondValue())
                                            * (Double)Main.mc.options.getMouseSensitivity().getValue()
                                            / 100.0
                            );
                            float var5 = (float)(
                                    (double)MathUtil.getRandomFloat((float)this.speed.getDefaultValue(), (float)this.speed.getSecondValue())
                                            * (Double)Main.mc.options.getMouseSensitivity().getValue()
                                            / 150.0
                            );
                            float var6 = MathHelper.lerpAngleDegrees(var4, var3.getYaw(), var2.getYaw());
                            float var7 = MathHelper.lerpAngleDegrees(var5, var3.getPitch(), var2.getPitch());
                            if (!(
                                    RotationUtil.getAngleToLookVecByRot(Main.mc.player.getYaw(), Main.mc.player.getPitch(), this.target.getBoundingBox().getCenter())
                                            < (double)((float)this.fov.getDefaultValue() / 2.0F)
                            )) {
                                this.target = null;
                                this.doAttack = false;
                                RotationManager.INSTANCE.setResetRotation(true);
                                RotationManager.INSTANCE.setEnabled(false);
                            } else {
                                if (!RotationManager.INSTANCE.isEnabled()) {
                                    RotationManager.INSTANCE.setEnabled(true);
                                }

                                RotationManager.INSTANCE.setCurrentRotation(new Rotation(var6, var7));
                                RotationManager.INSTANCE.setServerRotation(new Rotation(var6, var7));
                            }
                        }
                    }
                }
            }
        }
    }

    public static void bO() {
        bj = new byte[16];
        bj[12] = 75;
        bj[11] = -18;
        bj[1] = 61;
        bj[2] = 93;
        bj[8] = -89;
        bj[9] = 63;
        bj[10] = 1;
        bj[14] = -66;
        bj[7] = -29;
        bj[13] = -42;
        bj[5] = -22;
        bj[0] = -43;
        bj[6] = 37;
        bj[4] = -36;
        bj[15] = 2;
        bj[3] = -24;
    }

    static {
        bO();
        bN();
    }

    public static String bM(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{81, 27, 0, -45, 50, -27, 17, -68, 83, -14, 127, 8, 84, 12, -104, -70};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 81, 256);
        SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] var8 = var7.generateSecret(var6).getEncoded();
        SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
        Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var10.init(2, var9, new IvParameterSpec(var2));
        byte[] var11 = var10.doFinal(var5);
        return new String(var11, "UTF-8");
    }

    public SilentAim() {
        super(bi[11], bi[12], Category.Combat);
        this.bind = new KeyBindSetting(bi[13], 0, false);
        this.speed = new RangeSetting(bi[14], bi[15], 30.0, 50.0, 0.0, 100.0, 1.0, 20.0);
        this.fov = new NumberSetting(bi[16], bi[17], 180.0, 0.0, 360.0, 1.0);
        this.range = new NumberSetting(bi[18], bi[19], 5.0, 0.0, 6.0, 0.1);
        this.weapon = new BoolSetting(bi[20], bi[21], false);
        this.addSettings(new Setting[]{this.bind, this.speed, this.fov, this.range, this.weapon});
    }
}
