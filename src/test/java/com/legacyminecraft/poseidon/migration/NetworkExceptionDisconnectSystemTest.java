package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.NetworkExceptionDisconnectSystem;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class NetworkExceptionDisconnectSystemTest {
    private final NetworkExceptionDisconnectSystem networkExceptionDisconnectSystem =
            NetworkExceptionDisconnectSystem.getInstance();

    @Test
    public void executePrintsStackAndDisconnectsWithGenericReason() {
        ExceptionCapture exceptionCapture = new ExceptionCapture();
        IllegalStateException exception = new IllegalStateException("broken");

        networkExceptionDisconnectSystem.execute(exception, exceptionCapture);

        Assert.assertSame(exception, exceptionCapture.printedException);
        Assert.assertEquals("Internal exception: java.lang.IllegalStateException: broken", exceptionCapture.disconnectReason);
    }

    @Test
    public void executeSkipsStackTraceForExpectedSocketCloseDisconnects() {
        ExceptionCapture exceptionCapture = new ExceptionCapture();
        SocketException exception = new SocketException("Socket closed");

        networkExceptionDisconnectSystem.execute(exception, exceptionCapture);

        Assert.assertNull(exceptionCapture.printedException);
        Assert.assertEquals("Connection closed", exceptionCapture.disconnectReason);
    }

    @Test
    public void executeSkipsStackTraceForWrappedSocketCloseDisconnects() {
        ExceptionCapture exceptionCapture = new ExceptionCapture();
        IOException exception = new IOException();
        exception.initCause(new SocketException("Socket closed"));

        networkExceptionDisconnectSystem.execute(exception, exceptionCapture);

        Assert.assertNull(exceptionCapture.printedException);
        Assert.assertEquals("Connection closed", exceptionCapture.disconnectReason);
    }

    @Test
    public void executeSkipsStackTraceForSocketTimeoutDisconnects() {
        ExceptionCapture exceptionCapture = new ExceptionCapture();
        SocketTimeoutException exception = new SocketTimeoutException("Read timed out");

        networkExceptionDisconnectSystem.execute(exception, exceptionCapture);

        Assert.assertNull(exceptionCapture.printedException);
        Assert.assertEquals("Connection closed", exceptionCapture.disconnectReason);
    }

    private static final class ExceptionCapture implements NetworkExceptionDisconnectSystem.ExceptionActions {
        private Exception printedException;
        private String disconnectReason;

        @Override
        public void printStackTrace(Exception exception) {
            this.printedException = exception;
        }

        @Override
        public void disconnectWithGenericReason(String reason) {
            this.disconnectReason = reason;
        }
    }
}
