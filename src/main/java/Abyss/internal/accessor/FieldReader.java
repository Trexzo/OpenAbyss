/*
 * Decompiled with CFR 0.152.
 */
package Abyss.internal.accessor;

import java.lang.reflect.Field;

@FunctionalInterface
public interface FieldReader<T> {
    public T M(Field var1) throws Throwable;
}