package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.runtime.ConsoleReaderExceptionPolicy;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class ConsoleReaderExceptionPolicyTest {
    private final ConsoleReaderExceptionPolicy policy = ConsoleReaderExceptionPolicy.getInstance();

    @Test
    public void classifiesClosedReaderErrorsAsExpected() {
        Assert.assertTrue(policy.isExpectedShutdownThrowable(new IllegalStateException("Console reader closed")));
        Assert.assertTrue(policy.isExpectedShutdownThrowable(new IOException("Stream closed")));
        Assert.assertTrue(policy.isExpectedShutdownThrowable(new IOException("end of stream")));
    }

    @Test
    public void doesNotClassifyUnexpectedFailuresAsExpected() {
        Assert.assertFalse(policy.isExpectedShutdownThrowable(new RuntimeException("boom")));
    }
}
