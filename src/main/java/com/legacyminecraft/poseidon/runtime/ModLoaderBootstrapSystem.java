package com.legacyminecraft.poseidon.runtime;

import java.util.logging.Logger;

/**
 * Role-aligned canonical system for optional ModLoader bootstrap.
 */
public final class ModLoaderBootstrapSystem {
    private static final ModLoaderBootstrapSystem INSTANCE = new ModLoaderBootstrapSystem();
    private final ModLoaderBootstrapService delegate = ModLoaderBootstrapService.getInstance();

    private ModLoaderBootstrapSystem() {
    }

    public static ModLoaderBootstrapSystem getInstance() {
        return INSTANCE;
    }

    public boolean initializeIfEnabled(boolean modLoaderEnabled, Logger logger, final ModLoaderHooks hooks) {
        return delegate.initializeIfEnabled(modLoaderEnabled, logger, new ModLoaderBootstrapService.ModLoaderHooks() {
            @Override
            public boolean isModLoaderPresent() {
                return hooks.isModLoaderPresent();
            }

            @Override
            public void initializeModLoader() {
                hooks.initializeModLoader();
            }
        });
    }

    public boolean isModLoaderClassPresent() {
        return delegate.isModLoaderClassPresent();
    }

    public interface ModLoaderHooks {
        boolean isModLoaderPresent();

        void initializeModLoader();
    }
}

