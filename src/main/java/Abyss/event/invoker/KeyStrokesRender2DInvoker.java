/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.Render2DEvent;
import Abyss.module.impl.visual.KeyStrokes;
import java.io.UnsupportedEncodingException;

public final class KeyStrokesRender2DInvoker
implements EventInvoker {
    final KeyStrokes A;

    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = var1 ^ 0x47CB09E38431L;
        this.A.onRender2D(var4, (Render2DEvent)var3);
}
    public KeyStrokesRender2DInvoker(KeyStrokes var1) {
        this.A = var1;
}
}