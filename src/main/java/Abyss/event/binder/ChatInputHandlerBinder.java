/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.GuiChatKeyTypedEvent;
import Abyss.event.events.PostTickEvent;
import Abyss.event.invoker.ChatInputHandlerGuiChatKeyTypedInvoker;
import Abyss.event.invoker.ChatInputHandlerPostTickInvoker;
import Abyss.internal.ChatInputHandler;

public final class ChatInputHandlerBinder {
    public static void A(EventBus var0, ChatInputHandler var3) {
        var0.R(var3, GuiChatKeyTypedEvent.class, 3, new ChatInputHandlerGuiChatKeyTypedInvoker(var3));
        var0.R(var3, PostTickEvent.class, 3, new ChatInputHandlerPostTickInvoker(var3));
}
    private ChatInputHandlerBinder() {
}
}