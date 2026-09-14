/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.ScaledResolution
 */
package Abyss.event.events;

import Abyss.event.Event;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class PostDrawScreenEvent
extends Event {
    private static final long public final ScaledResolution s;
    public final GuiScreen C;

    public PostDrawScreenEvent(GuiScreen var1, ScaledResolution var4) {
        this.C = var1;
        this.s = var4;
}
}