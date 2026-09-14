/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemEgg
 *  net.minecraft.item.ItemSnowball
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.BlockPos$MutableBlockPos
 *  net.minecraft.util.Vec3i
 */
package Abyss.module.impl.world;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.FastPlaceBinder;
import Abyss.event.events.PostRightClickEvent;
import Abyss.event.events.PreUpdateEvent;
import Abyss.internal.accessor.MinecraftAccessor;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.setting.Setting;
import Abyss.setting.settings.NumberSetting;
import Abyss.util.TimerUtil;
import java.io.UnsupportedEncodingException;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemSnowball;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3i;

public class FastPlace
extends Module
implements EventSubscriber {
    private static Object[] d;
    private static String[] e;
    public static NumberSetting projectilesDelay;
    private final TimerUtil H;
    private static long c;
    public static NumberSetting blockDelay;
    private static String b;
    private BlockPos U;
    public static NumberSetting disableWhenBedInRange;
        private final BlockPos.MutableBlockPos scanPos = new BlockPos.MutableBlockPos();

    public FastPlace(long var1) {
        super(a ^ var1 ^ 0x1CE530679FAL);
        this.declare("FastPlace", Category.World, "Change the block placing delay when holding RMB", new Setting[0]);
        var1 = a ^ var1;
        this.H = new TimerUtil();
        this.U = null;
}
    public void onPreUpdate(PreUpdateEvent var1, long var2) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (!(disableWhenBedInRange.L() <= 0.0f) && this.H.L(c, true)) {
            BlockPos found;
            block22: {
                int range = (int)disableWhenBedInRange.L();
                found = null;
                try {
                    if (FastPlace.f.field_71441_e == null || FastPlace.f.field_71439_g == null) break block22;
                    int px = (int)FastPlace.f.field_71439_g.field_70165_t;
                    int py = (int)FastPlace.f.field_71439_g.field_70163_u;
                    int pz = (int)FastPlace.f.field_71439_g.field_70161_v;
                    for (int dy = range; dy >= -range; --dy) {
                        for (int dx = -range; dx <= range; ++dx) {
                            for (int dz = -range; dz <= range; ++dz) {
                                this.scanPos.func_181079_c(px + dx, py + dy, pz + dz);
                                if (FastPlace.f.field_71441_e.func_180495_p((BlockPos)this.scanPos).func_177230_c() != Blocks.field_150324_C) continue;
                                found = new BlockPos((Vec3i)this.scanPos);
                                break block22;
}
}
}
}
                catch (Throwable throwable) {
                    // empty catch block
}
}
            this.U = found;
}
        try {
            if (FastPlace.f.field_71415_G) {
                if (FastPlace.f.field_71439_g.func_70694_bm() != null && FastPlace.f.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemBlock) {
                    if (this.U != null) {
                        return;
}
                    int var13 = (int)blockDelay.L();
                    if (var13 == 0) {
                        MinecraftAccessor.j(0L, f, 0);
                    } else {
                        if (var13 == 4) {
                            return;
}
                        int var14 = MinecraftAccessor.C(f);
                        if (var14 == 4) {
                            MinecraftAccessor.j(0L, f, var13);
}
}
                } else if (FastPlace.f.field_71439_g.func_70694_bm() != null && (FastPlace.f.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemSnowball || FastPlace.f.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemEgg)) {
                    if (this.U != null) {
                        return;
}
                    int var12 = (int)projectilesDelay.L();
                    if (var12 == 0) {
                        MinecraftAccessor.j(0L, f, 0);
                    } else {
                        if (var12 == 4) {
                            return;
}
                        int var9 = MinecraftAccessor.C(f);
                        if (var9 == 4) {
                            MinecraftAccessor.j(0L, f, var12);
}
}
}
}
}
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            // empty catch block
}
}
    @Override
    public String g(long var1) {
        return blockDelay.L() != projectilesDelay.L() ? (int)blockDelay.L() + b + (int)projectilesDelay.L() : String.valueOf((int)blockDelay.L());
}
    @Override
    public final void x(long var1, EventBus var3) {
        FastPlaceBinder.e(var3, this);
}
    public void onPostRightClick(PostRightClickEvent var1, long var2) {
        try {
            if (FastPlace.f.field_71439_g.func_70694_bm() != null && FastPlace.f.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemBlock) {
                if (this.U != null) {
                    return;
}
                int var9 = (int)blockDelay.L();
                if (var9 == 0) {
                    MinecraftAccessor.j(0L, f, 0);
}
            } else if (FastPlace.f.field_71439_g.func_70694_bm() != null && (FastPlace.f.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemSnowball || FastPlace.f.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemEgg)) {
                if (this.U != null) {
                    return;
}
                int var6 = (int)projectilesDelay.L();
                if (var6 == 0) {
                    MinecraftAccessor.j(0L, f, 0);
}
}
}
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            // empty catch block
}
}
    static {
        d = new Object[7];
        e = new String[7];
        b = ", ";
        c = 500L;
        projectilesDelay = new NumberSetting("Projectiles-Delay", 2.0f, 0.0f, 4.0f, 1.0f);
        blockDelay = new NumberSetting("Block-Delay", 1.0f, 0.0f, 4.0f, 1.0f);
        disableWhenBedInRange = new NumberSetting("Disable-when-bed-in-range", -1.0f, -1.0f, 20.0f, 1.0f);
}
}