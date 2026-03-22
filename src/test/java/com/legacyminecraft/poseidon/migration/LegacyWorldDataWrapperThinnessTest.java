package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyWorldDataWrapperThinnessTest {
    private static final Path WORLD_DATA_PATH = Paths.get("src/main/java/net/minecraft/server/WorldData.java");

    @Test
    public void worldDataDelegatesNbtReadWritePolicyToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(WORLD_DATA_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("WorldDataStateBehaviour"));
        Assert.assertTrue(text.contains("WORLD_DATA_STATE_BEHAVIOUR.readFromTag"));
        Assert.assertTrue(text.contains("WORLD_DATA_STATE_BEHAVIOUR.createSaveTag"));
        Assert.assertTrue(text.contains("WORLD_DATA_STATE_BEHAVIOUR.createSaveTagWithFirstPlayer"));
        Assert.assertTrue(text.contains("poseidonSetSeed"));
        Assert.assertTrue(text.contains("poseidonSetLastPlayed"));
        Assert.assertTrue(text.contains("poseidonSetCachedPlayerData"));
        Assert.assertTrue(text.contains("poseidonGetCachedPlayerData"));
        Assert.assertFalse(text.contains("this.a = nbttagcompound.getLong(\"RandomSeed\")"));
        Assert.assertFalse(text.contains("nbttagcompound.setLong(\"LastPlayed\", System.currentTimeMillis())"));
        Assert.assertFalse(text.contains("entityhuman.d(nbttagcompound1);"));
    }
}
