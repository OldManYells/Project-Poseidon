package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.UnexpectedPacketProtocolErrorExecutionSystem;
import net.minecraft.server.Packet2Handshake;
import org.junit.Assert;
import org.junit.Test;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class UnexpectedPacketProtocolErrorExecutionSystemTest {
    private final UnexpectedPacketProtocolErrorExecutionSystem unexpectedPacketProtocolErrorExecutionSystem =
            UnexpectedPacketProtocolErrorExecutionSystem.getInstance();

    @Test
    public void executeLogsWarningAndDisconnectsWithProtocolMessage() {
        LoggerCaptureHandler loggerCaptureHandler = new LoggerCaptureHandler();
        Logger logger = Logger.getLogger(UnexpectedPacketProtocolErrorExecutionSystemTest.class.getName() + ".logger");
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);
        logger.addHandler(loggerCaptureHandler);

        ActionCapture actionCapture = new ActionCapture();

        unexpectedPacketProtocolErrorExecutionSystem.execute(
                Object.class,
                new Packet2Handshake(),
                logger,
                actionCapture
        );

        Assert.assertNotNull(loggerCaptureHandler.warningMessage);
        Assert.assertTrue(loggerCaptureHandler.warningMessage.contains("wasn\'t prepared to deal with"));
        Assert.assertEquals("Protocol error, unexpected packet", actionCapture.disconnectMessage);
    }

    private static final class ActionCapture implements UnexpectedPacketProtocolErrorExecutionSystem.ProtocolErrorActions {
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
