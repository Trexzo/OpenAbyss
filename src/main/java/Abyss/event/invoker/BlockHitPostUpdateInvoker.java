/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PostUpdateEvent;
import Abyss.module.impl.combat.BlockHit;
import java.io.UnsupportedEncodingException;

public final class BlockHitPostUpdateInvoker
implements EventInvoker {
    final BlockHit W;

    public BlockHitPostUpdateInvoker(BlockHit var1) {
        this.W = var1;
}
    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = var1 ^ 0x650E53CB257DL;
        this.W.onPostUpdate(var4, (PostUpdateEvent)var3);
}
}