package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.PlayerMovementPacketPreparationSystem;
import org.junit.Assert;
import org.junit.Test;

public class PlayerMovementPacketPreparationServiceTest {
    @Test
    public void sentinelPacketKeepsCurrentPosition() {
        PlayerMovementPacketPreparationSystem service = PlayerMovementPacketPreparationSystem.getInstance();

        PlayerMovementPacketPreparationSystem.MovementPreparationResult result = service.prepare(
                false,
                10.0D,
                20.0D,
                30.0D,
                40.0F,
                50.0F,
                true,
                100.0D,
                -999.0D,
                200.0D,
                -999.0D,
                false,
                0.0F,
                0.0F
        );

        Assert.assertTrue(result.isValid());
        Assert.assertEquals(10.0D, result.getTargetX(), 0.0D);
        Assert.assertEquals(20.0D, result.getTargetY(), 0.0D);
        Assert.assertEquals(30.0D, result.getTargetZ(), 0.0D);
    }

    @Test
    public void illegalStanceProducesDisconnectReason() {
        PlayerMovementPacketPreparationSystem service = PlayerMovementPacketPreparationSystem.getInstance();

        PlayerMovementPacketPreparationSystem.MovementPreparationResult result = service.prepare(
                false,
                0.0D,
                0.0D,
                0.0D,
                0.0F,
                0.0F,
                true,
                0.0D,
                1.0D,
                0.0D,
                5.0D,
                false,
                0.0F,
                0.0F
        );

        Assert.assertFalse(result.isValid());
        Assert.assertTrue(result.isIllegalStance());
        Assert.assertEquals("Illegal stance", result.getDisconnectReason());
    }

    @Test
    public void illegalHorizontalPositionProducesDisconnectReason() {
        PlayerMovementPacketPreparationSystem service = PlayerMovementPacketPreparationSystem.getInstance();

        PlayerMovementPacketPreparationSystem.MovementPreparationResult result = service.prepare(
                false,
                0.0D,
                0.0D,
                0.0D,
                0.0F,
                0.0F,
                true,
                3.3E7D,
                1.0D,
                0.0D,
                2.0D,
                false,
                0.0F,
                0.0F
        );

        Assert.assertFalse(result.isValid());
        Assert.assertFalse(result.isIllegalStance());
        Assert.assertEquals("Illegal position", result.getDisconnectReason());
    }

    @Test
    public void lookPacketOverridesYawAndPitch() {
        PlayerMovementPacketPreparationSystem service = PlayerMovementPacketPreparationSystem.getInstance();

        PlayerMovementPacketPreparationSystem.MovementPreparationResult result = service.prepare(
                false,
                0.0D,
                0.0D,
                0.0D,
                10.0F,
                20.0F,
                false,
                0.0D,
                0.0D,
                0.0D,
                0.0D,
                true,
                90.0F,
                45.0F
        );

        Assert.assertTrue(result.isValid());
        Assert.assertEquals(90.0F, result.getTargetYaw(), 0.0F);
        Assert.assertEquals(45.0F, result.getTargetPitch(), 0.0F);
    }
}
