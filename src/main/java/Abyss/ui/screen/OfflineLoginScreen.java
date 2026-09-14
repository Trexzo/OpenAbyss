/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 */
package Abyss.ui.screen;

import Abyss.internal.auth.RandomUsernamePool;
import Abyss.internal.auth.SessionSwapper;
import Abyss.ui.screen.AccountManagerScreen;
import java.util.concurrent.CompletableFuture;
import javax.swing.SwingUtilities;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

public class OfflineLoginScreen
extends GuiScreen {
    private static long private GuiTextField b;
    private GuiButton t;
    private GuiButton C = null;
    private final GuiScreen B;
    private GuiButton q;

    protected void func_73864_a(int var1, int var2, int var3) {
        this.b.func_146192_a(var1, var2, var3);
        super.func_73864_a(var1, var2, var3);
}
    protected void func_146284_a(GuiButton var1) {
        if (var1 != null && var1.field_146124_l) {
            switch (var1.field_146127_k) {
                case 0: {
                    this.m(124399565688198L);
                    break;
}
                case 1: {
                    this.U();
                    break;
}
                case 2: {
                    this.field_146297_k.func_147108_a(this.B);
                    break;
}
}
}
}
    public OfflineLoginScreen(GuiScreen var1) {
        this.B = var1;
}
    public void func_73876_c() {
        this.b.func_146178_a();
}
    protected void func_73869_a(char var1, int var2) {
        this.b.func_146201_a(var1, var2);
        if (var2 == 1) {
            this.field_146297_k.func_147108_a(this.B);
}
}
    public void func_73866_w_() {
        this.b = new GuiTextField(0, this.field_146289_q, this.field_146294_l / 2 - 100, this.field_146295_m / 2 - 30, 200, 20);
        this.b.func_146203_f(16);
        this.b.func_146195_b(true);
        this.t = new GuiButton(0, this.field_146294_l / 2 - 100, this.field_146295_m / 2, 200, 20, "Login");
        this.field_146292_n.add(this.t);
        this.q = new GuiButton(1, this.field_146294_l / 2 - 100, this.field_146295_m / 2 + 25, 200, 20, "Generate Random");
        this.field_146292_n.add(this.q);
        this.C = new GuiButton(2, this.field_146294_l / 2 - 100, this.field_146295_m / 2 + 50, 200, 20, "Cancel");
        this.field_146292_n.add(this.C);
}
    public void func_73863_a(int var1, int var2, float var3) {
        this.func_146276_q_();
        this.func_73732_a(this.field_146289_q, "Offline Authentication", this.field_146294_l / 2, this.field_146295_m / 2 - 60, 0xFFFFFF);
        this.b.func_146194_f();
        super.func_73863_a(var1, var2, var3);
}
    private void m(long var1) {
        boolean var8;
        String var7 = this.b.func_146179_b().trim();
        if (!var7.isEmpty() && (var8 = SessionSwapper.D(var7, 14635617689442L))) {
            this.field_146297_k.func_147108_a((GuiScreen)new AccountManagerScreen(81800336346822L, this.B));
}
}
    private void U() {
        CompletableFuture.runAsync(() -> {
            String var5 = RandomUsernamePool.x(77049211506207L);
            SwingUtilities.invokeLater(() -> this.b.func_146180_a(var5));
        });
}
}