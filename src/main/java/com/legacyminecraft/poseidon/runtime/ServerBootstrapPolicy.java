package com.legacyminecraft.poseidon.runtime;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

/**
 * Role-aligned canonical policy facade for server bootstrap configuration decisions.
 */
public final class ServerBootstrapPolicy {
    private static final ServerBootstrapPolicy INSTANCE = new ServerBootstrapPolicy();
    private final ServerBootstrapPolicyService delegate = ServerBootstrapPolicyService.getInstance();

    private ServerBootstrapPolicy() {
    }

    public static ServerBootstrapPolicy getInstance() {
        return INSTANCE;
    }

    public boolean shouldWarnLowMemory(long maxMemoryBytes) {
        return delegate.shouldWarnLowMemory(maxMemoryBytes);
    }

    public boolean shouldLogOfflineModeWarnings(boolean onlineMode) {
        return delegate.shouldLogOfflineModeWarnings(onlineMode);
    }

    public InetAddress resolveBindAddress(String configuredHost) throws UnknownHostException {
        return delegate.resolveBindAddress(configuredHost);
    }

    public String resolveBindHostDisplay(String configuredHost) {
        return delegate.resolveBindHostDisplay(configuredHost);
    }

    public long resolveLevelSeed(String configuredSeed, long randomFallbackSeed) {
        return delegate.resolveLevelSeed(configuredSeed, randomFallbackSeed);
    }

    public boolean hasLegacySpawnProtectionProperty(Properties properties) {
        return delegate.hasLegacySpawnProtectionProperty(properties);
    }

    ServerBootstrapPolicyService toService() {
        return delegate;
    }
}
