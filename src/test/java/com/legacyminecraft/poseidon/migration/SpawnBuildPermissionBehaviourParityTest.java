package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SpawnBuildPermissionBehaviourParityTest {
    private static final Path SPAWN_BUILD_PERMISSION_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/SpawnBuildPermissionBehaviour.java");

    @Test
    public void spawnBuildPermissionUsesLegacySpawnRadiusRules() throws IOException {
        String text = new String(Files.readAllBytes(SPAWN_BUILD_PERMISSION_BEHAVIOUR_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("int spawnSize = Bukkit.getServer().getSpawnRadius();"));
        Assert.assertTrue(text.contains("if (spawnSize <= 0)"));
        Assert.assertTrue(text.contains("if (player.isOp())"));
        Assert.assertTrue(text.contains("Math.max(Math.abs(x - spawn.x), Math.abs(z - spawn.z))"));
        Assert.assertFalse(text.contains("getOnlineMode()"));
    }
}
