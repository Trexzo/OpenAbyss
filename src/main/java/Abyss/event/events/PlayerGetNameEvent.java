/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.network.NetworkPlayerInfo
 */
package Abyss.event.events;

import Abyss.event.Event;
import net.minecraft.client.network.NetworkPlayerInfo;

public class PlayerGetNameEvent
extends Event {
    private String q = "";
    public final String h;
    private static final long public final NetworkPlayerInfo u;
    private String n = "";

    public void N(String var1) {
        this.q = this.q + var1;
}
    public void d(String var1) {
        this.n = var1 + this.n;
}
    public PlayerGetNameEvent(NetworkPlayerInfo var1, String var2) {
        this.u = var1;
        this.h = var2;
}
    public String d() {
        return this.n + this.h + this.q;
}
}