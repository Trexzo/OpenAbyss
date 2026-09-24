/*
 * Decompiled with CFR 0.152.
 */
package Abyss.module.impl.misc;

import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.setting.Setting;

public class NoObfuscation
extends Module {
    private static long a;

    private static String b = "\u00a7k";

    public static String f(String var2) {
        if (var2 == null) {
            return var2;
}
        return var2.replace(b, "");
}
    public NoObfuscation(long var1) {
        super(a ^ var1 ^ 0x55279F6C3841L);
        this.declare("NoObfuscation", Category.Misc, "Remove the obfuscation minecraft chat code", new Setting[0]);
        var1 = a ^ var1;
}
    static {
        a = 82802124200324L;
    }
}