/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 */
package Abyss.event.events;

import Abyss.event.Event;
import net.minecraft.network.Packet;

public class SendPacketEvent
extends Event {
    public final Packet<?> B;

    public SendPacketEvent(Packet var1) {
        this.B = var1;
}
}