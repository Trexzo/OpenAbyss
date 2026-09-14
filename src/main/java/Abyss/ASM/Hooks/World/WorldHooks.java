/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 */
package Abyss.ASM.Hooks.World;

import Abyss.AbyssClient;
import Abyss.event.events.EntityJoinWorldEvent;
import net.minecraft.entity.Entity;

public class WorldHooks {
    private static final long public static void onEntityJoinWorld(Entity var0) {
        EntityJoinWorldEvent var8 = new EntityJoinWorldEvent(23653, var0, -86, 4304924);
        AbyssClient.w.e(var8, 18670087776179L);
}
}