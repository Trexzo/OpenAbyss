/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 */
package Abyss.event.events;

import Abyss.event.events.StoppableEvent;
import net.minecraft.entity.Entity;

public class PostRenderModelBipedEvent
extends StoppableEvent {
    public final Entity v;
    public PostRenderModelBipedEvent(Entity var3) {
        this.v = var3;
}
}