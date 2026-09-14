/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 */
package Abyss.event.events;

import Abyss.event.events.StoppableEvent;
import net.minecraft.entity.Entity;

public class EntityRenderStateEvent
extends StoppableEvent {
    public final Entity k;
    private static final long public EntityRenderStateEvent(short var1, char var2, int var3, Entity var4) {
        this.k = var4;
}
}