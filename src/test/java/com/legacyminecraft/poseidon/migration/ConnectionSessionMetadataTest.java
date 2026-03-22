package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.api.network.ConnectionType;
import com.legacyminecraft.poseidon.network.ConnectionSessionMetadata;
import org.junit.Assert;
import org.junit.Test;

public class ConnectionSessionMetadataTest {
    @Test
    public void storesConnectionSessionState() {
        ConnectionSessionMetadata metadata = new ConnectionSessionMetadata();

        metadata.setUsingReleaseToBeta(true);
        metadata.setConnectionType(ConnectionType.BUNGEECORD_OFFLINE_MODE_IP_FORWARDING);
        metadata.setRawConnectionType(2);
        metadata.setReceivedKeepAlive(true);

        Assert.assertTrue(metadata.isUsingReleaseToBeta());
        Assert.assertEquals(ConnectionType.BUNGEECORD_OFFLINE_MODE_IP_FORWARDING, metadata.getConnectionType());
        Assert.assertEquals(2, metadata.getRawConnectionType());
        Assert.assertTrue(metadata.isReceivedKeepAlive());
    }
}
