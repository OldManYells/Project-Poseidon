package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.world.player.RespawnPacketPairSystem;
import org.junit.Assert;
import org.junit.Test;

public class RespawnPacketPairServiceTest {
    @Test
    public void handshakeDimensionMappingMatchesLegacyRule() {
        RespawnPacketPairSystem service = RespawnPacketPairSystem.getInstance();

        Assert.assertEquals((byte) -1, service.resolveHandshakeDimension((byte) 0));
        Assert.assertEquals((byte) -1, service.resolveHandshakeDimension((byte) 1));
        Assert.assertEquals((byte) 0, service.resolveHandshakeDimension((byte) -1));
    }
}
