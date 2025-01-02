package bre2el.fpsreducer.feature.module.modules.utility;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.event.impl.UpdateEvent.Pre;
import bre2el.fpsreducer.event.orbit.EventHandler;
import bre2el.fpsreducer.feature.module.Module;
import bre2el.fpsreducer.feature.module.Module.Category;
import bre2el.fpsreducer.feature.module.setting.KeyBindSetting;
import bre2el.fpsreducer.feature.module.setting.KeySetting;
import bre2el.fpsreducer.feature.module.setting.ModeSetting;
import bre2el.fpsreducer.feature.module.setting.NumberSetting;
import bre2el.fpsreducer.feature.module.setting.RangeSetting;
import bre2el.fpsreducer.feature.module.setting.Setting;
import bre2el.fpsreducer.mixin.HandledScreenAccessor;
import bre2el.fpsreducer.util.MathUtil;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.slot.SlotActionType;

public class AutoLoot extends Module {
    public RangeSetting delay;
    public ModeSetting mode;
    public NumberSetting minCrystal;
    public NumberSetting minPearl;
    public int delayTick;
    public int slot;
    public NumberSetting minTotem;
    public static byte[] bV;
    public KeyBindSetting bind;
    public KeySetting key;
    public static String[] bU;

    public static void cS() {
        try {
            bU = new String[42];
            bU[2] = cR("e82uNC6f9X6Tey1aD4TMCElrAMx1J8EbSrKHCbwnAmHDG/nbDkTTxPnqNy6+cbgTO9nnGPFa+gMHcVgjP8HULQ==", "�����m", bV);
            bU[39] = cR("7kp13sgNoHYJvc8dG+j6Q0lrAMx1J8EbSrKHCbwnAmFa4QCkSrKAI9l0k0LpteDu", "f��c�.", bV);
            bU[3] = cR("xZdH4fkNUkowrjwrBGgkzElrAMx1J8EbSrKHCbwnAmE+fkfwdsHnK0yJxyC1sfjv", "��{��*", bV);
            bU[14] = cR("+qif2f7u+a1RWOimXtK4FUlrAMx1J8EbSrKHCbwnAmFfCBiqMYvSe/9Et0k8RnlVwDiLrite8S1SsQIiA9WDBQ==", "C�\"Z F", bV);
            bU[13] = cR("Xm0H9zzRJblp7T25V1kcxElrAMx1J8EbSrKHCbwnAmH8e9YbhUG/+3AaAptEb+Pm2Hg18nsU+V8FwighMutq5w==", "�Z6A��", bV);
            bU[35] = cR("Lht883GyzETQVghMmA/01klrAMx1J8EbSrKHCbwnAmGUaqmL/j8W/0MN6AXOzxkHVd4Sd5Qg/YzpOd7OIWSVUA==", "�\u0001G���", bV);
            bU[1] = cR(
                    "cpMe1jD+duRTJ0gxA/SF30lrAMx1J8EbSrKHCbwnAmFXNI1qBXv3yuSEupnamTaa1su6Vz58v7S9KK9t0jZ2RLKunVZRd+xqwCyPP7Wrt0NUO52Gf+6cluugSglx9kqc", "ӷ#I�l", bV
            );
            bU[10] = cR("aTC5S9+Mt3EkAEUtlA1auklrAMx1J8EbSrKHCbwnAmFgqoN2yC3eTh/5YNxAeMaL", "qϋe\u0019�", bV);
            bU[20] = cR("83zvKHjrjlbfVEy28pMWSklrAMx1J8EbSrKHCbwnAmGEt76FhpmVQ+DVx8ZhQCn+", "�a]�L�", bV);
            bU[38] = cR("Hp6nvhFgLmy4imxlAAuqJklrAMx1J8EbSrKHCbwnAmHZbmqKSQFg1bX35fhgYqdB", "��UʗO", bV);
            bU[8] = cR("GQt/4+ySHFRZ4sClBEoXQklrAMx1J8EbSrKHCbwnAmHNGYeIDQh9w875qFuwBQv5", "��r��N", bV);
            bU[32] = cR("CDHcED1adu6SFssUH6lnWklrAMx1J8EbSrKHCbwnAmHu1h+eLmzcuDu/SzcZGwDdND8DV2CaQxXD0neMePXifA==", "'V�0�A", bV);
            bU[23] = cR("faBPj/aQ7dkO4FC/S8amt0lrAMx1J8EbSrKHCbwnAmEXsGEDIEKsGMd++F7tN/dKFiqIXFwFZTe0rZiAfY9Vuw==", "?ϭD��", bV);
            bU[25] = cR(
                    "JKqXgvvYnXF46EuWK+wHuUlrAMx1J8EbSrKHCbwnAmEhKHBLTA8QQvYlczweqoVJAFiyUISZjy4d5wDPcx+2qQxIQqLFDEGp1dlaz7jAxm6YxeIHMbXZMYHiBOpCWAbR", "��|�{c", bV
            );
            bU[16] = cR("vH63SqpUwvEoHfpx7o8HNElrAMx1J8EbSrKHCbwnAmFgAPnPuGtd2wGeoSSxrwGl", "r�jI��", bV);
            bU[21] = cR("B9VchggH0neAK55mnSGIKklrAMx1J8EbSrKHCbwnAmFyAhN6Nhea18LIRBnPst6q", "\u000b��w��", bV);
            bU[28] = cR("zElrbHasecSjcZSw6p6EJUlrAMx1J8EbSrKHCbwnAmEdcF6uNO/PGJkCHQZGM9I+", "�47\u001f3{", bV);
            bU[29] = cR("O0SrdNWBAEbUuiloTR5WWElrAMx1J8EbSrKHCbwnAmEfolFuGvx2CZY0H5JV3fym", "�OJr�a", bV);
            bU[36] = cR("hoAUytqxlQNEnjHPoNLQu0lrAMx1J8EbSrKHCbwnAmFhNv8AC0FGVVP9FiuOlDpv029x4KG0exGNTMeGFtmufQ==", "�E��5�", bV);
            bU[18] = cR("H51Cx3eHQXP8vbBmhg1B+klrAMx1J8EbSrKHCbwnAmFqO3jH2WtLbhobQ7S21EzZ", ",�\r��\u0004", bV);
            bU[5] = cR("ohntennFO0OyyPlG/qVGVUlrAMx1J8EbSrKHCbwnAmEU7XwmWqBJiUFe2M7VJBlw", "�m�y�\u001e", bV);
            bU[41] = cR("7y6X0awape2E5oiRpuIqQUlrAMx1J8EbSrKHCbwnAmEfnKwcf+GuqdjWcwr1FZmO", "gO\u0000�\u001bT", bV);
            bU[0] = cR("EDCodQkAwyEERc/SDp9noElrAMx1J8EbSrKHCbwnAmEerKU4KQO1S7r887sy7FjP", "x���(�", bV);
            bU[31] = cR("6zUjQYYvyQN+holNaRhNlElrAMx1J8EbSrKHCbwnAmGq+XrTwUTZcvRTWZbHmlwx", "��\b\u0011/(", bV);
            bU[7] = cR("FR+mGS84/1e6dfxaMkLeb0lrAMx1J8EbSrKHCbwnAmGa5aSPb4ZXeNLpKmmeieCF", "\u001bx�p��", bV);
            bU[37] = cR("2gjoCizZSzFe2ZKqZhbziUlrAMx1J8EbSrKHCbwnAmGY+z48e2KI+gwtKzj4grpu", "�O�\r\u0013X", bV);
            bU[17] = cR("7mKa1veLIcZOcDN/D5LJX0lrAMx1J8EbSrKHCbwnAmHelB3SPWS43gyWkyfY8bu+", "\u0007��t]1", bV);
            bU[26] = cR("UThirRWK06vgUbMvCPOxuUlrAMx1J8EbSrKHCbwnAmE65l7COCGM2S/uV8AyMYZr", "��C�ُ", bV);
            bU[33] = cR("hZGeb+subdY7Enp5Pp2wlklrAMx1J8EbSrKHCbwnAmGnM/TzhsWhuo1MMA+sDK1OFeNF9fGGkEKzrF3QBB9NkA==", "�\u001b�[��", bV);
            bU[15] = cR("ZHLLGN4D3d/Dcw9pTICyP0lrAMx1J8EbSrKHCbwnAmEOgD7zuN85Y1PCLQ0s+Zw35hjTIfZ/SpKGATfa8S6f5g==", "���\u0013�\u0000", bV);
            bU[40] = cR("c965+M7w9X2uL1hzexjThklrAMx1J8EbSrKHCbwnAmE27lD7dnwomBK2vPzH5SXO", "\u0016��\u0018\u0010O", bV);
            bU[24] = cR("Afne2hJbAFfn/MC4yJ8n2ElrAMx1J8EbSrKHCbwnAmH5IzU9ATBnptMd4fp7VuVK", "�*�\\\u001b/", bV);
            bU[6] = cR("wtjNPsYvc93PKB+BPsCy6ElrAMx1J8EbSrKHCbwnAmHJtC4vluvVigi3cIq4tdI1", "��m�\u0010]", bV);
            bU[9] = cR("OEr35xrm+9DieBDRrtAc8UlrAMx1J8EbSrKHCbwnAmH0KIN++Sa6FL1kXUBeSByFNdfS+YD/Vmk4c9e3INgHDg==", "\u001d:����", bV);
            bU[19] = cR("NuJtXhxzamg4envuBdnJFklrAMx1J8EbSrKHCbwnAmH+yPeTlfod32rgimwCbGsn", "����4�", bV);
            bU[22] = cR(
                    "dePnCflGCgK5GdK0S9oW00lrAMx1J8EbSrKHCbwnAmEWEnaQwLHORpFUMm9L/pu53PrUC0rZm/ZX/YlxT+nniALM3xqy11ITppbibsWspGOUYsZizYLiANRGtWotJ98S", "�&^���", bV
            );
            bU[27] = cR("JUpvs74OVefRd9s2zpWhjUlrAMx1J8EbSrKHCbwnAmGhCeaM08GNFfF7X7r+KHa6", "\u001d$� �W", bV);
            bU[12] = cR("BuJzSbUNqYQgzJLUMh2AjUlrAMx1J8EbSrKHCbwnAmE25DgSCfE1H1i1qnjXInSiS+bTV24iD92qksI3QY76pg==", "���b��", bV);
            bU[11] = cR("RKn5S3ZQHZQayLka8PxwfUlrAMx1J8EbSrKHCbwnAmH46TSGdvL2bgeeFcy4eEjxKu4F4+BjrxI4QgZ2+//0Cg==", "\u0003��\u001a�[", bV);
            bU[34] = cR("pHiqLZ8Wx89ng2g8nkLkhUlrAMx1J8EbSrKHCbwnAmHWAHyQ+ovQ2RbDqCGZACPwTLYaxM9Vu54qIOLA2RB5Qw==", "0\u000f�a,�", bV);
            bU[30] = cR("b35f3+AyzhLhMiB/PsxIsklrAMx1J8EbSrKHCbwnAmHpKVlnRRI88e1/xWmG8tchUx6oq1r7OXWeSNLbUjZ4pQ==", "j\nҗ�H", bV);
            bU[4] = cR(
                    "/02fR30E4KArgbtXNxA75klrAMx1J8EbSrKHCbwnAmEZ/7eAlWwfgbhCpCTJumYHib7PUCgNUnl3h7YXNxPrE7fkDRaVdjJd9roL3SUxVOge9NpvbtsRzi9CbnmBh4QS", "!�6\f�}", bV
            );
        } catch (Exception e) {}
    }

    public static void cT() {
        bV = new byte[16];
        bV[11] = 9;
        bV[10] = -121;
        bV[5] = 39;
        bV[4] = 117;
        bV[14] = 2;
        bV[6] = -63;
        bV[3] = -52;
        bV[8] = 74;
        bV[7] = 27;
        bV[15] = 97;
        bV[0] = 73;
        bV[12] = -68;
        bV[2] = 0;
        bV[9] = -78;
        bV[1] = 107;
        bV[13] = 39;
    }

    int getPearlSlot() {
        ArrayList var1 = new ArrayList();

        for (int var2 = 9; var2 < 35; var2++) {
            if (Main.mc.player.getInventory().getStack(var2).isOf(Items.ENDER_PEARL)) {
                var1.add(var2);
            }
        }

        if (!var1.isEmpty()) {
            Collections.shuffle(var1);
            return (Integer)var1.getFirst();
        } else {
            return -1;
        }
    }

    public AutoLoot() {
        super(bU[21], bU[22], Category.Utility);
        this.bind = new KeyBindSetting(bU[23], 0, false);
        this.mode = new ModeSetting(bU[24], bU[25], bU[26], new String[]{bU[27], bU[28]});
        this.key = new KeySetting(bU[29], bU[30], 2);
        this.minTotem = new NumberSetting(bU[31], bU[32], 10.0, 0.0, 20.0, 1.0);
        this.minPearl = new NumberSetting(bU[33], bU[34], 4.0, 0.0, 20.0, 1.0);
        this.minCrystal = new NumberSetting(bU[35], bU[36], 2.0, 0.0, 20.0, 1.0);
        this.delay = new RangeSetting(bU[37], bU[38], 0.0, 4.0, 0.0, 10.0, 1.0, 4.0);
        this.addSettings(new Setting[]{this.bind, this.mode, this.key, this.minTotem, this.minPearl, this.minCrystal, this.delay});
    }

    public static String cR(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{12, 59, 0, -104, 107, 70, -63, -45, 127, 59, -88, -102, -27, 106, -1, -98};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 183, 256);
        SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] var8 = var7.generateSecret(var6).getEncoded();
        SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
        Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var10.init(2, var9, new IvParameterSpec(var2));
        byte[] var11 = var10.doFinal(var5);
        return new String(var11, "UTF-8");
    }

    int getTotemSlot() {
        ArrayList var1 = new ArrayList();

        for (int var2 = 9; var2 < 35; var2++) {
            if (Main.mc.player.getInventory().getStack(var2).isOf(Items.TOTEM_OF_UNDYING)) {
                var1.add(var2);
            }
        }

        if (!var1.isEmpty()) {
            Collections.shuffle(var1);
            return (Integer)var1.getFirst();
        } else {
            return -1;
        }
    }

    void reset() {
        this.delayTick = 0;
    }

    int getCrystalSlot() {
        ArrayList var1 = new ArrayList();

        for (int var2 = 9; var2 < 35; var2++) {
            if (Main.mc.player.getInventory().getStack(var2).isOf(Items.END_CRYSTAL)) {
                var1.add(var2);
            }
        }

        if (!var1.isEmpty()) {
            Collections.shuffle(var1);
            return (Integer)var1.getFirst();
        } else {
            return -1;
        }
    }

    static {
        cT();
        cS();
    }

    boolean checkDelay() {
        return MathUtil.getRandomInt((int)this.delay.getDefaultValue(), (int)this.delay.getSecondValue()) <= this.delayTick;
    }

    @EventHandler
    void onUpdate(Pre event) {
        if (!nullCheck()) {
            this.setDetail(this.mode.getMode() + " " + this.delay.getDefaultValue() + "-" + this.delay.getSecondValue());
            this.delayTick++;
            if (this.checkDelay()) {
                if (Main.mc.currentScreen instanceof InventoryScreen var2 && this.key.isActive(this.key.getKey())) {
                    if (this.mode.getMode().equals(bU[39])) {
                        this.slot = this.getTotemSlot();
                        if (this.slot == -1) {
                            return;
                        }
                    } else {
                        if (((HandledScreenAccessor)var2).hoveredSlot() == null) {
                            return;
                        }

                        int var7 = ((HandledScreenAccessor)var2).hoveredSlot().getIndex();
                        if (var7 > 35) {
                            return;
                        }

                        if (var7 < 9) {
                            return;
                        }

                        this.slot = var7;
                    }

                    PlayerInventory var8 = Main.mc.player.getInventory();
                    int var4 = var8.count(Items.TOTEM_OF_UNDYING);
                    if ((double)var4 > this.minTotem.getDefaultValue() && ((ItemStack)var8.main.get(this.slot)).getItem() == Items.TOTEM_OF_UNDYING) {
                        Main.mc
                                .interactionManager
                                .clickSlot(
                                        ((PlayerScreenHandler)((InventoryScreen)Main.mc.currentScreen).getScreenHandler()).syncId,
                                        this.slot,
                                        1,
                                        SlotActionType.THROW,
                                        Main.mc.player
                                );
                        this.reset();
                    }

                    int var5 = var8.count(Items.ENDER_PEARL);
                    if (!this.mode.getMode().equals(bU[40])) {
                        this.slot = this.getPearlSlot();
                        if (this.slot == -1) {
                            return;
                        }
                    }

                    if ((double)var5 > this.minPearl.getDefaultValue() * 16.0 && ((ItemStack)var8.main.get(this.slot)).getItem() == Items.ENDER_PEARL) {
                        Main.mc
                                .interactionManager
                                .clickSlot(
                                        ((PlayerScreenHandler)((InventoryScreen)Main.mc.currentScreen).getScreenHandler()).syncId,
                                        this.slot,
                                        1,
                                        SlotActionType.THROW,
                                        Main.mc.player
                                );
                        this.reset();
                    }

                    int var6 = var8.count(Items.END_CRYSTAL);
                    if (!this.mode.getMode().equals(bU[41])) {
                        this.slot = this.getCrystalSlot();
                        if (this.slot == -1) {
                            return;
                        }
                    }

                    if ((double)var6 > this.minCrystal.getDefaultValue() * 64.0 && ((ItemStack)var8.main.get(this.slot)).getItem() == Items.END_CRYSTAL) {
                        Main.mc
                                .interactionManager
                                .clickSlot(
                                        ((PlayerScreenHandler)((InventoryScreen)Main.mc.currentScreen).getScreenHandler()).syncId,
                                        this.slot,
                                        1,
                                        SlotActionType.THROW,
                                        Main.mc.player
                                );
                        this.reset();
                    }
                }
            }
        }
    }
}
