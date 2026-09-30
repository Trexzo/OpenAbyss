/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 *  org.lwjgl.opengl.GL11
 */
package Abyss.util.render;

import Abyss.util.render.RenderUtil;
import Abyss.util.render.ShaderProgram;
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
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public class ShaderRenderer {
        
        private static long b;
    private static Map h;
    private static String[] c;
    private static long[] f;
    private static String[] d;
    private static Map e;
        private static ShaderProgram N;
    private static String a;
    
    public static void F(float var0, float var1, float var2, long var3, float var5, float var6, int var7, boolean var8, boolean var9, boolean var10, boolean var11) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        var3 = b ^ var3;
        if (!(var2 <= 0.0f) && !(var5 <= 0.0f)) {
            GL11.glPushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc((int)770, (int)771);
            RenderUtil.X();
            N.r();
            N.O("u_size", var2, var5);
            N.O("u_radius", Math.min(var6, Math.min(var2, var5) * 0.5f));
            N.O("u_color", ShaderRenderer.u(var7), ShaderRenderer.W(var7), ShaderRenderer.o(var7), ShaderRenderer.A(var7));
            N.O("u_edges", var8 ? 1.0f : 0.0f, var9 ? 1.0f : 0.0f, var10 ? 1.0f : 0.0f, var11 ? 1.0f : 0.0f);
            ShaderProgram.p(var0, var1, var2, var5);
            N.P();
            GlStateManager.disableBlend();
            RenderUtil.X();
            GL11.glPopMatrix();
}
}
    private static float W(int var0) {
        return (float)(var0 >> 8 & 0xFF) / 255.0f;
}
    private static float A(int var2) {
        return (float)(var2 >> 24 & 0xFF) / 255.0f;
}
    private static float u(int var0) {
        return (float)(var0 >> 16 & 0xFF) / 255.0f;
}
    private static float o(int var2) {
        return (float)(var2 & 0xFF) / 255.0f;
}
    private static String a(byte[] var0) {
        int var1 = 0;
        int var2;
        char[] var3 = new char[var2 = var0.length];
        for (int var4 = 0; var4 < var2; ++var4) {
            int var5;
            if ((var5 = 255 & var0[var4]) < 192) {
                var3[var1++] = (char)var5;
            } else if (var5 < 224) {
                char var6 = (char)((char)(var5 & 31) << 6);
                int var8 = var0[++var4];
                var6 = (char)(var6 | (char)(var8 & 63));
                var3[var1++] = var6;
            } else if (var4 < var2 - 2) {
                char var12 = (char)((char)(var5 & 15) << '\f');
                int var9 = var0[++var4];
                var12 = (char)(var12 | (char)(var9 & 63) << 6);
                var9 = var0[++var4];
                var12 = (char)(var12 | (char)(var9 & 63));
                var3[var1++] = var12;
            }
        }
        return new String(var3, 0, var1);
    }
    private static void zkm$clinit() {
        try {
            long var20 = b ^ 68803848563416L;
            long var22 = var20 ^ 122097270091879L;
            e = new HashMap(13);
            Cipher var11;
            byte[] var10003 = new byte[]{(byte)(var20 >>> 56), 0, 0, 0, 0, 0, 0, 0};
            for (int var12 = 1; var12 < 8; ++var12) {
                var10003[var12] = (byte)(var20 << var12 * 8 >>> 56);
            }
            (var11 = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
            String[] var18 = new String[6];
            int var16 = 0;
            String var15 = "\u00bbB\u00ec\u00cb\u0096\u00ea\u00f3\u009a\u0014o\u00bd6\u00b4\u00f3^\u0019\u00da.;\u00b1\u0080\u00aei\u00ea\u00d3\u00a0\u00ffh\u00b2\u0010R\u00c3\u0010g\u0015x\u0096\u00d5\u00fcD\u00d6\u0090\u0017S\u007f\u0084\u00b9E\u008c\u0400SC\u00f7p\u0081\u0005O{\u00de\u009b\u00c7\u001a\u00d9Y\u00ae\u00a3\u00a0\u00c7\u00e1\u000e)\u009b\tO?!\u0090r,#\u00d0\u001c\u00d7\u0093tn\u00af\u00f8\u00ee\u00bakt\u00af\u00f6\u0082\u0082nVGY\u00fd\u0080.\u00e4*]\u0005Y\\\u00b8\u00b5\u0015\u00ce\u0012R-\u0088N`\u00f5\u0090'\u0007\u00ae\u00f6\u00b9\u0012\u0089\u00a5\u00dc\u00d9B?\u0004\u00fa&\u0018>\u00a1()@\u0092Jz0\u00dd\u0092\u00f4\u009aU\u00ba\u00e2[\u00f1\u0003z\u00faoa\u00b8\u00f9\u00a4\u0003\u00f7g\u00b0l\u00fa\u00e8i\u000ef\u00d7\u00ac\u00c5\u0014[\u0000\u00dff\u009e\u001e\u00c2\u008f\u0012;#\u00e0a\u0090\u000b\u00db\u00ee3\u00c8\r\u0084\u00b2\u009e\"r\u00db\u00fdl\u00ef\u0081\u00c3\u0098\u0088\u008c\u00f4\u000b\u00db\u00de\u0002\u00d8 >\u009a\u001f@\u000b%n\u008e\u00aev\u0016\b{\u00f5\u00bc\u00e8\u0001\u00e3hR\f\u0091{\u00a51\u00dbL\u00bfE\u00ce\u00b8\u0082\u00dc\u0099\u0001\u00ca\u00d4\u0019\u00bf\u00e7\u00d8\u00c3\u0093\u00cc\u00ba\u00ff\u00c9\u00b1q\u008e\u00b4\u00edQ\u00c2[\u00d5\u0092\u00a9:\u0088|\u00b2\u00d3qZk\u00cev\u00b3[K\u00c3>\u00e7;\u0007\u00a3\u00cd\u00aap[\u0081\u00dd\u00f8\r:$\u0012#|\u0087\u000e\u00d8U)\u00d0\u000e\u00a1YR\u000er\u0080\u008e[\b\u0000\u00b3\u00b5\u00be9\u00fa\u00bc4\n,\u00d2\\\u00adjC]]\u00c5\u00ea-\u00e6\u008cE\u00d8\u00a4\u008d.?\u00e2\u00a1\u00edR\u00ecI\u00b6[\u00b9\u0017l\u0016:\u00e1\u0002\u00b7\u0010\u00e6V\u00cfq;\u008c2}\u008dK\u0007}\u00eb\u001c0\u0098\u00a0\n\u00f8\u001f\u0095%)4\u00af$\u00ff\u00fbP\u001e\u0083\"\u00f3\u00f2-\u0019)\\u\u00db~7\u00d5\u00bc\u0090\u00a5aiCb\u00ec\u00bc\u001f\u0094\u00e1\u0084Z\u00b8>\u008a\u0015\u00ba\u00d6\u0012\u008b\u00dfY\u00ce\u00d3\u00e2H\u0006\u00e3\u00aa;\u0092\u00a5\u00ae\u00ad'\u00b8\u00b6\u00b1\u008c\u0088\u0017\u00a0\u0014\u001a\u00bc\u00a6\u00c3\u00acLfx\u0091;\u000b\u00d8*\u009e\u008c~\u00ad\u00a3%\u0016!\u00de0\u0002\u0016\u0090\u00f8\"Z\u00b3\u00f7EE\u00f7\u00d1\u00de\u0017JrW\u0084(Fp] y7\u0001\u00f9\u00a45-\u0081\u00d3\u00b6\u0082g\u00c28\u008c\u00de;^\u00bf\u00ae,\u00ec\u009f\u001d:\u0018D5\u00af9\u0018\u00a6~\u0095\u00bc\u001b\u0013-\u0088n\u00b7\u0086\u00f9v\u007f+\u008e\u00ff\u00af@\u0002]\u001b\u00edB\u001d7\u00a1\u0012Wa\u0004\u0099\u001a\u00107?*\u00e6F\u00f0|\u00b1\u0011\u0089\u0004\u0007\u00bc\u00d1\u000fA:%\u00fcs\u0013\u00f8\u001b\u00cfY\u00ab\u0098;3\u00a9`2\u00b82c(\u0094qk\r\u008e\u0090\u0092\u0096\tY\u008a\u00c8\u00ad\u00ae\u0002\u0006l2\u00bb\u00cajN\u00e2\u008a\u001f\u0013\u00fc\u00d8\u00d0q\u00c2o\u00ce\u0017m?\u00b7\u00fb\u00e2!j\u00dd\r[|I\u00127\u0090\n\u0015\u000b\u00dc\u0099\u007fS\u001c\u00b8\u0006\u0015\u00feU\u001c\u00dd/\u0092:\u0084\u0000Z\u00d7G\u00beA\u00d9(i\u00abQ\u00e5w\u00d5[\u00d4\f\u00b5\u00ffV\u00ad\u00cd\u000eB\u00baW\u00eb\u001fp]H1e\u001c\u00ef\u00db\u00ae\u00b8w\u00fef\\\u0088\u00ed\u00855\u0090~2\u00e8S'M\u008a\u0088\u00b7t\u00ae\u0014|\u00ee\u00954,$F\u00b1\u0084\u0093\u0096==\f\u0004\u00e8h\u00a6q\u0001R\u00d76G\u00ae\u00ff]\u00a4\u0092~\\_\u00ceZ5\u0093J\\\u00aa\u0003.\u008f\u001f[p\u00d9q\u0017\u00cd\u00e9\u00c6\u00a3J\u00ad\u0088\u009eC@\u0094\u00c8\u008e\u00d7k\u00d7\u00bd\u0003\u0018l\u00d0D\u000e|!aKk1\u00cf\u00d0\u00a0\u00b0`\u00a4\u008ep\u00f9\u00a6\u001e\u00a6\u00cb\u00a8\u0006'\u00fc~\u008a\u0015\u0010\u000fg8\u00a2&\u0086\u00c4\t_\u00c6:\u001fi\u00f2\u0082\u0005\u009a\u00d3\u00d9d\u00de\u00ed\u009e\u0098\u009b\u001byV\u00a2#\u00e2\u00eb\u00af0>^S\u00e8\u0016\u00a6\u00de\u00e4L\u009f\u0097\u00f2D\u00d2\u00da\u00be\u0002\u00be8h\u00d6\u0001\u00eaQ?n\u00c1j\u00de\u00feE\u0087\u00ce&h\u00ff\u009c\u00d6f\fx<\u00dd\u00ba\u0006@\u00c80|\u00bb\u0004(>\u00fa14\u00843\u00bdHb\u00ed\u001e5*\u00f7rG\u00aa\u00ae\u00d6\u009aF\u00db\u00b1\u00ae\u00d1\u00a7\u00e56\u00aaU\u00dedt\u00b6\u00ea\u00caE&\u00cd\u00abZ%W-\u00bf\u009d\u00c0;\u00e6\u00bepJm\u0015\u00d1D[/aj\u008cZ\u000e\u0085P\u0088+?A\u00c7\u00f5\u007fU\u00e3\n{\u00b1\u00d0\u00f3EJ\u00d0\u00d0\u00f0\u008a\u00bd0x\u00f1\u00b0\u00ef\u00a4\u00a49\u00d6\u00d0\u001e\u0017\u0011\u0001\u0086\u0007lJ\u0004\u00b5\u00a2\u00c0\u00c0W\u00e5X\u00b3}\u00a0\u008f\u00ae\u00b0X\n\n\u0088\u00ed\u00f2\u0003\u00ac\u0011\u00140\u0096>\u00889;\u0018\u0090\u00d3\u00ba\u000f\u008f\u0098\r\u008c\u0086pX\u00c0\u0002Ir-\u0014\u0015Gn\b\u00e9S\f\u0010\u0084\u00a0w\u00af\u0087\u00b5\u00912>\u0080\u00ed\u00f6[^.\u0086";
            int var17 = "\u00bbB\u00ec\u00cb\u0096\u00ea\u00f3\u009a\u0014o\u00bd6\u00b4\u00f3^\u0019\u00da.;\u00b1\u0080\u00aei\u00ea\u00d3\u00a0\u00ffh\u00b2\u0010R\u00c3\u0010g\u0015x\u0096\u00d5\u00fcD\u00d6\u0090\u0017S\u007f\u0084\u00b9E\u008c\u0400SC\u00f7p\u0081\u0005O{\u00de\u009b\u00c7\u001a\u00d9Y\u00ae\u00a3\u00a0\u00c7\u00e1\u000e)\u009b\tO?!\u0090r,#\u00d0\u001c\u00d7\u0093tn\u00af\u00f8\u00ee\u00bakt\u00af\u00f6\u0082\u0082nVGY\u00fd\u0080.\u00e4*]\u0005Y\\\u00b8\u00b5\u0015\u00ce\u0012R-\u0088N`\u00f5\u0090'\u0007\u00ae\u00f6\u00b9\u0012\u0089\u00a5\u00dc\u00d9B?\u0004\u00fa&\u0018>\u00a1()@\u0092Jz0\u00dd\u0092\u00f4\u009aU\u00ba\u00e2[\u00f1\u0003z\u00faoa\u00b8\u00f9\u00a4\u0003\u00f7g\u00b0l\u00fa\u00e8i\u000ef\u00d7\u00ac\u00c5\u0014[\u0000\u00dff\u009e\u001e\u00c2\u008f\u0012;#\u00e0a\u0090\u000b\u00db\u00ee3\u00c8\r\u0084\u00b2\u009e\"r\u00db\u00fdl\u00ef\u0081\u00c3\u0098\u0088\u008c\u00f4\u000b\u00db\u00de\u0002\u00d8 >\u009a\u001f@\u000b%n\u008e\u00aev\u0016\b{\u00f5\u00bc\u00e8\u0001\u00e3hR\f\u0091{\u00a51\u00dbL\u00bfE\u00ce\u00b8\u0082\u00dc\u0099\u0001\u00ca\u00d4\u0019\u00bf\u00e7\u00d8\u00c3\u0093\u00cc\u00ba\u00ff\u00c9\u00b1q\u008e\u00b4\u00edQ\u00c2[\u00d5\u0092\u00a9:\u0088|\u00b2\u00d3qZk\u00cev\u00b3[K\u00c3>\u00e7;\u0007\u00a3\u00cd\u00aap[\u0081\u00dd\u00f8\r:$\u0012#|\u0087\u000e\u00d8U)\u00d0\u000e\u00a1YR\u000er\u0080\u008e[\b\u0000\u00b3\u00b5\u00be9\u00fa\u00bc4\n,\u00d2\\\u00adjC]]\u00c5\u00ea-\u00e6\u008cE\u00d8\u00a4\u008d.?\u00e2\u00a1\u00edR\u00ecI\u00b6[\u00b9\u0017l\u0016:\u00e1\u0002\u00b7\u0010\u00e6V\u00cfq;\u008c2}\u008dK\u0007}\u00eb\u001c0\u0098\u00a0\n\u00f8\u001f\u0095%)4\u00af$\u00ff\u00fbP\u001e\u0083\"\u00f3\u00f2-\u0019)\\u\u00db~7\u00d5\u00bc\u0090\u00a5aiCb\u00ec\u00bc\u001f\u0094\u00e1\u0084Z\u00b8>\u008a\u0015\u00ba\u00d6\u0012\u008b\u00dfY\u00ce\u00d3\u00e2H\u0006\u00e3\u00aa;\u0092\u00a5\u00ae\u00ad'\u00b8\u00b6\u00b1\u008c\u0088\u0017\u00a0\u0014\u001a\u00bc\u00a6\u00c3\u00acLfx\u0091;\u000b\u00d8*\u009e\u008c~\u00ad\u00a3%\u0016!\u00de0\u0002\u0016\u0090\u00f8\"Z\u00b3\u00f7EE\u00f7\u00d1\u00de\u0017JrW\u0084(Fp] y7\u0001\u00f9\u00a45-\u0081\u00d3\u00b6\u0082g\u00c28\u008c\u00de;^\u00bf\u00ae,\u00ec\u009f\u001d:\u0018D5\u00af9\u0018\u00a6~\u0095\u00bc\u001b\u0013-\u0088n\u00b7\u0086\u00f9v\u007f+\u008e\u00ff\u00af@\u0002]\u001b\u00edB\u001d7\u00a1\u0012Wa\u0004\u0099\u001a\u00107?*\u00e6F\u00f0|\u00b1\u0011\u0089\u0004\u0007\u00bc\u00d1\u000fA:%\u00fcs\u0013\u00f8\u001b\u00cfY\u00ab\u0098;3\u00a9`2\u00b82c(\u0094qk\r\u008e\u0090\u0092\u0096\tY\u008a\u00c8\u00ad\u00ae\u0002\u0006l2\u00bb\u00cajN\u00e2\u008a\u001f\u0013\u00fc\u00d8\u00d0q\u00c2o\u00ce\u0017m?\u00b7\u00fb\u00e2!j\u00dd\r[|I\u00127\u0090\n\u0015\u000b\u00dc\u0099\u007fS\u001c\u00b8\u0006\u0015\u00feU\u001c\u00dd/\u0092:\u0084\u0000Z\u00d7G\u00beA\u00d9(i\u00abQ\u00e5w\u00d5[\u00d4\f\u00b5\u00ffV\u00ad\u00cd\u000eB\u00baW\u00eb\u001fp]H1e\u001c\u00ef\u00db\u00ae\u00b8w\u00fef\\\u0088\u00ed\u00855\u0090~2\u00e8S'M\u008a\u0088\u00b7t\u00ae\u0014|\u00ee\u00954,$F\u00b1\u0084\u0093\u0096==\f\u0004\u00e8h\u00a6q\u0001R\u00d76G\u00ae\u00ff]\u00a4\u0092~\\_\u00ceZ5\u0093J\\\u00aa\u0003.\u008f\u001f[p\u00d9q\u0017\u00cd\u00e9\u00c6\u00a3J\u00ad\u0088\u009eC@\u0094\u00c8\u008e\u00d7k\u00d7\u00bd\u0003\u0018l\u00d0D\u000e|!aKk1\u00cf\u00d0\u00a0\u00b0`\u00a4\u008ep\u00f9\u00a6\u001e\u00a6\u00cb\u00a8\u0006'\u00fc~\u008a\u0015\u0010\u000fg8\u00a2&\u0086\u00c4\t_\u00c6:\u001fi\u00f2\u0082\u0005\u009a\u00d3\u00d9d\u00de\u00ed\u009e\u0098\u009b\u001byV\u00a2#\u00e2\u00eb\u00af0>^S\u00e8\u0016\u00a6\u00de\u00e4L\u009f\u0097\u00f2D\u00d2\u00da\u00be\u0002\u00be8h\u00d6\u0001\u00eaQ?n\u00c1j\u00de\u00feE\u0087\u00ce&h\u00ff\u009c\u00d6f\fx<\u00dd\u00ba\u0006@\u00c80|\u00bb\u0004(>\u00fa14\u00843\u00bdHb\u00ed\u001e5*\u00f7rG\u00aa\u00ae\u00d6\u009aF\u00db\u00b1\u00ae\u00d1\u00a7\u00e56\u00aaU\u00dedt\u00b6\u00ea\u00caE&\u00cd\u00abZ%W-\u00bf\u009d\u00c0;\u00e6\u00bepJm\u0015\u00d1D[/aj\u008cZ\u000e\u0085P\u0088+?A\u00c7\u00f5\u007fU\u00e3\n{\u00b1\u00d0\u00f3EJ\u00d0\u00d0\u00f0\u008a\u00bd0x\u00f1\u00b0\u00ef\u00a4\u00a49\u00d6\u00d0\u001e\u0017\u0011\u0001\u0086\u0007lJ\u0004\u00b5\u00a2\u00c0\u00c0W\u00e5X\u00b3}\u00a0\u008f\u00ae\u00b0X\n\n\u0088\u00ed\u00f2\u0003\u00ac\u0011\u00140\u0096>\u00889;\u0018\u0090\u00d3\u00ba\u000f\u008f\u0098\r\u008c\u0086pX\u00c0\u0002Ir-\u0014\u0015Gn\b\u00e9S\f\u0010\u0084\u00a0w\u00af\u0087\u00b5\u00912>\u0080\u00ed\u00f6[^.\u0086".length();
            int var14 = 32;
            int var27 = -1;
            block9: while (true) {
                String var28 = var15.substring(++var27, var27 + var14);
                int var10001 = -1;
                while (true) {
                    byte[] var19 = var11.doFinal(var28.getBytes("ISO-8859-1"));
                    String var39 = ShaderRenderer.a(var19).intern();
                    switch (var10001) {
                        case 0: {
                            var18[var16++] = var39;
                            if ((var27 += var14) >= var17) {
                                c = var18;
                                d = new String[6];
                                a = "#version 120\nuniform vec2 u_size;\nuniform float u_radius;\nuniform vec4 u_color;\nuniform vec4 u_edges;\n\nvoid main(void)\n{\n    vec2 tex_coord = gl_TexCoord[0].st;\n\n    if (tex_coord.x < 0.5 && tex_coord.y < 0.5 && u_edges.x == 0.0 ||\n        tex_coord.x > 0.5 && tex_coord.y < 0.5 && u_edges.y == 0.0 ||\n        tex_coord.x > 0.5 && tex_coord.y > 0.5 && u_edges.z == 0.0 ||\n        tex_coord.x < 0.5 && tex_coord.y > 0.5 && u_edges.w == 0.0) {\n        gl_FragColor = u_color;\n    } else {\n        gl_FragColor = vec4(u_color.rgb, u_color.a * smoothstep(1.0, 0.0, length(max((abs(tex_coord - 0.5) + 0.5) * u_size - u_size + u_radius, 0.0)) - u_radius + 0.5));\n    }\n}\n";
                                h = new HashMap(13);
                                var10003 = new byte[]{(byte)(var20 >>> 56), 0, 0, 0, 0, 0, 0, 0};
                                for (int var1 = 1; var1 < 8; ++var1) {
                                    var10003[var1] = (byte)(var20 << var1 * 8 >>> 56);
}
                                Cipher var0 = Cipher.getInstance("DES/CBC/NoPadding");
                                var0.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
                                long[] var6 = new long[7];
                                int var3 = 0;
                                String var4 = "\u0004J\rT\u00e5y\u00bc\u00bb\r\u00f4+Ce\u00b9\u00bf\u0084\u0017\u001aMm\u00c0\u009d\u00c52*kKP\u00a3\b\u0018\u00f1a\u00c9\u00b6\u00f2\u00fa,\u00b0\u00d0";
                                int var5 = "\u0004J\rT\u00e5y\u00bc\u00bb\r\u00f4+Ce\u00b9\u00bf\u0084\u0017\u001aMm\u00c0\u009d\u00c52*kKP\u00a3\b\u0018\u00f1a\u00c9\u00b6\u00f2\u00fa,\u00b0\u00d0".length();
                                int var2 = 0;
                                block12: while (true) {
                                    var10001 = var2;
                                    byte[] var7 = var4.substring(var10001, var2 += 8).getBytes("ISO-8859-1");
                                    long[] var31 = var6;
                                    var10001 = var3++;
                                    long var43 = ((long)var7[0] & 0xFFL) << 56 | ((long)var7[1] & 0xFFL) << 48 | ((long)var7[2] & 0xFFL) << 40 | ((long)var7[3] & 0xFFL) << 32 | ((long)var7[4] & 0xFFL) << 24 | ((long)var7[5] & 0xFFL) << 16 | ((long)var7[6] & 0xFFL) << 8 | (long)var7[7] & 0xFFL;
                                    int var46 = -1;
                                    while (true) {
                                        long var8 = var43;
                                        byte[] var10 = var0.doFinal(new byte[]{(byte)(var8 >>> 56), (byte)(var8 >>> 48), (byte)(var8 >>> 40), (byte)(var8 >>> 32), (byte)(var8 >>> 24), (byte)(var8 >>> 16), (byte)(var8 >>> 8), (byte)var8});
                                        long var48 = ((long)var10[0] & 0xFFL) << 56 | ((long)var10[1] & 0xFFL) << 48 | ((long)var10[2] & 0xFFL) << 40 | ((long)var10[3] & 0xFFL) << 32 | ((long)var10[4] & 0xFFL) << 24 | ((long)var10[5] & 0xFFL) << 16 | ((long)var10[6] & 0xFFL) << 8 | (long)var10[7] & 0xFFL;
                                        switch (var46) {
                                            case 0: {
                                                var31[var10001] = var48;
                                                if (var2 < var5) break;
                                                f = var6;
                                                N = new ShaderProgram(var22, "#version 120\nuniform vec2 u_size;\nuniform float u_radius;\nuniform vec4 u_color;\nuniform vec4 u_edges;\n\nvoid main(void)\n{\n    vec2 tex_coord = gl_TexCoord[0].st;\n\n    if (tex_coord.x < 0.5 && tex_coord.y < 0.5 && u_edges.x == 0.0 ||\n        tex_coord.x > 0.5 && tex_coord.y < 0.5 && u_edges.y == 0.0 ||\n        tex_coord.x > 0.5 && tex_coord.y > 0.5 && u_edges.z == 0.0 ||\n        tex_coord.x < 0.5 && tex_coord.y > 0.5 && u_edges.w == 0.0) {\n        gl_FragColor = u_color;\n    } else {\n        gl_FragColor = vec4(u_color.rgb, u_color.a * smoothstep(1.0, 0.0, length(max((abs(tex_coord - 0.5) + 0.5) * u_size - u_size + u_radius, 0.0)) - u_radius + 0.5));\n    }\n}\n");
                                                return;
}
                                            default: {
                                                var31[var10001] = var48;
                                                if (var2 < var5) continue block12;
                                                var4 = "\u00b87F\u0084\u00e9\f\u0012M\u00ec\u0095\u00f2\u00bb\u0088\u0002\u00deS";
                                                var5 = "\u00b87F\u0084\u00e9\f\u0012M\u00ec\u0095\u00f2\u00bb\u0088\u0002\u00deS".length();
                                                var2 = 0;
}
}
                                        int var37 = var2;
                                        var7 = var4.substring(var37, var2 += 8).getBytes("ISO-8859-1");
                                        var31 = var6;
                                        var10001 = var3++;
                                        var43 = ((long)var7[0] & 0xFFL) << 56 | ((long)var7[1] & 0xFFL) << 48 | ((long)var7[2] & 0xFFL) << 40 | ((long)var7[3] & 0xFFL) << 32 | ((long)var7[4] & 0xFFL) << 24 | ((long)var7[5] & 0xFFL) << 16 | ((long)var7[6] & 0xFFL) << 8 | (long)var7[7] & 0xFFL;
                                        var46 = 0;
}
}
}
                            var14 = var15.charAt(var27);
                            break;
}
                        default: {
                            var18[var16++] = var39;
                            if ((var27 += var14) < var17) {
                                var14 = var15.charAt(var27);
                                continue block9;
}
                            var15 = "\u0096\u0017Y>\u00db\u00a1D\u0099\u00f6%\u0083}\u0018\b\u0085\u00c0\u03f0\u00ee\u00a0\u007f\u00f1\u00a3\u00d3\u008f\u00ee\u00c1%\u0092\u00e2\u00b8kZ\u00cc\t\n\u0086;n\u00de\u00d6\u00fd\u00eds\u008c\t\u00aa\u0095\u0014\u00ce\u008b\u00e6\u00f4\u00a7W\u0011\u00ea\u0094R\u00eb\u0097^\u009d|2`\u00b1-M=Tu\u009f\u00bf\u009d=\u00bay\u00c0\u0080\t\u0095\u00ae+\u00a7I\u0086\u008c\u000f\u00aal\u009d0.\u00fa;E\u00d7\u00d6\u00d9\u00a6\u008b\u0092\u000b\u00b5?SDJO\u00df|\u0083[\u00e4\u00c9),[oMwIO\u001a\u001a\u009d\u00e5rf+Nj\u00f7\u0003O\u0081\u00b3 \u00e9\u00fd\u00b7\u0090\u00bf\u00ad;\u00b9\u00c2y\u00fc\u00c8\r\u00bb\u00c4v;\u00d7\u00fc\u00cb\u00b33\u00f3\u008d$\u00f8\u0085\u00b6\u0001\u008c\u00faPZ\u00b1^\u0080\u00e7B#\u00b2\u0083\u008d\u00d5>\u00b4\u00b1;F\u00e0\u00ab\u009b\u00f4\f\u00fcC\u00f4V\u00bfm\u000e\u00d9\u0097l\u0013\u009d\u00ef\u00a4;\u00a6\u0005\u00db|\u00e7c{\u00c2\"\u0015\u00a7\u00dc{\u0084y\u0011\u00a7\u00a93f\u0098\u00db\u0096g\u0002\u00a8Z\u00e0\u00ab\u00caJ\u00b2~\u0092\u0011Nkl\u00a2\u00eeH2\\@O\u0000\u000b\u00df\u00e4\u00a6&K\u0019d\u00df\u00a5\r\u00cf/\u00ed\u00c6\u00db2\u00cd\u00c9\u00bb\u00ccy\u00a7?o\u00ac>h\u0096Wv\u00c8\u00a0\u00cd\u00d9\u0084E\u008d\u00fd\u00e6\u00e7\u00902\u00b1\u00d5(\u00daN\u00cd\u00b5\u009e\u00c8\u001c\u00cd8}\u00a4\u0097A}_\u00b8\u0019cJ\u0019N\u00eb\u009f\u00ab\u00e5\u0094\u00e8w\u00ccX\u00b2\u00aa\\\u00ees\u00ab\u00a5\u001dLB_\u00ae2\u00b1K\u00bc\u0092P\rA\u00c6\u00e1\u0087<\u0080\u00936\u008bYn\b\u0097\u0013\u009fN4\u0091fw\u0089\u0016{B\u00fb _O\u00fe$\u001f\u00de6\u00f1h\u00da\u0001\u00ec\u00c5\u0013\u00b9\u00f3\u00d0\u000b\u00e1\u00cb=*o\u0088\u0001\u0090\u00ac\u0017\u009c\u0007\u008b_\u00aa'\u0004`\f+\u0082\u00fc\u009d\u0088\u0086\u00aa\u00a0\\\u0000\u00d0\u00cc\u00bd\u0091\u0016\u0090\u009d[\u00ba\u00d9m\u00e0\u0011\u00c76\u00b1G\u0098\u00c1\u00ea\u0005'#;fg\u0017S\\%\u00fc\u0006\u009a\u00d8\u00c5\u007f\u001e\u008bY\u009e\u00a6W\u00cf4b\u0092\u00f8\u0007\u00b0\u00ab\u00ab\u0092\u009e)LH\u00f8\u0090rc\u009cB\b\u00d2o\u00c4\u00b3\u0012\u00dcv!\u00ceK,D4/\u00ca\u00e1A_\u00f6\u00d8R\u00af,\u00a0\u0011k-\u0016\u00a2n\u00ea(CA@T\u0095D\u00aam\u00f5&VK\u0014,`7\u00eabC\u00b8\u00d1\u00a3q%s\f\u00c5\u00ad\u0002\u00c0\u0098~\u00e1P:\u0098\u00a4O\u000bn\u00a6\u00bd\u00b9\u00a0\u000eD2\u00c91FV\u001f\u00bfU\u00a5\u00f5\u0085\u0082x\u00f9\n\u00b3\u00f0T2\u00a3\t\u00d4W\u00e8\u00b7+r\u00d6%\u0003,Xf]\u00b08\u00a7\u00f7\u00ab8E\u00eb\u008c\"\u0087W\u0018\u00dd\u001c\u0090\b&L\u00a3\u00e3\u00e5:\u00f3\u00b9\u0005\u00e7H\u0092\u00a5\u00bc\b-\u00ff\u0088\u00f3\u007f\u0081\u00baZ\u0088\u009bC\u00bdY\u0004\u00b2l\u00a5\n\u00fb\u00f56\u00f9\u00d3Sn\u008c\u00f6\u00c5\u00e7\u00d6\u0016k\u0088\u00c9:\u00dc\u0013\"\u00c3\u00a9\u00ef\u00d5\u00c7j\u00eb(D\b>\u0002]\\\u0082L/\u0099\u0090z\u00c0\u0080\u00ceG%\u00b2\u008f\\H\u00fc\n-Z}\u008c\u0092\u00e1P\u00a54\u00ea\u0011\u00a1/\u0084\u0085\f'\u001cB\u00c8-\u0012\u00f6\u00b7z\u00da\u0001\u00ad\u0089TJ,\u00bf\u00acLJ\u00b5t\u00f2R\u00cfvO\u0099\u0004\u000e\u0091\u00f0\u00faB\t\u00a38\u00b3x\u00a04\u00db\u00db\u008e\r\u00f5g'\u00dc\u0082\u00811\u00b9F,\u0084\u0001\u00dd\"\u0090\u00da\u0004\u00f5\u0095\u00f8\u0005\u00f2\u00f7\u008bE\u00fb\u00ca\u00b4\u00c6<\u0002u\u00c5^\u00e7\u0015\u007fs\nK\u009b?\u00b4=Ia\u0089\u00cdD\u00f6\u0093\u0016\u0004\u0090\u00eb\u00b6\u00f6\u009b\u00f2\u0097\u00d2\u00d0T\u008e8\u00f2\u009f\u00f3\u00e4\u0097\u0083\u0083\u00b2\u00bdm\u0004\u00b8u\u00c4v\u009c\u00c8\u0017\u0012\u00ac\u0011\u000f\u00d2o\u00f3\u00dd\u0086\u009b\n\u00fb\u00a9t(Q\r\u0019\u00d8\u0003\n\u0001!0r\u00e42W\u00c1z\u00ce\u00bf\u0086\u00e1\u009e\u00bf\u0096\bD\u00da[\u00ads\u00e4\u009c\u00f4\u0099\u00b9\u0098\u00e5-->\u00de\u009e\u0082Y\u00f8\u0091JKw\u0010\u009e\u00d0\u00b8\u00c4\u00ad\u008cQ\u00a6\u0010Q\u00edJR\u0097\u00ea\u00cc\u0096%M\u00db\u00ear2X\u00de\u00bf\u0083g\u008d\u00a8x\u00fe\u0084\u00ce\t\u00bee6q\u00f2\u00bf\u000bH\u00fdFU{\u00be\u00d0IV`,|(\u00ca\u00d0y\u00ca\u0090\u00d0\u0004\u00c3E5cJ0y\u00e5zBe\u008c\u00e5f\u0012\u0003\u0001\u00bez#\u001f4\u00bb\u00c0\u00c0\u00e1.\"~)\u00cc\u00da=\u008e\u00b6\u00ce;\u00f4:\u00aav\u00ff\u0099\u00aa\u00d2\nR\u0095\u00a3\u00e8\u00b12\u0094\u00c3\u00b4\u00bdd\u00f8T\u00df";
                            var17 = "\u0096\u0017Y>\u00db\u00a1D\u0099\u00f6%\u0083}\u0018\b\u0085\u00c0\u03f0\u00ee\u00a0\u007f\u00f1\u00a3\u00d3\u008f\u00ee\u00c1%\u0092\u00e2\u00b8kZ\u00cc\t\n\u0086;n\u00de\u00d6\u00fd\u00eds\u008c\t\u00aa\u0095\u0014\u00ce\u008b\u00e6\u00f4\u00a7W\u0011\u00ea\u0094R\u00eb\u0097^\u009d|2`\u00b1-M=Tu\u009f\u00bf\u009d=\u00bay\u00c0\u0080\t\u0095\u00ae+\u00a7I\u0086\u008c\u000f\u00aal\u009d0.\u00fa;E\u00d7\u00d6\u00d9\u00a6\u008b\u0092\u000b\u00b5?SDJO\u00df|\u0083[\u00e4\u00c9),[oMwIO\u001a\u001a\u009d\u00e5rf+Nj\u00f7\u0003O\u0081\u00b3 \u00e9\u00fd\u00b7\u0090\u00bf\u00ad;\u00b9\u00c2y\u00fc\u00c8\r\u00bb\u00c4v;\u00d7\u00fc\u00cb\u00b33\u00f3\u008d$\u00f8\u0085\u00b6\u0001\u008c\u00faPZ\u00b1^\u0080\u00e7B#\u00b2\u0083\u008d\u00d5>\u00b4\u00b1;F\u00e0\u00ab\u009b\u00f4\f\u00fcC\u00f4V\u00bfm\u000e\u00d9\u0097l\u0013\u009d\u00ef\u00a4;\u00a6\u0005\u00db|\u00e7c{\u00c2\"\u0015\u00a7\u00dc{\u0084y\u0011\u00a7\u00a93f\u0098\u00db\u0096g\u0002\u00a8Z\u00e0\u00ab\u00caJ\u00b2~\u0092\u0011Nkl\u00a2\u00eeH2\\@O\u0000\u000b\u00df\u00e4\u00a6&K\u0019d\u00df\u00a5\r\u00cf/\u00ed\u00c6\u00db2\u00cd\u00c9\u00bb\u00ccy\u00a7?o\u00ac>h\u0096Wv\u00c8\u00a0\u00cd\u00d9\u0084E\u008d\u00fd\u00e6\u00e7\u00902\u00b1\u00d5(\u00daN\u00cd\u00b5\u009e\u00c8\u001c\u00cd8}\u00a4\u0097A}_\u00b8\u0019cJ\u0019N\u00eb\u009f\u00ab\u00e5\u0094\u00e8w\u00ccX\u00b2\u00aa\\\u00ees\u00ab\u00a5\u001dLB_\u00ae2\u00b1K\u00bc\u0092P\rA\u00c6\u00e1\u0087<\u0080\u00936\u008bYn\b\u0097\u0013\u009fN4\u0091fw\u0089\u0016{B\u00fb _O\u00fe$\u001f\u00de6\u00f1h\u00da\u0001\u00ec\u00c5\u0013\u00b9\u00f3\u00d0\u000b\u00e1\u00cb=*o\u0088\u0001\u0090\u00ac\u0017\u009c\u0007\u008b_\u00aa'\u0004`\f+\u0082\u00fc\u009d\u0088\u0086\u00aa\u00a0\\\u0000\u00d0\u00cc\u00bd\u0091\u0016\u0090\u009d[\u00ba\u00d9m\u00e0\u0011\u00c76\u00b1G\u0098\u00c1\u00ea\u0005'#;fg\u0017S\\%\u00fc\u0006\u009a\u00d8\u00c5\u007f\u001e\u008bY\u009e\u00a6W\u00cf4b\u0092\u00f8\u0007\u00b0\u00ab\u00ab\u0092\u009e)LH\u00f8\u0090rc\u009cB\b\u00d2o\u00c4\u00b3\u0012\u00dcv!\u00ceK,D4/\u00ca\u00e1A_\u00f6\u00d8R\u00af,\u00a0\u0011k-\u0016\u00a2n\u00ea(CA@T\u0095D\u00aam\u00f5&VK\u0014,`7\u00eabC\u00b8\u00d1\u00a3q%s\f\u00c5\u00ad\u0002\u00c0\u0098~\u00e1P:\u0098\u00a4O\u000bn\u00a6\u00bd\u00b9\u00a0\u000eD2\u00c91FV\u001f\u00bfU\u00a5\u00f5\u0085\u0082x\u00f9\n\u00b3\u00f0T2\u00a3\t\u00d4W\u00e8\u00b7+r\u00d6%\u0003,Xf]\u00b08\u00a7\u00f7\u00ab8E\u00eb\u008c\"\u0087W\u0018\u00dd\u001c\u0090\b&L\u00a3\u00e3\u00e5:\u00f3\u00b9\u0005\u00e7H\u0092\u00a5\u00bc\b-\u00ff\u0088\u00f3\u007f\u0081\u00baZ\u0088\u009bC\u00bdY\u0004\u00b2l\u00a5\n\u00fb\u00f56\u00f9\u00d3Sn\u008c\u00f6\u00c5\u00e7\u00d6\u0016k\u0088\u00c9:\u00dc\u0013\"\u00c3\u00a9\u00ef\u00d5\u00c7j\u00eb(D\b>\u0002]\\\u0082L/\u0099\u0090z\u00c0\u0080\u00ceG%\u00b2\u008f\\H\u00fc\n-Z}\u008c\u0092\u00e1P\u00a54\u00ea\u0011\u00a1/\u0084\u0085\f'\u001cB\u00c8-\u0012\u00f6\u00b7z\u00da\u0001\u00ad\u0089TJ,\u00bf\u00acLJ\u00b5t\u00f2R\u00cfvO\u0099\u0004\u000e\u0091\u00f0\u00faB\t\u00a38\u00b3x\u00a04\u00db\u00db\u008e\r\u00f5g'\u00dc\u0082\u00811\u00b9F,\u0084\u0001\u00dd\"\u0090\u00da\u0004\u00f5\u0095\u00f8\u0005\u00f2\u00f7\u008bE\u00fb\u00ca\u00b4\u00c6<\u0002u\u00c5^\u00e7\u0015\u007fs\nK\u009b?\u00b4=Ia\u0089\u00cdD\u00f6\u0093\u0016\u0004\u0090\u00eb\u00b6\u00f6\u009b\u00f2\u0097\u00d2\u00d0T\u008e8\u00f2\u009f\u00f3\u00e4\u0097\u0083\u0083\u00b2\u00bdm\u0004\u00b8u\u00c4v\u009c\u00c8\u0017\u0012\u00ac\u0011\u000f\u00d2o\u00f3\u00dd\u0086\u009b\n\u00fb\u00a9t(Q\r\u0019\u00d8\u0003\n\u0001!0r\u00e42W\u00c1z\u00ce\u00bf\u0086\u00e1\u009e\u00bf\u0096\bD\u00da[\u00ads\u00e4\u009c\u00f4\u0099\u00b9\u0098\u00e5-->\u00de\u009e\u0082Y\u00f8\u0091JKw\u0010\u009e\u00d0\u00b8\u00c4\u00ad\u008cQ\u00a6\u0010Q\u00edJR\u0097\u00ea\u00cc\u0096%M\u00db\u00ear2X\u00de\u00bf\u0083g\u008d\u00a8x\u00fe\u0084\u00ce\t\u00bee6q\u00f2\u00bf\u000bH\u00fdFU{\u00be\u00d0IV`,|(\u00ca\u00d0y\u00ca\u0090\u00d0\u0004\u00c3E5cJ0y\u00e5zBe\u008c\u00e5f\u0012\u0003\u0001\u00bez#\u001f4\u00bb\u00c0\u00c0\u00e1.\"~)\u00cc\u00da=\u008e\u00b6\u00ce;\u00f4:\u00aav\u00ff\u0099\u00aa\u00d2\nR\u0095\u00a3\u00e8\u00b12\u0094\u00c3\u00b4\u00bdd\u00f8T\u00df".length();
                            var14 = 16;
                            var27 = -1;
}
}
                    var28 = var15.substring(++var27, var27 + var14);
                    var10001 = 0;
}
}
}
        catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException var24) {
            throw new RuntimeException(var24);
}
}
    static {
        b = 130785924594311L;
        zkm$clinit();
}
}