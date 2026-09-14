/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 */
package Abyss.module.impl.misc;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.AntiNickBinder;
import Abyss.event.events.PlayerGetNameEvent;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.setting.Setting;
import Abyss.setting.settings.TextSetting;
import com.mojang.authlib.GameProfile;
import java.util.UUID;

public class AntiNick
extends Module
implements EventSubscriber {
    private static final long public static TextSetting suffix;

    @Override
    public final void x(long var1, EventBus var3) {
        AntiNickBinder.I(var3, this);
}
    public void onPlayerGetName(PlayerGetNameEvent var1) {
        GameProfile var2 = var1.u.func_178845_a();
        if (AntiNick.isVersion(var2.getId())) {
            var1.N(" " + suffix.X());
}
}
    public AntiNick(long var1) {
        super(0x61486C43D0A1L ^ var1 ^ 0x2E4F41048CDFL);
        this.declare("AntiNick", Category.Misc, "Allows you to see if any player is nicked", new Setting[0]);
        var1 = 0x61486C43D0A1L ^ var1;
}
    private static boolean isVersion(UUID var0) {
        return var0.version() == 1;
}
    private static void a() {
}
    static {
        AntiNick.a();
        suffix = new TextSetting("Suffix", "\u00a7l\u00a7e[\u00a7l\u00a7eNick\u00a7r\u00a7l\u00a7e]\u00a7r");
}
}