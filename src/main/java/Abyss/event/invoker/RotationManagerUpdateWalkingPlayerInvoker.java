/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.UpdateWalkingPlayerEvent;
import Abyss.util.RotationManager;

public final class RotationManagerUpdateWalkingPlayerInvoker
implements EventInvoker {
    final RotationManager k;

    @Override
    public void c(long var1, Object var3) {
        int var4 = (int)((var1 ^ 0xE7052C3BEF8L) >>> 32);
        int var5 = (int)((var1 ^ 0xE7052C3BEF8L) << 32 >>> 48);
        int var6 = (int)((var1 ^ 0xE7052C3BEF8L) << 48 >>> 48);
        this.k.onUpdateWalkingPlayer(var4, (char)var5, (char)var6, (UpdateWalkingPlayerEvent)var3);
}
    public RotationManagerUpdateWalkingPlayerInvoker(RotationManager var1) {
        this.k = var1;
}
}