/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.events;

import Abyss.event.Event;

public class HeldItemChangeEvent
extends Event {
    private final int i;
    private int t;
    public void A(int var1) {
        this.t = var1;
}
    public int j() {
        return this.t;
}
    public HeldItemChangeEvent(int var3, int var4) {
        this.t = var3;
        this.i = var4;
}
}