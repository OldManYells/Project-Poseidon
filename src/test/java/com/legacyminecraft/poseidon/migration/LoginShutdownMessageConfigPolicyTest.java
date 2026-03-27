package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.LoginShutdownMessageConfigPolicy;
import org.junit.Assert;
import org.junit.Test;

public class LoginShutdownMessageConfigPolicyTest {
    @Test
    public void exposesShutdownKickMessageKey() {
        LoginShutdownMessageConfigPolicy policy = LoginShutdownMessageConfigPolicy.getInstance();

        Assert.assertEquals("message.kick.shutdown", policy.kickShutdownKey());
    }
}
