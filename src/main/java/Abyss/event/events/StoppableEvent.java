/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.events;

import Abyss.event.Event;

public class StoppableEvent
extends Event {
    private static long b = 13431490024297L;
    private boolean Z = false;

    public boolean p() {
        return this.Z;
}
    public void G() {
        this.Z = true;
}
}