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
    private static long public int g = 20;
    private boolean p = false;
    private static String b;
    private static long[] c;

    public void func_146112_a(Minecraft var1, int var2, int var3) {
        if (this.field_146125_m) {
            FontRenderer var8 = var1.field_71466_p;
            GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            boolean bl = this.field_146123_n = var2 >= this.field_146128_h && var3 >= this.field_146129_i && var2 < this.field_146128_h + this.field_146120_f && var3 < this.field_146129_i + this.g;
            if (this.p && !this.field_146123_n) {
                this.p = false;
}
            GlStateManager.func_179147_l();
            GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
            GlStateManager.func_179112_b((int)770, (int)771);
            var1.func_110434_K().func_110577_a(MainMenuTheme.v);
            RiddleJokerMenuButton.func_146110_a((int)this.field_146128_h, (int)this.field_146129_i, (float)0.0f, (float)0.0f, (int)this.field_146120_f, (int)this.g, (float)this.field_146120_f, (float)this.g);
            this.func_146119_b(var1, var2, var3);
            if (this.field_146123_n) {
                if (!this.p) {
                    this.p = true;
                    SoundEngine.y(59424967409495L, b);
}
                var8.func_175065_a(this.field_146126_j, (float)this.field_146128_h + (float)this.field_146120_f / 10.0f, (float)this.field_146129_i + (float)this.g / 12.0f, 0xFFFFA0, true);
            } else {
                var8.func_175065_a(this.field_146126_j, (float)this.field_146128_h + (float)this.field_146120_f / 10.0f, (float)this.field_146129_i + (float)this.g / 12.0f, 0, false);
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