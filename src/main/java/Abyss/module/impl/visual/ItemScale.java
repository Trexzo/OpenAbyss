/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemBow
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.ItemSword
 *  net.minecraft.util.StringUtils
 */
package Abyss.module.impl.visual;

import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.NumberSetting;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.StringUtils;

public class ItemScale
extends Module {
    private static long b = 86762146743802L;
    public static BooleanSetting bedwarsResources;
    public static BooleanSetting nbtOnly;
    public static BooleanSetting renderGoldenApples;
    public static BooleanSetting megawallsItems;
    public static BooleanSetting renderSwordsAndBows;
    public static NumberSetting scale;
    public static BooleanSetting renderALL;

    private static boolean f(Item var0) {
        return var0 == Items.field_151175_af || var0 == Items.field_151173_ae || var0 == Items.field_151161_ac || var0 == Items.field_151163_ad;
}
    public ItemScale(long var1) {
        super(b ^ var1 ^ 0x5F90B90E910DL);
        this.declare("ItemScale", Category.Visual, "Scale the dropped items", new Setting[0]);
        var1 = b ^ var1;
}
    @Override
    public String g(long var1) {
        if (nbtOnly.c()) {
            return "NBT";
}
        if (renderALL.c()) {
            return "ALL";
}
        if (megawallsItems.c()) {
            return "MEGAWALLS";
}
        if (bedwarsResources.c()) {
            return "BEDWARS";
}
        if (renderGoldenApples.c()) {
            return "GAPPLES";
}
        return renderSwordsAndBows.c() ? "WEAPONS" : "NONE";
}
    public static boolean c(ItemStack var0) {
        Item var3 = var0.func_77973_b();
        String var4 = var0.func_82833_r();
        String var5 = StringUtils.func_76338_a((String)var4);
        if (renderALL.c()) {
            return !nbtOnly.c() || var0.func_77942_o();
}
        if (nbtOnly.c()) {
            return var0.func_77942_o();
}
        if (!(megawallsItems.c() && (var5.startsWith("Phoenix's Tears of Regen") || var5.startsWith("Squid's Absorption") || var5.startsWith("Matey") || var5.startsWith("Regen-Ade") || var5.startsWith("Ultra Pasteurized Milk Bucket") || var5.startsWith("Junk Apple") || var3 == Items.field_151158_bO || var3 == Items.field_151153_ao || var3 == Items.field_151045_i || var3 == Items.field_151048_u || ItemScale.f(var3)))) {
            if (!bedwarsResources.c() || var3 != Items.field_151045_i && var3 != Items.field_151043_k && var3 != Items.field_151042_j && var3 != Items.field_151166_bC) {
                return !renderSwordsAndBows.c() || !(var3 instanceof ItemSword) && !(var3 instanceof ItemBow) ? renderGoldenApples.c() && var3 == Items.field_151153_ao : true;
}
            return true;
}
        return true;
}
    static {
        scale = new NumberSetting("Scale", 3.0f, 0.01f, 5.0f, 0.01f);
        nbtOnly = new BooleanSetting("NBT-only", false);
        megawallsItems = new BooleanSetting("Megawalls-items", true);
        renderSwordsAndBows = new BooleanSetting("Render-swords-and-bows", false);
        bedwarsResources = new BooleanSetting("Bedwars-resources", false);
        renderGoldenApples = new BooleanSetting("Render-golden-apples", false);
        renderALL = new BooleanSetting("Render-ALL", false);
}
}