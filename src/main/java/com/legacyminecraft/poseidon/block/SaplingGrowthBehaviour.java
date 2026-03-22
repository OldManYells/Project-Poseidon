package com.legacyminecraft.poseidon.block;

import java.util.Random;

/**
 * Canonical growth-stage/variant policy for legacy sapling wrappers.
 */
public final class SaplingGrowthBehaviour {
    private static final SaplingGrowthBehaviour INSTANCE = new SaplingGrowthBehaviour();

    private SaplingGrowthBehaviour() {
    }

    public static SaplingGrowthBehaviour getInstance() {
        return INSTANCE;
    }

    public int growthMarkBit() {
        return 8;
    }

    public int extractVariant(int data) {
        return data & 3;
    }

    public boolean shouldAttemptGrowth(int lightAbove, Random random) {
        return lightAbove >= 9 && random.nextInt(30) == 0;
    }

    public boolean isMarkedForGrowth(int data) {
        return (data & growthMarkBit()) != 0;
    }

    public int markForGrowth(int data) {
        return data | growthMarkBit();
    }

    public int resolveTextureByVariant(int variant, int fallbackTexture) {
        return variant == 1 ? 63 : (variant == 2 ? 79 : fallbackTexture);
    }

    public TreeGeneratorType resolveGeneratorType(int variant, Random random) {
        if (variant == 1) {
            return TreeGeneratorType.TAIGA;
        }
        if (variant == 2) {
            return TreeGeneratorType.FOREST;
        }
        return random.nextInt(10) == 0 ? TreeGeneratorType.BIG_TREE : TreeGeneratorType.TREES;
    }

    public enum TreeGeneratorType {
        TAIGA,
        FOREST,
        BIG_TREE,
        TREES
    }
}
