/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.MoveInputEvent;
import Abyss.module.impl.world.Scaffold;

public final class ScaffoldMoveInputInvoker
implements EventInvoker {
    final Scaffold H;

    public ScaffoldMoveInputInvoker(Scaffold var1) {
        this.H = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x2B4E0C5CB696L;
        this.H.onMoveInput(var4, (MoveInputEvent)var3);
}
}