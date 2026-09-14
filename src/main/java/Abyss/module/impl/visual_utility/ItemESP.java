/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.AxisAlignedBB
 */
package Abyss.module.impl.visual_utility;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.ItemESPBinder;
import Abyss.event.events.PostTickEvent;
import Abyss.event.events.Render3DEvent;
import Abyss.internal.accessor.RenderManagerAccessor;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.impl.configuration.Font;
import Abyss.module.impl.visual_utility.ItemESPEntry;
import Abyss.module.impl.visual_utility.ItemESPStackKey;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.setting.settings.PercentageSetting;
import Abyss.util.LunarClientDetector;
import Abyss.util.render.CustomFont;
import Abyss.util.render.RenderUtil;
import java.awt.Color;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;

public class ItemESP
extends Module
implements EventSubscriber {
    public static NumberSetting scale;
    public static BooleanSetting emeralds;
    public static BooleanSetting golds;
    private static long b;
    public static PercentageSetting opacity;
    public static BooleanSetting irons;
    private final List<ItemESPEntry> t;
    public static BooleanSetting outline;
    public static BooleanSetting diamonds;

    private boolean Q(int var1) {
        Item var2 = Item.func_150899_d((int)var1);
        Block var3 = Block.func_149634_a((Item)var2);
        return var2 == Items.field_151042_j || var3 == Blocks.field_150339_S || var3 == Blocks.field_150366_p;
}
    private void W(AxisAlignedBB var1, Color var2, char var3) {
        RenderUtil.L();
        if (opacity.k() > 0) {
            RenderUtil.l(var1, var2.getRed(), var2.getGreen(), var2.getBlue(), (int)(2.55 * (double)opacity.k()));
}
        if (outline.c()) {
            RenderUtil.X(var1, var2.getRed(), var2.getGreen(), var2.getBlue(), 255, 1.5f);
}
        GlStateManager.func_179117_G();
        RenderUtil.w();
}
    private double h(double var1) {
        return (Math.max(6.0, var1) - 6.0) / 28.0;
}
    private double q(double var1) {
        return (0.5 + 0.375 * var1) * (double)scale.L();
}
    private float q() {
        return ItemESP.f.field_71474_y.field_74320_O == 2 ? -1.0f : 1.0f;
}
    private Color f(int var3) {
        if (this.i(var3)) {
            return new Color(-11141291);
}
        if (this.Y(var3)) {
            return new Color(-11141121);
}
        if (this.J(var3)) {
            return new Color(-171);
}
        return this.Q(var3) ? new Color(-1) : new Color(-5592406);
}
    public void onPostTick(PostTickEvent var1) {
        this.t.clear();
        List var2 = ItemESP.f.field_71441_e.field_72996_f;
        for (int var3 = 0; var3 < var2.size(); ++var3) {
            int var7;
            EntityItem var5;
            ItemStack var6;
            Entity var4 = (Entity)var2.get(var3);
            if (!this.D(var4) || !this.b(var6 = (var5 = (EntityItem)var4).func_92059_d()) || !this.q(var7 = Item.func_150891_b((Item)var6.func_77973_b()))) continue;
            this.t.add(new ItemESPEntry(var5, var7, null));
}
}
    private void s(CustomFont var1, String var2, Color var3, double var4, double var6, double var8, double var10, double var12, long var14, float var16, float var17) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        var14 = b ^ var14;
        long var20 = var14 ^ 0x4D3D565F8A9BL;
        long var22 = var14 ^ 0x6BFC6F85E18FL;
        long var24 = var14 ^ 0x554ADA7374A6L;
        GlStateManager.func_179094_E();
        GlStateManager.func_179137_b((double)var4, (double)(var6 + var10 * 0.5), (double)var8);
        GlStateManager.func_179114_b((float)(-var16), (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.func_179114_b((float)var17, (float)(LunarClientDetector.q(0L) ? 1.0f : this.q()), (float)0.0f, (float)0.0f);
        GlStateManager.func_179139_a((double)var12, (double)var12, (double)1.0);
        GlStateManager.func_179097_i();
        float var26 = -(var1.R(var2, var24) / 2.0f) + 0.5f;
        float var27 = -(var1.o(var20) / 2.0f) + 0.5f;
        RenderUtil.K(var1, var2, var22, var26, var27, var3.getRGB());
        GlStateManager.func_179126_j();
        GlStateManager.func_179117_G();
        GlStateManager.func_179121_F();
}
    private void q(Map<ItemESPStackKey, Integer> var1, ItemESPStackKey var2, int var3) {
        var1.merge(var2, var3, Integer::sum);
}
    private boolean D(Entity var1) {
        return var1 instanceof EntityItem && var1.field_70173_aa >= 3;
}
    private boolean Y(int var1) {
        Item var2 = Item.func_150899_d((int)var1);
        Block var3 = Block.func_149634_a((Item)var2);
        return var2 == Items.field_151045_i || var2 == Items.field_151048_u || var2 == Items.field_151046_w || var2 == Items.field_151047_v || var2 == Items.field_151056_x || var2 == Items.field_151012_L || var2 == Items.field_151161_ac || var2 == Items.field_151163_ad || var2 == Items.field_151173_ae || var2 == Items.field_151175_af || var3 == Blocks.field_150484_ah || var3 == Blocks.field_150482_ag;
}
    public ItemESP(short var1, char var2, int var3) {
        super(((long)var1 << 48 | (long)var2 << 48 >>> 16 | (long)var3 << 32 >>> 32) ^ b ^ 0x665A869572A0L);
        this.declare("ItemESP", Category.Visual_utility, "Render a box on items", new Setting[0]);
        this.t = new ArrayList<ItemESPEntry>();
}
    static void $jnicClinit() throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException {
        b = 28379158381100L;
}
    private boolean q(int var1) {
        return emeralds.c() && this.i(var1) || diamonds.c() && this.Y(var1) || golds.c() && this.J(var1) || irons.c() && this.Q(var1);
}
    private void x(CustomFont var1, Map.Entry var2, int var3, double var4, char var6, double var7, double var9, float var11, float var12, int var13) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var14 = ((long)var3 << 32 | (long)var6 << 48 >>> 32 | (long)var13 << 48 >>> 48) ^ b;
        long var16 = var14 ^ 0x3298E157A7E9L;
        int var20 = (int)((var14 ^ 0x3BEAEBB99BCAL) >>> 48);
        ItemESPStackKey var23 = (ItemESPStackKey)var2.getKey();
        int var24 = (Integer)var2.getValue();
        Color var25 = this.f(var23.Z);
        double var26 = var23.p - var4;
        double var28 = var23.d - var7;
        double var30 = var23.l - var9;
        double var32 = f.func_175606_aa().func_70011_f(var23.p, var23.d, var23.l);
        double var34 = this.h(var32);
        double var36 = this.q(var34);
        double var38 = this.Y(var34);
        AxisAlignedBB var40 = this.M(var26, var28, var30, var36);
        this.W(var40, var25, (char)var20);
        this.s(var1, Integer.toString(var24), var25, var26, var28, var30, var36, var38, var16, var11, var12);
}
    @Override
    public final void x(long var1, EventBus var3) {
        ItemESPBinder.v(var3, this);
}
    public void onRender3D(Render3DEvent var1, long var2) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        CustomFont var16 = Font.s(0L);
        LinkedHashMap<ItemESPStackKey, Integer> var17 = this.q(var1.j);
        ArrayList<Map.Entry<ItemESPStackKey, Integer>> var18 = new ArrayList<Map.Entry<ItemESPStackKey, Integer>>(var17.entrySet());
        this.G(var18);
        for (int var19 = 0; var19 < var18.size(); ++var19) {
            this.x(var16, var18.get(var19), 18589, RenderManagerAccessor.k(0L, f.func_175598_ae()), '\uf09d', RenderManagerAccessor.y(13236, f.func_175598_ae()), RenderManagerAccessor.W(0L, f.func_175598_ae()), ItemESP.f.func_175598_ae().field_78735_i, ItemESP.f.func_175598_ae().field_78732_j, 6069);
}
}
    private LinkedHashMap<ItemESPStackKey, Integer> q(float var1) {
        LinkedHashMap<ItemESPStackKey, Integer> var2 = new LinkedHashMap<ItemESPStackKey, Integer>();
        for (int var3 = 0; var3 < this.t.size(); ++var3) {
            ItemStack var6;
            ItemESPEntry var4 = this.t.get(var3);
            EntityItem var5 = ItemESPEntry.w(var4);
            if (!this.isGetEntityBoundingBox(var5) || !this.b(var6 = var5.func_92059_d())) continue;
            double var7 = this.m(var5.field_70142_S, var5.field_70165_t, var1);
            double var9 = this.m(var5.field_70137_T, var5.field_70163_u, var1);
            double var11 = this.m(var5.field_70136_U, var5.field_70161_v, var1);
            ItemESPStackKey var13 = new ItemESPStackKey(ItemESPEntry.r(var4), var7, var9, var11);
            this.q(var2, var13, var6.field_77994_a);
}
        return var2;
}
    private double Y(double var1) {
        return (-0.04375 - 0.0328125 * var1) * (double)scale.L();
}
    private void G(List<Map.Entry<ItemESPStackKey, Integer>> var1) {
        var1.sort((var1x, var2) -> Integer.compare(this.D(((ItemESPStackKey)var1x.getKey()).Z), this.D(((ItemESPStackKey)var2.getKey()).Z)));
}
    private boolean isGetEntityBoundingBox(EntityItem var1) {
        return var1 != null && !var1.field_70128_L && RenderUtil.p(var1.func_174813_aQ(), 0.125);
}
    private boolean i(int var1) {
        Item var2 = Item.func_150899_d((int)var1);
        Block var3 = Block.func_149634_a((Item)var2);
        return var2 == Items.field_151166_bC || var3 == Blocks.field_150475_bE || var3 == Blocks.field_150412_bA;
}
    @Override
    public void A(long var1) {
        this.t.clear();
}
    private AxisAlignedBB M(double var1, double var3, double var5, double var7) {
        double var9 = var7 * 0.5;
        return new AxisAlignedBB(var1 - var9, var3, var5 - var9, var1 + var9, var3 + var7, var5 + var9);
}
    private boolean b(ItemStack var1) {
        return var1 != null && var1.field_77994_a > 0;
}
    private int D(int var1) {
        if (this.i(var1)) {
            return 4;
}
        if (this.Y(var1)) {
            return 3;
}
        if (this.J(var1)) {
            return 2;
}
        return this.Q(var1) ? 1 : 0;
}
    private double m(double var1, double var3, float var5) {
        return (double)var5 * (var3 - var1) + var1;
}
    private boolean J(int var1) {
        Item var2 = Item.func_150899_d((int)var1);
        Block var3 = Block.func_149634_a((Item)var2);
        return var2 == Items.field_151043_k || var2 == Items.field_151074_bl || var2 == Items.field_151153_ao || var3 == Blocks.field_150340_R || var3 == Blocks.field_150352_o;
}
    static {
        try {
            ItemESP.$jnicClinit();
}
        catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException var0) {
            throw new RuntimeException(var0);
}
        scale = new NumberSetting("Scale", 0.8f, 0.01f, 5.0f, 0.01f);
        golds = new BooleanSetting("Golds", true);
        irons = new BooleanSetting("Irons", true);
        emeralds = new BooleanSetting("Emeralds", true);
        diamonds = new BooleanSetting("Diamonds", true);
        opacity = new PercentageSetting("Opacity", 60);
        outline = new BooleanSetting("Outline", true);
}
}