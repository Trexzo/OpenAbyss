/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreUpdateEvent;
import Abyss.module.impl.misc.CommandLine;

public final class CommandLinePreUpdateInvoker
implements EventInvoker {
    final CommandLine w;

    @Override
    public void c(long var1, Object var3) {
        long var4 = var1 ^ 0x4761C5F2355DL;
        this.w.onPreUpdate((PreUpdateEvent)var3, var4);
}
    public CommandLinePreUpdateInvoker(CommandLine var1) {
        this.w = var1;
}
}