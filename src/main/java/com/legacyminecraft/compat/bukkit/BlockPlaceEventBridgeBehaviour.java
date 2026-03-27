package com.legacyminecraft.compat.bukkit;


/**
 * Canonical Bukkit bridge behaviour for block-place event hooks.
 */
public final class BlockPlaceEventBridgeBehaviour {
    private static final BlockPlaceEventBridgeBehaviour INSTANCE = new BlockPlaceEventBridgeBehaviour();
    private static final EventFactoryInteractionSystem EVENT_FACTORY_INTERACTION_SYSTEM =
            EventFactoryInteractionSystem.getInstance();

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
        return EVENT_FACTORY_INTERACTION_SYSTEM.callBlockPlaceEvent(
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
        return EVENT_FACTORY_INTERACTION_SYSTEM.callBlockPlaceEvent(
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
