/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 */
package Abyss.event.events;

import Abyss.event.Event;
import net.minecraft.entity.Entity;

public class AttackTargetEntityEvent
extends Event {
    public final Entity w;
    public AttackTargetEntityEvent(Entity var1) {
        this.w = var1;
}
}