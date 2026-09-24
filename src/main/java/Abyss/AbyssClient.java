/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.init.Blocks
 *  net.minecraft.network.play.server.S02PacketChat
 *  net.minecraft.network.play.server.S08PacketPlayerPosLook
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.BlockPos$MutableBlockPos
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.Vec3i
 */
package Abyss;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.AbyssClientBinder;
import Abyss.event.events.EntityJoinWorldEvent;
import Abyss.event.events.PostTickEvent;
import Abyss.event.events.PreMouseInputEvent;
import Abyss.event.events.PreUpdateEvent;
import Abyss.event.events.ReceivePacketEvent;
import Abyss.event.events.SetKeyBindStateEvent;
import Abyss.internal.restore.AbyssNameMap;
import Abyss.module.Module;
import Abyss.module.ModuleManager;
import Abyss.module.Modules;
import Abyss.module.impl.configuration.ClickGUI;
import Abyss.module.impl.configuration.VisualSpoof;
import Abyss.module.impl.visual.Freelook;
import Abyss.module.impl.world.BedNuker;
import Abyss.ui.abyss.AbyssArrayListVisibility;
import Abyss.ui.swing.ConfigManagerWindow;
import Abyss.util.ClientUtil;
import Abyss.util.DeferredRendererReload;
import Abyss.util.KeyBindUtil;
import Abyss.util.MinecraftRef;
import Abyss.util.PlayerInfoCache;
import Abyss.util.Sneaky;
import Abyss.util.TimerUtil;
import Abyss.util.debug.StallWatchdog;
import Abyss.util.packet.PacketManager;
import Abyss.util.render.abyss.FontManager;
import java.io.UnsupportedEncodingException;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3i;

public class AbyssClient
implements EventSubscriber {
    private static Map e;
    private static Object[] l;
    private static String[] m;
    private static String[] d;
    private static Map h;
    private static Long[] j;
    private static long[] f;
    private static Integer[] g;
    private static String[] b;
    private static long a;                private final TimerUtil B;
            private final Minecraft c;
        public static Map<Integer, String> H;
    private boolean s = false;
            public static Set<BlockPos> G;
        private boolean N = false;
    public static ConfigManagerWindow T;
    
    private static Map k;
    private final ScheduledExecutorService U;
    private final BlockPos.MutableBlockPos bedScanPos = new BlockPos.MutableBlockPos();
    private boolean bedScanActive;
    private int bedScanCursor;
    private int bedScanMinX;
    private int bedScanMinY;
    private int bedScanMinZ;
    private int bedScanSpanY;
    private int bedScanSpanZ;
    private int bedScanVolume;
    private static long[] i;
    public static String I;
    private static final byte[] KEY_OFFSETS;
    public static EventBus w;

    public void onEntityJoinWorld(long var1, EntityJoinWorldEvent var3) {
        if (var3.H instanceof EntityPlayerSP) {
            BedNuker.D.clear();
            BedNuker.B = false;
            this.bedScanActive = false;
}
}
    public void onReceivePacket(ReceivePacketEvent var1, long var2) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (var1.d instanceof S02PacketChat) {
            String var4 = ((S02PacketChat)var1.d).getChatComponent().getFormattedText();
            if (var4.contains("\u00a7e\u00a7lProtect your bed and destroy the enemy bed") || var4.contains("\u00a7e\u00a7lDestroy the enemy bed and then eliminate them")) {
                BedNuker.B = true;
}
        } else if (var1.d instanceof S08PacketPlayerPosLook) {
            S08PacketPlayerPosLook var6 = (S08PacketPlayerPosLook)var1.d;
            if (BedNuker.B) {
                BedNuker.B = false;
                this.U.schedule(() -> this.c.addScheduledTask(() -> {
                    try {
                        if (this.c.theWorld == null) {
                            return;
}
                        int var4x = MathHelper.floor_double((double)var6.getX());
                        int var5x = MathHelper.floor_double((double)var6.getY());
                        int var6x = MathHelper.floor_double((double)var6.getZ());
                        this.bedScanMinX = var4x - 35;
                        this.bedScanMinY = var5x - 15;
                        this.bedScanMinZ = var6x - 35;
                        this.bedScanSpanY = 31;
                        this.bedScanSpanZ = 71;
                        this.bedScanVolume = 156271;
                        this.bedScanCursor = 0;
                        this.bedScanActive = true;
}
                    catch (Throwable throwable) {
                        // empty catch block
}
                }), 3000L, TimeUnit.MILLISECONDS);
}
}
}
    public void onPreUpdate(long var1, PreUpdateEvent var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        List<Module> var8 = ModuleManager.S;
        int var10 = var8.size();
        for (int var9 = 0; var9 < var10; ++var9) {
            Module var11 = var8.get(var9);
            if (var11.b().equalsIgnoreCase("Timer")) continue;
            if (var11.l()) {
                var11.h(122596698849654L);
            } else if (var11.K()) {
                var11.Z(110240354022990L);
}
            if (var11.o()) continue;
            var11.d();
}
}
    private static long c(int var0, long var1) {
        int var3 = var0 ^ (int)(var1 & 0x7FFFL) ^ 0x7DA9;
        if (j[var3] == null) {
            byte[] var10;
            byte[] var4 = new byte[]{(byte)(var1 >>> 56), (byte)(var1 >>> 48), (byte)(var1 >>> 40), (byte)(var1 >>> 32), (byte)(var1 >>> 24), (byte)(var1 >>> 16), (byte)(var1 >>> 8), (byte)var1};
            long var5 = i[var3];
            byte[] var7 = new byte[]{(byte)(var5 >>> 56), (byte)(var5 >>> 48), (byte)(var5 >>> 40), (byte)(var5 >>> 32), (byte)(var5 >>> 24), (byte)(var5 >>> 16), (byte)(var5 >>> 8), (byte)var5};
            Long var8 = Thread.currentThread().getId();
            Object[] var9 = (Object[])k.get(var8);
            try {
                if (var9 == null) {
                    var9 = new Object[]{Cipher.getInstance("DES/CBC/NoPadding"), SecretKeyFactory.getInstance("DES"), new IvParameterSpec(new byte[8])};
                    k.put(var8, var9);
}
                DESKeySpec var11 = new DESKeySpec(var4);
                SecretKey var12 = ((SecretKeyFactory)var9[1]).generateSecret(var11);
                Cipher var13 = (Cipher)var9[0];
                var13.init(2, (Key)var12, (IvParameterSpec)var9[2]);
                var10 = var13.doFinal(var7);
}
            catch (Exception var14) {
                throw new RuntimeException("Abyss/AbyssClient", var14);
}
            long var15 = ((long)var10[0] & 0xFFL) << 56 | ((long)var10[1] & 0xFFL) << 48 | ((long)var10[2] & 0xFFL) << 40 | ((long)var10[3] & 0xFFL) << 32 | ((long)var10[4] & 0xFFL) << 24 | ((long)var10[5] & 0xFFL) << 16 | ((long)var10[6] & 0xFFL) << 8 | (long)var10[7] & 0xFFL;
            AbyssClient.j[var3] = var15;
}
        return j[var3];
}
    private static Object a(MethodHandles.Lookup var0, MutableCallSite var1, String var2, MethodType var3, Object[] var4) throws Throwable {
        int var5 = var4.length - 2;
        long var6 = (Long)var4[var5];
        long var9 = (Long)var4[++var5];
        MethodHandle var8 = a(var0, var1, var2, var3, var6, var9);
        var1.setTarget(MethodHandles.explicitCastArguments(var8, var3));
        return (Object)var8.asSpreader(Object[].class, var4.length).invoke(var4);
    }

    private static CallSite a(MethodHandles.Lookup var0, String var1, MethodType var2) {
        MutableCallSite var3 = new MutableCallSite(var2);
        try {
            var3.setTarget(
                MethodHandles.explicitCastArguments(
                    MethodHandles.insertArguments(
                        MethodHandles.lookup().findStatic(
                            AbyssClient.class,
                            "a",
                            MethodType.fromMethodDescriptorString(
                                "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/invoke/MutableCallSite;Ljava/lang/String;Ljava/lang/invoke/MethodType;[Ljava/lang/Object;)Ljava/lang/Object;",
                                AbyssClient.class.getClassLoader()
                            )
                        ).asCollector(Object[].class, var2.parameterCount()),
                        0, var0, var3, var1, var2
                    ),
                    var2
                )
            );
            return var3;
        } catch (Exception var5) {
            throw new RuntimeException("Abyss/AbyssClient" + " : " + var1 + " : " + var2.toString(), var5);
        }
    }

    private static Field a(Class var0, String var1, Class var2) {
        for (Field var6 : var0.getDeclaredFields()) {
            if (var6.getName().equals(var1) && var6.getType() == var2) {
                return var6;
            }
        }
        return null;
    }

    private static int a(long var0, long var2) {
        var0 ^= var2 << 48 | var2;
        int var4 = (int)(var0 >>> 46);
        if (m[var4] != null) {
            return var4;
        }

        Object var5 = l[var4];
        if (!(var5 instanceof String)) {
            return var4;
        }

        byte var6 = KEY_OFFSETS[(int)(var0 >>> 42 & 63L)];
        int[] var7 = new int[6];

        for (int var8 = 0; var8 < 6; var8++) {
            int var9 = 7 * (5 - var8);
            int var10 = (int)(var0 >>> var9 & 127L);
            var10 -= var6;
            if (var10 < 0) {
                var10 += 128;
            }
            var7[var8] = var10;
        }

        char[] var13 = ((String)var5).toCharArray();
        for (int var14 = 0; var14 < var13.length; var14++) {
            int var16 = var7[var14 % var7.length];
            if (var16 == 0) {
                break;
            }
            var13[var14] = (char)(var13[var14] ^ var16);
        }

        m[var4] = new String(var13);
        return var4;
    }

    private static Method a(Class var0, String var1, Class var2, int var3, Class[] var4) {
        label33:
        for (Method var8 : var0.getDeclaredMethods()) {
            if (var8.getName().equals(var1) && var8.getReturnType() == var2) {
                Class[] var9 = var8.getParameterTypes();
                if (var9.length == var3) {
                    for (int var10 = 0; var10 < var3; var10++) {
                        if (var9[var10] != var4[var10]) {
                            continue label33;
                        }
                    }
                    return var8;
                }
            }
        }
        return null;
    }

    private static MethodHandle a(MethodHandles.Lookup var0, MutableCallSite var1, String var2, MethodType var3, long var4, long var6) {
        char var8 = var2.charAt(0);
        MethodHandle var9 = null;
        Field var10 = null;
        Method var11 = null;

        try {
            if (var8 != 204 && var8 != 200 && var8 != 'K' && var8 != 219) {
                var11 = d(var4, var6);
                Class var17 = var11.getDeclaringClass();
                String var19 = var11.getName();
                MethodType var20 = MethodType.methodType(var11.getReturnType(), var11.getParameterTypes());
                if (var8 == 244) {
                    var9 = var0.findVirtual(var17, var19, var20);
                } else if (var8 == 254) {
                    var9 = var0.findStatic(var17, var19, var20);
                } else {
                    var9 = var0.findSpecial(var17, var19, var20, var17);
                }
            } else {
                var10 = c(var4, var6);
                Class var12 = var10.getDeclaringClass();
                String var18 = var10.getName();
                Class var14 = var10.getType();
                if (var8 == 204) {
                    var9 = var0.findGetter(var12, var18, var14);
                } else if (var8 == 200) {
                    var9 = var0.findSetter(var12, var18, var14);
                } else if (var8 == 'K') {
                    var9 = var0.findStaticGetter(var12, var18, var14);
                } else {
                    var9 = var0.findStaticSetter(var12, var18, var14);
                }
            }
            return MethodHandles.dropArguments(var9, var3.parameterCount() - 2, long.class, long.class);
        } catch (Exception var15) {
            StringBuilder var13 = new StringBuilder();
            var13.append(var15.getClass().getName())
                .append(" : ")
                .append(var10 != null ? var10.toString() : (var11 != null ? var11.toString() : " null "))
                .append(" : ")
                .append(var15.toString());
            throw new RuntimeException(var13.toString());
        }
    }

    private static void a() {
        l[0] = "";
        m[0] = "Abyss.event.binder.AbyssClientBinder";
        l[1] = long.class;
        m[1] = "java/lang/Long";
        l[2] = "";
        m[2] = "Abyss.event.EventBus";
        l[3] = "";
        m[3] = "Abyss.AbyssClient";
        l[4] = void.class;
        m[4] = "java/lang/Void";
        l[5] = "";
        m[5] = "java.util.List";
        l[6] = int.class;
        m[6] = "java/lang/Integer";
        l[7] = "";
        m[7] = "java.lang.Object";
        l[8] = "";
        m[8] = "Abyss.module.impl.configuration.VisualSpoof";
        l[9] = boolean.class;
        m[9] = "java/lang/Boolean";
        l[10] = "";
        m[10] = "Abyss.module.Modules";
        l[11] = "";
        m[11] = "java.lang.Class";
        l[12] = "";
        m[12] = "Abyss.module.Module";
        l[13] = "";
        m[13] = "Abyss.setting.settings.DisableRenderVisualSetting";
        l[14] = "";
        m[14] = "Abyss.util.KeyBindUtil";
        l[15] = "";
        m[15] = "Abyss.setting.settings.ModeSetting";
        l[16] = "";
        m[16] = "java.lang.String";
        l[17] = "";
        m[17] = "Abyss.util.ClientUtil";
        l[18] = "";
        m[18] = "java.util.Set";
        l[19] = "";
        m[19] = "Abyss.module.impl.configuration.ClickGUI";
        l[20] = char.class;
        m[20] = "java/lang/Character";
        l[21] = short.class;
        m[21] = "java/lang/Short";
        l[22] = "";
        m[22] = "Abyss.util.packet.PacketManager";
        l[23] = "";
        m[23] = "Abyss.setting.settings.BooleanSetting";
        l[24] = "XUJ6&\u0017F]PyE\u0003B\u0010y9|\u0010K";
        l[25] = "";
        m[25] = "java.lang.Integer";
        l[26] = "~E$T!J`M>\u001bFKqV3A`M";
        l[27] = "";
        m[27] = "java.util.Map";
        l[28] = "";
        m[28] = "net.minecraft.client.entity.EntityPlayerSP";
        l[29] = "vn\u000f\u0011?q\u0003N\u0004\u001e.>~V\u0017\u0019'w\u0016";
        l[30] = "";
        m[30] = "Abyss.event.events.PreMouseInputEvent";
        l[31] = "KM\u001da\u0001Z\u0015EZu:D\u001dTe%\\HHH\u0004{TZ\u001d(X'VW\rF\u001fwUPp";
        l[32] = "m;B\n\f-=7L]l)P~JS\u000f!h(\bW\u001cOn \u001fJ\u0012\u007f!(\u0015Ml";
        l[33] = "<kz]v/7f-V\u000b)>fP\u0000zFn$\u007f\u0011v()t|\u0016\u000b";
        l[34] = "kC5.\u0005p0G%(lNT\u001d$\u007fS`2F:%\u0005\fdZ&5]rjD\"6l";
        l[35] = "Q58|Ec_+<\u007ft`a !g\u0014m[|<7\b\u001dXu&m\u001a%\u000e7\"~t'\u001f. 5\u0012v\u00136=\u000e";
        l[36] = "U2c|l\u0012\u000b:$hW\b\n:r~WY\u00104y?1\b\u001c,d\u0004";
        l[37] = "\u0018.N8,$U$Z!K#) Q(6#\u0019oY\"1]\u0010&R&%eFdV5KgW}T~-6[eIE";
        l[38] = "_\u0010\u0015\u001ax(LE\u0011\u0018\u0011\u00116\u0014\u0013\u001e)/WJ\u001b\f|O\u000f\u0010\u0011\u001e\u007fwYR\u0015\r\u0011qQE\b\u0003!>YO\u000f}";
        l[39] = "gsoQ*\nimkR\u001b\u0010W5rNf\ngzzDat";
        l[40] = "Wu\b\r\u007f\u0004Yk\f\u000eN>gn\u000f\u0003?\u0002\u001a0\b\u0016sz^5\u0016\u001c B\bw\u0012\u000fN@\u0019n\u0010D(\u0011\u0015v\r\u007f";
        l[41] = "^}A\u0019\u0012M\t\u007fBXq,7h_\u0013\u001f\bVx[\u0010\u000bv\teTT\u000fFFm^Sq";
        l[42] = "\u0019q?svy\u0017o;pG`)7\"l:y\u0019x*f=\u0007";
        l[43] = "\u0002j\u0000\u000fej\ft\u0004\fTt2.\u001c\u001eltSp\u0014\f9\u0014";
        l[44] = "H\rwmW\u0005F\u0013snf#xKjr\u001b\u0005H\u0004bx\u001c{";
        l[45] = "#MG\u000fA\u0016-SC\fp!\u0013\f\u0005\u0019\u0013\u0006+ZG\u001d\u0000h)K^\u001fK\u000exGF\u0002p";
        l[46] = "\u000b\u0000[R\u0014\u000f\u001a\u0014HPi7a@CG\u0014\bQ\u000fKM\u0013v";
        l[47] = "\u0005\u007fzF;][w=R\u0000W_ho><J]\"b_bBOw\u0002";
        l[48] = "XX\u001e_C!TGUW\"0GP\u00051\u001dg\u0003\bS1N^PPT\b\\?@TW\u001c\"dDU\f]D5HM\u0011f";
        l[49] = "\u0002[^\u0017\u001f\u0014R\u0005\u0001\btE\n\u001dw\u001b\u0004YcXV\u001b\t[S\u0017^\u0011\u000e%";
        l[50] = "%^\u0011\u0003s7+@\u0015\u0000B(\u0015\u001fS\u0015!'-I\u0011\u00112I/X\b\u0013y/~T\u0010\u000eB";
        l[51] = "\u007fZ\u0013I$HqD\u0017J\u0015wO\u001bQ_vXwM\u0013[e6qE\u0004Fk\u0006>M\u000eA\u0015\f1A\u000b\u0000s]=Y\u0016;";
        l[52] = "s\u0004\u0004DE8%V\u0011Fz8cQ\u0002R\u0006>e<\u0019\u0006\u00158t\rIXJ'\u001f";
        l[53] = "\u0011T>sg \u0002\u0001:q\u000e+xP8w6'\u0019\u000e0ecGD\n=,n&\u001a\u0002/y\u000e~@\b=z6(\u0002\f.\u00140 \u0015\u0011 $\u007f(\u001f\u0016^";
        l[54] = "D\u001e\u0015\u00123\b\u001a\u000e\u000e\fJ\u0001}_Z\u00106\u0007\u0013\u0018\n\u00131zDZ\u0000\u001f$B\u0012\u0018\u0004\fJ@\u0003\u0001\u0006G,\u0011\u000f\u0019\u001b|";
        l[55] = "'n-\\D\u0006.s8ffhx5 \u0005CP.w$\u0016-Q=|*\u001f_\u000f-g4f\u0017T3v/\u000f\\\u0012|5D\\S\u000b#6\"\r_\u0013>\r";
        l[56] = "$%Z9E#*;^:t\u0014\u0014cG&\t#$,O,\u000e].#C)O;\u007f/[4t";
        l[57] = "M{^\u001bYP\u001b)K\u0019fZE.K\u001ef\u000b_ @_\u0000ZS8]d";
        l[58] = "7\u001eH\b\u001aNm\u0015\u0006PyE1\u0010'\u0001\u001dY:lE\u000e\u001a\u001c7\r\u001b\u0006\bIW";
        l[59] = "@',HxZN9(KIvpfn^*JH0,Z9$J!5XrB\u001b--EI";
        l[60] = "}UC\u001e'8vX\u0014\u0015Z>\u007fXtG>,\u007f$\u0017\u00106-oJP@5*\u0012";
        l[61] = "ME<,r\"]A?8\f.^^o&w\u0002HEa8a\bNXkBf%\u001dMx#v!\u001eY\u0006|k.Z]63c$]#";
        l[62] = "\u0007k\u000e)X=\u0019j\u000e4%\u0016k1W4\u001d1\no_&HQWkRoE0\tc@:%=\u0019sClU0\f3QW\u001f/\bo\n1N#\u0010r1";
        l[63] = "\"J!xb\u0003<K!e\u001f\u001fN\u0010xe'\u000f/Npwro#OwfoU\u007fR'z\u001f\u0003<Rl=o\u000e)\u0012~\u0006!\b#Q`6n\u0000)V\u001e";
        l[64] = "tMyjj\u0003p\u0014rp\u0013w\u001a\u0014%wnL*[-}i2 T!x(TqX9e\u0013";
        l[65] = "6lW]whf2\bB\u001c?:!D<!g;-ERf78*8";
        l[66] = "\u0014\"kZG\u001f\u001a<oYv\"$dvE\u000b\u001f\u0014+~O\fa";
        l[67] = "C{\rnWxOb\\16qDwFpmq^\u000bP9Jc\u001dj\u0006k_a\"";
        l[68] = "\u0013GJbf\u0015\u001dYNaW\r#\u0001W}*\u0015\u0013N_w-k\u0019ASrl\rHMKoW";
        l[69] = "\u000e\u000b:\u0004\u0019\u000e\u0000\u0015>\u0007((>Jx\u0012K\u001e\u0006\u001c:\u0016Xp\u0004\r#\u0014\u0013\u0016U\u0001;\t(";
        l[70] = "s\u0000F0\u000f\tuWL1~\u001b\u001c\u0003\u0011a\u0003\u000e,L\u0019k\u0004p";
        l[71] = "Z!;QJkT??R{Xjg&N\u0006kZ(.D\u0001\u0015P'\"A@s\u0001+:\\{";
        l[72] = "\u0019\u0017\u007fof\u001a\u0017\t{lW\u0004)V=y4\n\u0011\u0000\u007f}'d\u0013\u0011f\u007fl\u0002B\u001d~bW";
        l[73] = "\b\u0010\u007f l\u0019\u0006\u000e{#]\u000b8R{1?\\^\u0003w)\"g";
        l[74] = "N\u000b(\rnN@\u0015,\u000e_Z~\u00194E1N\u001f\t0F%0";
        l[75] = "\\zu\u0010\t)\u0002jn\u000ep\u0011e;:\u0012\f&\u000b|j\u0011\u000b[_xg\u001cK=\u000et\u007f\u0001p";
        l[76] = "C+\u007fezkS/|q\u0004qQ)$w\u0004`Ow+uepKt?\u000bg7K&tmt;\u0010=E";
    }
    private static Field c(long var0, long var2) {
        int var4 = AbyssClient.a(var0, var2);
        Object var5 = l[var4];
        if (!(var5 instanceof String)) {
            return (Field)var5;
}
        String var6 = m[var4];
        int var7 = var6.indexOf(8);
        Class var8 = AbyssClient.b(Long.parseLong(var6.substring(0, var7), 36), 0L);
        int var9 = var6.indexOf(8, ++var7);
        String var10 = var6.substring(var7, var9);
        Class var11 = AbyssClient.b(Long.parseLong(var6.substring(++var9), 36), 0L);
        Class var12 = var8;
        while (true) {
            Field var13;
            if ((var13 = AbyssClient.a(var12, var10, var11)) != null) {
                AbyssClient.l[var4] = var13;
                return var13;
}
            Class<?>[] var14 = var12.getInterfaces();
            if (var14 != null) {
                for (int var15 = 0; var15 < var14.length; ++var15) {
                    var13 = AbyssClient.b(var14[var15], var10, var11);
                    if (var13 == null) continue;
                    AbyssClient.l[var4] = var13;
                    return var13;
}
}
            if (var12.getName().equals("java.lang.Object")) {
                StringBuffer var19 = new StringBuffer();
                var19.append("NoSuchFieldException in ").append(var8.getName()).append(' ').append(var11.getName()).append(' ').append(var10);
                throw new RuntimeException(var19.toString());
}
            if ((var12 = var12.getSuperclass()) != null) continue;
            var12 = AbyssClient.b(525810144067084L, 0L);
}
}
    public void onSetKeyBindState(SetKeyBindStateEvent var1, long var2) throws UnsupportedEncodingException, Throwable, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (ClientUtil.I()) {
            List<Module> var9 = ModuleManager.S;
            int var11 = var9.size();
            for (int var10 = 0; var10 < var11; ++var10) {
                Module var12 = var9.get(var10);
                try {
                    if (var12.b().equalsIgnoreCase("Timer") || var12.b().equalsIgnoreCase("ClickGUI") || var12.h() == 0 || !KeyBindUtil.d(var12.h(), var1.R, 55909487137472L) || var12.b().equalsIgnoreCase("FREELOOK") && !Freelook.mode.R("TOGGLE")) continue;
                    var12.u((short)0, 139350548161835L);
                    continue;
}
                catch (Throwable throwable) {
                    // empty catch block
}
}
            for (Map.Entry<Integer, String> var18 : H.entrySet()) {
                if (!KeyBindUtil.d(var18.getKey(), var1.R, 55909487137472L)) continue;
                for (String var15 : var18.getValue().split("\\n")) {
                    this.c.thePlayer.sendChatMessage(var15);
}
}
}
}
    public AbyssClient(int var1, char var2, int var3) {
        this.U = Executors.newScheduledThreadPool(1);
        this.c = MinecraftRef.c((byte)0, 0L);
        this.B = new TimerUtil();
}
    public void onPostTick(PostTickEvent var1, long var2) throws Throwable {
        StallWatchdog.tick(ClientUtil.I());
        int var16 = 22243;
        int var21 = 12652;
        if (!ClientUtil.I()) {
            BedNuker.B = false;
            PacketManager.M(false);
            PacketManager.u.clear();
            PacketManager.v.clear();
            PacketManager.a.clear();
            I = null;
            List<Module> var32 = ModuleManager.S;
            int var34 = var32.size();
            for (int var33 = 0; var33 < var34; ++var33) {
                Module var35 = var32.get(var33);
                if (var35.b().equalsIgnoreCase("Timer") || !var35.P()) continue;
                w.B(var35);
                var35.A(false);
}
            this.s = false;
        } else {
            List<Module> var26 = ModuleManager.S;
            int subscribesBudget = 3;
            boolean batching = false;
            int var28 = var26.size();
            for (int var27 = 0; var27 < var28; ++var27) {
                Module var29 = var26.get(var27);
                if (var29.b().equalsIgnoreCase("Timer")) continue;
                if (var29.l()) {
                    var29.i(17998201765264L);
                    var29.n(false);
                } else if (var29.K()) {
                    var29.A(94287625739397L);
                    var29.E(false);
}
                if (var29.o()) {
                    if (var29.P() || subscribesBudget <= 0) continue;
                    if (!batching) {
                        w.beginBatch();
                        batching = true;
}
                    w.s(var29, 25046058167973L);
                    var29.A(true);
                    --subscribesBudget;
                    continue;
}
                if (var29.P()) {
                    w.B(var29);
                    var29.A(false);
}
                var29.P(11128156246666L);
}
            if (batching) {
                w.endBatch();
}
            if (this.c.currentScreen == null) {
                if (ClickGUI.x(17550, (short)6998, (char)var16)) {
                    try {
                        ClickGUI.O(2169, 8663, (char)var21);
}
                    catch (NullPointerException nullPointerException) {
                        // empty catch block
}
}
                if (Freelook.mode.R("HOLD") && Modules.J(Freelook.class).h() != 0) {
                    Modules.J(Freelook.class).I(20724619369162L, KeyBindUtil.V(Modules.J(Freelook.class).h(), 64165991731362L));
}
}
            if (VisualSpoof.n(118536638251483L) && !this.s) {
                VisualSpoof.t.v(!VisualSpoof.t.c(), 64895789836511L);
}
            this.s = VisualSpoof.n(118536638251483L);
}
        FontManager.warmStep();
        AbyssArrayListVisibility.preload();
        PlayerInfoCache.refresh();
        Modules.flushPendingSave();
        DeferredRendererReload.flush();
        this.pumpBedScan();
}
    private void pumpBedScan() {
        if (!this.bedScanActive) {
            return;
}
        try {
            if (this.c.theWorld == null) {
                this.bedScanActive = false;
                return;
}
            long deadline = System.nanoTime() + 2000000L;
            int layerYZ = this.bedScanSpanY * this.bedScanSpanZ;
            while (this.bedScanCursor < this.bedScanVolume) {
                int idx = this.bedScanCursor;
                int dx = idx / layerYZ;
                int rem = idx % layerYZ;
                int dy = rem / this.bedScanSpanZ;
                int dz = rem % this.bedScanSpanZ;
                this.bedScanPos.set(this.bedScanMinX + dx, this.bedScanMinY + dy, this.bedScanMinZ + dz);
                if (this.c.theWorld.getBlockState((BlockPos)this.bedScanPos).getBlock() == Blocks.bed) {
                    BedNuker.D.add(new BlockPos((Vec3i)this.bedScanPos));
}
                ++this.bedScanCursor;
                if ((this.bedScanCursor & 0x1FFF) != 0 || System.nanoTime() < deadline) continue;
                return;
}
            this.bedScanActive = false;
}
        catch (Throwable ignored) {
            this.bedScanActive = false;
}
}
    public void d(long var1, PreUpdateEvent var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (I != null && this.B.L(300L, true)) {
            if (this.N) {
                this.N = false;
                this.c.thePlayer.sendChatMessage("/p " + I);
            } else {
                this.N = true;
                this.c.thePlayer.sendChatMessage("/p leave");
}
}
}
    public void onPreMouseInput(long var1, PreMouseInputEvent var3) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        List<Module> var6 = ModuleManager.S;
        int var8 = var6.size();
        for (int var7 = 0; var7 < var8; ++var7) {
            Module var9 = var6.get(var7);
            if (var9.b().equalsIgnoreCase("Timer") || var9.o()) continue;
            var9.L(var3, 85029904657643L);
}
}
    private static int b(int var0, long var1) {
        int var3 = var0 ^ (int)(var1 & 0x7FFFL) ^ 0x325D;
        if (g[var3] == null) {
            byte[] var10;
            byte[] var4 = new byte[]{(byte)(var1 >>> 56), (byte)(var1 >>> 48), (byte)(var1 >>> 40), (byte)(var1 >>> 32), (byte)(var1 >>> 24), (byte)(var1 >>> 16), (byte)(var1 >>> 8), (byte)var1};
            long var5 = f[var3];
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
                throw new RuntimeException("Abyss/AbyssClient", var14);
}
            int var15 = (var10[4] & 0xFF) << 24 | (var10[5] & 0xFF) << 16 | (var10[6] & 0xFF) << 8 | var10[7] & 0xFF;
            AbyssClient.g[var3] = var15;
}
        return g[var3];
}
    private static Class b(long var0, long var2) {
        Class<?> var5 = null;
        int var4 = AbyssClient.a(var0, var2);
        Object var6 = l[var4];
        try {
            if (var6 instanceof String) {
                AbyssClient.l[var4] = var5 = Class.forName(AbyssNameMap.map(m[var4]));
                return var5;
}
}
        catch (Exception var8) {
            throw new RuntimeException(var8.toString());
}
        return (Class)var6;
}
    @Override
    public final void x(long var1, EventBus var3) {
        AbyssClientBinder.C(var3, this);
}
    private static Method b(Class var0, String var1, Class var2, int var3, Class[] var4) {
        Method var5 = AbyssClient.a(var0, var1, var2, var3, var4);
        if (var5 != null) {
            return var5;
}
        Class<?>[] var6 = var0.getInterfaces();
        if (var6 != null) {
            for (int var7 = 0; var7 < var6.length; ++var7) {
                var5 = AbyssClient.b(var6[var7], var1, var2, var3, var4);
                if (var5 == null) continue;
                return var5;
}
}
        return null;
}
    private static Method d(long var0, long var2) {
        int var4 = a(var0, var2);
        Object var5 = l[var4];
        if (!(var5 instanceof String)) {
            return (Method)var5;
        }

        String var6 = m[var4];
        int var7 = var6.indexOf(8);
        Class var8 = b(Long.parseLong(var6.substring(0, var7), 36), 0L);
        int var9 = var6.indexOf(8, ++var7);
        String var10 = var6.substring(var7, var9);
        int var11 = -1;
        int var12 = var9;

        do {
            var11++;
            var12++;
        } while ((var12 = var6.indexOf(8, var12)) > -1);

        int var13;
        Class[] var14 = new Class[var13 = var11 - 1];
        Class var15 = null;
        var12 = var9 + 1;

        for (int var16 = 0; var16 < var11; var16++) {
            int var17 = var6.indexOf(8, var12);
            var15 = b(Long.parseLong(var6.substring(var12, var17), 36), 0L);
            if (var16 < var13) {
                var14[var16] = var15;
            }
        }

        Class var23 = var8;
        while (true) {
            Method var26 = a(var23, var10, var15, var13, var14);
            if (var26 != null) {
                l[var4] = var26;
                return var26;
            }

            if (var23.getName().equals("java.lang.Object")) {
                break;
            }

            if ((var23 = var23.getSuperclass()) == null) {
                var23 = b(525810144067084L, 0L);
                break;
            }
        }

        var23 = var8;
        while (true) {
            Class[] var27;
            if ((var27 = var23.getInterfaces()) != null) {
                for (int var18 = 0; var18 < var27.length; var18++) {
                    Method var19 = b(var27[var18], var10, var15, var13, var14);
                    if (var19 != null) {
                        l[var4] = var19;
                        return var19;
                    }
                }
            }

            if (var23.getName().equals("java.lang.Object")) {
                StringBuffer var28 = new StringBuffer();
                var28.append("NoSuchMethodException in ")
                    .append(var8.getName())
                    .append(' ')
                    .append(var15.getName())
                    .append(' ')
                    .append(var10)
                    .append('(');
                int var29 = 0;
                while (var29 < var13) {
                    var28.append(var14[var29].getName());
                    if (++var29 < var13) {
                        var28.append(", ");
                    }
                }
                var28.append(')');
                throw new RuntimeException(var28.toString());
            }

            if ((var23 = var23.getSuperclass()) == null) {
                var23 = b(525810144067084L, 0L);
            }
        }
    }
    private static Field b(Class var0, String var1, Class var2) {
        Field var3 = AbyssClient.a(var0, var1, var2);
        if (var3 != null) {
            return var3;
}
        Class<?>[] var4 = var0.getInterfaces();
        if (var4 != null) {
            for (int var5 = 0; var5 < var4.length; ++var5) {
                var3 = AbyssClient.b(var4[var5], var1, var2);
                if (var3 == null) continue;
                return var3;
}
}
        return null;
}
    private static boolean zkm$unresolved$0$monomorphic_exactly_one_target_not_statically_decidable_candidates_Abyss_iD_l_OR_Abyss_iD_K_y_slots_39_49_66_70(Object var0, long var3) {
        try {
            MethodType var5 = MethodType.fromMethodDescriptorString("(Ljava/lang/Object;JJ)Z", AbyssClient.class.getClassLoader());
            return (boolean)MethodHandles.explicitCastArguments(AbyssClient.a(MethodHandles.lookup(), null, "\u00f4", var5, 2266045794134596627L, 9901644652386L), var5).invoke((Object)var0, 2266045794134596627L, 9901644652386L);
}
        catch (Throwable ex) {
            throw Sneaky.rethrow(ex);
}
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
                char var12 = (char)((char)(var5 & 15) << 12);
                int var9 = var0[++var4];
                var12 = (char)(var12 | (char)(var9 & 63) << 6);
                var9 = var0[++var4];
                var12 = (char)(var12 | (char)(var9 & 63));
                var3[var1++] = var12;
            }
        }
        return new String(var3, 0, var1);
    }    private static void zkm$clinit() {
        try {
            l = new Object[77];
            m = new String[77];
            a();
            e = new HashMap(13);
            long var22 = a ^ 20790936441576L;
            byte[] var10003 = new byte[]{(byte)(var22 >>> 56), 0, 0, 0, 0, 0, 0, 0};
            for (int var25 = 1; var25 < 8; ++var25) {
                var10003[var25] = (byte)(var22 << var25 * 8 >>> 56);
            }
            Cipher var24 = Cipher.getInstance("DES/CBC/PKCS5Padding");
            var24.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
            String[] var31 = new String[10];
            int var29 = 0;
            String var28 = " !\u00f0\u00fc$\u00ec\u00e2\u00b4\u0097\u00bf//\u00fa\u0089sK \u00df\u00f0\u00c02\u0019\u00ae\u00d8\u0006\u0080\rn0\u00f0 &H\u00fd$\u0097\r\u0019\u00fb\u009d\u001f\u00b0p\u00c1\u00e3\u00cdh\u00af\u00e5\u00107\u0003m\u00c0\u00ce^\u00b3\u001b\u00ea`\u00d6\u000e\u001b\b\u00ae\u00f3PMv\u008d\u00b0&:\u00d0\u00a4\u0087\u00bf}\u00cf.\u00c5\u00fc/\u00dd3/\n6M\u00cb\u0015\u00a5\u00cf\f\u00f0\u00ae=a\u008f\u00cb\u00aa\u00a4Z^.QD\u00d9\u00e1!\u0017\u00b0\u0016\u0080\u00d0\u00a0\u00fb\u00d0X\nq\u00bchi\u00fb\u0003\u00a9\u00de/\non\u008c\u0011?\u00ae+#C\u00f7D\u0015\u008bW\u00ae\u0087?\u0010\u0093\u00a7/\u00f2\u00ec\u00be\u00b9\u00045\u00e2@5\u00c9\u0012\u00f5\u00bc\u0010\u00e6\u00e08%\u009b\u00e3U,h\u00ac%\u0083\u0099Z\u0088\u00a5\u0010\u00f1\u00ac\u00b5\u0085\u00f6\u008b\u000b\u00f0\u00e7_\u00ba\u0081\u00db\u00b3\u008f8X/\u00c6u\u0087#5\u0000h7d\u00e7\u00f6\u00b0\u00c8\u00de\u00c4\u00fce\u00e9\u0018\u00a8\u00b9\u00cf\u0083\u00cdP\u00f2)\b\u0016&\u001f\u00e91\u00c2\u00e0\u00db\u00a2/\u00b2;G\u0088;\u0011\u0000\u0099\u00a0\u00e4\b3\u00fbw\u00e0\u00d7\u00be#\u00e6\u0016\u008b\u001a3\u0015!.\\@\u00c5\u00d7\u00de$\u00e9\u00b3\u00b1\u0094xE\u000bUj\u00d2\u001a\u000e\u00b6q2K\u00cf";
            int var30 = " !\u00f0\u00fc$\u00ec\u00e2\u00b4\u0097\u00bf//\u00fa\u0089sK \u00df\u00f0\u00c02\u0019\u00ae\u00d8\u0006\u0080\rn0\u00f0 &H\u00fd$\u0097\r\u0019\u00fb\u009d\u001f\u00b0p\u00c1\u00e3\u00cdh\u00af\u00e5\u00107\u0003m\u00c0\u00ce^\u00b3\u001b\u00ea`\u00d6\u000e\u001b\b\u00ae\u00f3PMv\u008d\u00b0&:\u00d0\u00a4\u0087\u00bf}\u00cf.\u00c5\u00fc/\u00dd3/\n6M\u00cb\u0015\u00a5\u00cf\f\u00f0\u00ae=a\u008f\u00cb\u00aa\u00a4Z^.QD\u00d9\u00e1!\u0017\u00b0\u0016\u0080\u00d0\u00a0\u00fb\u00d0X\nq\u00bchi\u00fb\u0003\u00a9\u00de/\non\u008c\u0011?\u00ae+#C\u00f7D\u0015\u008bW\u00ae\u0087?\u0010\u0093\u00a7/\u00f2\u00ec\u00be\u00b9\u00045\u00e2@5\u00c9\u0012\u00f5\u00bc\u0010\u00e6\u00e08%\u009b\u00e3U,h\u00ac%\u0083\u0099Z\u0088\u00a5\u0010\u00f1\u00ac\u00b5\u0085\u00f6\u008b\u000b\u00f0\u00e7_\u00ba\u0081\u00db\u00b3\u008f8X/\u00c6u\u0087#5\u0000h7d\u00e7\u00f6\u00b0\u00c8\u00de\u00c4\u00fce\u00e9\u0018\u00a8\u00b9\u00cf\u0083\u00cdP\u00f2)\b\u0016&\u001f\u00e91\u00c2\u00e0\u00db\u00a2/\u00b2;G\u0088;\u0011\u0000\u0099\u00a0\u00e4\b3\u00fbw\u00e0\u00d7\u00be#\u00e6\u0016\u008b\u001a3\u0015!.\\@\u00c5\u00d7\u00de$\u00e9\u00b3\u00b1\u0094xE\u000bUj\u00d2\u001a\u000e\u00b6q2K\u00cf".length();
            int var27 = 16;
            int var36 = -1;
            block9: while (true) {
                String var37 = var28.substring(++var36, var36 + var27);
                int var10001 = -1;
                while (true) {
                    byte[] var32 = var24.doFinal(var37.getBytes("ISO-8859-1"));
                    String var51 = AbyssClient.a(var32).intern();
                    switch (var10001) {
                        case 0: {
                            var31[var29++] = var51;
                            if ((var36 += var27) >= var30) {
                                b = var31;
                                d = new String[10];
                                h = new HashMap(13);
                                var10003 = new byte[]{(byte)(var22 >>> 56), 0, 0, 0, 0, 0, 0, 0};
                                for (int var12 = 1; var12 < 8; ++var12) {
                                    var10003[var12] = (byte)(var22 << var12 * 8 >>> 56);
}
                                Cipher var11 = Cipher.getInstance("DES/CBC/NoPadding");
                                var11.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
                                long[] var17 = new long[8];
                                int var14 = 0;
                                String var15 = "\u00dc\u00f5\u00cd\u009bzg\u00b0FA\u00d6\u00c0\u00d7\u00a1\u00dc\u00f3\u00f5\u008a\u001a\u00d0\u00a5\u008b\u00db<H\u00d3\u00cf\u00b9>\u00ca\u00c8\u00ad\u00b5\u0002\u00d4\u0001\u00cf\u00c4\bu\u0005+\u001b\u00fd\u0092\u00ac\u001b_\u00fb";
                                int var16 = "\u00dc\u00f5\u00cd\u009bzg\u00b0FA\u00d6\u00c0\u00d7\u00a1\u00dc\u00f3\u00f5\u008a\u001a\u00d0\u00a5\u008b\u00db<H\u00d3\u00cf\u00b9>\u00ca\u00c8\u00ad\u00b5\u0002\u00d4\u0001\u00cf\u00c4\bu\u0005+\u001b\u00fd\u0092\u00ac\u001b_\u00fb".length();
                                int var13 = 0;
                                block12: while (true) {
                                    var10001 = var13;
                                    byte[] var18 = var15.substring(var10001, var13 += 8).getBytes("ISO-8859-1");
                                    long[] var40 = var17;
                                    var10001 = var14++;
                                    long var55 = ((long)var18[0] & 0xFFL) << 56 | ((long)var18[1] & 0xFFL) << 48 | ((long)var18[2] & 0xFFL) << 40 | ((long)var18[3] & 0xFFL) << 32 | ((long)var18[4] & 0xFFL) << 24 | ((long)var18[5] & 0xFFL) << 16 | ((long)var18[6] & 0xFFL) << 8 | (long)var18[7] & 0xFFL;
                                    int var59 = -1;
                                    while (true) {
                                        long var19 = var55;
                                        byte[] var21 = var11.doFinal(new byte[]{(byte)(var19 >>> 56), (byte)(var19 >>> 48), (byte)(var19 >>> 40), (byte)(var19 >>> 32), (byte)(var19 >>> 24), (byte)(var19 >>> 16), (byte)(var19 >>> 8), (byte)var19});
                                        long var63 = ((long)var21[0] & 0xFFL) << 56 | ((long)var21[1] & 0xFFL) << 48 | ((long)var21[2] & 0xFFL) << 40 | ((long)var21[3] & 0xFFL) << 32 | ((long)var21[4] & 0xFFL) << 24 | ((long)var21[5] & 0xFFL) << 16 | ((long)var21[6] & 0xFFL) << 8 | (long)var21[7] & 0xFFL;
                                        switch (var59) {
                                            case 0: {
                                                var40[var10001] = var63;
                                                if (var13 < var16) break;
                                                f = var17;
                                                g = new Integer[8];
                                                k = new HashMap(13);
                                                var10003 = new byte[]{(byte)(var22 >>> 56), 0, 0, 0, 0, 0, 0, 0};
                                                for (int var1 = 1; var1 < 8; ++var1) {
                                                    var10003[var1] = (byte)(var22 << var1 * 8 >>> 56);
}
                                                Cipher var0 = Cipher.getInstance("DES/CBC/NoPadding");
                                                var0.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
                                                long[] var6 = new long[2];
                                                int var3 = 0;
                                                String var4 = "\u0089\u009ej\u00db\u00e9P\u00b4~\u00cb\u000e\u00cbgZ\u00b1m\u007f";
                                                int var5 = "\u0089\u009ej\u00db\u00e9P\u00b4~\u00cb\u000e\u00cbgZ\u00b1m\u007f".length();
                                                int var2 = 0;
                                                do {
                                                    int var48 = var2;
                                                    byte[] var7 = var4.substring(var48, var2 += 8).getBytes("ISO-8859-1");
                                                    var48 = var3++;
                                                    long var8 = ((long)var7[0] & 0xFFL) << 56 | ((long)var7[1] & 0xFFL) << 48 | ((long)var7[2] & 0xFFL) << 40 | ((long)var7[3] & 0xFFL) << 32 | ((long)var7[4] & 0xFFL) << 24 | ((long)var7[5] & 0xFFL) << 16 | ((long)var7[6] & 0xFFL) << 8 | (long)var7[7] & 0xFFL;
                                                    byte[] var10 = var0.doFinal(new byte[]{(byte)(var8 >>> 56), (byte)(var8 >>> 48), (byte)(var8 >>> 40), (byte)(var8 >>> 32), (byte)(var8 >>> 24), (byte)(var8 >>> 16), (byte)(var8 >>> 8), (byte)var8});
                                                    var6[var48] = var63 = ((long)var10[0] & 0xFFL) << 56 | ((long)var10[1] & 0xFFL) << 48 | ((long)var10[2] & 0xFFL) << 40 | ((long)var10[3] & 0xFFL) << 32 | ((long)var10[4] & 0xFFL) << 24 | ((long)var10[5] & 0xFFL) << 16 | ((long)var10[6] & 0xFFL) << 8 | (long)var10[7] & 0xFFL;
                                                } while (var2 < var5);
                                                i = var6;
                                                j = new Long[2];
                                                return;
}
                                            default: {
                                                var40[var10001] = var63;
                                                if (var13 < var16) continue block12;
                                                var15 = "&\u000b\r\u00a1(k\u009cJw \ba\u00b7\u00f7\u00c5k";
                                                var16 = "&\u000b\r\u00a1(k\u009cJw \ba\u00b7\u00f7\u00c5k".length();
                                                var13 = 0;
}
}
                                        int var47 = var13;
                                        var18 = var15.substring(var47, var13 += 8).getBytes("ISO-8859-1");
                                        var40 = var17;
                                        var10001 = var14++;
                                        var55 = ((long)var18[0] & 0xFFL) << 56 | ((long)var18[1] & 0xFFL) << 48 | ((long)var18[2] & 0xFFL) << 40 | ((long)var18[3] & 0xFFL) << 32 | ((long)var18[4] & 0xFFL) << 24 | ((long)var18[5] & 0xFFL) << 16 | ((long)var18[6] & 0xFFL) << 8 | (long)var18[7] & 0xFFL;
                                        var59 = 0;
}
}
}
                            var27 = var28.charAt(var36);
                            break;
}
                        default: {
                            var31[var29++] = var51;
                            if ((var36 += var27) < var30) {
                                var27 = var28.charAt(var36);
                                continue block9;
}
                            var28 = "\u0018\u0086\u00db\u0099G\u008b!\u00fe/\u0015\u00e2\u0000\u0010\u00b7\u00bb\u0080\u00f0\u00d3\u009d\u00d3\u0000\u009b(\t.\u00c3\u0087\u00c8Jbv\u00e0\u0010a\u00cc\u009e\u00fa\u00d7b\u00de\u00d5\u00fc\u00fb\u0084\u00a0c3\u0007\u0013";
                            var30 = "\u0018\u0086\u00db\u0099G\u008b!\u00fe/\u0015\u00e2\u0000\u0010\u00b7\u00bb\u0080\u00f0\u00d3\u009d\u00d3\u0000\u009b(\t.\u00c3\u0087\u00c8Jbv\u00e0\u0010a\u00cc\u009e\u00fa\u00d7b\u00de\u00d5\u00fc\u00fb\u0084\u00a0c3\u0007\u0013".length();
                            var27 = 32;
                            var36 = -1;
}
}
                    var37 = var28.substring(++var36, var36 + var27);
                    var10001 = 0;
}
}
}
        catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException var33) {
            throw new RuntimeException(var33);
}
}
    // R11_SEMANTIC_RECOVERY_MARKER
    static {
        KEY_OFFSETS = new byte[]{39, 57, 59, 32, 29, 12, 48, 9, 40, 35, 20, 47, 44, 1, 25, 42, 11, 5, 28, 36, 41, 27, 14, 60, 2, 45, 52, 31, 23, 38, 62, 33, 24, 17, 15, 0, 37, 8, 46, 53, 61, 21, 30, 6, 16, 49, 51, 3, 55, 18, 50, 34, 63, 22, 10, 58, 56, 26, 54, 19, 4, 13, 43, 7};
        a = 55479544243313L;
        zkm$clinit();
        H = new LinkedHashMap<Integer, String>();
        G = new CopyOnWriteArraySet<BlockPos>();
        I = null;
}
}