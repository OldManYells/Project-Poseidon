package com.legacyminecraft.poseidon.world.biome;

import net.minecraft.server.WorldGenBigTree;
import net.minecraft.server.WorldGenForest;
import net.minecraft.server.WorldGenTaiga1;
import net.minecraft.server.WorldGenTaiga2;
import net.minecraft.server.WorldGenTrees;
import net.minecraft.server.WorldGenerator;

import java.util.Random;

/**
 * Canonical biome tree-generator selection service.
 */
public final class BiomeTreeGeneratorSelectionBehaviour {
    private static final BiomeTreeGeneratorSelectionBehaviour INSTANCE = new BiomeTreeGeneratorSelectionBehaviour();

    private BiomeTreeGeneratorSelectionBehaviour() {
    }

    public static BiomeTreeGeneratorSelectionBehaviour getInstance() {
        return INSTANCE;
    }

    public WorldGenerator selectForestTreeGenerator(Random random) {
        return random.nextInt(5) == 0
                ? new WorldGenForest()
                : (random.nextInt(3) == 0 ? new WorldGenBigTree() : new WorldGenTrees());
    }

    public WorldGenerator selectDefaultTreeGenerator(Random random) {
        return random.nextInt(10) == 0 ? new WorldGenBigTree() : new WorldGenTrees();
    }

    public WorldGenerator selectRainforestTreeGenerator(Random random) {
        return random.nextInt(3) == 0 ? new WorldGenBigTree() : new WorldGenTrees();
    }

    public WorldGenerator selectTaigaTreeGenerator(Random random) {
        return random.nextInt(3) == 0 ? new WorldGenTaiga1() : new WorldGenTaiga2();
    }
}
