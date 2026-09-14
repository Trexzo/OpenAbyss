/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.PreMouseInputEvent;
import Abyss.event.events.PreTickEvent;
import Abyss.event.events.WorldLoadEvent;
import Abyss.event.invoker.AntiFireballPreMouseInputInvoker;
import Abyss.event.invoker.AntiFireballPreTickInvoker;
import Abyss.event.invoker.AntiFireballWorldLoadInvoker;
import Abyss.module.impl.combat.AntiFireball;

public final class AntiFireballBinder {
    private static final long public static void r(EventBus var2, AntiFireball var3) {
        var2.R(var3, PreTickEvent.class, 3, new AntiFireballPreTickInvoker(var3));
        var2.R(var3, PreMouseInputEvent.class, 3, new AntiFireballPreMouseInputInvoker(var3));
        var2.R(var3, WorldLoadEvent.class, 3, new AntiFireballWorldLoadInvoker(var3));
}
    private AntiFireballBinder() {
}
}