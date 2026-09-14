/*
 * Decompiled with CFR 0.152.
 */
package Abyss.ui.raven;

public class RavenAnimation {
    public float w;
    public float v;
    public long K;

    public float m(float var1, float var2, int var3) {
        if (this.v == var2) {
            return this.v;
}
        float var4 = (float)(System.currentTimeMillis() - this.K) / this.w;
        switch (var3) {
            case 1: {
                var4 = var4 < 0.5f ? 4.0f * var4 * var4 * var4 : (var4 - 1.0f) * (2.0f * var4 - 2.0f) * (2.0f * var4 - 2.0f) + 1.0f;
                break;
}
            case 2: {
                var4 = (float)(1.0 - Math.pow(1.0f - var4, 5.0));
                break;
}
            case 3: {
                var4 = this.z(var4);
                break;
}
            case 4: {
                var4 = this.J(var4);
}
}
        float var5 = var1 + var4 * (var2 - var1);
        if (var2 > var1 && var5 > var2 || var2 < var1 && var5 < var2) {
            var5 = var2;
}
        if (var5 == var2) {
            this.v = var5;
}
        return var5;
}
    float J(float var1) {
        return var1 < 0.5f ? 2.0f * var1 * var1 : -1.0f + (4.0f - 2.0f * var1) * var1;
}
    public void y() {
        this.v = 0.0f;
        this.K = System.currentTimeMillis();
}
    private float z(float var1) {
        float var2;
        double var3 = 7.5625;
        double var5 = 2.75;
        if ((double)var1 < 1.0 / var5) {
            var2 = (float)(var3 * (double)var1 * (double)var1);
        } else if ((double)var1 < 2.0 / var5) {
            float var7 = (float)((double)var1 - 1.5 / var5);
            var2 = (float)(var3 * (double)var7 * (double)var7 + 0.75);
        } else if ((double)var1 < 2.5 / var5) {
            float var8 = (float)((double)var1 - 2.25 / var5);
            var2 = (float)(var3 * (double)var8 * (double)var8 + 0.9375);
        } else {
            float var9 = (float)((double)var1 - 2.625 / var5);
            var2 = (float)(var3 * (double)var9 * (double)var9 + 0.984375);
}
        return var2;
}
    public int F(int var1, int var2, int var3) {
        return Math.round(this.m(var1, var2, var3));
}
    public RavenAnimation(float var1) {
        this.w = var1;
}
}