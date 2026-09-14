/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.invoker;

import Abyss.event.EventInvoker;
import Abyss.event.events.GuiChatKeyTypedEvent;
import Abyss.internal.ChatInputHandler;

public final class ChatInputHandlerGuiChatKeyTypedInvoker
implements EventInvoker {
    final ChatInputHandler E;

    @Override
    public void c(long var1, Object var3) {
        int var4 = (int)((var1 ^ 0xA869DAA2363L) >>> 32);
        long var5 = (var1 ^ 0xA869DAA2363L) << 32 >>> 32;
        this.E.onGuiChatKeyTyped((GuiChatKeyTypedEvent)var3, var4, var5);
}
    public ChatInputHandlerGuiChatKeyTypedInvoker(ChatInputHandler var1) {
        this.E = var1;
}
}