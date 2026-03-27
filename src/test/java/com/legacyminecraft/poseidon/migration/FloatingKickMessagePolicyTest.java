package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.FloatingKickMessagePolicy;
import org.junit.Assert;
import org.junit.Test;

public class FloatingKickMessagePolicyTest {
    @Test
    public void exposesLegacyFloatingKickReason() {
        Assert.assertEquals(
                "Flying is not enabled on this server",
                FloatingKickMessagePolicy.getInstance().floatingKickReason()
        );
    }
}
