/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.INetHandlerPlayClient
 *  net.minecraft.network.play.client.C02PacketUseEntity
 *  net.minecraft.network.play.client.C02PacketUseEntity$Action
 *  net.minecraft.network.play.server.S12PacketEntityVelocity
 *  net.minecraft.network.play.server.S19PacketEntityStatus
 *  net.minecraft.network.play.server.S27PacketExplosion
 *  net.minecraft.world.World
 */
package Abyss.module.impl.combat;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.VelocityBinder;
import Abyss.event.events.KnockbackEvent;
import Abyss.event.events.PreLivingUpdateEvent;
import Abyss.event.events.PreMouseInputEvent;
import Abyss.event.events.PreTickEvent;
import Abyss.event.events.ReceivePacketEvent;
import Abyss.event.events.WorldLoadEvent;
import Abyss.internal.accessor.EntityAccessor;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.Modules;
import Abyss.module.impl.combat.JumpReset;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.HeaderSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.setting.settings.PercentageSetting;
import Abyss.util.EntityUtil;
import Abyss.util.KeyBindUtil;
import Abyss.util.MathUtil;
import Abyss.util.MoveUtil;
import Abyss.util.RaytraceUtil;
import Abyss.util.packet.IncomingPacketHold;
import Abyss.util.packet.OutgoingPacketState;
import Abyss.util.packet.PacketManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.world.World;

public class Velocity
extends Module
implements EventSubscriber {
    public static BooleanSetting animals;
    private boolean k;
    public static HeaderSetting targetSettings;
    private boolean E;
    public static BooleanSetting bosses;
    public static NumberSetting minDelayTicks;
    public static BooleanSetting reduceVelocity;
    private boolean N;
    public static BooleanSetting enemies;
    private List<EntityLivingBase> O;
    private boolean I;
    public static BooleanSetting mobs;
    private int v;
    public static NumberSetting maxDelayTicks;
    private static long c;
    private static String[] o;
    public static BooleanSetting delayVelocity;
    private boolean t;
    private boolean a;
    public static NumberSetting reduceEffectTicks;
    public static BooleanSetting delayReleaseOnReduce;
    public static BooleanSetting teammates;
    public static BooleanSetting bots;
    public static PercentageSetting horizontal;
    public static BooleanSetting modifyVelocity;
    public static BooleanSetting players;
    public static BooleanSetting reverseVelocity;
    public static BooleanSetting friends;
    private double n;
    private static Map C;
    public static BooleanSetting delayReleaseOnGround;
    public static BooleanSetting disableWhileHoldingS;
    private double g;
    public static PercentageSetting vertical;
    public static NumberSetting fov;
    public static PercentageSetting chance;
    public static BooleanSetting requireMoving;

    public Velocity(int var1, byte var2, int var3) {
        super(((long)var1 << 32 | (long)var2 << 56 >>> 32 | (long)var3 << 40 >>> 40) ^ c ^ 0x7533F258DB3AL);
        this.declare("Velocity", Category.Combat, "Modify the velocity received", new Setting[0]);
        this.E = false;
        this.I = false;
        this.g = 0.0;
        this.n = MathUtil.h(minDelayTicks.L(), maxDelayTicks.L());
        this.N = true;
        this.a = false;
        this.v = 0;
        this.k = false;
        this.t = false;
        this.O = new ArrayList<EntityLivingBase>();
}
    public void onPreTick(PreTickEvent var1, long var2) {
        this.y(49587205232658L);
        if (this.k) {
            ++this.v;
            if (this.v >= (int)reduceEffectTicks.L()) {
                this.k = false;
                this.a = false;
                this.v = 0;
}
}
        if (disableWhileHoldingS.c() && KeyBindUtil.V(Velocity.f.gameSettings.keyBindBack.getKeyCode(), 64165991731362L)) {
            this.Q();
}
        if (this.O.isEmpty()) {
            this.Q();
}
        if (this.I) {
            this.g += 1.0;
            this.o(0L);
}
}
    @Override
    public String g(long var1) {
        if (modifyVelocity.c()) {
            if (reverseVelocity.c()) {
                return "\u00a7m" + horizontal.k() + "%\u00a7r " + vertical.k() + "%";
}
            return horizontal.k() != vertical.k() ? horizontal.k() + "% " + vertical.k() + "%" : horizontal.k() + "%";
}
        if (delayVelocity.c() || reduceVelocity.c()) {
            StringBuilder var3 = new StringBuilder();
            boolean var4 = false;
            if (delayVelocity.c()) {
                if (minDelayTicks.L() == maxDelayTicks.L()) {
                    var3.append((int)minDelayTicks.L());
                } else {
                    var3.append((int)minDelayTicks.L()).append("-").append((int)maxDelayTicks.L());
}
                if ((int)maxDelayTicks.L() <= 1) {
                    var3.append("TICK");
                } else {
                    var3.append("TICKS");
}
                var4 = true;
}
            if (reduceVelocity.c()) {
                if (var4) {
                    var3.append(", REDUCE");
                } else {
                    var3.append("REDUCE");
}
}
            return var3.toString();
}
        return reverseVelocity.c() ? "\u00a7m" + horizontal.k() + "%\u00a7r " + vertical.k() + "%" : "NONE";
}
    public void onKnockback(long var1, KnockbackEvent var3) {
        if (this.E) {
            this.E = false;
        } else {
            if (this.isGetKeyCode(8945674770656L) && modifyVelocity.c()) {
                if (reverseVelocity.c()) {
                    var3.P(-(var3.S() * (double)horizontal.k() / 100.0));
                    var3.A(-(var3.R() * (double)horizontal.k() / 100.0));
                } else {
                    if (horizontal.k() != 100) {
                        var3.P(var3.S() * (double)horizontal.k() / 100.0);
                        var3.A(var3.R() * (double)horizontal.k() / 100.0);
}
                    if (vertical.k() != 100) {
                        var3.O(var3.f() * (double)vertical.k() / 100.0);
}
}
}
            this.k = true;
            this.a = reduceVelocity.c();
            this.v = 0;
}
}
    @Override
    public void A(long var1) {
        this.D(0L);
}
    private void o(long var1) {
        if (this.g >= this.n || delayReleaseOnGround.c() && Velocity.f.thePlayer.onGround || delayReleaseOnReduce.c() && this.t || Velocity.f.thePlayer.isInWater() || Velocity.f.thePlayer.isInLava()) {
            this.Q();
}
}
    public void onWorldLoad(WorldLoadEvent var1, long var2) {
        this.D(0L);
}
    private boolean isGetKeyCode(long var1) {
        var1 = c ^ var1;
        long var5 = var1 ^ 0x58F915F8A4B8L;
        long var7 = var1 ^ 0x4FBBB11CAC08L;
        this.y(var5);
        return !(!MathUtil.Q(chance.k(), 0L) || disableWhileHoldingS.c() && KeyBindUtil.V(Velocity.f.gameSettings.keyBindBack.getKeyCode(), var7) || this.O.isEmpty() || requireMoving.c() && !MoveUtil.o());
}
    public void onPreLivingUpdate(long var1, PreLivingUpdateEvent var3) {
        this.y(49587205232658L);
}
    private void k(long var1, ReceivePacketEvent var3) {
        S12PacketEntityVelocity var9 = (S12PacketEntityVelocity)var3.d;
        if (this.I) {
            IncomingPacketHold.p().add((Packet<INetHandlerPlayClient>)var9);
            var3.I(17581, 3624099827L);
        } else if (!(Modules.J(JumpReset.class).o() && JumpReset.C(132648017398215L) && Velocity.f.thePlayer.onGround || this.N)) {
            if (this.E) {
                this.E = false;
            } else {
                IncomingPacketHold.p().add((Packet<INetHandlerPlayClient>)var9);
                IncomingPacketHold.X(true);
                this.I = true;
                this.g = 0.0;
                var3.I(17581, 3624099827L);
}
}
}
    @Override
    public final void x(long var1, EventBus var3) {
        VelocityBinder.T(var3, this);
}
    private void D(long var1) {
        this.t = false;
        this.k = false;
        this.v = 0;
        this.N = true;
        this.a = false;
        this.E = false;
        this.Q();
}
    public void onReceivePacket(int var1, char var2, int var3, ReceivePacketEvent var4) {
        S27PacketExplosion var15;
        long var5 = ((long)var1 << 32 | (long)var2 << 48 >>> 32 | (long)var3 << 48 >>> 48) ^ c;
        long var7 = var5 ^ 0x56E3D74AAF78L;
        int var9 = (int)((var5 ^ 0x1B4E18B5075DL) >>> 32);
        long var10 = (var5 ^ 0x1B4E18B5075DL) << 32 >>> 32;
        Packet<?> var12 = var4.d;
        if (var12 instanceof S12PacketEntityVelocity) {
            S12PacketEntityVelocity var13 = (S12PacketEntityVelocity)var12;
            if (var13.getEntityID() != Velocity.f.thePlayer.getEntityId()) {
                return;
}
            if (horizontal.k() == 0 && vertical.k() == 0 && modifyVelocity.c()) {
                var4.I(var9, var10);
}
            if (delayVelocity.c()) {
                this.k(var7, var4);
}
        } else if (var12 instanceof S19PacketEntityStatus) {
            S19PacketEntityStatus var14 = (S19PacketEntityStatus)var12;
            if (var14.getEntity((World)Velocity.f.theWorld) instanceof EntityPlayerSP && var14.getOpCode() == 2) {
                this.N = false;
}
        } else if (var12 instanceof S27PacketExplosion && ((var15 = (S27PacketExplosion)var12).func_149149_c() != 0.0f || var15.func_149144_d() != 0.0f || var15.func_149147_e() != 0.0f)) {
            this.E = true;
}
}
    private void y(long var1) {
        var1 = c ^ var1;
        long var3 = var1 ^ 0x1DF4C75693E7L;
        long var5 = var1 ^ 0x236CDE8B7812L;
        this.O = EntityUtil.K(EntityUtil.F(10.0, var3, fov.L()), players.c(), var5, mobs.c(), animals.c(), bosses.c(), friends.c(), enemies.c(), teammates.c(), bots.c());
}
    private void Q() {
        this.n = MathUtil.h(minDelayTicks.L(), maxDelayTicks.L());
        if (this.I) {
            IncomingPacketHold.m();
            IncomingPacketHold.X(false);
            this.I = false;
}
        this.g = 0.0;
        this.N = true;
        this.E = false;
        this.t = false;
}
    private EntityLivingBase isSprinting(long var1) {
        var1 = c ^ var1;
        long var3 = var1 ^ 0x3B7514904FEEL;
        long var5 = var1 ^ 0x6DCE374ADE8AL;
        EntityLivingBase var7 = null;
        List<EntityLivingBase> var8 = RaytraceUtil.j(3.0);
        int var10 = var8.size();
        for (int var9 = 0; var9 < var10; ++var9) {
            EntityLivingBase var11 = var8.get(var9);
            if (!(var11 instanceof EntityPlayer)) continue;
            var7 = var11;
            break;
}
        if (var7 == null || !this.isGetKeyCode(var5) || !Velocity.f.thePlayer.isSprinting()) {
            return null;
}
        if (EntityAccessor.F((Entity)Velocity.f.thePlayer, var3)) {
            return null;
}
        return !OutgoingPacketState.f() ? null : var7;
}
    public void onPreMouseInput(int var1, PreMouseInputEvent var2, char var3, short var4) {
        long var5 = ((long)var1 << 32 | (long)var3 << 48 >>> 32 | (long)var4 << 48 >>> 48) ^ c;
        long var7 = var5 ^ 0x13A01E72EB40L;
        if (this.a && this.k) {
            EntityLivingBase var9 = this.isSprinting(var7);
            if (var9 == null) {
                return;
}
            Velocity.f.thePlayer.swingItem();
            PacketManager.b(new C02PacketUseEntity((Entity)var9, C02PacketUseEntity.Action.ATTACK));
            Velocity.f.thePlayer.motionX *= 0.6;
            Velocity.f.thePlayer.motionZ *= 0.6;
            Velocity.f.thePlayer.setSprinting(false);
            this.t = true;
            var2.Q(true);
            var2.G(true);
            var2.M(true);
}
}
    static {
        c = 138275096110154L;
        enemies = new BooleanSetting("Enemies", true);
        delayReleaseOnReduce = new BooleanSetting("Delay-release-on-reduce", true);
        disableWhileHoldingS = new BooleanSetting("Disable-while-holding-S", true);
        minDelayTicks = new NumberSetting("Min-delay-ticks", 3.0f, 1.0f, 20.0f, 1.0f);
        delayVelocity = new BooleanSetting("Delay-velocity", true);
        friends = new BooleanSetting("Friends", false);
        mobs = new BooleanSetting("Mobs", false);
        maxDelayTicks = new NumberSetting("Max-delay-ticks", 3.0f, 1.0f, 20.0f, 1.0f);
        reverseVelocity = new BooleanSetting("Reverse-velocity", false);
        reduceEffectTicks = new NumberSetting("Reduce-effect-ticks", 10.0f, 0.0f, 20.0f, 1.0f);
        requireMoving = new BooleanSetting("Require-moving", false);
        modifyVelocity = new BooleanSetting("Modify-velocity", false);
        bosses = new BooleanSetting("Bosses", false);
        reduceVelocity = new BooleanSetting("Reduce-velocity", false);
        players = new BooleanSetting("Players", true);
        horizontal = new PercentageSetting("Horizontal", 100);
        chance = new PercentageSetting("Chance", 100);
        animals = new BooleanSetting("Animals", false);
        teammates = new BooleanSetting("Teammates", false);
        fov = new NumberSetting("FOV", 360.0f, 0.0f, 360.0f, 1.0f);
        vertical = new PercentageSetting("Vertical", 100);
        bots = new BooleanSetting("Bots", false);
        delayReleaseOnGround = new BooleanSetting("Delay-release-on-ground", true);
        targetSettings = new HeaderSetting("Target settings");
}
}