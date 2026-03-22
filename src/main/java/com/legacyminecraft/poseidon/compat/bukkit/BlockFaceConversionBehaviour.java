package com.legacyminecraft.poseidon.compat.bukkit;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

/**
 * Canonical behaviour for CraftBlock notch/block-face conversion and adjacency face resolution.
 */
public final class BlockFaceConversionBehaviour {
    private static final BlockFaceConversionBehaviour INSTANCE = new BlockFaceConversionBehaviour();

    private BlockFaceConversionBehaviour() {
    }

    public static BlockFaceConversionBehaviour getInstance() {
        return INSTANCE;
    }

    public BlockFace notchToBlockFace(int notchFace) {
        switch (notchFace) {
            case 0:
                return BlockFace.DOWN;
            case 1:
                return BlockFace.UP;
            case 2:
                return BlockFace.EAST;
            case 3:
                return BlockFace.WEST;
            case 4:
                return BlockFace.NORTH;
            case 5:
                return BlockFace.SOUTH;
            default:
                return BlockFace.SELF;
        }
    }

    public int blockFaceToNotch(BlockFace face) {
        switch (face) {
            case DOWN:
                return 0;
            case UP:
                return 1;
            case EAST:
                return 2;
            case WEST:
                return 3;
            case NORTH:
                return 4;
            case SOUTH:
                return 5;
            default:
                return 7;
        }
    }

    public BlockFace resolveAdjacentFace(int sourceX, int sourceY, int sourceZ, Block adjacentBlock) {
        for (BlockFace face : BlockFace.values()) {
            if (sourceX + face.getModX() == adjacentBlock.getX()
                    && sourceY + face.getModY() == adjacentBlock.getY()
                    && sourceZ + face.getModZ() == adjacentBlock.getZ()) {
                return face;
            }
        }

        return null;
    }
}
