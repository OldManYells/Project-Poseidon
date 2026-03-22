package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.NetworkCloseMonitorStartSystem;
import org.junit.Assert;
import org.junit.Test;

public class NetworkCloseMonitorStartSystemTest {
    private final NetworkCloseMonitorStartSystem networkCloseMonitorStartSystem =
            NetworkCloseMonitorStartSystem.getInstance();

    @Test
    public void startExecutesCloseMonitorStartupSequenceInOrder() {
        CloseMonitorCapture closeMonitorCapture = new CloseMonitorCapture();

        networkCloseMonitorStartSystem.start(closeMonitorCapture);

        Assert.assertTrue(closeMonitorCapture.interruptedNetworkThreads);
        Assert.assertTrue(closeMonitorCapture.markedShuttingDown);
        Assert.assertTrue(closeMonitorCapture.interruptedReaderThread);
        Assert.assertTrue(closeMonitorCapture.startedCloseMonitorThread);
        Assert.assertEquals("interruptAll>markShutdown>interruptReader>startMonitor>", closeMonitorCapture.callOrder.toString());
    }

    private static final class CloseMonitorCapture implements NetworkCloseMonitorStartSystem.CloseMonitorActions {
        private final StringBuilder callOrder = new StringBuilder();
        private boolean interruptedNetworkThreads;
        private boolean markedShuttingDown;
        private boolean interruptedReaderThread;
        private boolean startedCloseMonitorThread;

        @Override
        public void interruptNetworkThreads() {
            this.callOrder.append("interruptAll>");
            this.interruptedNetworkThreads = true;
        }

        @Override
        public void markShuttingDown() {
            this.callOrder.append("markShutdown>");
            this.markedShuttingDown = true;
        }

        @Override
        public void interruptReaderThread() {
            this.callOrder.append("interruptReader>");
            this.interruptedReaderThread = true;
        }

        @Override
        public void startCloseMonitorThread() {
            this.callOrder.append("startMonitor>");
            this.startedCloseMonitorThread = true;
        }
    }
}
