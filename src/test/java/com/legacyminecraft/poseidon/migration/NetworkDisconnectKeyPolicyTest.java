package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.NetworkDisconnectKeyPolicy;
import org.junit.Assert;
import org.junit.Test;

public class NetworkDisconnectKeyPolicyTest {
    @Test
    public void exposesLegacyDisconnectKeys() {
        NetworkDisconnectKeyPolicy policy = NetworkDisconnectKeyPolicy.getInstance();

        Assert.assertEquals("disconnect.overflow", policy.overflow());
        Assert.assertEquals("disconnect.timeout", policy.timeout());
        Assert.assertEquals("disconnect.spam", policy.spam());
        Assert.assertEquals("disconnect.quitting", policy.quitting());
        Assert.assertEquals("disconnect.closed", policy.closed());
        Assert.assertEquals("disconnect.endOfStream", policy.endOfStream());
        Assert.assertEquals("disconnect.genericReason", policy.genericReason());
    }
}
