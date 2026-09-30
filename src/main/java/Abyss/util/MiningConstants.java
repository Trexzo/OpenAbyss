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
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class MiningConstants {
    public static boolean G;
    public static float s;
    public static float e;
    public static boolean r;
    public static boolean z;
    public static boolean q;
    public static boolean gapAltOnlyStone;
    public static boolean userManualScreenMove;
    public static float X;
    public static int w;
    public static boolean A;
    public static boolean k;
    public static int F;
    public static int h;
    public static float H;
    public static int n;
    public static boolean o;
    public static float g;
    public static float K;
    public static int I;
    public static float Q;
    public static boolean T;
    public static boolean Z;
    public static boolean x;
    public static int D;
    public static float c;
    public static float C;
    public static int L;
    public static int J;
    public static int B;
    public static boolean j;
    public static boolean v;

    private static void zkm$clinit() {
        try {
            Cipher var1;
            byte[] var10003 = new byte[]{(byte)0L, 0, 0, 0, 0, 0, 0, 0};
            for (int var2 = 1; var2 < 8; ++var2) {
                var10003[var2] = (byte)(91462574718829L << var2 * 8 >>> 56);
            }
            (var1 = Cipher.getInstance("DES/CBC/NoPadding")).init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
            long[] var0 = new long[8];
            int var4 = 0;
            String var5 = "\u008f\u00b7\u00fa\u00c5\u0097\u00a4C^\u00c3\u00d3\u0098\u0001\u007f\"\u009ds\u009b`\u00d8~w\u00f2\u00e7cF'-B\u00ec\u0086S\u00e4\u001cI\u00b23\u00c6u\u00d8\u00d1g;\u00ccS\u00b4\u00f5\u008e.";
            int var6 = "\u008f\u00b7\u00fa\u00c5\u0097\u00a4C^\u00c3\u00d3\u0098\u0001\u007f\"\u009ds\u009b`\u00d8~w\u00f2\u00e7cF'-B\u00ec\u0086S\u00e4\u001cI\u00b23\u00c6u\u00d8\u00d1g;\u00ccS\u00b4\u00f5\u008e.".length();
            int var3 = 0;
            block6: while (true) {
                int var10001 = var3;
                byte[] var7 = var5.substring(var10001, var3 += 8).getBytes("ISO-8859-1");
                long[] var15 = var0;
                var10001 = var4++;
                long var18 = ((long)var7[0] & 0xFFL) << 56 | ((long)var7[1] & 0xFFL) << 48 | ((long)var7[2] & 0xFFL) << 40 | ((long)var7[3] & 0xFFL) << 32 | ((long)var7[4] & 0xFFL) << 24 | ((long)var7[5] & 0xFFL) << 16 | ((long)var7[6] & 0xFFL) << 8 | (long)var7[7] & 0xFFL;
                int var20 = -1;
                while (true) {
                    long var8 = var18;
                    byte[] var10 = var1.doFinal(new byte[]{(byte)(var8 >>> 56), (byte)(var8 >>> 48), (byte)(var8 >>> 40), (byte)(var8 >>> 32), (byte)(var8 >>> 24), (byte)(var8 >>> 16), (byte)(var8 >>> 8), (byte)var8});
                    long var22 = ((long)var10[0] & 0xFFL) << 56 | ((long)var10[1] & 0xFFL) << 48 | ((long)var10[2] & 0xFFL) << 40 | ((long)var10[3] & 0xFFL) << 32 | ((long)var10[4] & 0xFFL) << 24 | ((long)var10[5] & 0xFFL) << 16 | ((long)var10[6] & 0xFFL) << 8 | (long)var10[7] & 0xFFL;
                    switch (var20) {
                        case 0: {
                            var15[var10001] = var22;
                            if (var3 < var6) break;
                            D = (int)var0[2];
                            n = (int)var0[0];
                            h = (int)var0[3];
                            B = (int)var0[2];
                            F = (int)var0[1];
                            L = (int)var0[3];
                            I = (int)var0[0];
                            j = (var0[5] & 1L) != 0L;
                            T = (var0[7] & 1L) != 0L;
                            X = 7.0f;
                            s = 7.0f;
                            w = (int)var0[6];
                            Q = 30.0f;
                            e = 150.0f;
                            r = (var0[4] & 1L) != 0L;
                            z = (var0[7] & 1L) != 0L;
                            A = (var0[4] & 1L) != 0L;
                            C = 10.0f;
                            K = 3.0f;
                            g = 100.0f;
                            v = (var0[4] & 1L) != 0L;
                            J = (int)var0[4];
                            H = 50.0f;
                            x = (var0[7] & 1L) != 0L;
                            Z = (var0[7] & 1L) != 0L;
                            o = (var0[4] & 1L) != 0L;
                            G = (var0[4] & 1L) != 0L;
                            c = 2.0f;
                            q = (var0[7] & 1L) != 0L;
                            k = (var0[4] & 1L) != 0L;
                            return;
}
                        default: {
                            var15[var10001] = var22;
                            if (var3 < var6) continue block6;
                            var5 = "U\u00db\u00e1\u00fe\u0002\u00c5\u00ae\u00e2\u0010[\u0087x\u00f6)\u007f\u00c6";
                            var6 = "U\u00db\u00e1\u00fe\u0002\u00c5\u00ae\u00e2\u0010[\u0087x\u00f6)\u007f\u00c6".length();
                            var3 = 0;
}
}
                    int var17 = var3;
                    var7 = var5.substring(var17, var3 += 8).getBytes("ISO-8859-1");
                    var15 = var0;
                    var10001 = var4++;
                    var18 = ((long)var7[0] & 0xFFL) << 56 | ((long)var7[1] & 0xFFL) << 48 | ((long)var7[2] & 0xFFL) << 40 | ((long)var7[3] & 0xFFL) << 32 | ((long)var7[4] & 0xFFL) << 24 | ((long)var7[5] & 0xFFL) << 16 | ((long)var7[6] & 0xFFL) << 8 | (long)var7[7] & 0xFFL;
                    var20 = 0;
}
}
}
        catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException var13) {
            throw new RuntimeException(var13);
}
}
    static {
        zkm$clinit();
}
}