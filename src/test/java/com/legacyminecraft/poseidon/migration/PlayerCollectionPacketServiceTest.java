package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.world.player.PlayerCollectionPacketSystem;
import org.junit.Assert;
import org.junit.Test;

public class PlayerCollectionPacketServiceTest {
    @Test
    public void broadcastRuleMatchesLegacyDeadAndTypeChecks() {
        PlayerCollectionPacketSystem service = PlayerCollectionPacketSystem.getInstance();

        Assert.assertTrue(service.shouldBroadcastCollectPacket(false, true, false));
        Assert.assertTrue(service.shouldBroadcastCollectPacket(false, false, true));
        Assert.assertFalse(service.shouldBroadcastCollectPacket(true, true, true));
        Assert.assertFalse(service.shouldBroadcastCollectPacket(false, false, false));
    }
}
