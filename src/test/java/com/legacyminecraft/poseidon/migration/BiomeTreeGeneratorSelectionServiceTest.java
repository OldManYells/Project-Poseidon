package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.world.biome.BiomeTreeGeneratorSelectionBehaviour;
import net.minecraft.server.WorldGenBigTree;
import net.minecraft.server.WorldGenForest;
import net.minecraft.server.WorldGenTaiga1;
import net.minecraft.server.WorldGenTaiga2;
import net.minecraft.server.WorldGenTrees;
import net.minecraft.server.WorldGenerator;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class BiomeTreeGeneratorSelectionServiceTest {
    @Test
    public void selectForestTreeGeneratorReturnsForestGeneratorWhenPrimaryRollHits() {
        BiomeTreeGeneratorSelectionBehaviour service = BiomeTreeGeneratorSelectionBehaviour.getInstance();
        WorldGenerator generator = service.selectForestTreeGenerator(new FixedRollRandom(0));

        Assert.assertTrue(generator instanceof WorldGenForest);
    }

    @Test
    public void selectForestTreeGeneratorReturnsBigTreeWhenSecondaryRollHits() {
        BiomeTreeGeneratorSelectionBehaviour service = BiomeTreeGeneratorSelectionBehaviour.getInstance();
        WorldGenerator generator = service.selectForestTreeGenerator(new FixedRollRandom(1, 0));

        Assert.assertTrue(generator instanceof WorldGenBigTree);
    }

    @Test
    public void selectForestTreeGeneratorReturnsNormalTreeWhenBothRollsMiss() {
        BiomeTreeGeneratorSelectionBehaviour service = BiomeTreeGeneratorSelectionBehaviour.getInstance();
        WorldGenerator generator = service.selectForestTreeGenerator(new FixedRollRandom(1, 1));

        Assert.assertTrue(generator instanceof WorldGenTrees);
    }

    @Test
    public void selectDefaultTreeGeneratorMatchesLegacyRollPolicy() {
        BiomeTreeGeneratorSelectionBehaviour service = BiomeTreeGeneratorSelectionBehaviour.getInstance();

        Assert.assertTrue(service.selectDefaultTreeGenerator(new FixedRollRandom(0)) instanceof WorldGenBigTree);
        Assert.assertTrue(service.selectDefaultTreeGenerator(new FixedRollRandom(1)) instanceof WorldGenTrees);
    }

    @Test
    public void selectRainforestTreeGeneratorMatchesLegacyRollPolicy() {
        BiomeTreeGeneratorSelectionBehaviour service = BiomeTreeGeneratorSelectionBehaviour.getInstance();

        Assert.assertTrue(service.selectRainforestTreeGenerator(new FixedRollRandom(0)) instanceof WorldGenBigTree);
        Assert.assertTrue(service.selectRainforestTreeGenerator(new FixedRollRandom(1)) instanceof WorldGenTrees);
    }

    @Test
    public void selectTaigaTreeGeneratorMatchesLegacyRollPolicy() {
        BiomeTreeGeneratorSelectionBehaviour service = BiomeTreeGeneratorSelectionBehaviour.getInstance();

        Assert.assertTrue(service.selectTaigaTreeGenerator(new FixedRollRandom(0)) instanceof WorldGenTaiga1);
        Assert.assertTrue(service.selectTaigaTreeGenerator(new FixedRollRandom(1)) instanceof WorldGenTaiga2);
    }

    private static final class FixedRollRandom extends Random {
        private final int[] rolls;
        private int index;

        private FixedRollRandom(int... rolls) {
            this.rolls = rolls;
        }

        @Override
        public int nextInt(int bound) {
            if (index >= rolls.length) {
                throw new AssertionError("Unexpected random call for bound " + bound);
            }

            int value = rolls[index++];
            if (value < 0 || value >= bound) {
                throw new AssertionError("Invalid roll " + value + " for bound " + bound);
            }

            return value;
        }
    }
}
