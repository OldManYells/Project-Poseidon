package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.ConnectionAcceptExceptionPolicy;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.SocketException;

public class ConnectionAcceptExceptionPolicyTest {
    private final ConnectionAcceptExceptionPolicy policy = ConnectionAcceptExceptionPolicy.getInstance();

    @Test
    public void classifiesSocketClosedAsExpectedShutdown() {
        Assert.assertTrue(policy.isExpectedShutdownException(new SocketException("Socket closed")));
    }

    @Test
    public void classifiesWrappedClosedMessageAsExpectedShutdown() {
        IOException exception = new IOException("accept failed");
        exception.initCause(new IllegalStateException("server socket closed"));
        Assert.assertTrue(policy.isExpectedShutdownException(exception));
    }

    @Test
    public void doesNotClassifyUnexpectedAcceptFailuresAsShutdown() {
        Assert.assertFalse(policy.isExpectedShutdownException(new IOException("bind failed")));
    }
}
