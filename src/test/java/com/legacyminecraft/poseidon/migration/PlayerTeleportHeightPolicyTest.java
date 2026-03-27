package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.PlayerTeleportHeightPolicy;
import org.junit.Assert;
import org.junit.Test;

public class PlayerTeleportHeightPolicyTest {
    @Test
    public void exposesLegacyEyeHeightOffset() {
        Assert.assertEquals(1.6200000047683716D, PlayerTeleportHeightPolicy.getInstance().legacyEyeHeightOffset(), 0.0D);
    }
}
