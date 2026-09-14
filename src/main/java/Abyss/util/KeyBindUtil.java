/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.settings.KeyBinding
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.input.Mouse
 */
package Abyss.util;

import Abyss.util.MinecraftRef;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class KeyBindUtil {
    private static long public static int m(long var0, int var2) {
        return var2 >= 1000 ? KeyBindUtil.w('\u0000', var2 - 1000, 132797583844084L) : var2;
}
    public static void T(int var0, short var1, int var2, short var3) {
        long var4 = ((long)var0 << 32 | (long)var1 << 48 >>> 32 | (long)var3 << 48 >>> 48) ^ a;
        long var6 = var4 ^ 0x1BC8930CD043L;
        KeyBinding.func_74507_a((int)KeyBindUtil.m(var6, var2));
}
    private static boolean isDigit(String var0) {
        if (var0 != null && !var0.isEmpty()) {
            for (int var1 = 0; var1 < var0.length(); ++var1) {
                if (Character.isDigit(var0.charAt(var1))) continue;
                return false;
}
            return true;
}
        return false;
}
    public static boolean V(int var0, long var1) {
        int var5 = KeyBindUtil.m(32881896332787L, var0);
        if (var5 == 0) {
            return false;
}
        return var5 < 0 ? Mouse.isButtonDown((int)(var5 + 100)) : Keyboard.isKeyDown((int)var5);
}
    public static int a(long var0, String var2) {
        int var9;
        String var7;
        if (var2 == null) {
            return Integer.MIN_VALUE;
}
        String var6 = var2.trim();
        if (var6.isEmpty()) {
            return Integer.MIN_VALUE;
}
        switch (var7 = var6.toUpperCase().replace(" ", "").replace("-", "").replace("_", "")) {
            case "NONE": 
            case "UNBOUND": 
            case "CLEAR": {
                return 0;
}
            case "LMB": 
            case "LEFTCLICK": 
            case "LEFTMOUSE": {
                return KeyBindUtil.w('\u0000', 0, 132797583844084L);
}
            case "RMB": 
            case "RIGHTCLICK": 
            case "RIGHTMOUSE": {
                return KeyBindUtil.w('\u0000', 1, 132797583844084L);
}
            case "MMB": 
            case "MIDDLECLICK": 
            case "MIDDLEMOUSE": {
                return KeyBindUtil.w('\u0000', 2, 132797583844084L);
}
            case "SIDE1": 
            case "X1": 
            case "XBUTTON1": {
                return KeyBindUtil.w('\u0000', 3, 132797583844084L);
}
            case "SIDE2": 
            case "X2": 
            case "XBUTTON2": {
                return KeyBindUtil.w('\u0000', 4, 132797583844084L);
}
}
        int var11 = Keyboard.getKeyIndex((String)var6.toUpperCase());
        if (var11 != 0) {
            return var11;
}
        int var12 = Keyboard.getKeyIndex((String)var7);
        if (var12 != 0) {
            return var12;
}
        if (var7.startsWith("MOUSE") && KeyBindUtil.isDigit(var7.substring(5))) {
            var9 = Integer.parseInt(var7.substring(5)) - 1;
            return var9 >= 0 ? KeyBindUtil.w('\u0000', var9, 132797583844084L) : Integer.MIN_VALUE;
}
        if (var7.startsWith("MB") && KeyBindUtil.isDigit(var7.substring(2))) {
            var9 = Integer.parseInt(var7.substring(2)) - 1;
            return var9 >= 0 ? KeyBindUtil.w('\u0000', var9, 132797583844084L) : Integer.MIN_VALUE;
}
        if (var7.startsWith("BUTTON") && KeyBindUtil.isDigit(var7.substring(6))) {
            var9 = Integer.parseInt(var7.substring(6));
            return var9 >= 0 ? KeyBindUtil.w('\u0000', var9, 132797583844084L) : Integer.MIN_VALUE;
}
        return Integer.MIN_VALUE;
}
    public static void h(long var0) {
        var0 = a ^ var0;
        int var2 = (int)((var0 ^ 0x2EDC802D03F7L) >>> 32);
        int var3 = (int)((var0 ^ 0x2EDC802D03F7L) << 32 >>> 48);
        int var4 = (int)((var0 ^ 0x2EDC802D03F7L) << 48 >>> 48);
        int var5 = (int)((var0 ^ 0xD69E447E219L) >>> 56);
        KeyBindUtil.T(var2, (short)var3, MinecraftRef.c((byte)((byte)var5), (long)0L).field_71474_y.field_74313_G.func_151463_i(), (short)var4);
}
    public static int w(char var0, int var1, long var2) {
        return -100 + var1;
}
    public static int x(int var0, long var1) {
        long var3 = var1 ^ 0x3ED4187CDF3DL;
        int var5 = KeyBindUtil.m(var3, var0);
        return var5 < 0 ? var5 + 100 : -1;
}
    public static boolean t(int var0, long var1) {
        long var3 = var1 ^ 0x3123470DBF4EL;
        return KeyBindUtil.m(var3, var0) < 0;
}
    public static void A(long var0, int var2, boolean var3) {
        KeyBinding.func_74510_a((int)KeyBindUtil.m(32881896332787L, var2), (boolean)var3);
}
    public static void o(long var0, int var2) {
        int var7;
        var0 = a ^ var0;
        long var3 = var0 ^ 0x7C232AFB33A8L;
        long var5 = var0 ^ 0x2B5284AFEEBEL;
        KeyBindUtil.A(var3, var7, (var7 = KeyBindUtil.m(var5, var2)) < 0 ? Mouse.isButtonDown((int)(var7 + 100)) : Keyboard.isKeyDown((int)var7));
}
    public static boolean d(int var0, int var1, long var2) {
        return KeyBindUtil.m(32881896332787L, var0) == KeyBindUtil.m(32881896332787L, var1);
}
    public static String p(long var0, char var2, int var3) {
        long var4 = (var0 << 16 | (long)var2 << 48 >>> 48) ^ a;
        long var6 = var4 ^ 0x20FCC43C5CC7L;
        long var8 = var4 ^ 0x1E28DC4083FAL;
        int var10 = KeyBindUtil.m(var8, var3);
        if (var10 == 0) {
            return "NONE";
}
        if (var10 < 0) {
            int var12 = KeyBindUtil.x(var10, var6);
            return var12 < 0 ? "UNKNOWN" : "MOUSE" + (var12 + 1);
}
        String var11 = Keyboard.getKeyName((int)var10);
        return var11 != null && !var11.trim().isEmpty() ? var11 : "UNKNOWN";
}
}