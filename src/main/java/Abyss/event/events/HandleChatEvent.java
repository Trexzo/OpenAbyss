/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.IChatComponent
 */
package Abyss.event.events;

import Abyss.event.Event;
import net.minecraft.util.IChatComponent;

public class HandleChatEvent
extends Event {
    public final IChatComponent A;

    public HandleChatEvent(IChatComponent var1) {
        this.A = var1;
}
}