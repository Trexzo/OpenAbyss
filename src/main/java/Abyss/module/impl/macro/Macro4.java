/*
 * Decompiled with CFR 0.152.
 */
package Abyss.module.impl.macro;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.Macro4Binder;
import Abyss.event.events.PreTickEvent;
import Abyss.module.Category;
import Abyss.module.MacroModule;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.ModeSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.setting.settings.TextSetting;

public class Macro4
extends MacroModule
implements EventSubscriber {
    public static NumberSetting projectilesDuration;
    public static BooleanSetting swapBack;
    private static final long b = 52488137415843L;
    public static NumberSetting minHealth;
    public static ModeSetting mode;
    public static TextSetting chatMessage;

    @Override
    public final void x(long var1, EventBus var3) {
        Macro4Binder.o(var3, this);
}
    private static void c() {
}
    public Macro4(long var1) {
        super(0x2FBCD8C91CA3L ^ var1 ^ 0x4743A8725A6EL);
        this.declare("Macro4", Category.Macro, "Macro slot 4 (Must be bound to use)", new Setting[0]);
        var1 = 0x2FBCD8C91CA3L ^ var1;
}
    public void onPreTick(PreTickEvent var3) {
        this.y(mode, projectilesDuration, 40065435448518L, minHealth, swapBack, chatMessage);
}
    static {
        Macro4.c();
        mode = new ModeSetting("Mode", "PROJECTILES", "ROD", "POT", "GOLDEN_HEAD", "PEARL", "WATER_BUCKET", "LAVA_BUCKET", "CHAT");
        minHealth = new NumberSetting("Min-health", 13.0f, 0.0f, 20.0f, 1.0f);
        projectilesDuration = new NumberSetting("Projectiles-duration", 100.0f, 30.0f, 1000.0f, 1.0f);
        swapBack = new BooleanSetting("Swap-back", true);
        chatMessage = new TextSetting("Chat-message", "");
}
}