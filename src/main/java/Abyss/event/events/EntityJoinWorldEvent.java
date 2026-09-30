/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 */
package Abyss.event.events;

import Abyss.event.Event;
import net.minecraft.entity.Entity;

public class EntityJoinWorldEvent
extends Event {
    public final Entity H;

    public EntityJoinWorldEvent(int var1, Entity var2, byte var3, int var4) {
        this.H = var2;
}
}