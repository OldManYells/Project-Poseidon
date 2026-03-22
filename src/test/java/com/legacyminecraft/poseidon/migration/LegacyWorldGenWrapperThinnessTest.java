package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyWorldGenWrapperThinnessTest {
    private static final Path WORLD_GEN_GRASS_PATH = Paths.get("src/main/java/net/minecraft/server/WorldGenGrass.java");
    private static final Path WORLD_GEN_MINABLE_PATH = Paths.get("src/main/java/net/minecraft/server/WorldGenMinable.java");
    private static final Path WORLD_GEN_FLOWERS_PATH = Paths.get("src/main/java/net/minecraft/server/WorldGenFlowers.java");
    private static final Path WORLD_GEN_REED_PATH = Paths.get("src/main/java/net/minecraft/server/WorldGenReed.java");
    private static final Path WORLD_GEN_PUMPKIN_PATH = Paths.get("src/main/java/net/minecraft/server/WorldGenPumpkin.java");
    private static final Path WORLD_GEN_CACTUS_PATH = Paths.get("src/main/java/net/minecraft/server/WorldGenCactus.java");
    private static final Path WORLD_GEN_FIRE_PATH = Paths.get("src/main/java/net/minecraft/server/WorldGenFire.java");
    private static final Path WORLD_GEN_DEAD_BUSH_PATH = Paths.get("src/main/java/net/minecraft/server/WorldGenDeadBush.java");
    private static final Path WORLD_GEN_CLAY_PATH = Paths.get("src/main/java/net/minecraft/server/WorldGenClay.java");
    private static final Path WORLD_GEN_LIQUIDS_PATH = Paths.get("src/main/java/net/minecraft/server/WorldGenLiquids.java");
    private static final Path WORLD_GEN_HELL_LAVA_PATH = Paths.get("src/main/java/net/minecraft/server/WorldGenHellLava.java");
    private static final Path WORLD_GEN_LIGHTSTONE1_PATH = Paths.get("src/main/java/net/minecraft/server/WorldGenLightStone1.java");
    private static final Path WORLD_GEN_LIGHTSTONE2_PATH = Paths.get("src/main/java/net/minecraft/server/WorldGenLightStone2.java");
    private static final Path WORLD_GEN_FOREST_PATH = Paths.get("src/main/java/net/minecraft/server/WorldGenForest.java");
    private static final Path WORLD_GEN_TREES_PATH = Paths.get("src/main/java/net/minecraft/server/WorldGenTrees.java");
    private static final Path WORLD_GEN_TAIGA1_PATH = Paths.get("src/main/java/net/minecraft/server/WorldGenTaiga1.java");
    private static final Path WORLD_GEN_TAIGA2_PATH = Paths.get("src/main/java/net/minecraft/server/WorldGenTaiga2.java");
    private static final Path WORLD_GEN_DUNGEONS_PATH = Paths.get("src/main/java/net/minecraft/server/WorldGenDungeons.java");
    private static final Path WORLD_GEN_LAKES_PATH = Paths.get("src/main/java/net/minecraft/server/WorldGenLakes.java");
    private static final Path WORLD_GEN_BIG_TREE_PATH = Paths.get("src/main/java/net/minecraft/server/WorldGenBigTree.java");
    private static final Path MAP_GEN_BASE_PATH = Paths.get("src/main/java/net/minecraft/server/MapGenBase.java");

    @Test
    public void worldGenGrassDelegatesPatchGenerationToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(WORLD_GEN_GRASS_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("GrassPatchGenerationBehaviour"));
        Assert.assertTrue(text.contains("GRASS_PATCH_GENERATION_BEHAVIOUR.generate"));
        Assert.assertFalse(text.contains("for (int i1 = 0; i1 < 128; ++i1)"));
        Assert.assertFalse(text.contains("((BlockFlower) Block.byId[this.a]).f"));
    }

    @Test
    public void worldGenMinableDelegatesVeinGenerationToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(WORLD_GEN_MINABLE_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("OreVeinGenerationBehaviour"));
        Assert.assertTrue(text.contains("ORE_VEIN_GENERATION_BEHAVIOUR.generate"));
        Assert.assertFalse(text.contains("float f = random.nextFloat() * 3.1415927F"));
        Assert.assertFalse(text.contains("world.setRawTypeId(k2, l2, i3, this.a)"));
    }

    @Test
    public void worldGenFlowersDelegatesPatchGenerationToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(WORLD_GEN_FLOWERS_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("FlowerPatchGenerationBehaviour"));
        Assert.assertTrue(text.contains("FLOWER_PATCH_GENERATION_BEHAVIOUR.generate"));
        Assert.assertFalse(text.contains("for (int l = 0; l < 64; ++l)"));
        Assert.assertFalse(text.contains("world.setRawTypeId(i1, j1, k1, this.a)"));
    }

    @Test
    public void worldGenReedDelegatesPatchGenerationToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(WORLD_GEN_REED_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("ReedPatchGenerationBehaviour"));
        Assert.assertTrue(text.contains("REED_PATCH_GENERATION_BEHAVIOUR.generate"));
        Assert.assertFalse(text.contains("world.getMaterial(i1 - 1, j - 1, k1) == Material.WATER"));
        Assert.assertFalse(text.contains("world.setRawTypeId(i1, j1 + i2, k1, Block.SUGAR_CANE_BLOCK.id)"));
    }

    @Test
    public void worldGenPumpkinDelegatesPatchGenerationToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(WORLD_GEN_PUMPKIN_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("PumpkinPatchGenerationBehaviour"));
        Assert.assertTrue(text.contains("PUMPKIN_PATCH_GENERATION_BEHAVIOUR.generate"));
        Assert.assertFalse(text.contains("world.getTypeId(i1, j1 - 1, k1) == Block.GRASS.id"));
        Assert.assertFalse(text.contains("world.setRawTypeIdAndData(i1, j1, k1, Block.PUMPKIN.id, random.nextInt(4))"));
    }

    @Test
    public void worldGenCactusDelegatesPatchGenerationToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(WORLD_GEN_CACTUS_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("CactusPatchGenerationBehaviour"));
        Assert.assertTrue(text.contains("CACTUS_PATCH_GENERATION_BEHAVIOUR.generate"));
        Assert.assertFalse(text.contains("int l1 = 1 + random.nextInt(random.nextInt(3) + 1)"));
        Assert.assertFalse(text.contains("world.setRawTypeId(i1, j1 + i2, k1, Block.CACTUS.id)"));
    }

    @Test
    public void worldGenFireDelegatesPatchGenerationToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(WORLD_GEN_FIRE_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("FirePatchGenerationBehaviour"));
        Assert.assertTrue(text.contains("FIRE_PATCH_GENERATION_BEHAVIOUR.generate"));
        Assert.assertFalse(text.contains("world.getTypeId(i1, j1 - 1, k1) == Block.NETHERRACK.id"));
        Assert.assertFalse(text.contains("world.setTypeId(i1, j1, k1, Block.FIRE.id)"));
    }

    @Test
    public void worldGenDeadBushDelegatesPatchGenerationToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(WORLD_GEN_DEAD_BUSH_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("DeadBushPatchGenerationBehaviour"));
        Assert.assertTrue(text.contains("DEAD_BUSH_PATCH_GENERATION_BEHAVIOUR.generate"));
        Assert.assertFalse(text.contains("((l = world.getTypeId(i, j, k)) == 0 || l == Block.LEAVES.id) && j > 0"));
        Assert.assertFalse(text.contains("world.setRawTypeId(j1, k1, l1, this.a)"));
    }

    @Test
    public void worldGenClayDelegatesVeinGenerationToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(WORLD_GEN_CLAY_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("ClayVeinGenerationBehaviour"));
        Assert.assertTrue(text.contains("CLAY_VEIN_GENERATION_BEHAVIOUR.generate"));
        Assert.assertFalse(text.contains("world.getMaterial(i, j, k) != Material.WATER"));
        Assert.assertFalse(text.contains("world.setRawTypeId(k2, l2, i3, this.a)"));
    }

    @Test
    public void worldGenLiquidsDelegatesPocketGenerationToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(WORLD_GEN_LIQUIDS_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("CaveLiquidPocketGenerationBehaviour"));
        Assert.assertTrue(text.contains("generateInStone"));
        Assert.assertFalse(text.contains("if (world.getTypeId(i, j + 1, k) != Block.STONE.id)"));
        Assert.assertFalse(text.contains("if (l == 3 && i1 == 1)"));
    }

    @Test
    public void worldGenHellLavaDelegatesPocketGenerationToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(WORLD_GEN_HELL_LAVA_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("CaveLiquidPocketGenerationBehaviour"));
        Assert.assertTrue(text.contains("generateInNetherrack"));
        Assert.assertFalse(text.contains("if (world.getTypeId(i, j + 1, k) != Block.NETHERRACK.id)"));
        Assert.assertFalse(text.contains("if (l == 4 && i1 == 1)"));
    }

    @Test
    public void worldGenLightStoneWrappersDelegateClusterGenerationToCanonicalBehaviour() throws IOException {
        String lightStone1 = new String(Files.readAllBytes(WORLD_GEN_LIGHTSTONE1_PATH), StandardCharsets.UTF_8);
        String lightStone2 = new String(Files.readAllBytes(WORLD_GEN_LIGHTSTONE2_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(lightStone1.contains("GlowstoneClusterGenerationBehaviour"));
        Assert.assertTrue(lightStone1.contains("GLOWSTONE_CLUSTER_GENERATION_BEHAVIOUR.generate"));
        Assert.assertFalse(lightStone1.contains("for (int l = 0; l < 1500; ++l)"));

        Assert.assertTrue(lightStone2.contains("GlowstoneClusterGenerationBehaviour"));
        Assert.assertTrue(lightStone2.contains("GLOWSTONE_CLUSTER_GENERATION_BEHAVIOUR.generate"));
        Assert.assertFalse(lightStone2.contains("for (int l = 0; l < 1500; ++l)"));
    }

    @Test
    public void worldGenForestAndTreesDelegateTreeGenerationToCanonicalBehaviours() throws IOException {
        String forest = new String(Files.readAllBytes(WORLD_GEN_FOREST_PATH), StandardCharsets.UTF_8);
        String trees = new String(Files.readAllBytes(WORLD_GEN_TREES_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(forest.contains("ForestTreeGenerationBehaviour"));
        Assert.assertTrue(forest.contains("FOREST_TREE_GENERATION_BEHAVIOUR.generate"));
        Assert.assertFalse(forest.contains("int l = random.nextInt(3) + 5"));
        Assert.assertFalse(forest.contains("world.setRawTypeIdAndData(i, j + i2, k, Block.LOG.id, 2)"));

        Assert.assertTrue(trees.contains("DefaultTreeGenerationBehaviour"));
        Assert.assertTrue(trees.contains("DEFAULT_TREE_GENERATION_BEHAVIOUR.generate"));
        Assert.assertFalse(trees.contains("int l = random.nextInt(3) + 4"));
        Assert.assertFalse(trees.contains("world.setRawTypeId(i, j + i2, k, Block.LOG.id)"));
    }

    @Test
    public void worldGenTaigaWrappersDelegateTreeGenerationToCanonicalBehaviours() throws IOException {
        String taiga1 = new String(Files.readAllBytes(WORLD_GEN_TAIGA1_PATH), StandardCharsets.UTF_8);
        String taiga2 = new String(Files.readAllBytes(WORLD_GEN_TAIGA2_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(taiga1.contains("TaigaTreeGenerationBehaviour"));
        Assert.assertTrue(taiga1.contains("generateTaiga1"));
        Assert.assertFalse(taiga1.contains("int l = random.nextInt(5) + 7"));
        Assert.assertFalse(taiga1.contains("world.setRawTypeIdAndData(i, j + i2, k, Block.LOG.id, 1)"));

        Assert.assertTrue(taiga2.contains("TaigaTreeGenerationBehaviour"));
        Assert.assertTrue(taiga2.contains("generateTaiga2"));
        Assert.assertFalse(taiga2.contains("int l = random.nextInt(4) + 6"));
        Assert.assertFalse(taiga2.contains("world.setRawTypeIdAndData(i, j + j3, k, Block.LOG.id, 1)"));
    }

    @Test
    public void worldGenDungeonsDelegatesGenerationToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(WORLD_GEN_DUNGEONS_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("DungeonGenerationBehaviour"));
        Assert.assertTrue(text.contains("DUNGEON_GENERATION_BEHAVIOUR.generate"));
        Assert.assertFalse(text.contains("world.setTypeId(i, j, k, Block.MOB_SPAWNER.id)"));
        Assert.assertFalse(text.contains("tileentitymobspawner.a(this.b(random))"));
    }

    @Test
    public void worldGenLakesDelegatesGenerationToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(WORLD_GEN_LAKES_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("LakeGenerationBehaviour"));
        Assert.assertTrue(text.contains("LAKE_GENERATION_BEHAVIOUR.generate"));
        Assert.assertFalse(text.contains("boolean[] aboolean = new boolean[2048]"));
        Assert.assertFalse(text.contains("if (Block.byId[this.a].material == Material.LAVA)"));
    }

    @Test
    public void worldGenBigTreeDelegatesGenerationToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(WORLD_GEN_BIG_TREE_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("BigTreeGenerationBehaviour"));
        Assert.assertTrue(text.contains("this.behaviour.generate"));
        Assert.assertFalse(text.contains("this.b.setSeed(l)"));
        Assert.assertFalse(text.contains("this.a();"));
        Assert.assertFalse(text.contains("this.c.setRawTypeId"));
    }

    @Test
    public void mapGenBaseDelegatesSeedingAndIterationToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(MAP_GEN_BASE_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("MapGenerationSeedingBehaviour"));
        Assert.assertTrue(text.contains("MAP_GENERATION_SEEDING_BEHAVIOUR.run"));
        Assert.assertFalse(text.contains("this.b.setSeed(world.getSeed())"));
        Assert.assertFalse(text.contains("for (int j1 = i - k; j1 <= i + k; ++j1)"));
    }
}
