/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PostTickEvent;
import Abyss.module.impl.visual.BreakProgress;
import java.io.UnsupportedEncodingException;

public final class BreakProgressPostTickInvoker
implements EventInvoker {
    final BreakProgress A;

    public BreakProgressPostTickInvoker(BreakProgress var1) {
        this.A = var1;
}
    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        int var4 = (int)((var1 ^ 0x3DAA45790218L) >>> 32);
        int var5 = (int)((var1 ^ 0x3DAA45790218L) << 32 >>> 48);
        int var6 = (int)((var1 ^ 0x3DAA45790218L) << 48 >>> 48);
        this.A.onPostTick(var4, (PostTickEvent)var3, (short)var5, (short)var6);
}
}