/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.SetKeyBindStateEvent;
import Abyss.module.impl.visual.TabGUI;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public final class TabGUISetKeyBindStateInvoker
implements EventInvoker {
    final TabGUI S;

    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, Throwable, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = var1 ^ 0x39BCDACB0107L;
        this.S.onSetKeyBindState((SetKeyBindStateEvent)var3, var4);
}
    public TabGUISetKeyBindStateInvoker(TabGUI var1) {
        this.S = var1;
}
}