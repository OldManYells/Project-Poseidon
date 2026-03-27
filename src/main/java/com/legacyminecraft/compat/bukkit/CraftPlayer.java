package com.legacyminecraft.compat.bukkit;

import java.util.UUID;

/**
 * Canonical compat CraftPlayer scaffold.
 */
public class CraftPlayer extends CraftLivingEntity implements Player {
    private EntityPlayer handle = new EntityPlayer();
    private final UUID uniqueId = new UUID(0L, 0L);
    private String name = "Player";

    public CraftPlayer() {
    }

    public CraftPlayer(CraftServer server, EntityPlayer handle) {
        this.handle = handle == null ? new EntityPlayer() : handle;
        this.name = this.handle.name;
    }

    @Override
    public EntityPlayer getHandle() {
        return handle;
    }

    public void setHandle(EntityPlayer handle) {
        this.handle = handle == null ? new EntityPlayer() : handle;
        this.name = this.handle.name;
    }

    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }

    public String getName() {
        return name;
    }

    public void kickPlayer(String message) {
    }
}
