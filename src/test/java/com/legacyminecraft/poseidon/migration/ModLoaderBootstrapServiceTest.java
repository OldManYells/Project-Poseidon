package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.runtime.ModLoaderBootstrapService;
import org.junit.Assert;
import org.junit.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ModLoaderBootstrapServiceTest {
    private static final Logger LOGGER = Logger.getLogger("ModLoaderBootstrapServiceTest");

    static {
        LOGGER.setUseParentHandlers(false);
        LOGGER.setLevel(Level.OFF);
    }

    @Test
    public void skipsInitializationWhenFeatureDisabled() {
        final boolean[] initialized = new boolean[]{false};

        boolean result = ModLoaderBootstrapService.getInstance().initializeIfEnabled(
                false,
                LOGGER,
                new ModLoaderBootstrapService.ModLoaderHooks() {
                    @Override
                    public boolean isModLoaderPresent() {
                        return true;
                    }

                    @Override
                    public void initializeModLoader() {
                        initialized[0] = true;
                    }
                }
        );

        Assert.assertTrue(result);
        Assert.assertFalse(initialized[0]);
    }

    @Test
    public void failsWhenModLoaderIsMissing() {
        final boolean[] initialized = new boolean[]{false};

        boolean result = ModLoaderBootstrapService.getInstance().initializeIfEnabled(
                true,
                LOGGER,
                new ModLoaderBootstrapService.ModLoaderHooks() {
                    @Override
                    public boolean isModLoaderPresent() {
                        return false;
                    }

                    @Override
                    public void initializeModLoader() {
                        initialized[0] = true;
                    }
                }
        );

        Assert.assertFalse(result);
        Assert.assertFalse(initialized[0]);
    }

    @Test
    public void initializesWhenEnabledAndPresent() {
        final boolean[] initialized = new boolean[]{false};

        boolean result = ModLoaderBootstrapService.getInstance().initializeIfEnabled(
                true,
                LOGGER,
                new ModLoaderBootstrapService.ModLoaderHooks() {
                    @Override
                    public boolean isModLoaderPresent() {
                        return true;
                    }

                    @Override
                    public void initializeModLoader() {
                        initialized[0] = true;
                    }
                }
        );

        Assert.assertTrue(result);
        Assert.assertTrue(initialized[0]);
    }
}
