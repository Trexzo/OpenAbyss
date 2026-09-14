/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.Render2DEvent;
import Abyss.module.impl.visual.ArrayList;
import java.io.UnsupportedEncodingException;

public final class ArrayListRender2DInvoker
implements EventInvoker {
    final ArrayList H;

    public ArrayListRender2DInvoker(ArrayList var1) {
        this.H = var1;
}
    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = var1 ^ 0x76F8B2025B89L;
        this.H.onRender2D((Render2DEvent)var3, var4);
}
}