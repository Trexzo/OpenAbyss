/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockBed
 *  net.minecraft.block.BlockBed$EnumPartType
 *  net.minecraft.block.properties.IProperty
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.Vec3i
 *  org.lwjgl.opengl.GL11
 */
package Abyss.module.impl.visual_utility;

import Abyss.AbyssClient;
import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.BedPlatesBinder;
import Abyss.event.events.PreUpdateEvent;
import Abyss.event.events.Render3DEvent;
import Abyss.internal.accessor.RenderManagerAccessor;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.impl.configuration.Font;
import Abyss.module.impl.configuration.Theme;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.ColorSetting;
import Abyss.setting.settings.ModeSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.setting.settings.PercentageSetting;
import Abyss.util.BlockUtil;
import Abyss.util.LunarClientDetector;
import Abyss.util.render.ColorUtil;
import Abyss.util.render.CustomFont;
import Abyss.util.render.RenderUtil;
import java.awt.Color;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3i;
import org.lwjgl.opengl.GL11;

public class BedPlates
extends Module
implements EventSubscriber {
    private static Map h;
    private static String[] c;
    private final int d;
    private final int F;
        public static BooleanSetting outline;
    private final int J;
    private static Map p;
    private final HashMap<BlockPos, Set<Block>> a;
    private static long b;
    private static String[] e;
    public static BooleanSetting fill;
    public static PercentageSetting backgroundOpacity;
    private static Object[] r;
    public static ModeSetting color;
    private static String[] s;
    public static ColorSetting customColor;
    public static NumberSetting surroundingRange;
    
    private static long[] m;

    @Override
    public final void x(long var1, EventBus var3) {
        BedPlatesBinder.y(var3, this);
}
    private BlockPos O(BlockPos var1) {
        IBlockState var2 = BedPlates.f.theWorld.getBlockState(var1);
        if (var2.getBlock() != Blocks.bed) {
            return null;
}
        EnumFacing var3 = (EnumFacing)var2.getValue((IProperty)BlockBed.FACING);
        return var1.offset(var3.getOpposite());
}
    public void onRender3D(Render3DEvent var1, long var2) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        CustomFont var39 = Font.s(0L);
        int var40;
        switch (color.Y()) {
            case "THEME": {
                var40 = Theme.S(0.0, 35338930340239L);
                break;
}
            case "THEME_CUSTOM": {
                var40 = Theme.X(65301174328177L, 0.0);
                break;
}
            default: {
                var40 = customColor.k(96531491288662L);
}
}
        for (Map.Entry entry : this.a.entrySet()) {
            float var63;
            float var62;
            double var50;
            double var48;
            double var46;
            BlockPos var43 = (BlockPos)entry.getKey();
            Set var44 = (Set)entry.getValue();
            BlockPos var45 = this.O(var43);
            if (var45 != null) {
                var46 = (double)(var43.getX() + var45.getX()) / 2.0 + 0.5;
                var48 = (double)(var43.getY() + var45.getY()) / 2.0 + 1.2;
                var50 = (double)(var43.getZ() + var45.getZ()) / 2.0 + 0.5;
            } else {
                var46 = (double)var43.getX() + 0.5;
                var48 = (double)var43.getY() + 1.2;
                var50 = (double)var43.getZ() + 0.5;
}
            double var52 = var46 - RenderManagerAccessor.k(0L, f.getRenderManager());
            double var54 = var48 - RenderManagerAccessor.y(13236, f.getRenderManager());
            double var56 = var50 - RenderManagerAccessor.W(0L, f.getRenderManager());
            GlStateManager.pushMatrix();
            GlStateManager.translate((double)var52, (double)var54, (double)var56);
            GlStateManager.rotate((float)(-BedPlates.f.getRenderManager().playerViewY), (float)0.0f, (float)1.0f, (float)0.0f);
            GlStateManager.rotate((float)BedPlates.f.getRenderManager().playerViewX, (float)(LunarClientDetector.q(0L) ? 1.0f : this.H()), (float)0.0f, (float)0.0f);
            double var58 = f.getRenderViewEntity().getDistance((double)var43.getX(), (double)var43.getY(), (double)var43.getZ());
            double var60 = Math.pow(Math.min(Math.max(var58, 6.0), 128.0), 0.75) * 0.005;
            GlStateManager.scale((double)(-var60), (double)(-var60), (double)var60);
            GlStateManager.disableDepth();
            String var64 = "EMPTY";
            if (var44.isEmpty()) {
                var62 = var39.R(var64, 52019766876817L) + 10.0f;
                var63 = var39.o(60714858652844L) + 10.0f;
            } else {
                var62 = var44.size() * 16 + (var44.size() - 1) * 2 + 10;
                var63 = 26.0f;
}
            float var65 = -(var62 / 2.0f);
            float var66 = -(var63 / 2.0f);
            float var67 = var62 / 2.0f;
            float var68 = var63 / 2.0f;
            int var69 = fill.c() ? new Color(ColorUtil.l(var40, 0L), ColorUtil.U(0L, var40), ColorUtil.d(0L, var40), (int)(2.55 * (double)backgroundOpacity.k())).getRGB() : new Color(0, 0, 0, (int)(2.55 * (double)backgroundOpacity.k())).getRGB();
            if (outline.c()) {
                RenderUtil.m(var65, var66, var67, var68, 3.0f, var69, var40, 45584246178720L, var40, var40);
            } else {
                RenderUtil.j(var65, var66, var67, var68, 3.0f, 4113131265056L, var69);
}
            if (var44.isEmpty()) {
                var39.X(var64, var65 + 5.0f, 90289579616747L, var66 + 5.0f, -1);
            } else {
                float var70 = var65 + 5.0f;
                float var71 = var66 + 5.0f;
                for (Block var73 : (Iterable<Block>)(var44)) {
                    this.U(new ItemStack(Item.getItemFromBlock((Block)var73)), (int)var70, (int)var71);
                    var70 += 18.0f;
}
}
            GlStateManager.enableDepth();
            GlStateManager.popMatrix();
}
}
    public BedPlates(long var1) {
        super(b ^ var1 ^ 0x4CD5C8495472L);
        this.declare("BedPlates", Category.Visual_utility, "Show surrounding blocks of beds", new Setting[0]);
        var1 = b ^ var1;
        this.a = new HashMap();
        this.F = 5;
        this.J = 2;
        this.d = 16;
}
    private static String b(byte[] var0) {
        int var1 = 0;
        int var2;
        char[] var3 = new char[var2 = var0.length];
        for (int var4 = 0; var4 < var2; ++var4) {
            int var5;
            if ((var5 = 255 & var0[var4]) < 192) {
                var3[var1++] = (char)var5;
            } else if (var5 < 224) {
                char var6 = (char)((char)(var5 & 31) << 6);
                byte var8 = var0[++var4];
                var6 = (char)(var6 | (char)(var8 & 63));
                var3[var1++] = var6;
            } else if (var4 < var2 - 2) {
                char var12 = (char)((char)(var5 & 15) << 12);
                byte var9 = var0[++var4];
                var12 = (char)(var12 | (char)(var9 & 63) << 6);
                var9 = var0[++var4];
                var12 = (char)(var12 | (char)(var9 & 63));
                var3[var1++] = var12;
            }
        }
        return new String(var3, 0, var1);
    }
    private static void a() {
        BedPlates.r[0] = "7=1s80\u0011";
        BedPlates.r[1] = "C\u0005z.D\u000bt\u0012~$\t/c\u0019$8";
        BedPlates.r[2] = Short.TYPE;
        BedPlates.s[2] = "java/lang/Short";
        BedPlates.r[3] = Character.TYPE;
        BedPlates.s[3] = "java/lang/Character";
        BedPlates.r[4] = "q^n\u001717G";
        BedPlates.r[5] = Integer.TYPE;
        BedPlates.s[5] = "java/lang/Integer";
        BedPlates.r[6] = Void.TYPE;
        BedPlates.s[6] = "java/lang/Void";
        BedPlates.r[7] = "}b#\u000es\u000fvm2A\u0012\u0001}f6\u001b";
        BedPlates.r[8] = " &\u0011)y.'3(i\u001bmv7Kwa00?C\u0018\"5/!\u0011c*+%=(\"pm#+M#p;pM\u0013)\u007fm:*Z%&hJvOcd3w,V\"pTvsDd`(w0Q}\u001b";
}
    private Set<Block> Q(BlockPos var1, int var2) {
        LinkedHashSet<Block> var3 = new LinkedHashSet<Block>();
        HashSet<BlockPos> var4 = new HashSet<BlockPos>();
        LinkedList<BlockPos> var5 = new LinkedList<BlockPos>();
        var5.add(var1);
        var4.add(var1);
        while (!var5.isEmpty()) {
            BlockPos var6 = (BlockPos)var5.poll();
            for (EnumFacing var10 : EnumFacing.values()) {
                Block var12;
                BlockPos var11 = var6.offset(var10);
                if (var4.contains(var11) || var11.getY() < var1.getY() || var11.distanceSq((Vec3i)var1) > (double)(var2 * var2) || BlockUtil.f(var12 = BedPlates.f.theWorld.getBlockState(var11).getBlock())) continue;
                var4.add(var11);
                var5.add(var11);
                if (var12 == Blocks.bed) continue;
                var3.add(this.E(var12));
}
}
        var3.removeIf(var1x -> !this.h((Block)var1x));
        return var3;
}
    private float H() {
        return BedPlates.f.gameSettings.thirdPersonView == 2 ? -1.0f : 1.0f;
}
    public void onPreUpdate(PreUpdateEvent var1) {
        for (BlockPos var3 : AbyssClient.G) {
            IBlockState var4 = BedPlates.f.theWorld.getBlockState(var3);
            if (!(var4.getBlock() instanceof BlockBed) || var4.getValue((IProperty)BlockBed.PART) != BlockBed.EnumPartType.HEAD) continue;
            LinkedHashSet<Block> var5 = new LinkedHashSet<Block>(this.Q(var3, (int)surroundingRange.L()));
            BlockPos var6 = this.O(var3);
            if (var6 != null) {
                var5.addAll(this.Q(var6, (int)surroundingRange.L()));
}
            this.a.put(var3, var5);
}
        this.a.keySet().removeIf(BlockUtil::a$r1);
}
    private Block E(Block var1) {
        if (var1 == Blocks.wool) {
            return Blocks.wool;
}
        return var1 != Blocks.stained_glass && var1 != Blocks.stained_glass_pane ? var1 : Blocks.glass;
}
    private boolean h(Block var1) {
        return var1 == Blocks.end_stone || var1 == Blocks.wool || var1 == Blocks.glass || var1 == Blocks.planks || var1 == Blocks.log || var1 == Blocks.log2 || var1 == Blocks.obsidian || var1 == Blocks.clay || var1 == Blocks.hardened_clay || var1 == Blocks.stained_hardened_clay || var1 == Blocks.ice || var1 == Blocks.packed_ice;
}
    @Override
    public void A(long var1) {
        this.a.clear();
}
    private void U(ItemStack var1, int var2, int var3) {
        GlStateManager.pushMatrix();
        GlStateManager.depthMask((boolean)true);
        GlStateManager.clear((int)256);
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glDisable((int)2896);
        GlStateManager.pushMatrix();
        GlStateManager.scale((float)1.0f, (float)1.0f, (float)-0.01f);
        BedPlates.f.getRenderItem().zLevel = -150.0f;
        f.getRenderItem().renderItemAndEffectIntoGUI(var1, var2, var3);
        f.getRenderItem().renderItemOverlays(BedPlates.f.fontRendererObj, var1, var2, var3);
        BedPlates.f.getRenderItem().zLevel = 0.0f;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.scale((float)0.5f, (float)0.5f, (float)0.5f);
        GlStateManager.disableDepth();
        GlStateManager.enableDepth();
        GlStateManager.scale((float)2.0f, (float)2.0f, (float)2.0f);
        GlStateManager.popMatrix();
}
    private static void zkm$clinit() {
        try {
            r = new Object[9]; s = new String[9]; a(); h = new HashMap(13);
            long var11 = b ^ 13899860389197L;
            byte[] var10003 = new byte[]{(byte)(var11 >>> 56), 0, 0, 0, 0, 0, 0, 0};
            for (int var14 = 1; var14 < 8; ++var14) { var10003[var14] = (byte)(var11 << var14 * 8 >>> 56); }
            Cipher var13 = Cipher.getInstance("DES/CBC/PKCS5Padding");
            var13.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
            String[] var20 = new String[3];
            int var18 = 0;
            String var17 = "\u00ce\u0000=\u0082\u0098Y\u0096/\u00b2\\\u00d1\u00a7\u00ef\u0014\u00e7<\u00a02\u00e7^\u00b6\u0015\u0098X\n\u00ec\u0098\u00a2\u00c4\u00fb\u0002\u00a5\u0010\t\r\u00d3\\\u008a;\u00e7\u0084\u00ab\u0094&\u001bg\u00fe\u00a7T\u0010\u00ba\u008a\u0010iH\u00c5\u00c72e\u00bc!\u00b7\u0003\u000e/3";
            int var19 = "\u00ce\u0000=\u0082\u0098Y\u0096/\u00b2\\\u00d1\u00a7\u00ef\u0014\u00e7<\u00a02\u00e7^\u00b6\u0015\u0098X\n\u00ec\u0098\u00a2\u00c4\u00fb\u0002\u00a5\u0010\t\r\u00d3\\\u008a;\u00e7\u0084\u00ab\u0094&\u001bg\u00fe\u00a7T\u0010\u00ba\u008a\u0010iH\u00c5\u00c72e\u00bc!\u00b7\u0003\u000e/3".length();
            int var16 = 32;
            int var15 = -1;
            while (true) {
                byte[] var21 = var13.doFinal(var17.substring(++var15, var15 + var16).getBytes("ISO-8859-1"));
                String var31 = BedPlates.b(var21).intern();
                int var10001 = -1;
                var20[var18++] = var31;
                if ((var15 += var16) >= var19) {
                    c = var20;
                    e = new String[3];
                    p = new HashMap(13);
                    var10003 = new byte[]{(byte)(var11 >>> 56), 0, 0, 0, 0, 0, 0, 0};
                    for (int var1 = 1; var1 < 8; ++var1) {
                        var10003[var1] = (byte)(var11 << var1 * 8 >>> 56);
}
                    Cipher var0 = Cipher.getInstance("DES/CBC/NoPadding");
                    var0.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
                    long[] var6 = new long[7];
                    int var3 = 0;
                    String var4 = "\u00b8\u00b3X~F \u00c7\rU\u0092\u00a8g\u00ed\u0012\u001a\u00f8\u00d7\u00cb\u00efaa\u0011F\u009eY\u000e\u0080\u008f4\u00de>\u009az)\u001anLW\u00b7\u007f";
                    int var5 = "\u00b8\u00b3X~F \u00c7\rU\u0092\u00a8g\u00ed\u0012\u001a\u00f8\u00d7\u00cb\u00efaa\u0011F\u009eY\u000e\u0080\u008f4\u00de>\u009az)\u001anLW\u00b7\u007f".length();
                    int var2 = 0;
                    block8: while (true) {
                        var10001 = var2;
                        byte[] var7 = var4.substring(var10001, var2 += 8).getBytes("ISO-8859-1");
                        long[] var26 = var6;
                        var10001 = var3++;
                        long var34 = ((long)var7[0] & 0xFFL) << 56 | ((long)var7[1] & 0xFFL) << 48 | ((long)var7[2] & 0xFFL) << 40 | ((long)var7[3] & 0xFFL) << 32 | ((long)var7[4] & 0xFFL) << 24 | ((long)var7[5] & 0xFFL) << 16 | ((long)var7[6] & 0xFFL) << 8 | (long)var7[7] & 0xFFL;
                        int var37 = -1;
                        while (true) {
                            long var8 = var34;
                            byte[] var10 = var0.doFinal(new byte[]{(byte)(var8 >>> 56), (byte)(var8 >>> 48), (byte)(var8 >>> 40), (byte)(var8 >>> 32), (byte)(var8 >>> 24), (byte)(var8 >>> 16), (byte)(var8 >>> 8), (byte)var8});
                            long var39 = ((long)var10[0] & 0xFFL) << 56 | ((long)var10[1] & 0xFFL) << 48 | ((long)var10[2] & 0xFFL) << 40 | ((long)var10[3] & 0xFFL) << 32 | ((long)var10[4] & 0xFFL) << 24 | ((long)var10[5] & 0xFFL) << 16 | ((long)var10[6] & 0xFFL) << 8 | (long)var10[7] & 0xFFL;
                            switch (var37) {
                                case 0: {
                                    var26[var10001] = var39;
                                    if (var2 < var5) break;
                                    m = var6;
                                    return;
}
                                default: {
                                    var26[var10001] = var39;
                                    if (var2 < var5) continue block8;
                                    var4 = "\u00ec\u0012h\u00a2\u00ba\u00ef\u008a\u00b0\u0010!\u00fcc\u0005Q\u007f\u00fb";
                                    var5 = "\u00ec\u0012h\u00a2\u00ba\u00ef\u008a\u00b0\u0010!\u00fcc\u0005Q\u007f\u00fb".length();
                                    var2 = 0;
}
}
                            int var30 = var2;
                            var7 = var4.substring(var30, var2 += 8).getBytes("ISO-8859-1");
                            var26 = var6;
                            var10001 = var3++;
                            var34 = ((long)var7[0] & 0xFFL) << 56 | ((long)var7[1] & 0xFFL) << 48 | ((long)var7[2] & 0xFFL) << 40 | ((long)var7[3] & 0xFFL) << 32 | ((long)var7[4] & 0xFFL) << 24 | ((long)var7[5] & 0xFFL) << 16 | ((long)var7[6] & 0xFFL) << 8 | (long)var7[7] & 0xFFL;
                            var37 = 0;
}
}
}
                var16 = var17.charAt(var15);
}
}
        catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException var22) {
            throw new RuntimeException(var22);
}
}
    static {
        b = 130834582854816L;
        zkm$clinit();
        backgroundOpacity = new PercentageSetting("Background-opacity", 40);
        customColor = new ColorSetting("Custom-color", "000000");
        outline = new BooleanSetting("Outline", true);
        fill = new BooleanSetting("Fill", false);
        surroundingRange = new NumberSetting("Surrounding-range", 5.0f, 0.0f, 10.0f, 1.0f);
        color = new ModeSetting("Color", false, "CUSTOM", "THEME", "THEME_CUSTOM", "CUSTOM");
}
}