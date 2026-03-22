package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.RespawnPacketHandler;
import org.junit.Assert;
import org.junit.Test;

public class RespawnPacketServiceTest {
    @Test
    public void healthThresholdControlsRespawnDecision() {
        RespawnPacketHandler service = RespawnPacketHandler.getInstance();

        Assert.assertTrue(service.shouldRespawn(0));
        Assert.assertTrue(service.shouldRespawn(-5));
        Assert.assertFalse(service.shouldRespawn(1));
    }

    @Test
    public void resultFactoriesExposeRespawnState() {
        RespawnPacketHandler.RespawnResult noRespawn = RespawnPacketHandler.RespawnResult.noRespawn(null);
        Assert.assertFalse(noRespawn.isRespawned());
        Assert.assertNull(noRespawn.getPlayer());

        RespawnPacketHandler.RespawnResult respawned = RespawnPacketHandler.RespawnResult.respawned(null);
        Assert.assertTrue(respawned.isRespawned());
        Assert.assertNull(respawned.getPlayer());
    }
}
