/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityPlayerSP
 */
package Abyss.module.impl.movement;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.StuckBinder;
import Abyss.event.events.MoveEntityEvent;
import Abyss.event.events.MoveEntityWithHeadingEvent;
import Abyss.event.events.MoveInputEvent;
import Abyss.event.events.PreLivingUpdateEvent;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.setting.Setting;
import Abyss.setting.settings.ModeSetting;
import Abyss.setting.settings.NumberSetting;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.entity.EntityPlayerSP;

public class Stuck
extends Module
implements EventSubscriber {
    private static Object[] g;
        private static long[] c;
    private static String[] h;
    public static ModeSetting mode;
    public static NumberSetting pulseDelay;
    private int s;
    private static String b;
    
    private boolean U;

    public void onMoveEntityWithHeading(long var1, MoveEntityWithHeadingEvent var3) {
        if (!this.U && var3.a instanceof EntityPlayerSP) {
            var3.I(21307, 3074332907L);
}
}
    public void onMoveInput(MoveInputEvent var1) {
        var1.i(0.0f);
        var1.A(0.0f);
}
    public void onPreLivingUpdate(PreLivingUpdateEvent var1, long var2) {
        this.U = false;
        Stuck.f.field_71439_g.func_70031_b(false);
        if (this.s > 0) {
            this.s -= 50;
}
        if (Stuck.f.field_71439_g.field_70737_aN != 0) {
            this.U = true;
        } else if (mode.R(b) && this.s <= 0) {
            this.s += (int)(pulseDelay.L() * 50.0f);
            this.U = true;
}
}
    @Override
    public void h(long var1) {
        Stuck.f.field_71439_g.func_70031_b(false);
        this.U = false;
        this.s = 0;
}
    @Override
    public void A(long var1) {
        this.U = false;
        this.s = 0;
}
    public void onMoveEntity(long var1, MoveEntityEvent var3) {
        if (!this.U && var3.D instanceof EntityPlayerSP) {
            var3.I(21307, 3074332907L);
}
}
    @Override
    public final void x(long var1, EventBus var3) {
        StuckBinder.c(var3, this);
}
    public Stuck(long var1, int var3) {
        super((var1 << 32 | (long)var3 << 32 >>> 32) ^ a ^ 0x184B43417065L);
        this.declare("Stuck", Category.Movement, "Stuck you and disable movement", new Setting[0]);
        this.s = 0;
        this.U = false;
}
    static {
        g = new Object[19];
        h = new String[19];
        b = "PULSE";
        e = new HashMap(13);
        c = new long[]{-3466031898384909254L, 5021153881801915561L, -1966682088493162257L, -9193661644592037382L, 8391141268143262382L};
        mode = new ModeSetting("Mode", "PULSE", "NORMAL");
        pulseDelay = new NumberSetting("Pulse-delay", 20.0f, 0.0f, 200.0f, 1.0f);
}
}