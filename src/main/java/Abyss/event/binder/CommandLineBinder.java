/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.PreUpdateEvent;
import Abyss.event.events.Render2DEvent;
import Abyss.event.invoker.CommandLinePreUpdateInvoker;
import Abyss.event.invoker.CommandLineRender2DInvoker;
import Abyss.module.impl.misc.CommandLine;

public final class CommandLineBinder {
    private static final long private CommandLineBinder() {
}
    public static void s(EventBus var0, CommandLine var3) {
        var0.R(var3, Render2DEvent.class, 3, new CommandLineRender2DInvoker(var3));
        var0.R(var3, PreUpdateEvent.class, 3, new CommandLinePreUpdateInvoker(var3));
}
}