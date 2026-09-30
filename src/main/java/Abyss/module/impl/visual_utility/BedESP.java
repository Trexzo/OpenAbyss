/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.BlockBed
 *  net.minecraft.block.BlockBed$EnumPartType
 *  net.minecraft.block.BlockObsidian
 *  net.minecraft.block.properties.IProperty
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.EnumFacing
 */
package Abyss.module.impl.visual_utility;

import Abyss.AbyssClient;
import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.BedESPBinder;
import Abyss.event.events.Render3DEvent;
import Abyss.internal.accessor.RenderManagerAccessor;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.impl.configuration.Theme;
import Abyss.module.impl.visual_utility.BedESPViewerOffset;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.ColorSetting;
import Abyss.setting.settings.ModeSetting;
import Abyss.setting.settings.NumberSetting;
import Abyss.setting.settings.PercentageSetting;
import Abyss.util.DeferredRendererReload;
import Abyss.util.RaytraceUtil;
import Abyss.util.render.ColorUtil;
import Abyss.util.render.RenderUtil;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockObsidian;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class BedESP
extends Module
implements EventSubscriber {
    private static String[] c;
    private static long a;
    private static String[] b;
    private static Map d;
    private static String[] n;
    
    public static ColorSetting customColor;
    private static Map k;
    private static long[] g;
    public static ModeSetting color;
    
    public static NumberSetting range;
    public static BooleanSetting outline;
    private static Object[] m;
        
    public static PercentageSetting backgroundOpacity;
    
    @Override
    public String g(long var1) {
        return backgroundOpacity.k() + "%";
}
    private boolean c(BlockPos var1, long var2) {
        return RaytraceUtil.Y(var1, range.L(), 119767551018300L);
}
    private void L(AxisAlignedBB var1, int var2, long var3) {
        int var16 = ColorUtil.l(var2, 0L);
        int var17 = ColorUtil.U(0L, var2);
        int var18 = ColorUtil.d(0L, var2);
        int var19 = this.Q();
        if (outline.c()) {
            RenderUtil.X(var1, var16, var17, var18, 255, 1.5f);
}
        RenderUtil.l(var1, var16, var17, var18, var19);
}
    public BedESP(long var1) {
        super(a ^ var1 ^ 0x5E21B35D4440L);
        this.declare("BedESP", Category.Visual_utility, "Show ESP on beds", new Setting[0]);
        var1 = a ^ var1;
}
    private void w(long var1, AxisAlignedBB var3) {
        if (outline.c()) {
            RenderUtil.X(var3, 170, 0, 170, 255, 1.5f);
}
        RenderUtil.l(var3, 170, 0, 170, this.Q());
}
    private void E(BlockPos var1, BlockPos var2, BedESPViewerOffset var3, long var4) {
        AxisAlignedBB var8 = this.z(var1, var2);
        this.w(48528503391664L, this.P(var8, var3));
}
    private List<EnumFacing> I$r1() {
        return Arrays.asList(EnumFacing.UP, EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST);
}
    private void h(long var1, BlockPos var3, BedESPViewerOffset var4) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        IBlockState var9 = BedESP.f.theWorld.getBlockState(var3);
        BlockPos var10 = this.C(var3, var9);
        if (this.I(var10)) {
            this.X(var3, var10, 125221138039108L, var4);
            this.h(var3, var10, 82104404928410L, var4);
}
}
    private AxisAlignedBB z(BlockPos var1, BlockPos var2) {
        return new AxisAlignedBB((double)Math.min(var1.getX(), var2.getX()), (double)var1.getY(), (double)Math.min(var1.getZ(), var2.getZ()), Math.max((double)var1.getX() + 1.0, (double)var2.getX() + 1.0), (double)var1.getY() + 1.0, Math.max((double)var1.getZ() + 1.0, (double)var2.getZ() + 1.0));
}
    private boolean I(BlockPos var1) {
        IBlockState var2 = BedESP.f.theWorld.getBlockState(var1);
        return var2.getBlock() instanceof BlockBed && var2.getValue((IProperty)BlockBed.PART) == BlockBed.EnumPartType.FOOT;
}
    private boolean M(BlockPos var1) {
        return BedESP.f.theWorld.getBlockState(var1).getBlock() instanceof BlockObsidian;
}
    private int Q() {
        return (int)(2.55 * (double)backgroundOpacity.k());
}
    private void X(BlockPos var1, BlockPos var2, long var3, BedESPViewerOffset var5) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        for (EnumFacing var11 : this.I$r1()) {
            BlockPos var12 = var1.offset(var11);
            BlockPos var13 = var2.offset(var11);
            boolean var14 = this.M(var12);
            boolean var15 = this.M(var13);
            if (var14 && var15) {
                this.E(var12, var13, var5, 130796531652272L);
                continue;
}
            if (var14) {
                this.H(var12);
}
            if (!var15) continue;
            this.H(var13);
}
}
    @Override
    public final void x(long var1, EventBus var3) {
        BedESPBinder.Q(var3, this);
}
    private BedESPViewerOffset getRenderManager(long var1) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        return new BedESPViewerOffset(RenderManagerAccessor.k(0L, f.getRenderManager()), RenderManagerAccessor.y(13236, f.getRenderManager()), RenderManagerAccessor.W(0L, f.getRenderManager()), null);
}
    private void H(BlockPos var1) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (outline.c()) {
            RenderUtil.n(var1, 1.0, 170, 0, 170, 114394550953247L, 255, 1.5f);
}
        RenderUtil.C(var1, 1.0, 170, 96914206771396L, 0, 170, this.Q());
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
    private boolean Z(BlockPos var1) {
        IBlockState var2 = BedESP.f.theWorld.getBlockState(var1);
        return var2.getBlock() instanceof BlockBed && var2.getValue((IProperty)BlockBed.PART) == BlockBed.EnumPartType.HEAD;
}
    private static void a() {
        BedESP.m[0] = "HRP\u0015\u0013\bZ";
        BedESP.m[1] = "AG\u001fc}UvP\u001bi0qa[Au";
        BedESP.m[2] = Long.TYPE;
        BedESP.n[2] = "java/lang/Long";
        BedESP.m[3] = "7\u001bT\ftc=";
        BedESP.m[4] = Void.TYPE;
        BedESP.n[4] = "java/lang/Void";
        BedESP.m[5] = "\u0014\u00104\u0003W`\u001f\u001f%L6n\u0014\u0014!\u0016";
        BedESP.m[6] = "(D`2r>!F>][T>\u0000\u007f\"b. \u001c0];4/G9f~;=\u001e\u0000gzk/E|d=l3|;6k*t\u0001o:ooO";
}
    private BlockPos C(BlockPos var1, IBlockState var2) {
        return var1.offset(((EnumFacing)var2.getValue((IProperty)BlockBed.FACING)).getOpposite());
}
    private int z(long var1) {
        switch (color.Y()) {
            case "THEME": {
                return Theme.S(0.0, 35338930340239L);
}
            case "THEME_CUSTOM": {
                return Theme.X(65301174328177L, 0.0);
}
}
        return customColor.k(96531491288662L);
}
    @Override
    public void i(long var1) {
        DeferredRendererReload.request();
}
    private AxisAlignedBB P(AxisAlignedBB var1, BedESPViewerOffset var2) {
        return var1.offset(-BedESPViewerOffset.w(var2), -BedESPViewerOffset.l(var2), -BedESPViewerOffset.Q(var2));
}
    private void h(BlockPos var1, BlockPos var2, long var3, BedESPViewerOffset var5) {
        AxisAlignedBB var10 = this.s(var1, var2);
        AxisAlignedBB var11 = this.P(var10, var5);
        int var12 = this.z(81352373870158L);
        this.L(var11, var12, 10609842623415L);
}
    public void onRender3D(long var1, Render3DEvent var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        BedESPViewerOffset var12 = this.getRenderManager(23715203077604L);
        RenderUtil.L();
        ArrayList<BlockPos> var13 = new ArrayList<BlockPos>();
        for (BlockPos var15 : AbyssClient.G) {
            if (!this.c(var15, 1696358574351L)) continue;
            if (!this.Z(var15)) {
                var13.add(var15);
                continue;
}
            this.h(2988211852082L, var15, var12);
}
        this.forEach(var13);
        RenderUtil.w();
}
    private void forEach(List<BlockPos> var1) {
        if (!var1.isEmpty()) {
            var1.forEach(AbyssClient.G::remove);
}
}
    private AxisAlignedBB s(BlockPos var1, BlockPos var2) {
        return new AxisAlignedBB((double)Math.min(var1.getX(), var2.getX()), (double)var1.getY(), (double)Math.min(var1.getZ(), var2.getZ()), Math.max((double)var1.getX() + 1.0, (double)var2.getX() + 1.0), (double)var1.getY() + 0.5625, Math.max((double)var1.getZ() + 1.0, (double)var2.getZ() + 1.0));
}
    private static void zkm$clinit() {
        try {
            m = new Object[7]; n = new String[7]; a(); d = new HashMap(13); long var11 = a ^ 68154640225343L;
            byte[] var10003 = new byte[]{(byte)(var11 >>> 56), 0, 0, 0, 0, 0, 0, 0};
            for (int var14 = 1; var14 < 8; ++var14) { var10003[var14] = (byte)(var11 << var14 * 8 >>> 56); }
            Cipher var13 = Cipher.getInstance("DES/CBC/PKCS5Padding");
            var13.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
            String[] var20 = new String[2];
            int var18 = 0;
            String var17 = "y3\u00c6\u0006|\u00985\u0015\u0080\u00d3\u0098Z7\u00e9\u00eeX h\u000b\u00f8\u00de\u00e6\u0087\u00d8\u0082J\u001d\u001fOD`\u00c2\u0016S>\u00c7\u00f3\u00e7\u00ec\u00d3\u0000P\u008b\u009b\u008c5\u0097K\u0086";
            int var19 = "y3\u00c6\u0006|\u00985\u0015\u0080\u00d3\u0098Z7\u00e9\u00eeX h\u000b\u00f8\u00de\u00e6\u0087\u00d8\u0082J\u001d\u001fOD`\u00c2\u0016S>\u00c7\u00f3\u00e7\u00ec\u00d3\u0000P\u008b\u009b\u008c5\u0097K\u0086".length();
            int var16 = 16;
            int var15 = -1;
            while (true) {
                byte[] var21 = var13.doFinal(var17.substring(++var15, var15 + var16).getBytes("ISO-8859-1"));
                String var31 = BedESP.b(var21).intern();
                int var10001 = -1;
                var20[var18++] = var31;
                if ((var15 += var16) >= var19) {
                    b = var20;
                    c = new String[2];
                    k = new HashMap(13);
                    var10003 = new byte[]{(byte)(var11 >>> 56), 0, 0, 0, 0, 0, 0, 0};
                    for (int var1 = 1; var1 < 8; ++var1) {
                        var10003[var1] = (byte)(var11 << var1 * 8 >>> 56);
}
                    Cipher var0 = Cipher.getInstance("DES/CBC/NoPadding");
                    var0.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
                    long[] var6 = new long[4];
                    int var3 = 0;
                    String var4 = "\u0094x<\u009b\u00f4?a\u00a8/\u00e1:\u0087\u00ed\u008c\u008a\u0086";
                    int var5 = "\u0094x<\u009b\u00f4?a\u00a8/\u00e1:\u0087\u00ed\u008c\u008a\u0086".length();
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
                                    g = var6;
                                    return;
}
                                default: {
                                    var26[var10001] = var39;
                                    if (var2 < var5) continue block8;
                                    var4 = "\u00d0U\u00180\b\u00cd\u00cb:!U\u00f7\u00f4\u00e4\u00d5\u00d1\u00ae";
                                    var5 = "\u00d0U\u00180\b\u00cd\u00cb:!U\u00f7\u00f4\u00e4\u00d5\u00d1\u00ae".length();
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
        a = 32376492224292L;
        zkm$clinit();
        backgroundOpacity = new PercentageSetting("Background-opacity", 40);
        color = new ModeSetting("Color", "THEME", "THEME_CUSTOM", "CUSTOM");
        customColor = new ColorSetting("Custom-color", "FF0000");
        range = new NumberSetting("Range", 64.0f, 0.0f, 256.0f, 1.0f);
        outline = new BooleanSetting("Outline", false);
}
}