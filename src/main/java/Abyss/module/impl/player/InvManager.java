/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.inventory.GuiInventory
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.item.ItemArmor
 *  net.minecraft.item.ItemAxe
 *  net.minecraft.item.ItemBow
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
 *  net.minecraft.network.play.client.C0DPacketCloseWindow
 *  net.minecraft.network.play.client.C16PacketClientStatus
 *  net.minecraft.network.play.client.C16PacketClientStatus$EnumState
 *  org.lwjgl.input.Keyboard
 */
package Abyss.module.impl.player;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.InvManagerBinder;
import Abyss.event.events.DrawScreenEvent;
import Abyss.event.events.PickUpItemEvent;
import Abyss.event.events.PreUpdateEvent;
import Abyss.module.Category;
import Abyss.module.PriorityModule;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.HeaderSetting;
import Abyss.setting.settings.ModeSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.setting.settings.TextSetting;
import Abyss.util.ItemUtil;
import Abyss.util.KeyBindUtil;
import Abyss.util.MathUtil;
import Abyss.util.Pair;
import Abyss.util.TimerUtil;
import Abyss.util.packet.PacketManager;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBow;
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
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;
import org.lwjgl.input.Keyboard;

public class InvManager
extends PriorityModule
implements EventSubscriber {
    private static Map hb;
    private static long ob;
    public static NumberSetting maxTrashThrows;
    public static NumberSetting projectilesSlot;
    public static NumberSetting shovelSlot;
    private static String[] bb;
    public static TextSetting silentKey;
    private static long R;
    public static NumberSetting axeSlot;
    public static HeaderSetting trashSettings;
    private boolean J;
    public static BooleanSetting projectilesIsTrash;
    public static NumberSetting swordSlot;
    public static NumberSetting maxArrowSlots;
    public static HeaderSetting slotsSettings;
    private static Map nb;
    public static BooleanSetting potionIsTrash;
    public static BooleanSetting normalFoodIsTrash;
    public static BooleanSetting onlySortOnce;
    private static String[] gb;
    public static NumberSetting startDelay;
    private static long[] ib;
    public static BooleanSetting onlyItemsConfiguredAreTrash;
    public static NumberSetting minDelay;
    private final TimerUtil o;
    public static NumberSetting enderPearlSlot;
    public static NumberSetting shearsSlot;
    public static NumberSetting pickaxeSlot;
    public static NumberSetting blockSlot;
    public static NumberSetting bowSlot;
    public static BooleanSetting toolsAreTrash;
    public static ModeSetting silentMode;
    public static NumberSetting fireballSlot;
    public static HeaderSetting maxItemSlotsSettings;
    private boolean L;
    public static NumberSetting foodSlot;
    public static BooleanSetting autoClose;
    public static BooleanSetting bowIsTrash;
    public static NumberSetting maxBlockSlots;
    public static NumberSetting potionSlot;
    public static BooleanSetting throwTrash;
    public static ModeSetting mode;
    public static BooleanSetting autoArmor;
    public static NumberSetting maxDelay;
    private boolean machineRunning;
    private int machinePhase;
    private long machineNextActionAt;
    private int machineArmorStageType = -1;
    private int machineArmorStageSlot;
    private int machineTrashSlot;
    private int machineThrown;
    private int machineBlocks;
    private int machineArrows;
    private List machineBest;

    private void r(char var1, int var2) {
        if (this.machineRunning) {
            return;
}
        this.machineRunning = true;
        this.T(true);
        if (onlySortOnce.c()) {
            this.L = true;
}
        this.machinePhase = 0;
        this.machineNextActionAt = System.currentTimeMillis() + (long)startDelay.L();
        this.machineArmorStageType = -1;
        this.machineArmorStageSlot = -1;
        this.machineTrashSlot = 0;
        this.machineThrown = 0;
        this.machineBlocks = 0;
        this.machineArrows = 0;
        this.machineBest = null;
}
    private void finishMachine() {
        this.machineRunning = false;
        this.J = false;
        this.closeScreen();
        this.T(false);
}
    private void pumpMachine() {
        if (!this.machineRunning) {
            return;
}
        int guard = 0;
        while (guard++ < 64) {
            if (this.m(0L)) {
                this.finishMachine();
                return;
}
            if (this.machinePhase == 0) {
                if (System.currentTimeMillis() < this.machineNextActionAt) {
                    return;
}
                this.machinePhase = 1;
                this.machineBest = ItemUtil.O(0L, (IInventory)InvManager.f.field_71439_g.field_71071_by);
                continue;
}
            int acted = this.stepMachine();
            if (acted == 1) {
                long delay = (long)MathUtil.h(minDelay.L(), maxDelay.L());
                if (delay <= 0L) continue;
                this.machineNextActionAt = System.currentTimeMillis() + delay;
                return;
}
            if (acted == 2) {
                this.finishMachine();
                return;
}
            return;
}
}
    private int stepMachine() {
        if (this.machinePhase == 1) {
            int acted = this.stepArmor();
            if (acted != 0) {
                return acted;
}
            this.machinePhase = 2;
            return 0;
}
        if (this.machinePhase == 2) {
            int acted = this.stepSlots();
            if (acted != 0) {
                return acted;
}
            this.machinePhase = 3;
            this.machineTrashSlot = 0;
            this.machineThrown = 0;
            this.machineBlocks = 0;
            this.machineArrows = 0;
            return 0;
}
        if (this.machinePhase == 3) {
            if (!throwTrash.c()) {
                return 2;
}
            return this.stepTrash();
}
        return 2;
}
    private int stepArmor() {
        if (!autoArmor.c()) {
            return 0;
}
        if (this.machineArmorStageType >= 0) {
            int slot = this.machineArmorStageSlot;
            this.machineArmorStageType = -1;
            this.machineArmorStageSlot = -1;
            ItemUtil.B(0L, slot);
            return 1;
}
        if (this.machineBest == null) {
            return 0;
}
        for (int type = 0; type < 4; ++type) {
            int target = 39 - type;
            Pair best = (Pair)this.machineBest.get(type);
            if (best == null || best.p() == null || (Integer)best.p() == target || ItemUtil.M((ItemStack)best.a()) <= ItemUtil.M(InvManager.f.field_71439_g.field_71071_by.func_70301_a(target))) continue;
            if (InvManager.f.field_71439_g.func_71124_b(4 - type) != null) {
                ItemUtil.c(8537, 12546, '\uab5c', target);
                this.machineArmorStageType = type;
                this.machineArmorStageSlot = (Integer)best.p();
                return 1;
}
            ItemUtil.B(0L, (Integer)best.p());
            return 1;
}
        return 0;
}
    private void s(int var1, short var2, int var3) {
        long var4 = ((long)var1 << 32 | (long)var2 << 48 >>> 32 | (long)var3 << 48 >>> 48) ^ R;
        int var6 = (int)((var4 ^ 0x780D1040BAC9L) >>> 48);
        int var7 = (int)((var4 ^ 0x780D1040BAC9L) << 16 >>> 32);
        if (!this.Y()) {
            this.T(false);
        } else if (!this.J && !this.L) {
            this.J = true;
            this.r((char)var6, var7);
}
}
    @Override
    public final void x(long var1, EventBus var3) {
        InvManagerBinder.L(var3, this);
}
    private void closeScreen() {
        if (autoClose.c()) {
            if (InvManager.f.field_71462_r != null) {
                InvManager.f.field_71439_g.func_71053_j();
            } else if (mode.R("SILENT")) {
                PacketManager.b(new C0DPacketCloseWindow(InvManager.f.field_71439_g.field_71069_bz.field_75152_c));
}
}
        this.T(false);
}
    private int stepSlots() {
        long var4 = 0x2312F840ADEBL ^ R;
        int var10 = (int)((var4 ^ 0x3CA5B540D4L) >>> 48);
        long var13 = var4 ^ 0x86C5A8BF0C0L;
        if (swordSlot.L() != 0.0f && ItemUtil.q(var13, (IInventory)InvManager.f.field_71439_g.field_71071_by).p() != null && (int)swordSlot.L() - 1 != (Integer)ItemUtil.q(var13, (IInventory)InvManager.f.field_71439_g.field_71071_by).p()) {
            ItemUtil.Q(0L, (Integer)ItemUtil.q(var13, (IInventory)InvManager.f.field_71439_g.field_71071_by).p(), (int)swordSlot.L() - 37);
            return 1;
}
        if (projectilesSlot.L() != 0.0f && !projectilesIsTrash.c() && ItemUtil.o((IInventory)InvManager.f.field_71439_g.field_71071_by).p() != null && (int)projectilesSlot.L() - 1 != ItemUtil.o((IInventory)InvManager.f.field_71439_g.field_71071_by).p()) {
            ItemUtil.Q(0L, ItemUtil.o((IInventory)InvManager.f.field_71439_g.field_71071_by).p(), (int)projectilesSlot.L() - 37);
            return 1;
}
        if (blockSlot.L() != 0.0f && ItemUtil.Y((IInventory)InvManager.f.field_71439_g.field_71071_by).p() != null && (int)blockSlot.L() - 1 != ItemUtil.Y((IInventory)InvManager.f.field_71439_g.field_71071_by).p()) {
            ItemUtil.Q(0L, ItemUtil.Y((IInventory)InvManager.f.field_71439_g.field_71071_by).p(), (int)blockSlot.L() - 37);
            return 1;
}
        if (bowSlot.L() != 0.0f && !bowIsTrash.c() && ItemUtil.O((IInventory)InvManager.f.field_71439_g.field_71071_by).p() != null && (int)bowSlot.L() - 1 != ItemUtil.O((IInventory)InvManager.f.field_71439_g.field_71071_by).p()) {
            ItemUtil.Q(0L, ItemUtil.O((IInventory)InvManager.f.field_71439_g.field_71071_by).p(), (int)bowSlot.L() - 37);
            return 1;
}
        if (!toolsAreTrash.c()) {
            List var15 = ItemUtil.D((IInventory)InvManager.f.field_71439_g.field_71071_by, (short)var10);
            if (pickaxeSlot.L() != 0.0f && ((Pair)var15.get(0)).p() != null && (int)pickaxeSlot.L() - 1 != (Integer)((Pair)var15.get(0)).p()) {
                ItemUtil.Q(0L, (Integer)((Pair)var15.get(0)).p(), (int)pickaxeSlot.L() - 37);
                return 1;
}
            if (axeSlot.L() != 0.0f && ((Pair)var15.get(1)).p() != null && (int)axeSlot.L() - 1 != (Integer)((Pair)var15.get(1)).p()) {
                ItemUtil.Q(0L, (Integer)((Pair)var15.get(1)).p(), (int)axeSlot.L() - 37);
                return 1;
}
            if (shovelSlot.L() != 0.0f && ((Pair)var15.get(2)).p() != null && (int)shovelSlot.L() - 1 != (Integer)((Pair)var15.get(2)).p()) {
                ItemUtil.Q(0L, (Integer)((Pair)var15.get(2)).p(), (int)shovelSlot.L() - 37);
                return 1;
}
}
        if (!(foodSlot.L() == 0.0f || ItemUtil.k((IInventory)InvManager.f.field_71439_g.field_71071_by).p() == null || (int)foodSlot.L() - 1 == ItemUtil.k((IInventory)InvManager.f.field_71439_g.field_71071_by).p() || normalFoodIsTrash.c() && ItemUtil.k((IInventory)InvManager.f.field_71439_g.field_71071_by).a().func_77973_b() != Items.field_151153_ao)) {
            ItemUtil.Q(0L, ItemUtil.k((IInventory)InvManager.f.field_71439_g.field_71071_by).p(), (int)foodSlot.L() - 37);
            return 1;
}
        if (potionSlot.L() != 0.0f && !potionIsTrash.c() && ItemUtil.H((IInventory)InvManager.f.field_71439_g.field_71071_by).p() != null && (int)potionSlot.L() - 1 != ItemUtil.H((IInventory)InvManager.f.field_71439_g.field_71071_by).p()) {
            ItemUtil.Q(0L, ItemUtil.H((IInventory)InvManager.f.field_71439_g.field_71071_by).p(), (int)potionSlot.L() - 37);
            return 1;
}
        if (fireballSlot.L() != 0.0f && ItemUtil.i((IInventory)InvManager.f.field_71439_g.field_71071_by).p() != null && (int)fireballSlot.L() - 1 != ItemUtil.i((IInventory)InvManager.f.field_71439_g.field_71071_by).p()) {
            ItemUtil.Q(0L, ItemUtil.i((IInventory)InvManager.f.field_71439_g.field_71071_by).p(), (int)fireballSlot.L() - 37);
            return 1;
}
        if (enderPearlSlot.L() != 0.0f && ItemUtil.F((IInventory)InvManager.f.field_71439_g.field_71071_by).p() != null && (int)enderPearlSlot.L() - 1 != ItemUtil.F((IInventory)InvManager.f.field_71439_g.field_71071_by).p()) {
            ItemUtil.Q(0L, ItemUtil.F((IInventory)InvManager.f.field_71439_g.field_71071_by).p(), (int)enderPearlSlot.L() - 37);
            return 1;
}
        if (shearsSlot.L() != 0.0f && ItemUtil.W((IInventory)InvManager.f.field_71439_g.field_71071_by).p() != null && (int)shearsSlot.L() - 1 != ItemUtil.W((IInventory)InvManager.f.field_71439_g.field_71071_by).p()) {
            ItemUtil.Q(0L, ItemUtil.W((IInventory)InvManager.f.field_71439_g.field_71071_by).p(), (int)shearsSlot.L() - 37);
            return 1;
}
        return 0;
}
    private int stepTrash() {
        while (this.machineTrashSlot < InvManager.f.field_71439_g.field_71071_by.func_70302_i_()) {
            int slot;
            ItemStack stack;
            if ((stack = InvManager.f.field_71439_g.field_71071_by.func_70301_a(slot = this.machineTrashSlot++)) == null) continue;
            this.machineBest = ItemUtil.O(0L, (IInventory)InvManager.f.field_71439_g.field_71071_by);
            int acted = this.stepArmor();
            if (acted != 0) {
                return acted;
}
            acted = this.stepSlots();
            if (acted != 0) {
                return acted;
}
            if ((float)this.machineThrown >= maxTrashThrows.L()) {
                return 2;
}
            this.machineBest = ItemUtil.O(0L, (IInventory)InvManager.f.field_71439_g.field_71071_by);
            if (slot == InvManager.f.field_71439_g.field_71071_by.field_70461_c) {
                ++this.machineTrashSlot;
                continue;
}
            int threw = this.trashAction(stack, slot);
            ++this.machineTrashSlot;
            if (threw == 0) continue;
            return 1;
}
        return 2;
}
    /*
     * Enabled aggressive block sorting
     */
    private int trashAction(ItemStack var28, int var27) {
        char var7 = '\uab5c';
        if (var28.func_77973_b() instanceof ItemArmor && ((ItemArmor)var28.func_77973_b()).field_77881_a == 0) {
            if (ItemUtil.M(var28) > ItemUtil.M((ItemStack)((Pair)this.machineBest.get(0)).a())) return 0;
            if (var27 == 39) return 0;
            ItemUtil.c(8537, 12546, var7, var27);
            ++this.machineThrown;
            return 1;
}
        if (var28.func_77973_b() instanceof ItemArmor && ((ItemArmor)var28.func_77973_b()).field_77881_a == 1) {
            if (ItemUtil.M(var28) > ItemUtil.M((ItemStack)((Pair)this.machineBest.get(1)).a())) return 0;
            if (var27 == 38) return 0;
            ItemUtil.c(8537, 12546, var7, var27);
            ++this.machineThrown;
            return 1;
}
        if (var28.func_77973_b() instanceof ItemArmor && ((ItemArmor)var28.func_77973_b()).field_77881_a == 2) {
            if (ItemUtil.M(var28) > ItemUtil.M((ItemStack)((Pair)this.machineBest.get(2)).a())) return 0;
            if (var27 == 37) return 0;
            ItemUtil.c(8537, 12546, var7, var27);
            ++this.machineThrown;
            return 1;
}
        if (var28.func_77973_b() instanceof ItemArmor && ((ItemArmor)var28.func_77973_b()).field_77881_a == 3) {
            if (ItemUtil.M(var28) > ItemUtil.M((ItemStack)((Pair)this.machineBest.get(3)).a())) return 0;
            if (var27 == 36) return 0;
            ItemUtil.c(8537, 12546, var7, var27);
            ++this.machineThrown;
            return 1;
}
        if (var28.func_77973_b() instanceof ItemSword && (Integer)ItemUtil.q(45121668772412L, (IInventory)InvManager.f.field_71439_g.field_71071_by).p() != var27) {
            ItemUtil.c(8537, 12546, var7, var27);
            ++this.machineThrown;
            return 1;
}
        if ((var28.func_77973_b() instanceof ItemSnowball || var28.func_77973_b() instanceof ItemEgg || var28.func_77973_b() instanceof ItemFishingRod || var28.func_77973_b() instanceof ItemEnderPearl) && projectilesIsTrash.c()) {
            ItemUtil.c(8537, 12546, var7, var27);
            ++this.machineThrown;
            return 1;
}
        if (!(var28.func_77973_b() instanceof ItemBow && ItemUtil.O((IInventory)InvManager.f.field_71439_g.field_71071_by).p() != null && ItemUtil.O((IInventory)InvManager.f.field_71439_g.field_71071_by).p() != var27 || bowIsTrash.c() && (var28.func_77973_b() instanceof ItemBow || var28.func_77973_b() == Items.field_151032_g))) {
            if (!(var28.func_77973_b() instanceof ItemFood) || (!normalFoodIsTrash.c() || var28.func_77973_b() == Items.field_151153_ao) && ItemUtil.k((IInventory)InvManager.f.field_71439_g.field_71071_by).p() == var27) {
                if (potionIsTrash.c() && var28.func_77973_b() instanceof ItemPotion) {
                    ItemUtil.c(8537, 12546, var7, var27);
                    ++this.machineThrown;
                    return 1;
}
                if (var28.func_77973_b() instanceof ItemTool) {
                    if (toolsAreTrash.c()) {
                        ItemUtil.c(8537, 12546, var7, var27);
                        ++this.machineThrown;
                        return 1;
}
                    if (var28.func_77973_b() instanceof ItemPickaxe) {
                        if ((Integer)((Pair)ItemUtil.D((IInventory)InvManager.f.field_71439_g.field_71071_by, (short)0).get(0)).p() == var27) return 0;
                        ItemUtil.c(8537, 12546, var7, var27);
                        ++this.machineThrown;
                        return 1;
}
                    if (var28.func_77973_b() instanceof ItemAxe) {
                        if ((Integer)((Pair)ItemUtil.D((IInventory)InvManager.f.field_71439_g.field_71071_by, (short)0).get(1)).p() == var27) return 0;
                        ItemUtil.c(8537, 12546, var7, var27);
                        ++this.machineThrown;
                        return 1;
}
                    if (!(var28.func_77973_b() instanceof ItemSpade)) return 0;
                    if ((Integer)((Pair)ItemUtil.D((IInventory)InvManager.f.field_71439_g.field_71071_by, (short)0).get(2)).p() == var27) return 0;
                    ItemUtil.c(8537, 12546, var7, var27);
                    ++this.machineThrown;
                    return 1;
}
                if (var28.func_77973_b() instanceof ItemShears) {
                    if (ItemUtil.W((IInventory)InvManager.f.field_71439_g.field_71071_by).p() == var27) return 0;
                    ItemUtil.c(8537, 12546, var7, var27);
                    ++this.machineThrown;
                    return 1;
}
                if (ItemUtil.u(var28)) {
                    if ((float)this.machineBlocks >= maxBlockSlots.L()) {
                        ItemUtil.c(8537, 12546, var7, var27);
                        ++this.machineBlocks;
                        ++this.machineThrown;
                        return 1;
}
                    ++this.machineBlocks;
                    ++this.machineThrown;
                    return 0;
}
                if (var28.func_77973_b() != Items.field_151032_g) {
                    if (onlyItemsConfiguredAreTrash.c()) return 0;
                    if (var28.func_77973_b() instanceof ItemPotion) return 0;
                    if (var28.func_77973_b() == Items.field_151032_g) return 0;
                    if (var28.func_77973_b() instanceof ItemTool) return 0;
                    if (var28.func_77973_b() instanceof ItemShears) return 0;
                    if (var28.func_77973_b() instanceof ItemSword) return 0;
                    if (var28.func_77973_b() instanceof ItemFood) return 0;
                    if (var28.func_77973_b() instanceof ItemBow) return 0;
                    if (var28.func_77973_b() instanceof ItemArmor) return 0;
                    if (ItemUtil.u(var28)) return 0;
                    if (var28.func_77973_b() instanceof ItemSnowball) return 0;
                    if (var28.func_77973_b() instanceof ItemEgg) return 0;
                    if (var28.func_77973_b() instanceof ItemFishingRod) return 0;
                    if (var28.func_77973_b() instanceof ItemEnderPearl) return 0;
                    if (var28.func_77973_b() instanceof ItemFireball) return 0;
                    ItemUtil.c(8537, 12546, var7, var27);
                    ++this.machineThrown;
                    return 1;
}
                if ((float)this.machineArrows >= maxArrowSlots.L()) {
                    ItemUtil.c(8537, 12546, var7, var27);
                    ++this.machineArrows;
                    ++this.machineThrown;
                    return 1;
}
                ++this.machineArrows;
                ++this.machineThrown;
                return 0;
}
            ItemUtil.c(8537, 12546, var7, var27);
            ++this.machineThrown;
            return 1;
}
        ItemUtil.c(8537, 12546, var7, var27);
        ++this.machineThrown;
        return 1;
}
    public void onPreUpdate(PreUpdateEvent var1, long var2) {
        this.pumpMachine();
        if (!(InvManager.f.field_71462_r instanceof GuiInventory)) {
            this.L = false;
}
        if (InvManager.f.field_71462_r == null && mode.R("SILENT") && silentMode.R("KEY") && KeyBindUtil.V(Keyboard.getKeyIndex((String)silentKey.X().toUpperCase()), 64165991731362L)) {
            PacketManager.b(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
            f.func_147108_a((GuiScreen)new GuiInventory((EntityPlayer)InvManager.f.field_71439_g));
            this.s(23305, (short)32017, 51151);
        } else {
            switch (mode.Y()) {
                case "SILENT": {
                    if (!silentMode.R("OPEN_INV")) break;
                    if (InvManager.f.field_71462_r instanceof GuiInventory) {
                        this.s(23305, (short)32017, 51151);
                        break;
}
                    this.J = false;
                    break;
}
                case "OPEN_INV": {
                    if (InvManager.f.field_71462_r instanceof GuiInventory) {
                        this.s(23305, (short)32017, 51151);
                        break;
}
                    this.J = false;
}
}
}
}
    @Override
    public String g(long var1) {
        if (startDelay.L() == 0.0f && minDelay.L() == 0.0f && maxDelay.L() == 0.0f) {
            return "INSTANT";
}
        return minDelay.L() == maxDelay.L() ? String.valueOf((int)minDelay.L()) : (int)minDelay.L() + "-" + (int)maxDelay.L();
}
    @Override
    public void d() {
        this.pumpMachine();
}
    public void onDrawScreen(char var1, int var2, int var3, DrawScreenEvent var4) {
        long var5 = ((long)var1 << 48 | (long)var2 << 32 >>> 16 | (long)var3 << 48 >>> 48) ^ R;
        int var7 = (int)((var5 ^ 0x5AAC2C024CF6L) >>> 32);
        long var8 = (var5 ^ 0x5AAC2C024CF6L) << 32 >>> 32;
        if (InvManager.f.field_71462_r instanceof GuiInventory && mode.R("SILENT") && this.J) {
            var4.I(var7, var8);
}
}
    public void onPickUpItem(PickUpItemEvent var1, long var2) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (var1.F instanceof EntityPlayerSP && var1.P != null && mode.R("SILENT") && silentMode.R("PICK_ITEM")) {
            PacketManager.b(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
            f.func_147108_a((GuiScreen)new GuiInventory((EntityPlayer)InvManager.f.field_71439_g));
            this.s(23305, (short)32017, 51151);
}
}
    public InvManager(long var1) {
        super((R ^ var1 ^ 0x295F63674FBAL) >>> 16, (char)((R ^ var1 ^ 0x295F63674FBAL) << 48 >>> 48));
        this.declare("InvManager", Category.Player, "Clean and manage your inventory", new Setting[0]);
        var1 = R ^ var1;
        this.o = new TimerUtil();
        this.J = false;
        this.L = false;
}
    private boolean m(long var1) {
        if (InvManager.f.field_71462_r instanceof GuiInventory && this.o() && !KeyBindUtil.V(1, 64165991731362L)) {
            return false;
}
        this.J = false;
        this.T(false);
        return true;
}
    static {
        R = 2710522998551L;
        hb = new HashMap(13);
        bb = new String[]{"\u001d^\u009bM\u001b\u00b0\u00aad", "v\u00b2c2N\u00c9 \u008e\u00e7\u0093\u00cb\u00a0T\u00f9\u00b5\u00f3", "NGs\u00119\u00df\u00c6\u00ac", "\u00d4{V\u001f\u0092+\u001a\u00f9", "\u008d\f\u00bb@(\u00eb\nd", "\u00e8`wKD\u0082_$\u00f4:\u00db\u00fa\u00d2H\u00a2\u00f1", "_\u00cb\u00c3\u00bd\u00cdC\u00ab$\u000e\u008c\u00c5\u0080\u00a5\u00ebp-"};
        gb = new String[7];
        nb = new HashMap(13);
        ib = new long[]{-2670471100074826616L, -5966574634042417658L, -7277742583667841876L, -3369840771532992298L, -2466407860972894568L, -3491299397836251658L, -2251256513271661673L, -4997704363658310359L, 5609941588006067246L, 624057533236308340L, 7343629218183677455L, 4974517030527135104L};
        ob = 10L;
        fireballSlot = new NumberSetting("Fireball-slot", 0.0f, 0.0f, 9.0f, 1.0f);
        trashSettings = new HeaderSetting("Trash settings");
        slotsSettings = new HeaderSetting("Slots settings (0 = no sort)");
        maxItemSlotsSettings = new HeaderSetting("Max item slots settings");
        pickaxeSlot = new NumberSetting("Pickaxe-slot", 5.0f, 0.0f, 9.0f, 1.0f);
        shovelSlot = new NumberSetting("Shovel-slot", 7.0f, 0.0f, 9.0f, 1.0f);
        normalFoodIsTrash = new BooleanSetting("Normal-food-is-trash", true);
        maxArrowSlots = new NumberSetting("Max-arrow-slots", 10.0f, 1.0f, 36.0f, 1.0f);
        projectilesIsTrash = new BooleanSetting("Projectiles-is-trash", false);
        autoArmor = new BooleanSetting("Auto-armor", true);
        blockSlot = new NumberSetting("Block-slot", 2.0f, 0.0f, 9.0f, 1.0f);
        foodSlot = new NumberSetting("Food-slot", 9.0f, 0.0f, 9.0f, 1.0f);
        maxDelay = new NumberSetting("Max-delay", 50.0f, 0.0f, 1000.0f, 1.0f);
        onlyItemsConfiguredAreTrash = new BooleanSetting("Only-items-configured-are-trash", false);
        autoClose = new BooleanSetting("Auto-close", false);
        swordSlot = new NumberSetting("Sword-slot", 1.0f, 0.0f, 9.0f, 1.0f);
        minDelay = new NumberSetting("Min-delay", 50.0f, 0.0f, 1000.0f, 1.0f);
        enderPearlSlot = new NumberSetting("Ender-pearl-slot", 0.0f, 0.0f, 9.0f, 1.0f);
        toolsAreTrash = new BooleanSetting("Tools-are-trash", true);
        onlySortOnce = new BooleanSetting("Only-sort-once", false);
        maxBlockSlots = new NumberSetting("Max-block-slots", 10.0f, 1.0f, 36.0f, 1.0f);
        potionSlot = new NumberSetting("Potion-slot", 8.0f, 0.0f, 9.0f, 1.0f);
        bowIsTrash = new BooleanSetting("Bow-is-trash", true);
        potionIsTrash = new BooleanSetting("Potion-is-trash", true);
        mode = new ModeSetting("Mode", "OPEN_INV", "SILENT");
        silentKey = new TextSetting("Silent-key", "NONE");
        projectilesSlot = new NumberSetting("Projectiles-slot", 3.0f, 0.0f, 9.0f, 1.0f);
        axeSlot = new NumberSetting("Axe-slot", 6.0f, 0.0f, 9.0f, 1.0f);
        shearsSlot = new NumberSetting("Shears-slot", 0.0f, 0.0f, 9.0f, 1.0f);
        bowSlot = new NumberSetting("Bow-slot", 4.0f, 0.0f, 9.0f, 1.0f);
        silentMode = new ModeSetting("Silent-mode", "KEY", "PICK_ITEM", "OPEN_INV");
        maxTrashThrows = new NumberSetting("Max-trash-throws", 36.0f, 1.0f, 36.0f, 1.0f);
        throwTrash = new BooleanSetting("Throw-trash", true);
        startDelay = new NumberSetting("Start-delay", 50.0f, 0.0f, 1000.0f, 1.0f);
}
}