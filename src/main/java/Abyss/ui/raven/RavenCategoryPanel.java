/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  org.lwjgl.opengl.GL11
 */
package Abyss.ui.raven;

import Abyss.enums.Easing;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.ModuleManager;
import Abyss.module.impl.configuration.ClickGUI;
import Abyss.module.impl.configuration.Theme;
import Abyss.ui.raven.RavenAnimation;
import Abyss.ui.raven.RavenElement;
import Abyss.ui.raven.RavenModuleRow;
import Abyss.util.Animator;
import Abyss.util.MinecraftRef;
import Abyss.util.render.CustomFont;
import Abyss.util.render.RenderUtil;
import java.awt.Color;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class RavenCategoryPanel {
    private boolean x;
    private static long b = 7843458566225L;
    private static int a;
    private Animator H;
    private static int c;
    private float z;
    private int U;
    public boolean V;
    private int q;
    private static int Q;
    private static int C;
    private int P;
    public boolean s;
    public String Z;
    private static long[] d;
    private static long i;
    public int N;
    public int l;
    private int e;
    public Category w;
    private RavenAnimation E;
        public boolean g;
    public int u;
    public boolean D;
    public List<RavenModuleRow> R;

    public void v(int var1) {
        this.e = var1;
}
    public void T(CustomFont var1, long var2) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        int var17 = 86;
        int var22 = 48816;
        int var27 = 47391;
        double var32 = ClickGUI.scale.L();
        this.U = 92;
        int var34 = 0;
        if (!this.R.isEmpty() && this.x) {
            for (RavenElement ravenElement : this.R) {
                var34 += ravenElement.E(101577281637281L);
}
            this.z = var34;
}
        this.H.i(24948514690095L, this.x ? 1.0 : 0.0);
        float var40 = (float)this.H.Z();
        float f = (float)(this.P + this.q + 4) + (float)var34 * var40;
        if (!this.x) {
            if (this.E == null) {
                f = (float)(this.P + this.q) + (float)var34 * var40 + 4.0f;
            } else {
                float var37 = this.E.m(0.0f, this.z, 1);
                f = (float)(this.P + this.q + 4) + this.z - var37;
}
}
        GL11.glPushMatrix();
        RenderUtil.N(1334551664L, '\u549a');
        RenderUtil.P((int)(0.0 * var32), (int)((double)(this.P - 2) * var32), (int)((double)(this.e + this.U + 4) * var32), (int)((double)(f - (float)this.P + 4.0f) * var32));
        RenderUtil.m((float)(this.e - 2), (float)this.P, (float)(this.e + this.U + 2), f, 10.0f, Q, (Integer)Theme.k(22853, 17908, (short)var22).get(0), 45584246178720L, (Integer)Theme.k(22853, 17908, (short)var22).get(1), (Integer)Theme.k(22853, 17908, (short)var22).get(2));
        boolean var28 = this.x || this.g;
        int var29 = this.P + 4;
        int var30 = this.e + 1;
        Category var31 = this.w;
        this.O(18070, 13384080, var31, (byte)var17, var30, var29, var28);
        var1.v(this.D ? this.Z : this.w.x(12139, 2577, (short)var27), this.e + 12, this.P + 4, a, 88827598794260L, false);
        if (!this.D) {
            GL11.glPushMatrix();
            var1.v(this.x ? "-" : "+", this.e + 80, (float)((double)this.P + 4.5), this.x ? C : c, 88827598794260L, false);
            GL11.glPopMatrix();
            if (this.x && !this.R.isEmpty()) {
                for (RavenModuleRow var38 : this.R) {
                    var38.U(99412188383504L);
}
}
}
        RenderUtil.q(112619748911265L);
        GL11.glPopMatrix();
}
    public void S(boolean var1) {
        this.s = var1;
}
    public void y(int var1) {
        this.e = var1;
}
    public void h(int var1, int var2) {
        if (this.V) {
            this.y(var1 - this.u);
            this.k(var2 - this.N);
}
        this.g = this.j(var1, var2);
}
    public boolean h() {
        return this.x;
}
    public int X() {
        return this.e;
}
    public int t() {
        return this.U;
}
    public void M(long var1, char var3) {
        long var4 = (var1 << 16 | (long)var3 << 48 >>> 48) ^ b;
        long var6 = var4 ^ 0x258743D83AE8L;
        int var8 = (int)((var4 ^ 0x2E16D8643795L) >>> 32);
        int var9 = (int)((var4 ^ 0x2E16D8643795L) << 32 >>> 40);
        int var10 = (int)((var4 ^ 0x2E16D8643795L) << 56 >>> 56);
        int var11 = this.q + 3;
        for (RavenElement ravenElement : this.R) {
            ravenElement.i(var11, var8, var9, (byte)var10);
            var11 += ravenElement.E(var6);
}
}
    public void m(boolean var1) {
        this.V = var1;
}
    public void p(int var1) {
        this.P = var1;
}
    public RavenCategoryPanel(int var1, Category var2, char var3, int var4) {
        long var5 = ((long)var1 << 32 | (long)var3 << 48 >>> 32 | (long)var4 << 48 >>> 48) ^ b;
        long var7 = var5 ^ 0x3EA19D0A7A09L;
        this.R = new CopyOnWriteArrayList<RavenModuleRow>();
        this.D = false;
        this.s = false;
        this.g = false;
        this.K(var7, var2);
}
    public RavenCategoryPanel(Category var1, int var2) {
        Q = new Color(0, 0, 0, 110).getRGB();
        a = new Color(220, 220, 220).getRGB();
        C = new Color(250, 95, 85).getRGB();
        c = new Color(135, 238, 144).getRGB();
        this.R = new CopyOnWriteArrayList<RavenModuleRow>();
        this.D = false;
        this.s = false;
        this.g = false;
        this.w = var1;
        this.Z = null;
        this.U = 92;
        this.e = 5;
        this.P = var2;
        this.q = 13;
        this.E = null;
        this.u = 0;
        this.N = 0;
        this.z = 0.0f;
        this.x = false;
        this.V = false;
        this.l = new ScaledResolution(Minecraft.func_71410_x()).func_78325_e();
        this.H = new Animator(Easing.EASE_OUT_QUART, i);
}
    public int ravenWidth() {
        return this.U;
}
    public int ravenHeader() {
        return this.q;
}
    public static long ravenAnimationMillis() {
        return i;
}
    public boolean p(int var1, int var2, long var3) {
        return var1 >= this.e + 77 && var1 <= this.e + this.U - 6 && (float)var2 >= (float)this.P + 2.0f && var2 <= this.P + this.q + 1;
}
    private void K(long var1, Category var3) {
        Q = new Color(0, 0, 0, 110).getRGB();
        a = new Color(220, 220, 220).getRGB();
        C = new Color(250, 95, 85).getRGB();
        c = new Color(135, 238, 144).getRGB();
        this.w = var3;
        this.U = 92;
        this.e = 5;
        this.P = 5;
        this.q = 13;
        this.E = null;
        this.u = 0;
        this.x = false;
        this.V = false;
        int var10 = this.q + 3;
        this.l = new ScaledResolution(MinecraftRef.c((byte)0, 0L)).func_78325_e();
        this.H = new Animator(Easing.EASE_OUT_QUART, i);
        ArrayList var11 = new ArrayList();
        if (var3 == Category.Macro) {
            ArrayList<Module> var12 = new ArrayList<Module>();
            List<Module> var13 = ModuleManager.S;
            int var15 = var13.size();
            for (int var14 = 0; var14 < var15; ++var14) {
                Module var16 = var13.get(var14);
                if (!var16.f().equals((Object)var3)) continue;
                var12.add(var16);
}
            var12.sort(Comparator.comparing(var0 -> Character.valueOf(var0.b().toLowerCase().charAt(5))));
            var11.addAll(var12);
        } else {
            ArrayList<Module> var18 = new ArrayList<Module>();
            List<Module> var20 = ModuleManager.S;
            int var24 = var20.size();
            for (int var22 = 0; var22 < var24; ++var22) {
                Module var25 = var20.get(var22);
                if (!var25.f().equals((Object)this.w)) continue;
                var18.add(var25);
}
            var11.addAll(var18);
}
        for (Module var21 : var11) {
            RavenModuleRow var23 = new RavenModuleRow(29128, var21, this, 19320, 30520, var10);
            this.R.add(var23);
            var10 += 16;
}
}
    public boolean w(int var1, int var2) {
        return var1 >= this.e && var1 <= this.e + this.U && var2 >= this.P && var2 <= this.P + this.q;
}
    private void O(int var1, int var2, Category var3, byte var4, int var5, int var6, boolean var7) {
        long var8 = ((long)var1 << 32 | (long)var2 << 40 >>> 32 | (long)var4 << 56 >>> 56) ^ b;
        int var10 = (int)((var8 ^ 0x8C55D7511CBL) >>> 56);
        RenderItem var13 = MinecraftRef.c((byte)var10, 0L).func_175599_af();
        double var14 = 0.55;
        GlStateManager.func_179094_E();
        GlStateManager.func_179139_a((double)var14, (double)var14, (double)var14);
        ItemStack var16 = null;
        if (var3.equals((Object)Category.Combat)) {
            var16 = new ItemStack(Items.field_151048_u);
        } else if (var3.equals((Object)Category.Movement)) {
            var16 = new ItemStack(Items.field_151008_G);
        } else if (var3.equals((Object)Category.Player)) {
            var16 = new ItemStack(Items.field_151144_bL, 1, 3);
        } else if (var3.equals((Object)Category.World)) {
            var16 = new ItemStack(Item.func_150898_a((Block)Blocks.field_150349_c));
        } else if (var3.equals((Object)Category.Visual)) {
            var16 = new ItemStack(Items.field_151079_bi);
        } else if (var3.equals((Object)Category.Misc)) {
            var16 = new ItemStack(Items.field_151016_H);
        } else if (var3.equals((Object)Category.Configuration)) {
            var16 = new ItemStack(Items.field_151042_j);
        } else if (var3.equals((Object)Category.Macro)) {
            var16 = new ItemStack(Item.func_150898_a((Block)Blocks.field_150367_z));
        } else if (var3.equals((Object)Category.Visual_utility)) {
            var16 = new ItemStack(Items.field_151061_bv);
}
        if (var16 != null) {
            if (var7 && var3 != Category.Player) {
                var16.func_77966_a(Enchantment.field_77347_r, 2);
}
            RenderHelper.func_74520_c();
            GlStateManager.func_179084_k();
            var13.func_180450_b(var16, (int)((double)var5 / var14), (int)((double)var6 / var14));
            GlStateManager.func_179147_l();
            RenderHelper.func_74518_a();
}
        GlStateManager.func_179084_k();
        GlStateManager.func_179152_a((float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.func_179121_F();
}
    public boolean j(int var1, int var2) {
        return var1 >= this.e - 2 && var1 <= this.e + this.U + 2 && (float)var2 >= (float)this.P + 2.0f && var2 <= this.P + this.q + 1;
}
    public boolean g(long var1, int var3, int var4) {
        return var3 >= this.e + 92 - 13 && var3 <= this.e + this.U && (float)var4 >= (float)this.P + 2.0f && var4 <= this.P + this.q + 1;
}
    public int T() {
        return this.P;
}
    public void U(boolean var1, char var2, int var3, short var4) {
        long var5 = ((long)var2 << 48 | (long)var3 << 32 >>> 16 | (long)var4 << 48 >>> 48) ^ b;
        long var7 = var5 ^ 0x333B2E0A86F4L;
        this.x = var1;
        this.E = new RavenAnimation(600.0f);
        this.E.y();
        this.H.O(var7);
        this.H.C(var1 ? 1.0 : 0.0);
}
    public void l(boolean var1) {
        this.x = var1;
}
    public void k(int var1) {
        this.P = var1;
}
    public boolean g() {
        return this.s;
}
    public List<RavenModuleRow> s() {
        return this.R;
}
    public boolean D() {
        return this.x;
}
    static {
        h = new HashMap(13);
        d = new long[]{4761757691821300281L, 5113570216143711950L, 7076288492692668227L, -8175806172379427661L, 8756823661108571545L, 6603669316710787097L, -7818434524661274496L, -3788492994295951298L, 8437643454212387449L, -7487685381110389523L, 3649149712541518431L, 5248284696307956101L, 1213003007859160371L, 695343510205831688L, 5850844154461767343L, -2718821263593996205L, -1754807722676416291L, -3652659546346572275L, -5500673475836309612L, 5904796349296154618L, -427002400845779974L};
        i = 600L;
}
}