/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 */
package Abyss.event.events;

import Abyss.event.Event;
import java.util.List;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class InitGuiEvent
extends Event {
    public final GuiScreen A;
    public final List<GuiButton> B;

    public InitGuiEvent(GuiScreen var3, List var4) {
        this.A = var3;
        this.B = var4;
}
}