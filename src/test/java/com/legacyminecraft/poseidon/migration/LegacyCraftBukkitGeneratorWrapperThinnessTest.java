package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftBukkitGeneratorWrapperThinnessTest {
    private static final Path INTERNAL_CHUNK_GENERATOR_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/generator/InternalChunkGenerator.java");
    private static final Path NORMAL_CHUNK_GENERATOR_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/generator/NormalChunkGenerator.java");
    private static final Path CUSTOM_CHUNK_GENERATOR_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/generator/CustomChunkGenerator.java");
    private static final Path NETHER_CHUNK_GENERATOR_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/generator/NetherChunkGenerator.java");
    private static final Path SKY_LANDS_CHUNK_GENERATOR_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/generator/SkyLandsChunkGenerator.java");

    @Test
    public void internalChunkGeneratorRemainsACompatibilityShell() throws IOException {
        String text = read(INTERNAL_CHUNK_GENERATOR_PATH);

        Assert.assertTrue(text.contains("public abstract class InternalChunkGenerator extends ChunkGenerator implements IChunkProvider"));
        Assert.assertFalse(text.contains("public boolean"));
        Assert.assertFalse(text.contains("public Chunk"));
        Assert.assertFalse(text.contains("public void"));
    }

    @Test
    public void normalChunkGeneratorDelegatesProviderForwardingToCanonicalBehaviour() throws IOException {
        String text = read(NORMAL_CHUNK_GENERATOR_PATH);

        Assert.assertTrue(text.contains("import com.legacyminecraft.compat.bukkit.NormalChunkGeneratorBehaviour;"));
        Assert.assertTrue(text.contains("NORMAL_CHUNK_GENERATOR_BEHAVIOUR"));
        Assert.assertTrue(text.contains("resolveChunkProvider(world)"));
        Assert.assertTrue(text.contains("generateUnsupported()"));
        Assert.assertTrue(text.contains("canSpawn(world, x, z)"));
        Assert.assertTrue(text.contains("getDefaultPopulators(world)"));
        Assert.assertTrue(text.contains("isChunkLoaded(provider, i, i1)"));
        Assert.assertTrue(text.contains("getOrCreateChunk(provider, i, i1)"));
        Assert.assertTrue(text.contains("getChunkAt(provider, i, i1)"));
        Assert.assertTrue(text.contains("getChunkAt(provider, icp, i, i1)"));
        Assert.assertTrue(text.contains("saveChunks(provider, bln, ipu)"));
        Assert.assertTrue(text.contains("unloadChunks(provider)"));
        Assert.assertTrue(text.contains("canSave(provider)"));
        Assert.assertFalse(text.contains("provider.isChunkLoaded(i, i1)"));
        Assert.assertFalse(text.contains("provider.getOrCreateChunk(i, i1)"));
        Assert.assertFalse(text.contains("provider.getChunkAt(i, i1)"));
        Assert.assertFalse(text.contains("provider.getChunkAt(icp, i, i1)"));
        Assert.assertFalse(text.contains("provider.saveChunks(bln, ipu)"));
        Assert.assertFalse(text.contains("provider.unloadChunks()"));
        Assert.assertFalse(text.contains("provider.canSave()"));
        Assert.assertFalse(text.contains("throw new UnsupportedOperationException(\"Not supported.\");"));
    }

    @Test
    public void customChunkGeneratorDelegatesChunkCreationAndSpawnPolicyToCanonicalBehaviour() throws IOException {
        String text = read(CUSTOM_CHUNK_GENERATOR_PATH);

        Assert.assertTrue(text.contains("import com.legacyminecraft.compat.bukkit.CustomChunkGeneratorBehaviour;"));
        Assert.assertTrue(text.contains("CUSTOM_CHUNK_GENERATOR_BEHAVIOUR"));
        Assert.assertTrue(text.contains("createRandom(seed)"));
        Assert.assertTrue(text.contains("isChunkLoaded()"));
        Assert.assertTrue(text.contains("getChunkAt(world, generator, random, x, z)"));
        Assert.assertTrue(text.contains("getChunkAt(icp, i, i1)"));
        Assert.assertTrue(text.contains("saveChunks(bln, ipu)"));
        Assert.assertTrue(text.contains("unloadChunks()"));
        Assert.assertTrue(text.contains("canSave()"));
        Assert.assertTrue(text.contains("generate(world, random, x, z, generator)"));
        Assert.assertTrue(text.contains("canSpawn(world, x, z, generator)"));
        Assert.assertTrue(text.contains("getDefaultPopulators(world, generator)"));
        Assert.assertFalse(text.contains("this.random = new Random(seed);"));
        Assert.assertFalse(text.contains("random.setSeed((long) x * 341873128712L + (long) z * 132897987541L);"));
        Assert.assertFalse(text.contains("byte[] types = generator.generate(world.getWorld(), random, x, z);"));
        Assert.assertFalse(text.contains("Chunk chunk = new Chunk(world, types, x, z);"));
        Assert.assertFalse(text.contains("chunk.initLighting();"));
        Assert.assertFalse(text.contains("// Nothing!"));
    }

    @Test
    public void netherAndSkyLandsChunkGeneratorsRemainThinSubclassShells() throws IOException {
        String netherText = read(NETHER_CHUNK_GENERATOR_PATH);
        String skyLandsText = read(SKY_LANDS_CHUNK_GENERATOR_PATH);

        Assert.assertTrue(netherText.contains("public class NetherChunkGenerator extends NormalChunkGenerator"));
        Assert.assertTrue(netherText.contains("super(world, seed);"));
        Assert.assertFalse(netherText.contains("public boolean"));
        Assert.assertFalse(netherText.contains("public Chunk"));
        Assert.assertFalse(netherText.contains("provider."));

        Assert.assertTrue(skyLandsText.contains("public class SkyLandsChunkGenerator extends NormalChunkGenerator"));
        Assert.assertTrue(skyLandsText.contains("super(world, seed);"));
        Assert.assertFalse(skyLandsText.contains("public boolean"));
        Assert.assertFalse(skyLandsText.contains("public Chunk"));
        Assert.assertFalse(skyLandsText.contains("provider."));
    }

    private static String read(Path path) throws IOException {
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }
}
