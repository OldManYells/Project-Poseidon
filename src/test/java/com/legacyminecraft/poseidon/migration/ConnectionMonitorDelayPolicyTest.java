package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.ConnectionMonitorDelayPolicy;
import org.junit.Assert;
import org.junit.Test;

public class ConnectionMonitorDelayPolicyTest {
    @Test
    public void exposesLegacyMonitorDelay() {
        Assert.assertEquals(2000L, ConnectionMonitorDelayPolicy.getInstance().watchdogDelayMillis());
    }
}
