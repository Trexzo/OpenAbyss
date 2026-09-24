/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.util.DamageSource
 */
package Abyss.event.events;

import Abyss.event.Event;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public class LivingDeathEvent
extends Event {
    public final EntityLivingBase p;
    public final DamageSource M;
    public LivingDeathEvent(int var1, DamageSource var2, char var3, short var4, EntityLivingBase var5) {
        this.M = var2;
        this.p = var5;
}
}