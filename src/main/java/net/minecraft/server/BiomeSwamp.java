package net.minecraft.server;

import com.legacyminecraft.poseidon.world.biome.BiomeVariantInitializationBehaviour;

public class BiomeSwamp extends BiomeBase {
    private static final BiomeVariantInitializationBehaviour BIOME_VARIANT_INITIALIZATION_BEHAVIOUR = BiomeVariantInitializationBehaviour.getInstance();

    public BiomeSwamp() {
        BIOME_VARIANT_INITIALIZATION_BEHAVIOUR.initializeSwampBiome(this);
    }
}
