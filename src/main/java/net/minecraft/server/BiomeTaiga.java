package net.minecraft.server;

import com.legacyminecraft.poseidon.world.biome.BiomeSpawnListBehaviour;
import com.legacyminecraft.poseidon.world.biome.BiomeTreeGeneratorSelectionBehaviour;

import java.util.Random;

public class BiomeTaiga extends BiomeBase {
    private final BiomeSpawnListBehaviour biomeSpawnListService = BiomeSpawnListBehaviour.getInstance();
    private final BiomeTreeGeneratorSelectionBehaviour biomeTreeGeneratorSelectionService = BiomeTreeGeneratorSelectionBehaviour.getInstance();

    public BiomeTaiga() {
        biomeSpawnListService.configureTaigaBiomeSpawns(this.t);
    }

    public WorldGenerator a(Random random) {
        return biomeTreeGeneratorSelectionService.selectTaigaTreeGenerator(random);
    }
}
