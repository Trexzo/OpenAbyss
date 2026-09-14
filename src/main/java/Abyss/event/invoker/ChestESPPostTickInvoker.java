/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PostTickEvent;
import Abyss.module.impl.visual_utility.ChestESP;
import java.io.UnsupportedEncodingException;

public final class ChestESPPostTickInvoker
implements EventInvoker {
    final ChestESP d;

    public ChestESPPostTickInvoker(ChestESP var1) {
        this.d = var1;
}
    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        this.d.onPostTick((PostTickEvent)var3);
}
}