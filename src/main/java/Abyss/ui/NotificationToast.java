/*
 * Decompiled with CFR 0.152.
 */
package Abyss.ui;

import Abyss.module.impl.configuration.Notifications;
import Abyss.util.MathUtil;

public class NotificationToast {
    private final float M;
    private final String B;
    private final String L;
    private float e = Float.NaN;
    private final float a;
    private final long E;
    private final float n;
    private final float V;
    private final float Q;

    public static float c(NotificationToast var0) {
        return var0.Q;
}
    public static float e(NotificationToast var0) {
        return var0.M;
}
    public static String m(NotificationToast var0) {
        return var0.B;
}
    public static String l(NotificationToast var0) {
        return var0.L;
}
    public float i(long var1, float var3, float var4, float var5) {
        float var6 = var1 - this.E;
        if (var6 <= var3) {
            return Notifications.u(var6 / var3);
}
        float var7 = var3 + var4;
        if (var6 <= var7) {
            return 1.0f;
}
        float var8 = MathUtil.q((var6 - var7) / Math.max(1.0f, var5), 0.0f, 1.0f);
        return 1.0f - Notifications.R(var8);
}
    public static float O(NotificationToast var0) {
        return var0.a;
}
    public static float R(NotificationToast var0) {
        return var0.e;
}
    public float T() {
        float var1 = Notifications.Z();
        float var2 = System.currentTimeMillis() - this.E;
        if (var2 <= var1) {
            return 1.0f;
}
        float var3 = var2 - var1;
        return 1.0f - MathUtil.q(var3 / Math.max(1.0f, this.V), 0.0f, 1.0f);
}
    public NotificationToast(String var1, long var2, float var4, float var5, float var6, float var7, float var8) {
        this(var1, null, var2, var4, var5, var6, var7, var8);
}
    public NotificationToast(String var1, String var2, long var3, float var5, float var6, float var7, float var8, float var9) {
        this.B = var1;
        this.L = var2;
        this.E = var3;
        this.M = var5;
        this.a = var6;
        this.n = var7;
        this.Q = var8;
        this.V = var9;
}
    public static float f(NotificationToast var0) {
        return var0.n;
}
    public boolean c(long var1, float var3, float var4, float var5) {
        return (float)(var1 - this.E) >= var3 + var4 + var5;
}
    public static float K(NotificationToast var0, float var1) {
        var0.e = var1;
        return var0.e;
}
}