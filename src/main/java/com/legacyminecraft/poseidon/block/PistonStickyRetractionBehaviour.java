package com.legacyminecraft.poseidon.block;

import com.legacyminecraft.poseidon.world.TileEntity;
import com.legacyminecraft.poseidon.world.World;

/**
 * Canonical behaviour for sticky-piston retraction coordinate/state/pull policy.
 */
public final class PistonStickyRetractionBehaviour {
    private static final PistonStickyRetractionBehaviour INSTANCE = new PistonStickyRetractionBehaviour();

    private PistonStickyRetractionBehaviour() {
    }

    public static PistonStickyRetractionBehaviour getInstance() {
        return INSTANCE;
    }

    public RetractionPositions resolveRetractionPositions(int pistonX, int pistonY, int pistonZ, int facing) {
        int adjacentX = pistonX + PistonBlockTextures.b[facing];
        int adjacentY = pistonY + PistonBlockTextures.c[facing];
        int adjacentZ = pistonZ + PistonBlockTextures.d[facing];
        int pullX = pistonX + PistonBlockTextures.b[facing] * 2;
        int pullY = pistonY + PistonBlockTextures.c[facing] * 2;
        int pullZ = pistonZ + PistonBlockTextures.d[facing] * 2;
        return new RetractionPositions(adjacentX, adjacentY, adjacentZ, pullX, pullY, pullZ);
    }

    public PulledBlockState resolvePulledBlockState(World world, int pullX, int pullY, int pullZ, int facing) {
        int pulledBlockId = world.getTypeId(pullX, pullY, pullZ);
        int pulledBlockData = world.getData(pullX, pullY, pullZ);
        boolean handledByMovingPiston = false;

        if (pulledBlockId == Block.PISTON_MOVING.id) {
            TileEntity tileEntity = world.getTileEntity(pullX, pullY, pullZ);
            if (tileEntity instanceof TileEntityPiston) {
                TileEntityPiston movingPiston = (TileEntityPiston) tileEntity;
                if (movingPiston.d() == facing && movingPiston.c()) {
                    movingPiston.k();
                    pulledBlockId = movingPiston.a();
                    pulledBlockData = movingPiston.e();
                    handledByMovingPiston = true;
                }
            }
        }

        return new PulledBlockState(pulledBlockId, pulledBlockData, handledByMovingPiston);
    }

    public boolean shouldPullBlock(PulledBlockState pulledBlockState,
                                   World world,
                                   int pullX,
                                   int pullY,
                                   int pullZ,
                                   PushabilityQuery pushabilityQuery) {
        if (pulledBlockState.handledByMovingPiston || pulledBlockState.blockId <= 0) {
            return false;
        }
        if (!pushabilityQuery.canPush(pulledBlockState.blockId, world, pullX, pullY, pullZ, false)) {
            return false;
        }
        return Block.byId[pulledBlockState.blockId].e() == 0
                || pulledBlockState.blockId == Block.PISTON.id
                || pulledBlockState.blockId == Block.PISTON_STICKY.id;
    }

    public boolean shouldClearAdjacentBlock(PulledBlockState pulledBlockState) {
        return !pulledBlockState.handledByMovingPiston;
    }

    public interface PushabilityQuery {
        boolean canPush(int blockId, World world, int x, int y, int z, boolean allowDestroy);
    }

    public static final class RetractionPositions {
        private final int adjacentX;
        private final int adjacentY;
        private final int adjacentZ;
        private final int pullX;
        private final int pullY;
        private final int pullZ;

        public RetractionPositions(int adjacentX, int adjacentY, int adjacentZ, int pullX, int pullY, int pullZ) {
            this.adjacentX = adjacentX;
            this.adjacentY = adjacentY;
            this.adjacentZ = adjacentZ;
            this.pullX = pullX;
            this.pullY = pullY;
            this.pullZ = pullZ;
        }

        public int getAdjacentX() {
            return adjacentX;
        }

        public int getAdjacentY() {
            return adjacentY;
        }

        public int getAdjacentZ() {
            return adjacentZ;
        }

        public int getPullX() {
            return pullX;
        }

        public int getPullY() {
            return pullY;
        }

        public int getPullZ() {
            return pullZ;
        }
    }

    public static final class PulledBlockState {
        private final int blockId;
        private final int blockData;
        private final boolean handledByMovingPiston;

        public PulledBlockState(int blockId, int blockData, boolean handledByMovingPiston) {
            this.blockId = blockId;
            this.blockData = blockData;
            this.handledByMovingPiston = handledByMovingPiston;
        }

        public int getBlockId() {
            return blockId;
        }

        public int getBlockData() {
            return blockData;
        }
    }
}
