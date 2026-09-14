/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.BlockPos
 */
package Abyss.event.events;

import Abyss.event.Event;
import net.minecraft.util.BlockPos;

public class ClickBlockReturnEvent
extends Event {
    public final BlockPos b;
    private static final long public ClickBlockReturnEvent(BlockPos var3) {
        this.b = var3;
}
}