package com.legacyminecraft.poseidon.block;

import net.minecraft.server.Block;
import net.minecraft.server.World;

/**
 * Canonical log texture and adjacent-leaf decay marking policy.
 */
public final class LogLeafDecayBehaviour {
    private static final LogLeafDecayBehaviour INSTANCE = new LogLeafDecayBehaviour();

    private LogLeafDecayBehaviour() {
    }

    public static LogLeafDecayBehaviour getInstance() {
        return INSTANCE;
    }

    public int decayRadius() {
        return 4;
    }

    public int decayRange(int radius) {
        return radius + 1;
    }

    public int resolveTextureBySideAndVariant(int side, int variant) {
        return side == 1 ? 21 : (side == 0 ? 21 : (variant == 1 ? 116 : (variant == 2 ? 117 : 20)));
    }

    public boolean shouldMarkLeafForDecay(int blockTypeId, int leavesBlockId, int leafData) {
        return blockTypeId == leavesBlockId && (leafData & 8) == 0;
    }

    public int markLeafDataForDecay(int leafData) {
        return leafData | 8;
    }

    public void markNearbyLeavesForDecay(World world, int x, int y, int z, int leavesBlockId) {
        int radius = decayRadius();
        int range = decayRange(radius);
        if (!world.a(x - range, y - range, z - range, x + range, y + range, z + range)) {
            return;
        }

        for (int dx = -radius; dx <= radius; ++dx) {
            for (int dy = -radius; dy <= radius; ++dy) {
                for (int dz = -radius; dz <= radius; ++dz) {
                    int leafX = x + dx;
                    int leafY = y + dy;
                    int leafZ = z + dz;
                    int blockTypeId = world.getTypeId(leafX, leafY, leafZ);
                    int leafData = world.getData(leafX, leafY, leafZ);
                    if (shouldMarkLeafForDecay(blockTypeId, leavesBlockId, leafData)) {
                        world.setRawData(leafX, leafY, leafZ, markLeafDataForDecay(leafData));
                    }
                }
            }
        }
    }
}
