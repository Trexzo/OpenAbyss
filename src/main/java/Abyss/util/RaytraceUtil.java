/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.MovingObjectPosition
 *  net.minecraft.util.MovingObjectPosition$MovingObjectType
 *  net.minecraft.util.Vec3
 */
package Abyss.util;

import Abyss.internal.accessor.MethodAccessors;
import Abyss.internal.synthetic.RaytraceUtilSwitchMapEnumFacing;
import Abyss.util.BlockUtil;
import Abyss.util.MathUtil;
import Abyss.util.MinecraftRef;
import Abyss.util.RotationManager;
import Abyss.util.RotationUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class RaytraceUtil {
    private static String[] e;
    private static final double c = 0.15;
    
    private static final double d = 1.0;
    private static Map i;
        private static Object[] j;
    private static Minecraft F;
    private static String[] l;
    private static long[] g;
    private static int V;
    private static final double k = 1.0E-6;
    private static List<Vec3> q;
    private static List<Vec3> p;
    private static Map f;

    private static double D(AxisAlignedBB var0, Vec3 var1, boolean var2, double var3) {
        double var5 = var0.field_72338_b;
        double var7 = var0.field_72337_e;
        double var9 = (var0.field_72340_a + var0.field_72336_d) * 0.5;
        double var11 = (var0.field_72339_c + var0.field_72334_f) * 0.5;
        double var13 = (var5 + var7) * 0.5;
        double var15 = var2 ? MathUtil.R(var3, var5, var7) : var13;
        double var17 = Math.sqrt((var1.field_72450_a - var9) * (var1.field_72450_a - var9) + (var1.field_72449_c - var11) * (var1.field_72449_c - var11));
        double var19 = var15 - var13;
        double var21 = Math.min(var13 + var17 * (var19 * 0.35), var15);
        double var23 = var1.field_72448_b <= var5 ? (var5 + var13) * 0.5 : MathUtil.R(var1.field_72448_b, (var5 + var13) * 0.5, var21);
        return MathUtil.R(var23, var5, var7);
}
    public static MovingObjectPosition k(AxisAlignedBB var0, double var1) {
        return RaytraceUtil.k(var0, RotationManager.r, RotationManager.G, var1);
}
    private static Vec3 U(AxisAlignedBB var0, long var1, Vec3 var3, double var4) {
        long var6 = var1 ^ 0x34923FD328FAL;
        double var8 = var4 > 0.0 ? var4 : Double.POSITIVE_INFINITY;
        double var10 = var0.field_72340_a;
        double var12 = var0.field_72336_d;
        double var14 = var0.field_72338_b;
        double var16 = var0.field_72337_e;
        double var18 = var0.field_72339_c;
        double var20 = var0.field_72334_f;
        double var22 = (var10 + var12) * 0.5;
        double var24 = (var14 + var16) * 0.5;
        double var26 = (var18 + var20) * 0.5;
        Vec3 var28 = RaytraceUtil.l(var0, var3);
        q.clear();
        if (!var0.func_72318_a(var3)) {
            q.add(var28);
}
        q.add(new Vec3(var22, var24, var26));
        q.add(new Vec3(var22, var14 + (var16 - var14) * 0.3, var26));
        q.add(new Vec3(var22, var14 + (var16 - var14) * 0.45, var26));
        q.add(new Vec3(var22, var14 + (var16 - var14) * 0.6, var26));
        q.add(new Vec3(var22, var14 + (var16 - var14) * 0.75, var26));
        q.add(new Vec3(var10, var24, var26));
        q.add(new Vec3(var12, var24, var26));
        q.add(new Vec3(var22, var24, var18));
        q.add(new Vec3(var22, var24, var20));
        q.add(new Vec3(var10, var14 + (var16 - var14) * 0.35, var26));
        q.add(new Vec3(var12, var14 + (var16 - var14) * 0.35, var26));
        q.add(new Vec3(var22, var14 + (var16 - var14) * 0.35, var18));
        q.add(new Vec3(var22, var14 + (var16 - var14) * 0.35, var20));
        q.add(new Vec3(var10, var14 + (var16 - var14) * 0.65, var26));
        q.add(new Vec3(var12, var14 + (var16 - var14) * 0.65, var26));
        q.add(new Vec3(var22, var14 + (var16 - var14) * 0.65, var18));
        q.add(new Vec3(var22, var14 + (var16 - var14) * 0.65, var20));
        q.addAll(RaytraceUtil.A(var0, var6));
        Vec3 var29 = null;
        double var30 = Double.MAX_VALUE;
        int var33 = q.size();
        for (int var32 = 0; var32 < var33; ++var32) {
            Vec3 var34 = RaytraceUtil.h(q.get(var32), var0);
            double var35 = var3.func_72438_d(var34);
            if (var35 > var8 || RaytraceUtil.F.field_71441_e.func_72933_a(var3, var34) != null) continue;
            double var37 = Math.abs(var34.field_72448_b - var24);
            double var39 = RaytraceUtil.d(var34.field_72450_a, var34.field_72449_c, var22, var26);
            double var41 = var34.func_72438_d(var28);
            double var43 = var35 * 1.5 + var41 * 0.55 + var37 * 0.8 + var39 * 0.65;
            if (!(var43 < var30)) continue;
            var30 = var43;
            var29 = var34;
}
        return var29;
}
    public static Vec3 R(long var0, AxisAlignedBB var2) {
        var0 = a ^ var0;
        int var3 = (int)((var0 ^ 0xD041333B342L) >>> 48);
        int var4 = (int)((var0 ^ 0xD041333B342L) << 16 >>> 48);
        int var5 = (int)((var0 ^ 0xD041333B342L) << 32 >>> 32);
        return RaytraceUtil.Q((short)var3, var2, 0.0, (short)var4, true, var5);
}
    public static double z(Vec3 var0) {
        return RaytraceUtil.f().func_72438_d(var0);
}
    public static boolean H(Vec3 var0, double var1) {
        Vec3 var3 = RaytraceUtil.f();
        return var3.func_72438_d(var0) > var1 ? true : RaytraceUtil.F.field_71441_e.func_72933_a(var3, var0) != null;
}
    public static boolean q(long var0, Entity var2, double var3) {
        long var5 = var0 ^ 0x190BFD88585AL;
        return RaytraceUtil.i(var2, var3, var5, false);
}
    public static double y(AxisAlignedBB var0, long var1) {
        long var3 = var1 ^ 0x64D863F9C05EL;
        return RaytraceUtil.f().func_72438_d(RaytraceUtil.f(var0, var3));
}
    public static double M(long var0, Entity var2, double var3, boolean var5) {
        long var6 = var0 ^ 0x776E83F35ED1L;
        return RaytraceUtil.f().func_72438_d(RaytraceUtil.R(var2, var6, var3, var5));
}
    public static double n(long var0, BlockPos var2, EnumFacing var3) {
        long var4 = var0 ^ 0x4F9DD55F9B83L;
        return RaytraceUtil.f().func_72438_d(RaytraceUtil.o(var2, var3, var4));
}
    private static void s(BlockPos var0, long var1, EnumFacing var3) {
        if (var0 == null) {
            throw new IllegalArgumentException("BlockPos cannot be null");
}
        if (var3 == null) {
            throw new IllegalArgumentException("EnumFacing is required for block targeting");
}
}
    public static double Z(double var0, double var2, double var4, double var6, double var8, double var10) {
        double var12 = var6 - var0;
        double var14 = var8 - var2;
        double var16 = var10 - var4;
        return Math.sqrt(var12 * var12 + var14 * var14 + var16 * var16);
}
    public static Vec3 M(AxisAlignedBB var0, short var1, int var2, Vec3 var3, char var4) {
        long var5 = ((long)var1 << 48 | (long)var2 << 32 >>> 16 | (long)var4 << 48 >>> 48) ^ a;
        long var7 = var5 ^ 0x334EFCF540L;
        return RaytraceUtil.d(var0, var3, 0.0, true, var7);
}
    private static Vec3 S(BlockPos var0, EnumFacing var1) {
        return new Vec3((double)var0.func_177958_n() + 0.5 + (double)var1.func_82601_c() * 0.5, (double)var0.func_177956_o() + 0.5 + (double)var1.func_96559_d() * 0.5, (double)var0.func_177952_p() + 0.5 + (double)var1.func_82599_e() * 0.5);
}
    public static MovingObjectPosition J(double var0) {
        Vec3 var2 = RaytraceUtil.f();
        Vec3 var3 = RaytraceUtil.F.field_71439_g.func_70040_Z();
        Vec3 var4 = var2.func_72441_c(var3.field_72450_a * var0, var3.field_72448_b * var0, var3.field_72449_c * var0);
        return RaytraceUtil.F.field_71439_g.field_70170_p.func_147447_a(var2, var4, false, false, false);
}
    public static MovingObjectPosition M() {
        return RaytraceUtil.f(RotationManager.r, RotationManager.G, RaytraceUtil.F.field_71442_b.func_78757_d(), 1.0f);
}
    public static Vec3 S(AxisAlignedBB var0, long var1, double var3, short var5) {
        long var6 = (var1 << 16 | (long)var5 << 48 >>> 48) ^ a;
        int var8 = (int)((var6 ^ 0x787E7E83D03DL) >>> 48);
        int var9 = (int)((var6 ^ 0x787E7E83D03DL) << 16 >>> 48);
        int var10 = (int)((var6 ^ 0x787E7E83D03DL) << 32 >>> 32);
        return RaytraceUtil.Q((short)var8, var0, var3, (short)var9, true, var10);
}
    public static Vec3 f() {
        return RaytraceUtil.F.field_71439_g.func_174824_e(1.0f);
}
    public static Vec3 w(long var0, AxisAlignedBB var2, Vec3 var3, double var4) {
        long var6 = var0 ^ 0x6C4B10533CAL;
        return RaytraceUtil.d(var2, var3, var4, true, var6);
}
    public static Vec3 t(BlockPos var0, long var1, EnumFacing var3, double var4) {
        long var6 = var1 ^ 0x83DDE54AA5DL;
        return RaytraceUtil.Z(var0, var6, var3, var4, false);
}
    public static Vec3 f(AxisAlignedBB var0, long var1) {
        var1 = a ^ var1;
        int var3 = (int)((var1 ^ 0x721387CA644AL) >>> 48);
        int var4 = (int)((var1 ^ 0x721387CA644AL) << 16 >>> 48);
        int var5 = (int)((var1 ^ 0x721387CA644AL) << 32 >>> 32);
        return RaytraceUtil.Q((short)var3, var0, 0.0, (short)var4, false, var5);
}
    public static AxisAlignedBB J(BlockPos var0) {
        return new AxisAlignedBB((double)var0.func_177958_n(), (double)var0.func_177956_o(), (double)var0.func_177952_p(), (double)(var0.func_177958_n() + 1), (double)(var0.func_177956_o() + 1), (double)(var0.func_177952_p() + 1));
}
    public static double t(Vec3 var0, Vec3 var1) {
        return var0.func_72438_d(var1);
}
    public static MovingObjectPosition v(long var0, BlockPos var2, EnumFacing var3) {
        long var4 = var0 ^ 0xB027659B775L;
        Vec3 var6 = RaytraceUtil.Z(var2, var4, var3, RaytraceUtil.F.field_71442_b.func_78757_d(), true);
        return RaytraceUtil.H(var6);
}
    public static MovingObjectPosition f(float var0, float var1, double var2, float var4) {
        Vec3 var5 = RaytraceUtil.F.field_71439_g.func_174824_e(var4);
        Vec3 var6 = RotationUtil.d(var1, var0);
        Vec3 var7 = var5.func_72441_c(var6.field_72450_a * var2, var6.field_72448_b * var2, var6.field_72449_c * var2);
        return RaytraceUtil.F.field_71441_e.func_72933_a(var5, var7);
}
    private static Vec3 s(AxisAlignedBB var0, Vec3 var1, double var2) {
        boolean var16;
        double var4 = var0.field_72340_a;
        double var6 = var0.field_72336_d;
        double var8 = var0.field_72339_c;
        double var10 = var0.field_72334_f;
        double var12 = (var4 + var6) * 0.5;
        double var14 = (var8 + var10) * 0.5;
        boolean bl = var16 = var0.field_72340_a - 1.0 < var1.field_72450_a && var0.field_72336_d + 1.0 > var1.field_72450_a && var0.field_72339_c - 1.0 < var1.field_72449_c && var0.field_72334_f + 1.0 > var1.field_72449_c;
        if (var16) {
            return new Vec3(var12, var2, var14);
}
        double var17 = MathUtil.R(var1.field_72450_a, var4, var6);
        double var19 = MathUtil.R(var1.field_72449_c, var8, var10);
        double var21 = 0.3;
        double var23 = MathUtil.I(var17, var12, var21);
        double var25 = MathUtil.I(var19, var14, var21);
        double var27 = 0.06;
        return new Vec3(MathUtil.R(var23, var4 + var27, var6 - var27), var2, MathUtil.R(var25, var8 + var27, var10 - var27));
}
    public static EntityLivingBase Z(double var0) {
        Entity var2 = null;
        MovingObjectPosition var3 = RaytraceUtil.F.field_71439_g.func_174822_a(var0, 1.0f);
        Vec3 var4 = RaytraceUtil.f();
        float var5 = RotationManager.r;
        float var6 = RotationManager.G;
        float var7 = MathHelper.func_76134_b((float)(-var5 * ((float)Math.PI / 180) - (float)Math.PI));
        float var8 = MathHelper.func_76126_a((float)(-var5 * ((float)Math.PI / 180) - (float)Math.PI));
        float var9 = -MathHelper.func_76134_b((float)(-var6 * ((float)Math.PI / 180)));
        Vec3 var10 = new Vec3((double)(var8 * var9), (double)MathHelper.func_76126_a((float)(-var6 * ((float)Math.PI / 180))), (double)(var7 * var9));
        Vec3 var11 = var4.func_72441_c(var10.field_72450_a * var0, var10.field_72448_b * var0, var10.field_72449_c * var0);
        Vec3 var12 = null;
        List var13 = RaytraceUtil.F.field_71441_e.func_72839_b(F.func_175606_aa(), F.func_175606_aa().func_174813_aQ().func_72321_a(var10.field_72450_a * var0, var10.field_72448_b * var0, var10.field_72449_c * var0).func_72314_b(1.0, 1.0, 1.0));
        double var14 = var0;
        int var17 = var13.size();
        for (int var16 = 0; var16 < var17; ++var16) {
            double var21;
            Entity var18 = (Entity)var13.get(var16);
            if (!var18.func_70067_L()) continue;
            AxisAlignedBB var19 = RaytraceUtil.S(var18);
            MovingObjectPosition var20 = var19.func_72327_a(var4, var11);
            if (var19.func_72318_a(var4)) {
                if (!(0.0 < var14) && var14 != 0.0) continue;
                var2 = var18;
                var12 = var20 == null ? var4 : var20.field_72307_f;
                var14 = 0.0;
                continue;
}
            if (var20 == null || !((var21 = var4.func_72438_d(var20.field_72307_f)) < var14) && var14 != 0.0) continue;
            if (var18 != RaytraceUtil.F.func_175606_aa().field_70154_o || MethodAccessors.o(var18)) {
                var2 = var18;
                var12 = var20.field_72307_f;
                var14 = var21;
                continue;
}
            if (var14 != 0.0) continue;
            var2 = var18;
            var12 = var20.field_72307_f;
}
        if (var2 != null && (var14 < var0 || var3 == null)) {
            var3 = new MovingObjectPosition(var2, var12);
}
        return var3 != null && var3.field_72313_a == MovingObjectPosition.MovingObjectType.ENTITY && var3.field_72308_g instanceof EntityLivingBase ? (EntityLivingBase)var3.field_72308_g : null;
}
    public static AxisAlignedBB S(Entity var0) {
        float var1 = var0.func_70111_Y();
        return var0.func_174813_aQ().func_72314_b((double)var1, (double)var1, (double)var1);
}
    public static Vec3 e(Entity var0, long var1) {
        long var3 = var1 ^ 0x6010ED40E958L;
        return RaytraceUtil.R(var0, var3, 0.0, false);
}
    public static Vec3 R(Entity var0, long var1, double var3, boolean var5) {
        var1 = a ^ var1;
        int var6 = (int)((var1 ^ 0x627E6F55A69DL) >>> 32);
        int var7 = (int)((var1 ^ 0x627E6F55A69DL) << 32 >>> 40);
        int var8 = (int)((var1 ^ 0x627E6F55A69DL) << 56 >>> 56);
        AxisAlignedBB var9 = RaytraceUtil.S(var0);
        Vec3 var10 = RaytraceUtil.f();
        double var11 = var9.field_72338_b + (double)var0.func_70111_Y() + (double)var0.func_70047_e();
        return RaytraceUtil.distanceTo(var9, var10, var3, var5, var6, true, var7, (byte)var8, var11);
}
    public static double p(BlockPos var0, long var1) {
        long var3 = var1 ^ 0x12F798D90770L;
        return RaytraceUtil.n(var3, var0, BlockUtil.D(var0));
}
    private static Vec3 i(Vec3 var0, Vec3 var1, double var2) {
        return new Vec3(MathUtil.I(var0.field_72450_a, var1.field_72450_a, var2), MathUtil.I(var0.field_72448_b, var1.field_72448_b, var2), MathUtil.I(var0.field_72449_c, var1.field_72449_c, var2));
}
    public static boolean Y(BlockPos var0, double var1, long var3) {
        long var5 = var3 ^ 0x67B67CFBF6BEL;
        return RaytraceUtil.p(var0, var5) <= var1;
}
    public static Vec3 e(long var0, BlockPos var2, EnumFacing var3) {
        long var4 = var0 ^ 0x5602035C7034L;
        return RaytraceUtil.Z(var2, var4, var3, 0.0, true);
}
    public static Vec3 U(long var0, AxisAlignedBB var2, Vec3 var3) {
        long var4 = var0 ^ 0x56BBC397BBF2L;
        return RaytraceUtil.d(var2, var3, 0.0, false, var4);
}
    public static Vec3 w(Entity var0, int var1, short var2, double var3, char var5) {
        long var6 = ((long)var1 << 32 | (long)var2 << 48 >>> 32 | (long)var5 << 48 >>> 48) ^ a;
        long var8 = var6 ^ 0x3758B476ED4BL;
        return RaytraceUtil.R(var0, var8, var3, false);
}
    public static double t(Entity var0, int var1, double var2, long var4) {
        long var6 = ((long)var1 << 32 | var4 << 32 >>> 32) ^ a;
        long var8 = var6 ^ 0x63DD60E0123BL;
        return RaytraceUtil.M(var8, var0, var2, false);
}
    public static Vec3 o(BlockPos var0, EnumFacing var1, long var2) {
        long var4 = var2 ^ 0x170E17ADC94EL;
        return RaytraceUtil.Z(var0, var4, var1, 0.0, false);
}
    public static MovingObjectPosition H(Vec3 var0) {
        return RaytraceUtil.F.field_71441_e.func_72933_a(RaytraceUtil.f(), var0);
}
    public static Vec3 O(Entity var0, long var1) {
        long var3 = var1 ^ 0x59C782736E06L;
        return RaytraceUtil.R(var0, var3, 0.0, true);
}
    public static boolean i(Entity var0, double var1, long var3, boolean var5) {
        var3 = a ^ var3;
        long var6 = var3 ^ 0x17200FD85A2FL;
        long var8 = var3 ^ 0x5332F4A53553L;
        return var5 && RaytraceUtil.V(var0, var6, var1) ? false : RaytraceUtil.M(var8, var0, var1, var5) <= var1;
}
    private static Vec3 i(AxisAlignedBB var0, Vec3 var1, Vec3 var2) {
        boolean var3;
        boolean bl = var3 = var0.field_72340_a - 1.0 < var1.field_72450_a && var0.field_72336_d + 1.0 > var1.field_72450_a && var0.field_72339_c - 1.0 < var1.field_72449_c && var0.field_72334_f + 1.0 > var1.field_72449_c;
        if (var3) {
            double var4 = (var0.field_72340_a + var0.field_72336_d) * 0.5;
            double var6 = (var0.field_72339_c + var0.field_72334_f) * 0.5;
            return new Vec3(var4, var2.field_72448_b, var6);
}
        return RaytraceUtil.l(var0, var1);
}
    private static boolean M(BlockPos var0, EnumFacing var1, Vec3 var2, Vec3 var3, double var4) {
        if (var4 > 0.0 && var3.func_72438_d(var2) > var4) {
            return false;
}
        MovingObjectPosition var6 = RaytraceUtil.F.field_71441_e.func_72933_a(var3, var2);
        return var6 != null && var6.field_72313_a == MovingObjectPosition.MovingObjectType.BLOCK && var0.equals((Object)var6.func_178782_a()) && var1 == var6.field_178784_b;
}
    public static double d(double var0, double var2, double var4, double var6) {
        double var8 = var4 - var0;
        double var10 = var6 - var2;
        return Math.sqrt(var8 * var8 + var10 * var10);
}
    private static Vec3 e(AxisAlignedBB var0, Vec3 var1) {
        Vec3 var2 = RaytraceUtil.l(var0, var1);
        return var2.func_72438_d(var1) > 1.0E-6 ? var2 : new Vec3((var0.field_72340_a + var0.field_72336_d) * 0.5, (var0.field_72338_b + var0.field_72337_e) * 0.5, (var0.field_72339_c + var0.field_72334_f) * 0.5);
}
    public static List<EntityLivingBase> j(double var0) {
        WorldClient var2 = RaytraceUtil.F.field_71441_e;
        Entity var3 = F.func_175606_aa();
        ArrayList<EntityLivingBase> var4 = new ArrayList<EntityLivingBase>();
        Vec3 var5 = RaytraceUtil.f();
        float var6 = RotationManager.r;
        float var7 = RotationManager.G;
        float var8 = MathHelper.func_76134_b((float)(-var6 * ((float)Math.PI / 180) - (float)Math.PI));
        float var9 = MathHelper.func_76126_a((float)(-var6 * ((float)Math.PI / 180) - (float)Math.PI));
        float var10 = -MathHelper.func_76134_b((float)(-var7 * ((float)Math.PI / 180)));
        float var11 = MathHelper.func_76126_a((float)(-var7 * ((float)Math.PI / 180)));
        Vec3 var12 = new Vec3((double)(var9 * var10), (double)var11, (double)(var8 * var10));
        Vec3 var13 = var5.func_72441_c(var12.field_72450_a * var0, var12.field_72448_b * var0, var12.field_72449_c * var0);
        AxisAlignedBB var14 = var3.func_174813_aQ().func_72321_a(var12.field_72450_a * var0, var12.field_72448_b * var0, var12.field_72449_c * var0).func_72314_b(1.0, 1.0, 1.0);
        List var15 = var2.func_72839_b(var3, var14);
        int var17 = var15.size();
        for (int var16 = 0; var16 < var17; ++var16) {
            Entity var18 = (Entity)var15.get(var16);
            if (!(var18 instanceof EntityLivingBase) || !var18.func_70067_L()) continue;
            AxisAlignedBB var19 = RaytraceUtil.S(var18);
            MovingObjectPosition var20 = var19.func_72327_a(var5, var13);
            if (!var19.func_72318_a(var5) && var20 == null) continue;
            var4.add((EntityLivingBase)var18);
}
        return var4;
}
    public static boolean r(short var0, long var1, BlockPos var3, EnumFacing var4, double var5) {
        long var7 = ((long)var0 << 48 | var1 << 16 >>> 16) ^ a;
        long var9 = var7 ^ 0x599679687C6AL;
        long var11 = var7 ^ 0x27570541BDAEL;
        RaytraceUtil.s(var3, var11, var4);
        Vec3 var13 = RaytraceUtil.f();
        return !RaytraceUtil.M(var3, var4, RaytraceUtil.g(var3, var4, var9, var13), var13, var5) && !RaytraceUtil.M(var3, var4, RaytraceUtil.S(var3, var4), var13, var5);
}
    public static MovingObjectPosition h(AxisAlignedBB var0, float var1, float var2, double var3) {
        return RaytraceUtil.k(var0, var1, var2, var3);
}
    public static boolean V(Entity var0, long var1, double var3) {
        Vec3 var8;
        long var5 = var1 ^ 0x119A563F2717L;
        AxisAlignedBB var7 = RaytraceUtil.S(var0);
        return RaytraceUtil.U(var7, var5, var8 = RaytraceUtil.f(), var3) == null;
}
    private static List A(AxisAlignedBB var0, long var1) {
        p.clear();
        double var4 = 5.0;
        double var6 = (var0.field_72336_d - var0.field_72340_a) / var4;
        double var8 = (var0.field_72337_e - var0.field_72338_b) / var4;
        double var10 = (var0.field_72334_f - var0.field_72339_c) / var4;
        for (int var12 = 0; var12 < 6; ++var12) {
            double var13 = var0.field_72340_a + (double)var12 * var6;
            for (int var15 = 0; var15 < 6; ++var15) {
                double var16 = var0.field_72338_b + (double)var15 * var8;
                p.add(new Vec3(var13, var16, var0.field_72339_c));
                p.add(new Vec3(var13, var16, var0.field_72334_f));
}
}
        for (int var19 = 0; var19 < 6; ++var19) {
            double var21 = var0.field_72340_a + (double)var19 * var6;
            for (int var23 = 0; var23 < 6; ++var23) {
                double var25 = var0.field_72339_c + (double)var23 * var10;
                p.add(new Vec3(var21, var0.field_72338_b, var25));
                p.add(new Vec3(var21, var0.field_72337_e, var25));
}
}
        for (int var20 = 0; var20 < 6; ++var20) {
            double var22 = var0.field_72338_b + (double)var20 * var8;
            for (int var24 = 0; var24 < 6; ++var24) {
                double var26 = var0.field_72339_c + (double)var24 * var10;
                p.add(new Vec3(var0.field_72340_a, var22, var26));
                p.add(new Vec3(var0.field_72336_d, var22, var26));
}
}
        return p;
}
    public static Vec3 h(Vec3 var0, AxisAlignedBB var1) {
        double var2 = MathHelper.func_151237_a((double)var0.field_72450_a, (double)var1.field_72340_a, (double)var1.field_72336_d);
        double var4 = MathHelper.func_151237_a((double)var0.field_72448_b, (double)var1.field_72338_b, (double)var1.field_72337_e);
        double var6 = MathHelper.func_151237_a((double)var0.field_72449_c, (double)var1.field_72339_c, (double)var1.field_72334_f);
        return new Vec3(var2, var4, var6);
}
    public static Vec3 p(AxisAlignedBB var0, long var1, double var3) {
        var1 = a ^ var1;
        int var5 = (int)((var1 ^ 0x3F743FB991D2L) >>> 48);
        int var6 = (int)((var1 ^ 0x3F743FB991D2L) << 16 >>> 48);
        int var7 = (int)((var1 ^ 0x3F743FB991D2L) << 32 >>> 32);
        return RaytraceUtil.Q((short)var5, var0, var3, (short)var6, false, var7);
}
    public static Vec3 g(long var0, Entity var2, double var3) {
        long var5 = var0 ^ 0x29F9FC52F1D3L;
        return RaytraceUtil.R(var2, var5, var3, true);
}
    public static double i(Entity var0) {
        return RaytraceUtil.M(65711117411872L, var0, 0.0, false);
}
    private static boolean g(Vec3 var0, Vec3 var1, double var2, boolean var4) {
        if (var0 == null) {
            return false;
}
        return var1.func_72438_d(var0) > var2 ? false : !var4 || RaytraceUtil.F.field_71441_e.func_72933_a(var1, var0) == null;
}
    public static Vec3 v(BlockPos var0, EnumFacing var1, long var2, double var4) {
        long var6 = var2 ^ 0x52F007533CL;
        return RaytraceUtil.Z(var0, var6, var1, var4, true);
}
    public static boolean u(short var0, int var1, BlockPos var2, EnumFacing var3, int var4, double var5) {
        long var7 = ((long)var0 << 48 | (long)var1 << 32 >>> 16 | (long)var4 << 48 >>> 48) ^ a;
        long var9 = var7 ^ 0x3CA863A6FB26L;
        return RaytraceUtil.n(var9, var2, var3) <= var5;
}
    public static MovingObjectPosition F(float var0, float var1) {
        return RaytraceUtil.f(var0, var1, RaytraceUtil.F.field_71442_b.func_78757_d(), 1.0f);
}
    public static Vec3 J(AxisAlignedBB var0, char var1, Vec3 var2, double var3, short var5, int var6) {
        long var7 = ((long)var1 << 48 | (long)var5 << 48 >>> 16 | (long)var6 << 32 >>> 32) ^ a;
        long var9 = var7 ^ 0x72955BF95102L;
        return RaytraceUtil.d(var0, var2, var3, false, var9);
}
    private static Vec3 g(BlockPos var0, EnumFacing var1, long var2, Vec3 var4) {
        Vec3 var5 = RaytraceUtil.S(var0, var1);
        double var6 = (double)var0.func_177958_n() + 0.15;
        double var8 = (double)var0.func_177958_n() + 1.0 - 0.15;
        double var10 = (double)var0.func_177952_p() + 0.15;
        double var12 = (double)var0.func_177952_p() + 1.0 - 0.15;
        double var14 = Math.toRadians(RotationManager.r);
        double var16 = -Math.sin(var14);
        double var18 = Math.cos(var14);
        double var20 = var5.field_72450_a;
        double var22 = var5.field_72449_c;
        switch (RaytraceUtilSwitchMapEnumFacing.b[var1.ordinal()]) {
            case 1: 
            case 2: {
                double var29;
                if (!(Math.abs(var16) > 1.0E-6) || !((var29 = (var5.field_72450_a - var4.field_72450_a) / var16) >= 0.0)) break;
                var22 = MathUtil.R(var4.field_72449_c + var18 * var29, var10, var12);
                break;
}
            case 3: 
            case 4: {
                double var28;
                if (!(Math.abs(var18) > 1.0E-6) || !((var28 = (var5.field_72449_c - var4.field_72449_c) / var18) >= 0.0)) break;
                var20 = MathUtil.R(var4.field_72450_a + var16 * var28, var6, var8);
                break;
}
            case 5: 
            case 6: {
                double var24 = (var5.field_72450_a - var4.field_72450_a) * var16 + (var5.field_72449_c - var4.field_72449_c) * var18;
                if (!(var24 >= 0.0)) break;
                var20 = MathUtil.R(var4.field_72450_a + var16 * var24, var6, var8);
                var22 = MathUtil.R(var4.field_72449_c + var18 * var24, var10, var12);
}
}
        Vec3 var30 = new Vec3(var20, var5.field_72448_b, var22);
        float var25 = Math.abs(MathUtil.M(RotationManager.r, RotationUtil.W(var30, var4)[0]));
        float var26 = Math.abs(MathUtil.M(RotationManager.r, RotationUtil.W(var5, var4)[0]));
        return var25 <= var26 ? var30 : var5;
}
    public static Vec3 Q(short var0, AxisAlignedBB var1, double var2, short var4, boolean var5, int var6) {
        long var7 = ((long)var0 << 48 | (long)var4 << 48 >>> 16 | (long)var6 << 32 >>> 32) ^ a;
        long var9 = var7 ^ 0x473517D066DL;
        return RaytraceUtil.d(var1, RaytraceUtil.f(), var2, var5, var9);
}
    public static Vec3 d(AxisAlignedBB var0, Vec3 var1, double var2, boolean var4, long var5) {
        var5 = a ^ var5;
        int var7 = (int)((var5 ^ 0x5F26540FECBCL) >>> 32);
        int var8 = (int)((var5 ^ 0x5F26540FECBCL) << 32 >>> 40);
        int var9 = (int)((var5 ^ 0x5F26540FECBCL) << 56 >>> 56);
        return RaytraceUtil.distanceTo(var0, var1, var2, var4, var7, false, var8, (byte)var9, 0.0);
}
    private static Vec3 l(AxisAlignedBB var0, Vec3 var1) {
        return new Vec3(MathUtil.R(var1.field_72450_a, var0.field_72340_a, var0.field_72336_d), MathUtil.R(var1.field_72448_b, var0.field_72338_b, var0.field_72337_e), MathUtil.R(var1.field_72449_c, var0.field_72339_c, var0.field_72334_f));
}
    public static Vec3 Z(BlockPos var0, long var1, EnumFacing var3, double var4, boolean var6) {
        var1 = a ^ var1;
        long var7 = var1 ^ 0x29339CEB35DEL;
        long var9 = var1 ^ 0x57F2E0C2F41AL;
        RaytraceUtil.s(var0, var9, var3);
        Vec3 var11 = RaytraceUtil.f();
        Vec3 var12 = RaytraceUtil.g(var0, var3, var7, var11);
        if (var6 && !RaytraceUtil.M(var0, var3, var12, var11, var4)) {
            Vec3 var13 = RaytraceUtil.S(var0, var3);
            return RaytraceUtil.M(var0, var3, var13, var11, var4) ? var13 : var12;
}
        return var12;
}
    public static MovingObjectPosition k(AxisAlignedBB var0, float var1, float var2, double var3) {
        Vec3 var5 = RaytraceUtil.f();
        Vec3 var6 = RotationUtil.d(var2, var1);
        Vec3 var7 = var5.func_72441_c(var6.field_72450_a * var3, var6.field_72448_b * var3, var6.field_72449_c * var3);
        return var0.func_72327_a(var5, var7);
}
    public static boolean x(AxisAlignedBB var0, long var1, double var3) {
        long var5 = var1 ^ 0x7D346B835C14L;
        return RaytraceUtil.y(var0, var5) <= var3;
}
    private static Vec3 distanceTo(AxisAlignedBB var0, Vec3 var1, double var2, boolean var4, int var5, boolean var6, int var7, byte var8, double var9) {
        Vec3 var26;
        double var19;
        double var21;
        double var23;
        Vec3 var25;
        long var11 = ((long)var5 << 32 | (long)var7 << 40 >>> 32 | (long)var8 << 56 >>> 56) ^ a;
        long var13 = var11 ^ 0x409841E4B027L;
        double var15 = var2 > 0.0 ? var2 : Double.POSITIVE_INFINITY;
        Vec3 var17 = RaytraceUtil.a(var0, var1, var6, var9);
        if (RaytraceUtil.g(var17, var1, var15, var4)) {
            return var17;
}
        Vec3 var18 = RaytraceUtil.i(var0, var1, var17);
        if (RaytraceUtil.g(var18, var1, var15, var4) && RaytraceUtil.g(var25 = RaytraceUtil.h(RaytraceUtil.i(var18, var17, var23 = MathUtil.R(0.35 + (var21 = 1.0 - MathUtil.R((var19 = var1.func_72438_d(var18)) / var15, 0.0, 1.0)) * 0.5, 0.35, 0.85)), var0), var1, var15, var4)) {
            return var25;
}
        if (var4 && (var26 = RaytraceUtil.U(var0, var13, var1, var2)) != null) {
            return var26;
}
        return RaytraceUtil.e(var0, var1);
}
    public static MovingObjectPosition A(long var0, Entity var2) {
        long var3 = var0 ^ 0x53E3422DCE38L;
        Vec3 var5 = RaytraceUtil.R(var2, var3, RaytraceUtil.F.field_71442_b.func_78757_d(), true);
        return RaytraceUtil.H(var5);
}
    private static Vec3 a(AxisAlignedBB var0, Vec3 var1, boolean var2, double var3) {
        double var5 = RaytraceUtil.D(var0, var1, var2, var3);
        return RaytraceUtil.s(var0, var1, var5);
}
    static {
        V = 4;
        p = new ArrayList<Vec3>(256);
        q = new ArrayList<Vec3>(256);
        F = MinecraftRef.c((byte)0, 0L);
}
}