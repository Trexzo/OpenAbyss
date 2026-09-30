/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.util.Session
 */
package Abyss.internal.auth;

import Abyss.internal.auth.AltManager;
import Abyss.internal.auth.SessionAccessor;
import Abyss.util.MinecraftRef;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

public class SessionSwapper {
    private static String E(String var2) {
        String var3 = "offlinePlayer:" + var2;
        return UUID.nameUUIDFromBytes(var3.getBytes()).toString().replace("-", "");
}
    public static boolean D(String var0, long var1) {
        if (var0 != null && !var0.trim().isEmpty()) {
            AltManager.e((short)0, 76783016628697L, var0);
            String var10 = SessionSwapper.E(var0);
            Session var11 = new Session(var0, var10, "accessToken", "legacy");
            return SessionAccessor.set(var11);
}
        return false;
}
}