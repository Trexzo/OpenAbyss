/*
 * Decompiled with CFR 0.152.
 */
package Abyss.command.impl;

import Abyss.command.Command;
import Abyss.module.Module;
import Abyss.setting.settings.NumberSetting;
import Abyss.setting.settings.TextSetting;
import java.util.List;
import java.util.Map;

public class StockCommandModuleSetting
extends Command {
    
    private static long[] e;
    private static Map g;
        
    

    @Override
    public native String[] e(long var1);

    private native boolean d(long var1, NumberSetting var3, String[] var4);

    private native boolean b(TextSetting var1, short var2, String[] var3, int var4, short var5);

    public static native String O(String var0, String var1, String var2);

    @Override
    public native List g(String[] var1, int var2, long var3);

    private native void d(Module var1, long var2);

    public static native boolean w(String var0, String var1);

    @Override
    public native void j(String[] var1, long var2);
}