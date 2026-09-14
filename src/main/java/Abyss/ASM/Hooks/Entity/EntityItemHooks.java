/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.player.EntityPlayer
 */
package Abyss.ASM.Hooks.Entity;

import Abyss.AbyssClient;
import Abyss.event.events.PickUpItemEvent;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;

public class EntityItemHooks {
    private static final long public static void onPickUpItem(EntityItem var0, EntityPlayer var1) {
        PickUpItemEvent var8 = new PickUpItemEvent(var0, var1);
        AbyssClient.w.e(var8, 18670087776179L);
}
}