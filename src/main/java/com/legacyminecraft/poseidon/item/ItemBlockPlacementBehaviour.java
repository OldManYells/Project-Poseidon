package com.legacyminecraft.poseidon.item;

import com.legacyminecraft.poseidon.PoseidonConfig;
import com.legacyminecraft.poseidon.compat.bukkit.BlockPlaceEventBridgeBehaviour;
import net.minecraft.server.Block;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.ItemStack;
import net.minecraft.server.World;
import org.bukkit.block.BlockState;
import org.bukkit.event.block.BlockPlaceEvent;

public final class ItemBlockPlacementBehaviour {
    private static final ItemBlockPlacementBehaviour INSTANCE = new ItemBlockPlacementBehaviour();
    private static final BlockPlaceEventBridgeBehaviour BLOCK_PLACE_EVENT_BRIDGE = BlockPlaceEventBridgeBehaviour.getInstance();

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
        BlockState replacedBlockState = BLOCK_PLACE_EVENT_BRIDGE.captureBlockState(world, blockX, blockY, blockZ);
        BlockState blockStateBelow = null;
        boolean eventUseBlockBelow = false;

        if ((world.getTypeId(blockX, blockY - 1, blockZ) == Block.STEP.id || world.getTypeId(blockX, blockY - 1, blockZ) == Block.DOUBLE_STEP.id)
                && (itemstack.id == Block.DOUBLE_STEP.id || itemstack.id == Block.STEP.id)) {
            blockStateBelow = BLOCK_PLACE_EVENT_BRIDGE.captureBlockState(world, blockX, blockY - 1, blockZ);
            eventUseBlockBelow = itemstack.id == Block.STEP.id && blockStateBelow.getTypeId() == Block.STEP.id;
        }

        if (!world.setRawTypeIdAndData(blockX, blockY, blockZ, blockId, filteredData)) {
            return PlacementResult.handledWithoutPlacement();
        }

        BlockPlaceEvent event = BLOCK_PLACE_EVENT_BRIDGE.callBlockPlaceEvent(
                world,
                entityhuman,
                eventUseBlockBelow ? blockStateBelow : replacedBlockState,
                clickedX,
                clickedY,
                clickedZ,
                block
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

    public String resolveTranslationKey(int blockId) {
        return Block.byId[blockId].l();
    }

    public boolean usePistonPostPlaceOrderingFix(int blockId) {
        return PoseidonConfig.getInstance().getConfigBoolean("world.settings.pistons.other-fixes.enabled", true)
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
