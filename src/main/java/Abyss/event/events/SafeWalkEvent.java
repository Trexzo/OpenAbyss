/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.events;

import Abyss.event.Event;

public class SafeWalkEvent
extends Event {
    private static long b;
    private boolean S = (b & 1L) != 0L;

    public SafeWalkEvent(long var1) {
}
    public void z(boolean var1) {
        this.S = var1;
}
    public boolean O() {
        return this.S;
}
}