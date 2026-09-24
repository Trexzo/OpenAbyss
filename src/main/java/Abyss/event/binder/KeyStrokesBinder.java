/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.IsPressedEvent;
import Abyss.event.events.Render2DEvent;
import Abyss.event.events.SetKeyBindStateEvent;
import Abyss.event.invoker.KeyStrokesIsPressedInvoker;
import Abyss.event.invoker.KeyStrokesMouseInvoker;
import Abyss.event.invoker.KeyStrokesRender2DInvoker;
import Abyss.event.invoker.KeyStrokesSetKeyBindStateInvoker;
import Abyss.module.impl.visual.KeyStrokes;
import java.awt.event.MouseEvent;

public final class KeyStrokesBinder {
    public static void e(EventBus var0, KeyStrokes var3) {
        var0.R(var3, SetKeyBindStateEvent.class, 3, new KeyStrokesSetKeyBindStateInvoker(var3));
        var0.R(var3, MouseEvent.class, 3, new KeyStrokesMouseInvoker(var3));
        var0.R(var3, IsPressedEvent.class, 3, new KeyStrokesIsPressedInvoker(var3));
        var0.R(var3, Render2DEvent.class, 3, new KeyStrokesRender2DInvoker(var3));
}
    private KeyStrokesBinder() {
}
}