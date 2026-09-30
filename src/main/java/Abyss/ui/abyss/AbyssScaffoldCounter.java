/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.item.ItemStack
 */
package Abyss.ui.abyss;

import Abyss.event.events.Render2DEvent;
import Abyss.module.impl.configuration.Theme;
import Abyss.util.ItemUtil;
import Abyss.util.render.abyss.FontManager;
import Abyss.util.render.abyss.FontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.item.ItemStack;

public final class AbyssScaffoldCounter {
    private AbyssScaffoldCounter() {
}
    public static void render(Render2DEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer == null) {
            return;
}
        int blocks = 0;
        for (int slot = 0; slot < 36; ++slot) {
            ItemStack stack = mc.thePlayer.inventory.getStackInSlot(slot);
            if (stack == null || !ItemUtil.u(stack)) continue;
            blocks += stack.stackSize;
}
        if (!FontManager.isReady()) {
            return;
}
        FontRenderer font = FontManager.get();
        String text = String.valueOf(blocks);
        float cx = (float)event.C.getScaledWidth() / 2.0f;
        float y = (float)event.C.getScaledHeight() / 2.0f + 26.0f;
        int accent = blocks > 32 ? Theme.S(0.0, 35338930340239L) : -43691;
        float textW = font.getWidth(text);
        font.drawStringWithShadow(text, cx - textW / 2.0f, y, -1);
        float half = Math.max(6.0f, Math.min(30.0f, (float)blocks * 0.5f));
        int lineY = (int)(y + 10.0f);
        Gui.drawRect((int)((int)(cx - 30.0f)), (int)lineY, (int)((int)(cx + 30.0f)), (int)(lineY + 1), (int)0x50000000);
        Gui.drawRect((int)((int)(cx - half)), (int)lineY, (int)((int)(cx + half)), (int)(lineY + 1), (int)accent);
}
}