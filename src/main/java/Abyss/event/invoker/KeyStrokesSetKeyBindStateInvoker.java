/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.SetKeyBindStateEvent;
import Abyss.module.impl.visual.KeyStrokes;

public final class KeyStrokesSetKeyBindStateInvoker
implements EventInvoker {
    final KeyStrokes S;

    @Override
    public void c(long var1, Object var3) {
        this.S.onSetKeyBindState((SetKeyBindStateEvent)var3);
}
    public KeyStrokesSetKeyBindStateInvoker(KeyStrokes var1) {
        this.S = var1;
}
}