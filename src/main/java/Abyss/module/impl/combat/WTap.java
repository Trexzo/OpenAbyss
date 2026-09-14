/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.play.server.S19PacketEntityStatus
 *  net.minecraft.world.World
 */
package Abyss.module.impl.combat;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.WTapBinder;
import Abyss.event.events.AttackEntityEvent;
import Abyss.event.events.MoveInputEvent;
import Abyss.event.events.PreMouseInputEvent;
import Abyss.event.events.ReceivePacketEvent;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.setting.settings.PercentageSetting;
import Abyss.util.KeyBindUtil;
import Abyss.util.MathUtil;
import Abyss.util.TimerUtil;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.world.World;

public class WTap
extends Module
implements EventSubscriber {
    private static String[] o;
        public static NumberSetting interval;
    public static BooleanSetting useBlockInstead;
    private static String d;
    private int G;
    private boolean p;
    private boolean L;
    private static long[] e;
    private static Map m;
    private final TimerUtil r;
    private boolean u;
    public static NumberSetting minPauseTick;
    private int T;
    public static NumberSetting maxPauseTick;
    private EntityPlayer h;
    private static Object[] n;
    public static PercentageSetting chance;
    public static BooleanSetting requireOnGround;
    public static BooleanSetting requireTargetDamage;
    private boolean J;

    @Override
    public final void x(long var1, EventBus var3) {
        WTapBinder.U(var3, this);
}
    @Override
    public String g(long var1) {
        StringBuilder var3 = new StringBuilder();
        if (minPauseTick.L() == maxPauseTick.L()) {
            var3.append((int)minPauseTick.L() * 50);
        } else {
            var3.append((int)minPauseTick.L() * 50).append("-").append((int)maxPauseTick.L() * 50);
}
        var3.append(d);
        return var3.toString();
}
    public void onMoveInput(MoveInputEvent var1, long var2) {
        if (this.L) {
            var1.i(0.0f);
            var1.A(0.0f);
            this.L = false;
}
}
    public void onReceivePacket(ReceivePacketEvent var1, long var2) {
        S19PacketEntityStatus var6;
        if (requireTargetDamage.c() && var1.d instanceof S19PacketEntityStatus && !((var6 = (S19PacketEntityStatus)var1.d).func_149161_a((World)WTap.f.field_71441_e) instanceof EntityPlayerSP) && var6.func_149161_a((World)WTap.f.field_71441_e) instanceof EntityPlayer && var6.func_149160_c() == 2 && MathUtil.Q(chance.k(), 0L) && this.r.L((long)interval.L(), true)) {
            this.G = (int)MathUtil.h(minPauseTick.L(), maxPauseTick.L());
            this.J = false;
}
}
    public void onAttackEntity(long var1, AttackEntityEvent var3) {
        if (var3.O() instanceof EntityPlayer) {
            this.J = this.h == null;
            this.h = (EntityPlayer)var3.O();
            this.T = 60;
}
}
    public void onPreMouseInput(long var1, PreMouseInputEvent var3) {
        this.u = false;
        this.L = false;
        if (this.T > 0) {
            --this.T;
}
        if (this.T <= 0) {
            this.h = null;
}
        if (this.h != null) {
            if ((!requireTargetDamage.c() || this.J) && this.r.L((long)interval.L(), true)) {
                this.G = (int)MathUtil.h(minPauseTick.L(), maxPauseTick.L());
                this.J = false;
}
            if ((!requireOnGround.c() || WTap.f.field_71439_g.field_70122_E) && this.G > 0) {
                if (useBlockInstead.c()) {
                    this.u = true;
                } else {
                    this.L = true;
}
}
}
        if (this.G > 0) {
            --this.G;
}
        if (!this.u && this.p) {
            KeyBindUtil.o(99363263780575L, WTap.f.field_71474_y.field_74313_G.func_151463_i());
            this.p = false;
}
        if (this.u) {
            KeyBindUtil.A(82009306480869L, WTap.f.field_71474_y.field_74313_G.func_151463_i(), true);
            this.u = false;
            this.p = true;
}
}
    public WTap(long var1) {
        super(a ^ var1 ^ 0x2A1AE7D60AF5L);
        this.declare("WTap", Category.Combat, "Pause moving during combat to help combo", new Setting[0]);
        var1 = a ^ var1;
        this.h = null;
        this.r = new TimerUtil();
        this.T = 0;
        this.G = 0;
        this.u = false;
        this.L = false;
        this.J = false;
        this.p = false;
}
    @Override
    public void A(long var1) {
        long var3 = var1 ^ 0xF9FDC7ACA5AL;
        this.u = false;
        this.L = false;
        this.G = 0;
        this.T = 0;
        this.h = null;
        this.J = false;
        if (this.p) {
            KeyBindUtil.o(var3, WTap.f.field_71474_y.field_74313_G.func_151463_i());
            this.p = false;
}
}
    static {
        n = new Object[8];
        o = new String[8];
        d = "ms";
        m = new HashMap(13);
        e = new long[]{3266210825553073490L, 5314742259686245813L, 4719741884499584443L, 6622068100265281457L, -2199634987435189256L, -9111339067620825184L, -2825596532523530443L};
        useBlockInstead = new BooleanSetting("Use-block-instead", false);
        requireOnGround = new BooleanSetting("Require-on-ground", true);
        chance = new PercentageSetting("Chance", 100);
        minPauseTick = new NumberSetting("Min-pause-tick", 2.0f, 0.0f, 10.0f, 1.0f);
        maxPauseTick = new NumberSetting("Max-pause-tick", 3.0f, 0.0f, 10.0f, 1.0f);
        interval = new NumberSetting("Interval", 500.0f, 0.0f, 2000.0f, 50.0f);
        requireTargetDamage = new BooleanSetting("Require-target-damage", true);
}
}