/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PostTickEvent;
import Abyss.module.impl.visual_utility.FKCounter;
import java.io.UnsupportedEncodingException;

public final class FKCounterPostTickInvoker
implements EventInvoker {
    final FKCounter b;

    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = var1 ^ 0x1832321A5754L;
        this.b.onPostTick(var4, (PostTickEvent)var3);
}
    public FKCounterPostTickInvoker(FKCounter var1) {
        this.b = var1;
}
}