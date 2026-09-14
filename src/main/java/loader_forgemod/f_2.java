/*
 * Decompiled with CFR 0.152.
 */
package loader_forgemod;

import java.io.IOException;
import loader_forgemod.J;
import loader_forgemod.n;

class f_2
extends n {
    private final String a;

    f_2(String var1) {
        if (var1 == null) {
            throw new NullPointerException("string is null");
}
        this.a = var1;
}
    @Override
    public native String toString();

    @Override
    native void a(J var1) throws IOException;

    @Override
    public native boolean d();

    @Override
    public native int e();

    @Override
    public native long f();

    @Override
    public native float g();

    @Override
    public native double h();

    @Override
    public native int hashCode();

    @Override
    public native boolean equals(Object var1);
}