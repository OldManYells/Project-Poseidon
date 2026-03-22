package com.legacyminecraft.poseidon.block;

import com.legacyminecraft.poseidon.PoseidonConfig;
import net.minecraft.server.Block;
import net.minecraft.server.TileEntity;
import net.minecraft.server.World;

/**
 * Canonical pushability rules for piston block wrappers.
 */
public final class PistonPushabilityBehaviour {
    private static final PistonPushabilityBehaviour INSTANCE = new PistonPushabilityBehaviour();

    private final PistonStateBehaviour pistonStateService = PistonStateBehaviour.getInstance();

    private PistonPushabilityBehaviour() {
    }

    public static PistonPushabilityBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean isAlwaysImmovableBlock(int blockId, boolean furnacePushFixEnabled) {
        if (blockId == Block.OBSIDIAN.id) {
            return true;
        }

        return (blockId == Block.FURNACE.id || blockId == Block.BURNING_FURNACE.id) && furnacePushFixEnabled;
    }

    public boolean canPushBlock(int blockId, World world, int x, int y, int z, boolean allowDestroyingBlocks) {
        boolean furnacePushFixEnabled = PoseidonConfig.getInstance().getBoolean("world.settings.block-pistons-pushing-furnaces.enabled", true);
        if (isAlwaysImmovableBlock(blockId, furnacePushFixEnabled)) {
            return false;
        }

        if (blockId != Block.PISTON.id && blockId != Block.PISTON_STICKY.id) {
            if (Block.byId[blockId].j() == -1.0F) {
                return false;
            }

            if (Block.byId[blockId].e() == 2) {
                return false;
            }

            if (!allowDestroyingBlocks && Block.byId[blockId].e() == 1) {
                return false;
            }
        } else if (pistonStateService.isExtended(world.getData(x, y, z))) {
            return false;
        }

        TileEntity tileEntity = world.getTileEntity(x, y, z);
        return tileEntity == null;
    }
}
