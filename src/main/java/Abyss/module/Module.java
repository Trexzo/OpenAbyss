/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package Abyss.module;

import Abyss.event.events.PreMouseInputEvent;
import Abyss.internal.jnic.StockClientBootstrap;
import Abyss.module.Category;
import Abyss.module.MacroModule;
import Abyss.module.Modules;
import Abyss.module.impl.configuration.Language;
import Abyss.module.impl.configuration.Notifications;
import Abyss.setting.Setting;
import Abyss.util.KeyBindUtil;
import Abyss.util.MinecraftRef;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
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
import net.minecraft.client.Minecraft;

public class Module {
    private boolean V;
    private static Map lb;
    private boolean w;
    private boolean Z = false;
    private final List<Setting> l = new ArrayList<Setting>();
    private static long[] jb;
    private String W;
    private static String[] db;
    private String Q;
    public static Minecraft f;
    private static String[] eb;
    private boolean A = false;
    private static Map fb;
    private boolean z = true;
    private static long cb;
    private boolean q = true;
    private static Integer[] kb;
    private boolean i = false;
    private Category X;
    private int j;
    private boolean P = false;
    private volatile boolean settingsScanned;

    public void l(String var1) {
        this.W = var1;
}
    static void $jnicClinit() throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException {
        cb = 116567223632086L;
        long var20 = cb ^ 0x395B7356C238L;
        fb = new HashMap(13);
        byte[] var10003 = new byte[]{(byte)(var20 >>> 56), 0, 0, 0, 0, 0, 0, 0};
        for (int var12 = 1; var12 < 8; ++var12) {
            var10003[var12] = (byte)(var20 << var12 * 8 >>> 56);
}
        Cipher var11 = Cipher.getInstance("DES/CBC/PKCS5Padding");
        var11.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
        String[] var18 = new String[7];
        int var16 = 0;
        String var15 = "\u0012\u00b5V\u00c7\u00f1^\u00eb\u0004C\u000f\u0091\u00ef\u00ebj \u00e0\u00be\u00fc\u00b3\u00f9:@\u0088\u0088\u00e3\b\u00c2\t\u000b\u00d1\u0007c0U\u0010\u00c8\u0000\u00ba\u00f12\u00179\b\u00daQ\u00f2\u0017\u0089\u00de\u0001\u00e1\u00d2p<]e`\u00b8\u00b6W8RC\u00e4\u00102f:Pa\u00ed\u00d0\u00a1p\u009d8\u00d87\u009cm\u00dc\u0010\u00eb-\u00a2\u00dd\u00e2\u00dc\"\u009f2\u00c6\u00ee\u009b\u0083\u00fb\u00f6^8>\u0019\"\u001dj\u009e\u00ebf\u0092\u00e6\u00a0\f\u00e75\r\u00a0~e\u00cel\u00dc\u0087d\u00e6@\u0091\u00e5\u00d5\u0094\u00d2\u00c1\u008dQ\u009d\u00fd\u00cc\u00a4G\u001f\u00f4?\u0086\u00ca\u0084\u0099\u00ffT\u00b8;D*\u0086\u0007\u00fd\u00a7\u00e4\u0010\u00d4\u00ddM\u00cd\u0092\u00ce\u0092\u00d8{\u0090-\u009c\u00ce\u00b6'\u009d";
        int var17 = "\u0012\u00b5V\u00c7\u00f1^\u00eb\u0004C\u000f\u0091\u00ef\u00ebj \u00e0\u00be\u00fc\u00b3\u00f9:@\u0088\u0088\u00e3\b\u00c2\t\u000b\u00d1\u0007c0U\u0010\u00c8\u0000\u00ba\u00f12\u00179\b\u00daQ\u00f2\u0017\u0089\u00de\u0001\u00e1\u00d2p<]e`\u00b8\u00b6W8RC\u00e4\u00102f:Pa\u00ed\u00d0\u00a1p\u009d8\u00d87\u009cm\u00dc\u0010\u00eb-\u00a2\u00dd\u00e2\u00dc\"\u009f2\u00c6\u00ee\u009b\u0083\u00fb\u00f6^8>\u0019\"\u001dj\u009e\u00ebf\u0092\u00e6\u00a0\f\u00e75\r\u00a0~e\u00cel\u00dc\u0087d\u00e6@\u0091\u00e5\u00d5\u0094\u00d2\u00c1\u008dQ\u009d\u00fd\u00cc\u00a4G\u001f\u00f4?\u0086\u00ca\u0084\u0099\u00ffT\u00b8;D*\u0086\u0007\u00fd\u00a7\u00e4\u0010\u00d4\u00ddM\u00cd\u0092\u00ce\u0092\u00d8{\u0090-\u009c\u00ce\u00b6'\u009d".length();
        int var14 = 64;
        int var27 = -1;
        block7: while (true) {
            String var28 = var15.substring(++var27, var27 + var14);
            int var10001 = -1;
            while (true) {
                byte[] var19 = var11.doFinal(var28.getBytes("ISO-8859-1"));
                String var39 = Module.a(var19).intern();
                switch (var10001) {
                    case 0: {
                        var18[var16++] = var39;
                        if ((var27 += var14) >= var17) {
                            db = var18;
                            eb = new String[7];
                            lb = new HashMap(13);
                            var10003 = new byte[]{(byte)(var20 >>> 56), 0, 0, 0, 0, 0, 0, 0};
                            for (int var1 = 1; var1 < 8; ++var1) {
                                var10003[var1] = (byte)(var20 << var1 * 8 >>> 56);
}
                            Cipher var0 = Cipher.getInstance("DES/CBC/NoPadding");
                            var0.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
                            long[] var6 = new long[4];
                            int var3 = 0;
                            String var4 = "[TA\u00cb\u00fb&\u0000\u00c2`\u00f6\u00f7\u00a9\u0088\u0090-)";
                            int var5 = "[TA\u00cb\u00fb&\u0000\u00c2`\u00f6\u00f7\u00a9\u0088\u0090-)".length();
                            int var2 = 0;
                            block10: while (true) {
                                var10001 = var2;
                                byte[] var7 = var4.substring(var10001, var2 += 8).getBytes("ISO-8859-1");
                                long[] var31 = var6;
                                var10001 = var3++;
                                long var43 = ((long)var7[0] & 0xFFL) << 56 | ((long)var7[1] & 0xFFL) << 48 | ((long)var7[2] & 0xFFL) << 40 | ((long)var7[3] & 0xFFL) << 32 | ((long)var7[4] & 0xFFL) << 24 | ((long)var7[5] & 0xFFL) << 16 | ((long)var7[6] & 0xFFL) << 8 | (long)var7[7] & 0xFFL;
                                int var46 = -1;
                                while (true) {
                                    long var8 = var43;
                                    byte[] var10 = var0.doFinal(new byte[]{(byte)(var8 >>> 56), (byte)(var8 >>> 48), (byte)(var8 >>> 40), (byte)(var8 >>> 32), (byte)(var8 >>> 24), (byte)(var8 >>> 16), (byte)(var8 >>> 8), (byte)var8});
                                    long var48 = ((long)var10[0] & 0xFFL) << 56 | ((long)var10[1] & 0xFFL) << 48 | ((long)var10[2] & 0xFFL) << 40 | ((long)var10[3] & 0xFFL) << 32 | ((long)var10[4] & 0xFFL) << 24 | ((long)var10[5] & 0xFFL) << 16 | ((long)var10[6] & 0xFFL) << 8 | (long)var10[7] & 0xFFL;
                                    switch (var46) {
                                        case 0: {
                                            var31[var10001] = var48;
                                            if (var2 < var5) break;
                                            jb = var6;
                                            kb = new Integer[4];
                                            return;
}
                                        default: {
                                            var31[var10001] = var48;
                                            if (var2 < var5) continue block10;
                                            var4 = "Nkj;a\u00a8\u00ec\u0087[\u0092\u00f7\u00ed\u00c1m\u009d\u008f";
                                            var5 = "Nkj;a\u00a8\u00ec\u0087[\u0092\u00f7\u00ed\u00c1m\u009d\u008f".length();
                                            var2 = 0;
}
}
                                    int var37 = var2;
                                    var7 = var4.substring(var37, var2 += 8).getBytes("ISO-8859-1");
                                    var31 = var6;
                                    var10001 = var3++;
                                    var43 = ((long)var7[0] & 0xFFL) << 56 | ((long)var7[1] & 0xFFL) << 48 | ((long)var7[2] & 0xFFL) << 40 | ((long)var7[3] & 0xFFL) << 32 | ((long)var7[4] & 0xFFL) << 24 | ((long)var7[5] & 0xFFL) << 16 | ((long)var7[6] & 0xFFL) << 8 | (long)var7[7] & 0xFFL;
                                    var46 = 0;
}
                                break;
}
}
                        var14 = var15.charAt(var27);
                        break;
}
                    default: {
                        var18[var16++] = var39;
                        if ((var27 += var14) < var17) {
                            var14 = var15.charAt(var27);
                            continue block7;
}
                        var15 = "o\u001c\u00cf\f\u00e8\u0099j\u0001\u00b9(|D\u00ea:`\u001e(\u0096\u00ff\u00aa\u00d8P\u0086\u00d1Y\u00c8h\u00a7b\u00a2\u00d8\u00e6}x\u00aep\u00b2$\u00f4g]\u00db\u001f\u000f9,\u00ee\u00f7;RM\u00a7\u00b9\u008f}\u00f8\u00c1";
                        var17 = "o\u001c\u00cf\f\u00e8\u0099j\u0001\u00b9(|D\u00ea:`\u001e(\u0096\u00ff\u00aa\u00d8P\u0086\u00d1Y\u00c8h\u00a7b\u00a2\u00d8\u00e6}x\u00aep\u00b2$\u00f4g]\u00db\u001f\u000f9,\u00ee\u00f7;RM\u00a7\u00b9\u008f}\u00f8\u00c1".length();
                        var14 = 16;
                        var27 = -1;
}
}
                var28 = var15.substring(++var27, var27 + var14);
                var10001 = 0;
}
            break;
}
}
    public int x(Setting var1) {
        return this.l.indexOf(var1);
}
    public void E(boolean var1) {
        this.P = var1;
}
    public void L(PreMouseInputEvent var1, long var2) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
}
    public boolean o() {
        return this.V;
}
    public String j(long var1) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var5 = var1 ^ 0x7C322A14385DL;
        if (!Language.applyForDescriptions.c()) {
            return this.x(var5);
}
        return Language.language.R("ENGLISH") ? this.x(var5) : Language.o(this.Q);
}
    public boolean r() {
        return this.q;
}
    public void K(String var1) {
        this.Q = var1;
}
    public void Z(long var1) {
}
    public void B(Category var1) {
        this.X = var1;
}
    public List<Setting> w() {
        return this.settings();
}
    public void A(long var1) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
}
    public void u(short var1, long var2) throws UnsupportedEncodingException, Throwable, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var4 = ((long)var1 << 48 | var2 << 16 >>> 16) ^ cb;
        long var8 = var4 ^ 0x2075E6F0E5CDL;
        if (this.I() && !this.S()) {
            if (this.o()) {
                if (!(this instanceof MacroModule)) {
                    try {
                        Notifications.G(var8, "\u00a7l" + this.b() + " \u00a7r\u00a7l(\u00a7c\u00a7lOFF\u00a7r\u00a7l)", false);
}
                    catch (Throwable throwable) {
                        // empty catch block
}
}
                this.I(0L, false);
            } else {
                if (!(this instanceof MacroModule)) {
                    try {
                        Notifications.G(var8, "\u00a7l" + this.b() + " \u00a7r\u00a7l(\u00a7a\u00a7lON\u00a7r\u00a7l)", true);
}
                    catch (Throwable throwable) {
                        // empty catch block
}
}
                this.I(0L, true);
}
            Modules.c(0L);
}
}
    public boolean S() {
        return this.Z;
}
    public void P(long var1) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
}
    public boolean D() {
        return this.w;
}
    public String t(int var1, int var2, short var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (!Language.applyForArraylist.c()) {
            return this.b();
}
        return Language.language.R("ENGLISH") ? this.b() : Language.Z(0L, this.Q);
}
    public Module Q(String var1, long var2, byte var4, Boolean var5, Category var6, Boolean var7, String var8, Setting ... var9) {
        long var10 = (var2 << 8 | (long)var4 << 56 >>> 56) ^ cb;
        long var12 = var10 ^ 0x3DED77CB5E74L;
        StockClientBootstrap.W(var12, this, var1, var5, var6, var7, var8, var9);
        return this;
}
    public String b() {
        return this.Q;
}
    public String x(long var1) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        return !this.z ? "This module is currently disabled" : this.W;
}
    public void I(long var1, boolean var3) {
        if (this.z) {
            if (var3) {
                if (!this.V) {
                    this.i = true;
}
            } else if (this.V) {
                this.P = true;
}
            this.V = var3;
}
}
    public void r(boolean var1) {
        this.Z = var1;
}
    public void z(long var1, int var3) {
        long var4 = var1 ^ 0x767596D51C5AL;
        this.j = this.Z ? 0 : KeyBindUtil.m(var4, var3);
}
    public boolean I() {
        return this.z;
}
    public void h(long var1) {
}
    public void M(boolean var1) {
        this.z = var1;
}
    public List<Setting> m() {
        return this.settings();
}
    public boolean P() {
        return this.A;
}
    private static int c(int var0, long var1) {
        int var3 = var0 ^ (int)(var1 & 0x7FFFL) ^ 0xE1A;
        if (kb[var3] == null) {
            byte[] var10;
            byte[] var4 = new byte[]{(byte)(var1 >>> 56), (byte)(var1 >>> 48), (byte)(var1 >>> 40), (byte)(var1 >>> 32), (byte)(var1 >>> 24), (byte)(var1 >>> 16), (byte)(var1 >>> 8), (byte)var1};
            long var5 = jb[var3];
            byte[] var7 = new byte[]{(byte)(var5 >>> 56), (byte)(var5 >>> 48), (byte)(var5 >>> 40), (byte)(var5 >>> 32), (byte)(var5 >>> 24), (byte)(var5 >>> 16), (byte)(var5 >>> 8), (byte)var5};
            Long var8 = Thread.currentThread().getId();
            Object[] var9 = (Object[])lb.get(var8);
            try {
                if (var9 == null) {
                    var9 = new Object[]{Cipher.getInstance("DES/CBC/NoPadding"), SecretKeyFactory.getInstance("DES"), new IvParameterSpec(new byte[8])};
                    lb.put(var8, var9);
}
                DESKeySpec var11 = new DESKeySpec(var4);
                SecretKey var12 = ((SecretKeyFactory)var9[1]).generateSecret(var11);
                Cipher var13 = (Cipher)var9[0];
                var13.init(2, (Key)var12, (IvParameterSpec)var9[2]);
                var10 = var13.doFinal(var7);
}
            catch (Exception var14) {
                throw new RuntimeException("Abyss/module/Module", var14);
}
            int var15 = (var10[4] & 0xFF) << 24 | (var10[5] & 0xFF) << 16 | (var10[6] & 0xFF) << 8 | var10[7] & 0xFF;
            Module.kb[var3] = var15;
}
        return kb[var3];
}
    public void i(long var1) throws UnsupportedEncodingException, Throwable, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
}
                if (var5 < 224) {
                char var6 = (char)((char)(var5 & 0x1F) << 6);
                byte var8 = var0[++var4];
                var6 = (char)(var6 | (char)(var8 & 0x3F));
                var3[var1++] = var6;
                continue;
}
            if (var4 >= var2 - 2) continue;
            char var12 = (char)((char)(var5 & 0xF) << 12);
            byte var9 = var0[++var4];
            var12 = (char)(var12 | (char)(var9 & 0x3F) << 6);
            var9 = var0[++var4];
            var12 = (char)(var12 | (char)(var9 & 0x3F));
            var3[var1++] = var12;
}
        return new String(var3, 0, var1);
}
    public Module g(String var1, char var2, Boolean var3, Category var4, Boolean var5, long var6, String var8, boolean var9, boolean var10, Setting ... var11) {
        long var12 = ((long)var2 << 48 | var6 << 16 >>> 16) ^ cb;
        long var14 = var12 ^ 0x5697E5AFD69FL;
        StockClientBootstrap.Z(this, var1, var14, var3, var4, var5, var8, var9, var10, var11);
        return this;
}
    public void A(boolean var1) {
        this.A = var1;
}
    public void Y(long var1, boolean var3, short var4) {
        long var5 = (var1 << 16 | (long)var4 << 48 >>> 48) ^ cb;
        this.w = this.S() ? (Module.c(31161, 0x76CA3A3D9D8F6A42L ^ var5) & 1) != 0 : (this.f() == Category.Macro ? (Module.c(31161, 0x76CA3A3D9D8F6A42L ^ var5) & 1) != 0 : var3);
}
    public void d() {
}
    public void C(boolean var1) {
        this.q = var1;
}
    public boolean l() {
        return this.i;
}
    public Category f() {
        return this.X;
}
    protected final Module declare(String var1, Category var2, String var3, Setting ... var4) {
        this.Q = var1;
        this.X = var2;
        this.W = var3;
        boolean bl = this.w = !this.Z && var2 != Category.Macro;
        if (var4 != null) {
            for (Setting var5 : var4) {
                if (var5 == null || this.l.contains(var5)) continue;
                this.l.add(var5);
}
}
        return this;
}
    public final String name() {
        return this.Q;
}
    public final String description() {
        return this.W;
}
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final List<Setting> settings() {
        if (!this.settingsScanned) {
            List<Setting> list = this.l;
            synchronized (list) {
                if (!this.settingsScanned) {
                    for (Class<?> var1 = this.getClass(); var1 != null && var1 != Module.class; var1 = var1.getSuperclass()) {
                        for (Field var2 : var1.getDeclaredFields()) {
                            if (!Setting.class.isAssignableFrom(var2.getType())) continue;
                            try {
                                var2.setAccessible(true);
                                Setting var3 = (Setting)var2.get(this);
                                if (var3 == null || this.l.contains(var3)) continue;
                                this.l.add(var3);
}
                            catch (Throwable throwable) {
                                // empty catch block
}
}
}
                    this.settingsScanned = true;
}
}
}
        return this.l;
}
    public Module(long var1) {
}
    public void n(boolean var1) {
        this.i = var1;
}
    public String g(long var1) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        return null;
}
    public String Q(int var1, char var2, char var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (!Language.applyForName.c()) {
            return this.b();
}
        return Language.language.R("ENGLISH") ? this.b() : Language.Z(0L, this.Q);
}
    public boolean K() {
        return this.P;
}
    public int h() {
        return this.j;
}
    static {
        try {
            Module.$jnicClinit();
}
        catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException var0) {
            throw new RuntimeException(var0);
}
        f = MinecraftRef.c((byte)0, 0L);
}
}