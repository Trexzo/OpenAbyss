/*
 * Decompiled with CFR 0.152.
 */
package Abyss.util.render.abyss;

import java.awt.Color;

public final class ColorBlendUtil {
    private ColorBlendUtil() {
}
    public static Color blendColors(float[] fractions, Color[] colors, float progress) {
        Color color = Color.RED;
        if (fractions != null && colors != null && fractions.length == colors.length) {
            int[] indicies = ColorBlendUtil.getFractionIndicies(fractions, progress);
            float[] range = new float[]{fractions[indicies[0]], fractions[indicies[1]]};
            Color[] colorRange = new Color[]{colors[indicies[0]], colors[indicies[1]]};
            float max = range[1] - range[0];
            float value = progress - range[0];
            float weight = value / max;
            color = ColorBlendUtil.blend(colorRange[0], colorRange[1], 1.0f - weight);
}
        return color;
}
    public static int[] getFractionIndicies(float[] fractions, float progress) {
        int startPoint;
        int[] range = new int[2];
        for (startPoint = 0; startPoint < fractions.length && fractions[startPoint] <= progress; ++startPoint) {
}
        if (startPoint >= fractions.length) {
            startPoint = fractions.length - 1;
}
        range[0] = startPoint - 1;
        range[1] = startPoint;
        return range;
}
    public static Color blend(Color color1, Color color2, double ratio) {
        float r2 = (float)ratio;
        float ir = 1.0f - r2;
        float[] rgb1 = new float[3];
        float[] rgb2 = new float[3];
        color1.getColorComponents(rgb1);
        color2.getColorComponents(rgb2);
        float red = rgb1[0] * r2 + rgb2[0] * ir;
        float green = rgb1[1] * r2 + rgb2[1] * ir;
        float blue = rgb1[2] * r2 + rgb2[2] * ir;
        red = Math.max(0.0f, Math.min(1.0f, red));
        green = Math.max(0.0f, Math.min(1.0f, green));
        blue = Math.max(0.0f, Math.min(1.0f, blue));
        try {
            return new Color(red, green, blue);
}
        catch (IllegalArgumentException e) {
            return Color.RED;
}
}
}