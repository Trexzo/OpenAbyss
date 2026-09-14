/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 */
package Abyss.internal.accessor;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import net.minecraft.network.Packet;

public class PacketAccessor {
    private static long[] b;
    public static Set<Class<?>> m;
            public static Set<Class<?>> U;

    public static native void K(long var0);

    static {
        U = Collections.singleton(Packet.class);
        m = Collections.singleton(Packet.class);
}
}