/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.network.NetworkPlayerInfo
 */
package Abyss.util;

import Abyss.util.MinecraftRef;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;

public final class PlayerInfoCache {
    private static volatile Map<String, NetworkPlayerInfo> byName = Collections.emptyMap();
    private static volatile Map<UUID, NetworkPlayerInfo> byUuid = Collections.emptyMap();
    private static long lastRefresh;

    private PlayerInfoCache() {
}
    public static void refresh() {
        long now = System.currentTimeMillis();
        if (now - lastRefresh < 40L) {
            return;
}
        lastRefresh = now;
        try {
            Minecraft mc = MinecraftRef.c((byte)0, 0L);
            if (mc.func_147114_u() == null || mc.field_71439_g == null) {
                return;
}
            Collection infos = mc.func_147114_u().func_175106_d();
            HashMap<String, NetworkPlayerInfo> names = new HashMap<String, NetworkPlayerInfo>(infos.size() * 2);
            HashMap<UUID, NetworkPlayerInfo> uuids = new HashMap<UUID, NetworkPlayerInfo>(infos.size() * 2);
            for (Object o2 : infos) {
                NetworkPlayerInfo info = (NetworkPlayerInfo)o2;
                uuids.put(info.func_178845_a().getId(), info);
                names.put(info.func_178845_a().getName(), info);
}
            byName = names;
            byUuid = uuids;
}
        catch (Throwable throwable) {
            // empty catch block
}
}
    public static NetworkPlayerInfo byName(String name) {
        return name == null ? null : byName.get(name);
}
    public static NetworkPlayerInfo byUuid(UUID id) {
        return id == null ? null : byUuid.get(id);
}
    public static boolean inTabList(String name) {
        return name != null && byName.containsKey(name);
}
}