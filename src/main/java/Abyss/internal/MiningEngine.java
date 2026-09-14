/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.ItemTool
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.MovingObjectPosition
 *  net.minecraft.util.MovingObjectPosition$MovingObjectType
 *  net.minecraft.util.Vec3
 *  net.minecraft.world.World
 */
package Abyss.internal;

import Abyss.enums.MiningRegionState;
import Abyss.enums.RotationMode;
import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.MiningEngineBinder;
import Abyss.event.events.PreTickEvent;
import Abyss.internal.BrokenBlockTracker;
import Abyss.internal.MinedBlockTimestamp;
import Abyss.internal.MiningAxisScanResult;
import Abyss.internal.MiningBlockScanner;
import Abyss.internal.MiningProgress;
import Abyss.internal.MiningRegionScanResult;
import Abyss.internal.MiningState;
import Abyss.module.Modules;
import Abyss.module.impl.world.AutoTunnel;
import Abyss.util.AutoToolService;
import Abyss.util.ClientUtil;
import Abyss.util.KeyBindUtil;
import Abyss.util.MathUtil;
import Abyss.util.MinecraftRef;
import Abyss.util.MiningConstants;
import Abyss.util.RotationManager;
import Abyss.util.Sneaky;
import Abyss.util.render.BoxRenderer;
import java.awt.Color;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class MiningEngine
implements EventSubscriber {
    private static Map gb;
    private final MiningState K;
    private boolean j;
    private double Q;
    private final HashSet<BlockPos> P;
    private static long[] eb;
    private boolean W;
    private boolean p;
    private static long[] hb;
    private int H;
    private int n;
    private static String[] bb;
    private boolean w;
    private long z;
    private static long T;
    private long S;
    private float v;
    private boolean r;
    private long e;
    private long R;
    private boolean f;
    private int U;
    private boolean u;
    private boolean N;
    private static Long[] ib;
    private BlockPos L;
    private static final double x = 1.0;
    private float uM;
    private long X;
    private static long i;
    private long l;
    private static long Y;
    private final List<BlockPos> I;
    private static long o;
    private boolean D;
    private static Map jb;
    private boolean m;
    private Boolean d;
    private boolean J;
    private long u7;
    private static Random uA;
    private static Map db;
    private long k;
    private boolean q;
    private boolean A;
    private float B;
    private static long ab;
    private final List<MinedBlockTimestamp> c;
    private long O;
    private double C;
    private Float b;
    private static long g;
    private boolean V;
    private static final float y = 70.0f;
    private final List<BlockPos> E;
    
    private static String[] cb;
    private float h;
    public static MiningEngine uq;
    private long s;
    private static long t;
    private long G;
    private static Minecraft F;
    private static Integer[] fb;
    private BlockPos M;
    private boolean Z;

    public void b(long var1) {
        var1 = ab ^ var1;
        long var3 = var1 ^ 0x65975BCA0406L;
        long var5 = (var1 ^ 0x4499508386D3L) >>> 32;
        int var7 = (int)((var1 ^ 0x4499508386D3L) << 32 >>> 32);
        if (this.D || MiningEngine.F.field_71439_g != null && MiningEngine.F.field_71441_e != null) {
            if (this.D) {
                this.B(var5, var7);
            } else {
                this.a(var3);
}
}
}
    private void W(List<BlockPos> var1, HashSet<BlockPos> var2, BlockPos var3) {
        if (var2.add(var3)) {
            var1.add(var3);
}
}
    public void z(long var1) {
        long var3 = var1 ^ 0x67AF482D0296L;
        if (this.D && MiningEngine.F.field_71439_g != null) {
            this.W(var3);
}
}
    private boolean S(long var1, BlockPos var3) {
        var1 = ab ^ var1;
        long var4 = var1 ^ 0x3A60F8FD53ECL;
        long var6 = var1 ^ 0x37CC41507CB3L;
        return this.f() ? this.t(var6, var3) : this.i(var3, var4);
}
    private boolean z(long var1, long var3) {
        if (!this.w) {
            return false;
}
        if (var1 >= this.s) {
            if (this.U >= 3) {
                this.u(101811667684420L);
                this.N = true;
                return false;
}
            ++this.U;
            this.s = var1 + 300L;
}
        this.c(41798187465823L);
        return true;
}
    private boolean m(BlockPos var1, long var2) {
        long var4 = var2 ^ 0xD8B4587964CL;
        EntityPlayerSP var6 = MiningEngine.F.field_71439_g;
        Block var7 = MiningEngine.F.field_71441_e.func_180495_p(var1).func_177230_c();
        if (var7 == Blocks.field_150350_a) {
            return false;
}
        if (BrokenBlockTracker.m.k(var1)) {
            return false;
}
        return (var7 == Blocks.field_150486_ae || var7 == Blocks.field_150447_bR) && !this.Q(var1) ? false : var7.func_176195_g((World)MiningEngine.F.field_71441_e, var1) >= 0.0f && this.N(var4, F, var6, var1);
}
    private boolean c(EntityPlayerSP var1) {
        for (BlockPos var3 : this.u(var1)) {
            if (MiningEngine.F.field_71441_e.func_180495_p(var3).func_177230_c() == Blocks.field_150350_a) continue;
            return false;
}
        return true;
}
    private MiningEngine(int var1, int var2, int var3) {
        long var4 = ((long)var1 << 32 | (long)var2 << 48 >>> 32 | (long)var3 << 48 >>> 48) ^ ab;
        long var6 = var4 ^ 0x59E2917AC832L;
        this.K = new MiningState(var6);
        this.I = new ArrayList<BlockPos>();
        this.c = new ArrayList<MinedBlockTimestamp>();
        this.E = new ArrayList<BlockPos>();
        this.P = new HashSet();
        this.D = false;
        this.v = 0.0f;
        this.uM = 0.0f;
        this.u7 = 0L;
        this.O = 0L;
        this.e = 0L;
        this.V = false;
        this.C = 0.0;
        this.Q = 0.0;
        this.S = 0L;
        this.J = false;
        this.B = 0.0f;
        this.this.R = 0L;
        this.n = -1;
        this.d = null;
        this.h = Float.NaN;
        this.m = false;
        this.l = 0L;
        this.p = false;
        this.b = null;
        this.f = false;
        this.r = false;
        this.z = 0L;
        this.q = false;
        this.A = false;
        this.H = 0;
        this.j = false;
        this.W = false;
        this.X = 0L;
        this.Z = false;
        this.u = false;
        this.G = 0L;
        this.w = false;
        this.N = false;
        this.U = 0;
        this.s = 0L;
}
    public void v(long var1) {
        int var26 = 2524;
        if (this.D) {
            this.K.V(94183755216258L);
            this.K.s(28894398477326L);
            this.K.c(88214989872647L);
            this.K.w(108613680588488L);
            this.k((byte)0, 7372158);
            this.J();
            BrokenBlockTracker.m.e(98246303673997L);
            MiningBlockScanner.j(53803850989232L);
            MiningBlockScanner.h(61947740700751L);
            MiningBlockScanner.l(14863, (short)29585, (char)var26);
            this.I(32278145520763L);
}
}
    private boolean X(int var1, char var2, int var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = ((long)var1 << 32 | (long)var2 << 48 >>> 32 | (long)var3 << 48 >>> 48) ^ ab;
        long var6 = var4 ^ 0x3D3D1AACF9D9L;
        long var8 = var4 ^ 0xED32D1D29B6L;
        int var10 = (int)((var4 ^ 0x61F73370DE4CL) >>> 32);
        int var11 = (int)((var4 ^ 0x61F73370DE4CL) << 32 >>> 48);
        int var12 = (int)((var4 ^ 0x61F73370DE4CL) << 48 >>> 48);
        long var13 = var4 ^ 0x6B474CEC080BL;
        long var15 = var4 ^ 0x1B7F52AFABEAL;
        long var17 = System.currentTimeMillis();
        if (var17 - this.k < 1000L) {
            return false;
}
        float var19 = this.w(RotationManager.r);
        double var20 = Math.toRadians(var19);
        double var22 = -Math.sin(var20);
        double var24 = Math.cos(var20);
        double var26 = MiningEngine.F.field_71439_g.field_70165_t + var22 * 1.2;
        double var28 = MiningEngine.F.field_71439_g.field_70161_v + var24 * 1.2;
        int var30 = (int)Math.floor(var26);
        int var31 = (int)MiningEngine.F.field_71439_g.field_70163_u;
        int var32 = (int)Math.floor(var28);
        BlockPos var33 = new BlockPos(var30, var31, var32);
        BlockPos var34 = new BlockPos(var30, var31 + 1, var32);
        if (!this.X(var33) && !this.X(var34)) {
            return false;
}
        if (!MiningConstants.A) {
            this.L(var6, "dead end detected and auto turn is disabled");
            return true;
}
        double var35 = Math.sqrt(Math.pow((double)var33.func_177958_n() + 0.5 - MiningEngine.F.field_71439_g.field_70165_t, 2.0) + Math.pow((double)var33.func_177952_p() + 0.5 - MiningEngine.F.field_71439_g.field_70161_v, 2.0));
        if (var35 > 1.0) {
            KeyBindUtil.A(var13, MiningEngine.F.field_71474_y.field_74351_w.func_151463_i(), true);
            KeyBindUtil.A(var13, MiningEngine.F.field_71474_y.field_74368_y.func_151463_i(), false);
            KeyBindUtil.A(var13, MiningEngine.F.field_71474_y.field_74311_E.func_151463_i(), false);
            return false;
}
        this.k = var17;
        double var37 = Math.toRadians((var19 - 90.0f) % 360.0f);
        double var39 = Math.toRadians((var19 + 90.0f) % 360.0f);
        BlockPos var41 = new BlockPos((int)Math.floor(MiningEngine.F.field_71439_g.field_70165_t - Math.sin(var37) * 1.2), var31, (int)Math.floor(MiningEngine.F.field_71439_g.field_70161_v + Math.cos(var37) * 1.2));
        BlockPos var42 = new BlockPos((int)Math.floor(MiningEngine.F.field_71439_g.field_70165_t - Math.sin(var39) * 1.2), var31, (int)Math.floor(MiningEngine.F.field_71439_g.field_70161_v + Math.cos(var39) * 1.2));
        boolean var43 = this.X(var41);
        boolean var44 = this.X(var42);
        if (!var43 && !var44) {
            MiningAxisScanResult var46 = MiningBlockScanner.l(var8, 50);
            int var47 = var46.Z;
            int var48 = var46.t;
            int var49 = var46.c;
            int var50 = var46.S;
            boolean var54 = var47 > var48 + 3 ? true : (var48 > var47 + 3 ? false : (var49 != -1 && var50 != -1 ? (var49 > var50 ? true : (var50 > var49 ? false : uA.nextBoolean())) : (var49 != -1 ? false : (var50 != -1 ? true : uA.nextBoolean()))));
            MiningRegionScanResult var52 = MiningBlockScanner.b(var15, 4);
            if (var52.L == MiningRegionState.EMPTY && var52.j == MiningRegionState.EMPTY) {
                this.L(var6, "dead end detected with no viable tunnel on either side");
                return true;
}
            if ((var52.L == MiningRegionState.EMPTY || var52.j == MiningRegionState.EMPTY) && var52.N == MiningRegionState.EMPTY) {
                this.L(var6, "dead end detected with no side path and no back path");
                return true;
}
            if (!(var54 && var52.L == MiningRegionState.EMPTY || !var54 && var52.j == MiningRegionState.EMPTY)) {
                this.o(var54 ? -90.0f : 90.0f, var10, var11, (char)var12);
                return true;
}
            this.o(180.0f, var10, var11, (char)var12);
            return true;
}
        if (!var43) {
            MiningRegionScanResult var53 = MiningBlockScanner.b(var15, 4);
            if (var53.L == MiningRegionState.EMPTY) {
                this.o(180.0f, var10, var11, (char)var12);
                return true;
}
            this.o(-90.0f, var10, var11, (char)var12);
            return true;
}
        if (!var44) {
            MiningRegionScanResult var45 = MiningBlockScanner.b(var15, 4);
            if (var45.j == MiningRegionState.EMPTY) {
                this.o(180.0f, var10, var11, (char)var12);
                return true;
}
            this.o(90.0f, var10, var11, (char)var12);
            return true;
}
        this.L(var6, "dead end detected with no viable turn");
        return true;
}
    public boolean h() {
        return this.D;
}
    private float m(EntityPlayerSP var1) {
        return !this.D && !this.J ? var1.field_70177_z : RotationManager.r;
}
    private boolean x(EntityPlayerSP var1, long var2) {
        if (!this.f() || this.j || this.M == null) {
            return false;
}
        if (this.t(116121547939723L, this.M) && this.R(F, var1, 113596981294470L, this.M)) {
            double var8 = Math.sqrt(Math.pow((double)this.M.func_177958_n() + 0.5 - var1.field_70165_t, 2.0) + Math.pow((double)this.M.func_177952_p() + 0.5 - var1.field_70161_v, 2.0));
            float var10 = this.f(var1, this.M, this.w(this.m(var1)));
            return var8 > 2.5 && var10 > 40.0f ? true : var8 > 1.5 && var10 > 25.0f && this.K.I();
}
        return true;
}
    private void f(int var1, char var2, short var3) {
        long var4 = ((long)var1 << 32 | (long)var2 << 48 >>> 32 | (long)var3 << 48 >>> 48) ^ ab;
        long var6 = var4 ^ 0x4BCF1AAA05L;
        this.u(var6);
        this.N = false;
}
    public MiningState s() {
        return this.K;
}
    private void A(BlockPos var1, long var2) {
        float var20;
        float var18;
        long var4 = var2 ^ 0x4F3D51639FFL;
        Vec3 var6 = MiningEngine.F.field_71439_g.func_174824_e(1.0f);
        Vec3 var7 = new Vec3((double)var1.func_177958_n() + 0.5, (double)var1.func_177956_o() + 0.5, (double)var1.func_177952_p() + 0.5);
        Vec3 var8 = var7.func_178788_d(var6);
        double var9 = var8.field_72450_a;
        double var11 = var8.field_72448_b;
        double var13 = var8.field_72449_c;
        double var15 = Math.sqrt(var9 * var9 + var13 * var13);
        float var17 = (float)(-Math.toDegrees(Math.atan2(var11, var15)));
        for (var18 = (float)Math.toDegrees(Math.atan2(var13, var9)) - 90.0f; var18 > 180.0f; var18 -= 360.0f) {
}
        while (var18 < -180.0f) {
            var18 += 360.0f;
}
        float var19 = var17 - this.uM;
        for (var20 = var18 - this.v; var20 > 180.0f; var20 -= 360.0f) {
}
        while (var20 < -180.0f) {
            var20 += 360.0f;
}
        this.v += var20 / 4.0f;
        this.uM += var19 / 4.0f;
        this.uM = Math.max(-90.0f, Math.min(90.0f, this.uM));
        this.W(var4);
}
    private float O(float var1) {
        while (var1 > 180.0f) {
            var1 -= 360.0f;
}
        while (var1 < -180.0f) {
            var1 += 360.0f;
}
        return var1;
}
    private boolean S(char var1, long var2) {
        long var4 = ((long)var1 << 48 | var2 << 16 >>> 16) ^ ab;
        int var6 = (int)((var4 ^ 0x2FDCEBD4FCEFL) >>> 32);
        int var7 = (int)((var4 ^ 0x2FDCEBD4FCEFL) << 32 >>> 48);
        int var8 = (int)((var4 ^ 0x2FDCEBD4FCEFL) << 48 >>> 48);
        if (MiningConstants.A && !this.J) {
            if (!this.A) {
                this.A = true;
                this.H = 4;
}
            if (this.H <= 0) {
                return false;
}
            --this.H;
            this.M = null;
            this.o(90.0f, var6, var7, (char)var8);
            return true;
}
        return false;
}
    private BlockPos E(long var1, Minecraft var3, EntityPlayerSP var4) {
        long var5 = var1 ^ 0x376530738E68L;
        if (this.M == null) {
            return null;
}
        Vec3 var7 = var4.func_174824_e(1.0f);
        Vec3 var8 = new Vec3((double)this.M.func_177958_n() + 0.5, (double)this.M.func_177956_o() + 0.5, (double)this.M.func_177952_p() + 0.5);
        Vec3 var9 = var8.func_178788_d(var7);
        double var10 = var9.func_72433_c();
        if (var10 <= 0.001) {
            return null;
}
        Vec3 var12 = new Vec3(var9.field_72450_a / var10, var9.field_72448_b / var10, var9.field_72449_c / var10);
        double var13 = 0.1;
        int var15 = (int)Math.ceil(var10 / var13);
        for (int var16 = 1; var16 < var15 - 1; ++var16) {
            double var17 = var13 * (double)var16;
            Vec3 var19 = new Vec3(var7.field_72450_a + var12.field_72450_a * var17, var7.field_72448_b + var12.field_72448_b * var17, var7.field_72449_c + var12.field_72449_c * var17);
            BlockPos var20 = new BlockPos(var19.field_72450_a, var19.field_72448_b, var19.field_72449_c);
            if (var20.equals((Object)this.M) || !this.T(var3, var5, var4, var20)) continue;
            return var20;
}
        return null;
}
    private BlockPos j(EntityPlayerSP var1) {
        return this.R(var1).func_177977_b();
}
    private String k(long var1) {
        return "player stuck in same position for " + MiningConstants.X + " seconds";
}
    private BlockPos u$r2(EntityPlayerSP var1) {
        BlockPos var2 = this.R(var1);
        return new BlockPos(var2.func_177958_n(), var2.func_177956_o() + 1, var2.func_177952_p());
}
    private boolean G() {
        return MiningConstants.J == 2 || this.Z;
}
    private boolean D() {
        return MiningConstants.J == 3;
}
    private boolean T(Minecraft var1, long var2, EntityPlayerSP var4, BlockPos var5) {
        var2 = ab ^ var2;
        long var6 = var2 ^ 0x6D69A89570F9L;
        long var8 = var2 ^ 0x4A01ACE1AA26L;
        if (var5 != null && var1.field_71441_e != null) {
            for (BlockPos var13 : this.K.M()) {
                if (var13 == null || !var13.equals((Object)var5)) continue;
                return false;
}
            Block var15 = var1.field_71441_e.func_180495_p(var5).func_177230_c();
            if (var15 == Blocks.field_150350_a || BrokenBlockTracker.m.k(var5)) {
                return false;
}
            return this.t(var8, var5) ? false : var15.func_176195_g((World)var1.field_71441_e, var5) >= 0.0f && this.N(var6, var1, var4, var5);
}
        return false;
}
    private boolean z(BlockPos var1) {
        return this.P.contains(var1);
}
    private void T(String var1, long var2) {
        try {
            var2 = ab ^ var2;
            long var4 = var2 ^ 0x6796A506D692L;
            int var6 = (int)((var2 ^ 0x86C7CFAEC32L) >>> 32);
            int var7 = (int)((var2 ^ 0x86C7CFAEC32L) << 32 >>> 48);
            int var8 = (int)((var2 ^ 0x86C7CFAEC32L) << 48 >>> 48);
            int var9 = (int)((var2 ^ 0x3D7238AFE1E3L) >>> 48);
            long var10 = (var2 ^ 0x3D7238AFE1E3L) << 16 >>> 16;
            int var12 = (int)((var2 ^ 0x2E40A394086EL) >>> 32);
            int var13 = (int)((var2 ^ 0x2E40A394086EL) << 32 >>> 48);
            int var14 = (int)((var2 ^ 0x2E40A394086EL) << 48 >>> 48);
            int var15 = (int)((var2 ^ 0x65DA7547E2DCL) >>> 32);
            int var16 = (int)((var2 ^ 0x65DA7547E2DCL) << 32 >>> 48);
            int var17 = (int)((var2 ^ 0x65DA7547E2DCL) << 48 >>> 48);
            long var18 = var2 ^ 0x6875EE0C32D0L;
            long var20 = var2 ^ 0x2C36263EF887L;
            boolean var26 = this.D;
            this.D = false;
            this.C(var15, (char)var16, (char)var17);
            this.M = null;
            this.J = false;
            this.h = Float.NaN;
            RotationManager.O(var20);
            this.j(var4);
            this.U();
            this.o(0L);
            this.f(var12, (char)var13, (short)var14);
            BrokenBlockTracker.m.N(false);
            AutoToolService.K.p(var6, (char)var7, (char)var8);
            this.I.clear();
            this.E.clear();
            this.P.clear();
            this.L = null;
            this.V = false;
            this.r = false;
            this.f = false;
            if (this.n != -1) {
                MiningConstants.w = this.n;
                this.n = -1;
}
            if (this.d != null) {
                MiningConstants.v = this.d;
                this.d = null;
}
            if (var26) {
                if (var1 == null) {
                    ClientUtil.t(var18, "AutoTunnel closed");
                } else {
                    ClientUtil.t(var18, "AutoTunnel closed unexpectedly: " + var1);
                    if (Modules.J(AutoTunnel.class).o()) {
                        Modules.J(AutoTunnel.class).u((short)var9, var10);
}
}
}
}
        catch (Throwable ex) {
            throw Sneaky.rethrow(ex);
}
}
    private void c(long var1) {
        this.C(9749, '\u5e04', '\u2614');
        switch (this.U) {
            case 0: {
                KeyBindUtil.A(82009306480869L, MiningEngine.F.field_71474_y.field_74351_w.func_151463_i(), true);
                break;
}
            case 1: {
                KeyBindUtil.A(82009306480869L, MiningEngine.F.field_71474_y.field_74368_y.func_151463_i(), true);
                break;
}
            case 2: {
                KeyBindUtil.A(82009306480869L, MiningEngine.F.field_71474_y.field_74370_x.func_151463_i(), true);
                break;
}
            case 3: {
                KeyBindUtil.A(82009306480869L, MiningEngine.F.field_71474_y.field_74366_z.func_151463_i(), true);
}
}
}
    private boolean Q(BlockPos var1) {
        Block var2 = MiningEngine.F.field_71441_e.func_180495_p(var1).func_177230_c();
        return var2 != Blocks.field_150486_ae && var2 != Blocks.field_150447_bR ? false : !MiningConstants.k || this.K.g().contains(var1);
}
    private boolean f(int var1, short var2, EntityPlayerSP var3, long var4, char var6) {
        long var7 = ((long)var1 << 32 | (long)var2 << 48 >>> 32 | (long)var6 << 48 >>> 48) ^ ab;
        int var11 = (int)((var7 ^ 0x7A2738334F72L) >>> 32);
        long var12 = (var7 ^ 0x7A2738334F72L) << 32 >>> 32;
        long var14 = var7 ^ 0x403DD4F49A59L;
        long var16 = var7 ^ 0x2D8F3931BE25L;
        long var18 = var7 ^ 0x6ADE9507D6CAL;
        long var20 = var7 ^ 0x9B19F266FCDL;
        long var22 = var7 ^ 0x658CFE5051D9L;
        long var24 = var7 ^ 0x49D4750E89A4L;
        if (this.f() && !this.j) {
            boolean var26;
            boolean bl = var26 = var3.field_70123_F || this.K.I();
            if (!var26) {
                return false;
}
            BlockPos var27 = this.R(var3);
            BlockPos var28 = this.u$r2(var3);
            Block var29 = MiningEngine.F.field_71441_e.func_180495_p(var27).func_177230_c();
            if (var29 == Blocks.field_150350_a) {
                return false;
}
            if (!this.t(var18, var27) && !this.t(var18, var28)) {
                if (this.K(F, var3, var4, this.D(0L), var22)) {
                    return true;
}
                if (!this.u(var11, var12, var27) && !this.u(var11, var12, var28)) {
                    return false;
}
                BlockPos var30 = this.I(var16, var3);
                if (var30 != null) {
                    this.l(var20, var3, var30, var4);
                    this.S = var4;
                    return true;
}
                if (this.c(var3)) {
                    KeyBindUtil.A(var24, MiningEngine.F.field_71474_y.field_74368_y.func_151463_i(), false);
                    KeyBindUtil.A(var24, MiningEngine.F.field_71474_y.field_74311_E.func_151463_i(), false);
                    KeyBindUtil.A(var24, MiningEngine.F.field_71474_y.field_74351_w.func_151463_i(), true);
                    KeyBindUtil.A(var24, MiningEngine.F.field_71474_y.field_74314_A.func_151463_i(), true);
                    this.S = var4;
                    return true;
}
                if (this.m(var28, var14)) {
                    this.l(var20, var3, var28, var4);
                    this.S = var4;
                    return true;
}
                return false;
}
            return false;
}
        return false;
}
    private BlockPos I(long var1, EntityPlayerSP var3) {
        long var4 = var1 ^ 0x6DB2EDC5247CL;
        for (BlockPos var7 : this.u(var3)) {
            if (!this.m(var7, var4)) continue;
            return var7;
}
        return null;
}
    private List<BlockPos> u(EntityPlayerSP var1) {
        int var7;
        ArrayList<BlockPos> var2 = new ArrayList<BlockPos>();
        HashSet<BlockPos> var3 = new HashSet<BlockPos>();
        BlockPos var4 = this.t(var1);
        BlockPos var5 = this.R(var1);
        int var6 = var5.func_177958_n() - var4.func_177958_n();
        int var8 = var7 = var5.func_177952_p() - var4.func_177952_p();
        int var9 = -var6;
        this.W(var2, var3, new BlockPos(var4.func_177958_n(), var4.func_177956_o() + 2, var4.func_177952_p()));
        this.W(var2, var3, new BlockPos(var4.func_177958_n() + var6, var4.func_177956_o() + 2, var4.func_177952_p() + var7));
        this.W(var2, var3, new BlockPos(var4.func_177958_n() + var8, var4.func_177956_o() + 2, var4.func_177952_p() + var9));
        this.W(var2, var3, new BlockPos(var4.func_177958_n() - var8, var4.func_177956_o() + 2, var4.func_177952_p() - var9));
        this.W(var2, var3, this.u$r2(var1));
        this.W(var2, var3, new BlockPos(var5.func_177958_n(), var5.func_177956_o() + 2, var5.func_177952_p()));
        this.W(var2, var3, new BlockPos(var5.func_177958_n() + var8, var5.func_177956_o() + 2, var5.func_177952_p() + var9));
        this.W(var2, var3, new BlockPos(var5.func_177958_n() - var8, var5.func_177956_o() + 2, var5.func_177952_p() - var9));
        return var2;
}
    private boolean b(long var1, long var3) {
        long var5 = var3 ^ 0x2D5C67AD663BL;
        if (this.D() && !this.Z && !this.u && this.O != 0L) {
            if (var1 - this.O < 3000L) {
                return false;
}
            this.Z = true;
            this.u = true;
            this.G = var1 + 2000L;
            this.M = null;
            this.J = false;
            this.j(var5);
            this.u7 = var1;
            return true;
}
        return false;
}
    private List D(long var1) {
        MiningBlockScanner.r(this.K.g(), this.G(), this.k());
        return MiningBlockScanner.W();
}
    private boolean k() {
        return MiningConstants.J == 2;
}
    private void U() {
        this.j = false;
        this.W = false;
        this.X = 0L;
}
    private boolean X(BlockPos var1) {
        Block var2 = MiningEngine.F.field_71441_e.func_180495_p(var1).func_177230_c();
        return var2 != Blocks.field_150350_a && var2.func_176195_g((World)MiningEngine.F.field_71441_e, var1) < 0.0f || BrokenBlockTracker.m.k(var1);
}
    private BlockPos t(EntityPlayerSP var1) {
        return new BlockPos(Math.floor(var1.field_70165_t), Math.floor(var1.field_70163_u), Math.floor(var1.field_70161_v));
}
    private void u$r3(EntityPlayerSP var1) {
        boolean var3;
        this.E.clear();
        this.P.clear();
        this.L = null;
        BlockPos var2 = this.j(var1);
        boolean bl = var3 = MiningEngine.F.field_71441_e.func_180495_p(var2).func_177230_c() == Blocks.field_150350_a;
        if (var3) {
            int var10;
            this.L = var2;
            int var4 = var2.func_177958_n();
            int var5 = var2.func_177956_o() + 2;
            int var6 = var2.func_177952_p();
            double var7 = Math.toRadians(this.m(var1));
            int var9 = (int)Math.round(-Math.sin(var7));
            int var11 = var10 = (int)Math.round(Math.cos(var7));
            int var12 = -var9;
            for (int var13 = 0; var13 < 3; ++var13) {
                int var14 = var4 + var9 * var13;
                int var15 = var6 + var10 * var13;
                for (int var16 = 0; var16 < 2; ++var16) {
                    for (int var17 = -1; var17 <= 1; ++var17) {
                        BlockPos var18 = new BlockPos(var14 + var11 * var17, var5 + var16, var15 + var12 * var17);
                        this.E.add(var18);
                        this.P.add(var18);
}
}
}
}
}
    private static long c(int var0, long var1) {
        int var3 = var0 ^ (int)(var1 & 0x7FFFL) ^ 0x23F2;
        if (ib[var3] == null) {
            byte[] var10;
            byte[] var4 = new byte[]{(byte)(var1 >>> 56), (byte)(var1 >>> 48), (byte)(var1 >>> 40), (byte)(var1 >>> 32), (byte)(var1 >>> 24), (byte)(var1 >>> 16), (byte)(var1 >>> 8), (byte)var1};
            long var5 = hb[var3];
            byte[] var7 = new byte[]{(byte)(var5 >>> 56), (byte)(var5 >>> 48), (byte)(var5 >>> 40), (byte)(var5 >>> 32), (byte)(var5 >>> 24), (byte)(var5 >>> 16), (byte)(var5 >>> 8), (byte)var5};
            Long var8 = Thread.currentThread().getId();
            Object[] var9 = (Object[])jb.get(var8);
            try {
                if (var9 == null) {
                    var9 = new Object[]{Cipher.getInstance("DES/CBC/NoPadding"), SecretKeyFactory.getInstance("DES"), new IvParameterSpec(new byte[8])};
                    jb.put(var8, var9);
}
                DESKeySpec var11 = new DESKeySpec(var4);
                SecretKey var12 = ((SecretKeyFactory)var9[1]).generateSecret(var11);
                Cipher var13 = (Cipher)var9[0];
                var13.init(2, (Key)var12, (IvParameterSpec)var9[2]);
                var10 = var13.doFinal(var7);
}
            catch (Exception var14) {
                throw new RuntimeException("Abyss/internal/MiningEngine", var14);
}
            long var15 = ((long)var10[0] & 0xFFL) << 56 | ((long)var10[1] & 0xFFL) << 48 | ((long)var10[2] & 0xFFL) << 40 | ((long)var10[3] & 0xFFL) << 32 | ((long)var10[4] & 0xFFL) << 24 | ((long)var10[5] & 0xFFL) << 16 | ((long)var10[6] & 0xFFL) << 8 | (long)var10[7] & 0xFFL;
            MiningEngine.ib[var3] = var15;
}
        return ib[var3];
}
    private float w(float var1) {
        if ((var1 %= 360.0f) < 0.0f) {
            var1 += 360.0f;
}
        return var1;
}
    private float f(EntityPlayerSP var1, BlockPos var2, float var3) {
        double var4 = (double)var2.func_177958_n() + 0.5 - var1.field_70165_t;
        double var6 = (double)var2.func_177952_p() + 0.5 - var1.field_70161_v;
        float var8 = (float)Math.toDegrees(Math.atan2(var6, var4)) - 90.0f;
        return Math.abs(this.O(var8 - var3));
}
    private boolean f() {
        return MiningConstants.J == 2 || MiningConstants.J == 3;
}
    public void k(byte var1, int var2) {
        if (this.M != null && this.D) {
            BoxRenderer.I(this.M, new Color(0, 255, 0, 180));
}
}
    public void J() {
        long var5 = System.currentTimeMillis();
        this.c.removeIf(var2 -> var5 - var2.g > 10000L);
        for (MinedBlockTimestamp var8 : this.c) {
            BoxRenderer.I(var8.Y, new Color(255, 0, 0, 180));
}
}
    private void L(long var1, String var3) {
        long var4 = var1 ^ 0x6E7F9D13DF18L;
        this.T(var3, var4);
}
    private boolean B(Block var1) {
        return var1 == Blocks.field_150366_p || var1 == Blocks.field_150365_q;
}
    private void n(long var1) {
        if (MiningEngine.F.field_71439_g != null) {
            float var7;
            for (var7 = this.B - this.v; var7 > 180.0f; var7 -= 360.0f) {
}
            while (var7 < -180.0f) {
                var7 += 360.0f;
}
            float var8 = MiningConstants.C;
            if (Math.abs(var7) < var8) {
                this.v = this.B;
                RotationManager.N(71285564916286L, this.B, this.uM);
                this.v = RotationManager.r;
                this.uM = MathUtil.q(RotationManager.G, -90.0f, 90.0f);
                this.J = false;
                this.M = null;
                this.m = true;
            } else {
                RotationManager.S(this.B, this.uM, var8, 23926534103447L);
                this.v = RotationManager.r;
                this.uM = MathUtil.q(RotationManager.G, -90.0f, 90.0f);
}
}
}
    private BlockPos C(Minecraft var1, EntityPlayerSP var2, int var3, List var4, short var5, float var6, float var7, char var8) {
        long var9 = ((long)var3 << 32 | (long)var5 << 48 >>> 32 | (long)var8 << 48 >>> 48) ^ ab;
        long var11 = var9 ^ 0x49F1FC3CBFA6L;
        long var13 = var9 ^ 0x473D801695ABL;
        BlockPos var15 = null;
        boolean var16 = false;
        float var17 = Float.MAX_VALUE;
        double var18 = Double.MAX_VALUE;
        for (BlockPos var21 : var4) {
            float var22;
            if (!this.t(var13, var21) || !this.R(var1, var2, var11, var21) || (var22 = this.f(var2, var21, var6)) > var7) continue;
            boolean var23 = this.Q(var21);
            double var24 = var21.func_177954_c(var2.field_70165_t, var2.field_70163_u, var2.field_70161_v);
            if (!(var15 == null || var23 && !var16 || var23 == var16 && var22 < var17 - 0.001f) && (var23 != var16 || !(Math.abs(var22 - var17) < 0.001f) || !(var24 < var18))) continue;
            var15 = var21;
            var16 = var23;
            var17 = var22;
            var18 = var24;
}
        return var15;
}
    private Float g(Minecraft var1, EntityPlayerSP var2, List var3, long var4) {
        var4 = ab ^ var4;
        int var6 = (int)((var4 ^ 0x44E270221827L) >>> 32);
        int var7 = (int)((var4 ^ 0x44E270221827L) << 32 >>> 48);
        int var8 = (int)((var4 ^ 0x44E270221827L) << 48 >>> 48);
        float var9 = this.w(this.m(var2));
        float var10 = this.w(var9 + 180.0f);
        float var11 = this.w(var9 - 90.0f);
        float var12 = this.w(var9 + 90.0f);
        BlockPos var13 = this.C(var1, var2, var6, var3, (short)var7, var10, 70.0f, (char)var8);
        if (var13 != null) {
            return Float.valueOf(180.0f);
}
        BlockPos var14 = this.C(var1, var2, var6, var3, (short)var7, var11, 70.0f, (char)var8);
        BlockPos var15 = this.C(var1, var2, var6, var3, (short)var7, var12, 70.0f, (char)var8);
        if (var14 == null && var15 == null) {
            return null;
}
        if (var14 != null && var15 == null) {
            return Float.valueOf(-90.0f);
}
        if (var14 == null) {
            return Float.valueOf(90.0f);
}
        return Float.valueOf(this.D(var2, var14, var15) ? -90.0f : 90.0f);
}
    private boolean i(BlockPos var1, long var2) {
        boolean var15;
        long var4 = var2 ^ 0x650F10A18D2AL;
        for (BlockPos var11 : this.K.M()) {
            if (var11 == null || !var11.equals((Object)var1)) continue;
            return false;
}
        Block var13 = MiningEngine.F.field_71441_e.func_180495_p(var1).func_177230_c();
        if (var13 == Blocks.field_150350_a && var1.equals((Object)this.M)) {
            long var14;
            this.u7 = var14 = System.currentTimeMillis();
            this.O = var14;
            this.o(0L);
            if (MiningConstants.w == 1 && uA.nextFloat() < MiningConstants.Q / 100.0f) {
                this.V = true;
                this.e = var14;
}
            this.M = null;
            if (this.b != null) {
                this.v = this.b.floatValue();
                this.W(var4);
                this.b = null;
}
}
        boolean bl = var15 = var13 != Blocks.field_150350_a && var13.func_176195_g((World)MiningEngine.F.field_71441_e, var1) >= 0.0f && !BrokenBlockTracker.m.k(var1);
        if (var15 && !var1.equals((Object)this.M)) {
            this.u7 = System.currentTimeMillis();
}
        return var15;
}
    private boolean K(Minecraft var1, EntityPlayerSP var2, long var3, List var5, long var6) {
        var6 = ab ^ var6;
        long var8 = var6 ^ 0x73CBE50B2025L;
        int var10 = (int)((var6 ^ 0x17B4534624DDL) >>> 32);
        int var11 = (int)((var6 ^ 0x17B4534624DDL) << 32 >>> 48);
        int var12 = (int)((var6 ^ 0x17B4534624DDL) << 48 >>> 48);
        long var13 = var6 ^ 0x3DD13CE72078L;
        if (MiningConstants.A && !this.J) {
            Float var15 = this.g(var1, var2, var5, var13);
            if (var15 == null) {
                return false;
}
            this.j(var8);
            this.M = null;
            this.k = var3;
            this.o(var15.floatValue(), var10, var11, (char)var12);
            return true;
}
        return false;
}
    private void C(int var1, char var2, char var3) {
        long var4 = ((long)var1 << 32 | (long)var2 << 48 >>> 32 | (long)var3 << 48 >>> 48) ^ ab;
        long var6 = var4 ^ 0x5DDFBE14CC16L;
        KeyBindUtil.A(var6, MiningEngine.F.field_71474_y.field_74351_w.func_151463_i(), false);
        KeyBindUtil.A(var6, MiningEngine.F.field_71474_y.field_74314_A.func_151463_i(), false);
        KeyBindUtil.A(var6, MiningEngine.F.field_71474_y.field_74368_y.func_151463_i(), false);
        KeyBindUtil.A(var6, MiningEngine.F.field_71474_y.field_74370_x.func_151463_i(), false);
        KeyBindUtil.A(var6, MiningEngine.F.field_71474_y.field_74366_z.func_151463_i(), false);
        KeyBindUtil.A(var6, MiningEngine.F.field_71474_y.field_74312_F.func_151463_i(), false);
        KeyBindUtil.A(var6, MiningEngine.F.field_71474_y.field_74311_E.func_151463_i(), false);
}
    private void W(long var1) {
        long var3 = var1 ^ 0x41EA78577FC0L;
        RotationManager.N(var3, this.v, this.uM);
        this.v = RotationManager.r;
        this.uM = MathUtil.q(RotationManager.G, -90.0f, 90.0f);
}
    private boolean D(EntityPlayerSP var1, BlockPos var2, BlockPos var3) {
        double var8;
        boolean var5;
        boolean var4 = this.Q(var2);
        if (var4 != (var5 = this.Q(var3))) {
            return var4;
}
        double var6 = var2.func_177954_c(var1.field_70165_t, var1.field_70163_u, var1.field_70161_v);
        return Math.abs(var6 - (var8 = var3.func_177954_c(var1.field_70165_t, var1.field_70163_u, var1.field_70161_v))) > 0.001 ? var6 < var8 : uA.nextBoolean();
}
    private boolean u(int var1, long var2, BlockPos var4) {
        long var5 = ((long)var1 << 32 | var2 << 32 >>> 32) ^ ab;
        long var7 = var5 ^ 0x21A50AB0B35FL;
        Block var9 = MiningEngine.F.field_71441_e.func_180495_p(var4).func_177230_c();
        return var9 != Blocks.field_150350_a && !this.t(var7, var4);
}
    private void o(long var1) {
        this.H();
        this.u = false;
}
    private BlockPos R(EntityPlayerSP var1) {
        double var2 = Math.toRadians(this.m(var1));
        int var4 = (int)Math.round(-Math.sin(var2));
        int var5 = (int)Math.round(Math.cos(var2));
        int var6 = (int)Math.floor(var1.field_70165_t) + var4;
        int var7 = (int)Math.floor(var1.field_70163_u);
        int var8 = (int)Math.floor(var1.field_70161_v) + var5;
        return new BlockPos(var6, var7, var8);
}
    private BlockPos i(short var1, int var2, int var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = ((long)var1 << 48 | (long)var2 << 32 >>> 16 | (long)var3 << 48 >>> 48) ^ ab;
        long var6 = var4 ^ 0x7D9AFB9199FAL;
        int var10 = (int)((var4 ^ 0x21B1624B22CL) >>> 32);
        int var11 = (int)((var4 ^ 0x21B1624B22CL) << 32 >>> 48);
        int var12 = (int)((var4 ^ 0x21B1624B22CL) << 48 >>> 48);
        if (this.M == null) {
            return null;
}
        Vec3 var13 = MiningEngine.F.field_71439_g.func_174824_e(1.0f);
        Vec3 var14 = new Vec3((double)this.M.func_177958_n() + 0.5, (double)this.M.func_177956_o() + 0.5, (double)this.M.func_177952_p() + 0.5);
        Vec3 var15 = var14.func_178788_d(var13);
        double var16 = var15.func_72433_c();
        Vec3 var18 = new Vec3(var15.field_72450_a / var16, var15.field_72448_b / var16, var15.field_72449_c / var16);
        double var19 = 0.1;
        int var21 = (int)Math.ceil(var16 / var19);
        for (int var22 = 1; var22 < var21 - 1; ++var22) {
            double var23 = var19 * (double)var22;
            Vec3 var25 = new Vec3(var13.field_72450_a + var18.field_72450_a * var23, var13.field_72448_b + var18.field_72448_b * var23, var13.field_72449_c + var18.field_72449_c * var23);
            BlockPos var26 = new BlockPos(var25.field_72450_a, var25.field_72448_b, var25.field_72449_c);
            Block var27 = MiningEngine.F.field_71441_e.func_180495_p(var26).func_177230_c();
            if (var27 == Blocks.field_150350_a) continue;
            if (!var26.equals((Object)this.M) && this.X(var26)) {
                BoxRenderer.I(var26, new Color(0, 0, 255, 180));
                this.X(var10, (char)var11, var12);
                this.M = null;
                return var26;
}
            if (!var26.equals((Object)this.M) && this.S(var6, var26)) {
                return var26;
}
            if (!this.f() || var26.equals((Object)this.M)) continue;
            return var26;
}
        return null;
}
    private boolean G(Minecraft var1, long var2, EntityPlayerSP var4, long var5) {
        var2 = ab ^ var2;
        int var7 = (int)((var2 ^ 0x505EA76EA6FFL) >>> 32);
        int var8 = (int)((var2 ^ 0x505EA76EA6FFL) << 32 >>> 48);
        int var9 = (int)((var2 ^ 0x505EA76EA6FFL) << 48 >>> 48);
        long var10 = var2 ^ 0x357ED44085C4L;
        if (this.f() && this.M != null && !this.R(var1, var4, var10, this.M)) {
            if (!this.n(var7, var1, var4, var8, (char)var9, var5)) {
                return false;
}
            this.v = RotationManager.r;
            this.uM = RotationManager.G;
            this.h = this.w(RotationManager.r);
            return true;
}
        return false;
}
    private void l(long var1, EntityPlayerSP var3, BlockPos var4, long var5) {
        int var14;
        var1 = ab ^ var1;
        long var7 = var1 ^ 0x3E63D1BF086AL;
        long var9 = var1 ^ 0x71394DACCC8EL;
        Block var13 = MiningEngine.F.field_71441_e.func_180495_p(var4).func_177230_c();
        this.M = var4;
        this.A(this.M, var7);
        KeyBindUtil.A(var9, MiningEngine.F.field_71474_y.field_74351_w.func_151463_i(), false);
        KeyBindUtil.A(var9, MiningEngine.F.field_71474_y.field_74368_y.func_151463_i(), false);
        KeyBindUtil.A(var9, MiningEngine.F.field_71474_y.field_74311_E.func_151463_i(), false);
        KeyBindUtil.A(var9, MiningEngine.F.field_71474_y.field_74314_A.func_151463_i(), false);
        if (MiningConstants.r && (var14 = this.U(var3, var13)) != -1 && var14 != var3.field_71071_by.field_70461_c) {
            var3.field_71071_by.field_70461_c = var14;
}
        this.S = var5;
}
    private int U(EntityPlayerSP var3, Block var4) {
        float var5 = 1.0f;
        int var6 = -1;
        for (int var7 = 0; var7 < 9; ++var7) {
            ItemStack var8 = var3.field_71071_by.func_70301_a(var7);
            if (var8 == null) continue;
            float var9 = var8.func_150997_a(var4);
            if (var8.func_77973_b() instanceof ItemTool) {
                if (!(var9 > var5)) continue;
                var5 = var9;
                var6 = var7;
                continue;
}
            if (!(var9 > var5)) continue;
            var6 = var7;
}
        return var6;
}
    private BlockPos W(long var1, Minecraft var3, EntityPlayerSP var4) {
        var1 = ab ^ var1;
        long var5 = var1 ^ 0x4B24371DEED5L;
        long var7 = var1 ^ 0x7C41076E60BDL;
        MiningProgress var9 = this.K.d();
        if (var9 != null && this.T(var3, var7, var4, var9.i)) {
            return var9.i;
}
        BlockPos var10 = this.E(var5, var3, var4);
        if (var10 != null) {
            return var10;
}
        BlockPos var11 = this.K.U();
        if (this.T(var3, var7, var4, var11)) {
            return var11;
}
        for (BlockPos var15 : new BlockPos[]{this.R(var4), this.u$r2(var4)}) {
            if (!this.T(var3, var7, var4, var15)) continue;
            return var15;
}
        for (BlockPos var18 : this.u(var4)) {
            if (!this.T(var3, var7, var4, var18)) continue;
            return var18;
}
        return null;
}
    private boolean C(long var1, EntityPlayerSP var3, long var4) {
        if (this.f() && !this.J) {
            List var15 = this.D(0L);
            BlockPos var16 = this.C(F, var3, 8189, var15, (short)-32753, this.w(this.m(var3)), 70.0f, '\u20c7');
            if (var16 != null && !var16.equals((Object)this.M)) {
                this.M = var16;
                this.A(this.M, 6377414525953L);
                this.S = var4;
                return true;
}
            return this.K(F, var3, var4, var15, 113038385092760L);
}
        return false;
}
    private void H() {
        this.Z = false;
        this.G = 0L;
}
    public void I(long var1) {
        long var3 = 99005023413082L;
        if (MiningConstants.o) {
            if (this.L != null) {
                BoxRenderer.p(this.L, var3, new Color(0, 255, 255, 20));
}
            for (BlockPos var6 : this.E) {
                BoxRenderer.p(var6, var3, new Color(180, 180, 180, 40));
}
}
}
    public void B(long var1, int var3) {
        long var4 = (var1 << 32 | (long)var3 << 32 >>> 32) ^ ab;
        long var6 = var4 ^ 0x14F37E70EF65L;
        this.T(null, var6);
}
    private BlockPos d(byte var1, int var2, int var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = ((long)var1 << 56 | (long)var2 << 32 >>> 8 | (long)var3 << 40 >>> 40) ^ ab;
        long var6 = var4 ^ 0x31A808397307L;
        long var10 = var4 ^ 0x91D97A8506AL;
        int var12 = (int)((var4 ^ 0x32B999E262F7L) >>> 32);
        int var13 = (int)((var4 ^ 0x32B999E262F7L) << 32 >>> 48);
        int var14 = (int)((var4 ^ 0x32B999E262F7L) << 48 >>> 48);
        int var15 = (int)((var4 ^ 0x73E26DB5829AL) >>> 48);
        long var16 = (var4 ^ 0x73E26DB5829AL) << 16 >>> 16;
        long var18 = var4 ^ 0x497407DDF2E4L;
        int var20 = (int)((var4 ^ 0x3DC9B511A039L) >>> 32);
        int var21 = (int)((var4 ^ 0x3DC9B511A039L) << 32 >>> 48);
        int var22 = (int)((var4 ^ 0x3DC9B511A039L) << 48 >>> 48);
        if (this.f()) {
            EntityPlayerSP var55 = MiningEngine.F.field_71439_g;
            List var57 = this.D(0L);
            BlockPos var59 = this.C(F, var55, var12, var57, (short)var13, this.w(this.m(var55)), 70.0f, (char)var14);
            if (var59 != null) {
                this.j(var10);
                return var59;
}
            if (this.S((char)var15, var16)) {
                return null;
}
            this.j(var10);
            this.L(var6, "no directly reachable gapalt targets found in forward, side, or reverse search directions");
            return null;
}
        this.I.clear();
        if (MiningConstants.v && MiningEngine.F.field_71439_g != null && MiningEngine.F.field_71441_e != null) {
            EntityPlayerSP var23 = MiningEngine.F.field_71439_g;
            BlockPos var24 = this.R(var23);
            BlockPos var25 = this.u$r2(var23);
            BlockPos var26 = this.j(var23);
            BlockPos var27 = null;
            if (this.m && this.i(var25, var18)) {
                this.I.add(var25);
                var27 = var25;
}
            if (var27 == null && this.i(var24, var18)) {
                this.I.add(var24);
                var27 = var24;
}
            if (var27 == null && this.i(var26, var18)) {
                this.I.add(var26);
                var27 = var26;
}
            this.u$r3(var23);
            if (!this.I.isEmpty()) {
                BlockPos var28 = this.K.U();
                if (var28 != null && this.I.contains(var28)) {
                    return var28;
}
                this.I.removeIf(this::z);
                return var27;
}
            if (this.m) {
                return null;
}
}
        EntityPlayerSP var54 = MiningEngine.F.field_71439_g;
        float var56 = this.w(this.m(var54));
        double var58 = Math.toRadians(var56);
        double var60 = -Math.sin(var58);
        double var29 = Math.cos(var58);
        for (double var31 = 0.2; var31 < 1.2; var31 += 0.2) {
            int var33 = (int)Math.floor(var54.field_70165_t + var60 * var31);
            int var34 = (int)var54.field_70163_u;
            int var35 = (int)Math.floor(var54.field_70161_v + var29 * var31);
            BlockPos var36 = new BlockPos(var33, var34, var35);
            BlockPos var37 = new BlockPos(var33, var34 + 1, var35);
            if (this.i(var36, var18)) {
                return var36;
}
            if (!this.i(var37, var18)) continue;
            return var37;
}
        for (double var61 = 1.2; var61 <= 2.2; var61 += 1.0) {
            int var68;
            int var66;
            int var64 = (int)Math.floor(var54.field_70165_t + var60 * var61);
            BlockPos var69 = new BlockPos(var64, var66 = (int)var54.field_70163_u, var68 = (int)Math.floor(var54.field_70161_v + var29 * var61));
            if (!this.X(var69)) continue;
            this.c.add(new MinedBlockTimestamp(var69, System.currentTimeMillis()));
            this.X(var20, (char)var21, var22);
            return null;
}
        BlockPos var62 = null;
        for (double var32 = 1.2; var32 <= 4.2; var32 += 1.0) {
            double var67 = var54.field_70165_t + var60 * var32;
            double var70 = var54.field_70161_v + var29 * var32;
            int var38 = (int)Math.floor(var67);
            int var39 = (int)var54.field_70163_u;
            int var40 = (int)Math.floor(var70);
            BlockPos var41 = new BlockPos(var38, var39, var40);
            BlockPos var42 = new BlockPos(var38, var39 + 1, var40);
            if (this.i(var41, var18)) {
                if (var62 == null) {
                    var62 = var41;
}
                this.I.add(var41);
}
            if (this.i(var42, var18)) {
                if (var62 == null) {
                    var62 = var42;
}
                this.I.add(var42);
}
            if (!MiningConstants.q) continue;
            for (double var43 = -0.3; var43 <= 0.3; var43 += 0.3) {
                if (var43 == 0.0) continue;
                double var45 = var67 + var29 * var43;
                double var47 = var70 - var60 * var43;
                int var49 = (int)Math.floor(var45);
                int var50 = (int)var54.field_70163_u;
                int var51 = (int)Math.floor(var47);
                BlockPos var52 = new BlockPos(var49, var50, var51);
                BlockPos var53 = new BlockPos(var49, var50 + 1, var51);
                if (this.i(var52, var18)) {
                    if (var62 == null) {
                        var62 = var52;
}
                    this.I.add(var52);
}
                if (!this.i(var53, var18)) continue;
                if (var62 == null) {
                    var62 = var53;
}
                this.I.add(var53);
}
}
        BlockPos var63 = this.K.U();
        if (var63 != null && this.I.contains(var63)) {
            int var65 = (int)var54.field_70163_u;
            if (var63.func_177956_o() > var65) {
                return var63;
}
}
        if (var62 == null) {
            if (this.l == 0L) {
                this.l = System.currentTimeMillis();
}
            if ((float)(System.currentTimeMillis() - this.l) > MiningConstants.c * 1000.0f) {
                this.L(var6, "no blocks found for too long");
}
        } else {
            this.l = 0L;
}
        return var62;
}
    private boolean N(long var1, Minecraft var3, EntityPlayerSP var4, BlockPos var5) {
        long var6 = var1 ^ 0x74FC72D70ADBL;
        if (var4.func_70092_e((double)var5.func_177958_n() + 0.5, (double)var5.func_177956_o() + 0.5, (double)var5.func_177952_p() + 0.5) > 25.0) {
            return false;
}
        Vec3 var8 = var4.func_174824_e(1.0f);
        for (Vec3 var12 : this.a(var6, var5)) {
            MovingObjectPosition var13 = var3.field_71441_e.func_147447_a(var8, var12, false, true, false);
            if (var13 == null || var13.field_72313_a != MovingObjectPosition.MovingObjectType.BLOCK || !var5.equals((Object)var13.func_178782_a())) continue;
            return true;
}
        return false;
}
    private void j(long var1) {
        this.A = false;
        this.H = 0;
}
    private void o(float var1, int var2, int var3, char var4) {
        long var5 = ((long)var2 << 32 | (long)var3 << 48 >>> 32 | (long)var4 << 48 >>> 48) ^ ab;
        long var7 = var5 ^ 0x3BECD818FCA0L;
        this.J = true;
        this.B = RotationManager.r + var1;
        while (this.B > 180.0f) {
            this.B -= 360.0f;
}
        while (this.B < -180.0f) {
            this.B += 360.0f;
}
        KeyBindUtil.A(var7, MiningEngine.F.field_71474_y.field_74351_w.func_151463_i(), false);
        this.h = this.w(this.B);
}
    @Override
    public final void x(long var1, EventBus var3) {
        MiningEngineBinder.t(var3, this);
}
    private boolean n(int var1, Minecraft var2, EntityPlayerSP var3, int var4, char var5, long var6) {
        long var8 = ((long)var1 << 32 | (long)var4 << 48 >>> 32 | (long)var5 << 48 >>> 48) ^ ab;
        long var10 = var8 ^ 0x6CF0030FE94AL;
        if (this.f() && !this.j && !this.W) {
            BlockPos var12 = this.W(var10, var2, var3);
            if (var12 == null) {
                return false;
}
            this.j = true;
            this.W = true;
            this.X = var6 + 1200L;
            this.M = var12;
            this.u7 = var6;
            return true;
}
        return false;
}
    public void onPreTick(PreTickEvent var1, long var2) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var10001 = 120463243594918L;
        int var8 = (int)(var10001 << 48 >>> 48);
        var10001 = 39350982965954L;
        int var13 = (int)(var10001 << 48 >>> 48);
        var10001 = 2682888230589L;
        int var24 = (int)(var10001 << 48 >>> 48);
        var10001 = 18200678687241L;
        int var29 = (int)(var10001 << 48 >>> 48);
        var10001 = 55106932204454L;
        int var34 = (int)(var10001 << 48 >>> 48);
        var10001 = 67791441774702L;
        int var37 = (int)(var10001 << 32 >>> 32);
        var10001 = 30892599371991L;
        int var52 = (int)(var10001 << 40 >>> 40);
        if (this.D && !this.p) {
            boolean var78;
            BlockPos var64;
            KeyBindUtil.A(82009306480869L, MiningEngine.F.field_71474_y.field_74312_F.func_151463_i(), true);
            this.K.b();
            this.K.T(17790601399577L);
            this.K.v(14218255969322L);
            this.K.C();
            EntityPlayerSP var61 = MiningEngine.F.field_71439_g;
            long var62 = System.currentTimeMillis();
            if (MiningConstants.v && MiningEngine.F.field_71441_e.func_180495_p(var64 = new BlockPos(Math.floor(var61.field_70165_t), Math.floor(var61.field_70163_u) - 1.0, Math.floor(var61.field_70161_v))).func_177230_c() == Blocks.field_150357_h) {
                if (this.d == null) {
                    this.d = Boolean.TRUE;
}
                MiningConstants.v = false;
                if (this.n != -1) {
                    MiningConstants.w = this.n;
                    this.n = -1;
}
}
            if (var78 = this.K.G()) {
                for (BlockPos var68 : this.K.M()) {
                    Block var69 = MiningEngine.F.field_71441_e.func_180495_p(var68).func_177230_c();
                    if (var69 != Blocks.field_150486_ae && var69 != Blocks.field_150447_bR) continue;
                    this.M = var68;
                    if (this.b == null) {
                        this.b = Float.valueOf(RotationManager.r);
}
                    this.A(this.M, 6377414525953L);
                    if (this.q || this.f) break;
                    this.f = true;
                    this.r = true;
                    this.z = System.currentTimeMillis() + 700L;
                    break;
}
}
            this.q = var78;
            double var79 = var61.field_70165_t;
            double var80 = var61.field_70161_v;
            double var81 = Math.sqrt(Math.pow(var79 - this.C, 2.0) + Math.pow(var80 - this.Q, 2.0));
            if (var81 > 1.0) {
                this.C = var79;
                this.Q = var80;
                this.S = var62;
                this.f(28047, '\u88d7', (short)var8);
                this.U();
                this.o(0L);
            } else if (this.w) {
                if (this.z(var62, 50763164016555L)) {
                    return;
}
            } else if (this.j) {
                if (var62 > this.X) {
                    this.U();
                    this.L(31800236978487L, this.k(0L));
                    return;
}
            } else if ((float)(var62 - this.S) > MiningConstants.X * 1000.0f) {
                if (this.a('\u0000', var62, (short)15783, var37)) {
                    return;
}
                if (this.x(var61, 18422345256146L) && this.C(41474706476091L, var61, var62)) {
                    return;
}
                if (this.n(624, F, var61, 43161, (char)var24, var62)) {
                    return;
}
                this.L(31800236978487L, this.k(0L));
                return;
}
            if (this.Z && var62 > this.G) {
                this.H();
}
            if (!this.j && !this.Z && (float)(var62 - this.u7) > MiningConstants.s * 1000.0f && this.u7 != 0L) {
                this.L(31800236978487L, "no block broken for " + (var62 - this.u7) / 1000L + " seconds");
            } else if (this.J) {
                this.n(3902860684954L);
            } else {
                boolean var71;
                KeyBindUtil.A(82009306480869L, MiningEngine.F.field_71474_y.field_74314_A.func_151463_i(), false);
                boolean bl = var71 = MiningConstants.w != 2;
                if (this.V) {
                    if ((float)(System.currentTimeMillis() - this.e) > MiningConstants.e) {
                        this.V = false;
                    } else {
                        var71 = false;
}
}
                if (!this.f(12830, (short)-27362, var61, var62, (char)var34)) {
                    if (MiningConstants.v && this.M == null) {
                        BlockPos var72 = this.R(var61);
                        BlockPos[] var73 = this.j(var61);
                        if (MiningEngine.F.field_71441_e.func_180495_p(var72).func_177230_c() == Blocks.field_150350_a && MiningEngine.F.field_71441_e.func_180495_p((BlockPos)var73).func_177230_c() == Blocks.field_150350_a) {
                            KeyBindUtil.A(82009306480869L, MiningEngine.F.field_71474_y.field_74351_w.func_151463_i(), true);
}
}
                    if (this.M != null && this.z(this.M)) {
                        this.M = null;
}
                    if (this.M != null) {
                        if (this.x(var61, 18422345256146L)) {
                            this.M = null;
                            return;
}
                        BlockPos[] var10000 = new BlockPos[]{this.M.func_177978_c(), this.M.func_177968_d(), this.M.func_177974_f(), this.M.func_177976_e(), this.M.func_177984_a(), this.M.func_177977_b()};
                        for (BlockPos var75 : var10000) {
                            Block var76 = MiningEngine.F.field_71441_e.func_180495_p(var75).func_177230_c();
                            if (var76 != Blocks.field_150486_ae && var76 != Blocks.field_150447_bR || !this.Q(var75)) continue;
                            BoxRenderer.I(var75, new Color(0, 0, 255, 180));
                            this.M = var75;
                            break;
}
                        if (var62 - this.R > 10L) {
                            this.R = var62;
                            BlockPos var83 = this.i((short)0, 600448348, var13);
                            if (var83 != null) {
                                if (this.f() && !this.t(116121547939723L, var83)) {
                                    this.M = null;
                                    return;
}
                                if (MiningConstants.v) {
                                    BlockPos var87 = this.R(var61);
                                    BlockPos var89 = this.j(var61);
                                    if (var83.equals((Object)var87) || var83.equals((Object)var89)) {
                                        this.M = var83;
                                        this.A(this.M, 6377414525953L);
}
                                } else {
                                    this.M = var83;
                                    this.A(this.M, 6377414525953L);
}
}
                            if (this.M == null) {
                                return;
}
}
                        double var84 = Math.sqrt(Math.pow((double)this.M.func_177958_n() + 0.5 - var61.field_70165_t, 2.0) + Math.pow((double)this.M.func_177952_p() + 0.5 - var61.field_70161_v, 2.0));
                        KeyBindUtil.A(82009306480869L, MiningEngine.F.field_71474_y.field_74351_w.func_151463_i(), var84 > 1.5);
                        KeyBindUtil.A(82009306480869L, MiningEngine.F.field_71474_y.field_74368_y.func_151463_i(), false);
                        KeyBindUtil.A(82009306480869L, MiningEngine.F.field_71474_y.field_74311_E.func_151463_i(), var71);
                        if (this.i(this.M, 110157827715284L)) {
                            Block var90;
                            int var91;
                            this.A(this.M, 6377414525953L);
                            if (MiningConstants.r && (var91 = this.U(var61, var90 = MiningEngine.F.field_71441_e.func_180495_p(this.M).func_177230_c())) != -1 && var91 != var61.field_71071_by.field_70461_c) {
                                var61.field_71071_by.field_70461_c = var91;
}
                            if (this.m) {
                                this.m = false;
}
                        } else {
                            this.M = null;
}
                        if (this.b != null) {
                            this.v = this.b.floatValue();
                            this.W(1370338967038L);
}
                    } else {
                        this.M = this.d((byte)0, 1841342, var52);
                        if (this.M != null) {
                            this.A(this.M, 6377414525953L);
                            return;
}
                        if (this.J) {
                            return;
}
                        if (this.X(4237, '\uacfc', var29)) {
                            return;
}
                        if (this.J || !this.D) {
                            return;
}
                        KeyBindUtil.A(82009306480869L, MiningEngine.F.field_71474_y.field_74351_w.func_151463_i(), true);
                        KeyBindUtil.A(82009306480869L, MiningEngine.F.field_71474_y.field_74311_E.func_151463_i(), var71);
}
                    if (!(this.f() || this.J || Float.isNaN(this.h))) {
                        float var85 = this.w(RotationManager.r);
                        float var88 = Math.abs(var85 - this.h);
                        if (var88 > 180.0f) {
                            var88 = 360.0f - var88;
}
                        if (var88 > 45.0f) {
                            this.L(31800236978487L, "unexpected facing direction detected");
                            return;
}
}
                    if (MiningConstants.x && !this.p && this.K.I()) {
                        this.p = true;
                        new Thread(() -> {
                            try {
                                KeyBindUtil.A(82009306480869L, MiningEngine.F.field_71474_y.field_74351_w.func_151463_i(), false);
                                KeyBindUtil.A(82009306480869L, MiningEngine.F.field_71474_y.field_74368_y.func_151463_i(), true);
                                Thread.sleep(700L);
                                KeyBindUtil.A(82009306480869L, MiningEngine.F.field_71474_y.field_74368_y.func_151463_i(), false);
}
                            catch (Exception exception) {
                                // empty catch block
}
                            F.func_152344_a(() -> {
                                long var3x = 129958705539448L;
                                int var7x = 1429416877;
                                this.B(22332L, var7x);
                                this.a(var3x);
                                this.p = false;
                            });
                        }).start();
                    } else if (this.r) {
                        KeyBindUtil.A(82009306480869L, MiningEngine.F.field_71474_y.field_74368_y.func_151463_i(), true);
                        KeyBindUtil.A(82009306480869L, MiningEngine.F.field_71474_y.field_74311_E.func_151463_i(), true);
                        if (System.currentTimeMillis() >= this.z) {
                            KeyBindUtil.A(82009306480869L, MiningEngine.F.field_71474_y.field_74368_y.func_151463_i(), false);
                            KeyBindUtil.A(82009306480869L, MiningEngine.F.field_71474_y.field_74311_E.func_151463_i(), false);
                            this.r = false;
                            this.f = false;
}
}
}
}
}
}
    private boolean x() {
        if (this.j) {
            return false;
}
        if (this.J) {
            return false;
}
        float var1 = Math.abs(this.O(RotationManager.r - this.v));
        float var2 = Math.abs(RotationManager.G - this.uM);
        return var1 > 15.0f || var2 > 15.0f;
}
    private boolean t(long var1, BlockPos var3) {
        long var4 = var1 ^ 0xDACB9AD2F5FL;
        Block var6 = MiningEngine.F.field_71441_e.func_180495_p(var3).func_177230_c();
        boolean var7 = var6 == Blocks.field_150348_b || this.Q(var3) || !MiningConstants.gapAltOnlyStone && this.G() && this.B(var6);
        return var7 ? this.i(var3, var4) : false;
}
    private void u(long var1) {
        var1 = ab ^ var1;
        int var3 = (int)((var1 ^ 0x4BD119C940B7L) >>> 32);
        int var4 = (int)((var1 ^ 0x4BD119C940B7L) << 32 >>> 48);
        int var5 = (int)((var1 ^ 0x4BD119C940B7L) << 48 >>> 48);
        if (this.w) {
            this.C(var3, (char)var4, (char)var5);
}
        this.w = false;
        this.U = 0;
        this.s = 0L;
}
    private boolean R(Minecraft var1, EntityPlayerSP var2, long var3, BlockPos var5) {
        long var6 = var3 ^ 0x29A4785EF0D2L;
        return this.N(var6, var1, var2, var5);
}
                Cipher var22 = Cipher.getInstance("DES/CBC/PKCS5Padding");
            var22.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
            String[] var29 = new String[14];
            int var27 = 0;
            String var26 = "\u00f4\u00ba:\u00c2n\u0004\u00b0\u00eeq\u0098\u009a\u001f\u009e\u001b\u00c0\u0005L\\\u00cd\u00dd\u00ba\u0093\u009b\u00ba\u001ec\u00b9@z\u00d1\u0085\u00a0p\u0088\u00eb[0\u00d9[\u0004\u008eA^VcV8\u0096\u00e0\u00fe\u00e9\u00ed\u00f6\u00cf\u00aa\u00da\\(G,I\u00dcg\u00adX\u00df\n\u00e9y\u00b4\u00e9\u00a1o\u00c9\u00d2\u00e0:<\u0005\u00ccj;\u00ae/+Y\u00bb\u00ec\u0016\u00a2\u00cah\u00dd\u00a0`[\u00f5t\u00d7\u0086\u00b4\u009b\u00e2\u00cbnkw\u0094bP\r\u0086\u009d\u0097\u00cf\u0013\u00c2o \u0010\u009d\u0083b}p\u0015_\u00dd\u0090*E\u0096K\u0087\u0094vvv\u00d8\u00a0{4\u00f3\u0090Q\u00ba\u00ef\u00c1]\u00d4\u000b\u00e6\u00d1\u0018\u0084\u00e6\r>YQ\u008fA5\u00a9\u00a1\f\u00d1?\u00049Sb7\u00ab\u00d5\u009f\u00cb\nPB:\u00c72$\u009a\u00a2\u009c\r\u00c1\u0003E\f\u0016\u00f4c4\u00d21\u00ed\u0001\u001ePp\u0002~\u00c8\u0087\u00a3\u00fb\u007f\u00bcl\u00dc;\u0092bJR\f@ZC\u00d9k\u00b4\u001aC\u0080\u008a\u009c\u00f2\u00b9\u00baL0\u009d\u0080\u00ce\u009bM\u0017\u00c8\\\u0002\u0001,5^\raMK\u00da\u00cfF~\u00b1\u0094\u00f0@d\u00ddE\u0089QA\u0088\u00df\u00e4F\u0089\u00d6>q\u00acHZ\u00e0\u00b0\u00a6k\u0097\u00e4\u008eXR\u00c2\u0016s\u0098\u0017R\n&\nE\t\u00e4\u00aat:\u00a1\u00dd\u00f2G\u0089 [+D\u00f6\u00d4o@\u0088\u00f4\u0000\u00c5\u0082!^\u0002=\u00c78?FIG\u0093\u00e5\u0088\u00e9\u00e4\u007fm\u0083a\u00ce\u001a\u00e1\u00fd\u00c72\\\u0087u\u00a7h\u0090`#u\u00e1\u0092\u00d2\n\u0010rV\u00c1\u00a4ps\u00ba\u00bd\u0018\u00ee\u0084\u00b6\u00acfN\u00141\u001b\u00f1@i{\u008a@\u00d27^\u00a3\u001b\u0083\u00cbr\u00fa\u00e6\u00bf@n\u00d6>)\u00c8\u00a3\u0018\u00b0\u00ba\u00d4.\u00cbE*\u00e7\u00fd\u0014Y\u00a2\u00f2\u0011&V|\u00da\u00c0\u00c3+*M\u00d8\u00f5\u00bbh\u00e2D\u00b3\u00a4\u00e2\t\u001f\u00e0\b7{\u00ac\u00d40R\u009d+\u00c1P4(H\u008c\u00f2\u00bfbM\u00ed!s\u001f\u00fd;s\\S\u0085M((*\u0014\u00c4m\u009e\u00ac\u0088\u00d2\u000f\u0091t\u00a5b\u001f\u0083\\Tv\u0095\u00f5tU\u00b0\u00f0D\\o<M\u00a4\u00caB!p\u000b\u00a1\u00ab\u001a\u00a9\u0092\u00b10\u0083\u00b0\u00ed{\u0087)N8\u00a8\u00c3\u00b8\u00ca\u0004\u00eadE\u0087@X\u009f\u009a\u00dc\u00f1\u008e\u00ablo\u00af\u00f2c$\u00d4\u00aa<\u0006\u00b4\u0082$\u00c7/\u00e1\u00ad\u00ab\u00ea\u00c7\u0006\u00b1\u001e\u00e6\u00d59B\u00c5 \u008e\u0083\u000b\u0084\u0083\u00b4\u00c9\u00f6\u00a2\u00e5#\u0010\u00f4!\u00fd\u00cf+\u00e8s\u00c4IQ:\u001e\u0082\u00a2\u00cc(\u0018\u00f3\u00bb\u00e4\u00c8q\u0086k\u0017MV_\u00b9\u0091\u00a86\u009af\u0001/Sn\u00ec\u00dfq`5\u00c8x\u00ae\u00ac\u00d5c\u00be\u00ff\u00f6\u009a\u00b6\u00f4\u00a0Kx\u0095\u00ec\u00e5\u00d1\u007f\u00d3\u009c\u00b6\u00adK\u00dc\u0092\u0094\u00f4\u008a\u0019\u00dc^\u001bN\u008d,\u000f\u009d\u00a1\u001e9b\u00b0\u0019b4\u0012\u0012\u00b5;\u00cc\u008a\u00c9\u00c4\u0012\u00be\u00d2sM\u0087f\u00f71;k\u00ce\u00b4;\u00b4\u00a6\u0080\u00b3\u0095\u00f3\u00bf\u0016\u0015\u0095\u0002\u0089\u00dfp>0f\u00ad5`]\f7o\u001c\u00ce0\u00e9\u00ac\u00f1\u00aei\u00f3\u00b2\u00f0\r\u00e7\u00bb\u00a4\u0088\u0016,\u0087\u0013\u009c(\u00ff7 \u00b5c}(X\u0000\u00f2&\u00af|\u0012$\u0097\r\u009a\u00e3\u009cK'\u00af\u00c4\u00fb\u00ad\u00d3\u00b9\u00d6";
            int var28 = "\u00f4\u00ba:\u00c2n\u0004\u00b0\u00eeq\u0098\u009a\u001f\u009e\u001b\u00c0\u0005L\\\u00cd\u00dd\u00ba\u0093\u009b\u00ba\u001ec\u00b9@z\u00d1\u0085\u00a0p\u0088\u00eb[0\u00d9[\u0004\u008eA^VcV8\u0096\u00e0\u00fe\u00e9\u00ed\u00f6\u00cf\u00aa\u00da\\(G,I\u00dcg\u00adX\u00df\n\u00e9y\u00b4\u00e9\u00a1o\u00c9\u00d2\u00e0:<\u0005\u00ccj;\u00ae/+Y\u00bb\u00ec\u0016\u00a2\u00cah\u00dd\u00a0`[\u00f5t\u00d7\u0086\u00b4\u009b\u00e2\u00cbnkw\u0094bP\r\u0086\u009d\u0097\u00cf\u0013\u00c2o \u0010\u009d\u0083b}p\u0015_\u00dd\u0090*E\u0096K\u0087\u0094vvv\u00d8\u00a0{4\u00f3\u0090Q\u00ba\u00ef\u00c1]\u00d4\u000b\u00e6\u00d1\u0018\u0084\u00e6\r>YQ\u008fA5\u00a9\u00a1\f\u00d1?\u00049Sb7\u00ab\u00d5\u009f\u00cb\nPB:\u00c72$\u009a\u00a2\u009c\r\u00c1\u0003E\f\u0016\u00f4c4\u00d21\u00ed\u0001\u001ePp\u0002~\u00c8\u0087\u00a3\u00fb\u007f\u00bcl\u00dc;\u0092bJR\f@ZC\u00d9k\u00b4\u001aC\u0080\u008a\u009c\u00f2\u00b9\u00baL0\u009d\u0080\u00ce\u009bM\u0017\u00c8\\\u0002\u0001,5^\raMK\u00da\u00cfF~\u00b1\u0094\u00f0@d\u00ddE\u0089QA\u0088\u00df\u00e4F\u0089\u00d6>q\u00acHZ\u00e0\u00b0\u00a6k\u0097\u00e4\u008eXR\u00c2\u0016s\u0098\u0017R\n&\nE\t\u00e4\u00aat:\u00a1\u00dd\u00f2G\u0089 [+D\u00f6\u00d4o@\u0088\u00f4\u0000\u00c5\u0082!^\u0002=\u00c78?FIG\u0093\u00e5\u0088\u00e9\u00e4\u007fm\u0083a\u00ce\u001a\u00e1\u00fd\u00c72\\\u0087u\u00a7h\u0090`#u\u00e1\u0092\u00d2\n\u0010rV\u00c1\u00a4ps\u00ba\u00bd\u0018\u00ee\u0084\u00b6\u00acfN\u00141\u001b\u00f1@i{\u008a@\u00d27^\u00a3\u001b\u0083\u00cbr\u00fa\u00e6\u00bf@n\u00d6>)\u00c8\u00a3\u0018\u00b0\u00ba\u00d4.\u00cbE*\u00e7\u00fd\u0014Y\u00a2\u00f2\u0011&V|\u00da\u00c0\u00c3+*M\u00d8\u00f5\u00bbh\u00e2D\u00b3\u00a4\u00e2\t\u001f\u00e0\b7{\u00ac\u00d40R\u009d+\u00c1P4(H\u008c\u00f2\u00bfbM\u00ed!s\u001f\u00fd;s\\S\u0085M((*\u0014\u00c4m\u009e\u00ac\u0088\u00d2\u000f\u0091t\u00a5b\u001f\u0083\\Tv\u0095\u00f5tU\u00b0\u00f0D\\o<M\u00a4\u00caB!p\u000b\u00a1\u00ab\u001a\u00a9\u0092\u00b10\u0083\u00b0\u00ed{\u0087)N8\u00a8\u00c3\u00b8\u00ca\u0004\u00eadE\u0087@X\u009f\u009a\u00dc\u00f1\u008e\u00ablo\u00af\u00f2c$\u00d4\u00aa<\u0006\u00b4\u0082$\u00c7/\u00e1\u00ad\u00ab\u00ea\u00c7\u0006\u00b1\u001e\u00e6\u00d59B\u00c5 \u008e\u0083\u000b\u0084\u0083\u00b4\u00c9\u00f6\u00a2\u00e5#\u0010\u00f4!\u00fd\u00cf+\u00e8s\u00c4IQ:\u001e\u0082\u00a2\u00cc(\u0018\u00f3\u00bb\u00e4\u00c8q\u0086k\u0017MV_\u00b9\u0091\u00a86\u009af\u0001/Sn\u00ec\u00dfq`5\u00c8x\u00ae\u00ac\u00d5c\u00be\u00ff\u00f6\u009a\u00b6\u00f4\u00a0Kx\u0095\u00ec\u00e5\u00d1\u007f\u00d3\u009c\u00b6\u00adK\u00dc\u0092\u0094\u00f4\u008a\u0019\u00dc^\u001bN\u008d,\u000f\u009d\u00a1\u001e9b\u00b0\u0019b4\u0012\u0012\u00b5;\u00cc\u008a\u00c9\u00c4\u0012\u00be\u00d2sM\u0087f\u00f71;k\u00ce\u00b4;\u00b4\u00a6\u0080\u00b3\u0095\u00f3\u00bf\u0016\u0015\u0095\u0002\u0089\u00dfp>0f\u00ad5`]\f7o\u001c\u00ce0\u00e9\u00ac\u00f1\u00aei\u00f3\u00b2\u00f0\r\u00e7\u00bb\u00a4\u0088\u0016,\u0087\u0013\u009c(\u00ff7 \u00b5c}(X\u0000\u00f2&\u00af|\u0012$\u0097\r\u009a\u00e3\u009cK'\u00af\u00c4\u00fb\u00ad\u00d3\u00b9\u00d6".length();
            int var25 = 64;
            int var43 = -1;
            block12: while (true) {
                String var44 = var26.substring(++var43, var43 + var25);
                int var50 = -1;
                while (true) {
                    byte[] var30 = var22.doFinal(var44.getBytes("ISO-8859-1"));
                    String var61 = MiningEngine.a(var30).intern();
                    switch (var50) {
                        case 0: {
                            var29[var27++] = var61;
                            if ((var43 += var25) >= var28) {
                                bb = var29;
                                cb = new String[14];
                                gb = new HashMap(13);
                                var10003 = new byte[]{(byte)(var31 >>> 56), 0, 0, 0, 0, 0, 0, 0};
                                for (int var12 = 1; var12 < 8; ++var12) {
                                    var10003[var12] = (byte)(var31 << var12 * 8 >>> 56);
}
                                Cipher var11 = Cipher.getInstance("DES/CBC/NoPadding");
                                var11.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
                                long[] var17 = new long[18];
                                int var14 = 0;
                                String var15 = "\u00d4\u0006\u0090\u0091\u00f8\u00adu\u008d&\u00e7\u00ff\u00bb_\u00c6Yb\u00fdBU\u0006\u001f\u00fc\f}\u00eaC\u000e\u00be\u00dc;Dc\u000b\u009c\u00d0l\u0089\u00bf\u0087\u00e5\u00e0\u0086\u00aa\u001f9\u0012\u00fb\u00f2\u0016I\u00fa\u0006\u001e\"\u0082\u00a1HS\u00a2s\u00cb\u001e3\u00f2\u000b\u009c\u00c6\u00e0c\u0098;\u00f2\u0087M6\u0099\u001a\u00f5W\u00fbo@6(p\u00b5#b\u00b0P\u008a\u00fc(\u0098\u00c8G\u0096\t\u00e3?R=\u0011\u0002l\u0000e\u001d\u00fe2\u00d3\u00e6Vt\u00ba\u00ff\u001b\u0011\u0083\u00e6\u0080\u00aaQ\u00e37\u00a7`\u000b";
                                int var16 = "\u00d4\u0006\u0090\u0091\u00f8\u00adu\u008d&\u00e7\u00ff\u00bb_\u00c6Yb\u00fdBU\u0006\u001f\u00fc\f}\u00eaC\u000e\u00be\u00dc;Dc\u000b\u009c\u00d0l\u0089\u00bf\u0087\u00e5\u00e0\u0086\u00aa\u001f9\u0012\u00fb\u00f2\u0016I\u00fa\u0006\u001e\"\u0082\u00a1HS\u00a2s\u00cb\u001e3\u00f2\u000b\u009c\u00c6\u00e0c\u0098;\u00f2\u0087M6\u0099\u001a\u00f5W\u00fbo@6(p\u00b5#b\u00b0P\u008a\u00fc(\u0098\u00c8G\u0096\t\u00e3?R=\u0011\u0002l\u0000e\u001d\u00fe2\u00d3\u00e6Vt\u00ba\u00ff\u001b\u0011\u0083\u00e6\u0080\u00aaQ\u00e37\u00a7`\u000b".length();
                                int var13 = 0;
                                block15: while (true) {
                                    int var54 = var13;
                                    byte[] var18 = var15.substring(var54, var13 += 8).getBytes("ISO-8859-1");
                                    long[] var47 = var17;
                                    int var55 = var14++;
                                    long var65 = ((long)var18[0] & 0xFFL) << 56 | ((long)var18[1] & 0xFFL) << 48 | ((long)var18[2] & 0xFFL) << 40 | ((long)var18[3] & 0xFFL) << 32 | ((long)var18[4] & 0xFFL) << 24 | ((long)var18[5] & 0xFFL) << 16 | ((long)var18[6] & 0xFFL) << 8 | (long)var18[7] & 0xFFL;
                                    int var71 = -1;
                                    while (true) {
                                        long var19 = var65;
                                        byte[] var21 = var11.doFinal(new byte[]{(byte)(var19 >>> 56), (byte)(var19 >>> 48), (byte)(var19 >>> 40), (byte)(var19 >>> 32), (byte)(var19 >>> 24), (byte)(var19 >>> 16), (byte)(var19 >>> 8), (byte)var19});
                                        long var76 = ((long)var21[0] & 0xFFL) << 56 | ((long)var21[1] & 0xFFL) << 48 | ((long)var21[2] & 0xFFL) << 40 | ((long)var21[3] & 0xFFL) << 32 | ((long)var21[4] & 0xFFL) << 24 | ((long)var21[5] & 0xFFL) << 16 | ((long)var21[6] & 0xFFL) << 8 | (long)var21[7] & 0xFFL;
                                        switch (var71) {
                                            case 0: {
                                                var47[var55] = var76;
                                                if (var13 < var16) break;
                                                eb = var17;
                                                fb = new Integer[18];
                                                jb = new HashMap(13);
                                                var10003 = new byte[]{(byte)(var31 >>> 56), 0, 0, 0, 0, 0, 0, 0};
                                                for (int var1 = 1; var1 < 8; ++var1) {
                                                    var10003[var1] = (byte)(var31 << var1 * 8 >>> 56);
}
                                                Cipher var0 = Cipher.getInstance("DES/CBC/NoPadding");
                                                var0.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
                                                long[] var6 = new long[18];
                                                int var3 = 0;
                                                String var4 = "\u00b3\u00b6<\u008e\u00a2\u00d7\u0089\u0091\u0089s\u00efw\u00e3\u00a4\r\u00c4\u0019%\u00fdiM\u00d3\u00ec\u00da\u0091\u00dd\u00ffa\u00fe\u0087\u00f0\u00fe\u00c3\u00c3e\u00cd\u0082)]\\t\u00c7K\u00bf\u0097vu}O\u00c1\u00dfd\u009d9*\u0016\u00ff&\u008c\u00f4\u001e\u00f0\u001d\u001a\u001c\u00e5A\u00c4&A*\u0002\u00ed\u00aa\u00a8\u001a\u00fbv}\u0092J\u00a7\u00b9\u00f1\u00f6\u00b0[\u0007\u0006H\u0013\u00ea\u00cb\u00bf\u008e_9\u00b3\u00d5\u00e5\u00f0\rs\u0019#2m\u00f2\u00eci\u00a3X3\u008f(/_U\u00ea\u00bc-\u00df\u00d3l\u0081\u0011t\u00e1";
                                                int var5 = "\u00b3\u00b6<\u008e\u00a2\u00d7\u0089\u0091\u0089s\u00efw\u00e3\u00a4\r\u00c4\u0019%\u00fdiM\u00d3\u00ec\u00da\u0091\u00dd\u00ffa\u00fe\u0087\u00f0\u00fe\u00c3\u00c3e\u00cd\u0082)]\\t\u00c7K\u00bf\u0097vu}O\u00c1\u00dfd\u009d9*\u0016\u00ff&\u008c\u00f4\u001e\u00f0\u001d\u001a\u001c\u00e5A\u00c4&A*\u0002\u00ed\u00aa\u00a8\u001a\u00fbv}\u0092J\u00a7\u00b9\u00f1\u00f6\u00b0[\u0007\u0006H\u0013\u00ea\u00cb\u00bf\u008e_9\u00b3\u00d5\u00e5\u00f0\rs\u0019#2m\u00f2\u00eci\u00a3X3\u008f(/_U\u00ea\u00bc-\u00df\u00d3l\u0081\u0011t\u00e1".length();
                                                int var2 = 0;
                                                block18: while (true) {
                                                    int var57 = var2;
                                                    byte[] var7 = var4.substring(var57, var2 += 8).getBytes("ISO-8859-1");
                                                    long[] var49 = var6;
                                                    int var58 = var3++;
                                                    long var68 = ((long)var7[0] & 0xFFL) << 56 | ((long)var7[1] & 0xFFL) << 48 | ((long)var7[2] & 0xFFL) << 40 | ((long)var7[3] & 0xFFL) << 32 | ((long)var7[4] & 0xFFL) << 24 | ((long)var7[5] & 0xFFL) << 16 | ((long)var7[6] & 0xFFL) << 8 | (long)var7[7] & 0xFFL;
                                                    int var74 = -1;
                                                    while (true) {
                                                        long var8 = var68;
                                                        byte[] var10 = var0.doFinal(new byte[]{(byte)(var8 >>> 56), (byte)(var8 >>> 48), (byte)(var8 >>> 40), (byte)(var8 >>> 32), (byte)(var8 >>> 24), (byte)(var8 >>> 16), (byte)(var8 >>> 8), (byte)var8});
                                                        var76 = ((long)var10[0] & 0xFFL) << 56 | ((long)var10[1] & 0xFFL) << 48 | ((long)var10[2] & 0xFFL) << 40 | ((long)var10[3] & 0xFFL) << 32 | ((long)var10[4] & 0xFFL) << 24 | ((long)var10[5] & 0xFFL) << 16 | ((long)var10[6] & 0xFFL) << 8 | (long)var10[7] & 0xFFL;
                                                        switch (var74) {
                                                            case 0: {
                                                                var49[var58] = var76;
                                                                if (var2 < var5) break;
                                                                hb = var6;
                                                                ib = new Long[18];
                                                                i = 10L;
                                                                Y = 3000L;
                                                                g = 1000L;
                                                                o = 1200L;
                                                                t = 10000L;
                                                                T = 300L;
                                                                uq = new MiningEngine(var33, var34, var35);
                                                                return;
}
                                                            default: {
                                                                var49[var58] = var76;
                                                                if (var2 < var5) continue block18;
                                                                var4 = "$\u0083:\u008c\u0093HE\u00ffh t\u0019\u00cfK\u0004\u00ec";
                                                                var5 = "$\u0083:\u008c\u0093HE\u00ffh t\u0019\u00cfK\u0004\u00ec".length();
                                                                var2 = 0;
}
}
                                                        int var59 = var2;
                                                        var7 = var4.substring(var59, var2 += 8).getBytes("ISO-8859-1");
                                                        var49 = var6;
                                                        var58 = var3++;
                                                        var68 = ((long)var7[0] & 0xFFL) << 56 | ((long)var7[1] & 0xFFL) << 48 | ((long)var7[2] & 0xFFL) << 40 | ((long)var7[3] & 0xFFL) << 32 | ((long)var7[4] & 0xFFL) << 24 | ((long)var7[5] & 0xFFL) << 16 | ((long)var7[6] & 0xFFL) << 8 | (long)var7[7] & 0xFFL;
                                                        var74 = 0;
}
                                                    break;
}
}
                                            default: {
                                                var47[var55] = var76;
                                                if (var13 < var16) continue block15;
                                                var15 = "v\u000fj\u0013'\u00d2\u00e5\u00e0\u00ces\u00ffc(\u00b3\u0084\u0013";
                                                var16 = "v\u000fj\u0013'\u00d2\u00e5\u00e0\u00ces\u00ffc(\u00b3\u0084\u0013".length();
                                                var13 = 0;
}
}
                                        int var56 = var13;
                                        var18 = var15.substring(var56, var13 += 8).getBytes("ISO-8859-1");
                                        var47 = var17;
                                        var55 = var14++;
                                        var65 = ((long)var18[0] & 0xFFL) << 56 | ((long)var18[1] & 0xFFL) << 48 | ((long)var18[2] & 0xFFL) << 40 | ((long)var18[3] & 0xFFL) << 32 | ((long)var18[4] & 0xFFL) << 24 | ((long)var18[5] & 0xFFL) << 16 | ((long)var18[6] & 0xFFL) << 8 | (long)var18[7] & 0xFFL;
                                        var71 = 0;
}
                                    break;
}
}
                            var25 = var26.charAt(var43);
                            break;
}
                        default: {
                            var29[var27++] = var61;
                            if ((var43 += var25) < var28) {
                                var25 = var26.charAt(var43);
                                continue block12;
}
                            var26 = "\u00f37\u00f6rb%\u008d\u00b7\u00e4\u00d6iA'\u00d96+,\u00fe^\u0001\u0013\u0084J@\u00cb\\\u008c\u0087\u00d9A\u00d5\u00d4d&8\u0091\u0018\u00cbS\u001a\u0011\u009fj~D\u0089\u00a8A\u0000T\u009f\u00d0N:6\u00d4\u00c3\u00ab\u0017\u0084\f\u00e8\\A\u00c0\t\u00c3>\u00d7\u00fd\u00ffb\u0090\u00ff\u0017\u001b\u00e8/1\u00b0\u00fc\u00abc?\n:\u0006\u00d5N\u0085@\u00cc\u00c0\u00ab\u0095\u00d83<\u001a\u00a2\u0007q2\u0099tM\u00ca\u0011\u00b7l\u00f3G$\u00d6\u0094\u009b\u0016\"\u0011\u00fe\u0006\u00d6\u00c3I\u0000\u00bd\u0015U|\u00e7U`\u00d23 \b\u00d0t-\u0090\u00fc\u00cc\u00ec\u0005(\u00ae)\u0002\u00c2S\u008e\u0013\u0012IVEPLH\u0087-\u000bt\u00d1\u00dc\u0083\u008a\u0002\u00fa\u00b8\u008e\u0010\u00d0\u00b7h\u00d6;\u0080\u008c\u00f0{\u008b\u00d0\u00be\u00f8";
                            var28 = "\u00f37\u00f6rb%\u008d\u00b7\u00e4\u00d6iA'\u00d96+,\u00fe^\u0001\u0013\u0084J@\u00cb\\\u008c\u0087\u00d9A\u00d5\u00d4d&8\u0091\u0018\u00cbS\u001a\u0011\u009fj~D\u0089\u00a8A\u0000T\u009f\u00d0N:6\u00d4\u00c3\u00ab\u0017\u0084\f\u00e8\\A\u00c0\t\u00c3>\u00d7\u00fd\u00ffb\u0090\u00ff\u0017\u001b\u00e8/1\u00b0\u00fc\u00abc?\n:\u0006\u00d5N\u0085@\u00cc\u00c0\u00ab\u0095\u00d83<\u001a\u00a2\u0007q2\u0099tM\u00ca\u0011\u00b7l\u00f3G$\u00d6\u0094\u009b\u0016\"\u0011\u00fe\u0006\u00d6\u00c3I\u0000\u00bd\u0015U|\u00e7U`\u00d23 \b\u00d0t-\u0090\u00fc\u00cc\u00ec\u0005(\u00ae)\u0002\u00c2S\u008e\u0013\u0012IVEPLH\u0087-\u000bt\u00d1\u00dc\u0083\u008a\u0002\u00fa\u00b8\u008e\u0010\u00d0\u00b7h\u00d6;\u0080\u008c\u00f0{\u008b\u00d0\u00be\u00f8".length();
                            var25 = 144;
                            var43 = -1;
}
}
                    var44 = var26.substring(++var43, var43 + var25);
                    var50 = 0;
}
                break;
}
}
        catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException var39) {
            throw new RuntimeException(var39);
}
}
    static {
        ab = 54274017209063L;
        uA = new Random();
        F = MinecraftRef.c((byte)0, 0L);
}
}