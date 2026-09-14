/*
 * Decompiled with CFR 0.152.
 */
package Abyss.util;

import Abyss.util.OggStreamPlayer;
import Abyss.util.SoundCallback;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.CopyOnWriteArrayList;

public class SoundEngine {
    private static long private SoundCallback e;
    private static CopyOnWriteArrayList<OggStreamPlayer> V = new CopyOnWriteArrayList();

    public static void d(InputStream var0, long var1, char var3, float var4) {
        long var5 = (var1 << 16 | (long)var3 << 48 >>> 48) ^ a;
        long var7 = var5 ^ 0x57FB3B25B715L;
        if (var0 != null) {
            SoundEngine.D(var0, var4, var7);
}
}
    public void o(SoundCallback var1) {
        this.e = var1;
}
    public static void G(int var0) {
        for (OggStreamPlayer var8 : V) {
            var8.L();
}
}
    private static void D(InputStream var0, float var1, long var2) {
        var2 = a ^ var2;
        long var10001 = var2 ^ 0x2723AD7C0D9DL;
        int var4 = (int)((var2 ^ 0x2723AD7C0D9DL) >>> 32);
        int var5 = (int)((var2 ^ 0x2723AD7C0D9DL) << 32 >>> 48);
        int var6 = (int)(var10001 << 48 >>> 48);
        var10001 = var2 ^ 0x362D849D5405L;
        int var7 = (int)((var2 ^ 0x362D849D5405L) >>> 48);
        int var8 = (int)((var2 ^ 0x362D849D5405L) << 16 >>> 48);
        OggStreamPlayer var10 = new OggStreamPlayer(var4, (short)var5, var0, (char)var6, var1, null);
        V.add(var10);
        var10.t((char)var7, (char)var8);
}
    public static void y(long var0, String var2) {
        long var3 = var0 ^ 0x50B9F5A65923L;
        SoundEngine.B(var2, var3, 0.0f);
}
    public static void e(int var0, long var1) {
        long var3 = ((long)var0 << 32 | 0xD721A973L) ^ a;
        long var5 = var3 ^ 0x6D1D296EBAFDL;
        for (OggStreamPlayer var8 : V) {
            var8.F(var5);
}
}
    public static void c(long var0) {
        for (OggStreamPlayer var5 : V) {
            var5.b(129455148608165L);
}
        V.clear();
}
    static CopyOnWriteArrayList l() {
        return V;
}
    public static void B(String var0, long var1, float var3) {
        long var4 = var1 ^ 0x5EFCD8B249BL;
        try {
            if (var0 == null || var0.isEmpty() || var0.charAt(0) != '/') {
                return;
}
            URL var6 = SoundEngine.class.getResource(var0);
            if (var6 == null) {
                return;
}
            InputStream var7 = var6.openStream();
            SoundEngine.D(var7, var3, var4);
}
        catch (IOException iOException) {
            // empty catch block
}
}
    public static void E(long var0, InputStream var2) {
        var0 = a ^ var0;
        long var3 = (var0 ^ 0x6DE77C61E6B2L) >>> 16;
        int var5 = (int)((var0 ^ 0x6DE77C61E6B2L) << 48 >>> 48);
        SoundEngine.d(var2, var3, (char)var5, 0.0f);
}
}