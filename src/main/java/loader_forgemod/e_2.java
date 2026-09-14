/*
 * Decompiled with CFR 0.152.
 */
package loader_forgemod;

import java.io.IOException;
import loader_forgemod.J;
import loader_forgemod.n;

class e_2
extends n {
    private final String a;

    e_2(String var1) {
        if (var1 == null) {
            throw new NullPointerException("string is null");
}
        this.a = var1;
}
    @Override
    native void a(J var1) throws IOException;

    @Override
    public native boolean b();

    @Override
    public native String c();

    @Override
    public native int hashCode();

    @Override
    public native boolean equals(Object var1);
}