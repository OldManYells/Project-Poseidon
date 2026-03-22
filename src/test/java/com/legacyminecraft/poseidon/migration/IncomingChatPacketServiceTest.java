package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.IncomingChatPacketHandler;
import org.junit.Assert;
import org.junit.Test;

public class IncomingChatPacketServiceTest {
    @Test
    public void validChatMessageIsTrimmedAndAccepted() {
        IncomingChatPacketHandler service = IncomingChatPacketHandler.getInstance();
        IncomingChatPacketHandler.IncomingChatResult result =
                service.processIncomingChat("  hello  ", 100, "helo");

        Assert.assertTrue(result.isValid());
        Assert.assertEquals("hello", result.getNormalizedMessage());
        Assert.assertNull(result.getDisconnectReason());
    }

    @Test
    public void invalidChatMessageReturnsDisconnectReason() {
        IncomingChatPacketHandler service = IncomingChatPacketHandler.getInstance();
        IncomingChatPacketHandler.IncomingChatResult result =
                service.processIncomingChat("hello!", 100, "helo");

        Assert.assertFalse(result.isValid());
        Assert.assertEquals("Illegal characters in chat", result.getDisconnectReason());
    }
}
