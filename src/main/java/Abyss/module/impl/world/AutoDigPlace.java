/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.network.play.client.C07PacketPlayerDigging
 *  net.minecraft.network.play.client.C07PacketPlayerDigging$Action
 *  net.minecraft.network.play.client.C0APacketAnimation
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.EnumFacing
 */
package Abyss.module.impl.world;

import Abyss.enums.RotationMode;
import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.AutoDigPlaceBinder;
import Abyss.event.events.AttackEntityEvent;
import Abyss.event.events.HeldItemChangeEvent;
import Abyss.event.events.PreMouseInputEvent;
import Abyss.event.events.SendPacketEvent;
import Abyss.internal.accessor.EntityLivingBaseStateAccessor;
import Abyss.module.Category;
import Abyss.module.PriorityModule;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.util.BlockUtil;
import Abyss.util.CombatUtil;
import Abyss.util.ItemUtil;
import Abyss.util.KeyBindUtil;
import Abyss.util.PlacementTarget;
import Abyss.util.RotationManager;
import Abyss.util.packet.OutgoingPacketState;
import Abyss.util.packet.PacketManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class AutoDigPlace
extends PriorityModule
implements EventSubscriber {
    private boolean O;
    private int K;
    private boolean t;
    private BlockPos R;
    private boolean E;
    public static BooleanSetting rightClickDigDown;
    public static BooleanSetting swing;
    private boolean a;
    private boolean N;
    private BlockPos C;
    private boolean n;
    private static long b;

    private boolean L() {
        if (this.R == null) {
            return false;
}
        if (!this.O) {
            return false;
}
        if (!BlockUtil.a$r1(this.R)) {
            this.O = false;
            this.R = null;
            this.C = null;
            return true;
}
        if (this.n && !AutoDigPlace.f.field_71439_g.field_70122_E) {
            KeyBindUtil.A(82009306480869L, AutoDigPlace.f.field_71474_y.field_74314_A.func_151463_i(), false);
            return false;
}
        if (!this.N) {
            this.K = AutoDigPlace.f.field_71439_g.field_71071_by.field_70461_c;
            this.N = true;
}
        boolean var11 = false;
        for (int var12 = 0; var12 < InventoryPlayer.func_70451_h(); ++var12) {
            if (AutoDigPlace.f.field_71439_g.field_71071_by.field_70462_a[var12] == null || !ItemUtil.u(AutoDigPlace.f.field_71439_g.field_71071_by.field_70462_a[var12]) || AutoDigPlace.f.field_71439_g.field_71071_by.field_70462_a[var12].field_77994_a <= 0 || var11) continue;
            ItemUtil.P(var12);
            var11 = true;
}
        if (!var11) {
            return false;
}
        RotationManager.N(71285564916286L, RotationManager.r, this.n ? -90.0f : 90.0f);
        this.a = true;
        if (AutoDigPlace.f.field_71439_g.func_71039_bw()) {
            return true;
}
        PlacementTarget var14 = this.q(this.R);
        return var14 == null ? false : CombatUtil.u(var14.q, var14.Z, BlockUtil.f(var14.q, var14.Z), swing.c(), false);
}
    public void onHeldItemChange(HeldItemChangeEvent var1, int var2, int var3, int var4) {
        long var5 = ((long)var2 << 32 | (long)var3 << 48 >>> 32 | (long)var4 << 48 >>> 48) ^ b;
        int var7 = (int)((var5 ^ 0x58CBD362B882L) >>> 32);
        long var8 = (var5 ^ 0x58CBD362B882L) << 32 >>> 32;
        var1.I(var7, var8);
}
    public void onPreMouseInput(PreMouseInputEvent var1, long var2) {
        if (!this.Y()) {
            this.T(false);
        } else {
            this.T(true);
            RotationManager.n(RotationMode.SILENT);
            var1.q(9819, 57776);
            boolean bl = this.n = rightClickDigDown.c() && KeyBindUtil.V(AutoDigPlace.f.field_71474_y.field_74313_G.func_151463_i(), 64165991731362L);
            if (!this.n) {
                EntityLivingBaseStateAccessor.x(14848, (EntityLivingBase)AutoDigPlace.f.field_71439_g, 0);
                KeyBindUtil.A(82009306480869L, AutoDigPlace.f.field_71474_y.field_74314_A.func_151463_i(), true);
                this.t = true;
            } else {
                KeyBindUtil.A(82009306480869L, AutoDigPlace.f.field_71474_y.field_74314_A.func_151463_i(), false);
}
            if (this.C == null) {
                if (this.L()) {
                    this.E = false;
                    return;
}
                BlockPos var22 = null;
                double var23 = AutoDigPlace.f.field_71439_g.field_70165_t;
                double var25 = AutoDigPlace.f.field_71439_g.field_70163_u;
                double var27 = AutoDigPlace.f.field_71439_g.field_70161_v;
                BlockPos var29 = BlockUtil.Z();
                BlockPos var30 = var29.func_177982_a(0, 2, 0);
                if (this.n && this.E(var30, 0L)) {
                    this.C = null;
                    this.R = var30;
                    this.O = true;
                    this.E = false;
                    this.L();
                    return;
}
                if (this.n) {
                    if (BlockUtil.l(var23, var25 - 1.0, var27) != null) {
                        var22 = new BlockPos(var23, var25 - 1.0, var27);
                    } else if (BlockUtil.l(var23, var25 - 2.0, var27) != null) {
                        var22 = new BlockPos(var23, var25 - 2.0, var27);
}
                } else if (BlockUtil.l(var23, var25 + 2.0, var27) != null) {
                    var22 = new BlockPos(var23, var25 + 2.0, var27);
                } else if (BlockUtil.l(var23, var25 + 3.0, var27) != null) {
                    var22 = new BlockPos(var23, var25 + 3.0, var27);
}
                if (var22 != null) {
                    if (AutoDigPlace.f.field_71441_e.func_175623_d(var22)) {
                        this.C = null;
                        this.R = this.n ? var22 : BlockUtil.Z();
                        this.O = true;
                        this.E = false;
                        return;
}
                    RotationManager.N(71285564916286L, RotationManager.r, this.n ? 90.0f : -90.0f);
                    this.a = true;
                    this.C = var22;
                    this.getKeyCode(0L);
}
            } else {
                if (AutoDigPlace.f.field_71441_e.func_175623_d(this.C)) {
                    BlockPos var32 = this.C;
                    this.C = null;
                    this.R = this.n ? var32 : BlockUtil.Z();
                    this.O = true;
                    this.L();
                    this.E = false;
                    return;
}
                this.getKeyCode(0L);
}
}
}
    public AutoDigPlace(int var1, char var2, int var3) {
        super((((long)var1 << 32 | (long)var2 << 48 >>> 32 | (long)var3 << 48 >>> 48) ^ b ^ 0xFE43B3811C8L) >>> 16, (char)((((long)var1 << 32 | (long)var2 << 48 >>> 32 | (long)var3 << 48 >>> 48) ^ b ^ 0xFE43B3811C8L) << 48 >>> 48));
        this.declare("AutoDigPlace", Category.World, "Auto dig and place blocks beneath or above", new Setting[0]);
        this.C = null;
        this.O = false;
        this.R = null;
        this.a = false;
        this.N = false;
        this.t = false;
        this.E = false;
        this.n = false;
}
    private void getKeyCode(long var1) {
        if (this.C != null) {
            this.E = true;
            KeyBindUtil.A(82009306480869L, AutoDigPlace.f.field_71474_y.field_74314_A.func_151463_i(), false);
            int var13 = ItemUtil.e(0L, BlockUtil.a(this.C));
            ItemUtil.P(var13 == -1 ? AutoDigPlace.f.field_71439_g.field_71071_by.field_70461_c : var13);
            if (!OutgoingPacketState.P || !OutgoingPacketState.h) {
                EnumFacing var11 = this.n ? EnumFacing.UP : EnumFacing.DOWN;
                BlockPos var12 = this.C;
                CombatUtil.G(26365, var12, var11);
                this.swingItem();
}
}
}
    private boolean E(BlockPos var1, long var2) {
        return !BlockUtil.a$r1(var1) ? false : this.q(var1) != null;
}
    private void swingItem() {
        if (swing.c()) {
            AutoDigPlace.f.field_71439_g.func_71038_i();
        } else {
            PacketManager.b(new C0APacketAnimation());
}
}
    @Override
    public void Z(long var1) {
        long var5 = var1 ^ 0xBBA58590A01L;
        long var7 = var1 ^ 0x3E1D852EA091L;
        this.T(false);
        this.E = false;
        this.C = null;
        this.O = false;
        this.R = null;
        if (this.N && !OutgoingPacketState.P && !OutgoingPacketState.h) {
            ItemUtil.P(this.K);
            this.N = false;
}
        if (this.a) {
            RotationManager.O(var5);
            this.a = false;
}
        if (this.t) {
            KeyBindUtil.o(var7, AutoDigPlace.f.field_71474_y.field_74314_A.func_151463_i());
            this.t = false;
}
}
    @Override
    public final void x(long var1, EventBus var3) {
        AutoDigPlaceBinder.n(var3, this);
}
    public void onSendPacket(int var1, char var2, char var3, SendPacketEvent var4) {
        long var5 = ((long)var1 << 32 | (long)var2 << 48 >>> 32 | (long)var3 << 48 >>> 48) ^ b;
        int var7 = (int)((var5 ^ 0x1EF797CA4FC5L) >>> 32);
        long var8 = (var5 ^ 0x1EF797CA4FC5L) << 32 >>> 32;
        if (this.E && var4.B instanceof C07PacketPlayerDigging && ((C07PacketPlayerDigging)var4.B).func_180762_c() == C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK) {
            var4.I(var7, var8);
}
}
    private PlacementTarget q(BlockPos var1) {
        EnumFacing[] var4;
        EnumFacing[] var10000 = new EnumFacing[]{EnumFacing.DOWN, EnumFacing.UP, EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.EAST};
        for (EnumFacing var8 : var4 = var10000) {
            BlockPos var9 = var1.func_177972_a(var8.func_176734_d());
            if (BlockUtil.a$r1(var9)) continue;
            return new PlacementTarget(var9, var8, false);
}
        return null;
}
    public void onAttackEntity(AttackEntityEvent var3) {
        var3.I(21307, 3074332907L);
}
    static {
        b = 17293825422729L;
        swing = new BooleanSetting("Swing", true);
        rightClickDigDown = new BooleanSetting("Right-click-dig-down", true);
}
}