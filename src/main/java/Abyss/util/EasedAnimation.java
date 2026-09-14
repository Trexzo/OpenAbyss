/*
 * Decompiled with CFR 0.152.
 */
package Abyss.util;

import Abyss.enums.AnimationDirection;
import Abyss.util.TimerUtil;

public abstract class EasedAnimation {
    public TimerUtil t = new TimerUtil();
    protected AnimationDirection E;
    protected double n;
    protected int W;

    public double f() {
        return this.n;
}
    public void g(double var1) {
        this.n = var1;
}
    protected boolean o$r1() {
        return false;
}
    public EasedAnimation(int var1, double var2, AnimationDirection var4) {
        this.W = var1;
        this.n = var2;
        this.E = var4;
}
    public AnimationDirection c() {
        return this.E;
}
    public void o() {
        this.S(this.E.D());
}
    protected abstract double H(double var1);

    public EasedAnimation S(AnimationDirection var1) {
        if (this.E != var1) {
            this.E = var1;
            this.t.p(System.currentTimeMillis() - ((long)this.W - Math.min((long)this.W, this.t.p())));
}
        return this;
}
    public EasedAnimation(int var1, double var2) {
        this(var1, var2, AnimationDirection.FORWARDS);
}
    public double B() {
        return 1.0 - (double)this.t.p() / (double)this.W * this.n;
}
    public boolean f(AnimationDirection var1) {
        return this.W() && this.E.equals((Object)var1);
}
    public double z() {
        if (this.E.D$r1()) {
            return this.W() ? this.n : this.H((double)this.t.p() / (double)this.W) * this.n;
}
        if (this.W()) {
            return 0.0;
}
        if (this.o$r1()) {
            double var1 = Math.min((long)this.W, Math.max(0L, (long)this.W - this.t.p()));
            return this.H(var1 / (double)this.W) * this.n;
}
        return (1.0 - this.H((double)this.t.p() / (double)this.W)) * this.n;
}
    public void H(int var1) {
        this.W = var1;
}
    public void d() {
        this.t.W();
}
    public boolean W() {
        return this.t.Q(this.W);
}
}