package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.ConnectionLossReporter;
import org.junit.Assert;
import org.junit.Test;

public class ConnectionLossReporterTest {
    @Test
    public void disconnectReasonLoggingMatchesConfigAndReason() {
        ConnectionLossReporter reporter = ConnectionLossReporter.getInstance();

        Assert.assertFalse(reporter.shouldLogDisconnectReason(true, "disconnect.quitting"));
        Assert.assertTrue(reporter.shouldLogDisconnectReason(true, "disconnect.timeout"));
        Assert.assertTrue(reporter.shouldLogDisconnectReason(false, "disconnect.quitting"));
    }
}
