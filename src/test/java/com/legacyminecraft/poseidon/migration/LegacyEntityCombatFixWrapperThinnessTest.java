package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyEntityCombatFixWrapperThinnessTest {
    private static final Path ENTITY_LIVING_PATH = Paths.get("src/main/java/net/minecraft/server/EntityLiving.java");
    private static final Path ENTITY_HUMAN_PATH = Paths.get("src/main/java/net/minecraft/server/EntityHuman.java");
    private static final Path ENTITY_SKELETON_PATH = Paths.get("src/main/java/net/minecraft/server/EntitySkeleton.java");

    @Test
    public void entityWrappersUseCombatFixConfigPolicyForLegacyFixToggles() throws IOException {
        String livingText = new String(Files.readAllBytes(ENTITY_LIVING_PATH), StandardCharsets.UTF_8);
        String humanText = new String(Files.readAllBytes(ENTITY_HUMAN_PATH), StandardCharsets.UTF_8);
        String skeletonText = new String(Files.readAllBytes(ENTITY_SKELETON_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(livingText.contains("CombatFixConfigPolicy"));
        Assert.assertTrue(humanText.contains("CombatFixConfigPolicy"));
        Assert.assertTrue(skeletonText.contains("CombatFixConfigPolicy"));

        Assert.assertFalse(livingText.contains("\"settings.fix-drowning-push-down.enabled\""));
        Assert.assertFalse(humanText.contains("\"settings.player-knockback-fix.enabled\""));
        Assert.assertFalse(skeletonText.contains("\"world.settings.skeleton-shooting-sound-fix.enabled\""));
    }
}
