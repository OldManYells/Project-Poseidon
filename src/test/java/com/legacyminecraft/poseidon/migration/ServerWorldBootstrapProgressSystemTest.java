package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.runtime.ServerWorldBootstrapProgressSystem;
import org.junit.Assert;
import org.junit.Test;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class ServerWorldBootstrapProgressSystemTest {
    private final ServerWorldBootstrapProgressSystem serverWorldBootstrapProgressSystem =
            ServerWorldBootstrapProgressSystem.getInstance();

    @Test
    public void applyProgressUpdatesStateAndLogs() {
        LoggerCaptureHandler loggerCaptureHandler = new LoggerCaptureHandler();
        Logger logger = Logger.getLogger(ServerWorldBootstrapProgressSystemTest.class.getName() + ".logger");
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);
        logger.addHandler(loggerCaptureHandler);

        ProgressCapture progressCapture = new ProgressCapture();

        serverWorldBootstrapProgressSystem.applyProgress("Preparing spawn", 42, logger, progressCapture);

        Assert.assertEquals("Preparing spawn", progressCapture.task);
        Assert.assertEquals(42, progressCapture.percent);
        Assert.assertEquals("Preparing spawn: 42%", loggerCaptureHandler.infoMessage);
    }

    @Test
    public void completeBootstrapResetsAndEnablesPlugins() {
        CompletionCapture completionCapture = new CompletionCapture();

        serverWorldBootstrapProgressSystem.completeBootstrap(completionCapture);

        Assert.assertTrue(completionCapture.resetCalled);
        Assert.assertTrue(completionCapture.enableCalled);
    }

    private static final class ProgressCapture implements ServerWorldBootstrapProgressSystem.ProgressStateSink {
        private String task;
        private int percent;

        @Override
        public void apply(String task, int percent) {
            this.task = task;
            this.percent = percent;
        }
    }

    private static final class CompletionCapture implements ServerWorldBootstrapProgressSystem.CompletionActions {
        private boolean resetCalled;
        private boolean enableCalled;

        @Override
        public void resetProgress() {
            this.resetCalled = true;
        }

        @Override
        public void enablePostWorldPlugins() {
            this.enableCalled = true;
        }
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
