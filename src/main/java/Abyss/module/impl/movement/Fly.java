/*
 * Decompiled with CFR 0.152.
 */
package Abyss.module.impl.movement;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.FlyBinder;
import Abyss.event.events.MoveFlyingEvent;
import Abyss.event.events.PreUpdateEvent;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.setting.Setting;
import Abyss.setting.settings.NumberSetting;
import Abyss.util.KeyBindUtil;
import Abyss.util.MoveUtil;

public class Fly
extends Module
implements EventSubscriber {
    private double K;
    public static NumberSetting horizontalSpeed;
    public static NumberSetting verticalSpeed;

    private static void a() {
}
    public void onMoveFlying(char var1, int var2, short var3, MoveFlyingEvent var4) {
        long var5 = ((long)var1 << 48 | (long)var2 << 32 >>> 16 | (long)var3 << 48 >>> 48) ^ 0x72276E9B7B7EL;
        long var7 = var5 ^ 0x479F902631FFL;
        if (this.o()) {
            if (Fly.f.thePlayer.posY % 1.0 != 0.0) {
                Fly.f.thePlayer.motionY = this.K;
}
            MoveUtil.y(0.0, var7);
            var4.H((float)MoveUtil.A() * horizontalSpeed.L());
}
}
    @Override
    public void A(long var1) {
        long var3 = var1 ^ 0x711DE0724254L;
        long var5 = var1 ^ 0xF9FDC7ACA5AL;
        Fly.f.thePlayer.motionY = 0.0;
        MoveUtil.y(0.0, var3);
        KeyBindUtil.o(var5, Fly.f.gameSettings.keyBindSneak.getKeyCode());
}
    public Fly(long var1) {
        super(0x72276E9B7B7EL ^ var1 ^ 0x15358AB60AF1L);
        this.declare("Fly", Category.Movement, "Allows you to fly without creative", new Setting[0]);
        var1 = 0x72276E9B7B7EL ^ var1;
        this.K = 0.0;
}
    @Override
    public final void x(long var1, EventBus var3) {
        FlyBinder.x(var3, this);
}
    public void onPreUpdate(PreUpdateEvent var3) {
        this.K = 0.0;
        if (Fly.f.currentScreen == null) {
            if (KeyBindUtil.V(Fly.f.gameSettings.keyBindJump.getKeyCode(), 64165991731362L)) {
                this.K += (double)(verticalSpeed.L() * 0.42f);
}
            if (KeyBindUtil.V(Fly.f.gameSettings.keyBindSneak.getKeyCode(), 64165991731362L)) {
                this.K -= (double)(verticalSpeed.L() * 0.42f);
}
            KeyBindUtil.A(82009306480869L, Fly.f.gameSettings.keyBindSneak.getKeyCode(), false);
}
}
    static {
        Fly.a();
        horizontalSpeed = new NumberSetting("Horizontal-speed", 1.0f, 0.0f, 20.0f, 0.01f);
        verticalSpeed = new NumberSetting("Vertical-speed", 1.0f, 0.0f, 20.0f, 0.01f);
}
}