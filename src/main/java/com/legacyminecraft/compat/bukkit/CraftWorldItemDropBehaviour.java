package com.legacyminecraft.compat.bukkit;


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

    public com.legacyminecraft.compat.bukkit.ItemStack toNativeItemStack(ItemStack itemStack) {
        return new com.legacyminecraft.compat.bukkit.ItemStack(
                itemStack.getTypeId(),
                itemStack.getAmount(),
                itemStack.getDurability()
        );
    }

    public Item dropItem(com.legacyminecraft.compat.bukkit.WorldServer world, CraftServer server, Location location, ItemStack itemStack) {
        com.legacyminecraft.compat.bukkit.ItemStack nativeStack = this.toNativeItemStack(itemStack);
        com.legacyminecraft.compat.bukkit.EntityItem entityItem = new com.legacyminecraft.compat.bukkit.EntityItem(
                world,
                location.getX(),
                location.getY(),
                location.getZ(),
                nativeStack
        );
        entityItem.pickupDelay = 10;
        world.addEntity(entityItem);
        // TODO this is inconsistent with how Entity.getBukkitEntity() works.
        // However, this entity is not at the moment backed by a server entity class so it may be left.
        return new CraftItem(server, entityItem);
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
