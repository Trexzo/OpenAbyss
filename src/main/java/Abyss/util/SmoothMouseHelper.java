/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.util.concurrent.AtomicDouble
 *  net.java.games.input.Mouse
 *  net.minecraft.client.Minecraft
 *  net.minecraft.util.MouseHelper
 */
package Abyss.util;

import Abyss.util.ControllerEnvironmentImpl;
import Abyss.util.MinecraftRef;
import com.google.common.util.concurrent.AtomicDouble;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import net.java.games.input.Mouse;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MouseHelper;

public class SmoothMouseHelper
extends MouseHelper {
    private static long d;
    private final ScheduledExecutorService A;
    private ControllerEnvironmentImpl p;
    private final AtomicDouble a;
    private static long c;
    private Set<Mouse> o;
    private static Minecraft x;
    private final AtomicDouble I;
    private final AtomicBoolean M;
    private static long b;

    public void M() {
        this.A.shutdown();
        this.p = null;
}
    private void V() {
        if (MinecraftRef.c((byte)0, (long)0L).currentScreen == null) {
            this.o.forEach(var1x -> {
                var1x.poll();
                this.I.addAndGet((double)var1x.getX().getPollData());
                this.a.addAndGet((double)var1x.getY().getPollData());
            });
}
}
    public void mouseXYChange() {
        this.deltaX = (int)this.I.getAndSet(0.0);
        this.deltaY = (int)(-this.a.getAndSet(0.0));
}
    public SmoothMouseHelper(long var1) {
        var1 = b ^ var1;
        this.I = new AtomicDouble();
        this.a = new AtomicDouble();
        this.M = new AtomicBoolean(false);
        this.A = Executors.newScheduledThreadPool((int)c);
        this.o = new HashSet<Mouse>();
}
    public void f(long var1) {
        var1 = b ^ var1;
        int var3 = (int)((var1 ^ 0xFC9C826F8C6L) >>> 56);
        try {
            MinecraftRef.c((byte)((byte)var3), (long)0L).mouseHelper = this;
            this.p = new ControllerEnvironmentImpl();
            this.A.scheduleAtFixedRate(this::V, 0L, 1L, TimeUnit.MILLISECONDS);
            this.A.scheduleAtFixedRate(this::j, 0L, d, TimeUnit.MILLISECONDS);
}
        catch (NullPointerException nullPointerException) {
            // empty catch block
}
}
    private void j() {
        boolean var8;
        boolean bl = var8 = MinecraftRef.c((byte)0, (long)0L).currentScreen != null;
        if (var8 && !this.M.get()) {
            this.p.d(17775678696637L);
            this.o = Arrays.stream(this.p.getControllers()).filter(Mouse.class::isInstance).map(Mouse.class::cast).collect(Collectors.toSet());
}
        this.M.set(var8);
}
    static {
        b = 104171904487733L;
        x = MinecraftRef.c((byte)0, 0L);
        c = -6439579045398052856L;
        d = 50L;
}
}