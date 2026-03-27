package com.legacyminecraft.poseidon.auth.login;

/**
 * Auth-login local EntityPlayer alias.
 */
public class EntityPlayer extends com.legacyminecraft.poseidon.network.EntityPlayer {
    public EntityPlayer() {
        super();
    }

    public EntityPlayer(
            MinecraftServer server,
            com.legacyminecraft.compat.bukkit.WorldServer worldServer,
            String username,
            ItemInWorldManager itemInWorldManager
    ) {
        this.world = worldServer;
        this.name = username;
        this.dimension = worldServer.dimension;
    }
}
