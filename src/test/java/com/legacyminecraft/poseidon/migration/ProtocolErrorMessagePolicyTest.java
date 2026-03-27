package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.ProtocolErrorMessagePolicy;
import org.junit.Assert;
import org.junit.Test;

public class ProtocolErrorMessagePolicyTest {
    @Test
    public void exposesLegacyProtocolErrorKickMessage() {
        Assert.assertEquals(
                "Protocol error, unexpected packet",
                ProtocolErrorMessagePolicy.getInstance().protocolErrorKickMessage()
        );
    }
}
