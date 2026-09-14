/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package Abyss.ui.raven;

import Abyss.module.Module;
import Abyss.module.impl.configuration.Theme;
import Abyss.setting.Setting;
import Abyss.setting.settings.NumberSetting;
import Abyss.setting.settings.PercentageSetting;
import Abyss.ui.raven.AbstractRavenSettingRow;
import Abyss.ui.raven.RavenModuleRow;
import Abyss.util.ClientUtil;
import Abyss.util.render.RenderUtil;
import java.util.Map;
import org.lwjgl.opengl.GL11;

public class RavenSliderRow
extends AbstractRavenSettingRow {
    private static long[] j;
    private PercentageSetting v;
    private int z;
    private static Map i;
    private Module l;
    private int J;
    private double I;
    private int Y;
    private static String[] f;
    private NumberSetting n;
    private boolean X;
    private static long c;

    @Override
    public void f(int var1, int var2, int var3, int var4, short var5, int var6) {
        this.X = false;
}
    public RavenSliderRow(short var1, PercentageSetting var2, RavenModuleRow var3, char var4, int var5, int var6) {
        super(var3);
        long var7 = ((long)var1 << 48 | (long)var4 << 48 >>> 16 | (long)var6 << 32 >>> 32) ^ c;
        this.X = false;
        this.Q(var2, var3, var5);
}
    public boolean U(int var3, int var4) {
        return var3 > this.J && var3 < this.J + this.O.O.t() / 2 + 1 && var4 > this.Y && var4 < this.Y + 16;
}
    @Override
    public void U(long var1) {
        String var16;
        long var3 = var1 ^ 0xAA3FD542704L;
        long var5 = var1 ^ 0x7A49C84F089FL;
        long var7 = var1 ^ 0x3E6AD4F2F4A9L;
        int var9 = (int)((var1 ^ 0x34F9861E47ACL) >>> 56);
        long var10 = (var1 ^ 0x34F9861E47ACL) << 8 >>> 8;
        long var12 = var1 ^ 0x59D79D969530L;
        RenderUtil.j(this.O.O.X() + 4, this.O.O.T() + this.z + 11, this.O.O.X() + 4 + this.O.O.t() - 8, this.O.O.T() + this.z + 15, 3.0f, var12, -12302777);
        int var14 = this.O.O.X() + 4;
        int var15 = this.O.O.X() + 4 + (int)this.I;
        if (var15 - var14 > 84) {
            var15 = var14 + 84;
}
        RenderUtil.j(var14, this.O.O.T() + this.z + 11, var15, this.O.O.T() + this.z + 15, 3.0f, var12, Theme.S(Theme.offset.L(), var5));
        GL11.glPushMatrix();
        GL11.glScaled((double)0.5, (double)0.5, (double)0.5);
        if (this.n != null) {
            float var17 = this.n.L();
            var16 = this.n.e((byte)var9, this.l, var10).replaceAll("-", " ") + ": " + (ClientUtil.I(var17) ? (int)var17 + "" : String.valueOf(var17));
        } else {
            int var18 = this.v.k();
            var16 = this.v.e((byte)var9, this.l, var10).replaceAll("-", " ") + ": " + var18 + "%";
}
        this.C(var7).v(var16, (int)((float)(this.O.O.X() + 4) * 2.0f), (int)((float)(this.O.O.T() + this.z + 3) * 2.0f), this.P, var3, true);
        GL11.glPopMatrix();
}
    private void H(NumberSetting var1, RavenModuleRow var2, int var3) {
        this.v = null;
        this.n = var1;
        this.l = var2.R;
        this.J = var2.O.X() + var2.O.t();
        this.Y = var2.O.T() + var2.L;
        this.z = var3;
}
    @Override
    public void V(long var1, int var3, int var4) {
        long var5 = (var1 ^ 0x6D85046BE10L) >>> 16;
        int var7 = (int)((var1 ^ 0x6D85046BE10L) << 48 >>> 48);
        int var8 = (int)((var1 ^ 0x57D87C0F0355L) >>> 56);
        long var9 = (var1 ^ 0x57D87C0F0355L) << 8 >>> 8;
        int var11 = (int)((var1 ^ 0x7337E58D115DL) >>> 32);
        long var12 = (var1 ^ 0x7337E58D115DL) << 32 >>> 32;
        this.Y = this.O.O.T() + this.z;
        this.J = this.O.O.X();
        float var14 = Math.min(this.O.O.t() - 8, Math.max(0, var3 - this.J));
        this.I = this.n != null ? (double)(this.O.O.t() - 8) * (double)(this.n.L() - this.n.i()) / (double)(this.n.F() - this.n.i()) : (double)(this.O.O.t() - 8) * (double)this.v.k() / 100.0;
        if (this.X) {
            if ((double)var14 == 0.0) {
                if (this.n != null) {
                    this.n.o((byte)var8, var9, this.n.i());
                } else {
                    this.v.b(var11, var12, 0);
}
                this.O.O.M(var5, (char)var7);
            } else {
                if (this.n != null) {
                    float var15 = var14 / ((float)this.O.O.t() - 8.0f) * (this.n.F() - this.n.i()) + this.n.i();
                    this.n.o((byte)var8, var9, var15);
                } else {
                    float var16 = var14 / ((float)this.O.O.t() - 8.0f) * 100.0f;
                    this.v.b(var11, var12, (int)var16);
}
                this.O.O.M(var5, (char)var7);
}
}
}
    @Override
    public Setting f() {
        return this.n != null ? this.n : this.v;
}
    public boolean N(int var1, int var4) {
        return var1 > this.J + this.O.O.t() / 2 && var1 < this.J + this.O.O.t() && var4 > this.Y && var4 < this.Y + 16;
}
    @Override
    public void W(long var1) {
        this.X = false;
}
    public RavenSliderRow(NumberSetting var1, long var2, RavenModuleRow var4, int var5) {
        super(var4);
        var2 = c ^ var2;
        this.X = false;
        this.H(var1, var4, var5);
}
    @Override
    public void r(char var1, int var2, int var3, int var4, long var5) {
        if (this.f() != null) {
            if (this.U(var2, var3) && var4 == 0 && this.O.k) {
                this.X = true;
}
            if (this.N(var2, var3) && var4 == 0 && this.O.k) {
                this.X = true;
}
}
}
    private void Q(PercentageSetting var1, RavenModuleRow var2, int var3) {
        this.n = null;
        this.v = var1;
        this.l = var2.R;
        this.J = var2.O.X() + var2.O.t();
        this.Y = var2.O.T() + var2.L;
        this.z = var3;
}
    @Override
    public void i(int var1, int var2, int var3, byte var4) {
        this.z = var1;
}
    static {
        c = 61800043307808L;
}
}