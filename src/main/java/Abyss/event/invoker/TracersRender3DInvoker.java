/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.Render3DEvent;
import Abyss.module.impl.visual_utility.Tracers;
import java.io.UnsupportedEncodingException;

public final class TracersRender3DInvoker
implements EventInvoker {
    final Tracers K;

    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = var1 ^ 0x1D926C217B19L;
        this.K.onRender3D((Render3DEvent)var3, var4);
}
    public TracersRender3DInvoker(Tracers var1) {
        this.K = var1;
}
}