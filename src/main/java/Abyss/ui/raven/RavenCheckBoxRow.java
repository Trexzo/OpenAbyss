/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package Abyss.ui.raven;

import Abyss.module.Module;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.ui.raven.AbstractRavenSettingRow;
import Abyss.ui.raven.RavenModuleRow;
import org.lwjgl.opengl.GL11;

public class RavenCheckBoxRow
extends AbstractRavenSettingRow {
    private Module d;
    private static long c = 131974201764505L;
    private static String[] i;
    private BooleanSetting u;
    private static String[] f;

    public static void h(long var0) {
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDisable((int)2848);
        GL11.glHint((int)3154, (int)4352);
        GL11.glHint((int)3155, (int)4352);
}
    @Override
    public void U(long var1) {
        long var3 = var1 ^ 0xAA3FD542704L;
        long var5 = var1 ^ 0x3E6AD4F2F4A9L;
        int var7 = (int)((var1 ^ 0x34F9861E47ACL) >>> 56);
        long var8 = (var1 ^ 0x34F9861E47ACL) << 8 >>> 8;
        GL11.glPushMatrix();
        GL11.glScaled((double)0.5, (double)0.5, (double)0.5);
        this.C(var5).v((this.u.c() ? "[+]  " : "[-]  ") + this.u.e((byte)var7, this.d, var8).replace("-", " "), (this.O.O.X() + 4) * 2, (this.O.O.T() + this.y + 4) * 2, this.u.c() ? this.N : this.P, var3, false);
        GL11.glPopMatrix();
}
    public static void a(float var0, float var1, float var2, float var3, long var4, int var6) {
        var4 = c ^ var4;
        int var7 = (int)((var4 ^ 0x1B0477B3315FL) >>> 56);
        int var10 = (int)((var4 ^ 0x1D0FC07BC3ADL) >>> 32);
        int var11 = (int)((var4 ^ 0x1D0FC07BC3ADL) << 32 >>> 48);
        int var12 = (int)((var4 ^ 0x1D0FC07BC3ADL) << 48 >>> 48);
        RavenCheckBoxRow.t();
        RavenCheckBoxRow.E(var6, var10, (short)var11, (char)var12);
        RavenCheckBoxRow.h(var0, (byte)var7, var1, var2, var3);
        RavenCheckBoxRow.h(0L);
}
    public static void h(float var0, byte var1, float var2, float var3, float var6) {
        GL11.glBegin((int)7);
        GL11.glVertex2f((float)var0, (float)var6);
        GL11.glVertex2f((float)var3, (float)var6);
        GL11.glVertex2f((float)var3, (float)var2);
        GL11.glVertex2f((float)var0, (float)var2);
        GL11.glEnd();
}
    public static void t() {
        GL11.glDisable((int)2929);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDepthMask((boolean)true);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glHint((int)3155, (int)4354);
}
    public static void E(int var0, int var1, short var2, char var3) {
        float var6 = (float)(var0 >> 24 & 0xFF) / 350.0f;
        GL11.glColor4f((float)0.0f, (float)0.0f, (float)0.0f, (float)var6);
}
    public boolean G(int var1, int var4) {
        return var1 > this.h && var1 < this.h + this.O.O.t() && var4 > this.g && var4 < this.g + 11;
}
    @Override
    public void i(int var1, int var2, int var3, byte var4) {
        this.y = var1;
}
    private void V(Module var1, BooleanSetting var2, RavenModuleRow var3, int var4) {
        this.d = var1;
        this.u = var2;
        this.h = var3.O.X() + var3.O.t();
        this.g = var3.O.T() + var3.L;
        this.y = var4;
}
    public RavenCheckBoxRow(Module var1, BooleanSetting var2, RavenModuleRow var3, int var4) {
        super(var3);
        this.V(var1, var2, var3, var4);
}
    @Override
    public void r(char var1, int var2, int var3, int var4, long var5) throws Throwable {
        long var7 = (long)var1 << 48 | var5 << 16 >>> 16;
        long var9 = (var7 ^ 0x560E193AF36BL) >>> 16;
        int var11 = (int)((var7 ^ 0x560E193AF36BL) << 48 >>> 48);
        long var14 = var7 ^ 0x4EFA73013C49L;
        if (this.G(var2, var3) && var4 == 0 && this.O.k) {
            this.u.W(var14);
            this.O.O.M(var9, (char)var11);
}
}
    @Override
    public Setting f() {
        return this.u;
}
    @Override
    public void V(long var1, int var3, int var4) {
        this.g = this.O.O.T() + this.y;
        this.h = this.O.O.X();
}
}