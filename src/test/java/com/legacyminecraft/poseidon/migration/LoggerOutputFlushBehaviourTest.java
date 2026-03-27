package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.compat.bukkit.LoggerOutputFlushBehaviour;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class LoggerOutputFlushBehaviourTest {
    @Test
    public void flushDoesNotCallStreamFlushAndLogsMessagePayload() throws IOException {
        ThrowOnFlushOutputStream outputStream = new ThrowOnFlushOutputStream();
        outputStream.write("hello".getBytes(StandardCharsets.UTF_8));

        CapturingHandler handler = new CapturingHandler();
        Logger logger = Logger.getLogger("poseidon.loggerOutputFlushBehaviourTest");
        logger.setUseParentHandlers(false);
        logger.addHandler(handler);

        LoggerOutputFlushBehaviour.getInstance().flush(outputStream, System.lineSeparator(), logger, Level.INFO);

        Assert.assertEquals(1, handler.messages.size());
        Assert.assertEquals("hello", handler.messages.get(0));
        logger.removeHandler(handler);
    }

    @Test
    public void flushSkipsStandaloneLineSeparatorRecords() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(System.lineSeparator().getBytes(StandardCharsets.UTF_8));

        CapturingHandler handler = new CapturingHandler();
        Logger logger = Logger.getLogger("poseidon.loggerOutputFlushBehaviourSeparatorTest");
        logger.setUseParentHandlers(false);
        logger.addHandler(handler);

        LoggerOutputFlushBehaviour.getInstance().flush(outputStream, System.lineSeparator(), logger, Level.INFO);

        Assert.assertTrue(handler.messages.isEmpty());
        logger.removeHandler(handler);
    }

    private static final class ThrowOnFlushOutputStream extends ByteArrayOutputStream {
        @Override
        public void flush() throws IOException {
            throw new IOException("flush should not be called");
        }
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
        public void close() throws SecurityException {
        }
    }
}
