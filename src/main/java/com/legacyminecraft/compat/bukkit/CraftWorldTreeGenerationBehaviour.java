package com.legacyminecraft.compat.bukkit;


import java.util.Random;

/**
 * Canonical behavior for CraftWorld tree generation dispatch by Bukkit tree type.
 */
public final class CraftWorldTreeGenerationBehaviour {
    private static final CraftWorldTreeGenerationBehaviour INSTANCE = new CraftWorldTreeGenerationBehaviour();

    private CraftWorldTreeGenerationBehaviour() {
    }

    public static CraftWorldTreeGenerationBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean generateTree(Location location, TreeType treeType, BlockChangeDelegate blockChangeDelegate, Random random) {
        int blockX = location.getBlockX();
        int blockY = location.getBlockY();
        int blockZ = location.getBlockZ();

        switch (treeType) {
            case BIG_TREE:
                return new WorldGenBigTree().generate(blockChangeDelegate, random, blockX, blockY, blockZ);
            case BIRCH:
                return new WorldGenForest().generate(blockChangeDelegate, random, blockX, blockY, blockZ);
            case REDWOOD:
                return new WorldGenTaiga2().generate(blockChangeDelegate, random, blockX, blockY, blockZ);
            case TALL_REDWOOD:
                return new WorldGenTaiga1().generate(blockChangeDelegate, random, blockX, blockY, blockZ);
            case TREE:
            default:
                return new WorldGenTrees().generate(blockChangeDelegate, random, blockX, blockY, blockZ);
        }
    }
}
