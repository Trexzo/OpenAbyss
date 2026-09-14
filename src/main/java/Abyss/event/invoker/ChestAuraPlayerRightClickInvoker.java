/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PlayerRightClickEvent;
import Abyss.module.impl.player.ChestAura;

public final class ChestAuraPlayerRightClickInvoker
implements EventInvoker {
    final ChestAura l;

    @Override
    public void c(long var1, Object var3) {
        int var4 = (int)((var1 ^ 0x2FA3AB466044L) >>> 48);
        int var5 = (int)((var1 ^ 0x2FA3AB466044L) << 16 >>> 32);
        int var6 = (int)((var1 ^ 0x2FA3AB466044L) << 48 >>> 48);
        this.l.onPlayerRightClick((short)var4, var5, (short)var6, (PlayerRightClickEvent)var3);
}
    public ChestAuraPlayerRightClickInvoker(ChestAura var1) {
        this.l = var1;
}
}