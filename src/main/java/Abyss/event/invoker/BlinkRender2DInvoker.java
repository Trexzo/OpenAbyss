/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.Render2DEvent;
import Abyss.module.impl.player.Blink;
import java.io.UnsupportedEncodingException;

public final class BlinkRender2DInvoker
implements EventInvoker {
    final Blink h;

    public BlinkRender2DInvoker(Blink var1) {
        this.h = var1;
}
    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = var1 ^ 0x554378DD2E30L;
        this.h.onRender2D(var4, (Render2DEvent)var3);
}
}