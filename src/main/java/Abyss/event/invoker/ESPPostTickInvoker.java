/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PostTickEvent;
import Abyss.module.impl.visual_utility.ESP;

public final class ESPPostTickInvoker
implements EventInvoker {
    final ESP S;

    public ESPPostTickInvoker(ESP var1) {
        this.S = var1;
}
    @Override
    public void c(long var1, Object var3) {
        int var4 = (int)((var1 ^ 0x2142EB7130D6L) >>> 32);
        int var5 = (int)((var1 ^ 0x2142EB7130D6L) << 32 >>> 48);
        int var6 = (int)((var1 ^ 0x2142EB7130D6L) << 48 >>> 48);
        this.S.onPostTick(var4, (char)var5, (PostTickEvent)var3, (short)var6);
}
}