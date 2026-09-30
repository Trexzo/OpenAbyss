/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.PlayerGetNameEvent;
import Abyss.event.invoker.AntiNickPlayerGetNameInvoker;
import Abyss.module.impl.misc.AntiNick;

public final class AntiNickBinder {
    private AntiNickBinder() {
}
    public static void I(EventBus var2, AntiNick var3) {
        var2.R(var3, PlayerGetNameEvent.class, 3, new AntiNickPlayerGetNameInvoker(var3));
}
}