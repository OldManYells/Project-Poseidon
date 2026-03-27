package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.NetworkDisconnectReasonPolicy;
import org.junit.Assert;
import org.junit.Test;

public class NetworkDisconnectReasonPolicyTest {
    @Test
    public void exposesLegacyDisconnectReasonTexts() {
        NetworkDisconnectReasonPolicy policy = NetworkDisconnectReasonPolicy.getInstance();

        Assert.assertEquals("Connection closed", policy.connectionClosed());
        Assert.assertEquals("Internal exception: ", policy.internalExceptionPrefix());
    }
}
