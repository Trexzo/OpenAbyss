/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.PreUpdateEvent;
import Abyss.event.invoker.AutoWeaponPreUpdateInvoker;
import Abyss.module.impl.player.AutoWeapon;

public final class AutoWeaponBinder {
    private static final long private AutoWeaponBinder() {
}
    public static void N(EventBus var0, AutoWeapon var1) {
        var0.R(var1, PreUpdateEvent.class, 3, new AutoWeaponPreUpdateInvoker(var1));
}
}