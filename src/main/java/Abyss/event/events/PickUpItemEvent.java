/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.player.EntityPlayer
 */
package Abyss.event.events;

import Abyss.event.Event;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;

public class PickUpItemEvent
extends Event {
    private static final long public final EntityPlayer F;
    public final EntityItem P;

    public PickUpItemEvent(EntityItem var1, EntityPlayer var2) {
        this.P = var1;
        this.F = var2;
}
}