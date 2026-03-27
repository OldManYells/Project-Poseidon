package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.runtime.ServerCommandDispatchPolicy;
import org.junit.Assert;
import org.junit.Test;

public class ServerCommandDispatchPolicyTest {
    private final ServerCommandDispatchPolicy policy = ServerCommandDispatchPolicy.getInstance();

    @Test
    public void resolveDispatchCommandFallsBackToOriginalWhenEventCommandIsNull() {
        Assert.assertEquals("stop", policy.resolveDispatchCommand(null, "stop"));
        Assert.assertEquals("", policy.resolveDispatchCommand(null, null));
    }

    @Test
    public void shouldDispatchRejectsBlankCommands() {
        Assert.assertTrue(policy.shouldDispatch("say hello"));
        Assert.assertFalse(policy.shouldDispatch(null));
        Assert.assertFalse(policy.shouldDispatch(""));
        Assert.assertFalse(policy.shouldDispatch("   "));
    }
}
