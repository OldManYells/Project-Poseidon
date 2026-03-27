package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.NetworkManagerConsoleLogBehaviour;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class NetworkManagerConsoleLogBehaviourTest {
    private final NetworkManagerConsoleLogBehaviour behaviour = NetworkManagerConsoleLogBehaviour.getInstance();

    @Test
    public void logWritesInfoWhenLoggerAndMessagePresent() {
        Logger logger = Logger.getLogger("NetworkManagerConsoleLogBehaviourTest");
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);

        CapturingHandler handler = new CapturingHandler();
        logger.addHandler(handler);
        try {
            behaviour.log(logger, "hello");
        } finally {
            logger.removeHandler(handler);
        }

        Assert.assertEquals(1, handler.messages.size());
        Assert.assertEquals("hello", handler.messages.get(0));
    }

    @Test
    public void logNoopsWhenLoggerOrMessageMissing() {
        behaviour.log(null, "hello");
        behaviour.log(Logger.getLogger("NetworkManagerConsoleLogBehaviourTest.noop"), null);
    }

    private static final class CapturingHandler extends Handler {
        private final List<String> messages = new ArrayList<String>();

        @Override
        public void publish(LogRecord record) {
            messages.add(record.getMessage());
        }

        @Override
        public void flush() {
        }

        @Override
        public void close() {
        }
    }
}
