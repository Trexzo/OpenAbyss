/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiChat
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.entity.player.EntityPlayer$EnumChatVisibility
 */
package Abyss.module.impl.misc;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.CommandLineBinder;
import Abyss.event.events.PreUpdateEvent;
import Abyss.event.events.Render2DEvent;
import Abyss.internal.accessor.GuiChatAccessor;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.impl.configuration.Theme;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
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
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;

public class CommandLine
extends Module
implements EventSubscriber {
    private static long[] b;
    
    public static BooleanSetting autoFillPrompt;
    
    private static Object[] e;
    public static BooleanSetting autoFill;
    private static String[] g;

                Cipher var2 = Cipher.getInstance("DES/CBC/NoPadding");
            var2.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
            long[] var8 = new long[3];
            int var5 = 0;
            String var6 = "\u00f8\u0002&e\u00ab\u00cb\u0017\u001fX\u0098i*\u00a6\u0084\u00bc3G\u0097\u000f\u00f5]\u00ccN\u00e7";
            int var7 = "\u00f8\u0002&e\u00ab\u00cb\u0017\u001fX\u0098i*\u00a6\u0084\u00bc3G\u0097\u000f\u00f5]\u00ccN\u00e7".length();
            int var4 = 0;
            do {
                long var10004;
                int var10001 = var4;
                byte[] var9 = var6.substring(var10001, var4 += 8).getBytes("ISO-8859-1");
                var10001 = var5++;
                long var10 = ((long)var9[0] & 0xFFL) << 56 | ((long)var9[1] & 0xFFL) << 48 | ((long)var9[2] & 0xFFL) << 40 | ((long)var9[3] & 0xFFL) << 32 | ((long)var9[4] & 0xFFL) << 24 | ((long)var9[5] & 0xFFL) << 16 | ((long)var9[6] & 0xFFL) << 8 | (long)var9[7] & 0xFFL;
                byte[] var12 = var2.doFinal(new byte[]{(byte)(var10 >>> 56), (byte)(var10 >>> 48), (byte)(var10 >>> 40), (byte)(var10 >>> 32), (byte)(var10 >>> 24), (byte)(var10 >>> 16), (byte)(var10 >>> 8), (byte)var10});
                var8[var10001] = var10004 = ((long)var12[0] & 0xFFL) << 56 | ((long)var12[1] & 0xFFL) << 48 | ((long)var12[2] & 0xFFL) << 40 | ((long)var12[3] & 0xFFL) << 32 | ((long)var12[4] & 0xFFL) << 24 | ((long)var12[5] & 0xFFL) << 16 | ((long)var12[6] & 0xFFL) << 8 | (long)var12[7] & 0xFFL;
            } while (var4 < var7);
            b = var8;
}
        catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException var13) {
            throw new RuntimeException(var13);
}
}
    public void onRender2D(int var1, int var2, Render2DEvent var3, int var4) {
        long var5 = ((long)var1 << 32 | (long)var2 << 48 >>> 32 | (long)var4 << 48 >>> 48) ^ a;
        long var7 = var5 ^ 0x5A4089908D33L;
        long var10001 = var5 ^ 0x366E36524274L;
        int var9 = (int)((var5 ^ 0x366E36524274L) >>> 48);
        int var10 = (int)((var5 ^ 0x366E36524274L) << 16 >>> 48);
        int var12 = (int)((var5 ^ 0x52157B5B4E76L) >>> 48);
        int var13 = (int)((var5 ^ 0x52157B5B4E76L) << 16 >>> 48);
        int var14 = (int)((var5 ^ 0x52157B5B4E76L) << 32 >>> 32);
        if (CommandLine.f.field_71462_r instanceof GuiChat) {
            GuiTextField var17 = GuiChatAccessor.z((char)var12, (char)var13, var14, (GuiChat)CommandLine.f.field_71462_r);
            if (var17 == null) {
                return;
}
            String var18 = var17.func_146179_b();
            if (!var18.isEmpty() && var18.charAt(0) == '.') {
                float var20 = CommandLine.f.field_71462_r.field_146295_m - 14;
                Color var15 = new Color(Theme.S(Theme.offset.L(), var7));
                float var16 = CommandLine.f.field_71462_r.field_146295_m - 2;
                RenderUtil.G(2.0f, var20, CommandLine.f.field_71462_r.field_146294_l - 2, (char)var9, var16, (char)var10, var15);
}
}
}
    public CommandLine(long var1) {
        super(a ^ var1 ^ 0x25810403999EL);
        this.declare("CommandLine", Category.Misc, "Configure the client setting by typing command in chat", new Setting[0]);
        var1 = a ^ var1;
}
    private static void a() {
        CommandLine.e[0] = "\u0018=q~^\u00111";
        CommandLine.e[1] = "\u0018Pu.Z+/Gq$\u0017\u000f8L+8";
        CommandLine.e[2] = Long.TYPE;
        CommandLine.g[2] = "java/lang/Long";
        CommandLine.e[3] = "b9^gv&I";
        CommandLine.e[4] = Void.TYPE;
        CommandLine.g[4] = "java/lang/Void";
        CommandLine.e[5] = "[\u0019|\u0014{xP\u0016m[\u001av[\u001di\u0001";
        CommandLine.e[6] = "E\\H`\\2WCL\u000b\u001fB\u0017\u0019\bl\u0002zIMCydx\u0016D\u001cm\u0016:TI\u0002\u000b^.GG\u0012nX9_\u0019r1\u001bzU\u001c\u0010z^,V&";
}
    public void onPreUpdate(PreUpdateEvent var1, long var2) {
        if (CommandLine.f.field_71462_r == null) {
            boolean var6;
            boolean bl = var6 = CommandLine.f.field_71474_y.field_74343_n != EntityPlayer.EnumChatVisibility.HIDDEN;
            if (ClientUtil.b(52, 35207672374243L) && var6) {
                f.func_147108_a((GuiScreen)new GuiChat("."));
}
}
}
    @Override
    public final void x(long var1, EventBus var3) {
        CommandLineBinder.s(var3, this);
}
    static {
        autoFill = new BooleanSetting("Auto-fill", true);
        autoFillPrompt = new BooleanSetting("Auto-fill-prompt", true);
}
}