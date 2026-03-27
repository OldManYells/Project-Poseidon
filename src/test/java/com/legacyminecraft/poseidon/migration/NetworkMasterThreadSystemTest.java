package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.NetworkMasterThreadSystem;
import org.junit.Assert;
import org.junit.Test;

public class NetworkMasterThreadSystemTest {
    private final NetworkMasterThreadSystem networkMasterThreadSystem = NetworkMasterThreadSystem.getInstance();

    @Test
    public void stopLingeringNetworkThreadsPreservesInterruptFlag() {
        Thread.currentThread().interrupt();
        try {
            networkMasterThreadSystem.stopLingeringNetworkThreads(new Thread(), new Thread());
        } finally {
            Assert.assertTrue(Thread.currentThread().isInterrupted());
            Thread.interrupted();
        }
    }
}
