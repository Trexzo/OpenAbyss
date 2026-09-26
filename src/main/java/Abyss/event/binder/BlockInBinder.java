/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.HeldItemChangeEvent;
import Abyss.event.events.PreMouseInputEvent;
import Abyss.event.invoker.BlockInHeldItemChangeInvoker;
import Abyss.event.invoker.BlockInPreMouseInputInvoker;
import Abyss.module.impl.world.BlockIn;

public final class BlockInBinder {
    public static void s(EventBus var0, BlockIn var3) {
        var0.R(var3, HeldItemChangeEvent.class, 3, new BlockInHeldItemChangeInvoker(var3));
        var0.R(var3, PreMouseInputEvent.class, 3, new BlockInPreMouseInputInvoker(var3));
}
    private BlockInBinder() {
}
}