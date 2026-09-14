/*
 * Decompiled with CFR 0.152.
 */
package Abyss.module.impl.movement;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.FastFallBinder;
import Abyss.event.events.UpdateWalkingPlayerEvent;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.ModuleManager;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.util.KeyBindUtil;
import Abyss.util.MoveUtil;

public class FastFall
extends Module
implements EventSubscriber {
    public static BooleanSetting requireScaffold;
    public static BooleanSetting horizontalSpeedRestriction;
    private static final long public void onUpdateWalkingPlayer(UpdateWalkingPlayerEvent var1) {
        if (!(requireScaffold.c() && !ModuleManager.I.o() || !KeyBindUtil.V(FastFall.f.field_71474_y.field_74314_A.func_151463_i(), 64165991731362L) || horizontalSpeedRestriction.c() && !(MoveUtil.V() <= 0.02))) {
            if (FastFall.f.field_71439_g.field_70122_E) {
                FastFall.f.field_71439_g.field_70181_x = 0.42f;
}
            if (FastFall.f.field_71439_g.field_70181_x <= 0.0 && FastFall.f.field_71439_g.field_70181_x >= -0.09) {
                FastFall.f.field_71439_g.field_70181_x = -1.0;
}
}
}
    @Override
    public final void x(long var1, EventBus var3) {
        FastFallBinder.O(var3, this);
}
    public FastFall(long var1) {
        super(0x69D034317636L ^ var1 ^ 0x256463605669L);
        this.declare("FastFall", Category.Movement, "Fall faster", new Setting[0]);
        var1 = 0x69D034317636L ^ var1;
}
    static {
        horizontalSpeedRestriction = new BooleanSetting("Horizontal-speed-restriction", true);
        requireScaffold = new BooleanSetting("Require-scaffold", true);
}
}