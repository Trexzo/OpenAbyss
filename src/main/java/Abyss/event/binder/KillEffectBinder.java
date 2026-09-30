/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.EntityJoinWorldEvent;
import Abyss.event.events.HandleChatEvent;
import Abyss.event.events.LivingDeathEvent;
import Abyss.event.events.PostRenderEvent;
import Abyss.event.invoker.KillEffectEntityJoinWorldInvoker;
import Abyss.event.invoker.KillEffectHandleChatInvoker;
import Abyss.event.invoker.KillEffectLivingDeathInvoker;
import Abyss.event.invoker.KillEffectPostRenderInvoker;
import Abyss.event.invoker.Zc_2;
import Abyss.module.impl.visual.KillEffect;

public final class KillEffectBinder {
    public static void P(EventBus var0, KillEffect var1) {
        var0.R(var1, LivingDeathEvent.class, 3, new Zc_2(var1));
        var0.R(var1, LivingDeathEvent.class, 3, new KillEffectLivingDeathInvoker(var1));
        var0.R(var1, HandleChatEvent.class, 3, new KillEffectHandleChatInvoker(var1));
        var0.R(var1, PostRenderEvent.class, 3, new KillEffectPostRenderInvoker(var1));
        var0.R(var1, EntityJoinWorldEvent.class, 3, new KillEffectEntityJoinWorldInvoker(var1));
}
    private KillEffectBinder() {
}
}