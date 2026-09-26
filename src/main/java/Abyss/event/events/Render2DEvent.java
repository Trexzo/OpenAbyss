/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.ScaledResolution
 */
package Abyss.event.events;

import Abyss.event.Event;
import net.minecraft.client.gui.ScaledResolution;

public class Render2DEvent
extends Event {
    public float r;
    public final ScaledResolution C;

    public Render2DEvent(int var1, short var2, float var3, short var4, ScaledResolution var5) {
        this.r = var3;
        this.C = var5;
}
}