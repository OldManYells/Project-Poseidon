package com.legacyminecraft.poseidon.compat.nms;

import net.minecraft.server.ServerConfigurationManager;

/**
 * Transitional entrypoints for NMS wrappers during migration.
 */
public final class NmsCompat {
    private NmsCompat() {
    }

    public static ServerConfigurationManager getHandle(org.bukkit.craftbukkit.CraftServer craftServer) {
        return craftServer.getHandle();
    }
}
