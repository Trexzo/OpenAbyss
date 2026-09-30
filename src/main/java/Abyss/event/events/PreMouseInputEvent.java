/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.events;

import Abyss.event.Event;

public class PreMouseInputEvent
extends Event {
    private boolean G = false;
    private boolean l = false;
    private boolean E = false;
    private boolean Q = false;

    public void q(int var1, int var2) {
        this.E = true;
        this.l = true;
        this.G = true;
        this.Q = true;
}
    public boolean w() {
        return this.E;
}
    public void T(boolean var1) {
        this.l = var1;
}
    public void M(boolean var1) {
        this.Q = var1;
}
    public void G(boolean var1) {
        this.G = var1;
}
    public boolean y() {
        return this.Q;
}
    public boolean Y() {
        return this.l;
}
    public boolean C() {
        return this.G;
}
    public boolean[] M() {
        return new boolean[]{this.E, this.l, this.G, this.Q};
}
    public void Q(boolean var1) {
        this.E = var1;
}
}