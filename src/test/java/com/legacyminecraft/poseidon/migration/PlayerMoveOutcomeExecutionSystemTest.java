package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.PlayerMoveEventOutcomeSystem;
import com.legacyminecraft.poseidon.network.PlayerMoveOutcomeExecutionSystem;
import net.minecraft.server.Packet13PlayerLookMove;
import org.bukkit.Location;
import org.junit.Assert;
import org.junit.Test;

public class PlayerMoveOutcomeExecutionSystemTest {
    private final PlayerMoveOutcomeExecutionSystem playerMoveOutcomeExecutionSystem = PlayerMoveOutcomeExecutionSystem.getInstance();

    @Test
    public void executeOutcomeRollbackSendsPacketAndReturnsTrue() {
        PlayerMoveEventOutcomeSystem.MoveOutcomeDecision moveOutcomeDecision =
                PlayerMoveEventOutcomeSystem.MoveOutcomeDecision.rollbackAndReturn(
                        new Packet13PlayerLookMove(1.0D, 2.0D, 3.0D, 4.0D, 0.0F, 0.0F, false)
                );
        MoveOutcomeCapture capture = new MoveOutcomeCapture();

        boolean shouldReturn = playerMoveOutcomeExecutionSystem.executeOutcome(moveOutcomeDecision, capture);

        Assert.assertTrue(shouldReturn);
        Assert.assertNotNull(capture.rollbackPacket);
        Assert.assertNull(capture.teleportLocation);
    }

    @Test
    public void executeOutcomeTeleportCallsTeleportAndReturnsTrue() {
        Location location = new Location(null, 10.0D, 64.0D, -5.0D, 90.0F, 0.0F);
        PlayerMoveEventOutcomeSystem.MoveOutcomeDecision moveOutcomeDecision =
                PlayerMoveEventOutcomeSystem.MoveOutcomeDecision.teleportAndReturn(location);
        MoveOutcomeCapture capture = new MoveOutcomeCapture();

        boolean shouldReturn = playerMoveOutcomeExecutionSystem.executeOutcome(moveOutcomeDecision, capture);

        Assert.assertTrue(shouldReturn);
        Assert.assertNull(capture.rollbackPacket);
        Assert.assertSame(location, capture.teleportLocation);
    }

    @Test
    public void executeOutcomeContinueDoesNotInvokeActionsAndReturnsFalse() {
        PlayerMoveEventOutcomeSystem.MoveOutcomeDecision moveOutcomeDecision =
                PlayerMoveEventOutcomeSystem.MoveOutcomeDecision.continueProcessing();
        MoveOutcomeCapture capture = new MoveOutcomeCapture();

        boolean shouldReturn = playerMoveOutcomeExecutionSystem.executeOutcome(moveOutcomeDecision, capture);

        Assert.assertFalse(shouldReturn);
        Assert.assertNull(capture.rollbackPacket);
        Assert.assertNull(capture.teleportLocation);
    }

    private static final class MoveOutcomeCapture implements PlayerMoveOutcomeExecutionSystem.MoveOutcomeActions {
        private Packet13PlayerLookMove rollbackPacket;
        private Location teleportLocation;

        @Override
        public void sendRollbackPacket(Packet13PlayerLookMove rollbackPacket) {
            this.rollbackPacket = rollbackPacket;
        }

        @Override
        public void teleportPlayer(Location location) {
            this.teleportLocation = location;
        }
    }
}
