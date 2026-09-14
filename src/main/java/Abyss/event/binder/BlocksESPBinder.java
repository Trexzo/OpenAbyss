/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.EntityJoinWorldEvent;
import Abyss.event.events.ReceivePacketEvent;
import Abyss.event.events.Render3DEvent;
import Abyss.event.invoker.BlocksESPEntityJoinWorldInvoker;
import Abyss.event.invoker.BlocksESPReceivePacketInvoker;
import Abyss.event.invoker.BlocksESPRender3DInvoker;
import Abyss.module.impl.visual_utility.BlocksESP;

public final class BlocksESPBinder {
    private static final long private BlocksESPBinder() {
}
    public static void b(EventBus var2, BlocksESP var3) {
        var2.R(var3, EntityJoinWorldEvent.class, 3, new BlocksESPEntityJoinWorldInvoker(var3));
        var2.R(var3, ReceivePacketEvent.class, 3, new BlocksESPReceivePacketInvoker(var3));
        var2.R(var3, Render3DEvent.class, 3, new BlocksESPRender3DInvoker(var3));
}
}