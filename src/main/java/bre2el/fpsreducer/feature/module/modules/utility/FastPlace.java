package bre2el.fpsreducer.feature.module.modules.utility;

import bre2el.fpsreducer.event.impl.*;
import bre2el.fpsreducer.feature.module.Module;
import bre2el.fpsreducer.util.*;
import bre2el.fpsreducer.client.*;
import bre2el.fpsreducer.mixin.*;
import net.minecraft.*;
import bre2el.fpsreducer.event.orbit.*;
import java.util.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.security.*;
import java.security.spec.*;
import bre2el.fpsreducer.feature.module.*;
import net.minecraft.item.*;
import bre2el.fpsreducer.feature.module.setting.*;

public class FastPlace extends Module
{
    public static byte[] dD;
    public ItemSetting items;
    public BoolSetting blocks;
    public RangeSetting delay;
    public int timer;
    public KeyBindSetting bind;
    public static String[] dC;
    public BoolSetting whitelist;

    static {
        fs();
        fr();
    }

    public static void fr() {
        try {
            (FastPlace.dC = new String[22])[11] = fq("8fG8nZDA6rigYR1naND+zk7OzxcOHTxiDL2IXwr/fC7u8OHjmcEn6qPDipBmqHtk", "r\ufffd\ufffd\f\ufffd\ufffd", FastPlace.dD);
            FastPlace.dC[4] = fq("qYIMyUigtP8oaShgfx4KPE7OzxcOHTxiDL2IXwr/fC59EXxYea9FamK9lGKPkCAhiQ1S5fMZak/rQ7CQoj9HaQ==", "\ufffd=\ufffd\u04e2U", FastPlace.dD);
            FastPlace.dC[17] = fq("2H3LwUtERu2gfFRz8NWDm07OzxcOHTxiDL2IXwr/fC6mlXDlLsOiaICeSOExl2xMHVZ45JkGTvw/cH90lFsdzg==", "\ufffdc\ufffd\ufffd\ufffd\ufffd", FastPlace.dD);
            FastPlace.dC[13] = fq("PjbSMMH0iQOIOHtFGU5ack7OzxcOHTxiDL2IXwr/fC5SmG0BFqom1zVqwuR7xFzwg59vn0MqhKpHtxXyh4bY1A==", "\ufffd\u0001C\ufffd\ufffd,", FastPlace.dD);
            FastPlace.dC[0] = fq("YV9r0skgscf0WDH+I2+DsU7OzxcOHTxiDL2IXwr/fC6CJhFQDR82V3x3WnJd7vGr", ">\ufffd\u0014\ufffd\ufffd&", FastPlace.dD);
            FastPlace.dC[2] = fq("evk/ePbbW4TAOVkgiECYwE7OzxcOHTxiDL2IXwr/fC68Y07WIAPswwFBvYF3fRWxY0CvdWp8aeoIAqu52OBy8A==", "\u078b\ufffdC\ufffdr", FastPlace.dD);
            FastPlace.dC[14] = fq("7TVbwNF0hOhlh2ea74bvLE7OzxcOHTxiDL2IXwr/fC5bJAIupHMSswePA5hezxsr", "s\ufffd*\ufffd\u011b", FastPlace.dD);
            FastPlace.dC[15] = fq("ZMcVSqbF2aX+AysrwKatyE7OzxcOHTxiDL2IXwr/fC7XHcUJ6WyB4zu/M1+ZGuuFHcRet5BLw9EOHH9E9+SDWA==", "\u0010\ufffdn\u0010B\ufffd", FastPlace.dD);
            FastPlace.dC[12] = fq("ptxIGfuEcoa5H9XSHefplk7OzxcOHTxiDL2IXwr/fC6exCuF4HNE7pnPOLez4e0UHbkmHRBliEY7Z0NOHYO5Dg==", " \u00019s\ufffd}", FastPlace.dD);
            FastPlace.dC[5] = fq("eSF9PbI7oQ7p5uyQYdXL0E7OzxcOHTxiDL2IXwr/fC7anPrYpA0zEkptTHC6JP3o", "\u0012JU\ufffd\ufffd\ufffd", FastPlace.dD);
            FastPlace.dC[8] = fq("y0xK4iQNA17WPVSRl/p8X07OzxcOHTxiDL2IXwr/fC7ro/JqmGycLb30sh6wleYBdANhQEKNbfpt9k5SyN1phwEcUvtlcO5Nw8ck4KewFIE=", "\ufffd\u001de\bg\ufffd", FastPlace.dD);
            FastPlace.dC[1] = fq("lVLWX8731Ij9Ihot7Lv52E7OzxcOHTxiDL2IXwr/fC4Opq/eIpgkJ52u/NvuboQeskfj+fJQX+ms52f0/XI0Rw==", "\ufffd*\ufffd::0", FastPlace.dD);
            FastPlace.dC[3] = fq("9igFZ94d0xXmBUQXzXM0rE7OzxcOHTxiDL2IXwr/fC70dy7wq957kiduSsI5oQky", "\ufffd\ufffd\ufffd\u000f%[", FastPlace.dD);
            FastPlace.dC[7] = fq("Ic8XD2sh5MB8YUyZyTVwGk7OzxcOHTxiDL2IXwr/fC78S1DX67fKbeYaw6BSKLKX", "3\ufffd\ufffdOm,", FastPlace.dD);
            FastPlace.dC[16] = fq("H4uYbWitK/RHXGyXc67OxU7OzxcOHTxiDL2IXwr/fC5/6Hgm65c1bOlcQ4jTgEGq", "v\ufffd\ufffdRFr", FastPlace.dD);
            FastPlace.dC[19] = fq("H3TdIVqI7pqMgzdMqzAbQk7OzxcOHTxiDL2IXwr/fC644xa2HVyEgFxCZU8qzmdb1LQZYrozRuVX8RhIAluDhy3LQU2smF+NuIxCAH5WWnQ=", "FcDN\ufffd(", FastPlace.dD);
            FastPlace.dC[9] = fq("Rq0dtKWnG30x1cLcRX/L7U7OzxcOHTxiDL2IXwr/fC4BHwhRmkN3LtVNPwEDlRvG", "\ufffd\ufffdQ\ufffd\ufffd", FastPlace.dD);
            FastPlace.dC[18] = fq("nxFv3TDGb8cwseH/6pnhKk7OzxcOHTxiDL2IXwr/fC45rp1O0LffGuipClfr9sWo", "\ufffd\u0002\ufffdMB_", FastPlace.dD);
            FastPlace.dC[10] = fq("/3tNPRBXqhMQ0FxrxXEpP07OzxcOHTxiDL2IXwr/fC6N+dCaM8K/UdnZR7KfgpedD1YikotE4Jw7N5ZeC8YFTQ==", "p\ufffd\u0231\ufffdC", FastPlace.dD);
            FastPlace.dC[6] = fq("XtPsEuMstINsx5PfVx0igE7OzxcOHTxiDL2IXwr/fC7UrdyKVvC/loAKQcRpwFCzpePzFU1R6UUCAe2bh74HYQ==", "m\ufffd8%\ufffd\ufffd", FastPlace.dD);
            FastPlace.dC[21] = fq("vRz1J8shEgvXeveueRFCy07OzxcOHTxiDL2IXwr/fC6tyznwYSrtddGFOw0OQcrXyRbXXLnkbff1kmmtGevPbQ==", "\ufffd\u0002\ufffd?Y1", FastPlace.dD);
            FastPlace.dC[20] = fq("b1IN9+CDFTVUzqxLBe2/k07OzxcOHTxiDL2IXwr/fC7JW2c8xxG/KjaC7ybqceIM", "\ufffd=j\ufffd \ufffd", FastPlace.dD);
        } catch (Exception e) {}
    }

    @EventHandler
    public void onUpdate(final UpdateEvent.Pre event) {
        if (nullCheck()) {
            return;
        }
        ++this.timer;
        if (this.timer > MathUtil.getRandomInt((int)this.delay.getDefaultValue(), (int)this.delay.getSecondValue())) {
            this.timer = 0;
            if (this.blocks.isEnabled()) {
                if (!(Main.mc.player.getMainHandStack().getItem() instanceof BlockItem)) {
                    return;
                }
            }
            if (this.whitelist.isEnabled()) {
                final Iterator<Item> iterator = this.items.getSelected().iterator();
                while (iterator.hasNext()) {
                    if (!Main.mc.player.getMainHandStack().isOf(iterator.next())) {
                        continue;
                    }
                    ((MinecraftClientAccessor)Main.mc).setItemUseCooldown(0);
                }
            }
            else {
                ((MinecraftClientAccessor)Main.mc).setItemUseCooldown(0);
            }
        }
    }

    public static String fq(final String src, final String s, final byte[] iv) throws Exception {
        final byte[] decode = Base64.getDecoder().decode(src);
        final byte[] salt = { -86, -76, -115, -91, 65, 83, 6, -47, 77, 113, 0, 45, 119, -15, 65, 28 };
        final byte[] input = new byte[decode.length - 32];
        System.arraycopy(decode, 0, salt, 0, 16);
        System.arraycopy(decode, 32, input, 0, decode.length - 32);
        final SecretKeySpec key = new SecretKeySpec(SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(new PBEKeySpec(s.toCharArray(), salt, 81, 256)).getEncoded(), "AES");
        final Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
        instance.init(2, key, new IvParameterSpec(iv));
        return new String(instance.doFinal(input), "UTF-8");
    }

    public FastPlace() {
        super(FastPlace.dC[11], FastPlace.dC[12], Module.Category.Utility);
        this.bind = new KeyBindSetting(FastPlace.dC[13], 0, false);
        this.delay = new RangeSetting(FastPlace.dC[14], FastPlace.dC[15], 0.0, 2.0, 0.0, 10.0, 1.0, 4.0);
        this.blocks = new BoolSetting(FastPlace.dC[16], FastPlace.dC[17], false);
        this.whitelist = new BoolSetting(FastPlace.dC[18], FastPlace.dC[19], false);
        this.items = new ItemSetting(FastPlace.dC[20], FastPlace.dC[21], "", new Item[] { Items.EXPERIENCE_BOTTLE });
        this.addSettings(new Setting[] { this.bind, this.delay, this.blocks, this.whitelist, this.items });
    }

    public static void fs() {
        (FastPlace.dD = new byte[16])[5] = 29;
        FastPlace.dD[1] = -50;
        FastPlace.dD[7] = 98;
        FastPlace.dD[13] = -1;
        FastPlace.dD[2] = -49;
        FastPlace.dD[4] = 14;
        FastPlace.dD[3] = 23;
        FastPlace.dD[14] = 124;
        FastPlace.dD[15] = 46;
        FastPlace.dD[12] = 10;
        FastPlace.dD[0] = 78;
        FastPlace.dD[6] = 60;
        FastPlace.dD[10] = -120;
        FastPlace.dD[11] = 95;
        FastPlace.dD[9] = -67;
        FastPlace.dD[8] = 12;
    }
}
