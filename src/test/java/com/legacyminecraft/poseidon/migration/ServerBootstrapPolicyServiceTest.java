package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.runtime.ServerBootstrapPolicyService;
import org.junit.Assert;
import org.junit.Test;

import java.net.InetAddress;
import java.util.Properties;

public class ServerBootstrapPolicyServiceTest {
    @Test
    public void lowMemoryWarningThresholdMatchesLegacyRule() {
        ServerBootstrapPolicyService service = ServerBootstrapPolicyService.getInstance();

        Assert.assertTrue(service.shouldWarnLowMemory(511L * 1024L * 1024L));
        Assert.assertFalse(service.shouldWarnLowMemory(512L * 1024L * 1024L));
    }

    @Test
    public void offlineWarningRuleMirrorsOnlineModeFlag() {
        ServerBootstrapPolicyService service = ServerBootstrapPolicyService.getInstance();

        Assert.assertTrue(service.shouldLogOfflineModeWarnings(false));
        Assert.assertFalse(service.shouldLogOfflineModeWarnings(true));
    }

    @Test
    public void bindAddressResolutionHandlesEmptyAndConfiguredHosts() throws Exception {
        ServerBootstrapPolicyService service = ServerBootstrapPolicyService.getInstance();

        Assert.assertNull(service.resolveBindAddress(""));
        Assert.assertNull(service.resolveBindAddress(null));

        InetAddress loopback = service.resolveBindAddress("127.0.0.1");
        Assert.assertNotNull(loopback);
    }

    @Test
    public void bindHostDisplayFallsBackToWildcard() {
        ServerBootstrapPolicyService service = ServerBootstrapPolicyService.getInstance();

        Assert.assertEquals("*", service.resolveBindHostDisplay(""));
        Assert.assertEquals("*", service.resolveBindHostDisplay(null));
        Assert.assertEquals("0.0.0.0", service.resolveBindHostDisplay("0.0.0.0"));
    }

    @Test
    public void levelSeedResolutionSupportsEmptyNumericAndTextSeeds() {
        ServerBootstrapPolicyService service = ServerBootstrapPolicyService.getInstance();

        Assert.assertEquals(1234L, service.resolveLevelSeed("", 1234L));
        Assert.assertEquals(987654321L, service.resolveLevelSeed("987654321", 1234L));
        Assert.assertEquals((long) "abc".hashCode(), service.resolveLevelSeed("abc", 1234L));
    }

    @Test
    public void legacySpawnProtectionDetectionMatchesPropertyPresence() {
        ServerBootstrapPolicyService service = ServerBootstrapPolicyService.getInstance();
        Properties properties = new Properties();

        Assert.assertFalse(service.hasLegacySpawnProtectionProperty(properties));
        properties.put("spawn-protection", "16");
        Assert.assertTrue(service.hasLegacySpawnProtectionProperty(properties));
    }
}
