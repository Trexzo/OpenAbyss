/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.MovingObjectPosition
 *  net.minecraft.util.Vec3
 */
package Abyss.module.impl.visual_utility;

import Abyss.internal.synthetic.TrajectoriesSwitchMapAxis;
import java.util.ArrayList;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class TrajectorySimulationResult {
    private MovingObjectPosition a;
    private boolean C;
    private final ArrayList<Vec3> t = new ArrayList();
    private double o;
    private boolean T;
    private double r;
    private double u;
    private int f;

    public static double R(TrajectorySimulationResult var0) {
        return var0.r;
}
    private TrajectorySimulationResult() {
}
    public static boolean T(TrajectorySimulationResult var0, boolean var1) {
        var0.C = var1;
        return var0.C;
}
    public static MovingObjectPosition E(TrajectorySimulationResult var0, MovingObjectPosition var1) {
        var0.a = var1;
        return var0.a;
}
    public static double B(TrajectorySimulationResult var0) {
        return var0.u;
}
    public static int g(TrajectorySimulationResult var0, int var1) {
        var0.f = var1;
        return var0.f;
}
    public static boolean L(TrajectorySimulationResult var0) {
        return var0.T;
}
    public static ArrayList b(TrajectorySimulationResult var0) {
        return var0.t;
}
    public static boolean Q(TrajectorySimulationResult var0, boolean var1) {
        var0.T = var1;
        return var0.T;
}
    public TrajectorySimulationResult(TrajectoriesSwitchMapAxis var1) {
        this();
}
    public static boolean c(TrajectorySimulationResult var0) {
        return var0.C;
}
    public static double M(TrajectorySimulationResult var0, double var1) {
        var0.o = var1;
        return var0.o;
}
    public static double l(TrajectorySimulationResult var0) {
        return var0.o;
}
    public static MovingObjectPosition R$r1(TrajectorySimulationResult var0) {
        return var0.a;
}
    public static double N(TrajectorySimulationResult var0, double var1) {
        var0.r = var1;
        return var0.r;
}
    public static int K(TrajectorySimulationResult var0) {
        return var0.f;
}
    public static double B(TrajectorySimulationResult var0, double var1) {
        var0.u = var1;
        return var0.u;
}
}