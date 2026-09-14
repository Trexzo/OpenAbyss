/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.Vec3
 */
package Abyss.util;

import Abyss.internal.synthetic.RotationUtilSwitchMapEnumFacing;
import Abyss.util.MathUtil;
import Abyss.util.MinecraftRef;
import Abyss.util.RaytraceUtil;
import Abyss.util.RotationManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class RotationUtil {
    private static final long private static final Minecraft j;

    public static Vec3 T(float var0) {
        if (var0 == 1.0f) {
            return RotationUtil.d(RotationManager.G, RotationManager.r);
}
        float var1 = RotationManager.h + (RotationManager.G - RotationManager.h) * var0;
        float var2 = RotationManager.b + (RotationManager.r - RotationManager.b) * var0;
        return RotationUtil.d(var1, var2);
}
    public static Vec3 h(BlockPos var0, EnumFacing var1, double var2) {
        double var20;
        double var18;
        double var4 = RaytraceUtil.f().field_72450_a;
        double var6 = RaytraceUtil.f().field_72449_c;
        double var8 = (double)var0.func_177958_n() + 0.05;
        double var10 = (double)var0.func_177958_n() + 0.95;
        double var12 = (double)var0.func_177956_o() + 0.05;
        double var14 = (double)var0.func_177952_p() + 0.05;
        double var16 = (double)var0.func_177952_p() + 0.95;
        switch (RotationUtilSwitchMapEnumFacing.m[var1.ordinal()]) {
            case 1: {
                var18 = MathUtil.R(RaytraceUtil.f().field_72450_a, var8, var10);
                var12 = Math.max((double)var0.func_177956_o() + 0.05, Math.min(RaytraceUtil.f().field_72448_b, (double)(var0.func_177956_o() + 1) - 0.05));
                var20 = MathUtil.R(RaytraceUtil.f().field_72449_c, var14, var16);
                break;
}
            case 2: {
                var20 = var14;
                if (var4 <= var10) {
                    if (var8 + var2 > var4) {
                        var18 = MathUtil.R(RaytraceUtil.f().field_72450_a + var2, var8, var10);
                        break;
}
                    var18 = MathUtil.R(RaytraceUtil.f().field_72450_a - var2, var8, var10);
                    break;
}
                var18 = MathUtil.R(RaytraceUtil.f().field_72450_a - var2, var8, var10);
                break;
}
            case 3: {
                var20 = var16;
                if (var4 >= var8) {
                    if (var10 - var2 < var4) {
                        var18 = MathUtil.R(RaytraceUtil.f().field_72450_a - var2, var8, var10);
                        break;
}
                    var18 = MathUtil.R(RaytraceUtil.f().field_72450_a + var2, var8, var10);
                    break;
}
                var18 = MathUtil.R(RaytraceUtil.f().field_72450_a + var2, var8, var10);
                break;
}
            case 4: {
                var18 = var8;
                if (var6 >= var14) {
                    if (var16 - var2 < var6) {
                        var20 = MathUtil.R(RaytraceUtil.f().field_72449_c - var2, var14, var16);
                        break;
}
                    var20 = MathUtil.R(RaytraceUtil.f().field_72449_c + var2, var14, var16);
                    break;
}
                var20 = MathUtil.R(RaytraceUtil.f().field_72449_c + var2, var14, var16);
                break;
}
            case 5: {
                var18 = var10;
                if (var6 <= var16) {
                    if (var14 + var2 > var6) {
                        var20 = MathUtil.R(RaytraceUtil.f().field_72449_c + var2, var14, var16);
                        break;
}
                    var20 = MathUtil.R(RaytraceUtil.f().field_72449_c - var2, var14, var16);
                    break;
}
                var20 = MathUtil.R(RaytraceUtil.f().field_72449_c - var2, var14, var16);
                break;
}
            default: {
                var18 = Math.max((double)var0.func_177958_n() + 0.05, Math.min(RaytraceUtil.f().field_72450_a + var2, (double)(var0.func_177958_n() + 1) - 0.05));
                var12 = Math.max((double)var0.func_177956_o() + 0.05, Math.min(RaytraceUtil.f().field_72448_b, (double)(var0.func_177956_o() + 1) - 0.05));
                var20 = Math.max((double)var0.func_177952_p() + 0.05, Math.min(RaytraceUtil.f().field_72449_c + var2, (double)(var0.func_177952_p() + 1) - 0.05));
}
}
        return new Vec3(var18, var12, var20);
}
    public static float[] u(long var0, BlockPos var2, EnumFacing var3, double var4) {
        var0 = 0x69CE2458EE46L ^ var0;
        long var6 = var0 ^ 0x7A3FB8E2C6BL;
        return RotationUtil.L(RaytraceUtil.t(var2, var6, var3, var4));
}
    public static float[] W(long var0, Entity var2) {
        var0 = 0x69CE2458EE46L ^ var0;
        long var3 = var0 ^ 0x19475BA982A1L;
        return RotationUtil.J(var2, var3, 0.0);
}
    public static boolean J(long var0, Vec3 var2, double var3) {
        return (double)RotationUtil.n(0L, var2) <= var3;
}
    public static float n(long var0, Vec3 var2) {
        Vec3 var3 = RaytraceUtil.f();
        if (RotationUtil.j.field_71439_g.func_174813_aQ().func_72318_a(var2)) {
            return 0.0f;
}
        double var4 = var2.field_72450_a - var3.field_72450_a;
        double var6 = var2.field_72449_c - var3.field_72449_c;
        return Math.abs(MathHelper.func_76142_g((float)((float)(Math.atan2(var6, var4) * 180.0 / Math.PI) - 90.0f - RotationManager.p()))) * 2.0f;
}
    public static boolean B(char var0, int var1, BlockPos var2, short var3, EnumFacing var4, double var5) {
        long var7 = ((long)var0 << 48 | (long)var1 << 32 >>> 16 | (long)var3 << 48 >>> 48) ^ 0x69CE2458EE46L;
        long var9 = var7 ^ 0x39B40EE47BCFL;
        return (double)RotationUtil.L(var2, var9, var4) <= var5;
}
    public static float[] J(Entity var0, long var1, double var3) {
        var1 = 0x69CE2458EE46L ^ var1;
        int var5 = (int)((var1 ^ 0x287B5F643769L) >>> 32);
        int var6 = (int)((var1 ^ 0x287B5F643769L) << 32 >>> 48);
        int var7 = (int)((var1 ^ 0x287B5F643769L) << 48 >>> 48);
        return RotationUtil.L(RaytraceUtil.w(var0, var5, (short)var6, var3, (char)var7));
}
    public static boolean b(long var0, Entity var2, double var3) {
        long var5 = (var0 = 0x69CE2458EE46L ^ var0) ^ 0x2988DBE287F5L;
        return (double)RotationUtil.e(var2, var5) <= var3;
}
    public static float[] W(Vec3 var0, Vec3 var1) {
        double var2 = var1.field_72450_a;
        double var4 = var1.field_72448_b;
        double var6 = var1.field_72449_c;
        double var8 = var0.field_72450_a - var2;
        double var10 = var0.field_72448_b - var4;
        double var12 = var0.field_72449_c - var6;
        double var14 = Math.sqrt(var8 * var8 + var12 * var12);
        float var16 = (float)(Math.toDegrees(Math.atan2(var12, var8)) - 90.0);
        float var17 = (float)(-Math.toDegrees(Math.atan2(var10, var14)));
        if (var17 > 90.0f) {
            var17 = 90.0f;
}
        if (var17 < -90.0f) {
            var17 = -90.0f;
}
        return new float[]{var16, var17};
}
    public static float[] b(long var0, AxisAlignedBB var2, double var3) {
        var0 = 0x69CE2458EE46L ^ var0;
        int var5 = (int)((var0 ^ 0x24FA5A572BE8L) >>> 48);
        int var6 = (int)((var0 ^ 0x24FA5A572BE8L) << 16 >>> 48);
        int var7 = (int)((var0 ^ 0x24FA5A572BE8L) << 32 >>> 32);
        return RotationUtil.L(RaytraceUtil.Q((short)var5, var2, var3, (short)var6, true, var7));
}
    public static float[] U(long var0, AxisAlignedBB var2) {
        var0 = 0x69CE2458EE46L ^ var0;
        long var3 = var0 ^ 0x5C3F4B6060EL;
        return RotationUtil.b(var3, var2, 0.0);
}
    public static Vec3 d(float var0, float var1) {
        float var2 = MathHelper.func_76134_b((float)(-var1 * ((float)Math.PI / 180) - (float)Math.PI));
        float var3 = MathHelper.func_76126_a((float)(-var1 * ((float)Math.PI / 180) - (float)Math.PI));
        float var4 = -MathHelper.func_76134_b((float)(-var0 * ((float)Math.PI / 180)));
        float var5 = MathHelper.func_76126_a((float)(-var0 * ((float)Math.PI / 180)));
        return new Vec3((double)(var3 * var4), (double)var5, (double)(var2 * var4));
}
    public static float[] y(long var0, AxisAlignedBB var2, double var3) {
        var0 = 0x69CE2458EE46L ^ var0;
        long var5 = var0 ^ 0x566264755808L;
        return RotationUtil.L(RaytraceUtil.p(var2, var5, var3));
}
    public static float L(BlockPos var0, long var1, EnumFacing var3) {
        var1 = 0x69CE2458EE46L ^ var1;
        long var4 = var1 ^ 0x75930507977DL;
        return RotationUtil.n(0L, RaytraceUtil.Z(var0, var4, var3, 2.147483647E9, false));
}
    public static float[] S(char var0, int var1, char var2, BlockPos var3, EnumFacing var4) {
        long var5 = ((long)var0 << 48 | (long)var1 << 32 >>> 16 | (long)var2 << 48 >>> 48) ^ 0x69CE2458EE46L;
        long var7 = var5 ^ 0xDEB582A768EL;
        return RotationUtil.L(RaytraceUtil.e(var7, var3, var4));
}
    public static float V(AxisAlignedBB var0, long var1) {
        var1 = 0x69CE2458EE46L ^ var1;
        int var3 = (int)((var1 ^ 0x4F5ACA558FE5L) >>> 48);
        int var4 = (int)((var1 ^ 0x4F5ACA558FE5L) << 16 >>> 48);
        int var5 = (int)((var1 ^ 0x4F5ACA558FE5L) << 32 >>> 32);
        return RotationUtil.n(0L, RaytraceUtil.Q((short)var3, var0, 2.147483647E9, (short)var4, false, var5));
}
    public static double g(Entity var0) {
        float[] var6 = RotationUtil.F(var0, (byte)0, 2595745, 2287551);
        float var7 = MathUtil.M(RotationManager.p(), var6[0]);
        float var8 = MathUtil.M(RotationManager.s(), var6[1]);
        return Math.sqrt(var7 * var7 + var8 * var8);
}
    public static float[] F(Entity var0, byte var1, int var2, int var3) {
        long var4 = ((long)var1 << 56 | (long)var2 << 32 >>> 8 | (long)var3 << 40 >>> 40) ^ 0x69CE2458EE46L;
        long var6 = var4 ^ 0x3704A03D2CE1L;
        return RotationUtil.p(var0, 0.0, var6);
}
    public static float[] P(BlockPos var0, long var1, EnumFacing var3) {
        var1 = 0x69CE2458EE46L ^ var1;
        long var6 = var1 ^ 0x6116BB0CD8ECL;
        return RotationUtil.L(RaytraceUtil.o(var0, var3, var6));
}
    public static float[] L(Vec3 var2) {
        return RotationUtil.W(var2, RaytraceUtil.f());
}
    public static boolean L(long var0, AxisAlignedBB var2, double var3) {
        long var5 = (var0 = 0x69CE2458EE46L ^ var0) ^ 0x9DFE773144CL;
        return (double)RotationUtil.V(var2, var5) <= var3;
}
    public static float[] h(int var0, AxisAlignedBB var1, byte var2, int var3) {
        long var4 = ((long)var0 << 32 | (long)var2 << 56 >>> 32 | (long)var3 << 40 >>> 40) ^ 0x69CE2458EE46L;
        long var6 = var4 ^ 0x379E5E4637F5L;
        return RotationUtil.y(var6, var1, 0.0);
}
    public static float e(Entity var0, long var1) {
        var1 = 0x69CE2458EE46L ^ var1;
        long var5 = var1 ^ 0x17DE3F0FD368L;
        return RotationUtil.n(0L, RaytraceUtil.R(var0, var5, 2.147483647E9, false));
}
    public static float[] V(long var0, BlockPos var2, EnumFacing var3, double var4) {
        var0 = 0x69CE2458EE46L ^ var0;
        long var6 = var0 ^ 0x11493B2DCB64L;
        return RotationUtil.L(RaytraceUtil.v(var2, var3, var6, var4));
}
    public static float[] p(Entity var0, double var1, long var3) {
        var3 = 0x69CE2458EE46L ^ var3;
        long var7 = var3 ^ 0x5C320D2333AFL;
        return RotationUtil.L(RaytraceUtil.R(var0, var7, var1, true));
}
    static {
        boolean var2 = false;
        j = MinecraftRef.c((byte)(var2 ? 1 : 0), 0L);
}
}