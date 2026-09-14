/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.launchwrapper.IClassTransformer
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package Abyss.ASM;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import net.minecraft.launchwrapper.IClassTransformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EncryptedClassMarkerHook
implements IClassTransformer {
    private static final Logger LOG = LogManager.getLogger((String)"Abyss Class Transformer");
    private static final byte[] MAGIC = new byte[]{69, 88, 80, 79, 69, 78, 67, 49};
    private static final int IV_LEN = 12;
    private static final int TAG_BITS = 128;
    private static final byte[] KEY;
    private static boolean warned;

    public EncryptedClassMarkerHook() {
        LOG.info("Class Transformer initialized");
}
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (basicClass == null || !EncryptedClassMarkerHook.startsWithMagic(basicClass)) {
            return basicClass;
}
        if (KEY == null) {
            if (!warned) {
                warned = true;
                LOG.error("Encrypted class encountered but no valid -Dabyss.payload.key was provided; protected classes cannot be decrypted.");
}
            return basicClass;
}
        try {
            return EncryptedClassMarkerHook.decrypt(basicClass);
}
        catch (Throwable t2) {
            LOG.error("Failed to decrypt protected class " + transformedName, t2);
            return basicClass;
}
}
    private static boolean startsWithMagic(byte[] b) {
        if (b.length < MAGIC.length + 1 + 12) {
            return false;
}
        for (int i = 0; i < MAGIC.length; ++i) {
            if (b[i] == MAGIC[i]) continue;
            return false;
}
        return true;
}
    private static byte[] decrypt(byte[] blob) throws Exception {
        byte version;
        int off = MAGIC.length;
        if ((version = blob[off++]) != 1) {
            throw new IllegalStateException("unsupported payload version " + version);
}
        byte[] iv = new byte[12];
        System.arraycopy(blob, off, iv, 0, 12);
        int ctLen = blob.length - (off += 12);
        byte[] ct = new byte[ctLen];
        System.arraycopy(blob, off, ct, 0, ctLen);
        Cipher c = Cipher.getInstance("AES/GCM/NoPadding");
        c.init(2, (Key)new SecretKeySpec(KEY, "AES"), new GCMParameterSpec(128, iv));
        return c.doFinal(ct);
}
    private static byte[] hex(String s) {
        int n2 = s.length() / 2;
        byte[] b = new byte[n2];
        for (int i = 0; i < n2; ++i) {
            b[i] = (byte)Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16);
}
        return b;
}
    static {
        byte[] k = null;
        try {
            String hex = System.getProperty("abyss.payload.key");
            if (hex != null && hex.trim().length() == 64) {
                k = EncryptedClassMarkerHook.hex(hex.trim());
}
}
        catch (Throwable throwable) {
            // empty catch block
}
        KEY = k;
}
}