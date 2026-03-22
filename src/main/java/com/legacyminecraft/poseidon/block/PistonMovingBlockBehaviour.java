package com.legacyminecraft.poseidon.block;

import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.Block;
import net.minecraft.server.PistonBlockTextures;
import net.minecraft.server.TileEntity;
import net.minecraft.server.TileEntityPiston;
import net.minecraft.server.World;

/**
 * Canonical behavior service for moving-piston block wrappers.
 */
public final class PistonMovingBlockBehaviour {
    private static final PistonMovingBlockBehaviour INSTANCE = new PistonMovingBlockBehaviour();

    private PistonMovingBlockBehaviour() {
    }

    public static PistonMovingBlockBehaviour getInstance() {
        return INSTANCE;
    }

    public TileEntity createMovingTileEntity(int movedBlockId, int movedBlockData, int facing, boolean extending, boolean renderHead) {
        return new TileEntityPiston(movedBlockId, movedBlockData, facing, extending, renderHead);
    }

    public TileEntityPiston extractPistonTileEntity(TileEntity tileEntity) {
        return tileEntity instanceof TileEntityPiston ? (TileEntityPiston) tileEntity : null;
    }

    public boolean handleRemove(World world, int x, int y, int z) {
        TileEntityPiston tileEntityPiston = extractPistonTileEntity(world.getTileEntity(x, y, z));
        if (tileEntityPiston == null) {
            return false;
        }

        tileEntityPiston.k();
        return true;
    }

    public boolean shouldClearOrphanMovingBlock(World world, int x, int y, int z) {
        return !world.isStatic && world.getTileEntity(x, y, z) == null;
    }

    public void dropMovedBlockNaturally(World world, int x, int y, int z, TileEntityPiston tileEntityPiston) {
        if (!world.isStatic && tileEntityPiston != null) {
            Block.byId[tileEntityPiston.a()].g(world, x, y, z, tileEntityPiston.e());
        }
    }

    public float resolveRenderProgress(TileEntityPiston tileEntityPiston) {
        float progress = tileEntityPiston.a(0.0F);
        return tileEntityPiston.c() ? 1.0F - progress : progress;
    }

    public AxisAlignedBB resolveShiftedCollisionBox(World world, int x, int y, int z, int movedBlockId, float progress, int facing, int movingBlockId) {
        if (movedBlockId == 0 || movedBlockId == movingBlockId) {
            return null;
        }

        AxisAlignedBB axisAlignedBB = Block.byId[movedBlockId].e(world, x, y, z);
        if (axisAlignedBB == null) {
            return null;
        }

        axisAlignedBB.a -= (double) ((float) PistonBlockTextures.b[facing] * progress);
        axisAlignedBB.d -= (double) ((float) PistonBlockTextures.b[facing] * progress);
        axisAlignedBB.b -= (double) ((float) PistonBlockTextures.c[facing] * progress);
        axisAlignedBB.e -= (double) ((float) PistonBlockTextures.c[facing] * progress);
        axisAlignedBB.c -= (double) ((float) PistonBlockTextures.d[facing] * progress);
        axisAlignedBB.f -= (double) ((float) PistonBlockTextures.d[facing] * progress);
        return axisAlignedBB;
    }

    public Bounds resolveShiftedOutlineBounds(Block movedBlock, float progress, int facing) {
        return new Bounds(
                movedBlock.minX - (double) ((float) PistonBlockTextures.b[facing] * progress),
                movedBlock.minY - (double) ((float) PistonBlockTextures.c[facing] * progress),
                movedBlock.minZ - (double) ((float) PistonBlockTextures.d[facing] * progress),
                movedBlock.maxX - (double) ((float) PistonBlockTextures.b[facing] * progress),
                movedBlock.maxY - (double) ((float) PistonBlockTextures.c[facing] * progress),
                movedBlock.maxZ - (double) ((float) PistonBlockTextures.d[facing] * progress)
        );
    }

    public static final class Bounds {
        private final double minX;
        private final double minY;
        private final double minZ;
        private final double maxX;
        private final double maxY;
        private final double maxZ;

        public Bounds(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
            this.minX = minX;
            this.minY = minY;
            this.minZ = minZ;
            this.maxX = maxX;
            this.maxY = maxY;
            this.maxZ = maxZ;
        }

        public double getMinX() {
            return minX;
        }

        public double getMinY() {
            return minY;
        }

        public double getMinZ() {
            return minZ;
        }

        public double getMaxX() {
            return maxX;
        }

        public double getMaxY() {
            return maxY;
        }

        public double getMaxZ() {
            return maxZ;
        }
    }
}
