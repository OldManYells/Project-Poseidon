package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacySpawnerWrapperThinnessTest {
    private static final Path SPAWNER_CREATURE_PATH = Paths.get("src/main/java/net/minecraft/server/SpawnerCreature.java");

    @Test
    public void spawnerCreatureDelegatesSpawnLogicToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(SPAWNER_CREATURE_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("CreatureSpawnBehaviour"));
        Assert.assertTrue(text.contains("CREATURE_SPAWN_BEHAVIOUR.spawnEntities"));
        Assert.assertTrue(text.contains("CREATURE_SPAWN_BEHAVIOUR.pickRandomBlockInChunkArea"));
        Assert.assertTrue(text.contains("CREATURE_SPAWN_BEHAVIOUR.canSpawnHere"));
        Assert.assertTrue(text.contains("CREATURE_SPAWN_BEHAVIOUR.applyPostSpawnExtras"));
        Assert.assertTrue(text.contains("CREATURE_SPAWN_BEHAVIOUR.spawnSleepThreats"));
        Assert.assertFalse(text.contains("candidateChunks"));
        Assert.assertFalse(text.contains("chunksLoop:"));
        Assert.assertFalse(text.contains("new Pathfinder(world)"));
    }
}
