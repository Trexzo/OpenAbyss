/*
 * Decompiled with CFR 0.152.
 */
package Abyss.module.impl.macro;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.Macro1Binder;
import Abyss.event.events.PreTickEvent;
import Abyss.module.Category;
import Abyss.module.MacroModule;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.ModeSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.setting.settings.TextSetting;

public class Macro1
extends MacroModule
implements EventSubscriber {
    public static BooleanSetting swapBack;
    private static final long b = 20717425069016L;
    public static ModeSetting mode;
    public static TextSetting chatMessage;
    public static NumberSetting minHealth;
    public static NumberSetting projectilesDuration;

    public Macro1(long var1) {
        super(0x12D7A6C30BD8L ^ var1 ^ 0x3F9707E109D5L);
        this.declare("Macro1", Category.Macro, "Macro slot 1 (Must be bound to use)", new Setting[0]);
        var1 = 0x12D7A6C30BD8L ^ var1;
}
    @Override
    public final void x(long var1, EventBus var3) {
        int var4 = (int)((var1 ^ 0x1CE2F7ABB36CL) >>> 48);
        int var5 = (int)((var1 ^ 0x1CE2F7ABB36CL) << 16 >>> 48);
        int var6 = (int)((var1 ^ 0x1CE2F7ABB36CL) << 32 >>> 32);
        Macro1Binder.g(var3, (short)var4, (char)var5, var6, this);
}
    public void onPreTick(PreTickEvent var1) {
        this.y(mode, projectilesDuration, 40065435448518L, minHealth, swapBack, chatMessage);
}
    static {
        minHealth = new NumberSetting("Min-health", 13.0f, 0.0f, 20.0f, 1.0f);
        mode = new ModeSetting("Mode", "PROJECTILES", "ROD", "POT", "GOLDEN_HEAD", "PEARL", "WATER_BUCKET", "LAVA_BUCKET", "CHAT");
        chatMessage = new TextSetting("Chat-message", "");
        swapBack = new BooleanSetting("Swap-back", true);
        projectilesDuration = new NumberSetting("Projectiles-duration", 100.0f, 30.0f, 1000.0f, 1.0f);
}
}