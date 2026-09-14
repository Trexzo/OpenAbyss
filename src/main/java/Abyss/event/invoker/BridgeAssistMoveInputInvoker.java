/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.MoveInputEvent;
import Abyss.module.impl.world.BridgeAssist;

public final class BridgeAssistMoveInputInvoker
implements EventInvoker {
    final BridgeAssist N;

    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x2B3A568C439DL;
        this.N.onMoveInput((MoveInputEvent)var3, var4);
}
    public BridgeAssistMoveInputInvoker(BridgeAssist var1) {
        this.N = var1;
}
}