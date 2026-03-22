package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.runtime.ServerRunFailureRecoverySystem;
import org.junit.Assert;
import org.junit.Test;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class ServerRunFailureRecoverySystemTest {
    private final ServerRunFailureRecoverySystem serverRunFailureRecoverySystem =
            ServerRunFailureRecoverySystem.getInstance();

    @Test
    public void executeOnInitFailureDrainsCommands() {
        ActionCapture actionCapture = new ActionCapture();

        serverRunFailureRecoverySystem.executeOnInitFailure(actionCapture);

        Assert.assertTrue(actionCapture.drained);
    }

    @Test
    public void executeOnExceptionLogsAndDrainsCommands() {
        ActionCapture actionCapture = new ActionCapture();
        LoggerCaptureHandler loggerCaptureHandler = new LoggerCaptureHandler();
        Logger logger = Logger.getLogger(ServerRunFailureRecoverySystemTest.class.getName() + ".logger");
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);
        logger.addHandler(loggerCaptureHandler);

        serverRunFailureRecoverySystem.executeOnException(new RuntimeException("boom"), logger, actionCapture);

        Assert.assertTrue(actionCapture.drained);
        Assert.assertEquals("Unexpected exception", loggerCaptureHandler.severeMessage);
    }

    private static final class ActionCapture implements ServerRunFailureRecoverySystem.FailureActions {
        private boolean drained;

        @Override
        public void drainCommandsUntilStopped() {
            this.drained = true;
        }
    }

    private static final class LoggerCaptureHandler extends Handler {
        private String severeMessage;

        @Override
        public void publish(LogRecord record) {
            if (record != null && record.getLevel() == Level.SEVERE) {
                this.severeMessage = record.getMessage();
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
