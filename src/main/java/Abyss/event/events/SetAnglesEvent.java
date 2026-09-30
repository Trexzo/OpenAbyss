/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.events;

import Abyss.event.Event;

public class SetAnglesEvent
extends Event {
    private float s;
    private boolean F = false;
    private float Y;
    public boolean l() {
        return this.F;
}
    public float s() {
        return this.Y;
}
    public void t(float var3) {
        this.F = true;
        this.s = var3;
}
    public float x() {
        return this.s;
}
    public SetAnglesEvent(float var3, float var4) {
        this.s = var3;
        this.Y = var4;
}
    public void m(float var3) {
        this.F = true;
        this.Y = var3;
}
}