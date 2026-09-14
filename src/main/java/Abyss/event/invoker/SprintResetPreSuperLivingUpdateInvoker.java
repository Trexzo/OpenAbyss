/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreSuperLivingUpdateEvent;
import Abyss.module.impl.combat.SprintReset;

public final class SprintResetPreSuperLivingUpdateInvoker
implements EventInvoker {
    final SprintReset K;

    @Override
    public void c(long var1, Object var3) {
        int var4 = (int)((var1 ^ 0x1B41A12E3B45L) >>> 48);
        int var5 = (int)((var1 ^ 0x1B41A12E3B45L) << 16 >>> 32);
        int var6 = (int)((var1 ^ 0x1B41A12E3B45L) << 48 >>> 48);
        this.K.onPreSuperLivingUpdate((short)var4, (PreSuperLivingUpdateEvent)var3, var5, (short)var6);
}
    public SprintResetPreSuperLivingUpdateInvoker(SprintReset var1) {
        this.K = var1;
}
}