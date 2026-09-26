/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.init.Blocks
 *  net.minecraft.network.play.client.C07PacketPlayerDigging
 *  net.minecraft.network.play.client.C07PacketPlayerDigging$Action
 *  net.minecraft.network.play.client.C0APacketAnimation
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.MovingObjectPosition
 *  net.minecraft.util.MovingObjectPosition$MovingObjectType
 *  net.minecraft.util.Vec3
 */
package Abyss.module.impl.world;

import Abyss.enums.RotationMode;
import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.NukerBinder;
import Abyss.event.events.HeldItemChangeEvent;
import Abyss.event.events.PreMouseInputEvent;
import Abyss.event.events.SendPacketEvent;
import Abyss.module.Category;
import Abyss.module.PriorityModule;
import Abyss.module.impl.world.NukerScanAxis;
import Abyss.module.impl.world.NukerScanState;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.ModeSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.util.BlockUtil;
import Abyss.util.CombatUtil;
import Abyss.util.ItemUtil;
import Abyss.util.MathUtil;
import Abyss.util.RaytraceUtil;
import Abyss.util.RotationManager;
import Abyss.util.RotationUtil;
import Abyss.util.TimerUtil;
import Abyss.util.packet.PacketManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class Nuker
extends PriorityModule
implements EventSubscriber {
    private static String[] d;

    private static Map e;

    public static BooleanSetting swing;
    public static NumberSetting range;
    private int U;
    private boolean K;
    private static long[] h;
    private static Map m;
    private boolean g;
    private BlockPos o;
    private static Object[] n;
        private final TimerUtil v;
    private static long b;
    public static BooleanSetting mineDown;
    public static ModeSetting moveFix;
        private static String[] p;
    private NukerScanState C;

    public void onSendPacket(SendPacketEvent var1, long var2, short var4) {
        long var5 = (var2 << 16 | (long)var4 << 48 >>> 48) ^ b;
        int var7 = (int)((var5 ^ 0x6DCE12022443L) >>> 32);
        long var8 = (var5 ^ 0x6DCE12022443L) << 32 >>> 32;
        if (var1.B instanceof C07PacketPlayerDigging && ((C07PacketPlayerDigging)var1.B).getStatus() == C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK) {
            var1.I(var7, var8);
}
}
    @Override
    public void A(long var1) {
        long var3 = var1 ^ 0x207BC6FEE47EL;
        this.resetBlockRemoving(var3);
        this.C = null;
}
    private List<Integer> x(int var1) {
        ArrayList<Integer> var2 = new ArrayList<Integer>();
        var2.add(0);
        for (int var3 = 1; var3 <= var1; ++var3) {
            var2.add(var3);
}
        if (mineDown.c()) {
            var2.add(-1);
}
        return var2;
}
    @Override
    public String g(long var1) {
        return String.valueOf((int)range.L());
}
    public Nuker(int var1, int var2, byte var3) {
        super((((long)var1 << 32 | (long)var2 << 40 >>> 32 | (long)var3 << 56 >>> 56) ^ b ^ 0x391462936126L) >>> 16, (char)((((long)var1 << 32 | (long)var2 << 40 >>> 32 | (long)var3 << 56 >>> 56) ^ b ^ 0x391462936126L) << 48 >>> 48));
        this.declare("Nuker", Category.World, "Mine blocks around you", new Setting[0]);
        long var4 = ((long)var1 << 32 | (long)var2 << 40 >>> 32 | (long)var3 << 56 >>> 56) ^ b;
        this.v = new TimerUtil();
        this.g = false;
        this.K = false;
        this.U = -1;
        this.o = null;
        this.C = null;
}
    private BlockPos e(long var1, NukerScanState var3) {
        long var4 = var1 ^ 0x2AC8CD08E4E0L;
        int var6 = (int)Math.ceil(range.L());
        for (int var7 = 0; var7 <= var6; ++var7) {
            BlockPos var8;
            BlockPos blockPos = var8 = NukerScanState.w(var3) ? new BlockPos(NukerScanState.m(var3) + NukerScanState.Y(var3) * var7, NukerScanState.x(var3), NukerScanState.n(var3)) : new BlockPos(NukerScanState.n(var3), NukerScanState.x(var3), NukerScanState.m(var3) + NukerScanState.Y(var3) * var7);
            if (!this.isBlockLoaded(var8, var4)) continue;
            return var8;
}
        return null;
}
    @Override
    public final void x(long var1, EventBus var3) {
        NukerBinder.p(var3, this);
}
    public void onPreMouseInput(PreMouseInputEvent var1, int var2, byte var3, int var4) {
        long var5 = ((long)var2 << 32 | (long)var3 << 56 >>> 32 | (long)var4 << 40 >>> 40) ^ b;
        long var10001 = var5 ^ 0x6108FFCDC129L;
        int var7 = (int)((var5 ^ 0x6108FFCDC129L) >>> 32);
        int var8 = (int)((var5 ^ 0x6108FFCDC129L) << 32 >>> 48);
        int var10 = (int)((var5 ^ 0x21AEFA084D0AL) >>> 32);
        long var12 = var5 ^ 0x32E9D4F3D51CL;
        long var14 = var5 ^ 0x625AA388ABA3L;
        int var20 = (int)((var5 ^ 0x1A069A15E78EL) >>> 48);
        int var21 = (int)((var5 ^ 0x1A069A15E78EL) << 16 >>> 32);
        int var22 = (int)((var5 ^ 0x1A069A15E78EL) << 48 >>> 48);
        if (!this.Y()) {
            this.resetBlockRemoving(var12);
            this.o = null;
            this.C = null;
        } else {
            var1.q(var7, var8);
            BlockPos var23 = this.X((char)var20, var21, (short)var22);
            if (var23 == null) {
                this.resetBlockRemoving(var12);
                this.o = null;
                this.C = null;
            } else {
                this.T(true);
                this.o = var23;
                this.F(BlockUtil.a(var23));
                EnumFacing var24 = this.c(var23, 0L);
                if (var24 != null && this.l(var23, var14, var24)) {
                    if (swing.c()) {
                        Nuker.f.thePlayer.swingItem();
                    } else {
                        PacketManager.b(new C0APacketAnimation());
}
                    CombatUtil.G(var10, var23, var24);
}
}
}
}
    private NukerScanAxis f$r1() {
        float var1 = MathUtil.T(RotationManager.r, 0.0f, 360.0f);
        if (var1 >= 45.0f && var1 < 135.0f) {
            return new NukerScanAxis(-1, 0, 0, -1, null);
}
        if (var1 >= 135.0f && var1 < 225.0f) {
            return new NukerScanAxis(0, -1, -1, 0, null);
}
        return var1 >= 225.0f && var1 < 315.0f ? new NukerScanAxis(1, 0, 0, 1, null) : new NukerScanAxis(0, 1, 1, 0, null);
}
    public void onHeldItemChange(int var1, int var2, char var3, HeldItemChangeEvent var4) {
        long var5 = ((long)var1 << 32 | (long)var2 << 48 >>> 32 | (long)var3 << 48 >>> 48) ^ b;
        int var7 = (int)((var5 ^ 0x7EDDE76D9E71L) >>> 32);
        long var8 = (var5 ^ 0x7EDDE76D9E71L) << 32 >>> 32;
        var4.I(var7, var8);
}
    private void F(Block var3) {
        int var8 = ItemUtil.e(0L, var3);
        if (var8 != -1) {
            if (!this.K) {
                this.U = Nuker.f.thePlayer.inventory.currentItem;
}
            ItemUtil.P(var8);
            this.K = true;
}
}
    private NukerScanState Z(NukerScanAxis var1, int var2, int var3, int var4, int var5) {
        if (NukerScanAxis.T(var1) != 0) {
            int var7 = var4 + NukerScanAxis.y(var1) * var5;
            return new NukerScanState(true, var7, var3, var2, NukerScanAxis.T(var1), null);
}
        int var6 = var2 + NukerScanAxis.a(var1) * var5;
        return new NukerScanState(false, var6, var3, var4, NukerScanAxis.Y(var1), null);
}
    private BlockPos X(char var1, int var2, short var3) {
        BlockPos var12;
        long var4 = ((long)var1 << 48 | (long)var2 << 32 >>> 16 | (long)var3 << 48 >>> 48) ^ b;
        long var6 = var4 ^ 0x7D6CA659B98FL;
        long var8 = var4 ^ 0x57A46B515D6FL;
        long var10 = var4 ^ 0x27A385253923L;
        if (this.isBlockLoaded(this.o, var8)) {
            return this.o;
}
        if (this.C != null && (var12 = this.e(var6, this.C)) != null) {
            return var12;
}
        this.C = this.B(var10);
        return this.C == null ? null : this.e(var6, this.C);
}
    private boolean isBlockLoaded(BlockPos var1, long var2) {
        long var4 = var2 ^ 0x5044BACBE782L;
        if (var1 != null && Nuker.f.theWorld != null && Nuker.f.theWorld.isBlockLoaded(var1)) {
            Block var8 = BlockUtil.a(var1);
            if (var8 != null && var8 != Blocks.air && !BlockUtil.a$r1(var1)) {
                return !RaytraceUtil.Y(var1, (double)range.L() + 0.75, var4) ? false : this.c(var1, 0L) != null;
}
            return false;
}
        return false;
}
    private List b(char var1, int var2, int var3) {
        long var4 = ((long)var1 << 48 | (long)var2 << 32 >>> 16 | (long)var3 << 48 >>> 48) ^ b;
        long var6 = var4 ^ 0xF772B1E220AL;
        ArrayList<NukerScanState> var8 = new ArrayList<NukerScanState>();
        int var9 = (int)Math.ceil(range.L());
        NukerScanAxis var10 = this.f$r1();
        List<Integer> var11 = this.x(var9);
        int var12 = (int)Math.floor(Nuker.f.thePlayer.posX);
        int var13 = (int)Math.floor(Nuker.f.thePlayer.posY);
        int var14 = (int)Math.floor(Nuker.f.thePlayer.posZ);
        Iterator iterator = var11.iterator();
        while (iterator.hasNext()) {
            int var16 = (Integer)iterator.next();
            int var17 = var13 + var16;
            for (int var18 = -var9; var18 <= var9; ++var18) {
                boolean var19 = false;
                for (int var20 = 0; var20 <= var9; ++var20) {
                    BlockPos var21 = this.Y(var10, var12, var17, var14, var20, var18);
                    if (!this.isBlockLoaded(var21, var6)) continue;
                    var19 = true;
                    break;
}
                if (!var19) continue;
                var8.add(this.Z(var10, var12, var17, var14, var18));
}
}
        return var8;
}
    private boolean l(BlockPos var1, long var2, EnumFacing var4) {
        var2 = b ^ var2;
        int var7 = (int)((var2 ^ 0x6049B1F9BD3FL) >>> 48);
        int var8 = (int)((var2 ^ 0x6049B1F9BD3FL) << 16 >>> 32);
        int var9 = (int)((var2 ^ 0x6049B1F9BD3FL) << 48 >>> 48);
        long var10 = var2 ^ 0x2294DC1BF2CL;
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
        if (!this.v.L(1L, true)) {
            return false;
}
        float[] var15 = RotationUtil.S((char)var7, var8, (char)var9, var1, var4);
        RotationManager.v(var15[0], 60.0f, 0L, 1.0f);
        RotationManager.A(var10, var15[1]);
        this.g = true;
        MovingObjectPosition var16 = RaytraceUtil.f(RotationManager.r, RotationManager.G, Nuker.f.playerController.getBlockReachDistance(), 1.0f);
        return var16 != null && var16.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && var1.equals((Object)var16.getBlockPos()) || Math.abs(MathUtil.M(RotationManager.r, var15[0])) <= 20.0f;
}
    private EnumFacing c(BlockPos var1, long var2) {
        EnumFacing[] var4;
        EnumFacing[] var10000 = new EnumFacing[]{EnumFacing.UP, EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.EAST, EnumFacing.WEST, EnumFacing.DOWN};
        for (EnumFacing var8 : var4 = var10000) {
            BlockPos var9 = var1.offset(var8);
            if (!Nuker.f.theWorld.isBlockLoaded(var9) || !BlockUtil.a$r1(var9)) continue;
            Vec3 var10 = BlockUtil.f(var1, var8);
            MovingObjectPosition var11 = RaytraceUtil.H(var10);
            if (var11 == null) {
                return var8;
}
            if (var11.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK || !var1.equals((Object)var11.getBlockPos())) continue;
            return var8;
}
        return null;
}
    private NukerScanState B(long var1) {
        int var3 = (int)(((var1 = b ^ var1) ^ 0x7F70C56A4646L) >>> 48);
        int var4 = (int)((var1 ^ 0x7F70C56A4646L) << 16 >>> 32);
        int var5 = (int)((var1 ^ 0x7F70C56A4646L) << 48 >>> 48);
        List var6 = this.b((char)var3, var4, var5);
        return var6.isEmpty() ? null : (NukerScanState)var6.get(0);
}
    private void resetBlockRemoving(long var1) {
        long var5 = var1 ^ 0x1A43C7F384B4L;
        this.T(false);
        if (this.g) {
            RotationManager.O(var5);
            Nuker.f.playerController.resetBlockRemoving();
            this.g = false;
}
        if (this.K) {
            if (this.U != -1) {
                ItemUtil.P(this.U);
}
            this.U = -1;
            this.K = false;
}
        this.o = null;
}
    private BlockPos Y(NukerScanAxis var1, int var2, int var3, int var4, int var5, int var6) {
        int var7 = var2 + NukerScanAxis.T(var1) * var5 + NukerScanAxis.a(var1) * var6;
        int var8 = var4 + NukerScanAxis.Y(var1) * var5 + NukerScanAxis.y(var1) * var6;
        return new BlockPos(var7, var3, var8);
}
    private BlockPos f(NukerScanAxis var1, int var2, int var3, int var4) {
        double var5 = Nuker.f.thePlayer.posX;
        double var7 = Nuker.f.thePlayer.posY;
        double var9 = Nuker.f.thePlayer.posZ;
        int var11 = (int)Math.floor(var5) + NukerScanAxis.T(var1) * var2 + NukerScanAxis.a(var1) * var3;
        int var12 = (int)Math.floor(var7) + var4;
        int var13 = (int)Math.floor(var9) + NukerScanAxis.Y(var1) * var2 + NukerScanAxis.y(var1) * var3;
        return new BlockPos(var11, var12, var13);
}
    static {
        b = 59755270223288L;
        n = new Object[23];
        p = new String[23];
        e = new HashMap(13);
        d = new String[3];
        m = new HashMap(13);
        h = new long[]{-5908676961446516622L, 5372785210800559796L, 6375693055559332728L, -1275294834765481263L, 2914767951935686523L, -8291187651642895498L, -4312771241707091288L};
        mineDown = new BooleanSetting("Mine-down", true);
        swing = new BooleanSetting("Swing", true);
        range = new NumberSetting("Range", 4.0f, 1.0f, 10.0f, 1.0f);
        moveFix = new ModeSetting("Move-fix", "SILENT", "STRICT", "NONE");
}
}