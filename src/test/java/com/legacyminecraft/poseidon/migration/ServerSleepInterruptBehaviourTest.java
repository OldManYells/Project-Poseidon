package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.runtime.ServerSleepInterruptBehaviour;
import org.junit.Assert;
import org.junit.Test;

public class ServerSleepInterruptBehaviourTest {
    private final ServerSleepInterruptBehaviour serverSleepInterruptBehaviour =
            ServerSleepInterruptBehaviour.getInstance();

    @Test
    public void preserveInterruptMarksCurrentThreadInterrupted() {
        Assert.assertFalse(Thread.currentThread().isInterrupted());
        try {
            serverSleepInterruptBehaviour.preserveInterrupt();
            Assert.assertTrue(Thread.currentThread().isInterrupted());
        } finally {
            Thread.interrupted();
        }
    }
}
