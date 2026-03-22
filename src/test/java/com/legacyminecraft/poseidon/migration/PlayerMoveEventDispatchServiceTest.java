package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.PlayerMoveEventDispatchSystem;
import org.bukkit.Location;
import org.junit.Assert;
import org.junit.Test;

public class PlayerMoveEventDispatchServiceTest {
    @Test
    public void moveEventResultFactoriesExposeExpectedActions() {
        PlayerMoveEventDispatchSystem.MoveEventResult continueResult =
                PlayerMoveEventDispatchSystem.MoveEventResult.continueProcessing();
        Assert.assertEquals(PlayerMoveEventDispatchSystem.MoveEventResult.Action.CONTINUE, continueResult.getAction());

        Location rollback = new Location(null, 1.0D, 2.0D, 3.0D, 4.0F, 5.0F);
        PlayerMoveEventDispatchSystem.MoveEventResult cancelResult =
                PlayerMoveEventDispatchSystem.MoveEventResult.cancelAndRollback(rollback);
        Assert.assertEquals(PlayerMoveEventDispatchSystem.MoveEventResult.Action.CANCEL_AND_ROLLBACK, cancelResult.getAction());
        Assert.assertEquals(rollback, cancelResult.getRollbackLocation());

        Location destination = new Location(null, 9.0D, 8.0D, 7.0D);
        PlayerMoveEventDispatchSystem.MoveEventResult teleportResult =
                PlayerMoveEventDispatchSystem.MoveEventResult.teleportToEventDestination(destination);
        Assert.assertEquals(PlayerMoveEventDispatchSystem.MoveEventResult.Action.TELEPORT_TO_EVENT_DESTINATION, teleportResult.getAction());
        Assert.assertEquals(destination, teleportResult.getTeleportDestination());

        PlayerMoveEventDispatchSystem.MoveEventResult abortResult =
                PlayerMoveEventDispatchSystem.MoveEventResult.abortAfterPluginTeleport();
        Assert.assertEquals(PlayerMoveEventDispatchSystem.MoveEventResult.Action.ABORT_AFTER_PLUGIN_TELEPORT, abortResult.getAction());
    }

    @Test
    public void movementStateMutatorsUpdateValues() {
        PlayerMoveEventDispatchSystem.MovementEventState state =
                new PlayerMoveEventDispatchSystem.MovementEventState(1.0D, 2.0D, 3.0D, 4.0F, 5.0F, true);

        state.setLastPosX(10.0D);
        state.setLastPosY(20.0D);
        state.setLastPosZ(30.0D);
        state.setLastYaw(40.0F);
        state.setLastPitch(50.0F);
        state.setJustTeleported(false);

        Assert.assertEquals(10.0D, state.getLastPosX(), 0.0D);
        Assert.assertEquals(20.0D, state.getLastPosY(), 0.0D);
        Assert.assertEquals(30.0D, state.getLastPosZ(), 0.0D);
        Assert.assertEquals(40.0F, state.getLastYaw(), 0.0F);
        Assert.assertEquals(50.0F, state.getLastPitch(), 0.0F);
        Assert.assertFalse(state.isJustTeleported());
    }
}
