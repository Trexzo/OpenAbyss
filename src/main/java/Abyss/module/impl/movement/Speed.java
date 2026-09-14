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
        if (Speed.f.field_71474_y.field_74351_w.func_151470_d() || Speed.f.field_71474_y.field_74370_x.func_151470_d() || Speed.f.field_71474_y.field_74366_z.func_151470_d() || Speed.f.field_71474_y.field_74368_y.func_151470_d()) {
            switch (mode.Y()) {
                case "GROUND_STRAFE": {
                    if (!Speed.f.field_71439_g.field_70122_E) break;
                    MoveUtil.r(speed.L());
                    Speed.f.field_71439_g.func_70664_aZ();
                    break;
}
                case "AUTO_JUMP": {
                    if (!Speed.f.field_71439_g.field_70122_E) {
                        Speed.f.field_71439_g.field_70159_w *= (double)speed.L();
                        Speed.f.field_71439_g.field_70179_y *= (double)speed.L();
                        break;
}
                    Speed.f.field_71439_g.func_70664_aZ();
                    break;
}
                case "VANILLA": {
                    if (!Speed.f.field_71439_g.field_70122_E) {
                        MoveUtil.r((double)(speed.L() * 4.0f) / Math.PI);
                        break;
}
                    Speed.f.field_71439_g.func_70664_aZ();
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