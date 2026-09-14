/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.ModuleTagEvent;
import Abyss.event.events.PostUpdateEvent;
import Abyss.event.events.PreMouseInputEvent;
import Abyss.event.events.ReceivePacketEvent;
import Abyss.event.events.RedirectIsUsingItemEvent;
import Abyss.event.events.TickEvent;
import Abyss.event.events.UpdateCameraAndRenderEvent;
import Abyss.event.invoker.AutoBlockModuleTagInvoker;
import Abyss.event.invoker.AutoBlockPostUpdateInvoker;
import Abyss.event.invoker.AutoBlockPreMouseInputInvoker;
import Abyss.event.invoker.AutoBlockReceivePacketInvoker;
import Abyss.event.invoker.AutoBlockRedirectIsUsingItemInvoker;
import Abyss.event.invoker.AutoBlockTickInvoker;
import Abyss.event.invoker.AutoBlockUpdateCameraAndRenderInvoker;
import Abyss.module.impl.combat.AutoBlock;

public final class AutoBlockBinder {
    private static final long public static void Z(int var0, EventBus var1, byte var2, AutoBlock var3) {
        var1.R(var3, RedirectIsUsingItemEvent.class, 3, new AutoBlockRedirectIsUsingItemInvoker(var3));
        var1.R(var3, ModuleTagEvent.class, 3, new AutoBlockModuleTagInvoker(var3));
        var1.R(var3, PreMouseInputEvent.class, 3, new AutoBlockPreMouseInputInvoker(var3));
        var1.R(var3, PostUpdateEvent.class, 3, new AutoBlockPostUpdateInvoker(var3));
        var1.R(var3, ReceivePacketEvent.class, 3, new AutoBlockReceivePacketInvoker(var3));
        var1.R(var3, UpdateCameraAndRenderEvent.class, 3, new AutoBlockUpdateCameraAndRenderInvoker(var3));
        var1.R(var3, TickEvent.class, 3, new AutoBlockTickInvoker(var3));
}
    private AutoBlockBinder() {
}
}