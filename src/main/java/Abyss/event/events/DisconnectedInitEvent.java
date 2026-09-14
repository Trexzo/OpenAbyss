/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.util.IChatComponent
 */
package Abyss.event.events;

import Abyss.event.Event;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.IChatComponent;

public class DisconnectedInitEvent
extends Event {
    private static final long public final IChatComponent X;
    public final GuiScreen O;

    public DisconnectedInitEvent(GuiScreen var3, IChatComponent var4) {
        this.O = var3;
        this.X = var4;
}
}