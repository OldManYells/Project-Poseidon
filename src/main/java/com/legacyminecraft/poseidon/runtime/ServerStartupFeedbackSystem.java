package com.legacyminecraft.poseidon.runtime;

import joptsimple.OptionSet;

import java.util.logging.Logger;

/**
 * Role-aligned canonical system for startup feedback and post-bootstrap actions.
 */
public final class ServerStartupFeedbackSystem {
    private static final ServerStartupFeedbackSystem INSTANCE = new ServerStartupFeedbackSystem();
    private final ServerStartupFeedbackService delegate = ServerStartupFeedbackService.getInstance();

    private ServerStartupFeedbackSystem() {
    }

    public static ServerStartupFeedbackSystem getInstance() {
        return INSTANCE;
    }

    public void applyDebugConfigIfRequested(OptionSet options, Logger logger) {
        delegate.applyDebugConfigIfRequested(options, logger);
    }

    public void warnLowMemoryIfNeeded(long maxMemoryBytes, ServerBootstrapPolicy bootstrapPolicy, Logger logger) {
        delegate.warnLowMemoryIfNeeded(maxMemoryBytes, bootstrapPolicy.toService(), logger);
    }

    public void logStartupCompleted(long startupStartNanos, Logger logger) {
        delegate.logStartupCompleted(startupStartNanos, logger);
    }

    public void startDailyLogRotatorIfEnabled() {
        delegate.startDailyLogRotatorIfEnabled();
    }

    public void migrateLegacySpawnProtectionIfNeeded(
            ServerBootstrapPolicy bootstrapPolicy,
            PropertyManager propertyManager,
            Server server,
            Logger logger
    ) {
        delegate.migrateLegacySpawnProtectionIfNeeded(bootstrapPolicy.toService(), propertyManager, server, logger);
    }
}
