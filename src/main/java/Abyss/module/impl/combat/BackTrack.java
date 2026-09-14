/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 */
package Abyss.module.impl.combat;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.BackTrackBinder;
import Abyss.event.events.AttackEntityEvent;
import Abyss.event.events.PreUpdateEvent;
import Abyss.event.events.Render2DEvent;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.HeaderSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.util.EntityUtil;
import Abyss.util.MathUtil;
import Abyss.util.RaytraceUtil;
import Abyss.util.TimerUtil;
import Abyss.util.packet.IncomingPacketHold;
import java.util.Map;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class BackTrack
extends Module
implements EventSubscriber {
    public static EntityLivingBase N;
    private static long[] m;
    public static HeaderSetting targetSettings;
    public static NumberSetting minDelay;
    private double K;
        public static NumberSetting maxRange;
    public static BooleanSetting friends;
    private static String[] g;
    public static NumberSetting minRange;
    private boolean E;
    private double u;
    public static NumberSetting minInterval;
    public static BooleanSetting animals;
    public static BooleanSetting teammates;
    private final TimerUtil p;
    public static BooleanSetting bots;
    public static BooleanSetting players;
    public static BooleanSetting bosses;
    private static long b;
    private final TimerUtil x;
    public static BooleanSetting mobs;
        public static NumberSetting maxInterval;
    public static BooleanSetting enemies;
    public static NumberSetting maxDelay;

    public void onPreUpdate(char var1, int var2, PreUpdateEvent var3, short var4) {
        long var5 = ((long)var1 << 48 | (long)var2 << 32 >>> 16 | (long)var4 << 48 >>> 48) ^ b;
        long var7 = var5 ^ 0x27138C15A8ABL;
        if (N != null && (RaytraceUtil.q(var7, (Entity)N, minRange.L()) || !RaytraceUtil.q(var7, (Entity)N, maxRange.L()))) {
            N = null;
            this.Y();
}
}
    @Override
    public void A(long var1) {
        this.Y();
}
    public void onRender2D(char var1, int var2, Render2DEvent var3, char var4) {
        if (N != null) {
            if (!this.E && this.x.A(0.0)) {
                IncomingPacketHold.X(true);
                this.E = true;
                this.u = MathUtil.h(minDelay.L(), maxDelay.L());
                this.p.W();
            } else if (this.E && this.p.A(0.0)) {
                this.Y();
                this.K = MathUtil.h(minInterval.L(), maxInterval.L());
                this.x.W();
}
}
}
    @Override
    public final void x(long var1, EventBus var3) {
        BackTrackBinder.P(var3, this);
}
    @Override
    public String g(long var1) {
        return (int)minDelay.L() == (int)maxDelay.L() ? (int)minDelay.L() + "ms" : (int)minDelay.L() + "-" + (int)maxDelay.L() + "ms";
}
    public void onAttackEntity(AttackEntityEvent var1, long var2) {
        if (N == null && var1.O() instanceof EntityLivingBase && EntityUtil.q(var1.O(), players.c(), mobs.c(), animals.c(), bosses.c(), friends.c(), enemies.c(), teammates.c(), bots.c(), 21816078198602L) && !RaytraceUtil.q(50051018191872L, var1.O(), minRange.L()) && RaytraceUtil.q(50051018191872L, var1.O(), maxRange.L())) {
            N = (EntityLivingBase)var1.O();
}
}
    public BackTrack(long var1) {
        super(b ^ var1 ^ 0x7BB18D656274L);
        this.declare("BackTrack", Category.Combat, "Simulate network lags to get advantage at reaching enemies", new Setting[0]);
        var1 = b ^ var1;
        this.p = new TimerUtil();
        this.x = new TimerUtil();
        this.E = false;
        this.u = MathUtil.h(minDelay.L(), maxDelay.L());
        this.K = MathUtil.h(minInterval.L(), maxInterval.L());
}
    private void Y() {
        if (this.E) {
            IncomingPacketHold.m();
            IncomingPacketHold.X(false);
            this.E = false;
}
}
    static {
        b = 110463194085250L;
        N = null;
        minInterval = new NumberSetting("Min-interval", 0.0f, 0.0f, 500.0f, 1.0f);
        maxInterval = new NumberSetting("Max-interval", 0.0f, 0.0f, 500.0f, 1.0f);
        players = new BooleanSetting("Players", true);
        maxDelay = new NumberSetting("Max-delay", 100.0f, 0.0f, 500.0f, 1.0f);
        enemies = new BooleanSetting("Enemies", true);
        minDelay = new NumberSetting("Min-delay", 100.0f, 0.0f, 500.0f, 1.0f);
        minRange = new NumberSetting("Min-range", 1.0f, 0.0f, 10.0f, 0.1f);
        bosses = new BooleanSetting("Bosses", false);
        maxRange = new NumberSetting("Max-range", 5.0f, 0.0f, 10.0f, 0.1f);
        mobs = new BooleanSetting("Mobs", false);
        bots = new BooleanSetting("Bots", false);
        animals = new BooleanSetting("Animals", false);
        teammates = new BooleanSetting("Teammates", false);
        friends = new BooleanSetting("Friends", false);
        targetSettings = new HeaderSetting("Target settings");
}
}