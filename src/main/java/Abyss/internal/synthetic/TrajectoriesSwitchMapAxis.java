/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.EnumFacing$Axis
 */
package Abyss.internal.synthetic;

import net.minecraft.util.EnumFacing;

public class TrajectoriesSwitchMapAxis {
    public static final int[] t = new int[EnumFacing.Axis.values().length];

    static {
        try {
            TrajectoriesSwitchMapAxis.t[EnumFacing.Axis.X.ordinal()] = 1;
}
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
}
        try {
            TrajectoriesSwitchMapAxis.t[EnumFacing.Axis.Y.ordinal()] = 2;
}
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
}
        try {
            TrajectoriesSwitchMapAxis.t[EnumFacing.Axis.Z.ordinal()] = 3;
}
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
}
}
}