/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.Vec3
 */
package Abyss.event.events;

import Abyss.event.Event;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

public class PlayerRightClickEvent
extends Event {
    private final Vec3 h;
    private final ItemStack R;
    private final EnumFacing i;
    private final BlockPos M;
    private static final long private final WorldClient y;

    public PlayerRightClickEvent(WorldClient var3, ItemStack var4, BlockPos var5, EnumFacing var6, Vec3 var7) {
        this.y = var3;
        this.R = var4;
        this.M = var5;
        this.i = var6;
        this.h = var7;
}
    public BlockPos a$r2() {
        return this.M;
}
    public Vec3 Z() {
        return this.h;
}
    public ItemStack Q() {
        return this.R;
}
    public WorldClient t() {
        return this.y;
}
    public EnumFacing l() {
        return this.i;
}
}