/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.projectile.EntityFireball
 *  net.minecraft.network.play.client.C0APacketAnimation
 */
package Abyss.module.impl.combat;

import Abyss.enums.RotationMode;
import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.AntiFireballBinder;
import Abyss.event.events.PreMouseInputEvent;
import Abyss.event.events.PreTickEvent;
import Abyss.event.events.WorldLoadEvent;
import Abyss.module.Category;
import Abyss.module.PriorityModule;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.ModeSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.util.CombatUtil;
import Abyss.util.RaytraceUtil;
import Abyss.util.RotationManager;
import Abyss.util.RotationUtil;
import Abyss.util.packet.OutgoingPacketState;
import Abyss.util.packet.PacketManager;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.network.play.client.C0APacketAnimation;

public class AntiFireball
extends PriorityModule
implements EventSubscriber {
    private static long a;
    static {
        a = 119630054251485L;
    }
    private EntityFireball G;
    public static NumberSetting fov;
    public static ModeSetting moveFix;
        private final List<EntityFireball> r;
    private final List<EntityFireball> K;
    public static BooleanSetting swing;
    public static NumberSetting range;
    private boolean N;

    private void m(long var1) {
        this.T(false);
        if (this.N) {
            RotationManager.O(123115463851087L);
            this.N = false;
}
}
    public void onPreTick(char var1, int var2, short var3, PreTickEvent var4) {
        int var12;
        long var5 = ((long)var1 << 48 | (long)var2 << 32 >>> 16 | (long)var3 << 48 >>> 48) ^ a;
        long var7 = (var5 ^ 0x69EEE6911949L) >>> 32;
        int var9 = (int)((var5 ^ 0x69EEE6911949L) << 32 >>> 32);
        ArrayList<EntityFireball> var10 = new ArrayList<EntityFireball>();
        List var11 = AntiFireball.f.theWorld.loadedEntityList;
        int var13 = var11.size();
        for (var12 = 0; var12 < var13; ++var12) {
            Entity var14 = (Entity)var11.get(var12);
            if (!(var14 instanceof EntityFireball)) continue;
            var10.add((EntityFireball)var14);
}
        this.K.removeIf(var1x -> !var10.contains(var1x));
        this.r.removeIf(var1x -> !var10.contains(var1x));
        int var20 = var10.size();
        for (var12 = 0; var12 < var20; ++var12) {
            EntityFireball var22 = (EntityFireball)var10.get(var12);
            if (this.K.contains(var22) || this.r.contains(var22)) continue;
            if (RaytraceUtil.i((Entity)var22) > 3.0) {
                this.K.add(var22);
                continue;
}
            this.r.add(var22);
}
        if (AntiFireball.f.thePlayer.capabilities.allowFlying) {
            this.G = null;
        } else {
            boolean var19 = false;
            EntityFireball var21 = null;
            Comparator<EntityFireball> var23 = Comparator.comparingDouble(RaytraceUtil::i);
            int var16 = this.K.size();
            for (int var15 = 0; var15 < var16; ++var15) {
                EntityFireball var17 = this.K.get(var15);
                if (!this.V(var7, var9, var17) || var19 && var23.compare(var17, var21) >= 0) continue;
                var19 = true;
                var21 = var17;
}
            this.G = var19 ? var21 : null;
}
}
    private void swingItem() {
        if (swing.c()) {
            AntiFireball.f.thePlayer.swingItem();
        } else {
            PacketManager.b(new C0APacketAnimation());
}
}
    @Override
    public final void x(long var1, EventBus var3) {
        AntiFireballBinder.r(var3, this);
}
    public void onWorldLoad(WorldLoadEvent var3) {
        this.K.clear();
        this.r.clear();
}
    public AntiFireball(long var1) {
        super((a ^ var1 ^ 0x4681D3546BEDL) >>> 16, (char)((a ^ var1 ^ 0x4681D3546BEDL) << 48 >>> 48));
        this.declare("AntiFireball", Category.Combat, "Hit fireballs back", new Setting[0]);
        var1 = a ^ var1;
        this.K = new ArrayList<EntityFireball>();
        this.r = new ArrayList<EntityFireball>();
        this.G = null;
        this.N = false;
}
    private boolean V(long var1, int var3, EntityFireball var4) {
        long var5 = (var1 << 32 | (long)var3 << 32 >>> 32) ^ a;
        long var7 = var5 ^ 0x40B159F50C21L;
        long var9 = var5 ^ 0x1FCFD80C544DL;
        return var4 != null && RaytraceUtil.q(var7, (Entity)var4, (double)range.L() + 3.0) && RotationUtil.b(var9, (Entity)var4, (double)fov.L());
}
    @Override
    public void A(long var1) {
        this.m(0L);
}
    public void onPreMouseInput(PreMouseInputEvent var1, long var2) {
        EntityFireball var16 = this.G;
        if (this.G != null && AntiFireball.f.theWorld.loadedEntityList.contains(var16) && this.Y() && OutgoingPacketState.f()) {
            this.T(true);
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
            float[] var20 = RotationUtil.h(11022, this.G.getEntityBoundingBox(), (byte)99, 9521810);
            RotationManager.N(71285564916286L, var20[0], var20[1]);
            this.N = true;
            if (RaytraceUtil.q(50051018191872L, (Entity)this.G, range.L())) {
                this.swingItem();
                CombatUtil.I((Entity)this.G, 10456, 1760016611L);
                return;
}
}
        this.m(0L);
}
    @Override
    public String g(long var1) {
        return String.valueOf(range.L());
}
    static {
        swing = new BooleanSetting("Swing", true);
        range = new NumberSetting("Range", 5.0f, 0.0f, 10.0f, 0.1f);
        fov = new NumberSetting("FOV", 180.0f, 0.0f, 360.0f, 1.0f);
        moveFix = new ModeSetting("Move-fix", "SILENT", "STRICT", "NONE");
}
}