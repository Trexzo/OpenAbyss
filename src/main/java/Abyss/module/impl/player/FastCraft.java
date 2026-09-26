/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.client.gui.inventory.GuiCrafting
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.inventory.Container
 *  net.minecraft.inventory.ContainerWorkbench
 *  net.minecraft.inventory.Slot
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  org.lwjgl.input.Mouse
 */
package Abyss.module.impl.player;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.FastCraftBinder;
import Abyss.event.events.PostDrawScreenEvent;
import Abyss.event.events.PreUpdateEvent;
import Abyss.internal.accessor.GuiContainerAccessor;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.setting.Setting;
import Abyss.util.ClientUtil;
import Abyss.util.render.RenderUtil;
import java.awt.Color;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.block.Block;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Mouse;

public class FastCraft
extends Module
implements EventSubscriber {
    private static Map h;
    private static Map d;
    private static String[] c;
    private static long a;
    private static Integer[] g;
    private static String[] b;
    private final int[] J;
    private static long[] e;
    private final int[] s;
        private boolean v;
    private static String[] o;
    private final int[] G;
    private final int[] t;
    
    
    
    private final int[] m;
        private static Object[] n;
    private final List<String> k;
    private boolean N;
    
    private static void a() {
        FastCraft.n[0] = "G@+Fl\u00054";
        FastCraft.n[1] = "=n9\u000f\fK=n.S\u0000D'%$U\u0004O}B9D\f";
        FastCraft.n[2] = Long.TYPE;
        FastCraft.o[2] = "java/lang/Long";
        FastCraft.n[3] = Integer.TYPE;
        FastCraft.o[3] = "java/lang/Integer";
        FastCraft.n[4] = "\u001e)}HR4\u001e)j\u0014^;\u0004bj\nV8\u001e8'\u000bJ1\u0004%y\n^$\u0015>'6S<\t){%P3\u0004>f\nS8\u0002\u0001Y";
        FastCraft.n[5] = "'/\u0016vD('/\u0001*H'=d\u00076](=3L(E 0/\u0010vl/=#\u0016!y-(3\u0007*";
        FastCraft.n[6] = "#G\u0006n,\u001b#G\u00112 \u00149\f\u001b4$\u001fck\u0006%,!9C\u0011+";
        FastCraft.n[7] = "bI`|\fMbIw \u0000Bx\u0002}<\u0017AbX{ \u0018\nOCz&\u0000MbIf\u0005\u000eVgNq<\u0002L";
        FastCraft.n[8] = "O\u0018";
        FastCraft.n[9] = Boolean.TYPE;
        FastCraft.o[9] = "java/lang/Boolean";
        FastCraft.n[10] = "*<T@9t5";
        FastCraft.n[11] = "\t{lHN\">lhB\u0003\u0006)g2^";
        FastCraft.n[12] = Void.TYPE;
        FastCraft.o[12] = "java/lang/Void";
        FastCraft.n[13] = "QO}\u00183sZ@lWR}QKh\r";
        FastCraft.n[14] = "\u000fJ!URcP\u000er1F\nP\u001c{\tLpP\u0014b_>3\bC\"\n\u0005n\u0004\u0012\"1\u00005\u000e\bl\t\u000ezU\u001d\u001cM\u0005hRNvK[qmM}MG1\u0007\fwJ\u0005\n";
        FastCraft.n[15] = "\u0013\u0006+0\u000f\u000e\u0011\u001bu,u\u0018,W*j\u001c\u0006L\t'h\u001cd\u0015\u0002*nN_H\u000e{nu\u0006\u0015Z~<\u001cYQ\t\u001a`\u001e\u0001TY`=O\u001f\u0010g";
        FastCraft.n[16] = "g\u0010i/^\u00018T:KMhy\u00126t\u000f\u0002\u007fL/K\u000b\r5\u0017opV\u0001d\u0017Tq\u000f\b:D74W\u0015j)nvRWhJ+.O\u0007\u0005";
        FastCraft.n[17] = "R4\u0019\u0013H=\u0016%\u0016\u001a/5\u00149\u0012<\u0010kVbB<F[Sb\u0019TB8\u0016:\u0004\u0004/aT?F\u0006L$\f\"\u0016k\u0015f\t`\u0014\bP>\u00140yQ\u0012;V2\u001a\u0014J&\u0006_ESU+\u001b FP\u00142ic\u0014\u0004MeP6\u0004\u0017\u0014[";
}
    private int w(Item var1, int var4) {
        Container var5 = FastCraft.f.thePlayer.openContainer;
        for (int var6 = 10; var6 < var5.inventorySlots.size(); ++var6) {
            Slot var7 = (Slot)var5.inventorySlots.get(var6);
            if (!var7.getHasStack() || var7.getStack().getItem() != var1 || var7.getStack().stackSize < var4) continue;
            return var6;
}
        return -1;
}
    private void getRGB(float var1, float var2, Item var3, long var4, Color var6) {
        var4 = a ^ var4;
        long var7 = var4 ^ 0x423BA88C31DBL;
        long var9 = var4 ^ 0x6AE2723C588FL;
        RenderUtil.c(var9, var1, var2, var1 + 18.0f, var2 + 18.0f, var6.getRGB());
        RenderUtil.m(new ItemStack(var3), (int)(var1 + 1.0f), (int)(var2 + 1.0f));
        RenderUtil.H(var1, var2, var7, var1 + 18.0f, var2 + 18.0f, Color.BLACK.getRGB());
}
    private boolean isWindowClick(ContainerWorkbench var1, int[] var4, Item var5) {
        int var8 = this.w(var5, var4.length);
        if (var8 == -1) {
            return false;
}
        FastCraft.f.playerController.windowClick(var1.windowId, var8, 0, 0, (EntityPlayer)FastCraft.f.thePlayer);
        for (int var12 : var4) {
            FastCraft.f.playerController.windowClick(var1.windowId, var12, 1, 0, (EntityPlayer)FastCraft.f.thePlayer);
}
        FastCraft.f.playerController.windowClick(var1.windowId, var8, 0, 0, (EntityPlayer)FastCraft.f.thePlayer);
        FastCraft.f.playerController.windowClick(var1.windowId, 0, 0, 1, (EntityPlayer)FastCraft.f.thePlayer);
        return true;
}
    private boolean U(int[] var1, Item var4) {
        int var7 = this.w(var4, var1.length);
        return var7 != -1;
}
    private static int d(int var0, long var1) {
        int var3 = var0 ^ (int)(var1 & 0x7FFFL) ^ 0xE51;
        if (g[var3] == null) {
            byte[] var10;
            byte[] var4 = new byte[]{(byte)(var1 >>> 56), (byte)(var1 >>> 48), (byte)(var1 >>> 40), (byte)(var1 >>> 32), (byte)(var1 >>> 24), (byte)(var1 >>> 16), (byte)(var1 >>> 8), (byte)var1};
            long var5 = e[var3];
            byte[] var7 = new byte[]{(byte)(var5 >>> 56), (byte)(var5 >>> 48), (byte)(var5 >>> 40), (byte)(var5 >>> 32), (byte)(var5 >>> 24), (byte)(var5 >>> 16), (byte)(var5 >>> 8), (byte)var5};
            Long var8 = Thread.currentThread().getId();
            Object[] var9 = (Object[])h.get(var8);
            try {
                if (var9 == null) {
                    var9 = new Object[]{Cipher.getInstance("DES/CBC/NoPadding"), SecretKeyFactory.getInstance("DES"), new IvParameterSpec(new byte[8])};
                    h.put(var8, var9);
}
                DESKeySpec var11 = new DESKeySpec(var4);
                SecretKey var12 = ((SecretKeyFactory)var9[1]).generateSecret(var11);
                Cipher var13 = (Cipher)var9[0];
                var13.init(2, (Key)var12, (IvParameterSpec)var9[2]);
                var10 = var13.doFinal(var7);
}
            catch (Exception var14) {
                throw new RuntimeException("Abyss/module/impl/player/FastCraft", var14);
}
            int var15 = (var10[4] & 0xFF) << 24 | (var10[5] & 0xFF) << 16 | (var10[6] & 0xFF) << 8 | var10[7] & 0xFF;
            FastCraft.g[var3] = var15;
}
        return g[var3];
}
    public void onPreUpdate(long var1, PreUpdateEvent var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (FastCraft.f.currentScreen instanceof GuiCrafting) {
            Container var10 = FastCraft.f.thePlayer.openContainer;
            ContainerWorkbench var11 = (ContainerWorkbench)var10;
            if (!this.k.isEmpty()) {
                this.N = true;
                switch (this.k.get(0)) {
                    case "helmet": {
                        if (this.isWindowClick(var11, this.J, Items.iron_ingot)) {
                            ClientUtil.t(48081174263320L, "\u00a7a\u00a7lSuccess \u00a7rCrafted an iron helmet");
                            break;
}
                        ClientUtil.t(48081174263320L, "\u00a7c\u00a7lFailed \u00a7rto craft iron helmet, not enough resources");
                        break;
}
                    case "chestplate": {
                        if (this.isWindowClick(var11, this.s, Items.iron_ingot)) {
                            ClientUtil.t(48081174263320L, "\u00a7a\u00a7lSuccess \u00a7rCrafted an iron chestplate");
                            break;
}
                        ClientUtil.t(48081174263320L, "\u00a7c\u00a7lFailed \u00a7rto craft iron chestplate, not enough resources");
                        break;
}
                    case "leggings": {
                        if (this.isWindowClick(var11, this.G, Items.iron_ingot)) {
                            ClientUtil.t(48081174263320L, "\u00a7a\u00a7lSuccess \u00a7rCrafted an iron leggings");
                            break;
}
                        ClientUtil.t(48081174263320L, "\u00a7c\u00a7lFailed \u00a7rto craft iron leggings, not enough resources");
                        break;
}
                    case "boots": {
                        if (this.isWindowClick(var11, this.m, Items.iron_ingot)) {
                            ClientUtil.t(48081174263320L, "\u00a7a\u00a7lSuccess \u00a7rCrafted an iron boots");
                            break;
}
                        ClientUtil.t(48081174263320L, "\u00a7c\u00a7lFailed \u00a7rto craft iron boots, not enough resources");
                        break;
}
                    case "ladders": {
                        if (this.isWindowClick(var11, this.t, Items.stick)) {
                            ClientUtil.t(48081174263320L, "\u00a7a\u00a7lSuccess \u00a7rCrafted 3 ladders");
                            break;
}
                        ClientUtil.t(48081174263320L, "\u00a7c\u00a7lFailed \u00a7rto craft ladders, not enough resources");
}
}
                this.k.clear();
            } else if (this.N && FastCraft.f.thePlayer.inventory.getItemStack() != null) {
                GuiContainerAccessor.S((GuiContainer)((GuiCrafting)FastCraft.f.currentScreen), null);
                FastCraft.f.thePlayer.inventory.setItemStack(null);
                FastCraft.f.currentScreen.updateScreen();
                this.N = false;
}
        } else {
            this.k.clear();
            this.N = false;
}
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
    public FastCraft(short var1, short var2, int var3) {
        super(((long)var1 << 48 | (long)var2 << 48 >>> 16 | (long)var3 << 32 >>> 32) ^ a ^ 0x658DA168DA3EL);
        this.declare("FastCraft", Category.Player, "Craft some MegaWalls items faster", new Setting[0]);
        this.J = new int[]{1, 2, 3, 4, 6};
        int[] var10001 = new int[]{1, 3, 4, 5, 6, 7, 8, 9};
        this.s = var10001;
        var10001 = new int[]{1, 2, 3, 4, 6, 7, 9};
        this.G = var10001;
        this.m = new int[]{4, 6, 7, 9};
        var10001 = new int[]{1, 3, 4, 5, 6, 7, 9};
        this.t = var10001;
        this.k = new CopyOnWriteArrayList<String>();
        this.v = false;
        this.N = false;
}
    public void onPostDrawScreen(PostDrawScreenEvent var1, long var2) {
        boolean var11 = this.v;
        if (var1.C instanceof GuiCrafting) {
            this.v = Mouse.isButtonDown((int)0);
            boolean var12 = !var11 && this.v;
            GuiCrafting var13 = (GuiCrafting)FastCraft.f.currentScreen;
            int var14 = Mouse.getEventX() * var13.width / FastCraft.f.displayWidth;
            int var15 = var13.height - Mouse.getEventY() * var13.height / FastCraft.f.displayHeight - 1;
            ScaledResolution var16 = var1.s;
            float var17 = (float)var16.getScaledWidth() / 2.0f + (float)(35 * var16.getScaleFactor());
            float var18 = (float)var16.getScaledHeight() / 2.0f - 25.0f * (float)var16.getScaleFactor();
            GlStateManager.pushMatrix();
            GlStateManager.disableLighting();
            GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            this.f((byte)0, var17, 4759559, var18, (Item)Items.iron_helmet, 3568500);
            float var19 = var18;
            float var20 = var18 + 18.0f;
            this.f((byte)0, var17, 4759559, var18 += 25.0f, (Item)Items.iron_chestplate, 3568500);
            float var21 = var18;
            float var22 = var18 + 18.0f;
            this.f((byte)0, var17, 4759559, var18 += 25.0f, (Item)Items.iron_leggings, 3568500);
            float var23 = var18;
            float var24 = var18 + 18.0f;
            this.f((byte)0, var17, 4759559, var18 += 25.0f, (Item)Items.iron_boots, 3568500);
            float var25 = var18;
            float var26 = var18 + 18.0f;
            this.f((byte)0, var17, 4759559, var18 += 25.0f, Item.getItemFromBlock((Block)Blocks.ladder), 3568500);
            float var27 = var18;
            float var28 = var18 + 18.0f;
            float var29 = var17;
            float var30 = var17 + 18.0f;
            GlStateManager.popMatrix();
            if ((float)var14 >= var29 && (float)var14 <= var30) {
                if ((float)var15 >= var19 && (float)var15 <= var20) {
                    this.getRGB(var17, var19, (Item)Items.iron_helmet, 105412507962817L, this.U(this.J, Items.iron_ingot) ? (this.v ? Color.ORANGE : Color.GREEN) : Color.RED);
                    if (var12) {
                        this.k.add("helmet");
}
                } else if ((float)var15 >= var21 && (float)var15 <= var22) {
                    this.getRGB(var17, var21, (Item)Items.iron_chestplate, 105412507962817L, this.U(this.s, Items.iron_ingot) ? (this.v ? Color.ORANGE : Color.GREEN) : Color.RED);
                    if (var12) {
                        this.k.add("chestplate");
}
                } else if ((float)var15 >= var23 && (float)var15 <= var24) {
                    this.getRGB(var17, var23, (Item)Items.iron_leggings, 105412507962817L, this.U(this.G, Items.iron_ingot) ? (this.v ? Color.ORANGE : Color.GREEN) : Color.RED);
                    if (var12) {
                        this.k.add("leggings");
}
                } else if ((float)var15 >= var25 && (float)var15 <= var26) {
                    this.getRGB(var17, var25, (Item)Items.iron_boots, 105412507962817L, this.U(this.m, Items.iron_ingot) ? (this.v ? Color.ORANGE : Color.GREEN) : Color.RED);
                    if (var12) {
                        this.k.add("boots");
}
                } else if ((float)var15 >= var27 && (float)var15 <= var28) {
                    this.getRGB(var17, var27, Item.getItemFromBlock((Block)Blocks.ladder), 105412507962817L, this.U(this.t, Items.stick) ? (this.v ? Color.ORANGE : Color.GREEN) : Color.RED);
                    if (var12) {
                        this.k.add("ladders");
}
}
}
        } else {
            this.v = false;
}
}
    @Override
    public final void x(long var1, EventBus var3) {
        FastCraftBinder.t(var3, this);
}
    private void f(byte var1, float var2, int var3, float var4, Item var5, int var6) {
        long var7 = ((long)var1 << 56 | (long)var3 << 32 >>> 8 | (long)var6 << 40 >>> 40) ^ a;
        long var9 = var7 ^ 0x50078EF9B9BBL;
        this.getRGB(var2, var4, var5, var9, Color.WHITE);
}
    @Override
    public void A(long var1) {
        this.N = false;
        this.k.clear();
}
    private static void zkm$clinit() {
        try {
            n = new Object[18]; o = new String[18]; a(); d = new HashMap(13); long var11 = a ^ 10213001497440L;
            byte[] var10003 = new byte[]{(byte)(var11 >>> 56), 0, 0, 0, 0, 0, 0, 0};
            for (int var14 = 1; var14 < 8; ++var14) { var10003[var14] = (byte)(var11 << var14 * 8 >>> 56); }
            Cipher var13 = Cipher.getInstance("DES/CBC/PKCS5Padding");
            var13.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
            String[] var20 = new String[20];
            int var18 = 0;
            String var17 = ":N\u00e9\u00d1\u00dc\u008f\u001a\u00dc\u00fe\u00a8X\u00ff\u00d6\u0013\u009b\r\u00bc\u0083\u0019\u00a5D\u00cc{\u00a9D\u00ea\u00d4\u00f9\u00eb!\u00aeA\u00e3\u0098qp\u00d6\u0098*V\u00c2M\t)\u00d3\u00e0\u0013!\u00a1\u007f| \u00b0\u00aez=t \u00c1\u00cc\u0087\u00f4g\u001b\u0010\u00c29\u00e8\u00be\u0018\u0083\u0094hP\u00e7\u00c4P\u00f5\u00c9\u00f5\u001a@G\u00c4\u001f\u00c42\u0093\u00e4\u0082\u00b0\u0097RhW\u00f4{\u00b7\u00c4\u009dM\u00e8\u00e9\\E]\u00aa\u0091C\u001c\u00c2n\u00d1J=\u00b0\u0019\u00016\u00e8y\u00ed\u00ecS\u00a6g\u009d\u00f3N>H\u0099`iZ.q\r\u0086\u00b5\u00e8\u00d2\u00a0N\u0018\f\u0010X\u00c2W\u0003`\u0012g\u009d\u00e2\t2\u00d4S}p]\u0010\u0003\u00d9\u0086\u0087\u00a1Me\u009f\u00d4lE\u00cb\u00e0\u00ce\u00f4u\u0018\u00ce\u00f3r4\u00ee:1%\u00962\u00c5.\u00adz\u00ed_\u00e8\u0095\u00d1\u00e8^C\u0089kX\u00d1\u008a\u00a4\u00c2\u007f\u001b\u00e2'\u00a5$g\u00e7\u00f1t;\u0097(\u0014\u00c0e\u00be\u0099Y1c\u00d71\u00d8\u00f8\u00e9\u00b6\u00c6\u00e2\u00d4\u0012\u0005\u00f9\u00ab\u00aaZ\u00f9hPb\u00f5\u0082C u\u00e9\u00a8\u00e8\u0091\u0019\u00e8.HH\u0093\u00b6\u0084\u0014\u00e7Tv\u00f5yB\u0099\u00c1@\u00d7G\u00f8\u0015B\u0086p'\u0015\u00f4\u00d5\u00a1IG\u00ab\u00a5:P\t\u00eb\u0098hg`i\u0001\u0099l\u00c9\u00dc,Y\u00bb(T\u0012p\u00d9\u00b1\b}\u00b8\u0007n\u00bcRi\u00ab\u0085&\u0002\u00b4\u000f\u00bd\u00ecw\u00d93\u00a0\u00d1\u0016\u0099\u001fa\u00ef\u00be\u0018\u0004,\u00a6|\u00b2L5n\u00ca{8\u00f6I\u00d2hE\u00947\u00a9\u00f5\u00f0W\u00a8,\u00bf\u00c44!\u001d\u00cf\u0098h\u008f\u00ca\u001f<Yl\b\u00c9\u001c\u00d8^\u00a5D\u00b0S\u00c01:;\u00e2Ud\bZ\u00d9\u0006l\u0088iu\u001c&AwI8g@\u00b3\u00b0\u008dL\u00c9\u00c8?WG}$\u00dd\u00c3\b\u001az\u00ad8!\u00beq\u00d3\u00fb\u00ffD\b\u00c6z\u00fcx#\u00fa\u00db\u0099e)9\u00c3\u00d0d\u00b7B\u00c1\u00a0\u00bb\u00e1\u00f8\u0006z\u00a5\u00db\r\u00d4\u001f\u0087\u00962\u0086\u0019\u0013\u00e2\u00a1-\u0095\u0012\n@\u00a9j\u0095\u0006M\u00c8|\u0096\u00b7~(W\u0005\u00fa\u0096\u0006Y\u0092\u0006y\u00d7\u0084\u008c\u00e5\u00a9\u00de\u00047\u0086\u00c3\u0096\u00d3\u00c1\u00f0\u00bd\u0011\u0099\tE\u00ab\u00dc#\u0018%\u00d1\u00e7\u000f\u001d\u00b2\u00db\u00eb\u0015\u0013$j\u00a8\u00ea\u00e3\u00f4Eb}M\u00abh!\u00f7d)\u0003\u009b\u00e4(\u0082\u001dR\u000f\u00d2\u00b6}\u0006\u00fe6Dh'Z\u00b3W%#\u00b2h\u00c7l\u00a1\u00a2E\u0004\u00e3\u00f3\u00dc\u00fdF\u0003\u001by(\u001f\u0095@\f\u0082\u00ca\u00d4\u00fa|\u00af\u0089\u0011\u0000{Y\u00a6?\u0098\u00b9\u00be\u00c2\u008f\u0013hw\u00a3\u0010 \u0091N\u00d1\u00b2\u0092&\u001c\u0004\u00cf\u00d5\u0016\u00bb\t \u0004\u00f4\u00e2&XR|\u0016F\u0096\u0084p\u00aa\u0090\u0003e>\u001a}h;Xv\u00afmt\u0000t\u00eb+^`m\u0098\u00b1\b\u00a2\u00d4\u0005Cs=\u00f5\u00b6\u0098H\u00e1\u00ee\u00f5\u00ce32\u00cd\u00e2r\u00de\u00f6\u00bf\u0015\u00c8\n\u00eeh\u001f\u00cfo\u00cf7,,\t\u00e3\u00d8\u0019(\u001f\u0086\u0081ss\u00a4\u00a01i\u009d\u00d5\u00ab\u00c2i~\u00c4\u00ad\u00aax\u00e7\u00baIy\u00ae\u00a6-\u0089ki\u00d3\u00b8;\u00db\u0096\u0017&<R\u00fb\u0001\u0013\u0010\u0012#!\u00f8\u008b\u00c3\u0003\u0018\u00f4c\u00c5\u00a8q\u00fa\u0004\u00f3i\u00d5\u0099\u0097@\u0080\u0094]\u007fY\u00cdF$\b\u00a3\u0004 \u00e4\u0080\u0088\u00b1S*\u0098ER\u00138\u00b8vo[m\u0080\u009a\u00d1NGL\u0084Y\u00b8m\u00a0Wf\u00b2\u00cb@h\u00b4\u00a7\u00fb\r\"}\u0088\u00d4\u00ecN\u008f]\u0019_\u008c\u009b\u00f6Zwr9\u00f8\u0000f\u00aa\u00da\u00d2\u0099\u0080_\u00f5\u000f\t3D\u0000\u008bw\u00c7C\u0095\u00ff\u009exd\u00ce\u001a\u00b4\u00c6\u00f7w\u008a\u0011M\u00b1]6V\u00d3\u008a\u00e4\u00df&X\u00b5j+\u00ba\u00a9U\u00ee\u0080\u00c1\u00b7\u00b5\u00e8\u0014\u0093f\u00ea\u00a4\u00fe\u0005HI\u00bf\u00ca\u00ab\u00b5]2\u00a2Z\u0012\u008379\u00f3\u00ff\u00f7\u0085\u009eo\u0006\u0010\b=\u00d5\u00ed\u008d\u0092\u001c\u00f5\u00dbF'A_\u0090\u00cd\u00c7H\u0018\r\u0087\u00d8: =M\u0005A1&C\u0007!\u00fc\u00bf[\u009e\u00db\u00e1cO\u00d3%\u0099\u00d0..\u00a1\u00e2\u001d\u0094i\u00db\u0099m\u00a4\u0013\u00bc}V\u00b2\u0081\u00d9ep\u00a8|F\r+|&\u00e6?\u0089\u00a1\u00fa\u0017\u001b_\u000bT\u00c3\u0085\u00fa\u00cdrT\u00f7\u0095\u0010\u0080\u0084dB\u0085\u009bZ?\u00b0\u0003X\u00de\bE\rN";
            int var19 = ":N\u00e9\u00d1\u00dc\u008f\u001a\u00dc\u00fe\u00a8X\u00ff\u00d6\u0013\u009b\r\u00bc\u0083\u0019\u00a5D\u00cc{\u00a9D\u00ea\u00d4\u00f9\u00eb!\u00aeA\u00e3\u0098qp\u00d6\u0098*V\u00c2M\t)\u00d3\u00e0\u0013!\u00a1\u007f| \u00b0\u00aez=t \u00c1\u00cc\u0087\u00f4g\u001b\u0010\u00c29\u00e8\u00be\u0018\u0083\u0094hP\u00e7\u00c4P\u00f5\u00c9\u00f5\u001a@G\u00c4\u001f\u00c42\u0093\u00e4\u0082\u00b0\u0097RhW\u00f4{\u00b7\u00c4\u009dM\u00e8\u00e9\\E]\u00aa\u0091C\u001c\u00c2n\u00d1J=\u00b0\u0019\u00016\u00e8y\u00ed\u00ecS\u00a6g\u009d\u00f3N>H\u0099`iZ.q\r\u0086\u00b5\u00e8\u00d2\u00a0N\u0018\f\u0010X\u00c2W\u0003`\u0012g\u009d\u00e2\t2\u00d4S}p]\u0010\u0003\u00d9\u0086\u0087\u00a1Me\u009f\u00d4lE\u00cb\u00e0\u00ce\u00f4u\u0018\u00ce\u00f3r4\u00ee:1%\u00962\u00c5.\u00adz\u00ed_\u00e8\u0095\u00d1\u00e8^C\u0089kX\u00d1\u008a\u00a4\u00c2\u007f\u001b\u00e2'\u00a5$g\u00e7\u00f1t;\u0097(\u0014\u00c0e\u00be\u0099Y1c\u00d71\u00d8\u00f8\u00e9\u00b6\u00c6\u00e2\u00d4\u0012\u0005\u00f9\u00ab\u00aaZ\u00f9hPb\u00f5\u0082C u\u00e9\u00a8\u00e8\u0091\u0019\u00e8.HH\u0093\u00b6\u0084\u0014\u00e7Tv\u00f5yB\u0099\u00c1@\u00d7G\u00f8\u0015B\u0086p'\u0015\u00f4\u00d5\u00a1IG\u00ab\u00a5:P\t\u00eb\u0098hg`i\u0001\u0099l\u00c9\u00dc,Y\u00bb(T\u0012p\u00d9\u00b1\b}\u00b8\u0007n\u00bcRi\u00ab\u0085&\u0002\u00b4\u000f\u00bd\u00ecw\u00d93\u00a0\u00d1\u0016\u0099\u001fa\u00ef\u00be\u0018\u0004,\u00a6|\u00b2L5n\u00ca{8\u00f6I\u00d2hE\u00947\u00a9\u00f5\u00f0W\u00a8,\u00bf\u00c44!\u001d\u00cf\u0098h\u008f\u00ca\u001f<Yl\b\u00c9\u001c\u00d8^\u00a5D\u00b0S\u00c01:;\u00e2Ud\bZ\u00d9\u0006l\u0088iu\u001c&AwI8g@\u00b3\u00b0\u008dL\u00c9\u00c8?WG}$\u00dd\u00c3\b\u001az\u00ad8!\u00beq\u00d3\u00fb\u00ffD\b\u00c6z\u00fcx#\u00fa\u00db\u0099e)9\u00c3\u00d0d\u00b7B\u00c1\u00a0\u00bb\u00e1\u00f8\u0006z\u00a5\u00db\r\u00d4\u001f\u0087\u00962\u0086\u0019\u0013\u00e2\u00a1-\u0095\u0012\n@\u00a9j\u0095\u0006M\u00c8|\u0096\u00b7~(W\u0005\u00fa\u0096\u0006Y\u0092\u0006y\u00d7\u0084\u008c\u00e5\u00a9\u00de\u00047\u0086\u00c3\u0096\u00d3\u00c1\u00f0\u00bd\u0011\u0099\tE\u00ab\u00dc#\u0018%\u00d1\u00e7\u000f\u001d\u00b2\u00db\u00eb\u0015\u0013$j\u00a8\u00ea\u00e3\u00f4Eb}M\u00abh!\u00f7d)\u0003\u009b\u00e4(\u0082\u001dR\u000f\u00d2\u00b6}\u0006\u00fe6Dh'Z\u00b3W%#\u00b2h\u00c7l\u00a1\u00a2E\u0004\u00e3\u00f3\u00dc\u00fdF\u0003\u001by(\u001f\u0095@\f\u0082\u00ca\u00d4\u00fa|\u00af\u0089\u0011\u0000{Y\u00a6?\u0098\u00b9\u00be\u00c2\u008f\u0013hw\u00a3\u0010 \u0091N\u00d1\u00b2\u0092&\u001c\u0004\u00cf\u00d5\u0016\u00bb\t \u0004\u00f4\u00e2&XR|\u0016F\u0096\u0084p\u00aa\u0090\u0003e>\u001a}h;Xv\u00afmt\u0000t\u00eb+^`m\u0098\u00b1\b\u00a2\u00d4\u0005Cs=\u00f5\u00b6\u0098H\u00e1\u00ee\u00f5\u00ce32\u00cd\u00e2r\u00de\u00f6\u00bf\u0015\u00c8\n\u00eeh\u001f\u00cfo\u00cf7,,\t\u00e3\u00d8\u0019(\u001f\u0086\u0081ss\u00a4\u00a01i\u009d\u00d5\u00ab\u00c2i~\u00c4\u00ad\u00aax\u00e7\u00baIy\u00ae\u00a6-\u0089ki\u00d3\u00b8;\u00db\u0096\u0017&<R\u00fb\u0001\u0013\u0010\u0012#!\u00f8\u008b\u00c3\u0003\u0018\u00f4c\u00c5\u00a8q\u00fa\u0004\u00f3i\u00d5\u0099\u0097@\u0080\u0094]\u007fY\u00cdF$\b\u00a3\u0004 \u00e4\u0080\u0088\u00b1S*\u0098ER\u00138\u00b8vo[m\u0080\u009a\u00d1NGL\u0084Y\u00b8m\u00a0Wf\u00b2\u00cb@h\u00b4\u00a7\u00fb\r\"}\u0088\u00d4\u00ecN\u008f]\u0019_\u008c\u009b\u00f6Zwr9\u00f8\u0000f\u00aa\u00da\u00d2\u0099\u0080_\u00f5\u000f\t3D\u0000\u008bw\u00c7C\u0095\u00ff\u009exd\u00ce\u001a\u00b4\u00c6\u00f7w\u008a\u0011M\u00b1]6V\u00d3\u008a\u00e4\u00df&X\u00b5j+\u00ba\u00a9U\u00ee\u0080\u00c1\u00b7\u00b5\u00e8\u0014\u0093f\u00ea\u00a4\u00fe\u0005HI\u00bf\u00ca\u00ab\u00b5]2\u00a2Z\u0012\u008379\u00f3\u00ff\u00f7\u0085\u009eo\u0006\u0010\b=\u00d5\u00ed\u008d\u0092\u001c\u00f5\u00dbF'A_\u0090\u00cd\u00c7H\u0018\r\u0087\u00d8: =M\u0005A1&C\u0007!\u00fc\u00bf[\u009e\u00db\u00e1cO\u00d3%\u0099\u00d0..\u00a1\u00e2\u001d\u0094i\u00db\u0099m\u00a4\u0013\u00bc}V\u00b2\u0081\u00d9ep\u00a8|F\r+|&\u00e6?\u0089\u00a1\u00fa\u0017\u001b_\u000bT\u00c3\u0085\u00fa\u00cdrT\u00f7\u0095\u0010\u0080\u0084dB\u0085\u009bZ?\u00b0\u0003X\u00de\bE\rN".length();
            int var16 = 64;
            int var25 = -1;
            block9: while (true) {
                String var26 = var17.substring(++var25, var25 + var16);
                int var10001 = -1;
                while (true) {
                    byte[] var21 = var13.doFinal(var26.getBytes("ISO-8859-1"));
                    String var37 = FastCraft.b(var21).intern();
                    switch (var10001) {
                        case 0: {
                            var20[var18++] = var37;
                            if ((var25 += var16) >= var19) {
                                b = var20;
                                c = new String[20];
                                h = new HashMap(13);
                                var10003 = new byte[]{(byte)(var11 >>> 56), 0, 0, 0, 0, 0, 0, 0};
                                for (int var1 = 1; var1 < 8; ++var1) {
                                    var10003[var1] = (byte)(var11 << var1 * 8 >>> 56);
}
                                Cipher var0 = Cipher.getInstance("DES/CBC/NoPadding");
                                var0.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
                                long[] var6 = new long[13];
                                int var3 = 0;
                                String var4 = "\u009d$\u00a0\u00d7\u00ec\u009f=\u0019\u00f1\u00a0\u00bb\u00b7D\u00c1 84c;\u00daC-9\u0094\u00d9\u00b25\u0012L\u00f3\u0005\u00b1\u00b1\u0086\u00bd\u00d8\u0083\u00b5\u00ff\u0000\u00bf\u00f1\u00f3\u00b0c\u0083\u0090\u00a1\u000b\u0089\u0092l\u0094b$D\u00f6\u00d7\u00b6\u001eM\u009d\u00aaK\u009d}d?3\u0089\u00db9\u008e\u00a6{\u00ca\u00f8\u00f1\u00f6\u00a9\u00a5\u00c6\u00b1\u0088H\u00fa\u00fdz";
                                int var5 = "\u009d$\u00a0\u00d7\u00ec\u009f=\u0019\u00f1\u00a0\u00bb\u00b7D\u00c1 84c;\u00daC-9\u0094\u00d9\u00b25\u0012L\u00f3\u0005\u00b1\u00b1\u0086\u00bd\u00d8\u0083\u00b5\u00ff\u0000\u00bf\u00f1\u00f3\u00b0c\u0083\u0090\u00a1\u000b\u0089\u0092l\u0094b$D\u00f6\u00d7\u00b6\u001eM\u009d\u00aaK\u009d}d?3\u0089\u00db9\u008e\u00a6{\u00ca\u00f8\u00f1\u00f6\u00a9\u00a5\u00c6\u00b1\u0088H\u00fa\u00fdz".length();
                                int var2 = 0;
                                block12: while (true) {
                                    var10001 = var2;
                                    byte[] var7 = var4.substring(var10001, var2 += 8).getBytes("ISO-8859-1");
                                    long[] var29 = var6;
                                    var10001 = var3++;
                                    long var41 = ((long)var7[0] & 0xFFL) << 56 | ((long)var7[1] & 0xFFL) << 48 | ((long)var7[2] & 0xFFL) << 40 | ((long)var7[3] & 0xFFL) << 32 | ((long)var7[4] & 0xFFL) << 24 | ((long)var7[5] & 0xFFL) << 16 | ((long)var7[6] & 0xFFL) << 8 | (long)var7[7] & 0xFFL;
                                    int var44 = -1;
                                    while (true) {
                                        long var8 = var41;
                                        byte[] var10 = var0.doFinal(new byte[]{(byte)(var8 >>> 56), (byte)(var8 >>> 48), (byte)(var8 >>> 40), (byte)(var8 >>> 32), (byte)(var8 >>> 24), (byte)(var8 >>> 16), (byte)(var8 >>> 8), (byte)var8});
                                        long var46 = ((long)var10[0] & 0xFFL) << 56 | ((long)var10[1] & 0xFFL) << 48 | ((long)var10[2] & 0xFFL) << 40 | ((long)var10[3] & 0xFFL) << 32 | ((long)var10[4] & 0xFFL) << 24 | ((long)var10[5] & 0xFFL) << 16 | ((long)var10[6] & 0xFFL) << 8 | (long)var10[7] & 0xFFL;
                                        switch (var44) {
                                            case 0: {
                                                var29[var10001] = var46;
                                                if (var2 < var5) break;
                                                e = var6;
                                                g = new Integer[13];
                                                return;
}
                                            default: {
                                                var29[var10001] = var46;
                                                if (var2 < var5) continue block12;
                                                var4 = "\u008b\u001fh\u00b8v\u00a4\u00e8\t\u00b3K\u001a\u00b7Y'\u0087\u00d5";
                                                var5 = "\u008b\u001fh\u00b8v\u00a4\u00e8\t\u00b3K\u001a\u00b7Y'\u0087\u00d5".length();
                                                var2 = 0;
}
}
                                        int var35 = var2;
                                        var7 = var4.substring(var35, var2 += 8).getBytes("ISO-8859-1");
                                        var29 = var6;
                                        var10001 = var3++;
                                        var41 = ((long)var7[0] & 0xFFL) << 56 | ((long)var7[1] & 0xFFL) << 48 | ((long)var7[2] & 0xFFL) << 40 | ((long)var7[3] & 0xFFL) << 32 | ((long)var7[4] & 0xFFL) << 24 | ((long)var7[5] & 0xFFL) << 16 | ((long)var7[6] & 0xFFL) << 8 | (long)var7[7] & 0xFFL;
                                        var44 = 0;
}
}
}
                            var16 = var17.charAt(var25);
                            break;
}
                        default: {
                            var20[var18++] = var37;
                            if ((var25 += var16) < var19) {
                                var16 = var17.charAt(var25);
                                continue block9;
}
                            var17 = "\u009e\rR\u00f9~\u001e\u00e3\u009c\u000fj\"\u00b9m\u00e7\u0006\u008d \u00bbyu\u009a4R\u00c8\b|\u00a0\fT\u00b7\u00be\u00ec1\u0084\u00e5vt\u0081<\u00f6\fB\u00a8\u00c5\u00f7\u00c8a\u000e\u00b8";
                            var19 = "\u009e\rR\u00f9~\u001e\u00e3\u009c\u000fj\"\u00b9m\u00e7\u0006\u008d \u00bbyu\u009a4R\u00c8\b|\u00a0\fT\u00b7\u00be\u00ec1\u0084\u00e5vt\u0081<\u00f6\fB\u00a8\u00c5\u00f7\u00c8a\u000e\u00b8".length();
                            var16 = 16;
                            var25 = -1;
}
}
                    var26 = var17.substring(++var25, var25 + var16);
                    var10001 = 0;
}
}
}
        catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException var22) {
            throw new RuntimeException(var22);
}
}
    static {
        a = 78584124819214L;
        zkm$clinit();
}
}