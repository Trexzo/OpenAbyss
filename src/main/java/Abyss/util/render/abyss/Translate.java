/*
 * Decompiled with CFR 0.152.
 */
package Abyss.util.render.abyss;

import Abyss.util.render.abyss.RenderingUtils;

public final class Translate {
    private double x;
    private double y;

    public Translate(double x, double y) {
        this.x = x;
        this.y = y;
}
    public void animate(double newX, double newY) {
        this.x = RenderingUtils.progressiveAnimation(this.x, newX, 1.0);
        this.y = RenderingUtils.progressiveAnimation(this.y, newY, 0.5);
}
    public double getX() {
        return this.x;
}
    public void setX(float x) {
        this.x = x;
}
    public double getY() {
        return this.y;
}
    public void setY(float y) {
        this.y = y;
}
}