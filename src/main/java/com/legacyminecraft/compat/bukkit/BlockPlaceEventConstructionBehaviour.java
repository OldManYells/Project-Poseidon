package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftEventFactory block-place event construction.
 */
public final class BlockPlaceEventConstructionBehaviour {
    private static final BlockPlaceEventConstructionBehaviour INSTANCE = new BlockPlaceEventConstructionBehaviour();

    private BlockPlaceEventConstructionBehaviour() {
    }

    public static BlockPlaceEventConstructionBehaviour getInstance() {
        return INSTANCE;
    }

    public BlockPlaceEvent createBlockPlaceEvent(Block placedBlock, BlockState replacedBlockState, Block clickedBlock,
                                                 CraftItemStack itemInHand, Player player, boolean canBuild) {
        return new BlockPlaceEvent(placedBlock, replacedBlockState, clickedBlock, itemInHand, player, canBuild);
    }
}
