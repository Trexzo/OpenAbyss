/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.Logger
 */
package Abyss.internal.jnic;

import Abyss.internal.restore.AbyssBootstrap;
import Abyss.internal.restore.AbyssRavenGui;
import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.setting.Setting;
import java.util.Map;
import org.apache.logging.log4j.Logger;

public class StockClientBootstrap {
    private static long[] e;
    
    private static Map g;
    private static volatile boolean F;
    
    
        private static Logger r;
    public static boolean $skidonion$891820656;

    public static native void q(long var0);

    public static native void Z(Module var0, String var1, long var2, Boolean var4, Category var5, Boolean var6, String var7, boolean var8, boolean var9, Setting ... var10);

    public static void F() {
        AbyssBootstrap.initClient();
}
    public static void P(int var0) {
        AbyssRavenGui.installPanels();
}
    public static native void W(long var0, Module var2, String var3, Boolean var4, Category var5, Boolean var6, String var7, Setting ... var8);
}