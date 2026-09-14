/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.PostUpdateEvent;
import Abyss.event.events.PreMouseInputEvent;
import Abyss.event.events.PreUpdateEvent;
import Abyss.event.events.ReceivePacketEvent;
import Abyss.event.events.UpdateCameraAndRenderEvent;
import Abyss.event.invoker.BlockHitPostUpdateInvoker;
import Abyss.event.invoker.BlockHitPreMouseInputInvoker;
import Abyss.event.invoker.BlockHitPreUpdateInvoker;
import Abyss.event.invoker.BlockHitReceivePacketInvoker;
import Abyss.event.invoker.BlockHitUpdateCameraAndRenderInvoker;
import Abyss.module.impl.combat.BlockHit;

public final class BlockHitBinder {
    private static final long private BlockHitBinder() {
}
    public static void s(EventBus var2, BlockHit var3) {
        var2.R(var3, PreMouseInputEvent.class, 2, new BlockHitPreMouseInputInvoker(var3));
        var2.R(var3, PreUpdateEvent.class, 3, new BlockHitPreUpdateInvoker(var3));
        var2.R(var3, PostUpdateEvent.class, 3, new BlockHitPostUpdateInvoker(var3));
        var2.R(var3, ReceivePacketEvent.class, 3, new BlockHitReceivePacketInvoker(var3));
        var2.R(var3, UpdateCameraAndRenderEvent.class, 3, new BlockHitUpdateCameraAndRenderInvoker(var3));
}
}