/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  org.lwjgl.opengl.GL11
 */
package Abyss.ui.raven;

import Abyss.module.Module;
import Abyss.setting.Setting;
import Abyss.setting.settings.ModeSetting;
import Abyss.ui.raven.AbstractRavenSettingRow;
import Abyss.ui.raven.RavenModuleRow;
import Abyss.util.KeyBindUtil;
import Abyss.util.MinecraftRef;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class RavenModeRow
extends AbstractRavenSettingRow {
    private static String d;
    private static Minecraft R;
    private ModeSetting q;
    private static long c;
    private Module z;

    private void q(int var1, boolean var2, long var3) {
        boolean var7;
        long var5 = var3 ^ 0x45FD74E09A9BL;
        switch (var1) {
            case 0: {
                var7 = true;
                break;
}
            case 1: {
                var7 = false;
                break;
}
            default: {
                return;
}
}
        if (var2) {
            boolean bl = var7 = !var7;
}
        if (var7) {
            this.q.w(var5);
        } else {
            this.q.X();
}
}
    private void O(ModeSetting var1, RavenModuleRow var2, int var3) {
        this.q = var1;
        this.z = var2.R;
        this.h = var2.O.X() + var2.O.t();
        this.g = var2.O.T() + var2.L;
        this.y = var3;
}
    @Override
    public void V(long var1, int var3, int var4) {
        this.g = this.O.O.T() + this.y;
        this.h = this.O.O.X();
}
    @Override
    public void U(long var1) {
        long var3 = var1 ^ 0xAA3FD542704L;
        int var5 = (int)((var1 ^ 0x3D99FFE9821CL) >>> 48);
        int var6 = (int)((var1 ^ 0x3D99FFE9821CL) << 16 >>> 48);
        int var7 = (int)((var1 ^ 0x3D99FFE9821CL) << 32 >>> 32);
        long var8 = var1 ^ 0x3E6AD4F2F4A9L;
        int var10 = (int)((var1 ^ 0x34F9861E47ACL) >>> 56);
        long var11 = (var1 ^ 0x34F9861E47ACL) << 8 >>> 8;
        GL11.glPushMatrix();
        GL11.glScaled((double)0.5, (double)0.5, (double)0.5);
        String var13 = this.q.a((char)var5, (short)var6, this.z, var7);
        this.C(var8).v(this.q.e((byte)var10, this.z, var11).replaceAll("-", " ") + d + var13, (int)((float)(this.O.O.X() + 4) * 2.0f), (int)((float)(this.O.O.T() + this.y + 3) * 2.0f), this.P, var3, true);
        GL11.glPopMatrix();
}
    public RavenModeRow(ModeSetting var1, RavenModuleRow var2, int var3) {
        super(var2);
        this.O(var1, var2, var3);
}
    @Override
    public void r(char var1, int var2, int var3, int var4, long var5) {
        long var7 = (long)var1 << 48 | var5 << 16 >>> 16;
        long var9 = (var7 ^ 0x560E193AF36BL) >>> 16;
        int var11 = (int)((var7 ^ 0x560E193AF36BL) << 48 >>> 48);
        long var12 = var7 ^ 0x5D56D944D24EL;
        long var14 = var7 ^ 0x7A8CABEF82AFL;
        int var16 = (int)((var7 ^ 0x61B8866443BFL) >>> 56);
        long var19 = var7 ^ 0x1292EA83DAD1L;
        if (this.D(var14, var2, var3) && this.O.k) {
            this.q(var4, KeyBindUtil.V(MinecraftRef.c((byte)((byte)var16), (long)0L).gameSettings.keyBindSneak.getKeyCode(), var19), var12);
            this.O.O.M(var9, (char)var11);
}
}
    @Override
    public void i(int var1, int var2, int var3, byte var4) {
        this.y = var1;
}
    @Override
    public Setting f() {
        return this.q;
}
    static {
        c = 69705471025448L;
        R = MinecraftRef.c((byte)0, 0L);
        d = ": ";
}
}