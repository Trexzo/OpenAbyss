/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.Render2DEvent;
import Abyss.event.invoker.ModuleTagRendererRender2DInvoker;
import Abyss.ui.ModuleTagRenderer;

public final class ModuleTagRendererBinder {
    private ModuleTagRendererBinder() {
}
    public static void Q(int var0, EventBus var1, ModuleTagRenderer var2) {
        var1.R(var2, Render2DEvent.class, 3, new ModuleTagRendererRender2DInvoker(var2));
}
}