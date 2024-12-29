package bre2el.fpsreducer.feature.module.modules.combat;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.event.impl.UpdateMouseEvent;
import bre2el.fpsreducer.event.impl.PacketEvent.Receive;
import bre2el.fpsreducer.event.orbit.EventHandler;
import bre2el.fpsreducer.feature.module.Module;
import bre2el.fpsreducer.feature.module.Module.Category;
import bre2el.fpsreducer.feature.module.setting.BoolSetting;
import bre2el.fpsreducer.feature.module.setting.KeyBindSetting;
import bre2el.fpsreducer.feature.module.setting.RangeSetting;
import bre2el.fpsreducer.feature.module.setting.Setting;
import bre2el.fpsreducer.util.MathUtil;
import bre2el.fpsreducer.util.PlayerUtil;
import bre2el.fpsreducer.util.RotationUtil;
import bre2el.fpsreducer.util.TimerUtil;
import java.util.Base64;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntityPositionS2CPacket;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;

public class Backtrack extends Module {
    public KeyBindSetting bind;
    public Queue<Packet> packets;
    public static byte[] fp;
    public BoolSetting weapon;
    public TimerUtil timer;
    public BoolSetting flushOnHurt;
    public static String[] fo;
    public boolean lag;
    public RangeSetting delay;
    public TimerUtil hurtTimer;

    public static String hV(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{66, -98, 5, -68, -6, -67, -57, -70, 13, -103, -21, -46, 32, 0, 14, 39};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 178, 256);
        SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] var8 = var7.generateSecret(var6).getEncoded();
        SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
        Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var10.init(2, var9, new IvParameterSpec(var2));
        byte[] var11 = var10.doFinal(var5);
        return new String(var11, "UTF-8");
    }

    public static void hW() {
        try {
            fo = new String[18];
            fo[8] = hV("djfcdqqhHLT5NVb8sx3nUSS35kc62wBGcgT/yVvzP8wWfphK2wZ+YsVs3avYaP+XM9Z3ot+DYDjo4ria94gryQ==", "\u0015�V�\u007f�", fp);
            fo[0] = hV("RpcVCBGpNJEObn4DRkgwnyS35kc62wBGcgT/yVvzP8zLtxyte7E5KmfnUmBoM09K", "���\u0003Щ", fp);
            fo[16] = hV("S0zsOGfW6gbV5QhXmfx2TyS35kc62wBGcgT/yVvzP8y1xZezqzQPFYgg/5B44lY5", "�b\u0004`�\u0005", fp);
            fo[2] = hV("OQGgkWq0hgooUxJVr3TuqyS35kc62wBGcgT/yVvzP8xSRzJCnayECK9RUMijtIMmfiGSoEbqvP0yLLBduUXOaQ==", "�\u00078\u0006��", fp);
            fo[12] = hV("9c30Zk+14zk8jc1iayDCOSS35kc62wBGcgT/yVvzP8yh2NWG55GWHhx+Re+HE7m5", "8�3[�\u001d", fp);
            fo[5] = hV("gICD7uuDweyqNCIkm3ZvMCS35kc62wBGcgT/yVvzP8wXQGQ0zVEYvlXk72EVgjxc", ";R&�", fp);
            fo[14] = hV("5NDnKJBgwMgkEups7DjyYSS35kc62wBGcgT/yVvzP8zoZqywX/SSn5uQ2q+17LfS", "\u0010'�ˬ\u0018", fp);
            fo[4] = hV("CttDCsa4DAsWfpqHpwgF2iS35kc62wBGcgT/yVvzP8xl/qMg7eSIRCok4JBkQr3jZS1bdXpDwgBr34vP4zo56g==", "K���S�", fp);
            fo[3] = hV("eAtJhn7lNJLlx+2RO84Q+CS35kc62wBGcgT/yVvzP8xieCAGgD8Aclj2ifAK+NcF", "v�A�G�", fp);
            fo[15] = hV("g2D+rAU9cTxS9uaN6pe8eCS35kc62wBGcgT/yVvzP8wXeytU9LbK0xP9tgUwGHhj0o+OzRI2wKNxzx0K9SucZVKyK7+UGW7fcg/9/UNit28=", "�}lI�5", fp);
            fo[1] = hV("5ReMRGZI0hX1Vw5lvNr5kSS35kc62wBGcgT/yVvzP8zwKnwE2mkJU+XHS3JThJAmVLu/pvxL732TP3XeOBDNuTZqsUIziwC+zHew9rfnLZU=", "�u�\u000e\n�", fp);
            fo[13] = hV("QpmYu4jyhvyQ59tdoGhl+yS35kc62wBGcgT/yVvzP8ysNp/yEkcWCxYb60RxO+Gotq60gGaKUhTfaZ486YUngw==", "�{�\u00151�", fp);
            fo[11] = hV("PhVVzdGlOm4dA2x0D4W6syS35kc62wBGcgT/yVvzP8zg9et1lJc9HTXMulj/LGqyFJK72oOSy1EoxdCkGvnCgg==", "ࡧP�O", fp);
            fo[10] = hV("1RK1jcYertDMJWWese9lGSS35kc62wBGcgT/yVvzP8xfH8bnM8GB8P/nXH+IujnpANAli7RtX5LgNpa381Nl+XpCE/mU7J2kO6Zf5VjVJvs=", "���!", fp);
            fo[7] = hV("ijvQQfRXIx1nexMv/x8WriS35kc62wBGcgT/yVvzP8zB2NS+om3jL2xRNq+Ywod2", "�QU$\u0006\\", fp);
            fo[6] = hV("mHFiTegiHe8WTvGOW3kysyS35kc62wBGcgT/yVvzP8zy1wDt7ZRbRIto/9Ax/Lp8myxjr9/bHEdTHFHXWk53elQ5FUiuRov6MB7RtVKcYEE=", "\u0000�f2�", fp);
            fo[9] = hV("I6OcGJuk6yjxXVaTx2pMyyS35kc62wBGcgT/yVvzP8x2eK36UcPZEWuFJbTU+8jk", "�/j\u0011�M", fp);
            fo[17] = hV("iEGkPqXnq1waVe5GYutQFiS35kc62wBGcgT/yVvzP8x9KbIHK6/hlBYYRxX6zSbSUMRtSSLdJoeXMpcMDOcepA==", "\u001dh�:a\n", fp);
        } catch (Exception e) {

        }
    }

    public static void hX() {
        fp = new byte[16];
        fp[14] = 63;
        fp[13] = -13;
        fp[7] = 70;
        fp[9] = 4;
        fp[0] = 36;
        fp[4] = 58;
        fp[11] = -55;
        fp[10] = -1;
        fp[3] = 71;
        fp[2] = -26;
        fp[8] = 114;
        fp[5] = -37;
        fp[12] = 91;
        fp[15] = -52;
        fp[6] = 0;
        fp[1] = -73;
    }

    // $VF: Could not create synchronized statement, marking monitor enters and exits
    // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
    void flush() {
        Queue var1 = this.packets;
        synchronized (this.packets){} // $VF: monitorenter

        for (Packet var3 : this.packets) {
            if (var3 != null) {
                var3.apply(Main.mc.getNetworkHandler().getConnection().getPacketListener());
            }
        }

        this.packets.clear();
        this.lag = false;
        // $VF: monitorexit
    }

    @EventHandler
    public void onReceivePacket(Receive event) {
        if (!nullCheck()) {
            if (!this.lag) {
                if (!this.weapon.isEnabled() || PlayerUtil.weaponCheck()) {
                    if (event.packet instanceof EntityPositionS2CPacket var2
                            && var2.getEntityId() != Main.mc.player.getId()
                            && RotationUtil.getHitResult(Main.mc.player, false, RotationUtil.getRotation().getYaw(), RotationUtil.getRotation().getPitch()) instanceof EntityHitResult var6
                            && var6.getEntity() instanceof PlayerEntity var4
                            && PlayerUtil.validateTarget(var4)) {
                        if (Main.mc.player.getPos().distanceTo(new Vec3d(var2.getX(), var2.getY(), var2.getZ()))
                                < Main.mc.player.getPos().distanceTo(var4.getPos())) {
                            return;
                        }

                        event.cancel();
                        this.packets.add(event.packet);
                        this.lag = true;
                        this.timer.reset();
                    }
                }
            }
        }
    }

    static {
        hX();
        hW();
    }

    @EventHandler
    public void onUpdateMouse(UpdateMouseEvent event) {
        if (!nullCheck()) {
            this.setDetail(this.delay.getDefaultValue() + "-" + this.delay.getSecondValue() + "ms");
            if (Main.mc.player.hurtTime != 0 && this.flushOnHurt.isEnabled()) {
                if (!this.hurtTimer.hasReached(1000.0)) {
                    return;
                }

                this.flush();
                this.hurtTimer.reset();
            }

            if (this.lag) {
                if (!this.timer.hasReached((double)MathUtil.getRandomInt((int)this.delay.getDefaultValue(), (int)this.delay.getSecondValue()))) {
                    return;
                }

                this.flush();
            }
        }
    }

    @Override
    public void onDisable() {
        if (!nullCheck()) {
            this.flush();
            super.onDisable();
        }
    }

    public Backtrack() {
        super(fo[9], fo[10], Category.Combat);
        this.bind = new KeyBindSetting(fo[11], 0, false);
        this.delay = new RangeSetting(fo[12], fo[13], 0.0, 50.0, 0.0, 500.0, 1.0, 100.0);
        this.weapon = new BoolSetting(fo[14], fo[15], false);
        this.flushOnHurt = new BoolSetting(fo[16], fo[17], true);
        this.packets = new ConcurrentLinkedQueue();
        this.timer = new TimerUtil();
        this.hurtTimer = new TimerUtil();
        this.addSettings(new Setting[]{this.bind, this.delay, this.weapon, this.flushOnHurt});
    }
}
