/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.network.NetworkPlayerInfo
 */
package Abyss.internal.accessor;

import Abyss.internal.accessor.Accessor;
import Abyss.internal.accessor.MethodAccessors;
import net.minecraft.client.network.NetworkPlayerInfo;

public final class NetworkPlayerInfoAccessor {
    private static Accessor p = MethodAccessors.G(NetworkPlayerInfo.class, "loadPlayerTextures", "loadPlayerTextures", new Class[0]);

    public static void W(NetworkPlayerInfo var0) {
        Accessor.v(p, new Object[]{var0});
}
}