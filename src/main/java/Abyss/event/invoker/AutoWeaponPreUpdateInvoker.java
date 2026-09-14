/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreUpdateEvent;
import Abyss.module.impl.player.AutoWeapon;

public final class AutoWeaponPreUpdateInvoker
implements EventInvoker {
    final AutoWeapon y;

    public AutoWeaponPreUpdateInvoker(AutoWeapon var1) {
        this.y = var1;
}
    @Override
    public void c(long var1, Object var3) {
        this.y.onPreUpdate((PreUpdateEvent)var3);
}
}