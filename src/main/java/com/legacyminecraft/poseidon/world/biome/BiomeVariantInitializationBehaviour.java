package com.legacyminecraft.poseidon.world.biome;

import net.minecraft.server.BiomeBase;

/**
 * Canonical initialization hooks for thin biome variant wrappers.
 */
public final class BiomeVariantInitializationBehaviour {
    private static final BiomeVariantInitializationBehaviour INSTANCE = new BiomeVariantInitializationBehaviour();

    private BiomeVariantInitializationBehaviour() {
    }

    public static BiomeVariantInitializationBehaviour getInstance() {
        return INSTANCE;
    }

    public void initializeDesertBiome(BiomeBase biome) {
        // Beta 1.7.3 desert variant currently relies on BiomeBase defaults.
    }

    public void initializeSwampBiome(BiomeBase biome) {
        // Beta 1.7.3 swamp variant currently relies on BiomeBase defaults.
    }
}
