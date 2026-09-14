/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.events;

import Abyss.event.Event;

public class RedirectIsUsingItemEvent
extends Event {
    float l;
    private boolean r = false;
    private static long public RedirectIsUsingItemEvent(float var1) {
        this.l = var1;
}
    public void W(float var1) {
        this.l = var1;
}
    public boolean v() {
        return this.r;
}
    @Override
    public void I(int var1, long var2) {
        this.r = true;
}
    public float q() {
        return this.l;
}
}