/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.ItemTool
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.MovingObjectPosition
 *  net.minecraft.util.MovingObjectPosition$MovingObjectType
 */
package Abyss.util;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.AutoToolServiceBinder;
import Abyss.event.events.PreTickEvent;
import Abyss.util.MinecraftRef;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;

public class AutoToolService
implements EventSubscriber {
    private boolean U = false;
    public static AutoToolService K;
        private static Minecraft f;

    private AutoToolService() {
}
    public boolean f() {
        this.U = !this.U;
        return this.U;
}
    public boolean K() {
        return this.U;
}
    @Override
    public final void x(long var1, EventBus var3) {
        AutoToolServiceBinder.z(var3, this);
}
    public void I(long var1) {
        this.U = true;
}
    public void p(int var1, char var2, char var3) {
        this.U = false;
}
    public int Q(long var1, EntityPlayerSP var3, Block var4) {
        float var5 = 1.0f;
        int var6 = -1;
        for (int var7 = 0; var7 < 9; ++var7) {
            ItemStack var8 = var3.inventory.getStackInSlot(var7);
            if (var8 == null) continue;
            float var9 = var8.getStrVsBlock(var4);
            if (var8.getItem() instanceof ItemTool) {
                if (!(var9 > var5)) continue;
                var5 = var9;
                var6 = var7;
                continue;
}
            if (!(var9 > var5)) continue;
            var6 = var7;
}
        return var6;
}
    public void onPreTick(long var1, PreTickEvent var3) {
        BlockPos var7;
        Block var8;
        EntityPlayerSP var9;
        int var10;
        MovingObjectPosition var6;
        if (this.U && (var6 = AutoToolService.f.objectMouseOver) != null && var6.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && (var10 = this.Q(0L, var9 = AutoToolService.f.thePlayer, var8 = AutoToolService.f.theWorld.getBlockState(var7 = var6.getBlockPos()).getBlock())) != -1 && var10 != var9.inventory.currentItem) {
            var9.inventory.currentItem = var10;
}
}
    static {
        K = new AutoToolService();
        f = MinecraftRef.c((byte)0, 0L);
}
}