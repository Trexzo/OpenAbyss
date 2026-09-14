/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.Render2DEvent;
import Abyss.module.impl.world.Scaffold;
import java.io.UnsupportedEncodingException;

public final class ScaffoldRender2DInvoker
implements EventInvoker {
    final Scaffold b;

    public ScaffoldRender2DInvoker(Scaffold var1) {
        this.b = var1;
}
    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = var1 ^ 0x1A6D424E91C0L;
        this.b.onRender2D(var4, (Render2DEvent)var3);
}
}