/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.SetAnglesEvent;
import Abyss.module.impl.combat.AimAssist;

public final class AimAssistSetAnglesInvoker
implements EventInvoker {
    final AimAssist E;

    public AimAssistSetAnglesInvoker(AimAssist var1) {
        this.E = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x2F32F698823FL;
        this.E.onSetAngles((SetAnglesEvent)var3, var4);
}
}