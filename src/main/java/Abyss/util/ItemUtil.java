/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.entity.ai.attributes.AttributeModifier
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemArmor
 *  net.minecraft.item.ItemAxe
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemBow
 *  net.minecraft.item.ItemBucket
 *  net.minecraft.item.ItemEgg
 *  net.minecraft.item.ItemEnderPearl
 *  net.minecraft.item.ItemFireball
 *  net.minecraft.item.ItemFishingRod
 *  net.minecraft.item.ItemFood
 *  net.minecraft.item.ItemPickaxe
 *  net.minecraft.item.ItemPotion
 *  net.minecraft.item.ItemShears
 *  net.minecraft.item.ItemSnowball
 *  net.minecraft.item.ItemSpade
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.ItemSword
 *  net.minecraft.item.ItemTool
 *  net.minecraft.potion.Potion
 *  net.minecraft.potion.PotionEffect
 *  net.minecraft.util.DamageSource
 *  org.lwjgl.input.Mouse
 */
package Abyss.util;

import Abyss.internal.accessor.BlockAccessor;
import Abyss.internal.accessor.GuiScreenAccessor;
import Abyss.internal.accessor.MethodAccessors;
import Abyss.internal.accessor.PlayerControllerAccessor;
import Abyss.util.BlockUtil;
import Abyss.util.MinecraftRef;
import Abyss.util.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemFireball;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import org.lwjgl.input.Mouse;

public class ItemUtil {
    private static long private static String[] i;
        private static Minecraft z;
    private static long[] e;
    private static Object[] h;
    

    public static boolean d() {
        return ItemUtil.z.field_71439_g != null && ItemUtil.z.field_71439_g.func_70694_bm() != null && ItemUtil.z.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemSword;
}
    public static boolean X(Item var0, IInventory var1) {
        for (int var2 = 0; var2 < var1.func_70302_i_(); ++var2) {
            ItemStack var3 = var1.func_70301_a(var2);
            if (var3 == null || var0 == null || var0 != var3.func_77973_b()) continue;
            return true;
}
        return false;
}
    public static int K(int var0) {
        if (var0 >= 36) {
            return 8 - (var0 - 36);
}
        return var0 < 9 ? var0 + 36 : var0;
}
    public static float Y(long var0, ItemStack var2) {
        if (var2 == null) {
            return 0.0f;
}
        if ((double)var2.func_77952_i() / (double)var2.func_77958_k() >= 0.7) {
            return 0.0f;
}
        Item var3 = var2.func_77973_b();
        int var4 = EnchantmentHelper.func_77506_a((int)Enchantment.field_77349_p.field_77352_x, (ItemStack)var2);
        switch (var4) {
            case 1: {
                break;
}
            case 2: {
                break;
}
            case 3: {
                break;
}
            case 4: {
                break;
}
            case 5: {
                break;
}
            default: {
                var4 = 0;
}
}
        if (var3 instanceof ItemPickaxe) {
            ItemPickaxe var14 = (ItemPickaxe)var3;
            return var14.func_150913_i().func_77998_b() + (float)var4;
}
        if (var3 instanceof ItemSpade) {
            ItemSpade var13 = (ItemSpade)var3;
            return var13.func_150913_i().func_77998_b() + (float)var4;
}
        if (var3 instanceof ItemAxe) {
            ItemAxe var12 = (ItemAxe)var3;
            return var12.func_150913_i().func_77998_b() + (float)var4;
}
        return 0.0f;
}
    public static int M(long var0) {
        for (int var2 = 0; var2 < 9; ++var2) {
            ItemStack var3 = ItemUtil.z.field_71439_g.field_71071_by.field_70462_a[var2];
            if (var3 == null || !(var3.func_77973_b() instanceof ItemPotion)) continue;
            var3.func_77973_b();
            if (!ItemPotion.func_77831_g((int)MethodAccessors.f(var3.func_77973_b(), var3))) continue;
            return var2;
}
        return -1;
}
    public static void c(int var0, int var1, char var2, int var3) {
        ItemUtil.z.field_71442_b.func_78753_a(ItemUtil.z.field_71439_g.field_71069_bz.field_75152_c, ItemUtil.K(var3), 1, 4, (EntityPlayer)ItemUtil.z.field_71439_g);
}
    public static int M(ItemStack var0) {
        if (var0 == null) {
            return 0;
}
        boolean var1 = (double)var0.func_77952_i() / (double)var0.func_77958_k() >= 0.7;
        int var2 = 0;
        Item var3 = var0.func_77973_b();
        if (var3 == Items.field_151161_ac || var3 == Items.field_151163_ad || var3 == Items.field_151173_ae || var3 == Items.field_151175_af) {
            var2 += 15;
        } else if (var3 == Items.field_151028_Y || var3 == Items.field_151030_Z || var3 == Items.field_151165_aa || var3 == Items.field_151167_ab) {
            var2 += 10;
        } else if (var3 == Items.field_151169_ag || var3 == Items.field_151171_ah || var3 == Items.field_151149_ai || var3 == Items.field_151151_aj) {
            var2 += 5;
        } else if (var3 == Items.field_151020_U || var3 == Items.field_151023_V || var3 == Items.field_151022_W || var3 == Items.field_151029_X) {
            var2 += 5;
}
        var2 += ItemUtil.X(var0);
        if (var1) {
            var2 = (int)((float)var2 * 0.5f);
}
        return var2;
}
    public static boolean y(ItemStack var0) {
        List var1 = ((ItemPotion)var0.func_77973_b()).func_77832_l(var0);
        if (var1 == null) {
            return false;
}
        int var3 = var1.size();
        for (int var2 = 0; var2 < var3; ++var2) {
            PotionEffect var4 = (PotionEffect)var1.get(var2);
            if (var4.func_76456_a() != Potion.field_76432_h.field_76415_H) continue;
            return true;
}
        return false;
}
    public static int b(long var0) {
        long var2 = 42909758276587L;
        for (int var4 = 0; var4 < 9; ++var4) {
            ItemStack var5 = ItemUtil.z.field_71439_g.field_71071_by.field_70462_a[var4];
            if (!ItemUtil.k(var2, var5)) continue;
            return var4;
}
        return -1;
}
    public static double p(short var0, ItemStack var1, char var2) {
        if (var1 == null) {
            return 0.0;
}
        boolean var6 = (double)var1.func_77952_i() / (double)var1.func_77958_k() >= 0.7;
        double var7 = 0.0;
        for (Map.Entry var10 : var1.func_111283_C().entries()) {
            if (!((String)var10.getKey()).equals("generic.attackDamage")) continue;
            var7 = ((AttributeModifier)var10.getValue()).func_111164_d();
            break;
}
        var7 += (double)EnchantmentHelper.func_77506_a((int)Enchantment.field_77334_n.field_77352_x, (ItemStack)var1) * 1.25 + (double)EnchantmentHelper.func_77506_a((int)Enchantment.field_180314_l.field_77352_x, (ItemStack)var1) * 1.25;
        if (var6) {
            var7 *= 0.5;
}
        return var7;
}
    public static Pair<ItemStack, Integer> H(IInventory var0) {
        Pair<Object, Object> var1 = new Pair<Object, Object>(null, null);
        for (int var2 = 0; var2 < var0.func_70302_i_(); ++var2) {
            ItemStack var3 = var0.func_70301_a(var2);
            if (var3 == null || !(var3.func_77973_b() instanceof ItemPotion) || var1.a() != null) continue;
            var1 = new Pair<ItemStack, Integer>(var3, var2);
}
        return var1;
}
    public static boolean f(ItemStack var0) {
        if (var0 == null) {
            return false;
}
        if (var0.func_77973_b() instanceof ItemBlock) {
            Block var1 = ((ItemBlock)var0.func_77973_b()).func_179223_d();
            return var1 == Blocks.field_150468_ap;
}
        return false;
}
    public static List O(long var0, IInventory var2) {
        ArrayList<Pair<Object, Integer>> var3 = new ArrayList<Pair<Object, Integer>>();
        var3.add(new Pair<Object, Integer>(null, 39));
        var3.add(new Pair<Object, Integer>(null, 38));
        var3.add(new Pair<Object, Integer>(null, 37));
        var3.add(new Pair<Object, Integer>(null, 36));
        ItemStack var4 = null;
        ItemStack var5 = null;
        ItemStack var6 = null;
        ItemStack var7 = null;
        for (int var8 = 0; var8 < var2.func_70302_i_(); ++var8) {
            ItemStack var9 = var2.func_70301_a(var8);
            if (var9 == null || !(var9.func_77973_b() instanceof ItemArmor)) continue;
            if (((ItemArmor)var9.func_77973_b()).field_77881_a == 0) {
                if (ItemUtil.M(var9) <= ItemUtil.M(var4)) continue;
                var4 = var9;
                var3.set(0, new Pair<ItemStack, Integer>(var4, var8));
                continue;
}
            if (((ItemArmor)var9.func_77973_b()).field_77881_a == 1) {
                if (ItemUtil.M(var9) <= ItemUtil.M(var5)) continue;
                var5 = var9;
                var3.set(1, new Pair<ItemStack, Integer>(var5, var8));
                continue;
}
            if (((ItemArmor)var9.func_77973_b()).field_77881_a == 2) {
                if (ItemUtil.M(var9) <= ItemUtil.M(var6)) continue;
                var6 = var9;
                var3.set(2, new Pair<ItemStack, Integer>(var6, var8));
                continue;
}
            if (((ItemArmor)var9.func_77973_b()).field_77881_a != 3 || ItemUtil.M(var9) <= ItemUtil.M(var7)) continue;
            var7 = var9;
            var3.set(3, new Pair<ItemStack, Integer>(var7, var8));
}
        return var3;
}
    public static int j(int var0, int var1, char var2, boolean var3) {
        for (int var8 = 0; var8 < 9; ++var8) {
            ItemStack var9 = ItemUtil.z.field_71439_g.field_71071_by.field_70462_a[var8];
            if (var9 == null || !(var3 ? var9.func_77973_b() instanceof ItemFishingRod : var9.func_77973_b() instanceof ItemSnowball || var9.func_77973_b() instanceof ItemEgg)) continue;
            ItemUtil.P(var8);
            return var8;
}
        return -1;
}
    public static Pair<ItemStack, Integer> o(IInventory var0) {
        Pair<Object, Object> var1 = new Pair<Object, Object>(null, null);
        for (int var2 = 0; var2 < var0.func_70302_i_(); ++var2) {
            ItemStack var3 = var0.func_70301_a(var2);
            if (var3 == null || !(var3.func_77973_b() instanceof ItemSnowball) && !(var3.func_77973_b() instanceof ItemEgg) && !(var3.func_77973_b() instanceof ItemFishingRod)) continue;
            if (var1.a() == null) {
                var1 = new Pair<ItemStack, Integer>(var3, var2);
                continue;
}
            if (!(((ItemStack)var1.a()).func_77973_b() instanceof ItemFishingRod) || !(var3.func_77973_b() instanceof ItemSnowball) && !(var3.func_77973_b() instanceof ItemEgg)) {
                if (!(((ItemStack)var1.a()).func_77973_b() instanceof ItemEgg) && !(((ItemStack)var1.a()).func_77973_b() instanceof ItemEgg) || !(var3.func_77973_b() instanceof ItemSnowball) && !(var3.func_77973_b() instanceof ItemEgg) || var3.field_77994_a <= ((ItemStack)var1.a()).field_77994_a) continue;
                var1 = new Pair<ItemStack, Integer>(var3, var2);
                continue;
}
            var1 = new Pair<ItemStack, Integer>(var3, var2);
}
        return var1;
}
    public static boolean u(ItemStack var0) {
        if (var0 != null && var0.field_77994_a >= 1) {
            Item var1 = var0.func_77973_b();
            return var1 instanceof ItemBlock ? ItemUtil.A((ItemBlock)var1) : false;
}
        return false;
}
    public static Pair<ItemStack, Integer> F(IInventory var0) {
        Pair<Object, Object> var1 = new Pair<Object, Object>(null, null);
        for (int var2 = 0; var2 < var0.func_70302_i_(); ++var2) {
            ItemStack var3 = var0.func_70301_a(var2);
            if (var3 == null || !(var3.func_77973_b() instanceof ItemEnderPearl) || var1.a() != null && var3.field_77994_a <= ((ItemStack)var1.a()).field_77994_a) continue;
            var1 = new Pair<ItemStack, Integer>(var3, var2);
}
        return var1;
}
    public static Pair q(long var0, IInventory var2) {
        var0 = a ^ var0;
        int var3 = (int)((var0 ^ 0x15F1D43D30F8L) >>> 48);
        int var4 = (int)((var0 ^ 0x15F1D43D30F8L) << 16 >>> 48);
        Pair<Object, Object> var6 = new Pair<Object, Object>(null, null);
        for (int var7 = 0; var7 < var2.func_70302_i_(); ++var7) {
            ItemStack var8 = var2.func_70301_a(var7);
            if (var8 == null || !(var8.func_77973_b() instanceof ItemSword) || !(ItemUtil.p((short)var3, var8, (char)var4) > ItemUtil.p((short)var3, var6.a(), (char)var4))) continue;
            var6 = new Pair<ItemStack, Integer>(var8, var7);
}
        return var6;
}
    public static boolean k(long var0, ItemStack var2) {
        var0 = a ^ var0;
        int var3 = (int)((var0 ^ 0x15AA1E9A9B48L) >>> 32);
        int var4 = (int)((var0 ^ 0x15AA1E9A9B48L) << 32 >>> 48);
        return var2 != null && var2.func_77973_b() instanceof ItemBucket && BlockAccessor.o(var3, (ItemBucket)var2.func_77973_b(), (short)var4) == Blocks.field_150356_k;
}
    public static boolean A(ItemBlock var0) {
        Block var1 = var0.func_179223_d();
        return BlockUtil.p(var1) ? false : BlockUtil.i(var1);
}
    public static void P(int var2) {
        if (var2 >= 0 && var2 <= 8 && ItemUtil.z.field_71439_g.field_71071_by.field_70461_c != var2) {
            ItemUtil.z.field_71439_g.field_71071_by.field_70461_c = var2;
            PlayerControllerAccessor.Q(ItemUtil.z.field_71442_b);
}
}
    public static void Q(long var0, int var2, int var3) {
        ItemUtil.z.field_71442_b.func_78753_a(ItemUtil.z.field_71439_g.field_71069_bz.field_75152_c, ItemUtil.K(var2), ItemUtil.K(var3), 2, (EntityPlayer)ItemUtil.z.field_71439_g);
}
    public static Pair<ItemStack, Integer> i(IInventory var0) {
        Pair<Object, Object> var1 = new Pair<Object, Object>(null, null);
        for (int var2 = 0; var2 < var0.func_70302_i_(); ++var2) {
            ItemStack var3 = var0.func_70301_a(var2);
            if (var3 == null || !(var3.func_77973_b() instanceof ItemFireball) || var1.a() != null && var3.field_77994_a <= ((ItemStack)var1.a()).field_77994_a) continue;
            var1 = new Pair<ItemStack, Integer>(var3, var2);
}
        return var1;
}
    public static int l(long var0) {
        long var2 = 2324698497256L;
        for (int var4 = 0; var4 < 9; ++var4) {
            ItemStack var5 = ItemUtil.z.field_71439_g.field_71071_by.field_70462_a[var4];
            if (!ItemUtil.c(var5, var2)) continue;
            return var4;
}
        return -1;
}
    public static boolean H(long var0) {
        for (int var2 = 9; var2 < 45; ++var2) {
            if (ItemUtil.z.field_71439_g.field_71069_bz.func_75139_a(var2).func_75211_c() != null) continue;
            return false;
}
        return true;
}
    public static int N(long var0) {
        for (int var2 = 0; var2 < 9; ++var2) {
            ItemStack var3 = ItemUtil.z.field_71439_g.field_71071_by.field_70462_a[var2];
            if (var3 == null || !(var3.func_77973_b() instanceof ItemEnderPearl)) continue;
            return var2;
}
        return -1;
}
    public static List D(IInventory var0, short var1) {
        ArrayList<Pair<Object, Object>> var8 = new ArrayList<Pair<Object, Object>>();
        var8.add(new Pair<Object, Object>(null, null));
        var8.add(new Pair<Object, Object>(null, null));
        var8.add(new Pair<Object, Object>(null, null));
        ItemStack var9 = null;
        ItemStack var10 = null;
        ItemStack var11 = null;
        for (int var12 = 0; var12 < var0.func_70302_i_(); ++var12) {
            ItemStack var13 = var0.func_70301_a(var12);
            if (var13 == null || !(var13.func_77973_b() instanceof ItemTool)) continue;
            if (var13.func_77973_b() instanceof ItemPickaxe && ItemUtil.Y(0L, var9) < ItemUtil.Y(0L, var13)) {
                var9 = var13;
                var8.set(0, new Pair<ItemStack, Integer>(var9, var12));
                continue;
}
            if (var13.func_77973_b() instanceof ItemAxe && ItemUtil.Y(0L, var10) < ItemUtil.Y(0L, var13)) {
                var10 = var13;
                var8.set(1, new Pair<ItemStack, Integer>(var10, var12));
                continue;
}
            if (!(var13.func_77973_b() instanceof ItemSpade) || !(ItemUtil.Y(0L, var11) < ItemUtil.Y(0L, var13))) continue;
            var11 = var13;
            var8.set(2, new Pair<ItemStack, Integer>(var11, var12));
}
        return var8;
}
    public static Pair<ItemStack, Integer> k(IInventory var0) {
        Pair<Object, Object> var1 = new Pair<Object, Object>(null, null);
        for (int var2 = 0; var2 < var0.func_70302_i_(); ++var2) {
            ItemStack var3 = var0.func_70301_a(var2);
            if (var3 == null || !(var3.func_77973_b() instanceof ItemFood)) continue;
            if (var1.a() == null) {
                var1 = new Pair<ItemStack, Integer>(var3, var2);
                continue;
}
            if (((ItemStack)var1.a()).func_77973_b() != Items.field_151153_ao && var3.func_77973_b() == Items.field_151153_ao) {
                var1 = new Pair<ItemStack, Integer>(var3, var2);
                continue;
}
            if (((ItemStack)var1.a()).func_77973_b() == Items.field_151153_ao && var3.func_77973_b() == Items.field_151153_ao && !((ItemStack)var1.a()).func_77948_v() && var3.func_77948_v()) {
                var1 = new Pair<ItemStack, Integer>(var3, var2);
                continue;
}
            if (((ItemStack)var1.a()).func_77973_b() == Items.field_151153_ao && var3.func_77973_b() == Items.field_151153_ao && ((ItemStack)var1.a()).func_77948_v() && var3.func_77948_v() && var3.field_77994_a > ((ItemStack)var1.a()).field_77994_a) {
                var1 = new Pair<ItemStack, Integer>(var3, var2);
                continue;
}
            if (((ItemStack)var1.a()).func_77973_b() == Items.field_151153_ao && var3.func_77973_b() == Items.field_151153_ao && var3.field_77994_a > ((ItemStack)var1.a()).field_77994_a && !((ItemStack)var1.a()).func_77948_v() && !var3.func_77948_v()) {
                var1 = new Pair<ItemStack, Integer>(var3, var2);
                continue;
}
            if (((ItemStack)var1.a()).func_77973_b() == Items.field_151153_ao || var3.func_77973_b() == Items.field_151153_ao || var3.field_77994_a <= ((ItemStack)var1.a()).field_77994_a) continue;
            var1 = new Pair<ItemStack, Integer>(var3, var2);
}
        return var1;
}
    public static Pair<ItemStack, Integer> Y(IInventory var0) {
        Pair<Object, Object> var1 = new Pair<Object, Object>(null, null);
        for (int var2 = 0; var2 < var0.func_70302_i_(); ++var2) {
            ItemStack var3 = var0.func_70301_a(var2);
            if (!ItemUtil.u(var3)) continue;
            if (var1.a() == null) {
                var1 = new Pair<ItemStack, Integer>(var3, var2);
                continue;
}
            if (var3.field_77994_a <= ((ItemStack)var1.a()).field_77994_a) continue;
            var1 = new Pair<ItemStack, Integer>(var3, var2);
}
        return var1;
}
    public static void e(GuiScreen var0) {
        int var1 = Mouse.getX() * var0.field_146294_l / ItemUtil.z.field_71443_c;
        int var2 = var0.field_146295_m - Mouse.getY() * var0.field_146295_m / ItemUtil.z.field_71440_d - 1;
        GuiScreenAccessor.c(var0, var1, var2, 0);
}
    public static float l(ItemStack var0, Block var1) {
        int var3;
        if (var0 == null) {
            return 0.0f;
}
        float var2 = var0.func_150997_a(var1);
        if (var2 > 1.0f && (var3 = EnchantmentHelper.func_77506_a((int)Enchantment.field_77349_p.field_77352_x, (ItemStack)var0)) > 0) {
            var2 += (float)(var3 * var3 + 1);
}
        return var2;
}
    public static boolean c(ItemStack var0, long var1) {
        var1 = a ^ var1;
        int var3 = (int)((var1 ^ 0x30B1E8A8D84BL) >>> 32);
        int var4 = (int)((var1 ^ 0x30B1E8A8D84BL) << 32 >>> 48);
        return var0 != null && var0.func_77973_b() instanceof ItemBucket && BlockAccessor.o(var3, (ItemBucket)var0.func_77973_b(), (short)var4) == Blocks.field_150358_i;
}
    public static void B(long var0, int var2) {
        ItemUtil.z.field_71442_b.func_78753_a(ItemUtil.z.field_71439_g.field_71069_bz.field_75152_c, ItemUtil.K(var2), 0, 1, (EntityPlayer)ItemUtil.z.field_71439_g);
}
    public static int e(long var0, Block var2) {
        float var3 = 1.0f;
        int var4 = -1;
        for (int var5 = 0; var5 < 9; ++var5) {
            float var7;
            ItemStack var6 = ItemUtil.z.field_71439_g.field_71071_by.func_70301_a(var5);
            if (var6 == null || !((var7 = ItemUtil.l(var6, var2)) > var3)) continue;
            var3 = var7;
            var4 = var5;
}
        return var4;
}
    public static Pair<ItemStack, Integer> W(IInventory var0) {
        Pair<Object, Object> var1 = new Pair<Object, Object>(null, null);
        for (int var2 = 0; var2 < var0.func_70302_i_(); ++var2) {
            ItemStack var3 = var0.func_70301_a(var2);
            if (var3 == null || !(var3.func_77973_b() instanceof ItemShears) || var1.a() != null && var3.field_77994_a <= ((ItemStack)var1.a()).field_77994_a) continue;
            var1 = new Pair<ItemStack, Integer>(var3, var2);
}
        return var1;
}
    public static int w(int var0, char var1, short var2) {
        for (int var5 = 0; var5 < 9; ++var5) {
            ItemStack var6 = ItemUtil.z.field_71439_g.field_71071_by.field_70462_a[var5];
            if (var6 == null || !var6.func_82833_r().toLowerCase().contains("golden head")) continue;
            return var5;
}
        return -1;
}
    public static Pair<ItemStack, Integer> O(IInventory var0) {
        Pair<Object, Object> var1 = new Pair<Object, Object>(null, null);
        for (int var2 = 0; var2 < var0.func_70302_i_(); ++var2) {
            ItemStack var3 = var0.func_70301_a(var2);
            if (var3 == null || !(var3.func_77973_b() instanceof ItemBow) || !(ItemUtil.b(var3) > ItemUtil.b(var1.a()))) continue;
            var1 = new Pair<ItemStack, Integer>(var3, var2);
}
        return var1;
}
    public static float b(ItemStack var0) {
        if (var0 == null) {
            return 0.0f;
}
        if ((double)var0.func_77952_i() / (double)var0.func_77958_k() >= 0.7) {
            return 0.0f;
}
        float var1 = 0.0f;
        Item var2 = var0.func_77973_b();
        if (var2 instanceof ItemBow) {
            var1 += (float)EnchantmentHelper.func_77506_a((int)Enchantment.field_77345_t.field_77352_x, (ItemStack)var0);
            var1 += (float)EnchantmentHelper.func_77506_a((int)Enchantment.field_77342_w.field_77352_x, (ItemStack)var0);
            var1 += (float)EnchantmentHelper.func_77506_a((int)Enchantment.field_77343_v.field_77352_x, (ItemStack)var0) * 0.5f;
            var1 += (float)EnchantmentHelper.func_77506_a((int)Enchantment.field_77347_r.field_77352_x, (ItemStack)var0) * 0.1f;
}
        return var1;
}
    public static int X(ItemStack var0) {
        boolean var1 = (double)var0.func_77952_i() / (double)var0.func_77958_k() >= 0.7;
        int var2 = ((ItemArmor)var0.func_77973_b()).field_77879_b + EnchantmentHelper.func_77508_a((ItemStack[])new ItemStack[]{var0}, (DamageSource)DamageSource.field_76377_j);
        if (var1) {
            var2 = (int)((float)var2 * 0.5f);
}
        return var2;
}
    static {
        z = MinecraftRef.c((byte)0, 0L);
}
}