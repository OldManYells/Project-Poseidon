package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.inventory.HotbarSelectionBehaviour;
import com.legacyminecraft.poseidon.network.HotbarSwitchPacketExecutionSystem;
import com.legacyminecraft.poseidon.network.HotbarSwitchResultExecutionSystem;
import org.junit.Assert;
import org.junit.Test;

import java.util.logging.Logger;

public class HotbarSwitchPacketExecutionSystemTest {
    private final HotbarSwitchPacketExecutionSystem hotbarSwitchPacketExecutionSystem =
            HotbarSwitchPacketExecutionSystem.getInstance();
    private final HotbarSwitchResultExecutionSystem hotbarSwitchResultExecutionSystem =
            HotbarSwitchResultExecutionSystem.getInstance();
    private final Logger logger = Logger.getLogger(getClass().getName());

    @Test
    public void executeDisconnectsWhenSwitchResultInvalid() {
        SwitchResolverCapture switchResolverCapture =
                new SwitchResolverCapture(HotbarSelectionBehaviour.SwitchResult.INVALID_SELECTION);
        SwitchActionsCapture switchActionsCapture = new SwitchActionsCapture();

        boolean accepted = hotbarSwitchPacketExecutionSystem.execute(
                switchResolverCapture,
                hotbarSwitchResultExecutionSystem,
                "Player",
                logger,
                switchActionsCapture
        );

        Assert.assertFalse(accepted);
        Assert.assertEquals(1, switchResolverCapture.resolveCalls);
        Assert.assertEquals("Invalid hotbar selection (Hacking?)", switchActionsCapture.disconnectMessage);
    }

    @Test
    public void executeAcceptsWhenSwitchResultApplied() {
        SwitchResolverCapture switchResolverCapture =
                new SwitchResolverCapture(HotbarSelectionBehaviour.SwitchResult.APPLIED);
        SwitchActionsCapture switchActionsCapture = new SwitchActionsCapture();

        boolean accepted = hotbarSwitchPacketExecutionSystem.execute(
                switchResolverCapture,
                hotbarSwitchResultExecutionSystem,
                "Player",
                logger,
                switchActionsCapture
        );

        Assert.assertTrue(accepted);
        Assert.assertEquals(1, switchResolverCapture.resolveCalls);
        Assert.assertNull(switchActionsCapture.disconnectMessage);
    }

    private static final class SwitchResolverCapture implements HotbarSwitchPacketExecutionSystem.SwitchResolver {
        private final HotbarSelectionBehaviour.SwitchResult switchResult;
        private int resolveCalls;

        private SwitchResolverCapture(HotbarSelectionBehaviour.SwitchResult switchResult) {
            this.switchResult = switchResult;
        }

        @Override
        public HotbarSelectionBehaviour.SwitchResult resolve() {
            this.resolveCalls++;
            return this.switchResult;
        }
    }

    private static final class SwitchActionsCapture implements HotbarSwitchResultExecutionSystem.SwitchActions {
        private String disconnectMessage;

        @Override
        public void disconnect(String message) {
            this.disconnectMessage = message;
        }
    }
}
