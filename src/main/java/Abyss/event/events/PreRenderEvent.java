/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.EntityLivingBase
 */
package Abyss.event.events;

import Abyss.event.Event;
import net.minecraft.entity.EntityLivingBase;

public class PreRenderEvent
extends Event {
    public final EntityLivingBase B;
    public PreRenderEvent(EntityLivingBase var3) {
        this.B = var3;
}
}