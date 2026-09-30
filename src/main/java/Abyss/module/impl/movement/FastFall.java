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
    public void onUpdateWalkingPlayer(UpdateWalkingPlayerEvent var1) {
        if (!(requireScaffold.c() && !ModuleManager.I.o() || !KeyBindUtil.V(FastFall.f.gameSettings.keyBindJump.getKeyCode(), 64165991731362L) || horizontalSpeedRestriction.c() && !(MoveUtil.V() <= 0.02))) {
            if (FastFall.f.thePlayer.onGround) {
                FastFall.f.thePlayer.motionY = 0.42f;
}
            if (FastFall.f.thePlayer.motionY <= 0.0 && FastFall.f.thePlayer.motionY >= -0.09) {
                FastFall.f.thePlayer.motionY = -1.0;
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