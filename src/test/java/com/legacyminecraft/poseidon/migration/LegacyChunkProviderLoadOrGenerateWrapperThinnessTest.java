package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyChunkProviderLoadOrGenerateWrapperThinnessTest {
    private static final Path CHUNK_PROVIDER_PATH = Paths.get("src/main/java/net/minecraft/server/ChunkProviderLoadOrGenerate.java");

    @Test
    public void chunkProviderDelegatesCacheBookkeepingToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CHUNK_PROVIDER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("import com.legacyminecraft.poseidon.world.chunk.ChunkProviderCacheBehaviour;"));
        Assert.assertTrue(text.contains("import com.legacyminecraft.poseidon.world.chunk.ChunkPopulationTriggerBehaviour;"));
        Assert.assertTrue(text.contains("import com.legacyminecraft.poseidon.world.chunk.ChunkProviderPersistencePolicyBehaviour;"));
        Assert.assertTrue(text.contains("CHUNK_PROVIDER_CACHE_BEHAVIOUR.isLoaded(this.e, i, j)"));
        Assert.assertTrue(text.contains("CHUNK_PROVIDER_CACHE_BEHAVIOUR.removeUnloadRequest(this.a, i, j)"));
        Assert.assertTrue(text.contains("CHUNK_PROVIDER_CACHE_BEHAVIOUR.getLoadedChunk(this.e, i, j)"));
        Assert.assertTrue(text.contains("CHUNK_PROVIDER_CACHE_BEHAVIOUR.cacheChunk(this.e, this.f, i, j, chunk)"));
        Assert.assertTrue(text.contains("CHUNK_POPULATION_TRIGGER_BEHAVIOUR.shouldPopulateCurrentChunk(chunk, hasSouthEast && hasSouth && hasEast)"));
        Assert.assertTrue(text.contains("CHUNK_POPULATION_TRIGGER_BEHAVIOUR.shouldPopulateWestNeighbor(hasWest, westDone, hasSouthWest, hasSouth)"));
        Assert.assertTrue(text.contains("CHUNK_POPULATION_TRIGGER_BEHAVIOUR.shouldPopulateNorthNeighbor(hasNorth, northDone, hasNorthEast, hasEast)"));
        Assert.assertTrue(text.contains("CHUNK_POPULATION_TRIGGER_BEHAVIOUR.shouldPopulateNorthWestNeighbor(hasNorthWest, northWestDone, hasNorth, hasWest)"));
        Assert.assertTrue(text.contains("CHUNK_PROVIDER_PERSISTENCE_POLICY_BEHAVIOUR.shouldWriteChunkMetadata(flag, chunk.p)"));
        Assert.assertTrue(text.contains("CHUNK_PROVIDER_PERSISTENCE_POLICY_BEHAVIOUR.shouldStopIncrementalSave(i, flag)"));
        Assert.assertTrue(text.contains("CHUNK_PROVIDER_PERSISTENCE_POLICY_BEHAVIOUR.shouldFlushChunkLoader(flag, this.d)"));
        Assert.assertTrue(text.contains("CHUNK_PROVIDER_PERSISTENCE_POLICY_BEHAVIOUR.maxChunksPerUnloadPass()"));
        Assert.assertTrue(text.contains("CHUNK_PROVIDER_PERSISTENCE_POLICY_BEHAVIOUR.hasQueuedUnloads(this.a.isEmpty())"));
        Assert.assertFalse(text.contains("this.e.containsKey(Integer.valueOf(ChunkCoordIntPair.a(i, j)))"));
        Assert.assertFalse(text.contains("this.a.remove(Integer.valueOf(k));"));
        Assert.assertFalse(text.contains("this.e.put(Integer.valueOf(k), chunk);"));
        Assert.assertFalse(text.contains("if (!chunk.done && this.isChunkLoaded(i + 1, j + 1) && this.isChunkLoaded(i, j + 1) && this.isChunkLoaded(i + 1, j))"));
        Assert.assertFalse(text.contains("if (i == 24 && !flag)"));
        Assert.assertFalse(text.contains("for (int i = 0; i < 100; ++i)"));
    }
}
