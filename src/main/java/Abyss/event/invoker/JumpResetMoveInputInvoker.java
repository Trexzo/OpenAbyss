/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.MoveInputEvent;
import Abyss.module.impl.combat.JumpReset;

public final class JumpResetMoveInputInvoker
implements EventInvoker {
    final JumpReset x;

    public JumpResetMoveInputInvoker(JumpReset var1) {
        this.x = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x504D0496A6EFL;
        this.x.onMoveInput(var4, (MoveInputEvent)var3);
}
}