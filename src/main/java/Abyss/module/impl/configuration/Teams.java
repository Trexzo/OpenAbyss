/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.network.NetworkPlayerInfo
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.monster.EntityIronGolem
 *  net.minecraft.entity.monster.EntitySilverfish
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemArmor
 *  net.minecraft.scoreboard.ScorePlayerTeam
 *  net.minecraft.util.IChatComponent
 */
package Abyss.module.impl.configuration;

import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.setting.Setting;
import Abyss.setting.settings.ModeSetting;
import Abyss.setting.settings.TextSetting;
import Abyss.util.EntityUtil;
import Abyss.util.MinecraftRef;
import Abyss.util.PlayerInfoCache;
import Abyss.util.render.ColorUtil;
import java.awt.Color;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.IChatComponent;

public class Teams
extends Module {
    private static long a;

    private static LinkedHashSet<String> x;
        public static TextSetting customPatternRegex;
    
    
    private static LinkedHashSet<String> s;
    
    private static Minecraft t;
    public static ModeSetting sortMode;
    private static volatile Pattern cachedPattern;
    private static volatile String cachedPatternSrc;

    public static boolean y(EntityLivingBase var0) {
        if (t.isSingleplayer()) {
            return false;
}
        if (var0 instanceof EntityPlayerSP) {
            return true;
}
        NetworkPlayerInfo var1 = PlayerInfoCache.byUuid(Teams.t.thePlayer.getUniqueID());
        if (var1 == null) {
            return false;
}
        ScorePlayerTeam var2 = var1.getPlayerTeam();
        if (var2 == null) {
            return false;
}
        NetworkPlayerInfo var3 = PlayerInfoCache.byUuid(var0.getUniqueID());
        if (var3 == null) {
            return false;
}
        ScorePlayerTeam var4 = var3.getPlayerTeam();
        return var4 == null ? false : var2.getColorPrefix().equals(var4.getColorPrefix());
}
    private static Pattern pattern() {
        String src = customPatternRegex.X();
        Pattern p = cachedPattern;
        if (p == null || !src.equals(cachedPatternSrc)) {
            cachedPattern = p = Pattern.compile(src);
            cachedPatternSrc = src;
}
        return p;
}
    public static boolean g(long var0, Entity var2) {
        if (!EntityUtil.B(0L, var2)) {
            return false;
}
        if (sortMode.R("NONE")) {
            return false;
}
        EntityLivingBase var7 = (EntityLivingBase)var2;
        if (Teams.a(0L, var7)) {
            return true;
}
        switch (sortMode.Y()) {
            case "PATTERN": {
                Pattern var10 = Teams.pattern();
                Matcher var11 = var10.matcher(var2.getDisplayName().getFormattedText());
                if (var11.find()) {
                    Matcher var12 = var10.matcher(Teams.t.thePlayer.getDisplayName().getFormattedText());
                    if (var12.find()) {
                        return var12.group().equalsIgnoreCase(var11.group());
}
                    return false;
}
                return false;
}
            case "NAME_COLOR": {
                return Teams.y(var7);
}
            case "ARMOR_COLOR": {
                return Teams.t.thePlayer.getEquipmentInSlot(4) != null && Teams.t.thePlayer.getEquipmentInSlot(4).getItem() == Items.leather_helmet && var7.getEquipmentInSlot(4) != null && var7.getEquipmentInSlot(4).getItem() == Items.leather_helmet && ((ItemArmor)var7.getEquipmentInSlot(4).getItem()).getColor(var7.getEquipmentInSlot(4)) == ((ItemArmor)var7.getEquipmentInSlot(4).getItem()).getColor(Teams.t.thePlayer.getEquipmentInSlot(4));
}
}
        return var7.isOnSameTeam((EntityLivingBase)Teams.t.thePlayer);
}
    public static void C(String var0) {
        if (var0 != null && !var0.trim().isEmpty() && !Teams.a().contains(var0)) {
            Teams.B().add(var0);
}
}
    public static void E(String var0) {
        if (var0 != null && !var0.trim().isEmpty() && !Teams.B().contains(var0)) {
            Teams.a().add(var0);
}
}
    public static Set<String> a() {
        return x;
}
    public static boolean l(Entity var0) {
        return var0 == null ? false : Teams.a().contains(var0.getName());
}
    public static boolean Y(Entity var0) {
        return var0 == null ? false : Teams.B().contains(var0.getName());
}
    public static int d(short var0, EntityLivingBase var3) {
        if (Teams.a().contains(var3.getName())) {
            return ColorUtil.D("2").getRGB();
}
        if (Teams.B().contains(var3.getName())) {
            return ColorUtil.D("4").getRGB();
}
        switch (sortMode.Y()) {
            case "ARMOR_COLOR": {
                if (var3.getEquipmentInSlot(4) == null || var3.getEquipmentInSlot(4).getItem() != Items.leather_helmet || !Items.leather_helmet.hasColor(var3.getEquipmentInSlot(4))) break;
                return Items.leather_helmet.getColor(var3.getEquipmentInSlot(4));
}
}
        return Teams.u(var3, 0L, 1.0f);
}
    public Teams(long var1, short var3) {
        super((0L | (long)var3 << 48 >>> 48) ^ a ^ 0x4FBAED86FBC9L);
        this.declare("Teams", Category.Configuration, "Manage the teaming system", new Setting[0]);
}
    public static int u(EntityLivingBase var0, long var1, float var3) {
        String var6;
        int var4 = 0xFFFFFF;
        ScorePlayerTeam var5 = (ScorePlayerTeam)var0.getTeam();
        if (var5 != null && (var6 = FontRenderer.getFormatFromString((String)var5.getColorPrefix())).length() >= 2) {
            var4 = Teams.t.fontRendererObj.getColorCode(var6.charAt(1));
}
        return new Color((float)(var4 >> 16 & 0xFF) / 255.0f, (float)(var4 >> 8 & 0xFF) / 255.0f, (float)(var4 & 0xFF) / 255.0f, var3).getRGB();
}
    public static void W() {
        Teams.B().clear();
}
    private static boolean a(long var0, EntityLivingBase var2) {
        if (!(var2 instanceof EntityIronGolem) && !(var2 instanceof EntitySilverfish)) {
            return false;
}
        char var5 = Teams.getFormattedText(Teams.t.thePlayer.getDisplayName());
        char var6 = Teams.getFormattedText(var2.getDisplayName());
        return var5 != '\u0000' && var5 == var6;
}
    public static void r$r1() {
        Teams.a().clear();
}
    public static Set<String> B() {
        return s;
}
    private static char getFormattedText(IChatComponent var0) {
        if (var0 == null) {
            return '\u0000';
}
        String var3 = var0.getFormattedText();
        if (var3 == null) {
            return '\u0000';
}
        for (int var4 = 0; var4 < var3.length() - 1; ++var4) {
            char var5;
            if (var3.charAt(var4) != '\u00a7' || ((var5 = Character.toLowerCase(var3.charAt(var4 + 1))) < '0' || var5 > '9') && (var5 < 'a' || var5 > 'f')) continue;
            return var5;
}
        return '\u0000';
}
    static {
        a = 125213457127301L;
        cachedPatternSrc = "";
        x = new LinkedHashSet();
        s = new LinkedHashSet();
        t = MinecraftRef.c((byte)0, 0L);
        customPatternRegex = new TextSetting("Custom-pattern-regex", "\\[[A-Z]\\]");
        sortMode = new ModeSetting("Sort-mode", "NAME_COLOR", "PATTERN", "ARMOR_COLOR", "VANILLA", "NONE");
}
}