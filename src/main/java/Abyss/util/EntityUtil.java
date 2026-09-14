/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.boss.IBossDisplayData
 *  net.minecraft.entity.monster.EntityGolem
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.monster.EntitySilverfish
 *  net.minecraft.entity.passive.IAnimals
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.scoreboard.ScorePlayerTeam
 */
package Abyss.util;

import Abyss.enums.MegaWallsClass;
import Abyss.module.impl.configuration.Teams;
import Abyss.module.impl.misc.AntiBot;
import Abyss.util.HypixelGameState;
import Abyss.util.MinecraftRef;
import Abyss.util.RaytraceUtil;
import Abyss.util.RotationUtil;
import Abyss.util.ScoreboardUtil;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScorePlayerTeam;

public class EntityUtil {
    private static String b;
        private static Minecraft z;

    public static List x(List var0, boolean var1, boolean var2, boolean var3, boolean var4, long var5, boolean var7, boolean var8, boolean var9, boolean var10, boolean var11, boolean var12) {
        long var13 = var5 ^ 0x3DB7A9023BEAL;
        ArrayList<EntityLivingBase> var15 = new ArrayList<EntityLivingBase>(var0.size());
        int var17 = var0.size();
        for (int var16 = 0; var16 < var17; ++var16) {
            EntityLivingBase var18 = (EntityLivingBase)var0.get(var16);
            if (var11 && var18 instanceof EntitySilverfish || var12 && var18 instanceof EntityGolem) {
                if (!var9 && Teams.g(0L, (Entity)var18)) continue;
                var15.add(var18);
                continue;
}
            if (!EntityUtil.q((Entity)var18, var1, var2, var3, var4, var7, var8, var9, var10, var13)) continue;
            var15.add(var18);
}
        return var15;
}
    public static boolean c(long var0, EntityPlayer var2, boolean var3, boolean var4, boolean var5, boolean var6) {
        long var7 = var0 ^ 0x59A5FE18379EL;
        return !EntityUtil.K((Entity)var2) ? false : EntityUtil.g((Entity)var2, var3, var4, var5, var6, var7);
}
    public static List J(List var0, boolean var1, boolean var2, boolean var3, int var4, int var5, boolean var6, byte var7) {
        long var8 = ((long)var4 << 32 | (long)var5 << 40 >>> 32 | (long)var7 << 56 >>> 56) ^ a;
        long var10 = var8 ^ 0x1F969843D00BL;
        ArrayList<EntityPlayer> var12 = new ArrayList<EntityPlayer>(var0.size());
        int var14 = var0.size();
        for (int var13 = 0; var13 < var14; ++var13) {
            EntityPlayer var15 = (EntityPlayer)var0.get(var13);
            if (!EntityUtil.c(var10, var15, var1, var2, var3, var6)) continue;
            var12.add(var15);
}
        return var12;
}
    private static char I(Entity var0, long var1) {
        int var5 = (int)(((var1 = a ^ var1) ^ 0x2EF5CA600EAL) << 48 >>> 48);
        String var6 = ScoreboardUtil.Z((short)var5, var0.func_70005_c_(), b);
        return var6.isEmpty() ? (char)'\u0000' : Character.toLowerCase(var6.charAt(0));
}
    public static List F(double var0, long var2, double var4) {
        var2 = a ^ var2;
        long var6 = var2 ^ 0x724C8CE0BEEEL;
        long var10 = var2 ^ 0x2D320D19E682L;
        List var12 = EntityUtil.z.field_71441_e.field_72996_f;
        ArrayList<EntityLivingBase> var13 = new ArrayList<EntityLivingBase>(var12.size());
        int var15 = var12.size();
        for (int var14 = 0; var14 < var15; ++var14) {
            Entity var16 = (Entity)var12.get(var14);
            if (!EntityUtil.B(0L, var16) || !RaytraceUtil.q(var6, var16, var0) || !RotationUtil.b(var10, var16, var4)) continue;
            var13.add((EntityLivingBase)var16);
}
        return var13;
}
    public static List F2(double var0, long var2, double var4, double var6, List var8) {
        var2 = a ^ var2;
        long var10 = var2 ^ 0x724C8CE0BEEEL;
        long var12 = var2 ^ 0x2D320D19E682L;
        List var14 = EntityUtil.z.field_71441_e.field_72996_f;
        ArrayList<EntityLivingBase> var15 = new ArrayList<EntityLivingBase>(var14.size());
        int var17 = var14.size();
        for (int var16 = 0; var16 < var17; ++var16) {
            Entity var18 = (Entity)var14.get(var16);
            if (!EntityUtil.B(0L, var18) || !RaytraceUtil.q(var10, var18, var0) || !RotationUtil.b(var12, var18, var4)) continue;
            var15.add((EntityLivingBase)var18);
            if (var8 == null || !(var0 <= var6) && !RaytraceUtil.q(var10, var18, var6)) continue;
            var8.add((EntityLivingBase)var18);
}
        return var15;
}
    public static boolean B(long var0, Entity var2) {
        return var2 instanceof EntityLivingBase && EntityUtil.G((EntityLivingBase)var2);
}
    public static List p(double var0, double var2, long var4) {
        var4 = a ^ var4;
        long var6 = var4 ^ 0x72A92D32B075L;
        long var8 = var4 ^ 0x2DD7ACCBE819L;
        List var12 = EntityUtil.z.field_71441_e.field_73010_i;
        ArrayList<EntityPlayer> var13 = new ArrayList<EntityPlayer>(var12.size());
        int var15 = var12.size();
        for (int var14 = 0; var14 < var15; ++var14) {
            EntityPlayer var16 = (EntityPlayer)var12.get(var14);
            if (!EntityUtil.K((Entity)var16) || !RaytraceUtil.q(var6, (Entity)var16, var0) || !RotationUtil.b(var8, (Entity)var16, var2)) continue;
            var13.add(var16);
}
        return var13;
}
    public static boolean g(Entity var0, boolean var1, boolean var2, boolean var3, boolean var4, long var5) {
        boolean var17;
        if (var0 instanceof EntityPlayerSP) {
            return false;
}
        boolean var14 = Teams.l(var0);
        boolean var15 = Teams.Y(var0);
        boolean var16 = Teams.g(0L, var0) || EntityUtil.G(44418141303531L, var0);
        boolean bl = var17 = var0 instanceof EntityPlayer && AntiBot.T((short)0, (EntityPlayer)var0);
        return !(var2 && var15 || var4 && var17 || var1 && var14 || var3 && var16) ? !(!var4 && var17 || !var1 && var14 || !var3 && var16 || !var2 && var15) : true;
}
    private static char W(EntityPlayer var0) {
        ScorePlayerTeam var5 = EntityUtil.z.field_71441_e.func_96441_U().func_96509_i(var0.func_70005_c_());
        if (var5 == null) {
            return '\u0000';
}
        String var6 = ScoreboardUtil.h(var5.func_96668_e(), 0L);
        return var6.isEmpty() ? (char)'\u0000' : Character.toLowerCase(var6.charAt(0));
}
    public static List M(long var0) {
        var0 = a ^ var0;
        return EntityUtil.U(false);
}
    public static boolean G(Entity var0, boolean var1, boolean var2, boolean var3, boolean var4) {
        boolean var8;
        if (var0 instanceof EntityPlayerSP) {
            return false;
}
        if (!(var1 || var2 || var3 || var4)) {
            return false;
}
        boolean var5 = var0 instanceof EntityPlayer;
        boolean var6 = var0 instanceof IBossDisplayData;
        boolean var7 = var0 instanceof EntityMob && !(var0 instanceof IBossDisplayData);
        boolean bl = var8 = var0 instanceof IAnimals && !(var0 instanceof IBossDisplayData) && !(var0 instanceof EntityMob);
        if (!(var1 && var5 || var4 && var6 || var2 && var7 || var3 && var8)) {
            return !(!var1 && var5 || !var4 && var6 || !var2 && var7 || !var3 && var8) ? false : false;
}
        return true;
}
    public static boolean q(Entity var0, boolean var1, boolean var2, boolean var3, boolean var4, boolean var5, boolean var6, boolean var7, boolean var8, long var9) {
        if (!EntityUtil.B(0L, var0)) {
            return false;
}
        return !EntityUtil.G(var0, var1, var2, var3, var4) ? false : EntityUtil.g(var0, var5, var6, var7, var8, 76556605506086L);
}
    public static List K(List var0, boolean var1, long var2, boolean var4, boolean var5, boolean var6, boolean var7, boolean var8, boolean var9, boolean var10) {
        long var11 = var2 ^ 0x60606B20E300L;
        ArrayList<EntityLivingBase> var13 = new ArrayList<EntityLivingBase>(var0.size());
        int var15 = var0.size();
        for (int var14 = 0; var14 < var15; ++var14) {
            EntityLivingBase var16 = (EntityLivingBase)var0.get(var14);
            if (!EntityUtil.q((Entity)var16, var1, var4, var5, var6, var7, var8, var9, var10, var11)) continue;
            var13.add(var16);
}
        return var13;
}
    public static boolean K(Entity var2) {
        return var2 instanceof EntityPlayer && EntityUtil.B(0L, var2);
}
    public static List o(int var0, char var1, short var2, double var3) {
        long var5 = ((long)var0 << 32 | (long)var1 << 48 >>> 32 | (long)var2 << 48 >>> 48) ^ a;
        long var7 = var5 ^ 0x7C98F22F2B28L;
        List var11 = EntityUtil.z.field_71441_e.field_73010_i;
        ArrayList<EntityPlayer> var12 = new ArrayList<EntityPlayer>(var11.size());
        int var14 = var11.size();
        for (int var13 = 0; var13 < var14; ++var13) {
            EntityPlayer var15 = (EntityPlayer)var11.get(var13);
            if (!EntityUtil.K((Entity)var15) || !RaytraceUtil.q(var7, (Entity)var15, var3)) continue;
            var12.add(var15);
}
        return var12;
}
    public static List U(boolean var2) {
        List var7 = EntityUtil.z.field_71441_e.field_73010_i;
        ArrayList<EntityLivingBase> var8 = new ArrayList<EntityLivingBase>(var7.size());
        if (var2) {
            int var15 = var7.size();
            for (int var14 = 0; var14 < var15; ++var14) {
                EntityLivingBase var16 = (EntityLivingBase)var7.get(var14);
                if (!EntityUtil.K((Entity)var16)) continue;
                var8.add(var16);
}
            return var8;
}
        List var9 = EntityUtil.z.field_71441_e.field_72996_f;
        int var11 = var9.size();
        for (int var10 = 0; var10 < var11; ++var10) {
            Entity var12 = (Entity)var9.get(var10);
            if (!EntityUtil.B(0L, var12)) continue;
            var8.add((EntityLivingBase)var12);
}
        return var8;
}
    public static List h(double var0, long var2) {
        long var4 = var2 ^ 0x1DC52E1BA725L;
        List var8 = EntityUtil.z.field_71441_e.field_72996_f;
        ArrayList<EntityLivingBase> var9 = new ArrayList<EntityLivingBase>(var8.size());
        int var11 = var8.size();
        for (int var10 = 0; var10 < var11; ++var10) {
            Entity var12 = (Entity)var8.get(var10);
            if (!EntityUtil.B(0L, var12) || !RaytraceUtil.q(var4, var12, var0)) continue;
            var9.add((EntityLivingBase)var12);
}
        return var9;
}
    private static boolean G(EntityLivingBase var0) {
        return var0 != EntityUtil.z.field_71439_g && !var0.field_70128_L && var0.field_70725_aQ <= 0 && var0.func_110143_aJ() > 0.0f;
}
    private static boolean G(long var0, Entity var2) {
        if (EntityUtil.z.field_71439_g == null || EntityUtil.z.field_71441_e == null || var2 == null) {
            return false;
}
        if (!HypixelGameState.p() && !HypixelGameState.d()) {
            return false;
}
        char var9 = EntityUtil.W((EntityPlayer)EntityUtil.z.field_71439_g);
        if (var9 == '\u0000') {
            return false;
}
        if (var2 instanceof IBossDisplayData) {
            char var13 = EntityUtil.I(var2, 12500867460862L);
            return var13 != '\u0000' && var13 == var9;
}
        if (var2 instanceof EntityPlayer) {
            MegaWallsClass var10 = MegaWallsClass.s(var2.func_70005_c_(), 126433336288858L);
            if (var10 != MegaWallsClass.SHEEP && var10 != MegaWallsClass.ANGEL) {
                return false;
}
            char var11 = EntityUtil.W((EntityPlayer)var2);
            return var11 != '\u0000' && var11 == var9;
}
        return false;
}
    public static List u(int var0, int var1, char var2) {
        List var7 = EntityUtil.z.field_71441_e.field_73010_i;
        ArrayList<EntityPlayer> var8 = new ArrayList<EntityPlayer>(var7.size());
        int var10 = var7.size();
        for (int var9 = 0; var9 < var10; ++var9) {
            EntityPlayer var11 = (EntityPlayer)var7.get(var9);
            if (!EntityUtil.K((Entity)var11)) continue;
            var8.add(var11);
}
        return var8;
}
    static {
        z = MinecraftRef.c((byte)0, 0L);
        b = "Wither";
}
}