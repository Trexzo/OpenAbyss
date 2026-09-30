/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 */
package Abyss.event.events;

import Abyss.event.Event;
import net.minecraft.client.gui.GuiScreen;

public class GuiMouseEvent
extends Event {
    public final GuiScreen j;
    public final boolean y;
    public final int A;
    public final int I;

    public GuiMouseEvent(GuiScreen var1, boolean var2, int var5, int var6) {
        this.j = var1;
        this.y = var2;
        this.I = var5;
        this.A = var6;
}
}