package bre2el.fpsreducer.feature.module.modules.utility;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.event.impl.UpdateEvent.Pre;
import bre2el.fpsreducer.event.orbit.EventHandler;
import bre2el.fpsreducer.feature.module.Module;
import bre2el.fpsreducer.feature.module.ModuleManager;
import bre2el.fpsreducer.feature.module.Module.Category;
import bre2el.fpsreducer.feature.module.setting.KeyBindSetting;
import bre2el.fpsreducer.feature.module.setting.ModeSetting;
import bre2el.fpsreducer.feature.module.setting.Setting;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

public class Teams extends Module {
    public static byte[] cv;
    public static String[] cu;
    public ModeSetting mode;
    public String currentMode;
    public KeyBindSetting bind;
    public static List<PlayerEntity> teams = new ArrayList();

    static {
        dG();
        dF();
    }

    public static String dE(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{57, 121, 95, 19, 67, -2, -112, 115, 0, 95, 49, 57, 111, 127, -17, 44};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 91, 256);
        SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] var8 = var7.generateSecret(var6).getEncoded();
        SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
        Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var10.init(2, var9, new IvParameterSpec(var2));
        byte[] var11 = var10.doFinal(var5);
        String info = new String(var11, "UTF-8");
        return info;
    }

    public static void dF() {
        try {
            cu = new String[24];
            cu[0] = dE("AnZzBGEw9u/XbMSqKAUdGdf5IzVyAmNvF1rHToQhhDEbTAIrq9Ln/LyZQozz2I7B", "���K��", cv);
            cu[15] = dE("28i8QbquYUCIEPT20MGbGdf5IzVyAmNvF1rHToQhhDEmUUi4abW8okHntSi+z0Gl", "\"��g\u001e", cv);
            cu[21] = dE("CydWCBIDsV5KOWYYx9IQxdf5IzVyAmNvF1rHToQhhDFGTACTVktbkt9f0NJTqK/p", "����\u0013", cv);
            cu[18] = dE("JCT08VZRKWVoguj9jCTO+Nf5IzVyAmNvF1rHToQhhDGgjGHg8QCKH6K+qM0rh6Yr", "\u0001p�0�\u0019", cv);
            cu[7] = dE("8Q7s04FaqYZnpkG/kQsx7df5IzVyAmNvF1rHToQhhDGj8IYrALRGP3UhYaaR/UoM", "\u0013�\rD��", cv);
            cu[4] = dE("i6z1jYE6CHKgnBdLGTYTYNf5IzVyAmNvF1rHToQhhDHiRh85gOjrwfLK9DL5Z+LrdE3eaPUpbfN2bjMkMLcemQ==", "�y����", cv);
            cu[19] = dE("t6GuJCFxBwQ+Mp4qMRO2Ntf5IzVyAmNvF1rHToQhhDHszQs2/D6yWVdxkLU877rs", "?g��6V", cv);
            cu[12] = dE("CH715ARsdXezVMm8MhWusNf5IzVyAmNvF1rHToQhhDEE527zhHK0RmLyxLOeFyCH", "\u000b\u0003J\u0012��", cv);
            cu[16] = dE("Pm9aIvT7UxALYXyGaBl2INf5IzVyAmNvF1rHToQhhDHA9OpfKclCL+XyoQy8MGSTgi612Lj0f8BLmvYYGTtRnA==", "��R�\u0016Y", cv);
            cu[23] = dE("qZQUWoQs1R2yBjzEG3NFMtf5IzVyAmNvF1rHToQhhDFXXAJRs1/leJHq3VWyMJDG", "�&I�Fv", cv);
            cu[1] = dE("UmiVQUw5uSqLOG8haM1q7df5IzVyAmNvF1rHToQhhDGo4R/sN/5vuC9nUOofmkyGTbSv6N0snsQOKC60pADqtw==", "V�)��", cv);
            cu[20] = dE("mPzxvE6QFJjMoVxZ++f0Q9f5IzVyAmNvF1rHToQhhDEyc90L8MS8bOpiAJZH3w5T", "\"�-��", cv);
            cu[2] = dE("/sOUnZAfic19+mPFLjB/dtf5IzVyAmNvF1rHToQhhDFgqtsTYKBJGEoTlzuRyuzJKOXoBL6kQaIVpNyqvG45Rw==", "\u001b�\u000b�P�", cv);
            cu[8] = dE("loyCp+pEDp1gbNJeUTCNDtf5IzVyAmNvF1rHToQhhDH9EOdnpRJvgJw8sLmu+o7V", "V\u000b�r�^", cv);
            cu[9] = dE("YWWO6V70JsnkBfc2oY4ujNf5IzVyAmNvF1rHToQhhDHYvFFfC+OQG4qeDUrFpsO1", ".�t���", cv);
            cu[13] = dE("npGQlV7h5825vc26qvyHxNf5IzVyAmNvF1rHToQhhDHdWnTT6IYIwd4gL8pw9c4GWUiw7PHlTMSRzavkrF02Qg==", "bמq\u001fj", cv);
            cu[3] = dE("u9U5GMgGupUFUf5vviPxHtf5IzVyAmNvF1rHToQhhDGMjNw8C9BSLcehNssCyNJA", "�_��\u0016(", cv);
            cu[10] = dE("ScLTDbFyO+Sq2j8y63aokdf5IzVyAmNvF1rHToQhhDGTK4ZVoLJAa3rFOUrT+YvG", "�DO��M", cv);
            cu[5] = dE("fybqTRsFYa6hgLqBL0e1Ytf5IzVyAmNvF1rHToQhhDFNVZTfuXbh6lG50/D7ADYu", "��Ρ\b�", cv);
            cu[6] = dE("WQcMxNiH6Coq6yxuHm9/W9f5IzVyAmNvF1rHToQhhDGt+PplABcUYp5Ge7WGonHB", "Z���G�", cv);
            cu[14] = dE("VcKf14cHnwlLgNIRQfeqJ9f5IzVyAmNvF1rHToQhhDGZTJbC6FzOWdyEO84DfDPQjM3XiPepjyMalDdkoOLPNA==", ">�È�", cv);
            cu[22] = dE("eoxwrgmsdHkMscz++CRNE9f5IzVyAmNvF1rHToQhhDH48EbnzCJfTHtqR21rylaa", "=S\u000f��\u0017", cv);
            cu[17] = dE("fTaC7xsPopSkFttQePLJlNf5IzVyAmNvF1rHToQhhDE5mEoWw6xjWE4xkkH5+a3K", "\f�\u000fxr�", cv);
            cu[11] = dE("j5slBi0dMvMjNnE0JBysztf5IzVyAmNvF1rHToQhhDF5MeJTTXX1puikhSPljkLA", "=#\u0011�-c", cv);
        } catch (Exception e) {

        }
    }

    void reset() {
        if (!nullCheck()) {
            teams.clear();
        }
    }

    public Teams() {
        super(cu[12], cu[13], Category.Utility);
        this.bind = new KeyBindSetting(cu[14], 0, false);
        this.mode = new ModeSetting(cu[15], cu[16], cu[17], new String[]{cu[18], cu[19], cu[20]});
        this.addSettings(new Setting[]{this.bind, this.mode});
    }

    public static void dG() {
        cv = new byte[16];
        cv[8] = 23;
        cv[12] = -124;
        cv[9] = 90;
        cv[3] = 53;
        cv[2] = 35;
        cv[1] = -7;
        cv[7] = 111;
        cv[0] = -41;
        cv[15] = 49;
        cv[5] = 2;
        cv[6] = 99;
        cv[4] = 114;
        cv[10] = -57;
        cv[14] = -124;
        cv[11] = 78;
        cv[13] = 33;
    }

    public static boolean isTeam(Entity e) {
        return !(e instanceof PlayerEntity) ? false : teams.contains(e) && ModuleManager.INSTANCE.getModuleByName(cu[21]).isEnabled();
    }

    @EventHandler
    public void onUpdate(Pre event) {
        this.setDetail(this.mode.getMode());
        if (!nullCheck()) {
            if (!Objects.equals(this.currentMode, this.mode.getMode())) {
                this.reset();
            }

            for (PlayerEntity var3 : Main.mc.world.getPlayers()) {
                if (!Objects.equals(this.mode.getMode(), cu[22])) {
                    if (!Objects.equals(this.mode.getMode(), cu[23])) {
                        if (!var3.isGlowing()) {
                            teams.remove(var3);
                        } else if (!teams.contains(var3)) {
                            teams.add(var3);
                        }
                    } else if (var3.getTeamColorValue() != Main.mc.player.getTeamColorValue()) {
                        if (var3.getTeamColorValue() != Main.mc.player.getTeamColorValue()) {
                            teams.remove(var3);
                        }
                    } else if (!teams.contains(var3)) {
                        teams.add(var3);
                    }
                } else if (var3.getScoreboardTeam() != Main.mc.player.getScoreboardTeam()) {
                    if (var3.getScoreboardTeam() != Main.mc.player.getScoreboardTeam()) {
                        teams.remove(var3);
                    }
                } else if (!teams.contains(var3)) {
                    teams.add(var3);
                }

                this.currentMode = this.mode.getMode();
            }
        }
    }

    @Override
    public void onDisable() {
        this.reset();
        super.onDisable();
    }
}
