/*
 * Decompiled with CFR 0.152.
 */
package Abyss.ui;

public class TextInputCharFilter {
    private TextInputCharFilter() {
}
    public static boolean R(char var0) {
        return var0 >= ' ' && var0 != '\u007f';
}
}