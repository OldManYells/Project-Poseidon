package com.legacyminecraft.poseidon.world;


/**
 * Canonical behaviour for chunk initial skylight/height-map bootstrapping.
 */
public final class ChunkLightingInitializationBehaviour {
    private static final ChunkLightingInitializationBehaviour INSTANCE = new ChunkLightingInitializationBehaviour();

    private ChunkLightingInitializationBehaviour() {
    }

    public static ChunkLightingInitializationBehaviour getInstance() {
        return INSTANCE;
    }

    public InitResult initLighting(World world, byte[] blocks, byte[] heightMap, NibbleArray skyLight) {
        int minHeight = 127;

        for (int localX = 0; localX < 16; ++localX) {
            for (int localZ = 0; localZ < 16; ++localZ) {
                int topY = 127;
                int columnBase = localX << 11 | localZ << 7;

                for (; topY > 0 && Block.q[blocks[columnBase + topY - 1] & 255] == 0; --topY) {
                    // scan down until top non-air/light-blocking block
                }

                heightMap[localZ << 4 | localX] = (byte) topY;
                if (topY < minHeight) {
                    minHeight = topY;
                }

                if (!world.worldProvider.e) {
                    int light = 15;
                    int y = 127;
                    do {
                        light -= Block.q[blocks[columnBase + y] & 255];
                        if (light > 0) {
                            skyLight.a(localX, y, localZ, light);
                        }
                        --y;
                    } while (y > 0 && light > 0);
                }
            }
        }

        return new InitResult(minHeight, true);
    }

    public static final class InitResult {
        private final int minHeight;
        private final boolean dirty;

        public InitResult(int minHeight, boolean dirty) {
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

