/*
 * Decompiled with CFR 0.152.
 */
package Abyss.command.impl;

import Abyss.command.AbyssCommands;
import Abyss.command.Command;
import Abyss.internal.restore.AbyssCommandData;
import Abyss.module.impl.configuration.Teams;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public final class AbyssCommandNames
extends Command {
    public static final int ENEMY = 0;
    public static final int FRIEND = 1;
    private final int kind;
    private final String desc;
    private final String[] usage;
    private final String[] aliases;

    public AbyssCommandNames(int var1, String var2, String[] var3, String ... var4) {
        this.kind = var1;
        this.desc = var2;
        this.usage = var3;
        this.aliases = var4;
}
    @Override
    public boolean J() {
        return false;
}
    @Override
    public String[] e(long var1) {
        return this.aliases;
}
    @Override
    public void h(long var1) {
        AbyssCommands.chat(this.desc);
        AbyssCommands.chat("\u00a77Usage:");
        for (int var3 = 0; var3 < this.usage.length; ++var3) {
            AbyssCommands.chat("\u00a7f" + this.usage[var3]);
}
        AbyssCommands.chat("\u00a78Stored in \u00a77" + this.file() + "\u00a78 next to current.json; " + this.set().size() + " name(s) now.");
}
    @Override
    public void j(String[] var1, long var2) {
        String var4 = var1[0];
        if ("list".equalsIgnoreCase(var4)) {
            this.list();
        } else if ("clear".equalsIgnoreCase(var4)) {
            this.clear();
        } else if ("add".equalsIgnoreCase(var4)) {
            if (var1.length < 2) {
                AbyssCommands.chat("\u00a7cUsage: \u00a7f." + this.aliases[0] + " add <name...>");
            } else {
                this.add(var1);
}
        } else if ("remove".equalsIgnoreCase(var4)) {
            if (var1.length < 2) {
                AbyssCommands.chat("\u00a7cUsage: \u00a7f." + this.aliases[0] + " remove <name...>");
            } else {
                this.remove(var1);
}
        } else {
            this.h(0L);
}
}
    @Override
    public List g(String[] var1, int var2, long var3) {
        ArrayList<String> var5 = new ArrayList<String>();
        if (var2 <= 1) {
            var5.addAll(Arrays.asList("add", "remove", "list", "clear"));
        } else if (var2 == 2 && var1.length > 0 && "remove".equalsIgnoreCase(var1[0])) {
            var5.addAll(this.set());
}
        return var5;
}
    private Set<String> set() {
        return this.kind == 0 ? Teams.B() : Teams.a();
}
    private Set<String> other() {
        return this.kind == 0 ? Teams.a() : Teams.B();
}
    private String file() {
        return this.kind == 0 ? "enemies.txt" : "friends.txt";
}
    private boolean save() {
        return this.kind == 0 ? AbyssCommandData.saveEnemies() : AbyssCommandData.saveFriends();
}
    private void add(String[] var1) {
        int var2 = 0;
        for (int var3 = 1; var3 < var1.length; ++var3) {
            String var4 = var1[var3].trim();
            if (var4.isEmpty()) continue;
            if (this.other().contains(var4)) {
                AbyssCommands.chat("\u00a7c" + var4 + " is already on the other list; the two are mutually exclusive.");
                continue;
}
            if (this.set().contains(var4)) {
                AbyssCommands.chat("\u00a77" + var4 + " was already there.");
                continue;
}
            if (this.kind == 0) {
                Teams.C(var4);
            } else {
                Teams.E(var4);
}
            if (this.set().contains(var4)) {
                AbyssCommands.chat("\u00a7a+ \u00a7f" + var4);
                ++var2;
                continue;
}
            AbyssCommands.chat("\u00a7c" + var4 + " was refused by the product's own writer.");
}
        this.report(var2);
}
    private void remove(String[] var1) {
        int var2 = 0;
        for (int var3 = 1; var3 < var1.length; ++var3) {
            String var4 = this.match(var1[var3]);
            if (var4 == null) {
                AbyssCommands.chat("\u00a7c" + var1[var3] + " is not on the list.");
                continue;
}
            if (!this.set().remove(var4)) continue;
            AbyssCommands.chat("\u00a7c- \u00a7f" + var4);
            ++var2;
}
        this.report(var2);
}
    private String match(String var1) {
        if (this.set().contains(var1)) {
            return var1;
}
        for (String var3 : this.set()) {
            if (var3 == null || !var3.equalsIgnoreCase(var1)) continue;
            return var3;
}
        return null;
}
    private void list() {
        Set<String> var1 = this.set();
        if (var1.isEmpty()) {
            AbyssCommands.chat("\u00a77The list is empty.");
            return;
}
        StringBuilder var2 = new StringBuilder();
        for (String var4 : var1) {
            if (var2.length() > 0) {
                var2.append("\u00a77, \u00a7f");
}
            var2.append(var4);
}
        AbyssCommands.chat("\u00a77" + var1.size() + ": \u00a7f" + var2);
}
    private void clear() {
        int var1 = this.set().size();
        if (this.kind == 0) {
            Teams.W();
        } else {
            Teams.r$r1();
}
        this.report(var1);
}
    private void report(int var1) {
        if (var1 == 0) {
            AbyssCommands.chat("\u00a77Nothing changed.");
            return;
}
        boolean var2 = this.save();
        AbyssCommands.chat("\u00a77" + var1 + " change(s); " + this.set().size() + " name(s) now" + (var2 ? ", saved to \u00a7f" + this.file() : " \u00a7cbut the write to " + this.file() + " FAILED"));
}
}