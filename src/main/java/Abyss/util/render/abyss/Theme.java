/*
 * Decompiled with CFR 0.152.
 */
package Abyss.util.render.abyss;

import Abyss.util.render.abyss.RenderingUtils;
import java.awt.Color;

public final class Theme {
    public static final int PRIMARY = -1689274;
    public static final int PRIMARY_DARK = -7725272;
    public static final int ACCENT = -41876;
    public static final int BG_DARK = -15921904;
    public static final int BG_PANEL = -15330022;
    public static final int TEXT = -855307;
    public static final int TEXT_MUTED = -7697773;
    public static final int OUTLINE = -14408918;

    private Theme() {
}
    public static int primary(int alpha) {
        return Theme.withAlpha(-1689274, alpha);
}
    public static int accent(int alpha) {
        return Theme.withAlpha(-41876, alpha);
}
    public static int withAlpha(int argb, int alpha) {
        return argb & 0xFFFFFF | (alpha & 0xFF) << 24;
}
    public static int pulsingPrimary() {
        long ms = System.currentTimeMillis();
        return RenderingUtils.fadeBetween(-1689274, -7725272, (float)(ms % 2600L) / 1300.0f);
}
    public static Color primaryColor() {
        return new Color(-1689274, true);
}
}