package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.RespawnPacketExecutionSystem;
import com.legacyminecraft.poseidon.network.RespawnPacketHandler;
import com.legacyminecraft.poseidon.network.RespawnResultExecutionSystem;
import net.minecraft.server.EntityPlayer;
import org.junit.Assert;
import org.junit.Test;

public class RespawnPacketExecutionSystemTest {
    private final RespawnPacketExecutionSystem respawnPacketExecutionSystem = RespawnPacketExecutionSystem.getInstance();
    private final RespawnResultExecutionSystem respawnResultExecutionSystem = RespawnResultExecutionSystem.getInstance();

    @Test
    public void executeDoesNotApplyPlayerWhenRespawnNotRequired() {
        ResolverCapture resolverCapture = new ResolverCapture(RespawnPacketHandler.RespawnResult.noRespawn(null));
        RespawnActionsCapture respawnActionsCapture = new RespawnActionsCapture();

        respawnPacketExecutionSystem.execute(resolverCapture, respawnResultExecutionSystem, respawnActionsCapture);

        Assert.assertEquals(1, resolverCapture.resolveCalls);
        Assert.assertFalse(respawnActionsCapture.applied);
    }

    @Test
    public void executeAppliesPlayerWhenRespawned() {
        ResolverCapture resolverCapture = new ResolverCapture(RespawnPacketHandler.RespawnResult.respawned(null));
        RespawnActionsCapture respawnActionsCapture = new RespawnActionsCapture();

        respawnPacketExecutionSystem.execute(resolverCapture, respawnResultExecutionSystem, respawnActionsCapture);

        Assert.assertEquals(1, resolverCapture.resolveCalls);
        Assert.assertTrue(respawnActionsCapture.applied);
        Assert.assertNull(respawnActionsCapture.player);
    }

    private static final class ResolverCapture implements RespawnPacketExecutionSystem.RespawnResultResolver {
        private final RespawnPacketHandler.RespawnResult result;
        private int resolveCalls;

        private ResolverCapture(RespawnPacketHandler.RespawnResult result) {
            this.result = result;
        }

        @Override
        public RespawnPacketHandler.RespawnResult resolve() {
            this.resolveCalls++;
            return this.result;
        }
    }

    private static final class RespawnActionsCapture implements RespawnResultExecutionSystem.RespawnActions {
        private boolean applied;
        private EntityPlayer player;

        @Override
        public void applyRespawnedPlayer(EntityPlayer player) {
            this.applied = true;
            this.player = player;
        }
    }
}
