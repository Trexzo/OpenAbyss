/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PostTickEvent;
import Abyss.module.impl.visual.ArrayList;
import java.io.UnsupportedEncodingException;

public final class ArrayListPostTickInvoker
implements EventInvoker {
    final ArrayList v;

    public ArrayListPostTickInvoker(ArrayList var1) {
        this.v = var1;
}
    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        int var4 = (int)((var1 ^ 0x26A463927497L) >>> 32);
        long var5 = (var1 ^ 0x26A463927497L) << 32 >>> 32;
        this.v.onPostTick((PostTickEvent)var3, var4, var5);
}
}