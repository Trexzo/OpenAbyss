/*
 * Decompiled with CFR 0.152.
 */
package loader_forgemod;

import java.util.Iterator;
import loader_forgemod.H;
import loader_forgemod.g_2;

class i_2
implements Iterator {
    final Iterator a;
    final Iterator b;
    final g_2 c;

    i_2(g_2 var1, Iterator var2, Iterator var3) {
        this.c = var1;
        this.a = var2;
        this.b = var3;
}
    @Override
    public native boolean hasNext();

    public native H a();

    @Override
    public native void remove();

    public native Object next();
}