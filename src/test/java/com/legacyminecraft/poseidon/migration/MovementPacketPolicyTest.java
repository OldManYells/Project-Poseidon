package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.MovementPacketPolicy;
import net.minecraft.server.Packet10Flying;
import org.bukkit.entity.Player;
import org.junit.Assert;
import org.junit.Test;

public class MovementPacketPolicyTest {
    @Test
    public void reEnableMovementCheckRequiresSmallDeltaAtKnownPosition() {
        MovementPacketPolicy policy = MovementPacketPolicy.getInstance();

        Assert.assertTrue(policy.shouldReEnableMovementCheck(false, 10.0D, 5.01D, -2.0D, 10.0D, 5.0D, -2.0D));
        Assert.assertFalse(policy.shouldReEnableMovementCheck(true, 10.0D, 5.0D, -2.0D, 10.0D, 5.0D, -2.0D));
    }

    @Test
    public void stanceAndPositionValidationUseLegacyLimits() {
        MovementPacketPolicy policy = MovementPacketPolicy.getInstance();

        Assert.assertTrue(policy.isIllegalStance(false, 2.0D));
        Assert.assertFalse(policy.isIllegalStance(true, 2.0D));
        Assert.assertTrue(policy.isIllegalHorizontalPosition(3.3E7D, 0.0D));
        Assert.assertFalse(policy.isIllegalHorizontalPosition(3.0E7D, 3.0E7D));
    }

    @Test
    public void speedAndFloatingChecksUseLegacyThresholds() {
        MovementPacketPolicy policy = MovementPacketPolicy.getInstance();

        Assert.assertTrue(policy.shouldTeleportOrKickForSpeed(true, true, 150.0D, 10.0D, 100.0D));
        Assert.assertFalse(policy.shouldTeleportOrKickForSpeed(false, true, 150.0D, 10.0D, 100.0D));
        Assert.assertEquals(0.0D, policy.normalizeVerticalDeltaAfterMove(0.25D), 0.0D);
        Assert.assertTrue(policy.shouldIncrementFloatingCounter(false, false, 0.0D));
        Assert.assertTrue(policy.shouldKickForFloatingTicks(81));
    }

    @Test
    public void movedWronglyAndTeleportBackPoliciesMatchLegacyPredicates() {
        MovementPacketPolicy policy = MovementPacketPolicy.getInstance();

        Assert.assertTrue(policy.shouldMarkMovedWrongly(0.1D, false));
        Assert.assertFalse(policy.shouldMarkMovedWrongly(0.01D, false));
        Assert.assertTrue(policy.shouldTeleportToLastGoodPosition(true, true, true, false));
        Assert.assertFalse(policy.shouldTeleportToLastGoodPosition(false, true, true, false));
    }

    @Test
    public void vehicleCrashCheckUsesSquaredMagnitude() {
        MovementPacketPolicy policy = MovementPacketPolicy.getInstance();

        Assert.assertTrue(policy.isVehicleCrashAttempt(11.0D, 0.0D));
        Assert.assertFalse(policy.isVehicleCrashAttempt(5.0D, 5.0D));
    }

    @Test
    public void invalidNumericPositionCheckEvaluatesNaNFields() {
        MovementPacketPolicy policy = MovementPacketPolicy.getInstance();
        Packet10Flying packet = new Packet10Flying();
        packet.x = Double.NaN;

        Assert.assertTrue(policy.hasInvalidNumericPosition(packet, (Player) null, false));
    }
}
