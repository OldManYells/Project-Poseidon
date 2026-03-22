package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.NetworkTickFinalizationSystem;
import org.junit.Assert;
import org.junit.Test;

public class NetworkTickFinalizationSystemTest {
    private final NetworkTickFinalizationSystem networkTickFinalizationSystem =
            NetworkTickFinalizationSystem.getInstance();

    @Test
    public void finalizeTickInterruptsAndNotifiesWhenTerminatingAndQueueEmpty() {
        TickFinalizationCapture tickFinalizationCapture = new TickFinalizationCapture();

        networkTickFinalizationSystem.finalizeTick(
                true,
                true,
                "disconnect.test",
                new Object[]{"reason"},
                tickFinalizationCapture
        );

        Assert.assertTrue(tickFinalizationCapture.interrupted);
        Assert.assertTrue(tickFinalizationCapture.notified);
        Assert.assertEquals("disconnect.test", tickFinalizationCapture.disconnectKey);
        Assert.assertNotNull(tickFinalizationCapture.disconnectArgs);
        Assert.assertEquals(1, tickFinalizationCapture.disconnectArgs.length);
        Assert.assertEquals("reason", tickFinalizationCapture.disconnectArgs[0]);
    }

    @Test
    public void finalizeTickSkipsInterruptAndNotificationWhenQueueNotEmpty() {
        TickFinalizationCapture tickFinalizationCapture = new TickFinalizationCapture();

        networkTickFinalizationSystem.finalizeTick(
                true,
                false,
                "disconnect.test",
                new Object[0],
                tickFinalizationCapture
        );

        Assert.assertFalse(tickFinalizationCapture.interrupted);
        Assert.assertFalse(tickFinalizationCapture.notified);
    }

    @Test
    public void finalizeTickSkipsInterruptAndNotificationWhenNotTerminating() {
        TickFinalizationCapture tickFinalizationCapture = new TickFinalizationCapture();

        networkTickFinalizationSystem.finalizeTick(
                false,
                true,
                "disconnect.test",
                new Object[0],
                tickFinalizationCapture
        );

        Assert.assertFalse(tickFinalizationCapture.interrupted);
        Assert.assertFalse(tickFinalizationCapture.notified);
    }

    private static final class TickFinalizationCapture implements NetworkTickFinalizationSystem.TickFinalizationActions {
        private boolean interrupted;
        private boolean notified;
        private String disconnectKey;
        private Object[] disconnectArgs;

        @Override
        public void interruptNetworkThreads() {
            this.interrupted = true;
        }

        @Override
        public void notifyDisconnect(String key, Object[] args) {
            this.notified = true;
            this.disconnectKey = key;
            this.disconnectArgs = args;
        }
    }
}
