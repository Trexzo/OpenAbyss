/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.inventory.GuiChest
 */
package Abyss.ASM.Hooks.Gui;

import Abyss.ASM.Hooks.CallbackInfo;
import Abyss.module.ModuleManager;
import Abyss.module.impl.player.ChestStealer;
import Abyss.util.BlockUtil;
import Abyss.util.MinecraftRef;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;

public class GuiContainerHooks {
    private static final Minecraft A;

    public static void cancelDrawChest(CallbackInfo var0) {
        if ((GuiContainerHooks.A.field_71462_r instanceof GuiChest || ChestStealer.y) && ModuleManager.q != null && ModuleManager.q.o() && ChestStealer.silent != null && ChestStealer.silent.c() && ChestStealer.chestIntegrityCheck != null && BlockUtil.o(ChestStealer.chestIntegrityCheck.c())) {
            var0.cancel();
}
}
    static {
        long var0 = 91512626691343L;
        int var2 = (int)((var0 ^ 0x1A4B525874C3L) >>> 56);
        long var3 = (var0 ^ 0x1A4B525874C3L) << 8 >>> 8;
        A = MinecraftRef.c((byte)var2, 0L);
}
}