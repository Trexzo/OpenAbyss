/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.audio.ISound
 *  net.minecraft.client.audio.PositionedSoundRecord
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.util.ResourceLocation
 */
package Abyss.module.impl.configuration;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.NotificationsBinder;
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
import Abyss.ui.NotificationToast;
import Abyss.util.ClientUtil;
import Abyss.util.ClipPlayer;
import Abyss.util.MathUtil;
import Abyss.util.MinecraftRef;
import Abyss.util.render.CustomFont;
import Abyss.util.render.abyss.FontManager;
import Abyss.util.render.abyss.RenderingUtils;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public class Notifications
extends Module
implements EventSubscriber {
    private static long a = 123782199563812L;

    private static List<NotificationToast> s;
    private static int F;
    private static Minecraft Y;
    public static ModeSetting stripColor;
    private static final float N = 2.0f;
    public static NumberSetting leaveTime;
    private static final float b = 6.0f;
    private static final float S = 0.7f;
    public static NumberSetting offsetX;
    public static ColorSetting customBackgroundColor;
    private static Map o;
    private static int B;
    private static int J;
    private static String[] x;
    public static ModeSetting graphic;
    public static ColorSetting customstripColor;
    public static ModeSetting sound;
    private static String[] h;
    
    private static Map u;
    private static Object[] v;
    private static long[] r;
    public static NumberSetting stayTime;
    public static NumberSetting offsetY;
    public static BooleanSetting textShadow;
    private static final float R = 1.6f;
    private static final float ABYSS_CARD_HEIGHT = 27.0f;
    private static final float ABYSS_CARD_GAP = 4.0f;
    private static final float ABYSS_SCREEN_MARGIN = 2.0f;

    public static float u(float var0) {
        return Notifications.b(var0);
}
    private static NotificationToast a(String var0, long var1, boolean var3, CustomFont var4, float var5) {
        var1 = a ^ var1;
        long var6 = var1 ^ 0x5CB487284B84L;
        long var8 = var1 ^ 0xBCA024F82AEL;
        ScaledResolution var10 = new ScaledResolution(Y);
        float var11 = Notifications.F(var6);
        if (!FontManager.isReady()) {
            return null;
}
        int split = var0.indexOf(10);
        String title = split < 0 ? var0 : var0.substring(0, split);
        String body = split < 0 ? null : var0.substring(split + 1);
        float var14 = body != null && body.length() > 0 ? Math.max(FontManager.getMedium().getWidth(title), FontManager.getSmall().getWidth(body)) + 12.0f : FontManager.getMedium().getWidth(title) + 12.0f;
        float var15 = var3 ? Math.max(2.0f, (float)var10.getScaledWidth() - var14 - 2.0f - offsetX.L()) : 2.0f + offsetX.L();
        float var16 = var3 ? (float)var10.getScaledWidth() : -var14;
        return new NotificationToast(title, body, System.currentTimeMillis(), var15, var16, var14, var11, var5);
}
    private static float b(float var0) {
        var0 = MathUtil.q(var0, 0.0f, 1.0f);
        float var1 = 1.0f - var0;
        return 1.0f - var1 * var1 * var1;
}
    public static void Z(long var0, String var2, char var3, boolean var4, float var5) {
        long var6 = (var0 << 16 | (long)var3 << 48 >>> 48) ^ a;
        long var8 = var6 ^ 0x3066A2FDF354L;
        int var10 = (int)((var6 ^ 0x3B98734D184FL) >>> 48);
        int var11 = (int)((var6 ^ 0x3B98734D184FL) << 16 >>> 32);
        long var13 = var6 ^ 0x4FCDE44C8B57L;
        CustomFont var15 = Font.O((short)var10, var11);
        switch (graphic.Y()) {
            case "CHAT": {
                ClientUtil.t(var13, var2);
                break;
}
            case "LEFT": {
                NotificationToast t2 = Notifications.a(var2, var8, false, var15, var5);
                if (t2 == null) break;
                s.add(t2);
                break;
}
            case "RIGHT": {
                NotificationToast t3 = Notifications.a(var2, var8, true, var15, var5);
                if (t3 == null) break;
                s.add(t3);
}
}
        switch (sound.Y()) {
            case "BUTTON": {
                ClientUtil.B("gui.button.press");
                break;
}
            case "PLATE": {
                if (var4) {
                    Y.getSoundHandler().playSound((ISound)PositionedSoundRecord.create((ResourceLocation)new ResourceLocation("random.click"), (float)0.6f));
                    break;
}
                Y.getSoundHandler().playSound((ISound)PositionedSoundRecord.create((ResourceLocation)new ResourceLocation("random.click"), (float)0.5f));
                break;
}
            case "SIGMA": {
                if (var4) {
                    ClipPlayer.x("/assets/minecraft/sounds/sigma/enable.wav");
                    break;
}
                ClipPlayer.x("/assets/minecraft/sounds/sigma/disable.wav");
                break;
}
            case "RISE": {
                if (var4) {
                    ClipPlayer.x("/assets/minecraft/sounds/rise/enable.wav");
                    break;
}
                ClipPlayer.x("/assets/minecraft/sounds/rise/disable.wav");
                break;
}
            case "QUICKMACRO": {
                if (var4) {
                    ClipPlayer.x("/assets/minecraft/sounds/quickmacro/enable.wav");
                    break;
}
                ClipPlayer.x("/assets/minecraft/sounds/quickmacro/disable.wav");
                break;
}
            case "SLEEP": {
                if (var4) {
                    ClipPlayer.x("/assets/minecraft/sounds/sleep/enable.wav");
                    break;
}
                ClipPlayer.x("/assets/minecraft/sounds/sleep/disable.wav");
}
}
}
    @Override
    public final void x(long var1, EventBus var3) {
        NotificationsBinder.r(var3, this);
}
    private static void n(CustomFont var0, NotificationToast var1, float var2, float var3, float var4, float var5, int var6, int var7, int var8, long var9, int var11, int var12) {
        float right = var2 + var4;
        float bottom = var3 + var5;
        if (!FontManager.isReady()) {
            return;
}
        RenderingUtils.drawGradientRect(var2, var3, right, bottom, false, Abyss.util.render.abyss.Theme.withAlpha(-15330022, 242), Abyss.util.render.abyss.Theme.withAlpha(-15921904, 242));
        RenderingUtils.drawRect(var2, var3, var2 + 2.0f, bottom, var12);
        RenderingUtils.drawRect(var2 + 2.0f, var3, right, var3 + 0.5f, RenderingUtils.alphaComponent(var12, 100));
        float trackTop = bottom - 1.5f;
        RenderingUtils.drawRect(var2, trackTop, right, bottom, var8);
        float fill = var4 * MathUtil.q(var1.T(), 0.0f, 1.0f);
        if (fill > 0.0f) {
            RenderingUtils.drawRect(var2, trackTop, var2 + fill, bottom, var12);
}
        String title = NotificationToast.m(var1);
        String body = NotificationToast.l(var1);
        if (body != null && body.length() > 0) {
            FontManager.getMedium().drawStringWithShadow(title, var2 + 6.0f, var3 + 4.0f, var11);
            FontManager.getSmall().drawStringWithShadow(body, var2 + 6.0f, var3 + 15.0f, -4605498);
        } else {
            FontManager.getMedium().drawStringWithShadow(title, var2 + 6.0f, var3 + 9.0f, var11);
}
}
    public static float R(float var0) {
        return Notifications.D(var0);
}
    private static int u(int var0, float var1) {
        var1 = MathUtil.q(var1, 0.0f, 1.0f);
        int var4 = Math.round((float)(var0 >> 24 & 0xFF) * var1);
        return var0 & 0xFFFFFF | var4 << 24;
}
    private static int F(long var0) {
        return 27;
}
    public Notifications(long var1) {
        super(a ^ var1 ^ 0xF7933B8D6EL);
        this.declare("Notifications", Category.Configuration, "Module toggle notifications settings", new Setting[0]);
        var1 = a ^ var1;
}
    private static float y() {
        return Math.max(180.0f, Math.min(320.0f, leaveTime.L() * 0.75f));
}
    public static void G(long var0, String var2, boolean var3) {
        var0 = a ^ var0;
        long var4 = (var0 ^ 0x50BBBFEABD7FL) >>> 16;
        int var6 = (int)((var0 ^ 0x50BBBFEABD7FL) << 48 >>> 48);
        Notifications.Z(var4, var2, (char)var6, var3, stayTime.L());
}
    private static float D(float var0) {
        var0 = MathUtil.q(var0, 0.0f, 1.0f);
        return var0 * var0 * var0;
}
    private static int C(int var0, int var1, float var3) {
        var3 = MathUtil.q(var3, 0.0f, 1.0f);
        int var8 = var0 >> 24 & 0xFF;
        int var9 = var0 >> 16 & 0xFF;
        int var10 = var0 >> 8 & 0xFF;
        int var11 = var0 & 0xFF;
        int var12 = var1 >> 24 & 0xFF;
        int var13 = var1 >> 16 & 0xFF;
        int var14 = var1 >> 8 & 0xFF;
        int var15 = var1 & 0xFF;
        int var16 = (int)((float)var8 + (float)(var12 - var8) * var3);
        int var17 = (int)((float)var9 + (float)(var13 - var9) * var3);
        int var18 = (int)((float)var10 + (float)(var14 - var10) * var3);
        int var19 = (int)((float)var11 + (float)(var15 - var11) * var3);
        return var16 << 24 | var17 << 16 | var18 << 8 | var19;
}
    public static float Z() {
        return Notifications.y();
}
    public void onRender2D(int var1, Render2DEvent var2, int var3, char var4) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var5 = ((long)var1 << 32 | (long)var3 << 48 >>> 32 | (long)var4 << 48 >>> 48) ^ a;
        long var9 = var5 ^ 0x163D22CEA6B8L;
        long var11 = var5 ^ 0x4E2F9073748CL;
        long var13 = var5 ^ 0x13475C9F2A7CL;
        int var15 = (int)((var5 ^ 0x69F18CE30237L) >>> 48);
        int var16 = (int)((var5 ^ 0x69F18CE30237L) << 16 >>> 32);
        long var18 = var5 ^ 0xD7AC3E1FC46L;
        long var20 = var5 ^ 0x61D5A3921161L;
        if (graphic.R("LEFT") || graphic.R("RIGHT")) {
            int var25;
            switch (stripColor.Y()) {
                case "THEME": {
                    var25 = Theme.S(0.0, var9);
                    break;
}
                case "THEME_CUSTOM": {
                    var25 = Theme.X(var18, 0.0);
                    break;
}
                default: {
                    var25 = customstripColor.k(var20);
}
}
            CustomFont var50 = Font.O((short)var15, var16);
            ScaledResolution var51 = var2.C;
            long var28 = System.currentTimeMillis();
            float var30 = Notifications.y();
            float var31 = stayTime.L();
            float var32 = leaveTime.L();
            float var33 = Notifications.F(var11);
            float var34 = var33 + 4.0f;
            float var35 = (float)var51.getScaledHeight() - offsetY.L() - var34;
            s.removeIf(var5x -> var5x.c(var28, var30, var31, var32));
            ArrayList<NotificationToast> var36 = new ArrayList<NotificationToast>();
            for (int var37 = 0; var37 < s.size(); ++var37) {
                NotificationToast var38 = s.get(var37);
                if (!(var38.i(var28, var30, var31, var32) > 0.0f)) continue;
                var36.add(var38);
}
            if (!var36.isEmpty()) {
                float var52 = var35;
                float var53 = 0.075f + var2.r * 0.025f;
                for (int var39 = 0; var39 < var36.size(); ++var39) {
                    NotificationToast var40 = (NotificationToast)var36.get(var39);
                    float var41 = var40.i(var28, var30, var31, var32);
                    if (var41 <= 0.0f) continue;
                    if (Float.isNaN(NotificationToast.R(var40))) {
                        NotificationToast.K(var40, var52 - 6.0f);
}
                    NotificationToast.K(var40, MathUtil.k(var52, NotificationToast.R(var40), var53));
                    if (Math.abs(var52 - NotificationToast.R(var40)) <= 0.03f) {
                        NotificationToast.K(var40, var52);
}
                    float var42 = NotificationToast.R(var40);
                    float var43 = MathUtil.k(NotificationToast.e(var40), NotificationToast.O(var40), var41);
                    int var44 = customBackgroundColor.k(var20);
                    int var45 = Notifications.C(var44, -16777216, 0.18f);
                    int var46 = Notifications.C(var25, -1, 0.1f);
                    int var47 = 0x50000000;
                    int var48 = -1;
                    int var49 = var25 | 0xFF000000;
                    float var54 = MathUtil.q(var42, 2.0f, Math.max(2.0f, (float)var51.getScaledHeight() - var33 - 2.0f));
                    Notifications.n(var50, var40, var43, var54, NotificationToast.f(var40), NotificationToast.c(var40), var45, var46, var47, var13, var48, var49);
                    var52 = var42 - var34;
}
}
}
}
    private static String b(byte[] var0) {
        int var1 = 0;
        int var2;
        char[] var3 = new char[var2 = var0.length];
        for (int var4 = 0; var4 < var2; ++var4) {
            int var5;
            if ((var5 = 255 & var0[var4]) < 192) {
                var3[var1++] = (char)var5;
            } else if (var5 < 224) {
                char var6 = (char)((char)(var5 & 31) << 6);
                byte var8 = var0[++var4];
                var6 = (char)(var6 | (char)(var8 & 63));
                var3[var1++] = var6;
            } else if (var4 < var2 - 2) {
                char var12 = (char)((char)(var5 & 15) << 12);
                byte var9 = var0[++var4];
                var12 = (char)(var12 | (char)(var9 & 63) << 6);
                var9 = var0[++var4];
                var12 = (char)(var12 | (char)(var9 & 63));
                var3[var1++] = var12;
            }
        }
        return new String(var3, 0, var1);
    }
    private static void a() {
        Notifications.v[0] = "/\u0014)q9#2";
        Notifications.v[1] = "u[?\u0007tyBL;\r9]UGa\u0011";
        Notifications.v[2] = Short.TYPE;
        Notifications.x[2] = "java/lang/Short";
        Notifications.v[3] = Integer.TYPE;
        Notifications.x[3] = "java/lang/Integer";
        Notifications.v[4] = "W=u0BwQ";
        Notifications.v[5] = Character.TYPE;
        Notifications.x[5] = "java/lang/Character";
        Notifications.v[6] = Void.TYPE;
        Notifications.x[6] = "java/lang/Void";
        Notifications.v[7] = "zX&}\t qW72h.z\\3h";
        Notifications.v[8] = "If5*V?\u000erh@\\F\u0000d2/\u0016:\u000b-1@\u001f%\u0016|c;Lx\u0012c\nzDz\u001cmv!]8\u0016\u001c1+Y{\u001fzm%A~p'n>_:@wa/XFLa`q_yN}r:&";
}
    private static float A(float var0) {
        return (var0 = MathUtil.q(var0, 0.0f, 1.0f)) < 0.5f ? 4.0f * var0 * var0 * var0 : 1.0f - (float)Math.pow(-2.0f * var0 + 2.0f, 3.0) / 2.0f;
}
    static {
        F = 3;
        J = 1;
        B = 3;
        s = new ArrayList<NotificationToast>();
        Y = MinecraftRef.c((byte)0, 0L);
        v = new Object[9];
        x = new String[9];
        o = new HashMap(13);
        h = new String[24];
        u = new HashMap(13);
        r = new long[]{2966946584198201996L, -5404648118911198893L, -8470739123547187686L, 1677113841768690543L, 4283053398555877610L, -7662428232398007713L, 4506934344420597124L, -3325439409045599548L, -6260022984373518689L, 2630741561568766778L, -302759479584201442L, -908384258933398582L, 644954613818694405L, -8163367185621506862L};
        graphic = new ModeSetting("Graphic", false, "RIGHT", "CHAT", "LEFT", "RIGHT", "DISABLE");
        customBackgroundColor = new ColorSetting("Custom-background-color", "000000");
        stripColor = new ModeSetting("Strip-color", "THEME", "THEME_CUSTOM", "CUSTOM");
        customstripColor = new ColorSetting("Customstrip-color", "FFFFFF");
        sound = new ModeSetting("Sound", "BUTTON", "PLATE", "SIGMA", "RISE", "QUICKMACRO", "SLEEP", "DISABLE");
        offsetY = new NumberSetting("Offset-Y", 50.0f, 0.0f, 200.0f, 1.0f);
        stayTime = new NumberSetting("Stay-time", 3000.0f, 1000.0f, 12000.0f, 50.0f);
        leaveTime = new NumberSetting("Leave-time", 600.0f, 200.0f, 2000.0f, 25.0f);
        textShadow = new BooleanSetting("Text-shadow", true);
        offsetX = new NumberSetting("Offset-X", 0.0f, 0.0f, 100.0f, 1.0f);
}
}