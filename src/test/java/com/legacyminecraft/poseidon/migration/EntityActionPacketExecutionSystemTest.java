package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.EntityActionPacketExecutionSystem;
import com.legacyminecraft.poseidon.network.EntityActionResultExecutionSystem;
import org.junit.Assert;
import org.junit.Test;

public class EntityActionPacketExecutionSystemTest {
    private final EntityActionPacketExecutionSystem entityActionPacketExecutionSystem =
            EntityActionPacketExecutionSystem.getInstance();
    private final EntityActionResultExecutionSystem entityActionResultExecutionSystem =
            EntityActionResultExecutionSystem.getInstance();

    @Test
    public void executeDisablesMovementCheckWhenResolverReturnsTrue() {
        ResolverCapture resolverCapture = new ResolverCapture(true);
        ActionsCapture actionsCapture = new ActionsCapture();

        entityActionPacketExecutionSystem.execute(
                resolverCapture,
                entityActionResultExecutionSystem,
                actionsCapture
        );

        Assert.assertEquals(1, resolverCapture.resolveCalls);
        Assert.assertTrue(actionsCapture.disabledMovementCheck);
    }

    @Test
    public void executeDoesNotDisableMovementCheckWhenResolverReturnsFalse() {
        ResolverCapture resolverCapture = new ResolverCapture(false);
        ActionsCapture actionsCapture = new ActionsCapture();

        entityActionPacketExecutionSystem.execute(
                resolverCapture,
                entityActionResultExecutionSystem,
                actionsCapture
        );

        Assert.assertEquals(1, resolverCapture.resolveCalls);
        Assert.assertFalse(actionsCapture.disabledMovementCheck);
    }

    private static final class ResolverCapture implements EntityActionPacketExecutionSystem.EntityActionResultResolver {
        private final boolean resolveResult;
        private int resolveCalls;

        private ResolverCapture(boolean resolveResult) {
            this.resolveResult = resolveResult;
        }

        @Override
        public boolean resolve() {
            this.resolveCalls++;
            return this.resolveResult;
        }
    }

    private static final class ActionsCapture implements EntityActionResultExecutionSystem.EntityActionResultActions {
        private boolean disabledMovementCheck;

        @Override
        public void disableMovementCheck() {
            this.disabledMovementCheck = true;
        }
    }
}
