/*
 * Decompiled with CFR 0.152.
 */
package Abyss.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public enum ChatFormatting {
    BLACK(0, '0', -16777216, (char)ChatFormatting.zkm$g24()),
    DARK_BLUE(1, '1', -16777046, (char)ChatFormatting.zkm$g24()),
    DARK_GREEN(2, '2', -16733696, (char)ChatFormatting.zkm$g24()),
    DARK_AQUA(3, '3', -16733526, (char)ChatFormatting.zkm$g24()),
    DARK_RED(4, '4', -5636096, (char)ChatFormatting.zkm$g24()),
    DARK_PURPLE(5, '5', -5635926, (char)ChatFormatting.zkm$g24()),
    GOLD(6, '6', -22016, (char)ChatFormatting.zkm$g24()),
    GRAY(7, '7', -5592406, (char)ChatFormatting.zkm$g24()),
    DARK_GRAY(8, '8', -11184811, (char)ChatFormatting.zkm$g24()),
    BLUE(9, '9', -11184641, (char)ChatFormatting.zkm$g24()),
    GREEN(10, 'a', -11141291, (char)ChatFormatting.zkm$g24()),
    AQUA(11, 'b', -11141121, (char)ChatFormatting.zkm$g24()),
    RED(12, 'c', -43691, (char)ChatFormatting.zkm$g24()),
    LIGHT_PURPLE(13, 'd', -43521, (char)ChatFormatting.zkm$g24()),
    YELLOW(14, 'e', -171, (char)ChatFormatting.zkm$g24()),
    WHITE(15, 'f', -1, (char)ChatFormatting.zkm$g24()),
    MAGIC(16, 'k', 0, (char)ChatFormatting.zkm$g24()),
    BOLD(17, 'l', 0, (char)ChatFormatting.zkm$g24()),
    STRIKETHROUGH(18, 'm', 0, (char)ChatFormatting.zkm$g24()),
    UNDERLINE(19, 'n', 0, (char)ChatFormatting.zkm$g24()),
    ITALIC(20, 'o', 0, (char)ChatFormatting.zkm$g24()),
    RESET(21, 'r', 0, (char)ChatFormatting.zkm$g24());

        private static long[] g;
    
    private final int b;
    public static char COLOR_CHAR;
    private final String e;
    private static Map i;
    private static Map f;
    
    
    // R16_UTIL_RECOVERY_MARKER
    // Exact outputs recovered from the sibling zkm$pre bootstrap.
    private static long zkm$g22() {
        return 261699583L;
    }

    private static int zkm$g24() {
        return 32594;
    }
    private static long zkm$ga() {
        return 13249631676262L;
    }

    // R17_CORE_UTIL_RECOVERY_MARKER
    private ChatFormatting(int var3, char var4, int var5, char var6) {
        long var8 = (ChatFormatting.zkm$g22() << 16 | (long)var6 << 48 >>> 48) ^ ChatFormatting.zkm$ga();
        this.b = var5;
        this.e = new String(new char[]{'\u00a7', var4});
}
    public int r() {
        return this.b;
}
    public String toString() {
        return this.e;
}
    public static String y(String var0) {
        char[] var6 = var0.toCharArray();
        for (int var7 = 0; var7 < var6.length - 1; ++var7) {
            if (var6[var7] != '&' || "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(var6[var7 + 1]) <= -1) continue;
            var6[var7] = 167;
            var6[var7 + 1] = Character.toLowerCase(var6[var7 + 1]);
}
        return new String(var6);
}
    static {
        ChatFormatting[] var10000 = new ChatFormatting[]{BLACK, DARK_BLUE, DARK_GREEN, DARK_AQUA, DARK_RED, DARK_PURPLE, GOLD, GRAY, DARK_GRAY, BLUE, GREEN, AQUA, RED, LIGHT_PURPLE, YELLOW, WHITE, MAGIC, BOLD, STRIKETHROUGH, UNDERLINE, ITALIC, RESET};
}
}