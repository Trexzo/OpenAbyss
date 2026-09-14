/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  org.lwjgl.input.Keyboard
 */
package Abyss.ui.screen;

import Abyss.internal.auth.Account;
import Abyss.internal.auth.AccountLookupService;
import Abyss.internal.auth.AltManager;
import Abyss.internal.auth.TimedStatusMessage;
import Abyss.ui.GuiTextWidget;
import Abyss.ui.screen.AccountManagerScreen;
import Abyss.util.ChatFormatting;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

public class RefreshTokenLoginScreen
extends GuiScreen {
        private static Pattern N;
    private GuiButton d;
    private GuiButton R;
    private ExecutorService u;
        private CompletableFuture<Void> i;
    private String U = "\u00a77Enter Microsoft OAuth refresh token(s)\u00a7r";
    private final GuiScreen X;
        private static long j;
    
        private GuiTextWidget W;
    

    private void l(long var1, String var3, int var4) {
        List var10;
        long var5 = (0x79AE00000000L | (long)var4 << 32 >>> 32) ^ a;
        int var7 = (int)((var5 ^ 0x4C7BAF8E69FCL) >>> 32);
        int var8 = (int)((var5 ^ 0x4C7BAF8E69FCL) << 32 >>> 48);
        int var9 = (int)((var5 ^ 0x4C7BAF8E69FCL) << 48 >>> 48);
        if (this.u == null || this.u.isShutdown()) {
            this.u = Executors.newFixedThreadPool(3);
}
        if ((var10 = RefreshTokenLoginScreen.m(var7, var8, var3, (short)var9)).isEmpty()) {
            this.U = "\u00a7cNo valid refresh tokens found.\u00a7r";
        } else {
            this.U = "\u00a77Processing accounts...\u00a7r";
            this.R.field_146124_l = false;
            ArrayList<CompletionStage> var11 = new ArrayList<CompletionStage>();
            ArrayList var12 = new ArrayList();
            ArrayList var13 = new ArrayList();
            for (String var15 : var10) {
                CompletionStage var16 = ((CompletableFuture)AccountLookupService.Y(var15, this.u).thenAcceptAsync(var2 -> {
                    Optional<Account> var3x = AltManager.Q.stream().filter(var2x -> var2x.d().equals(var15) || var2x.Y().equals(var2.Y())).findFirst();
                    if (var3x.isPresent()) {
                        Account var4x = var3x.get();
                        var4x.H(var2.d());
                        var4x.r(var2.Y());
                        var4x.J(var2.h());
                        var4x.j(var2.f());
                    } else {
                        AltManager.Q.add((Account)var2);
}
                    var13.add(var2.h());
                }, (Executor)this.u)).exceptionally(var2 -> {
                    long var3x = a ^ 0x6EAE3E2D0720L;
                    String var5x = "Login failed!";
                    if (var2 != null) {
                        Throwable var6 = var2.getCause();
                        var5x = var6 != null ? var6.getMessage() : var2.getMessage();
}
                    String var7x = var15.length() > 30 ? var15.substring(0, 30) + "..." : var15;
                    var12.add("\u00a7cFailed (" + var5x + ") for token: " + var7x + "\u00a7r");
                    return null;
                });
                var11.add(var16);
}
            this.i = ((CompletableFuture)CompletableFuture.allOf(var11.toArray(new CompletableFuture[0])).thenRunAsync(() -> {
                long var3x = a ^ 0x1DF0EF5418A9L;
                long var5x = var3x ^ 0x37C230D242C3L;
                AltManager.O(var5x);
                this.field_146297_k.func_152344_a(() -> {
                    long var3xx = 137993418207183L;
                    long var5xx = 106134966044692L;
                    int var7x = (int)((var3xx ^ 0x360947841EAEL) >>> 48);
                    String var10x = !var13.isEmpty() && var12.isEmpty() ? String.format("\u00a7aSuccessfully logged in %d account(s)!\u00a7r", var13.size()) : (var13.isEmpty() && !var12.isEmpty() ? String.format("\u00a7cFailed to log in %d account(s).\u00a7r", var12.size()) : String.format("\u00a7aLogged in %d, \u00a7cfailed %d account(s).\u00a7r", var13.size(), var12.size()));
                    this.field_146297_k.func_147108_a((GuiScreen)new AccountManagerScreen(var5xx, this.X, new TimedStatusMessage(ChatFormatting.y(var10x), j)));
                    if (!var12.isEmpty()) {
                        for (String string : var12) {
}
}
                });
            }, this.u)).exceptionally(var1x -> {
                this.field_146297_k.func_152344_a(() -> {
                    this.U = "\u00a7cAn unexpected error occurred during batch processing.\u00a7r";
                    this.R.field_146124_l = true;
                });
                return null;
            });
}
}
    public void func_73866_w_() {
        Keyboard.enableRepeatEvents((boolean)true);
        this.field_146292_n.clear();
        this.R = new GuiButton(0, this.field_146294_l / 2 - 100, this.field_146295_m / 2 + 30, 200, 20, "Login Account(s)");
        this.field_146292_n.add(this.R);
        this.d = new GuiButton(1, this.field_146294_l / 2 - 100, this.field_146295_m / 2 + 55, 200, 20, "Cancel");
        this.field_146292_n.add(this.d);
        this.W = new GuiTextWidget(2, this.field_146289_q, this.field_146294_l / 2 - 100, this.field_146295_m / 2 - 60, 200, 80);
        this.W.V(50000);
        this.W.r(true);
}
    public RefreshTokenLoginScreen(GuiScreen var1, long var2) {
        this.X = var1;
}
    public void func_146281_b() {
        Keyboard.enableRepeatEvents((boolean)false);
        if (this.i != null && !this.i.isDone()) {
            this.i.cancel(true);
            if (this.u != null && !this.u.isShutdown()) {
                this.u.shutdownNow();
}
}
}
    private static List m(int var0, int var1, String var2, short var3) {
        ArrayList<String> var6 = new ArrayList<String>();
        String var7 = var2.trim();
        if (var7.toLowerCase().startsWith("refreshtoken:")) {
            var7 = var7.substring(var7.indexOf(58) + 1).trim();
}
        String var8 = var7.replaceAll("\\s+", "");
        Matcher var9 = N.matcher(var8);
        while (var9.find()) {
            String var10 = var9.group();
            if (var10.length() < 50 || var6.contains(var10)) continue;
            var6.add(var10);
}
        if (!var6.isEmpty()) {
            return var6;
}
        for (String var13 : var2.split("[\\r\\n]+")) {
            String[] var14;
            if ((var13 = var13.trim()).isEmpty()) continue;
            if (var13.toLowerCase().contains("refreshtoken:") && (var14 = var13.split(":", 2)).length == 2) {
                var13 = var14[1].trim();
}
            if (!(var9 = N.matcher(var13 = var13.replaceAll("\\s+", ""))).matches() || var9.group().length() < 50 || var6.contains(var9.group())) continue;
            var6.add(var9.group());
}
        return var6;
}
    protected void func_146284_a(GuiButton var1) {
        if (var1.field_146124_l) {
            switch (var1.field_146127_k) {
                case 0: {
                    String var7 = this.W.z().trim();
                    if (var7.isEmpty()) {
                        this.U = "\u00a7cPlease enter at least one refresh token.\u00a7r";
                        return;
}
                    this.l(31150L, var7, -1382965814);
                    break;
}
                case 1: {
                    this.field_146297_k.func_147108_a(this.X);
}
}
}
}
    protected void func_73869_a(char var1, int var2) {
        if (var2 == 1) {
            this.func_146284_a(this.d);
        } else {
            this.W.W(131295994842818L, var1, var2);
            if (var2 == 28 && RefreshTokenLoginScreen.func_146271_m() && !this.W.z().trim().isEmpty()) {
                this.func_146284_a(this.R);
}
}
}
    public void func_73863_a(int var1, int var2, float var3) {
        this.func_146276_q_();
        this.func_73732_a(this.field_146289_q, "\u00a7fLogin with Refresh Token(s)", this.field_146294_l / 2, this.field_146295_m / 2 - 90, 0xFFFFFF);
        this.func_73732_a(this.field_146289_q, this.U, this.field_146294_l / 2, this.field_146295_m / 2 - 75, 0xAAAAAA);
        this.W.h();
        super.func_73863_a(var1, var2, var3);
}
    public void func_73876_c() {
        this.W.o();
}
    protected void func_73864_a(int var1, int var2, int var3) {
        super.func_73864_a(var1, var2, var3);
        this.W.D(var1, var2, var3);
}
    static {
        N = Pattern.compile("M\\.C[A-Za-z0-9._!*$\\-]+");
        e = new HashMap(13);
        b = new String[]{"\u00dd\u0090\u00bd\u00f0\u0019\u00b7\u0085\t", "\\\u00b0\u0000\u00f3l\u0016\u00b2\n\u00d4\u00af\u00de)hh\u008aK:\u00f0m\u00c8\u009a\u008a\n\u0006\u001ep\u00a4\u008ch\u00d0\u00d3\u0088\u0093\u008c\u00ce\u00eb\u00b3\u009d\u0086j", "(ghTk\u00f8y\u0095\u00ed\u00e6\u00bcl\u00d1\u00fd\u001d\u00a1", "2<fs\u00a2e\u00bb\u00cc", "\u0098\u00f7'\u00ca\u0085\u00b2\u000e\u00d1\u009eY\u009c\u00f4\u0096%\u00b7\u00b0\u0002s\u00d3\u00bc&\u008d\u008e\u001d\u00a6H\u00acc\u00c3\u00e1\u00ee\u00c0\u009d++x0\u00b3\u00cf\u0087\u00e0.9\u00ae^\u00fb\u00a8\u00e0", "\u00e7\u00c5\u00b0\u0098x\u0087.\u0089", "*\u00b1\u008d\u00c1'-\u00f0B", "b\u00f2dn\u00e3VA\u00fbn\u00e9\r((\\\u00db\u00d6", "\u0090\u0082\u0015\\\u00d2nh\u0086\rPs)\r\u0018*d\u00a9\u0004\u009b0g\u0013G\u008f\u00b2\u00d0\u00a1\u00b3\u009e\u00c1GT\u0087\u00c5\u00143\u009dsh\u00a3E\u00f4\u00e8\u00a1\u0005\u00a1H6K\u00e5\u00af\u0019\u00cfT`o\"\u00d0\u00bb9\u00b8$\u00da\u00a5", "\u00c1\u00d3\u00d6\u00b0\\\u00c3q\u00aaV\u00fc\u0087\u008d\u0095+A\t\u0097\u00aa\u0082\u00aa\u00ac\u00c0\u00abUu\u00a2\u00ba\u00e7\u00fd\u0096\u00ef\u0081\u0089\u00982\u00c6!\u00caT\u00e4", "\u00a3N\u0013\u00f6\u00bc\u0098\u00cam\u0093\u0088*~\u00f7\u009e\u008ff", "%\u00da\u00b6\u00b3Ue\u00d3\u008c", "\u00a9\u00975\u00c4\"\u00c9`\u00f7/}\u00cf\u00bf\u00f5\u0015Oa\f\u00d1\u001b1\u00e5\u007f\r{!\u0082\u00f2a\u00dc\u00e4\u00a9\u0002s\u008a\u00a2E(.we\u00a5\u008b\u00aa0\u009d\u00baP\u00bb", "\n\u00d5%\u001c4\u00da\u0088\u000fl\u00ce\"\u000b\u009b\u00e0\u00c7\u00e52I\u00f0\u00ab\u0013\u00ea\u0006\u00bd\u0014(\u00e9\u00bf\u0083\u000eQ\u001cY\u00e3\u00ff1\u00e1!\u00b0G?\t\u0091\u00e1\u0085qR\u00af", "\u00ebo\"\u008c^\u00f1\u00e1r\u00e1\u0017y\u00ac,q\u00d7\u0094\t\u00a1,\u0088KL\u00f5\u009d", "\u0093|\u00f3\u00c9\u009b]\u00b9\u0093\u0089\u0083\u0090\u009b(\u00af@v\u0012\u00de\u001c\u00c8\u00cb\u00dd\u0093K\u00c1\u00a1yj\u0012\u009e\u00ba\u0015", "q\u00ed\u00ac\rR\u00e4\u0088\u00efG\u00a7\u00b4E\u001c\u009a\u001e3\u009b\u00aaV\u00a8\u0011qI.\u00d90|('5\u00e0\u00e4", "\u00edT\u001f\u000bz\u0097\u0095yK\f%7\u00e3\u00a7\u00a8\u00b0H\u00dbG\u00be:@7\u001dE\u00d7\u00e1\u00fbQ\u00a8\u0001\u0004\u00efZ\u0010\u00c0\u00e3\u00dff\u009e\u00a7\u00b0\u00c1\u0086\u00a3\u001b|\u0002", "\u009b~\u00b3\u009d/\u00a0#\u001e*n\u00c1\u00d0\u00bd#\u009e\u0091\u0010r\u00fc\u00b0\u00ab\u00b8\u0011u", "\u0080H\u008b2-\u00c4\u00d7\u0097\u00dc\r\u00eb\u0089\u0001K\u00f3\u00a7^\u0002w,\u00bd\u00ce\u00e4FI\u00ad\u0097\u00dfxCu\"\u007f\u0004w\u00ef\u009dg\u00b2\u00ed", "\u00edYm\u0098\u0094\u00be\u00cd\b\u0019\u00d85\u00ec\u001b&\u00b0`/a\u0013\"O\u00ee\u00c0A\u0090(\u00f8>\u0098\u00b8o\u00e2", "\u00f7R\u00d0`7\u00b5!U!V\u00c3\u009a\u00cf\u00dbsH", "\u00ce\u00ffK\u00dc\u008b'\u00a9P", "\u00ecL \u001e,\u00ef\u00a2l\u0015\u009b\u00a2\u009cf\u00fe\u009f\u008d", "\u00f5\u00ce\u00d4\u00a7\u00d6\u008c\u00d5\u00c6"};
        c = new String[25];
        h = new HashMap(13);
        f = new long[]{-7570801240199433L, -8211173771169325036L, 613585343914352420L, -3132855412799374697L, -2419402698885468389L, -7736364222687519492L, 1422944840999800733L, -370637497708618771L, -247905534369938817L, 5786307659691052987L, 4352395137605550499L, 3587902760217718899L, -6141616386768244L, 3776211645278042015L, -1678665091334605079L, 7178751944627941208L, 3721894796051350167L, -4881095705570002641L, 1347736885119368877L, 129291971699333009L};
        j = 5000L;
}
}