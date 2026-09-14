/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 */
package Abyss.event.events;

import Abyss.event.Event;
import net.minecraft.entity.Entity;

public class AttackEntityEvent
extends Event {
    private static final long private final Entity Y;

    public Entity O() {
        return this.Y;
}
    public AttackEntityEvent(Entity var1, char var2, short var3, int var4) {
        this.Y = var1;
}
}