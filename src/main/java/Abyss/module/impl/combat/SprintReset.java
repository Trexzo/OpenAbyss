/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.play.server.S19PacketEntityStatus
 *  net.minecraft.world.World
 */
package Abyss.module.impl.combat;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.SprintResetBinder;
import Abyss.event.events.AttackEntityEvent;
import Abyss.event.events.AttackTargetEntityEvent;
import Abyss.event.events.MoveInputEvent;
import Abyss.event.events.PostTickEvent;
import Abyss.event.events.PreSuperLivingUpdateEvent;
import Abyss.event.events.PreUpdateEvent;
import Abyss.event.events.ReceivePacketEvent;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.ModeSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.util.KeyBindUtil;
import Abyss.util.RaytraceUtil;
import Abyss.util.TimerUtil;
import java.io.UnsupportedEncodingException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.world.World;

public class SprintReset
extends Module
implements EventSubscriber {
    private boolean K;
    private final TimerUtil o;
    private boolean u;
    private EntityLivingBase E;
    public static NumberSetting duration;
    public static ModeSetting mode;
    private boolean C;
    public static BooleanSetting requireTargetDamage;
        private final TimerUtil U;
    private boolean h;
    private boolean g;
    public static NumberSetting interval;
    private boolean H;

    @Override
    public void A(long var1) {
        long var3 = var1 ^ 0xF9FDC7ACA5AL;
        this.g = false;
        this.C = false;
        this.E = null;
        if (this.H) {
            KeyBindUtil.o(var3, SprintReset.f.field_71474_y.field_74351_w.func_151463_i());
            this.H = false;
}
        this.h = false;
}
    public void onPreUpdate(int var1, PreUpdateEvent var2, int var3, int var4) {
        long var5 = ((long)var1 << 32 | (long)var3 << 48 >>> 32 | (long)var4 << 48 >>> 48) ^ a;
        long var7 = var5 ^ 0x44600D41BC0FL;
        if (this.E != null && RaytraceUtil.q(var7, (Entity)this.E, 6.0)) {
            this.E = null;
}
}
    public void onPostTick(PostTickEvent var1) {
        if (this.H && this.o.A(duration.L())) {
            KeyBindUtil.o(99363263780575L, SprintReset.f.field_71474_y.field_74351_w.func_151463_i());
            this.H = false;
}
}
    @Override
    public final void x(long var1, EventBus var3) {
        SprintResetBinder.G(var3, this);
}
    public void onReceivePacket(short var1, int var2, ReceivePacketEvent var3, char var4) {
        S19PacketEntityStatus var7;
        if (this.C && this.E != null && var3.d instanceof S19PacketEntityStatus && (var7 = (S19PacketEntityStatus)var3.d).func_149161_a((World)SprintReset.f.field_71441_e) == this.E && var7.func_149160_c() == 2) {
            this.g = true;
            this.C = false;
}
}
    public void onAttackEntity(AttackEntityEvent var1) {
        Entity var6;
        if (this.U.A(interval.L()) && (var6 = var1.O()) instanceof EntityLivingBase) {
            this.E = (EntityLivingBase)var1.O();
            switch (mode.Y()) {
                case "LEGIT": {
                    if (this.H || !SprintReset.f.field_71439_g.func_70051_ag()) break;
                    if (requireTargetDamage.c()) {
                        if (!this.g) {
                            this.C = true;
                            break;
}
                        this.g = false;
                        this.u = true;
                        break;
}
                    this.o.W();
                    KeyBindUtil.A(82009306480869L, SprintReset.f.field_71474_y.field_74351_w.func_151463_i(), false);
                    SprintReset.f.field_71439_g.field_71158_b.field_78900_b = 0.0f;
                    SprintReset.f.field_71439_g.field_71158_b.field_78902_a = 0.0f;
                    this.U.W();
                    this.H = true;
                    break;
}
                case "NO_STOP": {
                    if (!SprintReset.f.field_71439_g.func_70051_ag()) break;
                    if (requireTargetDamage.c()) {
                        if (!this.g) {
                            this.C = true;
                            break;
}
                        this.g = false;
                        this.h = false;
                        this.K = true;
                        break;
}
                    this.h = false;
                    this.K = true;
}
}
}
}
    public SprintReset(int var1, char var2, short var3) {
        super(((long)var1 << 32 | (long)var2 << 48 >>> 32 | (long)var3 << 48 >>> 48) ^ a ^ 0x42CDFD2894BEL);
        this.declare("SprintReset", Category.Combat, "Reset sprint state during combat to give more knockback to opponent", new Setting[0]);
        this.o = new TimerUtil();
        this.U = new TimerUtil();
        this.H = false;
        this.E = null;
        this.C = false;
        this.g = false;
        this.u = false;
        this.K = false;
        this.h = false;
}
    public void onMoveInput(MoveInputEvent var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (mode.R("LEGIT") && this.u) {
            this.o.W();
            KeyBindUtil.A(82009306480869L, SprintReset.f.field_71474_y.field_74351_w.func_151463_i(), false);
            var3.i(0.0f);
            var3.A(0.0f);
            this.U.W();
            this.H = true;
            this.u = false;
}
}
    public void onPreSuperLivingUpdate(short var1, PreSuperLivingUpdateEvent var2, int var3, short var4) {
        if (this.h) {
            this.h = false;
            SprintReset.f.field_71439_g.func_70031_b(false);
}
}
    public void onAttackTargetEntity(AttackTargetEntityEvent var3) {
        if (var3.w instanceof EntityPlayer && this.K) {
            this.K = false;
            this.h = true;
}
}
    @Override
    public String g(long var1) {
        return mode.Y();
}
    static {
        mode = new ModeSetting("Mode", "NO_STOP", "LEGIT");
        interval = new NumberSetting("Interval", 400.0f, 0.0f, 2000.0f, 1.0f);
        requireTargetDamage = new BooleanSetting("Require-target-damage", true);
        duration = new NumberSetting("Duration", 50.0f, 0.0f, 200.0f, 1.0f);
}
}