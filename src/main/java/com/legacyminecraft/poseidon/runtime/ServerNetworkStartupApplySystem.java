package com.legacyminecraft.poseidon.runtime;

import net.minecraft.server.NetworkListenThread;
import net.minecraft.server.PropertyManager;

/**
 * Canonical application flow for network startup result state.
 */
public final class ServerNetworkStartupApplySystem {
    private static final ServerNetworkStartupApplySystem INSTANCE = new ServerNetworkStartupApplySystem();

    private ServerNetworkStartupApplySystem() {
    }

    public static ServerNetworkStartupApplySystem getInstance() {
        return INSTANCE;
    }

    public void applyStartupResult(
            ServerNetworkStartupService.StartupResult startupResult,
            StartupStateSink startupStateSink
    ) {
        startupStateSink.apply(
                startupResult.getPropertyManager(),
                startupResult.isOnlineMode(),
                startupResult.isSpawnAnimals(),
                startupResult.isPvpMode(),
                startupResult.isAllowFlight(),
                startupResult.getNetworkListenThread()
        );
    }

    public void applyStartupResult(
            ServerNetworkStartupSystem.StartupResult startupResult,
            StartupStateSink startupStateSink
    ) {
        applyStartupResult(startupResult.toServiceResult(), startupStateSink);
    }

    public interface StartupStateSink {
        void apply(
                PropertyManager propertyManager,
                boolean onlineMode,
                boolean spawnAnimals,
                boolean pvpMode,
                boolean allowFlight,
                NetworkListenThread networkListenThread
        );
    }
}
