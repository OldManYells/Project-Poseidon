package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.PlayerMoveEventPolicy;
import org.bukkit.Location;
import org.junit.Assert;
import org.junit.Test;

public class PlayerMoveEventPolicyTest {
    @Test
    public void packetPositionApplicabilityMatchesSentinelValues() {
        PlayerMoveEventPolicy policy = PlayerMoveEventPolicy.getInstance();

        Assert.assertTrue(policy.shouldApplyPositionFromPacket(true, 5.0D, 6.0D));
        Assert.assertFalse(policy.shouldApplyPositionFromPacket(true, -999.0D, -999.0D));
        Assert.assertFalse(policy.shouldApplyPositionFromPacket(false, 5.0D, 6.0D));
    }

    @Test
    public void moveEventDeltaThresholdMatchesLegacyLogic() {
        PlayerMoveEventPolicy policy = PlayerMoveEventPolicy.getInstance();
        Location to = new Location(null, 1.0D, 0.0D, 0.0D, 0.0F, 0.0F);

        Assert.assertTrue(policy.hasSignificantMoveEventDelta(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, to));
        Assert.assertFalse(policy.hasSignificantMoveEventDelta(1.0D, 0.0D, 0.0D, 0.0F, 0.0F, to));
    }

    @Test
    public void processAndTeleportAbortPredicatesFollowLegacyChecks() {
        PlayerMoveEventPolicy policy = PlayerMoveEventPolicy.getInstance();
        Location sentinelFrom = new Location(null, Double.MAX_VALUE, 0.0D, 0.0D);
        Location from = new Location(null, 0.0D, 0.0D, 0.0D);
        Location current = new Location(null, 1.0D, 0.0D, 0.0D);

        Assert.assertFalse(policy.hasInitializedMoveFromLocation(sentinelFrom));
        Assert.assertTrue(policy.hasInitializedMoveFromLocation(from));
        Assert.assertTrue(policy.shouldProcessMoveEvent(true, true, false));
        Assert.assertFalse(policy.shouldProcessMoveEvent(true, false, false));
        Assert.assertTrue(policy.shouldAbortAfterPluginTeleport(from, current, true));
    }
}
