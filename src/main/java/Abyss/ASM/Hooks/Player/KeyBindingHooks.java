/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package Abyss.ASM.Hooks.Player;

import Abyss.ASM.Hooks.CallbackInfoReturnable;
import Abyss.AbyssClient;
import Abyss.event.events.HeldItemChangeEvent;
import Abyss.event.events.IsPressedEvent;
import Abyss.event.events.SetKeyBindStateEvent;
import Abyss.event.events.TickEvent;
import Abyss.util.KeyBindUtil;
import Abyss.util.MinecraftRef;
import net.minecraft.client.Minecraft;

public class KeyBindingHooks {
    private static Minecraft T;
        private static long b;

    public static void onTick(int var0) {
        AbyssClient.w.e(new TickEvent(var0), 18670087776179L);
}
    public static void isPressed(CallbackInfoReturnable<Boolean> var0, String var1, int var2) {
        IsPressedEvent var13 = new IsPressedEvent(KeyBindUtil.m(32881896332787L, var2), var0.getReturnValue());
        AbyssClient.w.e(var13, 18670087776179L);
        if (var13.a()) {
            var0.setReturnValue(false);
        } else if (var0.getReturnValue().booleanValue()) {
            for (int var14 = 0; var14 < (int)b; ++var14) {
                if (!KeyBindingHooks.T.gameSettings.keyBindsHotbar[var14].getKeyDescription().equals(var1)) continue;
                HeldItemChangeEvent var15 = new HeldItemChangeEvent(var14, 0);
                AbyssClient.w.e(var15, 18670087776179L);
                if (!var15.a()) continue;
                var0.setReturnValue(false);
}
}
}
    public static void onSetKeyBindState(int var0, boolean var1) {
        if (var1 && KeyBindingHooks.T.currentScreen == null) {
            AbyssClient.w.e(new SetKeyBindStateEvent(var0), 18670087776179L);
}
}
    static {
        T = MinecraftRef.c((byte)0, 0L);
        b = -4381557878251585527L;
}
}