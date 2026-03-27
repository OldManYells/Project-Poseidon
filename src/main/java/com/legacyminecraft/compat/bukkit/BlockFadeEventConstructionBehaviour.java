package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftEventFactory block-fade event preparation and construction.
 */
public final class BlockFadeEventConstructionBehaviour {
    private static final BlockFadeEventConstructionBehaviour INSTANCE = new BlockFadeEventConstructionBehaviour();

    private BlockFadeEventConstructionBehaviour() {
    }

    public static BlockFadeEventConstructionBehaviour getInstance() {
        return INSTANCE;
    }

    public BlockFadeEvent createBlockFadeEvent(Block block, int targetTypeId) {
        BlockState state = block.getState();
        state.setTypeId(targetTypeId);
        return new BlockFadeEvent(block, state);
    }
}
