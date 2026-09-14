/*
 * Decompiled with CFR 0.152.
 */
package Abyss.util;

import Abyss.enums.AnimationDirection;
import Abyss.util.EasedAnimation;

public class SmoothStepAnimation
extends EasedAnimation {
    public SmoothStepAnimation(int var1, double var2, AnimationDirection var4) {
        super(var1, var2, var4);
}
    public SmoothStepAnimation(int var1, double var2) {
        super(var1, var2);
}
    @Override
    protected double H(double var1) {
        return -2.0 * Math.pow(var1, 3.0) + 3.0 * Math.pow(var1, 2.0);
}
}