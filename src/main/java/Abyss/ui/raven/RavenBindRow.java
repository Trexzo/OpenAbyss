/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package Abyss.ui.raven;

import Abyss.module.impl.configuration.Language;
import Abyss.module.impl.configuration.Theme;
import Abyss.ui.raven.AbstractRavenSettingRow;
import Abyss.ui.raven.RavenModuleRow;
import Abyss.util.KeyBindUtil;
import org.lwjgl.opengl.GL11;

public class RavenBindRow
extends AbstractRavenSettingRow {
    public boolean Y;
    private int i;
    private int q;
    private static long c = 38487870290381L;
    private int v;

    @Override
    public int E(long var1) {
        return 16;
}
    @Override
    public void r(char var1, int var2, int var3, int var4, long var5) {
        long var7 = (long)var1 << 48 | var5 << 16 >>> 16;
        int var11 = (int)((var7 ^ 0x500E7D68E887L) >>> 48);
        long var12 = (var7 ^ 0x500E7D68E887L) << 16 >>> 16;
        long var14 = var7 ^ 0x435B5AB20BDAL;
        if (this.L(var2, var3) && this.O.k) {
            if (var4 == 0) {
                this.Y = !this.Y;
            } else if (var4 > 1 && this.Y) {
                this.O.R.z(var14, KeyBindUtil.w((char)var11, var4, var12));
                this.Y = false;
}
}
}
    @Override
    public void i(int var1, int var2, int var3, byte var4) {
        this.v = var1;
}
    private void m(long var1, String var3) {
        var1 = c ^ var1;
        long var4 = var1 ^ 0x2B7EEF8BB676L;
        long var6 = var1 ^ 0x291416C33791L;
        long var8 = var1 ^ 0x6F5DF3364A40L;
        this.C(var8).T(var6, var3, (this.O.O.X() + 4) * 2, (this.O.O.T() + this.v + 3) * 2, Theme.S(Theme.offset.L(), var4));
}
    @Override
    public void W(long var1) {
        this.Y = false;
}
    public RavenBindRow(RavenModuleRow var1, int var2) {
        super(var1);
        this.F(var1, var2);
}
    public boolean L(int var1, int var4) {
        return var1 > this.q && var1 < this.q + this.O.O.t() && var4 > this.i - 1 && var4 < this.i + 12;
}
    @Override
    public void c(char var1, int var2, long var3) {
        long var5 = var3 ^ 0x67D43D5A9666L;
        if (this.Y) {
            if (var2 != 211 && var2 != 1) {
                this.O.R.z(var5, var2);
            } else {
                this.O.R.z(var5, 0);
}
            this.Y = false;
}
}
    @Override
    public void V(long var1, int var3, int var4) {
        this.i = this.O.O.T() + this.v;
        this.q = this.O.O.X();
}
    private void F(RavenModuleRow var1, int var2) {
        this.q = var1.O.X() + var1.O.t();
        this.i = var1.O.T() + var1.L;
        this.v = var2;
}
    @Override
    public void U(long var1) {
        long var3 = (var1 ^ 0x354EBCC9328BL) >>> 16;
        int var5 = (int)((var1 ^ 0x354EBCC9328BL) << 48 >>> 48);
        long var6 = var1 ^ 0x723600128724L;
        GL11.glPushMatrix();
        GL11.glScaled((double)0.5, (double)0.5, (double)0.5);
        this.m(var6, this.Y ? (Language.applyForSettings.c() ? Language.z("clickgui.bind.press", 0L) : "Press a key...") : (Language.applyForSettings.c() ? Language.z("clickgui.bind.current", 0L) : "Current bind: '\u00a7e") + KeyBindUtil.p(var3, (char)var5, this.O.R.h()) + "\u00a7r'");
        GL11.glPopMatrix();
}
}