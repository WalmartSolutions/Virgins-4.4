package bre2el.fpsreducer.feature.module.modules.combat;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.event.impl.UpdateMouseEvent;
import bre2el.fpsreducer.event.impl.UpdateEvent.Pre;
import bre2el.fpsreducer.event.orbit.EventHandler;
import bre2el.fpsreducer.feature.module.Module;
import bre2el.fpsreducer.feature.module.ModuleManager;
import bre2el.fpsreducer.feature.module.Module.Category;
import bre2el.fpsreducer.feature.module.modules.utility.BackPlace;
import bre2el.fpsreducer.feature.module.setting.BoolSetting;
import bre2el.fpsreducer.feature.module.setting.KeyBindSetting;
import bre2el.fpsreducer.feature.module.setting.ModeSetting;
import bre2el.fpsreducer.feature.module.setting.RangeSetting;
import bre2el.fpsreducer.feature.module.setting.Setting;
import bre2el.fpsreducer.util.InputUtil;
import bre2el.fpsreducer.util.MathUtil;
import bre2el.fpsreducer.util.RotationManager;
import bre2el.fpsreducer.util.RotationUtil;
import bre2el.fpsreducer.util.TimerUtil;
import bre2el.fpsreducer.util.RotationManager.Rotation;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import net.minecraft.block.Blocks;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class CrystalClicker extends Module {
    public boolean broke;
    public static String[] K;
    public BoolSetting noWeak;
    public KeyBindSetting bind;
    public boolean crystaling;
    public TimerUtil timer;
    public int id;
    public EndCrystalEntity target;
    public int breakTick;
    public ModeSetting mode;
    public static byte[] L;
    public BlockPos blockPos;
    public boolean placed;
    public int placeTick;
    public BoolSetting onGround;
    public RangeSetting breakDelay;
    public BoolSetting pauseOnKill;
    public RangeSetting placeDelay;

    boolean killCheck() {
        return Main.mc
                .world
                .getPlayers()
                .parallelStream()
                .filter(e -> Main.mc.player != e)
                .filter(e -> e.squaredDistanceTo(Main.mc.player) < 36.0)
                .anyMatch(LivingEntity::isDead);
    }

    public static void be() {
        L = new byte[16];
        L[4] = -79;
        L[1] = -70;
        L[7] = -105;
        L[5] = -14;
        L[8] = -14;
        L[6] = -36;
        L[14] = 14;
        L[10] = 37;
        L[12] = -82;
        L[2] = 121;
        L[13] = -28;
        L[15] = 38;
        L[3] = 89;
        L[9] = -45;
        L[0] = -44;
        L[11] = 92;
    }

    boolean crystalCheck() {
        return Main.mc.player.getMainHandStack().isOf(Items.END_CRYSTAL);
    }

    boolean isCrystal() {
        if (ModuleManager.INSTANCE.getModuleByName(K[45]).isEnabled() && ModuleManager.INSTANCE.getModuleClass(BackPlace.class).backBreak.isEnabled()) {
            if (RotationUtil.getHitResultIgnorePlayers(Main.mc.player, false, Main.mc.player.getYaw(), Main.mc.player.getPitch(), 3.0) instanceof EntityHitResult var3
                    && var3.getEntity() instanceof EndCrystalEntity) {
                return true;
            }

            return false;
        } else {
            if (RotationUtil.getHitResult(Main.mc.player, false, Main.mc.player.getYaw(), Main.mc.player.getPitch()) instanceof EntityHitResult var1
                    && var1.getEntity() instanceof EndCrystalEntity) {
                return true;
            }

            return false;
        }
    }

    @EventHandler
    public void onUpdate(Pre event) {
        if (!nullCheck()) {
            if (Main.mc.currentScreen == null) {
                if (this.broke) {
                    this.broke = false;
                    InputUtil.sendInput(0, 0);
                }

                if (this.placed) {
                    this.placed = false;
                    InputUtil.sendInput(1, 0);
                }

                if (this.mode.getMode().equals(K[48])) {
                    this.target = null;
                    this.blockPos = null;
                    RotationManager.INSTANCE.setEnabled(false);
                }

                if (this.killCheck() && this.pauseOnKill.isEnabled()) {
                    this.timer.reset();
                }

                if (this.timer.hasReached(500.0)) {
                    if (this.crystalCheck()) {
                        if (InputUtil.checkMouse(1)) {
                            if (!this.mode.getMode().equals(K[49])) {
                                if (!this.mode.getMode().equals(K[50])) {
                                    this.blatantCrystal();
                                } else {
                                    this.silentCrystal();
                                }
                            } else {
                                this.normalCrystal();
                            }

                            this.tick();
                        }
                    }
                }
            }
        }
    }

    void blatantCrystal() {
        Main.mc.options.useKey.setPressed(false);
        if (this.readyToBreak() && this.isCrystal()) {
            if (!Main.mc.player.isOnGround() && this.onGround.isEnabled()) {
                return;
            }

            if (RotationUtil.getHitResult(Main.mc.player, false, RotationUtil.getRotation().getYaw(), RotationUtil.getRotation().getPitch()) instanceof EntityHitResult var1
                    && var1.getEntity() instanceof EndCrystalEntity var2
                    && var2.getId() != this.id) {
                this.id = var2.getId();
                Main.mc.interactionManager.attackEntity(Main.mc.player, var2);
                Main.mc.player.swingHand(Hand.MAIN_HAND);
            }

            this.resetBreak();
        }

        if (this.readyToPlace() && this.isObi()) {
            if (RotationUtil.getHitResultBlock(Main.mc.player, false, RotationUtil.getRotation().getYaw(), RotationUtil.getRotation().getPitch(), 4.5) instanceof BlockHitResult var4
                    && Main.mc.world.getBlockState(var4.getBlockPos()).isOf(Blocks.OBSIDIAN)
                    && Main.mc.interactionManager.interactBlock(Main.mc.player, Hand.MAIN_HAND, var4).shouldSwingHand()) {
                Main.mc.player.swingHand(Hand.MAIN_HAND);
            }

            this.resetPlace();
        }
    }

    void tick() {
        this.placeTick++;
        this.breakTick++;
    }

    boolean readyToBreak() {
        return this.breakTick >= MathUtil.getRandomInt((int)this.breakDelay.getDefaultValue(), (int)this.breakDelay.getSecondValue());
    }

    public static void bd() {
        try {
            K = new String[52];
            K[13] = bc("WoVvBBJ3pB5A/o8rGIowS9S6eVmx8tyX8tMlXK7kDiZhzgmZfPNEemQ8POlrvgTx", " j\".�\u0007", L);
            K[14] = bc(
                    "g/15sNtI3U9v+5gbI/3sUNS6eVmx8tyX8tMlXK7kDiaKzNZ9K+DH15i4O7RAgNhyvy82OOK7JgNHzGAMLqZATST653SOpI9SYFQ0cCbIHsUBf6YTfEPQZY9jR4aRdMUj", "xڸ�\tZ", L
            );
            K[0] = bc("tejcO5/LfUduegFTqBhMW9S6eVmx8tyX8tMlXK7kDiYM8yy7ngecU2OfdHEt1zi7", "�l �r�", L);
            K[44] = bc("Bqut1CVK/aVBgUlTOKSuItS6eVmx8tyX8tMlXK7kDiY7YeOtEAlj6V+dwZWt3J3JUEY2y0LlSjoSCdCozgqB8gF9zNaw7In/msgs49d+REk=", "�\"G\n�\u0007", L);
            K[6] = bc("tXxmOIg3JIBgVmRNwws1m9S6eVmx8tyX8tMlXK7kDiZD4XPUnL4FKLz2sxD2YCDD", "���KL�", L);
            K[35] = bc("cX/Bo3/vAb9yqKewlVOUS9S6eVmx8tyX8tMlXK7kDibr2v5VuH+vqhs3Chr3MUcC", "�%�S��", L);
            K[43] = bc("P0KGuYciTFlsiOz9g/gpnNS6eVmx8tyX8tMlXK7kDiaheH2qQK/bBZ7vnm8oXMy/", "Z�/֍�", L);
            K[8] = bc("AgXXq3x0nZNXF90AOkeUJ9S6eVmx8tyX8tMlXK7kDibaMlMoxoSlzX4dSJ1ZLSdy", "����ѕ", L);
            K[33] = bc("JAY3NgP+NT0oDy9yzYNwq9S6eVmx8tyX8tMlXK7kDiaY+8yrDfVFpWtsqGV+gGAz", "��7ݴ9", L);
            K[28] = bc("zzWp3ZqTlvLyLdw1vdnsH9S6eVmx8tyX8tMlXK7kDibdiFfwXI+G6D2Rfg7nCjqV+3tdQHzP9+tSDZTeNgH0pA==", "�ٷ�=�", L);
            K[26] = bc("4oY75y0mgm9cfRNORFPibdS6eVmx8tyX8tMlXK7kDiYDTzzIqCYaRzJZNzAexImE", "\r;ƀ\u0017�", L);
            K[20] = bc("xR9UOUkYCoCBhBLN24fm+dS6eVmx8tyX8tMlXK7kDiaD1977Xqc7Ht3Kp5I3WuTg", "�+\u0004�ܓ", L);
            K[34] = bc("OsHmxYbtVoDaZ5nNsO4auNS6eVmx8tyX8tMlXK7kDiaJMA8CJlZ5NBTySkikZYqi", "������", L);
            K[17] = bc("9ThRrxYTDqHobZghfnAA+tS6eVmx8tyX8tMlXK7kDiZslYwKV/GzuK6NMUBir3MH", "���\u001a#]", L);
            K[1] = bc("1G9CbDlMhWrQOEaDeN5hVdS6eVmx8tyX8tMlXK7kDiZrBxxsJfWuvGmpsVI3ZqWETpHPu2iVYeNiqwZnuD/iFz1yi3IMJI9rd9J1iZdBuso=", "��\u0019h�V", L);
            K[21] = bc("u9ttKLgIS6muPaTMaNJD4tS6eVmx8tyX8tMlXK7kDibB1aJQ4S7uR16MnuNO1tQI", "�#��-", L);
            K[42] = bc("WLhbVyDeSYIrtW3/ZUSu2tS6eVmx8tyX8tMlXK7kDiZVWKNYU5amXUJk4xXT0KNp+d1b3jWYVYxe74a2KAm60w==", "Zz�\u001b�n", L);
            K[23] = bc("EFI3bLa2K9J0Lu810Cirb9S6eVmx8tyX8tMlXK7kDiYLqG3eHCHg3SrSdYhLymI7", "apA\u000eʭ", L);
            K[45] = bc("LDdVatBe1tP42XRbqggl8dS6eVmx8tyX8tMlXK7kDiZfwbUtVf82dpN1GE/O0x0K", "Yi�'i�", L);
            K[18] = bc("9BjQX6gyn4uLq+ruSN1S5NS6eVmx8tyX8tMlXK7kDia+fWvAlqcxOyZs87w6yNc5zs3dI+vrsaKW74W/8j2xg4dLN782FAMIRS1kSydWi7I=", "�Y {\u001a|", L);
            K[24] = bc("I/S+6UTUQYanqxMwvx6WLtS6eVmx8tyX8tMlXK7kDiYRBTZRdB8gH8L1ADJR7bYF", "M�~��T", L);
            K[49] = bc("5FBkHyxhijas/4i2fgo1ONS6eVmx8tyX8tMlXK7kDiY7H+DF/TUKbfW00bFwT+rT", "���t%5", L);
            K[12] = bc("Nl0AIoVyc5E2Uk/UQDss9tS6eVmx8tyX8tMlXK7kDiYMnzpzcZVs3bhni0RssKPk3SllvMYEFNLtB8qMG/doJA==", "B\u001d\u0016�f�", L);
            K[29] = bc("k95G/f1ceBr5OYGOLFkho9S6eVmx8tyX8tMlXK7kDib4GykFJ+ewOnNpO3OL72GS", "�\u007f\u0012S�8", L);
            K[5] = bc("s2INkLMRn6woA3C2oi/iT9S6eVmx8tyX8tMlXK7kDibsXDRgtOf3uwsqN5j1FV9j", "�\\\"�\u0015�", L);
            K[50] = bc("EgJUR0191wnulo4ubdjM9tS6eVmx8tyX8tMlXK7kDiayZ/Z7aHZ1gxFpGrRnpTcW", "F�w\u0007Q\u0000", L);
            K[16] = bc("8DL8/PINzPSBNFI4Cfyws9S6eVmx8tyX8tMlXK7kDiZvSO1NKqqZPbpn4SRa2UZQSIvHKhtadiV9RYRGEt/j2A==", "��f�4\b", L);
            K[41] = bc("RFwWcsqAadxBuNgl0tTbg9S6eVmx8tyX8tMlXK7kDiY1Y/YK0amvUqm1FrVQcwt7", "�4C���", L);
            K[9] = bc("Tz5tCTXy3JoshD1C7huy39S6eVmx8tyX8tMlXK7kDiae0ZjnaR+rLdPw1+zP2P9g", "���D�d", L);
            K[3] = bc("Cu9LMAgoKbOg3lJ0UbU/WdS6eVmx8tyX8tMlXK7kDiZiNqP1BVA+Sh6288szLpKa", "Q����\u0001", L);
            K[36] = bc(
                    "S0KOHiUkKjHMSj1zsDVrR9S6eVmx8tyX8tMlXK7kDiaYMpheO59AI6JE7gE0YDZOTg1tBWdh2U+xV4dBPnBBIT0Dfkb/+uKVCjK/MT7p3taP7maWWjvq+9SWFVuwsb8B", "ʅ7\u0000\t�", L
            );
            K[15] = bc("64eWTrYEjMdSVw4ZAU40SdS6eVmx8tyX8tMlXK7kDiauZOeXLPOZZqalEVdI4LR1", "f|NŅ�", L);
            K[25] = bc("9A6a73H36bkYLVgokpWTK9S6eVmx8tyX8tMlXK7kDiaiCOjFgjP7pJe6Pu/D0avA", "'e\u001a\b'\u0016", L);
            K[51] = bc("uxWudYzK9iphHOu0nIacptS6eVmx8tyX8tMlXK7kDiYZOm53xjXvS/7ohDblWfxr", "#ܜ\u0007g;", L);
            K[4] = bc("yeedK1fecdN1cmzeT587W9S6eVmx8tyX8tMlXK7kDiZjKsgqpBEoWcQ4SB1nl6b3JNBUzU0/nd8MHXbJJKUFVMBICm7jihQ4PN+q5swwk8Y=", "�N���Y", L);
            K[2] = bc("Uk/fjG3yzM5poCJXGeG5n9S6eVmx8tyX8tMlXK7kDibczjj0UzjTYA4RWx9nOIT9C/ZxU1ioNQp9Opn8V8c82w==", "�\u00028�\u00014", L);
            K[22] = bc("GigPAHi0xL/gzBtqj5Ejg9S6eVmx8tyX8tMlXK7kDiY7AsUsekiv8In17f5IvO1D", "\u0013��KYR", L);
            K[39] = bc("0jAooJWduXoZVDfaVo35OdS6eVmx8tyX8tMlXK7kDiZKl4rFUFt2ymwRfCvPh2qE", "+���\u0002�", L);
            K[7] = bc("L0n0TYC3EGha9Vlr5Ig+ZtS6eVmx8tyX8tMlXK7kDibmDa/qSibJwMRZzVM/ZaYl", "K���N\"", L);
            K[11] = bc("CdPZ4scr45vCC+lyKIq0edS6eVmx8tyX8tMlXK7kDibfIxypNx2PB+ppUKpEVApT", "69W\u0011I�", L);
            K[48] = bc("ysaKOaQdTpjpSGXfIowYONS6eVmx8tyX8tMlXK7kDiZspC0QokGwmEoRPr7Rs56n", "\t��st�", L);
            K[47] = bc("rH8Q9xCNLemgg7eEcCCtB9S6eVmx8tyX8tMlXK7kDiaVbFfvHwfPd0u2lyiLPnQE", "�4��\t�", L);
            K[40] = bc(
                    "otOKTb3/5DRcIoWsaO8Kx9S6eVmx8tyX8tMlXK7kDiZcsidSHjUZJnLaNjgUrGlHkLCfMThcbnBcn+8ac+3YyvZzbcH8yahmwkXZhlvJcwqwyyeBaMEeWx7ZHU28dUr3", "�*;v��", L
            );
            K[19] = bc("1miFTFIId0MN527yHPyLO9S6eVmx8tyX8tMlXK7kDiY9Yd6CewNVyb1ULm1jEaYK", "�s�ڿ", L);
            K[10] = bc(
                    "dPRR3tMPNpuvwU8E0sGD7NS6eVmx8tyX8tMlXK7kDiazUMd0rgePVIkiKyIpzY0G5ScdxFVQp6rHMVB+/+6seUDSW34AjG3J7PGyrk8x4r2vpV6lDKrVAErzNz6bW3p4", "b,4�c�", L
            );
            K[30] = bc("GFRUxy3jkN6xUjqMOvQyddS6eVmx8tyX8tMlXK7kDia9ut3bghD4YGaTdCt2gL2WQllvY0V9oxPM5HmFRmstXn+r8nK6BdY9955UkBsDE0o=", "=\u0001\u0006��<", L);
            K[32] = bc("G112PncZpQixs9SVikVZy9S6eVmx8tyX8tMlXK7kDiZwxRgYBRPHHtb6mg/XMS+t", "�\u000e-ҽ!", L);
            K[37] = bc("7Sr0HmaF8CPbkZtgmHMU3NS6eVmx8tyX8tMlXK7kDibVlP/fy7sFoZdZ71emEm2K", "��&�\u0010f", L);
            K[27] = bc("+8MXLY3T5uakND0/iloJv9S6eVmx8tyX8tMlXK7kDia1MzNxEwkaUVp3Mpk3C9q/4YhgCvu1ep/8FgXsqHsYgRyPve+EQX+1bz2mi2XDMak=", "���rVk", L);
            K[46] = bc("YC1ZpL186MxAx3eJSAC2ztS6eVmx8tyX8tMlXK7kDiawTRwde3g9zK8TfRWYzGvK", "=�f\n��", L);
            K[31] = bc("0Hu3acJNZqSgTDGbBhN4G9S6eVmx8tyX8tMlXK7kDiZYhO5lL4baaQ0krVQV7/0Y", "���)Y�", L);
            K[38] = bc("1QSZ/ZsvU+PQ//Ot6KPQRdS6eVmx8tyX8tMlXK7kDibhkhWw3kiMNyiUM2CekFz0tKkREmYF1tS6RPLZH76qPg==", "���\u0082�", L);
        } catch (Exception e) {

        }
    }

    public static String bc(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{89, 24, -44, 88, 105, 65, 100, 0, 50, 54, 119, 116, 22, -1, 40, -124};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 84, 256);
        SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] var8 = var7.generateSecret(var6).getEncoded();
        SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
        Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var10.init(2, var9, new IvParameterSpec(var2));
        byte[] var11 = var10.doFinal(var5);
        String info = new String(var11, "UTF-8");
        return info;
    }

    boolean readyToPlace() {
        return this.placeTick >= MathUtil.getRandomInt((int)this.placeDelay.getDefaultValue(), (int)this.placeDelay.getSecondValue());
    }

    @EventHandler
    public void onUpdateMouse(UpdateMouseEvent event) {
        if (!nullCheck()) {
            this.setDetail(this.mode.getMode());
            if (this.mode.getMode().equals(K[46])) {
                if (this.target == null) {
                    for (Entity var3 : Main.mc.world.getEntities()) {
                        if (var3 instanceof EndCrystalEntity) {
                            EndCrystalEntity var4 = (EndCrystalEntity)var3;
                            if (var3.distanceTo(Main.mc.player) <= 3.0F) {
                                this.target = var4;
                            }
                        }
                    }

                    if (this.target == null) {
                        Iterator var9 = this.findObsidianAroundPlayer(4.5).iterator();

                        while (true) {
                            if (var9.hasNext()) {
                                BlockPos var12 = (BlockPos)var9.next();
                                if (var12 == null) {
                                    continue;
                                }

                                this.blockPos = var12;
                            }

                            if (this.blockPos == null) {
                                RotationManager.INSTANCE.setEnabled(false);
                                return;
                            }

                            if (new Box(this.blockPos).getCenter().distanceTo(Main.mc.player.getPos()) > 4.5) {
                                RotationManager.INSTANCE.setEnabled(false);
                                return;
                            }

                            if (this.blockPos == null || this.crystaling) {
                                RotationManager.INSTANCE.setEnabled(false);
                                return;
                            }

                            Vec3d var10 = this.blockPos.toBottomCenterPos().add(0.0, !Main.mc.player.isSneaking() ? -1.5 : -1.0, 0.0);
                            Rotation var13 = RotationUtil.getDirection(Main.mc.player, var10);
                            Rotation var15 = new Rotation(RotationManager.INSTANCE.getServerYaw(), RotationManager.INSTANCE.getServerPitch());
                            float var5 = MathUtil.getRandomFloat(25.0F, 50.0F) / 500.0F;
                            float var6 = MathUtil.getRandomFloat(25.0F, 50.0F) / 500.0F;
                            float var7 = MathHelper.lerpAngleDegrees(var5, var15.getYaw(), var13.getYaw());
                            float var8 = MathHelper.lerpAngleDegrees(var6, var15.getPitch(), var13.getPitch());
                            if (RotationUtil.getAngleToLookVecByRot(Main.mc.player.getYaw(), Main.mc.player.getPitch(), new Box(this.blockPos).getCenter())
                                    < 180.0) {
                                if (!RotationManager.INSTANCE.isEnabled()) {
                                    RotationManager.INSTANCE.setEnabled(true);
                                }

                                RotationManager.INSTANCE.setCurrentRotation(new Rotation(var7, var8));
                                RotationManager.INSTANCE.setServerRotation(new Rotation(var7, var8));
                                return;
                            }

                            RotationManager.INSTANCE.setEnabled(false);
                            break;
                        }
                    }
                }

                if (this.target == null || !this.crystaling) {
                    return;
                }

                if (this.target.distanceTo(Main.mc.player) > 3.0F || Main.mc.player.isDead()) {
                    this.target = null;
                    RotationManager.INSTANCE.setEnabled(false);
                    return;
                }

                Vec3d var11 = this.target.getPos().add(0.0, !Main.mc.player.isSneaking() ? -1.5 : -1.0, 0.0);
                Rotation var14 = RotationUtil.getDirection(Main.mc.player, var11);
                Rotation var16 = new Rotation(RotationManager.INSTANCE.getServerYaw(), RotationManager.INSTANCE.getServerPitch());
                float var17 = MathUtil.getRandomFloat(25.0F, 50.0F) / 500.0F;
                float var18 = MathUtil.getRandomFloat(25.0F, 50.0F) / 500.0F;
                float var19 = MathHelper.lerpAngleDegrees(var17, var16.getYaw(), var14.getYaw());
                float var20 = MathHelper.lerpAngleDegrees(var18, var16.getPitch(), var14.getPitch());
                if (!(
                        RotationUtil.getAngleToLookVecByRot(Main.mc.player.getYaw(), Main.mc.player.getPitch(), this.target.getBoundingBox().getCenter()) < 180.0
                )) {
                    this.target = null;
                    RotationManager.INSTANCE.setEnabled(false);
                } else {
                    if (!RotationManager.INSTANCE.isEnabled()) {
                        RotationManager.INSTANCE.setEnabled(true);
                    }

                    RotationManager.INSTANCE.setCurrentRotation(new Rotation(var19, var20));
                    RotationManager.INSTANCE.setServerRotation(new Rotation(var19, var20));
                }
            }
        }
    }

    static {
        be();
        bd();
    }

    public List<BlockPos> findObsidianAroundPlayer(double radius) {
        ArrayList var3 = new ArrayList();
        if (nullCheck()) {
            return var3;
        } else {
            ClientWorld var4 = Main.mc.world;
            BlockPos var5 = Main.mc.player.getBlockPos();
            int var6 = (int)Math.ceil(radius);

            for (int var7 = -var6; var7 <= var6; var7++) {
                for (int var8 = -var6; var8 <= var6; var8++) {
                    double var9 = (double)(var7 * var7 + var8 * var8);
                    if (!(var9 > radius * radius)) {
                        for (int var11 = -2; var11 <= 2; var11++) {
                            BlockPos var12 = var5.add(var7, var11, var8);
                            if (var4.getBlockState(var12).getBlock() == Blocks.OBSIDIAN) {
                                var3.add(var12);
                            }
                        }
                    }
                }
            }

            return var3;
        }
    }

    public CrystalClicker() {
        super(K[26], K[27], Category.Combat);
        this.bind = new KeyBindSetting(K[28], 0, false);
        this.mode = new ModeSetting(K[29], K[30], K[31], new String[]{K[32], K[33], K[34]});
        this.placeDelay = new RangeSetting(K[35], K[36], 3.0, 4.0, 0.0, 10.0, 1.0, 4.0);
        this.breakDelay = new RangeSetting(K[37], K[38], 3.0, 4.0, 0.0, 10.0, 1.0, 4.0);
        this.pauseOnKill = new BoolSetting(K[39], K[40], false);
        this.onGround = new BoolSetting(K[41], K[42], false);
        this.noWeak = new BoolSetting(K[43], K[44], false);
        this.timer = new TimerUtil();
        this.addSettings(new Setting[]{this.bind, this.mode, this.placeDelay, this.breakDelay, this.pauseOnKill, this.onGround, this.noWeak});
    }

    void silentCrystal() {
        Main.mc.options.useKey.setPressed(false);
        if (this.readyToBreak()) {
            if (!Main.mc.player.isOnGround() && this.onGround.isEnabled()) {
                return;
            }

            if (RotationUtil.getHitResult(Main.mc.player, false, RotationManager.INSTANCE.getServerYaw(), RotationManager.INSTANCE.getServerPitch()) instanceof EntityHitResult var1
                    && var1.getEntity() instanceof EndCrystalEntity var2) {
                if (!Main.mc.player.isOnGround() && this.onGround.isEnabled()) {
                    return;
                }

                Main.mc.interactionManager.attackEntity(Main.mc.player, var2);
                Main.mc.player.swingHand(Hand.MAIN_HAND);
                this.crystaling = false;
                this.resetBreak();
            }
        }

        if (this.readyToPlace()
                && this.blockPos != null
                && new BlockHitResult(new Box(this.blockPos).getCenter(), Direction.UP, this.blockPos, false) instanceof BlockHitResult var5
                && var5.getType() != Type.MISS
                && RotationUtil.canPlaceCrystalServer(var5.getBlockPos())
                && Main.mc.interactionManager.interactBlock(Main.mc.player, Hand.MAIN_HAND, var5).shouldSwingHand()) {
            Main.mc.player.swingHand(Hand.MAIN_HAND);
            this.crystaling = true;
            this.resetPlace();
        }
    }

    @EventHandler
    public void onItemUse(bre2el.fpsreducer.event.impl.ItemUseEvent.Pre event) {
        if (this.mode.getMode().equals(K[47]) && RotationUtil.checkRotations()) {
            event.cancel();
        }
    }

    void resetPlace() {
        this.placeTick = 0;
    }

    @Override
    public void onDisable() {
        this.target = null;
        this.blockPos = null;
        RotationManager.INSTANCE.setEnabled(false);
        super.onDisable();
    }

    boolean isObi() {
        if (RotationUtil.getHitResultBlock(Main.mc.player, false, RotationUtil.getRotation().getYaw(), RotationUtil.getRotation().getPitch(), 4.5) instanceof BlockHitResult var1
                && (
                Main.mc.world.getBlockState(var1.getBlockPos()).isOf(Blocks.OBSIDIAN)
                        || Main.mc.world.getBlockState(var1.getBlockPos()).isOf(Blocks.BEDROCK)
        )) {
            return true;
        }

        return false;
    }

    void normalCrystal() {
        Main.mc.options.useKey.setPressed(false);
        if (this.readyToPlace() && this.isObi()) {
            InputUtil.sendInput(1, 1);
            this.resetPlace();
            this.placed = true;
        }

        if (this.readyToBreak() && this.isCrystal()) {
            if (!Main.mc.player.isOnGround() && this.onGround.isEnabled()) {
                return;
            }

            if (this.noWeak.isEnabled() && Main.mc.player.hasStatusEffect(StatusEffects.WEAKNESS)) {
                for (int var1 = 0; var1 < 9; var1++) {
                    if (Main.mc.player.getInventory().getStack(var1).getItem() instanceof SwordItem) {
                        Main.mc.player.getInventory().selectedSlot = var1;
                        break;
                    }
                }
            }

            if (ModuleManager.INSTANCE.getModuleByName(K[51]).isEnabled() && ModuleManager.INSTANCE.getModuleClass(BackPlace.class).backBreak.isEnabled()) {
                if (RotationUtil.getHitResultIgnorePlayers(Main.mc.player, false, Main.mc.player.getYaw(), Main.mc.player.getPitch(), 3.0) instanceof EntityHitResult var4
                        && var4.getEntity() instanceof EndCrystalEntity var2) {
                    Main.mc.interactionManager.attackEntity(Main.mc.player, var2);
                    Main.mc.player.swingHand(Hand.MAIN_HAND);
                }
            } else {
                InputUtil.sendInput(0, 1);
            }

            this.resetBreak();
            this.broke = true;
        }
    }

    void resetBreak() {
        this.breakTick = 0;
    }
}
