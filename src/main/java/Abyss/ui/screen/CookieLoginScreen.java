/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 */
package Abyss.ui.screen;

import Abyss.internal.auth.CookieAuthService;
import Abyss.ui.screen.AccountManagerScreen;
import Abyss.util.ChatFormatting;
import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class CookieLoginScreen
extends GuiScreen {
    private static long b = 118137226664231L;
    private String O;
    private final GuiScreen a;
    private GuiButton q;
    private CompletableFuture<Boolean> Z;
    private ExecutorService Q;
    private GuiButton k;
    private String M;
    private boolean v = true;

    public void func_146281_b() {
        if (this.Z != null && !this.Z.isDone()) {
            this.Z.cancel(true);
}
        if (this.Q != null && !this.Q.isShutdown()) {
            this.Q.shutdownNow();
}
}
    public void func_73866_w_() {
        this.field_146292_n.clear();
        int var6 = this.field_146294_l / 2;
        int var7 = var6 - 100;
        int var8 = this.field_146295_m / 2 + this.field_146289_q.field_78288_b / 2 + this.field_146289_q.field_78288_b * 2;
        this.k = new GuiButton(0, var7, var8, 200, 20, "Open Cookie File");
        this.field_146292_n.add(this.k);
        this.q = new GuiButton(1, var7, var8 + 20 + 5, 200, 20, "Cancel");
        this.field_146292_n.add(this.q);
        this.O = "&fSelect a cookie file to authenticate&r";
        if (this.Q == null || this.Q.isShutdown()) {
            this.Q = Executors.newSingleThreadExecutor();
}
}
    protected void func_73869_a(char var1, int var2) {
        if (var2 == 1) {
            this.func_146284_a(this.q);
}
}
    protected void func_146284_a(GuiButton var1) {
        if (var1 != null && var1.field_146124_l) {
            switch (var1.field_146127_k) {
                case 0: {
                    try {
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
}
                    catch (Exception exception) {
                        // empty catch block
}
                    this.x("&aOpening file picker...&r");
                    SwingUtilities.invokeLater(() -> {
                        FileDialog var3 = new FileDialog((Frame)null, "Select Cookie File", 0);
                        var3.setDirectory(System.getProperty("user.home") + File.separator + "Downloads");
                        var3.setVisible(true);
                        if (var3.getFile() == null) {
                            this.x("&eFile selection canceled.&r");
                        } else {
                            File var4 = new File(var3.getDirectory(), var3.getFile());
                            if (!var4.exists()) {
                                this.C(() -> {
                                    long var1xx = b ^ 0x6940F553F7A2L;
                                    this.x("&cSelected file does not exist!&r");
                                });
                            } else {
                                this.C(() -> {
                                    long var1xx = b ^ 0x3FF44985B7F8L;
                                    this.v = false;
                                    this.x("&fReading cookie file...&r");
                                });
                                this.Z = CookieAuthService.C(var4, this);
                                this.Z.whenComplete((var1xx, var2x) -> this.C(() -> {
                                    long var5x = 81800336346822L;
                                    this.v = true;
                                    if (Boolean.TRUE.equals(var1xx)) {
                                        this.field_146297_k.func_147108_a((GuiScreen)new AccountManagerScreen(var5x, this.a));
                                    } else if (var2x != null) {
                                        this.x("&cAuthentication failed: " + var2x.getMessage() + "&r");
}
                                }));
}
}
                    });
                    break;
}
                case 1: {
                    this.field_146297_k.func_147108_a(this.a);
                    break;
}
}
}
}
    public CookieLoginScreen(GuiScreen var1, long var2) {
        this.a = var1;
}
    public void x(String var1) {
        if (this.field_146297_k != null) {
            this.field_146297_k.func_152343_a(() -> {
                this.O = var1;
                return this.O;
            });
        } else {
            this.O = var1;
}
}
    private void C(Runnable var1) {
        this.field_146297_k.func_152344_a(var1);
}
    public void func_73863_a(int var1, int var2, float var3) {
        if (this.k != null) {
            this.k.field_146124_l = this.v;
}
        this.func_146276_q_();
        super.func_73863_a(var1, var2, var3);
        this.func_73732_a(this.field_146289_q, "Cookie Authentication", this.field_146294_l / 2, this.field_146295_m / 2 - this.field_146289_q.field_78288_b / 2 - this.field_146289_q.field_78288_b * 2, 0xAAAAAA);
        if (this.O != null) {
            this.func_73732_a(this.field_146289_q, ChatFormatting.y(this.O), this.field_146294_l / 2, this.field_146295_m / 2 - this.field_146289_q.field_78288_b / 2, -1);
}
        if (this.M != null) {
            String var9 = ChatFormatting.y(this.M);
            Gui.func_73734_a((int)0, (int)(this.field_146295_m - 2 - this.field_146289_q.field_78288_b - 3), (int)(3 + this.field_146297_k.field_71466_p.func_78256_a(var9) + 3), (int)this.field_146295_m, (int)0x64000000);
            this.func_73731_b(this.field_146289_q, var9, 3, this.field_146295_m - 2 - this.field_146289_q.field_78288_b, -1);
}
}
}