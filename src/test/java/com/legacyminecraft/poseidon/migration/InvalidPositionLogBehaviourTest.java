package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.InvalidPositionLogBehaviour;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class InvalidPositionLogBehaviourTest {
    private final InvalidPositionLogBehaviour behaviour = InvalidPositionLogBehaviour.getInstance();

    @Test
    public void logInvalidPositionWritesWarning() {
        Logger logger = Logger.getLogger("InvalidPositionLogBehaviourTest");
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);
        CapturingHandler handler = new CapturingHandler();
        logger.addHandler(handler);
        try {
            behaviour.logInvalidPosition(logger, "bad position");
        } finally {
            logger.removeHandler(handler);
        }

        Assert.assertEquals(1, handler.records.size());
        Assert.assertEquals(Level.WARNING, handler.records.get(0).getLevel());
        Assert.assertEquals("bad position", handler.records.get(0).getMessage());
    }

    @Test
    public void logInvalidPositionNoopsWhenLoggerOrMessageMissing() {
        behaviour.logInvalidPosition(null, "x");
        behaviour.logInvalidPosition(Logger.getLogger("InvalidPositionLogBehaviourTest.noop"), null);
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
