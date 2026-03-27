package com.legacyminecraft.poseidon.runtime;

import com.legacyminecraft.poseidon.PoseidonConfig;
import com.legacyminecraft.poseidon.util.ServerLogRotator;
import joptsimple.OptionSet;

import java.util.logging.Logger;

/**
 * Canonical startup feedback and post-bootstrap compatibility actions.
 */
public final class ServerStartupFeedbackService {
    private static final ServerStartupFeedbackService INSTANCE = new ServerStartupFeedbackService();

    private ServerStartupFeedbackService() {
    }

    public static ServerStartupFeedbackService getInstance() {
        return INSTANCE;
    }

    public void applyDebugConfigIfRequested(OptionSet options, Logger logger) {
        if (options.has("debug-config")) {
            logger.info("[Poseidon] Configuration debug mode has been enabled. This will cause the poseidon.yml to be reloaded every time the server starts.");
            PoseidonConfig.getInstance().resetConfig();
        }
    }

    public void warnLowMemoryIfNeeded(long maxMemoryBytes, ServerBootstrapPolicyService bootstrapPolicyService, Logger logger) {
        if (bootstrapPolicyService.shouldWarnLowMemory(maxMemoryBytes)) {
            logger.warning("**** NOT ENOUGH RAM!");
            logger.warning("To start the server with more ram, launch it as \"java -Xmx1024M -Xms1024M -jar minecraft_server.jar\"");
        }
    }

    public void logStartupCompleted(long startupStartNanos, Logger logger) {
        long elapsed = System.nanoTime() - startupStartNanos;
        String time = String.format("%.3fs", elapsed / 10000000000.0D);
        logger.info("Done (" + time + ")! For help, type \"help\" or \"?\"");
    }

    public void startDailyLogRotatorIfEnabled() {
        if ((boolean) PoseidonConfig.getInstance().getConfigOption("settings.per-day-log-file.enabled")
                && (boolean) PoseidonConfig.getInstance().getConfigOption("settings.per-day-log-file.latest-log.enabled")) {
            ServerLogRotator serverLogRotator = new ServerLogRotator("latest");
            serverLogRotator.start();
        }
    }

    public void migrateLegacySpawnProtectionIfNeeded(
            ServerBootstrapPolicyService bootstrapPolicyService,
            PropertyManager propertyManager,
            Server server,
            Logger logger
    ) {
        if (!bootstrapPolicyService.hasLegacySpawnProtectionProperty(propertyManager.properties)) {
            return;
        }

        logger.info("'spawn-protection' in server.properties has been moved to 'settings.spawn-radius' in bukkit.yml. I will move your config for you.");
        server.setSpawnRadius(propertyManager.getInt("spawn-protection", 16));
        propertyManager.properties.remove("spawn-protection");
        propertyManager.savePropertiesFile();
    }
}
