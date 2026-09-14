/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.client.gui.inventory.GuiInventory
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.inventory.InventoryCraftResult
 *  net.minecraft.inventory.InventoryCrafting
 *  net.minecraft.inventory.Slot
 *  net.minecraft.inventory.SlotCrafting
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C0DPacketCloseWindow
 *  net.minecraft.network.play.client.C0EPacketClickWindow
 */
package Abyss.module.impl.movement;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.InvMoveBinder;
import Abyss.event.events.CloseScreenEvent;
import Abyss.event.events.PostTickEvent;
import Abyss.event.events.PreUpdateEvent;
import Abyss.event.events.SendPacketEvent;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.Modules;
import Abyss.module.impl.movement.Sprint;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.ModeSetting;
import Abyss.ui.raven.RavenClickGuiScreen;
import Abyss.ui.studio.StudioClickGuiScreen;
import Abyss.util.KeyBindUtil;
import Abyss.util.MinecraftRef;
import Abyss.util.ScoreboardReader;
import Abyss.util.packet.PacketManager;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C0EPacketClickWindow;

public class InvMove
extends Module
implements EventSubscriber {
    public static ModeSetting containerMode;
        private boolean c;
    public static ModeSetting inventoryMode;
    public static BooleanSetting clickgui;
    private final List<Packet<?>> Y;

    public static void c(long var0) {
        KeyBinding[] var9;
        KeyBinding[] var10000 = new KeyBinding[]{MinecraftRef.c((byte)0, (long)0L).field_71474_y.field_74351_w, MinecraftRef.c((byte)0, (long)0L).field_71474_y.field_74368_y, MinecraftRef.c((byte)0, (long)0L).field_71474_y.field_74370_x, MinecraftRef.c((byte)0, (long)0L).field_71474_y.field_74366_z, MinecraftRef.c((byte)0, (long)0L).field_71474_y.field_74314_A, MinecraftRef.c((byte)0, (long)0L).field_71474_y.field_151444_V, MinecraftRef.c((byte)0, (long)0L).field_71474_y.field_74314_A};
        for (KeyBinding var13 : var9 = var10000) {
            KeyBindUtil.o(99363263780575L, var13.func_151463_i());
}
        if (Modules.J(Sprint.class).o()) {
            KeyBindUtil.A(82009306480869L, MinecraftRef.c((byte)0, (long)0L).field_71474_y.field_151444_V.func_151463_i(), true);
}
}
    public void onCloseScreen(CloseScreenEvent var1) {
        InvMove.c(0L);
        InvMove.f.field_71415_G = true;
}
    private boolean w$r3() {
        for (Slot var2 : InvMove.f.field_71439_g.field_71070_bA.field_75151_b) {
            ItemStack var3;
            if (!(var2 instanceof SlotCrafting) || (var3 = var2.func_75211_c()) == null) continue;
            return false;
}
        return InvMove.f.field_71439_g.field_71071_by.func_70445_o() == null;
}
    public void onSendPacket(SendPacketEvent var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (var3.B instanceof C0EPacketClickWindow) {
            C0EPacketClickWindow var13 = (C0EPacketClickWindow)var3.B;
            if (this.Z(var13.func_149544_d()) || !this.isGetItemStack()) {
                this.remove(false);
                InvMove.Q(0L);
                return;
}
            if (ScoreboardReader.v(0L)) {
                if (InvMove.f.field_71462_r instanceof GuiContainer && !(InvMove.f.field_71462_r instanceof GuiInventory)) {
                    if (containerMode.R("HYPIXEL")) {
                        this.Y.add((Packet<?>)var13);
                        var3.I(21307, 3074332907L);
                        return;
}
                } else if (InvMove.f.field_71462_r instanceof GuiInventory && inventoryMode.R("HYPIXEL")) {
                    this.Y.add((Packet<?>)var13);
                    var3.I(21307, 3074332907L);
                    return;
}
}
            this.c = true;
}
}
    private void remove(boolean var3) {
        if (!this.Y.isEmpty()) {
            ArrayList var6 = new ArrayList(this.Y);
            this.Y.clear();
            for (Packet packet : var6) {
                PacketManager.X(packet);
}
}
        if (var3) {
            PacketManager.b(new C0DPacketCloseWindow(InvMove.f.field_71439_g.field_71069_bz.field_75152_c));
}
}
    private boolean d$r2() {
        if (InvMove.f.field_71439_g != null && InvMove.f.field_71439_g.field_71070_bA != null) {
            for (Slot var2 : InvMove.f.field_71439_g.field_71070_bA.field_75151_b) {
                ItemStack var4;
                IInventory var3;
                if (var2 == null || !((var3 = var2.field_75224_c) instanceof InventoryCrafting) && !(var3 instanceof InventoryCraftResult) && !(var2 instanceof SlotCrafting) || (var4 = var2.func_75211_c()) == null) continue;
                return true;
}
            return false;
}
        return false;
}
    public void onPostTick(PostTickEvent var3) {
        if (!this.Y.isEmpty()) {
            this.remove(this.isGetItemStack());
}
}
    private boolean Z(int var1) {
        if (InvMove.f.field_71439_g.field_71070_bA == null) {
            return false;
}
        if (var1 >= 0 && var1 < InvMove.f.field_71439_g.field_71070_bA.field_75151_b.size()) {
            Slot var2 = (Slot)InvMove.f.field_71439_g.field_71070_bA.field_75151_b.get(var1);
            if (var2 == null) {
                return false;
}
            IInventory var3 = var2.field_75224_c;
            return var3 instanceof InventoryCrafting || var3 instanceof InventoryCraftResult;
}
        return false;
}
    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void onPreUpdate(PreUpdateEvent var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (InvMove.f.field_71462_r == null) {
            this.c = false;
}
        if (!clickgui.c() || !(InvMove.f.field_71462_r instanceof RavenClickGuiScreen) && !(InvMove.f.field_71462_r instanceof StudioClickGuiScreen)) {
            if (InvMove.f.field_71462_r == null) return;
            if (InvMove.f.field_71462_r instanceof GuiContainer && !(InvMove.f.field_71462_r instanceof GuiInventory)) {
                switch (containerMode.Y()) {
                    case "LEGIT": {
                        if (this.w$r3() && !this.c) {
                            InvMove.c(0L);
                            return;
}
                        InvMove.Q(0L);
                        return;
}
                    case "VANILLA": {
                        InvMove.c(0L);
                        return;
}
                    case "HYPIXEL": {
                        if (!ScoreboardReader.v(0L)) return;
                        if (this.isGetItemStack()) {
                            InvMove.c(0L);
                            return;
}
                        InvMove.Q(0L);
                        return;
}
                    default: {
                        InvMove.Q(0L);
                        return;
}
}
            } else {
                if (!(InvMove.f.field_71462_r instanceof GuiInventory)) return;
                switch (inventoryMode.Y()) {
                    case "LEGIT": {
                        if (this.w$r3() && !this.c) {
                            InvMove.c(0L);
                            return;
}
                        InvMove.Q(0L);
                        return;
}
                    case "VANILLA": {
                        InvMove.c(0L);
                        return;
}
                    case "HYPIXEL": {
                        if (!ScoreboardReader.v(0L)) return;
                        if (this.isGetItemStack()) {
                            InvMove.c(0L);
                            return;
}
                        InvMove.Q(0L);
                        return;
}
                    default: {
                        InvMove.Q(0L);
                        return;
}
}
}
        } else {
            InvMove.c(0L);
}
}
    public InvMove(long var1) {
        super(a ^ var1 ^ 0x64D83A8415ACL);
        this.declare("InvMove", Category.Movement, "Allows you to move around while opening a container", new Setting[0]);
        var1 = a ^ var1;
        this.Y = new CopyOnWriteArrayList();
        this.c = false;
}
    @Override
    public final void x(long var1, EventBus var3) {
        InvMoveBinder.I(var3, this);
}
    public static void Q(long var0) {
        KeyBinding[] var7;
        KeyBinding[] var10000 = new KeyBinding[]{MinecraftRef.c((byte)0, (long)0L).field_71474_y.field_74351_w, MinecraftRef.c((byte)0, (long)0L).field_71474_y.field_74368_y, MinecraftRef.c((byte)0, (long)0L).field_71474_y.field_74370_x, MinecraftRef.c((byte)0, (long)0L).field_71474_y.field_74366_z, MinecraftRef.c((byte)0, (long)0L).field_71474_y.field_74314_A, MinecraftRef.c((byte)0, (long)0L).field_71474_y.field_151444_V, MinecraftRef.c((byte)0, (long)0L).field_71474_y.field_74314_A};
        for (KeyBinding var11 : var7 = var10000) {
            KeyBindUtil.A(82009306480869L, var11.func_151463_i(), false);
}
}
    @Override
    public void A(long var1) {
        this.c = false;
}
    private boolean isGetItemStack() {
        return InvMove.f.field_71439_g != null && InvMove.f.field_71439_g.field_71070_bA != null && InvMove.f.field_71439_g.field_71071_by.func_70445_o() == null && !this.d$r2();
}
    static {
        clickgui = new BooleanSetting("ClickGUI", true);
        containerMode = new ModeSetting("Container-mode", "LEGIT", "HYPIXEL", "VANILLA", "NONE");
        inventoryMode = new ModeSetting("Inventory-mode", "LEGIT", "HYPIXEL", "VANILLA", "NONE");
}
}