package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.entity.PlayerActionPacketHandler;
import org.junit.Assert;
import org.junit.Test;

public class PlayerActionPacketServiceTest {
    @Test
    public void movementCheckOnlyDisabledForLeaveBedAnimation() {
        PlayerActionPacketHandler service = PlayerActionPacketHandler.getInstance();

        Assert.assertTrue(service.shouldDisableMovementCheck(3));
        Assert.assertFalse(service.shouldDisableMovementCheck(1));
        Assert.assertFalse(service.shouldDisableMovementCheck(2));
        Assert.assertFalse(service.shouldDisableMovementCheck(0));
    }
}
