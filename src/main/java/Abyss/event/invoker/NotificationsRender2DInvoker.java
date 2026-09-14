/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.Render2DEvent;
import Abyss.module.impl.configuration.Notifications;
import java.io.UnsupportedEncodingException;

public final class NotificationsRender2DInvoker
implements EventInvoker {
    final Notifications d;

    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        int var4 = (int)((var1 ^ 0x57B777C6205FL) >>> 32);
        int var5 = (int)((var1 ^ 0x57B777C6205FL) << 32 >>> 48);
        int var6 = (int)((var1 ^ 0x57B777C6205FL) << 48 >>> 48);
        this.d.onRender2D(var4, (Render2DEvent)var3, var5, (char)var6);
}
    public NotificationsRender2DInvoker(Notifications var1) {
        this.d = var1;
}
}