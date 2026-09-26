/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.StringUtils
 */
package Abyss.module.impl.visual;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.KillEffectBinder;
import Abyss.event.events.EntityJoinWorldEvent;
import Abyss.event.events.HandleChatEvent;
import Abyss.event.events.LivingDeathEvent;
import Abyss.event.events.PostRenderEvent;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.module.impl.visual.KillEffectDeathPos;
import Abyss.setting.Setting;
import Abyss.setting.settings.BooleanSetting;
import Abyss.setting.settings.ModeSetting;
import Abyss.util.render.LightningRenderer;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StringUtils;

public class KillEffect
extends Module
implements EventSubscriber {
    private static long a;
    private static String[] b;
    private static Map e;
    private static String[] c;
    public static BooleanSetting onlyKilledBySelf;
    
    private static Object[] g;
    private final Map<String, KillEffectDeathPos> F;
    private static String[] h;
    public static ModeSetting mode;
            

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
        KillEffect.g[0] = "ah\u001f\u0000O?I";
        KillEffect.g[1] = "B\n\u001e\u0001\u0003[u\u001d\u001a\u000bN\u007fb\u0016@\u0017";
        KillEffect.g[2] = "\"jf'\u0001%+";
        KillEffect.g[3] = Long.TYPE;
        KillEffect.h[3] = "java/lang/Long";
        KillEffect.g[4] = Void.TYPE;
        KillEffect.h[4] = "java/lang/Void";
        KillEffect.g[5] = "`F~d'6kIo+F8`Bkq";
        KillEffect.g[6] = "%'\\\u000f\u0003F>+Yt072-[\b\u0005Y&~RtQP*:Z\r\u0004[u!9N\u000e^%x\u0002L\u0018Lr@\u0002M\u0001\r=1@\u000f\u0006LO";
}
    public void onEntityJoinWorld(EntityJoinWorldEvent var1) {
        if (var1.H.equals((Object)KillEffect.f.thePlayer)) {
            this.F.clear();
}
}
    private void R(KillEffectDeathPos var1, long var2) {
        switch (mode.Y()) {
            case "BLOOD": {
                LightningRenderer.f(1, 6021109416714L, var1.G, var1.L, var1.S, var1.w);
                break;
}
            case "LIGHTNING": {
                LightningRenderer.f(3, 6021109416714L, var1.G, var1.L, var1.S, var1.w);
                break;
}
            case "SOUL_BREAK": {
                LightningRenderer.f(2, 6021109416714L, var1.G, var1.L, var1.S, var1.w);
}
}
}
    @Override
    public String g(long var1) {
        return mode.Y();
}
    public void onLivingDeath(LivingDeathEvent var1, long var2) {
        boolean var6;
        boolean bl = var6 = !onlyKilledBySelf.c() || var1.M.getEntity() != null && var1.M.getEntity().equals((Object)KillEffect.f.thePlayer);
        if (var6 && var1.p != KillEffect.f.thePlayer) {
            String var7 = var1.p.getName();
            KillEffectDeathPos var8 = this.F.remove(var7);
            if (var8 != null) {
                this.R(var8, 24462074121926L);
            } else {
                EntityLivingBase var9 = var1.p;
                this.R(new KillEffectDeathPos(var9.posX, var9.posY, var9.posZ, var9.getEyeHeight()), 24462074121926L);
}
}
}
    public void onPostRender(PostRenderEvent var1) {
        if (var1.z instanceof EntityPlayer && var1.z != KillEffect.f.thePlayer) {
            EntityLivingBase var2 = var1.z;
            this.F.put(var2.getName(), new KillEffectDeathPos(var2.posX, var2.posY, var2.posZ, var2.getEyeHeight()));
}
}
    public void onHandleChat(long var1, HandleChatEvent var3) {
        String var7;
        KillEffectDeathPos var8;
        String var6 = StringUtils.stripControlCodes((String)var3.A.getUnformattedText());
        if (KillEffect.f.thePlayer != null && !var6.contains(":") && var6.contains("by " + KillEffect.f.thePlayer.getName()) && (var8 = this.F.remove(var7 = var6.trim().split(" ")[0])) != null) {
            this.R(var8, 24462074121926L);
}
}
    public KillEffect(long var1) {
        super(a ^ var1 ^ 0x291F255CE722L);
        this.declare("KillEffect", Category.Visual, "Play some effects after you killed your enemy", new Setting[0]);
        var1 = a ^ var1;
        this.F = new ConcurrentHashMap<String, KillEffectDeathPos>();
}
    @Override
    public final void x(long var1, EventBus var3) {
        KillEffectBinder.P(var3, this);
}
    public void t(LivingDeathEvent var1, long var2) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (var1.p != null) {
            boolean var7;
            boolean bl = var7 = var1.M.getEntity() != null && var1.M.getEntity().equals((Object)KillEffect.f.thePlayer);
            if (!onlyKilledBySelf.c() || var7) {
                switch (mode.Y()) {
                    case "BLOOD": {
                        LightningRenderer.E((short)0, (Entity)var1.p, 1, (short)31551, 0);
                        break;
}
                    case "LIGHTNING": {
                        LightningRenderer.E((short)0, (Entity)var1.p, 3, (short)31551, 0);
                        break;
}
                    case "SOUL_BREAK": {
                        LightningRenderer.E((short)0, (Entity)var1.p, 2, (short)31551, 0);
}
}
}
}
}
    private static void zkm$clinit() {
        try {
            g = new Object[7]; h = new String[7]; a(); e = new HashMap(13); long var0 = a ^ 75590332247018L;
            byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};
            for (int var3 = 1; var3 < 8; ++var3) { var10003[var3] = (byte)(var0 << var3 * 8 >>> 56); }
            Cipher var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
            var2.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
            String[] var9 = new String[7];
            int var7 = 0;
            String var6 = "u\u00efK\u00d7:\u0007\u00a5p\u00b7\u0091e2\u0097JV\u00ed\u0018\u00d2\u00f4\u00e2\u00b7\u00a2i\u008b[* n\u00cb\u00acM\u00e7\u00da\u00b3\u00ac\u00f2IvKZI\u0010\u000fAE\u00ee'-P\u00da\u00db\u00d5\u00c0\u00e0\u00b6\u009b\u00cf\u0001 \u0096\u0017\r\u00cd\u00f1\u0005\u0083\u00dd\u0083\u008d]\u00cc\u0014+\u00f2\u00feiq\u00f3\u00d3*\u00f8\u00a4\u0012|\u008b5\u00c8$\u00ced\u00c2\u0018[\u00b8\u00b0z\u009b\u00a2\u00ea\u008c\u00c6rbt\u00f4 \u0080!\"\u008a\u00c7a\u00d0c\u00b9\u0002";
            int var8 = "u\u00efK\u00d7:\u0007\u00a5p\u00b7\u0091e2\u0097JV\u00ed\u0018\u00d2\u00f4\u00e2\u00b7\u00a2i\u008b[* n\u00cb\u00acM\u00e7\u00da\u00b3\u00ac\u00f2IvKZI\u0010\u000fAE\u00ee'-P\u00da\u00db\u00d5\u00c0\u00e0\u00b6\u009b\u00cf\u0001 \u0096\u0017\r\u00cd\u00f1\u0005\u0083\u00dd\u0083\u008d]\u00cc\u0014+\u00f2\u00feiq\u00f3\u00d3*\u00f8\u00a4\u0012|\u008b5\u00c8$\u00ced\u00c2\u0018[\u00b8\u00b0z\u009b\u00a2\u00ea\u008c\u00c6rbt\u00f4 \u0080!\"\u008a\u00c7a\u00d0c\u00b9\u0002".length();
            int var5 = 16;
            int var13 = -1;
            block6: while (true) {
                String var14 = var6.substring(++var13, var13 + var5);
                int var10001 = -1;
                while (true) {
                    byte[] var10 = var2.doFinal(var14.getBytes("ISO-8859-1"));
                    String var20 = KillEffect.b(var10).intern();
                    switch (var10001) {
                        case 0: {
                            var9[var7++] = var20;
                            if ((var13 += var5) >= var8) {
                                b = var9;
                                c = new String[7];
                                return;
}
                            var5 = var6.charAt(var13);
                            break;
}
                        default: {
                            var9[var7++] = var20;
                            if ((var13 += var5) < var8) {
                                var5 = var6.charAt(var13);
                                continue block6;
}
                            var6 = "\u00e0\u009d;t\u00ffr0E\u00f5\u0087_fF\u00e9\u001e\u00bf{.y\u00c1\u0006\u00d2\u008e\u00e9\u00ab\u0002:^^\u00c4)\u0013\u0010\u00aeETy&\u001ec\u00f2\u009f\u00abEHW=\u00f3M";
                            var8 = "\u00e0\u009d;t\u00ffr0E\u00f5\u0087_fF\u00e9\u001e\u00bf{.y\u00c1\u0006\u00d2\u008e\u00e9\u00ab\u0002:^^\u00c4)\u0013\u0010\u00aeETy&\u001ec\u00f2\u009f\u00abEHW=\u00f3M".length();
                            var5 = 32;
                            var13 = -1;
}
}
                    var14 = var6.substring(++var13, var13 + var5);
                    var10001 = 0;
}
}
}
        catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException var11) {
            throw new RuntimeException(var11);
}
}
    static {
        a = 117327217342098L;
        zkm$clinit();
        mode = new ModeSetting("Mode", false, "BLOOD", "NONE", "BLOOD", "LIGHTNING", "SOUL_BREAK");
        onlyKilledBySelf = new BooleanSetting("Only-killed-by-self", false);
}
}