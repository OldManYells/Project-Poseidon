package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.MovementCheckReenableExecutionSystem;
import com.legacyminecraft.poseidon.network.MovementPacketPolicy;
import org.junit.Assert;
import org.junit.Test;

public class MovementCheckReenableExecutionSystemTest {
    private final MovementCheckReenableExecutionSystem movementCheckReenableExecutionSystem =
            MovementCheckReenableExecutionSystem.getInstance();
    private final MovementPacketPolicy movementPacketPolicy = MovementPacketPolicy.getInstance();

    @Test
    public void applyReenablesWhenMovementPolicyTriggers() {
        boolean reenabled = movementCheckReenableExecutionSystem.apply(
                false,
                5.0D,
                0.05D,
                8.0D,
                5.0D,
                0.0D,
                8.0D,
                movementPacketPolicy
        );

        Assert.assertTrue(reenabled);
    }

    @Test
    public void applyKeepsCurrentStateWhenMovementPolicyDoesNotTrigger() {
        boolean stayedEnabled = movementCheckReenableExecutionSystem.apply(
                true,
                4.0D,
                0.0D,
                0.0D,
                0.0D,
                0.0D,
                0.0D,
                movementPacketPolicy
        );
        boolean stayedDisabled = movementCheckReenableExecutionSystem.apply(
                false,
                4.0D,
                0.0D,
                0.0D,
                0.0D,
                0.0D,
                0.0D,
                movementPacketPolicy
        );

        Assert.assertTrue(stayedEnabled);
        Assert.assertFalse(stayedDisabled);
    }
}
