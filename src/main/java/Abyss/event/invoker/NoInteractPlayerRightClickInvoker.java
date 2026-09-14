/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PlayerRightClickEvent;
import Abyss.module.impl.player.NoInteract;

public final class NoInteractPlayerRightClickInvoker
implements EventInvoker {
    final NoInteract s;

    @Override
    public void c(long var1, Object var3) {
        this.s.onPlayerRightClick((PlayerRightClickEvent)var3);
}
    public NoInteractPlayerRightClickInvoker(NoInteract var1) {
        this.s = var1;
}
}