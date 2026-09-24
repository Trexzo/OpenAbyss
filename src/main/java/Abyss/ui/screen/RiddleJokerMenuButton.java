/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.renderer.GlStateManager
 */
package Abyss.ui.screen;

import Abyss.ui.screen.MainMenuTheme;
import Abyss.util.SoundEngine;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

public class RiddleJokerMenuButton
extends GuiButton {
    private static Map e;

    public int g = 20;
    private boolean p = false;
    private static String b;
    private static long[] c;

    public void drawButton(Minecraft var1, int var2, int var3) {
        if (this.visible) {
            FontRenderer var8 = var1.fontRendererObj;
            GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            boolean bl = this.hovered = var2 >= this.xPosition && var3 >= this.yPosition && var2 < this.xPosition + this.width && var3 < this.yPosition + this.g;
            if (this.p && !this.hovered) {
                this.p = false;
}
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
            GlStateManager.blendFunc((int)770, (int)771);
            var1.getTextureManager().bindTexture(MainMenuTheme.v);
            RiddleJokerMenuButton.drawModalRectWithCustomSizedTexture((int)this.xPosition, (int)this.yPosition, (float)0.0f, (float)0.0f, (int)this.width, (int)this.g, (float)this.width, (float)this.g);
            this.mouseDragged(var1, var2, var3);
            if (this.hovered) {
                if (!this.p) {
                    this.p = true;
                    SoundEngine.y(59424967409495L, b);
}
                var8.drawString(this.displayString, (float)this.xPosition + (float)this.width / 10.0f, (float)this.yPosition + (float)this.g / 12.0f, 0xFFFFA0, true);
            } else {
                var8.drawString(this.displayString, (float)this.xPosition + (float)this.width / 10.0f, (float)this.yPosition + (float)this.g / 12.0f, 0, false);
}
}
}
    public void n(int var1) {
        this.g = var1;
}
    public RiddleJokerMenuButton(int var1, int var2, short var3, int var4, int var5, int var6, int var7, String var8, short var9) {
        super(var1, var4, var5, var6, var7, var8);
}
    static {
        b = "/assets/minecraft/mainmenu/option.ogg";
        e = new HashMap(13);
        c = new long[]{-6108108481085390217L, -5152194304845228098L, -4593200023364279790L, -8218568827744828237L, 3085352055472297028L, -1837421424127876107L, -8233674725960633202L, -4813340029029197693L, -7459030310650241238L};
}
}