package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.IncomingChatPacketExecutionSystem;
import com.legacyminecraft.poseidon.network.IncomingChatPacketHandler;
import com.legacyminecraft.poseidon.network.IncomingChatResultExecutionSystem;
import org.junit.Assert;
import org.junit.Test;

public class IncomingChatPacketExecutionSystemTest {
    private final IncomingChatPacketExecutionSystem incomingChatPacketExecutionSystem =
            IncomingChatPacketExecutionSystem.getInstance();
    private final IncomingChatPacketHandler incomingChatPacketHandler = IncomingChatPacketHandler.getInstance();
    private final IncomingChatResultExecutionSystem incomingChatResultExecutionSystem =
            IncomingChatResultExecutionSystem.getInstance();

    @Test
    public void executeDispatchesNormalizedChatWhenMessageValid() {
        ChatActionsCapture chatActionsCapture = new ChatActionsCapture();

        boolean continueProcessing = incomingChatPacketExecutionSystem.execute(
                "  hello  ",
                100,
                "helo ",
                incomingChatPacketHandler,
                incomingChatResultExecutionSystem,
                chatActionsCapture
        );

        Assert.assertTrue(continueProcessing);
        Assert.assertEquals("hello", chatActionsCapture.normalizedMessage);
        Assert.assertNull(chatActionsCapture.disconnectMessage);
    }

    @Test
    public void executeDisconnectsWhenMessageContainsIllegalCharacters() {
        ChatActionsCapture chatActionsCapture = new ChatActionsCapture();

        boolean continueProcessing = incomingChatPacketExecutionSystem.execute(
                "hello!",
                100,
                "helo ",
                incomingChatPacketHandler,
                incomingChatResultExecutionSystem,
                chatActionsCapture
        );

        Assert.assertFalse(continueProcessing);
        Assert.assertEquals("Illegal characters in chat", chatActionsCapture.disconnectMessage);
        Assert.assertNull(chatActionsCapture.normalizedMessage);
    }

    private static final class ChatActionsCapture implements IncomingChatResultExecutionSystem.ChatActions {
        private String disconnectMessage;
        private String normalizedMessage;

        @Override
        public void disconnect(String message) {
            this.disconnectMessage = message;
        }

        @Override
        public void dispatchNormalizedChat(String normalizedMessage) {
            this.normalizedMessage = normalizedMessage;
        }
    }
}
