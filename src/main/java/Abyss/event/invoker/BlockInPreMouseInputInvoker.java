/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreMouseInputEvent;
import Abyss.module.impl.world.BlockIn;
import java.io.UnsupportedEncodingException;

public final class BlockInPreMouseInputInvoker
implements EventInvoker {
    final BlockIn w;

    public BlockInPreMouseInputInvoker(BlockIn var1) {
        this.w = var1;
}
    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = var1 ^ 0x4D54E50E020DL;
        this.w.onPreMouseInput((PreMouseInputEvent)var3, var4);
}
}