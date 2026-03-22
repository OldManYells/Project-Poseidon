package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.Block;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.World;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * Canonical Bukkit bridge behaviour for block-place event hooks.
 */
public final class BlockPlaceEventBridgeBehaviour {
    private static final BlockPlaceEventBridgeBehaviour INSTANCE = new BlockPlaceEventBridgeBehaviour();

    private BlockPlaceEventBridgeBehaviour() {
    }

    public static BlockPlaceEventBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public BlockState captureBlockState(World world, int x, int y, int z) {
        return world.getWorld().getBlockAt(x, y, z).getState();
    }

    public BlockPlaceEvent callBlockPlaceEvent(
            World world,
            EntityHuman player,
            BlockState replacedBlockState,
            int clickedX,
            int clickedY,
            int clickedZ,
            Block blockInHand
    ) {
        return CraftEventFactory.callBlockPlaceEvent(
                world,
                player,
                replacedBlockState,
                clickedX,
                clickedY,
                clickedZ,
                blockInHand
        );
    }

    public BlockPlaceEvent callBlockPlaceEvent(
            World world,
            EntityHuman player,
            BlockState replacedBlockState,
            int clickedX,
            int clickedY,
            int clickedZ,
            int itemTypeId
    ) {
        return CraftEventFactory.callBlockPlaceEvent(
                world,
                player,
                replacedBlockState,
                clickedX,
                clickedY,
                clickedZ,
                itemTypeId
        );
    }
}
