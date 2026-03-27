package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.GroundMovementConsoleLogBehaviour;
import org.junit.Assert;
import org.junit.Test;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class GroundMovementConsoleLogBehaviourTest {
    @Test
    public void logsInfoLineWhenLoggerAndLinePresent() {
        LoggerCaptureHandler handler = new LoggerCaptureHandler();
        Logger logger = Logger.getLogger(GroundMovementConsoleLogBehaviourTest.class.getName() + ".logger");
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);
        logger.addHandler(handler);

        GroundMovementConsoleLogBehaviour.getInstance().log(logger, "movement-log-line");

        Assert.assertEquals("movement-log-line", handler.infoMessage);
    }

    @Test
    public void ignoresNullInputs() {
        GroundMovementConsoleLogBehaviour behaviour = GroundMovementConsoleLogBehaviour.getInstance();
        behaviour.log(null, "line");
        behaviour.log(Logger.getLogger(getClass().getName()), null);
    }

    private static final class LoggerCaptureHandler extends Handler {
        private String infoMessage;

        @Override
        public void publish(LogRecord record) {
            if (record != null && record.getLevel() == Level.INFO) {
                this.infoMessage = record.getMessage();
            }
        }

        @Override
        public void flush() {
        }

        @Override
        public void close() {
        }
    }
}
