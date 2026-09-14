/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiSlot
 *  org.apache.commons.lang3.StringUtils
 */
package Abyss.internal.auth;

import Abyss.enums.AccountType;
import Abyss.internal.auth.Account;
import Abyss.internal.auth.AltManager;
import Abyss.internal.auth.SessionAccessor;
import Abyss.module.impl.configuration.Theme;
import Abyss.ui.screen.AccountManagerScreen;
import Abyss.util.ChatFormatting;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiSlot;
import org.apache.commons.lang3.StringUtils;

public class AccountListSlot
extends GuiSlot {
        final AccountManagerScreen u;
    
    
    private static long[] h;
    

    public int func_148139_c() {
        return 308;
}
    protected int func_148127_b() {
        return AltManager.Q.size();
}
    protected int func_148138_e() {
        return AltManager.Q.size() * 16;
}
    protected void func_148123_a() {
        this.u.func_146276_q_();
}
    protected void func_148144_a(int var1, boolean var2, int var3, int var4) {
        AccountManagerScreen.C(this.u, var1);
        this.u.func_73876_c();
        if (var2) {
            this.u.func_146284_a(AccountManagerScreen.L(this.u));
}
}
    protected void func_180791_a(int var1, int var2, int var3, int var4, int var5, int var6) {
        String var12;
        FontRenderer var13 = AccountManagerScreen.p(this.u);
        Account var14 = AltManager.Q.get(var1);
        if (this.func_148131_a(var1)) {
            Gui.func_73734_a((int)(var2 - 4), (int)(var3 - 2), (int)(var2 - 2), (int)(var3 + var4 - 2), (int)AccountListSlot.accent());
}
        Gui.func_73734_a((int)(var2 - 2), (int)(var3 + var4 - 1), (int)(var2 + this.func_148139_c() - 4), (int)(var3 + var4), (int)0x30FFFFFF);
        String var15 = var14.h();
        if (StringUtils.isBlank((CharSequence)var15)) {
            var15 = "&7&l?";
}
        if (SessionAccessor.d() != null) {
            if (var14.v() == AccountType.OFFLINE && var15.equals(SessionAccessor.d().func_111285_a())) {
                var15 = String.format("&a&l%s", var15);
            } else if (var14.v() == AccountType.MINECRAFT && var14.h().equals(SessionAccessor.d().func_111285_a())) {
                var15 = String.format("&a&l%s", var15);
}
}
        String var16 = var14.v() == AccountType.OFFLINE ? " &7(Offline)" : " &7(Minecraft)";
        String var17 = ChatFormatting.y(String.format("&r%s", var15));
        String var18 = ChatFormatting.y(var16);
        this.u.func_73731_b(var13, var17, var2 + 2, var3 + 2, -1);
        this.u.func_73731_b(var13, var18, var2 + 2 + var13.func_78256_a(var17), var3 + 2, -1);
        long var19 = System.currentTimeMillis();
        long var21 = var14.F();
        if (var21 < 0L) {
            var12 = "&4&l\u26a0";
        } else if (var21 <= var19) {
            var12 = "&2&l\u2714";
        } else {
            long var23 = var21 - var19;
            long var25 = var23 / 1000L % 60L;
            long var27 = var23 / 60000L % 60L;
            long var29 = var23 / 3600000L % 24L;
            long var31 = var23 / 86400000L;
            var12 = String.format("%s%s%s%s", var31 > 0L ? String.format("%dd", var31) : "", var29 > 0L ? String.format(" %dh", var29) : "", var27 > 0L ? String.format(" %dm", var27) : "", var25 > 0L ? String.format(" %ds", var25) : "");
            var12 = var12.trim();
            var12 = String.format("%s &c&l\u26a0", var12);
}
        var12 = ChatFormatting.y(String.format("&r%s&r", var12));
        this.u.func_73731_b(var13, var12, var2 + this.func_148139_c() - 5 - var13.func_78256_a(var12), var3 + 2, -1);
}
    protected boolean func_148131_a(int var1) {
        return var1 == AccountManagerScreen.W(this.u);
}
    private static int accent() {
        try {
            return Theme.S(0.0, 35338930340239L);
}
        catch (Throwable var1) {
            return -8761857;
}
}
    protected int func_148137_d() {
        return (this.field_148155_a + this.func_148139_c()) / 2 + 2;
}
    public AccountListSlot(long var1, AccountManagerScreen var3, Minecraft var4) {
        super(var4, var3.field_146294_l, var3.field_146295_m, 46, var3.field_146295_m - 64, 16);
        this.u = var3;
}
    static {
}
}