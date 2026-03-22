package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.auth.login.LoginTimeoutPolicy;
import org.junit.Assert;
import org.junit.Test;

public class LoginTimeoutPolicyTest {
    private final LoginTimeoutPolicy policy = LoginTimeoutPolicy.getInstance();

    @Test
    public void timesOutAtConfiguredTick() {
        Assert.assertFalse(policy.shouldDisconnectForTimeout(599, 600));
        Assert.assertTrue(policy.shouldDisconnectForTimeout(600, 600));
        Assert.assertTrue(policy.shouldDisconnectForTimeout(700, 600));
    }

    @Test
    public void exposesTimeoutKickMessage() {
        Assert.assertEquals("Took too long to log in", policy.getTimeoutKickMessage());
    }
}
