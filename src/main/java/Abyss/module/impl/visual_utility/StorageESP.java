/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.tileentity.TileEntityBrewingStand
 *  net.minecraft.tileentity.TileEntityChest
 *  net.minecraft.tileentity.TileEntityDispenser
 *  net.minecraft.tileentity.TileEntityEnderChest
 *  net.minecraft.tileentity.TileEntityFurnace
 *  net.minecraft.tileentity.TileEntityHopper
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.BlockPos
 *  net.minecraft.world.World
 *  org.lwjgl.opengl.GL11
 */
package Abyss.module.impl.visual_utility;

import Abyss.event.EventBus;
import Abyss.event.EventInvoker;
import Abyss.event.EventSubscriber;
import Abyss.event.events.Render3DEvent;
import Abyss.internal.accessor.RenderManagerAccessor;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.impl.configuration.Theme;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.ColorSetting;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBrewingStand;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public final class StorageESP
extends Module
implements EventSubscriber {
    public static final BooleanSetting chests = new BooleanSetting("Chests", true);
    public static final BooleanSetting furnaces = new BooleanSetting("Furnaces", true);
    public static final BooleanSetting dispensers = new BooleanSetting("Dispensers", true);
    public static final BooleanSetting hoppers = new BooleanSetting("Hoppers", true);
    public static final BooleanSetting enderChests = new BooleanSetting("Ender-chests", true);
    public static final BooleanSetting outline = new BooleanSetting("Outline", true);
    public static final ColorSetting furnaceColor = new ColorSetting("Furnace-color", "FF5555");
    public static final ColorSetting dispenserColor = new ColorSetting("Dispenser-color", "55FFFF");
    public static final ColorSetting hopperColor = new ColorSetting("Hopper-color", "8A55FF");

    public StorageESP(long ignored) {
        super(ignored);
        this.declare("StorageESP", Category.Visual_utility, "ESP for storage tile entities", chests, furnaces, dispensers, hoppers, enderChests, outline, furnaceColor, dispenserColor, hopperColor);
}
    @Override
    public void x(long ignored, EventBus bus) {
        bus.R(this, Render3DEvent.class, 3, new EventInvoker(){

            @Override
            public void c(long seed, Object event) {
                StorageESP.this.onRender3D((Render3DEvent)event);
}
        });
}
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void onRender3D(Render3DEvent event) {
        if (StorageESP.f.field_71441_e == null) {
            return;
}
        GL11.glPushAttrib((int)1048575);
        GL11.glPushMatrix();
        try {
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2929);
            GL11.glDepthMask((boolean)false);
            if (outline.c()) {
                GL11.glEnable((int)2848);
                GL11.glLineWidth((float)1.0f);
}
            RenderManager manager = f.func_175598_ae();
            GL11.glTranslated((double)(-RenderManagerAccessor.k(0L, manager)), (double)(-StorageESP.renderY(manager)), (double)(-RenderManagerAccessor.W(0L, manager)));
            for (TileEntity tile : StorageESP.f.field_71441_e.field_147482_g) {
                int color = this.colorFor(tile);
                if (color == 0) continue;
                BlockPos pos = tile.func_174877_v();
                AxisAlignedBB box = tile.func_145838_q().func_180640_a((World)StorageESP.f.field_71441_e, pos, tile.func_145838_q().func_176203_a(tile.func_145832_p()));
                if (box == null) {
                    box = new AxisAlignedBB(pos, pos.func_177982_a(1, 1, 1));
}
                StorageESP.color(color);
                StorageESP.drawBox(box, outline.c());
}
}
        finally {
            GL11.glPopMatrix();
            GL11.glPopAttrib();
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
}
}
    private static double renderY(RenderManager manager) {
        try {
            return RenderManagerAccessor.y(0, manager);
}
        catch (Exception ignored) {
            return 0.0;
}
}
    private static void drawBox(AxisAlignedBB b, boolean lines) {
        if (lines) {
            GL11.glBegin((int)1);
            StorageESP.edge(b.field_72340_a, b.field_72338_b, b.field_72339_c, b.field_72336_d, b.field_72338_b, b.field_72339_c);
            StorageESP.edge(b.field_72336_d, b.field_72338_b, b.field_72339_c, b.field_72336_d, b.field_72338_b, b.field_72334_f);
            StorageESP.edge(b.field_72336_d, b.field_72338_b, b.field_72334_f, b.field_72340_a, b.field_72338_b, b.field_72334_f);
            StorageESP.edge(b.field_72340_a, b.field_72338_b, b.field_72334_f, b.field_72340_a, b.field_72338_b, b.field_72339_c);
            StorageESP.edge(b.field_72340_a, b.field_72337_e, b.field_72339_c, b.field_72336_d, b.field_72337_e, b.field_72339_c);
            StorageESP.edge(b.field_72336_d, b.field_72337_e, b.field_72339_c, b.field_72336_d, b.field_72337_e, b.field_72334_f);
            StorageESP.edge(b.field_72336_d, b.field_72337_e, b.field_72334_f, b.field_72340_a, b.field_72337_e, b.field_72334_f);
            StorageESP.edge(b.field_72340_a, b.field_72337_e, b.field_72334_f, b.field_72340_a, b.field_72337_e, b.field_72339_c);
            StorageESP.edge(b.field_72340_a, b.field_72338_b, b.field_72339_c, b.field_72340_a, b.field_72337_e, b.field_72339_c);
            StorageESP.edge(b.field_72336_d, b.field_72338_b, b.field_72339_c, b.field_72336_d, b.field_72337_e, b.field_72339_c);
            StorageESP.edge(b.field_72336_d, b.field_72338_b, b.field_72334_f, b.field_72336_d, b.field_72337_e, b.field_72334_f);
            StorageESP.edge(b.field_72340_a, b.field_72338_b, b.field_72334_f, b.field_72340_a, b.field_72337_e, b.field_72334_f);
            GL11.glEnd();
}
}
    private static void edge(double x, double y, double z, double x2, double y2, double z2) {
        GL11.glVertex3d((double)x, (double)y, (double)z);
        GL11.glVertex3d((double)x2, (double)y2, (double)z2);
}
    private int colorFor(TileEntity t2) {
        if (t2 instanceof TileEntityChest || t2 instanceof TileEntityBrewingStand) {
            return chests.c() ? Theme.S(0.0, 35338930340239L) : 0;
}
        if (t2 instanceof TileEntityEnderChest) {
            return enderChests.c() ? Theme.S(0.35, 35338930340239L) : 0;
}
        if (t2 instanceof TileEntityFurnace) {
            return furnaces.c() ? furnaceColor.k(0L) : 0;
}
        if (t2 instanceof TileEntityDispenser) {
            return dispensers.c() ? dispenserColor.k(0L) : 0;
}
        if (t2 instanceof TileEntityHopper) {
            return hoppers.c() ? hopperColor.k(0L) : 0;
}
        return 0;
}
    private static void color(int c) {
        GL11.glColor4f((float)((float)(c >> 16 & 0xFF) / 255.0f), (float)((float)(c >> 8 & 0xFF) / 255.0f), (float)((float)(c & 0xFF) / 255.0f), (float)((float)(c >>> 24) / 255.0f));
}
}