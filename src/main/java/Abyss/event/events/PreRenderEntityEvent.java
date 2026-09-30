/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 */
package Abyss.event.events;

import Abyss.event.events.StoppableEvent;
import net.minecraft.entity.Entity;

public class PreRenderEntityEvent
extends StoppableEvent {
    public final Entity O;
    public PreRenderEntityEvent(int var1, byte var2, int var3, Entity var4) {
        this.O = var4;
}
}