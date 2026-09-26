/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.events;

import Abyss.event.Event;

public class GetArmSwingAnimationEndEvent
extends Event {
    private int N;
    public GetArmSwingAnimationEndEvent(int var1, int var4) {
        this.N = var4;
}
    public int N() {
        return this.N;
}
    public void t(int var1) {
        this.N = var1;
}
}