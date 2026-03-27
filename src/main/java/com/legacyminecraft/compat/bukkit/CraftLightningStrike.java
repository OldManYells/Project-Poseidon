package com.legacyminecraft.compat.bukkit;

import java.util.UUID;

/**
 * CraftBukkit lightning wrapper scaffold.
 */
public class CraftLightningStrike implements LightningStrike {
    private final UUID uniqueId = new UUID(0L, 0L);

    public CraftLightningStrike(CraftServer server, EntityWeatherStorm lightning) {
    }

    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }
}
