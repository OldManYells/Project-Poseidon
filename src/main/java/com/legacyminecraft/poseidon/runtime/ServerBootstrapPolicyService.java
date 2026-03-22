package com.legacyminecraft.poseidon.runtime;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

/**
 * Canonical bootstrap policy helpers for server startup configuration decisions.
 */
public final class ServerBootstrapPolicyService {
    private static final long ONE_MEGABYTE = 1024L * 1024L;
    private static final long LOW_MEMORY_THRESHOLD_MB = 512L;
    private static final String LEGACY_SPAWN_PROTECTION_KEY = "spawn-protection";
    private static final ServerBootstrapPolicyService INSTANCE = new ServerBootstrapPolicyService();

    private ServerBootstrapPolicyService() {
    }

    public static ServerBootstrapPolicyService getInstance() {
        return INSTANCE;
    }

    public boolean shouldWarnLowMemory(long maxMemoryBytes) {
        return maxMemoryBytes / ONE_MEGABYTE < LOW_MEMORY_THRESHOLD_MB;
    }

    public boolean shouldLogOfflineModeWarnings(boolean onlineMode) {
        return !onlineMode;
    }

    public InetAddress resolveBindAddress(String configuredHost) throws UnknownHostException {
        if (configuredHost == null || configuredHost.length() == 0) {
            return null;
        }
        return InetAddress.getByName(configuredHost);
    }

    public String resolveBindHostDisplay(String configuredHost) {
        return configuredHost == null || configuredHost.length() == 0 ? "*" : configuredHost;
    }

    public long resolveLevelSeed(String configuredSeed, long randomFallbackSeed) {
        if (configuredSeed == null || configuredSeed.length() == 0) {
            return randomFallbackSeed;
        }
        try {
            return Long.parseLong(configuredSeed);
        } catch (NumberFormatException numberFormatException) {
            return (long) configuredSeed.hashCode();
        }
    }

    public boolean hasLegacySpawnProtectionProperty(Properties properties) {
        return properties != null && properties.containsKey(LEGACY_SPAWN_PROTECTION_KEY);
    }
}
