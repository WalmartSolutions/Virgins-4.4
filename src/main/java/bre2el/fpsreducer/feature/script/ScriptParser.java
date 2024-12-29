package bre2el.fpsreducer.feature.script;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.mixin.ClientPlayerEntityAccessor;
import bre2el.fpsreducer.util.InputUtil;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import static bre2el.fpsreducer.client.Main.mc;

public class ScriptParser {
    public static byte[] cB;
    public Map<String, FunctionData> functions;
    public static String[] cA;
    public Map<String, Object> variables;
    public Map<String, Runnable> eventHandlers = new HashMap();

    Object evaluateExpression(String expression) {
        if (!expression.matches(cA[93])) {
            return !this.variables.containsKey(expression) ? expression : this.variables.get(expression);
        } else {
            return Integer.parseInt(expression);
        }
    }

    public static String dN(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{1, -126, -60, -20, 0, -11, 11, -71, -101, 43, 49, 50, 55, 34, -119, 52};
        byte[] var5 = new byte[var3.length - 32];
        System.arraycopy(var3, 0, var4, 0, 16);
        System.arraycopy(var3, 32, var5, 0, var3.length - 32);
        PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 48, 256);
        SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] var8 = var7.generateSecret(var6).getEncoded();
        SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
        Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
        var10.init(2, var9, new IvParameterSpec(var2));
        byte[] var11 = var10.doFinal(var5);
        String info = new String(var11, "UTF-8");
        return info;
    }

    void executeAction(String action) {
        if (action.startsWith(cA[66])) {
            String var2 = action.substring(6).replace(cA[67], "");
            mc.player.sendMessage(Text.of(var2));
        }

        if (action.startsWith(cA[68])) {
            InputUtil.callMouse(0, 1);
        }

        if (action.startsWith(cA[69])) {
            InputUtil.callMouse(1, 1);
        }

        if (action.startsWith(cA[70])) {
            if (!mc.player.isOnGround()) {
                return;
            }

            if (mc.player.isInsideWaterOrBubbleColumn()) {
                return;
            }

            if (mc.player.isInsideWall()) {
                return;
            }

            if (mc.player.isTouchingWater()) {
                return;
            }

            mc.player.jump();
        }

        ClientPlayerEntityAccessor accessor = (ClientPlayerEntityAccessor) mc.player;

        if (action.startsWith(cA[71])) {
            mc
                    .player
                    .setSneaking(
                            mc.player.forwardSpeed > 1.0E-5F
                                    && accessor.invokeCanSprint()
                                    && (!mc.player.horizontalCollision || mc.player.collidedSoftly)
                                    && (!mc.player.isTouchingWater() || mc.player.isSubmergedInWater())
                    );
        }

        if (action.startsWith(cA[72])) {
            mc.player.setSprinting(false);
        }

        if (action.startsWith(cA[73])) {
            mc.player.setSneaking(true);
        }

        if (action.startsWith(cA[74])) {
            mc.player.setSneaking(false);
        }

        if (action.startsWith(cA[75])) {
            String var5 = action.substring(5).replace(cA[76], "");

            for (int var3 = 0; var3 < 9; var3++) {
                ItemStack var4 = mc.player.getInventory().getStack(var3);
                if (var4.getItem().toString().replaceAll(cA[77], cA[78]).replaceAll(cA[79], cA[80]).equalsIgnoreCase(var5)) {
                    mc.player.getInventory().selectedSlot = var3;
                    break;
                }
            }
        }
    }

    public void triggerEvent(String eventName) {
        Runnable var2 = (Runnable)this.eventHandlers.get(eventName);
        if (var2 != null) {
            var2.run();
        }
    }

    void executeIf(String condition, String actions) {
        if (this.evaluateCondition(condition)) {
            this.executeActions(actions);
        }
    }

    static {
        dP();
        dO();
    }

    public ScriptParser() {
        this.variables = new HashMap();
        this.functions = new HashMap();
    }

    void executeActions(String actions) {
        String[] var2 = actions.split(cA[56]);

        for (String var6 : var2) {
            var6 = var6.trim();
            if (!var6.startsWith(cA[57])) {
                if (!var6.startsWith(cA[60])) {
                    if (!var6.startsWith(cA[64])) {
                        this.executeAction(var6);
                    } else {
                        String[] var7 = var6.substring(4).split(cA[65], 2);
                        if (var7.length == 2) {
                            this.variables.put(var7[0].trim(), this.evaluateExpression(var7[1].trim()));
                        }
                    }
                } else {
                    String var10 = var6.substring(5, var6.indexOf(cA[61])).trim();
                    String var8 = var6.substring(var6.indexOf(cA[62]) + 1, var6.indexOf(cA[63])).trim();
                    this.executeFunction(var10, var8);
                }
            } else {
                String var11 = var6.substring(3, var6.indexOf(cA[58])).trim();
                String var12 = var6.substring(var6.indexOf(cA[59]) + 2).trim();
                this.executeIf(var11, var12);
            }
        }
    }

    public static void dO() {
        try {
            cA[41] = dN("vta0VCFh6IJ8ERMaj8dPaAe+ENDAV0e+0B1XnaOm48HUDTpiCJo5/zi7ctPppgW+", "\u00157��|B", cB);
            cA[16] = dN("6KzKB/LOz2D0O8rCRmfRxwe+ENDAV0e+0B1XnaOm48GVeEeSJR7cznl6U6aYjjp2", "\u001bҎ��A", cB);
            cA[36] = dN("fsXPLih8Oy7raXX3D4QCQge+ENDAV0e+0B1XnaOm48EgMLiI1Rf3P4+Qzm0c/krk", "�?ֈp!", cB);
            cA[45] = dN("JWMQIlk6Ceit/AKEy8iTrQe+ENDAV0e+0B1XnaOm48Evk3GvKvO9WLvrr0EZfW6C", "г\n��", cB);
            cA[49] = dN("MM8drUhMGLhTfjRIyS3i9Ae+ENDAV0e+0B1XnaOm48F/K4lsWPCB+d4zt2IXYjT3", "\f7�Q{7", cB);
            cA[20] = dN("kYoHf6EVDB9KZeXlxc5dHwe+ENDAV0e+0B1XnaOm48G3yJasXwgHiQV5XE9eEfqo", "�`��:\f", cB);
            cA[74] = dN("vrEJIsxgBkAra55ca6ubQAe+ENDAV0e+0B1XnaOm48FrgQvOp1YW/WiQepwzaEyN", "\u1ad1oc\u001f", cB);
            cA[48] = dN("l9v1MO+Zo8EaLaTECj/BEge+ENDAV0e+0B1XnaOm48HooMqdYe09SHomtwc3ZlqH", "\u0007ctڐ\u001f", cB);
            cA[86] = dN("/EbiPhjKkDb98HHj6fU/oge+ENDAV0e+0B1XnaOm48F/2DvGLZNeO4tXf2hn+j06", "`\u0018O��Y", cB);
            cA[50] = dN("n4DA28xeTeaaQ/AzNBVreQe+ENDAV0e+0B1XnaOm48Fm66jifNIfxLCXhbDQp9xY", "9�ڽl�", cB);
            cA[70] = dN("YrWYcUh7S06PFbFJqXXZbAe+ENDAV0e+0B1XnaOm48HZWZsZugPbPj5bXbtKpXrx", "����YT", cB);
            cA[13] = dN("/4h15Ti8rSVuuC+yQ0/nuAe+ENDAV0e+0B1XnaOm48Gtzje71RMGl71BY0qKtuN/", "�t2�|�", cB);
            cA[22] = dN("b5rtvl1W4f9KjLGY3OX2ZAe+ENDAV0e+0B1XnaOm48EjAaE5YNRS3nkp4UFk6gZ8", "��V-ҧ", cB);
            cA[84] = dN("39U1neEe772h/YmI9wdMKge+ENDAV0e+0B1XnaOm48GpobC09aP8TaEAoQaf6j7n", "��BxT@", cB);
            cA[4] = dN("JLgaCZ8vfsiAVVO2NUdFMwe+ENDAV0e+0B1XnaOm48F/h+oVcyne63HBATwmehN/", "\u001b\u0002����", cB);
            cA[61] = dN("/7924VV1SaNtcT4rlEm1Xwe+ENDAV0e+0B1XnaOm48EFrRMALZFjEtltWuiR98LX", "�JG��I", cB);
            cA[30] = dN("6TkQQjCLdyHD1cq9fukRZwe+ENDAV0e+0B1XnaOm48Hdiz0rZC16HF8bdrnVqwRz", "�n�v��", cB);
            cA[18] = dN("ve4mG1VT3Rwm/N1UMBkGAQe+ENDAV0e+0B1XnaOm48FYs3XUMy07A6X0rqeuO0wT", "�T�Z�\r", cB);
            cA[76] = dN("wmZSumslFUkh/iZwVNbudQe+ENDAV0e+0B1XnaOm48EEZJaLe9zyFsTBveT0PFmJ", "ӆy��=", cB);
            cA[80] = dN("6HXcgYOHCLZKyzKpeL/rJwe+ENDAV0e+0B1XnaOm48EqhVv+EXwLGiwKJYwzGtNN", "5.\"���", cB);
            cA[9] = dN("JfiNEHSqN0isW4RIuMiTvQe+ENDAV0e+0B1XnaOm48GXZraTJL7L7HCAldf8ynTM", "�+\u0001�)�", cB);
            cA[0] = dN("P27Or9einMYmtzttxFPAyQe+ENDAV0e+0B1XnaOm48H0NTiLMCTewOlpXGaAu9Zy", "\u0015{\u0012�у", cB);
            cA[32] = dN("VuiUpTFSgfjLOK0kH7WyAQe+ENDAV0e+0B1XnaOm48EXvm+VR3KdTnca9ETfYKRN", "�\u001f]@�0", cB);
            cA[28] = dN("HnVrLw+5WJQYtmGakAmtQAe+ENDAV0e+0B1XnaOm48GAXYnkV3N6upVfPGk8PS8l", "]�\u0005S��", cB);
            cA[71] = dN("KDP8oGmUZbsqh75hbpVKjQe+ENDAV0e+0B1XnaOm48Hr6fj7jyhFfLJ2KJZnVOR2", "�\n\u001b�\b\u0005", cB);
            cA[90] = dN("jOW8ukiLwGXZb7uOD8dAGAe+ENDAV0e+0B1XnaOm48GOtjOwx+XOefBG8z4wYcCJ", "��3ϯ�", cB);
            cA[51] = dN("Dv2ozYBLsAPnI5zkV0to5Ae+ENDAV0e+0B1XnaOm48FwL1oQEnRkhjJjtIqSescv", "�pC�\u000f�", cB);
            cA[3] = dN("0QR7Sl3CwYKhP/86mkOQ3Qe+ENDAV0e+0B1XnaOm48GokDffcM1zn3DS9njtLSEj", "1\u0011�TK�", cB);
            cA[69] = dN("s3QkCPy/tafpobSqgQeG3ge+ENDAV0e+0B1XnaOm48HvcpTZCnjKeIlx7ncc0nkz", "\u0015�`���", cB);
            cA[59] = dN("Hyd8z12yUVIaKv/AkKRzHQe+ENDAV0e+0B1XnaOm48HhAWjqJ+r7LvqCLzD1tk2J", "P�i`��", cB);
            cA[89] = dN("BYg/c3TaUdRfwWLe1NXG2Qe+ENDAV0e+0B1XnaOm48HP92cyDcU1BLRsCyBT70md", "��0�0/", cB);
            cA[7] = dN("7wr3EfoCJPradkBhAI2nTAe+ENDAV0e+0B1XnaOm48ELMMO0DkZgYcTDNy8Y1kXI", "\u0001�\u0015=\u00185", cB);
            cA[46] = dN("uzcZz5F0U0C9ESKwRGttcge+ENDAV0e+0B1XnaOm48FkhYGDLt1uRmqwtgWDPJkU", "\u0015\u001b\u0004�8�", cB);
            cA[43] = dN("TQq4vQpl3uabMzgxjYDhhQe+ENDAV0e+0B1XnaOm48G/HXExeezAX8hTsxzpE6h/", "Z���\u001f", cB);
            cA[34] = dN("nsuUXgzW1ezmN9HbiXHCLAe+ENDAV0e+0B1XnaOm48GvoZsYd/DH0C5s++qWNB/p", "��� ��", cB);
            cA[81] = dN("RAZLMewmn5ZP4ILNaWKt5ge+ENDAV0e+0B1XnaOm48EpY2+sp7BFIZiIIFCGuQVA", "�)��2�", cB);
            cA[47] = dN("6HNUcIthnHVztx1Gqz0Bowe+ENDAV0e+0B1XnaOm48HgKj6I9o7mqBhr1TWeuZj/", "Y3`�ۖ", cB);
            cA[87] = dN("R/F8gCi3kg/sU08KIIPr5Ae+ENDAV0e+0B1XnaOm48GHYtBNSSm0hr1cbmKFRP+T", "�!¦6\u001f", cB);
            cA[66] = dN("UOXKYCqMo5blNwjUYBXHJQe+ENDAV0e+0B1XnaOm48Eu2l5apBfhP3zU1MamvYNJ", "��\\\u0014d�", cB);
            cA[57] = dN("e/3FWc3OBW+NWwPxjJzkQge+ENDAV0e+0B1XnaOm48F3ot1xgH/+5uWvVq3uPC2x", "o|Bv��", cB);
            cA[77] = dN("/zbhRuG6guPEqGo3E6JPTwe+ENDAV0e+0B1XnaOm48Hkfv/OHIq3cJ/J8CSV9pzN", "\u07b6���\u0003", cB);
            cA[40] = dN("pQmZXf2V6uN1Jv0hED0Vlwe+ENDAV0e+0B1XnaOm48Fe5ucBrRD+FUmxtKDrjlWg", "\u0004+v���", cB);
            cA[83] = dN("QLZLJF6QZk5wcdEeJypClQe+ENDAV0e+0B1XnaOm48Hme3VkJjNWdGQh1EK4HOAH", "\u0011g�8b^", cB);
            cA[12] = dN("O5u1Ympr/3D8lNXXe/YfXAe+ENDAV0e+0B1XnaOm48G8mcrEoHcsEKrodOCocXRb", "+:\u0003��(", cB);
            cA[78] = dN("LI9OHVAqIHLelv+1sQvoiwe+ENDAV0e+0B1XnaOm48ET3uCAvywMC3O3K9NMUPMw", "0vg '�", cB);
            cA[23] = dN("/6gOOCuQPh2y+S8Nirg6VAe+ENDAV0e+0B1XnaOm48Gx32ttL82sTJzcvFAGvh4o", "\u00105S��\u0016", cB);
            cA[29] = dN("t2XK7rylk+jkxpX0svwMiAe+ENDAV0e+0B1XnaOm48HcQzRW5GuuVwzoZelm/p1L", "0!\u05ff�Y", cB);
            cA[26] = dN("p5k4qAidWHR6w/+2laBI+Qe+ENDAV0e+0B1XnaOm48GnAYptGmRvG/hDG9Xi1cMo", "\u001b�[�*�", cB);
            cA[91] = dN("aycEWhqPWbfOWFNewAvAkge+ENDAV0e+0B1XnaOm48GQKqcqngZBxz1S6mQ7PS30", "{G���;", cB);
            cA[88] = dN("lkZGl+oIy9RZKLp1BWY1VAe+ENDAV0e+0B1XnaOm48GQb3Xy6EDtPWE4HChtaRAt", "��J@\u0000�", cB);
            cA[6] = dN("LIRiuhqtYwSj7iTpY7wnFwe+ENDAV0e+0B1XnaOm48Fm6EoOoUbtsqMmCo4XNh0q", "z�\u0003���", cB);
            cA[52] = dN("wUfVaMy8OGCky3h65H6W7ge+ENDAV0e+0B1XnaOm48F3To9DA3BOUGU3VAuj8x65", "uC���p", cB);
            cA[31] = dN("uwae2gFS+NhZ4CeWT7lBGwe+ENDAV0e+0B1XnaOm48FJ3+3dEw2dU5gOy6jKOgeG", "'\t��\u00054", cB);
            cA[11] = dN("NbT+Vionl9cSGcXRi6ZAkge+ENDAV0e+0B1XnaOm48EzUHLnlYBQLNPEfBc9k+fQ", "\u001e]��݀", cB);
            cA[42] = dN("/ASvfnHFd9j0E87FxFFt6we+ENDAV0e+0B1XnaOm48FANuIi1b0SoUP9ECgOKppH", "�Y!B��", cB);
            cA[53] = dN("A3elZrfimWlWwsXEImetkge+ENDAV0e+0B1XnaOm48F6t7ULPP1KoLr/FpVZqRPg", "��&�6R", cB);
            cA[58] = dN("90h1TxwerrDwqhQ98QyeVwe+ENDAV0e+0B1XnaOm48H7Ou8aiJVoMmUJj0KDTpgC", "��2=�V", cB);
            cA[56] = dN("z0z26Pdu9Adzo109Kmkm4ge+ENDAV0e+0B1XnaOm48FMI6lJX7LyD0kiFjBtmkuJ", "|rbyH�", cB);
            cA[93] = dN("kLD7z/czQT3gw4yST8lF3Ae+ENDAV0e+0B1XnaOm48HDdQH5qD1spK7aqnnPLMp5", "\"�wȂB", cB);
            cA[1] = dN("nsCqUlBMEYZtVXg2p0lcPAe+ENDAV0e+0B1XnaOm48FzUlP6cxzP9YMcdWfZAll/", "[e��*5", cB);
            cA[85] = dN("yXJS0rXNElA49oemdymZVge+ENDAV0e+0B1XnaOm48FbjbsD6vBQrTLIhNThKQot", "����to", cB);
            cA[73] = dN("+sfhF6qITKXgEASLsRvwjQe+ENDAV0e+0B1XnaOm48G1DfB9tq1oujf2y8pkvnIM", "�D\u0007\u000fՀ", cB);
            cA[2] = dN("biF1aK1qLA2mdsvbnWVxfge+ENDAV0e+0B1XnaOm48GGYgxNQ05W5DTiteuom/2M", "�;钫�", cB);
            cA[21] = dN("R2N8KRQlEsebx9b+HqYGRge+ENDAV0e+0B1XnaOm48GEoVe4407enKGeyUhTbEh0", "�����p", cB);
            cA[54] = dN("zUE6OXipnDniqbq5KzyQSge+ENDAV0e+0B1XnaOm48HOJ2GW5bs6qM0Nv09Sy2l4", "�Ly��G", cB);
            cA[62] = dN("GR87Jdjdna/Msd6vZ3GmUAe+ENDAV0e+0B1XnaOm48G9cX8eORuzrBDGi9wB5qe6", "�\u0011R#X;", cB);
            cA[24] = dN("P3lN4iJW8NF972Vd1UBSIwe+ENDAV0e+0B1XnaOm48FTKFcNRvoIzbiu8JEmMLCa", "�����S", cB);
            cA[10] = dN("lnMu/2H8/bMMCIITfv/R1we+ENDAV0e+0B1XnaOm48HsYNSvmDcUkIf2krbHTrCg", "Ak(\u0013��", cB);
            cA[75] = dN("JkBUDIPX44nnQLMsui7zlwe+ENDAV0e+0B1XnaOm48FxIBDdQVtuybqCLJSgGlTT", "��F�\u001a\u0013", cB);
            cA[14] = dN("TqxFHPwIYiksTgTzUC+bXAe+ENDAV0e+0B1XnaOm48HvzMi4eyVLlD8TqSKXhiPy", "��<��/", cB);
            cA[79] = dN("KrkEWtdzyueh9hKc5ysvjge+ENDAV0e+0B1XnaOm48EMvE83hG/gvX85wIP7FEUR", "ֻ��}:", cB);
            cA[35] = dN("S9mXbcZcAucSjVrytUaTqQe+ENDAV0e+0B1XnaOm48E7OB475LuOvDQwPkLrLvaz", "|�\u007f\u0001Q\u0007", cB);
            cA[60] = dN("R3hEaXbHfs3r3TTb6Cz8xQe+ENDAV0e+0B1XnaOm48EulDt8f5sXiyfKCp1rmMi0", ".RU���", cB);
            cA[72] = dN("SN2C5FsLsCxhVB5WtdO+5we+ENDAV0e+0B1XnaOm48EjuZprq/dpD1yN7QDmQJLy", "�\u001e��\u001d�", cB);
            cA[15] = dN("mUSpLWjWBLjn5k1oEcHuvQe+ENDAV0e+0B1XnaOm48E4zEQWFQAivaECHZ3MzQRK", "�vHe\f'", cB);
            cA[38] = dN("kz55JuHaePk/chYiCfQyzwe+ENDAV0e+0B1XnaOm48E0W22ibeA0Ugr+GwDdSf+h", "Z\"�?�6", cB);
            cA[64] = dN("DrD2qqZwRH/20rGQeDw16Ae+ENDAV0e+0B1XnaOm48FBqstFet1pJTQLFua+dhJ3", "�X��m�", cB);
            cA[33] = dN("Pvc4Qjbdq59GwpTLqi+DVwe+ENDAV0e+0B1XnaOm48Hkvq/RaUsuzrusoIxq8p0f", "�g�\u0001\u0002�", cB);
            cA[39] = dN("JfLBYj8xL+OYsKoAHWTceAe+ENDAV0e+0B1XnaOm48HD/ZayipdD5pfV5NwPBPmZ", "��Q�P0", cB);
            cA[37] = dN("g7OHNfy0Zc2ZMQLizdkzqAe+ENDAV0e+0B1XnaOm48Gelqf35eXDyvQRqsTWa78U", "V�6��'", cB);
            cA[25] = dN("D15lSqhOGgwBWjbx+ek0tQe+ENDAV0e+0B1XnaOm48HLJTSNS0BEK5lK4wNF/pS9", "p�F�", cB);
            cA[8] = dN("uy0PCajwGed/Chv/PZ+xzwe+ENDAV0e+0B1XnaOm48H+IiF4iY+siFDnVII2Lk2T", "�\\e�\u00053", cB);
            cA[63] = dN("9BWDqWc68LE+x1CkafPo7Ae+ENDAV0e+0B1XnaOm48GGhPewr3+iaSxZJ7gZ4hEE", "��E\u0013��", cB);
            cA[65] = dN("usMAqZhQpS3Zq0k55TU0dAe+ENDAV0e+0B1XnaOm48FVufVxTtXCco1sUmO98Vf/", "�\u0015�\u0012b", cB);
            cA[92] = dN("DjdVaberVxPuQTvgEt9l5ge+ENDAV0e+0B1XnaOm48E8POCtV5EosEY/H00xEsKG", "�\u001d�\u0014\u0005H", cB);
            cA[17] = dN("RaSS9JvsKUgFfMXviWObVge+ENDAV0e+0B1XnaOm48H8JdSKBbmmM89ErPS54bb6", "�`t�\u0013�", cB);
            cA[67] = dN("D8GHakZFhWQSPnvTF9bmqQe+ENDAV0e+0B1XnaOm48EIMBh9OmggeZLeQQB/rYlx", "�:�C\u058c", cB);
            cA[44] = dN("tuZa+pkvoOapIvseJnR4Gge+ENDAV0e+0B1XnaOm48G35MOFm44kapINKkdMQWO3", "C�EjP\"", cB);
            cA[19] = dN("xUSOvxlNGY/4ossoEVGsJge+ENDAV0e+0B1XnaOm48HLpquPEjpPNmtVKK/1BLA9", "�<��\u001b�", cB);
            cA[82] = dN("JHDD8OD0RvTIOk9CaZQ7BQe+ENDAV0e+0B1XnaOm48GK1fb0j/A7mlQqJD8h5/Gg", "$�\u001e\b�\u0011", cB);
            cA[27] = dN("mhdxCCoqv5c2GnkuFF9wZAe+ENDAV0e+0B1XnaOm48F5wLDuhglqd8X6dtYPCq7i", "�\u001bc\n4\u001b", cB);
            cA[55] = dN("ZHK5gfFqI6PcVgN+DREDhQe+ENDAV0e+0B1XnaOm48E2uk4craJf1BoQtT4qyjXZ", "\u001c�P���", cB);
            cA[68] = dN("Tmd5ql2kYcy82x6rYpcL6Qe+ENDAV0e+0B1XnaOm48G+/UyuyfjPKGBy6XW+o8q4", "S\u001ejDuY", cB);
            cA[5] = dN("JA8cyAI+rEr8Yf48ry4Cige+ENDAV0e+0B1XnaOm48Gn4y1NLqaaw+p0b6r+EYZw", "\u0006>��Y�", cB);
            cA = new String[94];
        } catch (Exception e) {

        }
    }

    public static void dP() {
        cB = new byte[16];
        cB[6] = 71;
        cB[5] = 87;
        cB[11] = -99;
        cB[7] = -66;
        cB[1] = -66;
        cB[14] = -29;
        cB[0] = 7;
        cB[9] = 29;
        cB[3] = -48;
        cB[10] = 87;
        cB[8] = -48;
        cB[4] = -64;
        cB[15] = -63;
        cB[13] = -90;
        cB[2] = 16;
        cB[12] = -93;
    }

    public void parse(List<String> scriptLines) {
        String var2 = null;
        StringBuilder var3 = new StringBuilder();

        for (int var4 = 0; var4 < scriptLines.size(); var4++) {
            String var5 = ((String)scriptLines.get(var4)).trim();
            if (var5.startsWith(cA[47])) {
                var2 = var5.substring(3, var5.indexOf(cA[48])).trim();
                var3 = new StringBuilder();
            } else if (!var5.startsWith(cA[49])) {
                if (!var5.equals(cA[54])) {
                    if (var2 != null) {
                        var3.append(var5).append(cA[55]);
                    }
                } else if (var2 != null) {
                    String var12 = var3.toString().trim();
                    this.eventHandlers.put(var2, (Runnable)() -> this.executeActions(var12));
                    var2 = null;
                }
            } else {
                int var6 = var5.indexOf(cA[50]);
                int var7 = var5.indexOf(cA[51]);
                if (var6 != -1 && var7 != -1) {
                    String var8 = var5.substring(5, var6).trim();
                    String var9 = var5.substring(var6 + 1, var7).trim();
                    StringBuilder var10 = new StringBuilder();

                    while (++var4 < scriptLines.size()) {
                        String var11 = ((String)scriptLines.get(var4)).trim();
                        if (var11.equals(cA[52])) {
                            break;
                        }

                        var10.append(var11).append(cA[53]);
                    }

                    this.functions.put(var8, new FunctionData(var9, var10.toString().trim()));
                }
            }
        }
    }

    void executeFunction(String functionName, String paramValue) {
        FunctionData var3 = (FunctionData)this.functions.get(functionName);
        if (var3 != null) {
            String var4 = var3.actions.replace(var3.paramName, paramValue);
            this.executeActions(var4);
        }
    }

    boolean evaluateCondition(String condition) {
        if (!condition.contains(cA[81])) {
            if (!condition.contains(cA[83])) {
                if (!condition.contains(cA[85])) {
                    if (!condition.contains(cA[87])) {
                        if (!condition.contains(cA[89])) {
                            if (!condition.contains(cA[91])) {
                                return false;
                            } else {
                                String[] var7 = condition.split(cA[92]);
                                return Double.parseDouble(this.evaluateExpression(var7[0].trim()).toString())
                                        <= Double.parseDouble(this.evaluateExpression(var7[1].trim()).toString());
                            }
                        } else {
                            String[] var6 = condition.split(cA[90]);
                            return Double.parseDouble(this.evaluateExpression(var6[0].trim()).toString())
                                    >= Double.parseDouble(this.evaluateExpression(var6[1].trim()).toString());
                        }
                    } else {
                        String[] var5 = condition.split(cA[88]);
                        return Double.parseDouble(this.evaluateExpression(var5[0].trim()).toString())
                                < Double.parseDouble(this.evaluateExpression(var5[1].trim()).toString());
                    }
                } else {
                    String[] var4 = condition.split(cA[86]);
                    return Double.parseDouble(this.evaluateExpression(var4[0].trim()).toString())
                            > Double.parseDouble(this.evaluateExpression(var4[1].trim()).toString());
                }
            } else {
                String[] var3 = condition.split(cA[84]);
                return !this.evaluateExpression(var3[0].trim()).equals(this.evaluateExpression(var3[1].trim()));
            }
        } else {
            String[] var2 = condition.split(cA[82]);
            return this.evaluateExpression(var2[0].trim()).equals(this.evaluateExpression(var2[1].trim()));
        }
    }

    static class FunctionData {
        public String paramName;
        public String actions;

        public FunctionData(String actions, String paramName) {
            this.paramName = actions;
            this.actions = paramName;
        }
    }
}
