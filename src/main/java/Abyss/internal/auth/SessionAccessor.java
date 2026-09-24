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
                    if (!var3.getType().isAssignableFrom(Session.class)) continue;
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
}
}