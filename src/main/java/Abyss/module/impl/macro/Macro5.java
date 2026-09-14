/*
 * Decompiled with CFR 0.152.
 */
package Abyss.module.impl.macro;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.Macro5Binder;
import Abyss.event.events.PreTickEvent;
import Abyss.module.Category;
import Abyss.module.MacroModule;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.ModeSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.setting.settings.TextSetting;

public class Macro5
extends MacroModule
implements EventSubscriber {
    public static NumberSetting minHealth;
    public static TextSetting chatMessage;
    public static NumberSetting projectilesDuration;
    public static BooleanSetting swapBack;
    private static final long public static ModeSetting mode;

    public Macro5(long var1) {
        super(0x3152ACEB4FCAL ^ var1 ^ 0x2B762164A235L);
        this.declare("Macro5", Category.Macro, "Macro slot 5 (Must be bound to use)", new Setting[0]);
        var1 = 0x3152ACEB4FCAL ^ var1;
}
    public void onPreTick(PreTickEvent var1) {
        this.y(mode, projectilesDuration, 40065435448518L, minHealth, swapBack, chatMessage);
}
    @Override
    public final void x(long var1, EventBus var3) {
        Macro5Binder.j(var3, this);
}
    static {
        projectilesDuration = new NumberSetting("Projectiles-duration", 100.0f, 30.0f, 1000.0f, 1.0f);
        swapBack = new BooleanSetting("Swap-back", true);
        mode = new ModeSetting("Mode", "PROJECTILES", "ROD", "POT", "GOLDEN_HEAD", "PEARL", "WATER_BUCKET", "LAVA_BUCKET", "CHAT");
        minHealth = new NumberSetting("Min-health", 13.0f, 0.0f, 20.0f, 1.0f);
        chatMessage = new TextSetting("Chat-message", "");
}
}