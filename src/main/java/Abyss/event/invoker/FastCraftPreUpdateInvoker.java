/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreUpdateEvent;
import Abyss.module.impl.player.FastCraft;
import java.io.UnsupportedEncodingException;

public final class FastCraftPreUpdateInvoker
implements EventInvoker {
    final FastCraft m;

    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = var1 ^ 0x4DD10541129CL;
        this.m.onPreUpdate(var4, (PreUpdateEvent)var3);
}
    public FastCraftPreUpdateInvoker(FastCraft var1) {
        this.m = var1;
}
}