package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.entity.EntityTrackingVisibilityPolicy;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class EntityTrackingVisibilityPolicyTest {
    @Test
    public void rangeCheckMatchesTrackerBounds() {
        EntityTrackingVisibilityPolicy policy = EntityTrackingVisibilityPolicy.getInstance();

        Assert.assertTrue(policy.isWithinTrackingRange(0.0D, 0.0D, 32));
        Assert.assertTrue(policy.isWithinTrackingRange(-32.0D, 32.0D, 32));
        Assert.assertFalse(policy.isWithinTrackingRange(32.1D, 0.0D, 32));
        Assert.assertFalse(policy.isWithinTrackingRange(0.0D, -33.0D, 32));
    }

    @Test
    public void startAndStopTrackingPredicatesReflectSetMembership() {
        EntityTrackingVisibilityPolicy policy = EntityTrackingVisibilityPolicy.getInstance();
        Set trackedPlayers = new HashSet();

        Assert.assertTrue(policy.shouldStartTracking(trackedPlayers, null, true, true));
        trackedPlayers.add(null);
        Assert.assertFalse(policy.shouldStartTracking(trackedPlayers, null, true, true));
        Assert.assertFalse(policy.shouldStopTracking(trackedPlayers, null, true));
        Assert.assertTrue(policy.shouldStopTracking(trackedPlayers, null, false));
    }
}
