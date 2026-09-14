/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 */
package Abyss.internal.accessor;

import Abyss.internal.accessor.Accessor;
import Abyss.internal.accessor.MethodAccessors;
import java.io.IOException;
import net.minecraft.client.gui.GuiScreen;

public final class GuiScreenAccessor {
    private static Accessor V = MethodAccessors.G(GuiScreen.class, "mouseClicked", "mouseClicked", new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE});
    private static Accessor e = MethodAccessors.G(GuiScreen.class, "keyTyped", "keyTyped", new Class[]{Character.TYPE, Integer.TYPE});

    public static void c(GuiScreen var0, int var1, int var2, int var3) {
        Accessor.v(V, new Object[]{var0, var1, var2, var3});
}
    public static void J(GuiScreen var0, char var1, int var2) throws IOException {
        try {
            Accessor.v(e, new Object[]{var0, Character.valueOf(var1), var2});
}
        catch (RuntimeException var4) {
            throw MethodAccessors.H(var4);
}
}
}