/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.AbstractClientPlayer
 */
package Abyss.event.events;

import Abyss.event.events.StoppableEvent;
import net.minecraft.client.entity.AbstractClientPlayer;

public class PostRenderCapeEvent
extends StoppableEvent {
    private static final long public final AbstractClientPlayer U;

    public PostRenderCapeEvent(AbstractClientPlayer var3) {
        this.U = var3;
}
}