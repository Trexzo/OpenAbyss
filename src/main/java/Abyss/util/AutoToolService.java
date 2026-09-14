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
            ItemStack var8 = var3.field_71071_by.func_70301_a(var7);
            if (var8 == null) continue;
            float var9 = var8.func_150997_a(var4);
            if (var8.func_77973_b() instanceof ItemTool) {
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
        if (this.U && (var6 = AutoToolService.f.field_71476_x) != null && var6.field_72313_a == MovingObjectPosition.MovingObjectType.BLOCK && (var10 = this.Q(0L, var9 = AutoToolService.f.field_71439_g, var8 = AutoToolService.f.field_71441_e.func_180495_p(var7 = var6.func_178782_a()).func_177230_c())) != -1 && var10 != var9.field_71071_by.field_70461_c) {
            var9.field_71071_by.field_70461_c = var10;
}
}
    static {
        K = new AutoToolService();
        f = MinecraftRef.c((byte)0, 0L);
}
}