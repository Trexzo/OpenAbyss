/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.IsPressedEvent;
import Abyss.module.impl.visual.KeyStrokes;

public final class KeyStrokesIsPressedInvoker
implements EventInvoker {
    final KeyStrokes S;

    public KeyStrokesIsPressedInvoker(KeyStrokes var1) {
        this.S = var1;
}
    @Override
    public void c(long var1, Object var3) {
        this.S.onIsPressed((IsPressedEvent)var3);
}
}