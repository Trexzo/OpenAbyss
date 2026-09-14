/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.EnumFacing
 */
package Abyss.internal.synthetic;

import net.minecraft.util.EnumFacing;

public class BlockUtilSwitchMapEnumFacing {
    public static final int[] b = new int[EnumFacing.values().length];

    static {
        try {
            BlockUtilSwitchMapEnumFacing.b[EnumFacing.UP.ordinal()] = 1;
}
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
}
        try {
            BlockUtilSwitchMapEnumFacing.b[EnumFacing.NORTH.ordinal()] = 2;
}
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
}
        try {
            BlockUtilSwitchMapEnumFacing.b[EnumFacing.EAST.ordinal()] = 3;
}
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
}
        try {
            BlockUtilSwitchMapEnumFacing.b[EnumFacing.SOUTH.ordinal()] = 4;
}
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
}
        try {
            BlockUtilSwitchMapEnumFacing.b[EnumFacing.WEST.ordinal()] = 5;
}
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
}
}
}