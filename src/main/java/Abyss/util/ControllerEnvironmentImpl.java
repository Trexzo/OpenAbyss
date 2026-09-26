/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.java.games.input.Controller
 *  net.java.games.input.ControllerEnvironment
 *  net.java.games.util.plugins.Plugins
 */
package Abyss.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.util.plugins.Plugins;

public class ControllerEnvironmentImpl
extends ControllerEnvironment {
    private final Collection<String> x = new ArrayList<String>();
    private ArrayList<Controller> N;
    private void c(String var1) {
        File var2 = new File(var1);
        if (var2.exists()) {
            try {
                Class[] var4;
                Plugins var3 = new Plugins(var2);
                for (Class var8 : var4 = var3.getExtends(ControllerEnvironment.class)) {
                    ControllerEnvironment var9 = (ControllerEnvironment)var8.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                    if (!var9.isSupported()) continue;
                    this.k(var9.getControllers());
                    this.x.add(var9.getClass().getName());
}
}
            catch (Exception exception) {
                // empty catch block
}
}
}
    private void k(Controller[] var1) {
        this.N.addAll(Arrays.asList(var1));
}
    public void d(long var1) {
        String var3 = System.getProperty("jinput.controllerPluginPath");
        if (var3 == null) {
            var3 = "controller";
}
        this.c(System.getProperty("java.home") + File.separator + "lib" + File.separator + var3);
        this.c(System.getProperty("user.dir") + File.separator + var3);
}
    public boolean isSupported() {
        return true;
}
    public Controller[] getControllers() {
        if (this.N == null) {
            this.N = new ArrayList();
            this.d(0L);
            ArrayList<String> var5 = new ArrayList<String>();
            String var6 = System.getProperty("os.name", "").trim();
            if (var6.equalsIgnoreCase("Linux")) {
                var5.add("net.java.games.input.LinuxEnvironmentPlugin");
            } else if (var6.equalsIgnoreCase("Mac OS X")) {
                var5.add("net.java.games.input.OSXEnvironmentPlugin");
            } else if (var6.contains("Windows")) {
                var5.add("net.java.games.input.DirectAndRawInputEnvironmentPlugin");
}
            for (String var8 : var5) {
                try {
                    if (this.x.contains(var8)) continue;
                    Class<?> var9 = Class.forName(var8);
                    ControllerEnvironment var10 = (ControllerEnvironment)var9.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                    this.k(var10.getControllers());
                    this.x.add(var10.getClass().getName());
}
                catch (Exception exception) {}
}
}
        return this.N.toArray(new Controller[0]);
}
}