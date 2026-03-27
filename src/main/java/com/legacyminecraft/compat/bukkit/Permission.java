package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat permission scaffold.
 */
public class Permission {
    public final String name;

    public Permission(String name) {
        this.name = name;
    }

    public static Permission loadPermission(String name, Object node) {
        return new Permission(name);
    }
}
