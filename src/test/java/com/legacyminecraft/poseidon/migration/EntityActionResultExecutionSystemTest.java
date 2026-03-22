package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.EntityActionResultExecutionSystem;
import org.junit.Assert;
import org.junit.Test;

public class EntityActionResultExecutionSystemTest {
    private final EntityActionResultExecutionSystem entityActionResultExecutionSystem = EntityActionResultExecutionSystem.getInstance();

    @Test
    public void executeResultDisablesMovementCheckWhenRequested() {
        EntityActionCapture capture = new EntityActionCapture();

        entityActionResultExecutionSystem.executeResult(true, capture);

        Assert.assertTrue(capture.movementCheckDisabled);
    }

    @Test
    public void executeResultLeavesMovementCheckWhenNotRequested() {
        EntityActionCapture capture = new EntityActionCapture();

        entityActionResultExecutionSystem.executeResult(false, capture);

        Assert.assertFalse(capture.movementCheckDisabled);
    }

    private static final class EntityActionCapture implements EntityActionResultExecutionSystem.EntityActionResultActions {
        private boolean movementCheckDisabled;

        @Override
        public void disableMovementCheck() {
            this.movementCheckDisabled = true;
        }
    }
}
