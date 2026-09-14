/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreTickEvent;
import Abyss.internal.MiningEngine;
import java.io.UnsupportedEncodingException;

public final class MiningEnginePreTickInvoker
implements EventInvoker {
    final MiningEngine A;

    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = var1 ^ 0x7B384500D727L;
        this.A.onPreTick((PreTickEvent)var3, var4);
}
    public MiningEnginePreTickInvoker(MiningEngine var1) {
        this.A = var1;
}
}