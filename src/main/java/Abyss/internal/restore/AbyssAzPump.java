/*
 * Decompiled with CFR 0.152.
 */
package Abyss.internal.restore;

import Abyss.AbyssClient;
import Abyss.event.EventBus;
import Abyss.module.impl.configuration.VisualSpoof;
import Abyss.module.impl.visual.Freelook;
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
        AbyssAzPump.requireRecoveredStatics();
        return var4;
}
    private static void requireRecoveredStatics() {
        ArrayList<String> missing = new ArrayList<String>();
        if (Freelook.mode == null) {
            missing.add("Freelook.mode");
}
        if (VisualSpoof.t == null) {
            missing.add("VisualSpoof.t");
}
        if (VisualSpoof.keybindToggleRenderVisual == null) {
            missing.add("VisualSpoof.keybindToggleRenderVisual");
}
        if (!missing.isEmpty()) {
            String note = "Abyss.AbyssClient recovered-static regression: " + missing;
            DEGRADED.add(note);
            throw new IllegalStateException(note);
}
}
    static {
        DEGRADED = new ArrayList<String>();
}
}