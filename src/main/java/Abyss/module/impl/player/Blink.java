/*
 * Decompiled with CFR 0.152.
 */
package Abyss.module.impl.player;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.BlinkBinder;
import Abyss.event.events.AttackTargetEntityEvent;
import Abyss.event.events.PostUpdateWalkingPlayerEvent;
import Abyss.event.events.Render2DEvent;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.impl.configuration.Font;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.ModeSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.util.Sneaky;
import Abyss.util.TimerUtil;
import Abyss.util.packet.PacketManager;
import Abyss.util.render.CustomFont;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Blink
extends Module
implements EventSubscriber {
    public static NumberSetting pulseTicks;
    private static String[] x;
    
    public static ModeSetting mode;
    private static Object[] v;
    private static Map m;
    public static BooleanSetting showDelay;
    private static long[] n;
    private final TimerUtil t;
    public static BooleanSetting autoDisable;
            public static NumberSetting autoDisableTicks;
    private long C;
    private boolean J;
    private long s;
    public static BooleanSetting turnOffOnHit;
        private long y;
    private boolean k;

    public void onAttackTargetEntity(AttackTargetEntityEvent var1, long var2) {
        try {
            var2 = a ^ var2;
            int var4 = (int)((var2 ^ 0x35923557EF82L) >>> 48);
            long var5 = (var2 ^ 0x35923557EF82L) << 16 >>> 16;
            if (turnOffOnHit.c() && this.J) {
                this.u((short)var4, var5);
}
}
        catch (Throwable ex) {
            throw Sneaky.rethrow(ex);
}
}
    public void onPostUpdateWalkingPlayer(PostUpdateWalkingPlayerEvent var1, long var2) {
        try {
            var2 = a ^ var2;
            int var4 = (int)((var2 ^ 0x73A73FE2BA50L) >>> 48);
            long var5 = (var2 ^ 0x73A73FE2BA50L) << 16 >>> 16;
            if (this.s > 0L) {
                this.s -= 50L;
}
            if (autoDisable.c() && (float)(System.currentTimeMillis() - this.y) > autoDisableTicks.L() * 50.0f) {
                this.u((short)var4, var5);
            } else {
                switch (mode.Y()) {
                    case "NORMAL": {
                        PacketManager.M(true);
                        this.J = true;
                        break;
}
                    case "PULSE": {
                        if (!this.J) {
                            PacketManager.M(true);
                            this.s += (long)(pulseTicks.L() * 50.0f);
                            this.J = true;
}
                        if (this.s > 0L) break;
                        PacketManager.j();
                        PacketManager.M(false);
                        this.J = false;
                        this.s = 0L;
}
}
}
}
        catch (Throwable ex) {
            throw Sneaky.rethrow(ex);
}
}
    public Blink(long var1) {
        super(a ^ var1 ^ 0x79CBEB21B8FCL);
        this.declare("Blink", Category.Player, "Stop outgoing packet and release them at one time", new Setting[0]);
        var1 = a ^ var1;
        this.t = new TimerUtil();
        this.s = 0L;
        this.C = System.currentTimeMillis();
        this.J = false;
        this.k = true;
        this.y = System.currentTimeMillis();
}
    @Override
    public void i(long var1) {
        this.y = System.currentTimeMillis();
}
    @Override
    public void A(long var1) {
        this.s = 0L;
        this.k = true;
        if (this.J) {
            PacketManager.j();
            PacketManager.M(false);
            this.J = false;
}
}
    @Override
    public String g(long var1) {
        return mode.Y();
}
    @Override
    public final void x(long var1, EventBus var3) {
        int var4 = (int)((var1 ^ 0x2A40DE10E5B2L) >>> 48);
        BlinkBinder.o((short)var4, var3, this);
}
    public void onRender2D(long var1, Render2DEvent var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (showDelay.c() && this.J) {
            CustomFont var10 = Font.s(0L);
            if (this.k && this.t.L(10L, true)) {
                this.C = System.currentTimeMillis();
                this.k = false;
}
            String var11 = String.format("%.1f", (double)(System.currentTimeMillis() - this.C) / 1000.0);
            var10.T(37697014677608L, var11, (float)var3.C.func_78326_a() / 2.0f - var10.R(var11, 52019766876817L) / 2.0f, (float)var3.C.func_78328_b() / 2.0f + 75.0f, 0xFFFFFF);
        } else {
            this.k = true;
}
}
    static {
        v = new Object[8];
        x = new String[8];
        e = new HashMap(13);
        d = new String[3];
        m = new HashMap(13);
        r = new HashMap(13);
        n = new long[]{5475581527265492953L, 1411577678159929649L};
        showDelay = new BooleanSetting("Show-delay", true);
        turnOffOnHit = new BooleanSetting("Turn-off-on-hit", false);
        autoDisable = new BooleanSetting("Auto-disable", false);
        pulseTicks = new NumberSetting("Pulse-ticks", 20.0f, 1.0f, 100.0f, 1.0f);
        autoDisableTicks = new NumberSetting("Auto-disable-ticks", 20.0f, 1.0f, 100.0f, 1.0f);
        mode = new ModeSetting("Mode", "NORMAL", "PULSE");
}
}