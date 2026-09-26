/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.gui.inventory.GuiChest
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.inventory.ContainerChest
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemArmor
 *  net.minecraft.item.ItemAxe
 *  net.minecraft.item.ItemBow
 *  net.minecraft.item.ItemEgg
 *  net.minecraft.item.ItemEnderPearl
 *  net.minecraft.item.ItemFishingRod
 *  net.minecraft.item.ItemFood
 *  net.minecraft.item.ItemPickaxe
 *  net.minecraft.item.ItemPotion
 *  net.minecraft.item.ItemSnowball
 *  net.minecraft.item.ItemSpade
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.ItemSword
 *  net.minecraft.item.ItemTool
 */
package Abyss.module.impl.player;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.ChestStealerBinder;
import Abyss.event.events.PreUpdateEvent;
import Abyss.event.events.Render2DEvent;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.impl.configuration.Font;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.HeaderSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.util.BlockUtil;
import Abyss.util.ItemUtil;
import Abyss.util.MathUtil;
import Abyss.util.Pair;
import Abyss.util.ScoreboardUtil;
import Abyss.util.TimerUtil;
import Abyss.util.render.CustomFont;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public class ChestStealer
extends Module
implements EventSubscriber {
    private static long x;
    public static BooleanSetting projectiles;
    public static HeaderSetting itemsSettings;
    private static String[] h;
    public static BooleanSetting sword;
    public static NumberSetting startDelay;
    private static long[] o;
    public static BooleanSetting bow;
    public static BooleanSetting blocks;
    private static long d;
    private final TimerUtil p;
    public static NumberSetting minDelay;
    public static BooleanSetting ignoreTrash;
    public static BooleanSetting chestIntegrityCheck;
    public static BooleanSetting silent;
    public static BooleanSetting food;
    public static boolean y;
    public static BooleanSetting armor;
    private static String[] g;
    public static BooleanSetting potions;
    private List<Integer> D;
    public static BooleanSetting tools;
    public static NumberSetting maxDelay;
    public static BooleanSetting autoClose;
    private int stealState;
    private int stealIndex;
    private long stealNextActionAt;

    public ChestStealer(long var1) {
        super(d ^ var1 ^ 0x402C557AF98FL);
        this.declare("ChestStealer", Category.Player, "Steal items in the chest automatically", new Setting[0]);
        var1 = d ^ var1;
        this.p = new TimerUtil();
}
    private List z(long var1) {
        ArrayList<Integer> var5 = new ArrayList<Integer>();
        ContainerChest var6 = (ContainerChest)ChestStealer.f.thePlayer.openContainer;
        IInventory var7 = var6.getLowerChestInventory();
        ArrayList<Integer> var8 = new ArrayList<Integer>();
        for (int var9 = 0; var9 < var7.getSizeInventory(); ++var9) {
            ItemStack var10 = var7.getStackInSlot(var9);
            if (var10 == null || var10.getItem() == null || var5.contains(var9)) continue;
            Item var11 = var10.getItem();
            boolean var12 = this.y(var10, var9, var7);
            if (ignoreTrash.c() && var12) continue;
            if (armor.c() && var11 instanceof ItemArmor) {
                var5.add(var9);
            } else if (blocks.c() && ItemUtil.u(var10)) {
                var5.add(var9);
            } else if (!bow.c() || !(var11 instanceof ItemBow) && var11 != Items.arrow) {
                if (food.c() && var11 instanceof ItemFood) {
                    var5.add(var9);
                } else if (potions.c() && var11 instanceof ItemPotion) {
                    var5.add(var9);
                } else if (!(projectiles.c() && (var11 instanceof ItemEnderPearl || var11 instanceof ItemEgg || var11 instanceof ItemSnowball || var11 instanceof ItemFishingRod))) {
                    if (sword.c() && var11 instanceof ItemSword) {
                        var5.add(var9);
                    } else if (!tools.c() || !(var11 instanceof ItemPickaxe) && !(var11 instanceof ItemAxe) && !(var11 instanceof ItemSpade)) {
                        if (!ignoreTrash.c()) {
                            var5.add(var9);
}
                    } else {
                        var5.add(var9);
}
                } else {
                    var5.add(var9);
}
            } else {
                var5.add(var9);
}
            var8.add(var9);
}
        return var8.isEmpty() ? var5 : var5;
}
    public void onRender2D(long var1, Render2DEvent var3) {
        if (silent.c() && BlockUtil.o(chestIntegrityCheck.c()) && (ChestStealer.f.currentScreen instanceof GuiChest || y)) {
            CustomFont var10 = Font.s(0L);
            ScaledResolution var11 = var3.C;
            var10.T(37697014677608L, "Stealing...", (float)var11.getScaledWidth() / 2.0f - var10.R("Stealing...", 52019766876817L) / 2.0f, var11.getScaledHeight() / 2 + 75, 0xFFFFFF);
}
}
    @Override
    public void A(long var1) {
        this.resetStealState();
}
    @Override
    public final void x(long var1, EventBus var3) {
        ChestStealerBinder.W(var3, this);
}
    private boolean y(ItemStack var1, int var2, IInventory var5) {
        boolean var18 = false;
        Item var19 = var1.getItem();
        if (var19 instanceof ItemArmor) {
            if (((ItemArmor)var19).armorType == 0) {
                if (((Pair)ItemUtil.O(0L, var5).get(0)).p() != null && (Integer)((Pair)ItemUtil.O(0L, var5).get(0)).p() != var2) {
                    var18 = true;
}
            } else if (((ItemArmor)var19).armorType == 1) {
                if (((Pair)ItemUtil.O(0L, var5).get(1)).p() != null && (Integer)((Pair)ItemUtil.O(0L, var5).get(1)).p() != var2) {
                    var18 = true;
}
            } else if (((ItemArmor)var19).armorType == 2) {
                if (((Pair)ItemUtil.O(0L, var5).get(2)).p() != null && (Integer)((Pair)ItemUtil.O(0L, var5).get(2)).p() != var2) {
                    var18 = true;
}
            } else if (((ItemArmor)var19).armorType == 3 && ((Pair)ItemUtil.O(0L, var5).get(3)).p() != null && (Integer)((Pair)ItemUtil.O(0L, var5).get(3)).p() != var2) {
                var18 = true;
}
        } else if (var19 instanceof ItemSword) {
            if (ItemUtil.q(45121668772412L, var5).p() != null && (Integer)ItemUtil.q(45121668772412L, var5).p() != var2) {
                var18 = true;
}
        } else if (var19 instanceof ItemFood) {
            if (ItemUtil.k(var5).a().getItem() == Items.golden_apple && var19 != Items.golden_apple) {
                var18 = true;
}
        } else if (var19 instanceof ItemBow) {
            if (ItemUtil.O(var5).p() != null && ItemUtil.O(var5).p() != var2) {
                var18 = true;
}
        } else if (var19 instanceof ItemTool) {
            if (var19 instanceof ItemPickaxe) {
                if ((Integer)((Pair)ItemUtil.D(var5, (short)0).get(0)).p() != var2) {
                    var18 = true;
}
            } else if (var19 instanceof ItemAxe) {
                if ((Integer)((Pair)ItemUtil.D(var5, (short)0).get(1)).p() != var2) {
                    var18 = true;
}
            } else if (var19 instanceof ItemSpade && (Integer)((Pair)ItemUtil.D(var5, (short)0).get(2)).p() != var2) {
                var18 = true;
}
        } else if (var19 instanceof ItemFishingRod && ItemUtil.X((Item)Items.fishing_rod, var5)) {
            var18 = true;
}
        for (int var20 = 0; var20 < 40; ++var20) {
            ItemStack var21 = ChestStealer.f.thePlayer.inventory.getStackInSlot(var20);
            if (var21 == null) continue;
            if (var19 instanceof ItemArmor && var21.getItem() instanceof ItemArmor) {
                if (ItemUtil.M(var1) > ItemUtil.M(var21) || ((ItemArmor)var21.getItem()).armorType != ((ItemArmor)var19).armorType) continue;
                var18 = true;
                continue;
}
            if (var19 instanceof ItemSword && var21.getItem() instanceof ItemSword) {
                if (!(ItemUtil.p((short)0, var1, '\u1a4f') <= ItemUtil.p((short)0, var21, '\u1a4f'))) continue;
                var18 = true;
                continue;
}
            if (var19 instanceof ItemFood && var21.getItem() instanceof ItemFood) {
                if (ItemUtil.k((IInventory)ChestStealer.f.thePlayer.inventory).p() == null || ItemUtil.k((IInventory)ChestStealer.f.thePlayer.inventory).a().getItem() != Items.golden_apple || var19 == Items.golden_apple) continue;
                var18 = true;
                continue;
}
            if (var19 instanceof ItemBow && var21.getItem() instanceof ItemBow) {
                if (!(ItemUtil.b(var1) <= ItemUtil.b(var21))) continue;
                var18 = true;
                continue;
}
            if (!(var19 instanceof ItemTool) || !(var21.getItem() instanceof ItemTool)) continue;
            if (var19 instanceof ItemPickaxe && var21.getItem() instanceof ItemPickaxe) {
                if (((Pair)ItemUtil.D((IInventory)ChestStealer.f.thePlayer.inventory, (short)0).get(0)).p() == null || !(ItemUtil.Y(0L, var1) <= ItemUtil.Y(0L, var21))) continue;
                var18 = true;
                continue;
}
            if (var19 instanceof ItemAxe && var21.getItem() instanceof ItemAxe) {
                if (((Pair)ItemUtil.D((IInventory)ChestStealer.f.thePlayer.inventory, (short)0).get(1)).p() == null || !(ItemUtil.Y(0L, var1) <= ItemUtil.Y(0L, var21))) continue;
                var18 = true;
                continue;
}
            if (!(var19 instanceof ItemSpade) || !(var21.getItem() instanceof ItemSpade) || ((Pair)ItemUtil.D((IInventory)ChestStealer.f.thePlayer.inventory, (short)0).get(2)).p() == null || !(ItemUtil.Y(0L, var1) <= ItemUtil.Y(0L, var21))) continue;
            var18 = true;
}
        return var18;
}
    public void onPreUpdate(long var1, PreUpdateEvent var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (!(ChestStealer.f.thePlayer.openContainer instanceof ContainerChest)) {
            this.resetStealState();
            return;
}
        long now = System.currentTimeMillis();
        switch (this.stealState) {
            case 0: {
                if (!this.p.L(x, true)) {
                    return;
}
                if (chestIntegrityCheck.c()) {
                    if (ScoreboardUtil.x(0L)) {
                        return;
}
                    if (!BlockUtil.o(true)) {
                        return;
}
}
                this.D = this.z(0L);
                if (this.D.isEmpty() || ItemUtil.H(51268148435703L)) {
                    if (autoClose.c()) {
                        ChestStealer.f.thePlayer.closeScreen();
}
                    return;
}
                y = true;
                this.stealIndex = 0;
                this.stealNextActionAt = now + (long)startDelay.L();
                this.stealState = 1;
                break;
}
            case 1: {
                if (now < this.stealNextActionAt) {
                    return;
}
                this.stealState = 2;
                this.stealNextActionAt = now + (long)MathUtil.h(minDelay.L(), maxDelay.L());
}
            case 2: {
                if (now < this.stealNextActionAt) {
                    return;
}
                if (this.V(0L)) {
                    this.resetStealState();
                    return;
}
                if (this.stealIndex >= this.D.size()) {
                    if (autoClose.c()) {
                        ChestStealer.f.thePlayer.closeScreen();
}
                    this.resetStealState();
                    return;
}
                int slot = this.D.get(this.stealIndex);
                try {
                    ChestStealer.f.playerController.windowClick(ChestStealer.f.thePlayer.openContainer.windowId, slot, 0, 1, (EntityPlayer)ChestStealer.f.thePlayer);
}
                catch (Throwable throwable) {
                    // empty catch block
}
                ++this.stealIndex;
                this.stealNextActionAt = now + (long)MathUtil.h(minDelay.L(), maxDelay.L());
                if (this.stealIndex < this.D.size()) break;
                if (autoClose.c()) {
                    ChestStealer.f.thePlayer.closeScreen();
}
                this.resetStealState();
}
}
}
    private void resetStealState() {
        this.stealState = 0;
        this.stealIndex = 0;
        y = false;
}
    private boolean V(long var1) {
        if (this.o() && ChestStealer.f.thePlayer.openContainer instanceof ContainerChest) {
            return false;
}
        y = false;
        return true;
}
    @Override
    public String g(long var1) {
        return minDelay.L() == maxDelay.L() ? String.valueOf((int)minDelay.L()) : (int)minDelay.L() + "-" + (int)maxDelay.L();
}
    static {
        d = 28535189231375L;
        y = false;
        autoClose = new BooleanSetting("Auto-close", true);
        ignoreTrash = new BooleanSetting("Ignore-trash", true);
        chestIntegrityCheck = new BooleanSetting("Chest-integrity-check", true);
        silent = new BooleanSetting("Silent", false);
        armor = new BooleanSetting("Armor", true);
        blocks = new BooleanSetting("Blocks", true);
        bow = new BooleanSetting("Bow", false);
        food = new BooleanSetting("Food", true);
        potions = new BooleanSetting("Potions", false);
        projectiles = new BooleanSetting("Projectiles", true);
        sword = new BooleanSetting("Sword", true);
        tools = new BooleanSetting("Tools", false);
        startDelay = new NumberSetting("Start-delay", 50.0f, 0.0f, 1000.0f, 1.0f);
        minDelay = new NumberSetting("Min-delay", 50.0f, 0.0f, 1000.0f, 1.0f);
        maxDelay = new NumberSetting("Max-delay", 50.0f, 0.0f, 1000.0f, 1.0f);
        itemsSettings = new HeaderSetting("Items settings");
}
}