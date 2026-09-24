/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.network.NetworkPlayerInfo
 */
package Abyss.module.impl.misc;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.DenickBinder;
import Abyss.event.events.PlayerGetNameEvent;
import Abyss.event.events.PostTickEvent;
import Abyss.internal.auth.MojangApiClient;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.setting.Setting;
import Abyss.setting.settings.ModeSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.util.ClientUtil;
import Abyss.util.Sneaky;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import net.minecraft.client.network.NetworkPlayerInfo;

public class Denick
extends Module
implements EventSubscriber {
    private static long a = 94387336124410L;
    private static long[] p;
        private volatile long B;
    private static long[] g;
    private static long m;
    
    private final Set<UUID> c;
        private static long E;
    private static ExecutorService J;
    public static NumberSetting refreshRate;
    private final Map<UUID, Long> x;
    private volatile int O;
    private final Set<UUID> u;
    private volatile long G;
    private volatile boolean o;
    public static ModeSetting mode;
        private final Map<UUID, String> k;

    @Override
    public String g(long var1) {
        return mode.Y();
}
    public Denick(long var1) {
        super(a ^ var1 ^ 0x5694D882672CL);
        this.declare("Denick", Category.Misc, "Resolve Hypixel tablist nicknames", new Setting[0]);
        var1 = a ^ var1;
        this.k = new ConcurrentHashMap<UUID, String>();
        this.x = new ConcurrentHashMap<UUID, Long>();
        this.c = ConcurrentHashMap.newKeySet();
        this.u = ConcurrentHashMap.newKeySet();
}
    @Override
    public void i(long var1) {
        this.c(true, 0L);
}
    private Map<UUID, String> l$r1() {
        HashMap<UUID, String> var1 = new HashMap<UUID, String>();
        for (NetworkPlayerInfo var4 : f.getNetHandler().getPlayerInfoMap()) {
            UUID var5 = var4.getGameProfile().getId();
            if (var5 == null) continue;
            var1.put(var5, var4.getGameProfile().getName());
}
        return var1;
}
    private void w(boolean var3) {
        ++this.O;
        this.o = false;
        this.B = 0L;
        this.G = 0L;
        this.k.clear();
        this.x.clear();
        if (var3) {
            MojangApiClient.S();
}
}
    private void o(short var1, short var2, int var3) {
        long var4 = ((long)var1 << 48 | (long)var2 << 48 >>> 16 | (long)var3 << 32 >>> 32) ^ a;
        if (!this.o) {
            Map<UUID, String> var10 = this.l$r1();
            long var11 = System.currentTimeMillis();
            this.B = var11 + this.p();
            if (var10.isEmpty()) {
                this.k.clear();
                this.x.clear();
            } else {
                HashMap<UUID, String> var13 = new HashMap<UUID, String>(this.k);
                HashMap<UUID, Long> var14 = new HashMap<UUID, Long>(this.x);
                HashMap var15 = new HashMap();
                for (Map.Entry entry : var10.entrySet()) {
                    Long var19;
                    UUID var18 = (UUID)entry.getKey();
                    if (this.u.contains(var18) || var13.containsKey(var18) || (var19 = var14.get(var18)) != null && var19 > var11) continue;
                    var15.put(var18, entry.getValue());
}
                int var20 = this.O;
                if (var15.isEmpty()) {
                    this.u(var20, var10, var13, var14, Collections.emptyMap(), Collections.emptySet());
                } else {
                    this.o = true;
                    J.submit(() -> {
                        try {
                            long var6x = 76186575348656L;
                            int var8x = 28959;
                            HashMap<UUID, String> var11x = new HashMap<UUID, String>();
                            HashMap<UUID, Long> var12 = new HashMap<UUID, Long>();
                            long var13x = System.currentTimeMillis() + 1000L;
                            for (UUID var16 : (Iterable<UUID>)(var15.keySet())) {
                                try {
                                    String var17x = MojangApiClient.d(var8x, var16);
                                    var11x.put(var16, var17x);
                                    f.addScheduledTask(() -> {
                                        long var4xx = 123673313176787L;
                                        long var10001x = var4xx ^ 0x78616C6FA2CL;
                                        int var6xx = 30716;
                                        int var7 = (int)((var4xx ^ 0x78616C6FA2CL) << 32 >>> 48);
                                        int var8xx = (int)(var10001x << 48 >>> 48);
                                        this.F(var20, var16, var6xx, var7, (short)var8xx, var17x, null);
                                    });
}
                                catch (Exception var18x) {
                                    var12.put(var16, var13x);
                                    f.addScheduledTask(() -> {
                                        long var5x = 126046718054918L;
                                        long var10001x = var5x ^ 0x55F70FA44F9L;
                                        int var7 = (int)((var5x ^ 0x55F70FA44F9L) >>> 32);
                                        int var8xx = (int)((var5x ^ 0x55F70FA44F9L) << 32 >>> 48);
                                        int var9x = (int)(var10001x << 48 >>> 48);
                                        this.F(var20, var16, var7, var8xx, (short)var9x, null, var13x);
                                    });
}
}
                            f.addScheduledTask(() -> {
                                long var7 = a ^ 0xCC78FA28F40L;
                                long var9x = var7 ^ 0x14534F9FACBBL;
                                this.u(var20, var10, var13, var14, var11x, var12.keySet());
                            });
}
                        catch (Throwable ex) {
                            throw Sneaky.rethrow(ex);
}
                    });
}
}
}
}
    private void F(int var1, UUID var2, int var3, int var4, short var5, String var6, Long var7) {
        long var8 = ((long)var3 << 32 | (long)var4 << 48 >>> 32 | (long)var5 << 48 >>> 48) ^ a;
        long var10 = var8 ^ 0x99E7FCB771DL;
        if (var1 == this.O && this.o()) {
            if (var6 != null) {
                String var15;
                this.k.put(var2, var6);
                this.x.remove(var2);
                NetworkPlayerInfo var12 = null;
                for (NetworkPlayerInfo var14 : f.getNetHandler().getPlayerInfoMap()) {
                    if (!var2.equals(var14.getGameProfile().getId())) continue;
                    var12 = var14;
                    break;
}
                if (var12 != null && (var15 = var12.getGameProfile().getName()) != null) {
                    if (var15.toLowerCase().contains(var6.toLowerCase())) {
                        this.u.add(var2);
                        return;
}
                    if (!this.c.contains(var2)) {
                        this.c.add(var2);
                        ClientUtil.t(var10, "\u00a7lDenick \u00a7lresolved \u00a7r" + var15 + " \u00a77-> \u00a7b" + var6);
}
}
            } else if (var7 != null) {
                this.x.put(var2, var7);
                this.k.remove(var2);
}
}
}
    public void onPostTick(PostTickEvent var1, long var2) {
        long var9 = System.currentTimeMillis();
        if (var9 >= this.G) {
            this.w(true);
            this.G = var9 + 30000L;
}
        if (!this.o && var9 >= this.B) {
            this.o((short)0, (short)23979, -153849110);
}
}
    private void u(int var1, Map var2, Map var3, Map var4, Map var5, Set var6) {
        this.o = false;
        if (var1 == this.O && this.o()) {
            HashMap<UUID, String> var9 = new HashMap<UUID, String>();
            HashMap<UUID, Long> var10 = new HashMap<UUID, Long>();
            long var11 = System.currentTimeMillis() + 1000L;
            for (UUID var14 : (Iterable<UUID>)(var2.keySet())) {
                String var15;
                String string = var15 = var5.containsKey(var14) ? (String)var5.get(var14) : (String)var3.get(var14);
                if (var15 != null) {
                    var9.put(var14, var15);
                    continue;
}
                Long var16 = (Long)var4.get(var14);
                if (var6.contains(var14)) {
                    var10.put(var14, var11);
                    continue;
}
                if (var16 == null || var16 <= System.currentTimeMillis()) continue;
                var10.put(var14, var16);
}
            this.k.clear();
            this.k.putAll(var9);
            this.x.clear();
            this.x.putAll(var10);
}
}
    public void onPlayerGetName(long var1, PlayerGetNameEvent var3) {
        String var5;
        UUID var4 = var3.u.getGameProfile().getId();
        if (var4 != null && (var5 = this.k.get(var4)) != null && mode.R("DUPLICATE") && !var3.h.toLowerCase().contains(var5.toLowerCase())) {
            var3.N(" \u00a7f(\u00a7b" + var5 + "\u00a7f)");
}
}
    private long p() {
        return refreshRate == null ? 5000L : Math.max(500L, (long)refreshRate.L());
}
    private void c(boolean var1, long var2) {
        this.w(var1);
        this.c.clear();
        this.u.clear();
}
    @Override
    public final void x(long var1, EventBus var3) {
        int var4 = (int)((var1 ^ 0x45E396F2FE86L) >>> 48);
        long var5 = (var1 ^ 0x45E396F2FE86L) << 16 >>> 16;
        DenickBinder.Z((char)var4, var3, this);
}
    @Override
    public void A(long var1) {
        this.c(true, 0L);
}
    static {
        E = 1000L;
        m = 30000L;
        J = Executors.newFixedThreadPool(2);
        refreshRate = new NumberSetting("Refresh-rate", 5000.0f, 500.0f, 15000.0f, 100.0f);
        mode = new ModeSetting("Mode", "DUPLICATE");
}
}