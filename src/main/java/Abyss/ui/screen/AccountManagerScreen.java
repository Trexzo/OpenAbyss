/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  org.apache.commons.lang3.StringUtils
 *  org.lwjgl.input.Keyboard
 */
package Abyss.ui.screen;

import Abyss.enums.AccountType;
import Abyss.internal.auth.Account;
import Abyss.internal.auth.AccountListSlot;
import Abyss.internal.auth.AltManager;
import Abyss.internal.auth.AuthService;
import Abyss.internal.auth.SessionAccessor;
import Abyss.internal.auth.SessionSwapper;
import Abyss.internal.auth.TimedStatusMessage;
import Abyss.module.impl.configuration.Theme;
import Abyss.ui.screen.AddAccountScreen;
import Abyss.util.ChatFormatting;
import Abyss.util.Sneaky;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Keyboard;

public class AccountManagerScreen
extends GuiScreen {
        private int G = -1;
    private GuiButton Q = null;
    private GuiButton i = null;
    private AccountListSlot P = null;
        public static TimedStatusMessage q;
    private CompletableFuture<Void> I = null;
    private static Map g;
    private ExecutorService K = null;
    private static Map k;
    private GuiButton S = null;
    protected final GuiScreen v;
    
    public void func_146284_a(GuiButton var1) {
        block12: {
            try {
                long var2 = 43158565309945L;
                if (var1 == null || !var1.field_146124_l) break block12;
                switch (var1.field_146127_k) {
                    case 0: {
                        if (this.I == null || this.I.isDone()) {
                            Account var11;
                            String var12;
                            if (this.K == null) {
                                this.K = Executors.newSingleThreadExecutor();
}
                            String string = var12 = StringUtils.isBlank((CharSequence)(var11 = AltManager.Q.get(this.G)).h()) ? "???" : var11.h();
                            if (var11.v() == AccountType.OFFLINE) {
                                boolean var15 = SessionSwapper.D(var11.h(), 14635617689442L);
                                q = var15 ? new TimedStatusMessage(ChatFormatting.y(String.format("&aSuccessful login! (%s)&r", var11.h())), 5000L) : new TimedStatusMessage(ChatFormatting.y(String.format("&cFailed to log in! (%s)&r", var11.h())), 5000L);
                                return;
}
                            q = new TimedStatusMessage(ChatFormatting.y(String.format("&7Fetching your Minecraft profile... (%s)&r", var12)), -1L);
                            Account var14 = var11;
                            this.I = ((CompletableFuture)((CompletableFuture)AuthService.i(var14.Y(), this.K).handle((var3, var4x) -> {
                                try {
                                    long var5 = a ^ 0x4FEE5BC92C9FL;
                                    long var7 = var5 ^ 0xC9CB251CD6BL;
                                    long var10001x = var5 ^ 0x1B49E992B7C6L;
                                    int var9x = (int)((var5 ^ 0x1B49E992B7C6L) >>> 48);
                                    int var10x = (int)((var5 ^ 0x1B49E992B7C6L) << 16 >>> 32);
                                    int var11x = (int)(var10001x << 48 >>> 48);
                                    if (var3 != null) {
                                        var14.J(var3.func_111285_a());
                                        AltManager.O(var7);
                                        SessionAccessor.k(var3);
                                        q = new TimedStatusMessage(ChatFormatting.y(String.format("&aSuccessful login! (%s)&r", var14.h())), 5000L);
                                        return CompletableFuture.completedFuture(null);
}
                                    q = new TimedStatusMessage(ChatFormatting.y(String.format("&7Refreshing Microsoft access tokens... (%s)&r", var12)), -1L);
                                    return ((CompletableFuture)((CompletableFuture)((CompletableFuture)((CompletableFuture)AuthService.A(var14.d(), this.K).thenComposeAsync(var2xx -> {
                                        try {
                                            long var3x = a ^ 0x3891B84AC8EDL;
                                            long var10001xx = var3x ^ 0x6C360A1153B4L;
                                            int var5x = (int)((var3x ^ 0x6C360A1153B4L) >>> 48);
                                            int var6x = (int)((var3x ^ 0x6C360A1153B4L) << 16 >>> 32);
                                            int var7x = (int)(var10001xx << 48 >>> 48);
                                            q = new TimedStatusMessage(ChatFormatting.y(String.format("&7Acquiring Xbox access token... (%s)&r", var12)), -1L);
                                            return AuthService.M((String)var2xx.get("access_token"), this.K);
}
                                        catch (Throwable ex) {
                                            throw Sneaky.rethrow(ex);
}
                                    }, (Executor)this.K)).thenComposeAsync(var2xx -> {
                                        try {
                                            long var3x = a ^ 0x6447FFD16C3DL;
                                            long var10001xx = var3x ^ 0x30E04D8AF764L;
                                            int var5x = (int)((var3x ^ 0x30E04D8AF764L) >>> 48);
                                            int var6x = (int)((var3x ^ 0x30E04D8AF764L) << 16 >>> 32);
                                            int var7x = (int)(var10001xx << 48 >>> 48);
                                            q = new TimedStatusMessage(ChatFormatting.y(String.format("&7Acquiring Xbox XSTS token... (%s)&r", var12)), -1L);
                                            return AuthService.L(var2xx, this.K);
}
                                        catch (Throwable ex) {
                                            throw Sneaky.rethrow(ex);
}
                                    }, (Executor)this.K)).thenComposeAsync(var2xx -> {
                                        try {
                                            long var3x = a ^ 0x534BC8B22A70L;
                                            long var10001xx = var3x ^ 0x7EC7AE9B129L;
                                            int var5x = (int)((var3x ^ 0x7EC7AE9B129L) >>> 48);
                                            int var6x = (int)((var3x ^ 0x7EC7AE9B129L) << 16 >>> 32);
                                            int var7x = (int)(var10001xx << 48 >>> 48);
                                            q = new TimedStatusMessage(ChatFormatting.y(String.format("&7Acquiring Minecraft access token... (%s)&r", var12)), -1L);
                                            return AuthService.P((String)var2xx.get("Token"), (String)var2xx.get("uhs"), this.K);
}
                                        catch (Throwable ex) {
                                            throw Sneaky.rethrow(ex);
}
                                    }, (Executor)this.K)).thenComposeAsync(var2xx -> {
                                        try {
                                            long var3x = a ^ 0x787306F84EEEL;
                                            long var10001xx = var3x ^ 0x2CD4B4A3D5B7L;
                                            int var5x = (int)((var3x ^ 0x2CD4B4A3D5B7L) >>> 48);
                                            int var6x = (int)((var3x ^ 0x2CD4B4A3D5B7L) << 16 >>> 32);
                                            int var7x = (int)(var10001xx << 48 >>> 48);
                                            q = new TimedStatusMessage(ChatFormatting.y(String.format("&7Fetching your Minecraft profile... (%s)&r", var12)), -1L);
                                            return AuthService.i(var2xx, this.K);
}
                                        catch (Throwable ex) {
                                            throw Sneaky.rethrow(ex);
}
                                    }, (Executor)this.K)).thenAccept(var1xx -> {
                                        try {
                                            long var2xx = 6502544405800L;
                                            long var4xx = 101554584226764L;
                                            long var10001xx = var2xx ^ 0x4E61A2F10449L;
                                            int var6x = (int)((var2xx ^ 0x4E61A2F10449L) >>> 48);
                                            int var7x = (int)((var2xx ^ 0x4E61A2F10449L) << 16 >>> 32);
                                            int var8x = (int)(var10001xx << 48 >>> 48);
                                            var14.J(var1xx.func_111285_a());
                                            AltManager.O(var4xx);
                                            SessionAccessor.k(var1xx);
                                            q = new TimedStatusMessage(ChatFormatting.y(String.format("&aSuccessful login! (%s)&r", var14.h())), 5000L);
}
                                        catch (Throwable ex) {
                                            throw Sneaky.rethrow(ex);
}
                                    });
}
                                catch (Throwable ex) {
                                    throw Sneaky.rethrow(ex);
}
                            })).thenComposeAsync(var0 -> var0, (Executor)this.K)).exceptionally(var1x -> {
                                try {
                                    long var2x = 83056452077633L;
                                    long var10001x = var2x ^ 0x2497AF920L;
                                    int var5 = (int)((var2x ^ 0x2497AF920L) << 16 >>> 32);
                                    int var6x = (int)(var10001x << 48 >>> 48);
                                    q = new TimedStatusMessage(ChatFormatting.y(String.format("&c%s (%s)&r", var1x.getMessage(), var12)), 5000L);
                                    return null;
}
                                catch (Throwable ex) {
                                    throw Sneaky.rethrow(ex);
}
                            });
}
                        break;
}
                    case 1: {
                        this.field_146297_k.func_147108_a((GuiScreen)new AddAccountScreen(this.v));
                        break;
}
                    case 2: {
                        if (this.G > -1 && this.G < AltManager.Q.size()) {
                            AltManager.Q.remove(this.G);
                            AltManager.O(101554584226764L);
                            this.G = -1;
                            this.func_73876_c();
}
                        break;
}
                    case 3: {
                        this.field_146297_k.func_147108_a(AccountManagerScreen.Z(this.v));
                        break;
}
                    default: {
                        this.P.func_148147_a(var1);
}
}
}
            catch (Throwable ex) {
                throw Sneaky.rethrow(ex);
}
}
}
    public static GuiButton L(AccountManagerScreen var0) {
        return var0.i;
}
    public void func_73863_a(int var1, int var2, float var3) {
        try {
            long var4 = 32992442628228L;
            if (this.P != null) {
                this.P.func_148128_a(var1, var2, var3);
}
            int accent = AccountManagerScreen.accent();
            int cx = this.field_146294_l / 2;
            Gui.func_73734_a((int)0, (int)0, (int)this.field_146294_l, (int)45, (int)-1811347184);
            Gui.func_73734_a((int)0, (int)45, (int)this.field_146294_l, (int)46, (int)accent);
            Gui.func_73734_a((int)0, (int)(this.field_146295_m - 58), (int)this.field_146294_l, (int)this.field_146295_m, (int)-1811347184);
            Gui.func_73734_a((int)0, (int)(this.field_146295_m - 58), (int)this.field_146294_l, (int)(this.field_146295_m - 57), (int)accent);
            super.func_73863_a(var1, var2, var3);
            this.func_73732_a(this.field_146289_q, ChatFormatting.y(String.format("&r&lAbyss &r&7Alt Manager &8(&7%s&8)&r", AltManager.Q.size())), cx, 8, -1);
            String var9 = ChatFormatting.y(String.format("&8Logged in: &f%s&r", SessionAccessor.d().func_111285_a()));
            this.func_73731_b(this.field_146297_k.field_71466_p, var9, 4, 4, -1);
            if (q != null && !q.b()) {
                String var10 = q.o();
                int var11 = this.field_146297_k.field_71466_p.func_78256_a(var10);
                Gui.func_73734_a((int)(cx - var11 / 2 - 4), (int)22, (int)(cx + var11 / 2 + 4), (int)(24 + this.field_146297_k.field_71466_p.field_78288_b), (int)-871691500);
                Gui.func_73734_a((int)(cx - var11 / 2 - 4), (int)21, (int)(cx + var11 / 2 + 4), (int)22, (int)accent);
                this.func_73732_a(this.field_146297_k.field_71466_p, var10, cx, 24, -1);
}
}
        catch (Throwable ex) {
            throw Sneaky.rethrow(ex);
}
}
    private static int accent() {
        try {
            return Theme.S(0.0, 35338930340239L);
}
        catch (Throwable var1) {
            return -8761857;
}
}
    public void func_146281_b() {
        Keyboard.enableRepeatEvents((boolean)false);
        if (this.I != null && !this.I.isDone()) {
            this.I.cancel(true);
            this.K.shutdownNow();
}
}
    public static int W(AccountManagerScreen var0) {
        return var0.G;
}
    public AccountManagerScreen(long var1, GuiScreen var3, TimedStatusMessage var4) {
        this.v = var3;
        q = var4;
}
    public static FontRenderer p(AccountManagerScreen var0) {
        return var0.field_146289_q;
}
    public void func_146274_d() {
        if (this.P != null) {
            this.P.func_178039_p();
}
        super.func_146274_d();
}
    public AccountManagerScreen(long var1, GuiScreen var3) {
        this.v = var3;
}
    public static int C(AccountManagerScreen var0, int var1) {
        var0.G = var1;
        return var0.G;
}
    public void func_73876_c() {
        if (this.i != null && this.Q != null) {
            this.i.field_146124_l = this.Q.field_146124_l = this.G >= 0;
            if (this.I != null && !this.I.isDone()) {
                this.i.field_146124_l = false;
}
}
}
    private static GuiScreen Z(GuiScreen var0) {
        while (true) {
            if (var0 instanceof AccountManagerScreen) {
                var0 = ((AccountManagerScreen)var0).v;
                continue;
}
            if (!(var0 instanceof AddAccountScreen)) {
                return var0;
}
            var0 = ((AddAccountScreen)var0).j();
}
}
    public void func_73866_w_() {
        try {
            long var1 = 74020945541135L;
            AltManager.Q(17200, (short)3883, (short)-9723);
            Keyboard.enableRepeatEvents((boolean)true);
            this.field_146292_n.clear();
            this.i = new GuiButton(0, this.field_146294_l / 2 - 150 - 4, this.field_146295_m - 52, 150, 20, "Login");
            this.field_146292_n.add(this.i);
            this.field_146292_n.add(new GuiButton(1, this.field_146294_l / 2 + 4, this.field_146295_m - 52, 150, 20, "Add Account"));
            this.Q = new GuiButton(2, this.field_146294_l / 2 - 150 - 4, this.field_146295_m - 28, 150, 20, "Delete");
            this.field_146292_n.add(this.Q);
            this.S = new GuiButton(3, this.field_146294_l / 2 + 4, this.field_146295_m - 28, 150, 20, "Back");
            this.field_146292_n.add(this.S);
            this.P = new AccountListSlot(58253071927924L, this, this.field_146297_k);
            this.P.func_148134_d(11, 12);
            this.func_73876_c();
}
        catch (Throwable ex) {
            throw Sneaky.rethrow(ex);
}
}
    protected void func_73869_a(char var1, int var2) {
        switch (var2) {
            case 1: {
                this.func_146284_a(this.S);
                break;
}
            case 28: {
                this.func_146284_a(this.i);
                break;
}
            case 200: {
                if (this.G <= 0) break;
                --this.G;
                if (!AccountManagerScreen.func_146271_m()) break;
                Collections.swap(AltManager.Q, this.G, this.G + 1);
                AltManager.O(101554584226764L);
                break;
}
            case 208: {
                if (this.G >= AltManager.Q.size() - 1) break;
                ++this.G;
                if (!AccountManagerScreen.func_146271_m()) break;
                Collections.swap(AltManager.Q, this.G, this.G - 1);
                AltManager.O(101554584226764L);
                break;
}
            case 211: {
                this.func_146284_a(this.Q);
}
}
        if (AccountManagerScreen.func_175280_f((int)var2) && this.G >= 0) {
            AccountManagerScreen.func_146275_d((String)AltManager.Q.get(this.G).h());
}
}
    static {
        q = null;
        d = new HashMap(13);
        c = new String[20];
        g = new HashMap(13);
        k = new HashMap(13);
}
}