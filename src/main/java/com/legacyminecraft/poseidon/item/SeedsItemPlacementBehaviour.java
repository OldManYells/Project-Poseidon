package com.legacyminecraft.poseidon.item;

import com.legacyminecraft.poseidon.compat.bukkit.BlockPlaceEventBridgeBehaviour;
import net.minecraft.server.Block;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.ItemStack;
import net.minecraft.server.World;
import org.bukkit.block.BlockState;
import org.bukkit.event.block.BlockPlaceEvent;

public final class SeedsItemPlacementBehaviour {
    private static final SeedsItemPlacementBehaviour INSTANCE = new SeedsItemPlacementBehaviour();
    private static final BlockPlaceEventBridgeBehaviour BLOCK_PLACE_EVENT_BRIDGE = BlockPlaceEventBridgeBehaviour.getInstance();

    private SeedsItemPlacementBehaviour() {
    }

    public static SeedsItemPlacementBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean place(ItemStack itemstack, EntityHuman entityhuman, World world, int x, int y, int z, int face, int cropBlockId) {
        if (face != 1) {
            return false;
        }

        int blockId = world.getTypeId(x, y, z);
        if (blockId == Block.SOIL.id && world.isEmpty(x, y + 1, z)) {
            BlockState blockState = BLOCK_PLACE_EVENT_BRIDGE.captureBlockState(world, x, y + 1, z);
            world.setTypeId(x, y + 1, z, cropBlockId);
            BlockPlaceEvent event = BLOCK_PLACE_EVENT_BRIDGE.callBlockPlaceEvent(world, entityhuman, blockState, x, y, z, cropBlockId);
            if (event.isCancelled() || !event.canBuild()) {
                event.getBlockPlaced().setTypeId(0);
                return false;
            }
            --itemstack.count;
            return true;
        }

        return false;
    }
}
