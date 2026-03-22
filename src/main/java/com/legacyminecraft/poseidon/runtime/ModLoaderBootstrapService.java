package com.legacyminecraft.poseidon.runtime;

import java.util.logging.Logger;

/**
 * Canonical bootstrap policy for optional ModLoaderMP support.
 */
public final class ModLoaderBootstrapService {
    private static final ModLoaderBootstrapService INSTANCE = new ModLoaderBootstrapService();

    private ModLoaderBootstrapService() {
    }

    public static ModLoaderBootstrapService getInstance() {
        return INSTANCE;
    }

    public boolean initializeIfEnabled(boolean modLoaderEnabled, Logger logger, ModLoaderHooks hooks) {
        if (!modLoaderEnabled) {
            return true;
        }

        logger.info("EXPERIMENTAL MODLOADERMP SUPPORT ENABLED.");
        if (!hooks.isModLoaderPresent()) {
            logger.severe("ModLoaderMP support is enabled, however, it isn't present. Please install it before enabling this setting");
            return false;
        }

        hooks.initializeModLoader();
        return true;
    }

    public boolean isModLoaderClassPresent() {
        try {
            Class.forName("net.minecraft.server.ModLoader");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public interface ModLoaderHooks {
        boolean isModLoaderPresent();

        void initializeModLoader();
    }
}
