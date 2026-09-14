/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.Render3DEvent;
import Abyss.module.impl.visual_utility.Trajectories;
import java.io.UnsupportedEncodingException;

public final class TrajectoriesRender3DInvoker
implements EventInvoker {
    final Trajectories O;

    public TrajectoriesRender3DInvoker(Trajectories var1) {
        this.O = var1;
}
    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = var1 ^ 0x3FC02593BB7BL;
        this.O.onRender3D((Render3DEvent)var3, var4);
}
}