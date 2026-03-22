package com.legacyminecraft.poseidon.compat.bukkit;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

/**
 * Canonical behavior for CraftWorld item-drop conversion and natural spawn offset policy.
 */
public final class CraftWorldItemDropBehaviour {
    private static final CraftWorldItemDropBehaviour INSTANCE = new CraftWorldItemDropBehaviour();

    private CraftWorldItemDropBehaviour() {
    }

    public static CraftWorldItemDropBehaviour getInstance() {
        return INSTANCE;
    }

    public net.minecraft.server.ItemStack toNativeItemStack(ItemStack itemStack) {
        return new net.minecraft.server.ItemStack(
                itemStack.getTypeId(),
                itemStack.getAmount(),
                itemStack.getDurability()
        );
    }

    public Location toNaturalDropLocation(Location sourceLocation, Random random) {
        double xOffset = random.nextFloat() * 0.7F + (1.0F - 0.7F) * 0.5D;
        double yOffset = random.nextFloat() * 0.7F + (1.0F - 0.7F) * 0.5D;
        double zOffset = random.nextFloat() * 0.7F + (1.0F - 0.7F) * 0.5D;

        Location dropLocation = sourceLocation.clone();
        dropLocation.setX(dropLocation.getX() + xOffset);
        dropLocation.setY(dropLocation.getY() + yOffset);
        dropLocation.setZ(dropLocation.getZ() + zOffset);
        return dropLocation;
    }
}
