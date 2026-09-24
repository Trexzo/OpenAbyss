/*
 * Decompiled with CFR 0.152.
 */
package Abyss.module.impl.movement;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.SpeedBinder;
import Abyss.event.events.PreUpdateEvent;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.setting.Setting;
import Abyss.setting.settings.ModeSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.util.MoveUtil;
import java.util.HashMap;
import java.util.Map;

public class Speed
extends Module
implements EventSubscriber {
    private static String[] c;

    private static Map d;

    private static long a = 128241496468786L;

    public static ModeSetting mode;
    private static String[] g;
            public static NumberSetting speed;
        private static Object[] e;

    @Override
    public final void x(long var1, EventBus var3) {
        SpeedBinder.V(var3, this);
}
    @Override
    public String g(long var1) {
        return mode.Y();
}
    public void onPreUpdate(long var1, PreUpdateEvent var3) {
        if (Speed.f.gameSettings.keyBindForward.isKeyDown() || Speed.f.gameSettings.keyBindLeft.isKeyDown() || Speed.f.gameSettings.keyBindRight.isKeyDown() || Speed.f.gameSettings.keyBindBack.isKeyDown()) {
            switch (mode.Y()) {
                case "GROUND_STRAFE": {
                    if (!Speed.f.thePlayer.onGround) break;
                    MoveUtil.r(speed.L());
                    Speed.f.thePlayer.jump();
                    break;
}
                case "AUTO_JUMP": {
                    if (!Speed.f.thePlayer.onGround) {
                        Speed.f.thePlayer.motionX *= (double)speed.L();
                        Speed.f.thePlayer.motionZ *= (double)speed.L();
                        break;
}
                    Speed.f.thePlayer.jump();
                    break;
}
                case "VANILLA": {
                    if (!Speed.f.thePlayer.onGround) {
                        MoveUtil.r((double)(speed.L() * 4.0f) / Math.PI);
                        break;
}
                    Speed.f.thePlayer.jump();
}
}
}
}
    public Speed(long var1) {
        super(a ^ var1 ^ 0x28674387913BL);
        this.declare("Speed", Category.Movement, "Move faster", new Setting[0]);
        var1 = a ^ var1;
}
    static {
        e = new Object[7];
        g = new String[7];
        d = new HashMap(13);
        c = new String[3];
        mode = new ModeSetting("Mode", "GROUND_STRAFE", "AUTO_JUMP", "VANILLA");
        speed = new NumberSetting("Speed", 1.0f, 0.0f, 5.0f, 0.01f);
}
}