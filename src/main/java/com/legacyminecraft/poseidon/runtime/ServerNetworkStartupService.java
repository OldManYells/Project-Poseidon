package com.legacyminecraft.poseidon.runtime;

import joptsimple.OptionSet;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Canonical startup flow for property loading and network listener bootstrap.
 */
public final class ServerNetworkStartupService {
    private static final ServerNetworkStartupService INSTANCE = new ServerNetworkStartupService();

    private ServerNetworkStartupService() {
    }

    public static ServerNetworkStartupService getInstance() {
        return INSTANCE;
    }

    public StartupResult initializeNetwork(MinecraftServer server, OptionSet options, ServerBootstrapPolicyService bootstrapPolicy, Logger logger) throws UnknownHostException {
        logger.info("Loading properties");
        PropertyManager propertyManager = new PropertyManager(options);
        String configuredHost = propertyManager.getString("server-ip", "");

        boolean onlineMode = propertyManager.getBoolean("online-mode", false);
        boolean spawnAnimals = propertyManager.getBoolean("spawn-animals", true);
        boolean pvpMode = propertyManager.getBoolean("pvp", true);
        boolean allowFlight = propertyManager.getBoolean("allow-flight", false);
        InetAddress inetaddress = bootstrapPolicy.resolveBindAddress(configuredHost);
        int port = propertyManager.getInt("server-port", 25565);

        logger.info("Starting Minecraft server on " + bootstrapPolicy.resolveBindHostDisplay(configuredHost) + ":" + port);

        NetworkListenThread networkListenThread;
        try {
            networkListenThread = new NetworkListenThread(server, inetaddress, port);
        } catch (Throwable ioexception) {
            logger.warning("**** FAILED TO BIND TO PORT!");
            logger.log(Level.WARNING, "The exception was: " + ioexception.toString());
            logger.warning("Perhaps a server is already running on that port?");
            return StartupResult.failed();
        }

        if (bootstrapPolicy.shouldLogOfflineModeWarnings(onlineMode)) {
            logger.warning("**** SERVER IS RUNNING IN OFFLINE/INSECURE MODE!");
            logger.warning("The server will make no attempt to authenticate usernames. Beware.");
            logger.warning("While this makes the game possible to play without internet access, it also opens up the ability for hackers to connect with any username they choose.");
            logger.warning("To change this, set \"online-mode\" to \"true\" in the server.settings file.");
        }

        return StartupResult.success(
                propertyManager,
                configuredHost,
                onlineMode,
                spawnAnimals,
                pvpMode,
                allowFlight,
                networkListenThread
        );
    }

    public static final class StartupResult {
        private final boolean successful;
        private final PropertyManager propertyManager;
        private final String configuredHost;
        private final boolean onlineMode;
        private final boolean spawnAnimals;
        private final boolean pvpMode;
        private final boolean allowFlight;
        private final NetworkListenThread networkListenThread;

        private StartupResult(
                boolean successful,
                PropertyManager propertyManager,
                String configuredHost,
                boolean onlineMode,
                boolean spawnAnimals,
                boolean pvpMode,
                boolean allowFlight,
                NetworkListenThread networkListenThread
        ) {
            this.successful = successful;
            this.propertyManager = propertyManager;
            this.configuredHost = configuredHost;
            this.onlineMode = onlineMode;
            this.spawnAnimals = spawnAnimals;
            this.pvpMode = pvpMode;
            this.allowFlight = allowFlight;
            this.networkListenThread = networkListenThread;
        }

        public static StartupResult failed() {
            return new StartupResult(false, null, null, false, false, false, false, null);
        }

        public static StartupResult success(
                PropertyManager propertyManager,
                String configuredHost,
                boolean onlineMode,
                boolean spawnAnimals,
                boolean pvpMode,
                boolean allowFlight,
                NetworkListenThread networkListenThread
        ) {
            return new StartupResult(
                    true,
                    propertyManager,
                    configuredHost,
                    onlineMode,
                    spawnAnimals,
                    pvpMode,
                    allowFlight,
                    networkListenThread
            );
        }

        public boolean isSuccessful() {
            return successful;
        }

        public PropertyManager getPropertyManager() {
            return propertyManager;
        }

        public String getConfiguredHost() {
            return configuredHost;
        }

        public boolean isOnlineMode() {
            return onlineMode;
        }

        public boolean isSpawnAnimals() {
            return spawnAnimals;
        }

        public boolean isPvpMode() {
            return pvpMode;
        }

        public boolean isAllowFlight() {
            return allowFlight;
        }

        public NetworkListenThread getNetworkListenThread() {
            return networkListenThread;
        }
    }
}
