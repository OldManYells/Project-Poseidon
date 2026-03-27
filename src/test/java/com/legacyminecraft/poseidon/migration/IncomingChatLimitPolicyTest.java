package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.IncomingChatLimitPolicy;
import org.junit.Assert;
import org.junit.Test;

public class IncomingChatLimitPolicyTest {
    @Test
    public void exposesMaxChatLengthLimit() {
        IncomingChatLimitPolicy policy = IncomingChatLimitPolicy.getInstance();

        Assert.assertEquals(100, policy.maxChatLength());
    }
}
