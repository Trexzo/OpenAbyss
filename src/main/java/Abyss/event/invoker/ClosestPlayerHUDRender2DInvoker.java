/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.Render2DEvent;
import Abyss.module.impl.visual_utility.ClosestPlayerHUD;
import java.io.UnsupportedEncodingException;

public final class ClosestPlayerHUDRender2DInvoker
implements EventInvoker {
    final ClosestPlayerHUD B;

    public ClosestPlayerHUDRender2DInvoker(ClosestPlayerHUD var1) {
        this.B = var1;
}
    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = var1 ^ 0x452E701F12BEL;
        this.B.onRender2D((Render2DEvent)var3, var4);
}
}