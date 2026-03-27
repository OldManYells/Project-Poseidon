package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.GroundCollisionMarginPolicy;
import org.junit.Assert;
import org.junit.Test;

public class GroundCollisionMarginPolicyTest {
    @Test
    public void exposesLegacyCollisionMargin() {
        Assert.assertEquals(0.0625F, GroundCollisionMarginPolicy.getInstance().collisionMargin(), 0.0F);
    }
}
