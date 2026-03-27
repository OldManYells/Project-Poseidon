package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.NetworkExceptionLogBehaviour;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class NetworkExceptionLogBehaviourTest {
    private final NetworkExceptionLogBehaviour behaviour = NetworkExceptionLogBehaviour.getInstance();

    @Test
    public void logUnexpectedExceptionWritesWarningWithThrowable() {
        Logger logger = Logger.getLogger("NetworkExceptionLogBehaviourTest");
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);

        CapturingHandler handler = new CapturingHandler();
        logger.addHandler(handler);
        Exception exception = new IllegalStateException("boom");
        try {
            behaviour.logUnexpectedException(logger, exception);
        } finally {
            logger.removeHandler(handler);
        }

        Assert.assertEquals(1, handler.records.size());
        Assert.assertEquals(Level.WARNING, handler.records.get(0).getLevel());
        Assert.assertEquals("Unexpected network exception", handler.records.get(0).getMessage());
        Assert.assertSame(exception, handler.records.get(0).getThrown());
    }

    @Test
    public void logUnexpectedExceptionNoopsWhenLoggerOrExceptionMissing() {
        behaviour.logUnexpectedException(null, new RuntimeException("x"));
        behaviour.logUnexpectedException(Logger.getLogger("NetworkExceptionLogBehaviourTest.noop"), null);
    }

    private static final class CapturingHandler extends Handler {
        private final List<LogRecord> records = new ArrayList<LogRecord>();

        @Override
        public void publish(LogRecord record) {
            records.add(record);
        }

        @Override
        public void flush() {
        }

        @Override
        public void close() {
        }
    }
}
