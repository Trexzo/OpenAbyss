/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.EntityLivingBase
 */
package Abyss.event.events;

import Abyss.event.Event;
import net.minecraft.entity.EntityLivingBase;

public class PostRenderEvent
extends Event {
    public final EntityLivingBase z;
    public PostRenderEvent(EntityLivingBase var3) {
        this.z = var3;
}
}