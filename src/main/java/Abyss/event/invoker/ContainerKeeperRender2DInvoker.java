/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.Render2DEvent;
import Abyss.module.impl.misc.ContainerKeeper;
import java.io.UnsupportedEncodingException;

public final class ContainerKeeperRender2DInvoker
implements EventInvoker {
    final ContainerKeeper D;

    public ContainerKeeperRender2DInvoker(ContainerKeeper var1) {
        this.D = var1;
}
    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = (var1 ^ 0x2822D663AE03L) >>> 32;
        int var6 = (int)((var1 ^ 0x2822D663AE03L) << 32 >>> 32);
        this.D.onRender2D(var4, var6, (Render2DEvent)var3);
}
}