package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.NetworkDisconnectArgumentPolicy;
import org.junit.Assert;
import org.junit.Test;

public class NetworkDisconnectArgumentPolicyTest {
    @Test
    public void returnsCanonicalDisconnectArguments() {
        NetworkDisconnectArgumentPolicy policy = NetworkDisconnectArgumentPolicy.getInstance();

        Assert.assertEquals(0, policy.emptyArgs().length);
        Object[] reasonArgs = policy.genericReasonArgs("Connection closed");
        Assert.assertEquals(1, reasonArgs.length);
        Assert.assertEquals("Connection closed", reasonArgs[0]);
    }
}
