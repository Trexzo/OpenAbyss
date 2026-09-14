/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.EntityJoinWorldEvent;
import Abyss.module.impl.visual_utility.ChestESP;

public final class ChestESPEntityJoinWorldInvoker
implements EventInvoker {
    final ChestESP z;

    @Override
    public void c(long var1, Object var3) {
        this.z.onEntityJoinWorld((EntityJoinWorldEvent)var3);
}
    public ChestESPEntityJoinWorldInvoker(ChestESP var1) {
        this.z = var1;
}
}