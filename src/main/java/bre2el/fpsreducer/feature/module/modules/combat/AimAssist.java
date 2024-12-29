package bre2el.fpsreducer.feature.module.modules.combat;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.event.impl.UpdateMouseEvent;
import bre2el.fpsreducer.event.orbit.EventHandler;
import bre2el.fpsreducer.feature.module.Module;
import bre2el.fpsreducer.feature.module.Module.Category;
import bre2el.fpsreducer.feature.module.setting.BoolSetting;
import bre2el.fpsreducer.feature.module.setting.KeyBindSetting;
import bre2el.fpsreducer.feature.module.setting.ModeSetting;
import bre2el.fpsreducer.feature.module.setting.NumberSetting;
import bre2el.fpsreducer.feature.module.setting.RangeSetting;
import bre2el.fpsreducer.feature.module.setting.Setting;
import bre2el.fpsreducer.util.EntityUtil;
import bre2el.fpsreducer.util.HealthUtil;
import bre2el.fpsreducer.util.InputUtil;
import bre2el.fpsreducer.util.MathUtil;
import bre2el.fpsreducer.util.PlayerUtil;
import bre2el.fpsreducer.util.RotationUtil;
import bre2el.fpsreducer.util.RotationManager.Rotation;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.util.math.random.Random;

public class AimAssist extends Module {
    public RangeSetting fov;
    public NumberSetting range;
    public PlayerEntity target;
    public RangeSetting speed;
    public BoolSetting weapon;
    public static byte[] dh;
    public KeyBindSetting bind;
    public BoolSetting click;
    public ModeSetting mode;
    public static String[] dg;
    public BoolSetting lock;
    public boolean locked;
    public BoolSetting visible;

    public static String eJ(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{75, 86, 65, 0, 24, 54, -55, -75, -101, 8, -2, 56, -16, 42, -89, 120};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 110, 256);
        SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] var8 = var7.generateSecret(var6).getEncoded();
        SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
        Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var10.init(2, var9, new IvParameterSpec(var2));
        byte[] var11 = var10.doFinal(var5);
        String info = new String(var11, "UTF-8");
        return info;
    }

    public static void eL() {
        dh = new byte[16];
        dh[2] = -63;
        dh[11] = -90;
        dh[15] = 119;
        dh[0] = 88;
        dh[12] = 2;
        dh[3] = 96;
        dh[4] = 15;
        dh[14] = -3;
        dh[6] = -115;
        dh[10] = -124;
        dh[13] = -70;
        dh[7] = 109;
        dh[5] = -10;
        dh[8] = -84;
        dh[9] = 100;
        dh[1] = -47;
    }

    @Override
    public void onDisable() {
        this.locked = false;
        super.onDisable();
    }

    public static void eK() {
        try {
            dg = new String[46];
            dg[40] = eJ("EHyk0oDquKuh2OQAzaTftVjRwWAP9o1trGSEpgK6/XfiNvfJlZJDyx2gzXBXuxda5x3UfXWi+oFgiiMLiP76hu3O3/AIZK3GeOTdcE9NLVY=", "B��HM�", dh);
            dg[33] = eJ("L7s8mUUjSd4qX83S6QnyZFjRwWAP9o1trGSEpgK6/XeSZVSaYuGWEP4TrMkBn1XBJtyRxaI5NX5IgT0M+pJFFw==", " ����'", dh);
            dg[43] = eJ("OO++qEyqa0nTK09sLDSPU1jRwWAP9o1trGSEpgK6/XfMIaTvMigQ4CICg7Ny1Sjj", "``7���", dh);
            dg[8] = eJ("MT5H3PdwL3d9EnOA9yjzk1jRwWAP9o1trGSEpgK6/XereZIZlwAeOkCNaEnlzXBk/1qTZTeEtzDL9CiMEoiBvA==", "��>I$\u001b", dh);
            dg[32] = eJ("dzQvkw6jOPwjpF2PW/xhS1jRwWAP9o1trGSEpgK6/XewipkPHFMvvDA4K+qzQVyB", "!��b��", dh);
            dg[28] = eJ("A+yA65Ika8rCNXIZeIQv2VjRwWAP9o1trGSEpgK6/XcYqoiWupCFu3F+ctqDK8Ah", "0��=�P", dh);
            dg[3] = eJ("9oBBtJKdENIMlWEwEYoqsVjRwWAP9o1trGSEpgK6/XfmfT6RgwHApjMehuBKkbxe", "\u001b\u000bܩ�|", dh);
            dg[7] = eJ("/UL8NYaT9z9Xv0tjrEune1jRwWAP9o1trGSEpgK6/Xeqi+xgllkQMnI58906OyLE", "=שn�[", dh);
            dg[41] = eJ("vgvOTExD8oysyjz1M74Xh1jRwWAP9o1trGSEpgK6/Xf/w7sQ4vuMsx2g/8tpvVyW", "'��c�_", dh);
            dg[19] = eJ("s18Vh1wkFD7CCSHqoB6AAljRwWAP9o1trGSEpgK6/XeTs2EUoZ2+mKjlHYc2WzcPceWL9nTRYXXtc/2R37M0PQ==", "\u0012EZ��7", dh);
            dg[15] = eJ("bPmtV6jyxw4/8ACIXlyayljRwWAP9o1trGSEpgK6/Xes22PYkaL2uxLevPWmRRXaVCr1lkwpD3z9mZ+4xTR3PVr23a3xceoj3Uze9Z3nIdg=", "�3�\"�\t", dh);
            dg[0] = eJ("wOFunqC9j9vMKNdQ8G8ICljRwWAP9o1trGSEpgK6/XdScMLveRX6QaKhkvP+wt7y", "B��\u000eV�", dh);
            dg[35] = eJ("J66dx8Kz0V+nwevmx5oZlFjRwWAP9o1trGSEpgK6/Xdq0YauT5Zvhf5cIJ03UN7p", "G��{\u001c", dh);
            dg[39] = eJ("zzeBN+JdEjgXLvQYXesTqFjRwWAP9o1trGSEpgK6/Xcgz9ejP42CncJNkyFvr591", "\r!0�(�", dh);
            dg[23] = eJ("CyaBwtnBStdvSN7rWvIWHljRwWAP9o1trGSEpgK6/XdpadP5UtKQyjii54ulJYZ+", "rv��lV", dh);
            dg[34] = eJ("AsnHiaWaLYn9TqdN+jTLbFjRwWAP9o1trGSEpgK6/Xd31IyiRJInwhx9Pwp4EhZ5", "]s�r��", dh);
            dg[45] = eJ("O7IqH7SpRlN51nkP2xdzdljRwWAP9o1trGSEpgK6/XdBcWS0F5iJyUMzHFOWtjFo", "�� �\u001b3", dh);
            dg[9] = eJ("s/NgZMMGb+ZWW+kKB1ztdljRwWAP9o1trGSEpgK6/Xc33Q+KbV8e9nQuE+Jcisj1", "��\\'՝", dh);
            dg[2] = eJ("OXjsZrA0HNqUwJUc1o45B1jRwWAP9o1trGSEpgK6/XexEbWDnd/6xN8GM4AyO80+IVkRN4uABSomeD17ZOj1pg==", "��W^\u0015�", dh);
            dg[11] = eJ("bJ+0ZMCfKyu5exkLpSElaVjRwWAP9o1trGSEpgK6/Xf0z7iMsXbYAqQ1xshfXa/g", "8�̛6:", dh);
            dg[36] = eJ("dzdFVoXLpk2/mHpBIxxUHVjRwWAP9o1trGSEpgK6/Xfr6T0SOLMhkF5cbX09ZA2Z", "Ӭ�\"\u001eY", dh);
            dg[12] = eJ("5ISlY08XIA49FWhhqKgam1jRwWAP9o1trGSEpgK6/XeSAI4rZpxSQhxNMejm7HJw", "�XP)\u0015�", dh);
            dg[44] = eJ("qAr39Xg58nOvwtyD1PKd8ljRwWAP9o1trGSEpgK6/Xe5xPYsW5LQDI88iwOov882Gf0c/T4hiq68Cgo8+JaQwA==", "�|I�3\\", dh);
            dg[29] = eJ("qeOLNJ+pfCmTb57KolufhljRwWAP9o1trGSEpgK6/XerYqWQDCopwNxQnRtIXeEo", "����D�", dh);
            dg[18] = eJ("oyMEIEhzjHR1PcDPJZi4MFjRwWAP9o1trGSEpgK6/Xfs5tXuTf0CrD7WKgz8NXvi", ":�X�}l", dh);
            dg[31] = eJ("hihCHQuH4Czyv0hQq5tr5ljRwWAP9o1trGSEpgK6/XdY3gbfj7nenu/Aa/i/qSit1RKyUVcv1HYdIPIW6UjHzQ==", "e(\u0011-e�", dh);
            dg[17] = eJ("tEWu6vlK7huS/es/cecKgljRwWAP9o1trGSEpgK6/Xe/iDZHeNPI4+a8ZjoXXV1BNsr5srbAZayQex3qOGlCnoU2OmQE4UYaSmuo4HkFWIk=", ")�\fS�\u0005", dh);
            dg[21] = eJ("rCxFeMl21QSARtNqhiDKn1jRwWAP9o1trGSEpgK6/XdCpSTa85TyvRp6Rdv05m/dBPNQAvIatl+Ov0R3bJdQ7w==", "�؟�̨", dh);
            dg[1] = eJ("HF/v+tEyjdZNMdjnCOxllVjRwWAP9o1trGSEpgK6/XcfabUzAtepmihSROfId3WJdJceI5g/dqht0PmmoMrXJA==", "�}���y", dh);
            dg[42] = eJ("/Gw1VYgeMXQ9sbkoxBwGs1jRwWAP9o1trGSEpgK6/XeQ3UfWQ/aj9DiOtJFsByiV2tHWVRqFjd9ZF6op3sl03Q==", "�>�j�a", dh);
            dg[26] = eJ("hkWR1DLsdDr9oIoY82GvZVjRwWAP9o1trGSEpgK6/XdPOBVTWvnYC+lgyEt1tDDi", "4\u0004�\u0004��", dh);
            dg[24] = eJ("0TMBGlWIGkLVrkKWrd0q2FjRwWAP9o1trGSEpgK6/Xe6K3Tl2MHAKVazrF2Y+uBZbEwqftGq1MQQF6xgAl514g==", "�\u000bqTh�", dh);
            dg[14] = eJ("/2I88TOQYcTZGa8yy66xuljRwWAP9o1trGSEpgK6/XdHtZ4n9w51GeCjwg3UHsM6", "u\u0012! \u0014S", dh);
            dg[27] = eJ("IwIIzXFFwsB9T71PKnF95VjRwWAP9o1trGSEpgK6/Xf/efPJthDURGYAmfscXsjdI/ANJE6I6EH18Q/PM8Cm3g==", "\u001f�Ow�", dh);
            dg[16] = eJ("M7h+AmMZFhZ6F5un7Pwj7VjRwWAP9o1trGSEpgK6/XcwbHzkMwYKbRQ3h9jJlr6S", "��T�E�", dh);
            dg[4] = eJ("8BFDNYZsTMa2MhqCW8VuAljRwWAP9o1trGSEpgK6/Xc5C04uhGrgzsJP/msf6bxBDTIn1UigTGnfRABrzGM+sQ==", "��1\u001e��", dh);
            dg[22] = eJ("gadLmUQknqF//CXkDuHkrFjRwWAP9o1trGSEpgK6/Xd5IoW1B04bTP+I8MctTw5B", "�/y���", dh);
            dg[10] = eJ("wq8nnzzHTk4rmV+sw76mYFjRwWAP9o1trGSEpgK6/Xd7K2+3Mlegd8my47YIICA+al56laAvYGeeTQC27oRrFQ==", "��l(��", dh);
            dg[13] = eJ("fdtsMdRlJZdu/cBHA4a82FjRwWAP9o1trGSEpgK6/XepYI+rBVwicesqaZMr0nZ0", "i\u0012\uf4ce7", dh);
            dg[6] = eJ("2zJYp2GZ7LYCYq/+D8NRiVjRwWAP9o1trGSEpgK6/XeyVGGGNPGCfExnInmgV6H0", "�F�_<\u001f", dh);
            dg[38] = eJ("z9F9R6O7rrmuC2axRtlczVjRwWAP9o1trGSEpgK6/Xf8LQd/M1lYXFTr5qhw15ZRQJCXk9nu6eIxCG2c4LOeamjhig1i2KwIl1PRD6eyH/c=", "��}�-\r", dh);
            dg[25] = eJ("KJMwb6kYYrSdzSPm+FPbYFjRwWAP9o1trGSEpgK6/Xd+AV5U7bJPMStpBECgpmZI8dDuim7dUhOJDruWT8UC5A==", "�ԋ�\u0002�", dh);
            dg[37] = eJ("qkf9EuYjk72Vv2IlG4IQ+VjRwWAP9o1trGSEpgK6/XeNSkDbw3tjN8pX2D8DOHC2", "���ψ ", dh);
            dg[30] = eJ("f12yabPlh33bjoTkaK4HyFjRwWAP9o1trGSEpgK6/XfzQJMHKRhWIRff37mDGT9E", "R��Z��", dh);
            dg[20] = eJ("WdsFOoMCwDYPSg3fxpd0MVjRwWAP9o1trGSEpgK6/XdgcjcnE33vvLivLkTPW1GV", "�4�W�)", dh);
            dg[5] = eJ("0ulXXGl5Jgx660G07n0xYljRwWAP9o1trGSEpgK6/XfD6QuiEzbsJHOFfev27xox", "\r�>��<", dh);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public AimAssist() {
        super(dg[23], dg[24], Category.Combat);
        this.bind = new KeyBindSetting(dg[25], 0, false);
        this.speed = new RangeSetting(dg[26], dg[27], 30.0, 50.0, 0.0, 100.0, 1.0, 20.0);
        this.fov = new RangeSetting(dg[28], dg[29], 140.0, 180.0, 0.0, 180.0, 1.0, 60.0);
        this.range = new NumberSetting(dg[30], dg[31], 5.0, 0.0, 6.0, 0.1);
        this.mode = new ModeSetting(dg[32], dg[33], dg[34], new String[]{dg[35], dg[36]});
        this.weapon = new BoolSetting(dg[37], dg[38], false);
        this.click = new BoolSetting(dg[39], dg[40], false);
        this.lock = new BoolSetting(dg[41], dg[42], false);
        this.visible = new BoolSetting(dg[43], dg[44], false);
        this.addSettings(new Setting[]{this.bind, this.speed, this.fov, this.range, this.mode, this.weapon, this.click, this.lock});
    }

    @EventHandler
    public void onUpdateMouse(UpdateMouseEvent event) {
        if (!nullCheck()) {
            if (this.target != null && (this.target.isDead() || this.target.isRemoved())) {
                this.target = null;
            }

            if (Main.mc.currentScreen == null) {
                if (!this.click.isEnabled() || InputUtil.checkMouse(1)) {
                    if (PlayerUtil.weaponCheck() || !this.weapon.isEnabled()) {
                        if (this.lock.isEnabled() && !this.locked) {
                            this.target = (PlayerEntity) EntityUtil.findClosestPlayer(Entity.class, (float)this.range.getDefaultValue());
                            if (this.target == null) {
                                return;
                            }

                            this.locked = true;
                        }

                        if (!this.lock.isEnabled()) {
                            this.locked = false;
                            this.target = EntityUtil.findClosestPlayer(PlayerEntity.class, (float)this.range.getDefaultValue());
                        }

                        if (this.target != null) {
                            if (Main.mc.player.canSee(this.target) || !this.visible.isEnabled()) {
                                if (this.mode.getMode().equals(dg[45])) {
                                    for (PlayerEntity var3 : Main.mc.world.getPlayers()) {
                                        if ((!this.lock.isEnabled() || !this.locked)
                                                && var3 != Main.mc.player
                                                && HealthUtil.sortHealth(this.target, var3) > HealthUtil.sortHealth(var3, this.target)
                                                && var3.distanceTo(Main.mc.player) < 6.0F) {
                                            this.target = var3;
                                        }
                                    }
                                }

                                if (this.target != null) {
                                    boolean var6 = PlayerUtil.validateTarget(this.target)
                                            && RotationUtil.getAngleToLookVec(this.target.getBoundingBox().getCenter().addRandom(Random.create(), 2.0F))
                                            <= (double)(MathUtil.getRandomFloat((float)this.fov.getDefaultValue(), (float)this.fov.getSecondValue()) / 2.0F);
                                    if (var6) {
                                        if (!this.target.isDead()) {
                                            if (!Main.mc.interactionManager.isBreakingBlock()) {
                                                Rotation var7 = RotationUtil.getDir(
                                                        Main.mc.player,
                                                        this.target.getBoundingBox().getBottomCenter().addRandom(new CheckedRandom(System.currentTimeMillis()), 0.1F)
                                                );
                                                float var4 = (float)(
                                                        (double)MathUtil.getRandomFloat((float)this.speed.getDefaultValue(), (float)this.speed.getSecondValue())
                                                                * (Double)Main.mc.options.getMouseSensitivity().getValue()
                                                                / 1000.0
                                                );
                                                float var5 = MathHelper.lerpAngleDegrees(Math.abs(var4), Main.mc.player.getYaw(), var7.getYaw());
                                                Main.mc.player.setYaw(var5);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    static {
        eL();
        eK();
    }
}
