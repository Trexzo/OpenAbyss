/*
 * Decompiled with CFR 0.152.
 */
package Abyss.internal.restore;

import Abyss.AbyssClient;
import Abyss.event.EventBus;
import java.util.ArrayList;
import java.util.List;

public final class AbyssAzPump {
    public static AbyssClient INSTANCE;
    public static final List<String> DEGRADED;

    private AbyssAzPump() {
}
    public static AbyssClient install(EventBus var0, long var1, List<String> var2, List<String> var3) {
        if (var0 == null) {
            throw new IllegalStateException("AbyssAzPump: null bus");
}
        if (INSTANCE != null) {
            throw new IllegalStateException("AbyssAzPump: already installed");
}
        AbyssClient var4 = new AbyssClient(0, '\u0000', 0);
        var0.s(var4, 0L);
        INSTANCE = var4;
        if (var2 != null) {
            var2.add("Abyss.AbyssClient");
}
        DEGRADED.clear();
        DEGRADED.add("Abyss/zT_3.F : LAbyss/ModeSetting; -- 3 getstatic / 0 putstatic; read unguarded at AZ.java:476 (AZ.q@150, AZ.r@534)");
        DEGRADED.add("Abyss/zo_4.t : LAbyss/qD; and Abyss/zo_4.x : LAbyss/qk; -- 0 putstatic; read at AZ.java:482 behind zo_4.n(long)");
        if (var3 != null) {
            var3.add("Abyss.AbyssClient    SUBSCRIBED (module pump live). DEGRADED: zT_3.F and zo_4.t/.x are native-written statics that are still null (stage1 item 8), so AZ.r throws a caught, rate-limited NPE after the module loop; the pump itself completes. Fill them to clear this.");
}
        return var4;
}
    static {
        DEGRADED = new ArrayList<String>();
}
}