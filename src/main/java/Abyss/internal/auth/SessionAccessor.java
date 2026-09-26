/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.util.Session
 */
package Abyss.internal.auth;

import Abyss.util.MinecraftRef;
import java.lang.reflect.Field;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

public class SessionAccessor {
    private static Field t = null;
    private static final Minecraft u = MinecraftRef.c((byte)0, 0L);

    public static Session d() {
        return u.getSession();
}
    private static Field C() {
        if (t == null) {
            try {
                for (Field var3 : Minecraft.class.getDeclaredFields()) {
                    if (var3.getType() != Session.class) continue;
                    t = var3;
                    t.setAccessible(true);
                    break;
}
}
            catch (Exception var4) {
                t = null;
}
}
        return t;
}
    public static void k(Session var0) {
        try {
            SessionAccessor.C().set(u, var0);
}
        catch (Exception exception) {
            // empty catch block
}
    public static String selfTest() {
        Session original = null;
        try {
            original = SessionAccessor.d();
            Session probe = new Session("__openabyss_selftest__", "00000000000000000000000000000000", "selftest-token", "legacy");
            SessionAccessor.k(probe);
            Session got = SessionAccessor.d();
            if (got == null) {
                return "FAIL null";
}
            if (!"__openabyss_selftest__".equals(got.getUsername())) {
                return "FAIL username " + got.getUsername();
}
            if (!"00000000000000000000000000000000".equals(got.getPlayerID())) {
                return "FAIL uuid " + got.getPlayerID();
}
            if (!"selftest-token".equals(got.getToken())) {
                return "FAIL token";
}
            return "PASS";
}
        catch (Throwable throwable) {
            return "FAIL " + throwable.getClass().getName() + ": " + throwable.getMessage();
}
        finally {
            if (original != null) {
                SessionAccessor.k(original);
}
}
}
}