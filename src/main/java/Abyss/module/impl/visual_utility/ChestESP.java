/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockChest
 *  net.minecraft.block.properties.IProperty
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.init.Blocks
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.tileentity.TileEntityChest
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.EnumFacing
 */
package Abyss.module.impl.visual_utility;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.ChestESPBinder;
import Abyss.event.events.EntityJoinWorldEvent;
import Abyss.event.events.PlayerRightClickEvent;
import Abyss.event.events.PostTickEvent;
import Abyss.event.events.Render3DEvent;
import Abyss.internal.accessor.RenderManagerAccessor;
import Abyss.internal.synthetic.ChestESPSwitchMapEnumFacing;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.impl.configuration.Theme;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.ColorSetting;
import Abyss.setting.settings.ModeSetting;
import Abyss.setting.settings.PercentageSetting;
import Abyss.util.BlockUtil;
import Abyss.util.render.RenderUtil;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.properties.IProperty;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public class ChestESP
extends Module
implements EventSubscriber {
    private static long a = 17077916200986L;
    public static ModeSetting color;
    public static BooleanSetting showTargetShade;
        public static BooleanSetting ignoreOpened;
    public static PercentageSetting opacity;
    public static BooleanSetting showTargetOutline;
    private final List<AxisAlignedBB> I;
    private final Set<BlockPos> v;
    public static ColorSetting customColor;

    private void B(long var1) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        this.I.clear();
        List var6 = ChestESP.f.theWorld.loadedTileEntityList;
        boolean var7 = ignoreOpened.c();
        int var9 = var6.size();
        block6: for (int var8 = 0; var8 < var9; ++var8) {
            TileEntity var10 = (TileEntity)var6.get(var8);
            if (!(var10 instanceof TileEntityChest)) continue;
            BlockPos var11 = var10.getPos();
            if (!this.v.contains(var11) && (((TileEntityChest)var10).numPlayersUsing > 0 || BlockUtil.Y((byte)0, var11, 8170486))) {
                this.v.add(var11);
}
            if (this.v.contains(var11) && var7) continue;
            Block var12 = ChestESP.f.theWorld.getBlockState(var11).getBlock();
            double var13 = 0.0625;
            double var15 = 0.0625;
            double var17 = 0.9375;
            double var19 = 0.9375;
            if (var12 instanceof BlockChest) {
                EnumFacing var21 = (EnumFacing)ChestESP.f.theWorld.getBlockState(var11).getValue((IProperty)BlockChest.FACING);
                switch (ChestESPSwitchMapEnumFacing.V[var21.ordinal()]) {
                    case 1: {
                        if (ChestESP.f.theWorld.getBlockState(var11.east()).getBlock() == var12) continue block6;
                        if (ChestESP.f.theWorld.getBlockState(var11.west()).getBlock() != var12) break;
                        var13 -= 1.0;
                        break;
}
                    case 2: {
                        if (ChestESP.f.theWorld.getBlockState(var11.west()).getBlock() == var12) continue block6;
                        if (ChestESP.f.theWorld.getBlockState(var11.east()).getBlock() != var12) break;
                        var17 += 1.0;
                        break;
}
                    case 3: {
                        if (ChestESP.f.theWorld.getBlockState(var11.north()).getBlock() == var12) continue block6;
                        if (ChestESP.f.theWorld.getBlockState(var11.south()).getBlock() != var12) break;
                        var19 += 1.0;
                        break;
}
                    case 4: {
                        if (ChestESP.f.theWorld.getBlockState(var11.south()).getBlock() != var12) {
                            if (ChestESP.f.theWorld.getBlockState(var11.north()).getBlock() != var12) break;
                            var15 -= 1.0;
                            break;
}
}
                    default: {
                        continue block6;
}
}
}
            this.I.add(new AxisAlignedBB((double)var11.getX() + var13, (double)var11.getY(), (double)var11.getZ() + var15, (double)var11.getX() + var17, (double)var11.getY() + 0.875, (double)var11.getZ() + var19));
}
}
    @Override
    public final void x(long var1, EventBus var3) {
        ChestESPBinder.N(var3, this);
}
    public void onRender3D(long var1, Render3DEvent var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        int var19;
        switch (color.Y()) {
            case "THEME": {
                var19 = Theme.S(0.0, 35338930340239L);
                break;
}
            case "THEME_CUSTOM": {
                var19 = Theme.X(65301174328177L, 0.0);
                break;
}
            default: {
                var19 = customColor.k(96531491288662L);
}
}
        double var30 = RenderManagerAccessor.k(0L, f.getRenderManager());
        double var22 = RenderManagerAccessor.y(13236, f.getRenderManager());
        double var24 = RenderManagerAccessor.W(0L, f.getRenderManager());
        int var26 = (int)(2.55 * (double)opacity.k());
        for (int var27 = 0; var27 < this.I.size(); ++var27) {
            AxisAlignedBB var28 = this.I.get(var27).offset(-var30, -var22, -var24);
            RenderUtil.W(var28, 48544574689857L, var19, var26, showTargetOutline.c(), showTargetShade.c());
}
}
    public void onEntityJoinWorld(EntityJoinWorldEvent var1) {
        if (var1.H instanceof EntityPlayerSP) {
            this.v.clear();
            this.I.clear();
}
}
    public void onPostTick(PostTickEvent var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        this.B(129551973060899L);
}
    public ChestESP(long var1) {
        super(a ^ var1 ^ 0x597DAF7776CAL);
        this.declare("ChestESP", Category.Visual_utility, "ESP for chests", new Setting[0]);
        var1 = a ^ var1;
        this.v = new HashSet<BlockPos>();
        this.I = new ArrayList<AxisAlignedBB>();
}
    @Override
    public void A(long var1) {
        this.v.clear();
        this.I.clear();
}
    @Override
    public String g(long var1) {
        return opacity.k() + "%";
}
    public void onPlayerRightClick(PlayerRightClickEvent var1) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        BlockPos var6 = var1.a$r2();
        if (ChestESP.f.theWorld.getBlockState(var6).getBlock() == Blocks.chest) {
            this.v.add(var6);
            this.B(129551973060899L);
}
}
    static {
        opacity = new PercentageSetting("Opacity", 25);
        ignoreOpened = new BooleanSetting("Ignore-opened", false);
        showTargetOutline = new BooleanSetting("Show-target-outline", true);
        showTargetShade = new BooleanSetting("Show-target-shade", false);
        color = new ModeSetting("Color", "THEME", "THEME_CUSTOM", "CUSTOM");
        customColor = new ColorSetting("Custom-color", "FFFFFF");
}
}