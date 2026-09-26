/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 */
package Abyss.event.events;

import Abyss.event.Event;
import net.minecraft.network.Packet;

public class ReceivePacketEvent
extends Event {
    public final Packet<?> d;
    public ReceivePacketEvent(Packet var1) {
        this.d = var1;
}
}