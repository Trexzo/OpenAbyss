/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.RedirectIsUsingItemEvent;
import Abyss.module.impl.movement.NoSlow;
import java.io.UnsupportedEncodingException;

public final class NoSlowRedirectIsUsingItemInvoker
implements EventInvoker {
    final NoSlow w;

    public NoSlowRedirectIsUsingItemInvoker(NoSlow var1) {
        this.w = var1;
}
    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        int var4 = (int)((var1 ^ 0x83C087603D5L) >>> 56);
        int var5 = (int)((var1 ^ 0x83C087603D5L) << 8 >>> 32);
        int var6 = (int)((var1 ^ 0x83C087603D5L) << 40 >>> 40);
        this.w.onRedirectIsUsingItem((byte)var4, var5, var6, (RedirectIsUsingItemEvent)var3);
}
}