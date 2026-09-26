/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.BlockPos
 */
package Abyss.event.events;

import Abyss.event.Event;
import net.minecraft.util.BlockPos;

public class TryHarvestBlockHeadEvent
extends Event {
    public final BlockPos n;
    public TryHarvestBlockHeadEvent(BlockPos var1) {
        this.n = var1;
}
}