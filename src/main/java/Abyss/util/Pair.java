/*
 * Decompiled with CFR 0.152.
 */
package Abyss.util;

public class Pair<A, B> {
    private B K;
    private A H;

    public static <A, B> Pair<A, B> p(A var0, B var1) {
        return new Pair<A, B>(var0, var1);
}
    public void I(A var1) {
        this.H = var1;
}
    public Pair(A var1, B var2) {
        this.H = var1;
        this.K = var2;
}
    public B p() {
        return this.K;
}
    public void c(B var1) {
        this.K = var1;
}
    public A a() {
        return this.H;
}
}