package bre2el.fpsreducer.feature.module.modules.utility;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.event.impl.UpdateEvent.Pre;
import bre2el.fpsreducer.event.orbit.EventHandler;
import bre2el.fpsreducer.feature.module.Module;
import bre2el.fpsreducer.feature.module.ModuleManager;
import bre2el.fpsreducer.feature.module.Module.Category;
import bre2el.fpsreducer.feature.module.setting.ActionSetting;
import bre2el.fpsreducer.feature.module.setting.BoolSetting;
import bre2el.fpsreducer.feature.module.setting.KeyBindSetting;
import bre2el.fpsreducer.feature.module.setting.KeySetting;
import bre2el.fpsreducer.feature.module.setting.Setting;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.EntityHitResult;

public class Friends extends Module {
    public static byte[] ed;
    public ActionSetting clear;
    public KeySetting key;
    public static String[] ec;
    public KeyBindSetting bind;
    public int timer2;
    public BoolSetting noDamage;
    public static List<String> friends = new ArrayList();
    public PlayerEntity currentTarget;
    public int timer;

    public static boolean isFriend(Entity e) {
        return !(e instanceof PlayerEntity) ? false : checkFriend((PlayerEntity)e) && ModuleManager.INSTANCE.getModuleByName(ec[19]).isEnabled();
    }

    public static void removeFriend(PlayerEntity player) {
        if (isFriend(player)) {
            friends.remove(player.getUuidAsString());
        }
    }

    public static String gd(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{117, -79, 33, 27, -9, 26, -79, 67, -101, 0, 18, 26, -103, 84, -45, -112};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 149, 256);
        SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] var8 = var7.generateSecret(var6).getEncoded();
        SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
        Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var10.init(2, var9, new IvParameterSpec(var2));
        byte[] var11 = var10.doFinal(var5);
        String info = new String(var11, "UTF-8");
        return info;
    }

    public Friends() {
        super(ec[10], ec[11], Category.Utility);
        this.bind = new KeyBindSetting(ec[12], 0, false);
        this.key = new KeySetting(ec[13], ec[14], 2);
        this.clear = new ActionSetting(ec[15], ec[16]);
        this.noDamage = new BoolSetting(ec[17], ec[18], false);
        this.currentTarget = null;
        this.addSettings(new Setting[]{this.bind, this.key, this.clear, this.noDamage});
    }

    public static void gf() {
        ed = new byte[16];
        ed[3] = -43;
        ed[11] = 6;
        ed[1] = -39;
        ed[2] = -63;
        ed[6] = 36;
        ed[4] = 109;
        ed[9] = 58;
        ed[15] = 122;
        ed[14] = 18;
        ed[10] = -25;
        ed[5] = 57;
        ed[0] = 1;
        ed[7] = -35;
        ed[13] = -15;
        ed[8] = -59;
        ed[12] = 3;
    }

    public static void addFriend(PlayerEntity player) {
        if (!isFriend(player)) {
            friends.add(player.getUuidAsString());
        }
    }

    @EventHandler
    public void onUpdate(Pre event) {
        if (!nullCheck()) {
            if (this.clear.isActivated()) {
                friends.clear();
                this.clear.setActivated(false);
            }

            if (this.timer > 0) {
                this.timer2++;
            }

            if (this.timer2 > 19) {
                this.timer = 0;
                this.timer2 = 0;
            }

            if (Main.mc.crosshairTarget instanceof EntityHitResult var2 && var2.getEntity() instanceof PlayerEntity var3 && !AntiBot.isBot(var2.getEntity())) {
                if (this.currentTarget != var3) {
                    this.currentTarget = var3;
                    this.timer = 0;
                    this.timer2 = 0;
                }

                if (this.key.isActive(this.key.getKey()) && this.timer == 0) {
                    if (!isFriend(var3)) {
                        addFriend(var3);
                    } else {
                        removeFriend(var3);
                    }

                    this.timer++;
                }
            }
        }
    }

    static {
        gf();
        ge();
    }

    @EventHandler
    void onAttack(bre2el.fpsreducer.event.impl.AttackEvent.Pre event) {
        if (!nullCheck()) {
            if (this.noDamage.isEnabled()
                    && Main.mc.crosshairTarget instanceof EntityHitResult var2
                    && var2.getEntity() instanceof PlayerEntity var3
                    && isFriend(var3)) {
                event.cancel();
            }
        }
    }

    public static void addFriend(String uuid) {
        if (!friends.contains(uuid)) {
            friends.add(uuid);
        }
    }

    public static void ge() {
        try {
            ec = new String[20];
            ec[2] = gd("VZXfqfopvGE+1541j0yOzwHZwdVtOSTdxTrnBgPxEnp8+2kKjm7KSCXwnwekejDitzD81srR++ZN4/7baHJuCw==", "�Ͼ���", ed);
            ec[8] = gd("Eaw9z1DkKb1l6mNixf7IjgHZwdVtOSTdxTrnBgPxEnqP2ayXkiaCtqWC//z0ISksPpHHvSp0ghdW+mvTIS/Psw==", "\u0002rV�N�", ed);
            ec[11] = gd("NtdHOROGTdDWgU7qQbvTiAHZwdVtOSTdxTrnBgPxEnoe8idP+mURFdR16wQHbQkP", "��.]�%", ed);
            ec[13] = gd("8ZOSBfTXouBl+/lM6EDmJgHZwdVtOSTdxTrnBgPxEnpWuSLRoO35G8auZkPF6PVa", "Յ\r%\u0011n", ed);
            ec[14] = gd("RYMV8RpcrJsby6yhxpfJFQHZwdVtOSTdxTrnBgPxEnr955rFr/AeFb5+lTQTB48oKvlXJURFOXu6XkC7pqs6yA==", "���h��", ed);
            ec[7] = gd("WDmbgUX22v3DGxREnqEpGQHZwdVtOSTdxTrnBgPxEnpnXlq3DES3TG6QpW4M7Dqo", "T����\u007f", ed);
            ec[0] = gd("GX3CmgXxj4aClMvrBJm4tgHZwdVtOSTdxTrnBgPxEnpz5TCspiamr1dlbrOLeTZR", "�\u0005A�'%", ed);
            ec[12] = gd("ry6it8oGO2jS8o/kr+sreAHZwdVtOSTdxTrnBgPxEnpZx/XdV5CqX5qJ7w1ZS8RTM9K4dWXw8oWZWbEJrrTCFA==", "�\u0002c�A�", ed);
            ec[19] = gd("BT9QJRhmnNTecXfoJUBBTwHZwdVtOSTdxTrnBgPxEnoQbqvZu9By8utXxrW06kjc", "�Ff�z+", ed);
            ec[4] = gd("Kiolh+p0VNANAgElOVDIYQHZwdVtOSTdxTrnBgPxEnpFKdxe35m7mzj70o4k3aHpWraIwJmOglxkbqPStHiBNw==", "6\u0002�?��", ed);
            ec[10] = gd("XY92LCGa7SZfrAXyYkGs2AHZwdVtOSTdxTrnBgPxEnpT3X1de3mbZTy4Y8AbO4Uw", "\u0019��=2", ed);
            ec[16] = gd("16N230jRb7/upHlpeonVWAHZwdVtOSTdxTrnBgPxEnq9DueVaUnGCrBnj4R2fMPQ71nNMtpxRJ2q4yWOlFz235kPn0fJjo1KttBp8ca2xWo=", "��|��\u0019", ed);
            ec[17] = gd("QDg3wEoG5pAVRD2VnbRi8AHZwdVtOSTdxTrnBgPxEnqup0EOL8JDBUPGIuG+kuaO", "\u0004;�� �", ed);
            ec[3] = gd("klbGWXgg+X2n6nk71ufY/QHZwdVtOSTdxTrnBgPxEnoNOX4XuxjaAT0cLDFRV++I", "��\u0017ڒU", ed);
            ec[9] = gd("4mvoi7Y7cMRqhtcE51dMBwHZwdVtOSTdxTrnBgPxEnqD9h7MXAyC/UK2Wk2QxF38", "@�V��c", ed);
            ec[15] = gd("S4LPzsRPpz07Ou+RMf5xOgHZwdVtOSTdxTrnBgPxEnqgsL6mBxPvEbkvCN0wiSYp", "��~�R,", ed);
            ec[18] = gd("LNS9Bw5TO711k3YNxATr0AHZwdVtOSTdxTrnBgPxEnrpfoS5pt0+ISHwns10vPtK4K6mCwVvF9A51u96rR207Q==", "�\u0007���\u007f", ed);
            ec[1] = gd("zQyTcY/S1pSI6dbPTcL8aQHZwdVtOSTdxTrnBgPxEnrZKcF9jy2awl+ORjVSLzfV", "�O�\u001c��", ed);
            ec[6] = gd("sSBPP7i+FlbcAywUbmtuHgHZwdVtOSTdxTrnBgPxEnq1jbG5JrHCUl4H+plPati50mMrSKJqgwLIvc6efuU3RR8fFPG+2KJqchsXNioMzcw=", "�\"Q�EY", ed);
            ec[5] = gd("lRjx3e3DrX6k38U6HXMLgwHZwdVtOSTdxTrnBgPxEnp1oNxdpC/siFuGSLeKVSs1", "�\u0014L��a", ed);
        } catch (Exception e) {

        }
    }

    @Override
    public void onDisable() {
        for (String var2 : friends) {
            friends.remove(var2);
        }

        super.onDisable();
    }

    public static boolean checkFriend(PlayerEntity player) {
        return friends.contains(player.getUuidAsString());
    }
}
