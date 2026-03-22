package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.runtime.ServerNetworkStartupApplySystem;
import com.legacyminecraft.poseidon.runtime.ServerNetworkStartupService;
import net.minecraft.server.NetworkListenThread;
import net.minecraft.server.PropertyManager;
import org.junit.Assert;
import org.junit.Test;

public class ServerNetworkStartupApplySystemTest {
    private final ServerNetworkStartupApplySystem serverNetworkStartupApplySystem = ServerNetworkStartupApplySystem.getInstance();

    @Test
    public void applyStartupResultCopiesAllStartupState() {
        PropertyManager propertyManager = null;
        NetworkListenThread networkListenThread = null;
        ServerNetworkStartupService.StartupResult startupResult =
                ServerNetworkStartupService.StartupResult.success(
                        propertyManager,
                        "127.0.0.1",
                        true,
                        false,
                        true,
                        false,
                        networkListenThread
                );
        StartupStateCapture startupStateCapture = new StartupStateCapture();

        serverNetworkStartupApplySystem.applyStartupResult(startupResult, startupStateCapture);

        Assert.assertSame(propertyManager, startupStateCapture.propertyManager);
        Assert.assertTrue(startupStateCapture.onlineMode);
        Assert.assertFalse(startupStateCapture.spawnAnimals);
        Assert.assertTrue(startupStateCapture.pvpMode);
        Assert.assertFalse(startupStateCapture.allowFlight);
        Assert.assertSame(networkListenThread, startupStateCapture.networkListenThread);
    }

    private static final class StartupStateCapture implements ServerNetworkStartupApplySystem.StartupStateSink {
        private PropertyManager propertyManager;
        private boolean onlineMode;
        private boolean spawnAnimals;
        private boolean pvpMode;
        private boolean allowFlight;
        private NetworkListenThread networkListenThread;

        @Override
        public void apply(
                PropertyManager propertyManager,
                boolean onlineMode,
                boolean spawnAnimals,
                boolean pvpMode,
                boolean allowFlight,
                NetworkListenThread networkListenThread
        ) {
            this.propertyManager = propertyManager;
            this.onlineMode = onlineMode;
            this.spawnAnimals = spawnAnimals;
            this.pvpMode = pvpMode;
            this.allowFlight = allowFlight;
            this.networkListenThread = networkListenThread;
        }
    }
}
