/*
 * Decompiled with CFR 0.152.
 */
package Abyss.module.impl.macro;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.Macro2Binder;
import Abyss.event.events.PreTickEvent;
import Abyss.module.Category;
import Abyss.module.MacroModule;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.ModeSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.setting.settings.TextSetting;

public class Macro2
extends MacroModule
implements EventSubscriber {
    public static BooleanSetting swapBack;
    public static ModeSetting mode;
    public static TextSetting chatMessage;
    public static NumberSetting minHealth;
    public static NumberSetting projectilesDuration;

    public Macro2(int var1, int var2, short var3) {
        super(((long)var1 << 32 | (long)var2 << 48 >>> 32 | (long)var3 << 48 >>> 48) ^ 0x6F09CDB64197L ^ 0x7859EDBAAFF1L);
        this.declare("Macro2", Category.Macro, "Macro slot 2 (Must be bound to use)", new Setting[0]);
}
    public void onPreTick(PreTickEvent var3) {
        this.y(mode, projectilesDuration, 40065435448518L, minHealth, swapBack, chatMessage);
}
    @Override
    public final void x(long var1, EventBus var3) {
        Macro2Binder.d(var3, this);
}
    static {
        mode = new ModeSetting("Mode", "PROJECTILES", "ROD", "POT", "GOLDEN_HEAD", "PEARL", "WATER_BUCKET", "LAVA_BUCKET", "CHAT");
        projectilesDuration = new NumberSetting("Projectiles-duration", 100.0f, 30.0f, 1000.0f, 1.0f);
        swapBack = new BooleanSetting("Swap-back", true);
        minHealth = new NumberSetting("Min-health", 13.0f, 0.0f, 20.0f, 1.0f);
        chatMessage = new TextSetting("Chat-message", "");
}
}