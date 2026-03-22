package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.api.network.ConnectionType;
import com.legacyminecraft.poseidon.network.ConnectionAddressParser;
import com.legacyminecraft.poseidon.network.LoginProxySupport;
import org.junit.Assert;
import org.junit.Test;

public class NetworkMigrationFacadeTest {
    @Test
    public void extractsHostFromLegacySocketAddress() {
        Assert.assertEquals("203.0.113.5", ConnectionAddressParser.extractHost("/203.0.113.5:25565"));
    }

    @Test
    public void returnsRawAddressWhenFormatIsUnexpected() {
        Assert.assertEquals("example.invalid", ConnectionAddressParser.extractHost("example.invalid"));
    }

    @Test
    public void connectionTypeMappingIsStable() {
        Assert.assertEquals(ConnectionType.RELEASE2BETA_OFFLINE_MODE_IP_FORWARDING, LoginProxySupport.resolveConnectionType((byte) -999));
        Assert.assertEquals(ConnectionType.RELEASE2BETA_OFFLINE_MODE_IP_FORWARDING, LoginProxySupport.resolveConnectionType((byte) 25));
        Assert.assertEquals(ConnectionType.RELEASE2BETA_ONLINE_MODE_IP_FORWARDING, LoginProxySupport.resolveConnectionType((byte) 26));
        Assert.assertEquals(ConnectionType.RELEASE2BETA, LoginProxySupport.resolveConnectionType((byte) 1));
        Assert.assertEquals(ConnectionType.BUNGEECORD_OFFLINE_MODE_IP_FORWARDING, LoginProxySupport.resolveConnectionType((byte) 2));
        Assert.assertEquals(ConnectionType.NORMAL, LoginProxySupport.resolveConnectionType((byte) 0));
    }
}
