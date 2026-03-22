package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.runtime.ServerConsoleMessageLogSystem;
import org.junit.Assert;
import org.junit.Test;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class ServerConsoleMessageLogSystemTest {
    private final ServerConsoleMessageLogSystem serverConsoleMessageLogSystem = ServerConsoleMessageLogSystem.getInstance();

    @Test
    public void logInfoWritesInfoMessage() {
        LoggerCaptureHandler loggerCaptureHandler = new LoggerCaptureHandler();
        Logger logger = Logger.getLogger(ServerConsoleMessageLogSystemTest.class.getName() + ".info");
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);
        logger.addHandler(loggerCaptureHandler);

        serverConsoleMessageLogSystem.logInfo("info-message", logger);

        Assert.assertEquals("info-message", loggerCaptureHandler.infoMessage);
    }

    @Test
    public void logWarningWritesWarningMessage() {
        LoggerCaptureHandler loggerCaptureHandler = new LoggerCaptureHandler();
        Logger logger = Logger.getLogger(ServerConsoleMessageLogSystemTest.class.getName() + ".warning");
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);
        logger.addHandler(loggerCaptureHandler);

        serverConsoleMessageLogSystem.logWarning("warning-message", logger);

        Assert.assertEquals("warning-message", loggerCaptureHandler.warningMessage);
    }

    private static final class LoggerCaptureHandler extends Handler {
        private String infoMessage;
        private String warningMessage;

        @Override
        public void publish(LogRecord record) {
            if (record == null) {
                return;
            }
            if (record.getLevel() == Level.INFO) {
                this.infoMessage = record.getMessage();
            } else if (record.getLevel() == Level.WARNING) {
                this.warningMessage = record.getMessage();
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
