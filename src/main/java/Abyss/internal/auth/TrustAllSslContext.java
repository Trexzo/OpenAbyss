/*
 * Decompiled with CFR 0.152.
 */
package Abyss.internal.auth;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

public class TrustAllSslContext {
    private static SSLContext O;

    

    public static SSLContext j() {
        return O;
}
    private static String a(byte[] var0) {
        int var1 = 0;
        int var2;
        char[] var3 = new char[var2 = var0.length];
        for (int var4 = 0; var4 < var2; ++var4) {
            int var5;
            if ((var5 = 255 & var0[var4]) < 192) {
                var3[var1++] = (char)var5;
            } else if (var5 < 224) {
                char var6 = (char)((char)(var5 & 31) << 6);
                int var8 = var0[++var4];
                var6 = (char)(var6 | (char)(var8 & 63));
                var3[var1++] = var6;
            } else if (var4 < var2 - 2) {
                char var12 = (char)((char)(var5 & 15) << 12);
                int var9 = var0[++var4];
                var12 = (char)(var12 | (char)(var9 & 63) << 6);
                var9 = var0[++var4];
                var12 = (char)(var12 | (char)(var9 & 63));
                var3[var1++] = var12;
            }
        }
        return new String(var3, 0, var1);
    }    private static void zkm$clinit() {
        try {
            byte[] var10003 = new byte[]{0, 0, 0, 0, 0, 0, 0, 0};
            for (int var2 = 1; var2 < 8; ++var2) {
                var10003[var2] = (byte)(69834604646357L << var2 * 8 >>> 56);
            }
            Cipher var1 = Cipher.getInstance("DES/CBC/PKCS5Padding");
            var1.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
            String[] var0 = new String[6];
            int var6 = 0;
            String var5 = "\u0091\u00ef\u00e4\u00c3%\u0007t\u00cfx\u00e9\u00fd\u0019\u00af9\u009e\u00f3\u001b\u001aJ\u00b4\u00f1[\u00bda\u00ec^\u000b\u00f93\u00f8_Q\u0011\u00d7\u00f3\u0018\u0094h\u00cf\u00fff\u00a915z\u00d6\u00fbZ\u00d9\u0005\u00d9\u00dd\u00d9\u00d7\u00cc\u00a7\u0010')\u0007\u009e\u00d5\u00bd\u001c\u00d4\u00ca+\u00d6g\u008f%\u001da\u0018\u00cc\u00fc\u00cd\u00e5\u001a\u0082\u0088\u0081X\u00da\u00e8\u00da\u009f\u0006J\u00b8\u00dc9M{U\u0094\u0091\u00af\b\u009dr\u009b\u00dc\u0088\u00c9\u00961";
            int var7 = "\u0091\u00ef\u00e4\u00c3%\u0007t\u00cfx\u00e9\u00fd\u0019\u00af9\u009e\u00f3\u001b\u001aJ\u00b4\u00f1[\u00bda\u00ec^\u000b\u00f93\u00f8_Q\u0011\u00d7\u00f3\u0018\u0094h\u00cf\u00fff\u00a915z\u00d6\u00fbZ\u00d9\u0005\u00d9\u00dd\u00d9\u00d7\u00cc\u00a7\u0010')\u0007\u009e\u00d5\u00bd\u001c\u00d4\u00ca+\u00d6g\u008f%\u001da\u0018\u00cc\u00fc\u00cd\u00e5\u001a\u0082\u0088\u0081X\u00da\u00e8\u00da\u009f\u0006J\u00b8\u00dc9M{U\u0094\u0091\u00af\b\u009dr\u009b\u00dc\u0088\u00c9\u00961".length();
            int var4 = 56;
            int var17 = -1;
            block8: while (true) {
                String var18 = var5.substring(++var17, var17 + var4);
                int var10001 = -1;
                while (true) {
                    byte[] var8 = var1.doFinal(var18.getBytes("ISO-8859-1"));
                    String var24 = TrustAllSslContext.a(var8).intern();
                    switch (var10001) {
                        case 0: {
                            var0[var6++] = var24;
                            if ((var17 += var4) >= var7) {
                                try {
                                    KeyStore var11 = KeyStore.getInstance(var0[4]);
                                    InputStream var12 = TrustAllSslContext.class.getResourceAsStream(var0[2]);
                                    if (var12 == null) {
                                        throw new RuntimeException(var0[0]);
}
                                    var11.load(var12, var0[1].toCharArray());
                                    TrustManagerFactory var13 = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                                    var13.init(var11);
                                    O = SSLContext.getInstance(var0[3]);
                                    O.init(null, var13.getTrustManagers(), null);
                                    HttpsURLConnection.setDefaultSSLSocketFactory(O.getSocketFactory());
                                    return;
}
                                catch (Exception var14) {
                                    throw new RuntimeException(var0[5], var14);
}
}
                            var4 = var5.charAt(var17);
                            break;
}
                        default: {
                            var0[var6++] = var24;
                            if ((var17 += var4) < var7) {
                                var4 = var5.charAt(var17);
                                continue block8;
}
                            var5 = "(\u008b\u009b\u00d2\u00f1(\u00a2\u00e3(e8\u00d0 [\r\u0089i\u0014\u0000\b5\u00b2\u0012uQ/a\u00be2\u00a0\u00ba\u00a2*\u0002\u0001uX\u00997\u00ecU\u00adZ\u001f\u0093\u00cd\u00dc\u00a0\u0003";
                            var7 = "(\u008b\u009b\u00d2\u00f1(\u00a2\u00e3(e8\u00d0 [\r\u0089i\u0014\u0000\b5\u00b2\u0012uQ/a\u00be2\u00a0\u00ba\u00a2*\u0002\u0001uX\u00997\u00ecU\u00adZ\u001f\u0093\u00cd\u00dc\u00a0\u0003".length();
                            var4 = 8;
                            var17 = -1;
}
}
                    var18 = var5.substring(++var17, var17 + var4);
                    var10001 = 0;
}
}
}
        catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException var15) {
            throw new RuntimeException(var15);
}
}
    static {
        zkm$clinit();
}
}