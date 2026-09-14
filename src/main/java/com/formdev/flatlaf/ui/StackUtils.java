/*
 * Decompiled with CFR 0.152.
 */
package com.formdev.flatlaf.ui;

import com.formdev.flatlaf.ui.StackUtilsImpl;
import java.util.function.BiPredicate;

class StackUtils {
    private static final StackUtils INSTANCE = new StackUtilsImpl();

    StackUtils() {
}
    public static boolean wasInvokedFrom(String className, String methodName, int limit) {
        return StackUtils.wasInvokedFrom((c, m2) -> c.equals(className) && m2.equals(methodName), limit);
}
    public static boolean wasInvokedFrom(BiPredicate<String, String> predicate, int limit) {
        return INSTANCE.wasInvokedFromImpl(predicate, limit);
}
    boolean wasInvokedFromImpl(BiPredicate<String, String> predicate, int limit) {
        throw new UnsupportedOperationException();
}
}