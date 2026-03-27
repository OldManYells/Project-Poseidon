package com.legacyminecraft.poseidon.runtime;

import java.util.UUID;

/**
 * Runtime-local player facade.
 */
public class Player implements com.legacyminecraft.compat.bukkit.Player {
    private String name = "player";
    private final UUID uniqueId = new UUID(0L, 0L);

    @Override
    public String getName() {
        return name;
    }

    public boolean hasPermission(String permission) {
        return true;
    }

    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }

    @Override
    public void sendMessage(String message) {
    }
}
