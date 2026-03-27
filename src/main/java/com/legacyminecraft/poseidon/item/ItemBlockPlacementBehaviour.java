package com.legacyminecraft.poseidon.item;

import com.legacyminecraft.poseidon.PoseidonConfig;
import com.legacyminecraft.compat.bukkit.BlockPlaceEvent;
import com.legacyminecraft.compat.bukkit.BlockPlaceEventBridgeBehaviour;
import com.legacyminecraft.compat.bukkit.BlockState;
import com.legacyminecraft.poseidon.world.WorldFeatureConfigPolicy;

public final class ItemBlockPlacementBehaviour {
    private static final ItemBlockPlacementBehaviour INSTANCE = new ItemBlockPlacementBehaviour();
    private static final BlockPlaceEventBridgeBehaviour BLOCK_PLACE_EVENT_BRIDGE = BlockPlaceEventBridgeBehaviour.getInstance();
    private static final WorldFeatureConfigPolicy WORLD_FEATURE_CONFIG_POLICY = WorldFeatureConfigPolicy.getInstance();

    private ItemBlockPlacementBehaviour() {
    }

    public static ItemBlockPlacementBehaviour getInstance() {
        return INSTANCE;
    }

    public PlacementResult place(ItemStack itemstack,
                                 EntityHuman entityhuman, World world, int blockX, int blockY, int blockZ, int face, int blockId, int filteredData) {
        int clickedX = blockX;
        int clickedY = blockY;
        int clickedZ = blockZ;

        if (world.getTypeId(blockX, blockY, blockZ) == Block.SNOW.id) {
            face = 0;
        } else {
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
        }

        if (itemstack.count == 0) {
            return PlacementResult.notHandled();
        }
        if (blockY == 127 && Block.byId[blockId].material.isBuildable()) {
            return PlacementResult.notHandled();
        }
        if (!world.a(blockId, blockX, blockY, blockZ, false, face)) {
            return PlacementResult.notHandled();
        }

        Block block = Block.byId[blockId];
        BlockState replacedBlockState = BLOCK_PLACE_EVENT_BRIDGE.captureBlockState(world.toCompatWorldServer(), blockX, blockY, blockZ);
        BlockState blockStateBelow = null;
        boolean eventUseBlockBelow = false;

        if ((world.getTypeId(blockX, blockY - 1, blockZ) == Block.STEP.id || world.getTypeId(blockX, blockY - 1, blockZ) == Block.DOUBLE_STEP.id)
                && (itemstack.id == Block.DOUBLE_STEP.id || itemstack.id == Block.STEP.id)) {
            blockStateBelow = BLOCK_PLACE_EVENT_BRIDGE.captureBlockState(world.toCompatWorldServer(), blockX, blockY - 1, blockZ);
            eventUseBlockBelow = itemstack.id == Block.STEP.id && blockStateBelow.getTypeId() == Block.STEP.id;
        }

        if (!world.setRawTypeIdAndData(blockX, blockY, blockZ, blockId, filteredData)) {
            return PlacementResult.handledWithoutPlacement();
        }

        BlockPlaceEvent event = BLOCK_PLACE_EVENT_BRIDGE.callBlockPlaceEvent(
                world.toCompatWorldServer(),
                entityhuman,
                eventUseBlockBelow ? blockStateBelow : replacedBlockState,
                clickedX,
                clickedY,
                clickedZ,
                block.id
        );

        if (event.isCancelled() || !event.canBuild()) {
            if (blockStateBelow != null) {
                world.setTypeIdAndData(blockX, blockY, blockZ, replacedBlockState.getTypeId(), replacedBlockState.getRawData());
                world.setTypeIdAndData(blockX, blockY - 1, blockZ, blockStateBelow.getTypeId(), blockStateBelow.getRawData());
            } else {
                if (blockId == Block.ICE.id) {
                    world.setTypeId(blockX, blockY, blockZ, 20);
                }
                world.setTypeIdAndData(blockX, blockY, blockZ, replacedBlockState.getTypeId(), replacedBlockState.getRawData());
            }
            return PlacementResult.handledWithoutPlacement();
        }

        return PlacementResult.handledWithPlacement(blockX, blockY, blockZ, face, block);
    }

    public PlacementResult place(
            Object itemstack,
            Object entityhuman,
            Object world,
            int blockX,
            int blockY,
            int blockZ,
            int face,
            int blockId,
            int filteredData
    ) {
        if (itemstack instanceof ItemStack && entityhuman instanceof EntityHuman && world instanceof World) {
            return place((ItemStack) itemstack, (EntityHuman) entityhuman, (World) world, blockX, blockY, blockZ, face, blockId, filteredData);
        }
        return PlacementResult.notHandled();
    }

    public String resolveTranslationKey(int blockId) {
        if (blockId >= 0 && blockId < Block.byId.length && Block.byId[blockId] != null) {
            return Block.byId[blockId].l();
        }
        return "tile.unknown";
    }

    public boolean usePistonPostPlaceOrderingFix(int blockId) {
        return PoseidonConfig.getInstance().getConfigBoolean(
                WORLD_FEATURE_CONFIG_POLICY.pistonOtherFixesEnabledKey(),
                WORLD_FEATURE_CONFIG_POLICY.pistonOtherFixesEnabledDefault()
        )
                && (blockId == 29 || blockId == 33);
    }

    public static final class PlacementResult {
        public final boolean handled;
        public final boolean placed;
        public final int x;
        public final int y;
        public final int z;
        public final int face;
        public final Block block;

        private PlacementResult(boolean handled, boolean placed, int x, int y, int z, int face, Block block) {
            this.handled = handled;
            this.placed = placed;
            this.x = x;
            this.y = y;
            this.z = z;
            this.face = face;
            this.block = block;
        }

        public static PlacementResult notHandled() {
            return new PlacementResult(false, false, 0, 0, 0, 0, null);
        }

        public static PlacementResult handledWithoutPlacement() {
            return new PlacementResult(true, false, 0, 0, 0, 0, null);
        }

        public static PlacementResult handledWithPlacement(int x, int y, int z, int face, Block block) {
            return new PlacementResult(true, true, x, y, z, face, block);
        }
    }
}
