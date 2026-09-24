/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.AbstractClientPlayer
 */
package Abyss.event.events;

import Abyss.event.events.StoppableEvent;
import net.minecraft.client.entity.AbstractClientPlayer;

public class PreRenderCapeEvent
extends StoppableEvent {
    public final AbstractClientPlayer F;
    public PreRenderCapeEvent(short var1, int var2, short var3, AbstractClientPlayer var4) {
        this.F = var4;
}
}