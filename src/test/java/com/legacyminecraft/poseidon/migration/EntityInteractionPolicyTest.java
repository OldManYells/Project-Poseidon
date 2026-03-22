package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.entity.EntityInteractionMode;
import com.legacyminecraft.poseidon.entity.EntityInteractionPolicy;
import org.junit.Assert;
import org.junit.Test;

public class EntityInteractionPolicyTest {
    @Test
    public void actionCodesMapToCanonicalInteractionModes() {
        EntityInteractionPolicy policy = EntityInteractionPolicy.getInstance();

        Assert.assertEquals(EntityInteractionMode.INTERACT, policy.resolveInteractionMode(0));
        Assert.assertEquals(EntityInteractionMode.ATTACK, policy.resolveInteractionMode(1));
        Assert.assertEquals(EntityInteractionMode.UNKNOWN, policy.resolveInteractionMode(99));
    }

    @Test
    public void storageMinecartGuardRemainsVehicleAndMinecartOnly() {
        EntityInteractionPolicy policy = EntityInteractionPolicy.getInstance();

        Assert.assertTrue(policy.shouldCancelStorageMinecartInteraction(true, true));
        Assert.assertFalse(policy.shouldCancelStorageMinecartInteraction(true, false));
        Assert.assertFalse(policy.shouldCancelStorageMinecartInteraction(false, true));
    }
}
