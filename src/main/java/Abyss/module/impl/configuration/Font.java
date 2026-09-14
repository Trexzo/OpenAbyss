/*
 * Decompiled with CFR 0.152.
 */
package Abyss.module.impl.configuration;

import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.setting.Setting;
import Abyss.setting.settings.ModeSetting;
import Abyss.util.render.CustomFont;
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

public class Font
extends Module {
    private static CustomFont H;
    
    public static ModeSetting scoreboardFont;
    private static String[] e;
    public static ModeSetting othersFont;
    private static CustomFont c;
        private static CustomFont J;
    public static ModeSetting clickguiFont;
    private static CustomFont S;
    public static ModeSetting notificationsFont;
        private static CustomFont d;
    public static ModeSetting hudFont;
    public static ModeSetting arraylistFont;
    private static CustomFont n;

    public static CustomFont s(long var0) {
        return Font.Q(othersFont.Y());
}
    public static CustomFont J() {
        return Font.Q(scoreboardFont.Y());
}
    public static CustomFont O(short var0, int var1) {
        return Font.Q(notificationsFont.Y());
}
    public Font(long var1) {
        super(a ^ var1 ^ 0x1F272EDCB6C7L);
        this.declare("Font", Category.Configuration, "Manage font rendering", new Setting[0]);
        var1 = a ^ var1;
}
    public static CustomFont Q(long var0) {
        return S;
}
    public static CustomFont F(long var0) {
        return S;
}
    private static CustomFont Q(String var2) {
        return S;
}
    public static CustomFont m(long var0) {
        return S;
}
                if (var5 < 224) {
                char var6 = (char)((char)(var5 & 0x1F) << 6);
                byte var8 = var0[++var4];
                var6 = (char)(var6 | (char)(var8 & 0x3F));
                var3[var1++] = var6;
                continue;
}
            if (var4 >= var2 - 2) continue;
            char var12 = (char)((char)(var5 & 0xF) << 12);
            byte var9 = var0[++var4];
            var12 = (char)(var12 | (char)(var9 & 0x3F) << 6);
            var9 = var0[++var4];
            var12 = (char)(var12 | (char)(var9 & 0x3F));
            var3[var1++] = var12;
}
        return new String(var3, 0, var1);
}
                Cipher var0 = Cipher.getInstance("DES/CBC/PKCS5Padding");
            var0.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
            String[] var7 = new String[11];
            int var5 = 0;
            String var4 = "F2o\u0082b\u00f9\u00ae\u00d3xI\u00f1cO\u0083u\u008f\u008b\u00f3u\u00d2^ta\u00b6UJ\u001a\u00e3\u0015I\u008e\u009a\u0010\u0015\u001c\u00e3r\u00cc\u009c\u00c1\u0095\u00e1\u0097%\u0099\u00b5\u00bc\u0090|\u0010\u00c5\u00c2\u00a3\u00a0\u00b7\u00d6\u0080\u000e@\u0099l\u00abq\u00b7\u00e8\u00a1\u0010,\u00b4\u00a0\u00bbW\u008dI\u00fbf\u00faa:e5M+\u0010#\u00f5d\u0093\u000b?\u00cbh\f\u00e7\u00c6\u00cb\u00ba\u00a6\u00ddn \u00c9\u0010\u0005\u008dL#\u0099\u0016\u00d6\r\u00c2fqh\u008a\u0095\u00a3\u00a3\u008a\u008cz\u0010R\u00c8\u0098%\u00cf\u00f4w\u00f9\u00c1\r \u00c7@\u007f_7\u00fb\u00c1\u00e5\u0013\u001c\u00eb\u008c\"g\u0097\u00cb`6r;\u00d9\u0098}\u00bb\u00d5\u00e2\u0081!kz`\u0017\u00109r\u00f0\u00d4n\u00f2\\\u00f7\u00910\u00d6zGZ\u00b8\u00b4\u0010\u00dd)K\u00bcv\u0085#\u00bb:\u0096\u00a54:Y\u000fi";
            int var6 = "F2o\u0082b\u00f9\u00ae\u00d3xI\u00f1cO\u0083u\u008f\u008b\u00f3u\u00d2^ta\u00b6UJ\u001a\u00e3\u0015I\u008e\u009a\u0010\u0015\u001c\u00e3r\u00cc\u009c\u00c1\u0095\u00e1\u0097%\u0099\u00b5\u00bc\u0090|\u0010\u00c5\u00c2\u00a3\u00a0\u00b7\u00d6\u0080\u000e@\u0099l\u00abq\u00b7\u00e8\u00a1\u0010,\u00b4\u00a0\u00bbW\u008dI\u00fbf\u00faa:e5M+\u0010#\u00f5d\u0093\u000b?\u00cbh\f\u00e7\u00c6\u00cb\u00ba\u00a6\u00ddn \u00c9\u0010\u0005\u008dL#\u0099\u0016\u00d6\r\u00c2fqh\u008a\u0095\u00a3\u00a3\u008a\u008cz\u0010R\u00c8\u0098%\u00cf\u00f4w\u00f9\u00c1\r \u00c7@\u007f_7\u00fb\u00c1\u00e5\u0013\u001c\u00eb\u008c\"g\u0097\u00cb`6r;\u00d9\u0098}\u00bb\u00d5\u00e2\u0081!kz`\u0017\u00109r\u00f0\u00d4n\u00f2\\\u00f7\u00910\u00d6zGZ\u00b8\u00b4\u0010\u00dd)K\u00bcv\u0085#\u00bb:\u0096\u00a54:Y\u000fi".length();
            int var3 = 32;
            int var15 = -1;
            block6: while (true) {
                String var16 = var4.substring(++var15, var15 + var3);
                int var10001 = -1;
                while (true) {
                    byte[] var8 = var0.doFinal(var16.getBytes("ISO-8859-1"));
                    String var22 = Font.b(var8).intern();
                    switch (var10001) {
                        case 0: {
                            var7[var5++] = var22;
                            if ((var15 += var3) >= var6) {
                                b = var7;
                                e = new String[11];
                                S = new CustomFont(var11, "NONE");
                                J = new CustomFont(var11, "productsans");
                                d = new CustomFont(var11, "inter");
                                H = new CustomFont(var11, "pingfang");
                                c = new CustomFont(var11, "roboto");
                                n = new CustomFont(var11, "tahoma");
                                return;
}
                            var3 = var4.charAt(var15);
                            break;
}
                        default: {
                            var7[var5++] = var22;
                            if ((var15 += var3) < var6) {
                                var3 = var4.charAt(var15);
                                continue block6;
}
                            var4 = "\u009b\u0013`\u00dc7\u00fa\u000e\u00cdl\u0017\u00cc\u00f9\u0005`\u00f6\u008e\u001b\u009e*!Y\u00edz^c\u00a2}n\f\u00e2\u0011\u001c\u0010\u0094\u0087\u0004\u00e0\u00b5k\u00db\u00b1\u00af4s\u00f8d\u0010\u009d\u0089";
                            var6 = "\u009b\u0013`\u00dc7\u00fa\u000e\u00cdl\u0017\u00cc\u00f9\u0005`\u00f6\u008e\u001b\u009e*!Y\u00edz^c\u00a2}n\f\u00e2\u0011\u001c\u0010\u0094\u0087\u0004\u00e0\u00b5k\u00db\u00b1\u00af4s\u00f8d\u0010\u009d\u0089".length();
                            var3 = 32;
                            var15 = -1;
}
}
                    var16 = var4.substring(++var15, var15 + var3);
                    var10001 = 0;
}
                break;
}
}
        catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException var13) {
            throw new RuntimeException(var13);
}
}
    static {
        clickguiFont = new ModeSetting("ClickGUI-font", "NONE", "PRODUCT_SANS", "INTER", "PING_FANG", "ROBOTO", "TAHOMA");
        hudFont = new ModeSetting("HUD-font", "NONE", "PRODUCT_SANS", "INTER", "PING_FANG", "ROBOTO", "TAHOMA");
        scoreboardFont = new ModeSetting("Scoreboard-font", "NONE", "PRODUCT_SANS", "INTER", "PING_FANG", "ROBOTO", "TAHOMA");
        notificationsFont = new ModeSetting("Notifications-font", false, "PING_FANG", "NONE", "PRODUCT_SANS", "INTER", "PING_FANG", "ROBOTO", "TAHOMA");
        othersFont = new ModeSetting("Others-font", "NONE", "PRODUCT_SANS", "INTER", "PING_FANG", "ROBOTO", "TAHOMA");
        arraylistFont = new ModeSetting("ArrayList-font", "NONE", "PRODUCT_SANS", "INTER", "PING_FANG", "ROBOTO", "TAHOMA");
}
}