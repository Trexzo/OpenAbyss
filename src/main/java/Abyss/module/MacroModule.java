/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemPotion
 *  net.minecraft.item.ItemStack
 */
package Abyss.module;

import Abyss.internal.accessor.MethodAccessors;
import Abyss.module.Module;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.ModeSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.setting.settings.TextSetting;
import Abyss.util.ItemUtil;
import Abyss.util.KeyBindUtil;
import java.util.Objects;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;

public class MacroModule
extends Module {
    private static long k = 100029549804245L;

    private boolean Y = false;
    private boolean J = false;
    private int U = -1;
    private boolean M = false;
    private int g = 0;
    private int D = 0;

    private void O(char var1, ModeSetting var2, BooleanSetting var3, int var4, short var5) {
        long var6 = ((long)var1 << 48 | (long)var4 << 32 >>> 16 | (long)var5 << 48 >>> 48) ^ k;
        long var8 = var6 ^ 0x746F6E4515F1L;
        if (var2.R("ROD")) {
            KeyBindUtil.h(var8);
}
        if (var3.c()) {
            ItemUtil.P(this.g);
}
}
    public MacroModule(long var1) {
        super(k ^ var1 ^ 68044894831310L);
        this.g = 0;
        this.D = 0;
        this.U = -1;
        this.M = false;
        this.J = false;
        this.Y = false;
}
    private boolean h(long var1, ModeSetting var3, NumberSetting var4) {
        int var11 = 61899;
        int var14 = 16552;
        this.U = -1;
        switch (var3.Y()) {
            case "PROJECTILES": {
                this.U = ItemUtil.j(30888, 38028, (char)var11, false);
                break;
}
            case "ROD": {
                this.U = ItemUtil.j(30888, 38028, (char)var11, true);
                break;
}
            case "POT": {
                ItemStack var23;
                this.U = ItemUtil.M(69180515578808L);
                if (this.U == -1 || (var23 = MacroModule.f.thePlayer.inventory.mainInventory[this.U]) == null || !(var23.getItem() instanceof ItemPotion) || !ItemPotion.isSplash((int)MethodAccessors.f(var23.getItem(), var23)) || !ItemUtil.y(var23) || !(MacroModule.f.thePlayer.getHealth() <= var4.L())) break;
                return false;
}
            case "GOLDEN_HEAD": {
                this.U = ItemUtil.w(17215, '\u89b6', (short)var14);
                break;
}
            case "PEARL": {
                this.U = ItemUtil.N(0L);
                break;
}
            case "WATER_BUCKET": {
                this.U = ItemUtil.l(2486174265250L);
                break;
}
            case "LAVA_BUCKET": {
                this.U = ItemUtil.b(81324674286434L);
                break;
}
            default: {
                return false;
}
}
        if (this.U == -1) {
            return false;
}
        ItemUtil.P(this.U);
        return true;
}
    private int p(ModeSetting var1, NumberSetting var2) {
        switch (var1.Y()) {
            case "PROJECTILES": 
            case "ROD": {
                return Math.max(1, (int)var2.L());
}
}
        return 1;
}
    public void y(ModeSetting var1, NumberSetting var2, long var3, NumberSetting var5, BooleanSetting var6, TextSetting var7) {
        if (var1.R("CHAT")) {
            this.I(20724619369162L, false);
            if (!Objects.equals(var7.X(), "")) {
                MacroModule.f.thePlayer.sendChatMessage(var7.X());
}
        } else if (!this.M) {
            this.g = MacroModule.f.thePlayer.inventory.currentItem;
            if (!this.h(122956549676365L, var1, var5)) {
                this.N(false);
                this.I(20724619369162L, false);
            } else {
                this.M = true;
                this.J = false;
                this.D = 0;
}
        } else {
            ++this.D;
            if (!this.J) {
                KeyBindUtil.h(45028351266375L);
                this.J = true;
                this.D = 0;
            } else {
                int var23 = this.p(var1, var2);
                if (this.D >= var23) {
                    this.O('\u0000', var1, var6, 0x665656C, (short)-24221);
                    if (this.Y) {
                        this.Y = false;
                        this.M = false;
                        this.J = false;
                        this.D = 0;
                        this.y(var1, var2, 40065435448518L, var5, var6, var7);
                        return;
}
                    this.N(true);
                    this.I(20724619369162L, false);
}
}
}
}
    private void N(boolean var3) {
        this.M = false;
        this.J = false;
        this.Y = false;
        this.D = 0;
        if (var3) {
            this.U = -1;
}
}
}