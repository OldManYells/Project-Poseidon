package net.minecraft.server;

import com.legacyminecraft.poseidon.world.biome.BiomeTreeGeneratorSelectionBehaviour;

import java.util.Random;

public class BiomeRainforest extends BiomeBase {
    private final BiomeTreeGeneratorSelectionBehaviour biomeTreeGeneratorSelectionService = BiomeTreeGeneratorSelectionBehaviour.getInstance();

    public BiomeRainforest() {}

    public WorldGenerator a(Random random) {
        return biomeTreeGeneratorSelectionService.selectRainforestTreeGenerator(random);
    }
}
