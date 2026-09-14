/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.MoveInputEvent;
import Abyss.util.RotationManager;

public final class RotationManagerMoveInputInvoker
implements EventInvoker {
    final RotationManager S;

    public RotationManagerMoveInputInvoker(RotationManager var1) {
        this.S = var1;
}
    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x1429E4E8B3EDL;
        this.S.onMoveInput((MoveInputEvent)var3, var4);
}
}