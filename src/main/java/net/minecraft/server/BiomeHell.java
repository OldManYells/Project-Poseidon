package net.minecraft.server;

import com.legacyminecraft.poseidon.world.biome.BiomeSpawnListBehaviour;

public class BiomeHell extends BiomeBase {
    private final BiomeSpawnListBehaviour biomeSpawnListService = BiomeSpawnListBehaviour.getInstance();

    public BiomeHell() {
        biomeSpawnListService.configureHellBiomeSpawns(this.s, this.t, this.u);
    }
}
