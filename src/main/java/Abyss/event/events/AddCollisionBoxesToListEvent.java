/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.util.BlockPos
 */
package Abyss.event.events;

import Abyss.event.Event;
import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;

public class AddCollisionBoxesToListEvent
extends Event {
    private static final long public final BlockPos E;
    public final Block B;

    public AddCollisionBoxesToListEvent(Block var1, BlockPos var2) {
        this.B = var1;
        this.E = var2;
}
}