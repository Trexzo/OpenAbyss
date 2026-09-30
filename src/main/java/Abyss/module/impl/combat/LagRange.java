/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.ItemSword
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C02PacketUseEntity
 *  net.minecraft.network.play.client.C07PacketPlayerDigging
 *  net.minecraft.network.play.client.C07PacketPlayerDigging$Action
 *  net.minecraft.network.play.client.C08PacketPlayerBlockPlacement
 */
package Abyss.module.impl.combat;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.LagRangeBinder;
import Abyss.event.events.PostTickEvent;
import Abyss.event.events.PreLivingUpdateEvent;
import Abyss.event.events.Render2DEvent;
import Abyss.event.events.SendPacketEvent;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.HeaderSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.util.EntityUtil;
import Abyss.util.ItemUtil;
import Abyss.util.RaytraceUtil;
import Abyss.util.RotationUtil;
import Abyss.util.TimerUtil;
import Abyss.util.packet.PacketManager;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;

public class LagRange
extends Module
implements EventSubscriber {
    private static long a = 120211912914588L;

    public static NumberSetting disableRange;
    public static BooleanSetting s;
    public static HeaderSetting M;
    private static long[] d;
    private static String b;
    public static BooleanSetting animals;
    private boolean e;
    public static BooleanSetting friends;
    public static BooleanSetting players;
    public static BooleanSetting bosses;
    public static BooleanSetting bots;
    public static BooleanSetting mobs;
    public static BooleanSetting enemies;
    private final Set<EntityLivingBase> E;
    public static NumberSetting delay;
    private static Map m;
    private final TimerUtil v;
    public static NumberSetting fov;
    public static NumberSetting targetRange;
    public static BooleanSetting teammates;
    
    @Override
    public String g(long var1) {
        return (int)delay.L() + b;
}
    public void onPreLivingUpdate(PreLivingUpdateEvent var1, long var2) {
        this.E.clear();
        boolean var14 = players.c();
        boolean var15 = mobs.c();
        boolean var16 = animals.c();
        boolean var17 = bosses.c();
        boolean var18 = friends.c();
        boolean var19 = enemies.c();
        boolean var20 = teammates.c();
        boolean var21 = bots.c();
        boolean var22 = var14 && !var15 && !var16 && !var17;
        int var23 = (int)targetRange.L();
        int var24 = (int)disableRange.L();
        int var25 = (int)fov.L();
        List var26 = EntityUtil.U(var22);
        int var28 = var26.size();
        for (int var27 = 0; var27 < var28; ++var27) {
            EntityLivingBase var29 = (EntityLivingBase)var26.get(var27);
            if (!(var22 ? EntityUtil.c(30808997819832L, (EntityPlayer)var29, var18, var19, var20, var21) : EntityUtil.q((Entity)var29, var14, var15, var16, var17, var18, var19, var20, var21, 21816078198602L)) || !RaytraceUtil.q(50051018191872L, (Entity)var29, var23) || RaytraceUtil.q(50051018191872L, (Entity)var29, var24) || !RotationUtil.b(126426268413036L, (Entity)var29, (double)var25)) continue;
            this.E.add(var29);
}
}
    public void onPostTick(int var1, int var2, char var3, PostTickEvent var4) {
        long var5 = ((long)var1 << 32 | (long)var2 << 48 >>> 32 | (long)var3 << 48 >>> 48) ^ a;
        long var7 = var5 ^ 0x306C00AD8BEEL;
        if (LagRange.f.thePlayer.isDead) {
            this.H(var7);
}
}
    private void H(long var1) {
        if (this.e) {
            PacketManager.j();
            PacketManager.M(false);
            this.e = false;
            this.v.W();
}
}
    public void onRender2D(short var1, Render2DEvent var2, long var3) {
        if (!(LagRange.f.thePlayer.isUsingItem() && !LagRange.f.thePlayer.isBlocking() || s.c() && !ItemUtil.d())) {
            if (this.E.isEmpty()) {
                this.H(111406552585898L);
            } else {
                PacketManager.M(true);
                this.e = true;
                if (this.v.A(delay.L())) {
                    this.H(111406552585898L);
}
}
        } else {
            this.H(111406552585898L);
}
}
    private boolean H(Packet<?> var1) {
        if (var1 instanceof C02PacketUseEntity) {
            return true;
}
        if (var1 instanceof C07PacketPlayerDigging) {
            return ((C07PacketPlayerDigging)var1).getStatus() != C07PacketPlayerDigging.Action.RELEASE_USE_ITEM;
}
        if (!(var1 instanceof C08PacketPlayerBlockPlacement)) {
            return false;
}
        ItemStack var2 = ((C08PacketPlayerBlockPlacement)var1).getStack();
        return var2 == null || !(var2.getItem() instanceof ItemSword);
}
    @Override
    public void A(long var1) {
        long var3 = var1 ^ 0x3093D78E4A2FL;
        this.H(var3);
        this.E.clear();
}
    public LagRange(long var1) {
        super(a ^ var1 ^ 0x18CFAE9434D6L);
        this.declare("LagRange", Category.Combat, "Perform network lag when entities in range", new Setting[0]);
        var1 = a ^ var1;
        this.E = new HashSet<EntityLivingBase>();
        this.v = new TimerUtil();
        this.e = false;
}
    @Override
    public final void x(long var1, EventBus var3) {
        LagRangeBinder.D(var3, this);
}
    public void onSendPacket(long var1, int var3, SendPacketEvent var4) {
        long var5 = (var1 << 32 | (long)var3 << 32 >>> 32) ^ a;
        long var7 = var5 ^ 0x2F9BBBA61466L;
        if (this.H(var4.B)) {
            this.H(var7);
}
}
    static {
        b = "ms";
        m = new HashMap(13);
        d = new long[]{3397638145785976630L, 921795783406817893L, -3875737584293005616L};
        teammates = new BooleanSetting("Teammates", false);
        bots = new BooleanSetting("Bots", false);
        mobs = new BooleanSetting("Mobs", false);
        friends = new BooleanSetting("Friends", false);
        bosses = new BooleanSetting("Bosses", false);
        s = new BooleanSetting("S", false);
        delay = new NumberSetting("Delay", 150.0f, 0.0f, 1000.0f, 1.0f);
        animals = new BooleanSetting("Animals", false);
        enemies = new BooleanSetting("Enemies", true);
        players = new BooleanSetting("Players", true);
        targetRange = new NumberSetting("Target-range", 8.0f, 0.0f, 20.0f, 0.1f);
        fov = new NumberSetting("FOV", 180.0f, 0.0f, 360.0f, 1.0f);
        disableRange = new NumberSetting("Disable-range", 3.0f, 0.0f, 15.0f, 0.1f);
}
}