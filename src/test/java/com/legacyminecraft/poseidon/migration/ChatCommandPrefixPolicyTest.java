package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.ChatCommandPrefixPolicy;
import org.junit.Assert;
import org.junit.Test;

public class ChatCommandPrefixPolicyTest {
    @Test
    public void detectsCommandPrefixSafely() {
        ChatCommandPrefixPolicy policy = ChatCommandPrefixPolicy.getInstance();

        Assert.assertEquals("/", policy.commandPrefix());
        Assert.assertTrue(policy.isCommand("/help"));
        Assert.assertFalse(policy.isCommand("hello"));
        Assert.assertFalse(policy.isCommand(null));
    }
}
