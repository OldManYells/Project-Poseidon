package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.PlayerTeleportExecutionSystem;
import com.legacyminecraft.poseidon.network.PlayerTeleportPlanApplySystem;
import net.minecraft.server.Packet13PlayerLookMove;
import org.bukkit.Location;
import org.junit.Assert;
import org.junit.Test;

public class PlayerTeleportPlanApplySystemTest {
    private final PlayerTeleportExecutionSystem playerTeleportExecutionSystem = PlayerTeleportExecutionSystem.getInstance();
    private final PlayerTeleportPlanApplySystem playerTeleportPlanApplySystem = PlayerTeleportPlanApplySystem.getInstance();

    @Test
    public void applyPlanForwardsMovementStateLocationAndPacket() {
        PlayerTeleportExecutionSystem.TeleportExecutionPlan teleportExecutionPlan =
                playerTeleportExecutionSystem.createExecutionPlan(new Location(null, 12.5D, 64.0D, -3.25D, 45.0F, 10.0F));
        ActionCapture actionCapture = new ActionCapture();

        playerTeleportPlanApplySystem.applyPlan(teleportExecutionPlan, actionCapture);

        Assert.assertEquals(12.5D, actionCapture.stateX, 0.0001D);
        Assert.assertEquals(64.0D, actionCapture.stateY, 0.0001D);
        Assert.assertEquals(-3.25D, actionCapture.stateZ, 0.0001D);
        Assert.assertEquals(45.0F, actionCapture.stateYaw, 0.0001F);
        Assert.assertEquals(10.0F, actionCapture.statePitch, 0.0001F);
        Assert.assertTrue(actionCapture.justTeleported);
        Assert.assertFalse(actionCapture.movementCheckEnabled);
        Assert.assertEquals(12.5D, actionCapture.locationX, 0.0001D);
        Assert.assertEquals(64.0D, actionCapture.locationY, 0.0001D);
        Assert.assertEquals(-3.25D, actionCapture.locationZ, 0.0001D);
        Assert.assertEquals(45.0F, actionCapture.locationYaw, 0.0001F);
        Assert.assertEquals(10.0F, actionCapture.locationPitch, 0.0001F);
        Assert.assertNotNull(actionCapture.teleportPacket);
    }

    private static final class ActionCapture implements PlayerTeleportPlanApplySystem.TeleportPlanActions {
        private double stateX;
        private double stateY;
        private double stateZ;
        private float stateYaw;
        private float statePitch;
        private boolean justTeleported;
        private boolean movementCheckEnabled;
        private double locationX;
        private double locationY;
        private double locationZ;
        private float locationYaw;
        private float locationPitch;
        private Packet13PlayerLookMove teleportPacket;

        @Override
        public void applyMovementState(
                double x,
                double y,
                double z,
                float yaw,
                float pitch,
                boolean justTeleported,
                boolean movementCheckEnabled
        ) {
            this.stateX = x;
            this.stateY = y;
            this.stateZ = z;
            this.stateYaw = yaw;
            this.statePitch = pitch;
            this.justTeleported = justTeleported;
            this.movementCheckEnabled = movementCheckEnabled;
        }

        @Override
        public void applyPlayerLocation(double x, double y, double z, float yaw, float pitch) {
            this.locationX = x;
            this.locationY = y;
            this.locationZ = z;
            this.locationYaw = yaw;
            this.locationPitch = pitch;
        }

        @Override
        public void sendTeleportPacket(Packet13PlayerLookMove teleportPacket) {
            this.teleportPacket = teleportPacket;
        }
    }
}
