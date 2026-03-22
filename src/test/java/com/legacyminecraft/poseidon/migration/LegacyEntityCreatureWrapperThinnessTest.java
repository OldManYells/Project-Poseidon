package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyEntityCreatureWrapperThinnessTest {
    private static final Path ENTITY_CREATURE_PATH = Paths.get("src/main/java/net/minecraft/server/EntityCreature.java");

    @Test
    public void entityCreatureDelegatesRoamingTargetSelectionToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(ENTITY_CREATURE_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("EntityCreatureRoamingBehaviour"));
        Assert.assertTrue(text.contains("ENTITY_CREATURE_ROAMING_BEHAVIOUR.selectBestRoamTarget"));
        Assert.assertFalse(text.contains("for (int l = 0; l < 10; ++l) {"));
        Assert.assertFalse(text.contains("int i1 = MathHelper.floor(this.locX + (double) this.random.nextInt(13) - 6.0D);"));
    }
}
