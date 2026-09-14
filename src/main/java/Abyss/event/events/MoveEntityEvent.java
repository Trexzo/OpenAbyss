/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 */
package Abyss.event.events;

import Abyss.event.Event;
import net.minecraft.entity.Entity;

public class MoveEntityEvent
extends Event {
    public final Entity D;
    private static final long public MoveEntityEvent(short var1, int var2, short var3, Entity var4) {
        this.D = var4;
}
}