/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreTickEvent;
import Abyss.module.impl.movement.NoJumpDelay;
import java.io.UnsupportedEncodingException;

public final class NoJumpDelayPreTickInvoker
implements EventInvoker {
    final NoJumpDelay V;

    public NoJumpDelayPreTickInvoker(NoJumpDelay var1) {
        this.V = var1;
}
    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = var1 ^ 0x255E80393ACBL;
        this.V.onPreTick((PreTickEvent)var3, var4);
}
}