/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.Render3DEvent;
import Abyss.module.impl.visual_utility.BlocksESP;
import java.io.UnsupportedEncodingException;

public final class BlocksESPRender3DInvoker
implements EventInvoker {
    final BlocksESP L;

    public BlocksESPRender3DInvoker(BlocksESP var1) {
        this.L = var1;
}
    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = var1 ^ 0x53FFE762EDDEL;
        this.L.onRender3D(var4, (Render3DEvent)var3);
}
}