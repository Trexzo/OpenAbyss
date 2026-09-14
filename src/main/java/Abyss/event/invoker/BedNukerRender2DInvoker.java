/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.Render2DEvent;
import Abyss.module.impl.world.BedNuker;
import java.io.UnsupportedEncodingException;

public final class BedNukerRender2DInvoker
implements EventInvoker {
    final BedNuker K;

    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = var1 ^ 0x68CF02F69398L;
        this.K.onRender2D(var4, (Render2DEvent)var3);
}
    public BedNukerRender2DInvoker(BedNuker var1) {
        this.K = var1;
}
}