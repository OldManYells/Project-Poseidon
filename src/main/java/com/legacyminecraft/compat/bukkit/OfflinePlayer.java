package com.legacyminecraft.compat.bukkit;

import java.util.UUID;

/**
 * Canonical compat offline-player scaffold.
 */
public class OfflinePlayer implements AnimalTamer {
    private final String name;
    private boolean operator;

    public OfflinePlayer(String name) {
        this.name = name == null ? "" : name;
    }

    @Override
    public UUID getUniqueId() {
        return new UUID(0L, name.hashCode());
    }

    @Override
    public String getName() {
        return name;
    }

    public boolean isOp() {
        return operator;
    }

    public void setOp(boolean operator) {
        this.operator = operator;
    }
}

