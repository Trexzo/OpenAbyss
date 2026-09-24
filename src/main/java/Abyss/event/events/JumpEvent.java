/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.events;

import Abyss.event.Event;

public class JumpEvent
extends Event {
    private float r;
    private float V;

    public void y(float var1) {
        this.V = var1;
}
    public JumpEvent(float var1, float var4) {
        this.V = var1;
        this.r = var4;
}
    public float j() {
        return this.r;
}
    public float o() {
        return this.V;
}
    public void x(float var1) {
        this.r = var1;
}
}