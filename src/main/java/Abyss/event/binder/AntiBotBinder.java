/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.PreLivingUpdateEvent;
import Abyss.event.invoker.AntiBotPreLivingUpdateInvoker;
import Abyss.module.impl.misc.AntiBot;

public final class AntiBotBinder {
    private static final long private AntiBotBinder() {
}
    public static void t(EventBus var0, AntiBot var1) {
        var0.R(var1, PreLivingUpdateEvent.class, 3, new AntiBotPreLivingUpdateInvoker(var1));
}
}