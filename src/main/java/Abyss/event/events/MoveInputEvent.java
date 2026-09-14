/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.events;

import Abyss.event.Event;

public class MoveInputEvent
extends Event {
    private double r;
    private float E;
    private static final long private boolean n;
    private float e;
    private boolean o;

    @Override
    public void I(int var1, long var2) {
        this.i(0.0f);
        this.A(0.0f);
        this.O(false);
        this.x(false);
}
    public void i(float var1) {
        this.e = var1;
}
    public void m(double var1) {
        this.r = var1;
}
    public boolean d() {
        return this.o;
}
    public MoveInputEvent(float var3, float var4, boolean var5, boolean var6, double var7) {
        this.e = var3;
        this.E = var4;
        this.o = var5;
        this.n = var6;
        this.r = var7;
}
    public void A(float var1) {
        this.E = var1;
}
    public float t() {
        return this.e;
}
    public boolean b() {
        return this.n;
}
    public void x(boolean var1) {
        this.n = var1;
}
    public double r() {
        return this.r;
}
    public void O(boolean var1) {
        this.o = var1;
}
    public float R() {
        return this.E;
}
}