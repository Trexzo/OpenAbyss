/*
 * Decompiled with CFR 0.152.
 */
package Abyss.ui.raven;

import Abyss.setting.Setting;
import Abyss.ui.raven.RavenClickGuiScreen;
import Abyss.ui.raven.RavenModuleRow;
import Abyss.util.render.CustomFont;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public interface RavenElement {
    public static final long e = 66652673112161L;

    default public void V(long var1, int var3, int var4) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
}
    default public void f(int var1, int var2, int var3, int var4, short var5, int var6) {
}
    default public void i(int var1, int var2, int var3, byte var4) {
}
    default public void c(char var1, int var2, long var3) {
}
    default public Setting f() {
        return null;
}
    default public CustomFont C(long var1) {
        return RavenClickGuiScreen.t();
}
    default public void r(char var1, int var2, int var3, int var4, long var5) throws UnsupportedEncodingException, Throwable, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
}
    default public void c(int var1, long var2, int var4) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        this.V(0L, var1, var4);
}
    default public void W(long var1) {
}
    default public void U(long var1) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
}
    public RavenModuleRow C();

    default public int E(long var1) {
        return 0;
}
}