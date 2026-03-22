package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.PlayerMoveEventDispatchSystem;
import com.legacyminecraft.poseidon.network.PlayerMoveEventStateApplySystem;
import org.junit.Assert;
import org.junit.Test;

public class PlayerMoveEventStateApplySystemTest {
    private final PlayerMoveEventStateApplySystem playerMoveEventStateApplySystem = PlayerMoveEventStateApplySystem.getInstance();

    @Test
    public void applyStateCopiesAllMovementStateFields() {
        PlayerMoveEventDispatchSystem.MovementEventState movementEventState =
                new PlayerMoveEventDispatchSystem.MovementEventState(
                        11.0D,
                        22.0D,
                        33.0D,
                        44.0F,
                        55.0F,
                        true
                );
        MovementStateCapture movementStateCapture = new MovementStateCapture();

        playerMoveEventStateApplySystem.applyState(movementEventState, movementStateCapture);

        Assert.assertEquals(11.0D, movementStateCapture.lastPosX, 0.0001D);
        Assert.assertEquals(22.0D, movementStateCapture.lastPosY, 0.0001D);
        Assert.assertEquals(33.0D, movementStateCapture.lastPosZ, 0.0001D);
        Assert.assertEquals(44.0F, movementStateCapture.lastYaw, 0.0001F);
        Assert.assertEquals(55.0F, movementStateCapture.lastPitch, 0.0001F);
        Assert.assertTrue(movementStateCapture.justTeleported);
    }

    private static final class MovementStateCapture implements PlayerMoveEventStateApplySystem.MovementStateSink {
        private double lastPosX;
        private double lastPosY;
        private double lastPosZ;
        private float lastYaw;
        private float lastPitch;
        private boolean justTeleported;

        @Override
        public void apply(double lastPosX, double lastPosY, double lastPosZ, float lastYaw, float lastPitch, boolean justTeleported) {
            this.lastPosX = lastPosX;
            this.lastPosY = lastPosY;
            this.lastPosZ = lastPosZ;
            this.lastYaw = lastYaw;
            this.lastPitch = lastPitch;
            this.justTeleported = justTeleported;
        }
    }
}
