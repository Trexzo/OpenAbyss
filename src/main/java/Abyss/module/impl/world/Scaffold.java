/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 *  net.minecraft.potion.Potion
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.MovingObjectPosition
 *  net.minecraft.util.MovingObjectPosition$MovingObjectType
 *  net.minecraft.util.Vec3
 *  net.minecraft.world.WorldSettings$GameType
 */
package Abyss.module.impl.world;

import Abyss.enums.RotationMode;
import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.ScaffoldBinder;
import Abyss.event.events.HeldItemChangeEvent;
import Abyss.event.events.MoveInputEvent;
import Abyss.event.events.PreMouseInputEvent;
import Abyss.event.events.Render2DEvent;
import Abyss.event.events.Render3DEvent;
import Abyss.internal.accessor.EntityLivingBaseStateAccessor;
import Abyss.module.Category;
import Abyss.module.PriorityModule;
import Abyss.module.impl.configuration.Theme;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.ColorSetting;
import Abyss.setting.settings.ModeSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.setting.settings.PercentageSetting;
import Abyss.ui.abyss.AbyssScaffoldCounter;
import Abyss.util.BlockUtil;
import Abyss.util.CombatUtil;
import Abyss.util.ItemUtil;
import Abyss.util.KeyBindUtil;
import Abyss.util.MathUtil;
import Abyss.util.MoveUtil;
import Abyss.util.Pair;
import Abyss.util.PlacementTarget;
import Abyss.util.RaytraceUtil;
import Abyss.util.RotationManager;
import Abyss.util.RotationUtil;
import Abyss.util.TimerUtil;
import Abyss.util.packet.OutgoingPacketState;
import Abyss.util.packet.PacketManager;
import Abyss.util.render.RenderUtil;
import java.awt.Color;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldSettings;

public class Scaffold
extends PriorityModule
implements EventSubscriber {
    public static PercentageSetting keepYJumpForwardChance;
    private boolean e;
    private static boolean dk;
    public static BooleanSetting aimCheck;
    private boolean G;
    public static ColorSetting customColor;
    public static NumberSetting diagonalAirDelay;
    private int F;
    private boolean d2;
    private boolean b;
    public static PercentageSetting rotationSmoothing;
    private boolean dw;
    private final TimerUtil dS;
    private int de;
    private boolean k;
    private boolean N;
    private long s;
    public static ModeSetting espColor;
    private final TimerUtil dW;
    public static BooleanSetting keepYOnRightClick;
    private static ItemStack c;
    private boolean u;
    public static ModeSetting normalModeRotation;
    private static int dO;
    public static BooleanSetting strictAimCheck;
    public static NumberSetting straightAirDelay;
    private boolean dZ;
    private int dl;
    public static NumberSetting straightJumpBlocks;
    private boolean L;
    private static long bb;
    public static NumberSetting offsetRotationOffset;
    private boolean dt;
    public static NumberSetting legitModeUnsneakDelay;
    private boolean m;
    public static NumberSetting diagonalJumpBlocks;
    public static ModeSetting moveFix;
    public static BooleanSetting outlineFadeOut;
    public static BooleanSetting downPlace;
    public static ModeSetting keepYModeRotation;
    public static BooleanSetting itemCounter;
    private boolean H;
    public static BooleanSetting autoItem;
    public static NumberSetting legitModeEdgeOffset;
    private int K;
    private boolean y;
    public static BooleanSetting showTargetOutline;
    private int v;
    private int T;
    private final TimerUtil S;
    public static ModeSetting mode;
    public static BooleanSetting keepYBlinkRotation;
    public static BooleanSetting fakeItem;
    private boolean h;
    private double R;
    private boolean d;
    public static BooleanSetting swing;
    private final List<Pair<BlockPos, Long>> dV;
    public static ModeSetting legitModeRotation;
    public static BooleanSetting dontRenderRotation;
    private boolean g;
    private int x;
    public static BooleanSetting showTargetShade;
    public static NumberSetting angleStep;

    private BlockPos Y$r1() {
        return !this.dV.isEmpty() ? this.dV.get(this.dV.size() - 1).a() : null;
}
    private void U() {
        PlacementTarget var11;
        if (!this.dw) {
            KeyBindUtil.o(99363263780575L, Scaffold.f.field_71474_y.field_74314_A.func_151463_i());
            this.dw = true;
}
        if ((var11 = BlockUtil.x(Scaffold.f.field_71439_g.field_70163_u - 1.0, this.Y$r1(), downPlace.c())) != null) {
            this.v(var11, angleStep.L());
}
        if (var11 != null && ((double)var11.q.func_177956_o() <= Scaffold.f.field_71439_g.field_70163_u - 1.0 || downPlace.c())) {
            this.M(var11);
}
}
    private void i(long var1, MoveInputEvent var3) {
        int var8 = Scaffold.f.field_71439_g.field_70173_aa;
        if (this.T == -1) {
            this.T = var8;
            this.de = this.o(Math.max(0.0, (double)legitModeUnsneakDelay.L() - 50.0));
}
        if (var8 - this.T < this.de) {
            this.t(var3);
        } else {
            var3.x(false);
            this.a();
}
}
    private void D(long var1) {
        PlacementTarget var9 = BlockUtil.x(Scaffold.f.field_71439_g.field_70163_u - 1.0, this.Y$r1(), downPlace.c());
        if (var9 != null && ((double)var9.q.func_177956_o() <= Scaffold.f.field_71439_g.field_70163_u - 1.0 || downPlace.c())) {
            this.v(var9, angleStep.L());
            this.M(var9);
}
}
    private int o(double var1) {
        double var3 = var1 / 50.0;
        int var5 = (int)var3;
        return var5 + (Math.random() < var3 - (double)var5 ? 1 : 0);
}
    private float a(int var1, BlockPos var2, int var3, char var4, EnumFacing var5) {
        long var6 = ((long)var1 << 32 | (long)var3 << 48 >>> 32 | (long)var4 << 48 >>> 48) ^ bb;
        int var8 = (int)((var6 ^ 0x270F3F9284F1L) >>> 48);
        int var9 = (int)((var6 ^ 0x270F3F9284F1L) << 16 >>> 32);
        int var10 = (int)((var6 ^ 0x270F3F9284F1L) << 48 >>> 48);
        return RotationUtil.S((char)var8, var9, (char)var10, var2, var5)[0];
}
    private void O(long var1, MoveInputEvent var3, short var4) {
        long var5 = (0x7123B6AB0000L | (long)var4 << 48 >>> 48) ^ bb;
        long var9 = var5 ^ 0x3968AAB42E2BL;
        if (this.d && !KeyBindUtil.V(Scaffold.f.field_71474_y.field_74311_E.func_151463_i(), var9)) {
            var3.x(false);
}
        this.a();
}
    @Override
    public void Z(long var1) {
        if (this.b) {
            ItemUtil.P(this.dl);
            this.b = false;
}
}
    @Override
    public final void x(long var1, EventBus var3) {
        int var4 = (int)((var1 ^ 0x2C398B515F54L) >>> 56);
        ScaffoldBinder.h(var3, (byte)var4, this);
}
    private void Q(float var1) {
        switch (moveFix.Y()) {
            case "SILENT": {
                RotationManager.n(RotationMode.SILENT);
                break;
}
            case "STRICT": {
                RotationManager.n(RotationMode.STRICT);
                break;
}
            case "NONE": {
                RotationManager.n(RotationMode.NONE);
}
}
        if (this.dW.L(1L, true)) {
            if (dontRenderRotation.c()) {
                RotationManager.w(true);
}
            float[] var11 = this.T(new BlockPos(Scaffold.f.field_71439_g.field_70165_t, Scaffold.f.field_71439_g.field_70163_u - 1.0, Scaffold.f.field_71439_g.field_70161_v), EnumFacing.UP, true, 10744777957284L);
            float var12 = (float)rotationSmoothing.k() / 100.0f;
            RotationManager.v(var11[0], var1, 0L, var12);
            RotationManager.f(var11[1], 39.0f, var12, 0L);
            this.d2 = true;
}
}
    private void v(PlacementTarget var1, float var4) {
        switch (moveFix.Y()) {
            case "SILENT": {
                RotationManager.n(RotationMode.SILENT);
                break;
}
            case "STRICT": {
                RotationManager.n(RotationMode.STRICT);
                break;
}
            case "NONE": {
                RotationManager.n(RotationMode.NONE);
}
}
        if (this.dW.L(1L, true)) {
            if (this.y && mode.R("KEEP_Y") && keepYBlinkRotation.c() && dk && this.u) {
                PacketManager.j();
                PacketManager.M(false);
                dk = false;
}
            if (this.u) {
                var4 = 39.0f;
}
            if (dontRenderRotation.c()) {
                RotationManager.w(true);
}
            float[] var14 = this.J(var1.q, 19296727618367L, var1.Z);
            float var15 = (float)rotationSmoothing.k() / 100.0f;
            RotationManager.v(var14[0], var4, 0L, var15);
            RotationManager.f(var14[1], 39.0f, var15, 0L);
            this.d2 = true;
}
}
    private void H() {
        if (autoItem.c() && !OutgoingPacketState.P && !OutgoingPacketState.h) {
            ItemStack var5;
            if (!this.b) {
                this.dl = Scaffold.f.field_71439_g.field_71071_by.field_70461_c;
                this.b = true;
}
            int var6 = ItemUtil.u(var5 = Scaffold.f.field_71439_g.func_70694_bm()) ? var5.field_77994_a : 0;
            this.v = Math.min(this.v, var6);
            if (this.v <= 0) {
                int var7 = Scaffold.f.field_71439_g.field_71071_by.field_70461_c;
                if (this.v == 0) {
                    --var7;
}
                for (int var8 = var7; var8 > var7 - 9; --var8) {
                    int var9 = (var8 % 9 + 9) % 9;
                    ItemStack var10 = Scaffold.f.field_71439_g.field_71071_by.func_70301_a(var9);
                    if (!ItemUtil.u(var10)) continue;
                    ItemUtil.P(var9);
                    this.v = var10.field_77994_a;
                    break;
}
}
}
}
    private float[] atan2(long var1, String var3, BlockPos var4, EnumFacing var5, boolean var6) {
        long var10001 = 41812496666049L;
        int var9 = (int)(var10001 << 48 >>> 48);
        var10001 = 48056310104396L;
        int var15 = 6279;
        float var20 = this.q(var4, var5, 16973084796600L);
        float[] var21 = new float[]{RotationManager.r, var20};
        float var22 = MoveUtil.X(11188, (short)-1205);
        PlacementTarget var23 = new PlacementTarget(var4, var5, false);
        switch (var3) {
            case "NORMAL": {
                var21[0] = this.a(9735, var4, 15106, (char)var9, var5);
                if (this.isGetBlockPos(var23, var22 - 180.0f, var20)) {
                    var21[0] = var22 - 180.0f;
                    break;
}
                if (this.isGetBlockPos(var23, var22 - 135.0f, var20)) {
                    var21[0] = var22 - 135.0f;
                    break;
}
                if (!this.isGetBlockPos(var23, var22 + 135.0f, var20)) break;
                var21[0] = var22 + 135.0f;
                break;
}
            case "BACK": {
                boolean var38;
                var21[0] = var22 - 180.0f;
                var21[1] = var20;
                if (var6) break;
                boolean bl = var38 = this.isGetBlockPos(var23, var22 - 135.0f, var20) || this.isGetBlockPos(var23, var22 + 135.0f, var20);
                if (!(this.isGetBlockPos(var23, var22 - 180.0f, var20) || !strictAimCheck.c() && var38)) {
                    var21[0] = this.a(9735, var4, 15106, (char)var9, var5);
                    break;
}
                var21[0] = var22 - 180.0f;
                break;
}
            case "OFFSET": {
                Vec3 var37 = RotationUtil.h(var4, var5, offsetRotationOffset.L());
                var21[0] = RotationUtil.L(var37)[0];
                var21[1] = RotationUtil.L(var37)[1];
                if (!strictAimCheck.c() || this.isGetBlockPos(var23, var21[0], var21[1])) break;
                var21[0] = this.a(9735, var4, 15106, (char)var9, var5);
                break;
}
            case "DIAGONAL": {
                float var35;
                var21[1] = this.q(var4, var5, 16973084796600L);
                boolean var26 = this.isGetBlockPos(var23, var22 - 135.0f, var21[1]) || this.isGetBlockPos(var23, var22 + 135.0f, var21[1]);
                boolean var27 = this.isGetBlockPos(var23, var22 - 180.0f, var21[1]);
                if (!(var6 || var26 || var27)) {
                    var21[0] = this.a(9735, var4, 15106, (char)var9, var5);
                    break;
}
                if (Scaffold.H(true, '\u0000', 558459959, var15) && !var6) {
                    if (var27) {
                        var21[0] = var22 - 180.0f;
                        break;
}
                    var21[0] = this.a(9735, var4, 15106, (char)var9, var5);
                    break;
}
                if (!var26 && !var6) {
                    var21[0] = var22 - 180.0f;
                    break;
}
                BlockPos var28 = new BlockPos(Math.floor(Scaffold.f.field_71439_g.field_70165_t), Math.floor(Scaffold.f.field_71439_g.field_70163_u) - 1.0, Math.floor(Scaffold.f.field_71439_g.field_70161_v));
                double var29 = (double)var28.func_177958_n() + 0.5 - Scaffold.f.field_71439_g.field_70165_t;
                double var31 = (double)var28.func_177952_p() + 0.5 - Scaffold.f.field_71439_g.field_70161_v;
                float var33 = (float)(Math.toDegrees(Math.atan2(var31, var29)) - 90.0);
                float var34 = MathHelper.func_76142_g((float)(var33 - var22));
                if (var34 > 0.0f) {
                    var35 = var22 + 135.0f;
                    if (strictAimCheck.c() && !this.isGetBlockPos(var23, var35, var21[1])) {
                        var35 = var22 - 135.0f;
}
                } else {
                    var35 = var22 - 135.0f;
                    if (strictAimCheck.c() && !this.isGetBlockPos(var23, var35, var21[1])) {
                        var35 = var22 + 135.0f;
}
}
                var21[0] = var35;
                this.e = true;
}
}
        return var21;
}
    private void v(long var1, MoveInputEvent var3) {
        if (mode.R("LEGIT") && Scaffold.f.field_71462_r == null && !Scaffold.f.field_71439_g.field_71075_bZ.field_75100_b && ItemUtil.u(Scaffold.f.field_71439_g.func_70694_bm()) && this.Y()) {
            if (KeyBindUtil.V(Scaffold.f.field_71474_y.field_74311_E.func_151463_i(), 64165991731362L)) {
                this.a();
            } else {
                AxisAlignedBB var17 = this.K(20403440901494L);
                double var18 = this.q(var17);
                if (Double.isNaN(var18)) {
                    if (var3.d()) {
                        if (this.d) {
                            this.i(0L, var3);
}
                    } else if (Scaffold.f.field_71439_g.field_70122_E) {
                        this.t(var3);
                    } else if (this.d) {
                        this.i(0L, var3);
}
                } else if (var18 > (double)legitModeEdgeOffset.L()) {
                    this.t(var3);
                } else if (this.d) {
                    this.i(0L, var3);
}
}
        } else {
            this.O(1898165931L, var3, (short)31098);
}
}
    private void a() {
        this.d = false;
        this.T = -1;
        this.de = -1;
}
    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean H(boolean var0, char var1, int var2, int var3) {
        long var4 = ((long)var1 << 48 | (long)var2 << 32 >>> 16 | (long)var3 << 48 >>> 48) ^ bb;
        int var6 = (int)((var4 ^ 0x78ED44479E38L) >>> 32);
        int var7 = (int)((var4 ^ 0x78ED44479E38L) << 32 >>> 48);
        float var9 = MoveUtil.X(var6, (short)var7);
        var9 = MathUtil.T(var9, 0.0f, 360.0f);
        float var10 = var9 % 90.0f;
        int n2 = var0 ? 10 : 20;
        if (!(var10 > (float)n2)) return false;
        int n3 = var0 ? 80 : 70;
        if (!(var10 < (float)n3)) return false;
        return true;
}
    public void onMoveInput(long var1, MoveInputEvent var3) {
        if (this.h) {
            var3.O(true);
            this.h = false;
}
        this.v(46907066512003L, var3);
}
    private void t(MoveInputEvent var1) {
        var1.x(true);
        this.d = true;
        this.T = -1;
}
    public void onRender3D(int var1, short var2, Render3DEvent var3, int var4) {
        int var17;
        long var5 = ((long)var1 << 32 | (long)var2 << 48 >>> 32 | (long)var4 << 48 >>> 48) ^ bb;
        long var7 = var5 ^ 0x231493B8969AL;
        long var9 = var5 ^ 0x20AA5A44401CL;
        long var11 = var5 ^ 0x38537297CC64L;
        long var13 = var5 ^ 0x54FC12E42143L;
        long var15 = var5 ^ 0x4CA29532E25L;
        this.dV.removeIf(var1x -> System.currentTimeMillis() - (Long)var1x.p() > 700L && this.dV.size() > 1);
        switch (espColor.Y()) {
            case "THEME": {
                var17 = Theme.S(0.0, var7);
                break;
}
            case "THEME_CUSTOM": {
                var17 = Theme.X(var11, 0.0);
                break;
}
            default: {
                var17 = customColor.k(var13);
}
}
        if (!this.dV.isEmpty()) {
            if (showTargetShade.c()) {
                RenderUtil.M(this.dV.get(this.dV.size() - 1).a(), var9, var17, 64, showTargetOutline.c(), showTargetShade.c());
}
            if (showTargetOutline.c()) {
                if (outlineFadeOut.c() && this.dV.size() >= 2) {
                    ArrayList<Pair<BlockPos, Long>> var24 = new ArrayList<Pair<BlockPos, Long>>(this.dV);
                    var24.removeIf(var0 -> System.currentTimeMillis() - (Long)var0.p() > 700L);
                    for (Pair pair : var24) {
                        double var21 = (double)System.currentTimeMillis() - (double)((Long)pair.p()).longValue();
                        int var23 = var21 < 250.0 ? 255 : MathUtil.k((int)(0.5666666666666667 * (450.0 - var21)) + 60, 100, 255);
                        RenderUtil.V((BlockPos)pair.a(), var15, var17, var23);
}
                } else {
                    RenderUtil.V(this.dV.get(this.dV.size() - 1).a(), var15, var17, 255);
}
}
}
}
    private float[] J(BlockPos var1, long var2, EnumFacing var4) {
        return this.T(var1, var4, false, 10744777957284L);
}
    private void Y(long var1) {
        if (Scaffold.f.field_71439_g.field_70122_E && this.G) {
            if (!this.N) {
                EntityLivingBaseStateAccessor.x(14848, (EntityLivingBase)Scaffold.f.field_71439_g, 0);
                KeyBindUtil.A(82009306480869L, Scaffold.f.field_71474_y.field_74314_A.func_151463_i(), true);
                this.G = false;
            } else {
                if (keepYBlinkRotation.c() && straightAirDelay.L() >= 1.0f && diagonalAirDelay.L() >= 1.0f) {
                    if (dk) {
                        PacketManager.j();
}
                    PacketManager.M(true);
                    dk = true;
}
                if (MathUtil.Q(keepYJumpForwardChance.k(), 0L)) {
                    RotationManager.N(71285564916286L, MoveUtil.X(11188, (short)-1205), MathUtil.h(60.0f, 70.0f));
                    RotationManager.V = MoveUtil.X(11188, (short)-1205);
                    this.d2 = true;
}
                this.h = true;
                this.y = false;
                this.dS.W();
                this.S.W();
                this.G = false;
}
}
}
    private boolean isGetBlockPos(PlacementTarget var1, float var2, float var3) {
        MovingObjectPosition var4 = BlockUtil.F(new float[]{var2, var3}, 4.0);
        return var4.field_72313_a != MovingObjectPosition.MovingObjectType.BLOCK ? false : BlockUtil.p(var4.func_178782_a(), var1.q) && (!strictAimCheck.c() || var4.field_178784_b == var1.Z);
}
    @Override
    public void A(long var1) {
        long var5 = var1 ^ 0x7DA718BFC5A5L;
        long var7 = var1 ^ 0x3A38010D60CAL;
        long var9 = var1 ^ 0x1F574BE49C60L;
        long var11 = var1 ^ 0xF9FDC7ACA5AL;
        long var13 = var1 ^ 0x6F9AC3548C27L;
        this.e = false;
        this.T(false);
        this.u = false;
        this.v = -1;
        this.y = false;
        this.k = false;
        this.g = false;
        this.h = false;
        this.F = Scaffold.x$r1(var5) ? (int)diagonalJumpBlocks.L() : (int)straightJumpBlocks.L();
        this.dt = false;
        this.K = 0;
        this.N = false;
        this.m = false;
        this.a();
        this.L = true;
        this.G = false;
        this.x = 0;
        this.dV.clear();
        this.s = System.currentTimeMillis() - 600L;
        if (dk) {
            PacketManager.j();
            PacketManager.M(false);
            dk = false;
}
        if (!this.dw) {
            KeyBindUtil.o(var11, Scaffold.f.field_71474_y.field_74314_A.func_151463_i());
            this.dw = true;
}
        KeyBindUtil.o(var11, Scaffold.f.field_71474_y.field_74311_E.func_151463_i());
        if (this.d2) {
            RotationManager.O(var7);
            this.d2 = false;
}
        if (this.H) {
            this.H = false;
            KeyBindUtil.A(var9, Scaffold.f.field_71474_y.field_74313_G.func_151463_i(), KeyBindUtil.V(Scaffold.f.field_71474_y.field_74313_G.func_151463_i(), var13));
}
}
    private float[] T(BlockPos var1, EnumFacing var2, boolean var3, long var4) {
        return this.atan2(54672650222099L, this.d(39148151720929L), var1, var2, var3);
}
    @Override
    public String g(long var1) {
        long var3 = var1 ^ 0x28AAC77F0BF7L;
        return mode.R("KEEP_Y") && keepYOnRightClick.c() && !KeyBindUtil.V(Scaffold.f.field_71474_y.field_74313_G.func_151463_i(), var3) ? "NORMAL" : mode.Y();
}
    private float q(BlockPos var1, EnumFacing var2, long var3) {
        return RotationUtil.S('\u0000', 1931007915, '\ucec3', var1, var2)[1];
}
    private void N() {
        if (this.k && !Scaffold.f.field_71439_g.field_70122_E) {
            this.K = -1;
            this.dt = true;
}
        if (!(keepYOnRightClick.c() && !KeyBindUtil.V(Scaffold.f.field_71474_y.field_74313_G.func_151463_i(), 64165991731362L) || Scaffold.f.field_71439_g.func_70644_a(Potion.field_76430_j))) {
            if (KeyBindUtil.V(Scaffold.f.field_71474_y.field_74314_A.func_151463_i(), 64165991731362L)) {
                this.R = (int)Scaffold.f.field_71439_g.field_70163_u - 1;
                this.L = true;
            } else if (this.L || Scaffold.f.field_71439_g.field_70122_E) {
                this.R = (int)Scaffold.f.field_71439_g.field_70163_u - 1;
                this.L = false;
}
            PlacementTarget var23 = BlockUtil.x(this.R, this.Y$r1(), downPlace.c());
            this.isKeyDown();
            this.Y(68163567833154L);
            if (!this.G && this.S.Q(10L)) {
                if (var23 != null && !this.y) {
                    this.dt = true;
                    this.K = 0;
                    this.y = true;
}
                if (var23 == null || !((double)var23.q.func_177956_o() <= Scaffold.f.field_71439_g.field_70163_u - 1.0) && !downPlace.c()) {
                    if (!(Scaffold.f.field_71474_y.field_74351_w.func_151470_d() || Scaffold.f.field_71474_y.field_74370_x.func_151470_d() || Scaffold.f.field_71474_y.field_74366_z.func_151470_d() || Scaffold.f.field_71474_y.field_74368_y.func_151470_d())) {
                        KeyBindUtil.o(99363263780575L, Scaffold.f.field_71474_y.field_74314_A.func_151463_i());
                    } else if (this.x < this.F) {
                        KeyBindUtil.A(82009306480869L, Scaffold.f.field_71474_y.field_74314_A.func_151463_i(), false);
}
                } else {
                    this.v(var23, MathUtil.h(84.0f, 99.0f));
                    this.u = true;
}
}
            if (var23 != null && ((double)var23.q.func_177956_o() <= Scaffold.f.field_71439_g.field_70163_u - 1.0 || downPlace.c())) {
                float f = this.K;
                float f2 = Scaffold.x$r1(44418900924704L) ? diagonalAirDelay.L() : straightAirDelay.L();
                if (f >= f2 || this.x < this.F) {
                    this.dt = false;
                    this.M(var23);
}
}
        } else {
            this.k = false;
            this.g = false;
            this.R = (int)Scaffold.f.field_71439_g.field_70163_u - 1;
            this.U();
}
}
    public void onHeldItemChange(HeldItemChangeEvent var1, long var2) {
        var1.I(21307, 3074332907L);
}
    private double q(AxisAlignedBB var1) {
        AxisAlignedBB var2 = new AxisAlignedBB(var1.field_72340_a, var1.field_72338_b - 0.01, var1.field_72339_c, var1.field_72336_d, var1.field_72338_b, var1.field_72334_f);
        List var3 = Scaffold.f.field_71441_e.func_72945_a((Entity)Scaffold.f.field_71439_g, var2);
        if (var3.isEmpty()) {
            return Double.NaN;
}
        double var4 = (var1.field_72340_a + var1.field_72336_d) * 0.5;
        double var6 = (var1.field_72339_c + var1.field_72334_f) * 0.5;
        double var8 = Double.MAX_VALUE;
        for (AxisAlignedBB var11 : var3) {
            double var12 = Math.max(var11.field_72340_a, Math.min(var4, var11.field_72336_d));
            double var14 = Math.max(var11.field_72339_c, Math.min(var6, var11.field_72334_f));
            double var16 = Math.abs(var4 - var12);
            double var18 = Math.abs(var6 - var14);
            var8 = Math.min(var8, Math.max(var16, var18));
}
        return var8;
}
    public void onPreMouseInput(long var1, PreMouseInputEvent var3) {
        this.e = false;
        if (!this.Y()) {
            this.T(false);
        } else {
            this.T(true);
            if (this.u && Scaffold.f.field_71439_g.field_70122_E) {
                this.u = false;
}
            if (!this.k && !this.g) {
                this.k = true;
            } else if (this.k) {
                this.g = true;
                this.k = false;
}
            if (this.dt) {
                ++this.K;
}
            this.H();
            if (ItemUtil.u(Scaffold.f.field_71439_g.func_70694_bm()) && Scaffold.f.field_71462_r == null) {
                if (System.currentTimeMillis() - this.s > (long)(CombatUtil.q() + 500)) {
                    this.Q(angleStep.L());
}
                this.H = true;
                this.dw = false;
                switch (mode.Y()) {
                    case "NORMAL": {
                        this.U();
                        break;
}
                    case "LEGIT": {
                        this.D(0L);
                        break;
}
                    case "KEEP_Y": {
                        this.N();
}
}
                var3.T(true);
            } else if (this.d2) {
                RotationManager.O(123115463851087L);
                this.d2 = false;
}
}
}
    private void isKeyDown() {
        int n2 = this.F = Scaffold.x$r1(44418900924704L) ? (int)diagonalJumpBlocks.L() : (int)straightJumpBlocks.L();
        if (!(Scaffold.f.field_71474_y.field_74351_w.func_151470_d() || Scaffold.f.field_71474_y.field_74370_x.func_151470_d() || Scaffold.f.field_71474_y.field_74366_z.func_151470_d() || Scaffold.f.field_71474_y.field_74368_y.func_151470_d())) {
            this.L = true;
            this.N = false;
            this.m = false;
        } else {
            if (!this.m) {
                this.m = true;
                this.N = false;
}
            if (Scaffold.f.field_71439_g.field_70122_E && !this.G && this.x >= this.F) {
                this.L = true;
                this.G = true;
}
}
        if (this.m) {
            this.N = true;
}
}
    public static boolean Z() {
        return dk;
}
    public void onRender2D(long var1, Render2DEvent var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (itemCounter.c()) {
            AbyssScaffoldCounter.render(var3);
            return;
}
}
    public int q() {
        if (Scaffold.f.field_71439_g == null) {
            return -1;
}
        return this.b ? this.dl : Scaffold.f.field_71439_g.field_71071_by.field_70461_c;
}
    public static boolean x$r1(long var0) {
        var0 = bb ^ var0;
        int var2 = (int)((var0 ^ 0x7B3FABC30654L) >>> 48);
        int var3 = (int)((var0 ^ 0x7B3FABC30654L) << 16 >>> 32);
        int var4 = (int)((var0 ^ 0x7B3FABC30654L) << 48 >>> 48);
        return Scaffold.H(false, (char)var2, var3, var4);
}
    public Scaffold(long var1) {
        super((bb ^ var1 ^ 0x178F888F14DCL) >>> 16, (char)((bb ^ var1 ^ 0x178F888F14DCL) << 48 >>> 48));
        this.declare("Scaffold", Category.World, "Bridge automatically for you", new Setting[0]);
        var1 = bb ^ var1;
        this.dW = new TimerUtil();
        this.S = new TimerUtil();
        this.dS = new TimerUtil();
        this.dV = new ArrayList<Pair<BlockPos, Long>>();
        this.e = false;
        this.d2 = false;
        this.L = true;
        this.b = false;
        this.d = false;
        this.T = -1;
        this.de = -1;
        this.G = false;
        this.x = 0;
        this.F = 0;
        this.dZ = false;
        this.dw = false;
        this.N = false;
        this.m = false;
        this.H = false;
        this.y = false;
        this.s = System.currentTimeMillis();
        this.h = false;
        this.K = 0;
        this.dt = false;
        this.k = false;
        this.g = false;
        this.v = 0;
        this.u = false;
}
    private String d(long var1) {
        String var7;
        switch (mode.Y()) {
            case "LEGIT": {
                var7 = legitModeRotation.Y();
                break;
}
            case "KEEP_Y": {
                if (keepYOnRightClick.c() && !KeyBindUtil.V(Scaffold.f.field_71474_y.field_74313_G.func_151463_i(), 64165991731362L)) {
                    var7 = normalModeRotation.Y();
                    break;
}
                if (Scaffold.x$r1(44418900924704L)) {
                    var7 = "NORMAL";
                    break;
}
                var7 = keepYModeRotation.Y();
                break;
}
            default: {
                var7 = normalModeRotation.Y();
}
}
        return var7;
}
    private boolean M(PlacementTarget var1) {
        boolean var8;
        if (!OutgoingPacketState.Y()) {
            return false;
}
        this.s = System.currentTimeMillis();
        float[] var6 = this.J(var1.q, 19296727618367L, var1.Z);
        MovingObjectPosition var7 = RaytraceUtil.M();
        boolean bl = var8 = var7 != null;
        float var9 = var1.o ? 1.0f : (!strictAimCheck.c() && !this.e ? 30.0f : 1.0f);
        boolean var10 = Math.abs(MathUtil.M(RotationManager.r, var6[0])) <= var9;
        boolean var11 = Math.abs(MathUtil.M(RotationManager.G, var6[1])) <= var9;
        boolean var12 = var8 && var7.field_72313_a == MovingObjectPosition.MovingObjectType.BLOCK && BlockUtil.p(var7.func_178782_a(), var1.q);
        boolean var13 = var12 && var7.field_178784_b == var1.Z;
        boolean var14 = this.dZ;
        boolean bl2 = this.dZ = var10 && var11;
        if ((!aimCheck.c() || (var1.o ? var10 && var11 && var14 : (strictAimCheck.c() ? var12 && var13 : var10 && var11 || var12))) && this.v > 0) {
            Vec3 var15 = BlockUtil.f(var1.q, var1.Z);
            if (var12) {
                var15 = var7.field_72307_f;
}
            if (CombatUtil.u(var1.q, var1.Z, var15, swing.c(), !fakeItem.c() || Scaffold.f.field_71439_g.field_71071_by.func_70301_a(this.dl) != null && Scaffold.f.field_71439_g.field_71071_by.func_70301_a(this.dl).func_77973_b() instanceof ItemBlock)) {
                if (Scaffold.f.field_71442_b.func_178889_l() != WorldSettings.GameType.CREATIVE) {
                    --this.v;
}
                this.dV.add(new Pair<BlockPos, Long>(var1.q.func_177972_a(var1.Z), System.currentTimeMillis()));
                this.x = Scaffold.f.field_71439_g.field_70122_E ? ++this.x : 0;
                return true;
}
}
        return false;
}
    private AxisAlignedBB K(long var1) {
        AxisAlignedBB var6 = Scaffold.f.field_71439_g.func_174813_aQ();
        if (MoveUtil.f() == 0 && MoveUtil.K() == 0) {
            return var6.func_72317_d(Scaffold.f.field_71439_g.field_70159_w, 0.0, Scaffold.f.field_71439_g.field_70179_y);
}
        double var7 = Scaffold.f.field_71439_g.func_70051_ag() ? 0.2873 : 0.221;
        float var9 = MoveUtil.X(11188, (short)-1205);
        float var10 = MathHelper.func_76126_a((float)(var9 * (float)Math.PI / 180.0f));
        float var11 = MathHelper.func_76134_b((float)(var9 * (float)Math.PI / 180.0f));
        double var12 = (double)(-var10) * var7;
        double var14 = (double)var11 * var7;
        return var6.func_72317_d(var12, 0.0, var14);
}
    static {
        bb = 125416588937203L;
        dO = new Color(0, 0, 0, 100).getRGB();
        c = new ItemStack(Item.func_150898_a((Block)Blocks.field_180401_cv));
        dk = false;
        keepYJumpForwardChance = new PercentageSetting("Keep-Y-jump-forward-chance", 100);
        rotationSmoothing = new PercentageSetting("Rotation-smoothing", 0);
        customColor = new ColorSetting("Custom-color", "FFFFFF");
        aimCheck = new BooleanSetting("Aim-check", true);
        strictAimCheck = new BooleanSetting("Strict-aim-check", true);
        swing = new BooleanSetting("Swing", true);
        autoItem = new BooleanSetting("Auto-item", true);
        keepYOnRightClick = new BooleanSetting("Keep-Y-on-right-click", false);
        itemCounter = new BooleanSetting("Item-counter", true);
        keepYBlinkRotation = new BooleanSetting("Keep-Y-blink-rotation", false);
        downPlace = new BooleanSetting("Down-place", false);
        dontRenderRotation = new BooleanSetting("Dont-render-rotation", false);
        fakeItem = new BooleanSetting("Fake-item", true);
        showTargetShade = new BooleanSetting("Show-target-shade", false);
        showTargetOutline = new BooleanSetting("Show-target-outline", true);
        outlineFadeOut = new BooleanSetting("Outline-fade-out", true);
        offsetRotationOffset = new NumberSetting("Offset-rotation-offset", 0.15f, 0.0f, 1.0f, 0.01f);
        legitModeEdgeOffset = new NumberSetting("Legit-mode-edge-offset", 0.0f, 0.0f, 0.3f, 0.01f);
        legitModeUnsneakDelay = new NumberSetting("Legit-mode-unsneak-delay", 50.0f, 50.0f, 300.0f, 5.0f);
        straightJumpBlocks = new NumberSetting("Straight-jump-blocks", 0.0f, 0.0f, 3.0f, 1.0f);
        diagonalJumpBlocks = new NumberSetting("Diagonal-jump-blocks", 0.0f, 0.0f, 3.0f, 1.0f);
        straightAirDelay = new NumberSetting("Straight-air-delay", 1.0f, 0.0f, 4.0f, 1.0f);
        diagonalAirDelay = new NumberSetting("Diagonal-air-delay", 1.0f, 0.0f, 4.0f, 1.0f);
        angleStep = new NumberSetting("Angle-step", 90.0f, 1.0f, 180.0f, 1.0f);
        mode = new ModeSetting("Mode", "NORMAL", "LEGIT", "KEEP_Y");
        normalModeRotation = new ModeSetting("Normal-mode-rotation", false, "DIAGONAL", "BACK", "NORMAL", "OFFSET", "DIAGONAL");
        legitModeRotation = new ModeSetting("Legit-mode-rotation", false, "DIAGONAL", "BACK", "NORMAL", "OFFSET", "DIAGONAL");
        keepYModeRotation = new ModeSetting("Keep-Y-mode-rotation", false, "DIAGONAL", "BACK", "NORMAL", "OFFSET", "DIAGONAL");
        moveFix = new ModeSetting("Move-fix", "SILENT", "STRICT", "NONE");
        espColor = new ModeSetting("ESP-Color", "THEME", "THEME_CUSTOM", "CUSTOM");
}
}