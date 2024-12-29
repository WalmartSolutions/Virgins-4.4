package bre2el.fpsreducer.util;

import bre2el.fpsreducer.client.Main;
import bre2el.fpsreducer.mixin.MouseAccessor;
import bre2el.fpsreducer.util.InputUtil.Quartz;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.platform.unix.X11;
import com.sun.jna.platform.unix.X11.Display;
import com.sun.jna.platform.unix.X11.Window;
import com.sun.jna.platform.unix.X11.XEvent;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.LPARAM;
import com.sun.jna.platform.win32.WinDef.WPARAM;

import java.awt.*;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.macosx.CGPoint;

public class InputUtil {
    public static String[] fS;
    public static byte[] fT;
    public static boolean rightClicked;
    public static boolean leftClicked;

    static void callMouseWindows(int action, int button) {
        HWND var6 = User32.INSTANCE.GetForegroundWindow();
        if (action == 0 && button == 1) {
            leftClicked = true;
            User32.INSTANCE.SendMessage(var6, 513, new WPARAM(1L), new LPARAM(0L));
        }

        if (action == 0 && button == 0) {
            User32.INSTANCE.SendMessage(var6, 514, new WPARAM(1L), new LPARAM(0L));
        }

        if (action == 1 && button == 1) {
            rightClicked = true;
            User32.INSTANCE.SendMessage(var6, 516, new WPARAM(1L), new LPARAM(0L));
        }

        if (action == 1 && button == 0) {
            User32.INSTANCE.SendMessage(var6, 517, new WPARAM(1L), new LPARAM(0L));
        }
    }

    static {
        iQ();
        iP();
    }

    public static void iQ() {
        fT = new byte[16];
        fT[5] = 91;
        fT[0] = -83;
        fT[10] = 77;
        fT[9] = 101;
        fT[3] = 49;
        fT[6] = 87;
        fT[12] = -74;
        fT[7] = 35;
        fT[15] = 45;
        fT[8] = 57;
        fT[14] = -9;
        fT[2] = -82;
        fT[4] = 79;
        fT[11] = -68;
        fT[13] = 40;
        fT[1] = -29;
    }

    static void callMouseMac(int button, int action) {
        Quartz var2 = Quartz.INSTANCE;
        int var3 = -1;
        if (button == 0 && action == 1) {
            var3 = 1;
        }

        if (button == 0 && action == 0) {
            var3 = 2;
        }

        if (button == 1 && action == 1) {
            var3 = 3;
        }

        if (button == 1 && action == 0) {
            var3 = 4;
        }

        if (var3 != -1) {
            Pointer var4 = var2.CGEventCreateMouseEvent(null, var3, null, 0);
            var2.CGEventPost(0, var4);
            var2.CFRelease(var4);
        }
    }

    public static void callMouse(int button, int action) {
        if (Main.mc.currentScreen == null && Main.mc.isWindowFocused()) {
            if (!Main.OS.contains(fS[52])) {
                callMouseNative(button, action);
            } else {
                callMouseWindows(button, action);
            }

            new Thread(() -> {
                try {
                    Thread.sleep(25L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (leftClicked) {
                    leftClicked = false;
                    if (!Main.OS.contains(fS[92])) {
                        callMouseNative(0, 0);
                    } else {
                        callMouseWindows(0, 0);
                    }
                }

                if (rightClicked) {
                    rightClicked = false;
                    if (!Main.OS.contains(fS[93])) {
                        callMouseNative(1, 0);
                    } else {
                        callMouseWindows(1, 0);
                    }
                }
            }).start();
        }
    }

    public static boolean checkMouse(int button) {
        if (button != 0) {
            if (button != 1) {
                return false;
            } else {
                return !Main.OS.contains(fS[48]) ? GLFW.glfwGetMouseButton(Main.mc.getWindow().getHandle(), 1) == 1 : User32.INSTANCE.GetAsyncKeyState(2) == -32768;
            }
        } else {
            return !Main.OS.contains(fS[47]) ? GLFW.glfwGetMouseButton(Main.mc.getWindow().getHandle(), 0) == 1 : User32.INSTANCE.GetAsyncKeyState(1) == -32768;
        }
    }

    public static String iO(String var0, String var1, byte[] var2) throws Exception {
        byte[] var3 = Base64.getDecoder().decode(var0);
        byte[] var4 = new byte[]{-29, 93, -37, -127, 0, 99, -78, -55, -21, 96, -41, -85, 113, -25, -20, 32};
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

    //was supposed to be a switch but qprotect sucks so
    public static String getKeyString(int key) {
        if (key != 0) {
            if (key != 1) {
                if (key != 2) {
                    if (key != 3) {
                        if (key != 4) {
                            if (key >= 48 && key <= 90) {
                                return String.valueOf((char)key);
                            } else if (key != 342) {
                                if (key != 346) {
                                    if (key != 340) {
                                        if (key != 344) {
                                            if (key != 341) {
                                                if (key != 345) {
                                                    if (key != 32) {
                                                        if (key != 257) {
                                                            if (key != 259) {
                                                                if (key != 92) {
                                                                    if (key != 260) {
                                                                        if (key != 261) {
                                                                            if (key != 268) {
                                                                                if (key != 269) {
                                                                                    if (key != 266) {
                                                                                        if (key != 267) {
                                                                                            if (key != 258) {
                                                                                                if (key != 280) {
                                                                                                    if (key != 96) {
                                                                                                        if (key != 45) {
                                                                                                            if (key != 290) {
                                                                                                                if (key != 291) {
                                                                                                                    if (key != 292) {
                                                                                                                        if (key != 293) {
                                                                                                                            if (key != 294) {
                                                                                                                                if (key != 295) {
                                                                                                                                    if (key != 296) {
                                                                                                                                        if (key != 297) {
                                                                                                                                            if (key != 298) {
                                                                                                                                                if (key != 299) {
                                                                                                                                                    if (key != 300) {
                                                                                                                                                        return key != 301 ? "" : fS[89];
                                                                                                                                                    } else {
                                                                                                                                                        return fS[88];
                                                                                                                                                    }
                                                                                                                                                } else {
                                                                                                                                                    return fS[87];
                                                                                                                                                }
                                                                                                                                            } else {
                                                                                                                                                return fS[86];
                                                                                                                                            }
                                                                                                                                        } else {
                                                                                                                                            return fS[85];
                                                                                                                                        }
                                                                                                                                    } else {
                                                                                                                                        return fS[84];
                                                                                                                                    }
                                                                                                                                } else {
                                                                                                                                    return fS[83];
                                                                                                                                }
                                                                                                                            } else {
                                                                                                                                return fS[82];
                                                                                                                            }
                                                                                                                        } else {
                                                                                                                            return fS[81];
                                                                                                                        }
                                                                                                                    } else {
                                                                                                                        return fS[80];
                                                                                                                    }
                                                                                                                } else {
                                                                                                                    return fS[79];
                                                                                                                }
                                                                                                            } else {
                                                                                                                return fS[78];
                                                                                                            }
                                                                                                        } else {
                                                                                                            return fS[77];
                                                                                                        }
                                                                                                    } else {
                                                                                                        return fS[76];
                                                                                                    }
                                                                                                } else {
                                                                                                    return fS[75];
                                                                                                }
                                                                                            } else {
                                                                                                return fS[74];
                                                                                            }
                                                                                        } else {
                                                                                            return fS[73];
                                                                                        }
                                                                                    } else {
                                                                                        return fS[72];
                                                                                    }
                                                                                } else {
                                                                                    return fS[71];
                                                                                }
                                                                            } else {
                                                                                return fS[70];
                                                                            }
                                                                        } else {
                                                                            return fS[69];
                                                                        }
                                                                    } else {
                                                                        return fS[68];
                                                                    }
                                                                } else {
                                                                    return fS[67];
                                                                }
                                                            } else {
                                                                return fS[66];
                                                            }
                                                        } else {
                                                            return fS[65];
                                                        }
                                                    } else {
                                                        return fS[64];
                                                    }
                                                } else {
                                                    return fS[63];
                                                }
                                            } else {
                                                return fS[62];
                                            }
                                        } else {
                                            return fS[61];
                                        }
                                    } else {
                                        return fS[60];
                                    }
                                } else {
                                    return fS[59];
                                }
                            } else {
                                return fS[58];
                            }
                        } else {
                            return fS[57];
                        }
                    } else {
                        return fS[56];
                    }
                } else {
                    return fS[55];
                }
            } else {
                return fS[54];
            }
        } else {
            return "";
        }
    }

    public static void sendInput(int button, int action) {
        if (Main.mc.currentScreen == null && Main.mc.isWindowFocused()) {
            if (!Main.OS.contains(fS[49])) {
                if (!Main.OS.contains(fS[50])) {
                    if (Main.OS.contains(fS[51])) {
                        callMouseNative(button, action);
                    }
                } else {
                    callMouseNative(button, action);
                }
            } else {
                callMouseWindows(button, action);
            }
        }
    }

    public static void callMouse(int delay, int action, long button) {
        if (Main.mc.currentScreen == null && Main.mc.isWindowFocused()) {
            if (!Main.OS.contains(fS[53])) {
                callMouseNative(delay, action);
            } else {
                callMouseWindows(delay, action);
            }

            new Thread(() -> {
                try {
                    Thread.sleep(button);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (leftClicked) {
                    leftClicked = false;
                    if (!Main.OS.contains(fS[90])) {
                        callMouseNative(0, 0);
                    } else {
                        callMouseWindows(0, 0);
                    }
                }

                if (rightClicked) {
                    rightClicked = false;
                    if (!Main.OS.contains(fS[91])) {
                        callMouseNative(1, 0);
                    } else {
                        callMouseWindows(1, 0);
                    }
                }
            }).start();
        }
    }

    public static void iP() {
        try {
            fS = new String[94];
            fS[11] = iO("uqhOx1lTQUBdH3PkprCt+a3jrjFPW1cjOWVNvLYo9y0GuDS0F8Yb5u0nE6iQh7/S", "~�\u0018���", fT);
            fS[1] = iO("3fdWHBfH38z1EK0icgRTWK3jrjFPW1cjOWVNvLYo9y09NsrVJR0bFwBcl/wmAd9j", "�,U�z�", fT);
            fS[3] = iO("tWYf/s4jigCgKTNv/fl2qa3jrjFPW1cjOWVNvLYo9y1GB9WsILYCoUObDyGpOsRj", "�~1 �-", fT);
            fS[65] = iO("BxhEgFd0OJC7t+nl/+CV1a3jrjFPW1cjOWVNvLYo9y1yroL0BdXaKGxeDFVVcTCh", "b�}\u000e#\u001a", fT);
            fS[50] = iO("6kdq3U7CFN/yzrY48huzua3jrjFPW1cjOWVNvLYo9y2lkgjemPwZPy0BjfPM/lrV", "´\u000bjHP", fT);
            fS[64] = iO("16iXXn8GUjuxspF5pdRvq63jrjFPW1cjOWVNvLYo9y0MoCz8As5Go0qSmfsDfpG8", "5!�i\u00178", fT);
            fS[66] = iO("/9A8TMK38hZVxa88pl9hXa3jrjFPW1cjOWVNvLYo9y2sfyqLrWi8IZ30OUGepoQg", "�!no˨", fT);
            fS[74] = iO("GB2d0JxWTeFC08qyevY2pa3jrjFPW1cjOWVNvLYo9y3vMB3vKI+5okrDezg6krQz", "\u001e\u0011\u0012�7�", fT);
            fS[93] = iO("mF/5yinXn7q0B6RDTuL3ja3jrjFPW1cjOWVNvLYo9y3+ZdwaPQ2ajh918v5+0Dfy", "�C��\r�", fT);
            fS[58] = iO("ajulGAfaKXLHoJR5NhbJ3K3jrjFPW1cjOWVNvLYo9y0mIUCmMlDkJNY1XC6z1qTE", "�N�f\u00015", fT);
            fS[52] = iO("r6SToRMy8mctVuvJaPBhaq3jrjFPW1cjOWVNvLYo9y3lNXEb+q/lfsYh0PilmpZy", "r��ۭ\u007f", fT);
            fS[33] = iO("LONe3Z5KZ+eexPTGDk0kHq3jrjFPW1cjOWVNvLYo9y3BClKw07mk308eN+QDDcO0", "@�=�DM", fT);
            fS[43] = iO("0qPadPqv+a9GsqUxpcpFYq3jrjFPW1cjOWVNvLYo9y1tgFogNPhBTeFCuG0HQKQc", "�P� ��", fT);
            fS[85] = iO("CF/BXfvrvAFU/SSTmeVLnq3jrjFPW1cjOWVNvLYo9y3KSkzVKsBvsHkuJirOqGWb", "tԘ��\u0014", fT);
            fS[61] = iO("o1PNYGoyHj8pDkcPOWS/Ga3jrjFPW1cjOWVNvLYo9y3z7TT++2PCm/mIUuyqg/Dz", "\u001e��\u0014�\u0007", fT);
            fS[68] = iO("0fS6koqPX5kZZ8EFsBZptq3jrjFPW1cjOWVNvLYo9y13y+A1r0dET5x3jc5uLk/J", ",�$3�c", fT);
            fS[2] = iO("Q7B65z+i709oWUfyaBXDXa3jrjFPW1cjOWVNvLYo9y2aHOIadE/Cb9ApHeJ5ebyH", "��\u0013�e�", fT);
            fS[36] = iO("ZwqmEpVj+Jg+fL1o8cGyNq3jrjFPW1cjOWVNvLYo9y2D0l35DBLbd2B178wIyQC5", "�ݟ�!\u001f", fT);
            fS[10] = iO("zauWe8ahaOilKsTv75mPxK3jrjFPW1cjOWVNvLYo9y1QdyO7scxPfPIzqlKot9Qq", "I\u0005��\u001a�", fT);
            fS[22] = iO("oS/gtz+wUS+ZcX2NR9GKo63jrjFPW1cjOWVNvLYo9y3XDsXeo+3/kHfSaIZbk2Km", "�]lL��", fT);
            fS[21] = iO("9tIBzFTS9EqtwDs6ZkV7563jrjFPW1cjOWVNvLYo9y3sOyt1grDes4dgLDLdhgEY", "�N��F�", fT);
            fS[49] = iO("HWjwghc7znpLgTDp1eOEAK3jrjFPW1cjOWVNvLYo9y21CrGPAjo/QYnbEO20zQLJ", "kM�/*\u0007", fT);
            fS[59] = iO("bt/Fm+tTUMpS59GI/4mPI63jrjFPW1cjOWVNvLYo9y0Iy6ic1YDlGtxHOBWprNus", "��rz��", fT);
            fS[5] = iO("lKXpgYnbTsF2SzMVV1qO+K3jrjFPW1cjOWVNvLYo9y0kzn/xMWQnGuQtxrwjc+ER", "\u001f�����", fT);
            fS[44] = iO("FFnagGkAt+55BkFCWzt+Kq3jrjFPW1cjOWVNvLYo9y2EOjLg23qZs81zYJPr8uxv", "\u0001��q�\n", fT);
            fS[60] = iO("nX2keyKN5/G/0vS00yjG1q3jrjFPW1cjOWVNvLYo9y1R/o6JAgPSXPGTlCHdBmGz", "��$���", fT);
            fS[57] = iO("dZ6xQ2+1gB09el2PEa7ARq3jrjFPW1cjOWVNvLYo9y0QzwlQkfG+/ic5HIYo6A6N", "\u0014ֳ��+", fT);
            fS[51] = iO("8969V8T+c1L5+9mH4Kl68q3jrjFPW1cjOWVNvLYo9y3SzzdKxSB4Cqv1dvWLpVMZ", "q�<ΊM", fT);
            fS[16] = iO("Dzzr8xEpzm95rlxqi0kura3jrjFPW1cjOWVNvLYo9y3iZyHRNbiGG4W7nb9tp2m6", "�b$\t$", fT);
            fS[91] = iO("D6M9GjUq25v9i2vJ4kocH63jrjFPW1cjOWVNvLYo9y2sXJi/aGg23/7VABD7uJNy", "nf��e\r", fT);
            fS[72] = iO("o98NsxkzZZmIHZZD/EsqO63jrjFPW1cjOWVNvLYo9y3H9n02DgMjdVpvrbbDW8G2", "��\u001a���", fT);
            fS[81] = iO("rX+lhJ3VRTv43AtxG6u1uq3jrjFPW1cjOWVNvLYo9y2YSvP72UZGDA/S2crTh089", "�>u���", fT);
            fS[88] = iO("lYfiIsolJ/2EXdXDN0cNAq3jrjFPW1cjOWVNvLYo9y2NNoOJzRcPS/hjfRkKoXIR", "\u001b�y>LV", fT);
            fS[46] = iO("YSetxA/f7PE0MoBmwV0REK3jrjFPW1cjOWVNvLYo9y32PDSzkZ/YPyK8dFfFoZUt", "\u000f*��H�", fT);
            fS[40] = iO("4uWgNfYERS7xFdiGHyLb5q3jrjFPW1cjOWVNvLYo9y1UrGVhcq+E7pBYqUxD32RN", "Ƅ\u000e\u0016��", fT);
            fS[84] = iO("yr8QE88fMqETyRS5zxjVOa3jrjFPW1cjOWVNvLYo9y31pz3jizJ9cVwzCiLFCUpf", "�Ɩҫ", fT);
            fS[62] = iO("BvNtPiH8EgPpwziPPY2yWa3jrjFPW1cjOWVNvLYo9y1zhCnMnYiQqmgjNTPdHiWR", "�\u0013t\u00018[", fT);
            fS[82] = iO("qcdUa4IMUj+yOcfr9nUg063jrjFPW1cjOWVNvLYo9y0YoI7pJL8SSwAKQWtTxr/2", "\\\u0381���", fT);
            fS[78] = iO("ebmf74snxmyDaQ2rtlbMSq3jrjFPW1cjOWVNvLYo9y23gf1ljuM5AdYngtbTuIj5", "\t���>�", fT);
            fS[53] = iO("tRKjWQ1+YBSvFFZdxrCapa3jrjFPW1cjOWVNvLYo9y3f0K2Pmz8alrDBnmdrnq1T", "�>�H�1", fT);
            fS[71] = iO("rvMdsvtf3VF4NG7zZyABvK3jrjFPW1cjOWVNvLYo9y0NG9OP/GYLAukIwoaSunb7", "TY�F\u0016q", fT);
            fS[67] = iO("q6KGptw2pf7UU0AQsRIbXq3jrjFPW1cjOWVNvLYo9y30HugPwzJ0R+V8/aIIz8NH", "\u0010�$���", fT);
            fS[25] = iO("4B6piSqNRmPs0nB3aWhWWq3jrjFPW1cjOWVNvLYo9y02oAKs5ZJxveiVv8WJ2gRE", "�cc��\u001d", fT);
            fS[90] = iO("kbZXx1T9bMwBDZPkGDfJsK3jrjFPW1cjOWVNvLYo9y2UoqQHhvSrZUUN2hUFkoqn", "����e�", fT);
            fS[55] = iO("+ed1bEXGbP8qYDRro8Nw5K3jrjFPW1cjOWVNvLYo9y04kyn4bH3cNqsDwpWFbkSb", "c2��u�", fT);
            fS[37] = iO("tnpZve6JHTvBfgWIc+gKSq3jrjFPW1cjOWVNvLYo9y335z6FrWVHI5BgJBsmWAmH", "\t��\u0016\u0016\u001e", fT);
            fS[31] = iO("djBdPXH3RN1ooTZ0i+/9rq3jrjFPW1cjOWVNvLYo9y30O/8zsTPFUGrU0wxxnlmb", "�\u0017�S\u0013�", fT);
            fS[26] = iO("97kCrNHedPtRXtyAwXo/5K3jrjFPW1cjOWVNvLYo9y0iHgubojC8YKs1ygJqWk0I", "�p�.�b", fT);
            fS[13] = iO("QyjKVXBXcEUws3Dq7P9D3K3jrjFPW1cjOWVNvLYo9y3Y2Hwz/XNT2xWidoYUkqZZ", "\u0010wcy��", fT);
            fS[35] = iO("ygZ3pKTH3upe7fSPx/6zCq3jrjFPW1cjOWVNvLYo9y1/SD2Zt2uYGKxFkNF4drmJ", "\u0003\u0002��\u000ep", fT);
            fS[92] = iO("dLWNzJE0j9HH+g/4DcHI363jrjFPW1cjOWVNvLYo9y1tmu2KizhOJuioXH+pzEVQ", "�Ǝ\\��", fT);
            fS[75] = iO("VugK0Jn9aV/ZLrW30xZP/a3jrjFPW1cjOWVNvLYo9y2PuyA0ylVCwA3FkxrHhhox", "t�����", fT);
            fS[4] = iO("dUpfZi35EdyoPlPQELVMVK3jrjFPW1cjOWVNvLYo9y0dWaf86qqCbqk7I1nzmgKL", "��C�>e", fT);
            fS[70] = iO("cuG8I4NHfGdSXYF8z/KMCK3jrjFPW1cjOWVNvLYo9y2ETYw6JQ5xUZarATpEQBjo", "�\u0019��Z\u001a", fT);
            fS[76] = iO("JoJ6VycSq95HT7rzN3h4r63jrjFPW1cjOWVNvLYo9y299Xfyl4GMVMnpYLbUUgB7", "am�RmB", fT);
            fS[9] = iO("7DrCeE38KxGtjI9siNnZ163jrjFPW1cjOWVNvLYo9y1QfEZ3loKIOab3h16Bnntj", "\u0019�N���", fT);
            fS[42] = iO("yPTFRBC6Pw47+kWDyEeIiq3jrjFPW1cjOWVNvLYo9y3Cg6fzr31T7f5Y9CFlHN6+", "�',��?", fT);
            fS[56] = iO("DIO3yJyrmCAqm9em/OBxsK3jrjFPW1cjOWVNvLYo9y1lRpyIs5je1eTOfTBJ/Sro", "��>�3-", fT);
            fS[32] = iO("2np1HhOWNtOxxPAc5sITY63jrjFPW1cjOWVNvLYo9y0jcTHjmVNFSy1S1twRjY1L", "�\u0016��BC", fT);
            fS[20] = iO("yS1R15zILiyDM5YDRD1bPa3jrjFPW1cjOWVNvLYo9y1QDOQFlpe4lmMqkOLdLxW7", "�lga�\u000e", fT);
            fS[77] = iO("bQCKilf0N0tJ7W10r5tk3q3jrjFPW1cjOWVNvLYo9y2fFXsganp33/0CYn1304Pa", "5�?1�w", fT);
            fS[7] = iO("4L/ZyH+Lp0GsEMYpEY4KbK3jrjFPW1cjOWVNvLYo9y2gXJS1bxz2DWofIQ60e/jE", "\n\u001c\u000b�&�", fT);
            fS[6] = iO("l8oyospYSY78btYRUo2b1a3jrjFPW1cjOWVNvLYo9y1yr8++NCfXdceDT1Zg+MMg", "6��-�a", fT);
            fS[87] = iO("det6+wf0HlkIROYR47cStK3jrjFPW1cjOWVNvLYo9y3F6+NanQ79Z9YRrCnuzyCV", "�Ӳ۽", fT);
            fS[34] = iO("9ziBsJ8tS8rV7YYwK4SA/K3jrjFPW1cjOWVNvLYo9y2SsQd+7Bzc0Fi79acsRQOX", "��y�w�", fT);
            fS[24] = iO("KRzsMCjoFtkPT4BNGBcxOq3jrjFPW1cjOWVNvLYo9y1GINro3WshL4Nt8BzdyZHA", "\u0013��i\u0000l", fT);
            fS[30] = iO("lvlyxnR0pHczV/tKTWOQmK3jrjFPW1cjOWVNvLYo9y20ZrgvZqJl36uSkqlEvAw5", "��gT�", fT);
            fS[12] = iO("3QUqjEh65HMGlDLgHWi4HK3jrjFPW1cjOWVNvLYo9y3cwgYnYZImenZNjKcXb2cw", "<�\t}��", fT);
            fS[38] = iO("HgJm0IANNRvDuqEuxIq2Ga3jrjFPW1cjOWVNvLYo9y2jo8cDeGpfF+q+Tt3QCc1a", "Ӹ=�\u0012�", fT);
            fS[73] = iO("3RpexnOUDfaK1g+FQbH8b63jrjFPW1cjOWVNvLYo9y0aEFqMrKeibJFojvgWZkCT", "�#��\u0018", fT);
            fS[0] = iO("T4XlVwScxbvh1GhpOZl+ua3jrjFPW1cjOWVNvLYo9y1HFiRomzCVtkkhHdzL4UJN", "Q2����", fT);
            fS[29] = iO("7MMSWgk9Ff8MkutpbW5TR63jrjFPW1cjOWVNvLYo9y1XwSXPHRObn118tzeVSDEv", "��\u0015j��", fT);
            fS[28] = iO("g4h2cCiQSTWo2z/JMlWula3jrjFPW1cjOWVNvLYo9y3T8akioVxDP1pn06oanVc4", "\f\t�C͘", fT);
            fS[14] = iO("rgkXKBFjvAtnM2iPcDpGPq3jrjFPW1cjOWVNvLYo9y083qbN9lNtjc4+udRha+m2", "�����\u0002", fT);
            fS[63] = iO("nWThySCUTulCwEOImfDSYa3jrjFPW1cjOWVNvLYo9y0tfSrsk6Wc56wyhTAHB02l", ";\\\\\u0005�Z", fT);
            fS[15] = iO("Rqo4/dvLjJSDIkhTls7wyK3jrjFPW1cjOWVNvLYo9y0WPbsBlBqz1t7wZ399kze3", "\".\u001a\u001b7M", fT);
            fS[8] = iO("4I8prWVvhhg1cAuaaplsKK3jrjFPW1cjOWVNvLYo9y2h0VUY6ndfofpP+IcIc9Qd", "�<�MD�", fT);
            fS[54] = iO("oBN6ZA9MOQrDRP30E0vd8a3jrjFPW1cjOWVNvLYo9y12ktPQ/eAVnrOQlPMyvdXW", "{�'Z�i", fT);
            fS[86] = iO("u8K3iUuGq0+R1GRULiUkta3jrjFPW1cjOWVNvLYo9y0Dn1DvzMrdNCWacnIc2Lu7", "\":\u000eIX�", fT);
            fS[23] = iO("JqiPcEmFVJQBJf9Wt6J8c63jrjFPW1cjOWVNvLYo9y1XItKGzQn+u0kK0yoVHTGX", "\u0007�KM\u001e~", fT);
            fS[39] = iO("X4AXkrgVzbbZ6aInDq90Aq3jrjFPW1cjOWVNvLYo9y20HRbI+CPozjUfLmi6nxwz", "η�KF�", fT);
            fS[27] = iO("n5vW8n+otf1z/CCRYRPugK3jrjFPW1cjOWVNvLYo9y2NRJd+1cv7YxAVOKiSmPzH", "\fq2?-�", fT);
            fS[19] = iO("VADxvY8+icZcM3jv9233KK3jrjFPW1cjOWVNvLYo9y2EBxDfyjlipLeZDeNxtVj1", "\u0005i\u0011IM�", fT);
            fS[79] = iO("kU4kZsLhz0QQxKM28FkwVa3jrjFPW1cjOWVNvLYo9y3mmWBDxzb0zWQUTlph5bkE", "\u0007\u0016C�hu", fT);
            fS[41] = iO("HffZyST0Xsq6fUkNUy5u8a3jrjFPW1cjOWVNvLYo9y3iigOjp/wgP47aOf/18s4c", "r�g\u0018�_", fT);
            fS[83] = iO("7szN19MMlvuOCtPNHjpwya3jrjFPW1cjOWVNvLYo9y0/fRZLPhoQEMo3OA+RuGQZ", "L�{@3�", fT);
            fS[47] = iO("0mLT+V3x0FVNakjHjlwd563jrjFPW1cjOWVNvLYo9y0+6hwZ1b8rbJcFT/5qnWX3", "W���١", fT);
            fS[48] = iO("DJya+WlH0aszVjSyC196nq3jrjFPW1cjOWVNvLYo9y2DLxrM9l8ADy3O597jiwO9", "��\u001f\u001eg", fT);
            fS[69] = iO("i7XPgvIFVCCBx2eojjDEua3jrjFPW1cjOWVNvLYo9y1ntZnZ8VC4UCYj8llybghj", "���.+�", fT);
            fS[17] = iO("4cWzm7Vam9xx36mjSDMr7q3jrjFPW1cjOWVNvLYo9y3fPEL4v9SzFBVDTJrnuXRw", "$PKҚL", fT);
            fS[89] = iO("Dh3O2C1fz4oPoL1lgK4lj63jrjFPW1cjOWVNvLYo9y04eiXLQqgFEKWUah6H2ISB", "ǉ�B�/", fT);
            fS[80] = iO("6O8rfFyAQeAFYdz9/8qLga3jrjFPW1cjOWVNvLYo9y1d/lhiXzS3FQhMIm5SQ+DX", "�2a��\r", fT);
            fS[18] = iO("W9zC8DNNeTFsiQgCioDT463jrjFPW1cjOWVNvLYo9y0D99EN0EV9GhWIe/Zg2ll3", "�\u0014�ie�", fT);
            fS[45] = iO("FHH1QFudcoLx+D1VcAX1Rq3jrjFPW1cjOWVNvLYo9y0LavHpAsmfSKtZss4XYhCq", "�x��\u0013v", fT);
        } catch (Exception e) {}
    }

    static void callMouseLinux(int action, int button) {
        X11 var2 = X11.INSTANCE;
        Display var3 = var2.XOpenDisplay(null);
        if (var3 != null) {
            int var4 = action != 0 ? 3 : 1;
            int var5 = button != 1 ? 5 : 4;
            XEvent var6 = new XEvent();
            var6.type = var5;
            var6.xbutton.button = var4;
            var6.xbutton.same_screen = 1;
            Window var7 = var2.XDefaultRootWindow(var3);
            var6.xbutton.window = var7;
            var6.xbutton.root = var7;
            var2.XSendEvent(var3, var7, 1, new NativeLong(4L), var6);
            var2.XFlush(var3);
            var2.XCloseDisplay(var3);
        }
    }

    public static void callMouseNative(int button, int action) {
        if (Main.mc.currentScreen == null && Main.mc.isWindowFocused()) {
            ((MouseAccessor)Main.mc.mouse).callOnMouseButton(Main.mc.getWindow().getHandle(), button, action, 0);
            if (button == 0 && action == 1) {
                leftClicked = true;
            }

            if (button == 1 && action == 1) {
                rightClicked = true;
            }
        }
    }

    interface Quartz extends Library {
        int kCGEventLeftMouseUp = 2;
        int kCGMouseButtonRight = 1;
        int kCGEventLeftMouseDown = 1;
        int kCGEventRightMouseDown = 3;
        int kCGHIDEventTap = 0;
        static Quartz INSTANCE = (Quartz) Native.load("ApplicationServices", Quartz.class);
        int kCGMouseButtonLeft = 0;
        int kCGEventRightMouseUp = 4;

        void CFRelease(Pointer var1);

        Pointer CGEventCreateMouseEvent(Pointer var1, int var2, CGPoint var3, int var4);

        void CGEventPost(int var1, Pointer var2);

        class CGPoint {
            public double x;
            public static byte[] cf;
            public double y;
            public static String[] ce;

            static {
                di();
                dh();
            }

            List<String> getFieldOrder() {
                return Arrays.asList(ce[2], ce[3]);
            }

            public static void dh() {
                try {
                    ce = new String[4];
                    ce[3] = dg("XMzw6r71o5gT+4WqHcAf5OOVuf1XtbhTgdLe8TLOddkLl2odPKQZqH0P5SJcbW9/", "R��\u001c�p", cf);
                    ce[1] = dg("6z/5YhraNnfvIiMcQMVnouOVuf1XtbhTgdLe8TLOddnq9SHjgIpuJ0MG+B2pZ+Yd", "\u0012.\u009c�|", cf);
                    ce[2] = dg("WFyyKr1KzAqUBqRURowhd+OVuf1XtbhTgdLe8TLOddlbdn0Vn4V825Hlb3ja3chS", "�#�M\b�", cf);
                    ce[0] = dg("ARbRV7Uk+50c0gVsgJFXmOOVuf1XtbhTgdLe8TLOddnxVs4rNUkfI4WVq3fsXRN1", "�,���", cf);
                } catch (Exception e) {}
            }

            public static String dg(String var0, String var1, byte[] var2) throws Exception {
                byte[] var3 = Base64.getDecoder().decode(var0);
                byte[] var4 = new byte[]{-3, 51, 95, -83, -29, 120, 109, 107, 0, -14, -2, -97, -4, -72, 41, 47};
                byte[] var5 = new byte[var3.length - 32];
                System.arraycopy(var3, 0, var4, 0, 16);
                System.arraycopy(var3, 32, var5, 0, var3.length - 32);
                PBEKeySpec var6 = new PBEKeySpec(var1.toCharArray(), var4, 88, 256);
                SecretKeyFactory var7 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
                byte[] var8 = var7.generateSecret(var6).getEncoded();
                SecretKeySpec var9 = new SecretKeySpec(var8, "AES");
                Cipher var10 = Cipher.getInstance("AES/CBC/PKCS5Padding");
                var10.init(2, var9, new IvParameterSpec(var2));
                byte[] var11 = var10.doFinal(var5);
                String info = new String(var11, "UTF-8");
        return info;
            }

            public CGPoint(double x, double y) {
                this.x = x;
                this.y = y;
            }

            public static void di() {
                cf = new byte[16];
                cf[5] = -75;
                cf[2] = -71;
                cf[9] = -46;
                cf[8] = -127;
                cf[14] = 117;
                cf[6] = -72;
                cf[13] = -50;
                cf[4] = 87;
                cf[10] = -34;
                cf[11] = -15;
                cf[0] = -29;
                cf[3] = -3;
                cf[15] = -39;
                cf[1] = -107;
                cf[7] = 83;
                cf[12] = 50;

            }
        }
    }
}
