/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiSlot
 */
package Abyss.ui.screen;

import Abyss.ui.screen.MainMenuStyleScreen;
import Abyss.ui.screen.MainMenuTheme;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiSlot;

class MainMenuStyleList
extends GuiSlot {
    private static long final MainMenuStyleScreen b;

    protected int func_148137_d() {
        return (this.field_148155_a + this.func_148139_c()) / 2 + 2;
}
    protected boolean func_148131_a(int var1) {
        return var1 == MainMenuStyleScreen.F(this.b);
}
    protected int func_148127_b() {
        return MainMenuTheme.mode.S().size();
}
    protected void func_148144_a(int var1, boolean var2, int var3, int var4) {
        MainMenuStyleScreen.b(this.b, var1);
        this.b.func_73876_c();
        if (var2) {
            this.b.func_146284_a(MainMenuStyleScreen.y(this.b));
}
}
    protected void func_180791_a(int var1, int var2, int var3, int var4, int var5, int var6) {
        String var9;
        String var10 = var9 = MainMenuTheme.mode.S().get(var1);
        int var11 = -1;
        if (MainMenuTheme.mode.Y().equals(var9)) {
            var11 = 0xFFAA00;
            var10 = "> " + var10 + " <";
}
        this.b.func_73732_a(MainMenuStyleScreen.X(this.b), var10, this.b.field_146294_l / 2, var3 + 3, var11);
}
    public int func_148139_c() {
        return 200;
}
    public MainMenuStyleList(MainMenuStyleScreen var1, long var2, Minecraft var4) {
        super(var4, var1.field_146294_l, var1.field_146295_m, 32, var1.field_146295_m - 64, 20);
        this.b = var1;
}
    protected void func_148123_a() {
        this.b.func_146276_q_();
}
    protected int func_148138_e() {
        return MainMenuTheme.mode.S().size() * 20;
}
}