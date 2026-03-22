package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.WorldProvider;
import net.minecraft.server.WorldServer;
import org.bukkit.World;

/**
 * Canonical behavior for CraftWorld environment update orchestration.
 */
public final class CraftWorldEnvironmentUpdateBehaviour {
    private static final CraftWorldEnvironmentUpdateBehaviour INSTANCE = new CraftWorldEnvironmentUpdateBehaviour();

    private CraftWorldEnvironmentUpdateBehaviour() {
    }

    public static CraftWorldEnvironmentUpdateBehaviour getInstance() {
        return INSTANCE;
    }

    public World.Environment setEnvironment(WorldServer worldServer, World.Environment currentEnvironment, World.Environment requestedEnvironment) {
        if (currentEnvironment != requestedEnvironment) {
            worldServer.worldProvider = WorldProvider.byDimension(requestedEnvironment.getId());
            return requestedEnvironment;
        }

        return currentEnvironment;
    }
}
