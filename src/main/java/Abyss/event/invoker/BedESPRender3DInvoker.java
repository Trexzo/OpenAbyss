/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.Render3DEvent;
import Abyss.module.impl.visual_utility.BedESP;
import java.io.UnsupportedEncodingException;

public final class BedESPRender3DInvoker
implements EventInvoker {
    final BedESP k;

    public BedESPRender3DInvoker(BedESP var1) {
        this.k = var1;
}
    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = var1 ^ 0x7D57CFDE4F25L;
        this.k.onRender3D(var4, (Render3DEvent)var3);
}
}