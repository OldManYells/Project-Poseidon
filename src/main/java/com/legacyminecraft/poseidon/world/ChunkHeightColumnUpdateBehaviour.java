package com.legacyminecraft.poseidon.world;


/**
 * Canonical behaviour for chunk height-column recomputation and related sky-light updates.
 */
public final class ChunkHeightColumnUpdateBehaviour {
    private static final ChunkHeightColumnUpdateBehaviour INSTANCE = new ChunkHeightColumnUpdateBehaviour();

    private ChunkHeightColumnUpdateBehaviour() {
    }

    public static ChunkHeightColumnUpdateBehaviour getInstance() {
        return INSTANCE;
    }

    public UpdateResult updateHeightColumn(World world, byte[] blocks, byte[] heightMap, NibbleArray skyLight,
                                           int chunkX, int chunkZ, int localX, int requestedY, int localZ, int minHeight) {
        int oldHeight = heightMap[localZ << 4 | localX] & 255;
        int newHeight = oldHeight;
        if (requestedY > oldHeight) {
            newHeight = requestedY;
        }

        int columnBase = localX << 11 | localZ << 7;
        while (newHeight > 0 && Block.q[blocks[columnBase + newHeight - 1] & 255] == 0) {
            --newHeight;
        }

        if (newHeight == oldHeight) {
            return new UpdateResult(minHeight, false);
        }

        world.g(localX, localZ, newHeight, oldHeight);
        heightMap[localZ << 4 | localX] = (byte) newHeight;

        int updatedMinHeight;
        if (newHeight < minHeight) {
            updatedMinHeight = newHeight;
        } else {
            int computedMinHeight = 127;
            for (int scanX = 0; scanX < 16; ++scanX) {
                for (int scanZ = 0; scanZ < 16; ++scanZ) {
                    int sample = heightMap[scanZ << 4 | scanX] & 255;
                    if (sample < computedMinHeight) {
                        computedMinHeight = sample;
                    }
                }
            }
            updatedMinHeight = computedMinHeight;
        }

        int worldX = chunkX * 16 + localX;
        int worldZ = chunkZ * 16 + localZ;
        if (newHeight < oldHeight) {
            for (int y = newHeight; y < oldHeight; ++y) {
                skyLight.a(localX, y, localZ, 15);
            }
        } else {
            world.a(EnumSkyBlock.SKY, worldX, oldHeight, worldZ, worldX, newHeight, worldZ);
            for (int y = oldHeight; y < newHeight; ++y) {
                skyLight.a(localX, y, localZ, 0);
            }
        }

        int light = 15;
        int scanY;
        for (scanY = newHeight; scanY > 0 && light > 0; skyLight.a(localX, scanY, localZ, light)) {
            --scanY;
            int attenuation = Block.q[getTypeId(blocks, localX, scanY, localZ)];
            if (attenuation == 0) {
                attenuation = 1;
            }
            light -= attenuation;
            if (light < 0) {
                light = 0;
            }
        }

        while (scanY > 0 && Block.q[getTypeId(blocks, localX, scanY - 1, localZ)] == 0) {
            --scanY;
        }

        if (scanY != newHeight) {
            world.a(EnumSkyBlock.SKY, worldX - 1, scanY, worldZ - 1, worldX + 1, newHeight, worldZ + 1);
        }

        return new UpdateResult(updatedMinHeight, true);
    }

    private int getTypeId(byte[] blocks, int x, int y, int z) {
        return blocks[x << 11 | z << 7 | y] & 255;
    }

    public static final class UpdateResult {
        private final int minHeight;
        private final boolean dirty;

        public UpdateResult(int minHeight, boolean dirty) {
            this.minHeight = minHeight;
            this.dirty = dirty;
        }

        public int minHeight() {
            return minHeight;
        }

        public boolean dirty() {
            return dirty;
        }
    }
}

