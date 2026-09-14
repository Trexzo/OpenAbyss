/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockAnvil
 *  net.minecraft.block.BlockBasePressurePlate
 *  net.minecraft.block.BlockBed
 *  net.minecraft.block.BlockBrewingStand
 *  net.minecraft.block.BlockBush
 *  net.minecraft.block.BlockButton
 *  net.minecraft.block.BlockCactus
 *  net.minecraft.block.BlockCarpet
 *  net.minecraft.block.BlockChest
 *  net.minecraft.block.BlockContainer
 *  net.minecraft.block.BlockDispenser
 *  net.minecraft.block.BlockDoor
 *  net.minecraft.block.BlockDropper
 *  net.minecraft.block.BlockEnchantmentTable
 *  net.minecraft.block.BlockEndPortal
 *  net.minecraft.block.BlockEndPortalFrame
 *  net.minecraft.block.BlockEnderChest
 *  net.minecraft.block.BlockFalling
 *  net.minecraft.block.BlockFence
 *  net.minecraft.block.BlockFenceGate
 *  net.minecraft.block.BlockFurnace
 *  net.minecraft.block.BlockHopper
 *  net.minecraft.block.BlockJukebox
 *  net.minecraft.block.BlockLadder
 *  net.minecraft.block.BlockLever
 *  net.minecraft.block.BlockPane
 *  net.minecraft.block.BlockPumpkin
 *  net.minecraft.block.BlockRailBase
 *  net.minecraft.block.BlockRedstoneDiode
 *  net.minecraft.block.BlockRedstoneWire
 *  net.minecraft.block.BlockSlab
 *  net.minecraft.block.BlockSlime
 *  net.minecraft.block.BlockSnow
 *  net.minecraft.block.BlockStairs
 *  net.minecraft.block.BlockTNT
 *  net.minecraft.block.BlockTorch
 *  net.minecraft.block.BlockTrapDoor
 *  net.minecraft.block.BlockTripWire
 *  net.minecraft.block.BlockTripWireHook
 *  net.minecraft.block.BlockVine
 *  net.minecraft.block.BlockWall
 *  net.minecraft.block.BlockWeb
 *  net.minecraft.block.BlockWorkbench
 *  net.minecraft.block.material.Material
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.Minecraft
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.item.EntityArmorStand
 *  net.minecraft.init.Blocks
 *  net.minecraft.inventory.ContainerChest
 *  net.minecraft.item.ItemStack
 *  net.minecraft.potion.Potion
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.MovingObjectPosition
 *  net.minecraft.util.MovingObjectPosition$MovingObjectType
 *  net.minecraft.util.Vec3
 *  net.minecraft.world.World
 */
package Abyss.util;

import Abyss.internal.synthetic.BlockUtilSwitchMapEnumFacing;
import Abyss.util.ClientUtil;
import Abyss.util.MathUtil;
import Abyss.util.MinecraftRef;
import Abyss.util.PlacementTarget;
import Abyss.util.RaytraceUtil;
import Abyss.util.RotationUtil;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockBasePressurePlate;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockBrewingStand;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockCarpet;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockDropper;
import net.minecraft.block.BlockEnchantmentTable;
import net.minecraft.block.BlockEndPortal;
import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockJukebox;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockRedstoneDiode;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockSlime;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockTNT;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.BlockTripWire;
import net.minecraft.block.BlockTripWireHook;
import net.minecraft.block.BlockVine;
import net.minecraft.block.BlockWall;
import net.minecraft.block.BlockWeb;
import net.minecraft.block.BlockWorkbench;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class BlockUtil {
    private static long private static Minecraft Q;
            
    private static EnumFacing B(BlockPos var0, BlockPos var1, boolean var2) {
        double var3 = 0.0;
        EnumFacing var5 = null;
        for (EnumFacing var9 : EnumFacing.field_82609_l) {
            AxisAlignedBB var12;
            AxisAlignedBB var11;
            BlockPos var10;
            if (!var2 && var9 == EnumFacing.DOWN || !BlockUtil.a$r1(var10 = var0.func_177972_a(var9)) || !var2 && var10.func_177956_o() != var1.func_177956_o() || (var11 = RaytraceUtil.J(var10)).func_72326_a(var12 = BlockUtil.Q.field_71439_g.func_174813_aQ())) continue;
            double var13 = var10.func_177957_d((double)var1.func_177958_n() + 0.5, (double)var1.func_177956_o() + 0.5, (double)var1.func_177952_p() + 0.5);
            if (var5 != null && !(var13 < var3) && (var13 != var3 || var9 != EnumFacing.UP)) continue;
            var3 = var13;
            var5 = var9;
}
        return var5;
}
    public static boolean p(Block var0) {
        return var0 instanceof BlockFurnace || var0 instanceof BlockFenceGate || var0 instanceof BlockChest || var0 instanceof BlockEnderChest || var0 instanceof BlockEnchantmentTable || var0 instanceof BlockBrewingStand || var0 instanceof BlockBed || var0 instanceof BlockDropper || var0 instanceof BlockDispenser || var0 instanceof BlockHopper || var0 instanceof BlockAnvil || var0 == Blocks.field_150462_ai;
}
    public static EnumFacing D(BlockPos var0) {
        return BlockUtil.c(var0, RaytraceUtil.f());
}
    public static boolean U(Block var0) {
        if (var0 instanceof BlockContainer) {
            return true;
}
        if (var0 instanceof BlockWorkbench) {
            return true;
}
        if (var0 instanceof BlockAnvil) {
            return true;
}
        if (var0 instanceof BlockBed) {
            return true;
}
        if (var0 instanceof BlockDoor && var0.func_149688_o() != Material.field_151573_f) {
            return true;
}
        if (var0 instanceof BlockTrapDoor) {
            return true;
}
        if (var0 instanceof BlockFenceGate) {
            return true;
}
        if (var0 instanceof BlockFence) {
            return true;
}
        if (var0 instanceof BlockButton) {
            return true;
}
        return var0 instanceof BlockLever ? true : var0 instanceof BlockJukebox;
}
    public static Vec3 s(BlockPos var0) {
        return new Vec3((double)var0.func_177958_n(), (double)var0.func_177956_o(), (double)var0.func_177952_p());
}
    public static BlockPos Z(Vec3 var0) {
        return new BlockPos(var0.field_72450_a, var0.field_72448_b, var0.field_72449_c);
}
    public static boolean o(boolean var0) {
        if (!(BlockUtil.Q.field_71439_g.field_71070_bA instanceof ContainerChest)) {
            return false;
}
        if (var0) {
            String var1 = ((ContainerChest)BlockUtil.Q.field_71439_g.field_71070_bA).func_85151_d().func_70005_c_();
            for (String var5 : c) {
                if (!var1.toLowerCase().contains(var5)) continue;
                return false;
}
}
        return true;
}
    public static boolean Y(byte var0, BlockPos var1, int var2) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        AxisAlignedBB var6 = new AxisAlignedBB((double)(var1.func_177958_n() - 1), (double)var1.func_177956_o() + 0.8, (double)(var1.func_177952_p() - 1), (double)(var1.func_177958_n() + 2), (double)var1.func_177956_o() + 1.5, (double)(var1.func_177952_p() + 2));
        List var7 = BlockUtil.Q.field_71441_e.func_72872_a(EntityArmorStand.class, var6);
        int var9 = var7.size();
        for (int var8 = 0; var8 < var9; ++var8) {
            EntityArmorStand var10 = (EntityArmorStand)var7.get(var8);
            String var11 = var10.func_145748_c_().func_150260_c();
            if (var11 == null || var11.isEmpty() || !(var11 = var11.toLowerCase()).contains("empty") && !var11.contains(":")) continue;
            return true;
}
        return false;
}
    public static boolean S(BlockPos var0) {
        return BlockUtil.U(BlockUtil.Q.field_71441_e.func_180495_p(var0).func_177230_c());
}
    public static BlockPos Z() {
        return new BlockPos(BlockUtil.Q.field_71439_g.field_70165_t, BlockUtil.Q.field_71439_g.field_70163_u, BlockUtil.Q.field_71439_g.field_70161_v);
}
    public static boolean i(Block var0) {
        if (var0 instanceof BlockStairs) {
            return false;
}
        if (var0 instanceof BlockSlab) {
            return false;
}
        if (var0 instanceof BlockEndPortalFrame) {
            return false;
}
        if (var0 instanceof BlockEndPortal) {
            return false;
}
        if (var0 instanceof BlockVine) {
            return false;
}
        if (var0 instanceof BlockPumpkin) {
            return false;
}
        if (var0 instanceof BlockCactus) {
            return false;
}
        if (var0 instanceof BlockBush) {
            return false;
}
        if (var0 instanceof BlockFalling) {
            return false;
}
        if (var0 instanceof BlockWeb) {
            return false;
}
        if (var0 instanceof BlockPane) {
            return false;
}
        if (var0 instanceof BlockCarpet) {
            return false;
}
        if (var0 instanceof BlockSnow) {
            return false;
}
        if (var0 instanceof BlockFence) {
            return false;
}
        if (var0 instanceof BlockFenceGate) {
            return false;
}
        if (var0 instanceof BlockWall) {
            return false;
}
        if (var0 instanceof BlockLadder) {
            return false;
}
        if (var0 instanceof BlockTorch) {
            return false;
}
        if (var0 instanceof BlockRedstoneWire) {
            return false;
}
        if (var0 instanceof BlockRedstoneDiode) {
            return false;
}
        if (var0 instanceof BlockBasePressurePlate) {
            return false;
}
        if (var0 instanceof BlockTripWire) {
            return false;
}
        if (var0 instanceof BlockTripWireHook) {
            return false;
}
        if (var0 instanceof BlockRailBase) {
            return false;
}
        if (var0 instanceof BlockSlime) {
            return false;
}
        return var0 instanceof BlockTNT ? false : var0 != null && var0.func_149730_j() && var0.func_149662_c();
}
    public static float i(ItemStack var0, Block var1, boolean var2, boolean var3) {
        int var5;
        float var4;
        float f = var4 = var0 == null ? 1.0f : var0.func_77973_b().func_150893_a(var0, var1);
        if (var4 > 1.0f && (var5 = EnchantmentHelper.func_77506_a((int)Enchantment.field_77349_p.field_77352_x, (ItemStack)var0)) > 0 && var0 != null) {
            var4 += (float)(var5 * var5 + 1);
}
        if (BlockUtil.Q.field_71439_g.func_70644_a(Potion.field_76422_e)) {
            var4 *= 1.0f + (float)(BlockUtil.Q.field_71439_g.func_70660_b(Potion.field_76422_e).func_76458_c() + 1) * 0.2f;
}
        if (!var2) {
            if (BlockUtil.Q.field_71439_g.func_70644_a(Potion.field_76419_f)) {
                float var6;
                switch (BlockUtil.Q.field_71439_g.func_70660_b(Potion.field_76419_f).func_76458_c()) {
                    case 0: {
                        var6 = 0.3f;
                        break;
}
                    case 1: {
                        var6 = 0.09f;
                        break;
}
                    case 2: {
                        var6 = 0.0027f;
                        break;
}
                    default: {
                        var6 = 8.1E-4f;
}
}
                var4 *= var6;
}
            if (BlockUtil.Q.field_71439_g.func_70055_a(Material.field_151586_h) && !EnchantmentHelper.func_77510_g((EntityLivingBase)BlockUtil.Q.field_71439_g)) {
                var4 /= 5.0f;
}
            if (!BlockUtil.Q.field_71439_g.field_70122_E && !var3) {
                var4 /= 5.0f;
}
}
        return var4;
}
    public static Vec3 f(BlockPos var0, EnumFacing var1) {
        Block var2 = BlockUtil.Q.field_71441_e.func_180495_p(var0).func_177230_c();
        Vec3 var3 = new Vec3((double)var0.func_177958_n() + Math.min(Math.max((double)MathUtil.Y(0.0, 1.0), var2.func_149704_x()), var2.func_149753_y()), (double)var0.func_177956_o() + Math.min(Math.max((double)MathUtil.Y(0.0, 1.0), var2.func_149665_z()), var2.func_149669_A()), (double)var0.func_177952_p() + Math.min(Math.max((double)MathUtil.Y(0.0, 1.0), var2.func_149706_B()), var2.func_149693_C()));
        switch (BlockUtilSwitchMapEnumFacing.b[var1.ordinal()]) {
            case 1: {
                return new Vec3(var3.field_72450_a, (double)var0.func_177956_o() + var2.func_149669_A(), var3.field_72449_c);
}
            case 2: {
                return new Vec3(var3.field_72450_a, var3.field_72448_b, (double)var0.func_177952_p() + var2.func_149706_B());
}
            case 3: {
                return new Vec3((double)var0.func_177958_n() + var2.func_149753_y(), var3.field_72448_b, var3.field_72449_c);
}
            case 4: {
                return new Vec3(var3.field_72450_a, var3.field_72448_b, (double)var0.func_177952_p() + var2.func_149693_C());
}
            case 5: {
                return new Vec3((double)var0.func_177958_n() + var2.func_149704_x(), var3.field_72448_b, var3.field_72449_c);
}
}
        return new Vec3(var3.field_72450_a, (double)var0.func_177956_o() + var2.func_149665_z(), var3.field_72449_c);
}
    public static boolean a$r1(BlockPos var0) {
        return BlockUtil.f(BlockUtil.Q.field_71441_e.func_180495_p(var0).func_177230_c());
}
    public static double g(BlockPos var0, BlockPos var1) {
        return MathUtil.Z((double)var0.func_177958_n() + 0.5, (double)var1.func_177958_n() + 0.5) + MathUtil.Z((double)var0.func_177956_o() + 0.5, (double)var1.func_177956_o() + 0.5) + MathUtil.Z((double)var0.func_177952_p() + 0.5, (double)var1.func_177952_p() + 0.5);
}
    public static boolean f(Block var0) {
        if (!var0.func_149688_o().func_76222_j()) {
            return false;
}
        return !(var0 instanceof BlockSnow) ? true : !(var0.func_149669_A() > 0.125);
}
    public static PlacementTarget x(double var0, BlockPos var2, boolean var5) {
        BlockPos var6 = new BlockPos(BlockUtil.Q.field_71439_g.field_70165_t, var0, BlockUtil.Q.field_71439_g.field_70161_v);
        if (!BlockUtil.a$r1(var6)) {
            return null;
}
        ArrayList<BlockPos> var7 = new ArrayList<BlockPos>();
        for (int var8 = -4; var8 <= 4; ++var8) {
            for (int var9 = -4; var9 <= 0; ++var9) {
                for (int var10 = -4; var10 <= 4; ++var10) {
                    EnumFacing[] var12;
                    BlockPos var11 = var6.func_177982_a(var8, var9, var10);
                    if (BlockUtil.a$r1(var11) || BlockUtil.S(var11)) continue;
                    for (EnumFacing var16 : var12 = EnumFacing.field_82609_l) {
                        BlockPos var17 = var11.func_177972_a(var16);
                        if (!BlockUtil.a$r1(var17)) continue;
                        var7.add(var11);
}
}
}
}
        if (var7.isEmpty() && !var5) {
            return null;
}
        boolean var22 = false;
        if (var7.isEmpty()) {
            for (int var23 = -4; var23 <= 4; ++var23) {
                for (int var25 = 0; var25 <= 6; ++var25) {
                    for (int var28 = -4; var28 <= 4; ++var28) {
                        EnumFacing[] var31;
                        BlockPos var30 = var6.func_177982_a(var23, var25, var28);
                        if (BlockUtil.a$r1(var30) || BlockUtil.S(var30)) continue;
                        for (EnumFacing var35 : var31 = EnumFacing.field_82609_l) {
                            BlockPos var18 = var30.func_177972_a(var35);
                            AxisAlignedBB var19 = RaytraceUtil.J(var18);
                            AxisAlignedBB var20 = BlockUtil.Q.field_71439_g.func_174813_aQ();
                            if (!BlockUtil.a$r1(var18) || var19.func_72326_a(var20)) continue;
                            var7.add(var30);
}
}
}
}
            if (var7.isEmpty()) {
                return null;
}
            var22 = true;
}
        if (!var22 && var2 != null) {
            PlacementTarget var24;
            EnumFacing var26 = BlockUtil.B(var2, var6, false);
            PlacementTarget placementTarget = var24 = var26 == null ? null : new PlacementTarget(var2, var26, false);
            if (Math.round(var6.func_177957_d((double)var2.func_177958_n() + 0.5, (double)var2.func_177956_o() + 0.5, (double)var2.func_177952_p() + 0.5)) < 1L && var24 != null) {
                return var24;
}
}
        var7.sort(Comparator.comparingDouble(var1 -> var1.func_177957_d((double)var6.func_177958_n() + 0.5, (double)var6.func_177956_o() + 0.5, (double)var6.func_177952_p() + 0.5)));
        BlockPos var27 = (BlockPos)var7.get(0);
        EnumFacing var29 = BlockUtil.B(var27, var6, var22);
        return var29 == null ? null : new PlacementTarget(var27, var29, var22);
}
    public static Block l(double var0, double var2, double var4) {
        return BlockUtil.a(new BlockPos(var0, var2, var4));
}
    public static boolean p(BlockPos var0, BlockPos var1) {
        return var0 == var1 || var0.func_177958_n() == var1.func_177958_n() && var0.func_177956_o() == var1.func_177956_o() && var0.func_177952_p() == var1.func_177952_p();
}
    public static IBlockState d(BlockPos var0) {
        return BlockUtil.Q.field_71441_e.func_180495_p(var0);
}
    public static MovingObjectPosition S(Vec3 var0, Vec3 var1, double var2) {
        Vec3 var4 = var0.func_72441_c(var1.field_72450_a * var2, var1.field_72448_b * var2, var1.field_72449_c * var2);
        MovingObjectPosition var5 = BlockUtil.Q.field_71441_e.func_147447_a(var0, var4, false, false, false);
        return var5 == null ? new MovingObjectPosition(MovingObjectPosition.MovingObjectType.MISS, var4, EnumFacing.UP, new BlockPos(var4)) : var5;
}
    public static float g(Block var0, ItemStack var1, boolean var2, boolean var3) {
        float var4 = var0.func_176195_g((World)BlockUtil.Q.field_71441_e, null);
        if (var4 < 0.0f) {
            return 0.0f;
}
        return !var0.func_149688_o().func_76229_l() && (var1 == null || !var1.func_150998_b(var0)) ? BlockUtil.i(var1, var0, var2, var3) / var4 / 100.0f : BlockUtil.i(var1, var0, var2, var3) / var4 / 30.0f;
}
    public static MovingObjectPosition F(float[] var0, double var1) {
        Vec3 var3 = BlockUtil.Q.field_71439_g.func_174824_e(1.0f);
        Vec3 var4 = RotationUtil.d(var0[1], var0[0]);
        return BlockUtil.S(var3, var4, var1);
}
    public static EnumFacing c(BlockPos var0, Vec3 var1) {
        EnumFacing[] var5;
        double var2 = Double.MAX_VALUE;
        EnumFacing var4 = EnumFacing.UP;
        for (EnumFacing var9 : var5 = EnumFacing.field_82609_l) {
            BlockPos var10 = var0.func_177972_a(var9);
            double var11 = var10.func_177957_d(var1.field_72450_a, var1.field_72448_b, var1.field_72449_c);
            if (!(var11 < var2)) continue;
            var2 = var11;
            var4 = var9;
}
        return var4;
}
    static {
        Q = MinecraftRef.c((byte)0, 0L);
        e = new HashMap(13);
        b = new String[]{"\u00c42\u0019\u00b8W\u0000\u0018\u00d9", "\u00b1\u00a6c\u00b5_T\u00c3*", "\u001dS\u00d3\u00a8\u0019&k&", "\"v;uL2\u00f9\u000e", "E@|\u00b1\u00b8Ai7", "\u00fd/\u00d9\u00b7X*\u001a\u00e1", "\u00cf\u008b\u0080\f\u00f0\u00d6N\u00e4", "g\u0007r|\u00bb\u00111\u00c5i\u00d3\u00e7\u00eb\u0083Dh\u00d9", "pn\u0004\\\u00ad\u0018\u0016\u00bb", "u@L@.\u0005sA*+\u0010S0\u0095\u0085\u009d", "\u00ac'\u00e7\u00af\u00e2\u0082B\u00e9", "_\u00c5r~\u0098\u00eay\u0099", "!\u0092}\u00a6\u0099R\u0019\u001a", "\u0089\u00d5\u001e1w\u00c1\u0085W", "\u001a2\u00b8\u0085T\u0013\u00b8\u00d2", "q\"D\u00b9T\u008a\u00a4GL%\u00e7G4v\u00bb\u00df", "w\u001a\u000bT,t\u00c4\u0098", "\u00d9\u00f5\u00e9\u000b\u0091\u0006\u00b2\u0018", "\u00ec\u00dbvxK\u00c6\u008a\u00ac", "\u00cf\u00df\u00eb:\u00a4\u00ec\u001d~\u00f3\u009dn\u0092\u001e\u00fe\u009ea", "D{\u00dd{\u00d0\u00d6\u00e3\u00d2", "\u001d\u00f8Y\u00f0fs\u00df\u0092", "V\u00c5\u009c\u00eai\u00d6\u00e0\u00de\u001ftrh\u00fe#\u008b\u00ab", "`?!\u00cb\u00c7\u00dc\u008cd", "O\u00b7~\u00b4\u0004\u001d\u00f7\u0012", "F\u00bd\u00a4\u00b5E.\f1", "\u00b9\u00be{\u00f2\u00e0VT\u00b5", "\u00c4pt76s\u0019\u008e", "W\u00c6\u008d\u00f1ksn_", "\u0006@\u0011<k&A\u0019", "\u00d6\u0098\u00d1\u00ab\u0089\u00daY[", "\u0088qc\u00c4\u0089`\u0089\u0014", "q\u00b7\u00d4:z\u0097\u00bb]", "\u00d1O\u00d1\u00db\u00c13\u0001\u00a6", "\b\u00b5ha\u0084\u00ee\u00d0\u0000", "o\u0080\u00f0+J\u00f7\u00baE\u0018`\u00eep\u00fb\u00bbwk", "\u007f\u00de\f\u0003\t\u0016F\u00c4", "\u0087\u00c6@\u0082\u0003}\u00b4\u0089", "\u00fc\u00c2\u001fI]\u0013\u0083\u0019", "\\\u00f1\u0095\u00fa\u00c6\u00af34", "S\u00c2r\u001b|%\u00edH", "\u00cc\u00cf\u00ec\u0013\u000e\u00ca9\u00b6", "\u00bb#\u00cc\u00f8\t\u00e9+N", "i\u00d5\u00d6|\u00a8\u0092\u00b5\u00c5", "\u00e9>\u00f7c\u00cf\u00bb\u00ce\u00d7\u0013\u00ab\u0096\u00a3\u00ac\u00d8\u001a\u00a5", "\u00de\u00b4\u00b3\u001a\u0087\u0097\u0000\u00c1", "\u0003\u0095\u00c6?'\u0088\u0088\u0012\u009c\u00fb\u008aI\"\u00bb*\u000b", "b\u008e\u00d5f\u0002\u008d\u00a7\u0085", "|\u00bd\u001ecg,\b ", "\r=F\u00df\u00de\u00fd\u0090\u00af", "\u00fb7\u00cc\u0097\u00e6;#D", "\u00eb\u00d5J\u00a3\u001bBD\u00cb", "z\b<\u00a0\u00c06\u00c1\u00f6"};
        d = new String[53];
        h = new HashMap(13);
        f = new long[]{-5671541356283299340L, 3336589932144786014L, -8281948604808253526L, -5060774444664922834L, 7841368919069733423L, 5340281294950306958L, 4290905163612322334L, -2986648663499677542L, -1347321381564391885L, -3763399881681324595L, -1536057098851550396L, 7444637763195574075L, 2064505903243086526L, 1593520964357543818L, -8105899777621118194L, -339913808545842599L, -4099605816807005818L, 566504690872309709L, 7670613460910879957L, 7165309605720599660L, 8982390553565958740L, 5826110918349952270L, 2768814401464318111L, -3390074631222057908L, 4707947972981865289L, 1464744269818169426L, -1128503674984837999L, 36758256892976834L, -7616167634185220824L, -6464644118396601494L, -3945902389478518219L, 3145555551174003741L, -6076230780240992093L, -9148959203328170500L, -4158616357511191016L, 8054236822352301133L, 5611169608431648541L, -3810029233648607858L, 7100067255059854739L, -7011230850504339168L, 1962460623117554997L, 8915862846947233467L, -3378956555984624870L, -3157190938201907241L, -3708095488035699444L, 3138278856185626371L, -8265783177457158026L, -2278938156293539407L, -7347209435530725453L, 4632125009215777798L};
        c = new String[]{"mode", "delivery", "menu", "selector", "game", "gui", "server", "inventory", "play", "teleporter", "shop", "melee", "armor", "block", "castle", "mini", "warp", "teleport", "user", "team", "tool", "sure", "trade", "cancel", "accept", "soul", "book", "recipe", "profile", "tele", "port", "map", "kit", "select", "lobby", "vault", "lock", "anticheat", "travel", "settings", "user", "preference", "compass", "cake", "wars", "buy", "upgrade", "ranged", "potions", "utility", "duel", "classic"};
}
}