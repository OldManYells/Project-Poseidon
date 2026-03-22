package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyWorldProviderBoundaryTest {
    private static final Path WORLD_PROVIDER_NORMAL_PATH = Paths.get("src/main/java/net/minecraft/server/WorldProviderNormal.java");

    @Test
    public void worldProviderNormalImplementsCanonicalOverworldContract() throws IOException {
        String text = new String(Files.readAllBytes(WORLD_PROVIDER_NORMAL_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("import com.legacyminecraft.poseidon.world.OverworldProviderContract;"));
        Assert.assertTrue(text.contains("public class WorldProviderNormal extends WorldProvider implements OverworldProviderContract"));
    }
}
