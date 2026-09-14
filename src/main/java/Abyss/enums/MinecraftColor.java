/*
 * Decompiled with CFR 0.152.
 */
package Abyss.enums;

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

public enum MinecraftColor {
    BLACK('0', -16777216),
    DARK_BLUE('1', -16777046),
    DARK_GREEN('2', -16733696),
    DARK_AQUA('3', -16733526),
    DARK_RED('4', -5636096),
    DARK_PURPLE('5', -5635926),
    GOLD('6', -22016),
    GRAY('7', -5592406),
    DARK_GRAY('8', -11184811),
    BLUE('9', -11184641),
    GREEN('a', -11141291),
    AQUA('b', -11141121),
    RED('c', -43691),
    LIGHT_PURPLE('d', -43521),
    YELLOW('e', -171),
    WHITE('f', -1),
    MAGIC('k', 0),
    BOLD('l', 0),
    STRIKETHROUGH('m', 0),
    UNDERLINE('n', 0),
    ITALIC('o', 0),
    RESET('r', 0);

    public static char COLOR_CHAR;
    private static long[] e;
    private static Map g;
    
    private final char G;
    private final int I;
    
    
        public String S() {
        return new String(new char[]{'\u00a7', this.G});
}
    public static String C(String var0) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        char[] var3 = var0.toCharArray();
        for (int var4 = 0; var4 < var3.length - 1; ++var4) {
            if (var3[var4] != '&' || "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(var3[var4 + 1]) <= -1) continue;
            var3[var4] = 167;
            var3[var4 + 1] = Character.toLowerCase(var3[var4 + 1]);
}
        return new String(var3);
}
    public String toString() {
        return this.S();
}
    private MinecraftColor(char var3, int var4) {
        this.G = var3;
        this.I = var4;
}
    public int U() {
        return this.I;
}
    static {
        MinecraftColor[] var10000 = new MinecraftColor[]{BLACK, DARK_BLUE, DARK_GREEN, DARK_AQUA, DARK_RED, DARK_PURPLE, GOLD, GRAY, DARK_GRAY, BLUE, GREEN, AQUA, RED, LIGHT_PURPLE, YELLOW, WHITE, MAGIC, BOLD, STRIKETHROUGH, UNDERLINE, ITALIC, RESET};
}
}