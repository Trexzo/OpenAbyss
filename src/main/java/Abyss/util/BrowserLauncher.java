/*
 * Decompiled with CFR 0.152.
 */
package Abyss.util;

import Abyss.AbyssClient;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.net.URI;

public class BrowserLauncher {
    public static void Y(String var0) {
        try {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(var0), null);
}
        catch (Exception exception) {
            AbyssClient.recordFeatureFailure("BrowserLauncher", "clipboard-copy", exception);
}
}
    public static void F(URI var0) {
        try {
            Class<?> var3 = Class.forName("java.awt.Desktop");
            Object var4 = var3.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
            var3.getMethod("browse", URI.class).invoke(var4, var0);
}
        catch (Exception exception) {
            AbyssClient.recordFeatureFailure("BrowserLauncher", "browser-open", exception);
}
}
}