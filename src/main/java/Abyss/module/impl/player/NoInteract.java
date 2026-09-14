/*
 * Decompiled with CFR 0.152.
 */
package Abyss.module.impl.player;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.NoInteractBinder;
import Abyss.event.events.PlayerRightClickEvent;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.setting.Setting;
import Abyss.util.BlockUtil;

public class NoInteract
extends Module
implements EventSubscriber {
    private static final long private static void a() {
}
    public NoInteract(long var1) {
        super(0x46A33A3E2A7FL ^ var1 ^ 0x7852AF29E643L);
        this.declare("NoInteract", Category.Player, "Prevent you from interacting with container blocks", new Setting[0]);
        var1 = 0x46A33A3E2A7FL ^ var1;
}
    public void onPlayerRightClick(PlayerRightClickEvent var1) {
        if (BlockUtil.S(var1.a$r2())) {
            var1.I(21307, 3074332907L);
}
}
    @Override
    public final void x(long var1, EventBus var3) {
        NoInteractBinder.y(var3, this);
}
    static {
        NoInteract.a();
}
}