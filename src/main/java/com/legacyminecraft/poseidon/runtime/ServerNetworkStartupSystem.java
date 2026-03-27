package com.legacyminecraft.poseidon.runtime;

import joptsimple.OptionSet;

import java.net.UnknownHostException;
import java.util.logging.Logger;

/**
 * Role-aligned canonical system for property loading and network listener bootstrap.
 */
public final class ServerNetworkStartupSystem {
    private static final ServerNetworkStartupSystem INSTANCE = new ServerNetworkStartupSystem();
    private final ServerNetworkStartupService delegate = ServerNetworkStartupService.getInstance();

    private ServerNetworkStartupSystem() {
    }

    public static ServerNetworkStartupSystem getInstance() {
        return INSTANCE;
    }

    public StartupResult initializeNetwork(
            MinecraftServer server,
            OptionSet options,
            ServerBootstrapPolicy bootstrapPolicy,
            Logger logger
    ) throws UnknownHostException {
        return StartupResult.wrap(delegate.initializeNetwork(server, options, bootstrapPolicy.toService(), logger));
    }

    public static final class StartupResult {
        private final ServerNetworkStartupService.StartupResult delegateResult;

        private StartupResult(ServerNetworkStartupService.StartupResult delegateResult) {
            this.delegateResult = delegateResult;
        }

        static StartupResult wrap(ServerNetworkStartupService.StartupResult delegateResult) {
            return new StartupResult(delegateResult);
        }

        ServerNetworkStartupService.StartupResult toServiceResult() {
            return delegateResult;
        }

        public boolean isSuccessful() {
            return delegateResult.isSuccessful();
        }

        public PropertyManager getPropertyManager() {
            return delegateResult.getPropertyManager();
        }

        public String getConfiguredHost() {
            return delegateResult.getConfiguredHost();
        }

        public boolean isOnlineMode() {
            return delegateResult.isOnlineMode();
        }

        public boolean isSpawnAnimals() {
            return delegateResult.isSpawnAnimals();
        }

        public boolean isPvpMode() {
            return delegateResult.isPvpMode();
        }

        public boolean isAllowFlight() {
            return delegateResult.isAllowFlight();
        }

        public NetworkListenThread getNetworkListenThread() {
            return delegateResult.getNetworkListenThread();
        }
    }
}

