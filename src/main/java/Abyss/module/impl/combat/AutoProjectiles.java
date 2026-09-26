/*
 * Decompiled with CFR 0.152.
 */
package Abyss.module.impl.combat;

import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.ModeSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.util.TimerUtil;

public class AutoProjectiles
extends Module {
    private static long a;

    public static NumberSetting disableRange;
    public static BooleanSetting allowAutoblock;
    private final TimerUtil x;
    public static NumberSetting range;
    public static BooleanSetting onlyUsePacketWhileAutoblocking;
    public static NumberSetting holdItemDelay;
    public static NumberSetting throwInterval;
    public static NumberSetting throwAmounts;
    public static ModeSetting mode;
        private final TimerUtil c;

    public AutoProjectiles(long var1, short var3) {
        super((0L | (long)var3 << 48 >>> 48) ^ a ^ 0x6331308E3A43L);
        this.declare("AutoProjectiles", Category.Combat, "This module is currently disabled", new Setting[0]);
        this.x = new TimerUtil();
        this.c = new TimerUtil();
}
    static {
        a = 75155036336937L;
        allowAutoblock = new BooleanSetting("Allow-autoblock", true);
        onlyUsePacketWhileAutoblocking = new BooleanSetting("Only-use-packet-while-autoblocking", true);
        disableRange = new NumberSetting("Disable-range", 3.0f, 0.0f, 800.0f, 0.050000004f);
        range = new NumberSetting("Range", 5.0f, 0.0f, 800.0f, 0.050000004f);
        holdItemDelay = new NumberSetting("Hold-item-delay", 100.0f, 0.0f, 800.0f, 0.050000004f);
        throwInterval = new NumberSetting("Throw-interval", 400.0f, 0.0f, 800.0f, 0.050000004f);
        throwAmounts = new NumberSetting("Throw-amounts", 1.0f, 0.0f, 800.0f, 0.050000004f);
        mode = new ModeSetting("Mode", "PACKET", "LEGIT");
}
}