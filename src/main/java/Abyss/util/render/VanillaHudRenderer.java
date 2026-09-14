/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.potion.Potion
 *  net.minecraft.scoreboard.Score
 *  net.minecraft.scoreboard.ScoreObjective
 *  net.minecraft.scoreboard.ScorePlayerTeam
 *  net.minecraft.scoreboard.Scoreboard
 *  net.minecraft.scoreboard.Team
 *  net.minecraft.util.EnumChatFormatting
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.ResourceLocation
 */
package Abyss.util.render;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class VanillaHudRenderer
extends Gui {
    private static final ResourceLocation e = new ResourceLocation("textures/gui/widgets.png");

    private void A(EntityPlayer var1, int var2, int var3) {
        if (var1.func_70055_a(Material.field_151586_h)) {
            int var4 = var1.func_70086_ai();
            int var5 = MathHelper.func_76143_f((double)((double)(var4 - 2) * 10.0 / 300.0));
            int var6 = MathHelper.func_76143_f((double)((double)var4 * 10.0 / 300.0)) - var5;
            int var7 = var3 - 10;
            for (int var8 = 0; var8 < var5 + var6; ++var8) {
                int var9 = var2 - var8 * 8 - 9;
                this.func_73729_b(var9, var7, var8 < var5 ? 16 : 25, 18, 9, 9);
}
}
}
    private void j(Minecraft var1, ScaledResolution var2) {
        Entity var3 = var1.func_175606_aa();
        if (var3 instanceof EntityPlayer && var1.field_71442_b != null && var1.field_71442_b.func_78763_f()) {
            EntityPlayer var4 = (EntityPlayer)var3;
            int var5 = var2.func_78326_a() / 2 - 91;
            int var6 = var2.func_78326_a() / 2 + 91;
            int var7 = var2.func_78328_b() - 39;
            var1.func_110434_K().func_110577_a(Gui.field_110324_m);
            GlStateManager.func_179147_l();
            GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
            this.Y(var4, var5, var7);
            this.I(var4, var5, var7);
            this.h(var4, var6, var7);
            this.A(var4, var6, var7);
            GlStateManager.func_179084_k();
}
}
    private void C(Minecraft var1, ScaledResolution var2) {
        FontRenderer var5;
        ScoreObjective var4;
        Scoreboard var3 = var1.field_71441_e.func_96441_U();
        if (var3 != null && (var4 = this.o(var1, var3)) != null && (var5 = var1.field_71466_p) != null) {
            ArrayList<Score> var6 = new ArrayList<Score>();
            for (Score var8 : var3.func_96534_i(var4)) {
                String var9 = var8.func_96653_e();
                if (var9 == null || var9.startsWith("#")) continue;
                var6.add(var8);
}
            if (!var6.isEmpty()) {
                int var25 = Math.max(0, var6.size() - 15);
                List var26 = var6.subList(var25, var6.size());
                int var27 = var5.func_78256_a(var4.func_96678_d());
                for (Score var11 : var26) {
                    ScorePlayerTeam var12 = var3.func_96509_i(var11.func_96653_e());
                    String var13 = ScorePlayerTeam.func_96667_a((Team)var12, (String)var11.func_96653_e());
                    String var14 = EnumChatFormatting.RED + String.valueOf(var11.func_96652_c());
                    var27 = Math.max(var27, var5.func_78256_a(var13 + ":" + var14));
}
                int var28 = var5.field_78288_b;
                int var29 = var26.size() * var28;
                int var30 = 3;
                int var31 = var2.func_78326_a() - var30 + 2;
                int var32 = var2.func_78326_a() - var27 - var30;
                int var15 = var2.func_78328_b() / 2 + var29 / 3;
                int var16 = 0;
                for (Score var18 : var26) {
                    ScorePlayerTeam var19 = var3.func_96509_i(var18.func_96653_e());
                    String var20 = ScorePlayerTeam.func_96667_a((Team)var19, (String)var18.func_96653_e());
                    String var21 = EnumChatFormatting.RED + "" + var18.func_96652_c();
                    int var22 = var15 - ++var16 * var28;
                    VanillaHudRenderer.func_73734_a((int)(var32 - 2), (int)var22, (int)var31, (int)(var22 + var28), (int)0x50000000);
                    var5.func_78276_b(var20, var32, var22, 0x20FFFFFF);
                    var5.func_78276_b(var21, var31 - var5.func_78256_a(var21), var22, 0x20FFFFFF);
                    if (var16 != var26.size()) continue;
                    String var23 = var4.func_96678_d();
                    int var24 = var22 - var28;
                    VanillaHudRenderer.func_73734_a((int)(var32 - 2), (int)(var24 - 1), (int)var31, (int)(var24 + var28 - 1), (int)0x60000000);
                    VanillaHudRenderer.func_73734_a((int)(var32 - 2), (int)var24, (int)var31, (int)(var24 + var28), (int)0x50000000);
                    var5.func_78276_b(var23, var32 + var27 / 2 - var5.func_78256_a(var23) / 2, var24, 0x20FFFFFF);
}
}
}
}
    private void Y(EntityPlayer var1, int var2, int var3) {
        int var4 = var1.func_70658_aO();
        if (var4 > 0) {
            for (int var5 = 0; var5 < 10; ++var5) {
                int var6 = var2 + var5 * 8;
                if (var5 * 2 + 1 < var4) {
                    this.func_73729_b(var6, var3 - 10, 34, 9, 9, 9);
                    continue;
}
                if (var5 * 2 + 1 == var4) {
                    this.func_73729_b(var6, var3 - 10, 25, 9, 9, 9);
                    continue;
}
                this.func_73729_b(var6, var3 - 10, 16, 9, 9, 9);
}
}
}
    private void i(Minecraft var1, ScaledResolution var2) {
        if (var1.field_71474_y != null && var1.field_71474_y.field_74320_O == 0) {
            var1.func_110434_K().func_110577_a(Gui.field_110324_m);
            GlStateManager.func_179147_l();
            GlStateManager.func_179120_a((int)775, (int)769, (int)1, (int)0);
            int var3 = var2.func_78326_a() / 2 - 7;
            int var4 = var2.func_78328_b() / 2 - 7;
            this.func_73729_b(var3, var4, 0, 0, 16, 16);
            GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
}
}
    private void h(EntityPlayer var1, int var2, int var3) {
        int var4 = var1.func_71024_bL().func_75116_a();
        int var5 = var1.func_70644_a(Potion.field_76438_s) ? 52 : 16;
        for (int var6 = 0; var6 < 10; ++var6) {
            int var7 = var2 - var6 * 8 - 9;
            int var8 = var3;
            this.func_73729_b(var7, var8, 16, 27, 9, 9);
            int var9 = var6 * 2 + 2;
            if (var9 <= var4) {
                this.func_73729_b(var7, var8, var5 + 36, 27, 9, 9);
                continue;
}
            if (var9 - 1 != var4) continue;
            this.func_73729_b(var7, var8, var5 + 45, 27, 9, 9);
}
}
    public void e(Minecraft var1, float var2) {
        if (var1 != null && var1.field_71439_g != null && var1.field_71441_e != null) {
            ScaledResolution var3 = new ScaledResolution(var1);
            var1.field_71460_t.func_78478_c();
            GlStateManager.func_179147_l();
            GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
            this.i(var1, var3);
            this.o(var1, var3);
            this.j(var1, var3);
            this.e(var1, var3);
            this.C(var1, var3);
            GlStateManager.func_179084_k();
            GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
}
}
    private void I(EntityPlayer var1, int var2, int var3) {
        int var4 = MathHelper.func_76123_f((float)var1.func_110143_aJ());
        int var5 = MathHelper.func_76123_f((float)var1.func_110138_aP());
        int var6 = MathHelper.func_76123_f((float)var1.func_110139_bj());
        int var7 = MathHelper.func_76123_f((float)((float)(var5 + var6) / 20.0f));
        int var8 = Math.max(10 - (var7 - 2), 3);
        int var9 = 16;
        if (var1.func_70644_a(Potion.field_76436_u)) {
            var9 = (byte)(var9 + 36);
        } else if (var1.func_70644_a(Potion.field_82731_v)) {
            var9 = (byte)(var9 + 72);
}
        int var10 = MathHelper.func_76123_f((float)((float)(var5 + var6) / 2.0f));
        for (int var11 = var10 - 1; var11 >= 0; --var11) {
            int var12 = MathHelper.func_76123_f((float)((float)(var11 + 1) / 10.0f)) - 1;
            int var13 = var11 % 10;
            int var14 = var2 + var13 * 8;
            int var15 = var3 - var12 * var8;
            this.func_73729_b(var14, var15, 16, 0, 9, 9);
            int var16 = var11 * 2 + 2;
            if (var11 * 2 + 1 < var4) {
                this.func_73729_b(var14, var15, var9 + 36, 0, 9, 9);
                continue;
}
            if (var11 * 2 + 1 == var4) {
                this.func_73729_b(var14, var15, var9 + 45, 0, 9, 9);
                continue;
}
            if (var16 <= var4 + var6) {
                this.func_73729_b(var14, var15, 160, 0, 9, 9);
                continue;
}
            if (var16 - 1 != var4 + var6) continue;
            this.func_73729_b(var14, var15, 169, 0, 9, 9);
}
}
    private void e(Minecraft var1, ScaledResolution var2) {
        EntityPlayerSP var3;
        int var4;
        if (var1.field_71442_b != null && var1.field_71442_b.func_78763_f() && var1.field_71439_g != null && !var1.field_71439_g.func_110317_t() && (var4 = (var3 = var1.field_71439_g).func_71050_bK()) > 0) {
            int var5 = var2.func_78326_a() / 2 - 91;
            int var6 = var2.func_78328_b() - 29;
            var1.func_110434_K().func_110577_a(Gui.field_110324_m);
            this.func_73729_b(var5, var6, 0, 64, 182, 5);
            int var7 = (int)(var3.field_71106_cc * 183.0f);
            if (var7 > 0) {
                this.func_73729_b(var5, var6, 0, 69, var7, 5);
}
            if (var3.field_71068_ca > 0 && var1.field_71466_p != null) {
                String var8 = String.valueOf(var3.field_71068_ca);
                int var9 = var2.func_78326_a() / 2 - var1.field_71466_p.func_78256_a(var8) / 2;
                int var10 = var2.func_78328_b() - 31 - 4;
                var1.field_71466_p.func_78276_b(var8, var9 + 1, var10, 0);
                var1.field_71466_p.func_78276_b(var8, var9 - 1, var10, 0);
                var1.field_71466_p.func_78276_b(var8, var9, var10 + 1, 0);
                var1.field_71466_p.func_78276_b(var8, var9, var10 - 1, 0);
                var1.field_71466_p.func_78276_b(var8, var9, var10, 8453920);
}
}
}
    private void o(Minecraft var1, ScaledResolution var2) {
        Entity var3 = var1.func_175606_aa();
        if (var3 instanceof EntityPlayer) {
            EntityPlayer var4 = (EntityPlayer)var3;
            int var5 = var2.func_78326_a();
            int var6 = var2.func_78328_b();
            int var7 = var5 / 2;
            var1.func_110434_K().func_110577_a(e);
            GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            this.field_73735_i = -90.0f;
            this.func_73729_b(var7 - 91, var6 - 22, 0, 0, 182, 22);
            this.func_73729_b(var7 - 91 - 1 + var4.field_71071_by.field_70461_c * 20, var6 - 23, 0, 22, 24, 22);
            RenderItem var8 = var1.func_175599_af();
            RenderHelper.func_74520_c();
            GlStateManager.func_179091_B();
            GlStateManager.func_179147_l();
            GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
            for (int var9 = 0; var9 < 9; ++var9) {
                int var10 = var7 - 90 + var9 * 20 + 2;
                int var11 = var6 - 19;
                ItemStack var12 = var4.field_71071_by.field_70462_a[var9];
                if (var12 == null) continue;
                var8.func_180450_b(var12, var10, var11);
                var8.func_175030_a(var1.field_71466_p, var12, var10, var11);
}
            GlStateManager.func_179101_C();
            RenderHelper.func_74518_a();
            GlStateManager.func_179140_f();
}
}
    private ScoreObjective o(Minecraft var1, Scoreboard var2) {
        int var5;
        ScoreObjective var3 = null;
        ScorePlayerTeam var4 = var2.func_96509_i(var1.field_71439_g.func_70005_c_());
        if (var4 != null && var4.func_178775_l() != null && (var5 = var4.func_178775_l().func_175746_b()) >= 0) {
            var3 = var2.func_96539_a(3 + var5);
}
        return var3 != null ? var3 : var2.func_96539_a(1);
}
}