package com.legacyminecraft.poseidon.entity;

import java.util.UUID;

/**
 * Entity-local player facade.
 */
public class Player implements com.legacyminecraft.compat.bukkit.Player {
    private final UUID uniqueId = new UUID(0L, 0L);

    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }
}
