package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.ClientDisconnectPacketHandler;
import org.junit.Assert;
import org.junit.Test;

public class ClientDisconnectPacketServiceTest {
    @Test
    public void disconnectReasonKeyMatchesLegacyValue() {
        ClientDisconnectPacketHandler service = ClientDisconnectPacketHandler.getInstance();

        Assert.assertEquals("disconnect.quitting", service.getDisconnectReasonKey());
    }
}
