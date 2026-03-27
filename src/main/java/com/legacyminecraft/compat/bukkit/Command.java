package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat command scaffold.
 */
public class Command {
    private final String name;

    public Command(String name) {
        this.name = name == null ? "" : name;
    }

    public String getName() {
        return name;
    }
}

