package net.minecraft.server;

import com.legacyminecraft.poseidon.world.biome.BiomeVariantInitializationBehaviour;

public class BiomeDesert extends BiomeBase {
    private static final BiomeVariantInitializationBehaviour BIOME_VARIANT_INITIALIZATION_BEHAVIOUR = BiomeVariantInitializationBehaviour.getInstance();

    public BiomeDesert() {
        BIOME_VARIANT_INITIALIZATION_BEHAVIOUR.initializeDesertBiome(this);
    }
}
