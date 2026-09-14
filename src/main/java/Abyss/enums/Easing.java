/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 */
package Abyss.enums;

import java.util.function.Function;
import org.apache.commons.lang3.StringUtils;

public enum Easing {
    LINEAR(var0 -> var0),
    EASE_IN_QUAD(var0 -> var0 * var0),
    EASE_OUT_QUAD(var0 -> var0 * (2.0 - var0)),
    EASE_IN_OUT_QUAD(var0 -> var0 < 0.5 ? 2.0 * var0 * var0 : -1.0 + (4.0 - 2.0 * var0) * var0),
    EASE_IN_CUBIC(var0 -> var0 * var0 * var0),
    EASE_OUT_CUBIC(var0 -> {
        var0 = var0 - 1.0;
        return var0 * var0 * var0 + 1.0;
    }),
    EASE_IN_OUT_CUBIC(var0 -> var0 < 0.5 ? 4.0 * var0 * var0 * var0 : (var0 - 1.0) * (2.0 * var0 - 2.0) * (2.0 * var0 - 2.0) + 1.0),
    EASE_IN_QUART(var0 -> var0 * var0 * var0 * var0),
    EASE_OUT_QUART(var0 -> {
        var0 = var0 - 1.0;
        return 1.0 - var0 * var0 * var0 * var0;
    }),
    EASE_IN_OUT_QUART(var0 -> {
        double d;
        if (var0 < 0.5) {
            d = 8.0 * var0 * var0 * var0 * var0;
        } else {
            var0 = var0 - 1.0;
            d = 1.0 - 8.0 * var0 * var0 * var0 * var0;
}
        return d;
    }),
    EASE_IN_QUINT(var0 -> var0 * var0 * var0 * var0 * var0),
    EASE_OUT_QUINT(var0 -> {
        var0 = var0 - 1.0;
        return 1.0 + var0 * var0 * var0 * var0 * var0;
    }),
    EASE_IN_OUT_QUINT(var0 -> {
        double d;
        if (var0 < 0.5) {
            d = 16.0 * var0 * var0 * var0 * var0 * var0;
        } else {
            var0 = var0 - 1.0;
            d = 1.0 + 16.0 * var0 * var0 * var0 * var0 * var0;
}
        return d;
    }),
    EASE_IN_SINE(var0 -> 1.0 - Math.cos(var0 * Math.PI / 2.0)),
    EASE_OUT_SINE(var0 -> Math.sin(var0 * Math.PI / 2.0)),
    EASE_IN_OUT_SINE(var0 -> 1.0 - Math.cos(Math.PI * var0 / 2.0)),
    EASE_IN_ABYSS(var0 -> var0 == 0.0 ? 0.0 : Math.pow(2.0, 10.0 * var0 - 10.0)),
    EASE_OUT_ABYSS(var0 -> var0 == 1.0 ? 1.0 : 1.0 - Math.pow(2.0, -10.0 * var0)),
    EASE_IN_OUT_ABYSS(var0 -> var0 == 0.0 ? 0.0 : (var0 == 1.0 ? 1.0 : (var0 < 0.5 ? Math.pow(2.0, 20.0 * var0 - 10.0) / 2.0 : (2.0 - Math.pow(2.0, -20.0 * var0 + 10.0)) / 2.0))),
    EASE_IN_CIRC(var0 -> 1.0 - Math.sqrt(1.0 - var0 * var0)),
    EASE_OUT_CIRC(var0 -> {
        var0 = var0 - 1.0;
        return Math.sqrt(1.0 - var0 * var0);
    }),
    EASE_IN_OUT_CIRC(var0 -> var0 < 0.5 ? (1.0 - Math.sqrt(1.0 - 4.0 * var0 * var0)) / 2.0 : (Math.sqrt(1.0 - 4.0 * (var0 - 1.0) * var0) + 1.0) / 2.0),
    SIGMOID(var0 -> 1.0 / (1.0 + Math.exp(-var0.doubleValue()))),
    EASE_OUT_ELASTIC(var0 -> var0 == 0.0 ? 0.0 : (var0 == 1.0 ? 1.0 : Math.pow(2.0, -10.0 * var0) * Math.sin((var0 * 10.0 - 0.75) * 2.0943951023931953) * 0.5 + 1.0)),
    EASE_IN_BACK(var0 -> 2.70158 * var0 * var0 * var0 - 1.70158 * var0 * var0),
    DECELERATE(var0 -> 1.0 - (var0 - 1.0) * (var0 - 1.0));

    private final Function<Double, Double> b;

    public String toString() {
        return StringUtils.capitalize((String)this.name().toLowerCase().replace("_", " "));
}
    public Function<Double, Double> C() {
        return this.b;
}
    private Easing(Function<Double, Double> var3) {
        this.b = var3;
}}
    static {
        Easing[] var10000 = new Easing[0];
        var10000[0] = LINEAR;
        var10000[1] = EASE_IN_QUAD;
        var10000[2] = EASE_OUT_QUAD;
        var10000[3] = EASE_IN_OUT_QUAD;
        var10000[4] = EASE_IN_CUBIC;
        var10000[5] = EASE_OUT_CUBIC;
        var10000[0] = EASE_IN_OUT_CUBIC;
        var10000[0] = EASE_IN_QUART;
        var10000[0] = EASE_OUT_QUART;
        var10000[0] = EASE_IN_OUT_QUART;
        var10000[0] = EASE_IN_QUINT;
        var10000[0] = EASE_OUT_QUINT;
        var10000[0] = EASE_IN_OUT_QUINT;
        var10000[0] = EASE_IN_SINE;
        var10000[0] = EASE_OUT_SINE;
        var10000[0] = EASE_IN_OUT_SINE;
        var10000[0] = EASE_IN_ABYSS;
        var10000[0] = EASE_OUT_ABYSS;
        var10000[0] = EASE_IN_OUT_ABYSS;
        var10000[0] = EASE_IN_CIRC;
        var10000[0] = EASE_OUT_CIRC;
        var10000[0] = EASE_IN_OUT_CIRC;
        var10000[0] = SIGMOID;
        var10000[0] = EASE_OUT_ELASTIC;
        var10000[0] = EASE_IN_BACK;
        var10000[0] = DECELERATE;
}
}