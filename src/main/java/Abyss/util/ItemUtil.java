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
    private static long a;

    private static String[] i;
        private static Minecraft z;
    private static long[] e;
    private static Object[] h;
    

    public static boolean d() {
        return ItemUtil.z.thePlayer != null && ItemUtil.z.thePlayer.getHeldItem() != null && ItemUtil.z.thePlayer.getHeldItem().getItem() instanceof ItemSword;
}
    public static boolean X(Item var0, IInventory var1) {
        for (int var2 = 0; var2 < var1.getSizeInventory(); ++var2) {
            ItemStack var3 = var1.getStackInSlot(var2);
            if (var3 == null || var0 == null || var0 != var3.getItem()) continue;
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
        if ((double)var2.getItemDamage() / (double)var2.getMaxDamage() >= 0.7) {
            return 0.0f;
}
        Item var3 = var2.getItem();
        int var4 = EnchantmentHelper.getEnchantmentLevel((int)Enchantment.efficiency.effectId, (ItemStack)var2);
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
            return var14.getToolMaterial().getEfficiencyOnProperMaterial() + (float)var4;
}
        if (var3 instanceof ItemSpade) {
            ItemSpade var13 = (ItemSpade)var3;
            return var13.getToolMaterial().getEfficiencyOnProperMaterial() + (float)var4;
}
        if (var3 instanceof ItemAxe) {
            ItemAxe var12 = (ItemAxe)var3;
            return var12.getToolMaterial().getEfficiencyOnProperMaterial() + (float)var4;
}
        return 0.0f;
}
    public static int M(long var0) {
        for (int var2 = 0; var2 < 9; ++var2) {
            ItemStack var3 = ItemUtil.z.thePlayer.inventory.mainInventory[var2];
            if (var3 == null || !(var3.getItem() instanceof ItemPotion)) continue;
            var3.getItem();
            if (!ItemPotion.isSplash((int)MethodAccessors.f(var3.getItem(), var3))) continue;
            return var2;
}
        return -1;
}
    public static void c(int var0, int var1, char var2, int var3) {
        ItemUtil.z.playerController.windowClick(ItemUtil.z.thePlayer.inventoryContainer.windowId, ItemUtil.K(var3), 1, 4, (EntityPlayer)ItemUtil.z.thePlayer);
}
    public static int M(ItemStack var0) {
        if (var0 == null) {
            return 0;
}
        boolean var1 = (double)var0.getItemDamage() / (double)var0.getMaxDamage() >= 0.7;
        int var2 = 0;
        Item var3 = var0.getItem();
        if (var3 == Items.diamond_helmet || var3 == Items.diamond_chestplate || var3 == Items.diamond_leggings || var3 == Items.diamond_boots) {
            var2 += 15;
        } else if (var3 == Items.iron_helmet || var3 == Items.iron_chestplate || var3 == Items.iron_leggings || var3 == Items.iron_boots) {
            var2 += 10;
        } else if (var3 == Items.golden_helmet || var3 == Items.golden_chestplate || var3 == Items.golden_leggings || var3 == Items.golden_boots) {
            var2 += 5;
        } else if (var3 == Items.chainmail_helmet || var3 == Items.chainmail_chestplate || var3 == Items.chainmail_leggings || var3 == Items.chainmail_boots) {
            var2 += 5;
}
        var2 += ItemUtil.X(var0);
        if (var1) {
            var2 = (int)((float)var2 * 0.5f);
}
        return var2;
}
    public static boolean y(ItemStack var0) {
        List var1 = ((ItemPotion)var0.getItem()).getEffects(var0);
        if (var1 == null) {
            return false;
}
        int var3 = var1.size();
        for (int var2 = 0; var2 < var3; ++var2) {
            PotionEffect var4 = (PotionEffect)var1.get(var2);
            if (var4.getPotionID() != Potion.heal.id) continue;
            return true;
}
        return false;
}
    public static int b(long var0) {
        long var2 = 42909758276587L;
        for (int var4 = 0; var4 < 9; ++var4) {
            ItemStack var5 = ItemUtil.z.thePlayer.inventory.mainInventory[var4];
            if (!ItemUtil.k(var2, var5)) continue;
            return var4;
}
        return -1;
}
    public static double p(short var0, ItemStack var1, char var2) {
        if (var1 == null) {
            return 0.0;
}
        boolean var6 = (double)var1.getItemDamage() / (double)var1.getMaxDamage() >= 0.7;
        double var7 = 0.0;
        for (Map.Entry var10 : var1.getAttributeModifiers().entries()) {
            if (!((String)var10.getKey()).equals("generic.attackDamage")) continue;
            var7 = ((AttributeModifier)var10.getValue()).getAmount();
            break;
}
        var7 += (double)EnchantmentHelper.getEnchantmentLevel((int)Enchantment.fireAspect.effectId, (ItemStack)var1) * 1.25 + (double)EnchantmentHelper.getEnchantmentLevel((int)Enchantment.sharpness.effectId, (ItemStack)var1) * 1.25;
        if (var6) {
            var7 *= 0.5;
}
        return var7;
}
    public static Pair<ItemStack, Integer> H(IInventory var0) {
        Pair<ItemStack, Integer> var1 = new Pair<ItemStack, Integer>(null, null);
        for (int var2 = 0; var2 < var0.getSizeInventory(); ++var2) {
            ItemStack var3 = var0.getStackInSlot(var2);
            if (var3 == null || !(var3.getItem() instanceof ItemPotion) || var1.a() != null) continue;
            var1 = new Pair<ItemStack, Integer>(var3, var2);
}
        return var1;
}
    public static boolean f(ItemStack var0) {
        if (var0 == null) {
            return false;
}
        if (var0.getItem() instanceof ItemBlock) {
            Block var1 = ((ItemBlock)var0.getItem()).getBlock();
            return var1 == Blocks.ladder;
}
        return false;
}
    public static List O(long var0, IInventory var2) {
        ArrayList<Pair<ItemStack, Integer>> var3 = new ArrayList<Pair<ItemStack, Integer>>();
        var3.add(new Pair<ItemStack, Integer>(null, 39));
        var3.add(new Pair<ItemStack, Integer>(null, 38));
        var3.add(new Pair<ItemStack, Integer>(null, 37));
        var3.add(new Pair<ItemStack, Integer>(null, 36));
        ItemStack var4 = null;
        ItemStack var5 = null;
        ItemStack var6 = null;
        ItemStack var7 = null;
        for (int var8 = 0; var8 < var2.getSizeInventory(); ++var8) {
            ItemStack var9 = var2.getStackInSlot(var8);
            if (var9 == null || !(var9.getItem() instanceof ItemArmor)) continue;
            if (((ItemArmor)var9.getItem()).armorType == 0) {
                if (ItemUtil.M(var9) <= ItemUtil.M(var4)) continue;
                var4 = var9;
                var3.set(0, new Pair<ItemStack, Integer>(var4, var8));
                continue;
}
            if (((ItemArmor)var9.getItem()).armorType == 1) {
                if (ItemUtil.M(var9) <= ItemUtil.M(var5)) continue;
                var5 = var9;
                var3.set(1, new Pair<ItemStack, Integer>(var5, var8));
                continue;
}
            if (((ItemArmor)var9.getItem()).armorType == 2) {
                if (ItemUtil.M(var9) <= ItemUtil.M(var6)) continue;
                var6 = var9;
                var3.set(2, new Pair<ItemStack, Integer>(var6, var8));
                continue;
}
            if (((ItemArmor)var9.getItem()).armorType != 3 || ItemUtil.M(var9) <= ItemUtil.M(var7)) continue;
            var7 = var9;
            var3.set(3, new Pair<ItemStack, Integer>(var7, var8));
}
        return var3;
}
    public static int j(int var0, int var1, char var2, boolean var3) {
        for (int var8 = 0; var8 < 9; ++var8) {
            ItemStack var9 = ItemUtil.z.thePlayer.inventory.mainInventory[var8];
            if (var9 == null || !(var3 ? var9.getItem() instanceof ItemFishingRod : var9.getItem() instanceof ItemSnowball || var9.getItem() instanceof ItemEgg)) continue;
            ItemUtil.P(var8);
            return var8;
}
        return -1;
}
    public static Pair<ItemStack, Integer> o(IInventory var0) {
        Pair<ItemStack, Integer> var1 = new Pair<ItemStack, Integer>(null, null);
        for (int var2 = 0; var2 < var0.getSizeInventory(); ++var2) {
            ItemStack var3 = var0.getStackInSlot(var2);
            if (var3 == null || !(var3.getItem() instanceof ItemSnowball) && !(var3.getItem() instanceof ItemEgg) && !(var3.getItem() instanceof ItemFishingRod)) continue;
            if (var1.a() == null) {
                var1 = new Pair<ItemStack, Integer>(var3, var2);
                continue;
}
            if (!(((ItemStack)var1.a()).getItem() instanceof ItemFishingRod) || !(var3.getItem() instanceof ItemSnowball) && !(var3.getItem() instanceof ItemEgg)) {
                if (!(((ItemStack)var1.a()).getItem() instanceof ItemEgg) && !(((ItemStack)var1.a()).getItem() instanceof ItemEgg) || !(var3.getItem() instanceof ItemSnowball) && !(var3.getItem() instanceof ItemEgg) || var3.stackSize <= ((ItemStack)var1.a()).stackSize) continue;
                var1 = new Pair<ItemStack, Integer>(var3, var2);
                continue;
}
            var1 = new Pair<ItemStack, Integer>(var3, var2);
}
        return var1;
}
    public static boolean u(ItemStack var0) {
        if (var0 != null && var0.stackSize >= 1) {
            Item var1 = var0.getItem();
            return var1 instanceof ItemBlock ? ItemUtil.A((ItemBlock)var1) : false;
}
        return false;
}
    public static Pair<ItemStack, Integer> F(IInventory var0) {
        Pair<ItemStack, Integer> var1 = new Pair<ItemStack, Integer>(null, null);
        for (int var2 = 0; var2 < var0.getSizeInventory(); ++var2) {
            ItemStack var3 = var0.getStackInSlot(var2);
            if (var3 == null || !(var3.getItem() instanceof ItemEnderPearl) || var1.a() != null && var3.stackSize <= ((ItemStack)var1.a()).stackSize) continue;
            var1 = new Pair<ItemStack, Integer>(var3, var2);
}
        return var1;
}
    public static Pair q(long var0, IInventory var2) {
        var0 = a ^ var0;
        int var3 = (int)((var0 ^ 0x15F1D43D30F8L) >>> 48);
        int var4 = (int)((var0 ^ 0x15F1D43D30F8L) << 16 >>> 48);
        Pair<ItemStack, Integer> var6 = new Pair<ItemStack, Integer>(null, null);
        for (int var7 = 0; var7 < var2.getSizeInventory(); ++var7) {
            ItemStack var8 = var2.getStackInSlot(var7);
            if (var8 == null || !(var8.getItem() instanceof ItemSword) || !(ItemUtil.p((short)var3, var8, (char)var4) > ItemUtil.p((short)var3, var6.a(), (char)var4))) continue;
            var6 = new Pair<ItemStack, Integer>(var8, var7);
}
        return var6;
}
    public static boolean k(long var0, ItemStack var2) {
        var0 = a ^ var0;
        int var3 = (int)((var0 ^ 0x15AA1E9A9B48L) >>> 32);
        int var4 = (int)((var0 ^ 0x15AA1E9A9B48L) << 32 >>> 48);
        return var2 != null && var2.getItem() instanceof ItemBucket && BlockAccessor.o(var3, (ItemBucket)var2.getItem(), (short)var4) == Blocks.flowing_lava;
}
    public static boolean A(ItemBlock var0) {
        Block var1 = var0.getBlock();
        return BlockUtil.p(var1) ? false : BlockUtil.i(var1);
}
    public static void P(int var2) {
        if (var2 >= 0 && var2 <= 8 && ItemUtil.z.thePlayer.inventory.currentItem != var2) {
            ItemUtil.z.thePlayer.inventory.currentItem = var2;
            PlayerControllerAccessor.Q(ItemUtil.z.playerController);
}
}
    public static void Q(long var0, int var2, int var3) {
        ItemUtil.z.playerController.windowClick(ItemUtil.z.thePlayer.inventoryContainer.windowId, ItemUtil.K(var2), ItemUtil.K(var3), 2, (EntityPlayer)ItemUtil.z.thePlayer);
}
    public static Pair<ItemStack, Integer> i(IInventory var0) {
        Pair<ItemStack, Integer> var1 = new Pair<ItemStack, Integer>(null, null);
        for (int var2 = 0; var2 < var0.getSizeInventory(); ++var2) {
            ItemStack var3 = var0.getStackInSlot(var2);
            if (var3 == null || !(var3.getItem() instanceof ItemFireball) || var1.a() != null && var3.stackSize <= ((ItemStack)var1.a()).stackSize) continue;
            var1 = new Pair<ItemStack, Integer>(var3, var2);
}
        return var1;
}
    public static int l(long var0) {
        long var2 = 2324698497256L;
        for (int var4 = 0; var4 < 9; ++var4) {
            ItemStack var5 = ItemUtil.z.thePlayer.inventory.mainInventory[var4];
            if (!ItemUtil.c(var5, var2)) continue;
            return var4;
}
        return -1;
}
    public static boolean H(long var0) {
        for (int var2 = 9; var2 < 45; ++var2) {
            if (ItemUtil.z.thePlayer.inventoryContainer.getSlot(var2).getStack() != null) continue;
            return false;
}
        return true;
}
    public static int N(long var0) {
        for (int var2 = 0; var2 < 9; ++var2) {
            ItemStack var3 = ItemUtil.z.thePlayer.inventory.mainInventory[var2];
            if (var3 == null || !(var3.getItem() instanceof ItemEnderPearl)) continue;
            return var2;
}
        return -1;
}
    public static List D(IInventory var0, short var1) {
        ArrayList<Pair<ItemStack, Integer>> var8 = new ArrayList<Pair<ItemStack, Integer>>();
        var8.add(new Pair<ItemStack, Integer>(null, null));
        var8.add(new Pair<ItemStack, Integer>(null, null));
        var8.add(new Pair<ItemStack, Integer>(null, null));
        ItemStack var9 = null;
        ItemStack var10 = null;
        ItemStack var11 = null;
        for (int var12 = 0; var12 < var0.getSizeInventory(); ++var12) {
            ItemStack var13 = var0.getStackInSlot(var12);
            if (var13 == null || !(var13.getItem() instanceof ItemTool)) continue;
            if (var13.getItem() instanceof ItemPickaxe && ItemUtil.Y(0L, var9) < ItemUtil.Y(0L, var13)) {
                var9 = var13;
                var8.set(0, new Pair<ItemStack, Integer>(var9, var12));
                continue;
}
            if (var13.getItem() instanceof ItemAxe && ItemUtil.Y(0L, var10) < ItemUtil.Y(0L, var13)) {
                var10 = var13;
                var8.set(1, new Pair<ItemStack, Integer>(var10, var12));
                continue;
}
            if (!(var13.getItem() instanceof ItemSpade) || !(ItemUtil.Y(0L, var11) < ItemUtil.Y(0L, var13))) continue;
            var11 = var13;
            var8.set(2, new Pair<ItemStack, Integer>(var11, var12));
}
        return var8;
}
    public static Pair<ItemStack, Integer> k(IInventory var0) {
        Pair<ItemStack, Integer> var1 = new Pair<ItemStack, Integer>(null, null);
        for (int var2 = 0; var2 < var0.getSizeInventory(); ++var2) {
            ItemStack var3 = var0.getStackInSlot(var2);
            if (var3 == null || !(var3.getItem() instanceof ItemFood)) continue;
            if (var1.a() == null) {
                var1 = new Pair<ItemStack, Integer>(var3, var2);
                continue;
}
            if (((ItemStack)var1.a()).getItem() != Items.golden_apple && var3.getItem() == Items.golden_apple) {
                var1 = new Pair<ItemStack, Integer>(var3, var2);
                continue;
}
            if (((ItemStack)var1.a()).getItem() == Items.golden_apple && var3.getItem() == Items.golden_apple && !((ItemStack)var1.a()).isItemEnchanted() && var3.isItemEnchanted()) {
                var1 = new Pair<ItemStack, Integer>(var3, var2);
                continue;
}
            if (((ItemStack)var1.a()).getItem() == Items.golden_apple && var3.getItem() == Items.golden_apple && ((ItemStack)var1.a()).isItemEnchanted() && var3.isItemEnchanted() && var3.stackSize > ((ItemStack)var1.a()).stackSize) {
                var1 = new Pair<ItemStack, Integer>(var3, var2);
                continue;
}
            if (((ItemStack)var1.a()).getItem() == Items.golden_apple && var3.getItem() == Items.golden_apple && var3.stackSize > ((ItemStack)var1.a()).stackSize && !((ItemStack)var1.a()).isItemEnchanted() && !var3.isItemEnchanted()) {
                var1 = new Pair<ItemStack, Integer>(var3, var2);
                continue;
}
            if (((ItemStack)var1.a()).getItem() == Items.golden_apple || var3.getItem() == Items.golden_apple || var3.stackSize <= ((ItemStack)var1.a()).stackSize) continue;
            var1 = new Pair<ItemStack, Integer>(var3, var2);
}
        return var1;
}
    public static Pair<ItemStack, Integer> Y(IInventory var0) {
        Pair<ItemStack, Integer> var1 = new Pair<ItemStack, Integer>(null, null);
        for (int var2 = 0; var2 < var0.getSizeInventory(); ++var2) {
            ItemStack var3 = var0.getStackInSlot(var2);
            if (!ItemUtil.u(var3)) continue;
            if (var1.a() == null) {
                var1 = new Pair<ItemStack, Integer>(var3, var2);
                continue;
}
            if (var3.stackSize <= ((ItemStack)var1.a()).stackSize) continue;
            var1 = new Pair<ItemStack, Integer>(var3, var2);
}
        return var1;
}
    public static void e(GuiScreen var0) {
        int var1 = Mouse.getX() * var0.width / ItemUtil.z.displayWidth;
        int var2 = var0.height - Mouse.getY() * var0.height / ItemUtil.z.displayHeight - 1;
        GuiScreenAccessor.c(var0, var1, var2, 0);
}
    public static float l(ItemStack var0, Block var1) {
        int var3;
        if (var0 == null) {
            return 0.0f;
}
        float var2 = var0.getStrVsBlock(var1);
        if (var2 > 1.0f && (var3 = EnchantmentHelper.getEnchantmentLevel((int)Enchantment.efficiency.effectId, (ItemStack)var0)) > 0) {
            var2 += (float)(var3 * var3 + 1);
}
        return var2;
}
    public static boolean c(ItemStack var0, long var1) {
        var1 = a ^ var1;
        int var3 = (int)((var1 ^ 0x30B1E8A8D84BL) >>> 32);
        int var4 = (int)((var1 ^ 0x30B1E8A8D84BL) << 32 >>> 48);
        return var0 != null && var0.getItem() instanceof ItemBucket && BlockAccessor.o(var3, (ItemBucket)var0.getItem(), (short)var4) == Blocks.flowing_water;
}
    public static void B(long var0, int var2) {
        ItemUtil.z.playerController.windowClick(ItemUtil.z.thePlayer.inventoryContainer.windowId, ItemUtil.K(var2), 0, 1, (EntityPlayer)ItemUtil.z.thePlayer);
}
    public static int e(long var0, Block var2) {
        float var3 = 1.0f;
        int var4 = -1;
        for (int var5 = 0; var5 < 9; ++var5) {
            float var7;
            ItemStack var6 = ItemUtil.z.thePlayer.inventory.getStackInSlot(var5);
            if (var6 == null || !((var7 = ItemUtil.l(var6, var2)) > var3)) continue;
            var3 = var7;
            var4 = var5;
}
        return var4;
}
    public static Pair<ItemStack, Integer> W(IInventory var0) {
        Pair<ItemStack, Integer> var1 = new Pair<ItemStack, Integer>(null, null);
        for (int var2 = 0; var2 < var0.getSizeInventory(); ++var2) {
            ItemStack var3 = var0.getStackInSlot(var2);
            if (var3 == null || !(var3.getItem() instanceof ItemShears) || var1.a() != null && var3.stackSize <= ((ItemStack)var1.a()).stackSize) continue;
            var1 = new Pair<ItemStack, Integer>(var3, var2);
}
        return var1;
}
    public static int w(int var0, char var1, short var2) {
        for (int var5 = 0; var5 < 9; ++var5) {
            ItemStack var6 = ItemUtil.z.thePlayer.inventory.mainInventory[var5];
            if (var6 == null || !var6.getDisplayName().toLowerCase().contains("golden head")) continue;
            return var5;
}
        return -1;
}
    public static Pair<ItemStack, Integer> O(IInventory var0) {
        Pair<ItemStack, Integer> var1 = new Pair<ItemStack, Integer>(null, null);
        for (int var2 = 0; var2 < var0.getSizeInventory(); ++var2) {
            ItemStack var3 = var0.getStackInSlot(var2);
            if (var3 == null || !(var3.getItem() instanceof ItemBow) || !(ItemUtil.b(var3) > ItemUtil.b(var1.a()))) continue;
            var1 = new Pair<ItemStack, Integer>(var3, var2);
}
        return var1;
}
    public static float b(ItemStack var0) {
        if (var0 == null) {
            return 0.0f;
}
        if ((double)var0.getItemDamage() / (double)var0.getMaxDamage() >= 0.7) {
            return 0.0f;
}
        float var1 = 0.0f;
        Item var2 = var0.getItem();
        if (var2 instanceof ItemBow) {
            var1 += (float)EnchantmentHelper.getEnchantmentLevel((int)Enchantment.power.effectId, (ItemStack)var0);
            var1 += (float)EnchantmentHelper.getEnchantmentLevel((int)Enchantment.infinity.effectId, (ItemStack)var0);
            var1 += (float)EnchantmentHelper.getEnchantmentLevel((int)Enchantment.flame.effectId, (ItemStack)var0) * 0.5f;
            var1 += (float)EnchantmentHelper.getEnchantmentLevel((int)Enchantment.unbreaking.effectId, (ItemStack)var0) * 0.1f;
}
        return var1;
}
    public static int X(ItemStack var0) {
        boolean var1 = (double)var0.getItemDamage() / (double)var0.getMaxDamage() >= 0.7;
        int var2 = ((ItemArmor)var0.getItem()).damageReduceAmount + EnchantmentHelper.getEnchantmentModifierDamage((ItemStack[])new ItemStack[]{var0}, (DamageSource)DamageSource.generic);
        if (var1) {
            var2 = (int)((float)var2 * 0.5f);
}
        return var2;
}
    static {
        a = 42568341579408L;
        z = MinecraftRef.c((byte)0, 0L);
}
}