/*
 * Decompiled with CFR 0.152.
 */
package Abyss.module;

import Abyss.module.ModulePriorityEntry;
import Abyss.module.PriorityModule;
import Abyss.module.impl.combat.AntiFireball;
import Abyss.module.impl.combat.KillAura;
import Abyss.module.impl.player.ChestAura;
import Abyss.module.impl.player.InvManager;
import Abyss.module.impl.world.AutoDigPlace;
import Abyss.module.impl.world.AutoTool;
import Abyss.module.impl.world.BedNuker;
import Abyss.module.impl.world.BlockIn;
import Abyss.module.impl.world.Nuker;
import Abyss.module.impl.world.Scaffold;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
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

public class ModulePriority {
    public static List<ModulePriorityEntry> l;
    public static Map<Class<? extends PriorityModule>, ModulePriorityEntry> C;

    public static boolean c(Class<? extends PriorityModule> var0) {
        ModulePriorityEntry var1 = C.get(var0);
        if (var1 == null) {
            return true;
}
        for (int var2 = var1.L + 1; var2 < l.size() - 1; ++var2) {
            ModulePriorityEntry var3 = l.get(var2);
            if (!ModulePriorityEntry.r(var3)) continue;
            return false;
}
        return true;
}
    public static void U(Class<? extends PriorityModule> var0, boolean var1) {
        ModulePriorityEntry var2 = C.get(var0);
        if (var2 != null) {
            ModulePriorityEntry.z(var2, var1);
}
}
    private static void zkm$clinit() {
        try {
            Cipher var1;
            byte[] var10003 = new byte[]{(byte)0L, 0, 0, 0, 0, 0, 0, 0};
            for (int var2 = 1; var2 < 8; ++var2) {
                var10003[var2] = (byte)(132284884015632L << var2 * 8 >>> 56);
            }
            (var1 = Cipher.getInstance("DES/CBC/NoPadding")).init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
            long[] var0 = new long[9];
            int var4 = 0;
            String var5 = "H\u00dc\f/\u001fYh\u009e\u00da\u008e\u00d0~\u00aeL\u009a\u0080{0A~;\u0087\u00c5m\u0004\"\u00dd\u00a0|\u00a6\u00cc[\u001e\u00bd\u00ff\u00c9\u00e7\u00a2\b\u00c9\u00eb\u0011\u00d0\u0093jC\u00f7\u00cd\u0002l\u008a\u00b4\u00ad\u00d6+\u00bb";
            int var6 = "H\u00dc\f/\u001fYh\u009e\u00da\u008e\u00d0~\u00aeL\u009a\u0080{0A~;\u0087\u00c5m\u0004\"\u00dd\u00a0|\u00a6\u00cc[\u001e\u00bd\u00ff\u00c9\u00e7\u00a2\b\u00c9\u00eb\u0011\u00d0\u0093jC\u00f7\u00cd\u0002l\u008a\u00b4\u00ad\u00d6+\u00bb".length();
            int var3 = 0;
            block6: while (true) {
                int var10001 = var3;
                byte[] var7 = var5.substring(var10001, var3 += 8).getBytes("ISO-8859-1");
                long[] var19 = var0;
                var10001 = var4++;
                long var23 = ((long)var7[0] & 0xFFL) << 56 | ((long)var7[1] & 0xFFL) << 48 | ((long)var7[2] & 0xFFL) << 40 | ((long)var7[3] & 0xFFL) << 32 | ((long)var7[4] & 0xFFL) << 24 | ((long)var7[5] & 0xFFL) << 16 | ((long)var7[6] & 0xFFL) << 8 | (long)var7[7] & 0xFFL;
                int var25 = -1;
                while (true) {
                    long var8 = var23;
                    byte[] var10 = var1.doFinal(new byte[]{(byte)(var8 >>> 56), (byte)(var8 >>> 48), (byte)(var8 >>> 40), (byte)(var8 >>> 32), (byte)(var8 >>> 24), (byte)(var8 >>> 16), (byte)(var8 >>> 8), (byte)var8});
                    long var27 = ((long)var10[0] & 0xFFL) << 56 | ((long)var10[1] & 0xFFL) << 48 | ((long)var10[2] & 0xFFL) << 40 | ((long)var10[3] & 0xFFL) << 32 | ((long)var10[4] & 0xFFL) << 24 | ((long)var10[5] & 0xFFL) << 16 | ((long)var10[6] & 0xFFL) << 8 | (long)var10[7] & 0xFFL;
                    switch (var25) {
                        case 0: {
                            var19[var10001] = var27;
                            if (var3 < var6) break;
                            C = new HashMap<Class<? extends PriorityModule>, ModulePriorityEntry>();
                            ModulePriorityEntry[] var20 = new ModulePriorityEntry[(int)var0[5]];
                            var20[0] = new ModulePriorityEntry(AutoTool.class, 0, null, 55039588965837L);
                            var20[1] = new ModulePriorityEntry(Nuker.class, 1, null, 55039588965837L);
                            var20[2] = new ModulePriorityEntry(InvManager.class, 2, null, 55039588965837L);
                            var20[3] = new ModulePriorityEntry(ChestAura.class, 3, null, 55039588965837L);
                            var20[4] = new ModulePriorityEntry(AntiFireball.class, 4, null, 55039588965837L);
                            var20[5] = new ModulePriorityEntry(BedNuker.class, 5, null, 55039588965837L);
                            var20[(int)var0[4]] = new ModulePriorityEntry(BlockIn.class, (int)var0[6], null, 55039588965837L);
                            var20[(int)var0[1]] = new ModulePriorityEntry(KillAura.class, (int)var0[3], null, 55039588965837L);
                            var20[(int)var0[8]] = new ModulePriorityEntry(Scaffold.class, (int)var0[0], null, 55039588965837L);
                            var20[(int)var0[7]] = new ModulePriorityEntry(AutoDigPlace.class, (int)var0[2], null, 55039588965837L);
                            l = Arrays.asList(var20);
                            for (ModulePriorityEntry var16 : l) {
                                C.put(var16.P, var16);
}
                            return;
}
                        default: {
                            var19[var10001] = var27;
                            if (var3 < var6) continue block6;
                            var5 = "\u00f9\u00be\u00bb6N\u0097\u00d7\u0002\u001c\u00e5\u0096\u0017\u00c3\u0090\u00d3\u00c6";
                            var6 = "\u00f9\u00be\u00bb6N\u0097\u00d7\u0002\u001c\u00e5\u0096\u0017\u00c3\u0090\u00d3\u00c6".length();
                            var3 = 0;
}
}
                    int var22 = var3;
                    var7 = var5.substring(var22, var3 += 8).getBytes("ISO-8859-1");
                    var19 = var0;
                    var10001 = var4++;
                    var23 = ((long)var7[0] & 0xFFL) << 56 | ((long)var7[1] & 0xFFL) << 48 | ((long)var7[2] & 0xFFL) << 40 | ((long)var7[3] & 0xFFL) << 32 | ((long)var7[4] & 0xFFL) << 24 | ((long)var7[5] & 0xFFL) << 16 | ((long)var7[6] & 0xFFL) << 8 | (long)var7[7] & 0xFFL;
                    var25 = 0;
}
}
}
        catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException var17) {
            throw new RuntimeException(var17);
}
}
    static {
        zkm$clinit();
}
}