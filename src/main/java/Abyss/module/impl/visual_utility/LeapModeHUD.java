/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.network.play.server.S02PacketChat
 *  net.minecraft.util.EnumChatFormatting
 */
package Abyss.module.impl.visual_utility;

import Abyss.enums.MegaWallsClass;
import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.LeapModeHUDBinder;
import Abyss.event.events.ReceivePacketEvent;
import Abyss.event.events.Render2DEvent;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.setting.Setting;
import Abyss.setting.settings.NumberSetting;
import Abyss.setting.settings.PercentageSetting;
import Abyss.util.TeamPrefixUtil;
import java.awt.Color;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.EnumChatFormatting;

public class LeapModeHUD
extends Module
implements EventSubscriber {
    private static String[] c;

    private static String[] b;

    private static Map d;

    private static long a = 48115485393190L;

    public static PercentageSetting backgroundOpacity;
    private static String D;
    private static String Y;
    public static NumberSetting offsetY;
    
    public static NumberSetting scale;
        private static Map k;
    private static long[] e;
    public static NumberSetting offsetX;
    private String h;
    
    

    private void j$r1(long var1) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        var1 = a ^ var1;
        this.h = EnumChatFormatting.GOLD + LeapModeHUD.b(25405, 0xFEF100E210D9808L ^ var1);
}
    @Override
    public final void x(long var1, EventBus var3) {
        LeapModeHUDBinder.k(var3, this);
}
    public LeapModeHUD(long var1) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        super(a ^ var1 ^ 0x4B8D44DDEE8CL);
        this.declare("LeapModeHUD", Category.Visual_utility, "Show the current Spider leap mode on screen", new Setting[0]);
        var1 = a ^ var1;
        this.h = EnumChatFormatting.GOLD + "Arrow";
}
    @Override
    public void A(long var1) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var3 = var1 ^ 0x35937023DA8L;
        this.j$r1(var3);
}
    private static String b(int var0, long var1) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        int var5 = var0 ^ (int)(var1 & 0x7FFFL) ^ 0x5B3B;
        if (c[var5] == null) {
            Object[] var4;
            try {
                Long var3 = Thread.currentThread().getId();
                var4 = (Object[])d.get(var3);
                if (var4 == null) {
                    var4 = new Object[]{Cipher.getInstance("DES/CBC/PKCS5Padding"), SecretKeyFactory.getInstance("DES"), new IvParameterSpec(new byte[8])};
                    d.put(var3, var4);
}
}
            catch (Exception var10) {
                throw new RuntimeException("Abyss/module/impl/visual_utility/LeapModeHUD", var10);
}
            byte[] var6 = new byte[8];
            var6[0] = (byte)(var1 >>> 56);
            for (int var7 = 1; var7 < 8; ++var7) {
                var6[var7] = (byte)(var1 << var7 * 8 >>> 56);
}
            DESKeySpec var11 = new DESKeySpec(var6);
            SecretKey var8 = ((SecretKeyFactory)var4[1]).generateSecret(var11);
            ((Cipher)var4[0]).init(2, (Key)var8, (IvParameterSpec)var4[2]);
            byte[] var9 = b[var5].getBytes("ISO-8859-1");
            LeapModeHUD.c[var5] = LeapModeHUD.b(((Cipher)var4[0]).doFinal(var9));
}
        return c[var5];
}
    @Override
    public void i(long var1) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var3 = var1 ^ 0x46C6B31E5CBDL;
        this.j$r1(var3);
}
    public void onRender2D(long var1, Render2DEvent var3) {
        if (LeapModeHUD.f.thePlayer != null && TeamPrefixUtil.i() && TeamPrefixUtil.F(22611545248530L, LeapModeHUD.f.thePlayer.getName()) == MegaWallsClass.SPIDER) {
            String var6 = "Leap Mode: " + this.h;
            float var7 = scale == null ? 1.0f : scale.L();
            float var8 = (float)var3.C.getScaledWidth() / 2.0f + (offsetX == null ? 0.0f : offsetX.L());
            float var9 = (float)var3.C.getScaledHeight() / 2.0f + (offsetY == null ? 0.0f : offsetY.L());
            int var10 = LeapModeHUD.f.fontRendererObj.getStringWidth(var6);
            int var11 = LeapModeHUD.f.fontRendererObj.FONT_HEIGHT;
            int var12 = backgroundOpacity == null ? 0 : backgroundOpacity.k() * 255 / 100;
            GlStateManager.pushMatrix();
            GlStateManager.translate((float)var8, (float)var9, (float)0.0f);
            GlStateManager.scale((float)var7, (float)var7, (float)1.0f);
            int var13 = -var10 / 2 - 4;
            int var14 = -var11 / 2 - 3;
            int var15 = var10 / 2 + 4;
            int var16 = var11 / 2 + 3;
            if (var12 > 0) {
                Gui.drawRect((int)var13, (int)var14, (int)var15, (int)var16, (int)new Color(0, 0, 0, var12).getRGB());
}
            LeapModeHUD.f.fontRendererObj.drawStringWithShadow(var6, (float)(-var10) / 2.0f, (float)(-var11) / 2.0f, 0xFFFFFF);
            GlStateManager.popMatrix();
}
}
    public void onReceivePacket(long var1, ReceivePacketEvent var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        String var4;
        if (var3.d instanceof S02PacketChat && (var4 = ((S02PacketChat)var3.d).getChatComponent().getUnformattedText()) != null) {
            if (var4.contains("Your primary Leap skill switched to Arrow mode.")) {
                this.h = EnumChatFormatting.GOLD + "Arrow";
            } else if (var4.contains("Your primary Leap skill switched to Arced mode.")) {
                this.h = EnumChatFormatting.AQUA + "Arced";
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
    static {
        d = new HashMap(13);
        b = new String[]{"\b/\u00d5\u00f1\u00eb'\u00f4s11f\u00ae3\u00c5\u00df\u00b6k\u00ce\u000eZ\u0099\u00ad]\u00f8%\u00e0Q\u0017\u00b2\u00a3j\u008c4\u001eZ\u0099\u00cd\u00cdxQI\u0016\u0080\u00ef\u009b\u00b0\u00bdn", "\u00c3\u00e7K\u0091\u0082\u0095\u00c1\u0094", "L\u00aegI$\u00e4xF\u001e\u00f9u\u0003\u00e8ZAh", ">>\u00df\u001e2Y\u0013\u00a9(\u001cp2C/\u00b1\u00c5\u00d3\u00c4\u0085\u00aa\u00cc:B_\":\u00feZO\u00cb\u00a7\u00d8\u00c1\u00beX\u0088|\u0084Y\u0080v0\u0016\u00deT6N\u00a2", "_U\u0000\u008bC8\u008fn,m\u00aa\u00cf\u00df\u00ca\u00e5d\u00a1\u00fa\u00e1\u00c4t\u009d\u00bch\u0092P\u00a8J\u00ab\u008d\u00c2\u0084\u00013\u0089\u00af\u0007\u00af\u00f3;\u00ddE\u00849\u001ab\u0014\u0012", "AT\u0015\u00ed\u00bb\u00a0\u00fa\u00b0", "\u00c0\u00f3TnPR\u0006\u00cf\u00fax\u00dd\u00c4\u00d9b\u008c\u00b4\u00c3\u00e5\u00b1\u00c2\u00d5\u00d1*\u00923:\u00f8%\u00ae\u00a8\u00aa\u00a7SG\u00eb\u00dbz.\u00ac\u007f\u0000\u00b8\u00c9\u0098^\u008fCR", "\u00b6\u0013n\u00bc\u00bd\u00c8\u009d\u00ee"};
        c = new String[8];
        Y = "Your primary Leap skill switched to Arced mode.";
        D = "Your primary Leap skill switched to Arrow mode.";
        k = new HashMap(13);
        e = new long[]{-3107896629969578717L, -3044017662710845743L, 1183114144443129644L};
        offsetX = new NumberSetting("Offset-X", 4.0f, 0.0f, 1000.0f, 1.0f);
        scale = new NumberSetting("Scale", 0.9f, 0.5f, 3.0f, 0.01f);
        backgroundOpacity = new PercentageSetting("Background-opacity", 40);
        offsetY = new NumberSetting("Offset-Y", 120.0f, 0.0f, 1000.0f, 1.0f);
}
}