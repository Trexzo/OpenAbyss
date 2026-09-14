/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PostTickEvent;
import Abyss.module.impl.player.NoHitDelay;
import java.io.UnsupportedEncodingException;

public final class NoHitDelayPostTickInvoker
implements EventInvoker {
    final NoHitDelay A;

    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = var1 ^ 0x21C77A6D1147L;
        this.A.onPostTick(var4, (PostTickEvent)var3);
}
    public NoHitDelayPostTickInvoker(NoHitDelay var1) {
        this.A = var1;
}
}