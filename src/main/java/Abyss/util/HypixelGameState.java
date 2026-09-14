/*
 * Decompiled with CFR 0.152.
 */
package Abyss.util;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.HypixelGameStateBinder;
import Abyss.event.events.PostTickEvent;
import Abyss.event.events.WorldLoadEvent;
import Abyss.util.HypixelScoreboardParser;
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

public class HypixelGameState
implements EventSubscriber {
    private static boolean E;
    private static boolean e;
    private static HypixelScoreboardParser h;
    private static boolean A;
    private static boolean j;
        private static boolean r;
    private static boolean G;
    private static boolean N;
    private static boolean H;

    private static void u(HypixelScoreboardParser var0) {
        A = var0.V();
        H = var0.F();
        G = var0.k();
        r = var0.P();
        E = var0.f();
        e = var0.l();
        j = var0.W();
        N = var0.i();
}
    public static boolean r() {
        return N;
}
    public static boolean P() {
        return G;
}
    public static boolean F() {
        return r;
}
    public static boolean A() {
        return H;
}
    public void onPostTick(long var1, PostTickEvent var3) {
        HypixelGameState.k(113061010428450L);
}
    public static HypixelScoreboardParser L() {
        return h;
}
    private static void a() {
}
    @Override
    public final void x(long var1, EventBus var3) {
        HypixelGameStateBinder.F(var3, this);
}
    public static boolean p() {
        return A;
}
    public static boolean C() {
        return e;
}
    public static boolean d() {
        return j;
}
    public void onWorldLoad(WorldLoadEvent var1, long var2) {
        HypixelScoreboardParser.M((short)0, 730080858);
        h = new HypixelScoreboardParser(5239149500758L);
        HypixelGameState.u(h);
}
    public static boolean G() {
        return E;
}
    public static void k(long var0) {
        h = new HypixelScoreboardParser(5239149500758L);
        HypixelGameState.u(h);
}
    static {
        try {
            long var11 = a ^ 0x133AC981DFF4L;
            long var13 = var11 ^ 0x5B4A37E84F19L;
            HypixelGameState.a();
            byte[] var10003 = new byte[]{(byte)(var11 >>> 56), 0, 0, 0, 0, 0, 0, 0};
            for (int var2 = 1; var2 < 8; ++var2) {
                var10003[var2] = (byte)(var11 << var2 * 8 >>> 56);
}
            Cipher var1 = Cipher.getInstance("DES/CBC/NoPadding");
            var1.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
            long[] var0 = new long[2];
            int var4 = 0;
            String var5 = "\u0007\b\u00e6\u00a6[\u0013\u00e2\u00afz!7\u007fW\u00cf\u00aa?";
            int var6 = "\u0007\b\u00e6\u00a6[\u0013\u00e2\u00afz!7\u007fW\u00cf\u00aa?".length();
            int var3 = 0;
            do {
                int var10001 = var3;
                byte[] var7 = var5.substring(var10001, var3 += 8).getBytes("ISO-8859-1");
                long var8 = ((long)var7[0] & 0xFFL) << 56 | ((long)var7[1] & 0xFFL) << 48 | ((long)var7[2] & 0xFFL) << 40 | ((long)var7[3] & 0xFFL) << 32 | ((long)var7[4] & 0xFFL) << 24 | ((long)var7[5] & 0xFFL) << 16 | ((long)var7[6] & 0xFFL) << 8 | (long)var7[7] & 0xFFL;
                byte[] var10 = var1.doFinal(new byte[]{(byte)(var8 >>> 56), (byte)(var8 >>> 48), (byte)(var8 >>> 40), (byte)(var8 >>> 32), (byte)(var8 >>> 24), (byte)(var8 >>> 16), (byte)(var8 >>> 8), (byte)var8});
                long var10004 = ((long)var10[0] & 0xFFL) << 56 | ((long)var10[1] & 0xFFL) << 48 | ((long)var10[2] & 0xFFL) << 40 | ((long)var10[3] & 0xFFL) << 32 | ((long)var10[4] & 0xFFL) << 24 | ((long)var10[5] & 0xFFL) << 16 | ((long)var10[6] & 0xFFL) << 8 | (long)var10[7] & 0xFFL;
                int var17 = -1;
                var0[var4++] = var10004;
            } while (var3 < var6);
            A = (var0[0] & 1L) != 0L;
            G = (var0[1] & 1L) != 0L;
            r = (var0[1] & 1L) != 0L;
            H = (var0[1] & 1L) != 0L;
            E = (var0[1] & 1L) != 0L;
            e = (var0[1] & 1L) != 0L;
            j = (var0[1] & 1L) != 0L;
            N = (var0[1] & 1L) != 0L;
            h = new HypixelScoreboardParser(var13);
}
        catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException var15) {
            throw new RuntimeException(var15);
}
}
}