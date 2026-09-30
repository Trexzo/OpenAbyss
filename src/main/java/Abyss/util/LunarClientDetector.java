/*
 * Decompiled with CFR 0.152.
 */
package Abyss.util;

public class LunarClientDetector {
    private static Boolean P;

    public static boolean q(long var0) {
        if (P == null) {
            P = LunarClientDetector.z("com.moonsworth.lunar.genesis.Genesis") || LunarClientDetector.z("lunar.GenesisLauncher");
}
        return P;
}
    private static boolean isAttribute(String var0, ClassLoader var1) {
        if (var1 == null) {
            return false;
}
        try {
            Class.forName(var0, false, var1);
            return true;
}
        catch (Throwable var3) {
            return false;
}
}
    private static boolean z(String var0) {
        ClassLoader var1 = Thread.currentThread().getContextClassLoader();
        if (LunarClientDetector.isAttribute(var0, var1)) {
            return true;
}
        return LunarClientDetector.isAttribute(var0, LunarClientDetector.class.getClassLoader()) ? true : LunarClientDetector.isAttribute(var0, ClassLoader.getSystemClassLoader());
}
    private LunarClientDetector() {
}
}