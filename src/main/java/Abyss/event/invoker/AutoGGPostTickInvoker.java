/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PostTickEvent;
import Abyss.module.impl.misc.AutoGG;

public final class AutoGGPostTickInvoker
implements EventInvoker {
    final AutoGG X;

    public AutoGGPostTickInvoker(AutoGG var1) {
        this.X = var1;
}
    @Override
    public void c(long var1, Object var3) {
        int var4 = (int)((var1 ^ 0x747AB1E77A40L) >>> 48);
        int var5 = (int)((var1 ^ 0x747AB1E77A40L) << 16 >>> 48);
        int var6 = (int)((var1 ^ 0x747AB1E77A40L) << 32 >>> 32);
        this.X.onPostTick((short)var4, (PostTickEvent)var3, (char)var5, var6);
}
}