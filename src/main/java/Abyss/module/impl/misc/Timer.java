/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package Abyss.module.impl.misc;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.TimerBinder;
import Abyss.event.events.Render2DEvent;
import Abyss.internal.accessor.MinecraftAccessor;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.setting.Setting;
import Abyss.setting.settings.NumberSetting;
import Abyss.util.ClientUtil;
import Abyss.util.KeyBindUtil;
import Abyss.util.Sneaky;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;

public class Timer
extends Module
implements EventSubscriber {
    private static Map g;
    private boolean a;
    private static long[] c;
    private float s;
    private static long b;
    private static String[] k;
    private static Object[] h;
    private boolean d;
    public static NumberSetting speed;

    @Override
    public final void x(long var1, EventBus var3) {
        TimerBinder.C(var3, this);
}
    public void onRender2D(long var1, Render2DEvent var3) {
        try {
            boolean var11;
            var1 = b ^ var1;
            int var6 = (int)((var1 ^ 0x74E117C54967L) >>> 48);
            long var7 = (var1 ^ 0x74E117C54967L) << 16 >>> 16;
            long var9 = var1 ^ 0x3007CB0DBCEEL;
            boolean bl = var11 = ClientUtil.I() && Timer.f.currentScreen == null && this.h() != 0 && KeyBindUtil.V(this.h(), var9);
            if (var11 && !this.a) {
                this.u((short)var6, var7);
}
            this.a = var11;
            if (this.o() && ClientUtil.I()) {
                if (!this.d) {
                    this.s = MinecraftAccessor.o((Minecraft)Timer.f).timerSpeed;
                    this.d = true;
}
                MinecraftAccessor.o((Minecraft)Timer.f).timerSpeed = speed.L();
            } else if (this.d) {
                MinecraftAccessor.o((Minecraft)Timer.f).timerSpeed = this.s;
                this.d = false;
}
}
        catch (Throwable ex) {
            throw Sneaky.rethrow(ex);
}
}
    public Timer(long var1) {
        super(b ^ var1 ^ 0x1398105D1C07L);
        this.declare("Timer", Category.Misc, "Modify your game running speed", new Setting[0]);
        var1 = b ^ var1;
        this.d = false;
        this.s = 1.0f;
        this.a = false;
}
    static {
        b = 125743794480863L;
        h = new Object[7];
        k = new String[7];
        g = new HashMap(13);
        c = new long[]{-221930958687381324L, 1319250214599420535L, 4199866987675195430L};
        speed = new NumberSetting("Speed", 1.0f, 0.0f, 5.0f, 0.01f);
}
}