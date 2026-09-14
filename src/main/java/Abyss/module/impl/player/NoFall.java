/*
 * Decompiled with CFR 0.152.
 */
package Abyss.module.impl.player;

import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.ModeSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.util.TimerUtil;

public class NoFall
extends Module {
    private static long[] b;
    public static BooleanSetting alwaysGroundSpoof;
    private final TimerUtil o;
        public static ModeSetting mode;
    public static NumberSetting fallDistance;
    private final boolean n;
    private final boolean K;
    public static NumberSetting timerSpeed;
    private final boolean L;
    private final boolean C;
    private final boolean x;
    private float S;
    private final boolean r;
    public static NumberSetting groundSpoofTicks;

    public NoFall(byte var1, long var2) {
        super(((long)var1 << 56 | var2 << 8 >>> 8) ^ a ^ 0x294FF7E32C65L);
        this.declare("NoFall", Category.Player, "This module is currently disabled", new Setting[0]);
        this.o = new TimerUtil();
        this.r = false;
        this.K = false;
        this.C = false;
        this.n = false;
        this.L = false;
        this.x = false;
}
    static {
        alwaysGroundSpoof = new BooleanSetting("Always-ground-spoof", true);
        fallDistance = new NumberSetting("Fall-distance", 3.0f, 0.0f, 6.0f, 0.020000001f);
        timerSpeed = new NumberSetting("Timer-speed", 0.7f, 0.0f, 6.0f, 0.020000001f);
        groundSpoofTicks = new NumberSetting("Ground-spoof-ticks", 0.0f, 0.0f, 6.0f, 0.020000001f);
        mode = new ModeSetting("Mode", "NO_GROUND", "ON_GROUND", "JUMP", "TIMER");
}
}