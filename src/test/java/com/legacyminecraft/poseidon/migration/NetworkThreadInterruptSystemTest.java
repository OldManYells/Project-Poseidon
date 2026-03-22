package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.NetworkThreadInterruptSystem;
import org.junit.Assert;
import org.junit.Test;

public class NetworkThreadInterruptSystemTest {
    private final NetworkThreadInterruptSystem networkThreadInterruptSystem = NetworkThreadInterruptSystem.getInstance();

    @Test
    public void interruptInterruptsBothReaderAndWriterThreads() {
        InterruptCaptureThread readerThread = new InterruptCaptureThread();
        InterruptCaptureThread writerThread = new InterruptCaptureThread();

        networkThreadInterruptSystem.interrupt(readerThread, writerThread);

        Assert.assertTrue(readerThread.interruptCalled);
        Assert.assertTrue(writerThread.interruptCalled);
    }

    private static final class InterruptCaptureThread extends Thread {
        private boolean interruptCalled;

        @Override
        public void interrupt() {
            this.interruptCalled = true;
            super.interrupt();
        }
    }
}
