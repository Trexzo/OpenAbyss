/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.util.ResourceLocation
 */
package Abyss.module.impl.visual;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.HUDBinder;
import Abyss.event.events.Render2DEvent;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.impl.configuration.Font;
import Abyss.module.impl.configuration.Theme;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.ColorSetting;
import Abyss.setting.settings.ModeSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.util.CombatUtil;
import Abyss.util.MathUtil;
import Abyss.util.MoveUtil;
import Abyss.util.render.CustomFont;
import Abyss.util.render.abyss.FontManager;
import Abyss.util.render.abyss.FontRenderer;
import Abyss.util.render.abyss.RenderingUtils;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class HUD
extends Module
implements EventSubscriber {
    private static String[] k;
    
    private static ResourceLocation r;
    public static BooleanSetting fps;
    public static BooleanSetting bps;
    public static BooleanSetting version;
    public static BooleanSetting userInfo;
    
    public static BooleanSetting coordinate;
    private static Object[] h;
        public static BooleanSetting time;
    
    public static ModeSetting infoMode;
    private static long g;
    public static BooleanSetting health;
    public static BooleanSetting watermark;
    public static BooleanSetting useCustomFont;
    public static final ModeSetting theme;
    public static final ModeSetting customTheme;
    public static final NumberSetting themeOffset;
    public static final NumberSetting themeSpeed;
    public static final ColorSetting themeColor1;
    public static final ColorSetting themeColor2;
    public static final ColorSetting themeColor3;
    private static boolean abyssFontReady;
    private static final DateTimeFormatter TIME_FMT_HMS;
    private static final DateTimeFormatter TIME_FMT_HM;

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
    public void onRender2D(Render2DEvent var1, long var2) {
        CustomFont var25 = Font.F(0L);
        ScaledResolution var26 = var1.C;
        String var27 = "";
        if (bps.c()) {
            var27 = var27 + MathUtil.W(MoveUtil.k(32438560973981L)) + " BPS";
}
        if (coordinate.c()) {
            if (bps.c()) {
                var27 = var27 + " | ";
}
            var27 = var27 + (int)HUD.f.field_71439_g.field_70165_t + "/" + (int)HUD.f.field_71439_g.field_70163_u + "/" + (int)HUD.f.field_71439_g.field_70161_v;
}
        var25.T(37697014677608L, var27, 0.0f, (float)var26.func_78328_b() - var25.o(60714858652844L), -1);
        if (health.c()) {
            String var43 = CombatUtil.h(CombatUtil.P((EntityLivingBase)HUD.f.field_71439_g), HUD.f.field_71439_g.func_110138_aP(), 88877475006969L) + MathUtil.W(CombatUtil.h((EntityLivingBase)HUD.f.field_71439_g) + CombatUtil.D((EntityLivingBase)HUD.f.field_71439_g)) + "\u2764";
            var25.T(37697014677608L, var43, (float)var26.func_78326_a() / 2.0f - var25.R(var43, 52019766876817L) / 2.0f, (float)var26.func_78328_b() / 2.0f + var25.o(60714858652844L) + 2.0f, -1);
}
        float var44 = 1.0f;
        if (watermark.c()) {
            this.drawAbyssWatermark(var26, var44);
}
}
    private void drawAbyssWatermark(ScaledResolution sr, float uiScale) {
        try {
            if (!abyssFontReady && !(abyssFontReady = FontManager.warmStep())) {
                return;
}
            FontRenderer fr = FontManager.get();
            long now = System.currentTimeMillis();
            long period = 1000L;
            float off = (float)(now % period) / ((float)period / 2.0f);
            int primary = Theme.S(0.0, 35338930340239L);
            int wColor = RenderingUtils.fadeBetween(primary, RenderingUtils.darker(primary, 0.49f), off);
            String text = "Abyss";
            GlStateManager.func_179094_E();
            float s = uiScale <= 0.0f ? 1.0f : uiScale;
            GlStateManager.func_179152_a((float)s, (float)s, (float)s);
            float bx = 2.0f / s;
            float by = 2.0f / s;
            fr.drawStringWithShadow("A", bx, by, wColor);
            float ax = bx + fr.getWidth("A");
            fr.drawStringWithShadow(text.substring(1), ax, by, -1);
            float x = bx + fr.getWidth(text) + 3.0f;
            if (time.c()) {
                String tm = LocalDateTime.now().format(TIME_FMT_HM);
                fr.drawStringWithShadow("[", x, by, -1);
                fr.drawStringWithShadow(tm, x += fr.getWidth("["), by, -7697773);
                fr.drawStringWithShadow("]", x += fr.getWidth(tm), by, -1);
}
            GlStateManager.func_179121_F();
}
        catch (Throwable throwable) {
            // empty catch block
}
}
    @Override
    public final void x(long var1, EventBus var3) {
        HUDBinder.H(var3, this);
}
    public HUD(long var1) {
        super(a ^ var1 ^ 0x7F541FDAC077L);
        this.declare("HUD", Category.Visual, "Aka \"Heads up display\"", new Setting[0]);
        var1 = a ^ var1;
}
    private static void a() {
        HUD.h[0] = "\u0004\u0005bVVJ";
        HUD.h[1] = ">`\u0001A\u0007j\tw\u0005KJN\u001e|_W";
        HUD.h[2] = Long.TYPE;
        HUD.k[2] = "java/lang/Long";
        HUD.h[3] = "'\u000e\u0007\b\u0018\u0001:";
        HUD.h[4] = Void.TYPE;
        HUD.k[4] = "java/lang/Void";
        HUD.h[5] = "\u001ecFd62\u0015lW+W<\u001egSq";
        HUD.h[6] = "\u0005vbs\u0018\u000f\u001b0b\u00154vRsa+\u0018\u001a\u001aua+tO\u00142xq\t\u0014\u0016w%\u0015NI[)zm\u0018\u000f\u0014=\u001c/\u0006J\u0014-p-\u000f\u0006\rL";
}
                Cipher var5 = Cipher.getInstance("DES/CBC/PKCS5Padding");
            var5.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
            String[] var12 = new String[21];
            int var10 = 0;
            String var9 = ";.\u00f3\u00f8\u00baE\u00e5\u00a5\u00d7eOG\u00dd\u00f0\u00f5\u00f7\u0010\u00b8\u009dm\u0012\u00af\"r\u00c62\u0012\u0099\u00c0\u001dDd\u00ef\u0010\u00d5\u00b8\u009e\u00d0\u00ce\u00f4w\u00a3.ee\u00bc\u00f6\u00d1W\u00c5\u0010<tA\u00c7\"\u00f7xP\u000b\u00af\u0096\u00ed\u00e1\u00f6\u009b\u009e(\u00d2\u00ee\u00f1\u009dQ1>\u00ae\r\u00b3]\u00ec0\u0086\u0000\u008d\u00fd(\u00dc\u00fd&\u00ae.L\u00e57Yv\u00edvg\u00d2\u00b2\u0086\u00fa\u00dc]\u001d\u009c\u00d1 \u00e5k\u00de\u00c4\u00dc\u00b2\u00e8\u00c2\u00cc\u001b~\u00c2U\u0012e\u00dbw\u00bcC\u0005>\u00d1\u00b4\u000b\u008d\u00d6T\u00cc\u00e0\u00d6\u0001N\u0010\u008c\u0085\u0010p&\u00cd\u009b\u00b2f\u0094H\u00ff\u00c6KF\u009d@S\u0003\u00fc\u00ad\u00c8\u001b\u00af\f\u00e4Y\u00f0\u00820\u0080\u00e7CB\u00c5T\u008c\t\u009b\u00f5a\u00f7\u0085\u0019\u00a5G^\n;\u009a\u00e8 CC\u00f5-p\u0088\u0099\u00a8X\u00d2e\u00e5\u00f6P|\u00f8l\u00a6\u0095\u00c2KyECj\u0003\u00d1\u00df1\u0010\u009d.\u00ef\u00f1J\u00c8\u00f2}\u000e\u0096H84\tC\u00c5\u0010\r)\u00df\u00ad1\u00c1\u00e0\u00f4yzL\u0090\u0097\u00f1\u00e7\u00ba\u0010\u0014\u00d64\u00d8j\u0095{\u0005\u0087\u00e2a\u00dfa\u00fb\u00b6!\u0010\u00e6\u00fb\u00c4\u00f2\u00fd\u0081\u00d5d\u00f6T\u0002\u00ef\u00c2$\u00e9\u00c40 \u00e5\u00d5^Jh`b\u00cfk\u00d3W\u0005\u00f7\u00e9p\u009c\u00cf\u0010\r\u00d8\u0088\u00c5\u00dc\u001c/\u00c8\u00c4\u00eeW\u00b8\u0088\u009ff\u00d7:N\u00d24+\u0016M\u0085\u0081\u00f2hCZ\u0010\u00c2gO\u0084%\u00c0q\u0017*aa\u00a2Bd\u00dfi\u0010\u009b\u00e9% \u00a1U;\u0013\\\u00f6\u00d2\u00f1]\u00c2\u0084\u007f\u0018o3\u00c5\u00e5W\u00f5\u000f*\u00ad%\u0014\u001f\u00de\u008e\u00ddh\u00baE\u00d8\u00e9\u00a7\u008f,%\u0010\u00c3\u00e7\u0098\u001a\u00bb;y\u00a0,\u00c2\u009a\u00eb\u0085\u00c3\u00931(Fr\u0099pq2X\u00e1\u00ffq\u0002\u00af\u0019\u0012\u00d7i[\u00f5\"\u00e1$\u00e2\u00b8\u001c\u0015v*\u00c0\u0081\u00a27w:\u0011\u00db\u0018\u009fGYu \u00b0gb\u0018\u00eaM-s5\u00e6\u0090\u00f6\u008d9\n{5.\u00a1\u00a8\\w\u00d9\u00f3QcZ;\u0003\u0090\u00f9\u00db";
            int var11 = ";.\u00f3\u00f8\u00baE\u00e5\u00a5\u00d7eOG\u00dd\u00f0\u00f5\u00f7\u0010\u00b8\u009dm\u0012\u00af\"r\u00c62\u0012\u0099\u00c0\u001dDd\u00ef\u0010\u00d5\u00b8\u009e\u00d0\u00ce\u00f4w\u00a3.ee\u00bc\u00f6\u00d1W\u00c5\u0010<tA\u00c7\"\u00f7xP\u000b\u00af\u0096\u00ed\u00e1\u00f6\u009b\u009e(\u00d2\u00ee\u00f1\u009dQ1>\u00ae\r\u00b3]\u00ec0\u0086\u0000\u008d\u00fd(\u00dc\u00fd&\u00ae.L\u00e57Yv\u00edvg\u00d2\u00b2\u0086\u00fa\u00dc]\u001d\u009c\u00d1 \u00e5k\u00de\u00c4\u00dc\u00b2\u00e8\u00c2\u00cc\u001b~\u00c2U\u0012e\u00dbw\u00bcC\u0005>\u00d1\u00b4\u000b\u008d\u00d6T\u00cc\u00e0\u00d6\u0001N\u0010\u008c\u0085\u0010p&\u00cd\u009b\u00b2f\u0094H\u00ff\u00c6KF\u009d@S\u0003\u00fc\u00ad\u00c8\u001b\u00af\f\u00e4Y\u00f0\u00820\u0080\u00e7CB\u00c5T\u008c\t\u009b\u00f5a\u00f7\u0085\u0019\u00a5G^\n;\u009a\u00e8 CC\u00f5-p\u0088\u0099\u00a8X\u00d2e\u00e5\u00f6P|\u00f8l\u00a6\u0095\u00c2KyECj\u0003\u00d1\u00df1\u0010\u009d.\u00ef\u00f1J\u00c8\u00f2}\u000e\u0096H84\tC\u00c5\u0010\r)\u00df\u00ad1\u00c1\u00e0\u00f4yzL\u0090\u0097\u00f1\u00e7\u00ba\u0010\u0014\u00d64\u00d8j\u0095{\u0005\u0087\u00e2a\u00dfa\u00fb\u00b6!\u0010\u00e6\u00fb\u00c4\u00f2\u00fd\u0081\u00d5d\u00f6T\u0002\u00ef\u00c2$\u00e9\u00c40 \u00e5\u00d5^Jh`b\u00cfk\u00d3W\u0005\u00f7\u00e9p\u009c\u00cf\u0010\r\u00d8\u0088\u00c5\u00dc\u001c/\u00c8\u00c4\u00eeW\u00b8\u0088\u009ff\u00d7:N\u00d24+\u0016M\u0085\u0081\u00f2hCZ\u0010\u00c2gO\u0084%\u00c0q\u0017*aa\u00a2Bd\u00dfi\u0010\u009b\u00e9% \u00a1U;\u0013\\\u00f6\u00d2\u00f1]\u00c2\u0084\u007f\u0018o3\u00c5\u00e5W\u00f5\u000f*\u00ad%\u0014\u001f\u00de\u008e\u00ddh\u00baE\u00d8\u00e9\u00a7\u008f,%\u0010\u00c3\u00e7\u0098\u001a\u00bb;y\u00a0,\u00c2\u009a\u00eb\u0085\u00c3\u00931(Fr\u0099pq2X\u00e1\u00ffq\u0002\u00af\u0019\u0012\u00d7i[\u00f5\"\u00e1$\u00e2\u00b8\u001c\u0015v*\u00c0\u0081\u00a27w:\u0011\u00db\u0018\u009fGYu \u00b0gb\u0018\u00eaM-s5\u00e6\u0090\u00f6\u008d9\n{5.\u00a1\u00a8\\w\u00d9\u00f3QcZ;\u0003\u0090\u00f9\u00db".length();
            int var8 = 16;
            int var18 = -1;
            block6: while (true) {
                String var19 = var9.substring(++var18, var18 + var8);
                int var10001 = -1;
                while (true) {
                    byte[] var13 = var5.doFinal(var19.getBytes("ISO-8859-1"));
                    String var27 = HUD.b(var13).intern();
                    switch (var10001) {
                        case 0: {
                            var12[var10++] = var27;
                            if ((var18 += var8) >= var11) {
                                b = var12;
                                c = new String[21];
                                var10003 = new byte[]{(byte)(var14 >>> 56), 0, 0, 0, 0, 0, 0, 0};
                                for (int var1 = 1; var1 < 8; ++var1) {
                                    var10003[var1] = (byte)(var14 << var1 * 8 >>> 56);
}
                                Cipher var0 = Cipher.getInstance("DES/CBC/NoPadding");
                                var0.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
                                byte[] var4 = var0.doFinal(new byte[]{-125, 107, 99, -54, 63, 45, 113, -121});
                                long var31 = ((long)var4[0] & 0xFFL) << 56 | ((long)var4[1] & 0xFFL) << 48 | ((long)var4[2] & 0xFFL) << 40 | ((long)var4[3] & 0xFFL) << 32 | ((long)var4[4] & 0xFFL) << 24 | ((long)var4[5] & 0xFFL) << 16 | ((long)var4[6] & 0xFFL) << 8 | (long)var4[7] & 0xFFL;
                                var10001 = -1;
                                g = var31;
                                return;
}
                            var8 = var9.charAt(var18);
                            break;
}
                        default: {
                            var12[var10++] = var27;
                            if ((var18 += var8) < var11) {
                                var8 = var9.charAt(var18);
                                continue block6;
}
                            var9 = "\u00e6E\u0012\u00c3\u009a.\u000bB\u001d\u00e4\n\u00f8\u00e6\u00d2\u00cf\u00fd\u0018\u00e8VZ)C\u0081YSy\u0005\u00eb\u00ecN\u001dQ<\u00e6\u00e3\u0005\u00b4\u00c3\u00fc[\u008e";
                            var11 = "\u00e6E\u0012\u00c3\u009a.\u000bB\u001d\u00e4\n\u00f8\u00e6\u00d2\u00cf\u00fd\u0018\u00e8VZ)C\u0081YSy\u0005\u00eb\u00ecN\u001dQ<\u00e6\u00e3\u0005\u00b4\u00c3\u00fc[\u008e".length();
                            var8 = 16;
                            var18 = -1;
}
}
                    var19 = var9.substring(++var18, var18 + var8);
                    var10001 = 0;
}
                break;
}
}
        catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException var16) {
            throw new RuntimeException(var16);
}
}
    static {
        theme = Theme.theme;
        customTheme = Theme.customTheme;
        themeOffset = Theme.offset;
        themeSpeed = Theme.timerMultiplier;
        themeColor1 = Theme.customColor1;
        themeColor2 = Theme.customColor2;
        themeColor3 = Theme.customColor3;
        r = new ResourceLocation("watermark/logo.png");
        abyssFontReady = false;
        TIME_FMT_HMS = DateTimeFormatter.ofPattern("HH:mm:ss");
        TIME_FMT_HM = DateTimeFormatter.ofPattern("HH:mm");
        health = new BooleanSetting("Health", false);
        fps = new BooleanSetting("FPS", true);
        userInfo = new BooleanSetting("User Information", true);
        bps = new BooleanSetting("BPS", false);
        coordinate = new BooleanSetting("Coordinate", false);
        time = new BooleanSetting("Time", false);
        version = new BooleanSetting("Version", true);
        infoMode = new ModeSetting("Info-mode", "PLAIN_TEXT", "INFO", "LOGO");
        watermark = new BooleanSetting("Watermark", true);
        useCustomFont = new BooleanSetting("Custom Font", true);
}
}