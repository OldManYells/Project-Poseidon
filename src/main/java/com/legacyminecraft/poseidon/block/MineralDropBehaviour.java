package com.legacyminecraft.poseidon.block;

import java.util.Random;

/**
 * Canonical drop/count/data rules for legacy mineral block wrappers.
 */
public final class MineralDropBehaviour {
    private static final MineralDropBehaviour INSTANCE = new MineralDropBehaviour();

    private MineralDropBehaviour() {
    }

    public static MineralDropBehaviour getInstance() {
        return INSTANCE;
    }

    public int resolveOreDropItemId(
            int blockId,
            int coalOreBlockId,
            int diamondOreBlockId,
            int lapisOreBlockId,
            int coalItemId,
            int diamondItemId,
            int lapisItemId
    ) {
        if (blockId == coalOreBlockId) {
            return coalItemId;
        }
        if (blockId == diamondOreBlockId) {
            return diamondItemId;
        }
        if (blockId == lapisOreBlockId) {
            return lapisItemId;
        }
        return blockId;
    }

    public int resolveOreDropCount(int blockId, int lapisOreBlockId, Random random) {
        return blockId == lapisOreBlockId ? 4 + random.nextInt(5) : 1;
    }

    public int resolveOreDropData(int blockId, int lapisOreBlockId) {
        return blockId == lapisOreBlockId ? 4 : 0;
    }

    public int resolveClayDropItemId(int clayBallItemId) {
        return clayBallItemId;
    }

    public int resolveClayDropCount() {
        return 4;
    }

    public int resolveGlowstoneDustDropItemId(int glowstoneDustItemId) {
        return glowstoneDustItemId;
    }

    public int resolveGlowstoneDustDropCount(Random random) {
        return 2 + random.nextInt(3);
    }
}
