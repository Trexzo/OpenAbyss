/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.PostTickEvent;
import Abyss.internal.ChatInputHandler;
import java.io.UnsupportedEncodingException;

public final class ChatInputHandlerPostTickInvoker
implements EventInvoker {
    final ChatInputHandler C;

    public ChatInputHandlerPostTickInvoker(ChatInputHandler var1) {
        this.C = var1;
}
    @Override
    public void c(long var1, Object var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = var1 ^ 0x4227749592DEL;
        this.C.onPostTick(var4, (PostTickEvent)var3);
}
}