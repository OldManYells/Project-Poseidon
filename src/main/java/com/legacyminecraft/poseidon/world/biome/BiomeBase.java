package com.legacyminecraft.poseidon.world.biome;

import java.util.ArrayList;
import java.util.List;

/**
 * Biome registry scaffold for climate selectors.
 */
public class BiomeBase {
    public static final BiomeBase RAINFOREST = new BiomeBase();
    public static final BiomeBase SWAMPLAND = new BiomeBase();
    public static final BiomeBase SEASONAL_FOREST = new BiomeBase();
    public static final BiomeBase FOREST = new BiomeBase();
    public static final BiomeBase SAVANNA = new BiomeBase();
    public static final BiomeBase SHRUBLAND = new BiomeBase();
    public static final BiomeBase TAIGA = new BiomeBase();
    public static final BiomeBase DESERT = new BiomeBase();
    public static final BiomeBase PLAINS = new BiomeBase();
    public static final BiomeBase ICE_DESERT = new BiomeBase();
    public static final BiomeBase TUNDRA = new BiomeBase();
    public static final BiomeBase HELL = new BiomeBase();
    public static final BiomeBase SKY = new BiomeBase();

    public byte p;
    public byte q;

    public static BiomeBase a(double temperature, double humidity) {
        return BiomeClimateSelectionBehaviour.getInstance().selectClimateBiome((float) temperature, (float) humidity);
    }

    public List<BiomeMeta> a(EnumCreatureType creatureType) {
        return new ArrayList<BiomeMeta>();
    }
}
