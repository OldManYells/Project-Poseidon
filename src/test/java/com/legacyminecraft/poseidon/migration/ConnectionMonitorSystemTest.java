package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.ConnectionMonitorSystem;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

public class ConnectionMonitorSystemTest {
    private final ConnectionMonitorSystem connectionMonitorSystem = ConnectionMonitorSystem.getInstance();

    @Test
    public void monitorAndDisconnectIfOpenPreservesInterruptFlag() {
        AtomicBoolean interruptWriterCalled = new AtomicBoolean(false);
        AtomicBoolean disconnectCalled = new AtomicBoolean(false);

        Thread.currentThread().interrupt();
        try {
            connectionMonitorSystem.monitorAndDisconnectIfOpen(
                    new ConnectionMonitorSystem.ConnectionState() {
                        @Override
                        public boolean isConnectionOpen() {
                            return true;
                        }
                    },
                    new Runnable() {
                        @Override
                        public void run() {
                            interruptWriterCalled.set(true);
                        }
                    },
                    new Runnable() {
                        @Override
                        public void run() {
                            disconnectCalled.set(true);
                        }
                    }
            );
        } finally {
            Assert.assertTrue(Thread.currentThread().isInterrupted());
            Thread.interrupted();
        }

        Assert.assertFalse(interruptWriterCalled.get());
        Assert.assertFalse(disconnectCalled.get());
    }
}
