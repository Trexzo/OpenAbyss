/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.RedirectIsUsingItemEvent;
import Abyss.module.impl.combat.AutoBlock;

public final class AutoBlockRedirectIsUsingItemInvoker
implements EventInvoker {
    final AutoBlock u;

    public AutoBlockRedirectIsUsingItemInvoker(AutoBlock var1) {
        this.u = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x6D6B73FB7587L;
        this.u.onRedirectIsUsingItem((RedirectIsUsingItemEvent)var3, var4);
}
}