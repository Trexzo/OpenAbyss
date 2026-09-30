/*
 * Decompiled with CFR 0.152.
 */
package com.formdev.flatlaf.util;

import com.formdev.flatlaf.util.LoggingFacade;
import com.formdev.flatlaf.util.SystemInfo;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import javax.swing.JComponent;

public class JavaCompatibility {
    private static MethodHandle drawStringUnderlineCharAtMethod;
    private static MethodHandle getClippedStringMethod;

    public static void drawStringUnderlineCharAt(JComponent c, Graphics g, String text, int underlinedIndex, int x, int y) {
        synchronized (JavaCompatibility.class) {
            if (drawStringUnderlineCharAtMethod == null) {
                try {
                    Class<?> cls = Class.forName(SystemInfo.isJava_9_orLater ? "javax.swing.plaf.basic.BasicGraphicsUtils" : "sun.swing.SwingUtilities2");
                    MethodType mt = MethodType.methodType(Void.TYPE, SystemInfo.isJava_9_orLater
                            ? new Class[]{JComponent.class, Graphics2D.class, String.class, Integer.TYPE, Float.TYPE, Float.TYPE}
                            : new Class[]{JComponent.class, Graphics.class, String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE});
                    drawStringUnderlineCharAtMethod = MethodHandles.publicLookup().findStatic(cls, "drawStringUnderlineCharAt", mt);
}
                catch (Exception ex) {
                    LoggingFacade.INSTANCE.logSevere(null, ex);
                    throw new RuntimeException(ex);
}
}
}
        try {
            if (SystemInfo.isJava_9_orLater) {
                drawStringUnderlineCharAtMethod.invoke(c, (Graphics2D)g, text, underlinedIndex, (float)x, (float)y);
            } else {
                drawStringUnderlineCharAtMethod.invoke(c, g, text, underlinedIndex, x, y);
}
}
        catch (Throwable ex) {
            LoggingFacade.INSTANCE.logSevere(null, ex);
            throw new RuntimeException(ex);
}
}

    public static String getClippedString(JComponent c, FontMetrics fm, String string, int availTextWidth) {
        synchronized (JavaCompatibility.class) {
            if (getClippedStringMethod == null) {
                try {
                    Class<?> cls = Class.forName(SystemInfo.isJava_9_orLater ? "javax.swing.plaf.basic.BasicGraphicsUtils" : "sun.swing.SwingUtilities2");
                    MethodType mt = MethodType.methodType(String.class, JComponent.class, FontMetrics.class, String.class, Integer.TYPE);
                    getClippedStringMethod = MethodHandles.publicLookup().findStatic(cls, SystemInfo.isJava_9_orLater ? "getClippedString" : "clipStringIfNecessary", mt);
}
                catch (Exception ex) {
                    LoggingFacade.INSTANCE.logSevere(null, ex);
                    throw new RuntimeException(ex);
}
}
}
        try {
            return (String)getClippedStringMethod.invoke(c, fm, string, availTextWidth);
}
        catch (Throwable ex) {
            LoggingFacade.INSTANCE.logSevere(null, ex);
            throw new RuntimeException(ex);
}
}

}