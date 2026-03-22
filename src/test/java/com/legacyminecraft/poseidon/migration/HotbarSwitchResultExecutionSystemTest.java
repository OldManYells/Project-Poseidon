package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.inventory.HotbarSelectionBehaviour;
import com.legacyminecraft.poseidon.network.HotbarSwitchResultExecutionSystem;
import org.junit.Assert;
import org.junit.Test;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class HotbarSwitchResultExecutionSystemTest {
    private final HotbarSwitchResultExecutionSystem hotbarSwitchResultExecutionSystem = HotbarSwitchResultExecutionSystem.getInstance();

    @Test
    public void executeResultDisconnectsAndLogsOnInvalidSelection() {
        LoggerCaptureHandler loggerCaptureHandler = new LoggerCaptureHandler();
        Logger logger = Logger.getLogger(HotbarSwitchResultExecutionSystemTest.class.getName() + ".logger");
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);
        logger.addHandler(loggerCaptureHandler);

        SwitchActionCapture switchActionCapture = new SwitchActionCapture();

        boolean accepted = hotbarSwitchResultExecutionSystem.executeResult(
                HotbarSelectionBehaviour.SwitchResult.INVALID_SELECTION,
                "PlayerOne",
                logger,
                switchActionCapture
        );

        Assert.assertFalse(accepted);
        Assert.assertNotNull(loggerCaptureHandler.warningMessage);
        Assert.assertTrue(loggerCaptureHandler.warningMessage.contains("PlayerOne"));
        Assert.assertEquals("Invalid hotbar selection (Hacking?)", switchActionCapture.disconnectMessage);
    }

    @Test
    public void executeResultAcceptsValidSelection() {
        LoggerCaptureHandler loggerCaptureHandler = new LoggerCaptureHandler();
        Logger logger = Logger.getLogger(HotbarSwitchResultExecutionSystemTest.class.getName() + ".logger.valid");
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);
        logger.addHandler(loggerCaptureHandler);

        SwitchActionCapture switchActionCapture = new SwitchActionCapture();

        boolean accepted = hotbarSwitchResultExecutionSystem.executeResult(
                HotbarSelectionBehaviour.SwitchResult.APPLIED,
                "PlayerTwo",
                logger,
                switchActionCapture
        );

        Assert.assertTrue(accepted);
        Assert.assertNull(loggerCaptureHandler.warningMessage);
        Assert.assertNull(switchActionCapture.disconnectMessage);
    }

    private static final class SwitchActionCapture implements HotbarSwitchResultExecutionSystem.SwitchActions {
        private String disconnectMessage;

        @Override
        public void disconnect(String message) {
            this.disconnectMessage = message;
        }
    }

    private static final class LoggerCaptureHandler extends Handler {
        private String warningMessage;

        @Override
        public void publish(LogRecord record) {
            if (record != null && record.getLevel() == Level.WARNING) {
                warningMessage = record.getMessage();
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
