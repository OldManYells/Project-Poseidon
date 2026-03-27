package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MovementPacketSentinelPolicyWiringTest {
    private static final Path PLAYER_MOVE_EVENT_POLICY_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/network/PlayerMoveEventPolicy.java");
    private static final Path PLAYER_MOVEMENT_PREPARATION_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/network/PlayerMovementPacketPreparationSystem.java");
    private static final Path VEHICLE_MOVEMENT_HANDLER_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/network/VehicleMovementPacketHandler.java");

    @Test
    public void movementSystemsUseSharedSentinelPolicy() throws IOException {
        String movePolicyText = new String(Files.readAllBytes(PLAYER_MOVE_EVENT_POLICY_PATH), StandardCharsets.UTF_8);
        String movementPrepText = new String(Files.readAllBytes(PLAYER_MOVEMENT_PREPARATION_PATH), StandardCharsets.UTF_8);
        String vehicleHandlerText = new String(Files.readAllBytes(VEHICLE_MOVEMENT_HANDLER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(movePolicyText.contains("MovementPacketSentinelPolicy"));
        Assert.assertTrue(movementPrepText.contains("MovementPacketSentinelPolicy"));
        Assert.assertTrue(vehicleHandlerText.contains("MovementPacketSentinelPolicy"));
        Assert.assertFalse(movePolicyText.contains("-999.0D"));
        Assert.assertFalse(movementPrepText.contains("-999.0D"));
        Assert.assertFalse(vehicleHandlerText.contains("-999.0D"));
    }
}
