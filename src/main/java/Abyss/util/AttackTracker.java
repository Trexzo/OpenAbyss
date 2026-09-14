/*
 * Decompiled with CFR 0.152.
 */
package Abyss.util;

import Abyss.event.EventBus;
import Abyss.event.EventSubscriber;
import Abyss.event.binder.AttackTrackerBinder;
import Abyss.event.events.AttackEntityEvent;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class AttackTracker
implements EventSubscriber {
    public static boolean s;
    

    public static boolean J() {
        return s;
}
                Cipher var2 = Cipher.getInstance("DES/CBC/NoPadding");
            var2.init(2, (Key)SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
            byte[] var6 = var2.doFinal(new byte[]{23, 27, 63, -73, 35, 19, -18, 1});
            long var0 = var10 = ((long)var6[0] & 0xFFL) << 56 | ((long)var6[1] & 0xFFL) << 48 | ((long)var6[2] & 0xFFL) << 40 | ((long)var6[3] & 0xFFL) << 32 | ((long)var6[4] & 0xFFL) << 24 | ((long)var6[5] & 0xFFL) << 16 | ((long)var6[6] & 0xFFL) << 8 | (long)var6[7] & 0xFFL;
            s = (var0 & 1L) != 0L;
}
        catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException var9) {
            throw new RuntimeException(var9);
}
}
    public void onAttackEntity(AttackEntityEvent var1, long var2) {
        if (!s) {
            var1.I(21307, 3074332907L);
}
}
    public static void Z(boolean var0) {
        s = var0;
}
    private static void a() {
}
    @Override
    public final void x(long var1, EventBus var3) {
        AttackTrackerBinder.D(var3, this);
}
    static {
}
}