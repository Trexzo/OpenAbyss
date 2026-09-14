/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.multiplayer.ServerData
 */
package Abyss.event.events;

import Abyss.event.Event;
import net.minecraft.client.multiplayer.ServerData;

public class ServerJoinEvent
extends Event {
    public final ServerData u;
    private static final long public ServerJoinEvent(ServerData var1, int var2, char var3, short var4) {
        this.u = var1;
}
}