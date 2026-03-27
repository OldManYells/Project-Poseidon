package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.BlockInteractionDistancePolicy;
import org.junit.Assert;
import org.junit.Test;

public class BlockInteractionDistancePolicyTest {
    @Test
    public void exposesPlaceDistanceSquared() {
        BlockInteractionDistancePolicy policy = BlockInteractionDistancePolicy.getInstance();

        Assert.assertEquals(36, policy.placeDistanceSquared());
    }
}
