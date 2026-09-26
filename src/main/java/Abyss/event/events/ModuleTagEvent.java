/*
 * Decompiled with CFR 0.152.
 */
package Abyss.event.events;

import Abyss.event.Event;
import java.util.Arrays;
import java.util.List;

public class ModuleTagEvent
extends Event {
    private StringBuilder K = new StringBuilder();

    public void U(String var1) {
        this.K.append(var1).append("\n");
}
    public void y(String var1) {
        this.K.append(var1);
}
    public List<String> i() {
        return Arrays.asList(this.K.toString().split("\n"));
}
}