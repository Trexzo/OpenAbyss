/*
 * Decompiled with CFR 0.152.
 */
package Abyss.setting.settings;

import Abyss.setting.Setting;

public class BooleanSetting
extends Setting {
    private static final long private boolean h;

    private boolean Z() {
        return this.h;
}
    public void W(long var1) throws Throwable {
        this.v(!this.Z(), 0L);
}
    public BooleanSetting(String var1, boolean var2) {
        this.q = var1;
        this.h = var2;
}
    public boolean c() {
        return this.h;
}
    public void v(boolean var1, long var2) throws Throwable {
        this.h = var1;
}
}