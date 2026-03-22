package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.runtime.ServerRunTerminationSystem;
import org.junit.Assert;
import org.junit.Test;

public class ServerRunTerminationSystemTest {
    private final ServerRunTerminationSystem serverRunTerminationSystem =
            ServerRunTerminationSystem.getInstance();

    @Test
    public void executeTerminationStopsMarksStoppedAndExits() {
        TerminationCapture terminationCapture = new TerminationCapture();

        serverRunTerminationSystem.executeTermination(terminationCapture);

        Assert.assertTrue(terminationCapture.stopCalled);
        Assert.assertTrue(terminationCapture.markStoppedCalled);
        Assert.assertTrue(terminationCapture.exitCalled);
        Assert.assertEquals(0, terminationCapture.exitCode);
    }

    @Test
    public void executeTerminationStillExitsWhenStopThrows() {
        TerminationCapture terminationCapture = new TerminationCapture();
        terminationCapture.stopThrows = true;

        serverRunTerminationSystem.executeTermination(terminationCapture);

        Assert.assertTrue(terminationCapture.stopCalled);
        Assert.assertFalse(terminationCapture.markStoppedCalled);
        Assert.assertTrue(terminationCapture.exitCalled);
        Assert.assertEquals(0, terminationCapture.exitCode);
    }

    private static final class TerminationCapture implements ServerRunTerminationSystem.TerminationActions {
        private boolean stopCalled;
        private boolean markStoppedCalled;
        private boolean exitCalled;
        private boolean stopThrows;
        private int exitCode = -1;

        @Override
        public void stopServer() {
            this.stopCalled = true;
            if (this.stopThrows) {
                throw new RuntimeException("stop failed");
            }
        }

        @Override
        public void markServerStopped() {
            this.markStoppedCalled = true;
        }

        @Override
        public void exitProcess(int statusCode) {
            this.exitCalled = true;
            this.exitCode = statusCode;
        }
    }
}
