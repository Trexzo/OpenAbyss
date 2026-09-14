/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.ScaledResolution
 *  org.lwjgl.input.Keyboard
 */
package Abyss.ASM.Hooks.Gui;

import Abyss.ASM.Hooks.CallbackInfo;
import Abyss.AbyssClient;
import Abyss.event.events.DrawScreenEvent;
import Abyss.event.events.PostDrawScreenEvent;
import Abyss.internal.accessor.GuiScreenAccessor;
import Abyss.module.Modules;
import Abyss.module.impl.configuration.Gadgets;
import Abyss.module.impl.misc.InputFix;
import Abyss.util.ClientUtil;
import Abyss.util.MinecraftRef;
import Abyss.util.render.VisualSpoofRenderer;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;

public class GuiScreenHooks {
    private static long b;
        private static Minecraft c;

    public static void onPostDrawScreen(GuiScreen var0, int var1, int var2, float var3) throws Throwable {
        if (var0 != null && !VisualSpoofRenderer.H()) {
            VisualSpoofRenderer.L(var1, var2, var3, 86835669792802L);
            ScaledResolution var12 = new ScaledResolution(c);
            PostDrawScreenEvent var13 = new PostDrawScreenEvent(var0, var12);
            AbyssClient.w.e(var13, 18670087776179L);
}
}
    public static void onDrawScreen(GuiScreen var0, CallbackInfo var1) {
        if (var0 != null && !VisualSpoofRenderer.H()) {
            DrawScreenEvent var8 = new DrawScreenEvent(var0);
            AbyssClient.w.e(var8, 18670087776179L);
            if (var8.a()) {
                var1.cancel();
}
}
}
    public static void onHandleKeyboardInput(GuiScreen var0, CallbackInfo var1) throws IOException {
        if (Modules.J(InputFix.class).o()) {
            char var4 = Keyboard.getEventCharacter();
            int var5 = Keyboard.getEventKey();
            if (Keyboard.getEventKeyState() || var4 >= (int)b && var5 == 0) {
                GuiScreenAccessor.J(var0, Keyboard.getEventCharacter(), Keyboard.getEventKey());
}
            c.func_152348_aa();
            var1.cancel();
}
}
    public static boolean shouldCancel() {
        return ClientUtil.I() && Gadgets.noScreenBackground.c();
}
    static {
        c = MinecraftRef.c((byte)0, 0L);
        b = 3298079341916717088L;
}
}