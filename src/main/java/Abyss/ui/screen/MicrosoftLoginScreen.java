/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  org.apache.commons.lang3.RandomStringUtils
 */
package Abyss.ui.screen;

import Abyss.internal.auth.Account;
import Abyss.internal.auth.AltManager;
import Abyss.internal.auth.AuthService;
import Abyss.internal.auth.SessionAccessor;
import Abyss.internal.auth.TimedStatusMessage;
import Abyss.ui.screen.AccountManagerScreen;
import Abyss.util.BrowserLauncher;
import Abyss.util.ChatFormatting;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.Session;
import org.apache.commons.lang3.RandomStringUtils;

public class MicrosoftLoginScreen
extends GuiScreen {
    private GuiButton H = null;
    private final GuiScreen J;
    private GuiButton Y = null;
    private final String b;
    private boolean z = true;
    private GuiButton G = null;
    private long o;
    private ExecutorService a = null;
    private int m;
    private String p = null;
    private CompletableFuture<Void> P = null;
    private String i = null;
    private boolean v = false;
    private static long c = 11783191072859L;

    public void updateScreen() {
        if (this.v) {
            this.mc.displayGuiScreen((GuiScreen)new AccountManagerScreen(106134966044692L, this.J, new TimedStatusMessage(ChatFormatting.y(String.format("&aSuccessful login! (%s)&r", SessionAccessor.d().getUsername())), 5000L)));
            this.v = false;
}
        if (this.i != null && !this.v && this.P != null && !this.P.isDone()) {
            long var8 = System.currentTimeMillis();
            if (var8 - this.o >= 200L) {
                this.m = (this.m + 1) % 4;
                this.o = var8;
}
        } else {
            this.m = 0;
}
}
    protected void keyTyped(char var1, int var2) {
        if (var2 == 1) {
            this.actionPerformed(this.G);
}
}
    protected void actionPerformed(GuiButton var1) {
        if (var1 != null && var1.enabled) {
            switch (var1.id) {
                case 0: {
                    BrowserLauncher.F(AuthService.P(this.b));
                    this.i = "&fPlease complete the login in your browser&r";
                    this.p = null;
                    this.o = System.currentTimeMillis();
                    this.m = 0;
                    break;
}
                case 1: {
                    URI var8 = AuthService.P(this.b);
                    if (var8 != null) {
                        BrowserLauncher.Y(var8.toString());
                        this.i = "&aLogin link copied!&r";
                        this.p = null;
                        this.m = 0;
                        break;
}
                    this.i = "&cFailed to get login link.&r";
                    this.p = "&cPlease try again.&r";
                    this.m = 0;
                    break;
}
                case 2: {
                    this.mc.displayGuiScreen(this.J);
}
}
}
}
    public void drawScreen(int var1, int var2, float var3) {
        if (this.H != null) {
            this.H.enabled = this.z;
}
        if (this.Y != null) {
            this.Y.enabled = this.z;
}
        this.drawDefaultBackground();
        super.drawScreen(var1, var2, var3);
        this.drawCenteredString(this.fontRendererObj, "Microsoft Authentication", this.width / 2, this.height / 2 - this.fontRendererObj.FONT_HEIGHT / 2 - this.fontRendererObj.FONT_HEIGHT * 2, 0xAAAAAA);
        if (this.i != null) {
            String var9 = this.i;
            if (this.P != null && !this.P.isDone() && this.p == null) {
                for (int var10 = 0; var10 < this.m; ++var10) {
                    var9 = var9 + ".";
}
}
            this.drawCenteredString(this.fontRendererObj, ChatFormatting.y(var9), this.width / 2, this.height / 2 - this.fontRendererObj.FONT_HEIGHT / 2, -1);
}
        if (this.p != null) {
            this.drawCenteredString(this.fontRendererObj, ChatFormatting.y(this.p), this.width / 2, this.height / 2 + this.fontRendererObj.FONT_HEIGHT / 2 + this.fontRendererObj.FONT_HEIGHT, 0xFFAAAA);
}
}
    public void onGuiClosed() {
        if (this.P != null && !this.P.isDone()) {
            this.P.cancel(true);
            this.a.shutdownNow();
}
}
    public void initGui() {
        this.buttonList.clear();
        int var6 = this.width / 2;
        int var7 = var6 - 100;
        int var8 = this.height / 2 + this.fontRendererObj.FONT_HEIGHT / 2 + this.fontRendererObj.FONT_HEIGHT * 2;
        this.H = new GuiButton(0, var7, var8, 200, 20, "Open Link");
        this.buttonList.add(this.H);
        this.Y = new GuiButton(1, var7, var8 + 20 + 5, 200, 20, "Copy Link");
        this.buttonList.add(this.Y);
        this.G = new GuiButton(2, var7, var8 + 50, 200, 20, "Cancel");
        this.buttonList.add(this.G);
        if (this.P == null) {
            this.i = "&fWaiting for login&r";
            if (this.a == null) {
                this.a = Executors.newSingleThreadExecutor();
}
            AtomicReference<String> var9 = new AtomicReference<String>("");
            AtomicReference<String> var10 = new AtomicReference<String>("");
            this.P = AuthService.S(this.b, this.a).thenComposeAsync((String var1x) -> {
                long var2 = c ^ 0x2E1F3D1786C6L;
                this.z = false;
                this.i = "&fAcquiring Microsoft access tokens&r";
                return AuthService.x(var1x, this.a);
            }, this.a).thenComposeAsync((Map<String, String> var2) -> {
                long var3x = c ^ 0x1F20AACBBF67L;
                this.i = "&fAcquiring Xbox access token.&r";
                var9.set((String)var2.get("refresh_token"));
                return AuthService.M((String)var2.get("access_token"), this.a);
            }, this.a).thenComposeAsync((String var1x) -> {
                long var2 = c ^ 0x2B327CA615F2L;
                this.i = "&fAcquiring Xbox XSTS token&r";
                return AuthService.L(var1x, this.a);
            }, this.a).thenComposeAsync((Map<String, String> var1x) -> {
                long var2 = c ^ 0x61643A20EB02L;
                this.i = "&fAcquiring Minecraft access token&r";
                return AuthService.P((String)var1x.get("Token"), (String)var1x.get("uhs"), this.a);
            }, this.a).thenComposeAsync((String var2) -> {
                long var3x = c ^ 0x340411770159L;
                this.i = "&fFetching your Minecraft profile&r";
                var10.set((String)var2);
                return AuthService.i(var2, this.a);
            }, this.a).thenAccept((Session var3x) -> {
                long var4x = c ^ 0xFF53637347DL;
                long var6x = var4x ^ 0x591F4E372FEAL;
                this.i = null;
                this.p = null;
                Account var8x = new Account((String)var9.get(), (String)var10.get(), var3x.getUsername(), var3x.getPlayerID());
                for (Account var10x : AltManager.Q) {
                    if (!var8x.h().equals(var10x.h())) continue;
                    var8x.G(var10x.F());
                    break;
}
                AltManager.Q.add(var8x);
                AltManager.O(var6x);
                SessionAccessor.k(var3x);
                this.v = true;
            }).exceptionally((Throwable var1x) -> {
                long var2 = c ^ 0x7BAE80648F53L;
                this.z = true;
                this.i = "&cLogin failed!&r";
                Throwable var4x = var1x.getCause();
                this.p = var4x != null && var4x.getMessage() != null ? String.format("&cReason: %s&r", var4x.getMessage()) : "&cUnknown error occurred.&r";
                return null;
            });
}
}
    public MicrosoftLoginScreen(GuiScreen var1) {
        this.J = var1;
        this.b = RandomStringUtils.randomAlphanumeric((int)8);
        this.o = System.currentTimeMillis();
        this.m = 0;
}
}