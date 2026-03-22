package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.world.biome.BiomeClimateSelectionBehaviour;
import net.minecraft.server.BiomeBase;
import net.minecraft.server.Block;
import org.junit.Assert;
import org.junit.Test;

public class BiomeClimateSelectionServiceTest {
    @Test
    public void selectClimateBiomeMatchesLegacyTemperatureHumidityRules() {
        BiomeClimateSelectionBehaviour service = BiomeClimateSelectionBehaviour.getInstance();

        Assert.assertSame(BiomeBase.TUNDRA, service.selectClimateBiome(0.05F, 0.9F));
        Assert.assertSame(BiomeBase.SWAMPLAND, service.selectClimateBiome(0.6F, 0.9F));
        Assert.assertSame(BiomeBase.SHRUBLAND, service.selectClimateBiome(0.8F, 0.4F));
        Assert.assertSame(BiomeBase.RAINFOREST, service.selectClimateBiome(0.99F, 0.95F));
    }

    @Test
    public void bootstrapClimateLookupTablePopulatesLookupAndDesertSurfaceOverrides() {
        BiomeClimateSelectionBehaviour service = BiomeClimateSelectionBehaviour.getInstance();
        BiomeBase[] lookupTable = new BiomeBase[4096];

        service.bootstrapClimateLookupTable(lookupTable, BiomeBase.DESERT, BiomeBase.ICE_DESERT, (byte) Block.SAND.id);

        Assert.assertNotNull(lookupTable[0]);
        Assert.assertSame(BiomeBase.TUNDRA, service.lookupBiome(lookupTable, 0.0D, 0.0D));
        Assert.assertSame(BiomeBase.TUNDRA, service.lookupBiome(lookupTable, 0.2D, 0.8D));
        Assert.assertEquals((byte) Block.SAND.id, BiomeBase.DESERT.p);
        Assert.assertEquals((byte) Block.SAND.id, BiomeBase.DESERT.q);
        Assert.assertEquals((byte) Block.SAND.id, BiomeBase.ICE_DESERT.p);
        Assert.assertEquals((byte) Block.SAND.id, BiomeBase.ICE_DESERT.q);
    }
}
