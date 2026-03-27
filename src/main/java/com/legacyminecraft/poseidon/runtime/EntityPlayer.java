package com.legacyminecraft.poseidon.runtime;

/**
 * Runtime-local player entity facade.
 */
public class EntityPlayer extends com.legacyminecraft.compat.bukkit.EntityPlayer {
    public final NetServerHandler netServerHandler = new NetServerHandler();

    public boolean hasPermission(String permission) {
        return true;
    }

    @Override
    public long getPlayerTime() {
        return timeOffset;
    }
}
