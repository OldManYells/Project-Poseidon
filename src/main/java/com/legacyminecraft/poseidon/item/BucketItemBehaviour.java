package com.legacyminecraft.poseidon.item;

import com.legacyminecraft.poseidon.compat.bukkit.BucketEventBridgeBehaviour;
import net.minecraft.server.Block;
import net.minecraft.server.EntityCow;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EnumMovingObjectType;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Material;
import net.minecraft.server.MovingObjectPosition;
import net.minecraft.server.World;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;

public final class BucketItemBehaviour {
    private static final BucketItemBehaviour INSTANCE = new BucketItemBehaviour();
    private static final ItemUseRayTraceBehaviour ITEM_USE_RAYTRACE_BEHAVIOUR = ItemUseRayTraceBehaviour.getInstance();
    private static final BucketEventBridgeBehaviour BUCKET_EVENT_BRIDGE = BucketEventBridgeBehaviour.getInstance();

    private BucketItemBehaviour() {
    }

    public static BucketItemBehaviour getInstance() {
        return INSTANCE;
    }

    public ItemStack use(ItemStack itemstack, World world, EntityHuman entityhuman, int bucketContentId) {
        MovingObjectPosition movingobjectposition = ITEM_USE_RAYTRACE_BEHAVIOUR.rayTraceFromPlayer(world, entityhuman, bucketContentId == 0);
        if (movingobjectposition == null) {
            return itemstack;
        }

        if (movingobjectposition.type == EnumMovingObjectType.TILE) {
            return useOnTile(itemstack, world, entityhuman, movingobjectposition, bucketContentId);
        }

        if (bucketContentId == 0 && movingobjectposition.entity instanceof EntityCow) {
            Location loc = movingobjectposition.entity.getBukkitEntity().getLocation();
            PlayerBucketFillEvent event = BUCKET_EVENT_BRIDGE.callPlayerBucketFillEvent(
                    entityhuman,
                    loc.getBlockX(),
                    loc.getBlockY(),
                    loc.getBlockZ(),
                    -1,
                    itemstack,
                    Item.MILK_BUCKET
            );
            if (event.isCancelled()) {
                return itemstack;
            }
            return BUCKET_EVENT_BRIDGE.toNmsItemStack(event.getItemStack());
        }

        return itemstack;
    }

    private ItemStack useOnTile(ItemStack itemstack, World world, EntityHuman entityhuman, MovingObjectPosition movingobjectposition, int bucketContentId) {
        int tileX = movingobjectposition.b;
        int tileY = movingobjectposition.c;
        int tileZ = movingobjectposition.d;

        if (!world.a(entityhuman, tileX, tileY, tileZ)) {
            return itemstack;
        }

        if (bucketContentId == 0) {
            return tryFill(itemstack, world, entityhuman, tileX, tileY, tileZ);
        }

        return tryEmpty(itemstack, world, entityhuman, tileX, tileY, tileZ, movingobjectposition.face, bucketContentId);
    }

    private ItemStack tryFill(ItemStack itemstack, World world, EntityHuman entityhuman, int tileX, int tileY, int tileZ) {
        if (world.getMaterial(tileX, tileY, tileZ) == Material.WATER && world.getData(tileX, tileY, tileZ) == 0) {
            PlayerBucketFillEvent event = BUCKET_EVENT_BRIDGE.callPlayerBucketFillEvent(
                    entityhuman,
                    tileX,
                    tileY,
                    tileZ,
                    -1,
                    itemstack,
                    Item.WATER_BUCKET
            );
            if (event.isCancelled()) {
                return itemstack;
            }
            world.setTypeId(tileX, tileY, tileZ, 0);
            return BUCKET_EVENT_BRIDGE.toNmsItemStack(event.getItemStack());
        }

        if (world.getMaterial(tileX, tileY, tileZ) == Material.LAVA && world.getData(tileX, tileY, tileZ) == 0) {
            PlayerBucketFillEvent event = BUCKET_EVENT_BRIDGE.callPlayerBucketFillEvent(
                    entityhuman,
                    tileX,
                    tileY,
                    tileZ,
                    -1,
                    itemstack,
                    Item.LAVA_BUCKET
            );
            if (event.isCancelled()) {
                return itemstack;
            }
            world.setTypeId(tileX, tileY, tileZ, 0);
            return BUCKET_EVENT_BRIDGE.toNmsItemStack(event.getItemStack());
        }

        return itemstack;
    }

    private ItemStack tryEmpty(ItemStack itemstack, World world, EntityHuman entityhuman, int blockX, int blockY, int blockZ, int face, int bucketContentId) {
        if (bucketContentId < 0) {
            PlayerBucketEmptyEvent event = BUCKET_EVENT_BRIDGE.callPlayerBucketEmptyEvent(
                    entityhuman,
                    blockX,
                    blockY,
                    blockZ,
                    face,
                    itemstack
            );
            if (event.isCancelled()) {
                return itemstack;
            }
            return BUCKET_EVENT_BRIDGE.toNmsItemStack(event.getItemStack());
        }

        int clickedX = blockX;
        int clickedY = blockY;
        int clickedZ = blockZ;

        if (face == 0) {
            --blockY;
        }
        if (face == 1) {
            ++blockY;
        }
        if (face == 2) {
            --blockZ;
        }
        if (face == 3) {
            ++blockZ;
        }
        if (face == 4) {
            --blockX;
        }
        if (face == 5) {
            ++blockX;
        }

        if (world.isEmpty(blockX, blockY, blockZ) || !world.getMaterial(blockX, blockY, blockZ).isBuildable()) {
            PlayerBucketEmptyEvent event = BUCKET_EVENT_BRIDGE.callPlayerBucketEmptyEvent(
                    entityhuman,
                    clickedX,
                    clickedY,
                    clickedZ,
                    face,
                    itemstack
            );
            if (event.isCancelled()) {
                return itemstack;
            }

            if (world.worldProvider.d && bucketContentId == Block.WATER.id) {
                world.makeSound(entityhuman.locX + 0.5D, entityhuman.locY + 0.5D, entityhuman.locZ + 0.5D, "random.fizz", 0.5F, 2.6F + (world.random.nextFloat() - world.random.nextFloat()) * 0.8F);
                for (int smokeCount = 0; smokeCount < 8; ++smokeCount) {
                    world.a(
                            "largesmoke",
                            (double) blockX + Math.random(),
                            (double) blockY + Math.random(),
                            (double) blockZ + Math.random(),
                            0.0D,
                            0.0D,
                            0.0D
                    );
                }
            } else {
                world.setTypeIdAndData(blockX, blockY, blockZ, bucketContentId, 0);
            }

            return BUCKET_EVENT_BRIDGE.toNmsItemStack(event.getItemStack());
        }

        return itemstack;
    }
}
