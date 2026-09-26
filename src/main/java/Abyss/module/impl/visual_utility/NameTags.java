/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagList
 *  net.minecraft.potion.Potion
 *  net.minecraft.potion.PotionEffect
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package Abyss.module.impl.visual_utility;

import Abyss.enums.MinecraftColor;
import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.NameTagsBinder;
import Abyss.event.events.PostTickEvent;
import Abyss.event.events.Render3DEvent;
import Abyss.internal.accessor.RenderManagerAccessor;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.impl.configuration.Font;
import Abyss.module.impl.configuration.Teams;
import Abyss.module.impl.player.AutoWeapon;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.HeaderSetting;
import Abyss.setting.settings.ModeSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.setting.settings.PercentageSetting;
import Abyss.util.CombatUtil;
import Abyss.util.EnchantmentAbbreviation;
import Abyss.util.EnchantmentAbbreviations;
import Abyss.util.EntityUtil;
import Abyss.util.LunarClientDetector;
import Abyss.util.MathUtil;
import Abyss.util.render.CustomFont;
import Abyss.util.render.RenderUtil;
import java.awt.Color;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public class NameTags
extends Module
implements EventSubscriber {
    private static long a = 110449242857023L;

    public static BooleanSetting onlyName;
    private static String[] E;
    private static long[] s;
    public static PercentageSetting backgroundOpacity;
    public static NumberSetting backgroundSpacing;
    public static Set<EntityLivingBase> x;
    private static Object[] y;
    public static BooleanSetting textShadow;
    public static BooleanSetting enemies;
    public static BooleanSetting autoScale;
    public static BooleanSetting bots;
    public static BooleanSetting friends;
    public static BooleanSetting players;
        public static NumberSetting scale;
    public static BooleanSetting showHealth;
    public static BooleanSetting showEffects;
    private static String[] k;
    private static Map<Integer, EnchantmentAbbreviation> c;
    public static BooleanSetting showHitsToKill;
    public static ModeSetting armorMode;
    public static BooleanSetting enchant;
    public static BooleanSetting animals;
    public static BooleanSetting teammates;
    private static Map m;
    public static BooleanSetting showSelf;
    private final List<EntityLivingBase> Y;
    public static BooleanSetting mobs;
    public static BooleanSetting showDistance;
    public static BooleanSetting bosses;
    public static BooleanSetting showIndicator;
    public static HeaderSetting targetSettings;
    private static Map v;

    private void replaceAll(String var1, long var2, float var4, float var5) {
        long var6 = var2 ^ 0x1C22E63369CEL;
        CustomFont var10 = Font.s(0L);
        String var11 = var1.replaceAll("(?i)\u00a7[\\da-f]", "");
        var10.v(var11, var4 + 1.0f, var5, 0, var6, false);
        var10.v(var11, var4 - 1.0f, var5, 0, var6, false);
        var10.v(var11, var4, var5 + 1.0f, 0, var6, false);
        var10.v(var11, var4, var5 - 1.0f, 0, var6, false);
        var10.v(var1, var4, var5, -1, var6, false);
}
    private void c(EntityLivingBase var1, CustomFont var2, long var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (var1 instanceof EntityPlayer) {
            float var11 = var2.o(60714858652844L) + 2.0f;
            if (!armorMode.R("NONE")) {
                ArrayList<ItemStack> var12 = new ArrayList<ItemStack>();
                for (int var13 = 4; var13 >= 0; --var13) {
                    ItemStack var14 = var13 == 0 ? var1.getHeldItem() : ((EntityPlayer)var1).inventory.armorInventory[var13 - 1];
                    if (var14 == null) continue;
                    var12.add(var14);
}
                if (!var12.isEmpty()) {
                    boolean var25;
                    String var24 = armorMode.Y();
                    boolean bl = var25 = var1 == NameTags.f.thePlayer;
                    if (var24.equals("LEFT") || var25 && var24.equals("SELF_LEFT")) {
                        this.armorColumn(var12, -26, 114286643707769L);
                    } else if (var24.equals("RIGHT") || var25 && var24.equals("SELF_RIGHT")) {
                        this.armorColumn(var12, 10, 114286643707769L);
                    } else {
                        int var18 = var12.size() * -8;
                        for (int var21 = 0; var21 < var12.size(); ++var21) {
                            this.h((ItemStack)var12.get(var21), var18 + var21 * 16, 114286643707769L, (int)(-var11 - 16.0f));
}
                        var11 += 16.0f;
}
}
}
            if (showEffects.c()) {
                ArrayList<PotionEffect> var17 = new ArrayList<PotionEffect>();
                for (PotionEffect var22 : var1.getActivePotionEffects()) {
                    Potion var15 = Potion.potionTypes[var22.getPotionID()];
                    if (var15 == null || !var15.hasStatusIcon()) continue;
                    var17.add(var22);
}
                if (!var17.isEmpty()) {
                    GlStateManager.pushMatrix();
                    GlStateManager.scale((float)0.5f, (float)0.5f, (float)1.0f);
                    int var20 = var17.size() * -9;
                    for (int var23 = 0; var23 < var17.size(); ++var23) {
                        this.F((PotionEffect)var17.get(var23), var20 + var23 * 18, (int)(-(var11 * 2.0f) - 18.0f));
}
                    GlStateManager.popMatrix();
}
}
}
}
    private void armorColumn(List var1, int var2, long var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        GlStateManager.pushMatrix();
        for (int var5 = 0; var5 < var1.size(); ++var5) {
            this.h((ItemStack)var1.get(var5), var2, var3, -8 + var5 * 16);
}
        GlStateManager.popMatrix();
}
    private void F(PotionEffect var1, int var2, int var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        int var6 = Potion.potionTypes[var1.getPotionID()].getStatusIconIndex();
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.pushMatrix();
        GlStateManager.depthMask((boolean)true);
        GlStateManager.clear((int)256);
        GlStateManager.pushMatrix();
        GlStateManager.scale((float)1.0f, (float)1.0f, (float)-0.01f);
        f.getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/inventory.png"));
        Gui.drawModalRectWithCustomSizedTexture((int)var2, (int)var3, (float)(var6 % 8 * 18), (float)(198 + var6 / 8 * 18), (int)18, (int)18, (float)256.0f, (float)256.0f);
        GlStateManager.popMatrix();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
}
    @Override
    public void A(long var1) {
        this.Y.clear();
        x.clear();
}
    public void onRender3D(Render3DEvent var1, long var2) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        CustomFont var9 = Font.s(0L);
        float var10 = var1.j;
        x.clear();
        for (int var11 = 0; var11 < this.Y.size(); ++var11) {
            EntityLivingBase var12 = this.Y.get(var11);
            if (!RenderUtil.l((Entity)var12)) continue;
            this.A(2009537843L, var12, var10, '\udfa8', var9);
}
        if (showSelf.c() && NameTags.f.gameSettings.thirdPersonView != 0) {
            this.A(2009537843L, (EntityLivingBase)NameTags.f.thePlayer, var10, '\udfa8', var9);
}
}
    private float y() {
        return NameTags.f.gameSettings.thirdPersonView == 2 ? -1.0f : 1.0f;
}
    private void O(EntityLivingBase var1, double var2, double var4, double var8, double var10) {
        GlStateManager.translate((double)var2, (double)(var4 + (var1.isSneaking() ? 0.225 : 0.4)), (double)var8);
        GlStateManager.rotate((float)(NameTags.f.getRenderManager().playerViewY * -1.0f), (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.rotate((float)NameTags.f.getRenderManager().playerViewX, (float)(LunarClientDetector.q(0L) ? 1.0f : this.y()), (float)0.0f, (float)0.0f);
        double var14 = Math.pow(Math.min(Math.max(autoScale.c() ? var10 : 0.0, 6.0), 128.0), 0.75) * 0.0075;
        GlStateManager.scale((double)(-var14 * (double)scale.L()), (double)(-var14 * (double)scale.L()), (double)1.0);
}
    private String u(int var1, byte var2, int var3, EntityLivingBase var4, double var5) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var7 = ((long)var1 << 32 | (long)var2 << 56 >>> 32 | (long)var3 << 40 >>> 40) ^ a;
        long var9 = var7 ^ 0x5C7EA7EE24F1L;
        long var11 = var7 ^ 0x5845780C386EL;
        long var13 = var7 ^ 0x2EE3A2DB7E75L;
        long var15 = var7 ^ 0x4F6DE81DED7DL;
        StringBuilder var17 = new StringBuilder();
        if (onlyName.c()) {
            var17.append(var4.getName());
        } else {
            var17.append(var4.getDisplayName().getFormattedText());
}
        if (showHealth.c()) {
            float var18 = CombatUtil.h(var4);
            float var19 = var4.getMaxHealth();
            var17.append(" ").append(CombatUtil.h(var18, var19, var9)).append(MathUtil.W(var18));
            float var20 = CombatUtil.D(var4);
            if (var20 != 0.0f) {
                var17.append(" \u00a76").append(MathUtil.W(var20));
}
}
        if (showHitsToKill.c()) {
            var17.append(" ").append(CombatUtil.h(var4, var13, NameTags.f.thePlayer.inventory.getStackInSlot(AutoWeapon.M(var11))));
}
        if (showDistance.c() && var4 != NameTags.f.thePlayer) {
            int var21 = (int)MathUtil.W(var5);
            String var22 = "";
            if (var5 <= 8.0) {
                var22 = "\u00a7c";
            } else if (var5 <= 15.0) {
                var22 = "\u00a76";
            } else if (var5 <= 25.0) {
                var22 = "\u00a7e";
}
            var17.insert(0, "\u00a7b[\u00a7r" + var22 + var21 + "\u00a7b] \u00a7r");
}
        if (showIndicator.c() && var4 != NameTags.f.thePlayer) {
            var17.append(" ").append(CombatUtil.s(var15, var4) ? "\u00a76[\u00a7a+\u00a76]\u00a7r" : "\u00a76[\u00a7c-\u00a76]\u00a7r");
}
        return var17.toString();
}
    private void h(ItemStack var1, int var2, long var3, int var5) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var6 = 64928077817138L;
        GlStateManager.pushMatrix();
        GlStateManager.depthMask((boolean)true);
        GlStateManager.clear((int)256);
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glDisable((int)2896);
        GlStateManager.pushMatrix();
        GlStateManager.scale((float)1.0f, (float)1.0f, (float)-0.01f);
        NameTags.f.getRenderItem().zLevel = -150.0f;
        f.getRenderItem().renderItemAndEffectIntoGUI(var1, var2, var5);
        f.getRenderItem().renderItemOverlays(NameTags.f.fontRendererObj, var1, var2, var5);
        NameTags.f.getRenderItem().zLevel = 0.0f;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.scale((float)0.5f, (float)0.5f, (float)0.5f);
        GlStateManager.disableDepth();
        if (enchant.c()) {
            this.W(var6, var1, var2, var5, 0.5f);
}
        GlStateManager.enableDepth();
        GlStateManager.scale((float)2.0f, (float)2.0f, (float)2.0f);
        GlStateManager.popMatrix();
}
    private void l(long var1, EntityLivingBase var3, CustomFont var4, String var5) {
        float var18 = var4.R(var5, 52019766876817L);
        Color var19 = !var3.isSneaking() && !var3.isInvisible() ? new Color(0.0f, 0.0f, 0.0f, (float)backgroundOpacity.k() / 100.0f) : new Color(0.33f, 0.0f, 0.33f, (float)backgroundOpacity.k() / 100.0f);
        RenderUtil.L();
        float var24 = backgroundSpacing.L();
        float var20 = -var18 / 2.0f - var24;
        float var21 = -var4.o(60714858652844L) - var24;
        float var22 = var18 / 2.0f + (textShadow.c() ? var24 : 0.0f);
        float var23 = textShadow.c() ? 0.0f : -var24;
        RenderUtil.c(125644905353792L, var20, var21, var22, var23, var19.getRGB());
        if (Teams.l((Entity)var3)) {
            RenderUtil.m(var20, var21, 91446790430251L, var22, var23, 1.0f, Color.GREEN.getRGB());
        } else if (Teams.Y((Entity)var3)) {
            RenderUtil.m(var20, var21, 91446790430251L, var22, var23, 1.0f, Color.RED.getRGB());
}
        RenderUtil.w();
        GlStateManager.disableDepth();
        var4.v(var5, -var18 / 2.0f, -var4.o(60714858652844L), -1, 88827598794260L, textShadow.c());
        GlStateManager.enableDepth();
}
    private void A(long var1, EntityLivingBase var3, float var4, char var5, CustomFont var6) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        long var7 = (0x77C71D330000L | (long)var5 << 48 >>> 48) ^ a;
        long var10001 = var7 ^ 0x7B6CDC8E72A0L;
        int var11 = (int)((var7 ^ 0x7B6CDC8E72A0L) >>> 32);
        int var12 = (int)((var7 ^ 0x7B6CDC8E72A0L) << 32 >>> 56);
        int var13 = (int)(var10001 << 40 >>> 40);
        var10001 = var7 ^ 0x20002F3A1622L;
        int var18 = (int)((var7 ^ 0x20002F3A1622L) >>> 32);
        long var21 = var7 ^ 0x296781349226L;
        long var23 = var7 ^ 0x1B46EE82A849L;
        x.add(var3);
        RenderManager var25 = f.getRenderManager();
        double var26 = RenderManagerAccessor.k(0L, var25);
        double var28 = RenderManagerAccessor.y(var18, var25);
        double var30 = RenderManagerAccessor.W(0L, var25);
        double var32 = (var3.posX - var3.lastTickPosX) * (double)var4 + var3.lastTickPosX - var26;
        double var34 = (var3.posY - var3.lastTickPosY) * (double)var4 + var3.lastTickPosY - var28 + (double)var3.getEyeHeight();
        double var36 = (var3.posZ - var3.lastTickPosZ) * (double)var4 + var3.lastTickPosZ - var30;
        EntityPlayerSP var38 = NameTags.f.thePlayer;
        double var39 = var38 == null ? 0.0 : (double)var38.getDistanceToEntity((Entity)var3);
        GlStateManager.pushMatrix();
        this.O(var3, var32, var34, var36, var39);
        String var41 = this.u(var11, (byte)var12, var13, var3, var39);
        this.l(var21, var3, var6, var41);
        this.c(var3, var6, var23);
        GlStateManager.popMatrix();
}
    public NameTags(long var1) {
        super(a ^ var1 ^ 0x6D21FFF04784L);
        this.declare("NameTags", Category.Visual_utility, "Modify nametags rendering", new Setting[0]);
        var1 = a ^ var1;
        this.Y = new ArrayList<EntityLivingBase>();
}
    private static MinecraftColor y(int var0, int var1) {
        if (var0 > var1) {
            return MinecraftColor.LIGHT_PURPLE;
}
        if (var0 == var1) {
            return MinecraftColor.RED;
}
        switch (var0) {
            case 1: {
                return MinecraftColor.AQUA;
}
            case 2: {
                return MinecraftColor.GREEN;
}
            case 3: {
                return MinecraftColor.YELLOW;
}
            case 4: {
                return MinecraftColor.GOLD;
}
}
        return MinecraftColor.GRAY;
}
    @Override
    public final void x(long var1, EventBus var3) {
        NameTagsBinder.A(var3, this);
}
    @Override
    public String g(long var1) {
        return String.valueOf(scale.L());
}
    public void onPostTick(PostTickEvent var1, long var2) {
        this.Y.clear();
        boolean var10 = players.c();
        boolean var11 = mobs.c();
        boolean var12 = animals.c();
        boolean var13 = bosses.c();
        boolean var14 = friends.c();
        boolean var15 = enemies.c();
        boolean var16 = teammates.c();
        boolean var17 = bots.c();
        boolean var18 = var10 && !var11 && !var12 && !var13;
        List var19 = EntityUtil.U(var18);
        for (int var20 = 0; var20 < var19.size(); ++var20) {
            EntityLivingBase var21 = (EntityLivingBase)var19.get(var20);
            if (var21 instanceof EntityPlayerSP || var21.getDisplayName() == null || !(var18 ? EntityUtil.c(30808997819832L, (EntityPlayer)var21, var14, var15, var16, var17) : EntityUtil.q((Entity)var21, var10, var11, var12, var13, var14, var15, var16, var17, 21816078198602L))) continue;
            this.Y.add(var21);
}
}
    private void W(long var1, ItemStack var3, float var4, float var5, float var6) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        NBTTagList var11;
        long var9 = var1 ^ 0x77E6104822E8L;
        NBTTagList nBTTagList = var11 = var3.getItem() == Items.enchanted_book ? Items.enchanted_book.getEnchantments(var3) : var3.getEnchantmentTagList();
        if (var11 != null) {
            for (int var12 = 0; var12 < var11.tagCount(); ++var12) {
                EnchantmentAbbreviation var13 = c.get(var11.getCompoundTagAt(var12).getInteger("id"));
                if (var13 == null) continue;
                short var14 = var11.getCompoundTagAt(var12).getShort("lvl");
                MinecraftColor var15 = NameTags.y(var14, var13.L);
                this.replaceAll(MinecraftColor.C(String.format("&r%s%s%d&r", new Object[]{var13.S, var15, (int)var14})), var9, var4 * (1.0f / var6), (var5 + (float)var12 * 4.0f) * (1.0f / var6));
}
}
}
    static {
        x = new HashSet<EntityLivingBase>();
        c = new EnchantmentAbbreviations();
        y = new Object[15];
        E = new String[15];
        m = new HashMap(13);
        k = new String[13];
        v = new HashMap(13);
        s = new long[]{8114464844023189536L, -8498047997027657721L, -6848780433799349009L, 1123611790016166617L, -8478601633323954937L, -1822533454991580431L, 4543667561951989338L, 366751151073540261L, -7937407761630176002L, 4904965540425861536L, -5136359554768556648L};
        teammates = new BooleanSetting("Teammates", true);
        bots = new BooleanSetting("Bots", false);
        backgroundOpacity = new PercentageSetting("Background-opacity", 30);
        showHealth = new BooleanSetting("Show-health", true);
        players = new BooleanSetting("Players", true);
        showDistance = new BooleanSetting("Show-distance", false);
        autoScale = new BooleanSetting("Auto-scale", true);
        showHitsToKill = new BooleanSetting("Show-hits-to-kill", false);
        textShadow = new BooleanSetting("Text-shadow", false);
        mobs = new BooleanSetting("Mobs", false);
        showSelf = new BooleanSetting("Show-self", true);
        enchant = new BooleanSetting("Enchant", true);
        animals = new BooleanSetting("Animals", false);
        bosses = new BooleanSetting("Bosses", false);
        friends = new BooleanSetting("Friends", true);
        showIndicator = new BooleanSetting("Show-indicator", false);
        onlyName = new BooleanSetting("Only-name", false);
        enemies = new BooleanSetting("Enemies", true);
        scale = new NumberSetting("Scale", 0.8f, 0.1f, 3.0f, 0.01f);
        backgroundSpacing = new NumberSetting("Background-spacing", 1.0f, 0.0f, 5.0f, 0.1f);
        armorMode = new ModeSetting("Armor-mode", "NONE", "TOP", "LEFT", "RIGHT", "SELF_LEFT", "SELF_RIGHT");
        showEffects = new BooleanSetting("Show-effects", false);
        targetSettings = new HeaderSetting("Target settings");
}
}