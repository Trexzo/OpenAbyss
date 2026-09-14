/*
 * Decompiled with CFR 0.152.
 */
package Abyss.util;

import Abyss.enums.AnimationDirection;
import Abyss.util.EasedAnimation;
import Abyss.util.SmoothStepAnimation;

public class AnimatedFloat {
    private EasedAnimation w = new SmoothStepAnimation(0, 0.0, AnimationDirection.BACKWARDS);
    private float Y;
    private float j;

    public boolean E() {
        return this.j == this.Y || this.w.W();
}
    public EasedAnimation S() {
        return this.w;
}
    public void j(float var1, int var2) {
        this.j = (float)((double)this.Y - this.w.z());
        this.Y = var1;
        if (this.j != this.Y - var1) {
            this.w = new SmoothStepAnimation(var2, this.Y - this.j, AnimationDirection.BACKWARDS);
}
}
    public float H() {
        this.j = (float)((double)this.Y - this.w.z());
        return this.j;
}
}