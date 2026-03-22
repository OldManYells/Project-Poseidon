package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacySecondaryWorldServerWrapperThinnessTest {
    private static final Path SECONDARY_WORLD_SERVER_PATH = Paths.get("src/main/java/net/minecraft/server/SecondaryWorldServer.java");

    @Test
    public void secondaryWorldServerDelegatesSharedMapLinkingToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(SECONDARY_WORLD_SERVER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("import com.legacyminecraft.poseidon.world.SharedWorldMapLinkBehaviour;"));
        Assert.assertTrue(text.contains("SHARED_WORLD_MAP_LINK_BEHAVIOUR.linkSharedMaps(this, worldserver);"));
        Assert.assertFalse(text.contains("this.worldMaps = worldserver.worldMaps;"));
    }
}
