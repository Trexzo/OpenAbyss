/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PreMouseInputEvent;
import Abyss.module.impl.combat.BlockHit;
import java.io.UnsupportedEncodingException;

public final class BlockHitPreMouseInputInvoker
implements EventInvoker {
    final BlockHit l;

    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = (var1 ^ 0x5C28568DFE2FL) >>> 16;
        int var6 = (int)((var1 ^ 0x5C28568DFE2FL) << 48 >>> 48);
        this.l.onPreMouseInput((PreMouseInputEvent)var3, var4, (short)var6);
}
    public BlockHitPreMouseInputInvoker(BlockHit var1) {
        this.l = var1;
}
}