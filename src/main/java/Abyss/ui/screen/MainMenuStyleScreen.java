/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  org.lwjgl.input.Keyboard
 */
package Abyss.ui.screen;

import Abyss.module.Modules;
import Abyss.ui.screen.MainMenuStyleList;
import Abyss.ui.screen.MainMenuTheme;
import Abyss.util.Sneaky;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

public class MainMenuStyleScreen
extends GuiScreen {
    private final GuiScreen H;
    private static long b = 88517798541407L;
    private MainMenuStyleList z = null;
    private int h = -1;
    private GuiButton m = null;
    private GuiButton N = null;
    private GuiButton a = null;

    static FontRenderer X(MainMenuStyleScreen var0) {
        return var0.field_146289_q;
}
    static int F(MainMenuStyleScreen var0) {
        return var0.h;
}
    static GuiButton y(MainMenuStyleScreen var0) {
        return var0.N;
}
    public void func_73866_w_() {
        Keyboard.enableRepeatEvents((boolean)true);
        this.field_146292_n.clear();
        this.N = new GuiButton(0, this.field_146294_l / 2 - 150 - 4, this.field_146295_m - 52, 150, 20, "Select");
        this.field_146292_n.add(this.N);
        this.m = new GuiButton(1, this.field_146294_l / 2 + 4, this.field_146295_m - 52, 150, 20, "Cancel");
        this.field_146292_n.add(this.m);
        this.a = new GuiButton(2, 4, this.field_146295_m - 24, 110, 20, this.O());
        this.field_146292_n.add(this.a);
        this.z = new MainMenuStyleList(this, 120694520305086L, this.field_146297_k);
        this.z.func_148134_d(4, 5);
        this.h = this.D();
        this.func_73876_c();
}
    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected void func_146284_a(GuiButton var1) {
        try {
            long var2 = 94303189473383L;
            if (var1 == null) return;
            switch (var1.field_146127_k) {
                case 0: {
                    if (this.h < 0 || this.h >= MainMenuTheme.mode.S().size()) return;
                    String var8 = MainMenuTheme.mode.S().get(this.h);
                    MainMenuTheme.mode.i(var8);
                    Modules.c(0L);
                    return;
}
                case 1: {
                    this.field_146297_k.func_147108_a(this.H);
                    return;
}
                case 2: {
                    MainMenuTheme.music.W(112370683098682L);
                    Modules.c(0L);
                    this.func_73876_c();
                    return;
}
                default: {
                    if (this.z == null) return;
                    this.z.func_148147_a(var1);
}
}
            return;
}
        catch (Throwable ex) {
            throw Sneaky.rethrow(ex);
}
}
    public void func_73863_a(int var1, int var2, float var3) {
        if (this.z != null) {
            this.z.func_148128_a(var1, var2, var3);
}
        super.func_73863_a(var1, var2, var3);
        this.func_73732_a(this.field_146289_q, "Main Menu style", this.field_146294_l / 2, 20, -1);
        if (this.h >= 0 && this.h < MainMenuTheme.mode.S().size()) {
            String var6 = MainMenuTheme.mode.S().get(this.h);
            this.func_73732_a(this.field_146289_q, "Selected: " + var6, this.field_146294_l / 2, this.field_146295_m - 30, -1);
}
}
    private int D() {
        return MainMenuTheme.mode.G();
}
    public void func_146274_d() {
        if (this.z != null) {
            this.z.func_178039_p();
}
        super.func_146274_d();
}
    public MainMenuStyleScreen(GuiScreen var1) {
        this.H = var1;
}
    private String O() {
        return "Music: " + (MainMenuTheme.music.c() ? "ON" : "OFF");
}
    public void func_73876_c() {
        if (this.N != null) {
            boolean bl = this.N.field_146124_l = this.h >= 0;
}
        if (this.a != null) {
            this.a.field_146126_j = this.O();
}
}
    public void func_146281_b() {
        Keyboard.enableRepeatEvents((boolean)false);
}
    static int b(MainMenuStyleScreen var0, int var1) {
        var0.h = var1;
        return var0.h;
}
    protected void func_73869_a(char var1, int var2) {
        switch (var2) {
            case 1: {
                this.func_146284_a(this.m);
                break;
}
            case 28: {
                this.func_146284_a(this.N);
                break;
}
            case 200: {
                if (this.h <= 0) break;
                --this.h;
                break;
}
            case 208: {
                if (this.h >= MainMenuTheme.mode.S().size() - 1) break;
                ++this.h;
}
}
}
}