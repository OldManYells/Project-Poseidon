package net.minecraft.server;

import com.legacyminecraft.poseidon.world.biome.BiomeSpawnListBehaviour;

public class BiomeSky extends BiomeBase {
    private final BiomeSpawnListBehaviour biomeSpawnListService = BiomeSpawnListBehaviour.getInstance();

    public BiomeSky() {
        biomeSpawnListService.configureSkyBiomeSpawns(this.s, this.t, this.u);
    }
}
