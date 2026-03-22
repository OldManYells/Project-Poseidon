package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.BlockInteractionPacketHandler;
import com.legacyminecraft.poseidon.block.BlockInteractionSessionState;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Packet15Place;
import org.bukkit.Location;
import org.junit.Assert;
import org.junit.Test;

public class BlockInteractionPacketServiceTest {
    @Test
    public void digDistanceCheckMatchesLegacyStatuses() {
        BlockInteractionPacketHandler service = BlockInteractionPacketHandler.getInstance();

        Assert.assertTrue(service.requiresDigDistanceCheck(0));
        Assert.assertTrue(service.requiresDigDistanceCheck(2));
        Assert.assertFalse(service.requiresDigDistanceCheck(1));
        Assert.assertFalse(service.requiresDigDistanceCheck(3));
        Assert.assertFalse(service.requiresDigDistanceCheck(4));
    }

    @Test
    public void duplicateRightClickPacketGuardUsesMaterialAndTimeWindow() {
        BlockInteractionPacketHandler service = BlockInteractionPacketHandler.getInstance();
        BlockInteractionSessionState state = new BlockInteractionSessionState(0, 0, System.currentTimeMillis(), 5);

        Packet15Place packet15place = new Packet15Place();
        packet15place.itemstack = new ItemStack(5, 1, 0);

        Assert.assertTrue(service.isDuplicateRightClickPacket(packet15place, state));

        state.setLastMaterial(6);
        Assert.assertFalse(service.isDuplicateRightClickPacket(packet15place, state));
    }

    @Test
    public void placeDistanceCheckUsesSquaredDistanceThreshold() {
        BlockInteractionPacketHandler service = BlockInteractionPacketHandler.getInstance();
        Location origin = new Location(null, 0, 0, 0);

        Assert.assertTrue(service.isWithinPlaceDistanceSquared(origin, 2, 1, 1, 6 * 6));
        Assert.assertFalse(service.isWithinPlaceDistanceSquared(origin, 10, 0, 0, 6 * 6));
    }
}
