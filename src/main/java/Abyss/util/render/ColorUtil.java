/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.EnumChatFormatting
 *  net.minecraft.util.MathHelper
 */
package Abyss.util.render;

import java.awt.Color;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;

public class ColorUtil {
    
    private static Map g;
    private static Map<Character, Color> P;
    private static Map<EnumChatFormatting, Color> B;
    
    
    public static Color M(char var0) {
        return P.getOrDefault(Character.valueOf(var0), Color.WHITE);
}
    public static int s(int var0, int var1, long var2) {
        return ColorUtil.O(var0, var0, var0, var1);
}
    public static int O(int var0, int var1, int var2, int var5) {
        int var6 = MathHelper.func_76125_a((int)var5, (int)0, (int)255) << 24;
        var6 |= MathHelper.func_76125_a((int)var0, (int)0, (int)255) << 16;
        return (var6 |= MathHelper.func_76125_a((int)var1, (int)0, (int)255) << 8) | MathHelper.func_76125_a((int)var2, (int)0, (int)255);
}
    public static Color I(EnumChatFormatting var0) {
        return P.getOrDefault(var0, Color.WHITE);
}
    public static int U(long var0, int var2) {
        return var2 >> 8 & 0xFF;
}
    public static String A(EnumChatFormatting var0, long var1) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        Color var3 = ColorUtil.I(var0);
        return String.format("#%02X%02X%02X", var3.getRed(), var3.getGreen(), var3.getBlue());
}
    public static int d(long var0, int var2) {
        return var2 & 0xFF;
}
    public static int l(int var0, long var1) {
        return var0 >> 16 & 0xFF;
}
    public static int g(long var0, int var2) {
        return var2 >> 24 & 0xFF;
}
    public static int j(int var0, int var1, long var2) {
        var1 = Math.max(0, Math.min(255, var1));
        return var0 & 0xFFFFFF | var1 << 24;
}
    public static int Y(long var0, Color var2) {
        return ColorUtil.O(var2.getRed(), var2.getGreen(), var2.getBlue(), var2.getAlpha());
}
    public static Color Z(long var0, Color var2, float var3) {
        return ColorUtil.b(var2, 0L, var3, var2.getAlpha());
}
    public static String R(long var0, char var2) {
        Color var3 = ColorUtil.M(var2);
        return String.format("#%02X%02X%02X", var3.getRed(), var3.getGreen(), var3.getBlue());
}
    public static int t(int var0, long var1, byte var3) {
        return ColorUtil.O(var0, var0, var0, 255);
}
    public static Color D(String var0) {
        return P.getOrDefault(Character.valueOf(var0.charAt(0)), Color.WHITE);
}
    public static Color b(Color var0, long var1, float var3, int var4) {
        return new Color(Math.min(Math.max((int)((float)var0.getRed() * var3), 0), 255), Math.min(Math.max((int)((float)var0.getGreen() * var3), 0), 255), Math.min(Math.max((int)((float)var0.getBlue() * var3), 0), 255), var4);
}
    static {
        P = new HashMap<Character, Color>();
        B = new HashMap<EnumChatFormatting, Color>();
        P.put(Character.valueOf('0'), new Color(0, 0, 0));
        P.put(Character.valueOf('1'), new Color(0, 0, 170));
        P.put(Character.valueOf('2'), new Color(0, 170, 0));
        P.put(Character.valueOf('3'), new Color(0, 170, 170));
        P.put(Character.valueOf('4'), new Color(170, 0, 0));
        P.put(Character.valueOf('5'), new Color(170, 0, 170));
        P.put(Character.valueOf('6'), new Color(255, 170, 0));
        P.put(Character.valueOf('7'), new Color(170, 170, 170));
        P.put(Character.valueOf('8'), new Color(85, 85, 85));
        P.put(Character.valueOf('9'), new Color(85, 85, 255));
        P.put(Character.valueOf('a'), new Color(85, 255, 85));
        P.put(Character.valueOf('b'), new Color(85, 255, 255));
        P.put(Character.valueOf('c'), new Color(255, 85, 85));
        P.put(Character.valueOf('d'), new Color(255, 85, 255));
        P.put(Character.valueOf('e'), new Color(255, 255, 85));
        P.put(Character.valueOf('f'), new Color(255, 255, 255));
        B.put(EnumChatFormatting.BLACK, new Color(0, 0, 0));
        B.put(EnumChatFormatting.DARK_BLUE, new Color(0, 0, 170));
        B.put(EnumChatFormatting.DARK_GREEN, new Color(0, 170, 0));
        B.put(EnumChatFormatting.DARK_AQUA, new Color(0, 170, 170));
        B.put(EnumChatFormatting.DARK_RED, new Color(170, 0, 0));
        B.put(EnumChatFormatting.DARK_PURPLE, new Color(170, 0, 170));
        B.put(EnumChatFormatting.GOLD, new Color(255, 170, 0));
        B.put(EnumChatFormatting.GRAY, new Color(170, 170, 170));
        B.put(EnumChatFormatting.DARK_GRAY, new Color(85, 85, 85));
        B.put(EnumChatFormatting.BLUE, new Color(85, 85, 255));
        B.put(EnumChatFormatting.GREEN, new Color(85, 255, 85));
        B.put(EnumChatFormatting.AQUA, new Color(85, 255, 255));
        B.put(EnumChatFormatting.RED, new Color(255, 85, 85));
        B.put(EnumChatFormatting.LIGHT_PURPLE, new Color(255, 85, 255));
        B.put(EnumChatFormatting.YELLOW, new Color(255, 255, 85));
        B.put(EnumChatFormatting.WHITE, new Color(255, 255, 255));
}
}