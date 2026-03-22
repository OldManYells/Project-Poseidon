package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.GroundMovementDecisionLogSystem;
import com.legacyminecraft.poseidon.network.PlayerGroundMovementSystem;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class GroundMovementDecisionLogSystemTest {
    private final GroundMovementDecisionLogSystem system = GroundMovementDecisionLogSystem.getInstance();

    @Test
    public void emitLogsWritesWarningsAndConsoleLines() {
        PlayerGroundMovementSystem.GroundMovementDecision decision =
                PlayerGroundMovementSystem.GroundMovementDecision.abort(
                        Arrays.asList("warn-1", "warn-2"),
                        Arrays.asList("console-1", "console-2")
                );

        LogCaptureHandler logCaptureHandler = new LogCaptureHandler();
        Logger logger = Logger.getLogger(GroundMovementDecisionLogSystemTest.class.getName() + ".logger");
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);
        logger.addHandler(logCaptureHandler);

        ConsoleCaptureSink consoleCaptureSink = new ConsoleCaptureSink();

        system.emitLogs(decision, logger, consoleCaptureSink);

        Assert.assertEquals(Arrays.asList("warn-1", "warn-2"), logCaptureHandler.warningMessages);
        Assert.assertEquals(Arrays.asList("console-1", "console-2"), consoleCaptureSink.consoleMessages);
    }

    private static final class LogCaptureHandler extends Handler {
        private final List<String> warningMessages = new ArrayList<String>();

        @Override
        public void publish(LogRecord record) {
            if (record != null && record.getLevel() == Level.WARNING) {
                warningMessages.add(record.getMessage());
            }
        }

        @Override
        public void flush() {
        }

        @Override
        public void close() {
        }
    }

    private static final class ConsoleCaptureSink implements GroundMovementDecisionLogSystem.ConsoleLogSink {
        private final List<String> consoleMessages = new ArrayList<String>();

        @Override
        public void println(String logLine) {
            consoleMessages.add(logLine);
        }
    }
}
