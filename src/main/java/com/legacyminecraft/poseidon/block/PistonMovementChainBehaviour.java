package com.legacyminecraft.poseidon.block;

import net.minecraft.server.Block;
import net.minecraft.server.BlockPistonMoving;
import net.minecraft.server.PistonBlockTextures;
import net.minecraft.server.World;

/**
 * Canonical behaviour for piston push-length checks and moving-block chain shifts.
 */
public final class PistonMovementChainBehaviour {
    private static final PistonMovementChainBehaviour INSTANCE = new PistonMovementChainBehaviour();

    private PistonMovementChainBehaviour() {
    }

    public static PistonMovementChainBehaviour getInstance() {
        return INSTANCE;
    }

    public int calculateExtensionLength(World world,
                                        int pistonX,
                                        int pistonY,
                                        int pistonZ,
                                        int facing,
                                        PushabilityQuery pushabilityQuery) {
        int scanX = pistonX + PistonBlockTextures.b[facing];
        int scanY = pistonY + PistonBlockTextures.c[facing];
        int scanZ = pistonZ + PistonBlockTextures.d[facing];
        int pushedBlocks = 0;

        while (pushedBlocks < 13) {
            if (scanY <= 0 || scanY >= 127) {
                return -1;
            }

            int targetBlockId = world.getTypeId(scanX, scanY, scanZ);
            if (targetBlockId != 0) {
                if (!pushabilityQuery.canPush(targetBlockId, world, scanX, scanY, scanZ, true)) {
                    return -1;
                }

                if (Block.byId[targetBlockId].e() != 1) {
                    if (pushedBlocks == 12) {
                        return -1;
                    }

                    scanX += PistonBlockTextures.b[facing];
                    scanY += PistonBlockTextures.c[facing];
                    scanZ += PistonBlockTextures.d[facing];
                    ++pushedBlocks;
                    continue;
                }
            }

            return pushedBlocks;
        }

        return -1;
    }

    public boolean extendWithMovingBlocks(World world,
                                          int pistonX,
                                          int pistonY,
                                          int pistonZ,
                                          int facing,
                                          int pistonBlockId,
                                          boolean stickyPiston,
                                          PushabilityQuery pushabilityQuery,
                                          BlockClearAction blockClearAction) {
        int scanX = pistonX + PistonBlockTextures.b[facing];
        int scanY = pistonY + PistonBlockTextures.c[facing];
        int scanZ = pistonZ + PistonBlockTextures.d[facing];
        int pushedBlocks = 0;

        while (pushedBlocks < 13) {
            if (scanY <= 0 || scanY >= 127) {
                return false;
            }

            int targetBlockId = world.getTypeId(scanX, scanY, scanZ);
            if (targetBlockId != 0) {
                if (!pushabilityQuery.canPush(targetBlockId, world, scanX, scanY, scanZ, true)) {
                    return false;
                }

                if (Block.byId[targetBlockId].e() != 1) {
                    if (pushedBlocks == 12) {
                        return false;
                    }

                    scanX += PistonBlockTextures.b[facing];
                    scanY += PistonBlockTextures.c[facing];
                    scanZ += PistonBlockTextures.d[facing];
                    ++pushedBlocks;
                    continue;
                }

                Block.byId[targetBlockId].g(world, scanX, scanY, scanZ, world.getData(scanX, scanY, scanZ));
                blockClearAction.clear(world, scanX, scanY, scanZ);
            }
            break;
        }

        while (scanX != pistonX || scanY != pistonY || scanZ != pistonZ) {
            int sourceX = scanX - PistonBlockTextures.b[facing];
            int sourceY = scanY - PistonBlockTextures.c[facing];
            int sourceZ = scanZ - PistonBlockTextures.d[facing];
            int sourceBlockId = world.getTypeId(sourceX, sourceY, sourceZ);
            int sourceData = world.getData(sourceX, sourceY, sourceZ);

            if (sourceBlockId == pistonBlockId && sourceX == pistonX && sourceY == pistonY && sourceZ == pistonZ) {
                int movingData = facing | (stickyPiston ? 8 : 0);
                world.setRawTypeIdAndData(scanX, scanY, scanZ, Block.PISTON_MOVING.id, movingData);
                world.setTileEntity(scanX, scanY, scanZ,
                        BlockPistonMoving.a(Block.PISTON_EXTENSION.id, movingData, facing, true, false));
            } else {
                world.setRawTypeIdAndData(scanX, scanY, scanZ, Block.PISTON_MOVING.id, sourceData);
                world.setTileEntity(scanX, scanY, scanZ,
                        BlockPistonMoving.a(sourceBlockId, sourceData, facing, true, false));
            }

            scanX = sourceX;
            scanY = sourceY;
            scanZ = sourceZ;
        }

        return true;
    }

    public interface PushabilityQuery {
        boolean canPush(int blockId, World world, int x, int y, int z, boolean allowDestroy);
    }

    public interface BlockClearAction {
        void clear(World world, int x, int y, int z);
    }
}
