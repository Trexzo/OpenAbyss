/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 */
package Abyss.event.events;

import Abyss.event.events.StoppableEvent;
import net.minecraft.entity.Entity;

public class PreRenderModelBipedEvent
extends StoppableEvent {
    private static final long public final Entity O;

    public PreRenderModelBipedEvent(Entity var3) {
        this.O = var3;
}
}