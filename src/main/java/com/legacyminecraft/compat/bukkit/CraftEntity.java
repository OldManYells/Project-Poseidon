package com.legacyminecraft.compat.bukkit;

import java.util.UUID;

/**
 * Canonical compat CraftEntity scaffold.
 */
public class CraftEntity extends Entity {
    private final Entity handle;
    private final UUID uniqueId = new UUID(0L, 0L);

    public CraftEntity() {
        this(new Entity());
    }

    public CraftEntity(Entity handle) {
        this.handle = handle == null ? new Entity() : handle;
    }

    @Override
    public Entity getHandle() {
        return handle;
    }

    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }

    public static Entity getEntity(CraftServer server, Entity entity) {
        return entity == null ? new Entity() : entity;
    }
}
