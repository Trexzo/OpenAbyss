/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityOtherPlayerMP
 */
package Abyss.module.impl.player;

import Abyss.module.Category;
import Abyss.module.Module;
import Abyss.setting.Setting;
import net.minecraft.client.entity.EntityOtherPlayerMP;

public class FreeCam
extends Module {
    private EntityOtherPlayerMP r;
    private static double x;
    private float B;
    private boolean s;
    private float R;
    private static double n;
    private static double T;

    public FreeCam(long var1) {
        super(0x374F17214520L ^ var1 ^ 0x702352478879L);
        this.declare("FreeCam", Category.Player, "This module is currently disabled", new Setting[0]);
        var1 = 0x374F17214520L ^ var1;
}
}