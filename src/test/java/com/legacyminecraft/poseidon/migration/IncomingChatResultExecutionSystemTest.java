package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.IncomingChatPacketHandler;
import com.legacyminecraft.poseidon.network.IncomingChatResultExecutionSystem;
import org.junit.Assert;
import org.junit.Test;

public class IncomingChatResultExecutionSystemTest {
    private final IncomingChatResultExecutionSystem incomingChatResultExecutionSystem = IncomingChatResultExecutionSystem.getInstance();

    @Test
    public void executeResultDispatchesChatWhenValid() {
        IncomingChatPacketHandler.IncomingChatResult incomingChatResult =
                IncomingChatPacketHandler.IncomingChatResult.valid("hello");
        ChatActionCapture chatActionCapture = new ChatActionCapture();

        boolean accepted = incomingChatResultExecutionSystem.executeResult(incomingChatResult, chatActionCapture);

        Assert.assertTrue(accepted);
        Assert.assertEquals("hello", chatActionCapture.normalizedMessage);
        Assert.assertNull(chatActionCapture.disconnectMessage);
    }

    @Test
    public void executeResultDisconnectsWhenInvalid() {
        IncomingChatPacketHandler.IncomingChatResult incomingChatResult =
                IncomingChatPacketHandler.IncomingChatResult.invalid("bad chat");
        ChatActionCapture chatActionCapture = new ChatActionCapture();

        boolean accepted = incomingChatResultExecutionSystem.executeResult(incomingChatResult, chatActionCapture);

        Assert.assertFalse(accepted);
        Assert.assertEquals("bad chat", chatActionCapture.disconnectMessage);
        Assert.assertNull(chatActionCapture.normalizedMessage);
    }

    private static final class ChatActionCapture implements IncomingChatResultExecutionSystem.ChatActions {
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
