/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.ScaledResolution
 */
package Abyss.event.events;

import Abyss.event.Event;
import net.minecraft.client.gui.ScaledResolution;

public class Render3DEvent
extends Event {
    public final ScaledResolution O;
    public float j;
    private static final long public Render3DEvent(float var1, ScaledResolution var4) {
        this.j = var1;
        this.O = var4;
}
}