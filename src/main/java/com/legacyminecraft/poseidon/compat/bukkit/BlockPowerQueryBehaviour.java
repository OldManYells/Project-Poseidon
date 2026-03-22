package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.BlockRedstoneWire;
import org.bukkit.block.BlockFace;

/**
 * Canonical behaviour for CraftBlock power and redstone-strength query policy.
 */
public final class BlockPowerQueryBehaviour {
    private static final BlockPowerQueryBehaviour INSTANCE = new BlockPowerQueryBehaviour();

    private BlockPowerQueryBehaviour() {
    }

    public static BlockPowerQueryBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean isBlockPowered(net.minecraft.server.World world, int x, int y, int z) {
        return world.isBlockPowered(x, y, z);
    }

    public boolean isBlockIndirectlyPowered(net.minecraft.server.World world, int x, int y, int z) {
        return world.isBlockIndirectlyPowered(x, y, z);
    }

    public boolean isBlockFacePowered(
            net.minecraft.server.World world,
            int x,
            int y,
            int z,
            BlockFace face,
            BlockFaceConversionBehaviour blockFaceConversionBehaviour
    ) {
        return world.isBlockFacePowered(x, y, z, blockFaceConversionBehaviour.blockFaceToNotch(face));
    }

    public boolean isBlockFaceIndirectlyPowered(
            net.minecraft.server.World world,
            int x,
            int y,
            int z,
            BlockFace face,
            BlockFaceConversionBehaviour blockFaceConversionBehaviour
    ) {
        return world.isBlockFaceIndirectlyPowered(x, y, z, blockFaceConversionBehaviour.blockFaceToNotch(face));
    }

    public int getBlockPower(net.minecraft.server.World world, int x, int y, int z, BlockFace face) {
        int power = 0;
        BlockRedstoneWire wire = (BlockRedstoneWire) net.minecraft.server.Block.REDSTONE_WIRE;

        if ((face == BlockFace.DOWN || face == BlockFace.SELF) && world.isBlockFacePowered(x, y - 1, z, 0)) {
            power = wire.getPower(world, x, y - 1, z, power);
        }
        if ((face == BlockFace.UP || face == BlockFace.SELF) && world.isBlockFacePowered(x, y + 1, z, 1)) {
            power = wire.getPower(world, x, y + 1, z, power);
        }
        if ((face == BlockFace.EAST || face == BlockFace.SELF) && world.isBlockFacePowered(x, y, z - 1, 2)) {
            power = wire.getPower(world, x, y, z - 1, power);
        }
        if ((face == BlockFace.WEST || face == BlockFace.SELF) && world.isBlockFacePowered(x, y, z + 1, 3)) {
            power = wire.getPower(world, x, y, z + 1, power);
        }
        if ((face == BlockFace.NORTH || face == BlockFace.SELF) && world.isBlockFacePowered(x - 1, y, z, 4)) {
            power = wire.getPower(world, x - 1, y, z, power);
        }
        if ((face == BlockFace.SOUTH || face == BlockFace.SELF) && world.isBlockFacePowered(x + 1, y, z, 5)) {
            power = wire.getPower(world, x + 1, y, z, power);
        }

        if (power > 0) {
            return power;
        }

        return (face == BlockFace.SELF ? world.isBlockIndirectlyPowered(x, y, z)
                : world.isBlockFaceIndirectlyPowered(x, y, z, blockFaceToNotch(face))) ? 15 : 0;
    }

    private int blockFaceToNotch(BlockFace face) {
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
}
