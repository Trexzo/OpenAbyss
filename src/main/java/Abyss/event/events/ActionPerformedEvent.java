/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 */
package Abyss.event.events;

import Abyss.event.Event;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class ActionPerformedEvent
extends Event {
    public final GuiScreen O;
    public final GuiButton Q;
    public ActionPerformedEvent(GuiScreen var1, GuiButton var4) {
        this.O = var1;
        this.Q = var4;
}
}