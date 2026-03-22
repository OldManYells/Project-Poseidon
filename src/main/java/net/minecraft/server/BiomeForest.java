package net.minecraft.server;

import com.legacyminecraft.poseidon.world.biome.BiomeSpawnListBehaviour;
import com.legacyminecraft.poseidon.world.biome.BiomeTreeGeneratorSelectionBehaviour;

import java.util.Random;

public class BiomeForest extends BiomeBase {
    private final BiomeSpawnListBehaviour biomeSpawnListService = BiomeSpawnListBehaviour.getInstance();
    private final BiomeTreeGeneratorSelectionBehaviour biomeTreeGeneratorSelectionService = BiomeTreeGeneratorSelectionBehaviour.getInstance();

    public BiomeForest() {
        biomeSpawnListService.configureForestBiomeSpawns(this.t);
    }

    public WorldGenerator a(Random random) {
        return biomeTreeGeneratorSelectionService.selectForestTreeGenerator(random);
    }
}
