package com.legacyminecraft.poseidon.world;


/**
 * Canonical behaviour for chunk-local block mutation orchestration.
 */
public final class ChunkBlockMutationBehaviour {
    private static final ChunkBlockMutationBehaviour INSTANCE = new ChunkBlockMutationBehaviour();

    private ChunkBlockMutationBehaviour() {
    }

    public static ChunkBlockMutationBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean setTypeAndData(World world, byte[] blocks, byte[] heightMap, NibbleArray metadata,
                                  int chunkX, int chunkZ, int x, int y, int z,
                                  int newType, int newData, boolean transmutationFixEnabled,
                                  HeightUpdateActions actions) {
        int index = x << 11 | z << 7 | y;
        int oldHeight = heightMap[z << 4 | x] & 255;
        int oldType = blocks[index] & 255;

        if (oldType == newType && metadata.a(x, y, z) == newData) {
            return false;
        }

        int worldX = chunkX * 16 + x;
        int worldZ = chunkZ * 16 + z;
        blocks[index] = (byte) (newType & 255);

        if (transmutationFixEnabled) {
            metadata.a(x, y, z, newData);
            if (oldType != 0 && !world.isStatic) {
                Block.byId[oldType].remove(world, worldX, y, worldZ);
            }
        } else {
            if (oldType != 0 && !world.isStatic) {
                Block.byId[oldType].remove(world, worldX, y, worldZ);
            }
            metadata.a(x, y, z, newData);
        }

        if (!world.worldProvider.e) {
            if (Block.q[newType & 255] != 0) {
                if (y >= oldHeight) {
                    actions.updateHeightColumn(x, y + 1, z);
                }
            } else if (y == oldHeight - 1) {
                actions.updateHeightColumn(x, y, z);
            }
            world.a(EnumSkyBlock.SKY, worldX, y, worldZ, worldX, y, worldZ);
        }

        world.a(EnumSkyBlock.BLOCK, worldX, y, worldZ, worldX, y, worldZ);
        actions.relightNeighborColumns(x, z);
        if (newType != 0) {
            Block.byId[newType].c(world, worldX, y, worldZ);
        }
        return true;
    }

    public boolean setType(World world, byte[] blocks, byte[] heightMap, NibbleArray metadata,
                           int chunkX, int chunkZ, int x, int y, int z, int newType,
                           HeightUpdateActions actions) {
        int index = x << 11 | z << 7 | y;
        int oldHeight = heightMap[z << 4 | x] & 255;
        int oldType = blocks[index] & 255;
        if (oldType == newType) {
            return false;
        }

        int worldX = chunkX * 16 + x;
        int worldZ = chunkZ * 16 + z;
        blocks[index] = (byte) (newType & 255);
        if (oldType != 0) {
            Block.byId[oldType].remove(world, worldX, y, worldZ);
        }
        metadata.a(x, y, z, 0);

        if (Block.q[newType & 255] != 0) {
            if (y >= oldHeight) {
                actions.updateHeightColumn(x, y + 1, z);
            }
        } else if (y == oldHeight - 1) {
            actions.updateHeightColumn(x, y, z);
        }

        world.a(EnumSkyBlock.SKY, worldX, y, worldZ, worldX, y, worldZ);
        world.a(EnumSkyBlock.BLOCK, worldX, y, worldZ, worldX, y, worldZ);
        actions.relightNeighborColumns(x, z);
        if (newType != 0 && !world.isStatic) {
            Block.byId[newType].c(world, worldX, y, worldZ);
        }
        return true;
    }

    public interface HeightUpdateActions {
        void updateHeightColumn(int x, int y, int z);

        void relightNeighborColumns(int x, int z);
    }
}
