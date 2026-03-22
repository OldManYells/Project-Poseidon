package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.PlayerMoveEventDispatchSystem;
import com.legacyminecraft.poseidon.network.PlayerMoveEventOutcomeSystem;
import net.minecraft.server.Packet13PlayerLookMove;
import org.bukkit.Location;
import org.junit.Assert;
import org.junit.Test;

public class PlayerMoveEventOutcomeServiceTest {
    @Test
    public void continueOutcomeKeepsProcessing() {
        PlayerMoveEventOutcomeSystem service = PlayerMoveEventOutcomeSystem.getInstance();
        PlayerMoveEventDispatchSystem.MoveEventResult moveEventResult =
                PlayerMoveEventDispatchSystem.MoveEventResult.continueProcessing();

        PlayerMoveEventOutcomeSystem.MoveOutcomeDecision decision = service.resolve(moveEventResult);

        Assert.assertFalse(decision.shouldReturn());
        Assert.assertFalse(decision.shouldSendRollbackPacket());
        Assert.assertFalse(decision.shouldTeleportPlayer());
    }

    @Test
    public void rollbackOutcomeBuildsLegacyRollbackPacket() {
        PlayerMoveEventOutcomeSystem service = PlayerMoveEventOutcomeSystem.getInstance();
        Location rollback = new Location(null, 10.0D, 20.0D, 30.0D, 40.0F, 50.0F);
        PlayerMoveEventDispatchSystem.MoveEventResult moveEventResult =
                PlayerMoveEventDispatchSystem.MoveEventResult.cancelAndRollback(rollback);

        PlayerMoveEventOutcomeSystem.MoveOutcomeDecision decision = service.resolve(moveEventResult);

        Assert.assertTrue(decision.shouldReturn());
        Assert.assertTrue(decision.shouldSendRollbackPacket());
        Assert.assertFalse(decision.shouldTeleportPlayer());

        Packet13PlayerLookMove rollbackPacket = decision.getRollbackPacket();
        Assert.assertEquals(rollback.getX(), rollbackPacket.x, 0.0D);
        Assert.assertEquals(rollback.getY() + 1.6200000047683716D, rollbackPacket.y, 0.0D);
        Assert.assertEquals(rollback.getY(), rollbackPacket.stance, 0.0D);
        Assert.assertEquals(rollback.getZ(), rollbackPacket.z, 0.0D);
        Assert.assertEquals(rollback.getYaw(), rollbackPacket.yaw, 0.0F);
        Assert.assertEquals(rollback.getPitch(), rollbackPacket.pitch, 0.0F);
        Assert.assertFalse(rollbackPacket.g);
    }

    @Test
    public void teleportAndAbortOutcomesReturnWithoutRollback() {
        PlayerMoveEventOutcomeSystem service = PlayerMoveEventOutcomeSystem.getInstance();

        Location destination = new Location(null, 7.0D, 8.0D, 9.0D);
        PlayerMoveEventDispatchSystem.MoveEventResult teleportResult =
                PlayerMoveEventDispatchSystem.MoveEventResult.teleportToEventDestination(destination);
        PlayerMoveEventOutcomeSystem.MoveOutcomeDecision teleportDecision = service.resolve(teleportResult);
        Assert.assertTrue(teleportDecision.shouldReturn());
        Assert.assertFalse(teleportDecision.shouldSendRollbackPacket());
        Assert.assertTrue(teleportDecision.shouldTeleportPlayer());
        Assert.assertEquals(destination, teleportDecision.getTeleportDestination());

        PlayerMoveEventDispatchSystem.MoveEventResult abortResult =
                PlayerMoveEventDispatchSystem.MoveEventResult.abortAfterPluginTeleport();
        PlayerMoveEventOutcomeSystem.MoveOutcomeDecision abortDecision = service.resolve(abortResult);
        Assert.assertTrue(abortDecision.shouldReturn());
        Assert.assertFalse(abortDecision.shouldSendRollbackPacket());
        Assert.assertFalse(abortDecision.shouldTeleportPlayer());
    }
}
