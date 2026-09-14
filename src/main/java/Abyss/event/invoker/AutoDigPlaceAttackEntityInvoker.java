/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.AttackEntityEvent;
import Abyss.module.impl.world.AutoDigPlace;

public final class AutoDigPlaceAttackEntityInvoker
implements EventInvoker {
    final AutoDigPlace V;

    public AutoDigPlaceAttackEntityInvoker(AutoDigPlace var1) {
        this.V = var1;
}
    @Override
    public void c(long var1, Object var3) {
        this.V.onAttackEntity((AttackEntityEvent)var3);
}
}