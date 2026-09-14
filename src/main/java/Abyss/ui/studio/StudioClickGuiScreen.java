/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  net.minecraft.client.gui.GuiScreen
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package Abyss.ui.studio;

import Abyss.internal.restore.AbyssClickGui;
import Abyss.module.Category;
import Abyss.module.Modules;
import Abyss.module.impl.configuration.ClickGUI;
import Abyss.ui.studio.StudioFrameState;
import Abyss.ui.studio.StudioModuleFrame;
import Abyss.ui.studio.StudioNotification;
import Abyss.ui.studio.TextSettingComponent;
import Abyss.util.Animation;
import Abyss.util.KeyBindUtil;
import Abyss.util.MathUtil;
import Abyss.util.Sneaky;
import Abyss.util.render.RenderUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class StudioClickGuiScreen
extends GuiScreen {
    private float y;
    private String K;
    private static Map<Category, StudioFrameState> a;
    private long V;
        private StudioModuleFrame i;
        private float Q;
    private TextSettingComponent P;
    private boolean L;
    private final Animation A;
        private static long b;
    private final Animation W;
    private static final float D = 0.94f;
    private final List<StudioNotification> k;
    private boolean m;
    private float v;
        private float Z;
        

    public void X(String var1, float var2, float var3) {
        if (var1 != null && !var1.isEmpty()) {
            this.K = var1;
            this.Z = var2;
            this.v = var3;
}
}
    public static void n() {
        a.clear();
        StudioClickGuiScreen.Z();
}
    public void E(StudioModuleFrame var1, short var2, short var3, int var4) {
        long var5 = ((long)var2 << 48 | (long)var3 << 48 >>> 16 | (long)var4 << 32 >>> 32) ^ b;
        long var7 = var5 ^ 0x7E583DF1DE2FL;
        this.m = true;
        this.i = var1;
        if (this.P != null) {
            this.n(this.P, var7);
}
}
    static void $jnicClinit() throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException {
        b = 120365167084229L;
        e = new HashMap(13);
        long var11 = b ^ 0x670CFB5D3CF0L;
        byte[] var10003 = new byte[]{(byte)(var11 >>> 56), 0, 0, 0, 0, 0, 0, 0};
        for (int var14 = 1; var14 < 8; ++var14) {
            var10003[var14] = (byte)(var11 << var14 * 8 >>> 56);
}
        Cipher var13 = Cipher.getInstance("DES/CBC/PKCS5Padding");
        var13.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
        String[] var20 = new String[6];
        int var18 = 0;
        String var17 = "\u008aQX\u00cf\u0002I\u00bc>8X\u00a9\u00c4\u0002\u0017\u00b7\u00a2\u0010\u008b\u00fd\u0086\u00c9\u0086\b\u00ab\u00d1\u0010\u00a1tx\u00f0\u001b\u00ea\u007f\u0010\u0080\u00dcX\u00fejn\u0091\u00f5Q\u00fd\u00e5\u001et\u00da|\u001b\u0018\u00eb\u00191\u009cg\u00c3\u00d8z\b\u00eb\u00809\u00b6$\b|\u00f9\u001b\u00d9O8\u00d6N\u00e6";
        int var19 = "\u008aQX\u00cf\u0002I\u00bc>8X\u00a9\u00c4\u0002\u0017\u00b7\u00a2\u0010\u008b\u00fd\u0086\u00c9\u0086\b\u00ab\u00d1\u0010\u00a1tx\u00f0\u001b\u00ea\u007f\u0010\u0080\u00dcX\u00fejn\u0091\u00f5Q\u00fd\u00e5\u001et\u00da|\u001b\u0018\u00eb\u00191\u009cg\u00c3\u00d8z\b\u00eb\u00809\u00b6$\b|\u00f9\u001b\u00d9O8\u00d6N\u00e6".length();
        int var16 = 16;
        int var24 = -1;
        block7: while (true) {
            String var25 = var17.substring(++var24, var24 + var16);
            int var10001 = -1;
            while (true) {
                byte[] var21 = var13.doFinal(var25.getBytes("ISO-8859-1"));
                String var36 = StudioClickGuiScreen.a(var21).intern();
                switch (var10001) {
                    case 0: {
                        var20[var18++] = var36;
                        if ((var24 += var16) >= var19) {
                            c = var20;
                            d = new String[6];
                            h = new HashMap(13);
                            var10003 = new byte[]{(byte)(var11 >>> 56), 0, 0, 0, 0, 0, 0, 0};
                            for (int var1 = 1; var1 < 8; ++var1) {
                                var10003[var1] = (byte)(var11 << var1 * 8 >>> 56);
}
                            Cipher var0 = Cipher.getInstance("DES/CBC/NoPadding");
                            var0.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
                            long[] var6 = new long[5];
                            int var3 = 0;
                            String var4 = "Te\u00a8dn\u00b8\u001d\u001f\u00b9\u0006Q\u0082\u008c\u0014\u009a\u0000\u00d7r\u0018\u0089\n+`e";
                            int var5 = "Te\u00a8dn\u00b8\u001d\u001f\u00b9\u0006Q\u0082\u008c\u0014\u009a\u0000\u00d7r\u0018\u0089\n+`e".length();
                            int var2 = 0;
                            block10: while (true) {
                                var10001 = var2;
                                byte[] var7 = var4.substring(var10001, var2 += 8).getBytes("ISO-8859-1");
                                long[] var28 = var6;
                                var10001 = var3++;
                                long var40 = ((long)var7[0] & 0xFFL) << 56 | ((long)var7[1] & 0xFFL) << 48 | ((long)var7[2] & 0xFFL) << 40 | ((long)var7[3] & 0xFFL) << 32 | ((long)var7[4] & 0xFFL) << 24 | ((long)var7[5] & 0xFFL) << 16 | ((long)var7[6] & 0xFFL) << 8 | (long)var7[7] & 0xFFL;
                                int var43 = -1;
                                while (true) {
                                    long var8 = var40;
                                    byte[] var10 = var0.doFinal(new byte[]{(byte)(var8 >>> 56), (byte)(var8 >>> 48), (byte)(var8 >>> 40), (byte)(var8 >>> 32), (byte)(var8 >>> 24), (byte)(var8 >>> 16), (byte)(var8 >>> 8), (byte)var8});
                                    long var45 = ((long)var10[0] & 0xFFL) << 56 | ((long)var10[1] & 0xFFL) << 48 | ((long)var10[2] & 0xFFL) << 40 | ((long)var10[3] & 0xFFL) << 32 | ((long)var10[4] & 0xFFL) << 24 | ((long)var10[5] & 0xFFL) << 16 | ((long)var10[6] & 0xFFL) << 8 | (long)var10[7] & 0xFFL;
                                    switch (var43) {
                                        case 0: {
                                            var28[var10001] = var45;
                                            if (var2 < var5) break;
                                            f = var6;
                                            g = new Integer[5];
                                            return;
}
                                        default: {
                                            var28[var10001] = var45;
                                            if (var2 < var5) continue block10;
                                            var4 = "\u00eeY\u00b5c\u00fc\u0000\u00d3\u00df\u00d7\u00c0\u001f3\u00d6\u0011\u0090o";
                                            var5 = "\u00eeY\u00b5c\u00fc\u0000\u00d3\u00df\u00d7\u00c0\u001f3\u00d6\u0011\u0090o".length();
                                            var2 = 0;
}
}
                                    int var34 = var2;
                                    var7 = var4.substring(var34, var2 += 8).getBytes("ISO-8859-1");
                                    var28 = var6;
                                    var10001 = var3++;
                                    var40 = ((long)var7[0] & 0xFFL) << 56 | ((long)var7[1] & 0xFFL) << 48 | ((long)var7[2] & 0xFFL) << 40 | ((long)var7[3] & 0xFFL) << 32 | ((long)var7[4] & 0xFFL) << 24 | ((long)var7[5] & 0xFFL) << 16 | ((long)var7[6] & 0xFFL) << 8 | (long)var7[7] & 0xFFL;
                                    var43 = 0;
}
                                break;
}
}
                        var16 = var17.charAt(var24);
                        break;
}
                    default: {
                        var20[var18++] = var36;
                        if ((var24 += var16) < var19) {
                            var16 = var17.charAt(var24);
                            continue block7;
}
                        var17 = "Ua_8\u00f8i\u00c6\u00f7\u00d0!\u00d1\u00ae\u00fcX2e\u00fb\u00f5UX\u00cf\u0007oeV\u0089\u009c\u00d6\u0095\u00cb\u008b}\u0010\u00b8\u00deJu\u00ac\u00c3\u00c1\u00b9\u0092\u00f0\u001ee\u001b\u0085\u008a/";
                        var19 = "Ua_8\u00f8i\u00c6\u00f7\u00d0!\u00d1\u00ae\u00fcX2e\u00fb\u00f5UX\u00cf\u0007oeV\u0089\u009c\u00d6\u0095\u00cb\u008b}\u0010\u00b8\u00deJu\u00ac\u00c3\u00c1\u00b9\u0092\u00f0\u001ee\u001b\u0085\u008a/".length();
                        var16 = 32;
                        var24 = -1;
}
}
                var25 = var17.substring(++var24, var24 + var16);
                var10001 = 0;
}
            break;
}
}
    public float y() {
        return this.y;
}
    public void J(long var1, TextSettingComponent var3) {
        var1 = b ^ var1;
        long var4 = var1 ^ 0x57273DC8EE6DL;
        long var6 = var1 ^ 0x7A9C89C8CA27L;
        boolean bl = this.L = (StudioClickGuiScreen.b(27238, 0x5381481233679F68L ^ var1) & 1) != 0;
        if (this.P != null && this.P != var3) {
            this.n(this.P, var6);
}
        this.P = var3;
        this.P.c(var4);
}
    public void n(TextSettingComponent var1, long var2) {
        long var4 = var2 ^ 0x2BDA39E005E4L;
        if (this.P != null && (var1 == null || var1 == this.P)) {
            this.P.U(var4, true);
            this.P = null;
            Modules.c(0L);
}
}
    private void l(StudioNotification var1) {
        this.k.remove(var1);
        this.k.add(var1);
}
    public void func_73866_w_() {
        super.func_73866_w_();
        StudioClickGuiScreen.Z();
        AbyssClickGui.beginDisplaySort();
        try {
            this.z(9394818575647L);
}
        finally {
            AbyssClickGui.endDisplaySort();
}
        this.V = System.currentTimeMillis();
        this.A.U(0.0f);
        this.A.d(1.0f);
        this.W.U(this.Q);
}
    protected void func_146286_b(int var1, int var2, int var3) {
        super.func_146286_b(var1, var2, var3);
        float var8 = ClickGUI.scale.L() * 0.94f;
        float var9 = (float)var1 / var8;
        float var10 = (float)var2 / var8;
        for (StudioNotification var12 : this.k) {
            var12.k(var9, 61865618957008L, var10);
}
}
    public static void q(JsonObject var0, long var1) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        StudioClickGuiScreen.Z();
        JsonObject var3 = new JsonObject();
        for (Map.Entry<Category, StudioFrameState> var5 : a.entrySet()) {
            JsonObject var6 = new JsonObject();
            var6.addProperty("X", (Number)Float.valueOf(var5.getValue().J));
            var6.addProperty("Y", (Number)Float.valueOf(var5.getValue().h));
            var6.addProperty("Opened", Boolean.valueOf(var5.getValue().S));
            JsonObject var7 = new JsonObject();
            for (Map.Entry<String, Boolean> var9 : var5.getValue().W().entrySet()) {
                if (!Boolean.TRUE.equals(var9.getValue())) continue;
                var7.addProperty(var9.getKey(), Boolean.valueOf(true));
}
            if (!var7.entrySet().isEmpty()) {
                var6.add("ExpandedModules", (JsonElement)var7);
}
            var3.add(var5.getKey().c(), (JsonElement)var6);
}
        var0.add("Studio", (JsonElement)var3);
}
    public boolean func_73868_f() {
        return false;
}
    public void func_73863_a(int var1, int var2, float var3) {
        try {
            long var4 = 65742445683630L;
            this.W();
            float var13 = ClickGUI.scale.L() * 0.94f;
            float var14 = (float)var1 / var13;
            float var15 = (float)var2 / var13;
            this.A.y(0.18f, this.y);
            this.W.d(this.Q);
            this.W.y(0.18f, this.y);
            this.o();
            float var16 = this.A.b(var3);
            float var17 = this.W.b(var3);
            for (StudioNotification var19 : this.k) {
                var19.t(var14, '\u0000', var15, this.W.E(), 450090305);
}
            GL11.glPushMatrix();
            GL11.glScaled((double)var13, (double)var13, (double)1.0);
            this.l(var16);
            this.K = null;
            for (StudioNotification var21 : this.k) {
                var21.d(var14, var15, var17, var3, var16, 112770940687988L, var13);
}
            this.C(76088342050980L);
            GL11.glPopMatrix();
}
        catch (Throwable ex) {
            throw Sneaky.rethrow(ex);
}
}
    private void W() {
        long var1 = System.currentTimeMillis();
        if (this.V == 0L) {
            this.V = var1;
}
        this.y = MathUtil.q((float)(var1 - this.V) / 16.0f, 0.5f, 4.0f);
        this.V = var1;
}
    protected void func_73864_a(int var1, int var2, int var3) {
        try {
            long var4 = 21258311689151L;
            super.func_73864_a(var1, var2, var3);
            if (this.i != null && var3 > 1) {
                this.i.E().z(118276941480361L, KeyBindUtil.w('\u0000', var3, 132797583844084L));
                this.i = null;
                Modules.c(0L);
            } else {
                float var17 = ClickGUI.scale.L() * 0.94f;
                float var18 = (float)var1 / var17;
                float var19 = (float)var2 / var17;
                float var20 = this.W.E();
                TextSettingComponent var21 = this.P;
                StudioModuleFrame var22 = this.i;
                this.L = false;
                this.m = false;
                for (int var23 = this.k.size() - 1; var23 >= 0; --var23) {
                    StudioNotification var24 = this.k.get(var23);
                    if (!var24.m(var18, var19, 69261216583452L, var3, var20)) continue;
                    this.l(var24);
                    if (var21 != null && var21 == this.P && !this.L) {
                        this.n(var21, 89965113873812L);
}
                    if (var22 != null && var22 == this.i && !this.m) {
                        this.i = null;
}
                    return;
}
                if (this.P != null) {
                    this.n(this.P, 89965113873812L);
}
                this.i = null;
}
}
        catch (Throwable ex) {
            throw Sneaky.rethrow(ex);
}
}
    private void o() {
        float var1 = Float.MAX_VALUE;
        float var2 = -3.4028235E38f;
        for (StudioNotification var4 : this.k) {
            StudioFrameState var5 = a.get((Object)var4.S());
            var1 = Math.min(var1, var5.h);
            var2 = Math.max(var2, var4.m(1.0f));
}
        float var7 = 24.0f;
        float var8 = (float)this.field_146295_m - 20.0f;
        float var9 = Math.min(var7 - var1, var8 - var2);
        float var6 = var7 - var1;
        if (var2 - var1 <= (float)this.field_146295_m - 44.0f) {
            var9 = var6;
}
        this.Q = MathUtil.q(this.Q, var9, var6);
}
    protected void func_73869_a(char var1, int var2) {
        if (this.i != null) {
            this.i.E().z(118276941480361L, var2 != 211 && var2 != 1 ? var2 : 0);
            this.i = null;
            Modules.c(0L);
        } else if (this.P != null && this.P.V()) {
            this.P.h(13426, var1, var2, '\ua023', (short)4065);
        } else if (var2 == 1) {
            this.field_146297_k.func_147108_a(null);
            if (this.field_146297_k.field_71462_r == null) {
                this.field_146297_k.func_71381_h();
}
            Modules.c(0L);
        } else {
            for (StudioNotification var13 : this.k) {
                var13.s(var1, var2);
}
}
}
    private void l(float var1) {
}
    private void z(long var1) {
        this.k.clear();
        for (Category var8 : Category.values()) {
            this.k.add(new StudioNotification(this, var8, 55250609642696L, a.get((Object)var8)));
}
}
    public boolean s(StudioModuleFrame var1) {
        return this.i == var1;
}
    private static int b(int var0, long var1) {
        int var3 = var0 ^ (int)(var1 & 0x7FFFL) ^ 0x2ABF;
        if (g[var3] == null) {
            byte[] var10;
            byte[] var4 = new byte[]{(byte)(var1 >>> 56), (byte)(var1 >>> 48), (byte)(var1 >>> 40), (byte)(var1 >>> 32), (byte)(var1 >>> 24), (byte)(var1 >>> 16), (byte)(var1 >>> 8), (byte)var1};
            long var5 = f[var3];
            byte[] var7 = new byte[]{(byte)(var5 >>> 56), (byte)(var5 >>> 48), (byte)(var5 >>> 40), (byte)(var5 >>> 32), (byte)(var5 >>> 24), (byte)(var5 >>> 16), (byte)(var5 >>> 8), (byte)var5};
            Long var8 = Thread.currentThread().getId();
            Object[] var9 = (Object[])h.get(var8);
            try {
                if (var9 == null) {
                    var9 = new Object[]{Cipher.getInstance("DES/CBC/NoPadding"), SecretKeyFactory.getInstance("DES"), new IvParameterSpec(new byte[8])};
                    h.put(var8, var9);
}
                DESKeySpec var11 = new DESKeySpec(var4);
                SecretKey var12 = ((SecretKeyFactory)var9[1]).generateSecret(var11);
                Cipher var13 = (Cipher)var9[0];
                var13.init(2, (Key)var12, (IvParameterSpec)var9[2]);
                var10 = var13.doFinal(var7);
}
            catch (Exception var14) {
                throw new RuntimeException("Abyss/ui/studio/StudioClickGuiScreen", var14);
}
            int var15 = (var10[4] & 0xFF) << 24 | (var10[5] & 0xFF) << 16 | (var10[6] & 0xFF) << 8 | var10[7] & 0xFF;
            StudioClickGuiScreen.g[var3] = var15;
}
        return g[var3];
}
    private static void Z() {
        if (a.isEmpty() || a.size() != Category.values().length) {
            a.clear();
            float var0 = 24.0f;
            float var1 = 60.0f;
            float var2 = 20.0f;
            Category[] var3 = Category.values();
            for (int var4 = 0; var4 < var3.length; ++var4) {
                a.put(var3[var4], new StudioFrameState(var0, var1 + (float)var4 * var2, false));
}
}
}
    public static void H(JsonObject var0, long var1) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        StudioClickGuiScreen.Z();
        for (StudioFrameState var4 : a.values()) {
            var4.j();
}
        if (var0.has("Studio")) {
            JsonObject var14 = var0.getAsJsonObject("Studio");
            for (Category var7 : Category.values()) {
                if (!var14.has(var7.c())) continue;
                JsonObject var8 = var14.getAsJsonObject(var7.c());
                StudioFrameState var9 = a.get((Object)var7);
                if (var8.has("X")) {
                    var9.J = var8.get("X").getAsFloat();
}
                if (var8.has("Y")) {
                    var9.h = var8.get("Y").getAsFloat();
}
                if (var8.has("Opened")) {
                    var9.S = var8.get("Opened").getAsBoolean();
}
                if (!var8.has("ExpandedModules") || !var8.get("ExpandedModules").isJsonObject()) continue;
                JsonObject var10 = var8.getAsJsonObject("ExpandedModules");
                for (Map.Entry var12 : var10.entrySet()) {
                    if (!((JsonElement)var12.getValue()).isJsonPrimitive() || !((JsonElement)var12.getValue()).getAsJsonPrimitive().isBoolean()) continue;
                    var9.W((String)var12.getKey(), ((JsonElement)var12.getValue()).getAsBoolean());
}
}
}
}
    private void C(long var1) {
        if (this.K != null) {
            RenderUtil.m(28813, this.K, (int)this.Z, 53203, (short)7294, (int)this.v);
}
}
    public StudioClickGuiScreen(long var1) {
        long var3 = 9394818575647L;
        this.k = new ArrayList<StudioNotification>();
        this.A = new Animation(0.0f);
        this.W = new Animation(0.0f);
        this.y = 1.0f;
        StudioClickGuiScreen.Z();
        this.z(var3);
}
    public void func_146274_d() {
        super.func_146274_d();
        int var1 = Mouse.getDWheel();
        if (var1 != 0) {
            this.Q += var1 > 0 ? 34.0f : -34.0f;
}
}
    public void func_146281_b() {
        super.func_146281_b();
        if (this.P != null) {
            this.n(this.P, 89965113873812L);
}
        this.i = null;
        for (StudioNotification var11 : this.k) {
            var11.m(855328176L, '\u36ca');
}
        Modules.c(0L);
}
    static {
        try {
            StudioClickGuiScreen.$jnicClinit();
            a = new EnumMap<Category, StudioFrameState>(Category.class);
}
        catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException var0) {
            throw new RuntimeException(var0);
}
}
}