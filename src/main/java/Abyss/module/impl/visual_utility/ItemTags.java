/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemBow
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.ItemSword
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.util.StringUtils
 *  org.lwjgl.opengl.GL11
 */
package Abyss.module.impl.visual_utility;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.ItemTagsBinder;
import Abyss.event.events.PostTickEvent;
import Abyss.event.events.Render3DEvent;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.Modules;
import Abyss.module.impl.configuration.Font;
import Abyss.module.impl.visual.ItemScale;
import Abyss.module.impl.visual_utility.ItemTagsEntry;
import Abyss.module.impl.visual_utility.ItemTagsRenderPos;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.setting.settings.PercentageSetting;
import Abyss.util.ItemUtil;
import Abyss.util.LunarClientDetector;
import Abyss.util.render.CustomFont;
import Abyss.util.render.RenderUtil;
import java.awt.Color;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;

public class ItemTags
extends Module
implements EventSubscriber {
    public static BooleanSetting renderBlocks;
    public static BooleanSetting renderSwordsAndBows;
    public static BooleanSetting nbtOnly;
    public static BooleanSetting megawallsItems;
    public static NumberSetting scale;
    private final List<ItemTagsEntry> S;
    public static BooleanSetting renderALL;
        public static BooleanSetting renderGoldenApples;
    public static BooleanSetting bedwarsResources;
    public static PercentageSetting backgroundOpacity;

    private String l(String var1, long var2, int var4) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        return var1 + " \u00a7fx" + var4;
}
    private void w(EntityItem var1, String var2, int var3) {
        this.S.add(new ItemTagsEntry(var1, var2, var3, null));
}
    private void G(EntityItem var1, long var2, Item var4, String var5) {
        if (var4 instanceof ItemSword || var4 instanceof ItemBow) {
            this.w(var1, var5, 0xFF5555);
}
}
    private void y(long var1, EntityItem var3, ItemStack var4) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        Item var22 = var4.func_77973_b();
        String var23 = var4.func_82833_r();
        String var24 = StringUtils.func_76338_a((String)var23);
        int var25 = var4.field_77994_a;
        boolean var26 = var4.func_77942_o();
        if (!nbtOnly.c() || var26) {
            int var27 = this.L(var4, 0L);
            String var28 = this.l(var23, 0L, var25);
            if (renderALL.c()) {
                this.P(0L, var3, var28, var26, var27);
            } else {
                if (megawallsItems.c()) {
                    this.A(var3, var22, var24, var28, var25, var27);
}
                if (bedwarsResources.c()) {
                    this.W(0L, var3, var22, var28);
}
                if (renderSwordsAndBows.c()) {
                    this.G(var3, 0L, var22, var28);
}
                if (renderBlocks.c()) {
                    this.z(var3, var4, 0L, var28);
}
                if (renderGoldenApples.c()) {
                    this.g(var3, var22, var28, 0L);
}
}
}
}
    private float W(float var1, float var2, float var3) {
        return (var2 - var1) * var3 + var1;
}
    private void o(long var1) {
        GL11.glEnable((int)2929);
        GL11.glDisable((int)3042);
        GlStateManager.func_179117_G();
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.func_179121_F();
}
    @Override
    public void A(long var1) {
        this.S.clear();
}
    private void a(long var1) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        this.S.clear();
        List var5 = ItemTags.f.field_71441_e.field_72996_f;
        for (int var6 = 0; var6 < var5.size(); ++var6) {
            EntityItem var8;
            ItemStack var9;
            Entity var7 = (Entity)var5.get(var6);
            if (!(var7 instanceof EntityItem) || !this.I(var9 = (var8 = (EntityItem)var7).func_92059_d())) continue;
            this.y(17753419752380L, var8, var9);
}
}
    private float F(CustomFont var1, String var2, long var3) {
        return -var1.R(var2, 52019766876817L) / 2.0f - 4.6f;
}
    private void m(CustomFont var3, String var4, float var5, int var6) {
        float var11 = var3.R(var4, 52019766876817L);
        int var12 = 255 * backgroundOpacity.k() / 100;
        int var13 = new Color(0, 0, 0, var12).getRGB();
        RenderUtil.c(125644905353792L, (int)var5 + 2, -14 - var6, var11 / 2.0f, -4 - var6, var13);
}
    private float m(Minecraft var1, int var2, char var3, char var4) {
        if (LunarClientDetector.q(0L)) {
            return var1.func_175598_ae().field_78732_j;
}
        return var1.field_71474_y.field_74320_O == 2 ? -var1.func_175598_ae().field_78732_j : var1.func_175598_ae().field_78732_j;
}
    private void W(long var1, EntityItem var3, Item var4, String var5) {
        if (var4 == Items.field_151045_i) {
            this.w(var3, var5, 0x55FFFF);
}
        if (var4 == Items.field_151042_j) {
            this.w(var3, var5, 0xAAAAAA);
}
        if (var4 == Items.field_151043_k) {
            this.w(var3, var5, 0xFFFF55);
}
        if (var4 == Items.field_151166_bC) {
            this.w(var3, var5, 0x55FF55);
}
}
    public void onPostTick(long var1, PostTickEvent var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        this.a(32528519039452L);
}
    private ItemTagsRenderPos getRenderManager(Entity var1, float var2) {
        float var3 = this.W((float)var1.field_70142_S, (float)var1.field_70165_t, var2) - (float)ItemTags.f.func_175598_ae().field_78730_l;
        float var4 = this.W((float)var1.field_70137_T, (float)var1.field_70163_u, var2) - (float)ItemTags.f.func_175598_ae().field_78731_m;
        float var5 = this.W((float)var1.field_70136_U, (float)var1.field_70161_v, var2) - (float)ItemTags.f.func_175598_ae().field_78728_n;
        return new ItemTagsRenderPos(var3, var4, var5, null);
}
    private void P(long var1, EntityItem var3, String var4, boolean var5, int var6) {
        this.w(var3, var4, var5 ? var6 : 0xFFFFFF);
}
    static void $jnicClinit() throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException {
}
    private int A(long var1, EntityItem var3) {
        if (!this.M()) {
            return 0;
}
        return !ItemScale.c(var3.func_92059_d()) ? 0 : (int)((ItemScale.scale.L() - 1.0f) * 14.0f);
}
    public void onRender3D(long var1, Render3DEvent var3) {
        CustomFont var8 = Font.s(0L);
        for (int var9 = 0; var9 < this.S.size(); ++var9) {
            ItemTagsEntry var10 = this.S.get(var9);
            this.S(var8, var10, var3.j, scale.L(), 63742102300376L);
}
}
    public ItemTags(long var1) {
        super(a ^ var1 ^ 0x5932CBF6256EL);
        this.declare("ItemTags", Category.Visual_utility, "Render text bar on dropped items", new Setting[0]);
        var1 = a ^ var1;
        this.S = new ArrayList<ItemTagsEntry>();
}
    private void b(CustomFont var1, String var2, int var3, float var4, long var5, int var7) {
        var1.v(var2, var4 + 4.0f, -12.5f - (float)var7, var3, 88827598794260L, false);
}
    @Override
    public String g(long var1) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
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
    private int L(ItemStack var1, long var2) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (!var1.func_77942_o()) {
            return -1;
}
        NBTTagCompound var4 = var1.func_77978_p();
        if (!var4.func_74764_b("display")) {
            return -1;
}
        NBTTagCompound var5 = var4.func_74775_l("display");
        return !var5.func_74764_b("color") ? -1 : var5.func_74762_e("color");
}
    @Override
    public final void x(long var1, EventBus var3) {
        int var4 = (int)((var1 ^ 0x565405F3C546L) >>> 48);
        int var5 = (int)((var1 ^ 0x565405F3C546L) << 16 >>> 48);
        ItemTagsBinder.b(var3, (char)var4, (short)var5, this);
}
    private float getDistanceToEntity(Entity var1, float var2) {
        float var3 = var2 / 3.0f;
        float var4 = ItemTags.f.field_71439_g.func_70032_d(var1) / 10.0f;
        if (var4 < 1.1f) {
            var4 = 1.1f;
}
        float var5 = var4 * 1.8f;
        return (var5 /= 100.0f) + var3 / 50.0f;
}
    private void S(CustomFont var1, ItemTagsEntry var2, float var3, float var4, long var5) {
        float var19 = this.getDistanceToEntity((Entity)ItemTagsEntry.H(var2), var4);
        ItemTagsRenderPos var20 = this.getRenderManager((Entity)ItemTagsEntry.H(var2), var3);
        int var21 = this.A(0L, ItemTagsEntry.H(var2));
        float var22 = this.F(var1, ItemTagsEntry.T(var2), 51511525423037L);
        this.y(var20, var19, 37666237105538L);
        this.m(var1, ItemTagsEntry.T(var2), var22, var21);
        this.b(var1, ItemTagsEntry.T(var2), ItemTagsEntry.z(var2), var22, 20402189618877L, var21);
        this.o(0L);
}
    private boolean M() {
        return Modules.J(ItemScale.class).o();
}
    private boolean c(Item var1) {
        return var1 == Items.field_151175_af || var1 == Items.field_151173_ae || var1 == Items.field_151161_ac || var1 == Items.field_151163_ad;
}
    private void z(EntityItem var1, ItemStack var2, long var3, String var5) {
        if (ItemUtil.u(var2)) {
            this.w(var1, var5, 0xFFFFFF);
}
}
    private boolean I(ItemStack var1) {
        return var1 != null && var1.field_77994_a > 0;
}
    private void y(ItemTagsRenderPos var1, float var2, long var3) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)ItemTagsRenderPos.B(var1), (float)(ItemTagsRenderPos.Z(var1) + 0.5f), (float)ItemTagsRenderPos.M(var1));
        GL11.glRotatef((float)(-ItemTags.f.func_175598_ae().field_78735_i), (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)this.m(f, 25825, '\ud762', '\u1fcc'), (float)1.0f, (float)0.0f, (float)0.0f);
        GL11.glScalef((float)(-var2), (float)(-var2), (float)var2);
        GL11.glDisable((int)2929);
        GL11.glEnable((int)3042);
}
    private void g(EntityItem var1, Item var2, String var3, long var4) {
        if (var2 == Items.field_151153_ao) {
            this.w(var1, var3, 0xFFAA00);
}
}
    private void A(EntityItem var2, Item var3, String var4, String var5, int var6, int var7) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (var4.startsWith("Phoenix's Tears of Regen")) {
            this.w(var2, "\u00a76Phoenix's Tears of Regen \u00a7fx" + var6, var7);
}
        if (var4.startsWith("Squid's Absorption")) {
            this.w(var2, "\u00a79Squid's Absorption \u00a7fx" + var6, var7);
}
        if (var4.startsWith("Matey")) {
            this.w(var2, var5, var7);
}
        if (var4.startsWith("Regen-Ade")) {
            this.w(var2, "\u00a7bRegen-ades \u00a7fx" + var6, var7);
}
        if (var4.startsWith("Ultra Pasteurized Milk Bucket")) {
            this.w(var2, "\u00a7fMilk Bucket \u00a7fx" + var6, var7);
}
        if (var4.startsWith("Junk Apple")) {
            this.w(var2, var5, var7);
}
        if (var3 == Items.field_151158_bO) {
            this.w(var2, var5, 16711610);
}
        if (var3 == Items.field_151153_ao) {
            this.w(var2, var5, 0xFFAA00);
}
        if (var3 == Items.field_151045_i) {
            this.w(var2, var5, 0x55FFFF);
}
        if (var3 == Items.field_151048_u) {
            this.w(var2, var5, 0x55FFFF);
}
        if (this.c(var3)) {
            this.w(var2, var5, 0x55FFFF);
}
}
    static {
        try {
            ItemTags.$jnicClinit();
}
        catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException var0) {
            throw new RuntimeException(var0);
}
        renderSwordsAndBows = new BooleanSetting("Render-swords-and-bows", false);
        backgroundOpacity = new PercentageSetting("Background-opacity", 20);
        nbtOnly = new BooleanSetting("NBT-only", false);
        scale = new NumberSetting("Scale", 1.0f, 0.01f, 5.0f, 0.01f);
        renderALL = new BooleanSetting("Render-ALL", false);
        megawallsItems = new BooleanSetting("Megawalls-items", true);
        renderGoldenApples = new BooleanSetting("Render-golden-apples", false);
        bedwarsResources = new BooleanSetting("Bedwars-resources", false);
        renderBlocks = new BooleanSetting("Render-blocks", false);
}
}