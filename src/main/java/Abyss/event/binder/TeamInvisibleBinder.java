/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.binder;

import Abyss.event.EventBus;
import Abyss.event.events.EntityRenderStateEvent;
import Abyss.event.events.PostRenderCapeEvent;
import Abyss.event.events.PostRenderModelBipedEvent;
import Abyss.event.events.PreRenderCapeEvent;
import Abyss.event.events.PreRenderEntityEvent;
import Abyss.event.events.PreRenderModelBipedEvent;
import Abyss.event.invoker.TeamInvisibleEntityRenderStateInvoker;
import Abyss.event.invoker.TeamInvisiblePostRenderCapeInvoker;
import Abyss.event.invoker.TeamInvisiblePostRenderModelBipedInvoker;
import Abyss.event.invoker.TeamInvisiblePreRenderCapeInvoker;
import Abyss.event.invoker.TeamInvisiblePreRenderEntityInvoker;
import Abyss.event.invoker.TeamInvisiblePreRenderModelBipedInvoker;
import Abyss.module.impl.visual.TeamInvisible;

public final class TeamInvisibleBinder {
    private TeamInvisibleBinder() {
}
    public static void y(EventBus var2, TeamInvisible var3) {
        var2.R(var3, PreRenderEntityEvent.class, 3, new TeamInvisiblePreRenderEntityInvoker(var3));
        var2.R(var3, EntityRenderStateEvent.class, 3, new TeamInvisibleEntityRenderStateInvoker(var3));
        var2.R(var3, PreRenderModelBipedEvent.class, 3, new TeamInvisiblePreRenderModelBipedInvoker(var3));
        var2.R(var3, PostRenderModelBipedEvent.class, 3, new TeamInvisiblePostRenderModelBipedInvoker(var3));
        var2.R(var3, PreRenderCapeEvent.class, 3, new TeamInvisiblePreRenderCapeInvoker(var3));
        var2.R(var3, PostRenderCapeEvent.class, 3, new TeamInvisiblePostRenderCapeInvoker(var3));
}
}