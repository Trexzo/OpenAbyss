/*
 * Decompiled with CFR 0.152.
 */
package Abyss.module.impl.macro;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.Macro3Binder;
import Abyss.event.events.PreTickEvent;
import Abyss.module.Category;
import Abyss.module.MacroModule;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.ModeSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.setting.settings.TextSetting;

public class Macro3
extends MacroModule
implements EventSubscriber {
    public static NumberSetting minHealth;
    public static BooleanSetting swapBack;
    public static NumberSetting projectilesDuration;
    public static TextSetting chatMessage;
    public static ModeSetting mode;

    public void onPreTick(PreTickEvent var3) {
        this.y(mode, projectilesDuration, 40065435448518L, minHealth, swapBack, chatMessage);
}
    @Override
    public final void x(long var1, EventBus var3) {
        Macro3Binder.V(var3, this);
}
    public Macro3(long var1) {
        super(0x537DDC0D8250L ^ var1 ^ 0x3557E0D344B4L);
        this.declare("Macro3", Category.Macro, "Macro slot 3 (Must be bound to use)", new Setting[0]);
        var1 = 0x537DDC0D8250L ^ var1;
}
    static {
        mode = new ModeSetting("Mode", "PROJECTILES", "ROD", "POT", "GOLDEN_HEAD", "PEARL", "WATER_BUCKET", "LAVA_BUCKET", "CHAT");
        projectilesDuration = new NumberSetting("Projectiles-duration", 100.0f, 30.0f, 1000.0f, 1.0f);
        chatMessage = new TextSetting("Chat-message", "");
        swapBack = new BooleanSetting("Swap-back", true);
        minHealth = new NumberSetting("Min-health", 13.0f, 0.0f, 20.0f, 1.0f);
}
}