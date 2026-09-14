/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.network.NetworkPlayerInfo
 */
package Abyss.ASM.Hooks.Gui;

import Abyss.ASM.Hooks.CallbackInfoReturnable;
import Abyss.AbyssClient;
import Abyss.event.events.PlayerGetNameEvent;
import net.minecraft.client.network.NetworkPlayerInfo;

public class GuiPlayerTabOverlayHooks {
    private static final long public static void onPlayerGetName(NetworkPlayerInfo var0, CallbackInfoReturnable<String> var1) {
        PlayerGetNameEvent var8 = new PlayerGetNameEvent(var0, var1.getReturnValue());
        AbyssClient.w.e(var8, 18670087776179L);
        var1.setReturnValue(var8.d());
}
}