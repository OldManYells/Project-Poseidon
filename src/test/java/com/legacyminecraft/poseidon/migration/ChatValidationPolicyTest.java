package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.ChatValidationPolicy;
import org.junit.Assert;
import org.junit.Test;

public class ChatValidationPolicyTest {
    private final ChatValidationPolicy policy = ChatValidationPolicy.getInstance();

    @Test
    public void validatesAndNormalizesMessage() {
        ChatValidationPolicy.ValidationResult result = policy.validateIncomingMessage("  hello_world  ", 100, "abcdefghijklmnopqrstuvwxyz_");
        Assert.assertTrue(result.isValid());
        Assert.assertEquals("hello_world", result.getNormalizedMessage());
    }

    @Test
    public void rejectsLongMessages() {
        ChatValidationPolicy.ValidationResult result = policy.validateIncomingMessage("123456", 5, "123456");
        Assert.assertFalse(result.isValid());
        Assert.assertEquals("Chat message too long", result.getKickMessage());
    }

    @Test
    public void rejectsIllegalCharacters() {
        ChatValidationPolicy.ValidationResult result = policy.validateIncomingMessage("abc$", 100, "abc");
        Assert.assertFalse(result.isValid());
        Assert.assertEquals("Illegal characters in chat", result.getKickMessage());
    }
}
