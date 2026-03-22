package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.RespawnPacketHandler;
import com.legacyminecraft.poseidon.network.RespawnResultExecutionSystem;
import net.minecraft.server.EntityPlayer;
import org.junit.Assert;
import org.junit.Test;

public class RespawnResultExecutionSystemTest {
    private final RespawnResultExecutionSystem respawnResultExecutionSystem = RespawnResultExecutionSystem.getInstance();

    @Test
    public void executeResultAppliesRespawnedPlayerWhenRespawned() {
        EntityPlayer respawnedPlayer = null;
        RespawnPacketHandler.RespawnResult respawnResult = RespawnPacketHandler.RespawnResult.respawned(respawnedPlayer);
        RespawnActionCapture respawnActionCapture = new RespawnActionCapture();

        respawnResultExecutionSystem.executeResult(respawnResult, respawnActionCapture);

        Assert.assertTrue(respawnActionCapture.called);
        Assert.assertNull(respawnActionCapture.player);
    }

    @Test
    public void executeResultSkipsApplyWhenNoRespawn() {
        EntityPlayer currentPlayer = null;
        RespawnPacketHandler.RespawnResult respawnResult = RespawnPacketHandler.RespawnResult.noRespawn(currentPlayer);
        RespawnActionCapture respawnActionCapture = new RespawnActionCapture();

        respawnResultExecutionSystem.executeResult(respawnResult, respawnActionCapture);

        Assert.assertFalse(respawnActionCapture.called);
    }

    private static final class RespawnActionCapture implements RespawnResultExecutionSystem.RespawnActions {
        private boolean called;
        private EntityPlayer player;

        @Override
        public void applyRespawnedPlayer(EntityPlayer player) {
            this.called = true;
            this.player = player;
        }
    }
}
