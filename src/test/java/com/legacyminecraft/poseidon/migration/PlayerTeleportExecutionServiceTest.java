package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.PlayerTeleportExecutionSystem;
import net.minecraft.server.Packet13PlayerLookMove;
import org.bukkit.Location;
import org.junit.Assert;
import org.junit.Test;

public class PlayerTeleportExecutionServiceTest {
    @Test
    public void executionPlanSanitizesRotationAndCapturesLegacyStateFlags() {
        PlayerTeleportExecutionSystem service = PlayerTeleportExecutionSystem.getInstance();
        Location destination = new Location(null, 5.0D, 6.0D, 7.0D, Float.NaN, Float.NaN);

        PlayerTeleportExecutionSystem.TeleportExecutionPlan plan = service.createExecutionPlan(destination);

        Assert.assertEquals(5.0D, plan.getX(), 0.0D);
        Assert.assertEquals(6.0D, plan.getY(), 0.0D);
        Assert.assertEquals(7.0D, plan.getZ(), 0.0D);
        Assert.assertEquals(0.0F, plan.getYaw(), 0.0F);
        Assert.assertEquals(0.0F, plan.getPitch(), 0.0F);
        Assert.assertTrue(plan.isJustTeleported());
        Assert.assertFalse(plan.isMovementCheckEnabled());
    }

    @Test
    public void lookMovePacketUsesLegacyEyeHeightOffset() {
        PlayerTeleportExecutionSystem service = PlayerTeleportExecutionSystem.getInstance();

        Packet13PlayerLookMove packet = service.createLookMovePacket(10.0D, 20.0D, 30.0D, 40.0F, 50.0F);
        Assert.assertEquals(10.0D, packet.x, 0.0D);
        Assert.assertEquals(21.6200000047683716D, packet.y, 0.0D);
        Assert.assertEquals(20.0D, packet.stance, 0.0D);
        Assert.assertEquals(30.0D, packet.z, 0.0D);
        Assert.assertEquals(40.0F, packet.yaw, 0.0F);
        Assert.assertEquals(50.0F, packet.pitch, 0.0F);
        Assert.assertFalse(packet.g);
    }
}
