package bre2el.fpsreducer.feature.module.modules.visual;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.event.impl.RenderWorldEvent;
import bre2el.fpsreducer.event.orbit.EventHandler;
import bre2el.fpsreducer.feature.module.Module;
import bre2el.fpsreducer.feature.module.setting.KeyBindSetting;
import bre2el.fpsreducer.feature.module.setting.Setting;
import bre2el.fpsreducer.util.RenderUtil;
import bre2el.fpsreducer.util.RenderUtil.RenderHelper;
import java.awt.Color;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.Vec3d;

public class ItemESP extends Module {
    public KeyBindSetting bind;
    public static byte[] bh;
    public static String[] bg;

    static {
        bL();
        bK();
    }

    public static void bL() {
        bh = new byte[16];
        bh[12] = 1;
        bh[9] = 117;
        bh[15] = -103;
        bh[8] = 70;
        bh[5] = -56;
        bh[13] = -113;
        bh[3] = 47;
        bh[11] = -86;
        bh[7] = 90;
        bh[2] = -26;
        bh[1] = -64;
        bh[10] = 110;
        bh[0] = 79;
        bh[14] = 73;
        bh[4] = -122;
        bh[6] = 10;
    }

    public ItemESP() {
        super(bg[3], bg[4], Category.Visual);
        this.bind = new KeyBindSetting(bg[5], 0, false);
        this.addSettings(new Setting[]{this.bind});
    }

    public static int[] SwitchMapRarity;

    static {
        SwitchMapRarity = new int[Rarity.values().length];
        try {
            SwitchMapRarity[Rarity.COMMON.ordinal()] = 1;
            SwitchMapRarity[Rarity.UNCOMMON.ordinal()] = 2;
            SwitchMapRarity[Rarity.RARE.ordinal()] = 3;
            SwitchMapRarity[Rarity.EPIC.ordinal()] = 4;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // $VF: Unable to simplify switch on enum
    // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
    @EventHandler
    public void onRenderWorld(RenderWorldEvent event) {
        if (!nullCheck()) {
            for (Entity var3 : Main.mc.world.getEntities()) {
                if (var3 instanceof ItemEntity) {
                    ItemEntity var4 = (ItemEntity)var3;
                    RenderHelper.setMatrixStack(event.matrices);
                    RenderUtil.setCameraAction();
                    RenderHelper.getPositionMatrix().set(event.matrices.peek().getPositionMatrix());
                    Vec3d var5 = var4.getPos();
                    // nigga what
                    switch (SwitchMapRarity[var4.getStack().getRarity().ordinal()]) {
                        case 1:
                            RenderUtil.renderFilledBox(
                                    event.matrices,
                                    (float)var5.x - var4.getWidth() / 1.5F,
                                    (float)var5.y,
                                    (float)var5.z - var4.getWidth() / 1.5F,
                                    (float)var5.x + var4.getWidth() / 1.5F,
                                    (float)var5.y + var4.getHeight(),
                                    (float)var5.z + var4.getWidth() / 1.5F,
                                    new Color(220, 220, 220, 160)
                            );
                            RenderUtil.renderOutlinedBox(
                                    event.matrices,
                                    (float)var5.x - var4.getWidth() / 1.5F,
                                    (float)var5.y,
                                    (float)var5.z - var4.getWidth() / 1.5F,
                                    (float)var5.x + var4.getWidth() / 1.5F,
                                    (float)var5.y + var4.getHeight(),
                                    (float)var5.z + var4.getWidth() / 1.5F,
                                    new Color(220, 220, 220, 160)
                            );
                            break;
                        case 2:
                            RenderUtil.renderFilledBox(
                                    event.matrices,
                                    (float)var5.x - var4.getWidth() / 1.5F,
                                    (float)var5.y,
                                    (float)var5.z - var4.getWidth() / 1.5F,
                                    (float)var5.x + var4.getWidth() / 1.5F,
                                    (float)var5.y + var4.getHeight(),
                                    (float)var5.z + var4.getWidth() / 1.5F,
                                    new Color(220, 220, 0, 160)
                            );
                            RenderUtil.renderOutlinedBox(
                                    event.matrices,
                                    (float)var5.x - var4.getWidth() / 1.5F,
                                    (float)var5.y,
                                    (float)var5.z - var4.getWidth() / 1.5F,
                                    (float)var5.x + var4.getWidth() / 1.5F,
                                    (float)var5.y + var4.getHeight(),
                                    (float)var5.z + var4.getWidth() / 1.5F,
                                    new Color(220, 220, 0, 160)
                            );
                            break;
                        case 3:
                            RenderUtil.renderFilledBox(
                                    event.matrices,
                                    (float)var5.x - var4.getWidth() / 1.5F,
                                    (float)var5.y,
                                    (float)var5.z - var4.getWidth() / 1.5F,
                                    (float)var5.x + var4.getWidth() / 1.5F,
                                    (float)var5.y + var4.getHeight(),
                                    (float)var5.z + var4.getWidth() / 1.5F,
                                    new Color(0, 220, 220, 160)
                            );
                            RenderUtil.renderOutlinedBox(
                                    event.matrices,
                                    (float)var5.x - var4.getWidth() / 1.5F,
                                    (float)var5.y,
                                    (float)var5.z - var4.getWidth() / 1.5F,
                                    (float)var5.x + var4.getWidth() / 1.5F,
                                    (float)var5.y + var4.getHeight(),
                                    (float)var5.z + var4.getWidth() / 1.5F,
                                    new Color(0, 220, 220, 160)
                            );
                            break;
                        case 4:
                            RenderUtil.renderFilledBox(
                                    event.matrices,
                                    (float)var5.x - var4.getWidth() / 1.5F,
                                    (float)var5.y,
                                    (float)var5.z - var4.getWidth() / 1.5F,
                                    (float)var5.x + var4.getWidth() / 1.5F,
                                    (float)var5.y + var4.getHeight(),
                                    (float)var5.z + var4.getWidth() / 1.5F,
                                    new Color(220, 0, 220, 160)
                            );
                            RenderUtil.renderOutlinedBox(
                                    event.matrices,
                                    (float)var5.x - var4.getWidth() / 1.5F,
                                    (float)var5.y,
                                    (float)var5.z - var4.getWidth() / 1.5F,
                                    (float)var5.x + var4.getWidth() / 1.5F,
                                    (float)var5.y + var4.getHeight(),
                                    (float)var5.z + var4.getWidth() / 1.5F,
                                    new Color(220, 0, 220, 160)
                            );
                    }

                    RenderHelper.getMatrixStack().pop();
                }
            }
        }
    }

    public static String bJ(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{-92, -49, -8, -66, -39, 4, 126, -51, 113, 108, 2, 0, 6, 66, -114, -125};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 185, 256);
        SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] var8 = var7.generateSecret(var6).getEncoded();
        SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
        Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var10.init(2, var9, new IvParameterSpec(var2));
        byte[] var11 = var10.doFinal(var5);
        return new String(var11, "UTF-8");
    }

    public static void bK() {
        try {
            bg = new String[6];
            bg[3] = bJ("RnwkKlOQOQFb57pO6z+FLU/A5i+GyApaRnVuqgGPSZmZTuJcPuOEtVYV6ouga+7w", "�2�\u0004\u008b", bh);
            bg[0] = bJ("0pXt/+SBT69mpTu395ew6E/A5i+GyApaRnVuqgGPSZmuS+gQRZL5Wk+lV0oaAI9q", "`��XIJ", bh);
            bg[2] = bJ("+sQr24ref5TlvdsHsNWZak/A5i+GyApaRnVuqgGPSZmtoPWa6n7jtUM1Rv8FeSAB+FPC7QBSgG9Tv4Hk6NuwUQ==", "�ab\u0018�j", bh);
            bg[5] = bJ("oK3sSiyv7a/E8yGnG56TTk/A5i+GyApaRnVuqgGPSZl8njF9bHnO+iq9V5PRFrM5s0xmAdS2TaqYTQH/NzangQ==", "^�\u000e��^", bh);
            bg[1] = bJ("YPwBrB/F9kqtBfGIQ8cPX0/A5i+GyApaRnVuqgGPSZmACGvJdSbTv1y8DJ9AvLFUDQMQh07zGh23dURSRy21VQ==", "�X��6)", bh);
            bg[4] = bJ("l/ySLaVTNj4HRGRGER6jHk/A5i+GyApaRnVuqgGPSZklrijQImIkt1kmPTkU9/QNwRn0I3Q5nbekQRwweBe4Bg==", "C{[ś�", bh);
        } catch (Exception e) {
        }
    }
}
