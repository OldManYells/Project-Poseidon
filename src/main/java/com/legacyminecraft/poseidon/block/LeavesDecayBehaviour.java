package com.legacyminecraft.poseidon.block;

import java.util.Random;

/**
 * Canonical decay scan, drop, shear, and texture policy for legacy leaves wrappers.
 */
public final class LeavesDecayBehaviour {
    private static final LeavesDecayBehaviour INSTANCE = new LeavesDecayBehaviour();

    private LeavesDecayBehaviour() {
    }

    public static LeavesDecayBehaviour getInstance() {
        return INSTANCE;
    }

    public void markNearbyLeavesForDecay(RemoveQuery query, int x, int y, int z, int leavesBlockId) {
        int radius = 1;
        int chunkCheck = radius + 1;
        if (query.isAreaLoaded(x - chunkCheck, y - chunkCheck, z - chunkCheck, x + chunkCheck, y + chunkCheck, z + chunkCheck)) {
            for (int dx = -radius; dx <= radius; ++dx) {
                for (int dy = -radius; dy <= radius; ++dy) {
                    for (int dz = -radius; dz <= radius; ++dz) {
                        int typeId = query.getTypeId(x + dx, y + dy, z + dz);
                        if (typeId == leavesBlockId) {
                            int data = query.getData(x + dx, y + dy, z + dz);
                            query.setRawData(x + dx, y + dy, z + dz, data | 8);
                        }
                    }
                }
            }
        }
    }

    public DecayResult evaluateDecayTick(
            DecayQuery query,
            int x,
            int y,
            int z,
            int data,
            int[] scratch,
            int leavesBlockId,
            int logBlockId
    ) {
        if ((data & 8) == 0) {
            return new DecayResult(scratch, false, false);
        }

        int radius = 4;
        int chunkCheck = radius + 1;
        int side = 32;
        int plane = side * side;
        int half = side / 2;
        int[] working = scratch == null ? new int[side * side * side] : scratch;

        if (query.isAreaLoaded(x - chunkCheck, y - chunkCheck, z - chunkCheck, x + chunkCheck, y + chunkCheck, z + chunkCheck)) {
            for (int dx = -radius; dx <= radius; ++dx) {
                for (int dy = -radius; dy <= radius; ++dy) {
                    for (int dz = -radius; dz <= radius; ++dz) {
                        int typeId = query.getTypeId(x + dx, y + dy, z + dz);
                        if (typeId == logBlockId) {
                            working[(dx + half) * plane + (dy + half) * side + dz + half] = 0;
                        } else if (typeId == leavesBlockId) {
                            working[(dx + half) * plane + (dy + half) * side + dz + half] = -2;
                        } else {
                            working[(dx + half) * plane + (dy + half) * side + dz + half] = -1;
                        }
                    }
                }
            }

            for (int distance = 1; distance <= 4; ++distance) {
                for (int dx = -radius; dx <= radius; ++dx) {
                    for (int dy = -radius; dy <= radius; ++dy) {
                        for (int dz = -radius; dz <= radius; ++dz) {
                            if (working[(dx + half) * plane + (dy + half) * side + dz + half] == distance - 1) {
                                if (working[(dx + half - 1) * plane + (dy + half) * side + dz + half] == -2) {
                                    working[(dx + half - 1) * plane + (dy + half) * side + dz + half] = distance;
                                }
                                if (working[(dx + half + 1) * plane + (dy + half) * side + dz + half] == -2) {
                                    working[(dx + half + 1) * plane + (dy + half) * side + dz + half] = distance;
                                }
                                if (working[(dx + half) * plane + (dy + half - 1) * side + dz + half] == -2) {
                                    working[(dx + half) * plane + (dy + half - 1) * side + dz + half] = distance;
                                }
                                if (working[(dx + half) * plane + (dy + half + 1) * side + dz + half] == -2) {
                                    working[(dx + half) * plane + (dy + half + 1) * side + dz + half] = distance;
                                }
                                if (working[(dx + half) * plane + (dy + half) * side + dz + half - 1] == -2) {
                                    working[(dx + half) * plane + (dy + half) * side + dz + half - 1] = distance;
                                }
                                if (working[(dx + half) * plane + (dy + half) * side + dz + half + 1] == -2) {
                                    working[(dx + half) * plane + (dy + half) * side + dz + half + 1] = distance;
                                }
                            }
                        }
                    }
                }
            }
        }

        int center = working[half * plane + half * side + half];
        if (center >= 0) {
            return new DecayResult(working, true, false);
        }
        return new DecayResult(working, false, true);
    }

    public int clearDecayBit(int data) {
        return data & -9;
    }

    public int resolveSaplingDropCount(Random random) {
        return random.nextInt(20) == 0 ? 1 : 0;
    }

    public int resolveSaplingDropItemId(int saplingBlockId) {
        return saplingBlockId;
    }

    public boolean shouldUseShearHarvest(boolean worldIsStatic, int heldItemId, int shearsItemId) {
        return !worldIsStatic && heldItemId == shearsItemId;
    }

    public int stripVariantData(int data) {
        return data & 3;
    }

    public boolean isOpaqueCube(boolean graphicsFast) {
        return !graphicsFast;
    }

    public int resolveTextureByVariantData(int data, int textureId) {
        return (data & 3) == 1 ? textureId + 80 : textureId;
    }

    public interface RemoveQuery {
        boolean isAreaLoaded(int minX, int minY, int minZ, int maxX, int maxY, int maxZ);

        int getTypeId(int x, int y, int z);

        int getData(int x, int y, int z);

        void setRawData(int x, int y, int z, int data);
    }

    public interface DecayQuery {
        boolean isAreaLoaded(int minX, int minY, int minZ, int maxX, int maxY, int maxZ);

        int getTypeId(int x, int y, int z);
    }

    public static final class DecayResult {
        public final int[] scratch;
        public final boolean clearDecayBit;
        public final boolean decayNow;

        public DecayResult(int[] scratch, boolean clearDecayBit, boolean decayNow) {
            this.scratch = scratch;
            this.clearDecayBit = clearDecayBit;
            this.decayNow = decayNow;
        }
    }
}
