/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.module.impl.visual.KeyStrokes;
import java.awt.event.MouseEvent;

public final class KeyStrokesMouseInvoker
implements EventInvoker {
    final KeyStrokes b;

    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x33456EFF6E1FL;
        this.b.onMouse((MouseEvent)var3, var4);
}
    public KeyStrokesMouseInvoker(KeyStrokes var1) {
        this.b = var1;
}
}