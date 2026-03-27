package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.ChatValidationMessagePolicy;
import org.junit.Assert;
import org.junit.Test;

public class ChatValidationMessagePolicyTest {
    @Test
    public void exposesLegacyChatValidationMessages() {
        ChatValidationMessagePolicy policy = ChatValidationMessagePolicy.getInstance();

        Assert.assertEquals("Illegal characters in chat", policy.illegalCharactersMessage());
        Assert.assertEquals("Chat message too long", policy.chatTooLongMessage());
    }
}
