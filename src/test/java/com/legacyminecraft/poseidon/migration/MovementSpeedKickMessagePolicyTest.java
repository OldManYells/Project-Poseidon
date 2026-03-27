package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.MovementSpeedKickMessagePolicy;
import org.junit.Assert;
import org.junit.Test;

public class MovementSpeedKickMessagePolicyTest {
    @Test
    public void exposesLegacySpeedKickMessage() {
        Assert.assertEquals(
                "You moved too quickly :( (Hacking?)",
                MovementSpeedKickMessagePolicy.getInstance().speedViolationKickMessage()
        );
    }
}
