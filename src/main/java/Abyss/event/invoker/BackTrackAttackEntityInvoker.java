/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.AttackEntityEvent;
import Abyss.module.impl.combat.BackTrack;

public final class BackTrackAttackEntityInvoker
implements EventInvoker {
    final BackTrack O;

    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x5A2BF3E3BEF8L;
        this.O.onAttackEntity((AttackEntityEvent)var3, var4);
}
    public BackTrackAttackEntityInvoker(BackTrack var1) {
        this.O = var1;
}
}