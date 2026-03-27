package com.legacyminecraft.poseidon.compat.nms;

import com.legacyminecraft.compat.bukkit.CraftServer;
import com.legacyminecraft.compat.bukkit.ServerConfigurationManager;

/**
 * Transitional entrypoints for NMS wrappers during migration.
 */
public final class NmsCompat {
    private NmsCompat() {
    }

    public static ServerConfigurationManager getHandle(CraftServer craftServer) {
        return craftServer.getHandle();
    }
}
